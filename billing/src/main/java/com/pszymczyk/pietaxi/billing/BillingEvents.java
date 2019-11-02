package com.pszymczyk.pietaxi.billing;

interface BillingEvents {

    void publish(PassengerAccountActivated passengerAccountActivated);

    void publish(PassengerAccountBlocked passengerAccountBlocked);

}
