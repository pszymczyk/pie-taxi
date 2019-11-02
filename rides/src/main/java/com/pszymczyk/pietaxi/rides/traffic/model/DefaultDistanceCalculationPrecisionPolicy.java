package com.pszymczyk.pietaxi.rides.traffic.model;

import java.util.List;

public class DefaultDistanceCalculationPrecisionPolicy implements DistanceCalculationRequirementsPolicy {

    static final String NAME = "Default";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public boolean enoughDataToCalculateDistance(List<Ride.PingLocation> locations) {
        return locations.size() > 2;
    }
}
