package it.webred.ct.data.access.basic.rette.dao;

import it.webred.ct.data.access.basic.rette.RetteServiceException;
import it.webred.ct.data.access.basic.traffico.TrafficoServiceException;
import it.webred.ct.data.access.basic.traffico.dto.TrafficoSearchCriteria;
import it.webred.ct.data.model.rette.SitRttBollette;
import it.webred.ct.data.model.rette.SitRttDettBollette;
import it.webred.ct.data.model.rette.SitRttRateBollette;
import it.webred.ct.data.model.traffico.SitTrffMulte;

import java.util.List;

public interface RetteDAO {

	List<SitRttBollette> getListaBollettePagateByCodFis(String codFiscale, String numBolletta)
			throws RetteServiceException;

	List<SitRttBollette> getListaBolletteNonPagateByCodFis(String codFiscale, String numBolletta)
			throws RetteServiceException;

	List<SitRttDettBollette> getListaDettaglioBolletteByCodBoll(
			String codBolletta) throws RetteServiceException;

	List<SitRttRateBollette> getListaRateBolletteByCodBoll(String codBolletta)
			throws RetteServiceException;

	SitRttBollette getBollettaByCodBoll(String codBolletta)
			throws RetteServiceException;
	
}
