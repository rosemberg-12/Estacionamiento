package com.ceiba.parking.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Domain class used to represent a vehicle which is parked.
 * @author rosemberg.porras
 *
 */
public class Vehicle {

	private Long id;
	private String numberPlate;
	private EVehicle kindOfVehicle;
	private Date dateOfEntry;
	
	protected Vehicle(){
		
	}
	public Vehicle(Long id,String numberPlate, EVehicle kindOfVehicle, Date dateOfEntry) {
		super();
		this.id=id;
		this.numberPlate = numberPlate;
		this.kindOfVehicle = kindOfVehicle;
		this.dateOfEntry = dateOfEntry;
	}
	public String getNumberPlate() {
		return numberPlate;
	}
	public void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate;
	}
	public EVehicle getKindOfVehicle() {
		return kindOfVehicle;
	}
	public void setKindOfVehicle(EVehicle kindOfVehicle) {
		this.kindOfVehicle = kindOfVehicle;
	}
	public Date getDateOfEntry() {
		return dateOfEntry;
	}
	public void setDateOfEntry(Date dateOfEntry) {
		this.dateOfEntry = dateOfEntry;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

}
