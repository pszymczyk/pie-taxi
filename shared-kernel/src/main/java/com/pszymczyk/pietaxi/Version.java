package com.pszymczyk.pietaxi;

public class Version {

    private int version;

    public Version(int version) {
        this.version = version;
    }

    public static Version zero() {
        return new Version(0);
    }

    void increment() {
        version++;
    }
}
