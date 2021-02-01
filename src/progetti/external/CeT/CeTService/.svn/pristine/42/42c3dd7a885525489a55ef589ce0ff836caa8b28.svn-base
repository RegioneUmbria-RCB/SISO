package it.webred.ct.data.access.basic.docfa;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import it.webred.ct.data.access.basic.CTQueryBuilder;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;
import it.webred.ct.data.access.basic.docfa.dto.DocfaSearchCriteria;
import it.webred.ct.data.access.basic.docfa.dto.RicercaOggettoDocfaDTO;

public class DocfaQueryBuilder extends CTQueryBuilder{
	
	public static void main(String[] args) {
		DocfaSearchCriteria cri = new DocfaSearchCriteria();
		cri.setEnteId("F704");
		//cri.setRicercaOggetto(ricercaOggetto)
		RicercaOggettoDocfaDTO ro = new RicercaOggettoDocfaDTO();
		ro.setFoglio("0085");
		ro.setParticella("00339");
		ro.setEnteId("F704");
		GregorianCalendar gc = new GregorianCalendar(2008, 2, 6);
		Date dtRegDa = gc.getTime();
		ro.setDataRegistrazioneDa(dtRegDa);
		cri.setRicercaOggetto(ro);
		DocfaQueryBuilder qb = new DocfaQueryBuilder(cri);
		String sql = qb.createQueryGettingParticella(false);
		
	}
	
	private DocfaSearchCriteria criteria;
	
	private String sqlFrom =   "FROM Docfa_Uiu d";
	private String sqlAddFromDC =   ", Docfa_Dati_Censuari c";
	private String sqlAddFromDG =   ", Docfa_Dati_Generali ddg";
	private String sqlWhere =  "WHERE 1=1";
	private String sqlAddWhereDC =  " AND d.FORNITURA=C.FORNITURA AND d.PROTOCOLLO_REG=C.PROTOCOLLO_REGISTRAZIONE ";
	private String sqlAddWhereDG =  " AND ddg.PROTOCOLLO_REG = d.PROTOCOLLO_REG AND ddg.FORNITURA = d.FORNITURA";
	private SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	
	
	private final String SQL_LISTA_DATI_DOCFA_BY_FPS =
		"SELECT U.FORNITURA, U.PROTOCOLLO_REG, C.DATA_REGISTRAZIONE, U.TIPO_OPERAZIONE, INDIR_TOPONIMO, INDIR_NCIV_UNO,  INDIR_NCIV_DUE,  INDIR_NCIV_TRE " +
		"FROM  DOCFA_UIU U, DOCFA_DATI_CENSUARI C " +
		"WHERE LPAD(U.FOGLIO, 4, '0') = LPAD(?,4, '0') " +   
		  "AND LPAD(U.NUMERO, 5, '0') = LPAD(?, 5, '0')" + 
		  "AND LPAD(U.SUBALTERNO, 4,'0')=LPAD(?,4, '0') " +
		  "AND U.FORNITURA=C.FORNITURA " +
		  "AND U.PROTOCOLLO_REG=C.PROTOCOLLO_REGISTRAZIONE " +
		  "AND U.FOGLIO=C.FOGLIO AND U.NUMERO=C.NUMERO AND U.SUBALTERNO=C.SUBALTERNO";

	private final String SQL_LISTA_DATI_DOCFA_BY_FPSubNonVal =	
	"SELECT U.FORNITURA, U.PROTOCOLLO_REG, C.DATA_REGISTRAZIONE, U.TIPO_OPERAZIONE, INDIR_TOPONIMO, INDIR_NCIV_UNO,  INDIR_NCIV_DUE,  INDIR_NCIV_TRE " +
	"FROM  DOCFA_UIU U, DOCFA_DATI_CENSUARI C " +
	"WHERE LPAD(U.FOGLIO, 4, '0') = LPAD(?,4, '0') " +   
  	  "AND LPAD(U.NUMERO, 5, '0') = LPAD(?, 5, '0')" + 
	  "AND (U.SUBALTERNO IS NULL  OR TRIM(U.SUBALTERNO) IS NULL )" +
	  "AND U.FORNITURA=C.FORNITURA " +
	  "AND U.PROTOCOLLO_REG=C.PROTOCOLLO_REGISTRAZIONE " +
	  "AND U.FOGLIO=C.FOGLIO AND U.NUMERO=C.NUMERO   AND nvl(TRIM(U.SUBALTERNO),'0')=nvl(TRIM(C.SUBALTERNO),'0')";

	
	private final String SQL_LISTA_DATI_GENERALI_FABBRICATO =
		"SELECT DISTINCT D_GEN.PROTOCOLLO_REG AS PROTOCOLLO, " +
		" D_GEN.FORNITURA FORNITURA,  D_GEN.DATA_VARIAZIONE  AS DATA_VARIAZIONE, " +  
		" D_GEN.CAUSALE_NOTA_VAX        AS CAUSALE, " +
		" UIU_IN_SOPPRESSIONE           AS SOPPRESSIONE, " +    
		" UIU_IN_VARIAZIONE             AS VARIAZIONE,  " +
		" UIU_IN_COSTITUZIONE           AS COSTITUZIONE, " +
		" (SELECT MAX(C.DATA_REGISTRAZIONE) FROM DOCFA_DATI_CENSUARI C " +
		" WHERE U.FORNITURA = C.FORNITURA " +
		" AND U.PROTOCOLLO_REG = C.PROTOCOLLO_REGISTRAZIONE " +
		" AND U.FOGLIO = C.FOGLIO " +
		" AND U.NUMERO = C.NUMERO " +
		" AND U.SUBALTERNO = C.SUBALTERNO) AS DATA_REGISTRAZIONE " +
		" FROM  DOCFA_DATI_GENERALI  D_GEN, DOCFA_UIU U " +
		" WHERE D_GEN.PROTOCOLLO_REG = U.PROTOCOLLO_REG(+) " +     
		" AND D_GEN.FORNITURA = U.FORNITURA(+) " +    
		" AND LPAD(U.FOGLIO, 4, '0') = LPAD(?,4, '0') " +   
	    " AND LPAD(U.NUMERO, 5, '0') = LPAD(?, 5, '0') @UNIMM@" + 
		" AND (SEZIONE IS NULL OR SEZIONE =?) " +
		" AND DATA_VARIAZIONE <=  TO_DATE(?, 'dd/mm/yyyy') " +
		" ORDER BY  FORNITURA , PROTOCOLLO";
	
	private final String HQL_DOCFA_DATI_CENSUARI = "SELECT d FROM DocfaDatiCensuari d "
			+ " WHERE 1=1 "
			+ "#WHERE_DOCFA_DATI_CENSUARI#"
			+ " order by d.id.fornitura, d.id.protocolloRegistrazione ";

	public DocfaQueryBuilder(){
		super();
	}
	
	public DocfaQueryBuilder(DocfaSearchCriteria criteria){
		this.criteria = criteria;
	}
	
	public String createQueryGettingFPS(boolean isCount) {
		
		boolean flgCriteria = false;
		String sql = null;
		
		String sqlCriteriaDocfaUiu = this.getSQL_DocfaUiuCriteria();
		if(sqlCriteriaDocfaUiu.length()>0){
			flgCriteria = true;
			sqlWhere += sqlCriteriaDocfaUiu;
		}
		
		String sqlCriteriaDocfaDatiGenerali = this.getSQL_DocfaDatiGeneraliCriteria();
		if(sqlCriteriaDocfaDatiGenerali.length()>0){
			flgCriteria=true;
			sqlFrom += ", Docfa_Dati_Generali ddg";
			sqlWhere += sqlCriteriaDocfaDatiGenerali +
						" AND ddg.fornitura = d.fornitura" +
						" AND ddg.protocollo_reg = d.protocollo_reg";
			
		}
		
		String sqlCriteriaDichiarante = this.getSQL_DichiaranteCriteria();
		if(sqlCriteriaDichiarante.length()> 0){
			flgCriteria = true;
			sqlFrom += ", Docfa_Dichiaranti dd";
			sqlWhere += sqlCriteriaDichiarante +
						" AND dd.fornitura = d.fornitura" +
						" AND dd.protocollo_reg = d.protocollo_reg";
		}
		
		String sqlCriteriaDocfaDatiCensuari = this.getSQL_DocfaDatiCensuariCriteria();
		if(sqlCriteriaDocfaDatiCensuari.length()>0){
			flgCriteria = true;
			sqlFrom += ", Docfa_Dati_Censuari c";
			sqlWhere += sqlCriteriaDocfaDatiCensuari +
						" AND c.fornitura = d.fornitura" +
						" AND c.protocollo_registrazione = d.protocollo_reg" +
						" AND c.foglio = d.foglio" +
						" AND c.numero = d.numero" +
						" AND c.subalterno = d.subalterno";	
		}
		
		//Se esiste un criterio di selezione compone la query e la restituisce, altrimenti vale null
		flgCriteria = true; //Forzo la ricerca anche in assenza di criteri
		if(flgCriteria){
			
			String sqlFields =  "d.sezione sezione, " +
								"LPAD("+getSqlNvlTrimField("d.foglio","0")+",4,'0') foglio, " +
								"LPAD("+getSqlNvlTrimField("d.numero","0")+",5,'0') particella, " +
								"LPAD("+getSqlNvlTrimField("d.subalterno","0")+",4,'0') subalterno";
		
			if (isCount){
				sql = "SELECT COUNT(*) FROM (SELECT DISTINCT " + sqlFields + " ";
				sql +=  sqlFrom + " " + sqlWhere +")";
			}else{ 
				sql = "SELECT DISTINCT " + sqlFields + " ";
				sql +=  sqlFrom + " " + sqlWhere ;
			}
		
		}
		
		logger.info("SQL DOCFA ["+sql+"]");
		
		return sql;
		
	}
	public String createQueryGettingParticella(boolean isCount) {
		String sql = null;
		String sqlPart = null;
		String sqlFields =  "d.sezione sezione, " +
		"LPAD("+getSqlNvlTrimField("d.foglio","0")+",4,'0') foglio, " +
		"LPAD("+getSqlNvlTrimField("d.numero","0")+",5,'0') particella " ;
	
		sqlPart= this.getSQL_DocfaUiuCriteria()+ this.getSQL_DocfaDatiCensuariCriteria() + this.getSQL_DocfaDatiGeneraliCriteria();
		String sqlF = this.sqlFrom + this.sqlAddFromDC + this.sqlAddFromDG;
		String sqlW = this.sqlWhere + this.sqlAddWhereDC + this.sqlAddWhereDG;
		if (isCount){
			sql = "SELECT COUNT(*) FROM (SELECT DISTINCT " + sqlFields + " ";
			sql +=  sqlF + " " + sqlW + sqlPart +")";
		}else{ 
			sql = "SELECT DISTINCT " + sqlFields + " ";
			sql +=  sqlF + " " + sqlW + sqlPart  ;
		}
		return sql;
	}
	
	private String getSQLTrimField(String field){
		return  "TRIM("+field+")";
	}
	
	private String getSqlNvlTrimField(String field, String cod){
		return  "NVL(TRIM("+field+"),'"+cod+"')";
	}
	

	private String getSQL_DocfaUiuCriteria(){
		String sql = "";
		RicercaOggettoDocfaDTO oggetto = criteria.getRicercaOggetto();
		
		if(oggetto!=null){
			
			//Parametri catastali IMMOBILE
			sql = (oggetto.getFoglio() == null  || oggetto.getFoglio().equals("") ? sql : sql + " AND d.foglio= LPAD('" + oggetto.getFoglio()+ "',4,'0')");
			sql = (oggetto.getParticella() == null  || oggetto.getParticella().equals("") ? sql : sql + " AND d.numero=LPAD('" + oggetto.getParticella()+ "',5,'0')");
			sql = (oggetto.getUnimm() == null  || oggetto.getUnimm().equals("") ? sql : sql  + " AND d.subalterno = LPAD('" + oggetto.getUnimm()+ "',4,'0')");
		
			//Parametri dichiarazione DOCFA

			sql = (oggetto.getFornituraDa() == null || oggetto.getFornituraDa().equals("") ? sql : sql + " AND d.fornitura >= TO_DATE('" + oggetto.getFornituraDa() + "', 'yyyyMMdd')");
			sql = (oggetto.getFornituraA() == null  || oggetto.getFornituraA().equals("") ? sql : sql + " AND d.fornitura <= TO_DATE('" + oggetto.getFornituraA() + "', 'yyyyMMdd')");
			sql = (oggetto.getProtocollo() == null  || oggetto.getProtocollo().equals("") ? sql : sql + " AND UPPER(d.protocollo_Reg) ='" + oggetto.getProtocollo().toUpperCase() + "'");
			
			RicercaCivicoDTO indirizzo = oggetto.getIndirizzo();
			if(indirizzo!=null){
				sql = (indirizzo.getCivico()== null  || indirizzo.getCivico().equals("") ? sql : sql + " AND UPPER(d.indir_Nciv_Uno) LIKE '%" + indirizzo.getCivico() + "%'");
				sql = (indirizzo.getDescrizioneVia() == null  || indirizzo.getDescrizioneVia().equals("") ? sql : sql + " AND UPPER(d.indir_Toponimo) LIKE '%" + indirizzo.getDescrizioneVia().replace("'","''").toUpperCase() + "%'");

			}
				
		}
		
		return sql;
	}
		
	private String getSQL_DocfaDatiCensuariCriteria(){
		String sql = "";
		RicercaOggettoDocfaDTO oggetto = criteria.getRicercaOggetto();
		
		if(oggetto!=null){
			//Parametri catastali IMMOBILE
			sql = (oggetto.getFoglio() == null  || oggetto.getFoglio().equals("") ? sql : sql + " AND c.foglio= LPAD('" + oggetto.getFoglio()+ "',4,'0')");
			sql = (oggetto.getParticella() == null  || oggetto.getParticella().equals("") ? sql : sql + " AND c.numero=LPAD('" + oggetto.getParticella()+ "',5,'0')");
			sql = (oggetto.getUnimm() == null  || oggetto.getUnimm().equals("") ? sql : sql  + " AND c.subalterno = LPAD('" + oggetto.getUnimm()+ "',4,'0')");
			//Parametri dichiarazione DOCFA
			sql = (oggetto.getFornituraDa() == null || oggetto.getFornituraDa().equals("") ? sql : sql + " AND c.fornitura >= TO_DATE('" + oggetto.getFornituraDa() + "', 'yyyyMMdd')");
			sql = (oggetto.getFornituraA() == null  || oggetto.getFornituraA().equals("") ? sql : sql + " AND c.fornitura <= TO_DATE('" + oggetto.getFornituraA() + "', 'yyyyMMdd')");
			sql = (oggetto.getDataRegistrazioneDa() == null || oggetto.getDataRegistrazioneDa().equals("") ? sql : sql + " AND c.data_Registrazione >= '" + yyyyMMdd.format(oggetto.getDataRegistrazioneDa())+ "'");
			sql = (oggetto.getDataRegistrazioneA() == null  || oggetto.getDataRegistrazioneA().equals("") ? sql : sql + " AND c.data_Registrazione <= '" + yyyyMMdd.format(oggetto.getDataRegistrazioneA())+ "'");	
		}
		
		return sql;
	}
	
	private String getSQL_DocfaDatiGeneraliCriteria(){
		String sql = "";
		RicercaOggettoDocfaDTO oggetto = criteria.getRicercaOggetto();
		
		if(oggetto!=null){
			
			//Join con Docfa_Dati_Generali
			
			sql = (oggetto.getCausali() == null  || oggetto.getCausali().equals("") ? sql : sql + " AND " + addCondition("ddg.causale_Nota_Vax","IN",oggetto.getCausali()));
			sql = (oggetto.getFlgCommiFin2005() == null  || oggetto.getFlgCommiFin2005().equals("") ? sql : sql + " AND "+ addCondition("ddg.flag_commi_fin2005", "=", oggetto.getFlgCommiFin2005()));
			
		}
		
		return sql;
	}
	
	private String getSQL_DichiaranteCriteria(){
		
		String sql = "";
		RicercaSoggettoDTO soggetto = criteria.getRicercaDichiarante();
		
		if(soggetto!=null){
			String nome = soggetto.getNome();
			String cognome = soggetto.getCognome();
			String denominazione = soggetto.getDenom();
			
			sql = nome != null && !nome.trim().equals("") ? sql + " AND dd.dic_nome = '" + nome + "'" : sql;
			sql = cognome != null && !cognome.trim().equals("") ?  sql + " AND dd.dic_cognome = '" + cognome + "'" : sql;
			sql = denominazione != null && !denominazione.trim().equals("") ?  sql + " AND TRIM(dd.dic_cognome||' '||dd.dic_nome) LIKE '%"+denominazione+"%'" : sql;
		}
		
		
		return sql;
	}
	
	
	public String getSQL_LISTA_DATI_DOCFA_BY_FPS() {
		return SQL_LISTA_DATI_DOCFA_BY_FPS;
	}

	public String getSQL_LISTA_DATI_DOCFA_BY_FPS(boolean addCondSezione ) {
		String sql = SQL_LISTA_DATI_DOCFA_BY_FPS;
		if (addCondSezione)
			sql += 	"AND  (U.SEZIONE IS NOT NULL AND U.SEZIONE=C.SEZIONE) AND U.SEZIONE =?  ";
		return sql;
			
	}

	public String getSQL_LISTA_DATI_DOCFA_BY_FPSubNonVal() {
		return SQL_LISTA_DATI_DOCFA_BY_FPSubNonVal;
	}
	public String getSQL_LISTA_DATI_DOCFA_BY_FPSubNonVal(boolean addCondSezione ) {
		String sql = SQL_LISTA_DATI_DOCFA_BY_FPSubNonVal;
		if (addCondSezione)
			sql += 	"AND  (U.SEZIONE IS NOT NULL AND U.SEZIONE=C.SEZIONE) AND U.SEZIONE =?  ";
		return sql;
	}

	public String getSQL_LISTA_DATI_GENERALI_FABBRICATO() {
		return SQL_LISTA_DATI_GENERALI_FABBRICATO.replace("@UNIMM@", "");
	}
	
	public String getSQL_LISTA_DATI_GENERALI_IMMOBILE() {
		String sql = " AND lpad(u.subalterno, 4, '0') = lpad(?, 4, '0') ";
		return  SQL_LISTA_DATI_GENERALI_FABBRICATO.replace("@UNIMM@", sql);
	}//-------------------------------------------------------------------------
	
	public String getDocfaDatiCensuari(RicercaOggettoDocfaDTO rod) {
		
		String hql = HQL_DOCFA_DATI_CENSUARI;
		//SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
		
		String where = "";
		if(rod!=null){
			
			String sezione = rod.getSezione();
			String foglio = rod.getFoglio();
			String numero = rod.getParticella();
			String subalterno = rod.getUnimm();
			String protocollo = rod.getProtocollo();
			Date fornitura = rod.getFornitura();
			
			where += sezione != null && !sezione.trim().equals("") ? " AND d.sezione = '" + sezione + "' " : "";
			where += foglio != null && !foglio.trim().equals("") ? " AND d.foglio = lpad('" + foglio + "',4,'0') " : "";
			where += numero != null && !numero.trim().equals("") ? " AND d.numero = lpad('" + numero + "',5,'0') " : "";
			where += subalterno != null && !subalterno.trim().equals("") ? " AND d.subalterno = lpad('" + subalterno + "',4,'0') " : "";
			where += protocollo != null && !protocollo.trim().equals("") ? " AND d.id.protocolloRegistrazione = '" + protocollo + "' " : "";
			where += fornitura != null ? " AND d.id.fornitura = TO_DATE('" + yyyyMMdd.format(fornitura) + "', 'YYYYMMDD' ) " : "";
			//TO_DATE( '"+dataNascita+"', 'dd/MM/yyyy')
		}
		
		hql = hql.replace("#WHERE_DOCFA_DATI_CENSUARI#", where);
		
		return hql;
	}//-------------------------------------------------------------------------
	
	protected String addCondition(String field, String operator, String param) {
    	
		String criteria = "";
		operator = operator.trim();
		param = param.trim();
		
		if (operator.equals("="))
    	    criteria += " ("+ field +") "+ operator +" '" + param.toUpperCase() + "' " ;    	
    	else if (operator.equalsIgnoreCase("LIKE"))  	    
    		criteria += " ("+ field +") "+ operator +" '%' || '"+ param.toUpperCase()+"' || '%' " ;  
    	else if (operator.equalsIgnoreCase("IN")){
    		String lista = param.replace(",", "','");
    		String inClause = "( '" + lista + "' )";
    		criteria += " ("+ field +") "+ operator + inClause;
    	}
		
		return criteria;
    }//-------------------------------------------------------------------------
	
}
