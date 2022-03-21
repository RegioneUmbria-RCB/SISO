package it.webred.ct.data.access.basic.acqua.dao;

import it.webred.ct.data.access.basic.acqua.AcquaServiceException;
import it.webred.ct.data.access.basic.rette.RetteServiceException;
import it.webred.ct.data.model.acqua.SitAcquaCatasto;
import it.webred.ct.data.model.acqua.SitAcquaUtente;
import it.webred.ct.data.model.acqua.SitAcquaUtenze;

import java.util.List;

public interface AcquaDAO {

	List<SitAcquaUtenze> getListaUtenzeByCodFis(String codFiscale)
			throws AcquaServiceException;
	
	List<SitAcquaUtenze> getListaUtenzeByPIva(String pIva)
			throws AcquaServiceException;

	List<SitAcquaCatasto> getListaCatastoByCodServizio(String codServizio)
			throws RetteServiceException;

	List<SitAcquaUtente> getListaUtenteByCodFis(String codFiscale)
			throws AcquaServiceException;

	List<SitAcquaUtente> getListaUtenteByPIva(String pIva)
			throws AcquaServiceException;
	
}
