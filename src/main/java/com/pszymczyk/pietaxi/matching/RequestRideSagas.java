package com.pszymczyk.pietaxi.matching;

interface RequestRideSagas {
    RequestRideSaga findBy(SagaId sagaId);
    void save(RequestRideSaga saga);
}
