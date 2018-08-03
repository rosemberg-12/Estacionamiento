package com.ceiba.parking.restTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.ceiba.parking.domain.EVehicle;
import com.ceiba.parking.domain.FilterVehicle;
import com.ceiba.parking.domain.Vehicle;
import com.ceiba.parking.facade.Facade;

import Builder.MassiveCarBuilder;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ParkingRestTest {

	 @Autowired
	    private MockMvc mockMvc;
	 
	 @MockBean
	    private Facade facade;

	    @Test
	    public void allVehiclesRest_Test() throws Exception {
	    	List<Vehicle> vehicles=MassiveCarBuilder.generateMultipleVehicles(3, EVehicle.CAR);
	    	vehicles.addAll(MassiveCarBuilder.generateMultipleVehicles(3, EVehicle.MOTORCYCLE));
	    	when(facade.getVehicles(FilterVehicle.SEARCH_ALL)).thenReturn(vehicles);
	    	this.mockMvc.perform(get("/allVehicles")).andExpect(status().isOk());
	    }
	    
	    @Test
	    public void allCarsRest_Test() throws Exception {
	    	List<Vehicle> vehicles=MassiveCarBuilder.generateMultipleVehicles(3, EVehicle.CAR);
	    	vehicles.addAll(MassiveCarBuilder.generateMultipleVehicles(3, EVehicle.MOTORCYCLE));
	    	when(facade.getVehicles(FilterVehicle.SEARCH_CAR)).thenReturn(vehicles);
	    	this.mockMvc.perform(get("/allCars"))
//	    	.andExpect(jsonPath("$.listOfVehicles",))
	    	.andExpect(status().isOk());
	    }
	
}
