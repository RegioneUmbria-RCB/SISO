package it.webred.cs.sociosan.web.rest.dto;


import it.webred.cs.csa.ejb.dto.mobi.FindCasiByUsernameOperatoreRequestDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataRequestDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO;

import java.math.BigDecimal;
import java.util.List;



public class FindCasiByUsernameOperatoreRequest extends SocioSanAuthRequest {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	FindCasiByUsernameOperatoreRequestDTO input;

	public FindCasiByUsernameOperatoreRequestDTO getInput() {
		return input;
	}

	public void setInput(it.webred.cs.csa.ejb.dto.mobi.FindCasiByUsernameOperatoreRequestDTO input) {
		this.input = input;
	}
	
		
}
