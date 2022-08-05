package it.webred.cs.sociosan.web.rest.services;

import org.jboss.logging.Logger;
import it.umbriadigitale.rest.dto.BaseRequest;
import it.umbriadigitale.rest.service.IAuthManager;
import it.webred.cs.sociosan.web.rest.dto.SocioSanAuthRequest;
import it.webred.ct.support.validation.ValidationDBReader;

public class SocioSanServiceAuthManager implements IAuthManager {

	protected static Logger logger = Logger.getLogger("carsociale.log");

	@Override
	public boolean isAuthorizated(BaseRequest req) {
		SocioSanAuthRequest authreq = (SocioSanAuthRequest) req ;
		if (authreq.getToken()!=null &&  authreq.getToken().getSessionId() !=null) {
			ValidationDBReader valAuth = new ValidationDBReader();
			boolean isTokenValid = false;
			try {
				isTokenValid = valAuth.checkLogin(authreq.getToken());
			} catch (Exception e) {
				logger.error("Problemi nel controllo autenticazione per sessionid = " + authreq.getToken().getSessionId(),e);
				return false;
			}
			if (isTokenValid)
				return true;
			else
				return false;
		}
		else {
			return false;
		}
			
	}

}
