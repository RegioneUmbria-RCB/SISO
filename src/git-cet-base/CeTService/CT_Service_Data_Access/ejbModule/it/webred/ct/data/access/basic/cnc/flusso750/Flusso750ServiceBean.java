package it.webred.ct.data.access.basic.cnc.flusso750;

import java.util.ArrayList;
import java.util.List;

import it.webred.ct.data.access.basic.cnc.CNCBaseService;
import it.webred.ct.data.access.basic.cnc.CNCDataIn;
import it.webred.ct.data.access.basic.cnc.flusso750.Flusso750Service;
import it.webred.ct.data.access.basic.cnc.flusso750.dao.Flusso750DAO;
import it.webred.ct.data.access.basic.cnc.flusso750.dto.AnagraficaSoggettiDTO;
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
import it.webred.ct.data.model.cnc.flusso750.VFrontespizioRuolo;
import it.webred.ct.data.model.cnc.flusso750.VInizioRuolo;
import it.webred.ct.data.model.cnc.flusso750.VUlterioriBeneficiari;
import it.webred.ct.support.audit.AuditStateless;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
 
/**
 * Session Bean implementation class Flusso750ServiceBean
 */
@Stateless
public class Flusso750ServiceBean extends CNCBaseService implements Flusso750Service {
 
	@Autowired
	private Flusso750DAO flusso750DAO;
	
	@Override
	@Interceptors(AuditStateless.class)
	public List<AnagraficaSoggettiDTO> getAnagraficaByCodicePartita(CNCDataIn dataIn) {
		
		List<AnagraficaSoggettiDTO> result = new ArrayList<AnagraficaSoggettiDTO>();
		
		String codicePartita = (String) dataIn.getObj();
		
		try {
			
			List<VAnagrafeDebitore> debList = flusso750DAO.getAnagrafeDebitore(codicePartita);
			
			logger.debug("Lista anagrafica debitori ["+debList+"]");
			
			for (int i=0; i < debList.size(); i++) {
				VAnagrafeDebitore deb = debList.get(i);
				
				AnagraficaSoggettiDTO anagSogg = new AnagraficaSoggettiDTO();
				
				VInizioRuolo infoRuolo = flusso750DAO.getInizioRuolo(deb);
				anagSogg.addAnagDebitore(deb);
				anagSogg.setInfoRuolo(infoRuolo);
				
				result.add(anagSogg);
			}
				
		}
		catch (Throwable t) {
			throw new Flusso750ServiceException(t);
		}
		
		return result;
	}

	@Override
	@Interceptors(AuditStateless.class)
	public List<VAnagrafica> getAnagraficaRuoliVistati(			
			Flusso750SearchCriteria criteria, int start, int numRecord) {
		
		
		List<VAnagrafica> anagList = new ArrayList<VAnagrafica>();
		
		try {
			flusso750DAO.getAnagraficaRuoliVistati(criteria, start, numRecord);
		}
		catch(Throwable t) {
			throw new Flusso750ServiceException(t);
		}
		
		return anagList;
		
	}

	@Override
	public Long getRecordCountAnagraficaRuoliVistati(
			Flusso750SearchCriteria criteria) {
		
		try {
			return flusso750DAO.getRecordCountAnagraficaRuoliVistati(criteria);
		}
		catch(Throwable t) {
			throw new Flusso750ServiceException(t);
		}
	}

	@Override
	@Interceptors(AuditStateless.class)
	public DettaglioRuoloVistatoDTO getDettaglioRuoloVistato(
			CNCDataIn dataIn) {
		
		DettaglioRuoloVistatoDTO dettaglio = new DettaglioRuoloVistatoDTO();
		
		ChiaveULPartita chiave = (ChiaveULPartita) dataIn.getObj();
		
		try {
			logger.debug("-- Chiave Ruolo --");
			logger.debug("Cod. Ente Cred. ["+chiave.getCodEnteCreditore()+"]");
			logger.debug("Cod. Ambito ["+chiave.getCodAmbito()+"]");
			logger.debug("Num/Anno Ruolo ["+chiave.getProgressivoRuolo()+"/" + chiave.getAnnoRuolo() + "]");

			logger.debug("-- Chiave Partita --");
			logger.debug("Tipo Uff. ["+chiave.getCodTipoUfficio()+"]");
			logger.debug("Cod. Uff. ["+chiave.getCodUfficio()+"]");
			logger.debug("Anno rif. ["+chiave.getAnnoRiferimento()+"]");
			logger.debug("Cod. Parita ["+chiave.getCodicePartita()+"]");
			
			List<VAnagrafeDebitore> debList = flusso750DAO.getAnagrafeDebitore(chiave);			
			List<VAnagrafeCointestatario> cointList = flusso750DAO.getAnagrafeCoint(chiave);
			VInizioRuolo infoRuolo = flusso750DAO.getInfoRuolo(chiave);
			List<VUlterioriBeneficiari>  benefList = flusso750DAO.getUltBeneficiari(chiave);
			List<VArticolo> artList = flusso750DAO.getArticoli(chiave);
			
			AnagraficaSoggettiDTO anagDTO = new AnagraficaSoggettiDTO();
			anagDTO.setAnagraficaIntestatarioList(debList);
			anagDTO.setAnagraficaCointestatarioList(cointList);
			anagDTO.setInfoRuolo(infoRuolo);
			
			dettaglio.setArticoliList(artList);
			dettaglio.setAnagrafica(anagDTO);
			dettaglio.setUlterioriBeneficiari(benefList);

		}
		catch(Throwable t) {
			throw new Flusso750ServiceException(t);
		}
		
		return dettaglio;
	}

 

	@Override
	public Long getRecordCountRuoliVistati(Flusso750SearchCriteria criteria) {
		try {
			return flusso750DAO.getRecordCountRuoliVistati(criteria);
		}
		catch(Throwable t) {
			throw new Flusso750ServiceException(t);
		}
	}

	@Override
	@Interceptors(AuditStateless.class)
	public List<InfoRuoloDTO> getRuoliVistati(Flusso750SearchCriteria criteria,
			int start, int numRecord) {
		
		List<InfoRuoloDTO> ruoliList = new ArrayList<InfoRuoloDTO>();
		
		try {
			ruoliList = flusso750DAO.getRuoliVistati(criteria, start, numRecord);
		}
		catch(Throwable t) {
			throw new Flusso750ServiceException(t);
		}
		
		return ruoliList;
	}

	@Override
	public InfoRuoloDTO getFrontespizioRuolo(CNCDataIn dataIn) {
		InfoRuoloDTO infoRuolo = new InfoRuoloDTO();
		
		ChiaveULRuolo chiaveRuolo = (ChiaveULRuolo) dataIn.getObj();
		
		try {
			infoRuolo = flusso750DAO.getFrontespizioRuolo(chiaveRuolo);
		}
		catch (Throwable t) {
			throw new Flusso750ServiceException(t); 
		}
		
		return infoRuolo;
	}
	
	public List<VArticolo> getArticoliByComuneECF(Flusso750SearchCriteria criteria) {
		List<VArticolo> lista =null;
		try {
			lista=flusso750DAO.getArticoliByComuneECF(criteria);
		}
		catch (Throwable t) {
			throw new Flusso750ServiceException(t); 
		}
		return lista;
	}

	@Override
	@Interceptors(AuditStateless.class)
	public List<VAnagrafica> getAnagraficaDebitore(Flusso750SearchCriteria criteria) {
		List<VAnagrafica> lista =null;
		try {
			if (criteria.getCodiceFiscale() != null && !criteria.getCodiceFiscale().equals(""))
				lista=flusso750DAO.getAnagraficaDebitoreByComuneECF(criteria);
		}
		catch (Throwable t) {
			throw new Flusso750ServiceException(t); 
		}
		return lista;
	}

	@Override
	public List<AnnoTributoDTO> getAnnoETributo(Flusso750SearchCriteria criteria) {
		List<AnnoTributoDTO>  lista=null;
		try {
			lista=flusso750DAO.getAnnoETributo(criteria);
		}
		catch (Throwable t) {
			throw new Flusso750ServiceException(t); 
		}
		return lista;
	}
	
}
