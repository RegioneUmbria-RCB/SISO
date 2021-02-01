package it.webred.rulengine.brick.cataloghi;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

public class PraticheEdilizieSuParticella extends Command implements Rule {
	
	private static final Logger log = Logger.getLogger(PraticheEdilizieSuParticella.class.getName());
	
	private static final ArrayList<String> SQLS = new ArrayList<String>();
	static {
		SQLS.add("DROP TABLE JOIN_CIVICO_CONC_SIT");
		
		SQLS.add("CREATE TABLE JOIN_CIVICO_CONC_SIT AS (" +
				"SELECT VIS.INXDOC AS VIS_CODICE, VIS.TPV AS VIS_SEDIME," +
	            "VIS.NOME_VIA AS VIS_NOME_VIA,VIS.CIVICO AS VIS_CIVICO, VIS.CIVICO_SUB AS VIS_CIVICO_SUB," +
	            "VIS.INXVIA VIS_COD_VIA, DVIA.PKID_STRA, DVIA.PREFISSO AS VIA_SEDIME," +
	            "DVIA.NOME AS VIA_NOME_VIA," +
	            "CIV.CIVICO," +
	            "CIV.PKID_CIVI," +
	            "CIV.SHAPE  AS CIV_SHAPE " +
		        "FROM MI_CONC_EDILIZIE_VISURE VIS, SITIDSTR DVIA, SITICIVI CIV " +
		        "WHERE VIS.INXVIA = DVIA.PKID_STRA " +
		        "AND CIV.PKID_STRA = DVIA.PKID_STRA " +	
				"AND (VIS.CIVICO_SUB = CIV.CIVICO " +
		        "OR (" +
		            "TO_CHAR(VIS.CIVICO) = CIV.CIVICO " +
		            "AND INSTR(VIS.CIVICO_SUB, '/') > 0 " +
		            "AND TO_CHAR(VIS.CIVICO) = SUBSTR(VIS.CIVICO_SUB, 1, INSTR(VIS.CIVICO_SUB, '/') - 1)" +
		        ")" +
		        ")" +
		        "AND CIV.DATA_FINE_VAL = TO_DATE('31/12/9999','dd/mm/yyyy')" +          
		        "AND DVIA.DATA_FINE_VAL = TO_DATE('31/12/9999','dd/mm/yyyy')" +
		        ")");
		
		SQLS.add("DROP TABLE APPO_SFP");
		
		SQLS.add("CREATE TABLE APPO_SFP AS (" +
				"SELECT DISTINCT CATSEZ.SEZIONE AS SEZIONE, PAR.FOGLIO AS FOGLIO, PAR.PARTICELLA AS PARTICELLA " +
				"FROM &SITI..CATA_SEZIONI CATSEZ, SITIPART PAR " +
				"WHERE CATSEZ.COD_SEZIONE = PAR.COD_NAZIONALE " +
				"AND NVL(PAR.DATA_FINE_VAL, TO_DATE('31/12/9999','dd/mm/yyyy')) = TO_DATE('31/12/9999','dd/mm/yyyy') " +
				"AND EXISTS (SELECT 1 " +
					"FROM SITIUIU UIU " +
					"WHERE NVL(TRIM(UIU.COD_NAZIONALE), '-') = NVL(TRIM(CATSEZ.COD_SEZIONE), '-') " +
					"AND LPAD (TRIM (UIU.FOGLIO), 4, '0') = LPAD (TRIM (PAR.FOGLIO), 4, '0') " +
					"AND LPAD (TRIM (UIU.PARTICELLA), 5, '0') = LPAD (TRIM (PAR.PARTICELLA), 5, '0') " +
		        	"AND NVL(UIU.DATA_FINE_VAL, TO_DATE('31/12/9999','dd/mm/yyyy')) = TO_DATE('31/12/9999','dd/mm/yyyy')" +
		        	")" +
				")");
		
		SQLS.add("CREATE INDEX APPO_SFP_IDX ON APPO_SFP" +
				"(TRIM(SEZIONE), LPAD(TRIM(FOGLIO), 4, '0'), LPAD(TRIM(PARTICELLA), 5, '0'))");
		
		SQLS.add("DROP TABLE APPO_SFP_CIVICI");
		
		SQLS.add("CREATE TABLE APPO_SFP_CIVICI AS (" +
				"SELECT DISTINCT TRIM(CATSEZ.SEZIONE) AS SEZIONE," +
				"LPAD (TRIM (SITIUIU.FOGLIO), 4, '0') AS FOGLIO," +
				"LPAD (TRIM (SITIUIU.PARTICELLA), 5, '0')  AS PARTICELLA," +
				"SITICIVI_UIU.PKID_CIVI AS ID_CIVI " +
				"FROM SITIUIU, SITICOMU, SITICIVI_UIU, SITIDSTR, SITICIVI, &SITI..CATA_SEZIONI CATSEZ " +
				"WHERE SITIUIU.COD_NAZIONALE = SITICOMU.COD_NAZIONALE " +
				"AND SITICIVI_UIU.PKID_UIU = SITIUIU.PKID_UIU " +
				"AND SITICIVI_UIU.PKID_CIVI = SITICIVI.PKID_CIVI " +
				"AND SITICIVI.PKID_STRA = SITIDSTR.PKID_STRA " +
				"AND NVL(TRIM(CATSEZ.COD_SEZIONE), '-') = NVL(TRIM(SITIUIU.COD_NAZIONALE), '-') " +
				"AND NVL(SITICIVI_UIU.DATA_FINE_VAL, TO_DATE('31/12/9999','dd/mm/yyyy')) = TO_DATE('31/12/9999','dd/mm/yyyy')" +
				")");
		
		SQLS.add("CREATE INDEX APPO_SFP_CIVICI_IDX ON APPO_SFP_CIVICI" +
				"(TRIM(SEZIONE), LPAD(TRIM(FOGLIO), 4, '0'), LPAD(TRIM(PARTICELLA), 5, '0'))");
		
		SQLS.add("DROP TABLE APPO_CONC_FG_PART_SHAPE");
		
		SQLS.add("CREATE TABLE APPO_CONC_FG_PART_SHAPE" +
				"(" +
				  "SEZIONE               VARCHAR2(10 BYTE)," +
				  "FOGLIO				 VARCHAR2(4 BYTE)," +
				  "PARTICELLA			 VARCHAR2(5 BYTE)," +
				  "TIPO					 VARCHAR2(4 BYTE)," +
				  "FLAG_LAYER			 NUMBER(1)," +
				  "TOTALE				 NUMBER(9)," +
				  "SHAPE                 MDSYS.SDO_GEOMETRY" +
				")");
		
		SQLS.add("CREATE INDEX APPO_CONC_FG_PART_SHAPE_IDX ON APPO_CONC_FG_PART_SHAPE" +
				"(SEZIONE, FOGLIO, PARTICELLA)");
		
		SQLS.add("CREATE TABLE APPO_STO_VIS_STEP1 AS " +
				"SELECT JOCS.VIS_CODICE, TRIM(CATSEZ.SEZIONE) AS SEZIONE, LPAD (TRIM (UIU.FOGLIO), 4, '0') AS FOGLIO, LPAD (TRIM (UIU.PARTICELLA), 5, '0') AS PARTICELLA " +
				"FROM SITIUIU UIU,  SITICIVI_UIU CU, JOIN_CIVICO_CONC_SIT JOCS, &SITI..CATA_SEZIONI CATSEZ " +
				"WHERE UIU.PKID_UIU  = CU.PKID_UIU " +
				"AND JOCS.PKID_CIVI = CU.PKID_CIVI " +
				"AND CATSEZ.COD_SEZIONE = UIU.COD_NAZIONALE " +
				"AND CU.DATA_FINE_VAL = TO_DATE('31/12/9999','dd/mm/yyyy') " +
				"AND UIU.DATA_FINE_VAL = TO_DATE('31/12/9999','dd/mm/yyyy')");
			
		SQLS.add("DROP TABLE APPO_STO_VIS");

		SQLS.add("CREATE TABLE APPO_STO_VIS AS " +
				"SELECT DISTINCT * FROM APPO_STO_VIS_STEP1");

		SQLS.add("CREATE INDEX APPO_STO_VIS_IDX ON APPO_STO_VIS" +
				"(SEZIONE, FOGLIO, PARTICELLA)");
			
		SQLS.add("DROP TABLE APPO_STO_VIS_STEP1");

		//da qui select con parametri sezione-foglio-particella
		SQLS.add("SELECT * FROM APPO_SFP");
		
		//concessioni
		SQLS.add("INSERT INTO APPO_CONC_FG_PART_SHAPE " +
				"SELECT STEP1.SEZIONE, STEP1.FOGLIO, STEP1.PARTICELLA, 'CONC', 1, TOTALE, PAR.SHAPE " +
				"FROM (" +
					"SELECT SEZIONE, FOGLIO, PARTICELLA, COUNT(1) AS TOTALE FROM " +
					"(SELECT DISTINCT C.ID_EXT AS CHIAVE, TRIM(CC.SEZIONE) AS SEZIONE, LPAD (TRIM (CC.FOGLIO), 4, '0') AS FOGLIO, LPAD (TRIM (CC.PARTICELLA), 5, '0') AS PARTICELLA " +
					"FROM SIT_C_CONCESSIONI C, SIT_C_CONCESSIONI_CATASTO CC " +
					"WHERE CC.ID_EXT_C_CONCESSIONI = C.ID_EXT " +
					"AND NVL(C.DT_FINE_VAL, TO_DATE('31/12/9999','dd/mm/yyyy')) = TO_DATE('31/12/9999','dd/mm/yyyy') " +
					"AND NVL(CC.DT_FINE_VAL, TO_DATE('31/12/9999','dd/mm/yyyy')) = TO_DATE('31/12/9999','dd/mm/yyyy')" +
					")" +
					"GROUP BY SEZIONE, FOGLIO, PARTICELLA" +
				") STEP1, SITIPART PAR " +
				"WHERE NVL(TRIM(STEP1.SEZIONE), '-') = (SELECT NVL(TRIM(CATSEZ.SEZIONE), '-') " +
				"FROM &SITI..CATA_SEZIONI CATSEZ " +
				"WHERE NVL(TRIM(CATSEZ.COD_SEZIONE), '-') = NVL(TRIM(PAR.COD_NAZIONALE), '-')" +
				")" +
				"AND LPAD (TRIM (STEP1.FOGLIO), 4, '0') = LPAD (TRIM (PAR.FOGLIO), 4, '0') " +
				"AND LPAD (TRIM (STEP1.PARTICELLA), 5, '0') = LPAD (TRIM (PAR.PARTICELLA), 5, '0') " +
				"AND PAR.SUB = ' ' " +
				"AND PAR.DATA_FINE_VAL = TO_DATE('31/12/9999','dd/mm/yyyy') " +
				"AND NVL(TRIM(STEP1.SEZIONE), '-') = NVL(TRIM(?), '-') " +
				"AND LPAD (TRIM (STEP1.FOGLIO), 4, '0') = LPAD (TRIM (?), 4, '0') " +
				"AND LPAD (TRIM (STEP1.PARTICELLA), 5, '0') = LPAD (TRIM (?), 5, '0')");
		
		//archivio storico visure
		SQLS.add("INSERT INTO APPO_CONC_FG_PART_SHAPE " +
				"SELECT STEP1.SEZIONE, STEP1.FOGLIO, STEP1.PARTICELLA, 'ARCH', 1, TOTALE, PAR.SHAPE " +
				"FROM (" +
					"SELECT SEZIONE, FOGLIO, PARTICELLA, COUNT(1) AS TOTALE FROM " +
					"(SELECT * FROM APPO_STO_VIS) " +
					"GROUP BY SEZIONE, FOGLIO, PARTICELLA" +
				") STEP1, SITIPART PAR " +
				"WHERE NVL(TRIM(STEP1.SEZIONE), '-') = (SELECT NVL(TRIM(CATSEZ.SEZIONE), '-') " +
				"FROM &SITI..CATA_SEZIONI CATSEZ " +
				"WHERE NVL(TRIM(CATSEZ.COD_SEZIONE), '-') = NVL(TRIM(PAR.COD_NAZIONALE), '-')" +
				")" +
				"AND LPAD (TRIM (STEP1.FOGLIO), 4, '0') = LPAD (TRIM (PAR.FOGLIO), 4, '0') " +
				"AND LPAD (TRIM (STEP1.PARTICELLA), 5, '0') = LPAD (TRIM (PAR.PARTICELLA), 5, '0') " +
				"AND PAR.SUB = ' ' " +
				"AND PAR.DATA_FINE_VAL = TO_DATE('31/12/9999','dd/mm/yyyy') " +
				"AND NVL(TRIM(STEP1.SEZIONE), '-') = NVL(TRIM(?), '-') " +
				"AND LPAD (TRIM (STEP1.FOGLIO), 4, '0') = LPAD (TRIM (?), 4, '0') " +
				"AND LPAD (TRIM (STEP1.PARTICELLA), 5, '0') = LPAD (TRIM (?), 5, '0')");
		
		//concessioni al civico
		SQLS.add("INSERT INTO APPO_CONC_FG_PART_SHAPE " +
				"SELECT STEP1.SEZIONE, STEP1.FOGLIO, STEP1.PARTICELLA, 'CIVI', 1, TOTALE, PAR.SHAPE " +
				"FROM (" +
					"SELECT SEZIONE, FOGLIO, PARTICELLA, COUNT(1) AS TOTALE FROM " +
						"(SELECT DISTINCT C.ID_EXT AS CHIAVE, TRIM(?) AS SEZIONE, LPAD (TRIM (?), 4, '0') AS FOGLIO, LPAD (TRIM (?), 5, '0') AS PARTICELLA " +
						"FROM SIT_C_CONCESSIONI C " +
						"WHERE ID IN (" +
						"SELECT DISTINCT ID_DWH " +
						"FROM SIT_CIVICO_TOTALE CT1 " +
						"WHERE CT1.PROG_ES = 2 " +
						"AND CT1.FK_ENTE_SORGENTE = 3 " +
						"AND CT1.FK_CIVICO IN (SELECT CT.FK_CIVICO FROM SIT_CIVICO_TOTALE CT " +
						"WHERE CT.ID_DWH IN (" +
							"SELECT DISTINCT ID_DWH " +
							"FROM SIT_CIVICO_TOTALE CT1 " +
							"WHERE CT1.FK_ENTE_SORGENTE = 4 " +
							"AND CT1.PROG_ES = 2 " +
							"AND CT1.FK_CIVICO IN (SELECT CT.FK_CIVICO FROM SIT_CIVICO_TOTALE CT " +
							"WHERE CT.ID_DWH IN (" +
								"SELECT AC.ID_CIVI " +
								"FROM APPO_SFP_CIVICI AC " +
								"WHERE NVL(TRIM (AC.SEZIONE), '-') = NVL(TRIM(?), '-') " +
								"AND LPAD (TRIM (AC.FOGLIO), 4, '0') = LPAD (TRIM (?), 4, '0') " +
								"AND LPAD (TRIM (AC.PARTICELLA), 5, '0') = LPAD (TRIM (?), 5, '0')" +
							") " +
							"AND CT.PROG_ES = 2 " +
							"AND CT.FK_ENTE_SORGENTE = 4" +
							")" +
						") " +
						"AND CT.PROG_ES = 2 " +
						"AND CT.FK_ENTE_SORGENTE = 4)" +
						") " +
						"AND NOT EXISTS (SELECT 1 " +
						"FROM SIT_C_CONCESSIONI_CATASTO CC " +
						"WHERE CC.ID_EXT_C_CONCESSIONI = C.ID_EXT " +
						"AND NVL(TRIM(CC.SEZIONE), '-') = NVL(TRIM(?), '-') " +
						"AND LPAD (TRIM (CC.FOGLIO), 4, '0') = LPAD (TRIM (?), 4, '0') " +
						"AND LPAD (TRIM (CC.PARTICELLA), 5, '0') = LPAD (TRIM (?), 5, '0')" +
						")" +
					") " +
				"GROUP BY SEZIONE, FOGLIO, PARTICELLA" +
				") STEP1, SITIPART PAR " +
				"WHERE NVL(TRIM(STEP1.SEZIONE), '-') = (SELECT NVL(TRIM(CATSEZ.SEZIONE), '-') " +
				"FROM &SITI..CATA_SEZIONI CATSEZ " +
				"WHERE NVL(TRIM(CATSEZ.COD_SEZIONE), '-') = NVL(TRIM(PAR.COD_NAZIONALE), '-')" +
				") " +
				"AND LPAD (TRIM (STEP1.FOGLIO), 4, '0') = LPAD (TRIM (PAR.FOGLIO), 4, '0') " +
				"AND LPAD (TRIM (STEP1.PARTICELLA), 5, '0') = LPAD (TRIM (PAR.PARTICELLA), 5, '0') " +
				"AND PAR.SUB = ' ' " +
				"AND PAR.DATA_FINE_VAL = TO_DATE('31/12/9999','dd/mm/yyyy')");
		
		//da qui fine select con parametri sezione-foglio-particella
		SQLS.add("DROP TABLE CAT_VISURE_PART_TEMP");
		
		SQLS.add("CREATE TABLE CAT_VISURE_PART_TEMP AS SELECT * FROM CAT_VISURE_PART");
		
		SQLS.add("DROP TABLE CAT_VISURE_PART");
		
		SQLS.add("CREATE TABLE CAT_VISURE_PART " +
				"AS SELECT SEZIONE, FOGLIO, PARTICELLA, SUM(TOTALE) AS NUM_PRATICHE " +
				"FROM APPO_CONC_FG_PART_SHAPE " +
				"WHERE FLAG_LAYER = 1 " +
				"GROUP BY SEZIONE, FOGLIO, PARTICELLA");
		
		SQLS.add("CREATE UNIQUE INDEX CAT_VISURE_PART_IDX ON CAT_VISURE_PART" +
				"(SEZIONE, FOGLIO, PARTICELLA)");

		SQLS.add("ALTER TABLE CAT_VISURE_PART " +
				"ADD SHAPE MDSYS.SDO_GEOMETRY");

		SQLS.add("UPDATE CAT_VISURE_PART T " +
				"SET T.SHAPE = (" +
					"SELECT A.SHAPE FROM APPO_CONC_FG_PART_SHAPE A " +
					"WHERE NVL(A.SEZIONE, '-') = NVL(T.SEZIONE, '-') " +
					"AND A.FOGLIO = T.FOGLIO " +
					"AND A.PARTICELLA = T.PARTICELLA " +
					"AND ROWNUM = 1" +
				")");
		
		SQLS.add("INSERT INTO CAT_VISURE_PART " +
				"SELECT TRIM(CATSEZ.SEZIONE) AS SEZIONE, LPAD (TRIM (PAR.FOGLIO), 4, '0') AS FOGLIO, LPAD (TRIM (PAR.PARTICELLA), 5, '0') AS PARTICELLA, 0 AS NUM_PRATICHE, PAR.SHAPE " +
				"FROM &SITI..CATA_SEZIONI CATSEZ, SITIPART PAR " +
				"WHERE NVL(TRIM(CATSEZ.COD_SEZIONE), '-') = NVL(TRIM(PAR.COD_NAZIONALE), '-') " +
				"AND EXISTS (SELECT 1 " +
				    "FROM SITIUIU UIU " +
				    "WHERE NVL(TRIM(UIU.COD_NAZIONALE), '-') = NVL(TRIM(CATSEZ.COD_SEZIONE), '-') " +
				    "AND LPAD (TRIM (UIU.FOGLIO), 4, '0') = LPAD (TRIM (PAR.FOGLIO), 4, '0') " +
				    "AND LPAD (TRIM (UIU.PARTICELLA), 5, '0') = LPAD (TRIM (PAR.PARTICELLA), 5, '0') " +
				    "AND NVL(UIU.DATA_FINE_VAL, TO_DATE('31/12/9999','dd/mm/yyyy')) = TO_DATE('31/12/9999','dd/mm/yyyy')" +
				") " +
				"AND NOT EXISTS (SELECT 1 " +
				    "FROM APPO_CONC_FG_PART_SHAPE S " +
				    "WHERE NVL(TRIM(S.SEZIONE), '-') = NVL(TRIM(CATSEZ.SEZIONE), '-') " +
				    "AND LPAD (TRIM (S.FOGLIO), 4, '0') = LPAD (TRIM (PAR.FOGLIO), 4, '0') " +
				    "AND LPAD (TRIM (S.PARTICELLA), 5, '0') = LPAD (TRIM (PAR.PARTICELLA), 5, '0')" +
				") " +
				"AND NOT EXISTS (SELECT 1 " +
				    "FROM CAT_VISURE_PART CAT " +
				    "WHERE NVL(TRIM(CAT.SEZIONE), '-') = NVL(TRIM(CATSEZ.SEZIONE), '-') " +
				    "AND LPAD (TRIM (CAT.FOGLIO), 4, '0') = LPAD (TRIM (PAR.FOGLIO), 4, '0') " +
				    "AND LPAD (TRIM (CAT.PARTICELLA), 5, '0') = LPAD (TRIM (PAR.PARTICELLA), 5, '0')" +
				") " +
				"AND NVL(TRIM(CATSEZ.COD_SEZIONE), '-') = NVL(TRIM(PAR.COD_NAZIONALE), '-') " +
				"AND PAR.SUB = ' ' " +
				"AND NVL(PAR.DATA_FINE_VAL, TO_DATE('31/12/9999','dd/mm/yyyy')) = TO_DATE('31/12/9999','dd/mm/yyyy')");
		
		SQLS.add("ALTER TABLE CAT_VISURE_PART " +
				"ADD CODUT VARCHAR2(255)");
		
		SQLS.add("UPDATE CAT_VISURE_PART " +
				"SET CODUT = '000'");
		
		SQLS.add("UPDATE CAT_VISURE_PART " +
				"SET CODUT = '005' " +
				"WHERE NUM_PRATICHE BETWEEN 1 AND 5");

		SQLS.add("UPDATE CAT_VISURE_PART " +
				"SET CODUT = '010' " +
				"WHERE NUM_PRATICHE BETWEEN 6 AND 10");

		SQLS.add("UPDATE CAT_VISURE_PART " +
				"SET CODUT = '015' " +
				"WHERE NUM_PRATICHE BETWEEN 11 AND 15");

		SQLS.add("UPDATE CAT_VISURE_PART " +
				"SET CODUT = '020' " +
				"WHERE NUM_PRATICHE BETWEEN 16 AND 20");

		SQLS.add("UPDATE CAT_VISURE_PART " +
				"SET CODUT = '025' " +
				"WHERE NUM_PRATICHE BETWEEN 21 AND 25");

		SQLS.add("UPDATE CAT_VISURE_PART " +
				"SET CODUT = '030' " +
				"WHERE NUM_PRATICHE BETWEEN 26 AND 30");

		SQLS.add("UPDATE CAT_VISURE_PART " +
				"SET CODUT = '040' " +
				"WHERE NUM_PRATICHE BETWEEN 31 AND 40");

		SQLS.add("UPDATE CAT_VISURE_PART " +
				"SET CODUT = '050' " +
				"WHERE NUM_PRATICHE BETWEEN 41 AND 50");

		SQLS.add("UPDATE CAT_VISURE_PART " +
				"SET CODUT = '999' " +
				"WHERE NUM_PRATICHE > 50");
		
		SQLS.add("ALTER TABLE CAT_VISURE_PART ADD SE_ROW_ID NUMBER");
		
		SQLS.add("UPDATE CAT_VISURE_PART SET SE_ROW_ID = ROWNUM");
		
		SQLS.add("INSERT INTO PGT_SQL_LAYER" +
				"(ID," +
				"DES_LAYER," +
				"TIPO_LAYER," +
				"STANDARD," +
				"SQL_LAYER," +
				"NAME_COL_SHAPE," +
				"NAME_COL_ID," +
				"NAME_COL_TEMA," +
				"NAME_COL_TEMA_DESCR," +
				"NAME_TABLE," +
				"NOME_FILE," +
				"SQL_DECO," +
				"PLDECODE," +
				"PLDECODE_DESCR," +
				"PLENCODE," +
				"SHAPE_TYPE," +
				"FLG_DOWNLOAD," +
				"FLG_PUBLISH," +
				"OPACITY," +
				"DESCR_COL_INFO," +
				"NAME_COL_INFO," +
				"FLG_HIDE_INFO," +
				"DATA_ACQUISIZIONE) " +
				"SELECT (SELECT NVL(MAX(ID), 0) + 1 FROM PGT_SQL_LAYER), 'PRATICHE EDILIZIE SU PARTICELLA', 'STAT', 'N', 'SELECT * FROM CAT_VISURE_PART'," +
				"'SHAPE', 'SE_ROW_ID', 'CODUT', 'CODUT', 'CAT_VISURE_PART', NULL," +
				"'SELECT ' || (SELECT NVL(MAX(ID), 0) + 1 FROM PGT_SQL_LAYER) || ' AS ID_LAYER,ID,codut || '' - '' || DESCRIZIONE AS DESCRIZIONE, (SELECT ID FROM PGT_SQL_PALETTE WHERE DESCR = COLORE) AS COLORE, RIEMPIMENTO,SPESSORE,(SELECT ID FROM PGT_SQL_PALETTE WHERE DESCR = COLORELINEA) AS COLORELINEA FROM PGT_SQL_DECO_LAYER WHERE ID_LAYER = ' || (SELECT NVL(MAX(ID), 0) + 1 FROM PGT_SQL_LAYER)," + 
				"'LAYER_TOOLS.GETCODEPRGC', NULL, NULL," + 
				"'POLYGON', 'N', 'Y', '70', NULL, 'NUM_PRATICHE', 'N', NULL " +
				"FROM DUAL " +
				"WHERE NOT EXISTS (SELECT 1 FROM PGT_SQL_LAYER WHERE DES_LAYER = 'PRATICHE EDILIZIE SU PARTICELLA')");
		
		SQLS.add("DELETE FROM PGT_SQL_DECO_LAYER WHERE ID_LAYER = (SELECT ID FROM PGT_SQL_LAYER WHERE DES_LAYER = 'PRATICHE EDILIZIE SU PARTICELLA')");
		
		SQLS.add("DROP SEQUENCE CAT_VISURE_PART_SEQ");
		
		//da qui creazione sequence
		SQLS.add("SELECT (SELECT NVL(MAX(ID), 0) FROM PGT_SQL_DECO_LAYER) - MOD((SELECT NVL(MAX(ID), 0) FROM PGT_SQL_DECO_LAYER), 100) + 100 " +
				"FROM DUAL");
		
		SQLS.add("CREATE SEQUENCE CAT_VISURE_PART_SEQ " +
			   "START WITH ? " +
			   "MAXVALUE 9999999999999999999999999999 " +
			   "MINVALUE 1 " +
			   "NOCYCLE " +
			   "CACHE 20 " +
			   "NOORDER");
		
		//da qui fine creazione sequence
		SQLS.add("INSERT INTO PGT_SQL_DECO_LAYER " +
				"VALUES (CAT_VISURE_PART_SEQ.NEXTVAL, '000', '0', " +
				"(SELECT ID FROM PGT_SQL_LAYER WHERE DES_LAYER = 'PRATICHE EDILIZIE SU PARTICELLA'), " +
				"'snow', '1', '1', 'snow', NULL, 'N')");

		SQLS.add("INSERT INTO PGT_SQL_DECO_LAYER " +
				"VALUES (CAT_VISURE_PART_SEQ.NEXTVAL, '005', '1-5', " +
				"(SELECT ID FROM PGT_SQL_LAYER WHERE DES_LAYER = 'PRATICHE EDILIZIE SU PARTICELLA'), " +
				"'olive', '1', '1', 'olive', NULL, 'N')");

		SQLS.add("INSERT INTO PGT_SQL_DECO_LAYER " +
				"VALUES (CAT_VISURE_PART_SEQ.NEXTVAL, '010', '6-10', " +
				"(SELECT ID FROM PGT_SQL_LAYER WHERE DES_LAYER = 'PRATICHE EDILIZIE SU PARTICELLA'), " +
				"'green', '1', '1', 'green', NULL, 'N')");

		SQLS.add("INSERT INTO PGT_SQL_DECO_LAYER " +
				"VALUES (CAT_VISURE_PART_SEQ.NEXTVAL, '015', '11-15', " +
				"(SELECT ID FROM PGT_SQL_LAYER WHERE DES_LAYER = 'PRATICHE EDILIZIE SU PARTICELLA'), " +
				"'lightgreen', '1', '1', 'lightgreen', NULL, 'N')");

		SQLS.add("INSERT INTO PGT_SQL_DECO_LAYER " +
				"VALUES (CAT_VISURE_PART_SEQ.NEXTVAL, '020', '16-20', " +
				"(SELECT ID FROM PGT_SQL_LAYER WHERE DES_LAYER = 'PRATICHE EDILIZIE SU PARTICELLA'), " +
				"'yellow', '1', '1', 'yellow', NULL, 'N')");

		SQLS.add("INSERT INTO PGT_SQL_DECO_LAYER " +
				"VALUES (CAT_VISURE_PART_SEQ.NEXTVAL, '025', '21-25', " +
				"(SELECT ID FROM PGT_SQL_LAYER WHERE DES_LAYER = 'PRATICHE EDILIZIE SU PARTICELLA'), " +
				"'orange', '1', '1', 'orange', NULL, 'N')");

		SQLS.add("INSERT INTO PGT_SQL_DECO_LAYER " +
				"VALUES (CAT_VISURE_PART_SEQ.NEXTVAL, '030', '26-30', " +
				"(SELECT ID FROM PGT_SQL_LAYER WHERE DES_LAYER = 'PRATICHE EDILIZIE SU PARTICELLA'), " +
				"'red', '1', '1', 'red', NULL, 'N')");

		SQLS.add("INSERT INTO PGT_SQL_DECO_LAYER " +
				"VALUES (CAT_VISURE_PART_SEQ.NEXTVAL, '040', '31-40', " +
				"(SELECT ID FROM PGT_SQL_LAYER WHERE DES_LAYER = 'PRATICHE EDILIZIE SU PARTICELLA'), " +
				"'darkred', '1', '1', 'darkred', NULL, 'N')");

		SQLS.add("INSERT INTO PGT_SQL_DECO_LAYER " +
				"VALUES (CAT_VISURE_PART_SEQ.NEXTVAL, '050', '41-50', " +
				"(SELECT ID FROM PGT_SQL_LAYER WHERE DES_LAYER = 'PRATICHE EDILIZIE SU PARTICELLA'), " +
				"'violet', '1', '1', 'violet', NULL, 'N')");

		SQLS.add("INSERT INTO PGT_SQL_DECO_LAYER " +
				"VALUES (CAT_VISURE_PART_SEQ.NEXTVAL, '999', 'oltre 50', " +
				"(SELECT ID FROM PGT_SQL_LAYER WHERE DES_LAYER = 'PRATICHE EDILIZIE SU PARTICELLA'), " +
				"'purple', '1', '1', 'purple', NULL, 'N')");
	}	
	

	public PraticheEdilizieSuParticella(BeanCommand bc) {
		super(bc);
	}
	
	public PraticheEdilizieSuParticella(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	@Override
	public CommandAck run(Context ctx) throws CommandException {
		CallableStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String nomeSchemaSiti = null;
		String nomeSchemaDiogene = null;
		
		try {
			String belfiore = ctx.getBelfiore();
			Connection conn = ctx.getConnection("DWH_" + belfiore);
			DatabaseMetaData dmd = conn.getMetaData();
			nomeSchemaDiogene = dmd.getUserName();
			Connection connSiti = ctx.getConnection("SITI_" + belfiore);
			DatabaseMetaData dmdSiti = connSiti.getMetaData();
			nomeSchemaSiti = dmdSiti.getUserName();
			
			boolean isSelect = false;
			boolean isCreateSequence = false;
			int idx = 0;
			
			for (String aSql : SQLS) {
				sql = aSql.trim().replace("&SITI.", nomeSchemaSiti);
				
				if (isSelect && sql.equalsIgnoreCase("DROP TABLE CAT_VISURE_PART_TEMP")) {
					isSelect = false;
				}
				if (isCreateSequence && sql.startsWith("INSERT INTO PGT_SQL_DECO_LAYER")) {
					isCreateSequence = false;
				}
				if (!isSelect && !isCreateSequence) {
					if (sql.equalsIgnoreCase("SELECT * FROM APPO_SFP")) {
						isSelect = true;
						log.debug("SQL: " + sql);
						pstmt = conn.prepareCall(sql);
						rs = pstmt.executeQuery();
						int contaQuery = 0;
						while (rs.next()) {
							contaQuery = 0;
							String sezione = rs.getObject("SEZIONE") == null ? null : rs.getString("SEZIONE");
							String foglio = rs.getObject("FOGLIO") == null ? null : rs.getString("FOGLIO");
							String particella = rs.getObject("PARTICELLA") == null ? null : rs.getString("PARTICELLA");
							
							while (true) {
								contaQuery++;
								sql = SQLS.get(idx + contaQuery).trim().replace("&SITI.", nomeSchemaSiti);
								if (sql.indexOf("?") > -1) {
									CallableStatement pstmtInt = conn.prepareCall(sql);
									pstmtInt.setString(1, sezione);
									log.debug("Parametro 1: " + sezione);
									pstmtInt.setString(2, foglio);
									log.debug("Parametro 2: " + foglio);
									pstmtInt.setString(3, particella);
									log.debug("Parametro 3: " + particella);
									if (sql.indexOf("TRIM(?) AS SEZIONE") > -1) {
										pstmtInt.setString(4, sezione);
										log.debug("Parametro 4: " + sezione);
										pstmtInt.setString(5, foglio);
										log.debug("Parametro 5: " + foglio);
										pstmtInt.setString(6, particella);
										log.debug("Parametro 6: " + particella);
										pstmtInt.setString(7, sezione);
										log.debug("Parametro 7: " + sezione);
										pstmtInt.setString(8, foglio);
										log.debug("Parametro 8: " + foglio);
										pstmtInt.setString(9, particella);
										log.debug("Parametro 9: " + particella);
									}
									log.debug("SQL: " + sql);
									pstmtInt.execute();							
									if (pstmtInt != null) {
										pstmtInt.close();
									}
								} else {
									contaQuery--;
									break;
								}
							}																					
						}
						
						idx += contaQuery;
					} else if (sql.startsWith("SELECT (SELECT NVL(MAX(ID), 0) FROM PGT_SQL_DECO_LAYER)")) {
						isCreateSequence = true;
						log.debug("SQL: " + sql);
						pstmt = conn.prepareCall(sql);
						rs = pstmt.executeQuery();
						int contaQuery = 0;
						if (rs.next()) {
							contaQuery = 0;							
							int start = rs.getInt(1);
							
							contaQuery++;
							sql = SQLS.get(idx + contaQuery).trim().replace("&SITI.", nomeSchemaSiti);
							if (sql.indexOf("?") > -1) {
								sql = sql.replace("?", "" + start);
								log.debug("SQL: " + sql);
								CallableStatement pstmtInt = conn.prepareCall(sql);								
								pstmtInt.execute();							
								if (pstmtInt != null) {
									pstmtInt.close();
								}
							} else {
								contaQuery--;
							}												
						}
						idx += contaQuery;
					} else {
						try {
							log.debug("SQL: " + sql);
							pstmt = conn.prepareCall(sql);
							pstmt.execute();
						} catch (Exception e) {
							if (sql.startsWith("DROP ")) {
								log.debug(sql + " non eseguibile; la tabella potrebbe non esistere");
							} else if (sql.startsWith("CREATE TABLE CAT_VISURE_PART_TEMP ")) {
								log.debug(sql + " non eseguibile; la tabella CAT_VISURE_PART potrebbe non esistere");
							} else {
								log.error("Errore:", e);
								ErrorAck ea = new ErrorAck(e.getMessage());
								return (ea);
							}
						}					
					}
				} else {
					continue;
				}

				DbUtils.close(pstmt);
				idx++;
			}			
			
			log.debug("ESECUZIONE COMMIT");
			conn.commit();
			
			//generazione indice spaziale
			sql = "{CALL " + nomeSchemaDiogene + ".CREA_IDX_SPATIAL('CAT_VISURE_PART', 'SHAPE', NULL, 'CAT_VISURE_PART_SDX')}";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			pstmt.execute();
			DbUtils.close(pstmt);
			
			return (new ApplicationAck("CATALOGO PRATICHE EDILIZIE SU PARTICELLA GENERATO CON SUCCESSO"));
		} catch (Exception e) {
			log.error("Errore:", e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			}
			catch (Exception ec) {
			}
		}	
	}

}
