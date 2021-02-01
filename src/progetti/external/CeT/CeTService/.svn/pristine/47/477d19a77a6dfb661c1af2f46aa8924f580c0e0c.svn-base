package it.webred.ct.data.access.basic.cnc.statoriscossione.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import it.webred.ct.data.access.basic.cnc.dao.CNCBaseDAO;
import it.webred.ct.data.access.basic.cnc.dao.CNCDAOException;
import it.webred.ct.data.access.basic.cnc.statoriscossione.dao.StatoQueryBuilder;
import it.webred.ct.data.access.basic.cnc.statoriscossione.StatoRiscossioneServiceException;
import it.webred.ct.data.access.basic.cnc.statoriscossione.dto.FullRiscossioneInfo;
import it.webred.ct.data.access.basic.cnc.statoriscossione.dto.StatoRiscossioneSearchCriteria;
import it.webred.ct.data.model.cnc.flusso750.VArticolo;
import it.webred.ct.data.model.cnc.statoriscossione.ChiaveULStatoRiscossione;
import it.webred.ct.data.model.cnc.statoriscossione.SRiscossioni;
import it.webred.ct.data.model.cnc.statoriscossione.SRiversamenti;

public class StatoRiscossioneJPAImpl extends CNCBaseDAO implements StatoRiscossioneDAO {
 
	@Override
	public List<SRiscossioni> getRiscossioni(
			StatoRiscossioneSearchCriteria criteria, int start, int numRecord) throws CNCDAOException {
		
		List<SRiscossioni> riscossioniList = new ArrayList<SRiscossioni>();
		
		try {
			String sql = ((new StatoQueryBuilder(criteria))).createQuery(false);
			logger.debug("SQL ["+sql+"]");
		
			if (sql == null)
				return riscossioniList;
			
			Query q = manager.createQuery(sql);
			q.setFirstResult(start);
			q.setMaxResults(numRecord);
			riscossioniList = q.getResultList();	
			
			logger.debug("Result size ["+riscossioniList.size()+"]");
		}
		catch(Throwable t) {
			throw new CNCDAOException(t);			
		}
		
		return riscossioniList;
		
	}

	@Override
	public Long getRecordCountRiscossioni(
			StatoRiscossioneSearchCriteria criteria) throws CNCDAOException {
		try {
			String sql = ((new StatoQueryBuilder(criteria))).createQuery(true);
			
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
	public List<SRiscossioni> getRiscossioni(ChiaveULStatoRiscossione chiave) throws CNCDAOException {

		try {
			Query q = manager.createNamedQuery("StatoRiscossioni.getRiscossioneByChiaveULRiscossione");
			q.setParameter("annoRuolo", chiave.getAnnoRuolo());
			q.setParameter("codAmbito", chiave.getCodAmbito());
			q.setParameter("codEnteCreditore", chiave.getCodEnteCreditore());
			q.setParameter("codPartita", chiave.getCodPartita());
			q.setParameter("dtRegistrazione", chiave.getDtRegistrInformaz());
			q.setParameter("numeroOperazContab", chiave.getNumeroOperazContab());
			q.setParameter("progressivoArticoloRuolo", chiave.getProgressivoArticoloRuolo());
			q.setParameter("progressivoOperazContab", chiave.getProgressivoOperazContab());
			q.setParameter("progressivoRuolo", chiave.getProgressivoRuolo());
			
			logger.debug("Anno Ruolo ["+chiave.getAnnoRuolo()+"]");
			logger.debug("Cod Ambito ["+chiave.getCodAmbito()+"]");
			logger.debug("Cod Ente cred. ["+chiave.getCodEnteCreditore()+"]");
			logger.debug("Cod. part. ["+chiave.getCodPartita()+"]");
			logger.debug("dtRegistrazione ["+chiave.getDtRegistrInformaz()+"]");
			logger.debug("numeroOperazContab ["+chiave.getNumeroOperazContab()+"]");
			logger.debug("progressivoArticoloRuolo ["+chiave.getProgressivoArticoloRuolo()+"]");
			logger.debug("progressivoOperazContab ["+chiave.getProgressivoOperazContab()+"]");
			logger.debug("progressivoRuolo ["+chiave.getProgressivoRuolo()+"]");
			
			List<SRiscossioni> risList = q.getResultList();
			
			return risList;
		}
		catch(Throwable t) {
			throw new CNCDAOException(t);
		}
	}
		
	@Override
	public List<SRiversamenti> getRiversamenti(ChiaveULStatoRiscossione chiave) throws CNCDAOException {
				
		try { 
			Query q1 = manager.createNamedQuery("StatoRiscossione.getRiversamentiByChiaveULRiscossione");
			q1.setParameter("annoRuolo", chiave.getAnnoRuolo());
			q1.setParameter("codAmbito", chiave.getCodAmbito());
			q1.setParameter("codEnteCreditore", chiave.getCodEnteCreditore());
			q1.setParameter("codPartita", chiave.getCodPartita());
			q1.setParameter("dtRegistrazione", chiave.getDtRegistrInformaz());
			q1.setParameter("numeroOperazContab", chiave.getNumeroOperazContab());
			q1.setParameter("progressivoArticoloRuolo", chiave.getProgressivoArticoloRuolo());
			q1.setParameter("progressivoOperazContab", chiave.getProgressivoOperazContab());
			q1.setParameter("progressivoRuolo", chiave.getProgressivoRuolo());
			
			List<SRiversamenti> rivList = q1.getResultList();
			
			logger.debug("Riversamenti OK. Size ["+rivList.size()+"]");
			
			return rivList;
			
		}
		catch(Throwable t) {
			throw new CNCDAOException(t);
		}
	}

	@Override
	public List<SRiscossioni> getRiscossioniByAnnoComuneCF(StatoRiscossioneSearchCriteria criteria) throws CNCDAOException {
		try {
			Query q =null;
			if (criteria.getCodiceTributo() !=null )
				q = manager.createNamedQuery("StatoRiscossioni.getRiscossioneByAnnoComuneTributoCF");
			else
				q = manager.createNamedQuery("StatoRiscossioni.getRiscossioneByAnnoComuneCF");
			q.setParameter("codEnteCreditore", criteria.getChiaveRiscossione().getCodEnteCreditore());
			q.setParameter("codFiscale", criteria.getCodiceFiscale());
			q.setParameter("annoRuolo", criteria.getChiaveRiscossione().getAnnoRuolo());
			if (criteria.getCodiceTributo() !=null )
				q.setParameter("codEntrata", criteria.getCodiceTributo());
			List<SRiscossioni> riscList = q.getResultList();
			
			logger.debug("Lista riscossioni per debitore . ["+riscList+"]");
			return riscList;
		}
		catch(Throwable t) {
			throw new CNCDAOException(t);
		}		
	}
		
}
