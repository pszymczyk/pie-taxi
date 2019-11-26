package com.pszymczyk.pietaxi;

import java.time.Clock;
import java.util.UUID;

class RideService {

    private final RideDAO rideDAO;
    private final Clock clock;

    public RideService(RideDAO rideDAO, Clock clock) {
        this.rideDAO = rideDAO;
        this.clock = clock;
    }

    void stopRide(UUID rideId, Location location) {
        Ride ride = rideDAO.findById(rideId);
        if (ride == null) {
            throw new IllegalArgumentException("No ride with given id!");
        }

        if (!MobilePhoneUtil.enoughDataToCalculateDistance(ride.getUser().getMobilePhone(), ride.getLocations().size()+1)) {
            throw new CorruptedRideFinished(rideId);
        }

        ride.stop(location);
        ride.calculateCost(clock.instant());
    }

}
