package com.pszymczyk.pietaxi.rides;

import com.pszymczyk.pietaxi.PassengerId;

interface BillingApi {

    enum Status {
        OK,
        BLOCKED
    }

    Status checkPassengerAccount(PassengerId passengerId);

}
