package com.pszymczyk.pietaxi.rides.traffic.application;

import java.time.Clock;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pszymczyk.pietaxi.model.RideId;
import com.pszymczyk.pietaxi.rides.traffic.model.Ride;
import com.pszymczyk.pietaxi.rides.traffic.model.RideEvents;
import com.pszymczyk.pietaxi.rides.traffic.model.RideFactory;
import com.pszymczyk.pietaxi.rides.traffic.model.RideRepository;

@Component
@Transactional
public class RidesApplicationService {

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

    public RideId startNewRide(StartNewRideCommand startNewRideCommand) {
        Ride ride = rideFactory.create(startNewRideCommand.getPassengerId(), startNewRideCommand.getDriverId());
        ride.start(startNewRideCommand.getLocation(), clock);
        rideRepository.save(ride);
        return ride.getRideId();
    }

    public void updateLocation(UpdateLocationCommand updateLocationCommand) {
        Ride ride = rideRepository.findById(updateLocationCommand.getRideId()).orElseThrow(RideNotFound::new);
        ride.ping(updateLocationCommand.getLocation(), clock);
        rideRepository.save(ride);
    }

    public void stop(StopRideCommand stopRideCommand) {
        Ride ride = rideRepository.findById(stopRideCommand.getRideId()).orElseThrow(RideNotFound::new);
        ride.stop(stopRideCommand.getLocation(), clock, rideEvents);
        rideRepository.delete(ride);
    }
}
