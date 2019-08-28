package com.ceiba.parking.transport;

public class VehicleDTO {

    private String plate;

    public VehicleDTO() {
    }

    public VehicleDTO(String plate) {
        this.plate = plate;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    @Override
    public String toString() {
        return plate;
    }
}
