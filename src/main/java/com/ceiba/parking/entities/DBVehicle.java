package com.ceiba.parking.entities;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ceiba.parking.domain.EVehicle;

/**
 * DB class used to represent a vehicle which is parked.
 * @author rosemberg.porras
 *
 */
@Entity
public class DBVehicle {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String numberPlate;
	private EVehicle kindOfVehicle;
	private Date dateOfEntry;
	private Integer cylinderCapacity;
	
	public DBVehicle(){
		
	}
	
	public DBVehicle(String numberPlate, EVehicle kindOfVehicle, Date dateOfEntry, Integer cylinderCapacity) {
		super();
		this.numberPlate = numberPlate;
		this.kindOfVehicle = kindOfVehicle;
		this.dateOfEntry = dateOfEntry;
		this.cylinderCapacity=cylinderCapacity;
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
	public Integer getCylinderCapacity() {
		return cylinderCapacity;
	}
	public void setCylinderCapacity(Integer cylinderCapacity) {
		this.cylinderCapacity = cylinderCapacity;
	}

}
