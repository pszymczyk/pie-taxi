package com.pszymczyk.pietaxi.rides.traffic.model;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.pszymczyk.pietaxi.model.PassengerId;
import com.pszymczyk.pietaxi.model.RideId;

@Component
public
class RideFactory {

    private final BillingClient billingClient;
    private final DistanceCalculationRequirementsPolicyConfigurations distanceCalculationRequirementsPolicyConfigurations;

    public RideFactory(BillingClient billingClient, DistanceCalculationRequirementsPolicyConfigurations distanceCalculationRequirementsPolicyConfigurations) {
        this.billingClient = billingClient;
        this.distanceCalculationRequirementsPolicyConfigurations = distanceCalculationRequirementsPolicyConfigurations;
    }

    public Ride create(PassengerId passengerId, DriverId driverId) {
        if (passengerId == null) {
            throw new NullPointerException("PassengerId cannot be null!");
        }

        if (driverId == null) {
            throw new NullPointerException("DriverId cannot be null!");
        }

        if (billingClient.checkPassengerAccount(passengerId) == BillingClient.Status.BLOCKED) {
            throw new PassengerAccountBlocked();
        }

        DistanceCalculationRequirementsPolicy distanceCalculationRequirementsPolicy = choosePolicy(passengerId).orElse(new DefaultDistanceCalculationPrecisionPolicy());

        return new Ride(new RideId(UUID.randomUUID()), passengerId, driverId, distanceCalculationRequirementsPolicy, new ArrayList<>());
    }

    private Optional<DistanceCalculationRequirementsPolicy> choosePolicy(PassengerId passengerId) {
        return distanceCalculationRequirementsPolicyConfigurations.findPolicyFor(passengerId);
    }

}
