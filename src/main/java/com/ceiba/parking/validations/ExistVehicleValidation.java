package com.ceiba.parking.validations;

import com.ceiba.parking.domain.Vehicle;
import com.ceiba.parking.properties.ParkingProperties;
import com.ceiba.parking.repository.VehicleRepository;

public class ExistVehicleValidation implements ParkingValidation {
	private VehicleRepository repo;
	private Vehicle vehicle;
	
	public ExistVehicleValidation(VehicleRepository repo, Vehicle vehicle) {
		this.repo=repo;
		this.vehicle=vehicle;
	}
	@Override
	public boolean valid() {
		return !repo.findByNumberPlate(vehicle.getNumberPlate()).isEmpty();
	}

	@Override
	public String invalidationMessage() {
		return ParkingProperties.getValue("ERROR_DOESNT_EXIST");
	}
}
