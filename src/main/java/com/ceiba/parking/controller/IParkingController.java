package com.ceiba.parking.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.util.Pair;

import com.ceiba.parking.domain.FilterVehicle;
import com.ceiba.parking.domain.Vehicle;

public interface IParkingController {

	public List<Vehicle> getAllVehicle(FilterVehicle filter);
	public void registerVehicle(Vehicle vehicle);
	public Pair<Vehicle, Long> unRegisterVehicle(Vehicle vehicle);
	public int getQuantityOfCars();
	public int getQuantityOfMotorcycles();
	public LocalDateTime today();
	
}
