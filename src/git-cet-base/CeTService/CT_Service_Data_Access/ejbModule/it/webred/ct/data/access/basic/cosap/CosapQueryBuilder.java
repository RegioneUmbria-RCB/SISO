package it.webred.ct.data.access.basic.cosap;

import java.text.SimpleDateFormat;
import java.util.List;

import it.webred.ct.data.access.basic.CTQueryBuilder;
import it.webred.ct.data.access.basic.cosap.dto.RicercaSoggettoCosapDTO;

public class CosapQueryBuilder extends CTQueryBuilder{
	private RicercaSoggettoCosapDTO criteri ;
	private final static String SEL_DISTINCT_PF = "SELECT DISTINCT c.cogDenom, c.nome, c.dtNascita, c.codiceFiscale, c.codBelfioreNascita, c.descComuneNascita FROM SitTCosapContrib c "; 
	private final static String SEL_DISTINCT_PG = "SELECT DISTINCT c.cogDenom, c.partitaIva FROM SitTCosapContrib c " ;
	private final static String WHERE_PF = " WHERE c.tipoPersona='F'";
	private final static String WHERE_PG = " WHERE c.tipoPersona='G'";
	
	private final static String SQL_COUNT_BASE ="SELECT COUNT(*) FROM (" ; 
	private final static String ORDER_BY_PF  =" ORDER BY c.cogDenom, c.nome, c.dtNascita, c.descComuneNascita";
	private final static String ORDER_BY_PG  =" ORDER BY c.cogDenom, c.partitaIva";
	
	private final static String SQL_SINTESI_OGGETTI_COSAP = 
		"SELECT DISTINCT NUMERO_DOCUMENTO, ANNO_DOCUMENTO,CODICE_IMMOBILE,TIPO_OCCUPAZIONE, DT_INI_VALIDITA, DT_FIN_VALIDITA " +
		" FROM SIT_T_COSAP_TASSA "; 
	public CosapQueryBuilder() {
		
	}
	public static String getSqlSintesiOggettiCosap(List<String> listaId) {
		String sql = SQL_SINTESI_OGGETTI_COSAP;
		String inCondition="";
		if (listaId!= null && listaId.size()>0) {
			for (String id:listaId){
				if (inCondition.equals(""))
					inCondition = " WHERE ID IN ('" + id + "'";
				else
					inCondition += ", '" + id + "'"; 
			}
			inCondition += ")";
			sql += inCondition + " ORDER BY ANNO_DOCUMENTO DESC, NUMERO_DOCUMENTO ";
			
		}
			
		return sql;
	}
	public CosapQueryBuilder(RicercaSoggettoCosapDTO criteri) {
		this.criteri =criteri;
	}
	public String createQuery(boolean isCount) {
		String sql = "";
		if (criteri.getTipoSogg() ==null)
			return null;
		if (criteri.getTipoRicerca() != null &&  criteri.getTipoRicerca().equals("all") )
			sql= "SELECT c FROM SitTCosapContrib c";
		else {
			if (criteri.getTipoSogg().equals("F"))
				sql = SEL_DISTINCT_PF;
			else
				sql=SEL_DISTINCT_PG;
		}
		if (criteri.getTipoSogg().equals("F"))
			sql += WHERE_PF;
		else
			sql +=WHERE_PG;
		if (isCount)
			sql = SQL_COUNT_BASE + sql;
		
		String cond = getSQLCriteria();
		if (cond.equals(""))
			return null;
		if (!"".equals(cond)) {
			sql += cond;
		}
		if (isCount)
			sql+= ")";
		else{
			if (criteri.getTipoSogg().equals("F"))
				sql += ORDER_BY_PF;
			else
				sql += ORDER_BY_PG;
		}
		logger.info("SQL ["+sql+"]");
		return sql;
	}
	private String getSQLCriteria() {
		String sqlCriteria = "";
		sqlCriteria = (criteri.getProvenienza() == null  || criteri.getProvenienza().equals("") ? sqlCriteria : sqlCriteria + " AND c.provenienza='" + criteri.getProvenienza() + "'");
		sqlCriteria = (criteri.getCodFis() == null  || criteri.getCodFis().equals("") ? sqlCriteria : sqlCriteria + " AND c.codiceFiscale='" + criteri.getCodFis() + "'");
		sqlCriteria = (criteri.getCognome() == null  || criteri.getCognome().equals("") ? sqlCriteria : sqlCriteria + " AND c.cogDenom='" + criteri.getCognome().replace("'", "''") + "'");
		sqlCriteria = (criteri.getNome() == null  || criteri.getNome().equals("") ? sqlCriteria : sqlCriteria + " AND c.nome='" + criteri.getNome().replace("'", "''") + "'");
		if(criteri.getDenom() != null  && !criteri.getDenom().equals("") )  {
			if (criteri.getOperDenom()!= null &&  criteri.getOperDenom().equals("LIKE"))
				sqlCriteria = sqlCriteria + " AND c.cogDenom LIKE '%" + criteri.getDenom().replace("'", "''")+ "%'";
			else
				sqlCriteria = sqlCriteria +" AND c.cogDenom = '" + criteri.getDenom().replace("'", "''")+ "'";
		}
		sqlCriteria = (criteri.getParIva() == null  || criteri.getParIva().equals("") ? sqlCriteria : sqlCriteria + " AND c.partitaIva = '" + criteri.getParIva()+ "'");
		String dataStr="";
		String fmt ="dd/MM/yyyy";
		if (criteri.getDtNas()!= null) {
			SimpleDateFormat df = new SimpleDateFormat(fmt);
			dataStr = df.format(criteri.getDtNas());
			sqlCriteria = sqlCriteria + " AND c.dtNascita=TO_DATE('" + dataStr + "','dd/MM/yyyy')";
		}
		//codice e descrizione comune di nascita entrambi valorizzati
		if(criteri.getCodComNas() != null  && !criteri.getCodComNas().equals("") &&  criteri.getDesComNas() != null  && !criteri.getDesComNas().equals("")  )  {
			sqlCriteria = sqlCriteria + " AND (c.codBelfioreNascita = '" + criteri.getCodComNas()+ "' OR c.descComuneNascita='" + criteri.getDesComNas().replace("'", "''")+ "')" ;	
		}
		//solo codice  comune di nascita  valorizzato
		if(criteri.getCodComNas() != null  && !criteri.getCodComNas().equals("") && ( criteri.getDesComNas() == null  || criteri.getDesComNas().equals(""))  )  {
			sqlCriteria = sqlCriteria + " AND c.codBelfioreNascita = '" + criteri.getCodComNas()+ "'" ;	
		}
		//solo descrizione  comune di nascita  valorizzato
		if((criteri.getCodComNas() == null  || criteri.getCodComNas().equals("")) &&  criteri.getDesComNas() != null  && !criteri.getDesComNas().equals("")  )  {
			sqlCriteria = sqlCriteria + " AND c.descComuneNascita='" + criteri.getDesComNas().replace("'", "''")+ "'" ;	
		}
		return sqlCriteria;
	}
}
