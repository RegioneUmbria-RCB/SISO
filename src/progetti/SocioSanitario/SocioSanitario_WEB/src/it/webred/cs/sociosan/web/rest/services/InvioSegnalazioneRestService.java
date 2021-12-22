package it.webred.cs.sociosan.web.rest.services;

import it.umbriadigitale.rest.service.BaseAuthService;
import it.umbriadigitale.rest.service.BaseServiceException;
import it.umbriadigitale.rest.service.IAuthManager;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.SegnalazioneDTO;
import it.webred.cs.sociosan.ejb.client.AccessTableSchedaSegrSessionBeanClientRemote;
import it.webred.cs.sociosan.web.rest.dto.InvioSegnalazioneRequest;
import it.webred.cs.sociosan.web.rest.dto.InvioSegnalazioneRequestDTO;
import it.webred.cs.sociosan.web.rest.dto.InvioSegnalazioneResponse;
import it.webred.ejb.utility.ClientUtility;

import javax.naming.NamingException;

public class InvioSegnalazioneRestService extends BaseAuthService<InvioSegnalazioneRequest, InvioSegnalazioneResponse> {

	public InvioSegnalazioneRestService(InvioSegnalazioneRequest req) {
		super(req);
	}


	public InvioSegnalazioneResponse executeAuthService() {
		InvioSegnalazioneRequestDTO segnalazione = req.getInput();
		
		// TODO SISO-938
		// a partire dai dati ricevuti, in transazione:
		//   -INSERT su CS_SS_SCHEDA_SEGR; usare l'ID restituito per fare la successiva INSERT
		//   -INSERT su CS_SCHEDE_ALTRA_PROVENIENZA (usando l'ID della precedente INSERT)
		AccessTableSchedaSegrSessionBeanClientRemote sb = null;
		try {
			sb = (AccessTableSchedaSegrSessionBeanClientRemote)
				ClientUtility.getEjbInterface("SocioSanitario", "SocioSanitario_EJB", "AccessTableSchedaSegrSessionBeanClient");
		}
		catch (NamingException e) {
			logger.error(e);
		}
		
		// TODO al momento passiamo per il BaseDTO di CarSociale
		SegnalazioneDTO segnalazioneDTO = new SegnalazioneDTO();
		segnalazioneDTO.setComuneResidenza(segnalazione.getComuneResidenza());
		segnalazioneDTO.setCognome(segnalazione.getCognome());
		segnalazioneDTO.setNome(segnalazione.getNome());
		segnalazioneDTO.setDataNascita(segnalazione.getDataNascita());
		segnalazioneDTO.setCfOperatore(segnalazione.getCfOperatore());
		segnalazioneDTO.setCognomeOperatore(segnalazione.getCognomeOperatore());
		segnalazioneDTO.setNomeOperatore(segnalazione.getNomeOperatore());
		segnalazioneDTO.setUfficio(segnalazione.getUfficio());
		
		long idSchedaSegr = sb.saveSegnalazioneSerena(req.getToken(), segnalazioneDTO);
		
		
		InvioSegnalazioneResponse resp =  new InvioSegnalazioneResponse();
		
		// la risposta consiste in output = CS_SCHEDE_ALTRA_PROVENIENZA.ID_SCHEDA_SEGR
		resp.setOutput("" + idSchedaSegr);
		
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
