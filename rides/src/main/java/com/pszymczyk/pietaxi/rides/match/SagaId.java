package com.pszymczyk.pietaxi.rides.match;

import java.util.UUID;

import com.pszymczyk.pietaxi.EntityId;

class SagaId extends EntityId<UUID> {

    SagaId(UUID uuid) {
        super(uuid);
    }
}
