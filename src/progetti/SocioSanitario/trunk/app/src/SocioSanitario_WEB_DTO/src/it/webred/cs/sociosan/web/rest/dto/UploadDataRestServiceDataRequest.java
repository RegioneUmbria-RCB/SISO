package it.webred.cs.sociosan.web.rest.dto;

import it.webred.cs.csa.ejb.dto.mobi.upload.UploadInterventiErogazioneRequestDTO;

public class UploadDataRestServiceDataRequest extends SocioSanAuthRequest {

	private static final long serialVersionUID = 1L;
	
	private UploadInterventiErogazioneRequestDTO input;

	public UploadInterventiErogazioneRequestDTO getInput() {
		return input;
	}

	public void setInput(UploadInterventiErogazioneRequestDTO input) {
		this.input = input;
	}
}
