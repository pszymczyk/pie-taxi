package com.pszymczyk.pietaxi.rides.traffic.application;

import com.pszymczyk.pietaxi.model.RideId;
import com.pszymczyk.pietaxi.rides.traffic.model.Location;

public class StopRideCommand {

    private final RideId rideId;
    private final Location location;

    public StopRideCommand(RideId rideId, Location location) {
        this.rideId = rideId;
        this.location = location;
    }

    RideId getRideId() {
        return rideId;
    }

    Location getLocation() {
        return location;
    }
}
