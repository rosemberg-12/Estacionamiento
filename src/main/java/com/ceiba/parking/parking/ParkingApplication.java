package com.ceiba.parking.parking;

import java.time.LocalDateTime;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.ceiba.parking.domain.EVehicle;
import com.ceiba.parking.entities.DBVehicle;
import com.ceiba.parking.repository.VehicleRepository;

@SpringBootApplication(scanBasePackages = {"com.ceiba.parking"})
@ComponentScan({"com.ceiba.parking"})
@EnableJpaRepositories("com.ceiba.parking.repository")
@EntityScan("com.ceiba.parking.entities")
@ServletComponentScan({"com.ceiba.parking.rest"})
	
public class ParkingApplication implements CommandLineRunner {
	
    @Autowired
    DataSource dataSource;

    @Autowired
    VehicleRepository vehicleRepository;

	public static void main(String[] args) {
		SpringApplication.run(ParkingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		 DBVehicle vehicle1= new DBVehicle("UEO930", EVehicle.CAR, LocalDateTime.now(), null);
		 DBVehicle vehicle2= new DBVehicle("UEO931", EVehicle.CAR, LocalDateTime.now(), null);
		 DBVehicle vehicle3= new DBVehicle("UEO932", EVehicle.CAR, LocalDateTime.now(), null);
		 DBVehicle vehicle4= new DBVehicle("UEO933", EVehicle.CAR, LocalDateTime.now(), null);
		 DBVehicle vehicle5= new DBVehicle("UEO934", EVehicle.CAR, LocalDateTime.now(), null);
		 
		 DBVehicle vehicle6= new DBVehicle("UEO93A", EVehicle.MOTORCYCLE, LocalDateTime.now(), 100);
		 DBVehicle vehicle7= new DBVehicle("UEO93B", EVehicle.MOTORCYCLE, LocalDateTime.now(), 150);
		 DBVehicle vehicle8= new DBVehicle("UEO93C", EVehicle.MOTORCYCLE, LocalDateTime.now(), 300);
		 DBVehicle vehicle9= new DBVehicle("UEO93D", EVehicle.MOTORCYCLE, LocalDateTime.now(), 500);


		 vehicleRepository.save(vehicle1);
		 vehicleRepository.save(vehicle2);
		 vehicleRepository.save(vehicle3);
		 vehicleRepository.save(vehicle4);
		 vehicleRepository.save(vehicle5);
		 vehicleRepository.save(vehicle6);
		 vehicleRepository.save(vehicle7);
		 vehicleRepository.save(vehicle8);
		 vehicleRepository.save(vehicle9);
		 
	}

}
