package it.webred.ct.data.access.basic.tarsu;


import it.webred.ct.data.access.basic.CTQueryBuilder;
import it.webred.ct.data.access.basic.tarsu.dto.RicercaSoggettoTarsuDTO;

import java.text.SimpleDateFormat;
public class TarsuSoggQueryBuilder extends CTQueryBuilder {
	private RicercaSoggettoTarsuDTO criteri ;
	private final static String SEL_DISTINCT_PF = "SELECT DISTINCT s.cogDenom, s.nome, s.dtNsc, s.codFisc, s.codCmnNsc, s.desComNsc FROM SitTTarSogg s "; 
	private final static String SEL_DISTINCT_PG = "SELECT DISTINCT s.cogDenom, s.partIva FROM SitTTarSogg s " ;
	private final static String WHERE_PF = " WHERE tipSogg='F'";
	private final static String WHERE_PG = " WHERE tipSogg='G'";
	
	private final static String SQL_COUNT_BASE ="SELECT COUNT(*) FROM (" ; 
	private final static String ORDER_BY_PF  =" ORDER BY s.cogDenom, s.nome, s.dtNsc, s.desComNsc";
	private final static String ORDER_BY_PG  =" ORDER BY s.cogDenom, s.partIva";
	
	public TarsuSoggQueryBuilder(RicercaSoggettoTarsuDTO criteri) {
		this.criteri =criteri;
	}
	public String createQuery(boolean isCount) {
		String sql = "";
		if (criteri.getTipoSogg() ==null)
			return null;
		if (criteri.getTipoRicerca() != null &&  criteri.getTipoRicerca().equals("all") )
			sql= "SELECT s FROM SitTTarSogg s";
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
		sqlCriteria = (criteri.getProvenienza() == null  || criteri.getProvenienza().equals("") ? sqlCriteria : sqlCriteria + " AND s.provenienza='" + criteri.getProvenienza() + "'");
		sqlCriteria = (criteri.getCodFis() == null  || criteri.getCodFis().equals("") ? sqlCriteria : sqlCriteria + " AND s.codFisc='" + criteri.getCodFis().replace("'", "''") + "'");
		sqlCriteria = (criteri.getCognome() == null  || criteri.getCognome().equals("") ? sqlCriteria : sqlCriteria + " AND s.cogDenom='" + criteri.getCognome().replace("'", "''") + "'");
		sqlCriteria = (criteri.getNome() == null  || criteri.getNome().equals("") ? sqlCriteria : sqlCriteria + " AND s.nome='" + criteri.getNome().replace("'", "''") + "'");
		if(criteri.getDenom() != null  && !criteri.getDenom().equals("") )  {
			if (criteri.getOperDenom()!= null &&  criteri.getOperDenom().equals("LIKE"))
				sqlCriteria = sqlCriteria + " AND s.cogDenom LIKE '%" + criteri.getDenom().replace("'", "''") + "%'";
			else
				sqlCriteria = sqlCriteria +" AND s.cogDenom = '" + criteri.getDenom().replace("'", "''") + "'";
		}
		sqlCriteria = (criteri.getParIva() == null  || criteri.getParIva().equals("") ? sqlCriteria : sqlCriteria + " AND s.partIva = '" + criteri.getParIva()+ "'");
		String dataStr="";
		String fmt ="dd/MM/yyyy";
		if (criteri.getDtNas()!= null) {
			SimpleDateFormat df = new SimpleDateFormat(fmt);
			dataStr = df.format(criteri.getDtNas());
			sqlCriteria = sqlCriteria + " AND s.dtNsc=TO_DATE('" + dataStr + "','dd/MM/yyyy')";
		}
		//codice e descrizione comune di nascita entrambi valorizzati
		if(criteri.getCodComNas() != null  && !criteri.getCodComNas().equals("") &&  criteri.getDesComNas() != null  && !criteri.getDesComNas().equals("")  )  {
			sqlCriteria = sqlCriteria + " AND (s.codCmnNsc = '" + criteri.getCodComNas()+ "' OR s.desComNsc='" + criteri.getDesComNas().replace("'", "''")+ "')" ;	
		}
		//solo codice  comune di nascita  valorizzato
		if(criteri.getCodComNas() != null  && !criteri.getCodComNas().equals("") && ( criteri.getDesComNas() == null  || criteri.getDesComNas().equals(""))  )  {
			sqlCriteria = sqlCriteria + " AND s.codCmnNsc = '" + criteri.getCodComNas()+ "'" ;	
		}
		//solo descrizione  comune di nascita  valorizzato
		if((criteri.getCodComNas() == null  || criteri.getCodComNas().equals("")) &&  criteri.getDesComNas() != null  && !criteri.getDesComNas().equals("")  )  {
			sqlCriteria = sqlCriteria + " AND s.desComNsc='" + criteri.getDesComNas().replace("'", "''")+ "'" ;	
		}
		return sqlCriteria;
	}
}
