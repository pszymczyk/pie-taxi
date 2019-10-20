package com.pszymczyk.pietaxi;

import java.util.List;
import java.util.UUID;

class TestFixtures {

    static Ride newRide() {
        return newRide(new DistanceCalculationRequirementsPolicy() {
            @Override
            public String name() {
                return "always true";
            }

            @Override
            public boolean enoughDataToCalculateDistance(List<Ride.PingLocation> locations) {
                return true;
            }
        });
    }

    static Ride newRide(DistanceCalculationRequirementsPolicy precisionPolicy) {
        return new Ride(new RideId(UUID.randomUUID()), randomPassenger(), randomDriver(), precisionPolicy);
    }

    static DriverId randomDriver() {
        return new DriverId(UUID.randomUUID());
    }

    static PassengerId randomPassenger() {
        return new PassengerId("Test user");
    }

    static Location randomLocation() {
        return new Location(0,0);
    }

}
