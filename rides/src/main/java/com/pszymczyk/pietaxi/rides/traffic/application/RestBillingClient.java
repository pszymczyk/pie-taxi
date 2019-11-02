package com.pszymczyk.pietaxi.rides.traffic.application;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.pszymczyk.pietaxi.model.PassengerId;
import com.pszymczyk.pietaxi.rides.traffic.model.BillingClient;

@Component
class RestBillingClient implements BillingClient {

    private final RestTemplate restTemplate;
    private final String billingUrl;

    RestBillingClient(RestTemplate restTemplate, @Value("${billing.url:http://localhost:8090}") String billingUrl) {
        this.restTemplate = restTemplate;
        this.billingUrl = billingUrl;
    }

    @Override
    public Status checkPassengerAccount(PassengerId passengerId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<AllDebtorsResponse> response = restTemplate.exchange(billingUrl + "/debtors", HttpMethod.GET, new HttpEntity<>(httpHeaders), AllDebtorsResponse.class);
        return response.getBody().debtors.contains(passengerId) ? Status.BLOCKED : Status.OK;
    }
}

class AllDebtorsResponse {
    Set<PassengerId> debtors;

    Set<PassengerId> getDebtors() {
        return debtors;
    }

    void setDebtors(Set<PassengerId> debtors) {
        this.debtors = debtors;
    }
}
