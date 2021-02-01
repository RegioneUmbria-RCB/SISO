package it.webred.ct.data.access.basic.cnc.flusso750.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import it.webred.ct.data.access.basic.cnc.dao.CNCBaseDAO;
import it.webred.ct.data.access.basic.cnc.dao.CNCDAOException;
import it.webred.ct.data.access.basic.cnc.flusso750.Flusso750ServiceException;
import it.webred.ct.data.access.basic.cnc.flusso750.dto.AnagraficaSoggettiDTO;
import it.webred.ct.data.access.basic.cnc.flusso750.dto.AnnoTributoDTO;
import it.webred.ct.data.access.basic.cnc.flusso750.dto.DettaglioRuoloVistatoDTO;
import it.webred.ct.data.access.basic.cnc.flusso750.dto.Flusso750SearchCriteria;
import it.webred.ct.data.access.basic.cnc.flusso750.dto.InfoRuoloDTO;
import it.webred.ct.data.access.basic.ici.dto.OggettoIciDTO;
import it.webred.ct.data.model.cnc.flusso750.ChiaveULPartita;
import it.webred.ct.data.model.cnc.flusso750.ChiaveULRuolo;
import it.webred.ct.data.model.cnc.flusso750.VAnagrafeCointestatario;
import it.webred.ct.data.model.cnc.flusso750.VAnagrafeDebitore;
import it.webred.ct.data.model.cnc.flusso750.VAnagrafica;
import it.webred.ct.data.model.cnc.flusso750.VArticolo;
import it.webred.ct.data.model.cnc.flusso750.VFrontespizioRuolo;
import it.webred.ct.data.model.cnc.flusso750.VInizioRuolo;
import it.webred.ct.data.model.cnc.flusso750.VUlterioriBeneficiari;
import it.webred.ct.data.model.ici.SitTIciOggetto;

public class Flusso750JPAImpl extends CNCBaseDAO implements Flusso750DAO {

	
	public List<VAnagrafeDebitore> getAnagrafeDebitore(String codicePartita) throws CNCDAOException {
		try {
			logger.debug("Recupero Anagrafica soggetti 750 con codice partita ["+codicePartita+"]");
			Query q = manager.createNamedQuery("Flusso750.getAnagraficaDebitoreByParita");
			q.setParameter("codPartita", codicePartita);
			List<VAnagrafeDebitore> debList = q.getResultList();			
			return debList;
		}
		catch(Throwable t) {
			throw new CNCDAOException(t);
		}
	}
	
	public VInizioRuolo getInizioRuolo(VAnagrafeDebitore deb) throws CNCDAOException {
	  try {
		Query q1 = manager.createNamedQuery("Flusso750.getInfoRuoloByChiaveULRuolo");
		logger.debug("--- Chiave UL Ruolo ---");
		logger.debug("Anno ruolo ["+deb.getChiavePartita().getAnnoRuolo()+"]");
		logger.debug("Numero ["+deb.getChiavePartita().getProgressivoRuolo()+"]");
		logger.debug("Ambito ["+deb.getChiavePartita().getCodAmbito()+"]");
		logger.debug("Ente Cred. ["+deb.getChiavePartita().getCodEnteCreditore()+"]");
		
		q1.setParameter("annoRuolo", deb.getChiavePartita().getAnnoRuolo());
		q1.setParameter("codAmbito", deb.getChiavePartita().getCodAmbito());
		q1.setParameter("codEnteCreditore", deb.getChiavePartita().getCodEnteCreditore());
		q1.setParameter("progressivoRuolo", deb.getChiavePartita().getProgressivoRuolo());
		VInizioRuolo infoRuolo = (VInizioRuolo) q1.getSingleResult();
		return infoRuolo;
	  }
	  catch(Throwable t) {
		  throw new CNCDAOException(t);
	  }		
	}
	

	@Override
	public List<VAnagrafica> getAnagraficaRuoliVistati(			
			Flusso750SearchCriteria criteria, int start, int numRecord) throws CNCDAOException {
		
		
		List<VAnagrafica> anagList = new ArrayList<VAnagrafica>();
		
		try {
			String sql = ((new AnagQueryBuilder(criteria))).createQuery(false);
			logger.debug("SQL ["+sql+"]");
			
			
			if (sql == null)
				return anagList;
			
			Query q = manager.createQuery(sql);
			q.setFirstResult(start);
			q.setMaxResults(numRecord);
			anagList = q.getResultList();	
			
			logger.debug("Result size ["+anagList.size()+"]");
		}
		catch(Throwable t) {
			throw new CNCDAOException(t);
		}
		
		return anagList;
		
	}

	@Override
	public Long getRecordCountAnagraficaRuoliVistati(
			Flusso750SearchCriteria criteria) throws CNCDAOException {
		
		try {
			String sql = ((new AnagQueryBuilder(criteria))).createQuery(true);
			
			if (sql == null)
				return 0L;

			Query q = manager.createQuery(sql);
			Object o = q.getSingleResult();
			
			return (Long) o;
		}
		catch(Throwable t) {
			throw new Flusso750ServiceException(t);
		}
	}
	
	


 
	private Query createQuery(String namedQueryName, ChiaveULPartita chiave, boolean isRuoloKey) throws CNCDAOException {
		Query q = manager.createNamedQuery(namedQueryName);
		q.setParameter("codEnteCreditore", chiave.getCodEnteCreditore());
		q.setParameter("codAmbito", chiave.getCodAmbito());
		q.setParameter("annoRuolo", chiave.getAnnoRuolo());
		q.setParameter("progressivoRuolo", chiave.getProgressivoRuolo());
		
		if (!isRuoloKey) {
			q.setParameter("annoRiferimento", chiave.getAnnoRiferimento());
			q.setParameter("codicePartita", chiave.getCodicePartita());
			q.setParameter("codTipoUfficio", chiave.getCodTipoUfficio());
			q.setParameter("codUfficio", chiave.getCodUfficio());
		}
		
		return q;
	}

	@Override
	public Long getRecordCountRuoliVistati(Flusso750SearchCriteria criteria) throws CNCDAOException {
		try {
			String sql = ((new RuoloQueryBuilder(criteria))).createQuery(true);
			
			if (sql == null)
				return 0L;

			Query q = manager.createQuery(sql);
			Object o = q.getSingleResult();
			
			return (Long) o;
		}
		catch(Throwable t) {
			throw new CNCDAOException(t);
		}
	}

	@Override
	public List<InfoRuoloDTO> getRuoliVistati(Flusso750SearchCriteria criteria,
			int start, int numRecord) throws CNCDAOException {
		
		List<InfoRuoloDTO> ruoliList = new ArrayList<InfoRuoloDTO>();
		
		try {
			String sql = ((new RuoloQueryBuilder(criteria))).createQuery(false);
			logger.debug("SQL ["+sql+"]");
			
			
			if (sql == null)
				return ruoliList;
			
			Query q = manager.createQuery(sql);
			q.setFirstResult(start);
			q.setMaxResults(numRecord);
			ruoliList = q.getResultList();	
			
			logger.debug("Result size ["+ruoliList.size()+"]");
		}
		catch(Throwable t) {
			throw new CNCDAOException(t);
		}
		
		return ruoliList;
	}

	@Override
	public InfoRuoloDTO getFrontespizioRuolo(ChiaveULRuolo chiaveRuolo) throws CNCDAOException {
		InfoRuoloDTO infoRuolo = new InfoRuoloDTO();
		
		try {
			Query q = manager.createNamedQuery("Flusso750.getFrontespizioRuoloByChiaveRuolo");
			q.setParameter("codEnteCreditore", chiaveRuolo.getCodEnteCreditore());
			q.setParameter("annoRuolo", chiaveRuolo.getAnnoRuolo());
			q.setParameter("progressivoRuolo", chiaveRuolo.getProgressivoRuolo());
			q.setParameter("codAmbito", chiaveRuolo.getCodAmbito());
			
			List<VFrontespizioRuolo> fronteList = q.getResultList();
			
			infoRuolo.setInfoFrontespizioList(fronteList);
		}
		catch (Throwable t) {
			throw new CNCDAOException(t);
		}
		
		return infoRuolo;
	}

	@Override
	public List<VAnagrafeCointestatario> getAnagrafeCoint(ChiaveULPartita chiave)
			throws CNCDAOException {
		try {
			Query q1 = createQuery("Flusso750.getAnagraficaCointestatarioByChiavePartita", chiave, false);
			List<VAnagrafeCointestatario> cointList = q1.getResultList();
			logger.debug("Lista anagrafe coint. OK. ["+cointList+"]");
			return cointList;
		}
		catch(Throwable t) {
			throw new CNCDAOException(t);
		}		
	}

	@Override
	public List<VArticolo> getArticoli(ChiaveULPartita chiave)
			throws CNCDAOException {
		try {
			Query q2 = createQuery("Flusso750.getArticoliByChiavePartita", chiave, false);
			List<VArticolo> artList = q2.getResultList();
			logger.debug("Lista art. OK. ["+artList+"]");
			return artList;
		}
		catch(Throwable t) {
			throw new CNCDAOException(t);
		}
		
	}

	@Override
	public List<VUlterioriBeneficiari> getUltBeneficiari(ChiaveULPartita chiave)
			throws CNCDAOException {
		try {
			Query q4 = createQuery("Flusso750.getUlterioriBenefByChiavePartita", chiave, false);
			List<VUlterioriBeneficiari>  benefList = q4.getResultList();
			logger.debug("Lista ult benef. OK. ["+benefList+"]");
			return benefList;
		}
		catch(Throwable t) {
			throw new CNCDAOException(t);
		}		
	}

	@Override
	public List<VAnagrafeDebitore> getAnagrafeDebitore(ChiaveULPartita chiave)
			throws CNCDAOException {
		try {
			Query q = createQuery("Flusso750.getAnagraficaDebitoreByChiavePartita", chiave, false);
			List<VAnagrafeDebitore> debList = q.getResultList();
			
			logger.debug("Lista anagrafe deb. OK. ["+debList+"]");
			return debList;
		}
		catch(Throwable t) {
			throw new CNCDAOException(t);
		}		
	}

	@Override
	public VInizioRuolo getInfoRuolo(ChiaveULPartita chiave)
			throws CNCDAOException {
		try {
			Query q3 = createQuery("Flusso750.getInfoRuoloByChiaveULRuolo", chiave, true);
			VInizioRuolo infoRuolo = (VInizioRuolo) q3.getSingleResult();
			logger.debug("Info ruolo. OK. ["+infoRuolo+"]");
			return infoRuolo;
		}
		catch(Throwable t) {
			throw new CNCDAOException(t);
		}		 
	}

	@Override
	public List<VArticolo> getArticoliByComuneECF(
			Flusso750SearchCriteria criteria) throws CNCDAOException {
		try {
			Query q =null;
			if (criteria.getCodiceTipoTributo() !=null )
				q = manager.createNamedQuery("Flusso750.getArticoloByComuneTributoCF");
			else
				q = manager.createNamedQuery("Flusso750.getArticoloByComuneCF");
			q.setParameter("codEnteCred", criteria.getChiaveRuolo().getCodEnteCreditore());
			q.setParameter("codFis", criteria.getCodiceFiscale());
			if (criteria.getCodiceTipoTributo() !=null )
				q.setParameter("tipEntr", criteria.getCodiceTipoTributo());
			List<VArticolo> artList = q.getResultList();
			
			logger.debug("Lista articoli per debitore . ["+artList+"]");
			return artList;
		}catch(NoResultException nre) {
			return null;
		}
		catch(Throwable t) {
			throw new CNCDAOException(t);
		}		
	}

	@Override
	public List<VAnagrafica> getAnagraficaDebitoreByComuneECF(Flusso750SearchCriteria criteria) throws CNCDAOException {
		try {
			Query q = manager.createNamedQuery("Flusso750.getAnagraficaDebitoreByComuneCF");
			q.setParameter("codEnteCred", criteria.getChiaveRuolo().getCodEnteCreditore());
			q.setParameter("codFis", criteria.getCodiceFiscale());
			List<VAnagrafica> listaDeb = q.getResultList();
			
			logger.debug("Lista debitori. N.ele: ["+listaDeb.size()+"]");
			return listaDeb;
		}
		catch(NoResultException nre) {
			return null;
		}
		catch(Throwable t) {
			throw new CNCDAOException(t);
		}		
	}

	@Override
	public List<AnnoTributoDTO> getAnnoETributo(Flusso750SearchCriteria criteria) throws CNCDAOException {
		List<AnnoTributoDTO> lista=null;
		try {
			logger.debug("Flusso750JPAImpl.getAnnoETributo. ENTE:[" + criteria.getChiaveRuolo().getCodEnteCreditore() + "];CF:[" +criteria.getCodiceFiscale() + "]");
					
			Query q = manager.createNamedQuery("Flusso750.getDistinctAnnoRifCodEntrByComuneCF");
			q.setParameter("codEnteCred", criteria.getChiaveRuolo().getCodEnteCreditore());
			q.setParameter("codFis", criteria.getCodiceFiscale());
			List<Object[]> res = q.getResultList();
			logger.debug("Result size ["+res.size()+"]");
			lista=new ArrayList<AnnoTributoDTO>();
			for(Object[] eleRes: res){
				AnnoTributoDTO ogg = new AnnoTributoDTO();
				ogg.setAnnoRif(eleRes[0].toString());
				ogg.setCodTipoTributo(eleRes[1].toString());
				lista.add(ogg);
			}
			return lista;
		}
		catch(NoResultException nre) {
			return null;
		}
		catch(Throwable t) {
			throw new CNCDAOException(t);
		}		
	}
	
	
}
