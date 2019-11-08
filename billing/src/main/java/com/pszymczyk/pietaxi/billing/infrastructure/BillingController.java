package com.pszymczyk.pietaxi.billing.infrastructure;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pszymczyk.pietaxi.billing.application.BillingApplicationService;
import com.pszymczyk.pietaxi.billing.application.SupplyAccountCommand;
import com.pszymczyk.pietaxi.billing.model.Account;
import com.pszymczyk.pietaxi.billing.model.AccountsRepository;
import com.pszymczyk.pietaxi.billing.model.Money;
import com.pszymczyk.pietaxi.model.PassengerId;

import lombok.Data;

@RestController
class BillingController {

    private final BillingApplicationService billingApplicationService;
    private AccountsRepository accountsRepository;

    public BillingController(BillingApplicationService billingApplicationService, AccountsRepository accountsRepository) {
        this.billingApplicationService = billingApplicationService;
        this.accountsRepository = accountsRepository;
    }

    @GetMapping(path = "/debtors", produces = MediaType.APPLICATION_JSON_VALUE)
    AllDebtorsResponse getAllDebtors() {
        Set<PassengerId> debtors = accountsRepository.findBlockedAccounts().stream().map(Account::getPassengerId).collect(Collectors.toSet());
        AllDebtorsResponse allDebtorsResponse = new AllDebtorsResponse();
        allDebtorsResponse.debtors = debtors;
        return allDebtorsResponse;
    }

    @PutMapping(path = "/accounts/{personId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    void supplyAccount(@RequestBody SupplyAccountRequest supplyAccountRequest, @PathVariable String personId) {
        SupplyAccountCommand supplyAccountCommand = new SupplyAccountCommand(new PassengerId(personId), Money.of(supplyAccountRequest.money));
        billingApplicationService.supplyAccount(supplyAccountCommand);
    }
}

@Data
class AllDebtorsResponse {
    Set<PassengerId> debtors;
}

@Data
class SupplyAccountRequest {
    String money;
}
