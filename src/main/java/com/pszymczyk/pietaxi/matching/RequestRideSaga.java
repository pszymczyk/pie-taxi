package com.pszymczyk.pietaxi.matching;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.pszymczyk.pietaxi.DriverId;
import com.pszymczyk.pietaxi.Location;
import com.pszymczyk.pietaxi.PassengerId;

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
        driverId = driverAccepted.getDriverId();
        completeIfPossible();
    }

    void handle(FriendFound friendFound) {
        friends.add(friendFound.getPassengerId());
        if (friends.size() >= 2) {
            requestRideSagaEvents.publish(new CabIsFull(id));
        }
        completeIfPossible();
    }

    void timeout() {
        if (driverId != null) {
            state = State.COMPLETED;
            requestRideSagaEvents.publish(new RideAccepted(passengerId, driverId, location, friends));
        } else {
            state = State.TIMEOUT;
            requestRideSagaEvents.publish(new AllDriversBusy(id));
        }
    }

    private void completeIfPossible() {
        if (friends.size() >= 2 && driverId != null) {
            state = State.COMPLETED;
            requestRideSagaEvents.publish(new RideAccepted(passengerId, driverId, location, friends));
        }
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
