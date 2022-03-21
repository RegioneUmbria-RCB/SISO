package it.umbriadigitale.rest.service;

import it.umbriadigitale.rest.dto.BaseRequest;
import it.umbriadigitale.rest.dto.BaseResponse;

import org.apache.log4j.Logger;

public abstract class BaseAuthService<E1 extends BaseRequest, E2 extends BaseResponse > extends BaseService<E1, E2>  {
	
	public BaseAuthService(E1 req) {
		 super(req) ;
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
