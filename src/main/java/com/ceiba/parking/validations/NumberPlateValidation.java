package com.ceiba.parking.validations;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import com.ceiba.parking.properties.ParkingProperties;

public class NumberPlateValidation implements ParkingValidation{

	private String numberPlate;
	private LocalDateTime today;
	
	public NumberPlateValidation(String plate, LocalDateTime today){
		this.numberPlate=plate;
		this.today=today;
	}
	
	@Override
	public boolean valid() {
		return !(numberPlate.startsWith("A") && (today.getDayOfWeek()==DayOfWeek.SUNDAY || today.getDayOfWeek()==DayOfWeek.MONDAY));
	}

	@Override
	public String invalidationMessage() {
		return ParkingProperties.getValue("ERROR_VALIDATION_PLATE");
	}
	
	

}