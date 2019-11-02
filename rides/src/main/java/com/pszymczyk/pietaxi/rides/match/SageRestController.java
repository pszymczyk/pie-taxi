package com.pszymczyk.pietaxi.rides.match;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pszymczyk.pietaxi.model.PassengerId;
import com.pszymczyk.pietaxi.rides.traffic.model.DriverId;
import com.pszymczyk.pietaxi.rides.traffic.model.Location;

@RestController
class SageRestController {

    private final RequestRideSagaEvents requestRideSagaEvents;
    private final RequestRideSagas requestRideSagas;

    public SageRestController(RequestRideSagas requestRideSagas, RequestRideSagaEvents requestRideSagaEvents) {
        this.requestRideSagas = requestRideSagas;
        this.requestRideSagaEvents = requestRideSagaEvents;
    }

    static class NewSagaRequest {
        String passengerId;
        Integer longitude;
        Integer latitude;

        public String getPassengerId() {
            return passengerId;
        }

        public void setPassengerId(String passengerId) {
            this.passengerId = passengerId;
        }

        public Integer getLongitude() {
            return longitude;
        }

        public void setLongitude(Integer longitude) {
            this.longitude = longitude;
        }

        public Integer getLatitude() {
            return latitude;
        }

        public void setLatitude(Integer latitude) {
            this.latitude = latitude;
        }
    }

    @PostMapping(path = "/sagas", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    Object newSaga(@RequestBody NewSagaRequest newSagaRequest) {
        RequestRideSaga saga = new RequestRideSaga(
                new PassengerId(newSagaRequest.passengerId),
                new Location(newSagaRequest.longitude, newSagaRequest.latitude),
                requestRideSagaEvents);
        requestRideSagas.save(saga);

        Map<String, String> response = new HashMap<>();
        response.put("sagaId", saga.id.getId().toString());
        return response;
    }

    static class SagaEvent {
        String type;
        String entityId;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }
    }

    @PostMapping(path = "sagas/{sagaId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    void handleEvent(@RequestBody SagaEvent sagaEvent, @PathVariable String sagaId) {
        RequestRideSaga saga = requestRideSagas.findBy(new SagaId(UUID.fromString(sagaId))).orElseThrow(() -> new IllegalArgumentException("Saga not found"));
        if ("DriverAccepted".equals(sagaEvent.type)) {
            saga.handle(new DriverAccepted(new DriverId(sagaEvent.entityId)));
        } else if ("FriendFound".equals(sagaEvent.type)) {
            saga.handle(new FriendFound(new PassengerId(sagaEvent.entityId)));
        } else if ("Timeout".contains(sagaEvent.type)) {
            saga.timeout();
        } else {
            throw new IllegalArgumentException("Event " + sagaEvent.type + " unknown");
        }

    }
}
