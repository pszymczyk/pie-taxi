package com.pszymczyk.pietaxi.rides.traffic;

import org.springframework.stereotype.Component;

import com.pszymczyk.pietaxi.model.PassengerId;
import com.pszymczyk.pietaxi.rides.traffic.model.BillingClient;

@Component
class AcceptAllPassengersBillingClient implements BillingClient {

    @Override
    public Status checkPassengerAccount(PassengerId passengerId) {
        return Status.OK;
    }
}
