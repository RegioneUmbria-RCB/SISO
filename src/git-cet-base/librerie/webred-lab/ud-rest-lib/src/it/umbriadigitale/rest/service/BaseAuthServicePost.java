package it.umbriadigitale.rest.service;

import it.umbriadigitale.rest.dto.BaseRequest;
import it.umbriadigitale.rest.dto.BaseResponse;

import org.apache.log4j.Logger;

public abstract class BaseAuthServicePost<E1 extends BaseRequest, E2 extends BaseResponse, String > extends BaseServicePost<E1, E2, String>  {
	
	public BaseAuthServicePost(E1 req, String body) {
		 super(req, body) ;
	}

	protected Logger logger = Logger.getLogger("ud-rest-lib");
	
	public abstract IAuthManager getAuthManager();

	public abstract E2 executeAuthService();

	public E2 execute() throws BaseServiceException {
		IAuthManager authManager = getAuthManager();
		if (authManager==null ) 
			throw new BaseServiceException("AuthManager non definito per i servizio ");
		if (!authManager.isAuthorizated(req))
			throw new BaseServiceException("Autenticazione non valida ");
			
		return this.executeAuthService();
	}
	



	
	



	
	
}
