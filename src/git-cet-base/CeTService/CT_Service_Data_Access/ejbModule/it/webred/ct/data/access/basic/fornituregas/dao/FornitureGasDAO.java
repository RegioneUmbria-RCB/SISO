package it.webred.ct.data.access.basic.fornituregas.dao;


import it.webred.ct.data.access.basic.fornituregas.FornitureGasServiceException;
import it.webred.ct.data.access.basic.fornituregas.dto.FornituraGasDTO;
import it.webred.ct.data.model.gas.SitUGas;

import java.util.List;

public interface FornitureGasDAO {
	
	//GITOUT WS6
	public List<SitUGas> getUtentiByDatiAnag(FornituraGasDTO fe) throws FornitureGasServiceException;
	public List<SitUGas> getFornitureGasByParams(FornituraGasDTO fe) throws FornitureGasServiceException; 
	public List<SitUGas> getUtenzeByParams(FornituraGasDTO fe) throws FornitureGasServiceException;
	
}


