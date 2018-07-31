package com.ceiba.parking.validations;

import java.util.List;

import com.ceiba.parking.exceptions.ParkingException;

public final class ValidationEvaluator {
	
	private ValidationEvaluator(){
	}
	
	public static void evaluateSingleValidation(ParkingValidation validation){
		if(!validation.valid()){
			throw new ParkingException(validation.invalidationMessage());
		}
	}
	
	public static void evaluateMultipleValidations(List<ParkingValidation> validations){
		for(ParkingValidation validation:validations){
			evaluateSingleValidation(validation);
		}
	}
	
}
