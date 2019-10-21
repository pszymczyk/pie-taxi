package com.pszymczyk.pietaxi;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
class InMemoryRideRepository implements RideRepository {

    private final Map<RideId, Ride> storage = new HashMap<>();

    @Override
    public void save(Ride ride){
        storage.put(ride.rideId, ride);
    }

    @Override
    public Optional<Ride> findById(RideId rideId) {
        return Optional.ofNullable(storage.get(rideId));
    }

    @Override
    public void delete(Ride ride) {
        storage.remove(ride.rideId);
    }
}
