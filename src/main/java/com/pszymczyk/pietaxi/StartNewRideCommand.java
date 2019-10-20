package com.pszymczyk.pietaxi;

class StartNewRideCommand {

    private final PassengerId passengerId;
    private final DriverId driverId;

    public StartNewRideCommand(PassengerId passengerId, DriverId driverId) {
        this.passengerId = passengerId;
        this.driverId = driverId;
    }

    public PassengerId getPassengerId() {
        return passengerId;
    }

    public DriverId getDriverId() {
        return driverId;
    }
}
