/**
*
*/
package it.webred.rulengine.brick.loadDwh.load.util;


import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.exception.RulEngineException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;



/**
 * @author  	:  	$Author: riccardini $
 * @Revision :  	$Revision: 1.11 $ $Rev$
 * @Date		:	$Date: 2010/12/28 11:21:31 $
 */

public class Util {

	protected static final org.apache.log4j.Logger log = Logger.getLogger(Util.class.getName());

	public static String prefissoTmpTabelle = "RET_";
	
	public static void duplicateTabeleDWH(ArrayList<String> tabs, Connection conn) throws Exception {
			
			for (String tabella : tabs) {
				
				CallableStatement st = null;
				String tabellaTMP = null;
				String sqlCreateT = null;
				
				// DUPLICAZIONE TABELLA
				try {
					tabellaTMP =prefissoTmpTabelle+ tabella;
					try {
						dropTable(tabellaTMP,conn);
					} catch (Exception e) {
						log.info("NESSUN PROBLEMA CON LA TABELLA " + tabellaTMP + " : RISULTA GIUSTAMENTE DROPPATA .... oppure problema imprevisto :" + e.getMessage());
					}

					sqlCreateT = "CREATE TABLE " + tabellaTMP + " AS SELECT * FROM " + tabella + " where 0=1";
					log.info(sqlCreateT)	;
					st = conn.prepareCall(sqlCreateT);
					st.execute();
				} catch (Exception e) {
					log.warn("PROBLEMI DUPLICAZIONE TABELLA " + tabellaTMP  , e);
					log.warn(sqlCreateT);
					log.warn("SE NOME GIA' IN USO ALLORA PROBABILE TABELLA OCCUPATA!");
					throw e;
				}	finally {
					try {
						DbUtils.close(st);
					} catch (SQLException e) {
						log.error(e);
					}
				}

				// CREAZIONE CHIAVE
				st = null;
				try {
					// creo la chiave primaria
					String sqlPk = "SELECT cols.column_name colonna "
					+" FROM user_constraints cons, user_cons_columns cols "
					+" WHERE cols.table_name = '" + tabella + "'"
 					+" AND cons.constraint_type = 'P' "
					+" AND cons.constraint_name = cols.constraint_name "
					+" AND cons.owner = cols.owner "
					+" ORDER BY cols.table_name, cols.position ";	
					log.info(sqlPk);
					PreparedStatement ps = null;
					ResultSet rs = null;
					String campiChiave = "";
					try {
						ps = conn.prepareStatement(sqlPk);
						rs = ps.executeQuery();
						boolean prima = true;
						while (rs.next()) {
							if (!prima)
								campiChiave += "," + rs.getString(1);
							else
								campiChiave += rs.getString(1);
								
						}
					} finally {
						DbUtils.close(ps);
						DbUtils.close(rs);
					}
					if (!"".equals(campiChiave)) {
						String nomePk = tabellaTMP + "_PK";
						if (nomePk.length() > 30) {
							//gestione dei nomi PK che non possono avere più di 30 caratteri
							//l'unico caso di errore è che esista una seconda tabella con nome = tabellaTMP.substring(0, 27)...
							nomePk = tabellaTMP.substring(0, 27) + "_PK";
						}
						String alterPK = "ALTER TABLE " + tabellaTMP + " ADD CONSTRAINT " + nomePk + " PRIMARY KEY ("+campiChiave+") ENABLE";
						log.info(alterPK);
						st = conn.prepareCall(alterPK);
						st.execute();
					}
				} catch (Exception e) {
					log.warn("PROBLEMI DUPLICAZIONE TABELLA " + tabellaTMP  , e);
					log.warn("SE NOME GIA' IN USO ALLORA PROBABILE TABELLA OCCUPATA!");
					throw e;
				}	finally {
					try {
						DbUtils.close(st);
					} catch (SQLException e) {
						log.error(e);
					}
				}
				
				
				
				
			}
			
	}
	
	
	public static void dropTable(String table, Connection c) throws Exception {
		log.info("DROP TABLE " + table);
		CallableStatement st = null;
		try {
			st = c.prepareCall("DROP TABLE " +table);
			st.execute();
		} catch (Exception e) {
			try {
				DbUtils.close(st);
			} catch (SQLException e1) {
				log.warn("Non possibile chiudere lo statement o la connessione c della DROP: " + e1.getMessage());
			}
			
			log.warn("Impossibile fare la DROP della tabella " + table);
			throw e;
		}	
	}
	
	
	public static void truncateTable(String table, Connection c) throws Exception {
		log.info("TRUNCATE TABLE " + table);
		CallableStatement st = null;
		try {
			st = c.prepareCall("TRUNCATE TABLE " +table);
			st.execute();
		} catch (Exception e) {
			try {
				DbUtils.close(st);
			} catch (SQLException e1) {
				log.warn("Non possibile chiudere lo statement o la connessione c della TRUNCATE: " + e1.getMessage());
			}
			
			log.warn("Impossibile fare la TRUNCATE della tabella " + table);
			throw e;
		}	
	}
	
	
	public static void postNormalizza(ArrayList<String> tabelleDWH, 
						boolean gestisciTmp, Connection conn,ConcreteImport ci,
						LinkedHashMap<String, String> tabs, boolean disattivaStorico) throws Exception {
		
		if (gestisciTmp) {
			// RIVERSO DA TABELLA TMP A PRODUZIONE
			try {
				Util.riversaInteroSetDatiDaTmpADwh(tabelleDWH, conn,disattivaStorico );
			} catch (Exception e) {
				throw new RulEngineException(e.getMessage(), e);
			}
		}

		// dopo la normalizzazione setto ad elaborati tutti i record che 
		// sono rimasti con flag a zero per via del fatto che 
		// non avevano chiave
		if (!gestisciTmp) {
			log.info("setReFlagElaboratoConChiaveNullaONoChiave");
			if(ci != null) {
				ci.setReFlagElaboratoConChiaveNullaONoChiave();	
			}
		}
		else {
			for (String key : tabs.keySet())
			{
				try {
					log.info("TRUNCATE TABELLA " + key);
					Util.truncateTable(key, conn);
			} catch (Exception e) {
				log.error("ERRORE IN TRUNCATE TABELLA " + key );
				throw new RulEngineException("ERRORE IN TRUNCATE TABELLA " + key , e);
			}

			}
		}
	}
	
	public static void riversaSetDatiDaTmpADwh(ArrayList<String> tabs, Connection conn, boolean disattivaStorico, int inReplaceValue,GestoreCorrelazioneVariazioni gest) throws Exception {
		
		if(inReplaceValue==2)
			riversaVariazioniSetDatiDaTmpADwh(tabs, conn, disattivaStorico, gest);
		else 
			riversaInteroSetDatiDaTmpADwh(tabs, conn, disattivaStorico);
	}
	
	private static void riversaVariazioniSetDatiDaTmpADwh(ArrayList<String> tabs, Connection conn, boolean disattivaStorico, GestoreCorrelazioneVariazioni gest) throws Exception {
		log.debug("Trasferimento dati da tabelle temporanee a finali, mantenendo le chiavi, se possibile.");
		String msgErr=null;
		
		for (String tabella : tabs) {
			
			
			CallableStatement st = null;

			try {
				
				String tabellaTMP = prefissoTmpTabelle+ tabella ;
				
				log.debug("-----------------> Elaborazione tabella "+tabellaTMP);
				CallableStatement stDel = null;
				
				rimuoviChiaviDuplicate(tabellaTMP,conn,disattivaStorico);
				
				ProvenienzaTab provTab = verificaProvenienza(tabellaTMP, tabella, conn);
				
				// eventualmente vado a prossima tabella perche' la tabella non e' valorizzata
				if (provTab.isTabConProvenienza() && !provTab.isTabConProvenienzaValorizzata()) {
					log.info("tabella " + tabellaTMP + " non valorizzata - non da riversare");
					continue;
				}
				
				//Costruisco la condizione sulla provenienza (in caricamento) da gestire sulle tabelle già presenti
				String sqlCondProvenienza = "";
				if(!provTab.getProvenienza().isEmpty()){
					sqlCondProvenienza = "AND PROVENIENZA IN (";
					for(String s : provTab.getProvenienza())
						sqlCondProvenienza += "'"+s+"',";
					sqlCondProvenienza = sqlCondProvenienza.substring(0, sqlCondProvenienza.length()-1)+")";
				}
					
				
				log.info("1.SI CANCELLANO DALLE TABELLE SIT ("+tabella+"):");
				log.info("a. I record che non sono presenti in RET ("+tabellaTMP+") x ID_EXT (la nuova fornitura potrebbe avere alcuni record cancellati rispetto alla precedente");
				
				//Registrare i record rimossi in SIT_CORRELAZIONE_VARIAZIONI 
				if(gest==null){
					msgErr="Impossibile inserire in SIT_CORRELAZIONE_VARIAZIONI i record rimossi. " +
							   "Gestione Correlazione non Implementata (Estendere la classe astratta GestoreCorrelazioneVariazioni) per la tabella "+tabella;
					log.error(msgErr);
				}else
					gest.aggiornaOnDelete(conn,tabella, getResultSet(conn,"SELECT * FROM " + tabella+ " A WHERE  a.id_ext not in (SELECT  B.id_ext   FROM " + tabellaTMP + "  B) "+sqlCondProvenienza)); 
					
				try {
					String sqlDelete = "DELETE FROM " + tabella+ " A WHERE  a.id_ext not in (SELECT  B.id_ext   FROM " + tabellaTMP + "  B) "+sqlCondProvenienza; 
				
					stDel = conn.prepareCall(sqlDelete);
					stDel.execute();
					
				} catch (Exception e) {
						log.error(e.getMessage(),e);
						throw e;
				} finally {
					DbUtils.close(stDel);
				}
				
				
				log.info("b.I record che risultano modificati in RET ("+tabellaTMP+") (controllo id_ext e hash)");
				//Registrare i record rimossi in SIT_CORRELAZIONE_VARIAZIONI (STORED PROCEDURE ?)
				if(gest==null){
					msgErr="Impossibile inserire in SIT_CORRELAZIONE_VARIAZIONI i record modificati. " +
							   "Gestione Correlazione non Implementata (Estendere la classe astratta GestoreCorrelazioneVariazioni) per la tabella "+tabella;
					log.error(msgErr);
				}else
					gest.aggiornaOnUpdate(conn,tabella, getResultSet(conn,"SELECT * FROM " + tabella+ " A WHERE  (a.id_ext, a.ctr_hash)  not in (SELECT  B.id_ext , b.ctr_hash  FROM " + tabellaTMP + "  B) "+sqlCondProvenienza)); 
				
				try {
					String sql = "DELETE FROM " + tabella+ " A WHERE  (a.id_ext, a.ctr_hash)  not in (SELECT  B.id_ext , b.ctr_hash  FROM " + tabellaTMP + "  B) "+sqlCondProvenienza; 
					stDel = conn.prepareCall(sql);
					stDel.execute();
				} catch (Exception e) {
					log.error(e.getMessage(),e);
					throw e;
				} finally {
					DbUtils.close(stDel);
				}
				
				/**Si cancellano dalla RET tutti record che hanno stesso id_ext e stesso HASH nelle tabelle SIT, 
				 * quelli insomma che non hanno avuto modifiche
				 * (non è necessario registrare variazioni in SIT_CORRELAZIONE_VARIAZIONI)*/
				
				log.debug("2.Si cancellano dalla RET ("+tabellaTMP+") tutti record che hanno stesso id_ext e stesso HASH nelle tabelle SIT ("+tabella+") , quelli insomma che non hanno avuto modifiche");
				
				
				try {
					String sql = "DELETE FROM " + tabellaTMP+ " A WHERE  (a.id_ext, a.ctr_hash) in (SELECT  B.id_ext , b.ctr_hash  FROM " + tabella + "  B)"; 
					stDel = conn.prepareCall(sql);
					stDel.execute();
				} catch (Exception e) {
					log.error(e.getMessage(),e);
					throw e;
				} finally {
					DbUtils.close(stDel);
				}
				
				
				/**Si inseriscono in append in SIT i record di RET (è come il riversaInteroSetDatiDaTmpADwh ma meno hard) */
				log.info("Si inseriscono in append in SIT ("+tabella+") i record di RET  ("+tabellaTMP+")");
				
				try {
					String insertSql = "insert into " + tabella + " select * from " + tabellaTMP;
					st = conn.prepareCall(insertSql);
					log.info(insertSql);
					st.execute();
				} catch (Exception e) {
					log.error("Impossibile reinserire il set di dati nella tabella di produzione " + tabella);
					throw e;
				}
				
		
			} catch (Exception e) {
				throw e;
			}finally{
				DbUtils.close(st);
			}
			
		}
		
		conn.commit();
		
		/**Si eliminano le tabelle RET*/
		log.debug("Si eliminano le tabelle RET");
		// dopo aver ingressato tutto il set di dati allora vado a cancellare le tabelle temporanee
		for (String tabella : tabs) {
			String tabellaTMP = prefissoTmpTabelle+ tabella ;
			try {
				dropTable(tabellaTMP, conn);
			} catch (Exception e) {
				log.info("Errore su drop " + tabellaTMP + " intercettato, continuo .....");
			}
		}
		
	}
	
	private static List<String> getResultSet(Connection conn, String sql) throws Exception{
		CallableStatement st = null;
		ResultSet rs = null;
		List<String> lst = new ArrayList<String>();
		try {
			log.debug("SQL["+sql+"]");
			st = conn.prepareCall(sql);
			rs = st.executeQuery();
			while(rs.next())
				lst.add(rs.getString("ID"));
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			DbUtils.close(st);
			DbUtils.close(rs);
			throw e;
		}
		return lst;
	}
	
	
	private static ProvenienzaTab verificaProvenienza(String tabellaTMP, String tabella, Connection conn) throws Exception{
		
		//Verifico la provenienza
		
		// POSSO ANDARE A CANCELLARE LA TABELLA DEL DWH MA SOLTANTO SE IL FILE CHE HO CARICATO HA INSERITO RECORD PER QUELLA TABELLA
		// (NELLA TEMPORANEA RET_SIT_*) , ALTRIMENTI NON CANCELLO PROPRIO NIENTE PERCHE' VUOL DIRE
		// CHE IL FILE CHE HO CARICATO NON CONTENEVA RECORD PER QUELLA TABELLA
		ProvenienzaTab provTab = new ProvenienzaTab();
		ResultSet rs = null;
		CallableStatement st = null;
		try {
			String sqlRET = "select DISTINCT PROVENIENZA from " + tabellaTMP;
			st = conn.prepareCall(sqlRET);
			rs = st.executeQuery();
			provTab.setTabConProvenienza(true);
			while (rs.next()) {
				provTab.setTabConProvenienzaValorizzata(true);
				try {
					provTab.getProvenienza().add(rs.getString("provenienza"));
					log.info("Tabella " + tabellaTMP + " PROVENIENZA =" + provTab.getProvenienza());
				} catch (Exception e) {
					log.info("Tabella " + tabellaTMP + " NON MULTI PROVENIENZA");
				}
			}
		} catch (Exception e) {
			log.info("Tabella " + tabellaTMP + " NON MULTI PROVENIENZA");
			provTab.setTabConProvenienza(false);
		} finally {
			DbUtils.close(rs);
			DbUtils.close(st);
		}
		
		// sta ad indicare che sto caricando la stessa provenienza che sta dentro la tabella di destinazione
		// e nella tabella di destinazione non ce ne sono altre !
		// questo vuol dire che posso andare a fare la truncate e non la delete!!!
		provTab.setStessaProvenienza(true);
		if (!provTab.getProvenienza().isEmpty()) {
			rs = null;
			try {
				String sqlRET = "select distinct provenienza from " + tabella;
				st = conn.prepareCall(sqlRET);
				rs = st.executeQuery();
				while (rs.next()) {
						if (!provTab.getProvenienza().contains(rs.getString("provenienza"))) {
							provTab.setStessaProvenienza(false);
							log.info("Tabella " + tabella + " CONTIENE PROVENIENZA =" + rs.getString("provenienza") + " DIVERSA DA QUELLE CHE SI STANNO INSERENDO");
							break; 
						}
					}
			} catch (Exception e) {
				throw e; 
			} finally {
				DbUtils.close(rs);
				DbUtils.close(st);
			}
		}
		
		return provTab;
	}
	
	private static void rimuoviChiaviDuplicate(String tabellaTMP, Connection conn, boolean disattivaStorico) throws Exception{
		log.info("Tolgo eventuali chiavi duplicate nella tabella  " + tabellaTMP);
		CallableStatement stDel = null;
		
		try {
			// NEL CASO DI STORICO DISABILITATO QUESTA NON CANCELLA MAI NULLA
			String sqlDuplicati = "DELETE FROM " + tabellaTMP + " A WHERE  a.rowid >  ANY (SELECT  B.rowid   FROM " + tabellaTMP + "  B WHERE A.ID_EXT = B.ID_EXT AND A.CTR_HASH = B.CTR_HASH )"; 
			stDel = conn.prepareCall(sqlDuplicati);
			stDel.execute();
		} catch (Exception e) {
			// identificativo non valido : tabella senza ctr_hash! VADO AVANTI  
			if (!e.getMessage().contains("ORA-00904")) {
				log.error("Tabella " + tabellaTMP + " : impossibile cancellare record duplicati");
				throw e;
			} else {
				log.info("Tabella " + tabellaTMP + " non standard - non posso togliere duplicati");
			}
		} finally {
			DbUtils.close(stDel);
		}
		
		
		// mi accerto che ci sia un solo record , quello ultimo per uno stesso ID_EXT
		if (disattivaStorico) {
			stDel = null;
			try {
				// NEL CASO DI STORICO DISABILITATO QUESTA NON CANCELLA MAI NULLA
				String sqlDuplicati = "DELETE FROM " + tabellaTMP + " A WHERE  a.rowid >  ANY (SELECT  B.rowid   FROM " + tabellaTMP + "  B WHERE A.ID_EXT = B.ID_EXT)"; 
				stDel = conn.prepareCall(sqlDuplicati);
				stDel.execute();
			} catch (Exception e) {
				// identificativo non valido : tabella senza ctr_hash! VADO AVANTI  
				if (!e.getMessage().contains("ORA-00904")) {
					log.error("Tabella " + tabellaTMP + " : impossibile cancellare record CON STESSO id_ext IN CASO DI STORICO DISABILITATO");
					throw e;
				} else {
					log.info("Tabella " + tabellaTMP + " non standard - non posso togliere duplicati");
				}
			} finally {
				DbUtils.close(stDel);
			}
		}
	}
	
	private static void riversaInteroSetDatiDaTmpADwh(ArrayList<String> tabs, Connection conn, boolean disattivaStorico) throws Exception {
		for (String tabella : tabs) {
			
			
			CallableStatement st = null;

			try {
				String tabellaTMP = prefissoTmpTabelle+ tabella ;
				
				rimuoviChiaviDuplicate(tabellaTMP,conn,disattivaStorico);
				
				
				log.info("RIVERSO DA " + tabellaTMP + " A " + tabella );
				// POSSO ANDARE A CANCELLARE LA TABELLA DEL DWH MA SOLTANTO SE IL FILE CHE HO CARICATO HA INSERITO RECORD PER QUELLA TABELLA
				// (NELLA TEMPORANEA RET_SIT_*) , ALTRIMENTI NON CANCELLO PROPRIO NIENTE PERCHE' VUOL DIRE
				// CHE IL FILE CHE HO CARICATO NON CONTENEVA RECORD PER QUELLA TABELLA
/*				boolean tabConProvenienza=false;
				boolean tabConProvenienzaValorizzata=false;
				ArrayList<String> provenienza = new ArrayList<String>();
				ResultSet rs = null;
				try {
					String sqlRET = "select DISTINCT PROVENIENZA from " + tabellaTMP;
					st = conn.prepareCall(sqlRET);
					rs = st.executeQuery();
					tabConProvenienza = true;
					while (rs.next()) {
						tabConProvenienzaValorizzata = true;
						try {
							provenienza.add(rs.getString("provenienza"));
							log.info("Tabella " + tabellaTMP + " PROVENIENZA =" + provenienza);
						} catch (Exception e) {
							log.info("Tabella " + tabellaTMP + " NON MULTI PROVENIENZA");
						}
					}
				} catch (Exception e) {
					log.info("Tabella " + tabellaTMP + " NON MULTI PROVENIENZA");
					tabConProvenienza=false;
				} finally {
					DbUtils.close(rs);
					DbUtils.close(st);
				}
				
				// sta ad indicare che sto caricando la stessa provenienza che sta dentro la tabella di destinazione
				// e nella tabella di destinazione non ce ne sono altre !
				// questo vuol dire che posso andare a fare la truncate e non la delete!!!
				boolean stessaProvenienza = true;
				if (!provenienza.isEmpty()) {
					rs = null;
					try {
						String sqlRET = "select distinct provenienza from " + tabella;
						st = conn.prepareCall(sqlRET);
						rs = st.executeQuery();
						while (rs.next()) {
								if (!provenienza.contains(rs.getString("provenienza"))) {
									stessaProvenienza = false;
									break;
								} 
								log.info("Tabella " + tabella + " CONTIENE PROVENIENZA =" + rs.getString("provenienza") + "DIVERSA DA QUELLA CHE SI STA INSERENDO");
						}
					} catch (Exception e) {
						throw e; 
					} finally {
						DbUtils.close(rs);
						DbUtils.close(st);
					}
				}*/
				
				ProvenienzaTab provTab = verificaProvenienza(tabellaTMP, tabella, conn);
				
				// eventualmente vado a prossima tabella perche' la tabella non e' valorizzata
				if (provTab.isTabConProvenienza() && !provTab.isTabConProvenienzaValorizzata()) {
					log.info("tabella " + tabellaTMP + " non valorizzata - non da riversare");
					continue;
				}
				
					String sqlDelete = null;
					String sqlRevert = null;
					if (!provTab.getProvenienza().isEmpty()) {
						if (provTab.isStessaProvenienza()) 
							sqlRevert = "{ call UTIL.REVERT_TABLE_SRC_TO_DEST(?, ?) }";
						else {
							
							String sqlCondProvenienza = " PROVENIENZA IN (";
								for(String s : provTab.getProvenienza())
									sqlCondProvenienza += "'"+s+"',";
							sqlCondProvenienza = sqlCondProvenienza.substring(0, sqlCondProvenienza.length()-1)+")";
							
							sqlDelete = "delete from " + tabella + " WHERE "+sqlCondProvenienza;
						}
					} else
						sqlRevert = "{ call UTIL.REVERT_TABLE_SRC_TO_DEST(?, ?) }";

				if (sqlDelete!=null) {
					try {
						
						log.debug(sqlDelete);
						st = conn.prepareCall(sqlDelete);
						st.execute();
					} catch (Exception e) {
						log.error("Impossibile cancellare dati da " + tabella);
						throw e;
					}
				} else {
					CallableStatement proc = null;
					try {
						log.debug(sqlRevert);
					    proc = conn.prepareCall(sqlRevert);
					    proc.setString(1, tabellaTMP);
					    proc.setString(2, tabella);
					    proc.execute();
						continue; // esco perche' tutto il lavoro e' stato fatto dalla stored procedure
					} catch (Exception e) {
						log.error("Impossibile fare il revert da " + tabellaTMP + " a" + tabella,e);
						throw e;
					} finally {
						DbUtils.close(proc);
						conn.commit();
						conn.setAutoCommit(false);
					}
				}
				
				if (!provTab.isStessaProvenienza()) {
					// ora riverso l'altra tabella
					try {
						String insertSql = "insert into " + tabella + " select * from " + tabellaTMP;
						st = conn.prepareCall(insertSql);
						log.debug(insertSql);
						st.execute();
					} catch (Exception e) {
						log.error("Impossibile reinserire il set di dati nella tabella di produzione " + tabella);
						throw e;
					}
				}

			} catch (Exception e) {
				log.warn("PROBLEMI RIVERSAMENTO DA TABELLA TEMPORANEA A " + tabella , e);
				throw e;
			}	finally {
				try {
					DbUtils.close(st);
				} catch (SQLException e) {
					log.error(e);
				}
			}
			
			
		}

		
		conn.commit();
		//conn.setAutoCommit(false);
		// dopo aver ingressato tutto il set di dati allora vado a cancellare le tabelle temporanee
		for (String tabella : tabs) {
			String tabellaTMP = prefissoTmpTabelle+ tabella ;
			try {
				dropTable(tabellaTMP, conn);
			} catch (Exception e) {
				log.info("Errore su drop " + tabellaTMP + " intercettato, continuo .....");
			}
		}

		
		
}


	/**
	 * @param tabellaPErIndici
	 * @param conn
	 * @throws Exception
	 * Crea indici utili alle fasi di inserimento dei dati, viene chiamato quando la fonte dati e' in replace 
	 */
	public static void creaIndiciUtili(String tabellaPErIndici, Connection conn) throws Exception {
		CallableStatement st = null;
		try {
			String nomeindice = "IDX_" + System.currentTimeMillis() + "_TMP1";
				
			String CREATE = "CREATE INDEX " + nomeindice  + " ON " + tabellaPErIndici  + " (ID_EXT)";
			log.info(CREATE);
			st = conn.prepareCall(CREATE);
			st.execute();
		} finally {
			DbUtils.close(st);
		}

		st = null;
		try {
			long curt = System.currentTimeMillis() +1;
			String nomeindice = "IDX_" + curt   + "_TMP2";
			
			String CREATE = "CREATE INDEX " + nomeindice  + " ON " + tabellaPErIndici  + " (CTR_HASH)";
			log.info(CREATE);
			st = conn.prepareCall(CREATE);
			st.execute();
		} finally {
			DbUtils.close(st);
		}
		
		
	}

}
