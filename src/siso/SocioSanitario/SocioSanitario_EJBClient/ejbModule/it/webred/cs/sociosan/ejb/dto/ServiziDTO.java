package it.webred.cs.sociosan.ejb.dto;


import it.webred.siso.ws.client.atlante.client.dto.GetServiziOspiteDTO;

import java.io.Serializable;
import java.util.List;

public class ServiziDTO implements Serializable {
	
	private String errorMessage;

	private List<GetServiziOspiteDTO> servizi;

	public List<GetServiziOspiteDTO> getServizi() {
		return servizi;
	}

	public void setServizi(List<GetServiziOspiteDTO> servizi) {
		this.servizi = servizi;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
