package com.pszymczyk.pietaxi.rides.traffic.infrastructure.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.pszymczyk.pietaxi.rides.match.RideAccepted;
import com.pszymczyk.pietaxi.rides.traffic.application.RidesApplicationService;
import com.pszymczyk.pietaxi.rides.traffic.application.StartNewRideCommand;

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
