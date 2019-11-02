package com.pszymczyk.pietaxi.rides.traffic.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pszymczyk.pietaxi.model.RideId;
import com.pszymczyk.pietaxi.rides.traffic.TestFixtures;
import com.pszymczyk.pietaxi.rides.traffic.model.Ride;
import com.pszymczyk.pietaxi.rides.traffic.model.RideRepository;

import static com.pszymczyk.pietaxi.rides.traffic.TestFixtures.randomDriver;
import static com.pszymczyk.pietaxi.rides.traffic.TestFixtures.randomLocation;
import static com.pszymczyk.pietaxi.rides.traffic.TestFixtures.randomPassenger;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RidesApplicationServiceTest {

    @Autowired
    RidesApplicationService ridesApplicationService;

    @Autowired
    RideRepository rideRepository;

    @Test
    void Should_start_new_ride() {
        //given
        StartNewRideCommand startNewRideCommand = new StartNewRideCommand(randomPassenger(), randomDriver(), randomLocation());

        //when
        RideId rideId = ridesApplicationService.startNewRide(startNewRideCommand);

        //then
        assertThat(rideRepository.findById(rideId)).isPresent();
    }

    @Test
    void Should_update_location() {
        //given
        StartNewRideCommand startNewRideCommand = new StartNewRideCommand(randomPassenger(), randomDriver(), randomLocation());

        //when
        RideId rideId = ridesApplicationService.startNewRide(startNewRideCommand);

        //and
        ridesApplicationService.updateLocation(new UpdateLocationCommand(rideId, TestFixtures.randomLocation()));

        //then
        Ride ride = rideRepository.findById(rideId).get();
        Assertions.assertThat(ride.getLocations()).hasSize(2);
    }

    @Test
    void Should_stop_ride() {
        //given
        StartNewRideCommand startNewRideCommand = new StartNewRideCommand(randomPassenger(), randomDriver(), randomLocation());

        //when
        RideId rideId = ridesApplicationService.startNewRide(startNewRideCommand);

        //and
        ridesApplicationService.updateLocation(new UpdateLocationCommand(rideId, TestFixtures.randomLocation()));

        //and
        ridesApplicationService.stop(new StopRideCommand(rideId, null));

        //then
        assertThat(rideRepository.findById(rideId)).isEmpty();
    }
}
