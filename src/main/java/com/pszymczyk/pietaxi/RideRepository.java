package com.pszymczyk.pietaxi;

import java.util.Optional;

interface RideRepository {

    void save(Ride ride);

    Optional<Ride> findById(RideId rideId);
}
