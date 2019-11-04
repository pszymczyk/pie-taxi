package com.pszymczyk.pietaxi.billing;

import com.pszymczyk.pietaxi.model.PassengerId;

interface BillingEvents {

    void publish(PassengerAccountActivated passengerAccountActivated);

    void publish(PassengerAccountBlocked passengerAccountBlocked);

}

class PassengerAccountActivated {
    private final PassengerId passengerId;

    public PassengerAccountActivated(PassengerId passengerId) {
        this.passengerId = passengerId;
    }

    public PassengerId getPassengerId() {
        return passengerId;
    }
}

class PassengerAccountBlocked {

    private final PassengerId passengerId;

    public PassengerAccountBlocked(PassengerId passengerId) {
        this.passengerId = passengerId;
    }

    public PassengerId getPassengerId() {
        return passengerId;
    }
}


