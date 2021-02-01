package it.webred.ct.data.access.basic.indice.via.dao;

import it.webred.ct.data.access.basic.indice.AbstractQueryBuilder;
import it.webred.ct.data.access.basic.indice.dto.IndiceSearchCriteria;

public class ViaQueryBuilder extends AbstractQueryBuilder{

	
	private IndiceSearchCriteria criteria;
	
	private String indirizzo;
	private String unicoId;
	private String enteSorgenteId;
	private String progressivoES;
	
	private final String SQL_LISTA_ENTE_SORGENTE_BY_UNICO = 
		"SELECT DISTINCT o, j.id.progEs from SitEnteSorgente o, SitViaTotale j " + 
		"WHERE j.fkVia = @IDUNICO " +
		"AND o.id = j.id.fkEnteSorgente " +
		"ORDER BY o.descrizione, j.id.progEs";
	
		/*"SELECT DISTINCT o.*, j.prog_Es from Sit_Ente_Sorgente o, Sit_Via_Totale j "+
		"WHERE j.fk_Via = @IDUNICO AND o.id = j.fk_Ente_Sorgente "+
		"ORDER BY o.descrizione, j.prog_Es ";*/

	public ViaQueryBuilder() {
	}
	
	public ViaQueryBuilder(IndiceSearchCriteria criteria) {
		this.criteria = criteria;

		indirizzo = criteria.getIndirizzo();
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
		
				
		sql += " FROM SitViaUnico o";
			
		String whereCond = getSQLCriteria();
		
		if (whereCond.equals(""))
			return null;
		
		if (!"".equals(whereCond)) {
			sql += " WHERE " + whereCond;
		}
		
		sql += " ORDER BY o.indirizzo";
		
		return sql;
		
	}
	
	public String createQueryTotale(boolean isCount, boolean addUnico) {
		
		String sql = "";
		
		if (isCount)
			sql = "SELECT COUNT(DISTINCT o.id.ctrHash)";
		else
			sql = "SELECT DISTINCT o.id.ctrHash, o.sedime, o.indirizzo, o.validato, o.stato, o.id.fkEnteSorgente";
			
		if (addUnico && !isCount)
			sql += " , u";
		
		sql += " FROM SitViaTotale o";
		
		if (addUnico)
			sql += ", SitViaUnico u";
		
		String whereCond = getSQLCriteria();
		
		if (whereCond.equals(""))
			return null;
		
		if (!"".equals(whereCond)) {
			sql += " WHERE " + whereCond;
			
			if (addUnico)
				sql += " AND o.fkVia = u.idVia";
		}
		
		sql += " ORDER BY o.indirizzo";
		
		return sql;
		
	}
	
	public String createQueryEntiByUnico(String unicoId) {
		
		String sql = SQL_LISTA_ENTE_SORGENTE_BY_UNICO;
		sql = sql.replace("@IDUNICO", unicoId);
			
		return sql;
		
	}
	
	private String getSQLCriteria() {
		String sqlCriteria = "";
				
		sqlCriteria = (indirizzo == null  || indirizzo.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " UPPER(o.indirizzo) LIKE '%" + indirizzo.toUpperCase() + "%'");

		sqlCriteria = (unicoId == null  || unicoId.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " o.fkVia = " + unicoId);

		sqlCriteria = (enteSorgenteId == null  || enteSorgenteId.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " o.id.fkEnteSorgente = " + enteSorgenteId);

		sqlCriteria = (progressivoES == null  || progressivoES.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " o.id.progEs = " + progressivoES);
		
		return sqlCriteria;
	}
	
}
