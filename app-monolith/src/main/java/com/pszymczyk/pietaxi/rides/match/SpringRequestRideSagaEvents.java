package com.pszymczyk.pietaxi.rides.match;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
class SpringRequestRideSagaEvents implements RequestRideSagaEvents {

    private static final Logger log = LoggerFactory.getLogger(SpringRequestRideSagaEvents.class);

    private final ApplicationEventPublisher applicationEventPublisher;

    public SpringRequestRideSagaEvents(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(AllDriversBusy allDriversBusy) {
        applicationEventPublisher.publishEvent(allDriversBusy);
        log.info(allDriversBusy.toString());
    }

    @Override
    public void publish(RideAccepted rideAccepted) {
        applicationEventPublisher.publishEvent(rideAccepted);
        log.info(rideAccepted.toString());
    }

    @Override
    public void publish(CabIsFull cabIsFull) {
        applicationEventPublisher.publishEvent(cabIsFull);
        log.info(cabIsFull.toString());
    }
}
