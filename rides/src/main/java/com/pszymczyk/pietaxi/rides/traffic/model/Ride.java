package com.pszymczyk.pietaxi.rides.traffic.model;

import java.time.Clock;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.pszymczyk.pietaxi.model.PassengerId;
import com.pszymczyk.pietaxi.model.RideId;

public class Ride {

    public static class PingLocation {
        private final Instant time;
        private final Location location;

        public PingLocation(Instant time, Location location) {
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
    final DistanceCalculationRequirementsPolicy distanceCalculationRequirementsPolicy;
    final List<PingLocation> locations;

    public Ride(RideId rideId, PassengerId passengerId, DriverId driverId, DistanceCalculationRequirementsPolicy distanceCalculationRequirementsPolicy, List<PingLocation> locations) {
        this.rideId = rideId;
        this.passengerId = passengerId;
        this.driverId = driverId;
        this.distanceCalculationRequirementsPolicy = distanceCalculationRequirementsPolicy;
        this.locations = locations;
    }

    public void start(Location location, Clock clock) {
        if (location == null) {
            throw new NullPointerException("location cannot be null!");
        }

        if (!locations.isEmpty()) {
            throw new RideAlreadyStarted();
        }

        locations.add(new PingLocation(clock.instant(), location));
    }


    public void ping(Location location, Clock clock) {
        if (location == null) {
            throw new NullPointerException("location cannot be null!");
        }

        locations.add(new PingLocation(clock.instant(), location));
    }

    public void stop(Location location, Clock clock, RideEvents rideEvents){
        LinkedList<PingLocation> locations = new LinkedList<>(this.locations);

        if (location != null) {
            locations.add(new PingLocation(clock.instant(), location));
        }

        if (!distanceCalculationRequirementsPolicy.enoughDataToCalculateDistance(locations)) {
            rideEvents.publish(new CorruptedRideFinished(clock.instant(), rideId));
            return;
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

        rideEvents.publish(new RideFinished(clock.instant(), rideId, passengerId, driverId, distance, startTime, endTime));
    }

    public RideId getRideId() {
        return rideId;
    }

    public DriverId getDriverId() {
        return driverId;
    }

    public PassengerId getPassengerId() {
        return passengerId;
    }

    public DistanceCalculationRequirementsPolicy getDistanceCalculationRequirementsPolicy() {
        return distanceCalculationRequirementsPolicy;
    }

    public List<PingLocation> getLocations() {
        return Collections.unmodifiableList(locations);
    }
}
