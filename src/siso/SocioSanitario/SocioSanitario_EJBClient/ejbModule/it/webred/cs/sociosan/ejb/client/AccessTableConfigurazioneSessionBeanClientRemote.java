package it.webred.cs.sociosan.ejb.client;

import javax.ejb.Remote;

import it.webred.cs.csa.ejb.dto.rest.TrascodificheResponseDTO;
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;

@Remote
public interface AccessTableConfigurazioneSessionBeanClientRemote {
	
	public TrascodificheResponseDTO estraiTabellaConfigurazione(it.webred.cs.csa.ejb.dto.rest.TrascodificheRequestDTO input) throws SocioSanitarioException ;

}
