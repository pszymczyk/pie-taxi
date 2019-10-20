package com.pszymczyk.pietaxi;

interface BillingApi {

    enum Status {
        OK,
        BLOCKED
    }

    Status checkPassengerAccount(PassengerId passengerId);

}
