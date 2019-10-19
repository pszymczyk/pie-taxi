package com.pszymczyk.pietaxi;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IntegrationTests {

	@Autowired
	PieTaxiFacade pieTaxiFacade;

	@Test
	void should_start_and_stop_ride() {
		//given
		PieTaxiFacade.StartRideDTO startRideDTO = new PieTaxiFacade.StartRideDTO();
		startRideDTO.passengerId = UUID.randomUUID().toString();
		startRideDTO.driverId = UUID.randomUUID().toString();
		startRideDTO.pickLongitude = 72.59978;
		startRideDTO.pickLatitude = 141.56260;

		//when start ride
		String rideId = pieTaxiFacade.startRide(startRideDTO);

		PieTaxiFacade.StopRideDTO stopRideDTO = new PieTaxiFacade.StopRideDTO();
		stopRideDTO.rideId = rideId;
		stopRideDTO.stopLongitude = 82.59978;
		stopRideDTO.stopLatitude = 151.56260;

		//then stop ride
		pieTaxiFacade.stopRide(stopRideDTO);
	}

}
