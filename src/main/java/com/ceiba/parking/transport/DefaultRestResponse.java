package com.ceiba.parking.transport;

/**
 * Default transport class.
 * @author rosemberg.porras
 *
 */
public class DefaultRestResponse {

	private boolean response;
	private String nameException;
	private String messageException;
	
	public DefaultRestResponse(){
		
	}
	
	protected DefaultRestResponse(boolean response, String nameException, String messageException) {
		super();
		this.response = response;
		this.nameException = nameException;
		this.messageException = messageException;
	}
	
	public boolean isResponse() {
		return response;
	}
	
	public void setResponse(boolean response) {
		this.response = response;
	}
	
	public String getNameException() {
		return nameException;
	}
	
	public void setNameException(String nameException) {
		this.nameException = nameException;
	}
	
	public String getMessageException() {
		return messageException;
	}
	
	public void setMessageException(String messageException) {
		this.messageException = messageException;
	}

}
