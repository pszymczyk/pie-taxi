package com.pszymczyk.pietaxi.rides.traffic.infrastructure;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

interface RideEventsEntityCrudRepository extends CrudRepository<JpaRideEventsEntity, Long> {

    List<JpaRideEventsEntity> findAllByProcessedTimeIsNull();
}
