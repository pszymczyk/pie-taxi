package com.pszymczyk.pietaxi.match;

import com.pszymczyk.pietaxi.DriverId;

class DriverAccepted {

    private final DriverId driverId;

    DriverAccepted(DriverId driverId) {
        this.driverId = driverId;
    }

    DriverId getDriverId() {
        return driverId;
    }
}
