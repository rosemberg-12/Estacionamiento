package com.ceiba.parking.exceptions;

/**
 * Exception class used to define the possible business exceptions.
 * @author rosemberg.porras
 *
 */
public class ParkingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ParkingException(){
		
	}
	public ParkingException(String message){
		super(message);
	}
}
