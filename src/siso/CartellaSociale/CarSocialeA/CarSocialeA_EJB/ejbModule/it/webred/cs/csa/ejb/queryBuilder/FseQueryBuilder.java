package it.webred.cs.csa.ejb.queryBuilder;

import it.webred.cs.csa.ejb.dto.fse.FseSearchCriteria;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;


public class FseQueryBuilder extends QueryBuilderBase{
	
	private static String DENOMINAZIONE = "denominazione";
	private static String TIPO_FSE = "tipoFse";
	private static String DATA_INIZIO  = "dataInizio";
	private static String DATA_FINE = "dataFine";
	private static String COMUNE_RESIDENZA = "residenza";
	private static String COD_FISCALE = "cf";
	private static String PROGETTO = "progetto";
	private static String ATTIVITA_ID = "attivitaId";
	private static String ORGANIZZAZIONE_ID = "organizzazioneId";
	
	public String getSqlFse(FseSearchCriteria psb, boolean count){
		String sql = "SELECT "+(count ? "COUNT(DISTINCT v.id)":"v.*") 
				  + " FROM CS_VISTA_FSE v ";
		
		if(psb.isOnlyFirstByCFProgetto()){
			sql += ", (select min(id) keep(dense_rank first order by data_Sottoscrizione asc, DECODE(tipo,1,2,2,1,3,3)) id, "+
				   " cf, progetto, min(data_sottoscrizione) data_sottoscrizione "+
				   " from CS_VISTA_FSE ";
		}
		
		sql+= "WHERE 1=1 ";
		sql = this.getFilterCondition(psb, sql);
		
		if(psb.isOnlyFirstByCFProgetto()){
			sql+=" group by cf, progetto) f where f.id = v.id ";
		}
		
		if(!count)
			sql+= " order by v.cognome, v.nome, v.progetto, v.data_Sottoscrizione desc";
			
		return sql;
	}
	
	public String getFilterCondition(FseSearchCriteria psb, String cond){
		cond+= psb.getOrganizzazioneId()!=null ? " and ORG_TITOLARE_ID = :"+ORGANIZZAZIONE_ID : "";
		cond+= !StringUtils.isBlank(psb.getDenominazione()) ? " and upper(cognome||' '||nome) " + concatLikeParam(DENOMINAZIONE) : "";
		cond+= !StringUtils.isBlank(psb.getCodiceFiscale()) ? " and upper(cf) " + concatLikeParam(COD_FISCALE) : "";
		cond+= !StringUtils.isBlank(psb.getResidenzaIstat()) ? " and RES_COMUNE_ID = :" + COMUNE_RESIDENZA : "";
		cond+= !StringUtils.isBlank(psb.getProgetto()) ? " and upper(progetto) "+ concatLikeParam(PROGETTO)  : "";
		cond+= !StringUtils.isBlank(psb.getCodAttivita()) ? " and upper(progetto_attivita_id) " + concatLikeParam(ATTIVITA_ID) : "";
		cond+= psb.getTipoFse()!=null ? " and tipo IN (:"+ TIPO_FSE +")": "";
		cond+= psb.getDataInizio()!=null ? " and data_sottoscrizione >= :"+DATA_INIZIO : "";
		cond+= psb.getDataFine()!=null ? " and data_sottoscrizione <= :"+DATA_FINE : "";
		
		return cond;
	}
	
	
	public String setFilterParameters(Query q, FseSearchCriteria criteria){
		String params = "";
		if(criteria.getOrganizzazioneId()!=null) 
			params = setParameter(q, ORGANIZZAZIONE_ID, criteria.getOrganizzazioneId(), params);
		if(!StringUtils.isBlank(criteria.getDenominazione())) 
			params = setParameter(q, DENOMINAZIONE, criteria.getDenominazione().toUpperCase(), params);
		if(!StringUtils.isBlank(criteria.getCodiceFiscale())) 
			params = setParameter(q, COD_FISCALE, criteria.getCodiceFiscale().toUpperCase(), params);
		if(!StringUtils.isBlank(criteria.getProgetto())) 
			params = setParameter(q, PROGETTO, criteria.getProgetto().toUpperCase(), params);
		if(!StringUtils.isBlank(criteria.getCodAttivita())) 
			params = setParameter(q, ATTIVITA_ID, criteria.getCodAttivita().toUpperCase(), params);
		if(!StringUtils.isBlank(criteria.getResidenzaIstat())) 
			params = setParameter(q, COMUNE_RESIDENZA, criteria.getResidenzaIstat().toUpperCase(), params);
		if(criteria.getTipoFse()!=null) 
			params = setParameter(q, TIPO_FSE, criteria.getTipoFse(), params);
		if(criteria.getDataInizio()!=null)
			params = setParameter(q, DATA_INIZIO, criteria.getDataInizio(), params);
		if(criteria.getDataFine()!=null)
			params = setParameter(q, DATA_FINE, criteria.getDataFine(), params);
		return params;
	}
	
	
}
