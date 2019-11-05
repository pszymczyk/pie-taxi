package com.pszymczyk.pietaxi.rides.traffic.infrastructure.outbox;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
class RideEventsSender {

    private final RideEventsEntityCrudRepository rideEventsEntityCrudRepository;
    private final Clock clock;
    private final RestTemplate restTemplate;
    private final String billingUrl;

    public RideEventsSender(
            RideEventsEntityCrudRepository rideEventsEntityCrudRepository,
            Clock clock,
            RestTemplate restTemplate,
            @Value("${billing.url:http://localhost:8090}") String billingUrl) {
        this.rideEventsEntityCrudRepository = rideEventsEntityCrudRepository;
        this.clock = clock;
        this.restTemplate = restTemplate;
        this.billingUrl = billingUrl;
    }

    @Scheduled(fixedDelay = 1000)
    void send() {
        rideEventsEntityCrudRepository.findAllByProcessedTimeIsNull().forEach(entity -> {
            //TODO
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
