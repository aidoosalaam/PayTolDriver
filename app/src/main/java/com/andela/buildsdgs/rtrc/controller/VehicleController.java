package com.andela.buildsdgs.rtrc.controller;

import com.andela.buildsdgs.rtrc.models.Vehicle;
import com.andela.buildsdgs.rtrc.models.VehicleCategory;

import java.util.ArrayList;
import java.util.List;

public class VehicleController {
    public VehicleController() {
    }

    public List<Vehicle> getVehicleList() {
        List<Vehicle> vehicleList = new ArrayList<>();
        // public Vehicle(int vehicleId, String vehicleName, String vehicleCategory) {
//        vehicleList.add(new Vehicle(1, "Nissan Rogue", "4x4"));
//        vehicleList.add(new Vehicle(2, "Honda CRV", "4x4"));
//        vehicleList.add(new Vehicle(1, "Nissan Rogue", "4x4"));
//        vehicleList.add(new Vehicle(2, "Honda CRV", "4x4"));
//        vehicleList.add(new Vehicle(3, "Toyota Corolla", "Saloon"));
//        vehicleList.add(new Vehicle(3, "Toyota Corolla", "Saloon"));
        return vehicleList;
    }


    public List<VehicleCategory> getVehicleCategoryList() {
        List<VehicleCategory> vehicleCategories = new ArrayList<>();
        vehicleCategories.add(new VehicleCategory("SMALL BUS (LAND CRUISER) SUV", "1.0",""));
        vehicleCategories.add(new VehicleCategory("CAR", "0.50",""));
        vehicleCategories.add(new VehicleCategory("LARGE BUS", "2.0",""));

        return vehicleCategories;
    }
}
