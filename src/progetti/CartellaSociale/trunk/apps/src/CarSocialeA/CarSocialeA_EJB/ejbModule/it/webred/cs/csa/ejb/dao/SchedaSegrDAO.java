package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.dto.SchedaSegrDTO;
import it.webred.cs.data.model.CsSsSchedaSegr;
import it.webred.cs.data.model.view.VSsSchedeUdcDiff;

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
	
/*	public CsCCategoriaSocialeBASIC findCatSocialeSchedaSegrById(Long id) {
		String sql = "SELECT c FROM SS_REL_SCHEDA_CAT_SOC rel, CS_C_CATEGORIA_SOCIALE c WHERE csoc.scheda_id="+id;
		Query q = em.createNativeQuery(sql, CsCCategoriaSocialeBASIC.class);
		List<CsCCategoriaSocialeBASIC> lst = q.getResultList();
		if(lst != null && lst.size() > 0)
			return lst.get(0);
			
		return null;
	}*/

	public List<CsSsSchedaSegr> findSchedaSegrByIdAnagrafica(Long idAnagrafica, String stato) {
		Query q = em.createNamedQuery("CsSsSchedaSegr.findByIdAnagraficaStato");
		q.setParameter("idAnagrafica", idAnagrafica);
		q.setParameter("stato", stato);
		List<CsSsSchedaSegr> lista = q.getResultList();	
		return lista;
	}
	
	public void saveSchedaSegr(CsSsSchedaSegr newScheda) {
		em.persist(newScheda);
	}
	
	public void updateSchedaSegr(CsSsSchedaSegr scheda) {
		em.merge(scheda);
	}
	
	public void aggiornaCsSsSchedaSegr(Long idScheda, Long idSoggetto) throws Throwable{
		try{
		String sql = "UPDATE CS_SS_SCHEDA_SEGR SET SOGGETTO_ID="+idSoggetto;
		Query q = em.createNativeQuery(sql);
		q.executeUpdate();
		}catch(Throwable t){
			logger.error(t.getMessage(), t);
			throw t;
		}
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
				" WHERE vw.csCCategoriaSociale.id = cs.id.categoriaSocialeId "+
				" AND vw.idScheda=ss.id"+
				" AND ss.codEnte=cs.csOSettore.csOOrganizzazione.codRouting ";
		
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
		
		if(criteria.getTipoIntervento()!= null ){
		    String s = criteria.getTipoIntervento().replaceAll("'", "''");
			sql += " AND UPPER(vw.tipoIntervento) like '%" + s.trim().toUpperCase()+"%'";
		}
		if(criteria.getCategoriaSociale() != null){
			String s = criteria.getCategoriaSociale().replaceAll("'", "''");
			sql += "AND UPPER(vw.csCCategoriaSociale.descrizione) like '%"+s.trim().toUpperCase()+"%'";
		}
		
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

	public Long findSchedaAggiornataUDCSoggetto(String cf) {
		VSsSchedeUdcDiff v = em.find(VSsSchedeUdcDiff.class, cf.toUpperCase());
		return v!=null ? v.getIdScheda().longValue() : null;
	}
}
