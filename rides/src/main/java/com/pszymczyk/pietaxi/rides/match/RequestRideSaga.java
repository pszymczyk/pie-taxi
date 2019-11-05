package com.pszymczyk.pietaxi.rides.match;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.pszymczyk.pietaxi.model.PassengerId;
import com.pszymczyk.pietaxi.rides.traffic.model.DriverId;
import com.pszymczyk.pietaxi.rides.traffic.model.Location;

class RequestRideSaga {

    enum State {
        ACTIVE,
        COMPLETED,
        TIMEOUT
    }

    final SagaId id;
    final PassengerId passengerId;
    final Location location;
    final List<PassengerId> friends = new ArrayList<>();
    final RequestRideSagaEvents requestRideSagaEvents;

    DriverId driverId;
    State state = State.ACTIVE;

    RequestRideSaga(PassengerId passengerId, Location location, RequestRideSagaEvents requestRideSagaEvents) {
        this.id = new SagaId(UUID.randomUUID());
        this.passengerId = passengerId;
        this.location = location;
        this.requestRideSagaEvents = requestRideSagaEvents;
    }

    void handle(DriverAccepted driverAccepted) {

    }

    void handle(FriendFound friendFound) {
    }

    void timeout() {
    }

    @Override
    public String toString() {
        return "RequestRideSaga{" +
                "id=" + id +
                ", passengerId=" + passengerId +
                ", friends=" + friends +
                ", driverId=" + driverId +
                ", state=" + state +
                '}';
    }
}
