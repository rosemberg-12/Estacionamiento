package com.ceiba.parking.domain;

import java.time.LocalDateTime;

import com.ceiba.parking.entities.DBVehicle;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Domain class used to represent a vehicle which is parked.
 * @author rosemberg.porras
 *
 */
public class Vehicle {


	private Long id;
	private String numberPlate;
	private EVehicle kindOfVehicle;
	private Integer cylinderCapacity;
	private LocalDateTime dateOfEntry;
	
	protected Vehicle(){
		
	}
	public Vehicle(Long id,String numberPlate, EVehicle kindOfVehicle, LocalDateTime dateOfEntry, Integer cylinderCapacity) {
		super();
		this.id=id;
		this.numberPlate = numberPlate;
		this.kindOfVehicle = kindOfVehicle;
		this.dateOfEntry = dateOfEntry;
		this.cylinderCapacity=cylinderCapacity;
	}
	
	public Vehicle(DBVehicle dbVehicle){
		this.numberPlate=dbVehicle.getNumberPlate();
		this.kindOfVehicle=dbVehicle.getKindOfVehicle();
		this.cylinderCapacity=dbVehicle.getCylinderCapacity();
		this.dateOfEntry=dbVehicle.getDateOfEntry();
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
	public LocalDateTime getDateOfEntry() {
		return dateOfEntry;
	}
	public void setDateOfEntry(LocalDateTime dateOfEntry) {
		this.dateOfEntry = dateOfEntry;
	}
	@JsonIgnore
	public Long getId() {
		return id;
	}
	@JsonIgnore
	public void setId(Long id) {
		this.id = id;
	}
	@JsonIgnore
	public Integer getCylinderCapacity() {
		return cylinderCapacity;
	}
	@JsonIgnore
	public void setCylinderCapacity(Integer cylinderCapacity) {
		this.cylinderCapacity = cylinderCapacity;
	}
	@JsonIgnore
	public DBVehicle toEntity() {
	    DBVehicle dbVehicle = new DBVehicle();
	    dbVehicle.setNumberPlate(getNumberPlate());
	    dbVehicle.setKindOfVehicle(getKindOfVehicle());
	    dbVehicle.setCylinderCapacity(getCylinderCapacity());
	    dbVehicle.setDateOfEntry(getDateOfEntry());
	    return dbVehicle;
	 }

}
