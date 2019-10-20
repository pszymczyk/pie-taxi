package com.pszymczyk.pietaxi.matching;

interface RequestRideSagaEvents {

    void publish(AllDriversBusy allDriversBusy);

    void publish(RideAccepted rideAccepted);

    void publish(CabIsFull cabIsFull);
}
