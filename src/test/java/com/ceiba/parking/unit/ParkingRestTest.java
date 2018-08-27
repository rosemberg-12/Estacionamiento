package com.ceiba.parking.unit;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.ceiba.parking.domain.EVehicle;
import com.ceiba.parking.domain.FilterVehicle;
import com.ceiba.parking.domain.Vehicle;
import com.ceiba.parking.services.IParkingService;
import com.ceiba.parking.soap.client.ParkingSoapClient;
import com.ceiba.parking.wsdl.QueryTCRMResponse;
import com.ceiba.parking.wsdl.TcrmResponse;

import Builder.MassiveCarBuilder;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ParkingRestTest {

	private static final String JSON_CAR = "{\"numberPlate\":\"AAA432\",\"kindOfVehicle\":\"CAR\"}";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IParkingService controller;

	@MockBean
	private ParkingSoapClient soapClient;

	@Test
	public void allVehiclesRest_Test() throws Exception {
		List<Vehicle> vehicles = MassiveCarBuilder.generateMultipleVehicles(3, EVehicle.CAR);
		vehicles.addAll(MassiveCarBuilder.generateMultipleVehicles(3, EVehicle.MOTORCYCLE));
		when(controller.getAllVehicle(FilterVehicle.SEARCH_ALL)).thenReturn(vehicles);
		this.mockMvc.perform(get("/allVehicles")).andExpect(jsonPath("$.listOfVehicles", hasSize(6)))
				.andExpect(status().isOk());
	}

	@Test
	public void allCarsRest_Test() throws Exception {
		List<Vehicle> vehicles = MassiveCarBuilder.generateMultipleVehicles(3, EVehicle.CAR);
		when(controller.getAllVehicle(FilterVehicle.SEARCH_CAR)).thenReturn(vehicles);
		this.mockMvc.perform(get("/allCars")).andExpect(jsonPath("$.listOfVehicles", hasSize(3)))
				.andExpect(status().isOk());
	}

	@Test
	public void allMotorcycles_Test() throws Exception {
		List<Vehicle> vehicles = MassiveCarBuilder.generateMultipleVehicles(3, EVehicle.MOTORCYCLE);
		when(controller.getAllVehicle(FilterVehicle.SEARCH_MOTORCYCLE)).thenReturn(vehicles);
		this.mockMvc.perform(get("/allMotorcycles")).andExpect(jsonPath("$.listOfVehicles", hasSize(3)))
				.andExpect(status().isOk());
	}

	@Test
	public void carsVacancy_Test() throws Exception {
		when(controller.getVacancyCars()).thenReturn(5);
		this.mockMvc.perform(get("/carsVacancy")).andExpect(jsonPath("$.message", is("5"))).andExpect(status().isOk());
	}

	@Test
	public void motorcycleVacancy_Test() throws Exception {
		when(controller.getVacancyMotorcycles()).thenReturn(5);
		this.mockMvc.perform(get("/motorcycleVacancy")).andExpect(jsonPath("$.message", is("5")))
				.andExpect(status().isOk());
	}

	@Test
	public void registerVehicle_Test() throws Exception {
		doNothing().when(controller).registerVehicle(Mockito.any(Vehicle.class));
		this.mockMvc.perform(post("/registerVehicle").contentType(MediaType.APPLICATION_JSON).content(JSON_CAR))
				.andExpect(status().isOk());
	}

	@Test
	public void unregisterVehicle_Test() throws Exception {
		Vehicle vehicle = new Vehicle(1L, "AAA432", EVehicle.CAR, null, null);
		Pair<Vehicle, Long> pairToReturn = Pair.of(vehicle, 500L);
		when(controller.unRegisterVehicle(Mockito.any(Vehicle.class))).thenReturn(pairToReturn);
		this.mockMvc.perform(post("/unregisterVehicle").contentType(MediaType.APPLICATION_JSON).content(JSON_CAR))
				.andExpect(jsonPath("$.vehicleUnparked.numberPlate", is("AAA432")))
				.andExpect(jsonPath("$.costOfParking", is(500))).andExpect(status().isOk());
	}

	@Test
	public void getTCRMRest_Test() throws Exception {
		QueryTCRMResponse response = new QueryTCRMResponse();
		response.setReturn(new TcrmResponse());
		when(soapClient.getCurrentTCR()).thenReturn(response);
		this.mockMvc.perform(get("/getTCRM")).andExpect(status().isOk());
	}

}
