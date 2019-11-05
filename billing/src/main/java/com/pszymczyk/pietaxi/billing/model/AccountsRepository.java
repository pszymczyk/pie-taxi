package com.pszymczyk.pietaxi.billing.model;

import java.util.List;
import java.util.Optional;

import com.pszymczyk.pietaxi.model.PassengerId;

public interface AccountsRepository {

    Optional<Account> findByPassengerId(PassengerId passengerId);

    void save(Account account);

    List<Account> findBlockedAccounts();

}
