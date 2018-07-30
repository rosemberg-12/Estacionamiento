package com.ceiba.parking.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ceiba.parking.controller.ParkingController;
import com.ceiba.parking.domain.Vehicle;

/**
 * Facade which expose the methods exposed by System.
 * @author rosemberg.porras
 *
 */
@Component("Facade")
public class Facade {
	@Autowired
	private ParkingController controller;
	
	public List<Vehicle> getAllVehicles(){
		return controller.getAllVehicle();
	}

}
