package com.pszymczyk.pietaxi.matching;

import java.util.List;

import com.pszymczyk.pietaxi.DriverId;
import com.pszymczyk.pietaxi.PassengerId;

public class RideAccepted {

    private final PassengerId passengerId;
    private final DriverId driverId;
    private final List<PassengerId> friends;

    RideAccepted(PassengerId passengerId, DriverId driverId, List<PassengerId> friends) {
        this.passengerId = passengerId;
        this.driverId = driverId;
        this.friends = friends;
    }

    public PassengerId getPassengerId() {
        return passengerId;
    }

    public DriverId getDriverId() {
        return driverId;
    }

    public List<PassengerId> getFriends() {
        return friends;
    }
}
