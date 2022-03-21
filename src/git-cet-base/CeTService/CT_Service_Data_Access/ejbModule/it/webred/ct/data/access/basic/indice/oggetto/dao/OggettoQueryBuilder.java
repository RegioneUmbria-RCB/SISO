package it.webred.ct.data.access.basic.indice.oggetto.dao;

import it.webred.ct.data.access.basic.indice.AbstractQueryBuilder;
import it.webred.ct.data.access.basic.indice.dto.IndiceSearchCriteria;

public class OggettoQueryBuilder extends AbstractQueryBuilder{

private IndiceSearchCriteria criteria;
	
	private String foglio;
	private String particella;
	private String sub;
	private String unicoId;
	private String enteSorgenteId;
	private String progressivoES;
	
	
	private final String SQL_LISTA_ENTE_SORGENTE_BY_UNICO = 
		"SELECT DISTINCT o, j.id.progEs FROM SitEnteSorgente o, SitOggettoTotale j " + 
		"WHERE j.fkOggetto = " + "@IDUNICO " +
		"AND o.id = j.id.fkEnteSorgente " +
		"ORDER BY o.descrizione, j.id.progEs";
	
	public OggettoQueryBuilder() {
	}
	
	public OggettoQueryBuilder(IndiceSearchCriteria criteria) {
		this.criteria = criteria;

		foglio = criteria.getFoglio();
		particella = criteria.getParticella();
		sub = criteria.getSub();
		unicoId = criteria.getUnicoId();
		enteSorgenteId = criteria.getEnteSorgenteId();
		progressivoES = criteria.getProgressivoES();
		
	}
	
	public String createQueryUnico(boolean isCount) {
		
		String sql = "";
		enteSorgenteId = "";
		progressivoES = "";
		
		if (isCount)
			sql = "SELECT COUNT(o)";
		else
			sql = "SELECT o";
		
				
		sql += " FROM SitOggettoUnico o";
			
		String whereCond = getSQLCriteria();
		
		if (whereCond.equals(""))
			return null;
		
		if (!"".equals(whereCond)) {
			sql += " WHERE " + whereCond;
		}
		
		sql += " ORDER BY o.foglio";
		
		return sql;
		
	}
	
	public String createQueryTotale(boolean isCount, boolean addUnico) {
		
		String sql = "";
		
		if (isCount)
			sql = "SELECT COUNT(DISTINCT o.id.ctrHash)";
		else
			sql = "SELECT DISTINCT o.id.ctrHash, o.foglio, o.particella, o.sub, o.validato, o.stato";
			
		if (addUnico && !isCount)
			sql += " , u";
		
		sql += " FROM SitOggettoTotale o";
		
		if (addUnico)
			sql += ", SitOggettoUnico u";
		
		String whereCond = getSQLCriteria();
		
		if (whereCond.equals(""))
			return null;
		
		if (!"".equals(whereCond)) {
			sql += " WHERE " + whereCond;
			
			if (addUnico)
				sql += " AND o.fkOggetto = u.idOggetto";
		}
		
		sql += " ORDER BY o.foglio";
		
		return sql;
		
	}
	
	public String createQueryEntiByUnico(String unicoId) {
		
		String sql = SQL_LISTA_ENTE_SORGENTE_BY_UNICO;
		sql = sql.replace("@IDUNICO", unicoId);
			
		return sql;
		
	}
	
	private String getSQLCriteria() {
		String sqlCriteria = "";
				
		sqlCriteria = (foglio == null  || foglio.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " LPAD(o.foglio, 4, '0') LIKE LPAD('" + foglio + "', 4, '0')");

		sqlCriteria = (particella == null  || particella.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " LPAD(o.particella, 5, '0') LIKE LPAD ('" + particella + "', 5, '0')");

		sqlCriteria = (sub == null  || sub.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " LPAD(NVL (o.sub, '0'), 4, '0') LIKE LPAD ('" + (sub != null?sub:'0') + "', 4, '0')");
		
		sqlCriteria = (unicoId == null  || unicoId.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " o.fkOggetto = " + unicoId);

		sqlCriteria = (enteSorgenteId == null  || enteSorgenteId.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " o.id.fkEnteSorgente = " + enteSorgenteId);

		sqlCriteria = (progressivoES == null  || progressivoES.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " o.id.progEs = " + progressivoES);
		
		return sqlCriteria;
	}
	
}
