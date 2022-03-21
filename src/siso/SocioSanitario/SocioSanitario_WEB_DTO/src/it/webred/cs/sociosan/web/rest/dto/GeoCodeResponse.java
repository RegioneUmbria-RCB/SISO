package it.webred.cs.sociosan.web.rest.dto;

import it.umbriadigitale.geocode.GeocodeDTO;
import it.umbriadigitale.rest.dto.BaseResponse;

public class GeoCodeResponse implements BaseResponse {

	GeocodeDTO output;
	
	public GeocodeDTO getOutput() {
		return output;
	}

	public void setOutput(GeocodeDTO output) {
		this.output = output;
	}


}
