package com.pszymczyk.pietaxi;

import java.util.Random;

public class TestFixtures {

    private static final Random random = new Random(99);

    public static Ride newRide() {
        throw new TODO();
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
