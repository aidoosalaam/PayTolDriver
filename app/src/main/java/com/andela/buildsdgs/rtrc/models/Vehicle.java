package com.andela.buildsdgs.rtrc.models;

import com.google.gson.annotations.SerializedName;

public class Vehicle {
    private String updated_at;
    private String registration_number;
    private String chassis_number;
    private String qr_code;
    private String created_at;
    private String model;
    private String id;
   // @SerializedName("category")
   // private VehicleCategory vehicleCategory;
   // @SerializedName("category")
    private Object category;
    private User user;
    private String license_number;

    public Vehicle() {
    }

    public Vehicle(String registration_number, String chassis_number, String model, String category, String license_number) {
        this.registration_number = registration_number;
        this.chassis_number = chassis_number;
        this.model = model;
        this.category = category;
        this.license_number = license_number;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(String registration_number) {
        this.registration_number = registration_number;
    }

    public String getChassis_number() {
        return chassis_number;
    }

    public void setChassis_number(String chassis_number) {
        this.chassis_number = chassis_number;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLicense_number() {
        return license_number;
    }

    public void setLicense_number(String license_number) {
        this.license_number = license_number;
    }
}
