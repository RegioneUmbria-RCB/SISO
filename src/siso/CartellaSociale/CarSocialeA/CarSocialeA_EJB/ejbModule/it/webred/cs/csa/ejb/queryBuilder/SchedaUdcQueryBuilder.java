package it.webred.cs.csa.ejb.queryBuilder;

import java.util.Arrays;

import it.webred.cs.csa.ejb.dto.SchedaSegrDTO;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;


public class SchedaUdcQueryBuilder extends QueryBuilderBase{
	

	private static final String ORGANIZZAZIONE_ID = "organizzazioneId";
	private static final String SETTORE_ID = "settoreId";
	private static final String OPERATORE = "operatore";
	private static final String DENOMINAZIONE = "denominazione";
	private static final String COD_FISCALE = "cf";
	private static final String ANAGRAFICA_ID = "anagraficaId";
	private static final String ACCESSO_DATA = "accessoData";
	private static final String ACCESSO_UFFICIO = "accessoUfficio";
	private static final String TIPO_INTERVENTO = "tipoIntervento";
	private static final String CAT_SOCIALE = "catSociale";
	private static final String PROVENIENZA = "provenienza";
	
	private SchedaSegrDTO criteria;
	
	public SchedaUdcQueryBuilder(SchedaSegrDTO criteria){
		this.criteria = criteria;
	}
	
	public String getQuery(boolean count) {
		
		String sql = "";
		String join = " left join Cs_C_CATEGORIA_SOCIALE catsoc ON (vw.categoria_sociale_id = catsoc.id) "+
				"left join CS_TB_SS_PROVENIENZA tb ON (vw.provenienza = tb.id) "+
				"left join CS_A_SOGGETTO s ON (ss.soggetto_id = s.anagrafica_id) "+
				"left join CS_A_CASO c ON (c.id = s.caso_id) ";
		
		if(!count) {
			sql += "SELECT DISTINCT  tb.id, tb.descrizione, tb.tooltip, VW.ID_SCHEDA, "+ 
					"VW.ACCESSO_DATA, VW.ACCESSO_UFFICIO,  "+
					"NVL(TRIM(VW.ACCESSO_OPER_COGNOME||' '||VW.ACCESSO_OPER_NOME),VW.ACCESSO_OPER_CF) accesso_operatore, "+
					"VW.SEGNALATO_COGNOME, VW.SEGNALATO_NOME, VW.SEGNALATO_CF, "+
					"CATSOC.DESCRIZIONE cat_sociale, VW.TIPO_INTERVENTO,  "+
					"ss.id cs_Ss_Id, SS.STATO, SS.NOTA_STATO, SS.FLG_ESISTENTE, SS.SOGGETTO_ID, C.ID caso_ID ";	
		}
		else sql += "SELECT COUNT(*) ";
		
		if(criteria.isSearchBySoggetto()){
			sql += " FROM Cs_Vista_Schede vw left join Cs_Ss_Scheda_Segr ss  on (vw.id_Scheda = ss.scheda_Id AND vw.provenienza = ss.provenienza) " + join +
					" WHERE (ss.cod_Ente = :"+ORGANIZZAZIONE_ID+" OR vw.accesso_Cod_Ente= :"+ORGANIZZAZIONE_ID+")";
		}else{
			sql += " FROM Cs_Rel_Settore_Catsoc cs, Cs_O_SETTORE s, CS_O_ORGANIZZAZIONE o, Cs_Ss_Scheda_Segr ss "+
					"LEFT JOIN Cs_Vista_Casi_SS vw ON (vw.id_Scheda = ss.scheda_Id AND vw.provenienza = ss.provenienza)  " + join +
					" WHERE vw.id_Scheda = ss.scheda_Id" +
					" AND vw.provenienza = ss.provenienza" +
					" AND vw.categoria_sociale_id = cs.categoria_Sociale_Id "+
					" AND cs.settore_id = s.id AND s.organizzazione_id = o.id "+
					" AND ss.cod_Ente = o.cod_Routing ";
			
			sql += criteria.getIdSettore() != null ? " AND cs.settore_Id = :" + SETTORE_ID : "";
		}
		
		if(criteria.isOnlyNew())
			sql += " AND ss.soggetto_Id is null ";
		
		sql += !StringUtils.isBlank(criteria.getOperatore()) ? 
				" AND ( UPPER(vw.accesso_Oper_Cognome||' '||vw.accesso_Oper_Nome) "+concatLikeParam(OPERATORE)+" OR UPPER(vW.accesso_Oper_Cf) "+concatLikeParam(OPERATORE)+" )" : "";
		
		sql += !StringUtils.isBlank(criteria.getSoggettoSegnalato()) ? 
				" AND UPPER(vw.segnalato_Cognome || ' ' || vw.segnalato_Nome) "+concatLikeParam(DENOMINAZIONE) : "";
		
		if(criteria.isSearchBySoggetto()){
			if(!StringUtils.isBlank(criteria.getCf()) && criteria.getIdAnagrafica()!=null )
				sql += " AND (UPPER(vW.segnalato_Cf) "+concatLikeParam(COD_FISCALE)+" OR ss.soggetto_Id= :" + ANAGRAFICA_ID +")";
		}else{
				sql += criteria.getIdAnagrafica() != null ? " AND ss.soggetto_Id = :" + ANAGRAFICA_ID : "";
				sql += !StringUtils.isBlank(criteria.getCf()) ? " AND UPPER(vW.segnalato_Cf) "+concatLikeParam(COD_FISCALE):"";
		}
		
		sql += !StringUtils.isBlank(criteria.getUfficio()) ? " AND UPPER(vw.accesso_Ufficio) "+concatLikeParam(ACCESSO_UFFICIO) : "";
		sql += !StringUtils.isBlank(criteria.getTipoIntervento()) ? " AND UPPER(vw.tipo_Intervento) "+concatLikeParam(TIPO_INTERVENTO):"";
		sql += !StringUtils.isBlank(criteria.getCategoriaSociale()) ? " AND UPPER(catsoc.descrizione) "+concatLikeParam(CAT_SOCIALE):"";
		sql += !StringUtils.isBlank(criteria.getDataAccesso()) ? " AND TO_CHAR(vw.accesso_Data, 'dd/mm/yyyy') "+concatLikeParam(ACCESSO_DATA):"";

		// SISO-938 nuova colonna su cui filtrare: Tipo di scheda
		sql += (criteria.getLstProvenienza()!=null && criteria.getLstProvenienza().length>0) ? " AND vw.provenienza IN (:"+PROVENIENZA+")" : "";
		
		if (!count)
			sql += " ORDER BY (CASE WHEN ss.soggetto_Id IS NULL THEN 0 ELSE 1 END), vw.accesso_data DESC";
		
		return sql;
	}

	
	public String setFilterParameters(Query q){
		String params = "";
		if(criteria.isSearchBySoggetto()){
			if(!StringUtils.isBlank(criteria.getEnteId())) 
				params = setParameter(q, ORGANIZZAZIONE_ID, criteria.getEnteId(), params);
			
			if(!StringUtils.isBlank(criteria.getCf()) && criteria.getIdAnagrafica()!=null){
				params = setParameter(q, ANAGRAFICA_ID, criteria.getIdAnagrafica(), params);
				params = setParameter(q, COD_FISCALE, criteria.getCf().trim().toUpperCase(), params);
			}
			
		}else{ 
		  if(criteria.getIdSettore()!=null)
			  params = setParameter(q, SETTORE_ID, criteria.getSettoreId(), params);
		  
		  if(criteria.getIdAnagrafica() != null)
			  params = setParameter(q, ANAGRAFICA_ID, criteria.getIdAnagrafica(), params);
		  
			if(!StringUtils.isBlank(criteria.getCf()))
				params = setParameter(q, COD_FISCALE, criteria.getCf().trim().toUpperCase(), params);
		}
		
		if(!StringUtils.isBlank(criteria.getOperatore())) 
			params = setParameter(q, OPERATORE, criteria.getOperatore().trim().toUpperCase(), params);
		if(!StringUtils.isBlank(criteria.getSoggettoSegnalato())) 
			params = setParameter(q, DENOMINAZIONE, criteria.getSoggettoSegnalato().trim().toUpperCase(), params);
		if(!StringUtils.isBlank(criteria.getUfficio())) 
			params = setParameter(q, ACCESSO_UFFICIO, criteria.getUfficio().trim().toUpperCase(), params);
		if(!StringUtils.isBlank(criteria.getTipoIntervento())) 
			params = setParameter(q, TIPO_INTERVENTO, criteria.getTipoIntervento().trim().toUpperCase(), params);
		if(!StringUtils.isBlank(criteria.getCategoriaSociale())) 
			params = setParameter(q, CAT_SOCIALE, criteria.getCategoriaSociale().trim().toUpperCase(), params);
		if(!StringUtils.isBlank(criteria.getDataAccesso())) 
			params = setParameter(q, ACCESSO_DATA, criteria.getDataAccesso().trim(), params);
		if((criteria.getLstProvenienza()!=null && criteria.getLstProvenienza().length>0))
			params = setParameter(q, PROVENIENZA, Arrays.asList(criteria.getLstProvenienza()), params);
	
		return params;
	}
	
	
}
