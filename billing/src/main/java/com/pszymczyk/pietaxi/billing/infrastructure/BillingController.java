package com.pszymczyk.pietaxi.billing.infrastructure;

import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pszymczyk.pietaxi.billing.model.DebtorsRegister;
import com.pszymczyk.pietaxi.model.PassengerId;

@RestController
class BillingController {

    private final DebtorsRegister debtorsRegister;

    public BillingController(DebtorsRegister debtorsRegister) {
        this.debtorsRegister = debtorsRegister;
    }

    @GetMapping(path = "/debtors", produces = MediaType.APPLICATION_JSON_VALUE)
    AllDebtorsResponse getAllDebtors() {
        Set<PassengerId> debtors = debtorsRegister.findAll();
        AllDebtorsResponse allDebtorsResponse = new AllDebtorsResponse();
        allDebtorsResponse.debtors = debtors;
        return allDebtorsResponse;
    }
}

class AllDebtorsResponse {
    Set<PassengerId> debtors;

    public Set<PassengerId> getDebtors() {
        return debtors;
    }

    public void setDebtors(Set<PassengerId> debtors) {
        this.debtors = debtors;
    }
}
