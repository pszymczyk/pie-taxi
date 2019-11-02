package com.pszymczyk.pietaxi.rides.traffic.model;

import java.util.List;

public interface DistanceCalculationRequirementsPolicy {

    String name();

    boolean enoughDataToCalculateDistance(List<Ride.PingLocation> locations);
}
