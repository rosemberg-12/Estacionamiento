package com.ceiba.parking;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ceiba.parking.repository.VehicleRepository;

@SpringBootApplication
public class ParkingApplication{
	
    @Autowired
    DataSource dataSource;

    @Autowired
    VehicleRepository vehicleRepository;

	public static void main(String[] args) {
		SpringApplication.run(ParkingApplication.class, args);
	}


}
