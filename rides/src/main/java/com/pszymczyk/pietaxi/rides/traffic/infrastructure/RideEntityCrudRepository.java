package com.pszymczyk.pietaxi.rides.traffic.infrastructure;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.pszymczyk.pietaxi.rides.traffic.infrastructure.JpaRideEntity;

interface RideEntityCrudRepository extends CrudRepository<JpaRideEntity, Long> {

    Optional<JpaRideEntity> findByRideId(UUID rideId);

    Long deleteByRideId(UUID rideId);
}
