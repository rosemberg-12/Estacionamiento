package com.ceiba.parking.parking;

import java.util.Date;

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
		
		 DBVehicle vehicle1= new DBVehicle("UEO939", EVehicle.CAR, new Date(), null);
		 DBVehicle vehicle2= new DBVehicle("UEO939", EVehicle.MOTOCYCLE, new Date(), 500);

		 vehicleRepository.save(vehicle1);
		 vehicleRepository.save(vehicle2);
	}

}
