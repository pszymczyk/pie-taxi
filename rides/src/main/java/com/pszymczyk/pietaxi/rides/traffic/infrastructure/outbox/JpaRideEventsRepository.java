package com.pszymczyk.pietaxi.rides.traffic.infrastructure.outbox;

import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

import com.pszymczyk.pietax.infrastructure.Serde;
import com.pszymczyk.pietaxi.rides.traffic.model.CorruptedRideFinished;
import com.pszymczyk.pietaxi.rides.traffic.model.RideEvents;
import com.pszymczyk.pietaxi.rides.traffic.model.RideFinished;

@Component
class JpaRideEventsRepository implements RideEvents {

    private final RideEventsEntityCrudRepository rideEventsEntityCrudRepository;
    private final Serde serde;

    public JpaRideEventsRepository(RideEventsEntityCrudRepository rideEventsEntityCrudRepository, Serde serde) {
        this.rideEventsEntityCrudRepository = rideEventsEntityCrudRepository;
        this.serde = serde;
    }

    @Override
    public void publish(CorruptedRideFinished corruptedRideFinished) {
        //TODO
    }

    @Override
    public void publish(RideFinished rideFinished) {
        //TODO
    }
}

@Entity
class JpaRideEventsEntity {

    @Id
    @GeneratedValue
    private long id;

    private UUID eventId;

    private Instant occurrenceTime;

    private String type;

    private Instant processedTime;

    @Column( length = 100000 )
    private String payload;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public Instant getOccurrenceTime() {
        return occurrenceTime;
    }

    public void setOccurrenceTime(Instant occurrenceTime) {
        this.occurrenceTime = occurrenceTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getProcessedTime() {
        return processedTime;
    }

    public void setProcessedTime(Instant processedTime) {
        this.processedTime = processedTime;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}

