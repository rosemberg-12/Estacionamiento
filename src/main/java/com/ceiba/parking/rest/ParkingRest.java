package com.ceiba.parking.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.parking.domain.FilterVehicle;
import com.ceiba.parking.domain.Vehicle;
import com.ceiba.parking.facade.Facade;
/**
 * Rest class 
 * @author rosemberg.porras
 *
 */
@RestController
public class ParkingRest {
	@Autowired
	private Facade fachada;
	
	@RequestMapping("/allVehicles")
	public List<Vehicle> getAllVehicles(){
		return fachada.getVehicles(FilterVehicle.SEARCH_ALL);
	}
	
	@RequestMapping("/allCars")
	public List<Vehicle> getAllCars(){
		return fachada.getVehicles(FilterVehicle.SEARCH_CAR);
	}
	
	@RequestMapping("/allMotocycles")
	public List<Vehicle> getAllMotocycles(){
		return fachada.getVehicles(FilterVehicle.SEARCH_MOTOCYCLE);
	}
	
}
