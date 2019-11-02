package com.pszymczyk.pietaxi.rides.match;

public interface RequestRideSagaEvents {

    void publish(AllDriversBusy allDriversBusy);

    void publish(RideAccepted rideAccepted);

    void publish(CabIsFull cabIsFull);
}
