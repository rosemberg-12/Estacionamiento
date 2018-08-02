package com.ceiba.parking.validation.evaluator;

import java.util.List;

import com.ceiba.parking.exceptions.ParkingException;
import com.ceiba.parking.validations.IParkingValidation;

public final class ValidationEvaluator {
	
	private ValidationEvaluator(){
	}
	
	public static void evaluateSingleValidation(IParkingValidation validation){
		if(!validation.valid()){
			throw new ParkingException(validation.invalidationMessage());
		}
	}
	
	public static void evaluateMultipleValidations(List<IParkingValidation> validations){
		for(IParkingValidation validation:validations){
			evaluateSingleValidation(validation);
		}
	}
	
}
