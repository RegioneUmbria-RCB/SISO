package it.webred.ct.data.access.basic.cnc.flusso290.dao;

import it.webred.ct.data.access.basic.cnc.dao.CNCBaseDAO;
import it.webred.ct.data.access.basic.cnc.dao.CNCDAOException;
import it.webred.ct.data.access.basic.cnc.flusso290.Flusso290ServiceException;
import it.webred.ct.data.access.basic.cnc.flusso290.dto.AnagraficaImpostaDTO;
import it.webred.ct.data.access.basic.cnc.flusso290.dto.Flusso290SearchCriteria;
import it.webred.ct.data.model.cnc.flusso290.RAnagraficaIntestatarioRuolo;
import it.webred.ct.data.model.cnc.flusso290.RDatiContabili;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class Flusso290JPAImpl extends CNCBaseDAO implements Flusso290DAO {

	public List<RAnagraficaIntestatarioRuolo> getIntestarioRuolo(Flusso290SearchCriteria criteria, int startm, int numberRecord) throws CNCDAOException {
		List<RAnagraficaIntestatarioRuolo> anagList = new ArrayList<RAnagraficaIntestatarioRuolo>();

		try {
			
			String sql = (new QueryBuilder(criteria)).createQuery(false);
			
			if (sql == null)
				return new ArrayList<RAnagraficaIntestatarioRuolo>();
			
			Query q = manager.createQuery(sql);
			q.setFirstResult(startm);
			q.setMaxResults(numberRecord);
			
			anagList = q.getResultList();
			
			
		}
		catch(Throwable t) {
			throw new CNCDAOException(t);
		}
		
		return anagList;
		
	}
	
	public List<RDatiContabili> getDatiContByAnag(RAnagraficaIntestatarioRuolo anag) throws CNCDAOException {
		try {

			Query q1 = manager.createNamedQuery("Flusso290.getDatiContabileByPartitaComuneProgressivo");
			q1.setParameter("codComune", anag.getCodComuneIscrRuolo());
			q1.setParameter("codPartita", anag.getCodPartita());
			q1.setParameter("progr", anag.getProgressivoMinuta());
			List<RDatiContabili> datiContList = q1.getResultList();
			return datiContList;
		}
		catch(Throwable t) {
			throw new CNCDAOException(t);
		}

	}

	
	@Override
	public Long getRecordCount(Flusso290SearchCriteria criteria) throws CNCDAOException {
		try {
			
			String sql = (new QueryBuilder(criteria)).createQuery(true);
			
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
	
}
