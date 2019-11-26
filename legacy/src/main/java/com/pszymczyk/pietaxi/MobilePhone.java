package com.pszymczyk.pietaxi;

class MobilePhone {

    enum OperatingSystem {
        IOS,
        ANDROID
    }

    private String model;
    private OperatingSystem operatingSystem;
    private String osVersion;

    public MobilePhone(String model, OperatingSystem operatingSystem, String osVersion) {
        this.model = model;
        this.operatingSystem = operatingSystem;
        this.osVersion = osVersion;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }
}
