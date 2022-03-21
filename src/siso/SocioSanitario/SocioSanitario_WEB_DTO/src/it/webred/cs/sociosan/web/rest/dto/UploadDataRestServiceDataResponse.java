package it.webred.cs.sociosan.web.rest.dto;

import it.umbriadigitale.rest.dto.BaseResponse;
import it.webred.cs.csa.ejb.dto.mobi.upload.UploadInterventiErogazioneResponseDTO;

public class UploadDataRestServiceDataResponse implements BaseResponse {

	
	public UploadInterventiErogazioneResponseDTO output;

	public UploadInterventiErogazioneResponseDTO getOutput() {
		return output;
	}

	public void setOutput(UploadInterventiErogazioneResponseDTO output) {
		this.output = output;
	}
	
}
