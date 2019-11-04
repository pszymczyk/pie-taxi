package com.pszymczyk.pietaxi.rides.traffic.infrastructure.statetransfered;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
class BlockedPassengerEntity {

    @Id
    String passengerId;

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }
}
