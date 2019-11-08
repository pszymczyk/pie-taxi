package com.pszymczyk.pietaxi.billing.infrastructure;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.pszymczyk.pietax.infrastructure.Serde;
import com.pszymczyk.pietaxi.billing.model.BillingEvents;
import com.pszymczyk.pietaxi.billing.model.Overpayment;
import com.pszymczyk.pietaxi.billing.model.PassengerAccountActivated;
import com.pszymczyk.pietaxi.billing.model.PassengerAccountBlocked;
import com.pszymczyk.pietaxi.model.DomainEvent;

import lombok.Data;

@Component
class RestBillingEventsSender implements BillingEvents {

    private Serde serde;
    private final RestTemplate restTemplate;
    private String ridesUrl;

    public RestBillingEventsSender(
            Serde serde,
            RestTemplate restTemplate,
            @Value("${rides.url:http://localhost:8080}") String ridesUrl) {
        this.serde = serde;
        this.restTemplate = restTemplate;
        this.ridesUrl = ridesUrl;
    }

    @Override
    public void publish(PassengerAccountActivated passengerAccountActivated) {
        send(passengerAccountActivated, "PassengerAccountActivated");
    }

    @Override
    public void publish(PassengerAccountBlocked passengerAccountBlocked) {
        send(passengerAccountBlocked, "PassengerAccountBlocked");
    }

    @Override
    public void publish(Overpayment overpayment) {
        send(overpayment, "Overpayment");
    }

    private void send(DomainEvent domainEvent, String type) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        BillingEvent billingEvent = new BillingEvent();
        billingEvent.eventId = domainEvent.getEventId();
        billingEvent.occurrenceTime = domainEvent.getOccurrenceTime();
        billingEvent.type = type;
        billingEvent.payload = serde.serialize(domainEvent);
        HttpEntity<BillingEvent> httpEntity = new HttpEntity<>(billingEvent, httpHeaders);
        restTemplate.exchange(ridesUrl + "/events/billing", HttpMethod.POST, httpEntity, Void.class);
    }
}

@Data
class BillingEvent {

    UUID eventId;
    Instant occurrenceTime;
    String type;
    String payload;
}
