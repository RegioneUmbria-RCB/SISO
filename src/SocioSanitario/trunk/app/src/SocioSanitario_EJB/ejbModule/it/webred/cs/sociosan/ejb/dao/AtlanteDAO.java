package it.webred.cs.sociosan.ejb.dao;


import it.webred.cs.base.CsBaseDAO;
import it.webred.cs.sociosan.ejb.dto.ServiziDTO;
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.siso.ws.client.atlante.client.AtlanteClient;
import it.webred.siso.ws.client.atlante.client.dto.GetServiziOspiteDTO;
import it.webred.siso.ws.client.atlante.exception.AtlanteException;
import it.webred.siso.ws.client.atlante.exception.AtlanteNessunServizioException;
import it.webred.siso.ws.client.atlante.exception.AtlanteOspiteNonTrovatoException;
import it.webred.utils.GenericTuples.T2;

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
		URL urlServizio = getSisoWSDLLocation();
		if(urlServizio!=null){
			try {
				atlante = new AtlanteClient(urlServizio);
			} catch (AtlanteException e1) {
				logger.error("Errore nella creazione del client AtlanteClient", e1);
				throw new SocioSanitarioException(e1);
				
			}
			
			String user = getAtlanteLogin().firstObj;
			T2<String, String> login = getAtlanteLogin();
			
			List<GetServiziOspiteDTO> servizi = new ArrayList<GetServiziOspiteDTO>();
			servizi = new ArrayList<GetServiziOspiteDTO>();
			dto.setServizi(servizi);
			try {
				atlante.login(login.firstObj,login.secondObj );
				servizi = atlante.getServiziOspite(codiceFisclae);
				dto.setServizi(servizi);
			}  catch ( AtlanteOspiteNonTrovatoException e) {
				// non faccionulla, restituisco semplicemente la lista vuota
				dto.setErrorMessage(e.getMessage());
			}  catch ( AtlanteNessunServizioException e) {
				// non faccionulla, restituisco semplicemente la lista vuota
				dto.setErrorMessage(e.getMessage());
			} catch ( AtlanteException e) {
				logger.error("Errore nella chiamata AtlanteClient", e);
				throw new SocioSanitarioException(e);
			}
		}else{
			logger.warn("URL AtlanteClient non configurato!");
		}
		return dto;
	}

	

}
