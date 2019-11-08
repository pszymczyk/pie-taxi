package com.pszymczyk.pietaxi.billing.application;

import java.math.BigDecimal;

import com.pszymczyk.pietaxi.model.PassengerId;

import lombok.Data;

@Data
public class SettleRideCommand {

    private final PassengerId passengerId;
    private final BigDecimal distance;

}
