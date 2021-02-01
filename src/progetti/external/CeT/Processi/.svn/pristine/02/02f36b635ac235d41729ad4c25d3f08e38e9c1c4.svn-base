package it.webred.rulengine.brick.cataloghi;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.DbCommand;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import oracle.sql.STRUCT;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

/**
 * Regola che in base ad una stringa esegue un SQL.
 * Se il sql è stato esguito la regola ritorna un CommandAck con un messaggio di conferma
 * Invece se non è stato possibile eseguire il sql la regola ritorna un ErrorAck
 * con il messaggio di errore.
 * 
 * Possono essere passati dei parametri all'sql che verranno sostituiti ai ? del PreparedStatement
 *
 * @author sisani
 * @version $Revision: 1.7 $ $Date: 2010/09/28 12:12:27 $
 */
public class ZoneCensuarie extends Command implements Rule
{
	private static final Logger log = Logger.getLogger(ValoreMedioRenditaVano.class.getName());
	/**
	 * @param bc
	 */
	public ZoneCensuarie(BeanCommand bc){
		super(bc);
	}
	
	public ZoneCensuarie(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}
	
	@Override
	public CommandAck run(Context ctx)
		throws CommandException
	{
		final String NOME_TABELLA = "CAT_ZONE_CENSUARIE";
		final String NOME_SINONIMO = "CAT_ZONE_CENSUARIE";
		final String NOME_INDICE = "CAT_ZONE_CENSUARIE_SDX";
		final String CODIFICA = "ZC"; //Zona Censuaria
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
		}catch (Exception e)
		{
			log.error("Errore:", e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		}
		finally{
			try {
				conn.close();
				connDbTotale.close();
			} catch (SQLException e) {
				log.error(e);
			}
		}
		String message = "";
		try
		{
			/*
			 * Controllo l'esistenza della tabella
			 */
			sql = "select * from ALL_CATALOG " +
			"where owner='" + nomeSchemaSiti + "' and table_type = 'TABLE' and table_name = '" + NOME_TABELLA + "'";
			log.debug("SQL: " + sql);
			pstmt = connSiti.prepareCall(sql);
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
				 * Svuoto la tabella 
				 */
				sql = "truncate table "  + nomeSchemaSiti + "." + NOME_TABELLA;
				log.debug("SQL: " + sql);
				pstmt = connSiti.prepareCall(sql);
				boolean okTruncate = pstmt.execute();
				DbUtils.close(pstmt);
				message = "Svuotamento " + NOME_TABELLA + " eseguito con successo";
				log.debug(message);
				/*
				 * Creo i sinonimi - se non esistono - della NOME_TABELLA in DBTOTALE e DIOGENE
				 */
				sql = "select * from ALL_CATALOG where table_type = 'SYNONYM' and owner ='" + nomeSchemaDiogene + "' and table_name='" + NOME_SINONIMO + "'";
				log.debug("SQL: " + sql);
				pstmt = connSiti.prepareCall(sql);
				rs = pstmt.executeQuery();
				boolean esisteSynSuDiogene = false;
				if (rs != null){
					while(rs.next()){
						esisteSynSuDiogene = true;
					}
					DbUtils.close(rs);
				}
				DbUtils.close(pstmt);
				if (!esisteSynSuDiogene){
					sql = "CREATE SYNONYM " + nomeSchemaDiogene + "." + NOME_SINONIMO + " FOR " + nomeSchemaSiti +  "." + NOME_TABELLA + " ";
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

				sql = "select * from ALL_CATALOG where table_type = 'SYNONYM' and owner ='" + nomeSchemaDbtotale + "' and table_name='" + NOME_SINONIMO + "'";
				log.debug("SQL: " + sql);
				pstmt = connSiti.prepareCall(sql);
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
					sql = "CREATE SYNONYM " + nomeSchemaDbtotale + "." + NOME_SINONIMO + " FOR " + nomeSchemaSiti +  "." + NOME_TABELLA + " ";
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
				/*
				 * Verifico l'esistenza delle colonne della NOME_TABELLA e in 
				 * caso negativo altero la tabella
				 */
				sql = "select * from ALL_TAB_COLUMNS where column_name = 'FOGLIO' and table_name = '" + NOME_TABELLA + "' and owner = '" + nomeSchemaSiti + "' ";
				log.debug("SQL: " + sql);
				pstmt = connSiti.prepareCall(sql);
				rs = pstmt.executeQuery();
				boolean esisteColFo = false;
				if (rs != null){
					while(rs.next()){
						esisteColFo = true;
					}
					DbUtils.close(rs);
				}
				DbUtils.close(pstmt);
				if (!esisteColFo){
					sql = "alter table " + nomeSchemaSiti + "." + NOME_TABELLA + " add (FOGLIO  NUMBER) ";
					log.debug("SQL: " + sql);
					pstmt = connSiti.prepareCall(sql);
					pstmt.execute();
					DbUtils.close(pstmt);
				}
				sql = "select * from ALL_TAB_COLUMNS where column_name = 'PARTICELLA' and table_name = '" + NOME_TABELLA + "' and owner = '" + nomeSchemaSiti + "' ";
				log.debug("SQL: " + sql);
				pstmt = connSiti.prepareCall(sql);
				rs = pstmt.executeQuery();
				boolean esisteColPa = false;
				if (rs != null){
					while(rs.next()){
						esisteColPa = true;
					}
					DbUtils.close(rs);
				}
				DbUtils.close(pstmt);
				if (!esisteColPa){
					sql = "alter table " + nomeSchemaSiti + "." + NOME_TABELLA + " add (PARTICELLA  CHAR(5)) ";
					log.debug("SQL: " + sql);
					pstmt = connSiti.prepareCall(sql);
					pstmt.execute();
					DbUtils.close(pstmt);
				}
				sql = "select * from ALL_TAB_COLUMNS where column_name = 'SUB' and table_name = '" + NOME_TABELLA + "' and owner = '" + nomeSchemaSiti + "' ";
				log.debug("SQL: " + sql);
				pstmt = connSiti.prepareCall(sql);
				rs = pstmt.executeQuery();
				boolean esisteColSu = false;
				if (rs != null){
					while(rs.next()){
						esisteColSu = true;
					}
					DbUtils.close(rs);
				}
				DbUtils.close(pstmt);
				if (!esisteColSu){
					sql = "alter table " + nomeSchemaSiti + "." + NOME_TABELLA + " add (SUB  CHAR(3)) ";
					log.debug("SQL: " + sql);
					pstmt = connSiti.prepareCall(sql);
					pstmt.execute();
					DbUtils.close(pstmt);
				}
				/*
				 * Recupero le zone non nulle direttamente dalla tabella sitiuiu 
				 */
				sql = "select distinct zona from sitiuiu " +
					"where data_fine_val = to_date('99991231','yyyymmdd') " +
					"and zona is not null " +
					"order by zona ";
				log.debug("SQL: " + sql);

				Hashtable<String, String> htZone = new Hashtable<String, String>();
				pstmt = connSiti.prepareCall(sql);
				rs = pstmt.executeQuery();
				if (rs != null){
					while(rs.next()){
						String zona = rs.getString("zona");
						if (zona != null && !zona.trim().equalsIgnoreCase("")){
							htZone.put(zona.trim(), zona.trim());
						}
					}
					htZone.put("MIX", "MIX");
				}
				/*
				 * Ordinamento alfabetico delle zone censuarie 
				 */
				String[] aryZone = new String[htZone.size()];
				int index = 0;
				Enumeration<String> e = htZone.elements();
				while(e.hasMoreElements()){
					aryZone[index] = e.nextElement();
					index++;
				}
				Arrays.sort(aryZone);
				/*
				 * cav_001
				 */
				sql = "select distinct foglio, particella, sub, zona from " +
						"sitiuiu " +
						"where " +
						"data_fine_val=to_date('99991231','yyyymmdd') " +
						"and zona is not null " +
						"order by foglio, particella ";

				log.debug("SQL: " + sql);
				
				ArrayList<String[]> alZc = new ArrayList<String[]>();
				Hashtable<String, String> htZc = new Hashtable<String, String>();
				pstmt = connSiti.prepareCall(sql);
				rs = pstmt.executeQuery();
		
				if (rs != null){
					while(rs.next()){
						/*
						 * determino le zone censuarie
						 */
						String foglio = rs.getString("foglio");
						String particella = rs.getString("particella");
						String dettaglioParticella = rs.getString("sub");
						String zona = rs.getString("zona");
						
						if (zona != null && !zona.equalsIgnoreCase("")){
							String fog = (foglio!=null && !foglio.trim().equalsIgnoreCase(""))?foglio.trim():" ";
							String par = (particella!=null && !particella.trim().equalsIgnoreCase(""))?particella.trim():" ";
							String dp = (dettaglioParticella!=null && !dettaglioParticella.trim().equalsIgnoreCase(""))?dettaglioParticella.trim():" ";
							String zon = (zona!=null && !zona.trim().equalsIgnoreCase(""))?zona.trim():" ";
							alZc.add(new String[]{fog, par, zon, dp});
							String key = fog + "_" + par + "_" + dp;
							if (htZc.containsKey( key ))
								zon = "MIX";
							htZc.put(key, zon);
						}
					}
					DbUtils.close(rs);
				}
				DbUtils.close(pstmt);
				/*
				 * Recupero l'ID_CAT da SITICATALOG attraverso il nome della tabella 
				 */
				sql = "select * from " + nomeSchemaSiti + ".SITICATALOG where TABELLA = '" + NOME_TABELLA + "' ";
				log.debug("SQL: " + sql);
				
				String idCat = "";
				pstmt = connSiti.prepareCall(sql);
				rs = pstmt.executeQuery();
				if (rs != null){
					while(rs.next()){
						idCat = rs.getString("id_cat");
					}
					DbUtils.close(rs);
				}
				DbUtils.close(pstmt);
				/*
				 * Elimino i vecchi record in sitideco_catalog con il corrente idCat 
				 * per poi rigenerare la codifica 
				 */
				if (idCat != null && !idCat.trim().equalsIgnoreCase("")){
					sql = "delete from " + nomeSchemaSiti + ".sitideco_catalog where id_cat = " + idCat;
					log.debug("SQL: " + sql);
					pstmt = connSiti.prepareCall(sql);
					boolean okDelete = pstmt.execute();
					DbUtils.close(pstmt);
					/*
					 * Recupero il max codice in SITIDECO_CATALOG per le insert successive
					 */				
					Long maxId = new Long(0);
					sql = "select max(codice) as maxSeq from " + nomeSchemaSiti + ".sitideco_catalog";
					log.debug("SQL: " + sql);
					pstmt = connSiti.prepareCall(sql);
					rs = pstmt.executeQuery();
					if (rs != null){
						while(rs.next()){
							maxId = rs.getLong("maxSeq");
						}
						DbUtils.close(rs);
					}
					DbUtils.close(pstmt);
					/*
					 * Inizio Decodifica e insert dentro la SITIDECO_CATALOG dopo aver
					 * recuperato l'id_cat da SITICATALOG attraverso il nome della tabella
					 * specifica per questo catalogo (vedi costante nomeTabella)
					 */
					int indColore = 0;
					/*
					 * I colori hanno un senso logico (=dal piu chiaro al piu scuro )
					 * 1- Rosso
					 * 2- Giallo
					 * 3- Verde Chiaro
					 * 4- Celeste
					 * 5- Blu
					 * 6- Fuxia
					 * 7- Nero
					 * 8- Bianco
					 * 9- Grigio Chiaro
					 * 10- Grigio Scuro
					 * 11- Verde Scuro
					 * 12- Rosa
					 * 13- Marrone Chiaro
					 * 14- Arancione Scuro
					 * 15- Arancione Chiaro
					 */
					Integer[] alColori = new Integer[]{1, 2, 3, 4, 5, 6, 8, 9, 10, 11, 12, 13, 14, 15};
					/*
					 * Il numero delle classificazioni è dinamico + 1 perché viene
					 * introdotta la zona mista.
					 */
					//Enumeration<String> eZone = htZone.keys();
					int indice = 0;
					int colore = 0;
					for (int i=0; i<aryZone.length; i++){
					//while(eZone.hasMoreElements()){
						//String key = eZone.nextElement();
						//String desc = "ZONA: " + htZone.get(key);
						//String cod = CODIFICA + "_" + htZone.get(key);
						String desc = "ZONA: " + aryZone[i];
						String cod = CODIFICA + "_" + aryZone[i];
						indColore = indice;
						if (indColore > alColori.length-1)
							indColore = 0;
						colore = alColori[indColore];
						indColore++;
						maxId++;
						sql = "insert into " + nomeSchemaSiti + ".sitideco_catalog " +
								"(codice, codut, descrizione, id_cat, colore, riempimento, spessore, colorelinea) " +
								"values " +
								"(" + maxId + ", '" + cod + "', '" + desc + "', " + idCat + ", '" + colore + "', '1', '1', '7') ";
						
						log.debug("SQL: " + sql);
						
						pstmt = connSiti.prepareCall(sql);
						message = "Esito di " + sql + ": ";
						boolean okInsert = pstmt.execute();
						DbUtils.close(pstmt);
						message += (!okInsert)?"OK":"KO";
						log.debug(message);
						indice++;
					}
				}
				/*
				 * Recupero le info dalla siti_part per popolare la NOME_TABELLA
				 */
				Enumeration<String> eZc = htZc.keys();
				while (eZc.hasMoreElements()){
					String key = eZc.nextElement();
					String[] riga = key.split("_");
					sql = "select sp.foglio, sp.particella, sp.shape " +
							"from " + nomeSchemaSiti + ".sitipart sp " +
							"where sp.foglio = '" + riga[0] + "' " +
							"and sp.particella = '" + riga[1] + "' " +
							"and sp.sub = '" + riga[2] + "' " +
							"and sp.data_fine_val=to_date('99991231','yyyymmdd') ";
					
					log.debug("SQL: " + sql);
					
					String codZona = CODIFICA + "_" + htZc.get(key);
					
					pstmt = connSiti.prepareCall( sql );
					try {
						rs = pstmt.executeQuery();
						if (rs != null){
							while(rs.next()){
								STRUCT st = (oracle.sql.STRUCT) rs.getObject("shape");
								if(st != null && st.getAttributes() != null)
								{
									sql = "insert into " + nomeSchemaSiti + "." + NOME_TABELLA + " " +
											"(shape, codice, id_cat, foglio, particella, sub) " +
											"values (?,?,?,?,?,?)";
	
									CallableStatement cstmt = connSiti.prepareCall(sql);
									cstmt.setObject(1, st);
									cstmt.setString(2, codZona);
									cstmt.setInt(3, Integer.parseInt(idCat) );
									cstmt.setInt(4, Integer.parseInt(riga[0]) );
									cstmt.setString(5, riga[1]);
									cstmt.setString(6, riga[2]);
									boolean okInsert = cstmt.execute();
									log.debug(sql);
									
									DbUtils.close(cstmt); 
								}
							}
							DbUtils.close(rs);
						}
					} finally {
						DbUtils.close(pstmt);
					}
				}
				log.debug("Inizio COMMIT delle insert in " + NOME_TABELLA);
				connSiti.commit();
				/*
				 * Creazione degli indici spaziali su SITI {call SITI.crea_idx_spatial()}
				 */
				log.debug("Inizio creazione indici spaziali: " + NOME_INDICE);
				sql = "{call " + nomeSchemaSiti + ".crea_idx_spatial('" + NOME_TABELLA + "', 'SHAPE', null, '" + NOME_INDICE + "')}";
				log.debug("SQL: " + sql);
				
				pstmt = connSiti.prepareCall(sql);
				boolean creaIdx = pstmt.execute();
				DbUtils.close(pstmt);

				return (new ApplicationAck("SQL ESEGUITO CON SUCCESSO"));
			}else{
				/*
				 * log di tabella NOME_TABELLA inesistente
				 * P.S. la descrizione seguente della regola:
				 * STA01-V.M. RENDITA PER VANO
				 * è fissa perché non esiste fino a 
				 * quando da SITI non si è inserito un catalogo
				 */
				message = "STA04-ZONE CENSUARIE: Tabella " + NOME_TABELLA + " inesistente: e` necessario creare il catalogo tramite SITI";
				log.debug(message);
				return (new ApplicationAck(message));
			}
		}
		catch (Exception e)
		{
			log.error("Errore esecuzione sql:" + sql, e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} finally {
			try
			{
				if (pstmt != null)
					pstmt.close();
			}
			catch (Exception ec)
			{
			}

		}
	}


}
