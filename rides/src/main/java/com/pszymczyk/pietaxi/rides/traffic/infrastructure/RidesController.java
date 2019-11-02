package com.pszymczyk.pietaxi.rides.traffic.infrastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pszymczyk.pietaxi.model.PassengerId;
import com.pszymczyk.pietaxi.rides.traffic.model.DriverId;
import com.pszymczyk.pietaxi.rides.traffic.model.Location;
import com.pszymczyk.pietaxi.model.RideId;
import com.pszymczyk.pietaxi.rides.traffic.application.RidesApplicationService;
import com.pszymczyk.pietaxi.rides.traffic.application.StartNewRideCommand;
import com.pszymczyk.pietaxi.rides.traffic.application.StopRideCommand;
import com.pszymczyk.pietaxi.rides.traffic.application.UpdateLocationCommand;

@RestController
class RidesController {

    private final RidesApplicationService ridesApplicationService;

    public RidesController(RidesApplicationService ridesApplicationService) {
        this.ridesApplicationService = ridesApplicationService;
    }

    static class StartRideRequestBody {
        String passengerId;
        String driverId;
        double pickLongitude;
        double pickLatitude;

        public String getPassengerId() {
            return passengerId;
        }

        public void setPassengerId(String passengerId) {
            this.passengerId = passengerId;
        }

        public String getDriverId() {
            return driverId;
        }

        public void setDriverId(String driverId) {
            this.driverId = driverId;
        }

        public double getPickLongitude() {
            return pickLongitude;
        }

        public void setPickLongitude(double pickLongitude) {
            this.pickLongitude = pickLongitude;
        }

        public double getPickLatitude() {
            return pickLatitude;
        }

        public void setPickLatitude(double pickLatitude) {
            this.pickLatitude = pickLatitude;
        }
    }

    @PostMapping(path = "/rides", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    Object startRide(@RequestBody StartRideRequestBody startRideRequestBody) {
        StartNewRideCommand startNewRideCommand = new StartNewRideCommand(
                new PassengerId(startRideRequestBody.passengerId),
                new DriverId(startRideRequestBody.driverId),
                new Location(startRideRequestBody.pickLongitude, startRideRequestBody.pickLatitude));
        RideId rideId = ridesApplicationService.startNewRide(startNewRideCommand);

        Map<String, UUID> response = new HashMap<>();
        response.put("rideId", rideId.getId());
        return response;
    }

    static class UpdateLocationRequestBody {
        double longitude;
        double latitude;

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
    }

    @PutMapping(path = "/rides/{rideId}",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    void putLocation(@PathVariable UUID rideId, @RequestBody UpdateLocationRequestBody updateLocationRequestBody) {
        UpdateLocationCommand updateLocationCommand = new UpdateLocationCommand(
                new RideId(rideId),
                new Location(updateLocationRequestBody.longitude, updateLocationRequestBody.latitude));
        ridesApplicationService.updateLocation(updateLocationCommand);
    }

    static class FinishRideRequestBody {

        double longitude;
        double latitude;

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
    }

    @DeleteMapping(path = "rides/{rideId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    void finishRide(@PathVariable UUID rideId, @RequestBody FinishRideRequestBody finishRideRequestBody) {
        StopRideCommand stopRideCommand = new StopRideCommand(
                new RideId(rideId),
                new Location(finishRideRequestBody.longitude, finishRideRequestBody.latitude));
        ridesApplicationService.stop(stopRideCommand);
    }
}
