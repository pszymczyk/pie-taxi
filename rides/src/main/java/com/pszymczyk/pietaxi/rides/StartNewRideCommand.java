package com.pszymczyk.pietaxi.rides;

class StartNewRideCommand {

    private final PassengerId passengerId;
    private final DriverId driverId;
    private final Location location;

    public StartNewRideCommand(PassengerId passengerId, DriverId driverId, Location location) {
        this.passengerId = passengerId;
        this.driverId = driverId;
        this.location = location;
    }

    public PassengerId getPassengerId() {
        return passengerId;
    }

    public DriverId getDriverId() {
        return driverId;
    }

    public Location getLocation() {
        return location;
    }
}
