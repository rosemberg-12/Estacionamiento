package com.ceiba.parking.soap.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class ParkingSoapClientConfiguration {
	
	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package in the <generatePackage> specified in
		// pom.xml
		marshaller.setContextPath("com.ceiba.parking.wsdl");
		return marshaller;
	}

	@Bean
	public ParkingSoapClient soapClient(Jaxb2Marshaller marshaller) {
		ParkingSoapClient client = new ParkingSoapClient();
		client.setDefaultUri("http://superfinanciera.gov.co:8080/SuperfinancieraWebServiceTRM");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}

}
