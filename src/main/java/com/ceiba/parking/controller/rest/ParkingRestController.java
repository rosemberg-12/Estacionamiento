package com.ceiba.parking.controller.rest;

import javax.xml.datatype.DatatypeConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.parking.domain.EVehicle;
import com.ceiba.parking.domain.FilterVehicle;
import com.ceiba.parking.domain.Vehicle;
import com.ceiba.parking.services.IParkingService;
import com.ceiba.parking.soap.client.ParkingSoapClient;
import com.ceiba.parking.transport.MessageRestResponse;
import com.ceiba.parking.transport.TCRMRestResponse;
import com.ceiba.parking.transport.UnparkingRestResponse;
import com.ceiba.parking.transport.VehiclesRestResponse;
import com.ceiba.parking.wsdl.QueryTCRMResponse;
/**
 * Rest controller class. 
 * @author rosemberg.porras
 *
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ParkingRestController {
	
	@Autowired
	private IParkingService controller;

	@Autowired
	private ParkingSoapClient soapClient;
	
	@RequestMapping("/allVehicles")
	public VehiclesRestResponse getAllVehicles(){
		VehiclesRestResponse response= new VehiclesRestResponse();
		response.setListOfVehicles(controller.getAllVehicle(FilterVehicle.SEARCH_ALL));
		return response;
	}
	
	@RequestMapping("/allCars")
	public VehiclesRestResponse getAllCars(){
		VehiclesRestResponse response= new VehiclesRestResponse();
		response.setListOfVehicles(controller.getAllVehicle(FilterVehicle.SEARCH_CAR));
		return response;
	}
	
	@RequestMapping("/allMotorcycles")
	public VehiclesRestResponse getAllMotorcycles(){
		VehiclesRestResponse response= new VehiclesRestResponse();
		response.setListOfVehicles(controller.getAllVehicle(FilterVehicle.SEARCH_MOTORCYCLE));
		return response;
	}
	
	@RequestMapping("/carsVacancy")
	public MessageRestResponse getCarsVacancy(){
		MessageRestResponse response= new MessageRestResponse();
		response.setMessage(controller.getVacancyCars()+"");
		return response;
	}
	
	@RequestMapping("/motorcycleVacancy")
	public MessageRestResponse getMotorcycleVacancy(){
		MessageRestResponse response= new MessageRestResponse();
		response.setMessage(controller.getVacancyMotorcycles()+"");
		return response;
	}
	
	@PostMapping("/registerVehicle")
	public void registerVehicle(@RequestBody Vehicle vehicle){
		controller.registerVehicle(vehicle);
	}
	
	@PostMapping("/unregisterVehicle")
	public UnparkingRestResponse unregisterVehicle(@RequestBody Vehicle vehicle){
		UnparkingRestResponse response= new UnparkingRestResponse();
		Pair<Vehicle, Long>pair=controller.unRegisterVehicle(vehicle);
		response.setCostOfParking(pair.getSecond());
		response.setVehicleUnparked(pair.getFirst());

		return response;
	}
	
	@RequestMapping("/getTCRM")
	public TCRMRestResponse getTCRM() throws DatatypeConfigurationException{
		TCRMRestResponse response= new TCRMRestResponse();
		QueryTCRMResponse current= soapClient.getCurrentTCR();
		if(current!=null){
				response.setTcrm(current.getReturn());
		}

		return response;
	}

}
