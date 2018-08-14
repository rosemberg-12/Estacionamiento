package com.ceiba.parking.soap.client;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import com.ceiba.parking.wsdl.ObjectFactory;
import com.ceiba.parking.wsdl.QueryTCRM;
import com.ceiba.parking.wsdl.QueryTCRMResponse;

public class ParkingSoapClient extends WebServiceGatewaySupport {
	
	private static final String URI="https://superfinanciera.gov.co/SuperfinancieraWebServiceTRM/TCRMServicesWebService/TCRMServicesWebService";

	@SuppressWarnings("unchecked")
	public QueryTCRMResponse getCurrentTCR() throws DatatypeConfigurationException{
		ObjectFactory obj= new ObjectFactory();
		JAXBElement<QueryTCRM> request= obj.createQueryTCRM(obj.createQueryTCRM());
		
		GregorianCalendar c = new GregorianCalendar();
		
		c.setTime(new Date());
		
		request.getValue().setTcrmQueryAssociatedDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
		Object response= getWebServiceTemplate().marshalSendAndReceive(URI,request);
		if(response instanceof JAXBElement<?> && ((JAXBElement<?>)response).getValue() instanceof QueryTCRMResponse){
			return ((JAXBElement<QueryTCRMResponse>)response).getValue();
		}
		return null;
	}

}
