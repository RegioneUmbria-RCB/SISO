package it.umbriadigitale.rest.service;

import it.umbriadigitale.rest.dto.BaseRequest;
import it.umbriadigitale.rest.dto.BaseResponse;
import it.webred.utils.GenericTuples;
import it.webred.utils.GenericTuples.T2;

import org.apache.log4j.Logger;

public abstract class BaseService<E1 extends BaseRequest, E2 extends BaseResponse > {

	protected E1 req;
	public BaseService(E1 req) {
		 this.req = req;
	}


	
	protected Logger logger = Logger.getLogger("ud-rest-lib");
	
	public abstract  E2 execute() throws BaseServiceException;
	



	
	



	
	
}
