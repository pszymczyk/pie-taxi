package com.pszymczyk.pietaxi.rides;

import java.time.Clock;

import org.springframework.stereotype.Component;

@Component
class RidesApplicationService {

    private final RideFactory rideFactory;
    private final RideRepository rideRepository;
    private final Clock clock;
    private final RideEvents rideEvents;

    public RidesApplicationService(RideFactory rideFactory, RideRepository rideRepository, Clock clock, RideEvents rideEvents) {
        this.rideFactory = rideFactory;
        this.rideRepository = rideRepository;
        this.clock = clock;
        this.rideEvents = rideEvents;
    }

    RideId startNewRide(StartNewRideCommand startNewRideCommand) {
        Ride ride = rideFactory.create(startNewRideCommand.getPassengerId(), startNewRideCommand.getDriverId());
        ride.start(startNewRideCommand.getLocation(), clock);
        rideRepository.save(ride);
        return ride.rideId;
    }

    void updateLocation(UpdateLocationCommand updateLocationCommand) {
        Ride ride = rideRepository.findById(updateLocationCommand.getRideId()).orElseThrow(RideNotFound::new);
        ride.ping(updateLocationCommand.getLocation(), clock);
    }

    void stop(StopRideCommand stopRideCommand) {
        Ride ride = rideRepository.findById(stopRideCommand.getRideId()).orElseThrow(RideNotFound::new);
        ride.stop(stopRideCommand.getLocation(), clock, rideEvents);
        rideRepository.delete(ride);
    }
}
