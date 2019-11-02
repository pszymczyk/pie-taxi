package com.pszymczyk.pietaxi.rides.match;

import com.pszymczyk.pietaxi.rides.traffic.model.DriverId;

class DriverAccepted {

    private final DriverId driverId;

    DriverAccepted(DriverId driverId) {
        this.driverId = driverId;
    }

    DriverId getDriverId() {
        return driverId;
    }
}
