package com.pszymczyk.pietaxi.rides.traffic.infrastructure;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import org.springframework.stereotype.Component;

import com.pszymczyk.pietaxi.model.PassengerId;
import com.pszymczyk.pietaxi.rides.traffic.model.DefaultDistanceCalculationPrecisionPolicy;
import com.pszymczyk.pietaxi.rides.traffic.model.DriverId;
import com.pszymczyk.pietaxi.rides.traffic.model.Location;
import com.pszymczyk.pietaxi.model.RideId;
import com.pszymczyk.pietaxi.rides.traffic.model.Ride;
import com.pszymczyk.pietaxi.rides.traffic.model.RideRepository;

@Component
class JpaRideRepository implements RideRepository {

    private final RideEntityCrudRepository rideEntityCrudRepository;

    public JpaRideRepository(RideEntityCrudRepository rideEntityCrudRepository) {
        this.rideEntityCrudRepository = rideEntityCrudRepository;
    }

    @Override
    public void save(Ride ride) {
        //TODO
        Optional<JpaRideEntity> entity = rideEntityCrudRepository.findByRideId(ride.getRideId().getId());
        if (!entity.isPresent()) {
            JpaRideEntity jpaRideEntity = new JpaRideEntity();
            jpaRideEntity.setDriverId(ride.getDriverId().getId());
            jpaRideEntity.setRideId(ride.getRideId().getId());
            jpaRideEntity.setPassengerId(ride.getPassengerId().getId());
            jpaRideEntity.distanceCalculationPolicy = ride.getDistanceCalculationRequirementsPolicy().name();
            List<JpaLocationEntity> locations = ride.getLocations()
                                                   .stream()
                                                   .map(location -> {
                                                       JpaLocationEntity jpaLocationEntity = new JpaLocationEntity();
                                                       jpaLocationEntity.time = location.getTime();
                                                       jpaLocationEntity.latitude = location.getLocation().getPickLatitude();
                                                       jpaLocationEntity.longitude = location.getLocation().getPickLongitude();
                                                       return jpaLocationEntity;
                                                   })
                                                   .collect(Collectors.toList());
            jpaRideEntity.locations.addAll(locations);
            rideEntityCrudRepository.save(jpaRideEntity);
        } else {
            JpaRideEntity jpaRideEntity = entity.get();
            List<JpaLocationEntity> locations = ride.getLocations()
                    .stream()
                    .map(location -> {
                        JpaLocationEntity jpaLocationEntity = new JpaLocationEntity();
                        jpaLocationEntity.time = location.getTime();
                        jpaLocationEntity.latitude = location.getLocation().getPickLatitude();
                        jpaLocationEntity.longitude = location.getLocation().getPickLongitude();
                        return jpaLocationEntity;
                    })
                    .collect(Collectors.toList());
            jpaRideEntity.locations.clear();
            jpaRideEntity.locations.addAll(locations);
            rideEntityCrudRepository.save(jpaRideEntity);
        }
    }

    @Override
    public Optional<Ride> findById(RideId rideId) {
        Optional<JpaRideEntity> jpaRideEntity = rideEntityCrudRepository.findByRideId(rideId.getId());
        if (!jpaRideEntity.isPresent()) {
            return Optional.empty();
        }

        JpaRideEntity entity = jpaRideEntity.get();
        List<Ride.PingLocation> locations = entity.locations
                .stream()
                .map(locationEntity -> new Ride.PingLocation(locationEntity.time, new Location(locationEntity.longitude, locationEntity.latitude)))
                .collect(Collectors.toList());

        return Optional.of(new Ride(
                new RideId(entity.rideId),
                new PassengerId(entity.passengerId),
                new DriverId(entity.passengerId),
                new DefaultDistanceCalculationPrecisionPolicy(),
                locations));
    }

    @Override
    public void delete(Ride ride) {
        rideEntityCrudRepository.deleteByRideId(ride.getRideId().getId());
    }
}

@Entity
class JpaRideEntity {

    @Id
    @GeneratedValue
    private long entityId;
    UUID rideId;
    String passengerId;
    String driverId;
    String distanceCalculationPolicy;
    @Version
    int version;
    @OneToMany(orphanRemoval=true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="RIDE_ENTITY_ID")
    List<JpaLocationEntity> locations = new ArrayList<>();

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public UUID getRideId() {
        return rideId;
    }

    public void setRideId(UUID rideId) {
        this.rideId = rideId;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDistanceCalculationPolicy() {
        return distanceCalculationPolicy;
    }

    public void setDistanceCalculationPolicy(String distanceCalculationPolicy) {
        this.distanceCalculationPolicy = distanceCalculationPolicy;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<JpaLocationEntity> getLocations() {
        return locations;
    }

    public void setLocations(List<JpaLocationEntity> locations) {
        this.locations = locations;
    }
}

@Entity
class JpaLocationEntity {

    @Id
    @GeneratedValue
    private long entityId;
    Instant time;
    double longitude;
    double latitude;

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
