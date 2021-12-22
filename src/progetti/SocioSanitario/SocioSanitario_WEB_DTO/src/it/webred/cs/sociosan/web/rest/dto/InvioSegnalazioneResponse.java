package it.webred.cs.sociosan.web.rest.dto;


import it.umbriadigitale.rest.dto.BaseResponse;

public class InvioSegnalazioneResponse implements BaseResponse {

	// i della segnalazioen del minore
	String output;
	
	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}


}
