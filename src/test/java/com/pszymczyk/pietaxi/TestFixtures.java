package com.pszymczyk.pietaxi;

import java.util.UUID;

class TestFixtures {

    static Ride newRide() {
        return newRide(locations -> locations.size() > 2);
    }

    static Ride newRide(DistanceCalculationPrecisionPolicy precisionPolicy) {
        return new Ride(new RideId(UUID.randomUUID()), randomPassenger(), randomDriver(), precisionPolicy);
    }

    static DriverId randomDriver() {
        return new DriverId(UUID.randomUUID());
    }

    static PassengerId randomPassenger() {
        return new PassengerId(UUID.randomUUID());
    }

}
