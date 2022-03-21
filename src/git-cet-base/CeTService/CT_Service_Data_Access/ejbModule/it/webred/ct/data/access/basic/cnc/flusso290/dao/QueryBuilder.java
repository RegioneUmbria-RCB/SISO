package it.webred.ct.data.access.basic.cnc.flusso290.dao;

import it.webred.ct.data.access.basic.cnc.dao.AbstractQueryBuilder;
import it.webred.ct.data.access.basic.cnc.flusso290.dto.Flusso290SearchCriteria;

public class QueryBuilder extends AbstractQueryBuilder {

	private Flusso290SearchCriteria criteria;
	
	private String progressivoMinuta;
	private String codiceComuneIscrizione;
	private String codicePartita;
	private String codiceFiscale;
	private String codiceTributo;
	
	public QueryBuilder(Flusso290SearchCriteria criteria) {
		this.criteria = criteria;
		progressivoMinuta = criteria.getProgressivoMinuta();
		codiceComuneIscrizione = criteria.getCodiceComuneIscrizione();
		codicePartita = criteria.getCodicePartita();
		codiceFiscale = criteria.getCodiceFiscale();
		codiceTributo = criteria.getCodiceTributo();
	}
	
	
	public String createQuery(boolean isCount) {
		
		String sql = "";
		
		if (isCount)
			sql = "SELECT COUNT(anag)";
		else
			sql = "SELECT anag";
		
				
		sql += " FROM RAnagraficaIntestatarioRuolo anag";
		
		if (criteria.getCodiceTributo() != null && !criteria.getCodiceTributo().equals("")) {
			sql += " , RDatiContabili datiCont";
		}
		
		
		String whereCond = getSQLCriteria();
		
		if (whereCond.equals(""))
			return null;
		
		if (!"".equals(whereCond)) {
			sql += " WHERE " + whereCond;
		}
		
		// Add join conditions if necessary
		
		if (criteria.getCodiceTributo() != null && !criteria.getCodiceTributo().equals("")) {
			if (!"".equals(whereCond))
				sql += " AND";
			sql += " anag.codComuneIscrRuolo = datiCont.codComuneIscrRuolo AND anag.progressivoMinuta = datiCont.progressivoMinuta AND anag.codPartita = datiCont.codPartita";
		}
		
		sql += " ORDER BY anag.cognome, anag.denominazione, anag.nome";

		//logger.info("SQL ["+sql+"]");
		return sql;
	}
	
	private String getSQLCriteria() {
		String sqlCriteria = "";
		
		sqlCriteria = (progressivoMinuta == null  || progressivoMinuta.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " anag.progressivoMinuta='" + progressivoMinuta + "'");
		
		sqlCriteria = (codiceComuneIscrizione == null || codiceComuneIscrizione.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " anag.codComuneIscrRuolo='" + codiceComuneIscrizione +"'");
		
		sqlCriteria = (codicePartita == null || codicePartita.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " anag.codPartita LIKE '" + codicePartita + "'");

		sqlCriteria = (codiceFiscale == null || codiceFiscale.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " anag.codFiscale LIKE '" + codiceFiscale + "'");
		
		sqlCriteria = (codiceTributo == null || codiceTributo.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " datiCont.codTributo = " + codiceTributo);
		
		return sqlCriteria;
	}
	
	

}
