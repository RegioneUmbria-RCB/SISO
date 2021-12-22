package it.webred.cs.sociosan.web.rest.dto;


import it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequestDTO;



public class FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequest extends SocioSanAuthRequest {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequestDTO input;

	public FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequestDTO getInput() {
		return input;
	}

	public void setInput(FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequestDTO input) {
		this.input = input;
	}
	
		
}
