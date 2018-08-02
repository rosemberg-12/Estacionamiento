package Builder;

import java.time.LocalDateTime;
import java.util.List;

import com.ceiba.parking.domain.EVehicle;
import com.ceiba.parking.domain.Vehicle;
import com.ceiba.parking.entities.DBVehicle;
import com.google.common.collect.Lists;

public class MassiveCarBuilder {
	private final static String PLATE="XXX";
	private static final LocalDateTime DATE= LocalDateTime.of(2018, 7, 1, 7, 0);

	
	public static List<Vehicle> generateMultipleVehicles(int numberOfVehicles, EVehicle kind){
		List<Vehicle> vehicles= Lists.newArrayList();
		
		for(int i=1; i<=numberOfVehicles; i++){
			vehicles.add(new VehicleBuilder().withNumberPlate(PLATE+i).withKindOfVehicle(kind).
					withDateOfEntry(DATE.plusMinutes(1)).build());
		}
		return vehicles;
	}
	public static List<DBVehicle> generateMultipleVehiclesDB(int numberOfVehicles, EVehicle kind){
		List<DBVehicle> vehicles= Lists.newArrayList();
		
		for(int i=1; i<=numberOfVehicles; i++){
			vehicles.add(new DBVehicleBuilder().withNumberPlate(PLATE+(i+100)).withKindOfVehicle(kind).
					withDateOfEntry(DATE.plusMinutes(1)).build());
		}
		return vehicles;
	}
}
