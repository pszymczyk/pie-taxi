package com.pszymczyk.pietaxi;

import java.util.Objects;
import java.util.UUID;

abstract class EntityId {

    private final UUID uuid;

    EntityId(UUID uuid) {
        this.uuid = uuid;
    }

    UUID getUuid() {
        return uuid;
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
        return Objects.equals(uuid, entityId.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
