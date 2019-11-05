package com.pszymczyk.pietaxi.rides.traffic.model;

import java.time.Instant;
import java.util.UUID;

import com.pszymczyk.pietaxi.model.DomainEvent;
import com.pszymczyk.pietaxi.model.PassengerId;
import com.pszymczyk.pietaxi.model.RideId;

public class RideFinished implements DomainEvent {

    private final UUID eventId;
    private final Instant occurrenceTime;

    private final RideId rideId;
    private final PassengerId passengerId;
    private final DriverId driverId;
    private final Distance distance;
    private final Instant startTime;
    private final Instant endTime;

    public RideFinished(Instant occurrenceTime, RideId rideId, PassengerId passengerId, DriverId driverId, Distance distance, Instant startTime, Instant endTime) {
        this(UUID.randomUUID(),
                occurrenceTime,
                rideId,
                passengerId,
                driverId,
                distance,
                startTime,
                endTime);
    }

    RideFinished(UUID eventId,
            Instant occurrenceTime,
            RideId rideId,
            PassengerId passengerId,
            DriverId driverId,
            Distance distance,
            Instant startTime,
            Instant endTime) {
        this.eventId = eventId;
        this.occurrenceTime = occurrenceTime;
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

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public String getEntityId() {
        return rideId.getId().toString();
    }

    @Override
    public Instant getOccurrenceTime() {
        return occurrenceTime;
    }
}
