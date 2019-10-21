package com.pszymczyk.pietaxi;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.pszymczyk.pietaxi.TestFixtures.randomDriver;
import static com.pszymczyk.pietaxi.TestFixtures.randomPassenger;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class RideFactoryTest {

    private static final RideFactory rideFactory = new RideFactory(passengerId -> BillingApi.Status.OK, passengerId -> Optional.empty());

    @Test
    void Should_throw_exception_when_passenger_id_null() {
        //when
        Throwable thrown = catchThrowable(() -> rideFactory.create(null, randomDriver()));

        //then
        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("PassengerId");
    }

    @Test
    void Should_throw_exception_when_driver_id_null() {
        //when
        Throwable thrown = catchThrowable(() -> rideFactory.create(randomPassenger(), null));

        //then
        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("DriverId");
    }

    @Test
    void Should_throw_exception_when_passenger_is_blocked() {
        //given
        RideFactory rideFactory = new RideFactory(passengerId -> BillingApi.Status.BLOCKED, passengerId -> Optional.empty());

        //when
        Throwable thrown = catchThrowable(() -> rideFactory.create(randomPassenger(), randomDriver()));

        //then
        assertThat(thrown).isInstanceOf(PassengerAccountBlocked.class);
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
        RideFactory rideFactory = new RideFactory(passengerId -> BillingApi.Status.OK, passengerId -> Optional.of(mock));

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
