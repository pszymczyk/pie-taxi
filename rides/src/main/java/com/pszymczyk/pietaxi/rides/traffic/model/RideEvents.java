package com.pszymczyk.pietaxi.rides.traffic.model;

public interface RideEvents {

    void publish(CorruptedRideFinished corruptedRideFinished);

    void publish(RideFinished rideFinished);
}
