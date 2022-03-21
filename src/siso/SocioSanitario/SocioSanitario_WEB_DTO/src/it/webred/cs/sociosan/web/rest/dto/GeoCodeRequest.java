package it.webred.cs.sociosan.web.rest.dto;

import it.umbriadigitale.rest.dto.BaseRequest;

public class GeoCodeRequest extends SocioSanAuthRequest {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String input;

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
	
}
