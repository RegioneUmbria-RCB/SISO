package it.webred.ct.data.access.basic.ruolo.tarsu.dao;

import it.webred.ct.data.access.basic.ruolo.RuoloDataIn;
import it.webred.ct.data.access.basic.ruolo.tarsu.RTarsuServiceException;
import it.webred.ct.data.access.basic.ruolo.tarsu.dto.RataDTO;
import it.webred.ct.data.access.basic.ruolo.tarsu.dto.RuoloTarsuDTO;
import it.webred.ct.data.access.basic.versamenti.bp.dto.VersamentoTarBpDTO;
import it.webred.ct.data.model.ruolo.tarsu.SitRuoloTarsu;
import it.webred.ct.data.model.ruolo.tarsu.SitRuoloTarsuImm;
import it.webred.ct.data.model.ruolo.tarsu.SitRuoloTarsuSt;
import it.webred.ct.data.model.ruolo.tarsu.SitRuoloTarsuStSg;

import java.util.List;

public interface RTarsuDAO {

	List<SitRuoloTarsu> getListaRuoliByCodFis(String codFiscale)
			throws RTarsuServiceException;
	
	List<SitRuoloTarsuImm> getListaImmByCodRuolo(String codRuolo)
			throws RTarsuServiceException;
	
	List<RataDTO> getListaRateByCodRuolo(String codRuolo, boolean ricercaVersamenti)
			throws RTarsuServiceException;
	
	List<SitRuoloTarsuSt> getListaStByCodRuolo(String codRuolo)
			throws RTarsuServiceException;

	List<SitRuoloTarsuStSg> getListaStSgByCodRuolo(String codRuolo)
			throws RTarsuServiceException;
	
	SitRuoloTarsu getRuoloById(String id) 
			throws RTarsuServiceException;;
	
}
