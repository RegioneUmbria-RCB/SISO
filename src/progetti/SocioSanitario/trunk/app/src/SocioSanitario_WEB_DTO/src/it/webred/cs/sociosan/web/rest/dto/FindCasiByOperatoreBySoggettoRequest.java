package it.webred.cs.sociosan.web.rest.dto;


import it.webred.cs.csa.ejb.dto.mobi.FindCasiByOperatoreBySoggettoRequestDTO;



public class FindCasiByOperatoreBySoggettoRequest extends SocioSanAuthRequest {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	FindCasiByOperatoreBySoggettoRequestDTO input;

	public FindCasiByOperatoreBySoggettoRequestDTO getInput() {
		return input;
	}

	public void setInput(FindCasiByOperatoreBySoggettoRequestDTO input) {
		this.input = input;
	}
	
		
}
