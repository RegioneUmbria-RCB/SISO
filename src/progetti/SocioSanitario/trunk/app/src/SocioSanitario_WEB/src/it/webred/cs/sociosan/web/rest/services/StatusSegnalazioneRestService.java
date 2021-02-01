package it.webred.cs.sociosan.web.rest.services;

import it.umbriadigitale.rest.service.BaseAuthService;
import it.umbriadigitale.rest.service.BaseService;
import it.umbriadigitale.rest.service.BaseServiceException;
import it.umbriadigitale.rest.service.IAuthManager;
import it.webred.cs.sociosan.web.rest.dto.StatusSegnalazioneRequest;
import it.webred.cs.sociosan.web.rest.dto.StatusSegnalazioneResponse;




public class StatusSegnalazioneRestService extends BaseAuthService<StatusSegnalazioneRequest, StatusSegnalazioneResponse> {




	public StatusSegnalazioneRestService(StatusSegnalazioneRequest req) {
		super(req);
	}


	@Override
	public TipoServizioRest getTipoServizioRest() throws BaseServiceException {
		return TipoServizioRest.GET;
	}


	@Override
	public IAuthManager getAuthManager() {
		return new SocioSanServiceAuthManager();
	}

	@Override
	public StatusSegnalazioneResponse executeAuthService() {
		// TODO Auto-generated method stub
		StatusSegnalazioneResponse resp =  new StatusSegnalazioneResponse();
		resp.setOutput("NOT YET IMPLEMENTED");
		return resp;
		
	}
	

}






