package it.webred.cs.sociosan.web.rest.services;

import it.umbriadigitale.rest.service.BaseAuthService;
import it.umbriadigitale.rest.service.BaseServiceException;
import it.umbriadigitale.rest.service.IAuthManager;
import it.webred.cs.csa.ejb.dto.mobi.FindCasiByOperatoreBySoggettoRequestDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindCasiByOperatoreBySoggettoResponseDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindCasiByUsernameOperatoreRequestDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO;
import it.webred.cs.sociosan.ejb.client.AccessTableInterventoErogazioneSessionBeanClientRemote;
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.cs.sociosan.web.rest.dto.FindCasiByOperatoreBySoggettoRequest;
import it.webred.cs.sociosan.web.rest.dto.FindCasiByOperatoreBySoggettoResponse;
import it.webred.cs.sociosan.web.rest.dto.FindCasiByUsernameOperatoreRequest;
import it.webred.cs.sociosan.web.rest.dto.FindCasiByUsernameOperatoreResponse;
import it.webred.ct.support.validation.CeTToken;
import it.webred.ejb.utility.ClientUtility;

import java.math.BigDecimal;

import javax.naming.NamingException;


public class FindCasiByOperatoreBySoggettoRestService extends BaseAuthService<FindCasiByOperatoreBySoggettoRequest, FindCasiByOperatoreBySoggettoResponse> {

	public FindCasiByOperatoreBySoggettoRestService(
			FindCasiByOperatoreBySoggettoRequest req) {
		super(req);
		// TODO Auto-generated constructor stub
	}



	
	public FindCasiByOperatoreBySoggettoResponse executeAuthService() {
		// TODO Auto-generated method stub
		FindCasiByOperatoreBySoggettoResponse resp =  new FindCasiByOperatoreBySoggettoResponse();
		
		
		AccessTableInterventoErogazioneSessionBeanClientRemote sb=null;
		try {
			sb = (AccessTableInterventoErogazioneSessionBeanClientRemote) ClientUtility.getEjbInterface("SocioSanitario", "SocioSanitario_EJB", "AccessTableInterventoErogazioneSessionBeanClient");
		} catch (NamingException e1) {
			logger.error(e1);
		}

		

		try {
			it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataResponseDTO interventoErogazioneDTO = sb.findCasiByOperatoreBySoggetto(req.getToken(), (FindCasiByOperatoreBySoggettoRequestDTO)req.getInput()) ;
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






