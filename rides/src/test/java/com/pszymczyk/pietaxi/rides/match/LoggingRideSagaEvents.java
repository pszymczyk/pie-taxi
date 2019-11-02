package com.pszymczyk.pietaxi.rides.match;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("memory")
@Primary
class LoggingRideSagaEvents implements RequestRideSagaEvents {

    private static final Logger log = LoggerFactory.getLogger(LoggingRideSagaEvents.class);

    @Override
    public void publish(AllDriversBusy allDriversBusy) {
        log.info(allDriversBusy.toString());
    }

    @Override
    public void publish(RideAccepted rideAccepted) {
        log.info(rideAccepted.toString());
    }

    @Override
    public void publish(CabIsFull cabIsFull) {
        log.info(cabIsFull.toString());
    }
}
