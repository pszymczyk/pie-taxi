package com.pszymczyk.pietaxi.billing.model;

import java.time.Instant;
import java.util.UUID;

import com.pszymczyk.pietaxi.model.DomainEvent;
import com.pszymczyk.pietaxi.model.PassengerId;

public class PassengerAccountBlocked implements DomainEvent {

    private final PassengerId passengerId;
    private final UUID eventId;
    private final Instant occurrenceTime;

    PassengerAccountBlocked(PassengerId passengerId) {
        this.passengerId = passengerId;
        this.eventId = UUID.randomUUID();
        this.occurrenceTime = Instant.now();
    }

    PassengerId getPassengerId() {
        return passengerId;
    }

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public String getEntityId() {
        return passengerId.getId();
    }

    @Override
    public Instant getOccurrenceTime() {
        return occurrenceTime;
    }
}
