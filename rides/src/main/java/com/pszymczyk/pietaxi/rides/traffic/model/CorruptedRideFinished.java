package com.pszymczyk.pietaxi.rides.traffic.model;

import java.time.Instant;
import java.util.UUID;

import com.pszymczyk.pietaxi.model.DomainEvent;
import com.pszymczyk.pietaxi.model.RideId;

public class CorruptedRideFinished implements DomainEvent {

    private final UUID eventId;
    private final Instant occurrenceTime;
    private final RideId rideId;

    public CorruptedRideFinished(Instant occurrenceTime, RideId rideId) {
        this(UUID.randomUUID(), occurrenceTime, rideId);
    }

    public CorruptedRideFinished(UUID eventId, Instant occurrenceTime, RideId rideId) {
        this.eventId = eventId;
        this.occurrenceTime = occurrenceTime;
        this.rideId = rideId;
    }

    @Override
    public String toString() {
        return "CorruptedRideFinished{" +
                "rideId=" + rideId +
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
