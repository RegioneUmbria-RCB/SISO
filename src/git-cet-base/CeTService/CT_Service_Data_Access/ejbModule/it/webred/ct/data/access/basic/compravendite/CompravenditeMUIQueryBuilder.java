package it.webred.ct.data.access.basic.compravendite;

import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.compravendite.dto.RicercaCompravenditeDTO;

public class CompravenditeMUIQueryBuilder {
	private final String SQL_SOGGETTI_NOTA = 
		"SELECT DISTINCT "
		+ "SOGG.TIPO_SOGGETTO, SOGG.COGNOME, SOGG.NOME, SOGG.CODICE_FISCALE, SOGG.DENOMINAZIONE, SOGG.CODICE_FISCALE_G, "
		+ "TITO.SC_FLAG_TIPO_TITOL_NOTA , TITO.SF_FLAG_TIPO_TITOL_NOTA, "
		+ "TITO.SC_CODICE_DIRITTO, TITO.SC_QUOTA_NUMERATORE, TITO.SC_QUOTA_DENOMINATORE, "
		+ "TITO.SF_CODICE_DIRITTO, TITO.SF_QUOTA_NUMERATORE, TITO.SF_QUOTA_DENOMINATORE, "
		+ "SOGG.SEDE, SOGG.SESSO, SOGG.DATA_NASCITA, SOGG.LUOGO_NASCITA, NOTA.IID "+
	    "FROM MUI_NOTA_TRAS NOTA, MUI_SOGGETTI SOGG, MUI_TITOLARITA TITO " +
		"WHERE TITO.IID_NOTA = SOGG.IID_NOTA  " +
		  "AND TITO.IID_FORNITURA = SOGG.IID_FORNITURA " +
		  "AND TITO.ID_SOGGETTO_NOTA = SOGG.ID_SOGGETTO_NOTA " +
		  "AND SOGG.IID_NOTA = NOTA.IID " + 
		  "AND SOGG.IID_FORNITURA = NOTA.IID_FORNITURA " + 
		  "@SQL_CONDIZIONI_WHERE@ " +
		  "ORDER BY SF_FLAG_TIPO_TITOL_NOTA, SC_FLAG_TIPO_TITOL_NOTA, COGNOME,NOME, DENOMINAZIONE ";
	
	private final String HQL_NOTE_BY_CRITERIA = "from MuiNotaTras NOTA where 1 = 1 @HQL_CONDIZIONI_WHERE@ ";
	
	private final String HQL_NOTE_BY_COORD = "select distinct nota "
			+ " from MuiFabbricatiIdentifica fabbr, MuiNotaTras nota , MuiFabbricatiInfo fabInfo "
			+ " where nota.iid = fabbr.iidNota "
			+ " AND nota.iidFornitura = fabbr.iidFornitura "
			+ " AND fabbr.iidFornitura= fabinfo.iidFornitura "
			+ " AND fabbr.iidNota = fabinfo.iidNota "
			+ " AND fabbr.idImmobile = fabinfo.idImmobile "
			+ " @HQL_CONDIZIONI_WHERE@ "
			+ " ORDER BY nota.annoNotaDate DESC ";
	
	private final String HQL_FABBRICATI_BY_PARAMS = "from MuiFabbricatiIdentifica FABBIDE, MuiFabbricatiInfo FABBINFO where FABBIDE.iidFabbricatiinfo = FABBINFO.iid @HQL_FABBRICATI_WHERE@ ";
	
	private final String HQL_TERRENI_BY_PARAMS = "from MuiTerreniInfo TERRINFO where 1 = 1 @HQL_TERRENI_WHERE@ ";
	
	private final String SQL_SOGGETTI_BY_PARAMS = "select SOGG.IID, SOGG.IID_FORNITURA, SOGG.ID_NOTA, SOGG.IID_NOTA, SOGG.TIPO_SOGGETTO, "
			+ " SOGG.COGNOME, SOGG.NOME, SOGG.SESSO, SOGG.DATA_NASCITA, SOGG.LUOGO_NASCITA, "
			+ " SOGG.CODICE_FISCALE, SOGG.DENOMINAZIONE, SOGG.SEDE, SOGG.CODICE_FISCALE_G, "
			//+ " INDSOGG.IID, INDSOGG.IID_FORNITURA, INDSOGG.ID_NOTA, INDSOGG.IID_NOTA, INDSOGG.IID_SOGGETTO, "
			+ " INDSOGG.TIPO_INDIRIZZO, INDSOGG.COMUNE, INDSOGG.PROVINCIA, INDSOGG.INDIRIZZO, INDSOGG.CAP "
			+ " from MUI_SOGGETTI SOGG left join MUI_INDIRIZZI_SOG INDSOGG on SOGG.IID = INDSOGG.IID_SOGGETTO"
			+ " where 1 = 1 @SQL_SOGGETTI_WHERE@ ";
	
	public String getSQL_SOGGETTI_NOTA() {

		return SQL_SOGGETTI_NOTA;
	}//-------------------------------------------------------------------------
	
	public String createQueryListaSoggettiNotaByCriteria(RicercaCompravenditeDTO criteria){
		String sql = this.SQL_SOGGETTI_NOTA;
				
		String condSogg = "";
		condSogg = (criteria.getIidNota() == null ? condSogg : addOperator(condSogg) +  " NOTA.IID = '"+ criteria.getIidNota() +"' " );
		//condSogg = (criteria.getIdNota() == null ? condSogg : addOperator(condSogg) +  " NOTA.ID_NOTA = '"+ criteria.getIdNota() +"' " );
		if (criteria.getTipoSoggetto() != null && !criteria.getTipoSoggetto().trim().equalsIgnoreCase("")){
			if (criteria.getTipoSoggetto().trim().equalsIgnoreCase("P")){
				/*
				 * Soggetto Fisico
				 */
				condSogg = (criteria.getIdentificativoSoggetto() == null || criteria.getIdentificativoSoggetto().trim().equals("") ? condSogg : addOperator(condSogg) +  " SOGG.CODICE_FISCALE = '"+ criteria.getIdentificativoSoggetto()+"' " );
			}else if (criteria.getTipoSoggetto().trim().equalsIgnoreCase("G")){
				/*
				 * Soggetto Giuridico
				 */
				condSogg = (criteria.getIdentificativoSoggetto() == null || criteria.getIdentificativoSoggetto().trim().equals("") ? condSogg : addOperator(condSogg) +  " SOGG.CODICE_FISCALE_G = '"+ criteria.getIdentificativoSoggetto()+"' " );				
			}
		}else{
			/*
			 * se il tipo soggetto non è valorizzato applico una OR su entrambe i campi 
			 */
			condSogg = (criteria.getIdentificativoSoggetto() == null || criteria.getIdentificativoSoggetto().trim().equals("") ? condSogg : addOperator(condSogg) +  " ( SOGG.CODICE_FISCALE = '"+ criteria.getIdentificativoSoggetto()+"' OR SOGG.CODICE_FISCALE_G = '"+ criteria.getIdentificativoSoggetto()+"' ) " );			
		}
		
		sql = sql.replace("@SQL_CONDIZIONI_WHERE@", condSogg);
		
		return sql;
		
	}//-------------------------------------------------------------------------
	
	public String createQueryListaNoteByCriteria(RicercaCompravenditeDTO criteria){
		String hql = this.HQL_NOTE_BY_CRITERIA;
				
		String condSogg = "";
		condSogg = (criteria.getIidNota() == null ? condSogg : addOperator(condSogg) +  " NOTA.iid = '"+ criteria.getIidNota() +"' " );
		
		hql = hql.replace("@HQL_CONDIZIONI_WHERE@", condSogg);
		
		return hql;
	}//-------------------------------------------------------------------------

	public String createQueryFabbricatiByParams(RicercaCompravenditeDTO criteria){
		/*
		 * Questo metodo tiene conto del fatto che le due tabelle MUI_FABBRICATI_INFO
		 * e MUI_FABBRICATI_IDENTIFICA hanno lo stesso numero di righe
		 */
		String hql = this.HQL_FABBRICATI_BY_PARAMS;
				
		String condSogg = "";
		condSogg = (criteria.getIidNota() == null ? condSogg : addOperator(condSogg) +  " FABBIDE.iidNota = '"+ criteria.getIidNota() +"' " );
		condSogg = (criteria.getIidFornitura() == null ? condSogg : addOperator(condSogg) +  " FABBIDE.iidFornitura = '"+ criteria.getIidFornitura() +"' " );
		condSogg = ( ( criteria.getSezione() == null || criteria.getSezione().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " FABBIDE.sezioneCensuaria = '"+ criteria.getSezione() +"' " );
		condSogg = ( ( criteria.getFoglio() == null || criteria.getFoglio().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " FABBIDE.foglio = '"+ criteria.getFoglio() +"' " );
		condSogg = ( ( criteria.getParticella() == null || criteria.getParticella().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " FABBIDE.numero = '"+ criteria.getParticella() +"' " );
		condSogg = ( ( criteria.getSub() == null || criteria.getSub().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " FABBIDE.subalterno = '"+ criteria.getSub() +"' " );
		
		hql = hql.replace("@HQL_FABBRICATI_WHERE@", condSogg);
		
		return hql;
	}//-------------------------------------------------------------------------
	
	public String createQueryTerreniByParams(RicercaCompravenditeDTO criteria){
		
		String hql = this.HQL_TERRENI_BY_PARAMS;
				
		String condSogg = "";
		condSogg = (criteria.getIidNota() == null ? condSogg : addOperator(condSogg) +  " TERRINFO.iidNota = '"+ criteria.getIidNota() +"' " );
		condSogg = (criteria.getIidFornitura() == null ? condSogg : addOperator(condSogg) +  " TERRINFO.iidFornitura = '"+ criteria.getIidFornitura() +"' " );
		condSogg = ( ( criteria.getSezione() == null || criteria.getSezione().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " TERRINFO.sezioneCensuaruia = '"+ criteria.getSezione() +"' " );
		condSogg = ( ( criteria.getFoglio() == null || criteria.getFoglio().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " TERRINFO.foglio = '"+ criteria.getFoglio() +"' " );
		condSogg = ( ( criteria.getParticella() == null || criteria.getParticella().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " TERRINFO.numero = '"+ criteria.getParticella() +"' " );
		condSogg = ( ( criteria.getSub() == null || criteria.getSub().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " TERRINFO.subalterno = '"+ criteria.getSub() +"' " );
		
		hql = hql.replace("@HQL_TERRENI_WHERE@", condSogg);
		
		return hql;
	}//-------------------------------------------------------------------------
	
	public String createQuerySoggettiByParams(RicercaCompravenditeDTO criteria){
		
		String sql = this.SQL_SOGGETTI_BY_PARAMS;
				
		String condSogg = "";
		condSogg = (criteria.getIidNota() == null ? condSogg : addOperator(condSogg) +  " SOGG.IID_NOTA = '"+ criteria.getIidNota() +"' " );
		condSogg = (criteria.getIidFornitura() == null ? condSogg : addOperator(condSogg) +  " SOGG.IID_FORNITURA = '"+ criteria.getIidFornitura() +"' " );
		if (criteria.getTipoSoggetto() != null && !criteria.getTipoSoggetto().trim().equalsIgnoreCase("")){
			if (criteria.getTipoSoggetto().trim().equalsIgnoreCase("P")){
				/*
				 * Soggetto Fisico
				 */
				condSogg = (criteria.getIdentificativoSoggetto() == null || criteria.getIdentificativoSoggetto().trim().equals("") ? condSogg : addOperator(condSogg) +  " SOGG.CODICE_FISCALE = '"+ criteria.getIdentificativoSoggetto()+"' " );
			}else if (criteria.getTipoSoggetto().trim().equalsIgnoreCase("G")){
				/*
				 * Soggetto Giuridico
				 */
				condSogg = (criteria.getIdentificativoSoggetto() == null || criteria.getIdentificativoSoggetto().trim().equals("") ? condSogg : addOperator(condSogg) +  " SOGG.CODICE_FISCALE_G = '"+ criteria.getIdentificativoSoggetto()+"' " );				
			}
		}
		
		sql = sql.replace("@SQL_SOGGETTI_WHERE@", condSogg);
		
		return sql;
	}//-------------------------------------------------------------------------
	
	public String createQueryListaNoteByCoord(RicercaOggettoCatDTO criteria){
		String hql = this.HQL_NOTE_BY_COORD;

		String condSogg = "";
		condSogg = ( (criteria.getSezione() == null || criteria.getSezione().trim().equalsIgnoreCase("") )? condSogg : addOperator(condSogg) +  " fabbr.sezioneUrbana = '"+ criteria.getSezione() +"' " );
		condSogg = ( (criteria.getFoglio() == null  || criteria.getFoglio().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " LPAD(fabbr.foglio, 4, '0') = LPAD('"+ criteria.getFoglio() +"', 4, '0')  " );
		condSogg = ( (criteria.getParticella() == null || criteria.getParticella().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " LPAD(fabbr.numero, 5, '0') = LPAD('"+ criteria.getParticella() +"', 5, '0')  " );
		condSogg = ( (criteria.getUnimm() == null || criteria.getUnimm().trim().equalsIgnoreCase("") ) ? condSogg : addOperator(condSogg) +  " LPAD(fabbr.subalterno, 4, '0') = LPAD('"+ criteria.getUnimm() +"', 4, '0') " );
		
		hql = hql.replace("@HQL_CONDIZIONI_WHERE@", condSogg);
		
		return hql;
	}//-------------------------------------------------------------------------
	
	protected String addOperator(String criteria) {
    	if (criteria == null)
    	    return criteria;    	
    	else    	    
    	    return criteria += " AND ";
    }//-------------------------------------------------------------------------
	
}
