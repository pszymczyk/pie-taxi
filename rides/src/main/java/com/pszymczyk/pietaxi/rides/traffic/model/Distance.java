package com.pszymczyk.pietaxi.rides.traffic.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

class Distance {

    static Distance zero() {
        return new Distance(BigDecimal.ZERO);
    }

    private final BigDecimal value;

    Distance(Location x, Location y) {
        this.value = distance(x, y);
    }

    Distance(BigDecimal value) {
        this.value = value.setScale(2, RoundingMode.HALF_UP);
    }

    Distance and(Distance other) {
        return new Distance(this.value.add(other.value));
    }

    private BigDecimal distance(Location x, Location y) {
        double x1 = x.getPickLatitude();
        double x2 = y.getPickLatitude();
        double y1 = x.getPickLongitude();
        double y2 = y.getPickLongitude();

        return BigDecimal.valueOf(Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Distance distance = (Distance) o;
        return value.equals(distance.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
