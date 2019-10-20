package com.pszymczyk.pietaxi;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.pszymczyk.pietaxi.matching.RideAccepted;

@Component
class RideAcceptedListener {

    private final RidesApplicationService ridesApplicationService;

    public RideAcceptedListener(RidesApplicationService ridesApplicationService) {
        this.ridesApplicationService = ridesApplicationService;
    }

    @EventListener
    public void onRideAccepted(RideAccepted rideAccepted) {
        ridesApplicationService.startNewRide(new StartNewRideCommand(rideAccepted.getPassengerId(), rideAccepted.getDriverId()));
    }

}
