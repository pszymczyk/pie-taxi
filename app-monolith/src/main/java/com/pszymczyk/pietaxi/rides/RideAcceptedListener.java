package com.pszymczyk.pietaxi.rides;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.pszymczyk.pietaxi.rides.match.RideAccepted;

@Component
class RideAcceptedListener {

    private final RidesApplicationService ridesApplicationService;

    public RideAcceptedListener(RidesApplicationService ridesApplicationService) {
        this.ridesApplicationService = ridesApplicationService;
    }

    @EventListener
    public void onRideAccepted(RideAccepted rideAccepted) {
        ridesApplicationService.startNewRide(new StartNewRideCommand(rideAccepted.getPassengerId(), rideAccepted.getDriverId(), rideAccepted.getLocation()));
    }

}