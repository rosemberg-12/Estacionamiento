package com.ceiba.parking.transport;

public class MessageRestResponse extends DefaultRestResponse {

	private String message;
	
	public MessageRestResponse(){
		
	}
	
	public MessageRestResponse(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
