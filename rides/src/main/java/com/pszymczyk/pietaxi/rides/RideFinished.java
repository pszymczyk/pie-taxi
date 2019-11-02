package com.pszymczyk.pietaxi.rides;

import java.time.Instant;

import com.pszymczyk.pietaxi.PassengerId;

class RideFinished {

    private final RideId rideId;
    private final PassengerId passengerId;
    private final DriverId driverId;
    private final Distance distance;
    private final Instant startTime;
    private final Instant endTime;

    RideFinished(RideId rideId, PassengerId passengerId, DriverId driverId, Distance distance, Instant startTime, Instant endTime) {
        this.rideId = rideId;
        this.passengerId = passengerId;
        this.driverId = driverId;
        this.distance = distance;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public RideId getRideId() {
        return rideId;
    }

    public PassengerId getPassengerId() {
        return passengerId;
    }

    public DriverId getDriverId() {
        return driverId;
    }

    public Distance getDistance() {
        return distance;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "RideFinished{" +
                "rideId=" + rideId +
                ", passengerId=" + passengerId +
                ", driverId=" + driverId +
                ", distance=" + distance +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
