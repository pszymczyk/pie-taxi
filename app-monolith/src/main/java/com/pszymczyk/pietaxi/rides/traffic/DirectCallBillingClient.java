package com.pszymczyk.pietaxi.rides.traffic;

import org.springframework.stereotype.Component;

import com.pszymczyk.pietaxi.billing.BillingFacade;
import com.pszymczyk.pietaxi.model.PassengerId;
import com.pszymczyk.pietaxi.rides.traffic.model.BillingClient;

@Component
class DirectCallBillingClient implements BillingClient {

    private final BillingFacade billingFacade;

    DirectCallBillingClient(BillingFacade billingFacade) {
        this.billingFacade = billingFacade;
    }

    @Override
    public Status checkPassengerAccount(PassengerId passengerId) {
        return billingFacade.getBlockedPassengers()
                            .stream()
                            .anyMatch(blocked -> blocked.equals(passengerId)) ? Status.BLOCKED : Status.OK;
    }
}
