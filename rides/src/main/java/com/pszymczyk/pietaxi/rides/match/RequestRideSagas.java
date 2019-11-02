package com.pszymczyk.pietaxi.rides.match;

import java.util.Optional;

interface RequestRideSagas {
    Optional<RequestRideSaga> findBy(SagaId sagaId);
    void save(RequestRideSaga saga);
}
