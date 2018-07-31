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

		 
		 DBVehicle vehicle6= new DBVehicle("UEO93A", EVehicle.MOTORCYCLE, LocalDateTime.now(), 100);
		 DBVehicle vehicle7= new DBVehicle("UEO93B", EVehicle.MOTORCYCLE, LocalDateTime.now(), 150);



		 vehicleRepository.save(vehicle1);
		 vehicleRepository.save(vehicle2);

		 vehicleRepository.save(vehicle6);
		 vehicleRepository.save(vehicle7);

		 
	}

}
