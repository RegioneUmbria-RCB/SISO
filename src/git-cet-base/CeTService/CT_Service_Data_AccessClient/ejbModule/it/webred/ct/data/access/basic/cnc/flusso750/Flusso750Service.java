package it.webred.ct.data.access.basic.cnc.flusso750;
import java.util.List;

import it.webred.ct.data.access.basic.cnc.CNCDataIn;
import it.webred.ct.data.access.basic.cnc.flusso750.dto.AnagraficaSoggettiDTO;
import it.webred.ct.data.access.basic.cnc.flusso750.dto.AnnoTributoDTO;
import it.webred.ct.data.access.basic.cnc.flusso750.dto.DettaglioRuoloVistatoDTO;
import it.webred.ct.data.access.basic.cnc.flusso750.dto.Flusso750SearchCriteria;
import it.webred.ct.data.access.basic.cnc.flusso750.dto.InfoRuoloDTO;
import it.webred.ct.data.model.cnc.flusso750.ChiaveULPartita;
import it.webred.ct.data.model.cnc.flusso750.ChiaveULRuolo;
import it.webred.ct.data.model.cnc.flusso750.VAnagrafica;
import it.webred.ct.data.model.cnc.flusso750.VArticolo;

import javax.ejb.Remote;

@Remote
public interface Flusso750Service {

	public List<AnagraficaSoggettiDTO> getAnagraficaByCodicePartita(CNCDataIn dataIn) ;
	
	public List<VAnagrafica> getAnagraficaRuoliVistati(Flusso750SearchCriteria criteria, int start, int numRecord);
	
	public Long getRecordCountAnagraficaRuoliVistati(Flusso750SearchCriteria criteria);
	
	public DettaglioRuoloVistatoDTO getDettaglioRuoloVistato(CNCDataIn dataIn);
	
	//public DettaglioRuoloVistatoDTO getDettaglioRuoloVistato(ChiaveULPartita chiave);
	
	public List<InfoRuoloDTO> getRuoliVistati(Flusso750SearchCriteria criteria, int start, int numRecord);
	
	public Long getRecordCountRuoliVistati(Flusso750SearchCriteria criteria);

	public InfoRuoloDTO getFrontespizioRuolo(CNCDataIn dataIn);
	//public InfoRuoloDTO getFrontespizioRuolo(ChiaveULRuolo chiaveRuolo);
	
	public List<VArticolo> getArticoliByComuneECF(Flusso750SearchCriteria criteria);
	
	public List<VAnagrafica> getAnagraficaDebitore(Flusso750SearchCriteria criteria) ;
	
	public List<AnnoTributoDTO> getAnnoETributo(Flusso750SearchCriteria criteria) ;
}
