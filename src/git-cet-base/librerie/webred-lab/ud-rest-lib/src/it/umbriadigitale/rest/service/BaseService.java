package it.umbriadigitale.rest.service;

import it.umbriadigitale.rest.dto.BaseRequest;
import it.umbriadigitale.rest.dto.BaseResponse;

import org.apache.log4j.Logger;

public abstract class BaseService<E1 extends BaseRequest, E2 extends BaseResponse > {

	public enum TipoServizioRest {
	    GET,
	    POST
	}
	
	protected E1 req;
	public BaseService(E1 req) {
		 this.req = req;
	}


	
	protected Logger logger = Logger.getLogger("ud-rest-lib");
	
	public abstract  E2 execute() throws BaseServiceException;
	
	public abstract  TipoServizioRest getTipoServizioRest() throws BaseServiceException;



	
	



	
	
}
