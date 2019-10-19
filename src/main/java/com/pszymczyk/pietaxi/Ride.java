package com.pszymczyk.pietaxi;

import java.time.Clock;
import java.time.Instant;
import java.util.LinkedList;

class Ride {

    static class PingLocation {
        private final Instant time;
        private final Location location;

        PingLocation(Instant time, Location location) {
            this.time = time;
            this.location = location;
        }
    }

    private final RideId rideId;
    private final PassengerId passengerId;
    private final DriverId driverId;
    private final DistanceCalculationPrecisionPolicy distanceCalculationPrecisionPolicy;

    Ride(RideId rideId, PassengerId passengerId, DriverId driverId, DistanceCalculationPrecisionPolicy distanceCalculationPrecisionPolicy) {
        this.rideId = rideId;
        this.passengerId = passengerId;
        this.driverId = driverId;
        this.distanceCalculationPrecisionPolicy = distanceCalculationPrecisionPolicy;
    }

    private final LinkedList<PingLocation> locations = new LinkedList<>();

    void start(Location location, Clock clock) {
        if (location == null) {
            throw new NullPointerException("location cannot be null!");
        }

        if (!locations.isEmpty()) {
            throw new RideAlreadyStarted();
        }

        locations.add(new PingLocation(clock.instant(), location));
    }


    void ping(Location location, Clock clock) {
        if (location == null) {
            throw new NullPointerException("location cannot be null!");
        }

        locations.add(new PingLocation(clock.instant(), location));
    }

    void stop(Location location, Clock clock, RideEvents rideEvents){
        if (location != null) {
            locations.add(new PingLocation(clock.instant(), location));
        }

        if (locations.size() < 2 || !distanceCalculationPrecisionPolicy.enoughDataToCalculateDistance(locations)) {
            rideEvents.publish(new CorruptedRideFinished(rideId));
        }

        PingLocation x = locations.poll();
        Instant startTime = x.time;
        Instant endTime = null;
        Distance distance = Distance.zero();


        while (!locations.isEmpty()) {
            PingLocation ping = locations.poll();
            endTime = ping.time;
            distance = distance.and(new Distance(x.location, ping.location));
            x = ping;
        }


        rideEvents.publish(new RideFinished(rideId, passengerId, driverId, distance, startTime, endTime));
    }
}
