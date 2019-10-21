package com.pszymczyk.pietaxi.match;

public interface RequestRideSagaEvents {

    void publish(AllDriversBusy allDriversBusy);

    void publish(RideAccepted rideAccepted);

    void publish(CabIsFull cabIsFull);
}
