package com.pszymczyk.pietaxi;

import java.util.List;

interface DistanceCalculationPrecisionPolicy {

    boolean enoughDataToCalculateDistance(List<Ride.PingLocation> locations);
}
