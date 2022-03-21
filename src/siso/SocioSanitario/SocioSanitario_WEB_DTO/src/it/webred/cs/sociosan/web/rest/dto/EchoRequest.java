package it.webred.cs.sociosan.web.rest.dto;

import it.umbriadigitale.rest.dto.BaseRequest;

public class EchoRequest implements BaseRequest {

	private String input;

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
	
}
