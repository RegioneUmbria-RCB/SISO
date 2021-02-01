package it.webred.ct.data.access.basic.traffico.dao;

import it.webred.ct.data.access.basic.traffico.dto.TrafficoSearchCriteria;


public class TrafficoQueryBuilder {
	
	private TrafficoSearchCriteria criteria;
	
	private String codFiscale;
	private String numVerbale;
	private String targa;
	private String nome;
	private String cognome;
	private String idOrig;
	private boolean nullCodFisc;
	
	public TrafficoQueryBuilder(TrafficoSearchCriteria criteria) {
		this.criteria = criteria;
		codFiscale = criteria.getCodFiscale();
		numVerbale = criteria.getNumVerbale();
		targa = criteria.getTarga();
		nome = criteria.getNome();
		cognome = criteria.getCognome();
		idOrig = criteria.getIdOrig();
		nullCodFisc = criteria.isNullCodFisc();
	}
	
	
	public String createQuery() {
					
		String sql = "SELECT multe FROM SitTrffMulte multe";
		
		String whereCond = getSQLCriteria();
		
		if (!"".equals(whereCond)) {
			sql += " WHERE dtFineVal is null " + whereCond;
		}
		
		sql += " ORDER BY multe.dataMulta desc";

		return sql;
	}
	
	public String getSQLCriteria() {
		String sqlCriteria = "";
		
		sqlCriteria = (codFiscale == null || codFiscale.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " UPPER(multe.codFisc) LIKE '" + codFiscale.toUpperCase() + "'");
				
		sqlCriteria = (numVerbale == null || numVerbale.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " UPPER(multe.nrVerbale) LIKE '" + numVerbale.toUpperCase() + "'");
		
		sqlCriteria = (targa == null || targa.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " UPPER(multe.targa) LIKE '" + targa.toUpperCase() + "'");

		sqlCriteria = (nome == null || nome.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " UPPER(multe.nome) LIKE '" + nome.toUpperCase() + "'");
		
		sqlCriteria = (cognome == null || cognome.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " UPPER(multe.cognome) LIKE '" + cognome.toUpperCase() + "'");

		sqlCriteria = (idOrig == null || idOrig.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " UPPER(multe.idOrig) = '" + idOrig.toUpperCase() + "'");
		
		sqlCriteria = (!nullCodFisc ? sqlCriteria : addOperator(sqlCriteria) + " multe.codFisc is null");
		
		return sqlCriteria;
	}
	
	protected String addOperator(String criteria) {  	    
    	    return criteria += " AND ";
    }
	
}
