package com.pszymczyk.pietaxi;

import java.util.List;

class PingsPerMinutePolicy implements DistanceCalculationRequirementsPolicy {

    static final String NAME = "PingsPerMinutePolicy";


    public PingsPerMinutePolicy(double acceptedRate) {
        throw new TODO();
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public boolean enoughDataToCalculateDistance(List<Ride.PingLocation> locations) {
        throw new TODO();
    }
}
