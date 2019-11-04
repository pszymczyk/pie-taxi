package com.pszymczyk.pietaxi.rides.traffic.infrastructure.statetransfered;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import com.pszymczyk.pietaxi.model.PassengerId;
import com.pszymczyk.pietaxi.rides.traffic.model.BillingClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StateTransferTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    StateTransferredBillingClient stateTransferredBillingClient;

    @Test
    void Should_store_info_about_blocked_passengers() {
        //given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //when
        testRestTemplate.exchange("/events/billing", HttpMethod.POST, new HttpEntity<>("{ \n"
                + "  \"eventId\": \"6e4294fa-ff43-11e9-8f0b-362b9e155667\",\n"
                + "  \"occurrenceTime\": \"2007-12-03T10:15:30.00Z\",\n"
                + "  \"type\": \"PassengerAccountBlocked\",\n"
                + "  \"payload\": \"{\\\"passengerId\\\": \\\"kazik\\\"}\"\n"
                + "}",headers), Void.class);

        //then
        assertThat(stateTransferredBillingClient.checkPassengerAccount(new PassengerId("kazik"))).isEqualTo(BillingClient.Status.BLOCKED);
    }

    @Test
    void Should_remove_passenger_from_blacklist() {
        //given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        testRestTemplate.exchange("/events/billing", HttpMethod.POST, new HttpEntity<>("{ \n"
                + "  \"eventId\": \"6e4294fa-ff43-11e9-8f0b-362b9e155667\",\n"
                + "  \"occurrenceTime\": \"2007-12-03T10:15:30.00Z\",\n"
                + "  \"type\": \"PassengerAccountBlocked\",\n"
                + "  \"payload\": \"{\\\"passengerId\\\": \\\"kazik\\\"}\"\n"
                + "}",headers), Void.class);

        assertThat(stateTransferredBillingClient.checkPassengerAccount(new PassengerId("kazik"))).isEqualTo(BillingClient.Status.BLOCKED);

        //when
        testRestTemplate.exchange("/events/billing", HttpMethod.POST, new HttpEntity<>("{ \n"
                + "  \"eventId\": \"6e4294fa-ff43-11e9-8f0b-362b9e155667\",\n"
                + "  \"occurrenceTime\": \"2007-12-03T10:15:30.00Z\",\n"
                + "  \"type\": \"PassengerAccountActivated\",\n"
                + "  \"payload\": \"{\\\"passengerId\\\": \\\"kazik\\\"}\"\n"
                + "}",headers), Void.class);

        //then
        assertThat(stateTransferredBillingClient.checkPassengerAccount(new PassengerId("kazik"))).isEqualTo(BillingClient.Status.OK);
    }
}
