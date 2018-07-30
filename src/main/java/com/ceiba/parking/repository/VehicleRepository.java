package com.ceiba.parking.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ceiba.parking.entities.DBVehicle;

public interface VehicleRepository extends CrudRepository<DBVehicle, Long>  {
	
	List<DBVehicle> findByNumberPlate(String numberPlate);
}
