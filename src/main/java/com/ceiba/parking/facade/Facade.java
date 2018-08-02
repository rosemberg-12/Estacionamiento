package com.ceiba.parking.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import com.ceiba.parking.controller.IParkingController;
import com.ceiba.parking.domain.EVehicle;
import com.ceiba.parking.domain.FilterVehicle;
import com.ceiba.parking.domain.Vehicle;
import com.ceiba.parking.exceptions.ParkingException;

/**
 * Facade which expose the methods exposed by System.
 * @author rosemberg.porras
 *
 */
@Component("Facade")
public class Facade {
	@Autowired
	private IParkingController controller;
	
	/**
	 * Get all the vehicles given a filter criteria.
	 * @param filter
	 * @return
	 */
	public List<Vehicle> getVehicles(FilterVehicle filter){
		return controller.getAllVehicle(filter);
	}
	

	/**
	 * park a car, in the case that was not possible, will throw a business exception with the respective message.
	 * @param numberPlate
	 * @return
	 */
	public void registerVehicle (Vehicle vehicle) throws ParkingException {
		controller.registerVehicle(vehicle);
	}
	
	/**
	 * Get the vacancy of the cars in the parking.
	 */
	public Integer getCarVacancy(){
		return EVehicle.CAR.getMaxCapacity()-controller.getQuantityOfCars();
	}
	
	/**
	 * Get the vacancy of the cars in the parking.
	 */
	public Integer getMotorcycleVacancy(){
		return EVehicle.MOTORCYCLE.getMaxCapacity()-controller.getQuantityOfMotorcycles();
	}
	
	/**
	 * Get the cost of parking of a vehicle by number of plate.
	 * 
	 */
	public Pair<Vehicle, Long> unParkingVehicle(Vehicle vehicle){
		return controller.unRegisterVehicle(vehicle);
	}
}
