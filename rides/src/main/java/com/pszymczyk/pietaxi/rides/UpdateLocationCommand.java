package com.pszymczyk.pietaxi.rides;

class UpdateLocationCommand {

    private final RideId rideId;
    private final Location location;

    UpdateLocationCommand(RideId rideId, Location location) {
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
