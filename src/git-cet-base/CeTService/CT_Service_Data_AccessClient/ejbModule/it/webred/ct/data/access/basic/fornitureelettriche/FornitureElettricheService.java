package it.webred.ct.data.access.basic.fornitureelettriche;

import it.webred.ct.data.access.basic.fornitureelettriche.dto.FornituraElettricaDTO;
import it.webred.ct.data.model.fornituraelettrica.SitEnelUtente;
import it.webred.ct.data.model.fornituraelettrica.SitEnelUtenza;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface FornitureElettricheService {
	
	//GITOUT WS5
	public List<SitEnelUtente> getUtentiByDatiAnag(FornituraElettricaDTO fe) throws FornitureElettricheServiceException;
	public List<SitEnelUtenza> getUtenzeByParams(FornituraElettricaDTO fe) throws FornitureElettricheServiceException;
	public List<Object[]> getFornitureElettricheByParams(FornituraElettricaDTO fe) throws FornitureElettricheServiceException;
}
