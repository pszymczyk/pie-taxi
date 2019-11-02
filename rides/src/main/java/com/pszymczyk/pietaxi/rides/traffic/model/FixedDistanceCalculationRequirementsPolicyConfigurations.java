package com.pszymczyk.pietaxi.rides.traffic.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.pszymczyk.pietaxi.model.PassengerId;

@Component
class FixedDistanceCalculationRequirementsPolicyConfigurations implements DistanceCalculationRequirementsPolicyConfigurations{

    private static final Map<String, DistanceCalculationRequirementsPolicy> almostDatabase = new HashMap<>();

    static {
        almostDatabase.put("kazik", new PingsPerMinutePolicy(0.3));
        almostDatabase.put("jadzia", new PingsPerMinutePolicy(0.3));
        almostDatabase.put("waldek", new PingsPerMinutePolicy(0.3));

        almostDatabase.put("renata", new PingsPerMinutePolicy(0.01));
        almostDatabase.put("zenon", new PingsPerMinutePolicy(0.01));
        almostDatabase.put("adam", new PingsPerMinutePolicy(0.01));
    }

    @Override
    public Optional<DistanceCalculationRequirementsPolicy> findPolicyFor(PassengerId passengerId) {
        return Optional.ofNullable(almostDatabase.get(passengerId.getId()));
    }
}
