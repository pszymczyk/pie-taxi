package com.pszymczyk.pietaxi.matching;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
class InMemoryRequestRideSagas implements RequestRideSagas {

    private final Map<SagaId, RequestRideSaga> storage = new HashMap<>();

    @Override
    public RequestRideSaga findBy(SagaId sagaId) {
        return storage.get(sagaId);
    }

    @Override
    public void save(RequestRideSaga saga) {
        storage.put(saga.id, saga);
    }
}
