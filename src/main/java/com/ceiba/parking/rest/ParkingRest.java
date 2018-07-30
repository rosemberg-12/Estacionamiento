package com.ceiba.parking.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.parking.domain.Vehicle;
import com.ceiba.parking.facade.Facade;

@RestController
public class ParkingRest {
	@Autowired
	private Facade fachada;
	
	@RequestMapping("/allVehicles")
	public List<Vehicle> getAllVehicles(){
		return fachada.getAllVehicles();
	}
	
}
