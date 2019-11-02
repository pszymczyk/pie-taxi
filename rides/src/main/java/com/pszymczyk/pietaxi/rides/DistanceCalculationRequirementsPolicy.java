package com.pszymczyk.pietaxi.rides;

import java.util.List;

interface DistanceCalculationRequirementsPolicy {

    String name();

    boolean enoughDataToCalculateDistance(List<Ride.PingLocation> locations);
}
