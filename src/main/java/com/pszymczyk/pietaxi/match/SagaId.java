package com.pszymczyk.pietaxi.match;

import java.util.UUID;

import com.pszymczyk.pietaxi.EntityId;

class SagaId extends EntityId<UUID> {

    SagaId(UUID uuid) {
        super(uuid);
    }
}
