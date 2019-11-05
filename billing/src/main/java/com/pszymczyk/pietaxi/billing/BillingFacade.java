package com.pszymczyk.pietaxi.billing;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.pszymczyk.pietaxi.billing.model.Account;
import com.pszymczyk.pietaxi.billing.model.AccountsRepository;
import com.pszymczyk.pietaxi.model.PassengerId;

@Component
public class BillingFacade {

    private final AccountsRepository accountsRepository;

    public BillingFacade(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public Set<PassengerId> getBlockedPassengers() {
        return accountsRepository.findBlockedAccounts().stream().map(Account::getPassengerId).collect(Collectors.toSet());
    }
}
