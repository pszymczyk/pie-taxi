package com.pszymczyk.pietaxi;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class Ride {

    public enum Status {
        ACTIVE,
        FINISHED;
    }

    private UUID rideId;
    private User user;
    private Status status;
    private List<Location> locations = new ArrayList<>();
    private BigDecimal cost;
    private Instant startTime;

    BigDecimal distance;
    private boolean vipTaxiRide;
    private BigDecimal perKmFee;
    private BigDecimal perMinuteFee;
    private BigDecimal doorSlamFee;
    private BigDecimal vipTaxiFee;

    public void start(Location location, Clock clock) {
        if (location == null) {
            throw new NullPointerException("location cannot be null!");
        }

        if (!locations.isEmpty()) {
            throw new RideAlreadyStarted();
        }

        locations.add(location);
    }


    public void ping(Location location) {
        if (location == null) {
            throw new NullPointerException("location cannot be null!");
        }

        locations.add(location);
    }

    public void stop(Location location){
        if (location != null) {
            locations.add(location);
        }

        distance = BigDecimal.ZERO;
        Location previous = locations.get(0);
        for (int i=1; i<locations.size(); i++) {
            double x1 = previous.getPickLatitude();
            double x2 = previous.getPickLatitude();
            double y1 = locations.get(i).getPickLongitude();
            double y2 = locations.get(i).getPickLongitude();

            this.distance = distance.add(BigDecimal.valueOf(Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2))));
        }

        status = Status.FINISHED;
    }

    public void calculateCost(Instant stopTime) {
        long minutes = Duration.between(startTime, stopTime).get(ChronoUnit.MINUTES);
        cost = cost.add(doorSlamFee);
        cost = cost.add(perMinuteFee.multiply(new BigDecimal(minutes)));
        cost = cost.add(distance.multiply(perKmFee));
        if (vipTaxiRide) {
            cost = cost.add(vipTaxiFee);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public boolean isVipTaxiRide() {
        return vipTaxiRide;
    }

    public void setVipTaxiRide(boolean vipTaxiRide) {
        this.vipTaxiRide = vipTaxiRide;
    }

    public BigDecimal getPerKmFee() {
        return perKmFee;
    }

    public void setPerKmFee(BigDecimal perKmFee) {
        this.perKmFee = perKmFee;
    }

    public BigDecimal getPerMinuteFee() {
        return perMinuteFee;
    }

    public void setPerMinuteFee(BigDecimal perMinuteFee) {
        this.perMinuteFee = perMinuteFee;
    }

    public BigDecimal getDoorSlamFee() {
        return doorSlamFee;
    }

    public void setDoorSlamFee(BigDecimal doorSlamFee) {
        this.doorSlamFee = doorSlamFee;
    }

    public BigDecimal getVipTaxiFee() {
        return vipTaxiFee;
    }

    public void setVipTaxiFee(BigDecimal vipTaxiFee) {
        this.vipTaxiFee = vipTaxiFee;
    }
}
