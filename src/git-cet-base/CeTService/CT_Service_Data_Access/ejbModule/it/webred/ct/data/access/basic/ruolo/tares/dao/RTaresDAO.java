package it.webred.ct.data.access.basic.ruolo.tares.dao;

import it.webred.ct.data.access.basic.ruolo.tares.RTaresServiceException;
import it.webred.ct.data.model.ruolo.SitRuoloTarPdf;
import it.webred.ct.data.model.ruolo.tares.SitRuoloTares;
import it.webred.ct.data.model.ruolo.tares.SitRuoloTaresImm;
import it.webred.ct.data.model.ruolo.tares.SitRuoloTaresRata;
import it.webred.ct.data.model.ruolo.tares.SitRuoloTaresSt;
import it.webred.ct.data.model.ruolo.tares.SitRuoloTaresStSg;

import java.util.List;

public interface RTaresDAO {

	List<SitRuoloTares> getListaRuoliByCodFis(String codFiscale)
			throws RTaresServiceException;
	
	List<SitRuoloTaresImm> getListaImmByCodRuolo(String codRuolo)
			throws RTaresServiceException;
	
	List<SitRuoloTaresRata> getListaRateByCodRuolo(String codRuolo)
			throws RTaresServiceException;
	
	List<SitRuoloTaresSt> getListaStByCodRuolo(String codRuolo)
			throws RTaresServiceException;

	List<SitRuoloTaresStSg> getListaStSgByCodRuolo(String codRuolo)
			throws RTaresServiceException;

	List<SitRuoloTarPdf> getListaPdfByCfCu(String cf, Integer cu)
			throws RTaresServiceException;

	SitRuoloTares getRuoloById(String id)
			throws RTaresServiceException;
	
}
