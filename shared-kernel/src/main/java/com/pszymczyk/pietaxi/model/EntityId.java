package com.pszymczyk.pietaxi.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonValue;

public abstract class EntityId<ID> {

    private final ID id;

    public EntityId(ID id) {
        this.id = id;
    }

    @JsonValue
    public ID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EntityId entityId = (EntityId) o;
        return Objects.equals(id, entityId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "{"  + id + "}";
    }
}
