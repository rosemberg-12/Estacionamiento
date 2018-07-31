package com.ceiba.parking.transport;

import java.util.List;

import com.ceiba.parking.domain.Vehicle;

public class VehiclesRestResponse extends DefaultRestResponse {

	private List<Vehicle> listOfVehicles;
	
	public VehiclesRestResponse(){
	}
	
	public VehiclesRestResponse(List<Vehicle> listOfVehicles) {
		super();
		this.listOfVehicles = listOfVehicles;
	}

	public List<Vehicle> getListOfVehicles() {
		return listOfVehicles;
	}

	public void setListOfVehicles(List<Vehicle> listOfVehicles) {
		this.listOfVehicles = listOfVehicles;
	}
	
	
	
}
