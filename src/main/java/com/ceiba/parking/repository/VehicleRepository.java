package com.ceiba.parking.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ceiba.parking.domain.EVehicle;
import com.ceiba.parking.entities.DBVehicle;

/**
 * Repository of DBVehicle entity.
 * @author rosemberg.porras
 *
 */
public interface VehicleRepository extends CrudRepository<DBVehicle, Long>  {
	
	List<DBVehicle> findByNumberPlate(String numberPlate);
	List<DBVehicle> findByKindOfVehicle(EVehicle kindOfVehicle);
}
