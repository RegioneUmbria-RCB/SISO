package it.webred.ct.data.access.basic.fornitureelettriche.dao;

import it.webred.ct.data.access.basic.fornitureelettriche.FornitureElettricheServiceException;
import it.webred.ct.data.access.basic.fornitureelettriche.dto.FornituraElettricaDTO;
import it.webred.ct.data.model.fornituraelettrica.SitEnelUtente;
import it.webred.ct.data.model.fornituraelettrica.SitEnelUtenza;

import java.util.List;

public interface FornitureElettricheDAO {
	
	//GITOUT WS5
	public List<SitEnelUtente> getUtentiByDatiAnag(FornituraElettricaDTO fe) throws FornitureElettricheServiceException;
	public List<Object[]> getFornitureElettricheByParams(FornituraElettricaDTO fe) throws FornitureElettricheServiceException; 
	public List<SitEnelUtenza> getUtenzeByParams(FornituraElettricaDTO fe) throws FornitureElettricheServiceException;
	
}
