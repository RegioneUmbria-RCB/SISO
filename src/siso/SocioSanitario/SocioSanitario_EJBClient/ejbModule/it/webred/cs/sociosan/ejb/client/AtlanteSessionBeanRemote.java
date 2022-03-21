package it.webred.cs.sociosan.ejb.client;

import it.webred.cs.sociosan.ejb.dto.ServiziDTO;
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;

import javax.ejb.Remote;

@Remote
public interface AtlanteSessionBeanRemote {
	public ServiziDTO getServizi(String codiceFiscale) throws SocioSanitarioException;
}
