package com.pszymczyk.pietaxi.rides.traffic.model;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.pszymczyk.pietaxi.rides.traffic.TestFixtures.randomDriver;
import static com.pszymczyk.pietaxi.rides.traffic.TestFixtures.randomPassenger;
import static org.assertj.core.api.Assertions.assertThat;

class RideFactoryTest {

    private static final RideFactory rideFactory = new RideFactory(passengerId -> BillingClient.Status.OK, passengerId -> Optional.empty());

    @Test
    void Should_throw_exception_when_passenger_id_null() {
        //when
        Throwable thrown = Assertions.catchThrowable(() -> rideFactory.create(null, randomDriver()));

        //then
        Assertions.assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("PassengerId");
    }

    @Test
    void Should_throw_exception_when_driver_id_null() {
        //when
        Throwable thrown = Assertions.catchThrowable(() -> rideFactory.create(randomPassenger(), null));

        //then
        Assertions.assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("DriverId");
    }

    @Test
    void Should_throw_exception_when_passenger_is_blocked() {
        //given
        RideFactory rideFactory = new RideFactory(passengerId -> BillingClient.Status.BLOCKED, passengerId -> Optional.empty());

        //when
        Throwable thrown = Assertions.catchThrowable(() -> rideFactory.create(randomPassenger(), randomDriver()));

        //then
        Assertions.assertThat(thrown).isInstanceOf(PassengerAccountBlocked.class);
    }

    @Test
    void Should_use_default_policy_when_there_is_no_configured_one() {
        //when
        Ride ride = rideFactory.create(randomPassenger(), randomDriver());

        //then
        assertThat(ride.distanceCalculationRequirementsPolicy.name()).isEqualTo(DefaultDistanceCalculationPrecisionPolicy.NAME);
    }

    @Test
    void Should_use_configured_policy() {
        //given
        DistanceCalculationRequirementsPolicy mock = Mockito.mock(DistanceCalculationRequirementsPolicy.class);
        Mockito.when(mock.name()).thenReturn("MOCK");
        RideFactory rideFactory = new RideFactory(passengerId -> BillingClient.Status.OK, passengerId -> Optional.of(mock));

        //when
        Ride ride = rideFactory.create(randomPassenger(), randomDriver());

        //then
        assertThat(ride.distanceCalculationRequirementsPolicy.name()).isEqualTo("MOCK");
    }

    @Test
    void Should_generate_random_id() {
        //when
        Ride ride = rideFactory.create(randomPassenger(), randomDriver());

        //then
        assertThat(ride.rideId).isNotNull();
    }

}
