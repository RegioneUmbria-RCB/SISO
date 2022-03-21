package it.webred.cs.csa.ejb.queryBuilder;

import it.webred.cs.csa.ejb.dto.SinbaSearchCriteria;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

public class ExportValutazioneSinbaQueryBuilder extends QueryBuilderBase{

	private SinbaSearchCriteria criteria;
	
	private String CASO_ID = "casoId";
	private String CF = "cf";
	private String COGNOME = "cognome";
	private String NOME = "nome";
	private String DATA_INIZIO = "dataInizio";
	private String DATA_FINE = "dataFine";
	private String COD_CATASTALE = "belfioreOperatore";
	
	public ExportValutazioneSinbaQueryBuilder(SinbaSearchCriteria criteria) {
		this.criteria = criteria;
	}
	
	public ExportValutazioneSinbaQueryBuilder(){
		criteria = null;
	}
	
	private String createQuerySinbaMinoriBody(){
		
		String body="SELECT " + 
				"    ana.id ANAGRAFICA_ID, " + 
				"    ana.cognome COGNOME, " + 
				"    cast( ana.nome as VARCHAR2(100 BYTE)) NOME, " + 
				"    ana.data_nascita DATA_NASCITA, " + 
				"    casoPresoInCarico.data_presa_carico DATA_PRESA_CARICO, " + 
//				"    organizz.belfiore BELFIORE," + 
				//"    organizz.cod_routing BELFIORE," + 
				"    organizz.cod_catastale COD_CATASTALE," + 
				"    caso.ID CASO_ID," + 
				"    ana.cf CF,"
				+ "	 sinba.diario_id DIARIO_ID,"
				+ "val.descrizione_scheda DESCRIZIONE_SCHEDA,"
				+ "sinba.data_export DATA_EXPORT,"
				+ "sinba.file_export_id FILE_EXPORT_ID,"
				+ "diario.DT_INS DATA_SCHEDA_SINBA, "
				+ "clob.id ID_CLOB, "
				+ "clob.dati_clob DATI_CLOB, " + 
				 " exportSinba.IDENTIFICAZIONE_FLUSSO " + //SISO - 777
				" FROM " + 
				"    cs_a_anagrafica ana " + 
				"    inner join cs_a_soggetto sogg on sogg.ANAGRAFICA_ID=ana.ID " + 
				"    inner join cs_a_caso caso on caso.ID=sogg.CASO_ID " + 
				"    left join  " + 
				"        ( " + 
				"            select stato.NOME, caso1.ID CASO_ID, min(diario1.DT_INS) data_presa_carico, step.ORGANIZZAZIONE_ID " + 
				"            from cs_a_caso caso1 left join cs_it_step step on step.caso_id=caso1.ID  " + 
				"            inner join cs_cfg_it_stato stato on step.CFG_IT_STATO_ID=stato.ID  " + 
				"            inner join cs_d_diario diario1 on diario1.ID=step.DIARIO_ID  " + 
				"            and diario1.TIPO_DIARIO_ID=1 " + 
				"            where stato.NOME like '%Carico%' " + 
				"            GROUP BY caso1.ID, stato.NOME,step.ORGANIZZAZIONE_ID " + 
				"        )  " + 
				"     casoPresoInCarico on casoPresoInCarico.caso_id = caso.ID " + 
				"     inner join cs_o_organizzazione organizz on casoPresoInCarico.ORGANIZZAZIONE_ID=organizz.ID " + 
				"     left join cs_d_diario diario on diario.caso_id=caso.ID and diario.TIPO_DIARIO_ID=23 " +
				"	  left join cs_d_clob clob on diario.clob_id=clob.Id " +
				"	  LEFT JOIN cs_d_valutazione val ON val.diario_id = diario.ID "+   
				  "     left join cs_d_sinba sinba on sinba.diario_id=diario.ID " +
				  " left join cs_d_export_sinba exportSinba on sinba.FILE_EXPORT_ID = exportSinba.ID ";  //SISO - 777
				
		
//		 body="select ana.id," + 
//		 		"ana.cognome," + 
//		 		"ana.nome," + 
////		 		"ana.data_nascita"+
//		 		"FROM  cs_a_anagrafica ana  WHERE ROWNUM <= 10";
			
		
		return body;
	}

	
	

	//TASK-777
	public String createQueryMinoriPerSinbaDaEportare(){
		String sql = createQuerySinbaMinoriBody()+ "  WHERE  organizz.cod_catastale=:"+COD_CATASTALE;
				//"  WHERE  organizz.BELFIORE=:belfioreOperatore ";
		
		//SISO-777 a ricerca deve essere fatta secondo ogni singolo criterio inseribile
		sql+=(criteria.getCasoId()!=null && criteria.getCasoId()>0) ? " and caso.ID= :"+this.CASO_ID : "";
		sql+=!StringUtils.isBlank(criteria.getCodiceFiscale()) ? " and UPPER(ana.CF)= :"+ CF : ""; 
		sql+=!StringUtils.isBlank(criteria.getCognome()) ? " and UPPER(ana.COGNOME)= :"+ COGNOME : ""; 
		sql+=!StringUtils.isBlank(criteria.getNome()) ? " and UPPER(ana.NOME)= :"+ NOME : ""; 

		 if(criteria.getDataInizio()!=null &&  criteria.getDataFine()!=null){
			sql+="	and" + 
					"   round((TO_DATE(:"+DATA_FINE+",'dd/MM/yyyy') - ana.data_nascita)/365) <18 " + 
					"   and TO_DATE(:"+DATA_FINE+",'dd/MM/yyyy') > ana.data_nascita " + 
					"   and (casoPresoInCarico.data_presa_carico between TO_DATE(:"+DATA_INIZIO+",'dd/MM/yyyy') and TO_DATE(:"+DATA_FINE+",'dd/MM/yyyy'))";
		}

		sql+=createQueryMinoriPerSinbaDaEportareOrderBy();
		
		return sql;
	}
	
	private String createQueryMinoriPerSinbaDaEportareOrderBy(){
		
		String sql=" Order by ana.cognome, ana.nome, sinba.diario_id desc";
		
		return sql;
	}
	
	public String setParameters(Query q){
		String params = "";
		
		if(criteria!=null){
			if(!StringUtils.isBlank(criteria.getOrganizzazioneBelfiore()))
				params = setParameter(q, COD_CATASTALE, criteria.getOrganizzazioneBelfiore(), params);
			
			if(criteria.getCasoId()!=null && criteria.getCasoId()>0)
				params = setParameter(q, CASO_ID, criteria.getCasoId(), params);
			
			if(!StringUtils.isBlank(criteria.getCodiceFiscale()))
				params = setParameter(q, CF, criteria.getCodiceFiscale().toUpperCase(), params);
			
			if(criteria.getDataInizio()!=null && criteria.getDataFine()!=null){
				params = setParameter(q, DATA_INIZIO, ddMMyyyy.format(criteria.getDataInizio()), params);
				params = setParameter(q, DATA_FINE, ddMMyyyy.format(criteria.getDataFine()), params);
			}
			
			if(!StringUtils.isBlank(criteria.getCognome()))
				params = setParameter(q, COGNOME, criteria.getCognome().toUpperCase(), params);
			
			if(!StringUtils.isBlank(criteria.getNome()))
				params = setParameter(q, NOME, criteria.getNome().toUpperCase(), params);
		}
		return params;
	}
}
