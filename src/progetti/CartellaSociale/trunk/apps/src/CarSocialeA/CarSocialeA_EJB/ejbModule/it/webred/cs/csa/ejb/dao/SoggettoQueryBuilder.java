package it.webred.cs.csa.ejb.dao;

import java.util.Arrays;

import it.webred.cs.csa.ejb.dto.CasoSearchCriteria;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.StrutturaTribunale;

public class SoggettoQueryBuilder {

	private CasoSearchCriteria criteria;
	
	public SoggettoQueryBuilder() {
	}

	public SoggettoQueryBuilder(CasoSearchCriteria criteria) {
		this.criteria = criteria;
	}

	public String createQueryListaCasiAssegnati(boolean count){
		String sql = "";
		if(count)
			sql += "SELECT count(DISTINCT ANAGRAFICA_ID) ";
		else sql += "SELECT DISTINCT ANAGRAFICA_ID, DATA_CREAZIONE, CFG_IT_STATO_ID, upper(cast(DENOMINAZIONE as varchar2(100))) DENOMINAZIONE, DATA_APERTURA, CASO_ID, AIND.COM_DES res_comune, aind.prov res_prov ";  //SISO-812

		String sqlCasi = "SELECT DISTINCT A.COGNOME || ' ' || A.NOME DENOMINAZIONE, A.DATA_NASCITA, A.CF," +
			" S.ANAGRAFICA_ID, ITD.DT_AMMINISTRATIVA as DATA_CREAZIONE, IT.CFG_IT_STATO_ID, SC.CATEGORIA_SOCIALE_ID, SC.DATA_FINE_APP, "+
			" coto.flag_responsabile, nvl(os.operatore_id,'') operatore_id, ITDF.DT_AMMINISTRATIVA AS DATA_APERTURA, S.CASO_ID AS CASO_ID " +//SISO-812
			" FROM " +
			" CS_A_ANAGRAFICA a, "+
			" CS_A_CASO c, "+
			" CS_A_SOGGETTO S, "+
			" CS_A_SOGGETTO_CATEGORIA_SOC sc, " +
			" CS_REL_SETTORE_CATSOC cs, "+
			" CS_IT_STEP ITF, CS_D_DIARIO ITDF, "+
			" CS_IT_STEP it, CS_D_DIARIO ITD, " +
			" CS_A_CASO_OPE_TIPO_OPE coto," +
            " CS_O_OPERATORE_TIPO_OPERATORE oto," +
            " CS_O_OPERATORE_SETTORE os," +
            " CS_O_OPERATORE o," +
            " CS_O_SETTORE sett, " +
            " CS_A_CASO_ACCESSO_FASCICOLO secliv "+
			" WHERE S.CASO_ID = C.ID " +
            " AND SECLIV.CASO_ID=C.ID "+
			" AND S.ANAGRAFICA_ID = A.ID" +
			" AND SC.SOGGETTO_ANAGRAFICA_ID = S.ANAGRAFICA_ID" +
			" AND SC.DATA_FINE_APP >= sysdate" +
			" AND SC.CATEGORIA_SOCIALE_ID = CS.CATEGORIA_SOCIALE_ID" +
			" AND CS.SETTORE_ID = SETT.ID " +
			" AND (CS.SETTORE_ID=IT.SETTORE_ID OR it.settore_id is null)" + //Condizione per recuperare casi corrispondenti al settore nel caso in cui una stessa categoria sociale sia associata a settori diversi di organizzazioni diverse
			" AND CS.ABILITATO = 1" +
			" AND IT.ID = (SELECT MAX(it2.id) FROM CS_IT_STEP it2 WHERE IT2.CASO_ID = IT.CASO_ID)  AND IT.CASO_ID = S.CASO_ID AND IT.DIARIO_ID=ITD.ID" +
			" AND ITF.ID = (SELECT MIN(it2.id) FROM CS_IT_STEP it2 WHERE IT2.CASO_ID = ITF.CASO_ID) AND ITF.CASO_ID = S.CASO_ID AND ITF.DIARIO_ID=ITDF.ID " +
			" AND COTO.CASO_ID (+)= C.ID" +
            " AND OTO.ID (+)= COTO.OPERATORE_TIPO_OPERATORE_ID" +
            " AND OS.ID (+)= OTO.OPERATORE_SETTORE_ID" +
            " AND O.ID (+)= OS.OPERATORE_ID" +
            " AND COTO.DATA_FINE_APP (+)>= SYSDATE "+
            " AND SYSDATE BETWEEN SECLIV.DATA_INIZIO_APP AND SECLIV.DATA_FINE_APP ";
		
		if(criteria != null) {
			//Evitare che i casi di un comune (su cui l'operatore è responsabile, vengano visti all'interno di un comune B su cui è solo operatore
			if(criteria.getIdOrganizzazione() != null)
				sqlCasi += " AND secliv.ID_ORGANIZZAZIONE = " + criteria.getIdOrganizzazione();
			
			if(criteria.getIdSettore()!=null)
				sqlCasi += " AND secliv.ID_SETTORE= "+ criteria.getIdSettore();
		}
		
		sql += " FROM (" + sqlCasi + " ) U "
				+ "  LEFT JOIN CS_A_DATI_SOCIALI DS ON (DS.SOGGETTO_ANAGRAFICA_ID=U.ANAGRAFICA_ID AND DS.DATA_FINE_APP>= SYSDATE )  "
				+ "  LEFT JOIN CS_A_TRIBUNALE TR ON (TR.SOGGETTO_ANAGRAFICA_ID=U.ANAGRAFICA_ID AND TR.DATA_FINE_APP>= SYSDATE )  "
				+ "  LEFT JOIN CS_A_INDIRIZZO IND ON ( U.ANAGRAFICA_ID = IND.SOGGETTO_ANAGRAFICA_ID "
				+ " AND IND.TIPO_INDIRIZZO_ID = "+DataModelCostanti.TipiIndirizzi.RESIDENZA_ID
				+	" AND NVL(IND.DATA_FINE_APP, TO_DATE('31/12/9999','dd/MM/yyyy'))>= SYSDATE )"
				+ "  LEFT JOIN CS_A_ANA_INDIRIZZO AIND ON (IND.ANA_INDIRIZZO_ID = AIND.id) ";
		
			String whereCond = getSQLCriteria();
			sql += " WHERE 1 = 1 " + whereCond;
        
			if(!count)
				sql += " ORDER BY DENOMINAZIONE, DATA_CREAZIONE DESC";
			
			return sql;
	}
	
	public String createQueryListaCasi(boolean count) {

		//I casi segnalati verso l'utente loggato sono in prima posizione
		//seguiti dai casi per cui ha i permessi in ordine di iter decrescente
		String sql = "";
		
		if(count)
			sql += "SELECT count(DISTINCT ANAGRAFICA_ID) ";
		else sql += "SELECT DISTINCT ANAGRAFICA_ID, DATA_CREAZIONE, CFG_IT_STATO_ID, upper(cast(DENOMINAZIONE as varchar2(100))) DENOMINAZIONE, DATA_APERTURA, CASO_ID, AIND.COM_DES res_comune, aind.prov res_prov";  //SISO-812

		String sqlCasi = "SELECT DISTINCT A.COGNOME || ' ' || A.NOME DENOMINAZIONE, A.DATA_NASCITA, A.CF," +
			" S.ANAGRAFICA_ID, ITD.DT_AMMINISTRATIVA as DATA_CREAZIONE, IT.CFG_IT_STATO_ID, SC.CATEGORIA_SOCIALE_ID, SC.DATA_FINE_APP, "+
			" coto.flag_responsabile, nvl(os.operatore_id,'') operatore_id, ITDF.DT_AMMINISTRATIVA AS DATA_APERTURA, S.CASO_ID AS CASO_ID " +//SISO-812
			" FROM " +
			" CS_A_ANAGRAFICA a, "+
			" CS_A_CASO c, "+
			" CS_A_SOGGETTO S, "+
			" CS_A_SOGGETTO_CATEGORIA_SOC sc, " +
			" CS_REL_SETTORE_CATSOC cs, "+
			" CS_IT_STEP ITF, CS_D_DIARIO ITDF, "+
			" CS_IT_STEP it, CS_D_DIARIO ITD, " +
			" CS_A_CASO_OPE_TIPO_OPE coto," +
            " CS_O_OPERATORE_TIPO_OPERATORE oto," +
            " CS_O_OPERATORE_SETTORE os," +
            " CS_O_OPERATORE o," +
            " CS_O_SETTORE sett " +
			" WHERE S.CASO_ID = C.ID " +
			" AND S.ANAGRAFICA_ID = A.ID" +
			" AND SC.SOGGETTO_ANAGRAFICA_ID = S.ANAGRAFICA_ID" +
			" AND SC.DATA_FINE_APP >= sysdate" +
			" AND SC.CATEGORIA_SOCIALE_ID = CS.CATEGORIA_SOCIALE_ID" +
			" AND CS.SETTORE_ID = SETT.ID " +
			" AND (CS.SETTORE_ID=IT.SETTORE_ID OR it.settore_id is null)" + //Condizione per recuperare casi corrispondenti al settore nel caso in cui una stessa categoria sociale sia associata a settori diversi di organizzazioni diverse
			" AND CS.ABILITATO = 1" +
			" AND IT.ID = (SELECT MAX(it2.id) FROM CS_IT_STEP it2 WHERE IT2.CASO_ID = IT.CASO_ID)  AND IT.CASO_ID = S.CASO_ID AND IT.DIARIO_ID=ITD.ID" +
			" AND ITF.ID = (SELECT MIN(it2.id) FROM CS_IT_STEP it2 WHERE IT2.CASO_ID = ITF.CASO_ID) AND ITF.CASO_ID = S.CASO_ID AND ITF.DIARIO_ID=ITDF.ID " +
			" AND COTO.CASO_ID (+)= C.ID" +
            " AND OTO.ID (+)= COTO.OPERATORE_TIPO_OPERATORE_ID" +
            " AND OS.ID (+)= OTO.OPERATORE_SETTORE_ID" +
            " AND O.ID (+)= OS.OPERATORE_ID" +
            " AND COTO.DATA_FINE_APP (+)>= SYSDATE ";
		
		String sqlCasiSenzaCategoria="SELECT DISTINCT A.COGNOME || ' ' || A.NOME DENOMINAZIONE, A.DATA_NASCITA, A.CF," +
				" S.ANAGRAFICA_ID, ITD.DT_AMMINISTRATIVA as DATA_CREAZIONE, IT.CFG_IT_STATO_ID, SC.CATEGORIA_SOCIALE_ID, SC.DATA_FINE_APP, "+
				" coto.flag_responsabile, nvl(os.operatore_id,'') operatore_id, ITDF.DT_AMMINISTRATIVA AS DATA_APERTURA, S.CASO_ID AS CASO_ID" + //SISO-812
				" FROM " +
				" CS_A_ANAGRAFICA a, "+
				" CS_A_CASO c, "+
				" CS_A_SOGGETTO S, "+
				" CS_A_SOGGETTO_CATEGORIA_SOC sc, " +
				" CS_IT_STEP ITF, CS_D_DIARIO ITDF, "+
				" CS_IT_STEP it, CS_D_DIARIO ITD, " +
				" CS_A_CASO_OPE_TIPO_OPE coto," +
	            " CS_O_OPERATORE_TIPO_OPERATORE oto," +
	            " CS_O_OPERATORE_SETTORE os," +
	            " CS_O_OPERATORE o," +
	            " CS_O_SETTORE sett " +
	        	" WHERE S.CASO_ID = C.ID " +
				" AND S.ANAGRAFICA_ID = A.ID" +
				" AND S.ANAGRAFICA_ID = sc.soggetto_anagrafica_id(+) " +
				" AND SC.DATA_FINE_APP (+)>= sysdate" +
				" AND IT.ID = (SELECT MAX(it2.id) FROM CS_IT_STEP it2 WHERE IT2.CASO_ID = IT.CASO_ID)  AND IT.CASO_ID = S.CASO_ID AND IT.DIARIO_ID=ITD.ID" +
				" AND ITF.ID = (SELECT MIN(it2.id) FROM CS_IT_STEP it2 WHERE IT2.CASO_ID = ITF.CASO_ID) AND ITF.CASO_ID = S.CASO_ID AND ITF.DIARIO_ID=ITDF.ID " +
				" AND COTO.CASO_ID (+)= C.ID" +
	            " AND OTO.ID (+)= COTO.OPERATORE_TIPO_OPERATORE_ID" +
	            " AND OS.ID (+)= OTO.OPERATORE_SETTORE_ID" +
	            " AND O.ID (+)= OS.OPERATORE_ID" +
	            " AND COTO.DATA_FINE_APP (+)>= SYSDATE "+
				" AND sett.id = IT.SETTORE_ID "+
				" and sc.CATEGORIA_SOCIALE_ID is null ";

		if(criteria != null) {
			
			//Evitare che i casi di un comune (su cui l'operatore è responsabile, vengano visti all'interno di un comune B su cui è solo operatore
			if(criteria.getIdOrganizzazione() != null){
				sqlCasi += " AND SETT.ORGANIZZAZIONE_ID = " + criteria.getIdOrganizzazione();
				sqlCasiSenzaCategoria += " AND SETT.ORGANIZZAZIONE_ID = " + criteria.getIdOrganizzazione();
			}
			//filtro sempre per il settore scelto e se non ha il permesso casi settore
			//filtro i casi dove l'utente è inserito come tipo operatore (CS_A_CASO_OPE_TIPO_OPE)
			//e quelli inseriti dall'utente che non hanno responsabile (CFG_IT_STATO_ID != 3)
			String sqlCasiTipoOpe = " O.USERNAME = '" + criteria.getUsername() + "'  OR (C.USER_INS = '" + criteria.getUsername() + "' " +
					" AND " + DataModelCostanti.IterStatoInfo.PRESO_IN_CARICO +
					" NOT IN (SELECT IT2.CFG_IT_STATO_ID FROM CS_IT_STEP it2 WHERE IT2.CASO_ID = S.CASO_ID))"; 
			
			if(!criteria.isPermessoCasiSettore() && !criteria.isPermessoCasiOrganizzazione()){
				sqlCasi += " AND (" + sqlCasiTipoOpe + ")";
				sqlCasiSenzaCategoria += " AND (" + sqlCasiTipoOpe + ")";
			}else{
				//SISO-812
				if(!criteria.getPermessiScheda() && criteria.getIdSettore() != null && !criteria.isPermessoCasiOrganizzazione()){
					sqlCasi += " AND SETT.ID = " + criteria.getIdSettore();
					sqlCasiSenzaCategoria += " AND SETT.ID = " + criteria.getIdSettore();
				}
				
			/*	if(criteria.getIdOrganizzazione() != null)
					sqlCasi += " AND SETT.ORGANIZZAZIONE_ID = " + criteria.getIdOrganizzazione();*/
				
			/*	if(criteria.getIdOpSettore()!=null)
					  sqlCasi += " AND OTO.OPERATORE_SETTORE_ID = '" + criteria.getIdOpSettore() + "'" ;*/
			}
		}
		
		/*Casi segnalati all'organizzazione o settore (se non possiede permessi sull'organizzazione) di cui è responsabile
		  o direttamente all'utente corrente */
		String sqlCasiSegnalati = "SELECT DISTINCT A.COGNOME || ' ' || A.NOME DENOMINAZIONE, A.DATA_NASCITA, A.CF," +
			" S.ANAGRAFICA_ID, ITD.DT_AMMINISTRATIVA AS DATA_CREAZIONE, IT.CFG_IT_STATO_ID, SC.CATEGORIA_SOCIALE_ID, SC.DATA_FINE_APP, "+
			" coto.flag_responsabile, nvl(os.operatore_id,'') operatore_id, ITDF.DT_AMMINISTRATIVA AS DATA_APERTURA, S.CASO_ID  as CASO_ID "+ //SISO-812
			" FROM "+
			" CS_A_ANAGRAFICA A, "+
			" CS_A_CASO c, "+
			" CS_A_SOGGETTO S, "+
			" CS_IT_STEP it, CS_D_DIARIO ITD,"+
			" CS_IT_STEP ITF, CS_D_DIARIO ITDF, "+
			" CS_A_SOGGETTO_CATEGORIA_SOC sc,  "+
			" CS_A_CASO_OPE_TIPO_OPE coto, "+
            " CS_O_OPERATORE_TIPO_OPERATORE oto, " +
            " CS_O_OPERATORE_SETTORE os  " +
			" WHERE 1=1 "+
			" AND IT.ID =  (SELECT MAX(it2.id) FROM CS_IT_STEP it2 WHERE IT2.CASO_ID = IT.CASO_ID) AND IT.CASO_ID = S.CASO_ID AND IT.DIARIO_ID=ITD.ID" +
			" AND ITF.ID = (SELECT MIN(it2.id) FROM CS_IT_STEP it2 WHERE IT2.CASO_ID = ITF.CASO_ID) AND ITF.CASO_ID = S.CASO_ID AND ITF.DIARIO_ID=ITDF.ID" +
			" AND S.ANAGRAFICA_ID = A.ID" +
			" AND S.ANAGRAFICA_ID = SC.SOGGETTO_ANAGRAFICA_ID"+
			" AND IT.CFG_IT_STATO_ID IN (" + DataModelCostanti.IterStatoInfo.SEGNALATO + 
			" ," + DataModelCostanti.IterStatoInfo.SEGNALATO_OP +
			" ," + DataModelCostanti.IterStatoInfo.SEGNALATO_ENTE +
			" ," + DataModelCostanti.IterStatoInfo.RIAPERTO +")" +
			//Inseriti per la condizione di filtro sugli operatori
			" AND COTO.CASO_ID(+) = S.CASO_ID "+
			" AND OTO.ID(+) = COTO.OPERATORE_TIPO_OPERATORE_ID "+
			" AND COTO.DATA_FINE_APP(+) >= SYSDATE "+
			" AND OS.ID (+)= OTO.OPERATORE_SETTORE_ID ";
		
		
		//Di default vedo i casi segnalati all'organizzazione
		boolean visCasiOrganizzazione = criteria.getIdOrganizzazione() != null; //&& criteria.isPermessoCasiOrganizzazione();
		if(visCasiOrganizzazione)
			sqlCasiSegnalati += " AND IT.ORGANIZZAZIONE_ID_TO = " + criteria.getIdOrganizzazione();
		
		//se non è presente il permesso per vedere tutti i casi segnalati al settore o organizzazione filtro per operatore
		boolean visCasiSettore =  criteria.getIdSettore() != null        && !criteria.isPermessoCasiOrganizzazione();
		if(visCasiSettore)
			sqlCasiSegnalati += " AND IT.SETTORE_ID_TO = " + criteria.getIdSettore();
		
		if(criteria.getIdOperatore() != null && !criteria.isPermessoCasiSettore() && !criteria.isPermessoCasiOrganizzazione())
			sqlCasiSegnalati += " AND IT.OPERATORE_TO = " + criteria.getIdOperatore();
		
		sql += " FROM (" + sqlCasi + " UNION "+ sqlCasiSegnalati + " UNION "+ sqlCasiSenzaCategoria + " ) U "
			+ "  LEFT JOIN CS_A_DATI_SOCIALI DS ON (DS.SOGGETTO_ANAGRAFICA_ID=U.ANAGRAFICA_ID AND DS.DATA_FINE_APP>= SYSDATE )  "
			+ "  LEFT JOIN CS_A_TRIBUNALE TR ON (TR.SOGGETTO_ANAGRAFICA_ID=U.ANAGRAFICA_ID AND TR.DATA_FINE_APP>= SYSDATE )  "
			+ "  LEFT JOIN CS_A_INDIRIZZO IND ON ( U.ANAGRAFICA_ID = IND.SOGGETTO_ANAGRAFICA_ID "
										  + " AND IND.TIPO_INDIRIZZO_ID = "+DataModelCostanti.TipiIndirizzi.RESIDENZA_ID
										  +	" AND NVL(IND.DATA_FINE_APP, TO_DATE('31/12/9999','dd/MM/yyyy'))>= SYSDATE )"
			+ "  LEFT JOIN CS_A_ANA_INDIRIZZO AIND ON (IND.ANA_INDIRIZZO_ID = AIND.id) ";
		
		String whereCond = getSQLCriteria();
		sql += " WHERE 1 = 1 " + whereCond;
		
		//SISO-812
	    if(criteria.getPermessiScheda()){
	    	sql+= " AND CASO_ID IN ( SELECT CASO_ID FROM CS_A_CASO_ACCESSO_FASCICOLO ) ";
	    	}
	    
/*		if(!count)
			sql += " ORDER BY CASE WHEN CFG_IT_STATO_ID = " + DataModelCostanti.IterStatoInfo.SEGNALATO_OP + " THEN 1 " +
					" WHEN CFG_IT_STATO_ID = " + DataModelCostanti.IterStatoInfo.SEGNALATO + " THEN 2" +
					" WHEN CFG_IT_STATO_ID = " + DataModelCostanti.IterStatoInfo.SEGNALATO_ENTE + " THEN 3" +
					" ELSE 4 END, DENOMINAZIONE, DATA_CREAZIONE DESC";*/
		
		//ORDINAMENTO SOLO ALFABETICO
		if(!count)
			sql += " ORDER BY DENOMINAZIONE, DATA_CREAZIONE DESC";
		
		return sql;

	}
	
	private String getSQLCriteria() {
		//campi usati per il filter 
		String sqlCriteria = "";
				
		sqlCriteria += (criteria.getDenominazione() == null  || criteria.getDenominazione().trim().equals("") ? "" : " AND UPPER(DENOMINAZIONE) LIKE '%" + replaceApici(criteria.getDenominazione().toUpperCase()) + "%'");

		sqlCriteria += (criteria.getDataNascita() == null  || criteria.getDataNascita().trim().equals("") ? "" : " AND TO_CHAR(DATA_NASCITA, 'dd/mm/yyyy') like '%" + criteria.getDataNascita().toUpperCase() + "%'");
		
		sqlCriteria += (criteria.getCodiceFiscale() == null  || criteria.getCodiceFiscale().trim().equals("") ? "" : " AND UPPER(CF) LIKE '%" + criteria.getCodiceFiscale().toUpperCase() + "%'");
		
		sqlCriteria += (criteria.getLstCatSociale()==null) ? "" : " AND U.CATEGORIA_SOCIALE_ID IN ("+criteria.getLstCatSociale()+") AND U.DATA_FINE_APP>=SYSDATE";
		
		sqlCriteria += (criteria.getLstStati()!=null && !criteria.getLstStati().isEmpty()) ? " AND CFG_IT_STATO_ID IN("+criteria.getLstStati()+")" : "";
		
		if(criteria.getOpResponsabile()!=null)
			sqlCriteria += criteria.getOpResponsabile().booleanValue() ?    " AND flag_responsabile = 1 " : " AND flag_responsabile <> 1 ";
		
		sqlCriteria += criteria.getIdOperatoreAltro()!=null ? " AND OPERATORE_ID = "+criteria.getIdOperatoreAltro() : "";
		sqlCriteria += criteria.getTitStudioId()!=null  && criteria.getTitStudioId()>0  ? " AND DS.TITOLO_STUDIO_ID="  +criteria.getTitStudioId() : "";
		sqlCriteria += criteria.getCondLavoroId()!=null && criteria.getCondLavoroId()>0 ? " AND DS.COND_LAVORATIVA_ID="+criteria.getCondLavoroId() : "";
		
		sqlCriteria += (criteria.getDataApertura() == null  || criteria.getDataApertura().trim().equals("") ? "" : " AND TO_CHAR(DATA_APERTURA, 'dd/mm/yyyy') like '%" + criteria.getDataApertura().toUpperCase() + "%'");
		
		sqlCriteria += (criteria.getResidenza() == null  || criteria.getResidenza().trim().equals("") ? "" : " AND AIND.COM_COD = '"+criteria.getResidenza()+"'");
		
		
		if(criteria.getTipoTutela()!=null){
			sqlCriteria += "TUTE".equals(criteria.getTipoTutela()) ? " AND (DS.TUTELA_COMPONENTE_ID IS NOT NULL   OR DS.TUTELA_DENOM IS NOT NULL) " : "";
			sqlCriteria += "CURA".equals(criteria.getTipoTutela()) ? " AND (DS.CURATELA_COMPONENTE_ID IS NOT NULL OR DS.CURATELA_DENOM IS NOT NULL) " : "";
			sqlCriteria += "SOST".equals(criteria.getTipoTutela()) ? " AND (DS.SOSTEGNO_COMPONENTE_ID IS NOT NULL OR DS.SOSTEGNO_DENOM IS NOT NULL) " : "";
			sqlCriteria += "AFFI".equals(criteria.getTipoTutela()) ? " AND  DS.AFFIDAMENTO=1 " : "";
		}
		
		if(criteria.getTribunale()!=null){
			sqlCriteria += Arrays.asList(criteria.getTribunale()).contains(StrutturaTribunale.TM_CIVILE) ? " AND TR.TM_CIVILE=1 " : "";
			sqlCriteria += Arrays.asList(criteria.getTribunale()).contains(StrutturaTribunale.TM_AMMINISTRATIVO) ? " AND TR.TM_AMM=1 " : "";
			sqlCriteria += Arrays.asList(criteria.getTribunale()).contains(StrutturaTribunale.PENALE_MINORILE) ? " AND TR.PENALE_MIN=1 " : "";
			sqlCriteria += Arrays.asList(criteria.getTribunale()).contains(StrutturaTribunale.NIS) ? " AND TR.NIS=1 " : "";
			sqlCriteria += Arrays.asList(criteria.getTribunale()).contains(StrutturaTribunale.PROCURA_MINORILE) ? " AND TR.PROCURA_MIN=1 " : "";
			sqlCriteria += Arrays.asList(criteria.getTribunale()).contains(StrutturaTribunale.TO) ? " AND TR.TO_FLG=1 " : "";
			sqlCriteria += Arrays.asList(criteria.getTribunale()).contains(StrutturaTribunale.PROCURA_ORDINARIA) ? " AND TR.PROCURA_ORD=1 " : ""; //SISO-855
			sqlCriteria += Arrays.asList(criteria.getTribunale()).contains(StrutturaTribunale.CORTE_APPELLO) ? " AND TR.CORTE_APPELLO=1 " : ""; 
		}
		
//		 //SISO-812
//	    if(criteria.getPermessiScheda() != null && criteria.getPermessiScheda()==true){
//	    	sqlCriteria += " AND C.CASO_ID IN ( SELECT CASO_ID FROM CS_A_CASO_ACCESSO_FASCICOLO ) ";
//	    	}
		
		return sqlCriteria;
	}
	
	public String replaceApici(String s){
		return s.replaceAll("'", "''");
	}
	
	public String createQueryCasiPerCategoria(boolean count) {

		String sql = "";
		
		if(count)
			sql += "SELECT organizzazione_id, settore_id, count(DISTINCT S.ANAGRAFICA_ID) as conteggioSettore ";
		else sql += "SELECT DISTINCT S.ANAGRAFICA_ID, ITD.DT_AMMINSTRATIVA AS DATA_CREAZIONE, IT.CFG_IT_STATO_ID ";
		
		sql += "FROM CS_A_SOGGETTO S, " +
		       "CS_A_ANAGRAFICA a, " +
		       "CS_A_CASO c, " +
		       "CS_A_SOGGETTO_CATEGORIA_SOC sc, " +
		       "CS_IT_STEP it, CS_D_DIARIO ITD, " +
		       "CS_A_CASO_OPE_TIPO_OPE coto " +
		       "WHERE S.CASO_ID = C.ID " +
		       "AND S.ANAGRAFICA_ID = A.ID " +
		       "AND SC.SOGGETTO_ANAGRAFICA_ID = S.ANAGRAFICA_ID " +
		       "AND SC.DATA_FINE_APP >= SYSDATE " +
		       "AND SC.PREVALENTE=1 "+
		       "AND COTO.CASO_ID (+)= C.ID " +
		       "AND IT.ID = (SELECT MAX (it2.id) FROM CS_IT_STEP it2 WHERE IT2.CASO_ID = IT.CASO_ID) AND IT.CASO_ID = S.CASO_ID AND ITD.ID = IT.DIARIO_ID ";
		
		if(criteria.getIdCatSociale() != null)
			sql += "AND SC.CATEGORIA_SOCIALE_ID = " + criteria.getIdCatSociale() + " ";
		
		if(criteria.isWithResponsabile()) //Casi con responsabile non chiusi
			sql += "AND COTO.FLAG_RESPONSABILE = 1 AND IT.CFG_IT_STATO_ID <> 2 ";
		
		if(criteria.getIdLastIter() != null)
			sql += "AND IT.CFG_IT_STATO_ID = " + criteria.getIdLastIter() + " ";
		
		sql+= "GROUP BY organizzazione_id, settore_id ";
		
		return sql;
	}

}
