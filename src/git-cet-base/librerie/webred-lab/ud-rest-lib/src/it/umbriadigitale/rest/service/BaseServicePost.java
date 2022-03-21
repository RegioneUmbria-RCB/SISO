package it.umbriadigitale.rest.service;

import it.umbriadigitale.rest.dto.BaseBody;
import it.umbriadigitale.rest.dto.BaseRequest;
import it.umbriadigitale.rest.dto.BaseResponse;

import org.apache.log4j.Logger;

public abstract class BaseServicePost<E1 extends BaseRequest, E2 extends BaseResponse, String > {

	public enum TipoServizioRest {
	    GET,
	    POST
	}
	
	protected E1 req;
	protected String body;
	
	public BaseServicePost(E1 req, String body) {
		 this.req = req;
		 this.body = body;
	}


	
	protected Logger logger = Logger.getLogger("ud-rest-lib");
	
	public abstract  E2 execute() throws BaseServiceException;
	
	public abstract  TipoServizioRest getTipoServizioRest() throws BaseServiceException;



	
	



	
	
}
