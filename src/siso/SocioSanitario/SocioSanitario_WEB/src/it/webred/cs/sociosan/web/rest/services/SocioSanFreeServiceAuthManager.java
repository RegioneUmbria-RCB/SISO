package it.webred.cs.sociosan.web.rest.services;

import java.util.logging.Logger;

import it.umbriadigitale.rest.dto.BaseRequest;
import it.umbriadigitale.rest.service.IAuthManager;

public class SocioSanFreeServiceAuthManager   implements IAuthManager {

	protected static Logger logger = Logger.getLogger("carsociale.log");

	

	@Override
	public boolean isAuthorizated(BaseRequest req) {
		// TODO Auto-generated method stub
		 return true;
	}

}
