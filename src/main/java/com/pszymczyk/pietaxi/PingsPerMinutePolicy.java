package com.pszymczyk.pietaxi;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.List;

class PingsPerMinutePolicy implements DistanceCalculationRequirementsPolicy {

    static final String NAME = "PingsPerMinutePolicy";

    final double acceptedRate;

    public PingsPerMinutePolicy(double acceptedRate) {
        this.acceptedRate = acceptedRate;
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public boolean enoughDataToCalculateDistance(List<Ride.PingLocation> locations) {
        Ride.PingLocation first = locations.get(0);
        Ride.PingLocation last = locations.get(locations.size() - 1);

        long rideDurationInMinutes = Duration.between(first.getTime(), last.getTime()).toMinutes();

        if (rideDurationInMinutes < 1) {
            throw new IllegalStateException("There is not enough data data to calculate rate per minute");
        }

        double rate = ((double)rideDurationInMinutes / locations.size());
        return toBigDecimal(acceptedRate).compareTo(toBigDecimal(rate)) >= 0;
    }

    private BigDecimal toBigDecimal(double acceptedRate) {
        return BigDecimal.valueOf(acceptedRate).setScale(2, RoundingMode.HALF_UP);
    }
}
