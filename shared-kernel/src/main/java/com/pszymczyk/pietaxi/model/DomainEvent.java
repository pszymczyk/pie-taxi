package com.pszymczyk.pietaxi.model;

import java.time.Instant;
import java.util.UUID;

public interface DomainEvent {

    UUID getEventId();

    String getEntityId();

    Instant getOccurrenceTime();
}
