package it.webred.ct.config.audit.dto;

import java.text.SimpleDateFormat;
import java.util.List;

public class QueryBuilder {

	private AuditSearchCriteria criteria;
	
	private Integer id;
	
	private String dataInizio;
	private String dataFine;
	private String user;
	private String ente;
	private List<String> enti;
	private String chiave;
	private String fkAmFonte;
	private boolean exception;
	
	private List<String> order;
	
	public QueryBuilder(AuditSearchCriteria criteria) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		this.criteria = criteria;
		id = criteria.getId();
		dataInizio = criteria.getDataInizio() != null && !"".equals(criteria.getDataInizio())?sdf.format(criteria.getDataInizio()):null;
		dataFine = criteria.getDataFine() != null && !"".equals(criteria.getDataFine())?sdf.format(criteria.getDataFine()):null;
		user = criteria.getUser();
		ente = criteria.getEnte();
		enti = criteria.getEnti();
		exception = criteria.isException();
		chiave = criteria.getChiave();
		fkAmFonte = criteria.getFkAmFonte();
		order = criteria.getOrder();
	}
	
	
	public String createQuery(boolean isCount) {
			
		String sql = "SELECT a, " +
					"(SELECT MIN (dataIns) FROM AmAudit WHERE sessionId = a.sessionId) as minDtSessione, " +
					"(SELECT MAX (dataIns) FROM AmAudit WHERE sessionId = a.sessionId) AS maxDtSessione, " +
					"d " +
					" FROM AmAudit a, AmAuditDecode d";
		if(isCount)
			sql = "SELECT COUNT(a) FROM AmAudit a";
		
		String whereCond = getSQLCriteria();
		
		sql += " WHERE a.methodName = d.methodName AND a.className = d.className ";
		if (!"".equals(whereCond)) {
			sql += whereCond;
		}
		
		if(order != null && order.size() > 0){
			sql += " ORDER BY ";
			for(String o: order){
				if("descrizioneFonte".equals(o))
					sql += "d.amFonte.id, d.fkAmFonteTipoinfo,";
				else if("sessionId".equals(o))
					sql += "a.dataIns DESC, a.sessionId,";
				else sql += "a." + o + ",";
			}
			sql = sql.substring(0, sql.length()-1);
		}

		return sql;
	}
	
	public String createQueryForSingleColumn(String column) {
		
		String sql = "SELECT a."+ column +" FROM AmAudit a";
		
		String whereCond = getSQLCriteria();
		
		if (!"".equals(whereCond)) {
			sql += " WHERE 1 = 1 " + whereCond;
		}
		
		if(order != null && order.size() > 0){
			sql += " ORDER BY ";
			for(String o: order){
				sql += o + ",";
			}
			sql = sql.substring(0, sql.length()-1);
		}

		return sql;
	}
	
	public String getSQLCriteria() {
		String sqlCriteria = "";
		
		sqlCriteria = (id == null) ? sqlCriteria : addOperator(sqlCriteria) + " a.id = " + id;

		sqlCriteria = (user == null  || user.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " UPPER(a.userId) = '" + user.toUpperCase() + "'");
		
		if(enti != null  && enti.size()>0){
			String whereEnti = "";
			for(String e: enti){
				whereEnti += " '" + e + "',";
			}
			whereEnti = whereEnti.substring(0, whereEnti.length() - 1);
			sqlCriteria = addOperator(sqlCriteria) + " (a.enteId in ("+ whereEnti +") OR a.enteId is null)";
		}
				
		sqlCriteria = (ente == null  || ente.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " a.enteId = '" + ente + "'");
		
		sqlCriteria = (dataInizio == null || dataInizio.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " a.dataIns >= TO_DATE('" + dataInizio + "', 'dd/MM/yyyy')");
		
		sqlCriteria = (dataFine == null || dataFine.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " a.dataIns <= TO_DATE('" + dataFine + "', 'dd/MM/yyyy')");

		sqlCriteria = (!exception? sqlCriteria : addOperator(sqlCriteria) + " a.exception IS NOT NULL ");
		
		sqlCriteria = (fkAmFonte == null  || fkAmFonte.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + " d.amFonte.id = " + fkAmFonte);
		
		return sqlCriteria;
	}
	
	protected String addOperator(String criteria) {  	    
    	    return criteria += " AND ";
    }

}
