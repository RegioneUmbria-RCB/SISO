package it.webred.ct.data.access.basic.catasto.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import it.webred.ct.data.access.basic.CTQueryBuilder;
import it.webred.ct.data.access.basic.catasto.dto.CatastoSearchCriteria;
import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.catasto.dto.RicercaSoggettoCatDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.common.utils.StringUtils;

public class CatastoQueryBuilder extends CTQueryBuilder{
	
	private CatastoSearchCriteria criteria;
	
	private final String ListaImmobiliEGraffati_SQL="SELECT fkComune, codi_Fisc_Luna, sezione, foglio, particella,sub, unimm, graffato FROM ("+ 
               "SELECT DISTINCT "+
                       "ui.cod_Nazionale AS fkComune, "+
                       "c.codi_Fisc_Luna, "+
                       "c.id_Sezc AS sezione, "+
                       "LPAD (ui.foglio, 4, '0') AS foglio, "+
                       "ui.particella as particella, "+
                       "ui.sub AS sub, "+
                       "LPAD (ui.unimm, 4, '0') AS unimm, "+
                       "'N' AS graffato "+
                  "FROM Sitiuiu ui, Siticomu c "+
                  "WHERE ui.cod_Nazionale = c.cod_Nazionale "+
                       "AND c.codi_Fisc_Luna = :codEnte "+
                       "AND ui.foglio = LTRIM(:foglio,0) "+
                       "AND ui.particella = LPAD (:particella, 5, '0') "+
			    "UNION ALL "+
			    "SELECT DISTINCT "+
                       "c.cod_Nazionale AS fkComune, "+
                       "c.codi_Fisc_Luna, "+
                       "lc.sez AS sezione, "+
                       "lc.foglio, "+
                       "lc.mappale as particella, "+
                       "' ' AS sub, "+
                       "lc.sub AS unimm, "+
                       "lc.graffato "+
                  "FROM Load_Cat_Uiu_Id lc, Siticomu c "+
                 "WHERE     c.sezione_Censuaria = lc.sez "+
                       "AND c.codi_Fisc_Luna = lc.codi_Fisc_Luna "+
                       "AND lc.graffato = 'Y' "+
                       "AND lc.codi_Fisc_Luna = :codEnte "+
                       "AND lc.foglio = LPAD(:foglio,4,'0') "+
                       "AND lc.mappale = LPAD(:particella,5,'0') "+
                ") ORDER BY foglio, particella, unimm";
	
	private final String RegimeTerreni_SQL = 
		"SELECT DISTINCT b.cod_nazionale,b.foglio,b.particella,b.sub,c.regime, b.pk_cuaa  " +
		"FROM sititrkc a,(select cod_nazionale, foglio, particella, sub, tipo_titolo, tipo_documento, pk_cuaa, data_inizio, data_fine, perc_poss from cons_cons_tab " + 
		"        union all  " +
		"        select cod_nazionale, foglio, particella, sub, tipo_titolo, tipo_documento, pk_cuaa, data_inizio, data_fine, perc_poss  from cons_ufre_tab) b, " +
		"     load_cat_tit c,load_cat_sog d,anag_soggetti e  " +
		"WHERE a.ide_immo=c.id_imm  " +
		"AND a.cod_nazionale=b.cod_nazionale  " +
		"AND a.foglio=b.foglio  " +
		"AND a.particella=b.particella  " +
		"AND a.sub=b.sub  " +
		"AND a.data_fine>SYSDATE  " +
		"AND b.data_fine>SYSDATE  " +
		"AND c.stato_siti IS NULL  " +
		"AND c.id_sog=d.id_sog  " +
		"AND c.id_sog=d.id_sog  " +
		"AND c.data_effic_fin IS null  " +
		"and c.tp_imm='T'  " +
		"AND Trim(Trim(d.cognome)||' '||Trim(d.nome))=trim(Trim(e.cognome)||' '||Trim(e.nome))  " +
		"AND e.cod_soggetto=b.pk_cuaa   " +
		"AND e.data_fine_val>sysdate  " +
		"AND b.cod_nazionale= :codEnte  " +
		"AND b.foglio = :foglio  " +
		"AND b.particella = LPAD( :particella ,5,'0')  " +
		"AND b.sub =  :sub   " +
		"AND b.pk_cuaa = :pkCuaa ";

	private final String RegimeImmobili_SQL = 
		"SELECT DISTINCT b.cod_nazionale,b.foglio,b.particella,b.sub,b.unimm,c.regime, b.pk_cuaa  " +
		"FROM sitiuiu a,siticonduz_imm_all b,load_cat_tit c,load_cat_sog d,anag_soggetti e " +
		"WHERE a.ide_immo=c.id_imm " +
		"AND a.cod_nazionale=b.cod_nazionale " +
		"AND a.foglio=b.foglio " +
		"AND a.particella=b.particella " +
		"AND a.sub=b.sub " +
		"AND a.unimm=b.unimm " +
		"AND a.data_fine_val>SYSDATE " +
		"AND b.data_fine>SYSDATE " +
		"AND c.stato_siti IS NULL " +
		"AND c.id_sog=d.id_sog " +
		"AND c.data_effic_fin IS null " +
		"and c.tp_imm='F' " +
		"AND Trim(Trim(d.cognome)||' '||Trim(d.nome))=trim(Trim(e.cognome)||' '||Trim(e.nome)) " + 
		"AND e.cod_soggetto=b.pk_cuaa  " +
		"AND e.data_fine_val>sysdate " +
		"AND b.cod_nazionale= :codEnte " +
		"AND b.foglio = :foglio " +
		"AND b.particella = LPAD( :particella ,5,'0') " +
		"AND b.sub =  :sub  " +
		"AND b.unimm = :unimm " +
		"AND b.pk_cuaa = :pkCuaa " ;
	
	private final String SoggettoCollegatoTerreni_SQL = 
		"SELECT cognome, nome FROM load_cat_sog WHERE id_sog IN (" +
		"SELECT DISTINCT c.id_sog_rif  " +
		"FROM sititrkc a,(select cod_nazionale, foglio, particella, sub, tipo_titolo, tipo_documento, pk_cuaa, data_inizio, data_fine, perc_poss from cons_cons_tab " + 
		"        union all  " +
		"        select cod_nazionale, foglio, particella, sub, tipo_titolo, tipo_documento, pk_cuaa, data_inizio, data_fine, perc_poss  from cons_ufre_tab) b, " +
		"     load_cat_tit c,load_cat_sog d,anag_soggetti e  " +
		"WHERE a.ide_immo=c.id_imm  " +
		"AND a.cod_nazionale=b.cod_nazionale  " +
		"AND a.foglio=b.foglio  " +
		"AND a.particella=b.particella  " +
		"AND a.sub=b.sub  " +
		"AND a.data_fine>SYSDATE  " +
		"AND b.data_fine>SYSDATE  " +
		"AND c.stato_siti IS NULL  " +
		"AND c.id_sog=d.id_sog  " +
		"AND c.data_effic_fin IS null  " +
		"and c.tp_imm='T'  " +
		"AND Trim(Trim(d.cognome)||' '||Trim(d.nome))=trim(Trim(e.cognome)||' '||Trim(e.nome))  " +
		"AND e.cod_soggetto=b.pk_cuaa   " +
		"AND e.data_fine_val>sysdate  " +
		"AND b.cod_nazionale= :codEnte  " +
		"AND b.foglio = :foglio  " +
		"AND b.particella = LPAD( :particella ,5,'0')  " +
		"AND b.sub =  :sub   " +
		"AND b.pk_cuaa = :pkCuaa " +
		")";

	private final String SoggettoCollegatoImmobili_SQL = 
		"SELECT cognome, nome FROM load_cat_sog WHERE id_sog IN (" +
		"SELECT DISTINCT c.id_sog_rif  " +
		"FROM sitiuiu a,siticonduz_imm_all b,load_cat_tit c,load_cat_sog d,anag_soggetti e " +
		"WHERE a.ide_immo=c.id_imm " +
		"AND a.cod_nazionale=b.cod_nazionale " +
		"AND a.foglio=b.foglio " +
		"AND a.particella=b.particella " +
		"AND a.sub=b.sub " +
		"AND a.unimm=b.unimm " +
		"AND a.data_fine_val>SYSDATE " +
		"AND b.data_fine>SYSDATE " +
		"AND c.stato_siti IS NULL " +
		"AND c.id_sog=d.id_sog " +
		"AND c.data_effic_fin IS null " +
		"and c.tp_imm='F' " +
		"AND Trim(Trim(d.cognome)||' '||Trim(d.nome))=trim(Trim(e.cognome)||' '||Trim(e.nome)) " + 
		"AND e.cod_soggetto=b.pk_cuaa  " +
		"AND e.data_fine_val>sysdate " +
		"AND b.cod_nazionale= :codEnte " +
		"AND b.foglio = :foglio " +
		"AND b.particella = LPAD( :particella ,5,'0') " +
		"AND b.sub =  :sub  " +
		"AND b.unimm = :unimm " +
		"AND b.pk_cuaa = :pkCuaa " +
		")";
	
   
	private final String TerreniAccatastati_SQL_Step2  =
		"SELECT DISTINCT " +
		"SITICOMU.ID_SEZC SEZIONE, " +
		"SITITRKC.FOGLIO AS FOGLIO, " +
		"REMOVE_LEAD_ZERO (SITITRKC.PARTICELLA) AS PARTICELLA, " +
		"SITITRKC.SUB AS SUB, " +
		"SITITRKC.PARTITA, " +
		"conduz.PERC_POSS, " +
		"conduz.TIPO_DOCUMENTO, " +
		"cons_deco_tab.DESCRIPTION, " +
		"SITITRKC.qual_cat, siticods_qual.desc_qual, " +
		"SITITRKC.classe_terreno, SITITRKC.area_part, SITITRKC.rendita,SITITRKC.reddito_dominicale, SITITRKC.reddito_agrario, "+
		"SITITRKC.DATA_AGGI AS data_Inizio_Terr, " +
		"SITITRKC.DATA_FINE AS data_Fine_Terr, " +
		"conduz.DATA_INIZIO AS data_Inizio_Tit, " +
		"conduz.DATA_FINE AS data_Fine_Tit, " +
		"conduz.TIT_NO_COD " +
		"FROM SITICOMU, CAT_D_TOPONIMI, cons_deco_tab," +
		"(select cod_nazionale, foglio, particella, sub, tipo_titolo, tipo_documento, pk_cuaa, data_inizio, data_fine, perc_poss, tit_no_cod from cons_cons_tab " +
		"union all " +
		"select cod_nazionale, foglio, particella, sub, tipo_titolo, tipo_documento, pk_cuaa, data_inizio, data_fine, perc_poss, tit_no_cod from cons_ufre_tab) conduz  " +
		"inner join SITITRKC on  " +
		"(SITITRKC.foglio   = conduz.foglio AND SITITRKC.particella  = conduz.particella AND SITITRKC.sub  = conduz.sub and SITITRKC.cod_nazionale = conduz.cod_nazionale  ) " +
		"LEFT JOIN siticods_qual ON (sititrkc.QUAL_CAT = siticods_qual.CODI_QUAL) " +
		"WHERE cons_deco_tab.tablename = 'CONS_ATTI_TAB' " +
		"AND cons_deco_tab.fieldname = 'TIPO_DOCUMENTO' " +
		"AND cons_deco_tab.code = conduz.tipo_documento " +
		"@SQL_CONDIZIONI_ENTE@  " +
		"@SQL_CONDIZIONI_SOGGETTO@  " +
		"@SQL_CONDIZIONI_INTERVALLO_VAL@  " +
		"ORDER BY FOGLIO, PARTICELLA, SUB, data_Fine_Terr DESC ";
		
	private final String PkcuaaSoggetti_SQL = 
			"select PK_CUAA from cons_sogg_tab where data_fine = TO_DATE ('31/12/9999', 'dd/MM/yyyy')  @SQL_CONDIZIONI_SOGGETTO@ ";
	
/*	private final String ImmobiliAccatastati_SQL_Step2 = "SELECT SITICOMU.ID_SEZC SEZIONE, " +
         "SITIUIU.FOGLIO AS FOGLIO, " +
         "REMOVE_LEAD_ZERO (SITIUIU.PARTICELLA) AS PARTICELLA, " +
         "SITIUIU.UNIMM AS SUBALTERNO, " +
         "SITIUIU.SUB AS SUB, " +
         "SITIUIU.PARTITA, " +
         "NULL CODICE_VIA, " +
         "NULL INDIRIZZO, " +
         "NULL CIVICO, " +
         "TRIM ( " +
         "      CAT_D_TOPONIMI.DESCR || ' '|| LOAD_CAT_UIU_IND.IND || ' ' || REMOVE_LEAD_ZERO (LOAD_CAT_UIU_IND.CIV1)) AS INDIRIZZO_CATASTALE, " +
         "siticonduz_imm_all.PERC_POSS, " +
         "siticonduz_imm_all.TIPO_DOCUMENTO, " +
         "cons_deco_tab.DESCRIPTION, " +
         "SITIUIU.CATEGORIA, " +
         "SITIUIU.CLASSE, " +
         "SITIUIU.CONSISTENZA, " +
         "SITIUIU.RENDITA, " +
         "SITIUIU.SUP_CAT AS SUPCAT, " +
         "NULL AS TIPO_EVENTO, " +
         "SITIUIU.DATA_INIZIO_VAL AS data_Inizio_Uiu, " +
         "SITIUIU.DATA_FINE_VAL AS data_Fine_Uiu, " +
         "siticonduz_imm_all.DATA_INIZIO AS data_Inizio_Tit, " +
         "siticonduz_imm_all.DATA_FINE AS data_Fine_Tit " +
         "FROM SITICOMU, CAT_D_TOPONIMI, cons_deco_tab, SITICONDUZ_IMM_ALL  inner join SITIUIU on  "+
         "(SITIUIU.foglio   = siticonduz_imm_all.foglio "+
         "AND SITIUIU.particella  = siticonduz_imm_all.particella "+
         "AND SITIUIU.unimm  = siticonduz_imm_all.unimm "+
         "AND SITIUIU.sub  = siticonduz_imm_all.sub "+
         "and SITIUIU.cod_nazionale = siticonduz_imm_all.cod_nazionale  ) inner join LOAD_CAT_UIU_ID on "+
         " ( LOAD_CAT_UIU_ID.FOGLIO= LPAD (SITIUIU.foglio, 4, '0') "+
         "AND LOAD_CAT_UIU_ID.MAPPALE = LPAD (SITIUIU.particella, 5, '0') "+
         "AND LOAD_CAT_UIU_ID.SUB = LPAD (SITIUIU.unimm, 4, '0') ) inner join LOAD_CAT_UIU_IND ON  "+
         "( LOAD_CAT_UIU_IND.SEZIONE = LOAD_CAT_UIU_ID.SEZIONE "+
         "AND LOAD_CAT_UIU_IND.ID_IMM = LOAD_CAT_UIU_ID.ID_IMM "+
         "AND LOAD_CAT_UIU_IND.PROGRESSIVO = LOAD_CAT_UIU_ID.PROGRESSIVO " +
         "AND LOAD_CAT_UIU_IND.SEQ = LOAD_CAT_UIU_ID.SEQ ) "+
         "WHERE LOAD_CAT_UIU_ID.CODI_FISC_LUNA = siticomu.CODI_FISC_LUNA "+
         "AND LOAD_CAT_UIU_IND.CODI_FISC_LUNA = siticomu.CODI_FISC_LUNA "+
         "AND LOAD_CAT_UIU_ID.SEZ = SITICOMU.SEZIONE_CENSUARIA "+
         "AND CAT_D_TOPONIMI.PK_ID = LOAD_CAT_UIU_IND.TOPONIMO "+
         "AND LOAD_CAT_UIU_IND.SEQ = (SELECT MAX (SEQ) FROM load_cat_uiu_ind IND2 "+
         "         WHERE IND2.ID_IMM = LOAD_CAT_UIU_IND.ID_IMM "+
         "               AND IND2.CODI_FISC_LUNA = LOAD_CAT_UIU_IND.CODI_FISC_LUNA "+
         "               AND IND2.PROGRESSIVO = LOAD_CAT_UIU_IND.PROGRESSIVO) "+
         "AND LOAD_CAT_UIU_ID.SEQ = (SELECT MAX (SEQ) FROM load_cat_uiu_id LID2 "+
         "         WHERE LID2.ID_IMM = LOAD_CAT_UIU_ID.ID_IMM "+
         "               AND LID2.CODI_FISC_LUNA = LOAD_CAT_UIU_ID.CODI_FISC_LUNA "+
         "               AND LID2.PROGRESSIVO = LOAD_CAT_UIU_ID.PROGRESSIVO " +
         "				 and lid2.foglio = LOAD_CAT_UIU_ID.foglio and lid2.mappale = LOAD_CAT_UIU_ID.mappale and lid2.sub = LOAD_CAT_UIU_ID.sub) "+
         "AND LOAD_CAT_UIU_IND.PROGRESSIVO = (SELECT MAX (PROGRESSIVO) FROM load_cat_uiu_ind IND2 "+
         "         WHERE IND2.ID_IMM = LOAD_CAT_UIU_IND.ID_IMM "+
         "               AND IND2.CODI_FISC_LUNA = LOAD_CAT_UIU_IND.CODI_FISC_LUNA) "+
         "AND LOAD_CAT_UIU_ID.PROGRESSIVO = (SELECT MAX(PROGRESSIVO) FROM load_cat_uiu_id LID2 " +
         "		   WHERE LID2.ID_IMM = LOAD_CAT_UIU_ID.ID_IMM AND LID2.CODI_FISC_LUNA = LOAD_CAT_UIU_ID.CODI_FISC_LUNA) " +
         "AND cons_deco_tab.tablename = 'CONS_ATTI_TAB' "+
         "AND cons_deco_tab.fieldname = 'TIPO_DOCUMENTO' "+
         "AND cons_deco_tab.code = siticonduz_imm_all.tipo_documento "+
         " @SQL_CONDIZIONI_ENTE@ " +
         " @SQL_CONDIZIONI_SOGGETTO@ " +
         " @SQL_CONDIZIONI_INTERVALLO_VAL@ " +
         "ORDER BY siticonduz_imm_all.FOGLIO, siticonduz_imm_all.PARTICELLA,siticonduz_imm_all.SUB, siticonduz_imm_all.unimm, data_fine_uiu DESC ";
         */
	private final String ImmobiliAccatastati_SQL_Step2 = "SELECT SITICOMU.ID_SEZC SEZIONE, " +
			"SITIUIU.FOGLIO AS FOGLIO, " +
			"REMOVE_LEAD_ZERO (SITIUIU.PARTICELLA) AS PARTICELLA, " +
			"SITIUIU.UNIMM AS SUBALTERNO, SITIUIU.SUB AS SUB, SITIUIU.PARTITA, NULL CODICE_VIA, NULL INDIRIZZO, NULL CIVICO, NULL INDIRIZZO_CATASTALE,  " +
			"siticonduz_imm_all.PERC_POSS, siticonduz_imm_all.TIPO_DOCUMENTO, cons_deco_tab.DESCRIPTION, " +
			"SITIUIU.CATEGORIA,  SITIUIU.CLASSE, SITIUIU.CONSISTENZA, SITIUIU.RENDITA, SITIUIU.SUP_CAT AS SUPCAT, NULL AS TIPO_EVENTO, " +
		    "  SITIUIU.DATA_INIZIO_VAL AS data_Inizio_Uiu, " +
		    "  SITIUIU.DATA_FINE_VAL AS data_Fine_Uiu, " +
		    "  siticonduz_imm_all.DATA_INIZIO AS data_Inizio_Tit, " +
		    "  siticonduz_imm_all.DATA_FINE AS data_Fine_Tit, " +
		    "  siticonduz_imm_all.TIT_NO_COD " +
		    "FROM SITICOMU, cons_deco_tab, SITICONDUZ_IMM_ALL  " +
		    " INNER JOIN SITIUIU " +
		    "ON (    SITIUIU.foglio = siticonduz_imm_all.foglio " +
		    "           AND SITIUIU.particella = siticonduz_imm_all.particella " +
		    "           AND SITIUIU.unimm = siticonduz_imm_all.unimm " +
		    "           AND SITIUIU.sub = siticonduz_imm_all.sub " +
		    "           AND SITIUIU.cod_nazionale = siticonduz_imm_all.cod_nazionale)  " +
			"WHERE  cons_deco_tab.tablename = 'CONS_ATTI_TAB' " +
			"      AND cons_deco_tab.fieldname = 'TIPO_DOCUMENTO' " +
			"      AND cons_deco_tab.code = siticonduz_imm_all.tipo_documento " +
			" @SQL_CONDIZIONI_ENTE@ " +
		    " @SQL_CONDIZIONI_SOGGETTO@ " +
		    " @SQL_CONDIZIONI_INTERVALLO_VAL@ " +
		"ORDER BY siticonduz_imm_all.FOGLIO, siticonduz_imm_all.PARTICELLA, siticonduz_imm_all.SUB, siticonduz_imm_all.unimm, data_fine_uiu DESC ";
	
	private final String SQL_LOC_CAT_SelectDescr = "SELECT DISTINCT TRIM ( CAT_D_TOPONIMI.DESCR || ' '|| LOAD_CAT_UIU_IND.IND|| ' '|| REMOVE_LEAD_ZERO (LOAD_CAT_UIU_IND.CIV1)) AS INDIRIZZO_CATASTALE ";
	
	private final String SQL_LOC_CAT_SelectAll = "SELECT DISTINCT TRIM ( CAT_D_TOPONIMI.DESCR || ' '|| LOAD_CAT_UIU_IND.IND) AS INDIRIZZO, " +
												"REMOVE_LEAD_ZERO(LOAD_CAT_UIU_IND.CIV1) AS CIVICO, " +
												"LOAD_CAT_UIU_IND.ID_IMM, " +
												"LOAD_CAT_UIU_IND.PROGRESSIVO, " +
												"LOAD_CAT_UIU_IND.SEQ, " +
												"LOAD_CAT_UIU_IND.SEZIONE ";
	
	private final String SQL_LOCALIZZAZIONE_CATASTALE_PART_From = 
			"FROM SITICOMU,CAT_D_TOPONIMI,LOAD_CAT_UIU_ID INNER JOIN LOAD_CAT_UIU_IND " +
       "ON (LOAD_CAT_UIU_IND.SEZIONE = LOAD_CAT_UIU_ID.SEZIONE " +
       "    AND LOAD_CAT_UIU_IND.ID_IMM = LOAD_CAT_UIU_ID.ID_IMM " +
       "    AND LOAD_CAT_UIU_IND.PROGRESSIVO = LOAD_CAT_UIU_ID.PROGRESSIVO " +
       "    AND LOAD_CAT_UIU_IND.SEQ = LOAD_CAT_UIU_ID.SEQ) " +
       "WHERE     LOAD_CAT_UIU_ID.CODI_FISC_LUNA = siticomu.CODI_FISC_LUNA " +
       "AND LOAD_CAT_UIU_IND.CODI_FISC_LUNA = siticomu.CODI_FISC_LUNA " +
       "AND LOAD_CAT_UIU_ID.SEZ = SITICOMU.SEZIONE_CENSUARIA " +
       "AND CAT_D_TOPONIMI.PK_ID = LOAD_CAT_UIU_IND.TOPONIMO " +
       "AND LOAD_CAT_UIU_IND.SEQ = (SELECT MAX (SEQ) FROM load_cat_uiu_ind IND2  " +
       "          WHERE IND2.ID_IMM = LOAD_CAT_UIU_IND.ID_IMM AND IND2.CODI_FISC_LUNA =LOAD_CAT_UIU_IND.CODI_FISC_LUNA AND IND2.PROGRESSIVO = LOAD_CAT_UIU_IND.PROGRESSIVO) " +
       "AND LOAD_CAT_UIU_ID.SEQ = (SELECT MAX (SEQ) FROM load_cat_uiu_id LID2 " +
       "         WHERE LID2.ID_IMM = LOAD_CAT_UIU_ID.ID_IMM " +
       "               AND LID2.CODI_FISC_LUNA = LOAD_CAT_UIU_ID.CODI_FISC_LUNA " +
       "               AND LID2.PROGRESSIVO = LOAD_CAT_UIU_ID.PROGRESSIVO " +
       "               AND lid2.foglio = LOAD_CAT_UIU_ID.foglio " +
       "               AND lid2.mappale = LOAD_CAT_UIU_ID.mappale " +
       "               AND lid2.sub = LOAD_CAT_UIU_ID.sub) " +
       "AND LOAD_CAT_UIU_IND.PROGRESSIVO = (SELECT MAX (PROGRESSIVO) FROM load_cat_uiu_ind IND2 " +
       "        WHERE IND2.ID_IMM = LOAD_CAT_UIU_IND.ID_IMM AND IND2.CODI_FISC_LUNA = LOAD_CAT_UIU_IND.CODI_FISC_LUNA) " +
       "AND LOAD_CAT_UIU_ID.PROGRESSIVO =  (SELECT MAX (PROGRESSIVO)  FROM load_cat_uiu_id LID2 " +
       "         WHERE LID2.ID_IMM = LOAD_CAT_UIU_ID.ID_IMM  AND LID2.CODI_FISC_LUNA = LOAD_CAT_UIU_ID.CODI_FISC_LUNA) " +
       "AND (siticomu.COD_NAZIONALE = :codEnte OR siticomu.CODI_FISC_LUNA = :codEnte) "+
       "AND LOAD_CAT_UIU_ID.FOGLIO = LPAD (:foglio, 4, '0') " +
       "AND LOAD_CAT_UIU_ID.MAPPALE = LPAD (:particella, 5, '0') ";
	
	private final String SQL_FIELDS_TO_SHOW = "" +
			"SITIUIU.PKID_UIU, " +
			"sitiuiu.COD_NAZIONALE AS FK_COMUNE , " +
			"sitiuiu.FOGLIO AS FOGLIO, " +
			"CAST(sitiuiu.PARTICELLA as varchar2(5)) AS PARTICELLA, "+
			"sitiuiu.SUB AS SUBALTERNO, " +
			"sitiuiu.UNIMM AS UNIMM, " +
			"sitiuiu.DATA_INIZIO_VAL as DATA_INIZIO_VAL, " +
			"sitiuiu.DATA_FINE_VAL as DATA_FINE_VAL, " +
			"sitiuiu.CATEGORIA  " ;
	
	/*private final  String SQL_SELECT_LISTA_UIU_BY_TITOLARITA = "select * " +
			"from ( (@SQL_LISTA_UIU_TITOLARITA_PROPRIETARI@) UNION (@SQL_LISTA_UIU_TITOLARITA_ALTRO@))";
	
	private final  String SQL_LISTA_UIU_TITOLARITA_PROPRIETARI = 
			"select distinct " + this.SQL_FIELDS_TO_SHOW +
			"from sitiuiu, cons_csui_tab, siticomu " +
			"where sitiuiu.COD_NAZIONALE = cons_csui_tab.COD_NAZIONALE " +
			"and sitiuiu.FOGLIO = cons_csui_tab.FOGLIO " +
			"and sitiuiu.PARTICELLA = cons_csui_tab.PARTICELLA " +
			"and sitiuiu.SUB = cons_csui_tab.SUB " +
			"and sitiuiu.UNIMM = cons_csui_tab.UNIMM " +
			"and cons_csui_tab.DATA_INIZIO < sitiuiu.DATA_FINE_VAL " +
			"and cons_csui_tab.DATA_FINE > sitiuiu.DATA_INIZIO_VAL " +
			"and siticomu.COD_NAZIONALE = sitiuiu.COD_NAZIONALE " +
			"and cons_csui_tab.PK_CUAA = '@PK_CUAA_SOGGETTO@' " ;
	
	private final  String SQL_LISTA_UIU_TITOLARITA_ALTRO = 
			"select distinct " + this.SQL_FIELDS_TO_SHOW +
			"from sitiuiu, cons_urui_tab, siticomu  " +
			"where sitiuiu.COD_NAZIONALE = cons_urui_tab.COD_NAZIONALE " +
			"and sitiuiu.FOGLIO = cons_urui_tab.FOGLIO " +
			"and sitiuiu.PARTICELLA = cons_urui_tab.PARTICELLA " +
			"and sitiuiu.SUB = cons_urui_tab.SUB " +
			"and sitiuiu.UNIMM = cons_urui_tab.UNIMM " +
			"and cons_urui_tab.DATA_INIZIO < sitiuiu.DATA_FINE_VAL " +
			"and  cons_urui_tab.DATA_FINE > sitiuiu.DATA_INIZIO_VAL " +
			"and siticomu.COD_NAZIONALE = sitiuiu.COD_NAZIONALE " +
			"and cons_urui_tab.PK_CUAA = '@PK_CUAA_SOGGETTO@' ";*/
	
	private final  String SQL_SELECT_LISTA_UIU_BY_TITOLARITA = 
		"SELECT DISTINCT " + this.SQL_FIELDS_TO_SHOW +
		"FROM sitiuiu, siticonduz_imm_all, siticomu  " +
		"WHERE siticonduz_imm_all.DATA_INIZIO <= sitiuiu.DATA_FINE_VAL " +
		"AND siticonduz_imm_all.DATA_FINE >= sitiuiu.DATA_INIZIO_VAL " +
		"AND siticonduz_imm_all.COD_NAZIONALE = siticomu.COD_NAZIONALE " +
		"AND sitiuiu.COD_NAZIONALE = SITICOMU.COD_NAZIONALE " +
		"AND sitiuiu.FOGLIO = siticonduz_imm_all.FOGLIO " +
		"AND sitiuiu.PARTICELLA = siticonduz_imm_all.PARTICELLA " +
		"AND sitiuiu.SUB = siticonduz_imm_all.SUB " +
		"AND sitiuiu.UNIMM = siticonduz_imm_all.UNIMM "+ 
		"AND siticonduz_imm_all.PK_CUAA = '@PK_CUAA_SOGGETTO@' ";
	
	
	private final String SQL_LISTA_UIU_BY_FPS = 
		"SELECT DISTINCT " + this.SQL_FIELDS_TO_SHOW +
		"from sitiuiu, sitideco, siticomu @SQL_REPTARSU_TAB " + 
		"where sitiuiu.CATEGORIA = sitideco.CODE " +
		"AND sitideco.TABLENAME='SITIUIU' " +
		"AND sitideco.FIELDNAME = 'CATEGORIA' " +
		"AND sitiuiu.cod_nazionale = SITICOMU.cod_nazionale ";
	
	private final String SQL_LISTA_PART = 
		"SELECT DISTINCT " +
		"ID_SEZC AS SEZIONE, " +
		"LPAD(NVL(TRIM(FOGLIO),'0'),4,'0') AS foglio, " +
		"CAST(PARTICELLA as VARCHAR2(5)) AS particella " +
		"from sitiuiu, siticomu where sitiuiu.cod_nazionale = SITICOMU.cod_nazionale ";
	
	private final  String SQL_LISTA_UIU_BY_INDIRIZZO = 
		"select distinct " + this.SQL_FIELDS_TO_SHOW +
		"from sitiuiu, sitideco, siticomu, siticivi_uiu " +
		"where sitiuiu.CATEGORIA = sitideco.CODE " +
		"and sitideco.TABLENAME='SITIUIU' " +
		"and sitideco.FIELDNAME = 'CATEGORIA' " +
		"AND sitiuiu.cod_nazionale = SITICOMU.cod_nazionale " +
		"AND siticivi_uiu.PKID_UIU = sitiuiu.PKID_UIU " +
		"and siticivi_uiu.DATA_FINE_VAL = TO_DATE('99991231', 'yyyymmdd') ";

	private final  String SQL_LISTA_PARTICELLE_BY_INDIRIZZO = 
		"select distinct " +
		"siticomu.id_sezc, " +
		"foglio, " +
		"cast(particella as varchar2(5)) as particella " +
		"from sitiuiu, siticomu, siticivi_uiu " +
		"where sitiuiu.cod_nazionale = SITICOMU.cod_nazionale " +
		"AND siticivi_uiu.PKID_UIU = sitiuiu.PKID_UIU " +
		"and siticivi_uiu.DATA_FINE_VAL = TO_DATE('99991231', 'yyyymmdd') ";
	
	private final  String SQL_SELECT_LISTA = 
		"SELECT * FROM ( " +
		" @SQL_SELECT_BASE_LISTA@ " +
		"ORDER BY foglio, particella, unimm)";
	
	private final  String SQL_SELECT_LISTA_PARTICELLE = 
		"SELECT * FROM ( " +
		" @SQL_SELECT_BASE_LISTA@ " +
		"ORDER BY foglio, particella)";

	private final  String SQL_SELECT_COUNT_LISTA = "SELECT count(*) as conteggio FROM ( @SQL_SELECT_BASE_LISTA@ )";

	private String SQL_LISTA_INDIRIZZI_BY_FP = 
			"SELECT DISTINCT " +
			"SITIDSTR.PREFISSO, SITIDSTR.NOME AS VIA, SITICIVI.CIVICO, SITICIVI.PKID_CIVI " +
			"FROM SITIUIU, SITICOMU, SITICIVI_UIU, SITIDSTR , SITICIVI " +
			"WHERE SITIUIU.COD_NAZIONALE = SITICOMU.COD_NAZIONALE " +
			"AND   SITICIVI_UIU.PKID_UIU = SITIUIU.PKID_UIU " +
			"AND   SITICIVI_UIU.PKID_CIVI = SITICIVI.PKID_CIVI " +
			"AND   SITICIVI.PKID_STRA = SITIDSTR.PKID_STRA " +
			"AND   SITICOMU.CODI_FISC_LUNA = ? " +
			"AND   (SITICOMU.ID_SEZC IS NULL  OR SITICOMU.ID_SEZC =?) " +
			"AND   SITIUIU.FOGLIO =  ? " +
			"AND   SITIUIU.PARTICELLA= LPAD(? ,5,'0') ";
	
	private String SQL_LISTA_INDIRIZZI_BY_FPeDATA = 
		"SELECT DISTINCT " +
		"SITIDSTR.PREFISSO, SITIDSTR.NOME AS VIA, SITICIVI.CIVICO, SITICIVI.PKID_CIVI " +
		"FROM SITIUIU, SITICOMU, SITICIVI_UIU, SITIDSTR , SITICIVI " +
		"WHERE SITIUIU.COD_NAZIONALE = SITICOMU.COD_NAZIONALE " +
		"AND   SITICIVI_UIU.PKID_UIU = SITIUIU.PKID_UIU " +
		"AND   SITICIVI_UIU.PKID_CIVI = SITICIVI.PKID_CIVI " +
		"AND   SITICIVI.PKID_STRA = SITIDSTR.PKID_STRA " +
		"AND   SITICOMU.CODI_FISC_LUNA = ? " +
		"AND   (SITICOMU.ID_SEZC IS NULL  OR SITICOMU.ID_SEZC =?) " +
		"AND   SITIUIU.FOGLIO =  ? " +
		"AND   SITIUIU.PARTICELLA= LPAD(? ,5,'0') " +
		"AND   SITICIVI_UIU.DATA_INIZIO_VAL <= ? AND SITICIVI_UIU.DATA_FINE_VAL >= ?  ";
	
	/*private String SQL_LISTA_SOGGETTI_BY_UNIMM = "select distinct " +
		"NVL(FLAG_PERS_FISICA,'') AS FLAG, " +
		"TITOLO, " +
		"NVL(RAGI_SOCI,' ') AS COG_DENOM, " +
		"NVL(NOME,' ') AS NOME, " +
		"NVL(CF,'-') AS CF, " +
		"NVL(PIVA,'-') AS PIVA, " +
		"DATA_INIZIO_POS, "+
		"DATA_FINE_POS, " +
		"QUOTA, " +
		"DATA_FINE_VAL " +
		"FROM (" +
			"select distinct " +
			"CONS_SOGG_TAB.FLAG_PERS_FISICA, " +
			"'Proprietario' AS TITOLO, " +
			"CONS_SOGG_TAB.RAGI_SOCI," +
			"CONS_SOGG_TAB.NOME, " +
			"CONS_SOGG_TAB.CODI_FISC AS CF, " +
			"CONS_SOGG_TAB.CODI_PIVA AS PIVA, " +
			"cons_csui_tab.DATA_INIZIO as DATA_INIZIO_POS, " +
			"cons_csui_tab.DATA_FINE as DATA_FINE_POS, " +
			"cons_csui_tab.PERC_POSS AS QUOTA, " +
			"sitiuiu.DATA_FINE_VAL " +
			"from sitiuiu, cons_csui_tab, siticomu, CONS_SOGG_TAB " +
			"where sitiuiu.COD_NAZIONALE = cons_csui_tab.COD_NAZIONALE " +
			"and CONS_SOGG_TAB.DATA_FINE =  TO_DATE('99991231', 'yyyymmdd') " +
			"and sitiuiu.FOGLIO = cons_csui_tab.FOGLIO " +
			"and sitiuiu.PARTICELLA = cons_csui_tab.PARTICELLA " +
			"and sitiuiu.SUB = cons_csui_tab.SUB " +
			"and sitiuiu.UNIMM = cons_csui_tab.UNIMM " +
			"and cons_csui_tab.DATA_INIZIO < sitiuiu.DATA_FINE_VAL " +
			"and cons_csui_tab.DATA_FINE > sitiuiu.DATA_INIZIO_VAL " +
			"and siticomu.COD_NAZIONALE = sitiuiu.COD_NAZIONALE " +
			"and cons_csui_tab.PK_CUAA = cons_sogg_tab.PK_CUAA " +
			"and SITIUIU.PKID_UIU = ? " +
			"AND (SITICOMU.CODI_FISC_LUNA = ? OR siticomu.COD_NAZIONALE = ? )" +
			"UNION " +
			"select distinct " +
			"CONS_SOGG_TAB.FLAG_PERS_FISICA, " +
			"'Altro' AS TITOLO, " +
			"CONS_SOGG_TAB.RAGI_SOCI," +
			"CONS_SOGG_TAB.NOME, " +
			"CONS_SOGG_TAB.CODI_FISC AS CF, " +
			"CONS_SOGG_TAB.CODI_PIVA AS PIVA, " +
			"cons_urui_tab.DATA_INIZIO as DATA_INIZIO_POS, " +
			"cons_urui_tab.DATA_FINE AS DATA_FINE_POS, " +
			"cons_urui_tab.PERC_POSS AS QUOTA, " +
			"sitiuiu.DATA_FINE_VAL " +
			"from sitiuiu, cons_urui_tab, siticomu , CONS_SOGG_TAB " +
			"where sitiuiu.COD_NAZIONALE = cons_urui_tab.COD_NAZIONALE " +
			"and CONS_SOGG_TAB.DATA_FINE =  TO_DATE('99991231', 'yyyymmdd') " +
			"and sitiuiu.FOGLIO = cons_urui_tab.FOGLIO " +
			"and sitiuiu.PARTICELLA = cons_urui_tab.PARTICELLA " +
			"and sitiuiu.SUB = cons_urui_tab.SUB " +
			"and sitiuiu.UNIMM = cons_urui_tab.UNIMM " +
			"and cons_urui_tab.DATA_INIZIO < sitiuiu.DATA_FINE_VAL " +
			"and  cons_urui_tab.DATA_FINE > sitiuiu.DATA_INIZIO_VAL " +
			"and siticomu.COD_NAZIONALE = sitiuiu.COD_NAZIONALE " +
			"and cons_urui_tab.PK_CUAA = cons_sogg_tab.PK_CUAA " +
			"and SITIUIU.PKID_UIU = ? " +
			"AND (SITICOMU.CODI_FISC_LUNA = ? OR siticomu.COD_NAZIONALE = ? ))" +
		"ORDER BY data_fine_pos DESC,cog_denom, nome";*/
	
	private String SQL_LISTA_SOGGETTI_BY_UNIMM = 
		"SELECT DISTINCT " +
			"NVL(FLAG_PERS_FISICA,'') AS FLAG, " +
			"TITOLO, " +
			"NVL(RAGI_SOCI,' ') AS COG_DENOM, " +
			"NVL(NOME,' ') AS NOME, " +
			"NVL(CF,'-') AS CF, " +
			"NVL(PIVA,'-') AS PIVA, " +
			"DATA_INIZIO_POS, "+
			"DATA_FINE_POS, " +
			"QUOTA, " +
			"DATA_FINE_VAL, " +
			"TIPO_DOCUMENTO " +
			"FROM (" +
				"SELECT DISTINCT " +
				"cons_sogg_tab.FLAG_PERS_FISICA, " +
				"d1.DESCRIPTION AS TITOLO, " +
				"cons_sogg_tab.RAGI_SOCI," +
				"cons_sogg_tab.NOME, " +
				"cons_sogg_tab.CODI_FISC AS CF, " +
				"cons_sogg_tab.CODI_PIVA AS PIVA, " +
				"siticonduz_imm_all.DATA_INIZIO as DATA_INIZIO_POS, " +
				"siticonduz_imm_all.DATA_FINE as DATA_FINE_POS, " +
				"siticonduz_imm_all.PERC_POSS AS QUOTA, " +
				"sitiuiu.DATA_FINE_VAL, " +
				"d2.DESCRIPTION AS TIPO_DOCUMENTO " +
				"FROM sitiuiu, siticonduz_imm_all, siticomu, cons_sogg_tab, cons_deco_tab d1, cons_deco_tab d2 " +
				"WHERE sitiuiu.COD_NAZIONALE = siticonduz_imm_all.COD_NAZIONALE " +
				"AND CONS_SOGG_TAB.DATA_FINE =  TO_DATE('99991231', 'yyyymmdd') " +
				"AND sitiuiu.FOGLIO = siticonduz_imm_all.FOGLIO " +
				"AND sitiuiu.PARTICELLA = siticonduz_imm_all.PARTICELLA " +
				//"AND sitiuiu.SUB = siticonduz_imm_all.SUB " +
				"AND sitiuiu.UNIMM = siticonduz_imm_all.UNIMM " +
				"AND siticonduz_imm_all.DATA_INIZIO <= sitiuiu.DATA_FINE_VAL " +
				"AND siticonduz_imm_all.DATA_FINE >= sitiuiu.DATA_INIZIO_VAL " +
				"AND siticomu.COD_NAZIONALE = sitiuiu.COD_NAZIONALE " +
				"AND siticonduz_imm_all.PK_CUAA = cons_sogg_tab.PK_CUAA " +
				"AND siticonduz_imm_all.tipo_titolo = d1.code(+) " +
				"AND siticonduz_imm_all.tipo_documento = d2.code(+) " +
				"AND d1.tablename = 'CONS_ATTI_TAB' " +
				"AND d1.fieldname = 'TIPO_TITOLO' " +
				"AND d2.tablename = 'CONS_ATTI_TAB' " +
				"AND d2.fieldname = 'TIPO_DOCUMENTO' " +
				"and SITIUIU.PKID_UIU = ? " +
				"AND (SITICOMU.CODI_FISC_LUNA = ? OR siticomu.COD_NAZIONALE = ? ))" +
	"ORDER BY data_fine_pos DESC, cog_denom, nome ";
	
	
/*	private String SQL_LOCALIZZAZIONE_CATASTALE_IMMOBILE = 
		"SELECT DISTINCT  " +
		"t.descr || ' ' || ind.ind  indirizzo, " +
		" ind.civ1 AS civico, ind.id_imm, ind.progressivo, ind.seq, id.sezione "+
	    " FROM load_cat_uiu_id ID, siticomu c, load_cat_uiu_ind ind, cat_d_toponimi t  "+
		" WHERE (c.cod_nazionale = ? OR c.codi_fisc_luna = ?) "+
		" AND ID.codi_fisc_luna = c.codi_fisc_luna  "+
		" AND ID.sez = c.sezione_censuaria  "+
		" AND ID.foglio = LPAD (?, 4, '0')  "+
		" AND ID.mappale = LPAD (?, 5, '0')  "+
		" AND ID.SUB =  LPAD (?, 4, '0') "+
		" AND ind.codi_fisc_luna = c.codi_fisc_luna "+ 
		" AND ind.sezione = ID.sezione  "+
		" AND ind.id_imm = ID.id_imm  "+
		" AND ind.progressivo = ID.progressivo  "+
		" AND t.pk_id = ind.toponimo ";*/
	
	private String SQL_PLANIMETRIE_C340 = "SELECT nome_file_planimetrico, " +
		"decode(nvl(subalterno, ' '), ' ', '9999', subalterno) as subalterno " +
		"FROM sit_cat_planimetrie_c340 " +
		"WHERE foglio = lpad(?, 4, '0') " +
		"AND numero = lpad(?, 5, '0') " +
		"AND (subalterno = lpad(?, 4, '0') OR decode(nvl(subalterno, ' '), ' ', '9999', subalterno) = '9999') " +
		"AND codice_amministrativo= ? "+
		"ORDER BY subalterno";
	
	private String SQL_PLANIMETRIE_C340_SEZFGLNUM = "SELECT * " +
			"FROM sit_cat_planimetrie_c340 " +
			"WHERE nvl(ltrim(rtrim(sezione)), '-') = nvl(ltrim(rtrim(?)), '-') " +
			"AND foglio = lpad(?, 4, '0') " +
			"AND numero = lpad(?, 5, '0') " +
			"AND codice_amministrativo = ? "+
			"ORDER BY subalterno, nome_file_planimetrico";
	
	public String getSQL_PLANIMETRIE_C340() {
		return SQL_PLANIMETRIE_C340;
	}
	
	public String getSQL_PLANIMETRIE_C340_SEZFGLNUM() {
		return SQL_PLANIMETRIE_C340_SEZFGLNUM;
	}
	
	private String SQL_LISTA_TITOLARI_TERRENO = 
		"SELECT DISTINCT PK_CUAA, CC.DATA_INIZIO, CC.DATA_FINE, CC.PERC_POSS, CC.TIPO_TITOLO, DECO.DESCRIPTION  " +
		"FROM SITITRKC T ,CONS_CONS_TAB CC, SITICOMU C, CONS_DECO_TAB deco  " +
         "WHERE  T.COD_NAZIONALE = CC.COD_NAZIONALE " +
                    "AND C.COD_NAZIONALE = T.COD_NAZIONALE " + 
                    "AND C.CODI_FISC_LUNA=? " +
                    "AND (C.ID_SEZC IS NULL OR C.ID_SEZC=?) " + 
                    "AND deco.tablename = 'CONS_ATTI_TAB' " +
                    "AND deco.fieldname = 'TIPO_DOCUMENTO' " +
                    "AND deco.code = cc.tipo_documento " +
                    "AND T.FOGLIO = CC.FOGLIO " +
                    "AND T.PARTICELLA = CC.PARTICELLA "+  
                    "AND T.SUB = CC.SUB " +
                    "AND CC.DATA_INIZIO <= T.DATA_FINE " + 
                    "AND  CC.DATA_FINE >= T.DATA_AGGI " +
                    "AND T.FOGLIO = ? " +
                    "AND LPAD(T.PARTICELLA, 5,'0') = LPAD(?, 5,'0') " +
                    "AND TO_DATE(TO_CHAR(t.data_fine,'yyyyMMdd'),'yyyyMMdd') = TO_DATE(?, 'yyyyMMdd') "+
                    "@CC.DATA_FINE_COND " + //CONDIZ. DA SOSTITUIRE PER IMPOSTARE SOLO STORICI OPPURE SOLO ATTUALI
                  //"AND CC.DATA_FINE >=SYSDATE " +//--> TITOLARI ATTUALI 
                  //"AND CC.DATA_FINE <SYSDATE " +//--> TITOLARI STORICI    
                  "  ORDER BY CC.DATA_FINE DESC,CC.DATA_INIZIO ";
	
	private String SQL_LISTA_ALTRI_SOGGETTI_TERRENO = 
		"SELECT DISTINCT PK_CUAA, CC.DATA_INIZIO, CC.DATA_FINE, CC.PERC_POSS, CC.TIPO_TITOLO, DECO.DESCRIPTION " +
		"FROM SITITRKC T ,CONS_UFRE_TAB CC, SITICOMU C, CONS_DECO_TAB deco " +
         "WHERE  T.COD_NAZIONALE = CC.COD_NAZIONALE " +
                    "AND C.COD_NAZIONALE = T.COD_NAZIONALE " + 
                    "AND C.CODI_FISC_LUNA=? " +
                    "AND (C.ID_SEZC IS NULL OR C.ID_SEZC=?) " + 
                    "AND deco.tablename = 'CONS_ATTI_TAB' " +
                    "AND deco.fieldname = 'TIPO_DOCUMENTO' " +
                    "AND deco.code = cc.tipo_documento " +
                    "AND T.FOGLIO = CC.FOGLIO " +
                    "AND T.PARTICELLA = CC.PARTICELLA "+  
                    "AND T.SUB = CC.SUB " +
                    "AND CC.DATA_INIZIO <= T.DATA_FINE " + 
                    "AND  CC.DATA_FINE >= T.DATA_AGGI " +
                    "AND T.FOGLIO = ? " +
                    "AND LPAD(T.PARTICELLA, 5,'0') = LPAD(?, 5,'0') " +
                    "AND TO_DATE(TO_CHAR(t.data_fine,'yyyyMMdd'),'yyyyMMdd') = TO_DATE(?, 'yyyyMMdd') "+
                    "@CC.DATA_FINE_COND " + //CONDIZ. DA SOSTITUIRE PER IMPOSTARE SOLO STORICI OPPURE SOLO ATTUALI
                  //"AND CC.DATA_FINE >=SYSDATE " +//-->  ATTUALI 
                  //"AND CC.DATA_FINE <SYSDATE " +//-->   STORICI    
                  "  ORDER BY CC.DATA_FINE DESC,CC.DATA_INIZIO ";
	
	
	private final String SQL_COORD_BY_TOPO = "select distinct SITIUIU.FOGLIO, SITIUIU.PARTICELLA " +
			"FROM SITIDSTR " +
			"INNER JOIN SITICIVI ON SITICIVI.PKID_STRA = SITIDSTR.PKID_STRA " +
			"INNER JOIN SITICIVI_UIU ON SITICIVI_UIU.PKID_CIVI = SITICIVI.PKID_CIVI " +
			"INNER JOIN SITIUIU ON SITIUIU.PKID_UIU = SITICIVI_UIU.PKID_UIU " +
			"WHERE " + 
			"SITIDSTR.DATA_FINE_VAL = TO_DATE('31/12/9999', 'DD/MM/YYYY') " + 
			"AND SITICIVI.DATA_FINE_VAL = TO_DATE('31/12/9999', 'DD/MM/YYYY') " + 
			"AND SITICIVI_UIU.DATA_FINE_VAL = TO_DATE('31/12/9999', 'DD/MM/YYYY') " + 
			"AND SITIUIU.DATA_FINE_VAL = TO_DATE('31/12/9999', 'DD/MM/YYYY') "
			+ " @SQL_CONDIZIONI_WHERE@ " + 
			"ORDER BY SITIUIU.FOGLIO, SITIUIU.PARTICELLA ";
	
	/*
	 SELECT u FROM Sitiuiu u, Siticomu c 
				WHERE
					 c.codiFiscLuna =:codEnte
				 AND u.id.codNazionale= c.codNazionale
				 AND (c.idSezc is null or c.idSezc=:sezione)
				 AND u.id.foglio = TO_NUMBER(:foglio) 
				 AND u.id.particella = LPAD(:particella ,5,'0') 
				 AND u.dataInizioVal <= :dtVal
				 AND u.id.dataFineVal >= :dtVal  
				 ORDER BY u.id.unimm
	 */
	private final String HQL_IMMO_BY_PARAMS = "SELECT u, c.idSezc FROM Sitiuiu u, Siticomu c "
			+ "WHERE "
			+ "u.id.codNazionale= c.codNazionale "
			+ " @HQL_CONDIZIONI_WHERE@ "
			+ "ORDER BY u.id.unimm ";
	
	
	/*
	 * SELECT  t FROM Sititrkc t, Siticomu c
		    WHERE  	(c.codiFiscLuna=:codNazionale OR c.codNazionale=:codNazionale)
		      AND   c.codNazionale = t.id.codNazionale 
		      AND  (c.idSezc is null or c.idSezc=:sezione)
			  AND   t.id.foglio = TO_NUMBER(:foglio) 
			  AND   t.id.particella = LPAD(:particella ,5,'0') 
			  AND   NVL(t.id.sub,' ') = NVL(:sub, ' ')
			  AND   NVL(upper(t.annotazioni),'-') <> :annotazione
		 ORDER BY	t.id.dataFine desc, t.dataAggi desc 
	 */
	private final String HQL_TERRE_BY_PARAMS = "SELECT t FROM Sititrkc t, Siticomu c "
			+ "WHERE c.codNazionale = t.id.codNazionale "
			+ " @HQL_CONDIZIONI_WHERE@ "
			+ "ORDER BY t.id.dataFine desc, t.dataAggi desc ";
	
	public CatastoQueryBuilder() {}
	
	public CatastoQueryBuilder(CatastoSearchCriteria criteria) {
		this.criteria = criteria;
	}
	
	protected String createQueryByImmobile(){
		
		String sqlUiu = this.SQL_LISTA_UIU_BY_FPS;
		sqlUiu += " AND ("+ addCondition("SITICOMU.CODI_FISC_LUNA","=",criteria.getCodNazionale())+ " OR "+ addCondition("siticomu.COD_NAZIONALE","=",criteria.getCodNazionale())+")";
		sqlUiu += this.getSQL_ImmobileCriteria();
		
		logger.info("Non a norma: " + criteria.getNonANorma() + "]");		
		//Seleziona solo gli immobili non a norma
		if(criteria.getNonANorma()!=null && criteria.getNonANorma()){
			
			sqlUiu = sqlUiu.replace("@SQL_REPTARSU_TAB", ", sit_rep_tarsu ");
			//int flag = (nonANorma == Boolean.FALSE ? 1 : 0);
			//AND sitiuiu.pkid_uiu = sit_rep_tarsu.pkid_uiu " +
			sqlUiu += " AND sit_rep_tarsu.foglio = sitiuiu.foglio " +
					" AND sit_rep_tarsu.particella = sitiuiu.particella " +
					" AND sit_rep_tarsu.unimm = sitiuiu.unimm " +
					" AND sit_rep_tarsu.data_inizio_val = sitiuiu.data_inizio_val " +
					" AND sit_rep_tarsu.f_c340 = 0 ";
			
		}else sqlUiu = sqlUiu.replace("@SQL_REPTARSU_TAB", "");
			
		return sqlUiu;
	}
	
	
	protected String createQueryByIndirizzo(){
		
		String sqlUiu = this.SQL_LISTA_UIU_BY_INDIRIZZO;
		sqlUiu += " AND ("+ addCondition("SITICOMU.CODI_FISC_LUNA","=",criteria.getCodNazionale())+ " OR "+ addCondition("siticomu.COD_NAZIONALE","=",criteria.getCodNazionale())+")";
		sqlUiu += this.getSQL_IndirizzoCriteria();
		
		return sqlUiu;
	}
	protected String createQueryParticelleByIndirizzo(){
		
		String sqlUiu = this.SQL_LISTA_PARTICELLE_BY_INDIRIZZO;
		sqlUiu += " AND ("+ addCondition("SITICOMU.CODI_FISC_LUNA","=",criteria.getCodNazionale())+ " OR "+ addCondition("siticomu.COD_NAZIONALE","=",criteria.getCodNazionale())+")";
		sqlUiu += this.getSQL_IndirizzoCriteria();
		
		return sqlUiu;
	}
	
	protected String createQueryBySoggetto(){ //RIVEDERE
		String pk_cuaa_soggetto = criteria.getIdSoggetto();
		
		/*String sqlIdSoggetti = this.SQL_SELECT_ID_SOGGETTI_BASE;
		sqlIdSoggetti += " AND ("+ addCondition("SITICOMU.CODI_FISC_LUNA","=",cod_nazionale)+ " OR "+ addCondition("siticomu.COD_NAZIONALE","=",cod_nazionale)+")";
		sqlIdSoggetti += this.getSQL_SFCriteria() + this.getSQL_SGCriteria();*/
		
		/*String sqlUiuProprietari = this.SQL_LISTA_UIU_TITOLARITA_PROPRIETARI;
		sqlUiuProprietari = sqlUiuProprietari.replaceAll("@PK_CUAA_SOGGETTO@",pk_cuaa_soggetto);
		sqlUiuProprietari += " AND ("+ addCondition("SITICOMU.CODI_FISC_LUNA","=",cod_nazionale)+ " OR "+ addCondition("siticomu.COD_NAZIONALE","=",cod_nazionale)+")";
		
		String sqlUiuAltro = this.SQL_LISTA_UIU_TITOLARITA_ALTRO;
		sqlUiuAltro = sqlUiuAltro.replaceAll("@PK_CUAA_SOGGETTO@",pk_cuaa_soggetto);
		sqlUiuAltro += " AND ("+ addCondition("SITICOMU.CODI_FISC_LUNA","=",cod_nazionale)+ " OR "+ addCondition("siticomu.COD_NAZIONALE","=",cod_nazionale)+")";
		
		String sqlUiu = this.SQL_SELECT_LISTA_UIU_BY_TITOLARITA;
		sqlUiu = sqlUiu.replaceAll("@SQL_LISTA_UIU_TITOLARITA_PROPRIETARI@", sqlUiuProprietari);
		sqlUiu = sqlUiu.replaceAll("@SQL_LISTA_UIU_TITOLARITA_ALTRO@", sqlUiuAltro);*/
		
		String sqlUiu = this.SQL_SELECT_LISTA_UIU_BY_TITOLARITA;
		sqlUiu = sqlUiu.replaceAll("@PK_CUAA_SOGGETTO@",pk_cuaa_soggetto);
		sqlUiu += " AND ("+ addCondition("SITICOMU.CODI_FISC_LUNA","=",criteria.getCodNazionale())+ " OR "+ addCondition("siticomu.COD_NAZIONALE","=",criteria.getCodNazionale())+")";
		
		
		return sqlUiu;
	}
	
	public String createQueryCongiuntaGettingFPS(boolean isCount){
		String sql = null;
		Boolean flgCriteria = false;
				
		String sqlFrom   = "FROM sitiuiu";
		String sqlWhere  = "WHERE 1=1";
		
		if(criteria.getCodNazionale()!=null && criteria.getCodNazionale().length()>0){
			sqlFrom  += ", siticomu";
			sqlWhere += " AND sitiuiu.cod_nazionale = siticomu.cod_nazionale ";
			sqlWhere += " AND ("+ addCondition("siticomu.codi_fisc_luna","=",criteria.getCodNazionale())+ " OR "+ addCondition("siticomu.cod_nazionale","=",criteria.getCodNazionale())+")";
		}
		
		String sqlUiu = this.getSQL_ImmobileCriteria();
		if(sqlUiu.length()>0){
			flgCriteria = true;
			sqlWhere += sqlUiu;
		}
		
		if(this.isSearchByIndirizzo()){
			String sqlIndirizzo = this.getSQL_IndirizzoCriteria();
			if(sqlIndirizzo.length()>0){
				flgCriteria = true;
				sqlFrom  += ", siticivi_uiu";
				sqlWhere += " AND siticivi_uiu.PKID_UIU = sitiuiu.PKID_UIU " +
							" AND siticivi_uiu.DATA_FINE_VAL = TO_DATE('99991231', 'yyyymmdd') " + sqlIndirizzo;
			}
		}
		
		if(this.isSearchBySoggetto()){
			flgCriteria = true;
			sqlFrom  += ", siticonduz_imm_all";
			sqlWhere += "AND siticonduz_imm_all.DATA_INIZIO < sitiuiu.DATA_FINE_VAL " +
						"AND siticonduz_imm_all.DATA_FINE > sitiuiu.DATA_INIZIO_VAL " +
						"AND siticonduz_imm_all.COD_NAZIONALE = sitiuiu.COD_NAZIONALE " +
						"AND sitiuiu.FOGLIO = siticonduz_imm_all.FOGLIO " +
						"AND sitiuiu.PARTICELLA = siticonduz_imm_all.PARTICELLA " +
						"AND sitiuiu.SUB = siticonduz_imm_all.SUB " +
						"AND sitiuiu.UNIMM = siticonduz_imm_all.UNIMM "+ 
						"AND siticonduz_imm_all.PK_CUAA = '"+criteria.getIdSoggetto()+"' ";
		}
		
		
			//Se esiste un criterio di selezione compone la query e la restituisce, altrimenti vale null
			flgCriteria = true; //Forzo la ricerca anche in assenza di criteri
			if(flgCriteria){
				
				String sqlFields =  "NULL sezione, " +
									"LPAD("+getSqlNvlTrimField("sitiuiu.foglio","0")+",4,'0') foglio, " +
									"LPAD("+getSqlNvlTrimField("sitiuiu.particella","0")+",5,'0') particella, " +
									"LPAD("+getSqlNvlTrimField("sitiuiu.unimm","0")+",4,'0') subalterno";

				String sqlSelect = "SELECT DISTINCT " + sqlFields + " " + sqlFrom + " " + sqlWhere;
				
				if (isCount){
					sql = this.SQL_SELECT_COUNT_LISTA;
					sql = sql.replace("@SQL_SELECT_BASE_LISTA@", sqlSelect);
				}else
					sql = sqlSelect;
			}
			logger.info("SQL CATASTO ["+sql+"]");
			
		return sql;
	}
	
	private String getSqlNvlTrimField(String field, String cod){
		return  "NVL(TRIM("+field+"),'"+cod+"')";
	}
	
	public String createQuery(boolean isCount) {
		
		String sql = null;
		String sqlUiu = null;
		
		if(isSearchByImmobile())
			sqlUiu= this.createQueryByImmobile();
		
		if(isSearchByIndirizzo())
			sqlUiu= this.createQueryByIndirizzo();
		
		if(isSearchBySoggetto())
			sqlUiu= this.createQueryBySoggetto();
		
		//Elenca tutte le unità immobiliari
		//if(sqlUiu.isEmpty())
		//	sqlUiu= this.createQueryByFPS();
		
		if(sqlUiu != null){
			if (isCount){
				sql = this.SQL_SELECT_COUNT_LISTA;
				sql = sql.replace("@SQL_SELECT_BASE_LISTA@", sqlUiu);
			}else{
				sql = this.SQL_SELECT_LISTA;
				sql = sql.replace("@SQL_SELECT_BASE_LISTA@", sqlUiu);
			}
		}
		
		return sql;
	}
	public String createQueryParticelleGenerale(boolean isCount) {
		
		String sql = null;
		String sqlPart = null;
		sqlPart = this.SQL_LISTA_PART;
		sqlPart += this.getSQL_ImmobileCriteria();
		
		if(sqlPart != null){
			if (isCount){
				sql = this.SQL_SELECT_COUNT_LISTA;
				sql = sql.replace("@SQL_SELECT_BASE_LISTA@", sqlPart);
			}else{
				sql = this.SQL_SELECT_LISTA_PARTICELLE;
				sql = sql.replace("@SQL_SELECT_BASE_LISTA@", sqlPart);
			}
		}
		return sql;
	}
	
	//ricerca le particelle e non le unità immobiliari
	public String createQueryParticelle(boolean isCount) {
		
		String sql = null;
		String sqlPart = "";
		
		if(isSearchByImmobile()){
			sqlPart = this.SQL_LISTA_PART;
			sqlPart += this.getSQL_ImmobileCriteria();
		}else if(isSearchByIndirizzo())
			sqlPart += this.createQueryParticelleByIndirizzo();
	
		if(sqlPart != null){
			if (isCount){
				sql = this.SQL_SELECT_COUNT_LISTA;
				sql = sql.replace("@SQL_SELECT_BASE_LISTA@", sqlPart);
			}else{
				sql = this.SQL_SELECT_LISTA_PARTICELLE;
				sql = sql.replace("@SQL_SELECT_BASE_LISTA@", sqlPart);
			}
		}
		return sql;
	}
	
	public String createQueryParticelleTerreni(boolean isCount) {
		String sql = null;
		String sqlPart= null;
		
		sqlPart= createInnerQueryTerreni(false);
		sqlPart += ")";
		if(sqlPart != null){
			if (isCount){
				sql = this.SQL_SELECT_COUNT_LISTA;
				sql = sql.replace("@SQL_SELECT_BASE_LISTA@", sqlPart);
			}else{
				sql = this.SQL_SELECT_LISTA_PARTICELLE;
				sql = sql.replace("@SQL_SELECT_BASE_LISTA@", sqlPart);
			}
		}
		logger.info("sql3: " + sql);
		return sql;
	}
	
	public String createQuerySubTerreni(boolean isCount) {
		String sql = null;
		String sqlPart= null;
		
		sqlPart= createInnerQueryTerreni(true);
		sqlPart += ")";
		if(sqlPart != null){
			if (isCount){
				sql = this.SQL_SELECT_COUNT_LISTA;
				sql = sql.replace("@SQL_SELECT_BASE_LISTA@", sqlPart);
			}else{
				sql = this.SQL_SELECT_LISTA_PARTICELLE;
				sql = sql.replace("@SQL_SELECT_BASE_LISTA@", sqlPart);
			}
		}
		logger.info("sql3: " + sql);
		return sql;
	}
	
	public String createInnerQueryTerreni(boolean selectSub) {
		String sql = null;
		sql= "select distinct id_sezc AS SEZIONE ,LPAD(NVL(TRIM(FOGLIO),'0'),4,'0') AS FOGLIO, particella " ;
		if (selectSub)
			sql += ", sub AS subalterno ";
		sql += "from sititrkc t, siticomu c where " +
		"t.cod_nazionale= c.cod_nazionale  	AND   C.CODI_FISC_LUNA = '" + criteria.getCodNazionale() + "'";
		sql += getSQL_ParticellaTerreno();
		return sql;
	}
	private String getSQL_ParticellaTerreno() {
		String sqlCriteria = "";
		
		sqlCriteria = (criteria.getFoglio() == null  || criteria.getFoglio().trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) +  " FOGLIO = " + criteria.getFoglio() );
		sqlCriteria = (criteria.getParticella() == null || criteria.getParticella().trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) +  " LPAD(particella, 5, '0') = '" + StringUtils.fill('0',"sx", criteria.getParticella(),5) + "'");
		return sqlCriteria;
	}
	
	private String getSQL_ImmobileCriteria() {
		String sqlCriteria = "";
		
		sqlCriteria = (criteria.getFoglio() == null  || criteria.getFoglio().trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + addCondition("sitiuiu.foglio","=",StringUtils.cleanLeftPad(criteria.getFoglio(),'0')));
		
		sqlCriteria = (criteria.getParticella() == null || criteria.getParticella().trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + "sitiuiu.particella = LPAD ('" + criteria.getParticella() + "', 5, '0')");
		
		sqlCriteria = (criteria.getUnimm() == null || criteria.getUnimm().trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + addCondition("sitiuiu.unimm","=",StringUtils.cleanLeftPad(criteria.getUnimm(),'0')));

		sqlCriteria = (criteria.getCodCategoria() == null || criteria.getCodCategoria().trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + addCondition("sitiuiu.categoria","IN",criteria.getCodCategoria()));
		
		sqlCriteria = (criteria.getUiuAttuale() != null && criteria.getUiuAttuale() ? addOperator(sqlCriteria) + "( sitiuiu.data_fine_val IS NULL OR sitiuiu.data_fine_val = TO_DATE('99991231', 'yyyymmdd'))" : sqlCriteria );
		
		
		return sqlCriteria;
	}
	
	private String getSQL_IndirizzoCriteria() {
		
		String sqlCriteria = "";
		String civico = criteria.getIdCivico();
		String via = criteria.getIdVia();
		boolean isCivico = (criteria.getIdCivico() != null && !criteria.getIdCivico().trim().equals("")); 
		
		if(isCivico)
		  sqlCriteria = addOperator(sqlCriteria) + addCondition("SITICIVI_UIU.PKID_CIVI", "=",civico);
		  
		else{
			  sqlCriteria = "and SITICIVI_UIU.PKID_CIVI IN (" +
			  		"select " +
			  		"siticivi.PKID_CIVI as PK_CIVICO " +
			  		"from siticivi " +
			  		"where siticivi.DATA_FINE_VAL (+) = TO_DATE('99991231', 'yyyymmdd') " +
			  		"and SITICIVI.COD_NAZIONALE = '"+criteria.getCodNazionale()+"' " ;
			  		
			  sqlCriteria = (via == null  || via.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + addCondition("SITICIVI.PKID_STRA", "=", via));
		      sqlCriteria += ") ";
		  }
		return sqlCriteria;
	}
	
	private String getSQL_SoggettoCriteria(){
		String sqlCriteria = "";
		
		return sqlCriteria;
	}
	
	/*private String getSQL_SFCriteria() {
		
		//Ricerca per soggetto fisico titolare
		String sqlCriteria = "";
		
		sqlCriteria = (soggettof == null || soggettof.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + addCondition("cons_sogg_tab.PK_CUAA","=",soggettof));
		
		if(!sqlCriteria.isEmpty()){
			sqlCriteria = (cfSoggetto == null || cfSoggetto.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + addCondition("cons_sogg_tab.CODI_FISC","=",cfSoggetto));
			sqlCriteria = "(SELECT CONS_SOGG_TAB.PK_CUAA FROM CONS_SOGG_TAB WHERE DATA_FINE = TO_DATE('99991231', 'yyyymmdd') "+ sqlCriteria +")";
		}
		//Aggiunge la condizione sulla persona fisica
		if(!sqlCriteria.isEmpty())
			sqlCriteria += addOperator(sqlCriteria)+ addCondition("cons_sogg_tab.FLAG_PERS_FISICA","=","P");
		
		return sqlCriteria;
	}
	
	private String getSQL_SGCriteria() {
		//Ricerca per soggetto giuridico titolare
		String sqlCriteria = "";
		
		sqlCriteria = (soggettog == null || soggettog.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + addCondition("ccons_sogg_tab.PK_CUAA","=",soggettog));
		
		if(sqlCriteria.isEmpty()){
		sqlCriteria = (pivaSoggetto == null || pivaSoggetto.trim().equals("") ? sqlCriteria : addOperator(sqlCriteria) + addCondition("cons_sogg_tab.CODI_PIVA","=",pivaSoggetto));
		sqlCriteria = "(SELECT CONS_SOGG_TAB.PK_CUAA FROM CONS_SOGG_TAB WHERE DATA_FINE = TO_DATE('99991231', 'yyyymmdd') "+ sqlCriteria +")";
		}
		
		//Aggiunge la condizione sulla persona giuridica
		if(!sqlCriteria.isEmpty())
			sqlCriteria += addOperator(sqlCriteria)+ addCondition("cons_sogg_tab.FLAG_PERS_FISICA","=","G");
		
		return sqlCriteria;
	}*/
	
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
    }
	
	protected String addOperator(String criteria) {
    	if (criteria == null)
    	    return criteria;    	
    	else    	    
    	    return criteria += " AND ";
    }
	
	protected boolean isSearchByImmobile(){
		boolean result = false;
		String foglio = criteria.getFoglio();
		String particella = criteria.getParticella();
		String unimm = criteria.getUnimm();
		Boolean nonANorma = criteria.getNonANorma();
		String codCategoria = criteria.getCodCategoria();
		
		boolean isFoglio = (foglio != null && !foglio.equals(""));    // Parametro impostato
		boolean isParticella = (particella != null && !particella.equals(""));    // Parametro impostato
		boolean isUnimm = (unimm != null && !unimm.equals(""));    // Parametro impostato
		boolean isNonANorma = (nonANorma != null && nonANorma);
		boolean isCategoria = (codCategoria!=null && !codCategoria.equals(""));
		
		result = isFoglio || isParticella || isUnimm || isNonANorma || isCategoria;
		
		return result;
	}
	
	protected boolean isSearchByIndirizzo(){
		boolean result = false;
		String via = criteria.getIdVia();
		String civico = criteria.getIdCivico();
		
		boolean isVia = (via != null && !via.equals(""));    // Parametro impostato
		boolean isCivico = (civico != null && !civico.equals(""));    // Parametro impostato
		
		result = isVia || isCivico;
		return result;
	}
	
	
	protected boolean isSearchBySoggetto(){
		boolean result = false;
		String soggetto = criteria.getIdSoggetto();
		
		result = (soggetto!= null && !soggetto.equals(""));
		
		return result;
	}

	
	public String getSQL_LISTA_SOGGETTI_BY_UNIMM() {
		return SQL_LISTA_SOGGETTI_BY_UNIMM;
	}

	/*public String getSQL_LOCALIZZAZIONE_CATASTALE_IMMOBILE() {
		return SQL_LOCALIZZAZIONE_CATASTALE_IMMOBILE;
	}*/
	
	public String getSQL_LISTA_INDIRIZZI_BY_FP() {
		return SQL_LISTA_INDIRIZZI_BY_FP;
	}
    
	public String getSQL_LISTA_INDIRIZZI_BY_FPeDATA() {
		return SQL_LISTA_INDIRIZZI_BY_FPeDATA;
	}
	
	public String getSQL_LISTA_TITOLARI_TERRENO(boolean attuali) {
		String sql=SQL_LISTA_TITOLARI_TERRENO;
		if (attuali)
			sql= sql.replace("@CC.DATA_FINE_COND", "AND CC.DATA_FINE >=SYSDATE ");
		else	
			sql = sql.replace("@CC.DATA_FINE_COND", "AND CC.DATA_FINE <SYSDATE ");
		return sql;
	}
	
	public String getSQL_LISTA_ALTRI_SOGGETTI_TERRENO(boolean attuali) {
		String sql=SQL_LISTA_ALTRI_SOGGETTI_TERRENO;
		if (attuali)
			sql= sql.replace("@CC.DATA_FINE_COND", "AND CC.DATA_FINE >=SYSDATE ");
		else	
			sql = sql.replace("@CC.DATA_FINE_COND", "AND CC.DATA_FINE <SYSDATE ");
		return sql;
	}

	
	public String createQueryImmobiliAccatastati_Step2(RicercaSoggettoCatDTO criteria){
		String sql = this.getImmobiliAccatastati_SQL_Step2();
		
		String dataInizio = criteria.getsDtRifDa("dd/MM/yyyy");
		String dataFine = criteria.getsDtRifA("dd/MM/yyyy"); 
		
		String condEnte  = "";
		condEnte = (criteria.getEnteId() == null || criteria.getEnteId().trim().equals("") ? condEnte : addOperator(condEnte) +  " siticomu.COD_NAZIONALE = '" + criteria.getEnteId() +"'");
		sql= sql.replace("@SQL_CONDIZIONI_ENTE@", condEnte);
		
		String condSogg = "";
		condSogg = (criteria.getIdSogg() == null ? condSogg : addOperator(condSogg) +  " siticonduz_imm_all.pk_cuaa = "+ criteria.getIdSogg());
		sql = sql.replace("@SQL_CONDIZIONI_SOGGETTO@", condSogg);
		
		String condIntervallo = "";
		if(!(dataInizio==null && dataFine==null)){
		condIntervallo = 
			   " AND (siticonduz_imm_all.data_inizio IS NULL "+
               "         OR siticonduz_imm_all.data_inizio = TO_DATE ('01/01/0001', 'DD/MM/YYYY')  "+
               "         OR siticonduz_imm_all.data_inizio <= TO_DATE ("+nvlDateFine(dataFine)+", 'dd/MM/yyyy'))  "+
               "         AND (siticonduz_imm_all.data_fine IS NULL  "+
               "         OR siticonduz_imm_all.data_fine = TO_DATE ('31/12/9999', 'DD/MM/YYYY')  "+
               "         OR siticonduz_imm_all.data_fine >= TO_DATE ("+nvlDateInizio(dataInizio)+", 'dd/MM/yyyy'))  "+
               "AND (SITIUIU.data_inizio_val IS NULL  "+
               "         OR SITIUIU.data_inizio_val = TO_DATE ('01/01/0001', 'DD/MM/YYYY')  "+
               "         OR SITIUIU.data_inizio_val <= TO_DATE ("+nvlDateFine(dataFine)+", 'dd/MM/yyyy'))  "+
               "         AND (SITIUIU.data_fine_val IS NULL  "+
               "         OR SITIUIU.data_fine_val = TO_DATE ('31/12/9999', 'DD/MM/YYYY')  "+
               "         OR SITIUIU.data_fine_val >= TO_DATE ("+nvlDateInizio(dataInizio)+", 'dd/MM/yyyy'))  ";
				
			}
		
		sql = sql.replace("@SQL_CONDIZIONI_INTERVALLO_VAL@", condIntervallo);
		
		return sql;
		
	}
	
	
	public String createQueryTerreniAccatastati_Step2(RicercaSoggettoCatDTO criteria){
		String sql = this.getTerreniAccatastati_SQL_Step2();
		
		String dataInizio = criteria.getsDtRifDa("dd/MM/yyyy");
		String dataFine = criteria.getsDtRifA("dd/MM/yyyy"); 
		
		String condEnte  = "";
		condEnte = (criteria.getEnteId() == null || criteria.getEnteId().trim().equals("") ? condEnte : addOperator(condEnte) +  "( SITICOMU.CODI_FISC_LUNA= '"+criteria.getEnteId()+"' OR siticomu.COD_NAZIONALE = '" + criteria.getEnteId() +"' )");
		sql= sql.replace("@SQL_CONDIZIONI_ENTE@", condEnte);
		
		String condSogg = "";
		condSogg = (criteria.getIdSogg() == null ? condSogg : addOperator(condSogg) +  " conduz.pk_cuaa = "+ criteria.getIdSogg());
		sql = sql.replace("@SQL_CONDIZIONI_SOGGETTO@", condSogg);
		
		String condIntervallo = "";
		if(!(dataInizio==null && dataFine==null)){
		condIntervallo = 
			   " AND (conduz.data_inizio IS NULL "+
               "         OR conduz.data_inizio = TO_DATE ('01/01/0001', 'DD/MM/YYYY')  "+
               "         OR conduz.data_inizio <= TO_DATE ("+nvlDateFine(dataFine)+", 'dd/MM/yyyy'))  "+
               "         AND (conduz.data_fine IS NULL  "+
               "         OR conduz.data_fine = TO_DATE ('31/12/9999', 'DD/MM/YYYY')  "+
               "         OR conduz.data_fine >= TO_DATE ("+nvlDateInizio(dataInizio)+", 'dd/MM/yyyy'))  "+
               "AND (sititrkc.data_aggi IS NULL  "+
               "         OR sititrkc.data_aggi = TO_DATE ('01/01/0001', 'DD/MM/YYYY')  "+
               "         OR sititrkc.data_aggi <= TO_DATE ("+nvlDateFine(dataFine)+", 'dd/MM/yyyy'))  "+
               "         AND (sititrkc.data_fine IS NULL  "+
               "         OR sititrkc.data_fine = TO_DATE ('31/12/9999', 'DD/MM/YYYY')  "+
               "         OR sititrkc.data_fine >= TO_DATE ("+nvlDateInizio(dataInizio)+", 'dd/MM/yyyy'))  ";
				
		}
		
		sql = sql.replace("@SQL_CONDIZIONI_INTERVALLO_VAL@", condIntervallo);
		
		return sql;
		
	}
    
	public String createQueryPkcuaaSoggetti(RicercaSoggettoCatDTO criteria){
		String sql = this.getPkcuaaSoggetti_SQL();
		
		String dataNascita = criteria.getsDtNas("dd/MM/yyyy");
		
		String condSogg = "";
		condSogg = (criteria.getPartIva() == null || criteria.getPartIva().trim().equals("") ? condSogg : addOperator(condSogg) +  " cons_sogg_tab.codi_piva = '"+ criteria.getPartIva()+"'" );
		condSogg = (criteria.getCodFis() == null || criteria.getCodFis().trim().equals("") ? condSogg : addOperator(condSogg) +  " cons_sogg_tab.codi_fisc = '"+ criteria.getCodFis()+"'" );
		condSogg = (criteria.getCognome() == null || criteria.getCognome().trim().equals("") ? condSogg : addOperator(condSogg) +  " cons_sogg_tab.ragi_soci = '" + criteria.getCognome().replaceAll("'", "`") +"'" );
		condSogg = (criteria.getNome() == null || criteria.getNome().trim().equals("") ? condSogg : addOperator(condSogg) +  " cons_sogg_tab.nome = '" + criteria.getNome().replaceAll("'", "`") +"'");
		condSogg = (dataNascita == null || dataNascita.trim().equals("") ? condSogg : addOperator(condSogg) +  " cons_sogg_tab.data_nasc = TO_DATE( '"+dataNascita+"', 'dd/MM/yyyy') " );
		
		sql = sql.replace("@SQL_CONDIZIONI_SOGGETTO@", condSogg);
		
		return sql;
		
	}//-------------------------------------------------------------------------
		
	private String nvlDateInizio(String dt){
		
		return "NVL('"+dt+"', '01/01/0001')";
		
	}
	
	private String nvlDateFine(String dt){
		
		return "NVL('"+dt+"', '31/12/9999')";
		
	}

	public String getPkcuaaSoggetti_SQL() {
		return PkcuaaSoggetti_SQL;
	}

	public String getImmobiliAccatastati_SQL_Step2() {
		return ImmobiliAccatastati_SQL_Step2;
	}
	
	public String getTerreniAccatastati_SQL_Step2() {
		return TerreniAccatastati_SQL_Step2;
	}

	public String getRegimeImmobili_SQL() {	
		return RegimeImmobili_SQL;
	}//-------------------------------------------------------------------------

	public String getRegimeTerreni_SQL() {
		return RegimeTerreni_SQL;
	}

	public String getSoggettoCollegatoTerreni_SQL() {
		return SoggettoCollegatoTerreni_SQL;
	}

	public String getSoggettoCollegatoImmobili_SQL() {
		return SoggettoCollegatoImmobili_SQL;
	}//-------------------------------------------------------------------------

	public String getSQL_LOCALIZZAZIONE_CATASTALE(boolean soloDescrizione, boolean uiu){
		
		String s = null;
		
		if(soloDescrizione)
			s = this.SQL_LOC_CAT_SelectDescr + this.SQL_LOCALIZZAZIONE_CATASTALE_PART_From;
		else
			s = this.SQL_LOC_CAT_SelectAll + this.SQL_LOCALIZZAZIONE_CATASTALE_PART_From;
		
		if(s!=null && uiu)
			s +=  "AND LOAD_CAT_UIU_ID.SUB = LPAD (:unimm, 4, '0') ";
		
		return s;
	}//-------------------------------------------------------------------------
	
	public String createQueryCoordUiByTopo(RicercaCivicoDTO criteria){
		String sql = this.SQL_COORD_BY_TOPO;
		
		String cond = "";
		cond = (criteria.getToponimoVia() == null || criteria.getToponimoVia().trim().equals("") ? cond : addOperator(cond) +  " SITIDSTR.PREFISSO = '"+ criteria.getToponimoVia()+"'" );
		cond = (criteria.getDescrizioneVia() == null || criteria.getDescrizioneVia().trim().equals("") ? cond : addOperator(cond) +  " SITIDSTR.NOME = '"+ criteria.getDescrizioneVia()+"'" );
		cond = (criteria.getCivico() == null || criteria.getCivico().trim().equals("") ? cond : addOperator(cond) +  " SITICIVI.CIVICO = '" + criteria.getCivico() +"'" );
		
		sql = sql.replace("@SQL_CONDIZIONI_WHERE@", cond);
		
		return sql;
	}//-------------------------------------------------------------------------

	public String getListaImmobiliEGraffati_SQL() {
		return ListaImmobiliEGraffati_SQL;
	}
	
	public String createQueryListaImmobiliByParams(RicercaOggettoCatDTO criteria){
		String hql = this.HQL_IMMO_BY_PARAMS;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		String cond = "";
		cond = (criteria.getCodEnte() == null || criteria.getCodEnte().trim().equals("") ? cond : addOperator(cond) +  " c.codiFiscLuna = '"+criteria.getCodEnte()+"' " );
		cond = (criteria.getSezione() == null || criteria.getSezione().trim().equals("") ? cond : addOperator(cond) +  " (c.idSezc is null or c.idSezc = '"+criteria.getSezione()+"') " );
		cond = (criteria.getFoglio() == null || criteria.getFoglio().trim().equals("") ? cond : addOperator(cond) +  " u.id.foglio = TO_NUMBER('"+criteria.getFoglio()+"') " );
		cond = (criteria.getParticella() == null || criteria.getParticella().trim().equals("") ? cond : addOperator(cond) +  " u.id.particella = LPAD('"+criteria.getParticella()+"' ,5,'0') " );
		cond = (criteria.getUnimm() == null || criteria.getUnimm().trim().equals("") ? cond : addOperator(cond) +  " u.id.unimm = TO_NUMBER('"+criteria.getUnimm()+"') " );
		cond = (criteria.getDtVal() == null ? cond : addOperator(cond) +  " u.dataInizioVal <= TO_DATE('"+  formatter.format(criteria.getDtVal())+"', 'dd/MM/yyyy') AND u.id.dataFineVal >= TO_DATE('"+  formatter.format(criteria.getDtVal())+"', 'dd/MM/yyyy') " );
		
		hql = hql.replace("@HQL_CONDIZIONI_WHERE@", cond);
		
		return hql;
	}//-------------------------------------------------------------------------
	
	public String createQueryListaTerreniByParams(RicercaOggettoCatDTO criteria){
		String hql = this.HQL_TERRE_BY_PARAMS;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		String cond = "";
		cond = (criteria.getCodEnte() == null || criteria.getCodEnte().trim().equals("") ? cond : addOperator(cond) +  " (c.codiFiscLuna = '"+criteria.getCodEnte()+"' OR c.codNazionale = '"+criteria.getCodEnte()+"' ) " );
		cond = (criteria.getSezione() == null || criteria.getSezione().trim().equals("") ? cond : addOperator(cond) +  " (c.idSezc is null or c.idSezc = '"+criteria.getSezione()+"' ) " );
		cond = (criteria.getFoglio() == null || criteria.getFoglio().trim().equals("") ? cond : addOperator(cond) +  " t.id.foglio = TO_NUMBER('"+criteria.getFoglio()+"') " );
		cond = (criteria.getParticella() == null || criteria.getParticella().trim().equals("") ? cond : addOperator(cond) +  " t.id.particella = LPAD('"+criteria.getParticella()+"' ,5,'0') " );
		cond = (criteria.getSub() == null || criteria.getSub().trim().equals("") ? cond : addOperator(cond) +  " NVL(t.id.sub,' ') = NVL('"+criteria.getSub()+"') " );
		cond = (criteria.getDtVal() == null ? cond : addOperator(cond) +  " t.id.dataFine >= TO_DATE('"+  formatter.format(criteria.getDtVal())+"', 'dd/MM/yyyy') " );
		cond = addOperator(cond) +  " NVL(upper(t.annotazioni),'-') <> 'CREATO AUTOMATICAMENTE IN FASE DI IMPORTAZIONE UIU' ";
		
		hql = hql.replace("@HQL_CONDIZIONI_WHERE@", cond);
		
		return hql;
	}//-------------------------------------------------------------------------
	
	
}
