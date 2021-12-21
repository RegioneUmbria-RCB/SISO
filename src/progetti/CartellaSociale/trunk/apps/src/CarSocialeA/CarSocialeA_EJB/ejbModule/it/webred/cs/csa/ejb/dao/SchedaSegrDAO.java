package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.dto.SchedaSegrDTO;
import it.webred.cs.csa.ejb.dto.udc.DatiSchedaAccessoDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsSsSchedaSegr;
import it.webred.cs.data.model.view.VSsSchedeUdcDiff;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

@Named
public class SchedaSegrDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;
		
		public CsSsSchedaSegr findSchedaSegrBySchedaIdProvenienza(Long schedaId, String provenienza) {
			Query q = em.createNamedQuery("CsSsSchedaSegr.findSchedaSegrBySchedaIdProvenienza");
			q.setParameter("schedaId", schedaId);
			q.setParameter("provenienza", provenienza);
			
			List<CsSsSchedaSegr> lista = q.getResultList();
			if(lista != null && lista.size() > 0)
				return lista.get(0);
				
			return null;
		}
	
	public List<CsSsSchedaSegr> findSchedaSegrByIdAnagrafica(Long idAnagrafica, String stato) {
		Query q = em.createNamedQuery("CsSsSchedaSegr.findByIdAnagraficaStato");
		q.setParameter("idAnagrafica", idAnagrafica);
		q.setParameter("stato", stato);
		List<CsSsSchedaSegr> lista = q.getResultList();	
		return lista;
	}
	
	// SISO-938
	public List<CsSsSchedaSegr> findSchedaSegrByIdAnagraficaNotSS(Long idAnagrafica) {
		Query q = em.createNamedQuery("CsSsSchedaSegr.findByIdAnagraficaNotSS")
				.setParameter("idAnagrafica", idAnagrafica)
				.setParameter("provenienza", DataModelCostanti.SchedaSegr.PROVENIENZA_SS);
		
		return q.getResultList();
	}
	
	public void saveSchedaSegr(CsSsSchedaSegr newScheda) {
		em.persist(newScheda);
	}
	
	public CsSsSchedaSegr updateSchedaSegr(CsSsSchedaSegr scheda) {
		return em.merge(scheda);
	}
	
	public void aggiornaCsSsSchedaSegr(Long idScheda, Long idSoggetto) throws Throwable{
		try{
		String sql = "UPDATE CS_SS_SCHEDA_SEGR SET SOGGETTO_ID= :idSoggetto ";
		Query q = em.createNativeQuery(sql);
		q.setParameter("idSoggetto", idSoggetto);
		q.executeUpdate();
		}catch(Throwable t){
			logger.error(t.getMessage(), t);
			throw t;
		}
	}

	@SuppressWarnings("unchecked")
	public List<DatiSchedaAccessoDTO> getSchedeSegr(SchedaSegrDTO criteria) throws Throwable {
		List<DatiSchedaAccessoDTO> lst = new ArrayList<DatiSchedaAccessoDTO>();
		try{
			String sql = getQuery(false, criteria);
			Integer first= criteria.getFirst();
			int pageSize= criteria.getPageSize();
			logger.debug("ListaSchedeSegretariato - SQL["+sql+"]");
			Query q = em.createNativeQuery(sql);
			
			if(first != null) {
				q.setFirstResult(first);
				q.setMaxResults(pageSize);
			}
			
			List<Object[]> res = (List<Object[]>) q.getResultList();
			
			for(Object[] o : res){
				DatiSchedaAccessoDTO sa = new DatiSchedaAccessoDTO();
				
				int i = 0;
				sa.setProvenienza((String)o[i++]);
				sa.setProvenienzaDesc((String)o[i++]);
				sa.setProvenienzaTooltip((String)o[i++]);
				
				BigDecimal idScheda = (BigDecimal)o[i++];
				sa.setId(idScheda!=null ? idScheda.longValue() : null);
				sa.setDataAccesso((Date)o[i++]);
				sa.setUfficio((String)o[i++]);
				sa.setOperatore((String)o[i++]);
				
				sa.setCognome((String)o[i++]);
				sa.setNome((String)o[i++]);
				sa.setCf((String)o[i++]);
				
				List<String> categoriaSociale = new ArrayList<String>();
				String csoc = (String)o[i++];
				if(csoc!=null)
					categoriaSociale.add(csoc);
				
				sa.setCategoriaSociale(categoriaSociale);
				sa.setTipo((String)o[i++]);
				
				BigDecimal idCsSs = (BigDecimal)o[i++];
				sa.setCsSsId(idCsSs!=null ? idCsSs.longValue() : null);
				sa.setStato((String)o[i++]);
				sa.setNotaStato((String)o[i++]);
				BigDecimal esistente = (BigDecimal)o[i++];
				sa.setEsistente(esistente!=null && esistente.intValue() == 1);
				
				BigDecimal idSoggetto = (BigDecimal)o[i++];
				sa.setSoggettoId(idSoggetto!=null ? idSoggetto.longValue() : null);
				
				BigDecimal idCaso = (BigDecimal)o[i++];
				sa.setCasoId(idCaso!=null ? idCaso.longValue() : null);
						
				lst.add(sa);
			}
		}catch(Throwable t){
			logger.error(t.getMessage(), t);
			throw t;
		}
		return lst;
	}
	
	public Integer getSchedeSegrCount(boolean onlyNew,SchedaSegrDTO criteria) {
		
		criteria.setOnlyNew(onlyNew);
		
		String query = getQuery(true, criteria);
		Query q = em.createNativeQuery(query);
		BigDecimal o = (BigDecimal) q.getSingleResult();
		return new Integer(o.intValue());
	}
	
	private String getQuery(boolean count, SchedaSegrDTO criteria) {
		
		String sql = "";
		String join ="";
		if(!count) {
			sql += "SELECT DISTINCT  tb.id, tb.descrizione, tb.tooltip, VW.ID_SCHEDA, "+ 
					"VW.ACCESSO_DATA, VW.ACCESSO_UFFICIO,  "+
					"NVL(TRIM(VW.ACCESSO_OPER_COGNOME||' '||VW.ACCESSO_OPER_NOME),VW.ACCESSO_OPER_CF) accesso_operatore, "+
					"VW.SEGNALATO_COGNOME, VW.SEGNALATO_NOME, VW.SEGNALATO_CF, "+
					"CATSOC.DESCRIZIONE cat_sociale, VW.TIPO_INTERVENTO,  "+
					"ss.id cs_Ss_Id, SS.STATO, SS.NOTA_STATO, SS.FLG_ESISTENTE, SS.SOGGETTO_ID, C.ID caso_ID ";
			
			join = " left join Cs_C_CATEGORIA_SOCIALE catsoc ON (vw.categoria_sociale_id = catsoc.id) "+
					"left join CS_TB_SS_PROVENIENZA tb ON (vw.provenienza = tb.id) "+
					"left join CS_A_SOGGETTO s ON (ss.soggetto_id = s.anagrafica_id) "+
					"left join CS_A_CASO c ON (c.id = s.caso_id) ";
			
		}
		else sql += "SELECT COUNT(*) ";
		
		if(criteria.isSearchBySoggetto()){
			sql += " FROM Cs_Vista_Schede vw left join Cs_Ss_Scheda_Segr ss  on (vw.id_Scheda = ss.scheda_Id AND vw.provenienza = ss.provenienza) " + join +
					" WHERE (ss.cod_Ente ='"+criteria.getEnteId()+"' OR vw.accesso_Cod_Ente='"+criteria.getEnteId()+"')";
		}else{
			sql += " FROM Cs_Rel_Settore_Catsoc cs, Cs_O_SETTORE s, CS_O_ORGANIZZAZIONE o, Cs_Ss_Scheda_Segr ss "+
					"LEFT JOIN Cs_Vista_Casi_SS vw ON (vw.id_Scheda = ss.scheda_Id AND vw.provenienza = ss.provenienza)  " + join +
					" WHERE vw.id_Scheda = ss.scheda_Id" +
					" AND vw.provenienza = ss.provenienza" +
					" AND vw.categoria_sociale_id = cs.categoria_Sociale_Id "+
					" AND cs.settore_id = s.id AND s.organizzazione_id = o.id "+
					" AND ss.cod_Ente = o.cod_Routing ";
			
			sql += criteria.getIdSettore() != null ? " AND cs.settore_Id = " + criteria.getIdSettore() : "";
		}
		
		if(criteria.isOnlyNew())
			sql += " AND ss.soggetto_Id is null ";
		
		if(criteria.getOperatore() != null )
			sql += " AND ( UPPER(vw.accesso_Oper_Cognome|| ' '  ||vw.accesso_Oper_Nome) like '%" + criteria.getOperatore() .toUpperCase()+"%'  OR UPPER(vW.accesso_Oper_Cf) like '%"+ criteria.getOperatore() .toUpperCase() +"%') )";
		
		if(criteria.getSoggettoSegnalato() != null ){
		    String s = criteria.getSoggettoSegnalato().replaceAll("'", "''");
			sql += " AND UPPER(vw.segnalato_Cognome || ' ' || vw.segnalato_Nome) like '%" + s.trim().toUpperCase()+"%'";
		}
		
		if(criteria.isSearchBySoggetto()){
			if(!StringUtils.isBlank(criteria.getCf()) && criteria.getIdAnagrafica()!=null )
				sql += " AND (UPPER(vW.segnalato_Cf) like '%" + criteria.getCf().trim().toUpperCase()+"%' OR ss.soggetto_Id= " + criteria.getIdAnagrafica() +")";
		}else{
			if(criteria.getIdAnagrafica() != null)
				sql += " AND ss.soggetto_Id = " + criteria.getIdAnagrafica();
			if(!StringUtils.isBlank(criteria.getCf()))
				sql += " AND UPPER(vW.segnalato_Cf) like '%" + criteria.getCf().trim().toUpperCase()+"%'";
		}
		
		if(criteria.getUfficio() != null)
			sql += " AND UPPER(vw.accesso_Ufficio) like'%" + criteria.getUfficio().trim().toUpperCase()+"%'";
		
		if(criteria.getTipoIntervento()!= null ){
		    String s = criteria.getTipoIntervento().replaceAll("'", "''");
			sql += " AND UPPER(vw.tipo_Intervento) like '%" + s.trim().toUpperCase()+"%'";
		}
		if(criteria.getCategoriaSociale() != null){
			String s = criteria.getCategoriaSociale().replaceAll("'", "''");
			sql += "AND UPPER(catsoc.descrizione) like '%"+s.trim().toUpperCase()+"%'";
		}
		
		if(criteria.getDataAccesso() != null)
			sql += " AND TO_CHAR(vw.accesso_Data, 'dd/mm/yyyy') like '%" + criteria.getDataAccesso().trim()+"%'";

		// SISO-938 nuova colonna su cui filtrare: Tipo di scheda
		if(criteria.getLstProvenienza()!=null){
			String lst = "";
			for(String val : criteria.getLstProvenienza())
				lst+= "'"+val+"',";
			lst = !lst.isEmpty()  ? lst.substring(0, lst.length()-1) : lst;
			sql += lst.equals("") ? "" :  " AND vw.provenienza IN ("+lst+")";
		}
		
		if (!count)
			sql += " ORDER BY (CASE WHEN ss.soggetto_Id IS NULL THEN 0 ELSE 1 END), vw.accesso_data DESC";
		
		return sql;
	}
	
	public void deleteSchedaSegr(Long schedaId, String provenienza) {
		Query q = em.createNamedQuery("CsSsSchedaSegr.deleteSchedaBySchedaIdProvenienza")
				.setParameter("schedaId", schedaId)
				.setParameter("provenienza", provenienza);
		
		q.executeUpdate();
	}
	
	public Long findSchedaAggiornataUDCSoggetto(String cf) {
		VSsSchedeUdcDiff v = em.find(VSsSchedeUdcDiff.class, cf.toUpperCase());
		return v!=null ? v.getIdScheda().longValue() : null;
	}

	public CsSsSchedaSegr findCsSsSchedaSegr(Long id) {
		return em.find(CsSsSchedaSegr.class, id);
	}
}
