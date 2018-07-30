package com.ceiba.parking.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ceiba.parking.domain.Vehicle;
import com.ceiba.parking.entities.DBVehicle;
import com.ceiba.parking.repository.VehicleRepository;

/**
 * Controller of all behaviors of parking.
 * @author rosemberg.porras
 *
 */
@Component("ParkingController")
public class ParkingController {
	@Autowired 
	VehicleRepository repository;
	
	public List<Vehicle> getAllVehicle(){
		List<Vehicle> vehicleList=new ArrayList<>();
		List<DBVehicle> dbList=Lists.newArrayList(repository.findAll());
		if(!dbList.isEmpty()){
			vehicleList=dbList.stream().map(Vehicle::new).collect(Collectors.toList());
		}
		return vehicleList;
	}
	
}
