package com.pszymczyk.pietaxi.matching;

class AllDriversBusy {

    private final SagaId id;

    AllDriversBusy(SagaId id) {
        this.id = id;
    }

    public SagaId getId() {
        return id;
    }
}