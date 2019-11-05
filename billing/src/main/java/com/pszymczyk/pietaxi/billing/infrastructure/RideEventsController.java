package com.pszymczyk.pietaxi.billing.infrastructure;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pszymczyk.pietax.infrastructure.Serde;
import com.pszymczyk.pietaxi.billing.application.BillingApplicationService;
import com.pszymczyk.pietaxi.billing.application.SettleRideCommand;
import com.pszymczyk.pietaxi.model.PassengerId;

@RestController
class RideEventsController {

    private final BillingApplicationService billingService;
    private final Serde serde;

    public RideEventsController(BillingApplicationService billingService, Serde serde) {
        this.billingService = billingService;
        this.serde = serde;
    }

    @PostMapping("/events/rides")
    void handleEvent(@RequestBody RidesEvent ridesEvent) {
        if ("RideFinished".equalsIgnoreCase(ridesEvent.type)) {
            Map<String, String> payload = serde.deserialize(ridesEvent.payload);
            //TODOs
            billingService.settleRide(new SettleRideCommand(
                    new PassengerId(payload.get("passengerId")),
                    new BigDecimal(payload.get("distance"))
            ));
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
