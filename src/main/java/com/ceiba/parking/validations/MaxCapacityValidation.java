package com.ceiba.parking.validations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ceiba.parking.domain.EVehicle;
import com.ceiba.parking.domain.Vehicle;
import com.ceiba.parking.properties.ParkingProperties;

public class MaxCapacityValidation implements ParkingValidation{
	
	private List<Vehicle>vehicles;
	private EVehicle vehicleToValid;
	
	public  MaxCapacityValidation(List<Vehicle>vehicles,EVehicle toValid) {
		this.vehicleToValid=toValid;
		this.vehicles=vehicles;
	}
	
	@Override
	public boolean valid() {
		return vehicles.size()+1 <= vehicleToValid.getMaxCapacity();
	}

	@Override
	public String invalidationMessage() {
		return ParkingProperties.getValue("ERROR_NO_VACANCY_CARS");
	}

}
