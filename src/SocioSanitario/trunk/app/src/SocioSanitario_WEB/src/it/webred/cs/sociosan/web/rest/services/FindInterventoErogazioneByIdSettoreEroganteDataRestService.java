package it.webred.cs.sociosan.web.rest.services;

import it.umbriadigitale.rest.service.BaseAuthService;
import it.umbriadigitale.rest.service.BaseServiceException;
import it.umbriadigitale.rest.service.IAuthManager;
import it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataRequestDTO;
import it.webred.cs.sociosan.ejb.client.AccessTableInterventoErogazioneSessionBeanClientRemote;
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.cs.sociosan.web.rest.dto.FindInterventoErogazioneByIdSettoreEroganteDataRequest;
import it.webred.cs.sociosan.web.rest.dto.FindInterventoErogazioneByIdSettoreEroganteDataResponse;
import it.webred.ejb.utility.ClientUtility;

import java.util.Date;

import javax.naming.NamingException;


public class FindInterventoErogazioneByIdSettoreEroganteDataRestService extends BaseAuthService<FindInterventoErogazioneByIdSettoreEroganteDataRequest, FindInterventoErogazioneByIdSettoreEroganteDataResponse> {

	public FindInterventoErogazioneByIdSettoreEroganteDataRestService(FindInterventoErogazioneByIdSettoreEroganteDataRequest req) {
		super(req);
	}

	public FindInterventoErogazioneByIdSettoreEroganteDataResponse executeAuthService() {
		FindInterventoErogazioneByIdSettoreEroganteDataResponse resp =  new FindInterventoErogazioneByIdSettoreEroganteDataResponse();
		
		AccessTableInterventoErogazioneSessionBeanClientRemote sb=null;
		try {
			sb = (AccessTableInterventoErogazioneSessionBeanClientRemote) 
					ClientUtility.getEjbInterface("SocioSanitario", "SocioSanitario_EJB", "AccessTableInterventoErogazioneSessionBeanClient");
		} catch (NamingException e1) {
			logger.error(e1);
		}

		try {
			
			FindInterventoErogazioneByIdSettoreEroganteDataRequestDTO input = new FindInterventoErogazioneByIdSettoreEroganteDataRequestDTO();
			
			input.setDataValiditaErogazione(new Date());
			
			it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO interventoErogazioneDTO = 
					sb.findInterventoErogazioneByIdSettoreEroganteData(req.getToken(), input) ;
			resp.setOutput(interventoErogazioneDTO);
			
		} catch (SocioSanitarioException e1) {
			logger.error(e1);

		}
		
		return resp;
	}


	@Override
	public IAuthManager getAuthManager() {
		return new SocioSanServiceAuthManager();
	}
	
	@Override
	public it.umbriadigitale.rest.service.BaseService.TipoServizioRest getTipoServizioRest()
			throws BaseServiceException {
		return TipoServizioRest.GET;
	}


}






