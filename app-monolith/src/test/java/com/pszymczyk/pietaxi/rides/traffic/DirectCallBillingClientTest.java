package com.pszymczyk.pietaxi.rides.traffic;

import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.pszymczyk.pietaxi.billing.BillingFacade;
import com.pszymczyk.pietaxi.model.PassengerId;
import com.pszymczyk.pietaxi.rides.traffic.model.BillingClient;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DirectCallBillingClientTest {

    @Test
    void Should_return_info_about_blocked_passenger() {
        //given
        PassengerId blockedPassenger = new PassengerId("kazik");
        Set<PassengerId> blockedPassengers = new HashSet<>();
        blockedPassengers.add(blockedPassenger);
        BillingFacade billingFacade = mock(BillingFacade.class);
        when(billingFacade.getBlockedPassengers()).thenReturn(blockedPassengers);
        BillingClient billingClient = new DirectCallBillingClient(billingFacade);

        //when
        BillingClient.Status status = billingClient.checkPassengerAccount(blockedPassenger);

        //then
        Assertions.assertThat(status).isEqualTo(BillingClient.Status.BLOCKED);
    }

    @Test
    void Should_return_info_about_active_passenger() {
        //given
        BillingFacade billingFacade = mock(BillingFacade.class);
        when(billingFacade.getBlockedPassengers()).thenReturn(new HashSet<>());
        BillingClient billingClient = new DirectCallBillingClient(billingFacade);

        //when
        BillingClient.Status status = billingClient.checkPassengerAccount(new PassengerId("kazik"));

        //then
        Assertions.assertThat(status).isEqualTo(BillingClient.Status.OK);
    }
}
