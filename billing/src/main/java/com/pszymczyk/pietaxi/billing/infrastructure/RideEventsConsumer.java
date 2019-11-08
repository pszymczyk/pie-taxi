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

import lombok.Data;

@RestController
class RideEventsConsumer {

    private final BillingApplicationService billingService;
    private final Serde serde;

    public RideEventsConsumer(BillingApplicationService billingService, Serde serde) {
        this.billingService = billingService;
        this.serde = serde;
    }

    @PostMapping("/events/rides")
    void handleEvent(@RequestBody RidesEvent ridesEvent) {
        if ("RideFinished".equalsIgnoreCase(ridesEvent.type)) {
            Map<String, String> payload = serde.deserialize(ridesEvent.payload);
            billingService.settleRide(new SettleRideCommand(
                    new PassengerId(payload.get("passengerId")),
                    new BigDecimal(payload.get("distance"))
            ));
        }
    }
}

@Data
class RidesEvent {

    UUID eventId;
    Instant occurrenceTime;
    String type;
    String payload;
}
