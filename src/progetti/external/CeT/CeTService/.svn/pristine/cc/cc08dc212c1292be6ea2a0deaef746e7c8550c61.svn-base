package it.webred.ct.data.access.basic.anagrafe;

import it.webred.ct.data.access.basic.CTQueryBuilder;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;

import java.text.SimpleDateFormat;

public class AnagGenQueryBuilder extends CTQueryBuilder {
	private RicercaSoggettoAnagrafeDTO criteri ;
	private final static String SQL_BASE =
		"SELECT DISTINCT P.COGNOME, NOME, P.DATA_NASCITA, P.CODFISC, P.ID_EXT_COMUNE_NASCITA, C.DESCRIZIONE " +
		"FROM SIT_D_PERSONA P, SIT_COMUNE C " +
		"WHERE P.ID_EXT_COMUNE_NASCITA=C.ID_EXT (+) "; 
	private final static String SQL_COUNT_BASE ="SELECT COUNT(*) FROM (" + SQL_BASE; 
	private final static String ORDER_BY  =" ORDER BY COGNOME, NOME, DATA_NASCITA,DESCRIZIONE";
	public AnagGenQueryBuilder(RicercaSoggettoAnagrafeDTO criteri)  {
		this.criteri =criteri;
	}
	
	public String createQuery(boolean isCount) {
		String sql = "";
		if (isCount)
			sql = SQL_COUNT_BASE;
		else
			sql = SQL_BASE;
		String cond = getSQLCriteria();
		if (cond.equals(""))
			return null;
		if (!"".equals(cond)) {
			sql += cond;
		}
		if (isCount)
			sql+= ")";
		else		
			sql += ORDER_BY;
		logger.info("SQL ["+sql+"]");
		return sql;
	}
	private String getSQLCriteria() {
		String sqlCriteria = "";
		sqlCriteria = (criteri.getCodFis() == null  || criteri.getCodFis().equals("") ? sqlCriteria : sqlCriteria + " AND CODFISC='" + criteri.getCodFis() + "'");
		sqlCriteria = (criteri.getCognome() == null  || criteri.getCognome().equals("") ? sqlCriteria : sqlCriteria + " AND COGNOME='" + criteri.getCognome().replace("'","''") + "'");
		sqlCriteria = (criteri.getNome() == null  || criteri.getNome().equals("") ? sqlCriteria : sqlCriteria + " AND NOME='" + criteri.getNome().replace("'","''") + "'");
		String dataStr="";
		String fmt ="dd/MM/yyyy";
		if (criteri.getDtNas()!= null) {
			SimpleDateFormat df = new SimpleDateFormat(fmt);
			dataStr = df.format(criteri.getDtNas());
			sqlCriteria = sqlCriteria + " AND DATA_NASCITA=TO_DATE('" + dataStr + "','dd/MM/yyyy')";
		}
		return sqlCriteria;
	}
}
