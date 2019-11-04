package com.pszymczyk.pietaxi.rides.traffic.model;

import com.pszymczyk.pietaxi.model.PassengerId;

public interface BillingClient {

    public enum Status {
        OK,
        BLOCKED
    }

    Status checkPassengerAccount(PassengerId passengerId);

}
