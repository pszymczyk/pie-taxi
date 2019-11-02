package com.pszymczyk.pietaxi.rides;

import java.util.Optional;

interface DistanceCalculationRequirementsPolicyConfigurations {

    Optional<DistanceCalculationRequirementsPolicy> findPolicyFor(PassengerId passengerId);
}
