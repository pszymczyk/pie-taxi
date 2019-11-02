package com.pszymczyk.pietaxi.rides;

import java.util.Random;
import java.util.UUID;

import com.pszymczyk.pietaxi.PassengerId;

public class TestFixtures {

    private static final Random random = new Random(99);

    public static Ride newRide() {
        return newRide(new DefaultDistanceCalculationPrecisionPolicy());
    }

    public static Ride newRide(DistanceCalculationRequirementsPolicy precisionPolicy) {
        return new Ride(new RideId(UUID.randomUUID()), randomPassenger(), randomDriver(), precisionPolicy);
    }

    public static DriverId randomDriver() {
        return new DriverId("Test driver " + random.nextInt());
    }

    public static PassengerId randomPassenger() {
        return new PassengerId("Test user " + random.nextInt());
    }

    public static Location randomLocation() {
        return new Location(random.nextInt(), random.nextInt());
    }

}
