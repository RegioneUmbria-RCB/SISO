package it.webred.ct.data.access.basic.indice.civico.dao;

import it.webred.ct.data.access.basic.indice.civico.dto.RicercaCivicoIndiceDTO;
import it.webred.ct.data.access.basic.indice.dao.IndiceBaseDAO;
import it.webred.ct.data.access.basic.indice.dao.IndiceDAOException;
import it.webred.ct.data.model.indice.SitCivicoTotale;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class CivicoJPAImpl extends IndiceBaseDAO implements CivicoDAO{

	private static final long serialVersionUID = 1L;

	public List<Object[]> getListaCiviciByVia(String id, int start, int rowNumber) throws IndiceDAOException {
	
		List<Object[]> result = null;
		
		try {
			
			String sql = (new CivicoQueryBuilder()).createQueryCiviciByViaUnico(id, false);
			
			Query q = manager.createQuery(sql);
			if(start != 0 || rowNumber != 0){
				q.setFirstResult(start);
				q.setMaxResults(rowNumber);
			}
			
			result = q.getResultList();
			
			
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}		
		
		return result;
	}
			
	
	public Long getListaCiviciByViaRecordCount(String id) throws IndiceDAOException {
		
		Long ol = 0L;
		
		try{
			
			String sql = (new CivicoQueryBuilder()).createQueryCiviciByViaUnico(id, true);
			
			logger.debug("COUNT LISTA CIVICI - SQL["+sql+"]");
			
			if (sql != null) {
				
				Query q = manager.createQuery(sql);
				Object o = q.getSingleResult();
				ol = (Long) o;
				
				logger.debug("COUNT LISTA CIVICI ["+ol+"]");
				
			}
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
		return ol;
	}
	
	public List<SitCivicoTotale> getListaCivTotaleByViaUnicoDesc(RicercaCivicoIndiceDTO rci)throws IndiceDAOException{
		List<SitCivicoTotale> result = new ArrayList<SitCivicoTotale>();
		
		try{
			logger.debug("RICERCA CIVICO TOTALE [idViaUnico: "+rci.getIdVia() +"," +
					 "civico: "+rci.getCivico() +"," +
					 "fonte, progEs: "+rci.getEnteSorgente()+","+rci.getProgEs()+ "]");

			Query q = manager.createNamedQuery("SitCivicoTotale.getCivicoTotaleByViaUnicoCivico");
			q.setParameter("idVia", rci.getIdVia());
			q.setParameter("civico", rci.getCivico());
			q.setParameter("enteSorgente", rci.getEnteSorgente());
			q.setParameter("progressivo", rci.getProgEs());
			result = q.getResultList();
			
			logger.debug("Result["+result.size()+"]");
			
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
		return result;
	}


	@Override
	public List<SitCivicoTotale> getListaCivTotaleByCivicoFonte(RicercaCivicoIndiceDTO rci) throws IndiceDAOException {
		List<SitCivicoTotale> result = new ArrayList<SitCivicoTotale>();
		String sql1 = "SELECT ct.fkCivico from SitCivicoTotale ct  WHERE ct.id.idDwh= '" + rci.getIdCivico().trim() + "' AND " +
				     "ct.id.fkEnteSorgente=" + rci.getEnteSorgente() + " AND ct.id.progEs=" + rci.getProgEs();
		logger.debug("getListaCivTotaleByCivicoFonte - SQL1: " + sql1);
		
		try{
			Query q = manager.createQuery(sql1);
			List<BigDecimal> l = q.getResultList();
			if(l != null && l.size() > 0){
				
				BigDecimal fk = l.get(0);
				String sql2 = "SELECT ct1 FROM SitCivicoTotale ct1 "+
						" WHERE ct1.id.fkEnteSorgente="+rci.getDestEnteSorgente() +" AND ct1.id.progEs="+rci.getDestProgEs() + "AND " +
						 "ct1.fkCivico = " + fk.toString();
				logger.debug("getListaCivTotaleByCivicoFonte - SQL2: " + sql2);
				
				q = manager.createQuery(sql2);		
				result = q.getResultList();
			}
			
		}catch(Throwable t) {
			logger.error("getListaCivTotaleByCivicoFonte - ERRORE - IdDwh: " + rci.getIdCivico());
			logger.error("getListaCivTotaleByCivicoFonte - ERRORE ", t);
		}
		logger.debug("getListaCivTotaleByCivicoFonte - n.eleL: " + result.size());
		return result;
	}
}
