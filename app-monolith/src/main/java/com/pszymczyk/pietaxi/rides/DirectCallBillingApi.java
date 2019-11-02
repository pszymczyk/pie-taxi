package com.pszymczyk.pietaxi.rides;

import org.springframework.stereotype.Component;

import com.pszymczyk.pietaxi.PassengerId;
import com.pszymczyk.pietaxi.billing.BillingFacade;

@Component
class DirectCallBillingApi implements BillingApi {

    private final BillingFacade billingFacade;

    DirectCallBillingApi(BillingFacade billingFacade) {
        this.billingFacade = billingFacade;
    }

    @Override
    public Status checkPassengerAccount(PassengerId passengerId) {
        return billingFacade.getBlockedPassengers(passengerId)
                            .stream()
                            .anyMatch(blocked -> blocked.equals(passengerId)) ? Status.BLOCKED : Status.OK;
    }
}
