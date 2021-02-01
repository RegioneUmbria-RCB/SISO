 package it.webred.cs.sociosan.web.rest.dto;

import it.umbriadigitale.rest.dto.BaseResponse; 
import it.webred.cs.csa.ejb.dto.rest.TrascodificheResponseDTO;
 
public class TrascodificheResponse implements BaseResponse {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	TrascodificheResponseDTO output;

	public TrascodificheResponseDTO getOutput() {
		return output;
	}

	public void setOutput(TrascodificheResponseDTO output) {
		this.output = output;
	}

 
		
}