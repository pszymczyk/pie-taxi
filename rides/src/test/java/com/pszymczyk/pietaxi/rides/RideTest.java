package com.pszymczyk.pietaxi.rides;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class RideTest {

    private static final Clock clock = Clock.fixed(Instant.parse("2018-08-19T16:45:42.00Z"), ZoneId.systemDefault());

    @Test
    void Should_calculate_distance() {
        //given
        Ride ride = TestFixtures.newRide();
        TestRideEvents testRideEvents = new TestRideEvents();

        //when
        ride.start(new Location(1, 1), clock);

        //and
        ride.ping(new Location(2, 2), clock);

        //and
        ride.stop(new Location(3, 3), clock, testRideEvents);

        //then
        assertThat(testRideEvents.rideFinishedEvents).hasSize(1)
                  .first()
                  .extracting(RideFinished::getDistance)
                  .isEqualTo(new Distance(new BigDecimal(2.82)));
    }

    @Test
    void Should_allow_to_stop_transaction_with_null_location() {
        //given
        Ride ride = TestFixtures.newRide();
        TestRideEvents testRideEvents = new TestRideEvents();

        //when
        ride.start(new Location(1, 1), clock);

        //and
        ride.ping(new Location(2, 2), clock);

        //and
        ride.ping(new Location(5, 5), clock);

        //and
        ride.stop(null, clock, testRideEvents);

        //then
        assertThat(testRideEvents.rideFinishedEvents).hasSize(1)
                  .first()
                  .extracting(RideFinished::getDistance)
                  .isEqualTo(new Distance(new BigDecimal(5.65)));
    }

    @Test
    void Should_send_info_about_corrupted_data_when_policy_said_that_there_is_no_enough_pings_to_start() {
        //given
        Ride ride = TestFixtures.newRide(alwaysFalsePolicy());
        TestRideEvents testRideEvents = new TestRideEvents();

        //when
        ride.start(new Location(1, 1), clock);

        //and
        ride.ping(new Location(2, 2), clock);

        //and when
        ride.stop(new Location(3, 3), clock, testRideEvents);

        //then
        assertThat(testRideEvents.corruptedRideFinishedEvents).hasSize(1);
    }

    @Test
    void Should_send_info_about_corrupted_data_when_less_then_two_pings() {
        //given
        Ride ride = TestFixtures.newRide();
        TestRideEvents testRideEvents = new TestRideEvents();

        //when
        ride.start(new Location(1, 1), clock);

        //and when
        ride.stop(null, clock, testRideEvents);

        //then
        assertThat(testRideEvents.corruptedRideFinishedEvents).hasSize(1);
    }

    @Test
    void Should_throw_exception_when_try_to_start_ride_twice() {
        //given
        Ride ride = TestFixtures.newRide();

        //when
        ride.start(new Location(1, 1), clock);

        //and when
        Throwable thrown = catchThrowable(() -> ride.start(new Location(1, 1), clock));

        //then
        assertThat(thrown).isInstanceOf(RideAlreadyStarted.class);
    }

    static class TestRideEvents implements RideEvents {

        private final Queue<RideFinished> rideFinishedEvents = new LinkedList<>();
        private final Queue<CorruptedRideFinished> corruptedRideFinishedEvents = new LinkedList<>();

        @Override
        public void publish(CorruptedRideFinished corruptedRideFinished) {
            corruptedRideFinishedEvents.add(corruptedRideFinished);
        }

        @Override
        public void publish(RideFinished rideFinished) {
            rideFinishedEvents.add(rideFinished);
        }
    }

    private DistanceCalculationRequirementsPolicy alwaysFalsePolicy() {
        return new DistanceCalculationRequirementsPolicy() {
            @Override
            public String name() {
                return "always false";
            }

            @Override
            public boolean enoughDataToCalculateDistance(List<Ride.PingLocation> locations) {
                return false;
            }
        };
    }
}
