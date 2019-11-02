package com.pszymczyk.pietaxi.billing;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.pszymczyk.pietaxi.model.PassengerId;

@Component
public class BillingFacade {

    private final DebtorsRegister debtorsRegister;

    public BillingFacade(DebtorsRegister debtorsRegister) {
        this.debtorsRegister = debtorsRegister;
    }

    public Set<PassengerId> getBlockedPassengers() {
        return debtorsRegister.findAll();
    }
}
