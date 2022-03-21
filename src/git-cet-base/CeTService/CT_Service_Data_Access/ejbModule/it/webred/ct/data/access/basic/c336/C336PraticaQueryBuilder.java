package it.webred.ct.data.access.basic.c336;

import it.webred.ct.data.access.basic.CTQueryBuilder;
import it.webred.ct.data.access.basic.c336.dto.SearchC336PraticaDTO;
import it.webred.ct.data.access.basic.common.utils.StringUtils;
import it.webred.ct.data.access.basic.fabbricato.dto.SearchFabbricatoDTO;

public class C336PraticaQueryBuilder extends CTQueryBuilder {
	
	public static void main (String[] args) {
		SearchC336PraticaDTO cri = new SearchC336PraticaDTO();
		cri.setCodStato("010");
		cri.setFoglio("0010");
		cri.setParticella("00020");
		C336PraticaQueryBuilder qb = new  C336PraticaQueryBuilder(cri);
		String sql = qb.createQueryPerExistsParticella("SEZIONE", "FOGLIO", "PARTICELLA");
		
	}
	
	private SearchC336PraticaDTO criteria;
	
	private final  String SQL_SELECT_COUNT_LISTA = "SELECT count(*) as conteggio FROM ( @SQL_SELECT_BASE_LISTA@ )";

	public C336PraticaQueryBuilder(SearchC336PraticaDTO criteria) {
		super();
		this.criteria = criteria;
	}	
	public String getSelectClauseFP() {
		String select="";
		select= " SELECT DISTINCT SEZIONE,  LPAD(NVL(TRIM(foglio),'0'),4,'0') AS FOGLIO, PARTICELLA ";
		return select;
	}
	public String getSelectClauseFPS() {
		String select="";
		select= getSelectClauseFP() + ", sub";
		return select;
	}
	public String getFromClause() {
		String from="";
		from= " FROM S_C336_PRATICA P";
		return from;
	}
	
	public String getWhereClause() {
		String where="";
		where = " WHERE 1=1 "   ;
		
		return where;
	}
	
	public String createQuerySelectFP(boolean isCount){
		String sql=null;
		String sqlI=getSelectClauseFP() + getFromClause() + getWhereClause() + getSQL_Criteria_FPS() + getSQL_CriteriaPratica();
		logger.info("SQL1: " + sqlI);
		if (isCount){
			sql = this.SQL_SELECT_COUNT_LISTA;
			logger.info("SQL2: " + sql);
			sql = sql.replace("@SQL_SELECT_BASE_LISTA@", sqlI);
		}
		else {
			sql = sqlI;	
		}
			
		return sql;
	}
	
	public String createQuerySelectFPS(boolean isCount){
		String sql=null;
		String sqlI=getSelectClauseFPS() + getFromClause() + getWhereClause() + getSQL_Criteria_FPS() + getSQL_CriteriaPratica();
		logger.info("SQL1: " + sqlI);
		if (isCount){
			sql = this.SQL_SELECT_COUNT_LISTA;
			logger.info("SQL2: " + sql);
			sql = sql.replace("@SQL_SELECT_BASE_LISTA@", sqlI);
		}
		else {
			sql = sqlI;	
		}
			
		return sql;
	}
	
	public String createQueryPerExistsParticella(String nomeExtColSezione, String nomeExtColFoglio, String nomeExtColParticella){
		String sql=getSelectClauseFP() + getFromClause() + getWhereClause() +  getSQL_CriteriaPratica() + getSQL_PerExistsParticella(nomeExtColSezione, nomeExtColFoglio, nomeExtColParticella);
				
		return sql;
	}
	
	public String createQueryPerExistsSub(String nomeExtColSezione, String nomeExtColFoglio, String nomeExtColParticella,  String nomeExtColSub){
		String sql=getSelectClauseFPS() + getFromClause() + getWhereClause() +  getSQL_CriteriaPratica()+ getSQL_PerExistsSub(nomeExtColSezione, nomeExtColFoglio, nomeExtColParticella, nomeExtColSub);
				
		return sql;
	}
	
	private String getSQL_Criteria_FPS() {
		String sqlCriteria = "";
		sqlCriteria = (criteria.getSezione() == null  || criteria.getSezione().trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) +  " SEZIONE = '" + criteria.getSezione() + "'");
		sqlCriteria = (criteria.getFoglio() == null  || criteria.getFoglio().trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) +  " LPAD(foglio, 4, '0') = '" + StringUtils.fill('0',"sx", criteria.getFoglio(),4) + "'");		
		sqlCriteria = (criteria.getParticella() == null || criteria.getParticella().trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) +  " LPAD(particella, 5, '0') = '" + StringUtils.fill('0',"sx", criteria.getParticella(),5) + "'");
		sqlCriteria = (criteria.getSub() == null || criteria.getSub().trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) +  " LPAD(SUB, 5, '0') = '" + StringUtils.fill('0',"sx", criteria.getSub(),5) + "'");
		return sqlCriteria;
	}
	private String getSQL_CriteriaPratica() {
		String sqlCriteria = "";
		sqlCriteria = (criteria.getCodStato() == null || criteria.getCodStato().trim().equals("") || criteria.getCodStato().trim().equals("0") ? sqlCriteria : addOperator(sqlCriteria) +  " COD_STATO = '" + criteria.getCodStato() + "'");
		return sqlCriteria;
	}
	private String getSQL_PerExistsParticella(String nomeExtColSezione, String nomeExtColFoglio, String nomeExtColParticella) {
		String sqlCriteria = getSQL_Criteria_FPS() + " AND (P.SEZIONE IS NULL OR P.SEZIONE = " + nomeExtColSezione + ") AND P.FOGLIO = " + nomeExtColFoglio + " AND P.PARTICELLA = "  + nomeExtColParticella
		+ " AND P.SUB IS NULL ";
		return sqlCriteria;
	}
	private String getSQL_PerExistsSub(String nomeExtColSezione, String nomeExtColFoglio, String nomeExtColParticella, String nomeExtColSub) {
		String sqlCriteria = getSQL_Criteria_FPS() + " AND (P.SEZIONE IS NULL OR P.SEZIONE = " + nomeExtColSezione + ") AND P.FOGLIO = " + nomeExtColFoglio + 
			" AND P.PARTICELLA = "  + nomeExtColParticella + " AND (P.SUB IS NULL OR P.SUB = "  + nomeExtColSub + ")";
			
		return sqlCriteria;
	}
	protected String addOperator(String criteria) {
    	if (criteria == null)
    	    return criteria;    	
    	else    	    
    	    return criteria += " AND ";
    }
	
}
