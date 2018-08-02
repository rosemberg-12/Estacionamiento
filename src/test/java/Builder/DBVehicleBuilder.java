package Builder;

import java.time.LocalDateTime;

import com.ceiba.parking.domain.EVehicle;
import com.ceiba.parking.entities.DBVehicle;

public final class DBVehicleBuilder {

	private static final String DPLATE= "UEO393";
	private static final EVehicle DKIND_OF_VEHICLE= EVehicle.CAR;
	private static final LocalDateTime DDATE= LocalDateTime.of(2018, 07, 1, 7, 0);
	
	private String numberPlate;
	private EVehicle kindOfVehicle;
	private LocalDateTime dateOfEntry;
	private Integer cylinderCapacity;
	
	public DBVehicleBuilder(){
		this.numberPlate=DPLATE;
		this.kindOfVehicle=DKIND_OF_VEHICLE;
		this.dateOfEntry=DDATE;
		this.cylinderCapacity=null;
	}
	
	public DBVehicleBuilder withNumberPlate(String plate){
		this.numberPlate=plate;
		return this;
	}
	public DBVehicleBuilder withKindOfVehicle(EVehicle kindOfVehicle){
		this.kindOfVehicle=kindOfVehicle;
		return this;
	}
	public DBVehicleBuilder withDateOfEntry(LocalDateTime dateOfEntry){
		this.dateOfEntry=dateOfEntry;
		return this;
	}
	public DBVehicleBuilder withCylinderCapacity(Integer cylinderCapacity){
		this.cylinderCapacity=cylinderCapacity;
		return this;
	}
	
	public DBVehicle build(){
		return new DBVehicle(numberPlate, kindOfVehicle, dateOfEntry, cylinderCapacity);
	}
}
