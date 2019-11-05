package com.pszymczyk.pietaxi.billing.model;

import com.pszymczyk.pietaxi.model.PassengerId;

public class Account {

    public enum Status {
        ACTIVE,
        BLOCKED;
    }

    private final PassengerId passengerId;

    private Money debt;
    private Money limit;
    private Status status;

    public Account(PassengerId passengerId, Money debt, Money limit, Status status) {
        this.passengerId = passengerId;
        this.debt = debt;
        this.limit = limit;
        this.status = status;
    }

    public void charge(Money money, BillingEvents billingEvents) {
        debt = debt.add(money);
        if (debtGreaterOrEqualToLimit()) {
            blockAccount(billingEvents);
        }
    }

    private void blockAccount(BillingEvents billingEvents) {
        status = Status.BLOCKED;
    }

    private boolean debtGreaterOrEqualToLimit() {
        return debt.isGreaterOrEqualThen(limit);
    }

    public void supply(Money supply, BillingEvents billingEvents) {
        if (debt.isLessThen(supply)) {
            overpayment(supply, billingEvents);
        } else {
            debt = debt.minus(supply);
            if (status == Status.BLOCKED && debt.isLessThen(limit)) {
                activateAccount(billingEvents);
            }
        }
    }

    private void overpayment(Money supply, BillingEvents billingEvents) {
        debt = Money.zero();

        if (isBlocked()) {
            activateAccount(billingEvents);
        }
    }

    private void activateAccount(BillingEvents billingEvents) {
        status = Status.ACTIVE;
    }

    Money getDebt() {
        return debt;
    }

    public boolean isBlocked() {
        return status == Status.BLOCKED;
    }

    public PassengerId getPassengerId() {
        return passengerId;
    }
}
