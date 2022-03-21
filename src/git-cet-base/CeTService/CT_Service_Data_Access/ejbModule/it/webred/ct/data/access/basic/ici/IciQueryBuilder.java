package it.webred.ct.data.access.basic.ici;

import java.text.SimpleDateFormat;
import java.util.List;

import it.webred.ct.data.access.basic.CTQueryBuilder;
import it.webred.ct.data.access.basic.ici.dto.RicercaOggettoIciDTO;
import it.webred.ct.data.access.basic.ici.dto.RicercaSoggettoIciDTO;
import it.webred.ct.data.model.ici.VTIciCiviciAll;
import it.webred.ct.data.model.tarsu.VTTarCiviciAll;

public class IciQueryBuilder extends CTQueryBuilder{
	private RicercaSoggettoIciDTO criteri ;
	private RicercaOggettoIciDTO criteriOgg;
	private final static String SEL_DISTINCT_PF = "SELECT DISTINCT s.cogDenom, s.nome, s.dtNsc, s.codFisc, s.codCmnNsc, s.desComNsc FROM SitTIciSogg s "; 
	private final static String SEL_DISTINCT_PG = "SELECT DISTINCT s.cogDenom, s.partIva FROM SitTIciSogg s " ;
	private final static String WHERE_PF = " WHERE tipSogg='F'";
	private final static String WHERE_PG = " WHERE tipSogg='G'";
	
	private final static String SQL_COUNT_BASE ="SELECT COUNT(*) FROM (" ; 
	private final static String ORDER_BY_PF  =" ORDER BY s.cogDenom, s.nome, s.dtNsc, s.desComNsc";
	private final static String ORDER_BY_PG  =" ORDER BY s.cogDenom, s.partIva";
	
	//OGGETTI
	private final String SQL_OGGETTO_VIA = "SELECT o FROM SitTIciOggetto o, SitTIciVia v WHERE o.idExtVia=v.idExt ";
	
	public IciQueryBuilder(RicercaSoggettoIciDTO criteri) {
		this.criteri =criteri;
	}
	public IciQueryBuilder(RicercaOggettoIciDTO criteri) {
		this.criteriOgg =criteri;
	}
	public String createQuery(boolean isCount) {
		String sql = "";
		if (criteri.getTipoSogg() ==null)
			return null;
		if (criteri.getTipoRicerca() != null &&  criteri.getTipoRicerca().equals("all") )
			sql= "SELECT s FROM SitTIciSogg s";
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
		sqlCriteria = (criteri.getProvenienza() == null  || criteri.getProvenienza().equals("") ? sqlCriteria : sqlCriteria + " AND s.provenienza='" + criteri.getProvenienza() + "'");
		sqlCriteria = (criteri.getCodFis() == null  || criteri.getCodFis().equals("") ? sqlCriteria : sqlCriteria + " AND s.codFisc='" + criteri.getCodFis() + "'");
		sqlCriteria = (criteri.getCognome() == null  || criteri.getCognome().equals("") ? sqlCriteria : sqlCriteria + " AND s.cogDenom='" + criteri.getCognome().replace("'", "''") + "'");
		sqlCriteria = (criteri.getNome() == null  || criteri.getNome().equals("") ? sqlCriteria : sqlCriteria + " AND s.nome='" + criteri.getNome().replace("'", "''") + "'");
		if(criteri.getDenom() != null  && !criteri.getDenom().equals("") )  {
			if (criteri.getOperDenom()!= null &&  criteri.getOperDenom().equals("LIKE"))
				sqlCriteria = sqlCriteria + " AND s.cogDenom LIKE '%" + criteri.getDenom().replace("'", "''")+ "%'";
			else
				sqlCriteria = sqlCriteria +" AND s.cogDenom = '" + criteri.getDenom().replace("'", "''")+ "'";
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
	
	public String createQueryOggettoVia() {
		List<VTIciCiviciAll> listaCivIci = criteriOgg.getListaCivIci();
		String sql = "";
		sql= SQL_OGGETTO_VIA;
		String provenienza=null;
		
		if (criteriOgg==null)
			logger.info("************ criteriOgg NULLO");
		if (criteriOgg.getProvenienza()!=null)
			provenienza=criteriOgg.getProvenienza();
		if (provenienza !=null)
			sql += " AND o.provenienza = '" + provenienza + "' AND v.provenienza ='" + provenienza + "'";
		if (listaCivIci!=null && listaCivIci.size() >0 ) {
			sql += " AND ( ";
			int i = 0;
			for (VTIciCiviciAll civIci: listaCivIci ) {
				if (i>0)
					sql += " OR ";
				sql += " ( v.id='" + civIci.getId() + "' AND o.numCiv ='" + civIci.getNumCiv() + "'";
				if (civIci.getEspCiv() ==null )
					sql += " AND o.espCiv IS NULL )";
				else
					sql += " AND o.espCiv ='" + civIci.getEspCiv()  +"' )";
				i++;
			}
			sql += ") ORDER BY o.sez, o.foglio, o.numero, o.sub, o.desInd, o.numCiv, o.yeaDen desc";
		}
		return sql;
	}
}
