package it.webred.cs.sociosan.ejb.dao;


import it.webred.cs.base.CsBaseDAO;
import it.webred.cs.sociosan.ejb.dto.ServiziDTO;
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.siso.ws.client.atlante.client.AtlanteClient;
import it.webred.siso.ws.client.atlante.client.dto.GetServiziOspiteDTO;
import it.webred.siso.ws.client.atlante.exception.AtlanteException;
import it.webred.siso.ws.client.atlante.exception.AtlanteNessunServizioException;
import it.webred.siso.ws.client.atlante.exception.AtlanteOspiteNonTrovatoException;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

/**
 * @author alessandro.feriani
 *
 */
@Named
public class AtlanteDAO extends CsBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public ServiziDTO getServizi(String codiceFisclae) throws SocioSanitarioException {
		ServiziDTO dto = new ServiziDTO();

		AtlanteClient atlante = null;
		URL urlServizio = isAnagrafeSanitariaUmbriaAbilitata() ? getAnagrafeWSDLLocationURL() : null;
		if(urlServizio!=null){
			try {
				atlante = new AtlanteClient(urlServizio);
			} catch (AtlanteException e1) {
				logger.error("Errore nella creazione del client AtlanteClient", e1);
				throw new SocioSanitarioException(e1);
				
			}
			
			CredenzialiWS login = getAtlanteLogin();
			List<GetServiziOspiteDTO> servizi = new ArrayList<GetServiziOspiteDTO>();
			servizi = new ArrayList<GetServiziOspiteDTO>();
			dto.setServizi(servizi);
			try {
				atlante.login(login.getUsername(),login.getPassword());
				servizi = atlante.getServiziOspite(codiceFisclae);
				dto.setServizi(servizi);
			}  catch ( AtlanteOspiteNonTrovatoException e) {
				// non faccionulla, restituisco semplicemente la lista vuota
				dto.setErrorMessage("Soggetto non trovato");
			}  catch ( AtlanteNessunServizioException e) {
				// non faccionulla, restituisco semplicemente la lista vuota
				dto.setErrorMessage(e.getMessage());
			} catch ( AtlanteException e) {
				dto.setErrorMessage("Soggetto non trovato");
				logger.error("Errore nella chiamata AtlanteClient "+e.getMessage(), e);
				//throw new SocioSanitarioException(e);
			}
		}else{
			logger.warn("URL AtlanteClient non configurato!");
		}
		return dto;
	}

	

}
