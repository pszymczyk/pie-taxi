package com.pszymczyk.pietaxi.billing.model;

import java.time.Instant;
import java.util.UUID;

import com.pszymczyk.pietaxi.model.DomainEvent;
import com.pszymczyk.pietaxi.model.PassengerId;

public class Overpayment implements DomainEvent {

    private final UUID eventId;
    private final Instant occurrenceTime;
    private final PassengerId passengerId;
    private final Money money;

    Overpayment(PassengerId passengerId, Money money) {
        this.eventId = UUID.randomUUID();
        this.occurrenceTime = Instant.now();
        this.passengerId = passengerId;
        this.money = money;
    }

    PassengerId getPassengerId() {
        return passengerId;
    }

    Money getMoney() {
        return money;
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
