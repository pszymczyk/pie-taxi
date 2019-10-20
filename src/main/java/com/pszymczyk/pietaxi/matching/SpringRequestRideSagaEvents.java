package com.pszymczyk.pietaxi.matching;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
class SpringRequestRideSagaEvents implements RequestRideSagaEvents {

    private final ApplicationEventPublisher applicationEventPublisher;

    public SpringRequestRideSagaEvents(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(AllDriversBusy allDriversBusy) {
        applicationEventPublisher.publishEvent(allDriversBusy);
    }

    @Override
    public void publish(RideAccepted rideAccepted) {
        applicationEventPublisher.publishEvent(rideAccepted);
    }

    @Override
    public void publish(CabIsFull cabIsFull) {
        applicationEventPublisher.publishEvent(cabIsFull);
    }
}
