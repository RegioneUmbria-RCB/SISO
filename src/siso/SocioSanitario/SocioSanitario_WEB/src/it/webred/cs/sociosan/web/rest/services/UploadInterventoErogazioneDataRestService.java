package it.webred.cs.sociosan.web.rest.services;

import it.umbriadigitale.rest.service.BaseAuthServicePost;
import it.umbriadigitale.rest.service.BaseServiceException;
import it.umbriadigitale.rest.service.IAuthManager;
import it.webred.cs.csa.ejb.dto.mobi.upload.UploadInterventiErogazioneRequestDTO;
import it.webred.cs.csa.ejb.dto.mobi.upload.UploadInterventiErogazioneResponseDTO;
import it.webred.cs.sociosan.ejb.client.AccessTableInterventoErogazioneSessionBeanClientRemote;
import it.webred.cs.sociosan.web.rest.dto.UploadDataRestServiceDataRequest;
import it.webred.cs.sociosan.web.rest.dto.UploadDataRestServiceDataResponse;
import it.webred.ejb.utility.ClientUtility;

import javax.naming.NamingException;


public class UploadInterventoErogazioneDataRestService extends BaseAuthServicePost<UploadDataRestServiceDataRequest, UploadDataRestServiceDataResponse, String> {

	public UploadInterventoErogazioneDataRestService(UploadDataRestServiceDataRequest req, String body) {
		super(req, body);
	}

	public UploadDataRestServiceDataResponse executeAuthService() {
		UploadDataRestServiceDataResponse resp =  new UploadDataRestServiceDataResponse();
		

		AccessTableInterventoErogazioneSessionBeanClientRemote sb=null;
		try {
			sb = (AccessTableInterventoErogazioneSessionBeanClientRemote) ClientUtility.getEjbInterface("SocioSanitario", "SocioSanitario_EJB", "AccessTableInterventoErogazioneSessionBeanClient");
		} catch (NamingException e1) {
			logger.error(e1);
		}

		
		//UploadInterventiErogazioneRequestDTO input = req.getInput();
		
		UploadInterventiErogazioneResponseDTO esito = sb.uploadDatiMobile(req.getToken(), body);
		resp.setOutput(esito);
		
		sb.elaboraUploadDatiMobile(req.getToken());
		
		return resp;
	}


	@Override
	public IAuthManager getAuthManager() {
		return new SocioSanServiceAuthManager();
	}

	
	@Override
	public it.umbriadigitale.rest.service.BaseServicePost.TipoServizioRest getTipoServizioRest()
			throws BaseServiceException {
		return TipoServizioRest.POST;
	}

	
}






