package com.pszymczyk.pietaxi.rides.traffic.infrastructure.entities;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

interface RideEntityCrudRepository extends CrudRepository<JpaRideEntity, Long> {

    Optional<JpaRideEntity> findByRideId(UUID rideId);

    Long deleteByRideId(UUID rideId);
}
