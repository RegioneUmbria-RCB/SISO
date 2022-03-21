package it.webred.cs.sociosan.web.rest.services;

import it.umbriadigitale.rest.service.BaseAuthService;
import it.umbriadigitale.rest.service.BaseServiceException;
import it.umbriadigitale.rest.service.IAuthManager;
import it.webred.cs.csa.ejb.dto.mobi.FindCasiByUsernameOperatoreRequestDTO;
import it.webred.cs.sociosan.ejb.client.AccessTableInterventoErogazioneSessionBeanClientRemote;
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.cs.sociosan.web.rest.dto.FindCasiByUsernameOperatoreRequest;
import it.webred.cs.sociosan.web.rest.dto.FindCasiByUsernameOperatoreResponse;
import it.webred.ct.support.validation.CeTToken;
import it.webred.ejb.utility.ClientUtility;

import java.math.BigDecimal;

import javax.naming.NamingException;


public class FindCasiByUsernameOperatoreRestService extends BaseAuthService<FindCasiByUsernameOperatoreRequest, FindCasiByUsernameOperatoreResponse> {

	public FindCasiByUsernameOperatoreRestService(
			FindCasiByUsernameOperatoreRequest req) {
		super(req);
		// TODO Auto-generated constructor stub
	}



	
	public FindCasiByUsernameOperatoreResponse executeAuthService() {
		// TODO Auto-generated method stub
		FindCasiByUsernameOperatoreResponse resp =  new FindCasiByUsernameOperatoreResponse();
		
		
		AccessTableInterventoErogazioneSessionBeanClientRemote sb=null;
		try {
			sb = (AccessTableInterventoErogazioneSessionBeanClientRemote) ClientUtility.getEjbInterface("SocioSanitario", "SocioSanitario_EJB", "AccessTableInterventoErogazioneSessionBeanClient");
		} catch (NamingException e1) {
			logger.error(e1);
		}

		

		try {
			it.webred.cs.csa.ejb.dto.mobi.FindCasiByUsernameOperatoreResponseDTO interventoErogazioneDTO = sb.findCasiByUsernameOperatore(req.getToken(), (FindCasiByUsernameOperatoreRequestDTO)req.getInput()) ;
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






