package it.webred.ct.data.access.basic.fornitureelettriche;

import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.fornitureelettriche.dao.FornitureElettricheDAO;
import it.webred.ct.data.access.basic.fornitureelettriche.dto.FornituraElettricaDTO;
import it.webred.ct.data.model.fornituraelettrica.SitEnelUtente;
import it.webred.ct.data.model.fornituraelettrica.SitEnelUtenza;

@Stateless 
public class FornitureElettricheServiceBean extends CTServiceBaseBean implements FornitureElettricheService {
	
	private static final long serialVersionUID = -773108123941835345L;
	
	@Autowired
	private FornitureElettricheDAO fornitureElettricheDAO;
	
	@Override
	public List<SitEnelUtente> getUtentiByDatiAnag(FornituraElettricaDTO fe) throws FornitureElettricheServiceException {
		return fornitureElettricheDAO.getUtentiByDatiAnag(fe);
	}//-------------------------------------------------------------------------
	
	@Override
	public List<SitEnelUtenza> getUtenzeByParams(FornituraElettricaDTO fe) throws FornitureElettricheServiceException {
		return fornitureElettricheDAO.getUtenzeByParams(fe);
	}//-------------------------------------------------------------------------

	@Override
	public List<Object[]> getFornitureElettricheByParams(FornituraElettricaDTO fe) throws FornitureElettricheServiceException {
		return fornitureElettricheDAO.getFornitureElettricheByParams(fe);
	}//-------------------------------------------------------------------------
}










