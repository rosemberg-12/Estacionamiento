package com.ceiba.parking.repository;

import java.util.List;

import com.ceiba.parking.transport.VehicleDTO;
import org.springframework.data.jpa.repository.Query;
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

	@Query("SELECT new com.ceiba.parking.transport.VehicleDTO(v.numberPlate) FROM DBVehicle v")
	List<VehicleDTO> findAllvehicles();

}
