package it.webred.rulengine.brick.cataloghi;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;


public class CertificazioniEnergetiche extends Command implements Rule{

	private static final Logger log = Logger.getLogger(CertificazioniEnergetiche.class.getName());

	public CertificazioniEnergetiche(BeanCommand bc){
		super(bc);
	}//-------------------------------------------------------------------------

	public CertificazioniEnergetiche(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}//-------------------------------------------------------------------------

	@Override
	public CommandAck run(Context ctx) throws CommandException {
		final String NOME_TABELLA = "CAT_CENED";
		final String NOME_TABELLA_TMP0 = "CAT_CENED_TMP0";
		final String NOME_TABELLA_TMP1 = "CAT_CENED_TMP1";
		final String NOME_TABELLA_TMP2 = "CAT_CENED_TMP2";
		final String NOME_INDICE = "CAT_CENED_SDX";
 
		CallableStatement  pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		String belfiore = ctx.getBelfiore();
		Connection conn = ctx.getConnection("DWH_" + belfiore);
		String nomeSchemaDiogene = "";

		String message = "";
		
		try{
			DatabaseMetaData dmd = conn.getMetaData();
			nomeSchemaDiogene = dmd.getUserName();
			System.out.println("SCHEMA DIOGENE: " + nomeSchemaDiogene);
			/*
			 * Controllo l'esistenza della tabella CAT_CENED_TMP0
			 */
			sql = "select * from ALL_CATALOG where owner='" + nomeSchemaDiogene + "' and table_type = 'TABLE' and table_name = '" + NOME_TABELLA_TMP0 + "'";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			rs = pstmt.executeQuery();
			boolean esisteTab = false;
			if (rs != null){
				while(rs.next()){
					esisteTab = true;
				}
				DbUtils.close(rs);
			}
			DbUtils.close(pstmt);
			if (esisteTab){
				message = "Tabella " + NOME_TABELLA_TMP0 + " esistente: droppare";
				log.debug(message);
				/*
				 * Elimino la tabella 
				 */
				sql = "drop table "  + nomeSchemaDiogene + "." + NOME_TABELLA_TMP0 + " ";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				boolean okDrop = pstmt.execute();
				DbUtils.close(pstmt);
				message = "Tabella " + NOME_TABELLA + " droppata con successo";
				log.debug(message);
			}
			/*
			 * Controllo l'esistenza della tabella CAT_CENED_tmp1
			 */
			sql = "select * from ALL_CATALOG where owner='" + nomeSchemaDiogene + "' and table_type = 'TABLE' and table_name = '" + NOME_TABELLA_TMP1 + "'";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			rs = pstmt.executeQuery();
			esisteTab = false;
			if (rs != null){
				while(rs.next()){
					esisteTab = true;
				}
				DbUtils.close(rs);
			}
			DbUtils.close(pstmt);
			if (esisteTab){
				message = "Tabella " + NOME_TABELLA_TMP1 + " esistente: droppare";
				log.debug(message);
				/*
				 * Elimino la tabella 
				 */
				sql = "drop table "  + nomeSchemaDiogene + "." + NOME_TABELLA_TMP1 + " ";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				boolean okDrop = pstmt.execute();
				DbUtils.close(pstmt);
				message = "Tabella " + NOME_TABELLA + " droppata con successo";
				log.debug(message);
			}
			/*
			 * Controllo l'esistenza della tabella CAT_CENED_TMP2
			 */
			sql = "select * from ALL_CATALOG where owner='" + nomeSchemaDiogene + "' and table_type = 'TABLE' and table_name = '" + NOME_TABELLA_TMP2 + "'";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			rs = pstmt.executeQuery();
			esisteTab = false;
			if (rs != null){
				while(rs.next()){
					esisteTab = true;
				}
				DbUtils.close(rs);
			}
			DbUtils.close(pstmt);
			if (esisteTab){
				message = "Tabella " + NOME_TABELLA_TMP2 + " esistente: droppare";
				log.debug(message);
				/*
				 * Elimino la tabella 
				 */
				sql = "drop table "  + nomeSchemaDiogene + "." + NOME_TABELLA_TMP2 + " ";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				boolean okDrop = pstmt.execute();
				DbUtils.close(pstmt);
				message = "Tabella " + NOME_TABELLA_TMP2 + " droppata con successo";
				log.debug(message);
				
			}
			/*
			 * Controllo l'esistenza della tabella CAT_CENED
			 */
			sql = "select * from ALL_CATALOG where owner='" + nomeSchemaDiogene + "' and table_type = 'TABLE' and table_name = '" + NOME_TABELLA + "'";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			rs = pstmt.executeQuery();
			esisteTab = false;
			if (rs != null){
				while(rs.next()){
					esisteTab = true;
				}
				DbUtils.close(rs);
			}
			DbUtils.close(pstmt);
			if (esisteTab){
				message = "Tabella " + NOME_TABELLA + " esistente: droppare";
				log.debug(message);
				/*
				 * Elimino la tabella 
				 */
				sql = "drop table "  + nomeSchemaDiogene + "." + NOME_TABELLA + " ";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				boolean okDrop = pstmt.execute();
				DbUtils.close(pstmt);
				message = "Tabella " + NOME_TABELLA + " droppata con successo";
				log.debug(message);
				
			}
			/*
			 * Controllo esistenza in PGT_SQL_LAYER
			 */
			/*
			 * ritorna ID che userò per andare in PGT_SQL_DECO_LAYER e tematizzare
			 * per classe energetica
			 */
			sql = "select * from " + nomeSchemaDiogene + ".pgt_sql_layer where name_table = '" + NOME_TABELLA + "' ";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			rs = pstmt.executeQuery();
			String seqLayer = "";
			if (rs != null){
				while(rs.next()){
					seqLayer = rs.getString("ID");
				}
				DbUtils.close(rs);
			}
			DbUtils.close(pstmt);
			
			
			if (seqLayer!=null && !seqLayer.trim().equalsIgnoreCase("") && new Long(seqLayer).longValue()>0){
				
			}else{
				//layer non presente in pgt_sql_layer: fare insert
				Long maxIdLayer = 0l;
				sql = "SELECT MAX(ID) as MAXID FROM " + nomeSchemaDiogene + ".pgt_sql_layer ";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				rs = pstmt.executeQuery();
				String maxSeqLayer = "";
				if (rs != null){
					while(rs.next()){
						maxSeqLayer = rs.getString("MAXID");
					}
					DbUtils.close(rs);
				}
				DbUtils.close(pstmt);
				
				if (maxSeqLayer != null && !maxSeqLayer.trim().equalsIgnoreCase("")){
					
				}else{
					maxSeqLayer = "0";
				}
				maxIdLayer = new Long(maxSeqLayer);
				maxIdLayer++;
				
				/*
				 Insert into PGT_SQL_LAYER
				   (ID, DES_LAYER, TIPO_LAYER, STANDARD, SQL_LAYER, 
				    NAME_COL_SHAPE, NAME_TABLE, NAME_COL_TEMA, NAME_COL_ID, SQL_DECO, 
				    NAME_COL_TEMA_DESCR, NOME_FILE, PLENCODE, PLDECODE, PLDECODE_DESCR, 
				    SHAPE_TYPE, FLG_DOWNLOAD, FLG_PUBLISH, OPACITY, NAME_COL_INFO, 
				    FLG_HIDE_INFO, DATA_ACQUISIZIONE)
				 Values
				   ( 14 , 'CERTIFICAZIONI ENERGETICHE EDIFICI', 'CENED', 'Y', 'SELECT * FROM CAT_CENED', 
				    'SHAPE', 'CAT_CENED', 'CLASSE_ENERGETICA', 'ID', 'SELECT 14 AS ID_LAYER,ID,codut || '' - '' || DESCRIZIONE AS DESCRIZIONE, (SELECT ID FROM PGT_SQL_PALETTE WHERE DESCR = COLORE) AS COLORE, RIEMPIMENTO,SPESSORE,(SELECT ID FROM PGT_SQL_PALETTE WHERE DESCR = COLORELINEA) AS COLORELINEA FROM PGT_SQL_DECO_LAYER WHERE ID_LAYER = 14', 
				    'CLASSE ENERGETICA', 'CENED_Certificazioni_ENergetiche_Edifici.txt', 'LAYER_TOOLS.SETCODEPRGC', 'LAYER_TOOLS.GETCODEPRGC', 'LAYER_TOOLS.GETCODEPRGC_DESCR', 
				    'POLYGON', 'Y', 'Y', '70', 'CLASSE_ENERGETICA', 
				    'N', TO_DATE('02/15/2016 15:03:00', 'MM/DD/YYYY HH24:MI:SS'));
				 */
//				sql = "Insert into " + nomeSchemaDiogene + ".PGT_SQL_LAYER "
//						+ "(ID, DES_LAYER, TIPO_LAYER, STANDARD, SQL_LAYER, "
//						+ "NAME_COL_SHAPE, NAME_TABLE, NAME_COL_TEMA, NAME_COL_ID, SQL_DECO, "
//						+ "NAME_COL_TEMA_DESCR, NOME_FILE, PLENCODE, PLDECODE, PLDECODE_DESCR, "
//						+ "SHAPE_TYPE, FLG_DOWNLOAD, FLG_PUBLISH, OPACITY, NAME_COL_INFO, "
//						+ "FLG_HIDE_INFO, DATA_ACQUISIZIONE) "
//						+ "Values "
//						+ "( "+maxIdLayer+" , 'CERTIFICAZIONI ENERGETICHE EDIFICI', 'CENED', 'Y', 'SELECT * FROM CAT_CENED', "
//						+ "'SHAPE', 'CAT_CENED', 'CLASSE_ENERGETICA', 'ID', 'SELECT "+maxIdLayer+" AS ID_LAYER,ID,codut || '' - '' || DESCRIZIONE AS DESCRIZIONE, (SELECT ID FROM PGT_SQL_PALETTE WHERE DESCR = COLORE) AS COLORE, RIEMPIMENTO,SPESSORE,(SELECT ID FROM PGT_SQL_PALETTE WHERE DESCR = COLORELINEA) AS COLORELINEA FROM PGT_SQL_DECO_LAYER WHERE ID_LAYER = "+maxIdLayer+"', "
//						+ "'CLASSE ENERGETICA', 'CENED_Certificazioni_ENergetiche_Edifici.txt', 'LAYER_TOOLS.SETCODEPRGC', 'LAYER_TOOLS.GETCODEPRGC', 'LAYER_TOOLS.GETCODEPRGC_DESCR', "
//						+ "'POLYGON', 'Y', 'Y', '70', 'CLASSE_ENERGETICA', "
//						+ "'N', TO_DATE('02/15/2016 15:03:00', 'MM/DD/YYYY HH24:MI:SS')); ";
				
				sql = "Insert into " + nomeSchemaDiogene + ".PGT_SQL_LAYER "
						+ "(ID, DES_LAYER, TIPO_LAYER, STANDARD, SQL_LAYER, "
						+ "NAME_COL_SHAPE, NAME_TABLE, NAME_COL_TEMA, NAME_COL_ID, SQL_DECO, "
						+ "NAME_COL_TEMA_DESCR, NOME_FILE, PLENCODE, PLDECODE, PLDECODE_DESCR, "
						+ "SHAPE_TYPE, FLG_DOWNLOAD, FLG_PUBLISH, OPACITY, NAME_COL_INFO, "
						+ "FLG_HIDE_INFO, DATA_ACQUISIZIONE) "
						+ "Values "
						+ "(?, ?, ?, 'Y', ?, "
						+ "'SHAPE', ?, ?, ?, ?, "
						+ "?, ?, 'LAYER_TOOLS.SETCODEPRGC', 'LAYER_TOOLS.GETCODEPRGC', 'LAYER_TOOLS.GETCODEPRGC_DESCR', "
						+ "'POLYGON', 'Y', 'Y', '70', ?, "
						+ "'N', ?) ";
				
				
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				pstmt.setInt(1, maxIdLayer.intValue());
				pstmt.setString(2, "CERTIFICAZIONI ENERGETICHE EDIFICI");
				pstmt.setString(3, "CENED");
				pstmt.setString(4, "SELECT * FROM CAT_CENED");
				//fine parametri prima riga
				pstmt.setString(5, NOME_TABELLA);
				pstmt.setString(6, "CLASSE_ENERGETICA");
				pstmt.setString(7, "ID");
				pstmt.setString(8, "SELECT "+maxIdLayer+" AS ID_LAYER, ID, codut || ' - ' || DESCRIZIONE AS DESCRIZIONE, (SELECT ID FROM PGT_SQL_PALETTE WHERE DESCR = COLORE) AS COLORE, RIEMPIMENTO,SPESSORE,(SELECT ID FROM PGT_SQL_PALETTE WHERE DESCR = COLORELINEA) AS COLORELINEA FROM PGT_SQL_DECO_LAYER WHERE ID_LAYER = " + maxIdLayer);
				//fine parametri seconda riga
				pstmt.setString(9, "CLASSE ENERGETICA");
				pstmt.setString(10, "CENED_Certificazioni_ENergetiche_Edifici.txt");
				//fine parametri terza riga
				pstmt.setString(11, "CLASSE_ENERGETICA");
				//fine parametri quarta riga
				pstmt.setDate(12, new Date(System.currentTimeMillis()));
				//fine parametri quinta riga
				boolean okIns = pstmt.execute();
				conn.commit();
				DbUtils.close(pstmt);
				
				seqLayer = maxIdLayer.toString();
			}
		
			
/*
 * ITER GENERAZIONE CATALOGO
 
 drop table CAT_CENED_TMP0;

create table CAT_CENED_TMP0 as
select distinct to_number_all (cened.foglio) FOGLIO, LPAD (cened.particella, 5, '0') PARTICELLA, CENED.CLASSE_ENERGETICA CLASSE_ENERGETICA  
from dati_tec_fabbr_cert_ener cened 
where cened.belfiore = 'F704'  
order by FOGLIO, PARTICELLA;

drop table CAT_CENED_TMP1;

create table CAT_CENED_TMP1 as
select to_number_all (cened.foglio) FOGLIO, LPAD (cened.particella, 5, '0') PARTICELLA, count(DISTINCT classe_energetica) CNT, 'MIX' AS CLS  FROM 
                dati_tec_fabbr_cert_ener cened 
                group by foglio, particella
                having count(DISTINCT classe_energetica) > 1
               order by foglio, particella;
               
               
UPDATE CAT_CENED_TMP0 s0 SET s0.classe_energetica = 'MIX' 
WHERE (S0.foglio, S0.PARTICELLA) IN (SELECT FOGLIO, PARTICELLA FROM CAT_CENED_TMP1);
commit;

DROP TABLE CAT_CENED_TMP2;

CREATE TABLE CAT_CENED_TMP2 AS 
SELECT DISTINCT TMP0.FOGLIO, TMP0.PARTICELLA, TMP0.CLASSE_ENERGETICA 
FROM CAT_CENED_TMP0 TMP0;

DROP TABLE CAT_CENED;

CREATE TABLE CAT_CENED AS
select SEQ_CENED.nextval ID, TMP.* 
from (
SELECT TMP2.FOGLIO, tmp2.PARTICELLA, TMP2.CLASSE_ENERGETICA, SS.SHAPE 
FROM CAT_CENED_TMP2 TMP2 
inner join sitisuolo ss on TMP2.FOGLIO = ss.foglio and TMP2.PARTICELLA = ss.particella
order by TMP2.foglio, TMP2.particella) tmp
 
 */
			
			message = "Tabella " + NOME_TABELLA_TMP0 + " inesistente: inizio creazione ";
			log.debug(message);
			sql = "create table " + nomeSchemaDiogene + "." + NOME_TABELLA_TMP0 + " as "
					+ "select distinct "
					+ "to_number_all (cened.foglio) FOGLIO, LPAD (cened.particella, 5, '0') PARTICELLA, CENED.CLASSE_ENERGETICA CLASSE_ENERGETICA "
					+ "from dati_tec_fabbr_cert_ener cened "
					+ "where cened.belfiore = '"+belfiore+"' "
					+ "order by foglio, particella ";
						
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			boolean okCre = pstmt.execute();
			DbUtils.close(pstmt);
			message = "Tabella " + NOME_TABELLA_TMP0 + " inesistente: fine creazione ";
			log.debug(message);

			message = "Tabella " + NOME_TABELLA_TMP1 + " inesistente: inizio creazione ";
			log.debug(message);
			sql = "create table " + nomeSchemaDiogene + "." + NOME_TABELLA_TMP1 + " as "
					+ "select to_number_all (cened.foglio) FOGLIO, LPAD (cened.particella, 5, '0') PARTICELLA, count(DISTINCT classe_energetica) CNT, 'MIX' AS CLS "
					+ "from dati_tec_fabbr_cert_ener cened "
					+ "where cened.belfiore = '"+belfiore+"' "
					+ "group by foglio, particella "
					+ "having count(DISTINCT classe_energetica) > 1 "
					+ "order by foglio, particella ";

			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			okCre = pstmt.execute();
			DbUtils.close(pstmt);
			message = "Tabella " + NOME_TABELLA_TMP1 + " inesistente: fine creazione ";
			log.debug(message);
			
			sql = "UPDATE " + nomeSchemaDiogene + "." + NOME_TABELLA_TMP0 + " TMP0  "
					+ "SET "
					+ "TMP0.CLASSE_ENERGETICA = 'MIX' "
					+ "WHERE (TMP0.FOGLIO, TMP0.PARTICELLA) IN ( SELECT FOGLIO, PARTICELLA FROM " + nomeSchemaDiogene + "." + NOME_TABELLA_TMP1 + " ) ";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			boolean okUpdMIX = pstmt.execute();
			conn.commit();
			DbUtils.close(pstmt);
			
			message = "Tabella " + NOME_TABELLA_TMP2 + " inesistente: inizio creazione ";
			log.debug(message);
			sql = "create table " + nomeSchemaDiogene + "." + NOME_TABELLA_TMP2 + " as "
					+ "SELECT DISTINCT TMP0.FOGLIO, TMP0.PARTICELLA, TMP0.CLASSE_ENERGETICA "
					+ "FROM CAT_CENED_TMP0 TMP0 ";
						
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			okCre = pstmt.execute();
			DbUtils.close(pstmt);
			message = "Tabella " + NOME_TABELLA_TMP2 + " inesistente: fine creazione ";
			log.debug(message);
			
			message = "Tabella " + NOME_TABELLA + " inesistente: inizio creazione ";
			log.debug(message);
			sql = "create table " + nomeSchemaDiogene + "." + NOME_TABELLA + " as "
					+ "select " + nomeSchemaDiogene + ".SEQ_CENED.nextval ID, TMP.*  "
					+ "FROM ( SELECT TMP2.FOGLIO, tmp2.PARTICELLA, TMP2.CLASSE_ENERGETICA, SS.SHAPE "
					+ "FROM CAT_CENED_TMP2 TMP2 "
					+ "inner join sitisuolo ss on TMP2.FOGLIO = ss.foglio and TMP2.PARTICELLA = ss.particella "
					+ "order by TMP2.foglio, TMP2.particella ) tmp ";
						
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			okCre = pstmt.execute();
			DbUtils.close(pstmt);
			message = "Tabella " + NOME_TABELLA + " inesistente: fine creazione ";
			log.debug(message);
			/*
			 * Creazione degli indici spaziali {call SITI.crea_idx_spatial()}
			 */
			log.debug("Inizio creazione indici spaziali: " + NOME_INDICE);
			sql = "{call " + nomeSchemaDiogene + ".crea_idx_spatial('" + NOME_TABELLA + "', 'SHAPE', null, '" + NOME_INDICE + "')}";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			boolean creaIdx = pstmt.execute();
			DbUtils.close(pstmt);
			/*
			 * Eliminiamo righe doppie che hanno lo stesso id

			 DELETE FROM 
				   cat_cened A
				WHERE 
				  a.rowid > 
				   ANY (
				     SELECT B.rowid FROM cat_cened B WHERE A.id = B.id --AND --  A.col2 = B.col2 
				     );
			 */
			sql = "DELETE FROM " + nomeSchemaDiogene + "." + NOME_TABELLA + " A "
					+ "WHERE "
					+ "a.rowid > ANY ( "
					+ "SELECT B.rowid FROM cat_cened B WHERE A.id = B.id "
					+ ") ";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			boolean okSdoppia = pstmt.execute();
			conn.commit();
			DbUtils.close(pstmt);
			/*
			 * Aggiorniamo la colonna CLASSE_ENERGETICA con il valore MIX per quelle
			 * combinazione di foglio e particella a cui corrispondono classi energetiche
			 * diverse 
			 */
			/*
			 * 
			 select foglio, particella, count(DISTINCT classe_energetica) FROM 
		        cat_cened 
		        group by foglio, particella
		        having count(DISTINCT classe_energetica) > 1
		       order by foglio, particella;
			 * 
			 */
			/*
			 * Update la colonna CLASSE_ENERGETICA ove null con spazio
			 */
			sql = "UPDATE " + nomeSchemaDiogene + "." + NOME_TABELLA + "  "
					+ "SET "
					+ "CLASSE_ENERGETICA = '-' "
					+ "WHERE CLASSE_ENERGETICA IS NULL ";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			boolean okUpd = pstmt.execute();
			conn.commit();
			DbUtils.close(pstmt);
			/*
			 * Update la colonna CLASSE_ENERGETICA sostituendo il carattere + con 
			 * la scritta piu
			 */
			sql = "UPDATE " + nomeSchemaDiogene + "." + NOME_TABELLA + "  "
					+ "SET "
					+ "CLASSE_ENERGETICA = REPLACE(CLASSE_ENERGETICA, '+', 'piu' ) "
					+ "WHERE CLASSE_ENERGETICA LIKE '%+%' ";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			boolean okUpdPiu = pstmt.execute();
			conn.commit();
			DbUtils.close(pstmt);
			/*
			 *  
			 * Verifica della presenza della tematizzazione in PGT_SQL_DECO_LAYER 
			 * per questo layer ed in caso negativo aggiungere le attuali classi
			 * energetiche esistenti [A, A+, B, C, D, E, F, G, spazio]
			 */
			Boolean doInsert = false;
			if (seqLayer!=null && new Long(seqLayer).longValue()>0){
				
				/*
				 * Conto i temi presenti in PGT_SQL_DECO_LAYER
				 */
				sql = "SELECT  count(*) CNT "
						+ "FROM " + nomeSchemaDiogene + ".PGT_SQL_DECO_LAYER "
						+ "WHERE ID_LAYER = " + seqLayer;
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				rs = pstmt.executeQuery();
				Integer cntLayerDeco = 0;
				if (rs != null){
					while(rs.next()){
						cntLayerDeco = rs.getInt("CNT");
					}
					DbUtils.close(rs);
				}
				DbUtils.close(pstmt);
				/*
				 * Conto i temi presenti nel catalogo
				 */
				sql = "SELECT  count(DISTINCT CLASSE_ENERGETICA) CNT "
						+ "FROM " + nomeSchemaDiogene + "." + NOME_TABELLA + " ";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				rs = pstmt.executeQuery();
				Integer cntLayerThema = 0;
				if (rs != null){
					while(rs.next()){
						cntLayerThema = rs.getInt("CNT");
					}
					DbUtils.close(rs);
				}
				DbUtils.close(pstmt);
				
				if (cntLayerDeco!=null && cntLayerThema!=null && cntLayerThema.intValue()==cntLayerDeco.intValue()){
					/*
					 * verifichiamo se sono tutte ed in caso eliminiamo tutto per 
					 * poi inserirle nuovamente 
					 */

				}else{
					/*
					 * Eliminiamo tutte le tematizzazioni presenti perchè incomplete
					 * sotto le inseriremo nuovamente tutte
					 */
					doInsert = true;
					sql = "DELETE FROM " + nomeSchemaDiogene + ".PGT_SQL_DECO_LAYER  "
							+ "WHERE ID_LAYER = " + seqLayer;
					log.debug("SQL: " + sql);
					pstmt = conn.prepareCall(sql);
					boolean okDel = pstmt.execute();
					conn.commit();
					DbUtils.close(pstmt);
				}
				
			}
			
			if (doInsert){
				/*
				 * Recuperiamo id max 
				 */
				sql = "SELECT MAX(ID) as MAXID FROM " + nomeSchemaDiogene + ".pgt_sql_deco_layer ";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				rs = pstmt.executeQuery();
				String maxSeqDecoLayer = "";
				if (rs != null){
					while(rs.next()){
						maxSeqDecoLayer = rs.getString("MAXID");
					}
					DbUtils.close(rs);
				}
				DbUtils.close(pstmt);
				/*
				 * Recuperiamo le classi energetiche presenti nel catalogo
				 */
				sql = "SELECT DISTINCT CLASSE_ENERGETICA "
						+ "FROM " + nomeSchemaDiogene + "." + NOME_TABELLA + " "
						+ "order by CLASSE_ENERGETICA ";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				rs = pstmt.executeQuery();
				ArrayList<String> aryClassiEnergetiche = new ArrayList<String>();
				if (rs != null){
					while(rs.next()){
						String classeEnergetica = rs.getString("CLASSE_ENERGETICA");
						aryClassiEnergetiche.add(classeEnergetica);
					}
					DbUtils.close(rs);
				}
				DbUtils.close(pstmt);
				/*
				 * Recuperiamo i colori disponibili
				 */
				sql = "SELECT DISTINCT DESCR "
						+ "FROM " + nomeSchemaDiogene + ".PGT_SQL_PALETTE ";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				rs = pstmt.executeQuery();
				ArrayList<String> aryColori = new ArrayList<String>();
				if (rs != null){
					while(rs.next()){
						String colore = rs.getString("DESCR");
						aryColori.add(colore);
					}
					DbUtils.close(rs);
				}
				DbUtils.close(pstmt);
				
				if (aryClassiEnergetiche != null && aryClassiEnergetiche.size()>0){
					Integer maxIdDecoLayer = new Integer(maxSeqDecoLayer);
					for (int ind=0; ind<aryClassiEnergetiche.size(); ind++){
						String classeEner = aryClassiEnergetiche.get(ind);
						String colore = aryColori.get(ind);
						maxIdDecoLayer++;
						sql = "Insert into " + nomeSchemaDiogene + ".PGT_SQL_DECO_LAYER "
								+ "(ID, CODUT, DESCRIZIONE, ID_LAYER, COLORE, RIEMPIMENTO, SPESSORE, COLORELINEA, STANDARD) "
								+ "Values "
								+ "(?, ?, ?, ?, ?, '1', '1', 'black', 'Y')";
						log.debug("SQL: " + sql);
						log.debug("param1: " + maxIdDecoLayer);
						log.debug("param2: " + classeEner);
						log.debug("param3: " + "CLS ENERGETICA " + classeEner);
						log.debug("param4: " + seqLayer);
						log.debug("param5: " + colore);

						pstmt = conn.prepareCall(sql);
						pstmt.setLong(1, maxIdDecoLayer);
						pstmt.setString(2, classeEner);
						if (classeEner != null && classeEner.trim().equalsIgnoreCase("-"))
							pstmt.setString(3, "NO CLS ENERGETICA ");
						else
							pstmt.setString(3, "CLS ENERGETICA " + classeEner);
						pstmt.setLong(4, new Long(seqLayer));
						pstmt.setString(5, colore);
						boolean okIns = pstmt.execute();
						conn.commit();
						DbUtils.close(pstmt);
						
					}
				}else{
					/*
					 * In teoria non dovrebbe succedere che non ci siano le classi energetiche a questo punto
					 */
					
				}
				
			}
			
			/*
			 
			Insert into PGT_SQL_DECO_LAYER
			   (ID, CODUT, DESCRIZIONE, ID_LAYER, COLORE, RIEMPIMENTO, SPESSORE, COLORELINEA, OCCORRENZE, STANDARD)
			 Values
			   (76, ' ', 'CLS ENERGETICA ASSENTE', 15, 'orange', '1', '1', 'black', 1, 'Y');
			Insert into PGT_SQL_DECO_LAYER
			   (ID, CODUT, DESCRIZIONE, ID_LAYER, COLORE, RIEMPIMENTO, SPESSORE, COLORELINEA, OCCORRENZE, STANDARD)
			 Values
			   (77, 'A', 'CLS ENERGETICA A', 15, 'yellow', '1', '1', 'black', 1, 'Y');
			Insert into PGT_SQL_DECO_LAYER
			   (ID, CODUT, DESCRIZIONE, ID_LAYER, COLORE, RIEMPIMENTO, SPESSORE, COLORELINEA, OCCORRENZE, STANDARD)
			 Values
			   (78, 'A+', 'CLS ENERGETICA A+', 15, 'green', '1', '1', 'black', 1, 'Y');
			Insert into PGT_SQL_DECO_LAYER
			   (ID, CODUT, DESCRIZIONE, ID_LAYER, COLORE, RIEMPIMENTO, SPESSORE, COLORELINEA, OCCORRENZE, STANDARD)
			 Values
			   (79, 'B', 'CLS ENERGETICA B', 15, 'gray', '1', '1', 'black', 1, 'Y');
			Insert into PGT_SQL_DECO_LAYER
			   (ID, CODUT, DESCRIZIONE, ID_LAYER, COLORE, RIEMPIMENTO, SPESSORE, COLORELINEA, OCCORRENZE, STANDARD)
			 Values
			   (80, 'C', 'CLS ENERGETICA C', 15, 'blue', '1', '1', 'black', 1, 'Y');
			Insert into PGT_SQL_DECO_LAYER
			   (ID, CODUT, DESCRIZIONE, ID_LAYER, COLORE, RIEMPIMENTO, SPESSORE, COLORELINEA, OCCORRENZE, STANDARD)
			 Values
			   (81, 'D', 'CLS ENERGETICA D', 15, 'red', '1', '1', 'black', 1, 'Y');
			 Insert into PGT_SQL_DECO_LAYER
			   (ID, CODUT, DESCRIZIONE, ID_LAYER, COLORE, RIEMPIMENTO, SPESSORE, COLORELINEA, OCCORRENZE, STANDARD)
			 Values
			   (82, 'E', 'CLS ENERGETICA E', 15, 'pink', '1', '1', 'black', 1, 'Y');
			Insert into PGT_SQL_DECO_LAYER
			   (ID, CODUT, DESCRIZIONE, ID_LAYER, COLORE, RIEMPIMENTO, SPESSORE, COLORELINEA, OCCORRENZE, STANDARD)
			 Values
			   (83, 'F', 'CLS ENERGETICA F', 15, 'violet', '1', '1', 'black', 1, 'Y');
			Insert into PGT_SQL_DECO_LAYER
			   (ID, CODUT, DESCRIZIONE, ID_LAYER, COLORE, RIEMPIMENTO, SPESSORE, COLORELINEA, OCCORRENZE, STANDARD)
			 Values
			   (84, 'G', 'CLS ENERGETICA G', 15, 'lime', '1', '1', 'black', 1, 'Y');
			 
			 */
			
			/*
			 * ELIMINO LE TABELLE TMP CREATE PER GENERARE IL CATALOGO
			 */
			/*
			 * Controllo l'esistenza della tabella CAT_CENED_TMP0
			 */
			sql = "select * from ALL_CATALOG where owner='" + nomeSchemaDiogene + "' and table_type = 'TABLE' and table_name = '" + NOME_TABELLA_TMP0 + "'";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			rs = pstmt.executeQuery();
			esisteTab = false;
			if (rs != null){
				while(rs.next()){
					esisteTab = true;
				}
				DbUtils.close(rs);
			}
			DbUtils.close(pstmt);
			if (esisteTab){
				message = "Tabella " + NOME_TABELLA_TMP0 + " esistente: droppare";
				log.debug(message);
				/*
				 * Elimino la tabella 
				 */
				sql = "drop table "  + nomeSchemaDiogene + "." + NOME_TABELLA_TMP0 + " ";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				boolean okDrop = pstmt.execute();
				DbUtils.close(pstmt);
				message = "Tabella " + NOME_TABELLA + " droppata con successo";
				log.debug(message);
			}
			/*
			 * Controllo l'esistenza della tabella CAT_CENED_tmp1
			 */
			sql = "select * from ALL_CATALOG where owner='" + nomeSchemaDiogene + "' and table_type = 'TABLE' and table_name = '" + NOME_TABELLA_TMP1 + "'";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			rs = pstmt.executeQuery();
			esisteTab = false;
			if (rs != null){
				while(rs.next()){
					esisteTab = true;
				}
				DbUtils.close(rs);
			}
			DbUtils.close(pstmt);
			if (esisteTab){
				message = "Tabella " + NOME_TABELLA_TMP1 + " esistente: droppare";
				log.debug(message);
				/*
				 * Elimino la tabella 
				 */
				sql = "drop table "  + nomeSchemaDiogene + "." + NOME_TABELLA_TMP1 + " ";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				boolean okDrop = pstmt.execute();
				DbUtils.close(pstmt);
				message = "Tabella " + NOME_TABELLA + " droppata con successo";
				log.debug(message);
			}
			/*
			 * Controllo l'esistenza della tabella CAT_CENED_TMP2
			 */
			sql = "select * from ALL_CATALOG where owner='" + nomeSchemaDiogene + "' and table_type = 'TABLE' and table_name = '" + NOME_TABELLA_TMP2 + "'";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			rs = pstmt.executeQuery();
			esisteTab = false;
			if (rs != null){
				while(rs.next()){
					esisteTab = true;
				}
				DbUtils.close(rs);
			}
			DbUtils.close(pstmt);
			if (esisteTab){
				message = "Tabella " + NOME_TABELLA_TMP2 + " esistente: droppare";
				log.debug(message);
				/*
				 * Elimino la tabella 
				 */
				sql = "drop table "  + nomeSchemaDiogene + "." + NOME_TABELLA_TMP2 + " ";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				boolean okDrop = pstmt.execute();
				DbUtils.close(pstmt);
				message = "Tabella " + NOME_TABELLA_TMP2 + " droppata con successo";
				log.debug(message);
				
			}
		}catch (Exception e) {
			log.error("Errore:", e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
		finally{
			try {
				conn.close();
				//connSiti.close();
				//connDbTotale.close();
			} catch (SQLException e) {
				log.error(e);
			}
		}
		
		return (new ApplicationAck("CATALOGO " + NOME_TABELLA + " GEENRATO CON SUCCESSO"));
	
	}//-------------------------------------------------------------------------

}
