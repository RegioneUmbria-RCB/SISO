package it.webred.ct.data.access.basic.segnalazionequalificata.dao;

import java.text.SimpleDateFormat;

import it.webred.ct.data.access.basic.segnalazionequalificata.dto.RicercaPraticaSegnalazioneDTO;

public class PraticaSegnalazioneQueryBuilder {

	private RicercaPraticaSegnalazioneDTO criteri ;
	
	private String SQL_PRATICA = "FROM SOfPratica p WHERE 1 = 1 ";
	private final static String ORDER_BY  =" ORDER BY p.accDataInizio, p.accDataFine ";
	
	public PraticaSegnalazioneQueryBuilder(RicercaPraticaSegnalazioneDTO criteri) {
		this.criteri = criteri;
	}
	
	public String createQuery(boolean isCount) {
		String sql = "";
		if (criteri != null){
	
			if (isCount) sql = "SELECT COUNT(DISTINCT p) ";
			else         sql = "SELECT DISTINCT p ";
			
			sql += SQL_PRATICA;
			sql += getSQLCriteria();
			sql += ORDER_BY;
		
		}
		return sql;
	}
	
	private String getSQLCriteria() {
		String sqlCriteria = "";		
		SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
		
		//Ricerca Per Soggetto Accertato
		sqlCriteria += concatStringCriteria(criteri.getAccCodiFisc(),"p.accCodiFisc");
		sqlCriteria += concatStringCriteria(criteri.getAccCognome(), "p.accCognome");
		sqlCriteria += concatStringCriteria(criteri.getAccNome(),    "p.accNome");
		
		sqlCriteria += concatStringCriteria(criteri.getAccCodiPiva(),      "p.accCodiPiva");
		sqlCriteria += concatStringCriteria(criteri.getAccDenominazione(), "p.accDenominazione");
	
		//Ricerca per Responsabile del Procedimento
		sqlCriteria += concatStringCriteria(criteri.getResCodiFisc(),"p.resCodiFisc");
		sqlCriteria += concatStringCriteria(criteri.getResCognome(), "p.resCognome");
		sqlCriteria += concatStringCriteria(criteri.getResNome(),    "p.resNome");
		
		//Ricerca per Dati del Procedimento
		sqlCriteria += concatStringCriteria(criteri.getFinalita(),   "p.accFinalita");
		sqlCriteria += concatStringCriteria(criteri.getStato(),   "p.accStato");
		
		sqlCriteria = (criteri.getDataInizioDa() == null ? sqlCriteria : sqlCriteria +" AND p.accDataInizio >= TO_DATE('" + yyyyMMdd.format(criteri.getDataInizioDa()) + "','yyyyMMdd') ");
		sqlCriteria = (criteri.getDataInizioA() == null  ? sqlCriteria : sqlCriteria +" AND p.accDataInizio <= TO_DATE('" + yyyyMMdd.format(criteri.getDataInizioA()) + "','yyyyMMdd') ");

		
		int i = 0;
		String lista = "(";
		for(String operatore : criteri.getOperatori()){
			
			lista += " '"+ operatore +"' ";
			lista = (i == (criteri.getOperatori().size()-1)? lista : lista + ",");
			i++;
			
		}
		lista += ")";
		
		sqlCriteria = (criteri.getOperatori().isEmpty() ? sqlCriteria : sqlCriteria + " AND p.operatoreId IN " + lista);
	
		
		return sqlCriteria;
	}

	
	private String concatStringCriteria(String value, String column){
		
		return (value == null  ||value.equals("") ? "" : " AND "+ column + " = '" + value + "'");
	
	}
	
}
