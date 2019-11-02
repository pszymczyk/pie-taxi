package com.pszymczyk.pietaxi.rides;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
class SpringRideEvents implements RideEvents {

    private static final Logger log = LoggerFactory.getLogger(SpringRideEvents.class);

    private final ApplicationEventPublisher applicationEventPublisher;

    public SpringRideEvents(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(CorruptedRideFinished corruptedRideFinished) {
        applicationEventPublisher.publishEvent(corruptedRideFinished);
        log.info(corruptedRideFinished.toString());
    }

    @Override
    public void publish(RideFinished rideFinished) {
        applicationEventPublisher.publishEvent(rideFinished);
        log.info(rideFinished.toString());
    }
}
