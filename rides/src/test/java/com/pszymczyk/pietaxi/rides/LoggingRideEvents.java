package com.pszymczyk.pietaxi.rides;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class LoggingRideEvents implements RideEvents {

    private static final Logger log = LoggerFactory.getLogger(LoggingRideEvents.class);

    @Override
    public void publish(CorruptedRideFinished corruptedRideFinished) {
        log.info(corruptedRideFinished.toString());
    }

    @Override
    public void publish(RideFinished rideFinished) {
        log.info(rideFinished.toString());
    }
}
