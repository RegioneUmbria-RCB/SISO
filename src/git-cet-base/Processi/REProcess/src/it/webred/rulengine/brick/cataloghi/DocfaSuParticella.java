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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

public class DocfaSuParticella extends Command implements Rule
{
	private static final Logger log = Logger.getLogger(DocfaSuParticella.class.getName());


	public DocfaSuParticella(BeanCommand bc){
		super(bc);
	}
	
	public DocfaSuParticella(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	@Override
	public CommandAck run(Context ctx)
		throws CommandException
	{
		final String NOME_TABELLA = "CAT_DOCFA_SU_PARTICELLA";
		final String NOME_INDICE = "CAT_DOCFA_SU_PARTICELLA_SDX";
		final String CODIFICA = "DSP"; //Docfa su particella
		CallableStatement  pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		String belfiore = ctx.getBelfiore();
		Connection conn = ctx.getConnection("DWH_" + belfiore);
		
		String nomeSchemaDiogene = "";

		try{
			DatabaseMetaData dmd = conn.getMetaData();
			nomeSchemaDiogene = dmd.getUserName();
			
			System.out.println("SCHEMA DIOGENE: " + nomeSchemaDiogene);
		
		String message = "";

		/*
			 * Controllo l'esistenza della tabella nello schema DIOGENE
			 */
			sql = "select * from ALL_CATALOG " +
			"where owner='" + nomeSchemaDiogene + "' and table_type = 'TABLE' and table_name = '" + NOME_TABELLA + "'";
			log.debug(sql);
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
				 * Se la tabella esiste la droppo per poi ricrearla
				 */
				sql = "drop table "  + nomeSchemaDiogene + "." + NOME_TABELLA;
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				boolean troncata = pstmt.execute();
				DbUtils.close(pstmt);
				message = "Tabella "  + nomeSchemaDiogene + "." + NOME_TABELLA + " eliminata con successo";
				log.debug(message);
				
			}
			/*
			 * Creo la tabella 
			 */
			sql = "create table "  + nomeSchemaDiogene + "." + NOME_TABELLA + " as "
					+ "select se_row_id, c.*, sp.shape, '-         ' as codifica from "
					+ "(select foglio, numero, count(*) occorrenze from "
					+ "(select distinct foglio, numero, protocollo_reg "
					+ "from "  + nomeSchemaDiogene + ".docfa_uiu "
					+ "where tipo_operazione <> 'S' "
					+ "order by foglio, numero) "
					+ "group by foglio, numero) c "
					+ "inner join "  + nomeSchemaDiogene + ".sitipart sp "
					+ "on c.FOGLIO = lpad(to_char(sp.foglio), 4, '0') "
					+ "and c.NUMERO = sp.particella "
					+ "and sp.data_fine_val = to_date('31/12/9999', 'dd/MM/yyyy' ) ";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			boolean generata = pstmt.execute();
			DbUtils.close(pstmt);
			message = "Tabella "  + nomeSchemaDiogene + "." + NOME_TABELLA + " creata con successo";
			log.debug(message);
			/*
			 * Verifico la presenza del catalogo nella tabella PGT_SQL_LAYER
			 */
			sql = "select ID from "  + nomeSchemaDiogene + ".PGT_SQL_LAYER where name_table = '" + NOME_TABELLA + "' ";
			log.debug("SQL: " + sql);
			pstmt = conn.prepareCall(sql);
			rs = pstmt.executeQuery();
			Long codCat = null;
			if (rs != null){
				while(rs.next()){
					 codCat = rs.getLong("ID");
				}
			}
			BigDecimal seqCat = new BigDecimal( codCat!=null?codCat.longValue():0l );
			DbUtils.close(rs);
			DbUtils.close(pstmt);
			log.debug("Catalogo censito in PGT_SQL_LAYER, ID: " + seqCat.longValue());
			if (seqCat != null && seqCat.compareTo( new BigDecimal(0) )>0){
				/*
				 * Catalogo già censito: elimino le tematizzazioni perchè ora potrebbero 
				 * essere cambiate
				 */
				sql = "delete from " + nomeSchemaDiogene + ".PGT_SQL_DECO_LAYER where ID_LAYER = " + seqCat.longValue();
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				boolean okDelete = pstmt.execute();
				DbUtils.close(pstmt);
				
			}else{
				/*
				 * Se non è presente il catalogo in PGT_SQL_LAYER devo inserirlo
				 * dopo aver recuperato l'attuale MAX ID
				 * 
				 * Insert into PGT_SQL_LAYER
				   (ID, DES_LAYER, TIPO_LAYER, STANDARD, SQL_LAYER, 
				    NAME_COL_SHAPE, NAME_TABLE, NAME_COL_TEMA, NAME_COL_ID, SQL_DECO, 
				    NAME_COL_TEMA_DESCR, PLENCODE, PLDECODE, PLDECODE_DESCR, SHAPE_TYPE, 
				    FLG_DOWNLOAD, FLG_PUBLISH, OPACITY, NAME_COL_INFO)
				 Values
				   (5, 'AREE ALLUVIONATE', 'GENERICO', 'Y', '', 
				    'SHAPE', 'CAT_AREE_ALLUVIONATE', 'AREA', 'SE_ROW_ID', 'SELECT 5 AS ID_LAYER,ID,codut || '' - '' || DESCRIZIONE AS DESCRIZIONE, (SELECT ID FROM PGT_SQL_PALETTE WHERE DESCR = COLORE) AS COLORE, RIEMPIMENTO,SPESSORE,(SELECT ID FROM PGT_SQL_PALETTE WHERE DESCR = COLORELINEA) AS COLORELINEA FROM PGT_SQL_DECO_LAYER WHERE ID_LAYER = 5', 
				    'AREA', 'LAYER_TOOLS.SETCODEPRGC', 'LAYER_TOOLS.GETCODEPRGC', 'LAYER_TOOLS.GETCODEPRGC_DESCR', 'POLYGON', 
				    'Y', 'Y', '100', '');
				 */
				
				sql = "select max(ID) as MAXID from "  + nomeSchemaDiogene + ".PGT_SQL_LAYER ";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				rs = pstmt.executeQuery();
				if (rs != null){
						while(rs.next()){
							Long maxCod = rs.getLong("MAXID");
							BigDecimal maxId = new BigDecimal( maxCod!=null?maxCod.longValue():0l );
							seqCat = maxId.add( new BigDecimal(1) ) ;
						}
					}
				DbUtils.close(rs);
				DbUtils.close(pstmt);
				
				sql = "Insert into PGT_SQL_LAYER "
						+ "(ID, DES_LAYER, TIPO_LAYER, STANDARD, SQL_LAYER, "
						+ "NAME_COL_SHAPE, NAME_TABLE, NAME_COL_TEMA, NAME_COL_ID, SQL_DECO, "
						+ "NAME_COL_TEMA_DESCR, PLENCODE, PLDECODE, PLDECODE_DESCR, SHAPE_TYPE, "
						+ "FLG_DOWNLOAD, FLG_PUBLISH, OPACITY, NAME_COL_INFO, FLG_HIDE_INFO) "
						+ "Values "
						+ "("+seqCat.longValue()+", 'STA05: DOCFA SU PARTICELLA', 'GENERICO', 'Y', 'SELECT * FROM " + NOME_TABELLA + "', "
						+ "'SHAPE', '" + NOME_TABELLA + "', 'CODIFICA', 'SE_ROW_ID', 'SELECT "+seqCat.longValue()+" AS ID_LAYER,ID,codut || '' - '' || DESCRIZIONE AS DESCRIZIONE, (SELECT ID FROM PGT_SQL_PALETTE WHERE DESCR = COLORE) AS COLORE, RIEMPIMENTO,SPESSORE,(SELECT ID FROM PGT_SQL_PALETTE WHERE DESCR = COLORELINEA) AS COLORELINEA FROM PGT_SQL_DECO_LAYER WHERE ID_LAYER = "+seqCat.longValue()+" ', "
						+ "'CODIFICA', 'LAYER_TOOLS.SETCODEPRGC', 'LAYER_TOOLS.GETCODEPRGC', 'LAYER_TOOLS.GETCODEPRGC_DESCR', 'POLYGON', "
						+ "'Y', 'Y', '100', 'OCCORRENZE', 'Y') ";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				boolean inseritoPgt = pstmt.execute();
				DbUtils.close(pstmt);
				message = "Catalogo censito in "  + nomeSchemaDiogene + ".PGT_SQL_LAYER con successo";
				log.debug(message);
				
			}
			/*
			 * Genero le tematizzazioni con riferimento id = seqCat dividendo 
			 * per 10 il max delle occorrenze
			 */
			sql = "select max(occorrenze) as maxocco from "  + nomeSchemaDiogene + "." + NOME_TABELLA;

			log.debug("SQL: " + sql);
			double maxOcc = 0d;
			pstmt = conn.prepareCall(sql);
			rs = pstmt.executeQuery();
			if (rs != null){
					while(rs.next()){
						String occorrenze = rs.getString("maxocco");
						
						if (occorrenze != null && !occorrenze.equalsIgnoreCase("")){
							Double currentOcc = Double.valueOf(occorrenze);
							if (currentOcc > maxOcc)
								maxOcc = currentOcc;
						}
					}
				DbUtils.close(rs);
			}
			DbUtils.close(pstmt);
			log.debug("MAX OCC.: " + maxOcc);
			int range = 0;
			int rangeRnd = 0;
			int maxInt = (int)maxOcc;
			rangeRnd = maxInt / 10;
			range = (int)rangeRnd;

			/*
			 * Elimino i vecchi record della tematizzazione in PGT_SQL_DECO_LAYER 
			 * con il corrente idCat per poi rigenerare la codifica 
			 */
			Integer[] intValoreOcc = new Integer[11];
			String[] strCodiceOcc = new String[11];
			if (seqCat != null && seqCat.compareTo( new BigDecimal(0) )>0){
					sql = "delete from " + nomeSchemaDiogene + ".PGT_SQL_DECO_LAYER where ID_LAYER = " + seqCat.longValue();
					log.debug("SQL: " + sql);
					pstmt = conn.prepareCall(sql);
					boolean okDelete = pstmt.execute();
					DbUtils.close(pstmt);
					/*
					 * Invece di recuperare il max ID in PGT_SQL_DECO_LAYER inserisco 
					 * la (select max(ID)+1 from PGT_SQL_DECO_LAYER) direttamente 
					 * nella insert
					 */				

					/*
					 * Inizio Decodifica e insert dentro la PGT_SQL_DECO_LAYER 
					 * con le 11 tematizzazioni
					 */
					int start = 0;
					String colore = "darksalmon";
					/*
					 * Il numero delle classificazioni è pari a 10 (come sopra) 
					 * e l'11 servirà a gestire i valori oltre max range
					 */
					for (int i=0; i<11; i++){
						int end = start + range;
						String desc = "";
						
						strCodiceOcc[i] = CODIFICA+(i == 10?i:"0"+i);
						switch (i) {
						case 0:
							desc = "0-" + end;
							colore = "silver";
							intValoreOcc[i] = 0;
							break;
						case 1:
							desc = (start+1) + "-" + end;
							colore = "grey";
							intValoreOcc[i] = (start);
							break;
						case 2:
							desc = (start+1) + "-" + end;
							colore = "black";
							intValoreOcc[i] = (start);
							break;
						case 3:
							desc = (start+1) + "-" + end;
							colore = "burlywood";
							intValoreOcc[i] = (start);
							break;
						case 4:
							desc = (start+1) + "-" + end;
							colore = "darksalmon";
							intValoreOcc[i] = (start);
							break;
						case 5:
							desc = (start+1) + "-" + end;
							colore = "coral";
							intValoreOcc[i] = (start);
							break;
						case 6:
							desc = (start+1) + "-" + end;
							colore = "yellow";
							intValoreOcc[i] = (start);
							break;
						case 7:
							desc = (start+1) + "-" + end;
							colore = "lime";
							intValoreOcc[i] = (start);
							break;
						case 8:
							desc = (start+1) + "-" + end;
							colore = "aqua";
							intValoreOcc[i] = (start);
							break;
						case 9:
							desc = (start+1) + "-" + end;
							colore = "blue";
							intValoreOcc[i] = (start);
							break;
						case 10:
							desc = (start+1) + "-";
							colore = "red";
							intValoreOcc[i] = (start);
							break;

						default:

							break;
						}

						sql = "Insert into " + nomeSchemaDiogene + ".PGT_SQL_DECO_LAYER " +
								"(ID, CODUT, DESCRIZIONE, ID_LAYER, COLORE, RIEMPIMENTO, SPESSORE, COLORELINEA, OCCORRENZE, STANDARD) " +
								"values " +
								"( (select max(ID)+1 from " + nomeSchemaDiogene + ".PGT_SQL_DECO_LAYER) , '" + strCodiceOcc[i] + "', '" + desc + "', " + seqCat.longValue() + ", '" + colore + "', '1', '1', 'black', 1, 'Y') ";
						pstmt = conn.prepareCall(sql);
						message = "Esito di " + sql + ": ";
						boolean okInsert = pstmt.execute();
						DbUtils.close(pstmt);
						message += (!okInsert)?"OK":"KO";
						log.debug(message);
						/*
						 * Aggiorno la riga della tabella CAT_DOCFA_SU_PARTICELLA
						 * in base al numero delle occorrenze
						 * UPDATE CAT_DOCFA_SU_PARTIELLA SET CODIFICA = 'DSPxx' WHERE OCCORRENZE >= START AND OCCORRENZE < END;
						 */
						sql = "update " + nomeSchemaDiogene + "." + NOME_TABELLA + " " +
								"set CODIFICA = '" + strCodiceOcc[i] + "' " +
								"where OCCORRENZE >= " + (start+1) + " " +
								"and OCCORRENZE <= " + end;
						pstmt = conn.prepareCall(sql);
						message = "Esito di " + sql + ": ";
						boolean okUpdate = pstmt.execute();
						DbUtils.close(pstmt);
						message += (!okInsert)?"OK":"KO";
						log.debug(message);
						
						start = end;
						
					}
				}

				log.debug("Inizio COMMIT delle insert in " + NOME_TABELLA);
				conn.commit();
				/*
				 * Controllo esistenza indice spaziale
				 * select INDEX_NAME from USER_SDO_INDEX_INFO
					WHERE 
					TABLE_OWNER = 'DIOGENE_F704_GIT'
					AND TABLE_NAME = 'CAT_DOCFA_SU_PARTICELLA'
				 */
				sql = "select INDEX_NAME from USER_SDO_INDEX_INFO "
						+ "WHERE "
						+ "TABLE_OWNER = '"  + nomeSchemaDiogene + "' "
						+ "AND TABLE_NAME = '" + NOME_TABELLA + "' ";

				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				rs = pstmt.executeQuery();
				boolean esisteIS = false;
				if (rs != null){
						while(rs.next()){
							esisteIS = true;
						}
					}
				DbUtils.close(rs);
				DbUtils.close(pstmt);
				log.debug("Esistenza indice spaziale: " + esisteIS);
				if (esisteIS){
					/*
					 * Droppo indice spaziale
					 */
					sql = "drop index "  + nomeSchemaDiogene + "." + NOME_INDICE + " ";
					log.debug("SQL: " + sql);
					pstmt = conn.prepareCall(sql);
					boolean eliminatoIS = pstmt.execute();
					DbUtils.close(pstmt);
					message = "Indice spaziale "  + nomeSchemaDiogene + "." + NOME_INDICE + " eliminato con successo";
					log.debug(message);
				}
				/*
				 * Genero indice spaziale
				 * EXECUTE DIOGENE_F704_GIT.crea_idx_spatial('CAT_DOCFA_SU_PARTICELLA','SHAPE',null,'CAT_DOCFA_SU_PARTICELLA_SDX');
				 */
				sql = "{call " + nomeSchemaDiogene + ".crea_idx_spatial('" + NOME_TABELLA + "', 'SHAPE', null, '" + NOME_INDICE + "')}";
				log.debug("SQL: " + sql);
				pstmt = conn.prepareCall(sql);
				boolean generatoIS = pstmt.execute();
				DbUtils.close(pstmt);
				message = "Indice Spaziale "  + nomeSchemaDiogene + "." + NOME_INDICE + " creato con successo";
				log.debug(message);							
				

				return (new ApplicationAck("SQL ESEGUITO CON SUCCESSO"));

		}
		catch (Exception e)
		{
			log.error("Errore esecuzione sql:" + sql, e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} finally {
			try
			{
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			}
			catch (Exception ec)
			{
			}

		}
	}
	

}
