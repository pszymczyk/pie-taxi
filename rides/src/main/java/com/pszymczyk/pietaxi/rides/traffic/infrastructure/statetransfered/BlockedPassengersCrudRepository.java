package com.pszymczyk.pietaxi.rides.traffic.infrastructure.statetransfered;

import org.springframework.data.repository.CrudRepository;

interface BlockedPassengersCrudRepository extends CrudRepository<BlockedPassengerEntity, String> {

}
