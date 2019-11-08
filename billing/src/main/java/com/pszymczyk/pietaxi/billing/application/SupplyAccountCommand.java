package com.pszymczyk.pietaxi.billing.application;

import com.pszymczyk.pietaxi.billing.model.Money;
import com.pszymczyk.pietaxi.model.PassengerId;

import lombok.Data;

@Data
public class SupplyAccountCommand {

    private final PassengerId passengerId;
    private final Money money;

}
