package com.ceiba.parking.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ceiba.parking.domain.EVehicle;
import com.ceiba.parking.domain.FilterVehicle;
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
	
	/**
	 * Get all the vehicles base on the filter criteria.
	 * @param filter search criteria
	 * @return List of vehicles
	 */
	public List<Vehicle> getAllVehicle(FilterVehicle filter){
		List<Vehicle> vehicleList=new ArrayList<>();
		List<DBVehicle> dbList=null;
		switch (filter){
		case SEARCH_CAR:
			dbList =Lists.newArrayList(repository.findByKindOfVehicle(EVehicle.CAR));
			break;
		case SEARCH_MOTOCYCLE:
			dbList =Lists.newArrayList(repository.findByKindOfVehicle(EVehicle.MOTOCYCLE));
			break;
		case SEARCH_ALL:
			dbList =Lists.newArrayList(repository.findAll());
			break;
		}
		if(dbList!=null && !dbList.isEmpty()){
			vehicleList=dbList.stream().map(Vehicle::new).collect(Collectors.toList());
		}
		return vehicleList;
	}
	
}
