package it.webred.ct.data.access.basic.fornituregas;


import it.webred.ct.data.access.basic.fornituregas.dto.FornituraGasDTO;
import it.webred.ct.data.model.gas.SitUGas;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface FornitureGasService {
	
	//GITOUT WS5
	public List<SitUGas> getUtentiByDatiAnag(FornituraGasDTO fe) throws FornitureGasServiceException;
	public List<SitUGas> getUtenzeByParams(FornituraGasDTO fe) throws FornitureGasServiceException;
	public List<SitUGas> getFornitureGasByParams(FornituraGasDTO fe) throws FornitureGasServiceException;
}

