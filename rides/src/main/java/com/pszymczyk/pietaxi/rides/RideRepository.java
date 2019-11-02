package com.pszymczyk.pietaxi.rides;

import java.util.Optional;

interface RideRepository {

    void save(Ride ride);

    Optional<Ride> findById(RideId rideId);

    void delete(Ride ride);
}
