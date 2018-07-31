package com.ceiba.parking.controller;

import java.time.LocalDateTime;
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
import com.ceiba.parking.validations.MaxCapacityValidation;
import com.ceiba.parking.validations.NumberPlateValidation;
import com.ceiba.parking.validations.ParkingValidation;
import com.ceiba.parking.validations.UniqueVehicleValidation;
import com.ceiba.parking.validations.ValidationEvaluator;
import com.ceiba.parking.validations.VehicleStandarValidation;

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
	 * Get all the vehicles given a filter criteria.
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
			case SEARCH_MOTORCYCLE:
				dbList =Lists.newArrayList(repository.findByKindOfVehicle(EVehicle.MOTORCYCLE));
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
	
	/**
	 * Register a vehicle no matter if is a Motorcycle or Car.
	 * @param vehicle
	 * @return
	 */
	public void registerVehicle(Vehicle vehicle){
		
		ValidationEvaluator.evaluateMultipleValidations(getInitialVehicleValidation(vehicle));
		
		if(vehicle.getKindOfVehicle()== EVehicle.CAR){
			ValidationEvaluator.evaluateMultipleValidations(getCarValidations(vehicle));
		}
		else if(vehicle.getKindOfVehicle()== EVehicle.MOTORCYCLE){
			ValidationEvaluator.evaluateMultipleValidations(getMotorcycleValidations(vehicle));
		}
		vehicle.setDateOfEntry(today());

		repository.save(vehicle.toEntity());
	}
	
	/**
	 * Return the quantity of cars stored in the parking.
	 * @return
	 */
	public int getQuantityOfCars(){
		return repository.findByKindOfVehicle(EVehicle.CAR).size();
	}
	
	/**
	 * Return the quantity of cars stored in the parking.
	 * @return
	 */
	public int getQuantityOfMotorcycles(){
		return repository.findByKindOfVehicle(EVehicle.MOTORCYCLE).size();
	}
	
	/**
	 * Return today date.
	 * @return date
	 */
	public LocalDateTime today(){
		return LocalDateTime.now();
	}


	private List<ParkingValidation> getCarValidations(Vehicle car){
		List<ParkingValidation> validations=Lists.newArrayList();
		validations.add(new MaxCapacityValidation(getAllVehicle(FilterVehicle.SEARCH_CAR),EVehicle.CAR));
		validations.add(new NumberPlateValidation(car.getNumberPlate(), today()));
		return validations;
	}
	
	private List<ParkingValidation> getMotorcycleValidations(Vehicle motorcycle){
		List<ParkingValidation> validations=Lists.newArrayList();
		validations.add(new MaxCapacityValidation(getAllVehicle(FilterVehicle.SEARCH_MOTORCYCLE),EVehicle.MOTORCYCLE));
		validations.add(new NumberPlateValidation(motorcycle.getNumberPlate(), today()));
		return validations;
	}
	
	private List<ParkingValidation> getInitialVehicleValidation(Vehicle vehicle){
		List<ParkingValidation> validations=Lists.newArrayList();
		validations.add(new VehicleStandarValidation(vehicle));
		validations.add(new UniqueVehicleValidation(repository, vehicle));
		return validations;
	}

}
