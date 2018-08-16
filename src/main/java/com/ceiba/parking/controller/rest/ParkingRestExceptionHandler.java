package com.ceiba.parking.controller.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ceiba.parking.exceptions.ParkingException;

@ControllerAdvice
public class ParkingRestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler({ ParkingException.class })
	public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request){
		 return new ResponseEntity<>(
		          ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
	}

}
