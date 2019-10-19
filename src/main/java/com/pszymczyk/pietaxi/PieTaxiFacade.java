package com.pszymczyk.pietaxi;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
class PieTaxiFacade {

    static class StartRideDTO {
        String passengerId;
        String driverId;
        double pickLongitude;
        double pickLatitude;
    }

    public String startRide(StartRideDTO startRideDTO) {
        throw new UnsupportedOperationException();
    }

    static class StopRideDTO {
        String rideId;
        double stopLongitude;
        double stopLatitude;
    }

    public void stopRide(StopRideDTO stopRideDTO) {
        throw new UnsupportedOperationException();
    }
}
