package it.webred.ct.data.access.basic.fabbricato;

import it.webred.ct.data.access.basic.common.utils.StringUtils;
import it.webred.ct.data.access.basic.fabbricato.dto.SearchFabbricatoDTO;

public class FabbricatoQueryBuilder {
		
	private SearchFabbricatoDTO criteria;
	
	private final  String SQL_SELECT_COUNT_LISTA = "SELECT count(*) as conteggio FROM ( @SQL_SELECT_BASE_LISTA@ )";	
	
	public FabbricatoQueryBuilder(SearchFabbricatoDTO criteria) {
		super();
		this.criteria = criteria;
	}
	public String getSelectClause() {
		String select="";
		if  (criteria.isExRurale())
			select= " SELECT DISTINCT SEZIONE,  LPAD(NVL(TRIM(FOGLIO),'0'),4,'0') AS FOGLIO, PARTICELLA, SUB AS SUBALTERNO";
		if  (criteria.isMaiDichiarato())
			select= " SELECT DISTINCT SEZIONE,  LPAD(NVL(TRIM(foglio),'0'),4,'0') AS FOGLIO, PARTICELLA ";
		return select;
	}
	
	public String getFromClause() {
		String from="";
		if  (criteria.isExRurale())
			from= " FROM FABBRICATO_EX_RURALE";
		if  (criteria.isMaiDichiarato())
			from= " FROM FABBRICATO_MAI_DICHIARATO";
		return from;
	}
	
	public String getWhereClause() {
		String where="";
		where = " WHERE 1=1 " + getSQL_ImmobileCriteria() ;
		
		return where;
	}
	public String createQuery(boolean isCount){
		String sql=null;
		String sqlI=getSelectClause() + getFromClause() + getWhereClause();
		if (isCount){
			sql = this.SQL_SELECT_COUNT_LISTA;
			sql = sql.replace("@SQL_SELECT_BASE_LISTA@", sqlI);
		}
		else {
			sql = sqlI;	
		}
				
		return sql;
				
	}
	
	private String getSQL_ImmobileCriteria() {
		String sqlCriteria = "";
		sqlCriteria = (criteria.getSezione() == null  || criteria.getSezione().trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) +  " SEZIONE = '" + criteria.getSezione() + "'");
		sqlCriteria = (criteria.getFoglio() == null  || criteria.getFoglio().trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) +  " LPAD(foglio, 4, '0') = '" + StringUtils.fill('0',"sx", criteria.getFoglio(),4) + "'");		
		sqlCriteria = (criteria.getParticella() == null || criteria.getParticella().trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) +  " LPAD(particella, 5, '0') = '" + StringUtils.fill('0',"sx", criteria.getParticella(),5) + "'");
		sqlCriteria = (criteria.getSub() == null || criteria.getSub().trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) +  " LPAD(sub, 5, '0') = '" + StringUtils.fill('0',"sx", criteria.getSub(),5) + "'");
		return sqlCriteria;
	}
	
	protected String addOperator(String criteria) {
    	if (criteria == null)
    	    return criteria;    	
    	else    	    
    	    return criteria += " AND ";
    }
}
