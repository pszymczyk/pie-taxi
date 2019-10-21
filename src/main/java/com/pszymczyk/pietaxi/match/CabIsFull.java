package com.pszymczyk.pietaxi.match;

class CabIsFull {

    private final SagaId id;

    CabIsFull(SagaId id) {
        this.id = id;
    }

    public SagaId getId() {
        return id;
    }

    @Override
    public String toString() {
        return "CabIsFull{" +
                "id=" + id +
                '}';
    }
}
