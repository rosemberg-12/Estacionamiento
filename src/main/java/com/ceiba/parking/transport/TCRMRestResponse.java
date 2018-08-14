package com.ceiba.parking.transport;

import com.ceiba.parking.wsdl.TcrmResponse;

public class TCRMRestResponse extends DefaultRestResponse {

	private TcrmResponse tcrm;
	
	public TCRMRestResponse(){
	}
	
	public TCRMRestResponse(TcrmResponse tcrm) {
		super();
		this.tcrm = tcrm;
	}

	public TcrmResponse getTcrm() {
		return tcrm;
	}

	public void setTcrm(TcrmResponse tcrm) {
		this.tcrm = tcrm;
	}
	
	
	
}
