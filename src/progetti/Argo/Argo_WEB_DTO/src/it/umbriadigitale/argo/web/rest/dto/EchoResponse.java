package it.umbriadigitale.argo.web.rest.dto;

import it.umbriadigitale.rest.dto.BaseResponse;

public class EchoResponse implements BaseResponse {

	String output;
	
	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}


}
