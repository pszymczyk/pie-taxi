package com.pszymczyk.pietaxi.matching;

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
