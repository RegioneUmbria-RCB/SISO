package it.webred.ct.data.access.basic.acqua;

import it.webred.ct.data.access.basic.acqua.dto.AcquaUtenzeDTO;
import it.webred.ct.data.model.acqua.SitAcquaUtente;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AcquaService{

	public List<AcquaUtenzeDTO> getListaUtenzeByCodFisPIva(AcquaDataIn dataIn)
	throws AcquaServiceException;

	public List<SitAcquaUtente> getListaUtenteByCodFisPIva(AcquaDataIn dataIn)
			throws AcquaServiceException;
	
}
