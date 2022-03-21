package it.webred.ct.data.access.basic.cnc.flusso750.dao;

import it.webred.ct.data.access.basic.cnc.dao.CNCDAOException;
import it.webred.ct.data.access.basic.cnc.flusso750.dto.AnnoTributoDTO;
import it.webred.ct.data.access.basic.cnc.flusso750.dto.DettaglioRuoloVistatoDTO;
import it.webred.ct.data.access.basic.cnc.flusso750.dto.Flusso750SearchCriteria;
import it.webred.ct.data.access.basic.cnc.flusso750.dto.InfoRuoloDTO;
import it.webred.ct.data.model.cnc.flusso750.ChiaveULPartita;
import it.webred.ct.data.model.cnc.flusso750.ChiaveULRuolo;
import it.webred.ct.data.model.cnc.flusso750.VAnagrafeCointestatario;
import it.webred.ct.data.model.cnc.flusso750.VAnagrafeDebitore;
import it.webred.ct.data.model.cnc.flusso750.VAnagrafica;
import it.webred.ct.data.model.cnc.flusso750.VArticolo;
import it.webred.ct.data.model.cnc.flusso750.VInizioRuolo;
import it.webred.ct.data.model.cnc.flusso750.VUlterioriBeneficiari;

import java.util.List;

public interface Flusso750DAO {

	public List<VAnagrafica> getAnagraficaRuoliVistati(Flusso750SearchCriteria criteria, int start, int numRecord) throws CNCDAOException;
	
	public Long getRecordCountAnagraficaRuoliVistati(Flusso750SearchCriteria criteria) throws CNCDAOException;
		
	public List<InfoRuoloDTO> getRuoliVistati(Flusso750SearchCriteria criteria, int start, int numRecord) throws CNCDAOException;
	
	public Long getRecordCountRuoliVistati(Flusso750SearchCriteria criteria) throws CNCDAOException;

	public InfoRuoloDTO getFrontespizioRuolo(ChiaveULRuolo chiaveRuolo) throws CNCDAOException;

	public List<VAnagrafeDebitore> getAnagrafeDebitore(String codicePartita) throws CNCDAOException;

	public VInizioRuolo getInizioRuolo(VAnagrafeDebitore deb) throws CNCDAOException;

	public List<VAnagrafeCointestatario> getAnagrafeCoint(ChiaveULPartita chiave)  throws CNCDAOException;
	
	public List<VArticolo> getArticoli(ChiaveULPartita chiave)  throws CNCDAOException;
	
	public List<VUlterioriBeneficiari> getUltBeneficiari(ChiaveULPartita chiave)  throws CNCDAOException;
	
	public List<VAnagrafeDebitore> getAnagrafeDebitore(ChiaveULPartita chiave) throws CNCDAOException;
	
	public VInizioRuolo getInfoRuolo(ChiaveULPartita chiave) throws CNCDAOException;
	
	public List<VArticolo> getArticoliByComuneECF(Flusso750SearchCriteria criteria) throws CNCDAOException;
	
	public List<VAnagrafica> getAnagraficaDebitoreByComuneECF(Flusso750SearchCriteria criteria) throws CNCDAOException;
	
	public List<AnnoTributoDTO> getAnnoETributo(Flusso750SearchCriteria criteria) throws CNCDAOException;

} 
