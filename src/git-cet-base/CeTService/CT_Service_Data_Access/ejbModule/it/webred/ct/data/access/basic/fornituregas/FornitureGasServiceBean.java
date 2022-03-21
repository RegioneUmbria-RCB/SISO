package it.webred.ct.data.access.basic.fornituregas;

import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.fornituregas.FornitureGasService;
import it.webred.ct.data.access.basic.fornituregas.FornitureGasServiceException;
import it.webred.ct.data.access.basic.fornituregas.dao.FornitureGasDAO;
import it.webred.ct.data.access.basic.fornituregas.dto.FornituraGasDTO;
import it.webred.ct.data.model.gas.SitUGas;

@Stateless 
public class FornitureGasServiceBean extends CTServiceBaseBean implements FornitureGasService {
	
	private static final long serialVersionUID = -773108123941835345L;
	
	@Autowired
	private FornitureGasDAO fornitureGasDAO;
	
	@Override
	public List<SitUGas> getUtentiByDatiAnag(FornituraGasDTO fe) throws FornitureGasServiceException {
		return fornitureGasDAO.getUtentiByDatiAnag(fe);
	}//-------------------------------------------------------------------------
	
	@Override
	public List<SitUGas> getUtenzeByParams(FornituraGasDTO fe) throws FornitureGasServiceException {
		return fornitureGasDAO.getUtenzeByParams(fe);
	}//-------------------------------------------------------------------------

	@Override
	public List<SitUGas> getFornitureGasByParams(FornituraGasDTO fe) throws FornitureGasServiceException {
		return fornitureGasDAO.getFornitureGasByParams(fe);
	}//-------------------------------------------------------------------------
}



