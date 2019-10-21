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

        public Instant getTime() {
            return time;
        }

        public Location getLocation() {
            return location;
        }
    }

    final RideId rideId;
    final PassengerId passengerId;
    final DriverId driverId;
    final LinkedList<PingLocation> locations = new LinkedList<>();
    final DistanceCalculationRequirementsPolicy distanceCalculationRequirementsPolicy;

    Ride(RideId rideId, PassengerId passengerId, DriverId driverId, DistanceCalculationRequirementsPolicy distanceCalculationRequirementsPolicy) {
        this.rideId = rideId;
        this.passengerId = passengerId;
        this.driverId = driverId;
        this.distanceCalculationRequirementsPolicy = distanceCalculationRequirementsPolicy;
    }

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
        LinkedList<PingLocation> locations = new LinkedList<>(this.locations);

        if (location != null) {
            locations.add(new PingLocation(clock.instant(), location));
        }

        if (locations.size() < 2) {
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
