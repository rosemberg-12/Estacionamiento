package Builder;

import java.time.LocalDateTime;

import com.ceiba.parking.domain.EVehicle;
import com.ceiba.parking.domain.Vehicle;

public final class VehicleBuilder {
	
	private static final String PLATE= "UEO393";
	private static final EVehicle KIND_OF_VEHICLE= EVehicle.CAR;
	private static final LocalDateTime DATE= LocalDateTime.of(2018, 07, 1, 7, 0);
	
	private Long id;
	private String numberPlate;
	private EVehicle kindOfVehicle;
	private LocalDateTime dateOfEntry;
	private Integer cylinderCapacity;
	
	public VehicleBuilder(){
		this.numberPlate=PLATE;
		this.kindOfVehicle=KIND_OF_VEHICLE;
		this.dateOfEntry=DATE;
		this.cylinderCapacity=null;
	}
	
	public VehicleBuilder withId(Long id){
		this.id=id;
		return this;
	}
	public VehicleBuilder withNumberPlate(String plate){
		this.numberPlate=plate;
		return this;
	}
	public VehicleBuilder withKindOfVehicle(EVehicle kindOfVehicle){
		this.kindOfVehicle=kindOfVehicle;
		return this;
	}
	public VehicleBuilder withDateOfEntry(LocalDateTime dateOfEntry){
		this.dateOfEntry=dateOfEntry;
		return this;
	}
	public VehicleBuilder withCylinderCapacity(Integer cylinderCapacity){
		this.cylinderCapacity=cylinderCapacity;
		return this;
	}
	
	public Vehicle build(){
		return new Vehicle(id, numberPlate, kindOfVehicle, dateOfEntry, cylinderCapacity);
	}
}
