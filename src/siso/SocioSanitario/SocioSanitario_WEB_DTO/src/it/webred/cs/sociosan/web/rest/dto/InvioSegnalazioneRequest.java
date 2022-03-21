package it.webred.cs.sociosan.web.rest.dto;


public class InvioSegnalazioneRequest extends SocioSanAuthRequest {

	private static final long serialVersionUID = 1L;
	private InvioSegnalazioneRequestDTO input;

	public InvioSegnalazioneRequestDTO getInput() {
		return input;
	}

	
	public void setInput(InvioSegnalazioneRequestDTO input) {
		this.input = input;
	}
	
}
