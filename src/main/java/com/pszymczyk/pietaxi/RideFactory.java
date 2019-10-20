package com.pszymczyk.pietaxi;

import java.util.Optional;
import java.util.UUID;

class RideFactory {

    private final BillingApi billingApi;
    private final DistanceCalculationRequirementsPolicyConfigurations distanceCalculationRequirementsPolicyConfigurations;

    public RideFactory(BillingApi billingApi, DistanceCalculationRequirementsPolicyConfigurations distanceCalculationRequirementsPolicyConfigurations) {
        this.billingApi = billingApi;
        this.distanceCalculationRequirementsPolicyConfigurations = distanceCalculationRequirementsPolicyConfigurations;
    }

    Ride create(PassengerId passengerId, DriverId driverId) {
        if (passengerId == null) {
            throw new NullPointerException("PassengerId cannot be null!");
        }

        if (driverId == null) {
            throw new NullPointerException("DriverId cannot be null!");
        }

        if (billingApi.checkPassengerAccount(passengerId) == BillingApi.Status.BLOCKED) {
            throw new PassengerAccountBlocked();
        }

        DistanceCalculationRequirementsPolicy distanceCalculationRequirementsPolicy = choosePolicy(passengerId).orElse(new DefaultDistanceCalculationPrecisionPolicy());

        return new Ride(new RideId(UUID.randomUUID()), passengerId, driverId, distanceCalculationRequirementsPolicy);
    }

    private Optional<DistanceCalculationRequirementsPolicy> choosePolicy(PassengerId passengerId) {
        return distanceCalculationRequirementsPolicyConfigurations.findPolicyFor(passengerId);
    }

}
