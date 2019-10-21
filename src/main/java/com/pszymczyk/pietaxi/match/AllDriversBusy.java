package com.pszymczyk.pietaxi.match;

public class AllDriversBusy {

    private final SagaId id;

    AllDriversBusy(SagaId id) {
        this.id = id;
    }

    public SagaId getId() {
        return id;
    }

    @Override
    public String toString() {
        return "AllDriversBusy{" +
                "id=" + id +
                '}';
    }
}
