package com.pszymczyk.pietaxi.billing.application;

import java.math.BigDecimal;

import com.pszymczyk.pietaxi.model.PassengerId;

public class SettleRideCommand {

    private final PassengerId passengerId;
    private final BigDecimal distance;

    public SettleRideCommand(PassengerId passengerId, BigDecimal distance) {
        this.passengerId = passengerId;
        this.distance = distance;
    }

    PassengerId getPassengerId() {
        return passengerId;
    }

    BigDecimal getDistance() {
        return distance;
    }
}
