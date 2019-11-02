package com.pszymczyk.pietaxi.rides.traffic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.pszymczyk.pietaxi.rides.traffic.model.CorruptedRideFinished;
import com.pszymczyk.pietaxi.rides.traffic.model.RideEvents;
import com.pszymczyk.pietaxi.rides.traffic.model.RideFinished;

@Component
@Profile("memory")
@Primary
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
