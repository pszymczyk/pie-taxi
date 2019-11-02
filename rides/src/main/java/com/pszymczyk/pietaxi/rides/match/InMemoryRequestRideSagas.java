package com.pszymczyk.pietaxi.rides.match;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections4.MapUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class InMemoryRequestRideSagas implements RequestRideSagas {

    private final Map<SagaId, RequestRideSaga> storage = new HashMap<>();

    @Override
    public Optional<RequestRideSaga> findBy(SagaId sagaId) {
        return Optional.ofNullable(storage.get(sagaId));
    }

    @Override
    public void save(RequestRideSaga saga) {
        storage.put(saga.id, saga);
    }

    @Scheduled(fixedRate = 5000)
    public void printData() {
        MapUtils.verbosePrint(System.out, "Request rides", storage);
    }
}
