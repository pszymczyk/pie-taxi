package com.pszymczyk.pietaxi.rides;

interface BillingApi {

    enum Status {
        OK,
        BLOCKED
    }

    Status checkPassengerAccount(PassengerId passengerId);

}
