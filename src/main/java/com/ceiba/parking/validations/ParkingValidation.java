package com.ceiba.parking.validations;

/**
 * Interface which define the business validations.
 * @author rosemberg.porras
 *
 */
public interface ParkingValidation {

	/**
	 * Method which define if the validation is okey or not.
	 * @return
	 */
	public boolean valid();
	
	/**
	 * Message which is necessary to show in the case that the validation is invalid.
	 * @return
	 */
	public String invalidationMessage();
	
}
