package com.pszymczyk.pietaxi.billing.application;

import org.springframework.stereotype.Component;

import com.pszymczyk.pietaxi.billing.model.Account;
import com.pszymczyk.pietaxi.billing.model.AccountsRepository;
import com.pszymczyk.pietaxi.billing.model.BillingEvents;
import com.pszymczyk.pietaxi.billing.model.Money;
import com.pszymczyk.pietaxi.billing.model.TariffDomainService;

@Component
public class BillingApplicationService {

    private final AccountsRepository accountsRepository;
    private BillingEvents billingEvents;

    public BillingApplicationService(AccountsRepository accountsRepository, BillingEvents billingEvents) {
        this.accountsRepository = accountsRepository;
        this.billingEvents = billingEvents;
    }

    public void settleRide(SettleRideCommand settleRideCommand) {
        Account account = accountsRepository.findByPassengerId(settleRideCommand.getPassengerId()).orElseThrow(IllegalArgumentException::new);
        Money cost = TariffDomainService.calculate(settleRideCommand.getDistance());
        account.charge(cost, billingEvents);
        accountsRepository.save(account);
    }
}
