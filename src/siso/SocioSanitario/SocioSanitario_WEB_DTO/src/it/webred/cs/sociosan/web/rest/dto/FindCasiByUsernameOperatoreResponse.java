package it.webred.cs.sociosan.web.rest.dto;





import it.umbriadigitale.rest.dto.BaseResponse;
import it.webred.cs.csa.ejb.dto.mobi.FindCasiByUsernameOperatoreResponseDTO;

public class FindCasiByUsernameOperatoreResponse implements BaseResponse {

	FindCasiByUsernameOperatoreResponseDTO output;
	
	public FindCasiByUsernameOperatoreResponseDTO getOutput() {
		return output;
	}

	public void setOutput(FindCasiByUsernameOperatoreResponseDTO output) {
		this.output = output;
	}




}
