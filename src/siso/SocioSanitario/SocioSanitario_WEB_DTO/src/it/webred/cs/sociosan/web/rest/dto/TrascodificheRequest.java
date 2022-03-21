 package it.webred.cs.sociosan.web.rest.dto;

import it.webred.cs.csa.ejb.dto.rest.TrascodificheRequestDTO;
 
public class TrascodificheRequest extends SocioSanAuthRequest {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	TrascodificheRequestDTO input;

	public TrascodificheRequestDTO getInput() {
		return input;
	}

	public void setInput(TrascodificheRequestDTO input) {
		this.input = input;
	}

 
	
		
}