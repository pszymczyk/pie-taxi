package com.pszymczyk.pietaxi.rides;

import java.util.Objects;

public class Location {

    private final double pickLongitude;
    private final double pickLatitude;

    public Location(double pickLongitude, double pickLatitude) {
        this.pickLongitude = pickLongitude;
        this.pickLatitude = pickLatitude;
    }

    public double getPickLongitude() {
        return pickLongitude;
    }

    public double getPickLatitude() {
        return pickLatitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        return Double.compare(location.pickLongitude, pickLongitude) == 0 &&
                Double.compare(location.pickLatitude, pickLatitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pickLongitude, pickLatitude);
    }
}
