package com.pszymczyk.pietaxi.billing.model;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.Test;

import com.pszymczyk.pietaxi.model.PassengerId;

import static org.assertj.core.api.Assertions.assertThat;

class AccountTest {

    PassengerId passengerId = new PassengerId("kazik");

    @Test
    void Should_charge_account() {
        //given
        Account account = new Account(passengerId, Money.ZERO, Money.of("100"), Account.Status.ACTIVE);
        TestBillingEvents billingEvents = new TestBillingEvents();

        //when
        account.charge(Money.of("50"), billingEvents);

        //then
        assertThat(account.getDebt()).isEqualTo(Money.of("50"));
    }

    @Test
    void Should_block_account() {
        //given
        Account account = new Account(passengerId, Money.ZERO, Money.of("10"), Account.Status.ACTIVE);
        TestBillingEvents billingEvents = new TestBillingEvents();

        //when
        account.charge(Money.of("50"), billingEvents);

        //then
        assertThat(account.isBlocked()).isTrue();
        assertThat(billingEvents.passengerAccountBlockedEvents).hasSize(1)
                                                               .first()
                                                               .extracting(PassengerAccountBlocked::getEntityId).isEqualTo(passengerId.getId());
    }

    @Test
    void Should_charge_blocked_account() {
        //given
        Account account = new Account(passengerId, Money.of("100"), Money.of("10"), Account.Status.BLOCKED);
        TestBillingEvents billingEvents = new TestBillingEvents();

        //when
        account.charge(Money.of("50"), billingEvents);

        //then
        assertThat(account.getDebt()).isEqualTo(Money.of("150"));
        assertThat(account.isBlocked()).isTrue();
        assertThat(billingEvents.passengerAccountBlockedEvents).hasSize(1)
                                                               .first()
                                                               .extracting(PassengerAccountBlocked::getEntityId).isEqualTo(passengerId.getId());
    }

    @Test
    void Should_supply_account() {
        //given
        Account account = new Account(passengerId, Money.of("50"), Money.of("100"), Account.Status.ACTIVE);
        TestBillingEvents billingEvents = new TestBillingEvents();

        //when
        account.supply(Money.of("30"), billingEvents);

        //then
        assertThat(account.getDebt()).isEqualTo(Money.of("20"));
    }

    @Test
    void Should_inform_about_overpayment() {
        //given
        Account account = new Account(passengerId, Money.ZERO, Money.of("100"), Account.Status.ACTIVE);
        TestBillingEvents billingEvents = new TestBillingEvents();

        //when
        account.supply(Money.of("30"), billingEvents);

        //then
        assertThat(billingEvents.overpaymentEvents).hasSize(1)
                                                   .first()
                                                   .matches(overpayment -> overpayment.getMoney().equals(Money.of("30")) && overpayment.getEntityId().equals(passengerId.getId()));
    }

    @Test
    void Should_reduce_debt_when_overpayment() {
        //given
        Account account = new Account(passengerId, Money.of("20"), Money.of("100"), Account.Status.ACTIVE);
        TestBillingEvents billingEvents = new TestBillingEvents();

        //when
        account.supply(Money.of("30"), billingEvents);

        //then
        assertThat(account.getDebt()).isEqualTo(Money.ZERO);
    }

    @Test
    void Should_unblock_account_after_overpayment() {
        //given
        Account account = new Account(passengerId, Money.of("20"), Money.of("10"), Account.Status.BLOCKED);
        TestBillingEvents billingEvents = new TestBillingEvents();

        //when
        account.supply(Money.of("30"), billingEvents);

        //then
        assertThat(account.isBlocked()).isFalse();
    }

    @Test
    void Should_unblock_account_when_debt_under_limit() {
        //given
        Account account = new Account(passengerId, Money.of("50"), Money.of("30"), Account.Status.BLOCKED);
        TestBillingEvents billingEvents = new TestBillingEvents();

        //when
        account.supply(Money.of("30"), billingEvents);

        //then
        assertThat(account.isBlocked()).isFalse();
        assertThat(billingEvents.passengerAccountActivatedEvents).hasSize(1).first()
                                                                 .extracting(PassengerAccountActivated::getEntityId).isEqualTo(passengerId.getId());;
    }

    @Test
    void Should_not_unblock_account_when_debt_equal_to_limit() {
        //given
        Account account = new Account(passengerId, Money.of("50"), Money.of("30"), Account.Status.BLOCKED);
        TestBillingEvents billingEvents = new TestBillingEvents();

        //when
        account.supply(Money.of("20"), billingEvents);

        //then
        assertThat(account.isBlocked()).isTrue();
        assertThat(billingEvents.passengerAccountActivatedEvents).hasSize(0);
    }

    static class TestBillingEvents implements BillingEvents {

        private final Queue<PassengerAccountBlocked> passengerAccountBlockedEvents = new LinkedList<>();
        private final Queue<PassengerAccountActivated> passengerAccountActivatedEvents = new LinkedList<>();
        private final Queue<Overpayment> overpaymentEvents = new LinkedList<>();

        @Override
        public void publish(PassengerAccountActivated passengerAccountActivated) {
            passengerAccountActivatedEvents.add(passengerAccountActivated);
        }

        @Override
        public void publish(PassengerAccountBlocked passengerAccountBlocked) {
            passengerAccountBlockedEvents.add(passengerAccountBlocked);
        }

        @Override
        public void publish(Overpayment overpayment) {
            overpaymentEvents.add(overpayment);
        }
    }
}
