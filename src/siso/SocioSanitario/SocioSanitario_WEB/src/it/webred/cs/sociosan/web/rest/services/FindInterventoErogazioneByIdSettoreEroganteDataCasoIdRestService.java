package it.webred.cs.sociosan.web.rest.services;

import it.umbriadigitale.rest.service.BaseAuthService;
import it.umbriadigitale.rest.service.BaseServiceException;
import it.umbriadigitale.rest.service.IAuthManager;
import it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequestDTO;
import it.webred.cs.sociosan.ejb.client.AccessTableInterventoErogazioneSessionBeanClientRemote;
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.cs.sociosan.web.rest.dto.FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequest;
import it.webred.cs.sociosan.web.rest.dto.FindInterventoErogazioneByIdSettoreEroganteDataResponse;
import it.webred.ejb.utility.ClientUtility;

import java.util.Date;

import javax.naming.NamingException;


public class FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRestService extends BaseAuthService<FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequest, FindInterventoErogazioneByIdSettoreEroganteDataResponse> {

	public FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRestService(FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequest req) {
		super(req);
	}

	public FindInterventoErogazioneByIdSettoreEroganteDataResponse executeAuthService() {
		FindInterventoErogazioneByIdSettoreEroganteDataResponse resp =  new FindInterventoErogazioneByIdSettoreEroganteDataResponse();
		FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequestDTO input = new FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequestDTO();
		
		AccessTableInterventoErogazioneSessionBeanClientRemote sb=null;
		try {
			sb = (AccessTableInterventoErogazioneSessionBeanClientRemote) 
					ClientUtility.getEjbInterface("SocioSanitario", "SocioSanitario_EJB", "AccessTableInterventoErogazioneSessionBeanClient");
		} catch (NamingException e1) {
			logger.error(e1);
		}

		try {
			
			input= (FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequestDTO)req.getInput();
			input.setDataValiditaErogazione(new Date());
			
			it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO interventoErogazioneDTO = 
					sb.findInterventoErogazioneByIdSettoreEroganteDataCasoId(req.getToken(), input) ;
			
			
			
			
			//recupera il parametro idCaso prendendo sputno dalla ricerca per soggetto operatore
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






