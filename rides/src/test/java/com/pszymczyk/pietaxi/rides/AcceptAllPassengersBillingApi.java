package com.pszymczyk.pietaxi.rides;

import org.springframework.stereotype.Component;

import com.pszymczyk.pietaxi.PassengerId;

@Component
class AcceptAllPassengersBillingApi implements BillingApi {

    @Override
    public Status checkPassengerAccount(PassengerId passengerId) {
        return Status.OK;
    }
}
