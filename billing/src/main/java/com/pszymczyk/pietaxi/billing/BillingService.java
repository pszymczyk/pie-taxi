package com.pszymczyk.pietaxi.billing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pszymczyk.pietaxi.model.RideId;

@Component
class BillingService {

    private static final Logger log = LoggerFactory.getLogger(BillingService.class);

    void billRide(RideId rideId) {
        //TODO
        log.info("Start billing process for ride: {}", rideId.getId());
    }

}
