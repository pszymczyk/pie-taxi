package com.pszymczyk.pietaxi.rides.traffic.infrastructure.outbox;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
class RideEventsSender {

    private final RideEventsEntityCrudRepository rideEventsEntityCrudRepository;
    private final RideEventsQueue rideEventsQueue;
    private final Clock clock;

    public RideEventsSender(RideEventsEntityCrudRepository rideEventsEntityCrudRepository, RideEventsQueue rideEventsQueue, Clock clock) {
        this.rideEventsEntityCrudRepository = rideEventsEntityCrudRepository;
        this.rideEventsQueue = rideEventsQueue;
        this.clock = clock;
    }

    //TODO
    // @Scheduled(fixedDelay = 1000)
    void send() {
        rideEventsEntityCrudRepository.findAllByProcessedTimeIsNull().forEach(entity -> {
            try {
                RidesEvent ridesEvent = new RidesEvent();
                ridesEvent.eventId = entity.getEventId();
                ridesEvent.occurrenceTime = entity.getOccurrenceTime();
                ridesEvent.type = entity.getType();
                ridesEvent.payload = entity.getPayload();
                rideEventsQueue.add(ridesEvent);
                entity.setProcessedTime(clock.instant());
                rideEventsEntityCrudRepository.save(entity);
            } catch (Exception e) {
                //ignore, send again
            }
        });
    }
}

class RidesEvent {

    UUID eventId;
    Instant occurrenceTime;
    String type;
    String payload;

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

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
