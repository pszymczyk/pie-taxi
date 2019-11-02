package com.pszymczyk.pietaxi.rides.match;

import com.pszymczyk.pietaxi.model.PassengerId;

class FriendFound {

    private final PassengerId passengerId;

    public FriendFound(PassengerId passengerId) {
        this.passengerId = passengerId;
    }

    public PassengerId getPassengerId() {
        return passengerId;
    }
}
