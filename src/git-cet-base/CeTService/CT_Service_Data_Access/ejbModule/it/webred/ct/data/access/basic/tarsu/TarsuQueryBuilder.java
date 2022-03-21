package it.webred.ct.data.access.basic.tarsu;

import it.webred.ct.data.access.basic.CTQueryBuilder;
import it.webred.ct.data.access.basic.tarsu.dto.DichiarazioniTarsuSearchCriteria;

public class TarsuQueryBuilder extends CTQueryBuilder {

	private DichiarazioniTarsuSearchCriteria criteria;
	
	private String classe;
	
	private String foglio;
	private String particella;
	private String unimm;
	
	private String via;
	private String civico;
	
	private String soggettoF;
	
	private String soggettoG;
	
	private String[] titoloSoggetto;
	
	private final String SQL_FIELDS_TO_SHOW = 
		"SIT_T_TAR_OGGETTO.ID AS ID_OGG," +
		"NVL(SIT_T_TAR_OGGETTO.FOGLIO,'-') AS FOGLIO, " +
		"NVL(SIT_T_TAR_OGGETTO.NUMERO,'-') AS NUMERO, " +
		"NVL(SIT_T_TAR_OGGETTO.SUB,'-') AS SUB, " +
		//"NVL(SIT_T_TAR_OGGETTO.DES_CLS_RSU,'-') AS DES_CLS_RSU, " +
		//"SIT_T_TAR_OGGETTO.SUP_TOT AS SUP_TOT, " +
		"NVL(SIT_T_TAR_OGGETTO.PROVENIENZA,'-') AS PROVENIENZA," +
		"NVL(SIT_T_TAR_VIA.DESCRIZIONE,'-') AS INDIRIZZO," +
		"NVL(SIT_T_TAR_OGGETTO.NUM_CIV,'-') AS CIVICO ";
	
	private final String SQL_SELECT_BASE_BY_FPS_INDIRIZZO = "select sit_t_tar_oggetto.* " +
			"from SIT_T_TAR_OGGETTO, SIT_T_TAR_VIA " +
			"where SIT_T_TAR_OGGETTO.ID_EXT_VIA = SIT_T_TAR_VIA.ID_EXT(+)";
			//Condizioni Oggetto
	

	private final String SQL_SELECT_BASE_BY_SOGG = 
			"select sit_t_tar_oggetto.* " +
			"from sit_t_tar_oggetto, @TITOLO_TABLE@, sit_t_tar_sogg" +
			"where LISTA_OGG.id_ext = @TITOLO_TABLE@.id_ext_ogg_rsu " +
			"and @TITOLO_TABLE@.id_ext_sogg = sit_t_tar_sogg.id_ext "; 
			//Condizioni Soggetto
	
	
	private final String SQL_SELECT_LISTA = "select distinct " + this.SQL_FIELDS_TO_SHOW +
    										"FROM( @SQL_SELECT_BASE@ )order by FOGLIO, NUMERO, SUB;";
	
	private final String SQL_SELECT_COUNT_LISTA = "select count(*) as conteggio from ( @SQL_SELECT_LISTA@ )";
	

	public TarsuQueryBuilder(DichiarazioniTarsuSearchCriteria criteria) {
		this.criteria = criteria;
		
		classe = criteria.getClasse();
		
		foglio = criteria.getFoglio();
		particella = criteria.getParticella();
		unimm = criteria.getUnimm();
		
		via = criteria.getVia();
		civico = criteria.getCivico();
		
		soggettoF = criteria.getSoggettoF();
		soggettoG = criteria.getSoggettoG();
		
		titoloSoggetto = criteria.getTitoloSoggetto();
		
	}
	
	public String createQuery(boolean isCount) {
		
		String sql = null;
		String sqlpart = null;
		
		if(isSearchByFPS()){
			sqlpart= this.SQL_SELECT_BASE_BY_FPS_INDIRIZZO;
			sqlpart += this.getSQL_FPSCriteria();
		}
		
		if(isSearchByIndirizzo()){
			sqlpart = this.SQL_SELECT_BASE_BY_FPS_INDIRIZZO;
			sqlpart += this.getSQL_IndirizzoCriteria();
		}
		if(isSearchBySoggetto()){
			sqlpart= this.getSQL_SoggettiCriteria();
		
		}
		
		logger.info("Count ["+isCount+"]");
		
		if (isCount)
			sql = this.SQL_SELECT_COUNT_LISTA;
		else
			sql = this.SQL_SELECT_LISTA;
		
		logger.info("SQL [" + sql + "]");
		
		return sql;
	}
	
	
	private String getSQL_FPSCriteria() {
		String sqlCriteria = "";
		
		sqlCriteria = (foglio == null  || foglio.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + addCondition("sit_t_tar_oggetto.foglio","=",foglio));
		
		sqlCriteria = (particella == null || particella.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + addCondition("sit_t_tar_oggetto.numero","=",unimm));
		
		sqlCriteria = (unimm == null || unimm.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + addCondition("sit_t_tar_oggetto.sub","=",unimm));

		return sqlCriteria;
	}
	
	private String getSQL_IndirizzoCriteria() {
		String sqlCriteria = "";
	
		sqlCriteria = (via == null  || via.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + addCondition("sit_t_tar_oggetto.des_ind", criteria.getViaOp(), via));
		
		sqlCriteria = (civico == null || civico.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + addCondition("sit_t_tar_oggetto.num_civ","=",civico));
                
		return sqlCriteria;
	}
	
	
	protected String addCondition(String field, String operator, String param) {
	    	
			String criteria = "";
			operator = operator.trim();
			param = param.trim();
			
			if (operator.equals("="))
	    	    criteria += " ("+ field +") "+ operator +" '" + param.toUpperCase() + "' " ;    	
	    	else if (operator.equalsIgnoreCase("LIKE"))  	    
	    		criteria += " ("+ field +") "+ operator +" '%' || '"+ param.toUpperCase()+"' || '%' " ;   
			
			return criteria;
	 }
	
	protected String addOperator(String criteria) {
    	if (criteria == null)
    	    return criteria;    	
    	else    	    
    	    return criteria += " AND ";
    }
	

	protected String getSQL_SoggettiCriteria(){	
			
			//Scansiona l'array di tipi soggetto da considerare nella query
			boolean cnt = false;
			boolean dic = false;
			boolean ult = false;
			if (titoloSoggetto == null || titoloSoggetto.length == 0) {
				cnt = true;
				dic = true;
				ult = true;
			} else {
				for (String titoloSogg : titoloSoggetto) {
					if ("CNT".equalsIgnoreCase(titoloSogg)) {
						cnt = true;
					}
					if ("DIC".equalsIgnoreCase(titoloSogg)) {
						dic = true;
					}
					if ("ULT".equalsIgnoreCase(titoloSogg)) {
						ult = true;
					}
				}
			}
			
			//Crea le condizioni sul soggetto
			String soggCriteria = "";
			soggCriteria = (soggettoF == null  || soggettoF.trim().equals("") ? soggCriteria : addOperator(soggCriteria) + addCondition("sit_t_tar_sogg.COD_FISC","=",soggettoF));
			soggCriteria = (soggettoG == null  || soggettoG.trim().equals("") ? soggCriteria : addOperator(soggCriteria) + addCondition("sit_t_tar_sogg.PART_IVA","=",soggettoG));
			
			String sqlSogg = this.SQL_SELECT_BASE_BY_SOGG + soggCriteria;
			
			boolean addUnion = false;
			String sqlAdd = null;
			String sqlSogg_cnt = null;
			String sqlSogg_dic = null;
			String sqlSogg_ult = null;
			
			
			if(cnt){
				
				sqlSogg_cnt = sqlSogg;
				//Inserisce il tipo di soggetto
				sqlSogg_cnt = sqlSogg_cnt.replace("@TITOLO@", "Contribuente");
				//Inserisce il nome della tabella
				sqlSogg_cnt = sqlSogg_cnt.replace("@TITOLO_TABLE@", "sit_t_tar_contrib" );
				
				sqlAdd = sqlSogg_cnt;
			}
			
			if(dic){
				
				
				sqlSogg_dic += sqlSogg;
				//Inserisce il tipo di soggetto
				sqlSogg_dic = sqlSogg_dic.replace("@TITOLO@", "DICHIARANTE");
				//Inserisce il nome della tabella
				sqlSogg_dic = sqlSogg_dic.replace("@TITOLO_TABLE@", "sit_t_tar_dich" );
				
				
				if(sqlAdd!=null)
					sqlAdd += " UNION " + sqlSogg_dic;
				else
					sqlAdd = sqlSogg_dic;
				
			}
			if(ult){
				
				sqlSogg_ult = sqlSogg;
				//Inserisce il tipo di soggetto
				sqlSogg_ult = sqlSogg_cnt.replace("@TITOLO@", "sit_t_tar_ult_sogg.");
				//Inserisce il nome della tabella
				sqlSogg_ult = sqlSogg_cnt.replace("@TITOLO_TABLE@", "sit_t_tar_ult_sogg" );
				
				if(sqlAdd!=null)
					sqlAdd += " UNION " + sqlSogg_ult;
				else
					sqlAdd = sqlSogg_ult;
			}
	
		return sqlAdd;
	}
	
	protected boolean isSearchByFPS(){
		boolean result = false;
		
		boolean isFoglio = (foglio != null && !foglio.equals(""));    // Parametro impostato
		boolean isParticella = (particella != null && !particella.equals(""));    // Parametro impostato
		boolean isUnimm = (unimm != null && !unimm.equals(""));    // Parametro impostato
		
		result = isFoglio || isParticella || isUnimm;
		
		return result;
	}
	
	protected boolean isSearchByIndirizzo(){
		boolean result = false;
		
		boolean isVia = (via != null && !via.equals(""));    // Parametro impostato
		boolean isCivico = (civico != null && !civico.equals(""));    // Parametro impostato
		
		result = isVia || isCivico;
		return result;
	}
	
	protected boolean isSearchBySoggetto(){
		boolean result = false;
		
		result = (soggettoF!= null && !soggettoF.equals("")) || (soggettoG!= null && !soggettoG.equals(""));
		
		return result;
	}

}
