package com.pszymczyk.pietaxi.match;

import com.pszymczyk.pietaxi.PassengerId;

class FriendFound {

    private final PassengerId passengerId;

    public FriendFound(PassengerId passengerId) {
        this.passengerId = passengerId;
    }

    public PassengerId getPassengerId() {
        return passengerId;
    }
}
