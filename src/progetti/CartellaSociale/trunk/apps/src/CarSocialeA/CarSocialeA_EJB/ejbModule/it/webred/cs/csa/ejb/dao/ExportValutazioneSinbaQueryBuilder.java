package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.SinbaSearchCriteria;

public class ExportValutazioneSinbaQueryBuilder {

	private SinbaSearchCriteria criteria;
	private BaseDTO bDtoCriteria;
	
	public ExportValutazioneSinbaQueryBuilder(SinbaSearchCriteria criteria) {
		this.criteria = criteria;
	}
	
	public ExportValutazioneSinbaQueryBuilder(BaseDTO criteria) {
		this.bDtoCriteria = criteria;
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
		String sql = createQuerySinbaMinoriBody()
		
		+       "  WHERE  organizz.cod_catastale=:belfioreOperatore ";
				//"  WHERE  organizz.BELFIORE=:belfioreOperatore ";
		
		//SISO-777 a ricerca deve essere fatta secondo ogni singolo criterio inseribile
		if(criteria.getCasoId()!=null && criteria.getCasoId()>0)
		{
			sql+=" and caso.ID="+criteria.getCasoId();
		}
		if(criteria.getCodiceFiscale()!=null && !criteria.getCodiceFiscale().isEmpty())
		{
			sql+=" and UPPER(ana.CF)='"+criteria.getCodiceFiscale().toUpperCase()+"'";
		}
		 if(criteria.getDataInizio()!=null && !criteria.getDataInizio().isEmpty() &&  criteria.getDataFine()!=null && !criteria.getDataFine().isEmpty()   )
		{
			sql+="	and" + 
					"   round(( TO_DATE(:dataFine, 'dd/MM/yyyy') - ana.data_nascita)/365) <18 " + 
					"   and TO_DATE(:dataFine, 'dd/MM/yyyy') > ana.data_nascita " + 
					"   and  (TO_DATE(:dataInizio, 'dd/MM/yyyy') < casoPresoInCarico.data_presa_carico " +
					"	and casoPresoInCarico.data_presa_carico < TO_DATE(:dataFine, 'dd/MM/yyyy') )";

		}


		if((criteria.getCognome()!=null && !criteria.getCognome().isEmpty()) )
		{
			sql+=" and UPPER(ana.COGNOME)='"+criteria.getCognome().toUpperCase()+"'";
		}
		if( (criteria.getNome()!=null && !criteria.getNome().isEmpty()))
		{
			sql+=" and UPPER(ana.NOME)='"+criteria.getNome().toUpperCase()+"'";
		}

		



		
		sql+=createQueryMinoriPerSinbaDaEportareOrderBy();
		
		return sql;
	}
	
	
	
	
	public String createQueryMinoriPerSinbaDaEportareByCasoId(){
		String sql = createQuerySinbaMinoriBody()
				+       
						"  WHERE  1=1 ";
				
				if(bDtoCriteria.getObj2()!=null && (Long)bDtoCriteria.getObj2()>0)
				{
					Long idCaso=(Long)bDtoCriteria.getObj2();
					sql+=" and caso.ID="+idCaso;
				}
				
				
				
				sql+=createQueryMinoriPerSinbaDaEportareOrderBy();
							
				return sql;
		
	}
	
	private String createQueryMinoriPerSinbaDaEportareOrderBy(){
		
		String sql=" Order by ana.cognome, ana.nome, sinba.diario_id desc";
		
		return sql;
	}
	}
