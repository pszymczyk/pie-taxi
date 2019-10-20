package com.pszymczyk.pietaxi;

import java.util.List;
import java.util.Random;
import java.util.UUID;

class TestFixtures {

    private static final Random random = new Random(99);

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
        return new DriverId("Test driver " + random.nextInt());
    }

    static PassengerId randomPassenger() {
        return new PassengerId("Test user " + random.nextInt());
    }

    static Location randomLocation() {
        return new Location(random.nextInt(), random.nextInt());
    }

}
