package com.pszymczyk.pietaxi.billing;

import java.lang.reflect.ParameterizedType;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.pszymczyk.pietaxi.model.RideId;

@RestController
class RideEventsController {

    private final BillingService billingService;
    private final ObjectMapper objectMapper;

    public RideEventsController(BillingService billingService, ObjectMapper objectMapper) {
        this.billingService = billingService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/events/rides")
    void handleEvent(@RequestBody RidesEvent ridesEvent) throws JsonProcessingException {
        if ("RideFinished".equalsIgnoreCase(ridesEvent.type)) {
            Map<String, String> payload = objectMapper.readValue(ridesEvent.payload, TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, String.class));
            billingService.billRide(new RideId(UUID.fromString(payload.get("entityId"))));
        }
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
