package com.pszymczyk.pietaxi.rides.traffic.infrastructure.statetransfered;

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
import com.pszymczyk.pietaxi.model.PassengerId;
import com.pszymczyk.pietaxi.rides.traffic.model.BillingClient;

@RestController
class StateTransferredBillingClient implements BillingClient {

    private final BlockedPassengersCrudRepository blockedPassengersCrudRepository;
    private final ObjectMapper objectMapper;

    public StateTransferredBillingClient(BlockedPassengersCrudRepository blockedPassengersCrudRepository, ObjectMapper objectMapper) {
        this.blockedPassengersCrudRepository = blockedPassengersCrudRepository;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/events/billing")
    void handleEvent(@RequestBody BillingEvent billingEvent) throws JsonProcessingException {
        if ("PassengerAccountBlocked".equalsIgnoreCase(billingEvent.type)) {
            Map<String, String> payload = objectMapper.readValue(billingEvent.payload, TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, String.class));
            BlockedPassengerEntity blockedPassengerEntity = new BlockedPassengerEntity();
            blockedPassengerEntity.setPassengerId(payload.get("passengerId"));
            blockedPassengersCrudRepository.save(blockedPassengerEntity);
        } else if ("PassengerAccountActivated".equalsIgnoreCase(billingEvent.type)) {
            Map<String, String> payload = objectMapper.readValue(billingEvent.payload, TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, String.class));
            blockedPassengersCrudRepository.deleteById(payload.get("passengerId"));
        }
    }

    @Override
    public Status checkPassengerAccount(PassengerId passengerId) {
        return blockedPassengersCrudRepository.findById(passengerId.getId()).isPresent() ? Status.BLOCKED : Status.OK;
    }
}

class BillingEvent {

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
