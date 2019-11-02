package com.pszymczyk.pietaxi.rides;

import java.util.UUID;

import com.pszymczyk.pietaxi.EntityId;

public class RideId extends EntityId<UUID> {

    RideId(UUID uuid) {
        super(uuid);
    }
}
