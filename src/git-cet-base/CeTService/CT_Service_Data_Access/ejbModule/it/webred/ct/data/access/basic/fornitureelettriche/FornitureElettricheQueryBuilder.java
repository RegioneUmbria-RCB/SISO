package it.webred.ct.data.access.basic.fornitureelettriche;


import it.webred.ct.data.access.basic.fornitureelettriche.dto.FornituraElettricaDTO;

public class FornitureElettricheQueryBuilder {
	
//	private final String SQL_SOGGETTI_NOTA = 
//		"SELECT DISTINCT "
//		+ "SOGG.TIPO_SOGGETTO, SOGG.COGNOME, SOGG.NOME, SOGG.CODICE_FISCALE, SOGG.DENOMINAZIONE, SOGG.CODICE_FISCALE_G, "
//		+ "TITO.SC_FLAG_TIPO_TITOL_NOTA , TITO.SF_FLAG_TIPO_TITOL_NOTA, "
//		+ "TITO.SC_CODICE_DIRITTO, TITO.SC_QUOTA_NUMERATORE, TITO.SC_QUOTA_DENOMINATORE, "
//		+ "TITO.SF_CODICE_DIRITTO, TITO.SF_QUOTA_NUMERATORE, TITO.SF_QUOTA_DENOMINATORE, "
//		+ "SOGG.SEDE, SOGG.SESSO, SOGG.DATA_NASCITA, SOGG.LUOGO_NASCITA, NOTA.IID "+
//	    "FROM MUI_NOTA_TRAS NOTA, MUI_SOGGETTI SOGG, MUI_TITOLARITA TITO " +
//		"WHERE TITO.IID_NOTA = SOGG.IID_NOTA  " +
//		  "AND TITO.IID_FORNITURA = SOGG.IID_FORNITURA " +
//		  "AND TITO.ID_SOGGETTO_NOTA = SOGG.ID_SOGGETTO_NOTA " +
//		  "AND SOGG.IID_NOTA = NOTA.IID " + 
//		  "AND SOGG.IID_FORNITURA = NOTA.IID_FORNITURA " + 
//		  "@SQL_CONDIZIONI_WHERE@ " +
//		  "ORDER BY SF_FLAG_TIPO_TITOL_NOTA, SC_FLAG_TIPO_TITOL_NOTA, COGNOME,NOME, DENOMINAZIONE ";
	
	private final String HQL_UTENZE_BY_PARAMS = "from SitEnelUtenza UTENZE where 1 = 1 @HQL_UTENZE_WHERE@ order by UTENZE.annoRiferimentoDati, UTENZE.codiceUtenza ";
	
	private final String HQL_UTENTI_BY_PARAMS = "from SitEnelUtente UTENTI where 1 = 1 @HQL_UTENTI_WHERE@ order by UTENTI.denominazione, UTENTI.dataNascita ";
	
	/*
	 * XXX AAA Se viene cambiata la clausola ORDER BY dovrà essere rivista anche la logica di popolamento 
	 * della lista restituita da GITOUT 
	 */
	private final String HQL_UTENZE_UTENTI_BY_PARAMS = "from SitEnelUtenza UTENZE, SitEnelUtente UTENTI where UTENZE.idExtEnelUtente = UTENTI.idExt @HQL_UTENZE_UTENTI_WHERE@ order by UTENTI.denominazione, UTENTI.codiceFiscale, UTENZE.annoRiferimentoDati, UTENZE.codiceUtenza ";
	
//	public String createQueryListaUtentiByCriteria(FornituraElettricaDTO criteria){
//		String sql = this.SQL_SOGGETTI_NOTA;
//				
//		String condSogg = "";
//		//condSogg = (criteria.getIidNota() == null ? condSogg : addOperator(condSogg) +  " NOTA.IID = '"+ criteria.getIidNota() +"' " );
//		
//		sql = sql.replace("@SQL_CONDIZIONI_WHERE@", condSogg);
//		
//		return sql;
//		
//	}//-------------------------------------------------------------------------
	
	public String createQueryListaUtenzeByCriteria(FornituraElettricaDTO criteria){
		
		String hql = this.HQL_UTENZE_BY_PARAMS;
		
		String condSogg = "";
		String indirizzo = "";
		indirizzo += ( ( criteria.getTipoArea() == null || criteria.getTipoArea().trim().equalsIgnoreCase("") ) ? "" : criteria.getTipoArea().trim() + " " );
		indirizzo += ( ( criteria.getVia() == null || criteria.getVia().trim().equalsIgnoreCase("") ) ? "" : criteria.getVia().trim() + " " );
		indirizzo += ( ( criteria.getCivico() == null || criteria.getCivico().trim().equalsIgnoreCase("") ) ? "" : criteria.getCivico().trim() + " " );
		
		condSogg = ( ( indirizzo == null || indirizzo.trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.indirizzoUbicazione LIKE '%"+ indirizzo.trim() +"%' " );
		condSogg = ( ( criteria.getAnnoUtenza() == null || criteria.getAnnoUtenza().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.annoRiferimentoDati = '"+ criteria.getAnnoUtenza().trim() +"' " );
		condSogg = ( ( criteria.getCodiceUtenza() == null || criteria.getCodiceUtenza().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.codiceUtenza = '"+ criteria.getCodiceUtenza().trim() +"' " );
		condSogg = ( ( criteria.getSezione() == null || criteria.getSezione().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.sezione = '"+ criteria.getSezione().trim() +"' " );
		condSogg = ( ( criteria.getFoglio() == null || criteria.getFoglio().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.foglio = '"+ criteria.getFoglio().trim() +"' " );
		condSogg = ( ( criteria.getParticella() == null || criteria.getParticella().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.particella = '"+ criteria.getParticella().trim() +"' " );
		condSogg = ( ( criteria.getSubalterno() == null || criteria.getSubalterno().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.subalterno = '"+ criteria.getSubalterno().trim() +"' " );
				
		hql = hql.replace("@HQL_UTENZE_WHERE@", condSogg);
		
		return hql;
	}//-------------------------------------------------------------------------
	
	public String createQueryListaUtentiByCriteria(FornituraElettricaDTO criteria){
		
		String hql = this.HQL_UTENTI_BY_PARAMS;
		
		String condSogg = "";
		condSogg = ( ( criteria.getDenominazione() == null || criteria.getDenominazione().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENTI.denominazione LIKE '%"+ criteria.getDenominazione().trim() +"%' " );
		/*
		 * Non è necessario distinguere attraverso la tipologia perchè cf e piva sono nello stesso campo 
		 */
		condSogg = ( ( criteria.getIdentificativo() == null || criteria.getIdentificativo().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENTI.codiceFiscale = '"+ criteria.getIdentificativo().trim() +"' " );
				
		hql = hql.replace("@HQL_UTENTI_WHERE@", condSogg);
		
		return hql;
	}//-------------------------------------------------------------------------
	
	public String createQueryListaFornitureElettricheByCriteria(FornituraElettricaDTO criteria){
		
		String hql = this.HQL_UTENZE_UTENTI_BY_PARAMS;
		
		String condSogg = "";
		String indirizzo = "";
		indirizzo += ( ( criteria.getTipoArea() == null || criteria.getTipoArea().trim().equalsIgnoreCase("") ) ? "" : criteria.getTipoArea().trim() + " " );
		indirizzo += ( ( criteria.getVia() == null || criteria.getVia().trim().equalsIgnoreCase("") ) ? "" : criteria.getVia().trim() + " " );
		indirizzo += ( ( criteria.getCivico() == null || criteria.getCivico().trim().equalsIgnoreCase("") ) ? "" : criteria.getCivico().trim() + " " );
		
		condSogg = ( ( indirizzo == null || indirizzo.trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.indirizzoUbicazione LIKE '%"+ indirizzo.trim() +"%' " );
		condSogg = ( ( criteria.getAnnoUtenza() == null || criteria.getAnnoUtenza().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.annoRiferimentoDati = '"+ criteria.getAnnoUtenza().trim() +"' " );
		condSogg = ( ( criteria.getCodiceUtenza() == null || criteria.getCodiceUtenza().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.codiceUtenza = '"+ criteria.getCodiceUtenza().trim() +"' " );
		condSogg = ( ( criteria.getSezione() == null || criteria.getSezione().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.sezione = '"+ criteria.getSezione().trim() +"' " );
		condSogg = ( ( criteria.getFoglio() == null || criteria.getFoglio().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.foglio = '"+ criteria.getFoglio().trim() +"' " );
		condSogg = ( ( criteria.getParticella() == null || criteria.getParticella().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.particella = '"+ criteria.getParticella().trim() +"' " );
		condSogg = ( ( criteria.getSubalterno() == null || criteria.getSubalterno().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENZE.subalterno = '"+ criteria.getSubalterno().trim() +"' " );
		condSogg = ( ( criteria.getDenominazione() == null || criteria.getDenominazione().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENTI.denominazione LIKE '%"+ criteria.getDenominazione().trim() +"%' " );
		/*
		 * Non è necessario distinguere attraverso la tipologia perchè cf e piva sono nello stesso campo 
		 */
		condSogg = ( ( criteria.getIdentificativo() == null || criteria.getIdentificativo().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " UTENTI.codiceFiscale = '"+ criteria.getIdentificativo().trim() +"' " );
		
		hql = hql.replace("@HQL_UTENZE_UTENTI_WHERE@", condSogg);
		
		return hql;
	}//-------------------------------------------------------------------------
	
	protected String addOperator(String criteria) {
    	if (criteria == null)
    	    return criteria;    	
    	else    	    
    	    return criteria += " AND ";
    }//-------------------------------------------------------------------------
	
}




