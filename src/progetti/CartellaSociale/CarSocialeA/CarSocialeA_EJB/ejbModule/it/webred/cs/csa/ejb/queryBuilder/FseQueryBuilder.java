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
		String sql = "SELECT "+(count ? "COUNT(DISTINCT v.id)":"v") 
				  + " FROM CsVistaFse v WHERE organizzazioneTitolareId = :"+ORGANIZZAZIONE_ID;
		sql = this.getFilterCondition(psb, sql);
		if(!count)
			sql+= " order by cognome, nome, progetto, dataSottoscrizione desc";
			
		return sql;
	}
	
	public String getFilterCondition(FseSearchCriteria psb, String cond){
		cond+= !StringUtils.isBlank(psb.getDenominazione()) ? " and upper(cognome||' '||nome) " + concatLikeParam(DENOMINAZIONE) : "";
		cond+= !StringUtils.isBlank(psb.getCodiceFiscale()) ? " and upper(cf) " + concatLikeParam(COD_FISCALE) : "";
		cond+= !StringUtils.isBlank(psb.getResidenzaIstat()) ? " and V.residenzaComuneId = :" + COMUNE_RESIDENZA : "";
		cond+= !StringUtils.isBlank(psb.getProgetto()) ? " and upper(v.progetto) "+ concatLikeParam(PROGETTO)  : "";
		cond+= !StringUtils.isBlank(psb.getCodAttivita()) ? " and upper(v.progettoAttivitaId) " + concatLikeParam(ATTIVITA_ID) : "";
		cond+= psb.getTipoFse()!=null ? " and v.tipoFse.id = :"+ TIPO_FSE : "";
		cond+= psb.getDataInizio()!=null ? " and v.dataSottoscrizione >= :"+DATA_INIZIO : "";
		cond+= psb.getDataFine()!=null ? " and v.dataSottoscrizione <= :"+DATA_FINE : "";
		
		return cond;
	}
	
	
	public String setFilterParameters(Query q, FseSearchCriteria criteria){
		String params = "";
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
