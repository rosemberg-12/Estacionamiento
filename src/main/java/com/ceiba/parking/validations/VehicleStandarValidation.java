package com.ceiba.parking.validations;

import com.ceiba.parking.domain.EVehicle;
import com.ceiba.parking.domain.Vehicle;
import com.ceiba.parking.properties.ParkingProperties;

/**
 * Standard validation of a vehicle.
 * @author rosemberg.porras
 *
 */
public class VehicleStandarValidation implements ParkingValidation{
	
	private Vehicle vehicle;
	
	public VehicleStandarValidation(Vehicle vehicle) {
		this.vehicle=vehicle;
	}
	@Override
	public boolean valid() {
		return validVehicle() && (validMotocycle() || validCar());
	}

	@Override
	public String invalidationMessage() {
		return ParkingProperties.getValue("ERROR_MISSING_PARAMETERS");
	}


	private boolean validVehicle(){
		return vehicle != null || vehicle.getNumberPlate()!=null;
	}

	private boolean validMotocycle(){
		return vehicle.getKindOfVehicle()==EVehicle.MOTORCYCLE && vehicle.getCylinderCapacity()!=null;
	}
	
	private boolean validCar(){
		return vehicle.getKindOfVehicle()==EVehicle.CAR;
	}
	
}
