package com.pszymczyk.pietaxi.matching;

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

import com.pszymczyk.pietaxi.DriverId;
import com.pszymczyk.pietaxi.PassengerId;

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

        public String getPassengerId() {
            return passengerId;
        }

        public void setPassengerId(String passengerId) {
            this.passengerId = passengerId;
        }
    }

    @PostMapping(path = "/sagas", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    Object newSaga(@RequestBody NewSagaRequest newSagaRequest) {
        RequestRideSaga saga = new RequestRideSaga(new PassengerId(newSagaRequest.passengerId), requestRideSagaEvents);
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
        RequestRideSaga saga = requestRideSagas.findBy(new SagaId(UUID.fromString(sagaId)));
        if ("DriverAccepted".equals(sagaEvent.type)) {
            saga.handle(new DriverAccepted(new DriverId(sagaEvent.entityId)));
        } else if ("FriendFound".equals(sagaEvent.type)) {
            saga.handle(new FriendFound(new PassengerId(sagaEvent.entityId)));
        }

        throw new IllegalArgumentException("Event " + sagaEvent.type + " unknown");

    }
}
