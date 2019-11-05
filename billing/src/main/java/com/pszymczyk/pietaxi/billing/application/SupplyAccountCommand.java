package com.pszymczyk.pietaxi.billing.application;

import com.pszymczyk.pietaxi.billing.model.Money;
import com.pszymczyk.pietaxi.model.PassengerId;

public class SupplyAccountCommand {

    private final PassengerId passengerId;
    private final Money money;

    public SupplyAccountCommand(PassengerId passengerId, Money money) {
        this.passengerId = passengerId;
        this.money = money;
    }

    public PassengerId getPassengerId() {
        return passengerId;
    }

    public Money getMoney() {
        return money;
    }
}
