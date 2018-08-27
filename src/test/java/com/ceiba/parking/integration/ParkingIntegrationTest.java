package com.ceiba.parking.integration;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.util.Pair;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.parking.domain.EVehicle;
import com.ceiba.parking.domain.FilterVehicle;
import com.ceiba.parking.domain.Vehicle;
import com.ceiba.parking.entities.DBVehicle;
import com.ceiba.parking.exceptions.ParkingException;
import com.ceiba.parking.properties.ParkingProperties;
import com.ceiba.parking.repository.VehicleRepository;
import com.ceiba.parking.services.IParkingService;

import Builder.MassiveCarBuilder;
import Builder.VehicleBuilder;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkingIntegrationTest {
	
	private LocalDateTime now=LocalDateTime.of(2018,8,1,7,0);
	
    @Autowired
    private VehicleRepository repository;
    
    @SpyBean
    private IParkingService controllerImp;
    
    @Before
    public void initializeDate(){
		when(controllerImp.today()).thenReturn(now);
    }
   
    @After
    public void cleanData(){
    	repository.deleteAll();
    }
    
	@Test
	public void getVehiclesAll_Test() {

		List<DBVehicle>vehicles=MassiveCarBuilder.generateMultipleVehiclesDB(5, EVehicle.CAR);
		vehicles.addAll(MassiveCarBuilder.generateMultipleVehiclesDB(5, EVehicle.MOTORCYCLE));
		
		for(DBVehicle vehicle: vehicles){
			repository.save(vehicle);
		}
		
		List<Vehicle>allVehicles=controllerImp.getAllVehicle(FilterVehicle.SEARCH_ALL);
		
		Assert.assertTrue("All vehicles obtained",allVehicles.size()==10);
	}
	
	@Test
	public void getVehiclesCars_Test() {

		List<DBVehicle>vehicles=MassiveCarBuilder.generateMultipleVehiclesDB(6, EVehicle.CAR);
		vehicles.addAll(MassiveCarBuilder.generateMultipleVehiclesDB(4, EVehicle.MOTORCYCLE));
		
		for(DBVehicle vehicle: vehicles){
			repository.save(vehicle);
		}
		
		List<Vehicle>allVehicles=controllerImp.getAllVehicle(FilterVehicle.SEARCH_CAR);
		
		Assert.assertTrue("All cars obtained",allVehicles.size()==6);
	}
	
	@Test
	public void getVehiclesMotorcycle_Test() {

		List<DBVehicle>vehicles=MassiveCarBuilder.generateMultipleVehiclesDB(6, EVehicle.CAR);
		vehicles.addAll(MassiveCarBuilder.generateMultipleVehiclesDB(4, EVehicle.MOTORCYCLE));
		
		for(DBVehicle vehicle: vehicles){
			repository.save(vehicle);
		}
		
		List<Vehicle>allVehicles=controllerImp.getAllVehicle(FilterVehicle.SEARCH_MOTORCYCLE);
		
		Assert.assertTrue("All Motorcycles obtained",allVehicles.size()==4);
	}
		
	
	@Test
	public void registerCar_Test(){
		
		Vehicle vehicle=new VehicleBuilder().withNumberPlate("CCC000").withKindOfVehicle(EVehicle.CAR).build();
		
		controllerImp.registerVehicle(vehicle);
		
		List<DBVehicle> dbVehicle=repository.findByNumberPlate("CCC000");
		
		Assert.assertTrue(dbVehicle!=null);
		Assert.assertTrue(dbVehicle.size()>0);
		Assert.assertTrue("CCC000".equals(dbVehicle.get(0).getNumberPlate()));
		Assert.assertTrue("Car register",EVehicle.CAR==dbVehicle.get(0).getKindOfVehicle());

	}
	
	@Test
	public void registerCarRepeated_Test(){
		
		Vehicle vehicle=new VehicleBuilder().withNumberPlate("CCC000").withKindOfVehicle(EVehicle.CAR).build();
		
		controllerImp.registerVehicle(vehicle);
		
		try{
			controllerImp.registerVehicle(vehicle);
			fail("Car repeated allowed, error");
		}catch(ParkingException ex){
			Assert.assertTrue("Car repeated, not inserted",ex.getMessage().equals(ParkingProperties.getValue("ERROR_ALREADY_PARKED")));
		}
	}
	
	@Test
	public void registerCarNoVacancy_Test(){
		
		List<DBVehicle>vehicles=MassiveCarBuilder.generateMultipleVehiclesDB(Integer.parseInt(ParkingProperties.getValue("MAX_CARS")),
				EVehicle.CAR);
		
		for(DBVehicle vehicle: vehicles){
			repository.save(vehicle);
		}
		
		Vehicle vehicle=new VehicleBuilder().withNumberPlate("CCC000").withKindOfVehicle(EVehicle.CAR).build();
		
		try{
			controllerImp.registerVehicle(vehicle);
			fail("Limit exceded and inserted !!");
		}catch(ParkingException ex){
			Assert.assertTrue("Limit exceded, not inserted",ex.getMessage().equals(ParkingProperties.getValue("ERROR_NO_VACANCY_CARS")));
		}
	}
	
	@Test
	public void registerCarEmptyNumberPlate_Test(){
		
		Vehicle vehicle=new VehicleBuilder().withNumberPlate("").withKindOfVehicle(EVehicle.CAR).build();
		
		try{
			controllerImp.registerVehicle(vehicle);
			fail("Car allowed with number plate empty, error");
		}catch(ParkingException ex){
			Assert.assertTrue("Car with number plate empty, not registered",
					ex.getMessage().equals(ParkingProperties.getValue("ERROR_MISSING_PARAMETERS")));
		}
	}
	
	/**
	 * Valid the register of vehicles with plate starting with 'A' only Sunday and Monday.
	 */
	@Test
	public void registerCarSpecialLetterSunday_Test(){
		
		Vehicle vehicle=new VehicleBuilder().withNumberPlate(ParkingProperties.getValue("SPECIAL_LETTER_PLATE")+"CC000").withKindOfVehicle(EVehicle.CAR).build();
		when(controllerImp.today()).thenReturn(this.now.plusDays(4));
		try{
			controllerImp.registerVehicle(vehicle);
			fail("Car With invalid start letter, error");
		}catch(ParkingException ex){
			Assert.assertTrue("Car With invalid start letter, not inserted",ex.getMessage().equals(ParkingProperties.getValue("ERROR_VALIDATION_PLATE")));
		}
	}
	
	/**
	 * Valid the register of vehicles with plate starting with 'A' only Sunday and Monday.
	 */
	@Test
	public void registerCarSpecialLetterMonday_Test(){
		
		Vehicle vehicle=new VehicleBuilder().withNumberPlate(ParkingProperties.getValue("SPECIAL_LETTER_PLATE")+"CC000").withKindOfVehicle(EVehicle.CAR).build();
		when(controllerImp.today()).thenReturn(this.now.plusDays(5));
		try{
			controllerImp.registerVehicle(vehicle);
			fail("Car With invalid start letter, error");
		}catch(ParkingException ex){
			Assert.assertTrue("Car With invalid start letter, not inserted",ex.getMessage().equals(ParkingProperties.getValue("ERROR_VALIDATION_PLATE")));
		}
	}
	
	@Test
	public void registerMotorcycle_Test(){
		
		Vehicle vehicle=new VehicleBuilder().withNumberPlate("MMM000").withKindOfVehicle(EVehicle.MOTORCYCLE).withCylinderCapacity(500).build();
		
		controllerImp.registerVehicle(vehicle);
		
		List<DBVehicle> dbVehicle=repository.findByNumberPlate("MMM000");
		
		Assert.assertTrue(dbVehicle!=null);
		Assert.assertTrue(dbVehicle.size()>0);
		Assert.assertEquals("MMM000", (dbVehicle.get(0).getNumberPlate()));
		Assert.assertTrue("Motorcycle register",EVehicle.MOTORCYCLE==dbVehicle.get(0).getKindOfVehicle());

	}
	
	@Test
	public void registerMotorcycleRepeated_Test(){

		Vehicle vehicle=new VehicleBuilder().withNumberPlate("MMM000").withKindOfVehicle(EVehicle.MOTORCYCLE).withCylinderCapacity(500).build();
		
		controllerImp.registerVehicle(vehicle);
		
		try{
			controllerImp.registerVehicle(vehicle);
			fail("Motorcycle repeated allowed, error");
		}catch(ParkingException ex){
			Assert.assertTrue("Motorcycle repeated, not inserted",ex.getMessage().equals(ParkingProperties.getValue("ERROR_ALREADY_PARKED")));
		}
	}
	
	@Test
	public void registerMotorcycleNoVacancy_Test(){
		
		List<DBVehicle>vehicles=MassiveCarBuilder.generateMultipleVehiclesDB(Integer.parseInt(ParkingProperties.getValue("MAX_MOTORCYCLES")),
				EVehicle.MOTORCYCLE);
		
		for(DBVehicle vehicle: vehicles){
			repository.save(vehicle);
		}
		
		Vehicle vehicle=new VehicleBuilder().withNumberPlate("MMM000").withKindOfVehicle(EVehicle.MOTORCYCLE).withCylinderCapacity(500).build();
		
		try{
			controllerImp.registerVehicle(vehicle);
			fail("Limit exceded and inserted !!");
		}catch(ParkingException ex){
			Assert.assertTrue("Limit exceded, not inserted",ex.getMessage().equals(ParkingProperties.getValue("ERROR_NO_VACANCY_MOTORCYCLE")));
		}
	}
	
	@Test
	public void registerMotorcycleEmptyNumberPlate_Test(){
		
		Vehicle vehicle=new VehicleBuilder().withNumberPlate("").withKindOfVehicle(EVehicle.MOTORCYCLE).withCylinderCapacity(500).build();
		try{
			controllerImp.registerVehicle(vehicle);
			fail("Motorcycle allowed with number plate empty, error");
		}catch(ParkingException ex){
			Assert.assertTrue("Motorcycle with number plate empty, not registered",
					ex.getMessage().equals(ParkingProperties.getValue("ERROR_MISSING_PARAMETERS")));
		}
	}
	
	@Test
	public void registerMotorcycleWithoutCylinderCapacity_Test(){
		
		Vehicle vehicle=new VehicleBuilder().withNumberPlate("MMM000").withKindOfVehicle(EVehicle.MOTORCYCLE).build();
		try{
			controllerImp.registerVehicle(vehicle);
			fail("Motorcycle allowed without Cylinder capacity, error");
		}catch(ParkingException ex){
			Assert.assertTrue("Motorcycle without Cylinder capacity, not registered",
					ex.getMessage().equals(ParkingProperties.getValue("ERROR_MISSING_PARAMETERS")));
		}
	}
	
	
	/**
	 * Valid the register of vehicles with plate starting with 'A' only Sunday and Monday.
	 */
	@Test
	public void registerMotorcycleSpecialLetterSunday_Test(){
		
		Vehicle vehicle=new VehicleBuilder().withNumberPlate(ParkingProperties.getValue("SPECIAL_LETTER_PLATE")+"MM000").withKindOfVehicle(EVehicle.MOTORCYCLE).withCylinderCapacity(500).build();
		when(controllerImp.today()).thenReturn(this.now.plusDays(4));
		try{
			controllerImp.registerVehicle(vehicle);
			fail("Motorcycle With invalid start letter, error");
		}catch(ParkingException ex){
			Assert.assertTrue("Motorcycle With invalid start letter, not inserted",ex.getMessage().equals(ParkingProperties.getValue("ERROR_VALIDATION_PLATE")));
		}
	}
	
	/**
	 * Valid the register of vehicles with plate starting with 'A' only Sunday and Monday.
	 */
	@Test
	public void registerMotorcycleSpecialLetterMonday_Test(){
		
		Vehicle vehicle=new VehicleBuilder().withNumberPlate(ParkingProperties.getValue("SPECIAL_LETTER_PLATE")+"MM000").withKindOfVehicle(EVehicle.MOTORCYCLE).withCylinderCapacity(500).build();
		when(controllerImp.today()).thenReturn(this.now.plusDays(5));
		try{
			controllerImp.registerVehicle(vehicle);
			fail("Motorcycle With invalid start letter, error");
		}catch(ParkingException ex){
			Assert.assertTrue("Motorcycle With invalid start letter, not inserted",ex.getMessage().equals(ParkingProperties.getValue("ERROR_VALIDATION_PLATE")));
		}
	}
	
	
	@Test
	public void getCarVacancy_Test() {

		List<DBVehicle>vehicles=MassiveCarBuilder.generateMultipleVehiclesDB(6, EVehicle.CAR);
		vehicles.addAll(MassiveCarBuilder.generateMultipleVehiclesDB(4, EVehicle.MOTORCYCLE));
		
		for(DBVehicle vehicle: vehicles){
			repository.save(vehicle);
		}
		
		Integer vacancy=controllerImp.getVacancyCars();
		
		Assert.assertTrue("True vacancy for cars",(Integer.parseInt(ParkingProperties.getValue("MAX_CARS"))-6)==vacancy);
	}
	
	@Test
	public void getMotorcycleVacancy_Test() {

		List<DBVehicle>vehicles=MassiveCarBuilder.generateMultipleVehiclesDB(6, EVehicle.CAR);
		vehicles.addAll(MassiveCarBuilder.generateMultipleVehiclesDB(4, EVehicle.MOTORCYCLE));
		
		for(DBVehicle vehicle: vehicles){
			repository.save(vehicle);
		}
		
		Integer vacancy=controllerImp.getVacancyMotorcycles();
		
		Assert.assertTrue("True vacancy for Motorcycles",(Integer.parseInt(ParkingProperties.getValue("MAX_MOTORCYCLES"))-4)==vacancy);
	}
	
	@Test
	public void unparkingCarOneHour_Test(){
		
		Vehicle vehicle=new VehicleBuilder().withNumberPlate("CCC000").withKindOfVehicle(EVehicle.CAR).build();
		when(controllerImp.today()).thenReturn(now);
		controllerImp.registerVehicle(vehicle);
		when(controllerImp.today()).thenReturn(now.plusMinutes(50));
		Pair<Vehicle,Long> output=controllerImp.unRegisterVehicle(vehicle);
		Assert.assertTrue("Price according per 1 hour",output.getSecond().equals(1000L));
	}
	
	@Test
	public void unparkingCarOneDayFull_Test(){
		
		Vehicle vehicle=new VehicleBuilder().withNumberPlate("CCC000").withKindOfVehicle(EVehicle.CAR).build();
		when(controllerImp.today()).thenReturn(now);
		controllerImp.registerVehicle(vehicle);
		when(controllerImp.today()).thenReturn(now.plusDays(1));
		Pair<Vehicle,Long> output=controllerImp.unRegisterVehicle(vehicle);
		Assert.assertTrue("Price according per 1 Day",output.getSecond().equals(8000L));
	}
	
	@Test
	public void unparkingCarOneDayIncomplete_Test(){
		
		Vehicle vehicle=new VehicleBuilder().withNumberPlate("CCC000").withKindOfVehicle(EVehicle.CAR).build();
		when(controllerImp.today()).thenReturn(now);
		controllerImp.registerVehicle(vehicle);
		when(controllerImp.today()).thenReturn(now.plusHours(11));
		Pair<Vehicle,Long> output=controllerImp.unRegisterVehicle(vehicle);
		Assert.assertTrue("Price according per 1 Day",output.getSecond().equals(8000L));
	}
	
	
	@Test
	public void unparkingCarNotExistPlate_Test(){
		
		Vehicle vehicle=new VehicleBuilder().withNumberPlate("CCC000").withKindOfVehicle(EVehicle.CAR).build();
		when(controllerImp.today()).thenReturn(now);
		controllerImp.registerVehicle(vehicle);
		when(controllerImp.today()).thenReturn(now.plusMinutes(50));
		vehicle.setNumberPlate("XXX000");
		try{
			controllerImp.unRegisterVehicle(vehicle);
			fail("Car doesn't exist, error");
		}catch(ParkingException ex){
			Assert.assertTrue("Car doesn't exist, nothing happen",ex.getMessage().equals(ParkingProperties.getValue("ERROR_DOESNT_EXIST")));
		}
	}
	
	@Test
	public void unparkingMotorcycleOneHour_MinCC_Test(){
		
		Vehicle vehicle=new VehicleBuilder().withNumberPlate("MMM000").withKindOfVehicle(EVehicle.MOTORCYCLE).withCylinderCapacity(400).build();
		when(controllerImp.today()).thenReturn(now);
		controllerImp.registerVehicle(vehicle);
		when(controllerImp.today()).thenReturn(now.plusMinutes(50));
		Pair<Vehicle,Long> output=controllerImp.unRegisterVehicle(vehicle);
		Assert.assertTrue("Price according per 1 hour short CC",output.getSecond().equals(500L));
	}
	
	@Test
	public void unparkingMotorcycleOneHour_MaxCC_Test(){
		
		Vehicle vehicle=new VehicleBuilder().withNumberPlate("MMM000").withKindOfVehicle(EVehicle.MOTORCYCLE).withCylinderCapacity(600).build();
		when(controllerImp.today()).thenReturn(now);
		controllerImp.registerVehicle(vehicle);
		when(controllerImp.today()).thenReturn(now.plusMinutes(50));
		Pair<Vehicle,Long> output=controllerImp.unRegisterVehicle(vehicle);
		Assert.assertTrue("Price according per 1 hour and big CC",output.getSecond().equals(2500L));
	}
	
	@Test
	public void unparkingMotorcycleOneDayFull_MinCC_Test(){
		
		Vehicle vehicle=new VehicleBuilder().withNumberPlate("MMM000").withKindOfVehicle(EVehicle.MOTORCYCLE).withCylinderCapacity(400).build();
		when(controllerImp.today()).thenReturn(now);
		controllerImp.registerVehicle(vehicle);
		when(controllerImp.today()).thenReturn(now.plusDays(1));
		Pair<Vehicle,Long> output=controllerImp.unRegisterVehicle(vehicle);
		Assert.assertTrue("Price according per 1 Day",output.getSecond().equals(4000L));
	}
	
	@Test
	public void unparkingMotorcycleOneDayFull_MaxCC_Test(){
		
		Vehicle vehicle=new VehicleBuilder().withNumberPlate("MMM000").withKindOfVehicle(EVehicle.MOTORCYCLE).withCylinderCapacity(600).build();
		when(controllerImp.today()).thenReturn(now);
		controllerImp.registerVehicle(vehicle);
		when(controllerImp.today()).thenReturn(now.plusDays(1));
		Pair<Vehicle,Long> output=controllerImp.unRegisterVehicle(vehicle);
		Assert.assertTrue("Price according per 1 Day",output.getSecond().equals(6000L));
	}
	
	@Test
	public void unparkingMotorcycleOneDayIncomplete_MinCC_Test(){
		
		Vehicle vehicle=new VehicleBuilder().withNumberPlate("MMM000").withKindOfVehicle(EVehicle.MOTORCYCLE).withCylinderCapacity(400).build();
		when(controllerImp.today()).thenReturn(now);
		controllerImp.registerVehicle(vehicle);
		when(controllerImp.today()).thenReturn(now.plusHours(11));
		Pair<Vehicle,Long> output=controllerImp.unRegisterVehicle(vehicle);
		Assert.assertTrue("Price according per 1 Day",output.getSecond().equals(4000L));
	}
	
	@Test
	public void unparkingMotorcycleOneDayIncomplete_MaxCC_Test(){
		
		Vehicle vehicle=new VehicleBuilder().withNumberPlate("MMM000").withKindOfVehicle(EVehicle.MOTORCYCLE).withCylinderCapacity(600).build();
		when(controllerImp.today()).thenReturn(now);
		controllerImp.registerVehicle(vehicle);
		when(controllerImp.today()).thenReturn(now.plusHours(11));
		Pair<Vehicle,Long> output=controllerImp.unRegisterVehicle(vehicle);
		Assert.assertTrue("Price according per 1 Day",output.getSecond().equals(6000L));
	}
	
	@Test
	public void unparkingMotorcycleNotExistPlate_Test(){
		
		Vehicle vehicle=new VehicleBuilder().withNumberPlate("MMM000").withKindOfVehicle(EVehicle.MOTORCYCLE).withCylinderCapacity(400).build();
		when(controllerImp.today()).thenReturn(now);
		controllerImp.registerVehicle(vehicle);
		when(controllerImp.today()).thenReturn(now.plusMinutes(50));
		vehicle.setNumberPlate("XXX000");
		try{
			controllerImp.unRegisterVehicle(vehicle);
			fail("Motorcycle doesn't exist, error");
		}catch(ParkingException ex){
			Assert.assertTrue("Motorcycle doesn't exist, nothing happen",ex.getMessage().equals(ParkingProperties.getValue("ERROR_DOESNT_EXIST")));
		}
	}

}
