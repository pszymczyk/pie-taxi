package com.pszymczyk.pietaxi.billing.model;

import java.time.Instant;
import java.util.UUID;

import com.pszymczyk.pietaxi.model.DomainEvent;
import com.pszymczyk.pietaxi.model.PassengerId;

public class PassengerAccountActivated implements DomainEvent {

    private final UUID eventId;
    private final Instant occurrenceTime;
    private final PassengerId passengerId;

    PassengerAccountActivated(PassengerId passengerId) {
        this.eventId = UUID.randomUUID();
        this.occurrenceTime = Instant.now();
        this.passengerId = passengerId;
    }

    public PassengerId getPassengerId() {
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
