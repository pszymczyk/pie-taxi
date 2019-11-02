package com.pszymczyk.pietaxi.model;

import java.time.Instant;
import java.util.UUID;

public interface DomainEvent {

    UUID getEventId();

    UUID getEntityId();

    Instant getOccurrenceTime();
}
