package com.pszymczyk.pietaxi.billing.model;

public interface BillingEvents {

    void publish(PassengerAccountActivated passengerAccountActivated);

    void publish(PassengerAccountBlocked passengerAccountBlocked);

    void publish(Overpayment overpayment);

}


