package com.pszymczyk.pietaxi;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
class SpringRideEvents implements RideEvents {

    private final ApplicationEventPublisher applicationEventPublisher;

    public SpringRideEvents(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(CorruptedRideFinished corruptedRideFinished) {
        applicationEventPublisher.publishEvent(corruptedRideFinished);
    }

    @Override
    public void publish(RideFinished rideFinished) {
        applicationEventPublisher.publishEvent(rideFinished);
    }
}
