package it.webred.cs.sociosan.ejb.client.ricercaSoggetto;

import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.siso.ws.ricerca.dto.RicercaAnagraficaParams;
import it.webred.siso.ws.ricerca.dto.RicercaAnagraficaResult;

import javax.ejb.Remote;

@Remote
public interface RicercaSoggettoSessionBeanRemote {

	public RicercaAnagraficaResult ricercaPerDatiAnagrafici(RicercaAnagraficaParams params) throws SocioSanitarioException;

	public RicercaAnagraficaResult getDettaglioPersona(RicercaAnagraficaParams params);
	
	public RicercaAnagraficaResult getComposizioneFamiliare(RicercaAnagraficaParams params);
	
}
