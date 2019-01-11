package it.webred.cs.sociosan.web.rest.dto;


public class StatusSegnalazioneRequest extends SocioSanAuthRequest {


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
