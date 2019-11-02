package com.pszymczyk.pietaxi.rides.traffic.application;

import com.pszymczyk.pietaxi.rides.traffic.model.Location;
import com.pszymczyk.pietaxi.model.RideId;

public class UpdateLocationCommand {

    private final RideId rideId;
    private final Location location;

    public UpdateLocationCommand(RideId rideId, Location location) {
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
