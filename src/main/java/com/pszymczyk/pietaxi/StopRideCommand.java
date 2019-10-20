package com.pszymczyk.pietaxi;

class StopRideCommand {

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
