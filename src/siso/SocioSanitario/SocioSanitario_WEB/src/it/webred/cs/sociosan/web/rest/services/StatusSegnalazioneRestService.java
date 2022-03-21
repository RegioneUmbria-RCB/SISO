package it.webred.cs.sociosan.web.rest.services;

import javax.naming.NamingException;

import org.apache.commons.lang.StringUtils;

import it.umbriadigitale.rest.service.BaseAuthService;
import it.umbriadigitale.rest.service.BaseServiceException;
import it.umbriadigitale.rest.service.IAuthManager;
import it.webred.cs.sociosan.ejb.client.AccessTableSchedaSegrSessionBeanClientRemote;
import it.webred.cs.sociosan.ejb.dto.SegnalazioneSerenaDTO;
import it.webred.cs.sociosan.web.rest.dto.StatusSegnalazioneRequest;
import it.webred.cs.sociosan.web.rest.dto.StatusSegnalazioneResponse;
import it.webred.ejb.utility.ClientUtility;

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
		
		Long idMinoreSISO = Long.parseLong(req.getInput());
		
		// TODO SISO-938
		// a partire da idMinoreSISO (ricevuto dalla chiamata):
		//   -leggere CS_SS_SCHEDA_SEGR su ID = idMinoreSISO
		//   -comporre lo stato dai campi NOTA_STATO, STATO, FLG_ESISTENTE
		AccessTableSchedaSegrSessionBeanClientRemote sb = null;
		try {
			sb = (AccessTableSchedaSegrSessionBeanClientRemote)
				ClientUtility.getEjbInterface("SocioSanitario", "SocioSanitario_EJB", "AccessTableSchedaSegrSessionBeanClient");
		}
		catch (NamingException e) {
			logger.error(e);
		}
		
		SegnalazioneSerenaDTO scheda = sb.getStatusSegnalazioneSerena(req.getToken(), idMinoreSISO);

		// TODO decidere risposte di base
		String status = "";
		if (scheda == null) {
			status = "Sconosciuto al S.I.SO.";
		}
		else {
			String stato = scheda.getStato();
			
			status = StringUtils.isBlank(stato)
				? "RICEVUTA"	// verosimilmente succede quando STATO è ancora NULL
				: stato.equals("RESPINTA")	// se STATO = 'RESPINTA', si aggiungono le Note (stessa logica di pnlListaSchedeSegr)
					? "Respinta: " + scheda.getNotaStato()
					: stato;
		}
		
		
		StatusSegnalazioneResponse resp =  new StatusSegnalazioneResponse();
		
		resp.setOutput(status);
		
		return resp;
	}
}
