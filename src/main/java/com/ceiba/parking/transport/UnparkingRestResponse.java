package com.ceiba.parking.transport;

import com.ceiba.parking.domain.Vehicle;

public class UnparkingRestResponse extends DefaultRestResponse {
	private Vehicle vehicleUnparked;
	private Long costOfParking;
	
	public UnparkingRestResponse(){
		
	}
		
	public UnparkingRestResponse(Vehicle vehicleUnparked, Long costOfParking) {
		super();
		this.vehicleUnparked = vehicleUnparked;
		this.costOfParking = costOfParking;
	}
	
	public Vehicle getVehicleUnparked() {
		return vehicleUnparked;
	}
	public void setVehicleUnparked(Vehicle vehicleUnparked) {
		this.vehicleUnparked = vehicleUnparked;
	}
	public Long getCostOfParking() {
		return costOfParking;
	}
	public void setCostOfParking(Long costOfParking) {
		this.costOfParking = costOfParking;
	}
	
	
}
