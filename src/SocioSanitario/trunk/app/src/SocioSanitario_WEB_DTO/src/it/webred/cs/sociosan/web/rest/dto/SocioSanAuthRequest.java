package it.webred.cs.sociosan.web.rest.dto;

import it.umbriadigitale.rest.dto.BaseRequest;
import it.webred.ct.support.validation.CeTToken;


public class SocioSanAuthRequest implements BaseRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CeTToken token;

	public CeTToken getToken() {
		return token;
	}

	public void setToken(CeTToken token) {
		this.token = token;
	}
	
	
	
}
