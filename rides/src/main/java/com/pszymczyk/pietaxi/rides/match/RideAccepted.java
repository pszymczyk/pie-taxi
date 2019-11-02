package com.pszymczyk.pietaxi.rides.match;

import java.util.List;

import com.pszymczyk.pietaxi.PassengerId;
import com.pszymczyk.pietaxi.rides.DriverId;
import com.pszymczyk.pietaxi.rides.Location;

public class RideAccepted {

    private final PassengerId passengerId;
    private final DriverId driverId;
    private final Location location;
    private final List<PassengerId> friends;

    RideAccepted(PassengerId passengerId, DriverId driverId, Location location, List<PassengerId> friends) {
        this.passengerId = passengerId;
        this.driverId = driverId;
        this.location = location;
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

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "RideAccepted{" +
                "passengerId=" + passengerId +
                ", driverId=" + driverId +
                ", location=" + location +
                ", friends=" + friends +
                '}';
    }
}
