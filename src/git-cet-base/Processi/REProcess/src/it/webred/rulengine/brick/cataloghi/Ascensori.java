package it.webred.rulengine.brick.cataloghi;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

public class Ascensori extends Command implements Rule {
	
	private static final Logger log = Logger.getLogger(Ascensori.class.getName());

	public Ascensori(BeanCommand bc){
		super(bc);
	}//-------------------------------------------------------------------------
	
	public Ascensori(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}//-------------------------------------------------------------------------
	
	@Override
	public CommandAck run(Context ctx) throws CommandException{
		final String NOME_TABELLA = "CAT_ASCENSORI";
		final String NOME_SINONIMO = "CAT_ASCENSORI";
		final String NOME_INDICE = "CAT_ASCENSORI_SDX";
		//final String CODIFICA = "ASC"; //ASCENSORI
		final String PGT_SQL_DECO_LAYER = "PGT_SQL_DECO_LAYER"; //decodifica dei vari layer interni al catalogo
		final String PGT_SQL_LAYER = "PGT_SQL_LAYER"; //censimento del layer
		
		CallableStatement  pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		String belfiore = ctx.getBelfiore();
		Connection conn = ctx.getConnection("DWH_" + belfiore);
		Connection connSiti = ctx.getConnection("SITI_" + belfiore);
		Connection connDbTotale = ctx.getConnection("DBTOTALE_" + belfiore);

		String nomeSchemaSiti = "";
		String nomeSchemaDiogene = "";
		String nomeSchemaDbtotale = "";
		try{
			DatabaseMetaData dmd = conn.getMetaData();
			nomeSchemaDiogene = dmd.getUserName();
			
			DatabaseMetaData dmdSiti = connSiti.getMetaData();
			nomeSchemaSiti = dmdSiti.getUserName();
			
			DatabaseMetaData dmdDbTotale = connDbTotale.getMetaData();
			nomeSchemaDbtotale = dmdDbTotale.getUserName();
		
			System.out.println("SCHEMA DIOGENE: " + nomeSchemaDiogene);
			System.out.println("SCHEMA SITI: " + nomeSchemaSiti);
			System.out.println("SCHEMA DBTOTALE: " + nomeSchemaDbtotale);
		
		String message = "";

		/*
		 * Controllo esistenza riga su PGT_SQL_DECO_LAYER
		 * 
		 * 
		 * Insert into DIOGENE_F704_GIT.PGT_SQL_LAYER
		 *	   (ID, DES_LAYER, TIPO_LAYER, STANDARD, SQL_LAYER, 
		 *	    NAME_COL_SHAPE, NAME_TABLE, NAME_COL_TEMA, NAME_COL_ID, SQL_DECO, 
		 *	    NAME_COL_TEMA_DESCR, PLENCODE, PLDECODE, PLDECODE_DESCR, SHAPE_TYPE, 
		 *	    FLG_DOWNLOAD, FLG_PUBLISH, OPACITY, NAME_COL_INFO, FLG_HIDE_INFO, DATA_ACQUISIZIONE)
		 *	 Values
		 *	   ( (select max(id)+1 FROM &DIOGENEGIT..PGT_SQL_LAYER), 'ASCENSORI SU PARTICELLA', 'ASC', 'Y', 'SELECT * FROM CAT_ASCENSORI', 
		 *	    'SHAPE', 'CAT_ASCENSORI', 'CODUT', 'ID', 'SELECT (select max(id)+1 FROM &DIOGENEGIT..PGT_SQL_LAYER) AS ID_LAYER, ID, codut || '' - '' || DESCRIZIONE AS DESCRIZIONE, (SELECT ID FROM PGT_SQL_PALETTE WHERE DESCR = COLORE) AS COLORE, RIEMPIMENTO,SPESSORE,(SELECT ID FROM PGT_SQL_PALETTE WHERE DESCR = COLORELINEA) AS COLORELINEA FROM PGT_SQL_DECO_LAYER WHERE ID_LAYER = (select max(id)+1 FROM &DIOGENEGIT..PGT_SQL_LAYER) ', 
		 *	    'CODUT', 'LAYER_TOOLS.SETCODEPRGC', 'LAYER_TOOLS.GETCODEPRGC', 'LAYER_TOOLS.GETCODEPRGC_DESCR', 'POLYGON', 
		 *	    'Y', 'Y', '100', 'NUM_ASCENSORI', 'N', TO_DATE('12/15/2016 15:03:00', 'MM/DD/YYYY HH24:MI:SS'));
		 *	COMMIT;
		 * 
		 */
		Long layerId = 0l;
		sql = "select * from " + nomeSchemaDiogene + "." + PGT_SQL_LAYER + " where NAME_TABLE = 'CAT_ASCENSORI' ";
		log.debug("SQL: " + sql);
		pstmt = conn.prepareCall(sql);
		rs = pstmt.executeQuery();
		boolean esisteLayer = false;
		if (rs != null){
			while(rs.next()){
				esisteLayer = true;
				layerId = rs.getLong("ID");
			}
			DbUtils.close(rs);
		}
		DbUtils.close(pstmt);
		
		if (esisteLayer){
			/*
			 * esiste record catalogo censito su PGT_SQL_LAYER
			 */
		}else{
			/*
			 * recupero max id della tab PGT_SQL_LAYER
			 */
			sql = "select max(ID) as MAXID from " + nomeSchemaDiogene + "." + PGT_SQL_LAYER + "  ";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			rs = pstmt.executeQuery();
			Long maxId = 0l;
			if (rs != null){
				while(rs.next()){
					esisteLayer = true;
					maxId = rs.getLong("MAXID");
				}
				DbUtils.close(rs);
			}
			DbUtils.close(pstmt);
			layerId = maxId + 1;
			/*
			 * Faccio insert
			 */
			sql = "Insert into " + nomeSchemaDiogene + "." + PGT_SQL_LAYER + " "
					+ "(ID, DES_LAYER, TIPO_LAYER, STANDARD, SQL_LAYER, "
					+ "NAME_COL_SHAPE, NAME_TABLE, NAME_COL_TEMA, NAME_COL_ID, SQL_DECO, "
					+ "NAME_COL_TEMA_DESCR, PLENCODE, PLDECODE, PLDECODE_DESCR, SHAPE_TYPE, "
					+ "FLG_DOWNLOAD, FLG_PUBLISH, OPACITY, NAME_COL_INFO, FLG_HIDE_INFO, DATA_ACQUISIZIONE)"
					+ "Values"
					+ "( "+layerId+", 'ASCENSORI SU PARTICELLA', 'ASC', 'Y', 'SELECT * FROM CAT_ASCENSORI',"
					+ "'SHAPE', 'CAT_ASCENSORI', 'CODUT', 'ID', 'SELECT "+layerId+" AS ID_LAYER, ID, codut || '' - '' || DESCRIZIONE AS DESCRIZIONE, (SELECT ID FROM PGT_SQL_PALETTE WHERE DESCR = COLORE) AS COLORE, RIEMPIMENTO,SPESSORE,(SELECT ID FROM PGT_SQL_PALETTE WHERE DESCR = COLORELINEA) AS COLORELINEA FROM PGT_SQL_DECO_LAYER WHERE ID_LAYER = "+layerId+" ', "
					+ "'CODUT', 'LAYER_TOOLS.SETCODEPRGC', 'LAYER_TOOLS.GETCODEPRGC', 'LAYER_TOOLS.GETCODEPRGC_DESCR', 'POLYGON', "
					+ "'Y', 'Y', '100', 'NUM_ASCENSORI', 'N', TO_DATE('12/15/2016 15:03:00', 'MM/DD/YYYY HH24:MI:SS')) ";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			boolean okIns00 = pstmt.execute();
			conn.commit();
			DbUtils.close(pstmt);
			/*
			 * recuperiamo ID_LAYER del catalogo da PGT_SQL_LAYER se non esiste già
			 */
			if (layerId != null && layerId>0){
				sql = "select * from " + nomeSchemaDiogene + "." + PGT_SQL_LAYER + " where NAME_TABLE = 'CAT_ASCENSORI' ";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				rs = pstmt.executeQuery();
				if (rs != null){
					while(rs.next()){
						layerId = rs.getLong("ID");
					}
					DbUtils.close(rs);
				}
				DbUtils.close(pstmt);
			}
			
		}
		

		
		/*
		 * controllo esistenza riga su PGT_SQL_DECO_LAYER
		 * 
		 * Insert into &DIOGENEGIT..PGT_SQL_DECO_LAYER
			   (ID, CODUT, DESCRIZIONE, ID_LAYER, COLORE, RIEMPIMENTO, SPESSORE, COLORELINEA, STANDARD)
			 Values
			   ( (select max(id)+1 FROM &DIOGENEGIT..PGT_SQL_DECO_LAYER), 'ASC00', '00-05', (select max(id) FROM &DIOGENEGIT..PGT_SQL_LAYER), 'violet', '1', '1', 'black', 'Y');
				
			Insert into &DIOGENEGIT..PGT_SQL_DECO_LAYER
			   (ID, CODUT, DESCRIZIONE, ID_LAYER, COLORE, RIEMPIMENTO, SPESSORE, COLORELINEA, STANDARD)
			 Values
			   ( (select max(id)+1 FROM &DIOGENEGIT..PGT_SQL_DECO_LAYER), 'ASC01', '06-09', (select max(id) FROM &DIOGENEGIT..PGT_SQL_LAYER), 'pink', '1', '1', 'black', 'Y');
				
			Insert into &DIOGENEGIT..PGT_SQL_DECO_LAYER
			   (ID, CODUT, DESCRIZIONE, ID_LAYER, COLORE, RIEMPIMENTO, SPESSORE, COLORELINEA, STANDARD)
			 Values
			   ( (select max(id)+1 FROM &DIOGENEGIT..PGT_SQL_DECO_LAYER), 'ASC02', '10-', (select max(id) FROM &DIOGENEGIT..PGT_SQL_LAYER), 'lime', '1', '1', 'black', 'Y');
				
			COMMIT;
		 */
		
		sql = "select * from " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + " where ID_LAYER = " + layerId;
		log.debug("SQL: " + sql);
		pstmt = conn.prepareCall(sql);
		rs = pstmt.executeQuery();
		boolean esisteDecoLayer = false;
		if (rs != null){
			while(rs.next()){
				esisteDecoLayer = true;
			}
			DbUtils.close(rs);
		}
		DbUtils.close(pstmt);
		
		if (esisteDecoLayer){
			/*
			 * Catalogo CAT_ASCENSORI già tematizzato
			 */
		}else{
			/*
			 * CAT_ASCENSORI tematizzazione da inserire 3 fasce
			 */
			
			sql = "Insert into " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + " "
					+ "(ID, CODUT, DESCRIZIONE, ID_LAYER, COLORE, RIEMPIMENTO, SPESSORE, COLORELINEA, STANDARD) "
					+ "Values"
					+ "( (select max(id)+1 FROM " + PGT_SQL_DECO_LAYER + "), 'ASC00', '00-05', (select max(id) FROM " + PGT_SQL_DECO_LAYER + "), 'darkseagreen', '1', '1', 'black', 'Y') ";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			boolean okIns00 = pstmt.execute();
			
			sql = "Insert into " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + " "
					+ "(ID, CODUT, DESCRIZIONE, ID_LAYER, COLORE, RIEMPIMENTO, SPESSORE, COLORELINEA, STANDARD) "
					+ "Values"
					+ "( (select max(id)+1 FROM " + PGT_SQL_DECO_LAYER + "), 'ASC01', '06-09', (select max(id) FROM " + PGT_SQL_LAYER + "), 'royalblue', '1', '1', 'black', 'Y') ";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			boolean okIns01 = pstmt.execute();
			
			sql = "Insert into " + nomeSchemaDiogene + "." + PGT_SQL_DECO_LAYER + " "
					+ "(ID, CODUT, DESCRIZIONE, ID_LAYER, COLORE, RIEMPIMENTO, SPESSORE, COLORELINEA, STANDARD) "
					+ "Values"
					+ "( (select max(id)+1 FROM " + PGT_SQL_LAYER + "), 'ASC02', '10-', (select max(id) FROM " + PGT_SQL_LAYER + "), 'deeppink', '1', '1', 'black', 'Y') ";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			boolean okIns02 = pstmt.execute();
			
			conn.commit();
			DbUtils.close(pstmt);
			
		}
		
			/*
			 * Controllo l'esistenza della tabella
			 */
			sql = "select * from ALL_CATALOG where owner='" + nomeSchemaDiogene + "' and table_type = 'TABLE' and table_name = '" + NOME_TABELLA + "'";
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
				/*
				 * Droppiamo la tabella 
				 */
				sql = "drop table "  + nomeSchemaDiogene + "." + NOME_TABELLA;
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				boolean okDrop = pstmt.execute();
				DbUtils.close(pstmt);
				message = "Svuotamento " + NOME_TABELLA + " eseguito con successo";
				log.debug(message);
				
			}

				/*
				 * Recupero l'elenco degli ascensori per foglio e particella:
				 * nel caso di piu protocolli DOCFA presenti in una determinata 
				 * coppia di foglio + particella si prenderà il valore del numero 
				 * degli ascensori maggiore
				 */
				
				/**
				 * 
				    
				    select DU.FOGLIO, DU.NUMERO, MAX(substr(DD.riga_dettaglio, 76, 2)) NUM_ASCENSORI 
                    from doc_docfa_1_0 DD
                    left join docfa_uiu DU on  DU.PROTOCOLLO_REG = dd.protocollo AND DU.FORNITURA = DD.DATA
                    where substr(DD.riga_dettaglio,8,1)='P'
                    and substr(DD.riga_dettaglio,17,1)='C'
                    GROUP BY DU.FOGLIO, DU.NUMERO
                    ORDER BY DU.FOGLIO, DU.NUMERO
				    
                    select DISTINCT DU.FOGLIO, DU.NUMERO, DD.protocollo, DD.data, substr(DD.riga_dettaglio, 76, 2) NUM_ASCENSORI 
                    from doc_docfa_1_0 DD
                    left join docfa_uiu DU on  DU.PROTOCOLLO_REG = dd.protocollo AND DU.FORNITURA = DD.DATA
                    where substr(DD.riga_dettaglio,8,1)='P'
                    and substr(DD.riga_dettaglio,17,1)='C'
                    ORDER BY DU.FOGLIO, DU.NUMERO, DD.PROTOCOLLO
                    
                    drop table DIOGENE_F704_GIT.CAT_ASCENSORI;
                    
					create table CAT_ASCENSORI as                    
					select DU.FOGLIO, DU.NUMERO, MAX(substr(DD.riga_dettaglio, 76, 2)) NUM_ASCENSORI, CAST(NULL AS MDSYS.SDO_GEOMETRY) AS SHAPE 
                    from doc_docfa_1_0 DD
                    left join docfa_uiu DU on  DU.PROTOCOLLO_REG = dd.protocollo AND DU.FORNITURA = DD.DATA
                    where substr(DD.riga_dettaglio,8,1)='P'
                    and substr(DD.riga_dettaglio,17,1)='C'
                    GROUP BY DU.FOGLIO, DU.NUMERO
                    ORDER BY DU.FOGLIO, DU.NUMERO;
					
				 */

				sql = "create table "+nomeSchemaDiogene+"."+NOME_TABELLA+" as "
						+ "select CAST(NULL AS NUMBER) AS ID, CAST(NULL AS VARCHAR(255)) AS CODUT, DU.FOGLIO, DU.NUMERO as PARTICELLA, MAX(substr(DD.riga_dettaglio, 76, 2)) NUM_ASCENSORI, CAST(NULL AS MDSYS.SDO_GEOMETRY) AS SHAPE " +
					"from "+nomeSchemaDiogene+".doc_docfa_1_0 DD " + 
					"left join "+nomeSchemaDiogene+".docfa_uiu DU on  DU.PROTOCOLLO_REG = dd.protocollo AND DU.FORNITURA = DD.DATA " +
					"where substr(DD.riga_dettaglio,8,1)='P' " + 
					"and substr(DD.riga_dettaglio,17,1)='C' " + 
					"GROUP BY DU.FOGLIO, DU.NUMERO " +
					"ORDER BY DU.FOGLIO, DU.NUMERO ";
				
				log.debug("SQL: " + sql);

				pstmt = conn.prepareCall(sql);
				boolean okCreate = pstmt.execute();
				DbUtils.close(pstmt);
				/*
				 * Valorizziamo colonna ID e CODUT
				 */
				
				sql = "UPDATE " + nomeSchemaDiogene + "." + NOME_TABELLA + " SET ID = SEQ_TEMPO.nextval ";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				boolean okUpd = pstmt.execute();
				
				sql = "UPDATE " + nomeSchemaDiogene + "." + NOME_TABELLA + " SET CODUT = 'ASC00' where TO_NUMBER(NUM_ASCENSORI) <= 5 ";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				boolean okUpd00 = pstmt.execute();
				
				sql = "UPDATE " + nomeSchemaDiogene + "." + NOME_TABELLA + " SET CODUT = 'ASC01' where TO_NUMBER(NUM_ASCENSORI) > 5 AND TO_NUMBER(NUM_ASCENSORI) <= 9 ";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				boolean okUpd01 = pstmt.execute();
				
				sql = "UPDATE " + nomeSchemaDiogene + "." + NOME_TABELLA + " SET CODUT = 'ASC02' where TO_NUMBER(NUM_ASCENSORI) >= 10 ";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				boolean okUpd02 = pstmt.execute();
				
				sql = "DELETE FROM " + nomeSchemaDiogene + "." + NOME_TABELLA + " where TO_NUMBER(NUM_ASCENSORI) < 1 ";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				boolean okUpdate = pstmt.execute();

				sql = "update "+nomeSchemaDiogene+"."+NOME_TABELLA+" S1 set shape = "
						+ "(select shape from "+nomeSchemaDiogene+".SITIPART s2 "
						+ "where S1.FOGLIO = LPAD(TO_CHAR(s2.foglio), '4', '0' ) "
						+ "and TO_CHAR(S1.particella) = s2.particella "
						+ "AND s2.data_fine_val = TO_DATE('31/12/9999', 'dd/MM/yyyy')) ";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				boolean okUpd03 = pstmt.execute();
				conn.commit();
				DbUtils.close(pstmt);
				/*
				 * Creo i sinonimi - se non esistono - della NOME_TABELLA in DBTOTALE e SITI
				 */
				/*
				 * Controllo esistenza sinonimo in DBTOTALE
				 */
				sql = "select * from ALL_CATALOG where table_type = 'SYNONYM' and owner ='" + nomeSchemaDbtotale + "' and table_name='" + NOME_SINONIMO + "'";
				log.debug("SQL: " + sql);
				pstmt = connDbTotale.prepareCall(sql);
				rs = pstmt.executeQuery();
				boolean esiteSynSuDbtotale = false;
				if (rs != null){
					while(rs.next()){
						esiteSynSuDbtotale = true;
					}
					DbUtils.close(rs);
				}
				DbUtils.close(pstmt);
				if (!esiteSynSuDbtotale){
					sql = "CREATE SYNONYM " + nomeSchemaDbtotale + "." + NOME_SINONIMO + " FOR " + nomeSchemaDiogene +  "." + NOME_TABELLA + " ";
					log.debug("SQL: " + sql);
					pstmt = connDbTotale.prepareCall(sql);
					pstmt.execute();
					DbUtils.close(pstmt);
					message = "Sinonimo " + NOME_SINONIMO + " creato con successo";
					log.debug(message);
				}else{
					message = "Sinonimo " + NOME_SINONIMO + " esistente";
					log.debug(message);
				}
				/*
				 * Controllo esistenza sinonimo in SITI
				 */
				sql = "select * from ALL_CATALOG where table_type = 'SYNONYM' and owner ='" + nomeSchemaSiti + "' and table_name='" + NOME_SINONIMO + "'";
				log.debug("SQL: " + sql);
				pstmt = connSiti.prepareCall(sql);
				rs = pstmt.executeQuery();
				boolean esisteSynSuSiti = false;
				if (rs != null){
					while(rs.next()){
						esisteSynSuSiti = true;
					}
					DbUtils.close(rs);
				}
				DbUtils.close(pstmt);
				if (!esisteSynSuSiti){
					sql = "CREATE SYNONYM " + nomeSchemaSiti + "." + NOME_SINONIMO + " FOR " + nomeSchemaDiogene +  "." + NOME_TABELLA + " ";
					log.debug("SQL: " + sql);
					pstmt = connSiti.prepareCall(sql);
					pstmt.execute();
					DbUtils.close(pstmt);
					message = "Sinonimo " + NOME_SINONIMO + " creato con successo";
					log.debug(message);
				}else{
					message = "Sinonimo " + NOME_SINONIMO + " esistente";
					log.debug(message);
				}
					
				//			STRUCT st = (oracle.sql.STRUCT) rs.getObject("shape");
				
				/*
				 * Creazione degli indici spaziali su DIOGENE {call DIOGENE.crea_idx_spatial()}
				 * EXECUTE crea_idx_spatial('CAT_ASCENSORI', 'SHAPE', null, 'CAT_ASCENSORI_SDX');
				 */

				sql = "{call " + nomeSchemaDiogene + ".crea_idx_spatial('" + NOME_TABELLA + "', 'SHAPE', null, '" + NOME_INDICE + "')}";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				boolean creaIdx = pstmt.execute();
				DbUtils.close(pstmt);

				return (new ApplicationAck("LAYER CARTOGRAFICO ASCENSORI SU PARTICELLA GENERATO CON SUCCESSO"));
			
		}catch (Exception e)
		{
			log.error("Errore:", e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
		finally{
			try {
				conn.close();
				connSiti.close();
				connDbTotale.close();
			} catch (SQLException e) {
				log.error(e);
			}
		}

	}
}
