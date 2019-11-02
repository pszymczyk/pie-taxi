package com.pszymczyk.pietaxi.rides.traffic.model;

import java.util.Optional;

import com.pszymczyk.pietaxi.model.PassengerId;

interface DistanceCalculationRequirementsPolicyConfigurations {

    Optional<DistanceCalculationRequirementsPolicy> findPolicyFor(PassengerId passengerId);
}
