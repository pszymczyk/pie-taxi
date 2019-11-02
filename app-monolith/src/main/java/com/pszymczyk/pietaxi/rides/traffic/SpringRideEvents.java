package com.pszymczyk.pietaxi.rides.traffic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

import com.pszymczyk.pietaxi.rides.traffic.model.CorruptedRideFinished;
import com.pszymczyk.pietaxi.rides.traffic.model.RideEvents;
import com.pszymczyk.pietaxi.rides.traffic.model.RideFinished;

// @Component
//TODO
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
