package com.pszymczyk.pietaxi.rides;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.pszymczyk.pietaxi.rides.TestFixtures.randomLocation;

class PingsPerMinutePolicyTest {

    private static final Clock fixedClock = Clock.fixed(Instant.parse("2018-04-29T10:15:30.00Z"), ZoneId.systemDefault());

    @Test
    void Should_return_true_if_there_is_enough_data_to_calculate() {
        //given
        PingsPerMinutePolicy pingsPerMinutePolicy = new PingsPerMinutePolicy(0.33);
        Ride.PingLocation one = new Ride.PingLocation(fixedClock.instant(), randomLocation());
        Ride.PingLocation second = new Ride.PingLocation(fixedClock.instant().plusSeconds(30), randomLocation());
        Ride.PingLocation third = new Ride.PingLocation(fixedClock.instant().plusSeconds(45), randomLocation());
        Ride.PingLocation fourth = new Ride.PingLocation(fixedClock.instant().plusSeconds(60), randomLocation());

        //when
        boolean result = pingsPerMinutePolicy.enoughDataToCalculateDistance(Arrays.asList(one, second, third, fourth));

        //then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    void Should_return_false_if_there_is_no_enough_data_to_calculate() {
        //given
        PingsPerMinutePolicy pingsPerMinutePolicy = new PingsPerMinutePolicy(0.01);
        Ride.PingLocation one = new Ride.PingLocation(fixedClock.instant(), randomLocation());
        Ride.PingLocation second = new Ride.PingLocation(fixedClock.instant().plusSeconds(30), randomLocation());
        Ride.PingLocation third = new Ride.PingLocation(fixedClock.instant().plusSeconds(60), randomLocation());

        //when
        boolean result = pingsPerMinutePolicy.enoughDataToCalculateDistance(Arrays.asList(one, second, third));

        //then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    void Should_through_exception_when_ride_shorter_than_minute() {
        //given
        PingsPerMinutePolicy pingsPerMinutePolicy = new PingsPerMinutePolicy(0.01);
        Ride.PingLocation one = new Ride.PingLocation(fixedClock.instant(), randomLocation());
        Ride.PingLocation second = new Ride.PingLocation(fixedClock.instant().plusSeconds(1), randomLocation());
        Ride.PingLocation third = new Ride.PingLocation(fixedClock.instant().plusSeconds(1), randomLocation());

        //when
        Throwable thrown = Assertions.catchThrowable(() -> pingsPerMinutePolicy.enoughDataToCalculateDistance(Arrays.asList(one, second, third)));

        //then
        Assertions.assertThat(thrown).isInstanceOf(IllegalStateException.class);
    }
}
