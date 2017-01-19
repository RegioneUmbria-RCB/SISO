package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.dto.SchedaSegrDTO;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsSsSchedaSegr;
import it.webred.cs.data.model.CsVistaCasiSS;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

@Named
public class SchedaSegrDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public CsSsSchedaSegr findSchedaSegrById(Long id) {
		CsSsSchedaSegr scheda = em.find(CsSsSchedaSegr.class, id);
		return scheda;
	}

	public CsSsSchedaSegr findSchedaSegrByIdAnagrafica(Long idAnagrafica) {
		Query q = em.createNamedQuery("CsSsSchedaSegr.findByIdAnagrafica")
				.setParameter("idAnagrafica", idAnagrafica);
		List<CsSsSchedaSegr> lista = q.getResultList();
		if(lista != null && lista.size() > 0)
			return lista.get(0);
			
		return null;
	}
	
	public void saveSchedaSegr(CsSsSchedaSegr newScheda) {
		em.persist(newScheda);
	}
	
	public void updateSchedaSegr(CsSsSchedaSegr scheda) {
		em.merge(scheda);
	}

	@SuppressWarnings("unchecked")
	public List<CsSsSchedaSegr> getSchedeSegr(SchedaSegrDTO criteria) {
		
		String sql = getQuery(false, criteria);
		Integer first= criteria.getFirst();
		int pageSize= criteria.getPageSize();
		logger.debug("ListaSchedeSegretariato - SQL["+sql+"]");
		Query q = em.createQuery(sql);
		if(first != null) {
			q.setFirstResult(first);
			q.setMaxResults(pageSize);
		}
		return q.getResultList();
	}
	
	public Integer getSchedeSegrCount(boolean onlyNew,SchedaSegrDTO criteria) {
		
		criteria.setOnlyNew(onlyNew);
		
	
		Query q = em.createQuery(getQuery(true, criteria));
		Long o = (Long) q.getSingleResult();
		return new Integer(o.intValue());
	}
	
	private String getQuery(boolean count, SchedaSegrDTO criteria) {
		
		String sql = "";
		String join ="";
		if(!count) {
			join = " left join fetch ss.csASoggetto s left join fetch s.csACaso c ";
			sql += "SELECT DISTINCT ss ";
		}
		else sql += "SELECT COUNT(ss) ";
		
		sql += " FROM CsRelSettoreCatsoc cs,  CsSsSchedaSegr ss, CsVistaCasiSS vw" + join +
				" WHERE ss.csCCategoriaSociale.id = cs.id.categoriaSocialeId "+
				" AND vw.idScheda=ss.id"+
				
				" AND ss.codEnte=cs.csOSettore.csOOrganizzazione.belfiore ";
		
		if(criteria.isOnlyNew())
			sql += " AND ss.csASoggetto is null ";
		if(criteria.getIdSettore() != null)
			sql += " AND cs.id.settoreId = " + criteria.getIdSettore();
		if(criteria.getIdAnagrafica() != null)
			sql += " AND ss.csASoggetto.anagraficaId = " + criteria.getIdAnagrafica();
		if(criteria.getOperatore() != null )
			sql += " AND UPPER(vw.accesso_Oper_Cognome|| ' '  ||vw.accesso_Oper_Nome) like '%" + criteria.getOperatore().trim().toUpperCase()+"%'";
			
		if(criteria.getSoggSegnalante() != null ){
		    String s = criteria.getSoggSegnalante().replaceAll("'", "''");
			sql += " AND UPPER(vw.segnalatoCognome || ' ' || vw.segnalatoNome) like '%" + s.trim().toUpperCase()+"%'";
		}
		
		if(criteria.getUfficio() != null)
			sql += " AND UPPER(vw.accesso_Ufficio) like'%" + criteria.getUfficio().trim().toUpperCase()+"%'";
		
		if(criteria.getDataAccesso() != null)
			sql += " AND TO_CHAR(vw.accessoData, 'dd/mm/yyyy') like '%" + criteria.getDataAccesso().trim()+"%'";
		if(!count)
			sql += " ORDER BY (CASE WHEN ss.csASoggetto IS NULL THEN 0 ELSE 1 END), ss.dtIns DESC";
		
		return sql;
	}
	
	public void deleteSchedaSegr(Long obj) {
		Query q = em.createNamedQuery("CsSsSchedaSegr.deleteSchedaById");
		q.setParameter("idScheda", obj);
		q.executeUpdate();		
	}
}
