package it.webred.ct.data.access.basic.concedilizie;

import it.webred.ct.data.access.basic.CTQueryBuilder;
import it.webred.ct.data.access.basic.catasto.dto.ParametriCatastaliDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.common.utils.StringUtils;
import it.webred.ct.data.access.basic.concedilizie.dto.ConcEdiSearchCriteria;
import it.webred.ct.data.access.basic.concedilizie.dto.RicercaConcEdilizieDTO;
import it.webred.ct.data.access.basic.concedilizie.dto.RicercaSoggettoConcEdilizieDTO;

public class ConcessioniEdilizieQueryBuilder extends CTQueryBuilder{
	
	private ConcEdiSearchCriteria criteria;
	
	private String sqlFrom =   "FROM Sit_C_Concessioni_Catasto cc";
	private String sqlWhere =  "WHERE 1=1";
	
	private final String SQL_SOGGETTI_CONCESSIONE = 
		"SELECT CP.TITOLO,TIPO_PERSONA, COGNOME, NOME, TRIM(p.TITOLO || ' ' || DENOMINAZIONE) as denominazione,tipo_soggetto, ragsoc_ditta " +
	      "FROM SIT_C_CONC_PERSONA CP, SIT_C_PERSONA P  " +
	     "WHERE CP.ID_EXT_C_PERSONA = P.ID_EXT (+) " +
	       "AND CP.ID_EXT_C_CONCESSIONI = ? " +// -->ID_EXT DI SIT_ C_CONCESSIONI
	       "AND (CP.DT_FINE_VAL IS NULL OR CP.DT_FINE_VAL >= ?) " +
	       "AND (P.DT_FINE_VAL IS NULL OR P.DT_FINE_VAL >= ?) order by titolo";

	private final String SQL_OGGETTI_CONCESSIONE = 
		"SELECT SEZIONE, FOGLIO, PARTICELLA,SUBALTERNO " +
	      "FROM SIT_C_CONCESSIONI C , SIT_C_CONCESSIONI_CATASTO CC " +
	     "WHERE CC.ID_EXT_C_CONCESSIONI = C.ID_EXT " +
	       "AND C.ID_EXT=?" +
	       "AND (CC.DT_FINE_VAL IS NULL OR CC.DT_FINE_VAL >= ?) " +
	       "AND (C.DT_FINE_VAL IS NULL OR C.DT_FINE_VAL >= ?)";
	public String getSQL_OGGETTI_CONCESSIONE() {
		return SQL_OGGETTI_CONCESSIONE;
	}

	public String getSQL_SOGGETTI_CONCESSIONE() {
		return SQL_SOGGETTI_CONCESSIONE;
	}
	
	public ConcessioniEdilizieQueryBuilder(){
		super();
	}
	
	public ConcessioniEdilizieQueryBuilder(ConcEdiSearchCriteria criteria){
		new ConcessioniEdilizieQueryBuilder();
		this.criteria = criteria;
	}
	
	public String createQueryGettingFPS(boolean isCount) {
		
		boolean flgCriteria = false;
		String sql = null;
		
		String sqlSitCConcCatasto = this.getSQL_SitCConcCatastoCriteria();
		if(sqlSitCConcCatasto.length()>0){
			flgCriteria = true;
			sqlWhere += sqlSitCConcCatasto;

		}
		
		String sqlSitCConcessione = this.getSQL_SitCConcessioniCriteria();
		if(sqlSitCConcessione.length()>0){
			flgCriteria=true;
			sqlFrom += ", Sit_C_Concessioni c";
			sqlWhere += sqlSitCConcessione +
						" AND c.id_Ext = cc.id_Ext_C_Concessioni";
	
		}
		
		String sqlSitCPersona = this.getSQL_SitCPersonaCriteria();
		String sqlSitCConcPersona = this.getSQL_SitCConcPersonaCriteria();
		if(sqlSitCConcPersona.length()> 0 || sqlSitCPersona.length()>0){
			flgCriteria = true;
			if(sqlSitCPersona.length()==0){
				sqlFrom += ", Sit_C_Conc_Persona cp";
				sqlWhere += sqlSitCConcPersona +
				" AND cc.id_Ext_C_Concessioni = cp.id_Ext_C_Concessioni";
			}else{
				sqlFrom += ", Sit_C_Conc_Persona cp, Sit_C_Persona p";
				sqlWhere += sqlSitCConcPersona + sqlSitCPersona +
							" AND cc.id_Ext_C_Concessioni = cp.id_Ext_C_Concessioni" +
							" AND p.id_Ext = cp.id_Ext_C_Persona";
			}
		}
		
		String sqlIndirizzo = this.getSQL_IndirizzoCriteria();
		if(sqlIndirizzo.length()> 0){
			sqlFrom += ", Sit_C_Conc_Indirizzi ci";
			sqlWhere += sqlIndirizzo +
			" AND cc.id_Ext_C_Concessioni = ci.id_Ext_C_Concessioni";
		}
		
		//Se esiste un criterio di selezione compone la query e la restituisce, altrimenti vale null
		flgCriteria = true; //Forzo la ricerca anche in assenza di criteri
		if(flgCriteria){
			
			String tipo = criteria.getRicercaOggetto().getTipoCatasto();
			sqlWhere = (tipo == null  || tipo.equals("") ? sqlWhere : sqlWhere + " AND cc.tipo = '" + tipo+"'");
			
			String sqlFields =  "cc.sezione sezione, " +
								getSqlLpadNvlTrimField("cc.foglio",'0','4','0')     + " foglio, " +
								getSqlLpadNvlTrimField("cc.particella",'0','5','0') + " particella, " +
								getSqlLpadNvlTrimField("cc.subalterno",'0','4','0') + " subalterno";
			
			if (isCount){
				sql = "SELECT COUNT(*) FROM (SELECT DISTINCT " + sqlFields + " ";
				sql +=  sqlFrom + " " + sqlWhere +")";
			}else{ 
				sql = "SELECT DISTINCT " + sqlFields + " ";
				sql +=  sqlFrom + " " + sqlWhere ;
			}
			
		}
		logger.info("SQL CONCESSIONI EDILIZIE ["+sql+"]");
		
		return sql;
		
	}
	
	private String getSqlLpadNvlTrimField(String field, char nvlCod, char nLpad, char cLpad){
		
		String nvlTrim = getSqlNvlTrimField(field,nvlCod);
		String lpad = 	"LPAD("+nvlTrim+","+nLpad+",'"+cLpad+"')" ;
		
		return lpad;
	}
	
	private String getSqlLpadDecodeNvlTrimField(String field, char nvlCod, char cDecode, char cDecoded, char nLpad, char cLpad){
		
		String nvlTrim = getSqlNvlTrimField(field,nvlCod);
		String decode = "DECODE("+nvlTrim+", '"+cDecode+"','"+cDecoded+"',"+nvlTrim+")";
		String lpad = 	"LPAD("+decode+","+nLpad+",'"+cLpad+"')" ;
		
		return lpad;
	}
	
	private String getSqlNvlTrimField(String field, char cod){
		return  "NVL(TRIM("+field+"),'"+cod+"')";
	}
	

	private String getSQL_SitCConcCatastoCriteria() {
		String sql = "";
		
		RicercaConcEdilizieDTO oggetto = criteria.getRicercaOggetto();
		if(oggetto!=null){
			
			//Parametri catastali IMMOBILE
			sql = (oggetto.getFoglio() == null      || oggetto.getFoglio().equals("")     ? sql : sql + " AND cc.foglio= '" + StringUtils.cleanLeftPad(oggetto.getFoglio(),'0')+"'");
			sql = (oggetto.getParticella() == null  || oggetto.getParticella().equals("") ? sql : sql + " AND cc.particella= '" + StringUtils.cleanLeftPad(oggetto.getParticella(),'0')+"'");
			sql = (oggetto.getSub() == null  || oggetto.getSub().equals("") ? sql : sql + " AND cc.subalterno = '" + StringUtils.cleanLeftPad(oggetto.getSub(),'0')+"'");
		}
		
		return sql;
	}
	
	private String getSQL_SitCConcessioniCriteria() {
		String sql ="";
		
		RicercaConcEdilizieDTO oggetto = criteria.getRicercaOggetto();
		if(oggetto!=null){
			
			//Parametri CONCESSIONE
			sql = (oggetto.getIdConc() == null            || oggetto.getIdConc().equals("")           ? sql : sql + " AND c.id = '" + oggetto.getIdConc() + "'");
			
			sql = (oggetto.getConcNumero() == null        || oggetto.getConcNumero().equals("")       ? sql : sql + " AND UPPER(c.concessione_numero) ='" + oggetto.getConcNumero().toUpperCase() + "'");
			sql = (oggetto.getProtocolloNumero() == null  || oggetto.getProtocolloNumero().equals("") ? sql : sql + " AND UPPER(c.protocollo_numero) = '" + oggetto.getProtocolloNumero().toUpperCase() + "'");
			sql = (oggetto.getProtocolloAnno() == null    || oggetto.getProtocolloAnno().equals("")   ? sql : sql + " AND TO_CHAR(c.protocollo_data,'yyyy') = '" + oggetto.getProtocolloAnno().toUpperCase() + "'");
			
			sql = (oggetto.getProgressivoNumero() == null || oggetto.getProgressivoNumero().equals("")? sql : sql + " AND UPPER(c.progressivo_numero) ='" + oggetto.getProgressivoNumero().toUpperCase() + "'");
			sql = (oggetto.getProgressivoAnno() == null   || oggetto.getProgressivoAnno().equals("")  ? sql : sql + " AND UPPER(c.progressivo_anno) = '" + oggetto.getProgressivoAnno().toUpperCase() + "'");
				
		}
		
		return sql;
	}
	
	private String getSQL_IndirizzoCriteria() {
		String sql = "";
		
		RicercaConcEdilizieDTO oggetto = criteria.getRicercaOggetto();
		if(oggetto!=null){
			
			RicercaCivicoDTO indirizzo = oggetto.getIndirizzo();
			if(indirizzo!=null){
				sql = (indirizzo.getCivico()== null  || indirizzo.getCivico().equals("") ? sql : sql + " AND UPPER(ci.civ_liv1) LIKE '%" + indirizzo.getCivico() + "%'");
				sql = (indirizzo.getDescrizioneVia() == null  || indirizzo.getDescrizioneVia().equals("") ? sql : sql + " AND UPPER(TRIM(ci.sedime||' '||ci.indirizzo)) LIKE '%" + indirizzo.getDescrizioneVia().replace("'","''").toUpperCase() + "%'");
			}
		}
		
		return sql;
	}
	
	private String getSQL_SitCConcPersonaCriteria(){
		String sql ="";
		
		RicercaSoggettoConcEdilizieDTO soggetto = criteria.getRicercaSoggetto();
		if(soggetto!=null){
			sql = (soggetto.getTitolo() == null  || soggetto.getTitolo().equals("") ? sql : sql + " AND cp.titolo = '" + soggetto.getTitolo() + "'");	
		}
		
		return sql;
	}
	
	private String getSQL_SitCPersonaCriteria(){
		String sql ="";
		
		RicercaSoggettoConcEdilizieDTO soggetto = criteria.getRicercaSoggetto();
		if(soggetto!=null){
			sql = (soggetto.getCognome() == null  || soggetto.getCognome().equals("") ? sql : sql + " AND p.cognome = '" + soggetto.getCognome().toUpperCase() + "'");	
			sql = (soggetto.getNome() == null  || soggetto.getNome().equals("") ? sql : sql + " AND p.nome = '" + soggetto.getNome().toUpperCase() + "'");	
			sql = (soggetto.getDenom() == null  || soggetto.getDenom().equals("") ? 
					sql : sql + " AND (p.denominazione LIKE '%" + soggetto.getDenom().toUpperCase() + "%' OR p.cognome||' '||p.nome LIKE '%"+soggetto.getDenom().toUpperCase()+ "%')") ;	
		}
		
		return sql;
		
	}
	
	
	
	
	
}
