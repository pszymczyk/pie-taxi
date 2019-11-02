package com.pszymczyk.pietaxi.rides;

import java.util.Optional;

import com.pszymczyk.pietaxi.PassengerId;

interface DistanceCalculationRequirementsPolicyConfigurations {

    Optional<DistanceCalculationRequirementsPolicy> findPolicyFor(PassengerId passengerId);
}
