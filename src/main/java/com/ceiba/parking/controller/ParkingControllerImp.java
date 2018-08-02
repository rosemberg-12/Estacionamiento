package com.ceiba.parking.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.ceiba.parking.domain.EVehicle;
import com.ceiba.parking.domain.FilterVehicle;
import com.ceiba.parking.domain.Vehicle;
import com.ceiba.parking.entities.DBVehicle;
import com.ceiba.parking.repository.VehicleRepository;
import com.ceiba.parking.validation.evaluator.ValidationEvaluator;
import com.ceiba.parking.validations.ExistVehicleValidation;
import com.ceiba.parking.validations.IParkingValidation;
import com.ceiba.parking.validations.MaxCapacityValidation;
import com.ceiba.parking.validations.NumberPlateValidation;
import com.ceiba.parking.validations.UniqueVehicleValidation;
import com.ceiba.parking.validations.VehicleStandarValidation;

/**
 * Controller of all behaviors of parking.
 * @author rosemberg.porras
 *
 */
@Service
public class ParkingControllerImp implements IParkingController{
	
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
				dbList =new ArrayList<>(repository.findByKindOfVehicle(EVehicle.CAR));
				break;
			case SEARCH_MOTORCYCLE:
				dbList =new ArrayList<>(repository.findByKindOfVehicle(EVehicle.MOTORCYCLE));
				break;
			case SEARCH_ALL:
				dbList =new ArrayList<>();
				for(DBVehicle e: repository.findAll()){
					dbList.add(e);
				}
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
		
		ValidationEvaluator.evaluateMultipleValidations(getRegisterVehicleValidations(vehicle));
		
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
	 * Unregister a vehicle.
	 * @param vehicle
	 * @return
	 */
	public Pair<Vehicle, Long> unRegisterVehicle(Vehicle vehicle) {
		
		ValidationEvaluator.evaluateSingleValidation(new ExistVehicleValidation(repository, vehicle));
		
		Pair<Vehicle, Long> pairToReturn=null;
		Vehicle full=new Vehicle(repository.findByNumberPlate(vehicle.getNumberPlate()).get(0));
		
		if(full.getKindOfVehicle()== EVehicle.CAR){
			pairToReturn=getCostOfCar(full);
		}
		else if(full.getKindOfVehicle()== EVehicle.MOTORCYCLE){
			pairToReturn=getCostOfMotorcycle(full);
		}
		if(pairToReturn!=null){
			repository.delete(pairToReturn.getFirst().toEntity());
		}
		return pairToReturn;
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

	
	private Pair<Vehicle, Long> getCostOfCar(Vehicle vehicle) {

		LocalDateTime start=vehicle.getDateOfEntry();
		LocalDateTime now=today();
		
		long numberOfHours = Duration.between(start, now).toHours();

		if(Duration.between(start, now).toMinutes()%60!=0){
			numberOfHours++;
		}
		
		if(numberOfHours==0){
			numberOfHours++;
		}

		long numberOfDays=numberOfHours/24;
		long extraHours=numberOfHours%24;
		
		if(extraHours>9){
			extraHours=0;
			numberOfDays++;
		}

		return Pair.of(vehicle, (extraHours*EVehicle.CAR.getPricePerHour())+(numberOfDays*EVehicle.CAR.getPricePerDay()));
	}
	
	private Pair<Vehicle, Long> getCostOfMotorcycle(Vehicle vehicle) {
		
		LocalDateTime start=vehicle.getDateOfEntry();
		LocalDateTime now=today();
		
		long numberOfHours = Duration.between(start, now).toHours();
		
		if(Duration.between(start, now).toMinutes()%60!=0){
			numberOfHours++;
		}
		
		if(numberOfHours==0){
			numberOfHours++;
		}

		long numberOfDays=numberOfHours/24;
		long extraHours=numberOfHours%24;
		
		if(extraHours>9){
			extraHours=0;
			numberOfDays++;
		}

		return Pair.of(vehicle, (vehicle.getCylinderCapacity()>500?2000:0)+
				(extraHours*EVehicle.MOTORCYCLE.getPricePerHour())+
				(numberOfDays*EVehicle.MOTORCYCLE.getPricePerDay()));
	}

	private List<IParkingValidation> getCarValidations(Vehicle car){
		List<IParkingValidation> validations=new ArrayList<>();
		validations.add(new MaxCapacityValidation(getAllVehicle(FilterVehicle.SEARCH_CAR),EVehicle.CAR));
		validations.add(new NumberPlateValidation(car.getNumberPlate(), today()));
		return validations;
	}
	
	private List<IParkingValidation> getMotorcycleValidations(Vehicle motorcycle){
		List<IParkingValidation> validations=new ArrayList<>();
		validations.add(new MaxCapacityValidation(getAllVehicle(FilterVehicle.SEARCH_MOTORCYCLE),EVehicle.MOTORCYCLE));
		validations.add(new NumberPlateValidation(motorcycle.getNumberPlate(), today()));
		return validations;
	}
	
	private List<IParkingValidation> getRegisterVehicleValidations(Vehicle vehicle){
		List<IParkingValidation> validations=new ArrayList<>();
		validations.add(new VehicleStandarValidation(vehicle));
		validations.add(new UniqueVehicleValidation(repository, vehicle));
		return validations;
	}


}
