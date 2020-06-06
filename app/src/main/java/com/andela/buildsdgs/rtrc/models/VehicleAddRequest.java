package com.andela.buildsdgs.rtrc.models;

import com.google.gson.annotations.SerializedName;

public class VehicleAddRequest {

    @SerializedName("registration_number")
    private String registrationNumber;
    @SerializedName("chassis_number")
    private String chassisNumber;
    private String model;
    private String category;
    @SerializedName("license_number")
    private String licenseNumber;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
}

