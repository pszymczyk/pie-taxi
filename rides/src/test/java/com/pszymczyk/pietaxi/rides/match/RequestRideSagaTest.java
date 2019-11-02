package com.pszymczyk.pietaxi.rides.match;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import static com.pszymczyk.pietaxi.rides.traffic.TestFixtures.randomDriver;
import static com.pszymczyk.pietaxi.rides.traffic.TestFixtures.randomLocation;
import static com.pszymczyk.pietaxi.rides.traffic.TestFixtures.randomPassenger;
import static org.assertj.core.api.Assertions.assertThat;

class RequestRideSagaTest {

    @Test
    void Should_publish_event_when_cab_is_full() {
        //given
        TestEvents events = new TestEvents();
        RequestRideSaga requestRideSaga = new RequestRideSaga(randomPassenger(), randomLocation(), events);
        requestRideSaga.handle(new FriendFound(randomPassenger()));

        //when cab is full
        requestRideSaga.handle(new FriendFound(randomPassenger()));

        //and when third friend tries to join
        requestRideSaga.handle(new FriendFound(randomPassenger()));

        //then
        assertThat(events.cabIsFullEvents).hasSize(2);
        assertThat(events.allDriversBusyEvents).isEmpty();
        assertThat(events.rideAcceptedEvents).isEmpty();
    }

    @Test
    void Should_accept_ride_when_cab_is_full_and_driver_join() {
        //given
        TestEvents events = new TestEvents();
        RequestRideSaga requestRideSaga = new RequestRideSaga(randomPassenger(), randomLocation(), events);

        //when cab is full
        requestRideSaga.handle(new FriendFound(randomPassenger()));
        requestRideSaga.handle(new FriendFound(randomPassenger()));

        //and driver accepted
        requestRideSaga.handle(new DriverAccepted(randomDriver()));

        //then
        assertThat(events.cabIsFullEvents).hasSize(1);

        //and
        assertThat(events.rideAcceptedEvents).hasSize(1);
        assertThat(events.cabIsFullEvents).hasSize(1);
        assertThat(events.allDriversBusyEvents).isEmpty();
    }

    @Test
    void Should_send_timeout_when_driver_not_joined() {
        //given
        TestEvents events = new TestEvents();
        RequestRideSaga requestRideSaga = new RequestRideSaga(randomPassenger(), randomLocation(), events);

        //when
        requestRideSaga.timeout();

        //then
        assertThat(events.allDriversBusyEvents).hasSize(1);
        assertThat(events.rideAcceptedEvents).isEmpty();
        assertThat(events.cabIsFullEvents).isEmpty();
    }

    @Test
    void Should_send_ride_accepted_after_timeout_when_driver_joined() {
        //given
        TestEvents events = new TestEvents();
        RequestRideSaga requestRideSaga = new RequestRideSaga(randomPassenger(), randomLocation(), events);
        requestRideSaga.handle(new DriverAccepted(randomDriver()));

        //when
        requestRideSaga.timeout();

        //then
        assertThat(events.rideAcceptedEvents).hasSize(1);
        assertThat(events.allDriversBusyEvents).isEmpty();
        assertThat(events.cabIsFullEvents).isEmpty();
    }

    static class TestEvents implements RequestRideSagaEvents {

        LinkedList<AllDriversBusy> allDriversBusyEvents = new LinkedList<>();
        LinkedList<RideAccepted> rideAcceptedEvents = new LinkedList<>();
        LinkedList<CabIsFull> cabIsFullEvents = new LinkedList<>();

        @Override
        public void publish(AllDriversBusy allDriversBusy) {
            allDriversBusyEvents.add(allDriversBusy);
        }

        @Override
        public void publish(RideAccepted rideAccepted) {
            rideAcceptedEvents.add(rideAccepted);
        }

        @Override
        public void publish(CabIsFull cabIsFull) {
            cabIsFullEvents.add(cabIsFull);
        }
    }
}
