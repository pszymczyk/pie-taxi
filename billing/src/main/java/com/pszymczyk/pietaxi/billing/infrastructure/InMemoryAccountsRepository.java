package com.pszymczyk.pietaxi.billing.infrastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.pszymczyk.pietaxi.billing.model.Account;
import com.pszymczyk.pietaxi.billing.model.AccountsRepository;
import com.pszymczyk.pietaxi.billing.model.Money;
import com.pszymczyk.pietaxi.model.PassengerId;

@Component
class InMemoryAccountsRepository implements AccountsRepository {

    private final Map<PassengerId, Account> db = new HashMap<>();

    public InMemoryAccountsRepository() {
        db.put(new PassengerId("kazik"), new Account(new PassengerId("kazik"), Money.ZERO, Money.of("100"), Account.Status.ACTIVE));
        db.put(new PassengerId("jan"), new Account(new PassengerId("jan"), Money.ZERO, Money.of("100"), Account.Status.ACTIVE));
        db.put(new PassengerId("ryszard"), new Account(new PassengerId("ryszard"), Money.ZERO, Money.of("100"), Account.Status.ACTIVE));
    }

    @Override
    public Optional<Account> findByPassengerId(PassengerId passengerId) {
        return Optional.ofNullable(db.get(passengerId));
    }

    @Override
    public void save(Account account) {
        db.put(account.getPassengerId(), account);
    }
}
