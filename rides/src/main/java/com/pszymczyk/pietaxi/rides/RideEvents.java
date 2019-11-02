package com.pszymczyk.pietaxi.rides;

interface RideEvents {

    void publish(CorruptedRideFinished corruptedRideFinished);

    void publish(RideFinished rideFinished);
}
