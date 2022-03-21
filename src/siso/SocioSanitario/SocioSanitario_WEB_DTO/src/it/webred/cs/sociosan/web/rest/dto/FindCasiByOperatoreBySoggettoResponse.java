package it.webred.cs.sociosan.web.rest.dto;





import it.umbriadigitale.rest.dto.BaseResponse;
import it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO;


public class FindCasiByOperatoreBySoggettoResponse implements BaseResponse {

	FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO output;
	
	public FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO getOutput() {
		return output;
	}

	public void setOutput(FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO output) {
		this.output = output;
	}




}
