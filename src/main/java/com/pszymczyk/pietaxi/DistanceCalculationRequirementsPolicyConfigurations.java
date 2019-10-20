package com.pszymczyk.pietaxi;

import java.util.Optional;

interface DistanceCalculationRequirementsPolicyConfigurations {

    Optional<DistanceCalculationRequirementsPolicy> findPolicyFor(PassengerId passengerId);
}
