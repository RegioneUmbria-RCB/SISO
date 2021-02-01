package it.webred.cs.sociosan.web.rest.services;

import it.umbriadigitale.rest.service.BaseAuthService;
import it.umbriadigitale.rest.service.BaseServiceException;
import it.umbriadigitale.rest.service.IAuthManager;
import it.webred.cs.sociosan.web.rest.dto.InvioSegnalazioneRequest;
import it.webred.cs.sociosan.web.rest.dto.InvioSegnalazioneRequestDTO;
import it.webred.cs.sociosan.web.rest.dto.InvioSegnalazioneResponse;

import org.apache.commons.lang.math.RandomUtils;


public class InvioSegnalazioneRestService extends BaseAuthService<InvioSegnalazioneRequest, InvioSegnalazioneResponse> {

	public InvioSegnalazioneRestService(InvioSegnalazioneRequest req) {
		super(req);
	}


	
	public InvioSegnalazioneResponse executeAuthService() {
		// TODO Auto-generated method stub
		InvioSegnalazioneResponse resp =  new InvioSegnalazioneResponse();
		// todo: farci qualcosa
		InvioSegnalazioneRequestDTO segnalazione = req.getInput();
		
		
		resp.setOutput(new Long(RandomUtils.nextLong()).toString());
		
		return resp;
	}


	@Override
	public IAuthManager getAuthManager() {
		return new SocioSanServiceAuthManager();
	}


	@Override
	public it.umbriadigitale.rest.service.BaseService.TipoServizioRest getTipoServizioRest()
			throws BaseServiceException {
		return TipoServizioRest.POST;
	}

	



}






