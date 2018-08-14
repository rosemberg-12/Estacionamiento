package com.ceiba.parking.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.parking.domain.FilterVehicle;
import com.ceiba.parking.domain.Vehicle;
import com.ceiba.parking.facade.Facade;
import com.ceiba.parking.soap.client.ParkingSoapClient;
import com.ceiba.parking.transport.DefaultRestResponse;
import com.ceiba.parking.transport.MessageRestResponse;
import com.ceiba.parking.transport.TCRMRestResponse;
import com.ceiba.parking.transport.UnparkingRestResponse;
import com.ceiba.parking.transport.VehiclesRestResponse;
import com.ceiba.parking.wsdl.QueryTCRMResponse;
/**
 * Rest class 
 * @author rosemberg.porras
 *
 */
@RestController
public class ParkingRest {
	
	private static final Logger LOGGER = Logger.getLogger(ParkingRest.class.getName());

	
	@Autowired
	private Facade fachada;
	@Autowired
	private ParkingSoapClient soapClient;
	
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping("/allVehicles")
	public VehiclesRestResponse getAllVehicles(){
		VehiclesRestResponse response= new VehiclesRestResponse();
		try{
			response.setListOfVehicles(fachada.getVehicles(FilterVehicle.SEARCH_ALL));
			response.setResponse(true);
		}catch(Exception e){
			response.setNameException(e.getClass().getSimpleName());
			response.setMessageException(e.getMessage());
			LOGGER.log(Level.SEVERE, null, e);
		}
		return response;
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping("/allCars")
	public VehiclesRestResponse getAllCars(){
		VehiclesRestResponse response= new VehiclesRestResponse();
		try{
			response.setListOfVehicles(fachada.getVehicles(FilterVehicle.SEARCH_CAR));
			response.setResponse(true);
		}catch(Exception e){
			response.setNameException(e.getClass().getSimpleName());
			response.setMessageException(e.getMessage());
			LOGGER.log(Level.SEVERE, null, e);
		}
		return response;
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping("/allMotorcycles")
	public VehiclesRestResponse getAllMotorcycles(){
		VehiclesRestResponse response= new VehiclesRestResponse();
		try{
			response.setListOfVehicles(fachada.getVehicles(FilterVehicle.SEARCH_MOTORCYCLE));
			response.setResponse(true);
		}catch(Exception e){
			response.setNameException(e.getClass().getSimpleName());
			response.setMessageException(e.getMessage());
			LOGGER.log(Level.SEVERE, null, e);
		}
		return response;
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping("/carsVacancy")
	public MessageRestResponse getCarsVacancy(){
		MessageRestResponse response= new MessageRestResponse();
		try{
			response.setMessage(fachada.getCarVacancy().toString());
			response.setResponse(true);
		}catch(Exception e){
			response.setNameException(e.getClass().getSimpleName());
			response.setMessageException(e.getMessage());
			LOGGER.log(Level.SEVERE, null, e);
		}
		return response;
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping("/motorcycleVacancy")
	public MessageRestResponse getMotorcycleVacancy(){
		MessageRestResponse response= new MessageRestResponse();
		try{
			response.setMessage(fachada.getMotorcycleVacancy().toString());
			response.setResponse(true);
		}catch(Exception e){
			response.setNameException(e.getClass().getSimpleName());
			response.setMessageException(e.getMessage());
			LOGGER.log(Level.SEVERE, null, e);
		}
		return response;
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/registerVehicle")
	public DefaultRestResponse registerVehicle(@RequestBody Vehicle vehicle){
		DefaultRestResponse response= new DefaultRestResponse();
		try{
			fachada.registerVehicle(vehicle);
			response.setResponse(true);
		}
		catch(Exception e){
			response.setNameException(e.getClass().getSimpleName());
			response.setMessageException(e.getMessage());
			LOGGER.log(Level.SEVERE, null, e);
		}
		return response;
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/unregisterVehicle")
	public UnparkingRestResponse unregisterVehicle(@RequestBody Vehicle vehicle){
		UnparkingRestResponse response= new UnparkingRestResponse();
		try{
			Pair<Vehicle, Long>pair=fachada.unParkingVehicle(vehicle);
			response.setCostOfParking(pair.getSecond());
			response.setVehicleUnparked(pair.getFirst());
			response.setResponse(true);
		}
		catch(Exception e){
			response.setNameException(e.getClass().getSimpleName());
			response.setMessageException(e.getMessage());
			LOGGER.log(Level.SEVERE, null, e);
		}
		return response;
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping("/getTCRM")
	public TCRMRestResponse getTCRM(){
		TCRMRestResponse response= new TCRMRestResponse();
		try{
			QueryTCRMResponse current= soapClient.getCurrentTCR();
			if(current!=null){
				response.setTcrm(current.getReturn());
				response.setResponse(true);
			}
		}catch(Exception e){
			response.setNameException(e.getClass().getSimpleName());
			response.setMessageException(e.getMessage());
			LOGGER.log(Level.SEVERE, null, e);
		}
		return response;
	}

	
}
