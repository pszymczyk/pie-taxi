package com.pszymczyk.pietaxi.rides.traffic.model;

import java.util.Optional;

import com.pszymczyk.pietaxi.model.RideId;

public interface RideRepository {

    void save(Ride ride);

    Optional<Ride> findById(RideId rideId);

    void delete(Ride ride);
}
