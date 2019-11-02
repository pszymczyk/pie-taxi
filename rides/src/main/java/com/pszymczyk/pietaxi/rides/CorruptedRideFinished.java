package com.pszymczyk.pietaxi.rides;

class CorruptedRideFinished {

    private final RideId rideId;

    public CorruptedRideFinished(RideId rideId) {
        this.rideId = rideId;
    }

    @Override
    public String toString() {
        return "CorruptedRideFinished{" +
                "rideId=" + rideId +
                '}';
    }
}