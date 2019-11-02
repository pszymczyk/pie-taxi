package com.pszymczyk.pietaxi.rides;

import org.springframework.stereotype.Component;

@Component
class AcceptAllPassengersBillingApi implements BillingApi {

    @Override
    public Status checkPassengerAccount(PassengerId passengerId) {
        return Status.OK;
    }
}
