package com.pszymczyk.pietaxi;

interface RideEvents {

    void publish(CorruptedRideFinished corruptedRideFinished);

    void publish(RideFinished rideFinished);
}
