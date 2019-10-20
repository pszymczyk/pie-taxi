package com.pszymczyk.pietaxi.matching;

import java.util.Optional;

interface RequestRideSagas {
    Optional<RequestRideSaga> findBy(SagaId sagaId);
    void save(RequestRideSaga saga);
}
