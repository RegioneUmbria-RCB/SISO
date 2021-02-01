package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.core.EseguiSql;
import it.webred.rulengine.brick.loadDwh.load.util.Util;
import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.SitSintesiProcessiUtils;
import it.webred.rulengine.dwh.def.Campo;
import it.webred.rulengine.dwh.def.Chiave;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Identificativo;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;
import it.webred.rulengine.dwh.def.TipoXml;
import it.webred.rulengine.dwh.table.EseguiQueryInDisabilitaStorico;
import it.webred.rulengine.dwh.table.IdExtFromSequence;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.rulengine.impl.CommandUtil;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;
import it.webred.rulengine.type.impl.TypeFactory;
import it.webred.utils.DateFormat;
import it.webred.utils.GenericTuples;
import it.webred.utils.StringUtils;
import it.webred.utils.GenericTuples.T2;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.log4j.Logger;

/**
* @author  	:  	$Author: riccardini $
* @Revision :  	$Revision: 1.37 $ $Rev$
* @Date		:	$Date: 2010/11/26 07:53:45 $
*/

/**
* @author  	:  	$Author: riccardini $
* @Revision :  	$Revision: 1.37 $ $Rev$
* @Date		:	$Date: 2010/11/26 07:53:45 $
*/

public abstract class TabellaDwhDao extends AbstractTabellaDwhDao 
{
	protected static final Logger log = Logger.getLogger(TabellaDwhDao.class.getName());

	private static 	    HashMap<String, String> hashInsert = new HashMap<String, String>();
	private static 	    HashMap<String, String> hashInsertTMP = new HashMap<String, String>();
	private static 	    HashMap<String, PreparedStatement> hashStatement = new HashMap<String, PreparedStatement>();
	private static 	    HashMap<String, Set> hashSetsTabella = new HashMap<String, Set>();
	private static 	    HashMap<String, HashMap<String, Method>> hashGMethods = new HashMap<String, HashMap<String, Method>>();
	
	// non usato
	private static 	    HashMap<String, Integer> hashNumBatch = new HashMap<String, Integer>();
	
	Connection conn=null;
	

	
	public TabellaDwhDao(TabellaDwh tab, BeanEnteSorgente bes) 
	{
		super(tab, bes);
	}

	public TabellaDwhDao(TabellaDwh tab) 
	{
		super(tab);
	}

	/**
	 * il metodo viene chiamato da SitSintesiProcessiUtils durane  l'operazione 
	 * di chiusura di un processo
	 * @param tabella
	 */
	public static void distruggiPreparestatement( String tabella) {
		PreparedStatement ps =  hashStatement.get(tabella);
		if (ps!=null) {
			try {
				ps.close();
			} catch (SQLException e) {
				log.warn("Ho tentato di chiudere un ps gia chiuso:" + tabella);
			}
		}
		hashStatement.remove(tabella);
		
		
	}
		
	/**
	 * HashMap necessaria per effettuare la verifica della necessita
	 * di lanciare un ricalcolo degli hash su una tabella del db a seguito del cambiamento di struttura
	 * La HashMap serve a verificare che il test di ricalcolo sia gia stato effettuato oppure no
	 * sulla particolare tabella/classe
	 * onde evitare di effettuare il test (metodo manageRicalcoloHash ) 
	 * piu volte
	 * Nel parametro String viene messo il processid
	 */
	private static HashMap<Class<? extends TabellaDwh>, ProcessId> ricalcoloHash = new HashMap<Class<? extends TabellaDwh>, ProcessId>();
		
	private void manageRicalcoloHash(String belfiore) throws DaoException {
		
		ProcessId processId = this.getTabella().getProcessid();
		
		Class<? extends TabellaDwh> tabellaDwhClass = this.tabella.getClass();
		try {
		
		ProcessId pidLast = ricalcoloHash.get(tabellaDwhClass);

		// controllo gia eseguito per processid corrente
		// CHE SPETTACOLO, SONO UN MOSTRO!!!!!
		// I'M A MONSTER !
		if (pidLast!=null && pidLast.equals(processId))
			return;
		else {
			ricalcoloHash.remove(pidLast);
		}
		
		
		

		// gestione indici quando inserimento in replace, gli indici creati qui vengono cancellati ALLA FINE SU UTIL.REVERT_TABLE_SRC_TO_DEST
        try {
    		// inreplace faccio le query per verificare la presenza di altre chiave solo se  E' una tabella di un certo tipo
    		// in generale infatti vado con la insert senza problemi,  velocizzando la cosa
    		if (inReplace) {
    			log.info("Creo indici utili su " +nomeTabellaTMP);
	    		Util.creaIndiciUtili(nomeTabellaTMP, conn);
    		}
        } catch (Exception e ) {
        	log.warn("Creo indici utili su " +nomeTabellaTMP +" - ERRORE" +e.getMessage());
        	//throw new DaoException(e);
        }
        
			
        // non vado avanti a calcolare l'hash
		if (this.inReplace) {
			ricalcoloHash.put(tabellaDwhClass, processId);
			return;
		}
		
		/*
		 * nel caso che il controllo non sia stato effettuato allora lo effettuamo
		 * su un record della tabella
		 */
		log.warn("ATTENZIONE!! VERIFICA NECESSITA OPERAZIONI UNATANTUM IN CORSO");
		
		
		
		try {
				String sqlPlain = AbstractTabellaDwhDao.getPlainSelect(tabellaDwhClass);
				
				String sql = sqlPlain + " where rownum <=1";
				//log.debug("Query: "+sql);
				ResultSetHandler h = new MapHandler();
				QueryRunner run = new QueryRunner();
				HashMap<String, Method> campiWithSet = new HashMap<String, Method>();
				TabellaDwh t = tabellaDwhClass.newInstance();
				Map result = new HashMap();
	            result = (Map) run.query(conn, sql,h);
	            if (result==null) {
	        		ricalcoloHash.put(tabellaDwhClass, processId);
	            	return;
	            }
						// vado a settare i campi sul bean della tabella per calcolare l'Hash
			    		T2<DynaBean, HashMap<String, Object>> dyn = DwhUtils.getDynaInfoTable(t, true);
			    		Set<Entry<String, Object>> sets = dyn.secondObj.entrySet();
			    		Iterator it = sets.iterator();
			    		while (it.hasNext()) {
			    			Map.Entry entry = (Map.Entry) it.next();
			    			String setMethod = (String) entry.getKey();
			    			String fieldUpperFirst = setMethod.substring(3);
			    			String campo = StringUtils.JavaName2NomeCampo(fieldUpperFirst);
			    			Object value = result.get(campo);

			    			//log.debug("campo:"+campo+", VALUE:"+value);
			    			
			            	Method gmeth = t.getClass().getMethod("get" + fieldUpperFirst, new Class[0]);
			            	Class<?> tipoDatoSetMethod =gmeth.getReturnType();
			            	//log.debug("tipoDato:"+tipoDatoSetMethod.getName());
			            	if (Relazione.class.getName().equals(tipoDatoSetMethod.getName()))
			            		tipoDatoSetMethod = ChiaveEsterna.class;
			            	if (RelazioneToMasterTable.class.getName().equals(tipoDatoSetMethod.getName()))
			            		tipoDatoSetMethod = ChiaveEsterna.class;
		    				// non partecipa al calcolo dell'hash
		    				if (TipoXml.class.getName().equals(tipoDatoSetMethod.getName()))
		    					continue;

			    			Object o = null;
			    			try {
			                	o = TypeFactory.getType(value, tipoDatoSetMethod.getName());
			    			} catch (Exception e) {
			    				//provo con identificativo invece che ChiaveEsrerna
			    				if (tipoDatoSetMethod.getName().equals(ChiaveEsterna.class.getName())) {
			    					tipoDatoSetMethod = Identificativo.class;
			    				}
			    				
			                	o = TypeFactory.getType(value, tipoDatoSetMethod.getName());
			    			}
			    			
			    			log.debug("---");
			    			
			    			Method smeth =null;
			    			// l'unico meto do set con 2 parametri e' quello di fkentesorgente
			    			if ("setFkEnteSorgente".equals(setMethod))
			    				smeth = t.getClass().getMethod(setMethod,tipoDatoSetMethod, belfiore.getClass() );
			    			else
			    				smeth = t.getClass().getMethod(setMethod,tipoDatoSetMethod);
			    				
			    			
			    			campiWithSet.put(campo, smeth);
			    			// non invoco il set per quei campi ch non mi serve valorizzare
			    			if (TabellaDwh.notModificableFields.containsKey(StringUtils.nomeCampo2JavaName(campo)))
			    				continue;

			    			if (smeth.getParameterTypes().length > 1)
			    				smeth.invoke(t,o,belfiore);
			    			else
			    				smeth.invoke(t,o);
			    				
			    		}
						t.setCtrHash();

			// se necessario scateno ricalcolo hash !!!!!!!!!
			if (!t.getCtrHash().getValore().equals(result.get("CTR_HASH"))) {
				//CommandUtil.saveRCommandAck(getRCommandLaunch(processId.getValore()), new ApplicationAck(tabellaDwhClass.getName() + " RICALCOLO HASH ATTIVATO, ATTENDERE ...") ,this.getBeanCommand());

				log.warn("RICALCOLO HASH ATTIVATO!");
				PreparedStatement ps = conn.prepareStatement(sqlPlain);
				ResultSet rs = ps.executeQuery();
				try {
						String sqlUpd = AbstractTabellaDwhDao.getUpdateCtrHash(tabellaDwhClass);
						double count=0;
						double tot=0;
						while (rs.next()) {
							TabellaDwh row = tabellaDwhClass.newInstance();
							Iterator itcampi = campiWithSet.entrySet().iterator();
							while (itcampi.hasNext()) {
								String nomeCampo = null;
								Object valorecampo = null;
								try {
									Map.Entry entry = (Map.Entry) itcampi.next();
					    			nomeCampo = (String) entry.getKey();
					    			Method set = (Method) entry.getValue();
					    			valorecampo = rs.getObject(nomeCampo);
					    			if (set.getParameterTypes().length > 1)
					    				// fkentesorgente ha 2 parametri in set
										set.invoke(row,TypeFactory.getType(valorecampo, set.getParameterTypes()[0].getName()), belfiore);
					    			else
										set.invoke(row,TypeFactory.getType(valorecampo, set.getParameterTypes()[0].getName()));
					    			
								} catch (Exception e) {
									log.error("Errore nel reperire i campi per calcolo HASH:" +tabellaDwhClass.getName() + "." + nomeCampo +" "+ valorecampo , e);
									throw e;
								}
							}
							row.setCtrHash();
							// effettuo l'update del ctr_hash ricalcolato
							PreparedStatement upd =  null;
							try {
								upd = conn.prepareStatement(sqlUpd);
								upd.setString(1, row.getCtrHash().getValore());
								upd.setString(2, row.getId().getValore());
								upd.executeUpdate();
							} catch (Exception e) {
								log.error("Errore update HASH:" +tabellaDwhClass.getName() );
								throw e;
							} finally {
								DbUtils.close(upd);
							}
		
							count+=1;
							tot+=1;
							if (count==1000) {
								log.debug(tabellaDwhClass.getName() +  " Hash Ricalcolati:" + tot);
								
								count=0;
							}
						}
				} finally {
					DbUtils.close(rs);
					DbUtils.close(ps);
				}

			} else 
					log.debug("RICALCOLO HASH NON NECESSARIO!");
				
		} finally {
			
		}
							
		} catch (Exception e) {
			log.error("ERRORE GRAVE CERCANDO DI GESTIRE GLI hash !" + e.getMessage(),e);
			throw new DaoException("ERRORE GRAVE CERCANDO DI GESTIRE GLI hash !");
		}
				

		

		
		
		ricalcoloHash.put(tabellaDwhClass, processId);
	}
	

	
	/**
	 * NON UTILIZZATO - HO PROVATO UN TEST CON EXECUTEBATCH  MA NON HO RISCONTRATYO MIGLIORAMENTI NELLE PRESTAZIONI
	 * @param processid
	 * @param tabella
	 * @throws Exception
	 */
	public static void flushBatchMETODO_NON_USATO(String processid, String tabella, String belfiore) throws Exception  {
		Integer batchRimasti = hashNumBatch.get(tabella);
		if (batchRimasti==null || batchRimasti==0)
			return;

		PreparedStatement ps = hashStatement.get(tabella);
		
		try {
			if (ps!=null) {
				ps.executeBatch();
				ps.close();
				hashStatement.remove(tabella);
				hashNumBatch.remove(tabella);
			}
			
		    SitSintesiProcessiUtils.operazioneSincronizzata(processid, tabella, SitSintesiProcessiUtils.campi.get("INSERITI"), SitSintesiProcessiUtils.INCREMENTA, belfiore);

		} catch (Exception e) {
			log.error("Non possibile chiudere la fase di insert per la tabella " + tabella,e);
			throw new Exception("Non possibile chiudere la fase di insert per la tabella " + tabella,e);
		}
		
		
		
	}

	
	private String prepareInsert() throws Exception {

		String sqlRET = null;
		if (inReplace)
			sqlRET = hashInsertTMP.get(nomeTabella);
		else
			sqlRET = hashInsert.get(nomeTabella);
			
		if (sqlRET!=null)
			return sqlRET;
		
		String insertSqlCampi=null;
		String insertSqlValori=null;
		
		LinkedHashMap map = new LinkedHashMap();
		
		String sqlInsertInto = " INTO " + nomeTabella ;
		String sqlInsertIntoTMP = " INTO " + nomeTabellaTMP ;
		
		map = valoriSqlStandard;
		
		Set entries = map.entrySet();
	    Iterator it = entries.iterator();
	    while (it.hasNext()) {
	      Map.Entry entry = (Map.Entry) it.next();

	      if (insertSqlCampi==null) {
			  insertSqlCampi = "INSERT " + sqlInsertInto + " (";
	      } else {
	    	  insertSqlCampi = insertSqlCampi +",";
	      }
	      if (insertSqlValori==null) {
	    	  insertSqlValori = " VALUES (";
	      } else {
	    	  insertSqlValori = insertSqlValori +",";
	      }
	      insertSqlCampi = insertSqlCampi + entry.getKey();
	      if (entry.getValue() instanceof Identificativo && ((Campo)entry.getValue()).getValore()==null) {
	    	  insertSqlValori = insertSqlValori + "SEQ_"+nomeTabella+".NEXTVAL";
	      } else if (entry.getKey().equals("DT_INS"))  {
	    	  insertSqlValori = insertSqlValori + "sysdate";
	      } else {
		      insertSqlValori = insertSqlValori + '?';
	      }
	      
	    }

	    
		// map = this.getValoriSql();
		map = getValoriCustomSql(tabella, nomeTabella);
		entries = map.entrySet();
	    it = entries.iterator();
	    while (it.hasNext()) {
	      Map.Entry entry = (Map.Entry) it.next();

	      if (insertSqlCampi==null) {
			  insertSqlCampi = "INSERT INTO " + nomeTabella + " (";
	      } else {
	    	  insertSqlCampi = insertSqlCampi +",";
	      }
	      if (insertSqlValori==null) {
	    	  insertSqlValori = " VALUES (";
	      } else {
	    	  insertSqlValori = insertSqlValori +",";
	      }
	      insertSqlCampi = insertSqlCampi + entry.getKey();
	      if (entry.getValue() instanceof Identificativo && entry.getValue()==null) {
	    	  insertSqlValori = insertSqlValori + "SEQ_"+nomeTabella+".NEXTVAL";
	      } else if (entry.getValue() instanceof TipoXml)
	    	  insertSqlValori = insertSqlValori + "XMLTYPE(?)";
	      else {
		      insertSqlValori = insertSqlValori + '?';
	      }

	      
	    }
	    
	    
	    
	    insertSqlCampi = insertSqlCampi + ")"; 
	    insertSqlValori = insertSqlValori + ")";
	    String insert = insertSqlCampi + insertSqlValori;

	    
	    hashInsert.put(nomeTabella, insert);
	    String insertTMP = insert.replaceFirst(sqlInsertInto, sqlInsertIntoTMP);
	    hashInsertTMP.put(nomeTabella, insertTMP);
	    
	    return inReplace?insertTMP:insert;
		
	}
	
	private boolean  updateNoStessoID(String belfiore) throws DaoException
	{
		return update(this.getTabella().getIdExt(),belfiore, false,null);
	}

	private boolean updateStessoID(String belfiore) throws DaoException
	{
		return update(this.getTabella().getIdExt(),belfiore, true, null);
	}	
	
	/**
	 * 
	 * @param id: chiave esterna de,l record da aggiornare
	 * @param belfiore :codice comune
	 * @param noUpdateID : se true viene aggiornato il record con stesso ID in quanto, aggiornando solo quelli con ID_EXT UGIUALE potrebbe dare in situazioni particolari (stesso id_orig , su stessa fornitura , con dati diversi e dunque ctr_hash diversi) chiave duplicata
	 * @return
	 * @throws DaoException
	 */
	private boolean update(ChiaveEsterna id,String belfiore, boolean updateStessoID, String specificoID) throws DaoException
	{

		PreparedStatement ps = null;
		String sql = null;
		Set entries = null;
		try {
 			
			GenericTuples.T2<String,LinkedHashMap<String,Object>> upd = DwhUtils.prepareUpdate(this.getTabella());
 			
			sql = upd.firstObj;
			sql = sql + " WHERE ID_EXT=?";
			if (updateStessoID)
				sql = sql + " AND ID= '" + this.getTabella().getId().getValore() + "'";
			if (specificoID!=null)
				sql = sql + " AND ID= '" + specificoID + "'";
				
			
 			ps = conn.prepareStatement(sql);
 			
 			int i = 1;
 		    entries = upd.secondObj.entrySet();
 		    Iterator it = entries.iterator();
 		    while (it.hasNext()) {
 		      Map.Entry entry = (Map.Entry) it.next();
 		      Object o =  entry.getValue();

 		      
 		      if(o != null) {
	 		      if (o instanceof Campo) {
	 		    	  Object ob = ((Campo)o).getValore();
	 		    	  if(ob==null)
	 		    		  ps.setNull(i,java.sql.Types.VARCHAR);
	 		    	  else
	 		    		 ps.setObject(i,ob);
			      } else if (o instanceof Relazione) {
			    	  Campo c = ((Relazione)o).getRelazione();
			    	  Object ob = c.getValore();
			    	  if(ob==null)
	 		    		  ps.setNull(i,java.sql.Types.VARCHAR);
	 		    	  else
	 		    		 ps.setObject(i,ob);
			      }
			      else
			    	  ps.setObject(i,o);
 		      }
 		      else {
 		    	 ps.setNull(i,java.sql.Types.VARCHAR);
 		      }
 		      


 		      
 		      
		      i = i +1;
 			}
 		    
 		    ps.setObject(i, id.getValore());
 		    
 		    

 			ps.execute();
 			
 			//per il salvataggio in SIT_SINTESI_PROCESSI
 		    SitSintesiProcessiUtils.operazioneSincronizzata(this.getTabella().getProcessid().getValore(), nomeTabella, SitSintesiProcessiUtils.campi.get("SOSTITUITI"), SitSintesiProcessiUtils.INCREMENTA,belfiore);

 			return true;
		}	catch (Exception e){
				String msg = "Errore su update: " + sql + "Parametri:"+ entries.toString() +":"+ e.getMessage();
				log.error(msg,e);
				throw new DaoException(e);
			} finally {
				 
					try
					{
						if (ps!=null)
							ps.close();
					}
					catch (SQLException e)
					{
					}
			}
		
	}

	
	private boolean insert(boolean primaAggiorna,String belfiore) throws DaoException {
		
		manageIdNulll();
		
		PreparedStatement ps =null;

		LinkedHashMap map = new LinkedHashMap();
		String sql = null;
		try {
			
		
			sql = this.prepareInsert();
			
			PreparedStatement psFromHash = hashStatement.get(nomeTabella);
			boolean close = false;
			if (psFromHash==null) {
				ps = conn.prepareStatement(sql);
			} else {
				ps = psFromHash;
				if (ps == null) {
					ps = conn.prepareStatement(sql);
					hashStatement.put(nomeTabella, ps);
					close = true;
				} else {
					//test dell'accessibilità del PreparedStatement
					try {
						boolean myTest = (ps.getConnection() != null);
					} catch (Throwable e) {
						ps = conn.prepareStatement(sql);
						hashStatement.put(nomeTabella, ps);
						close = true;
					}
				}
			}
			map = valoriSqlStandard;
			Set entries = map.entrySet();
		    int count = 1;
		    for (Object object : entries) {
		    	Map.Entry entry = (Map.Entry) object;
			      if (!entry.getKey().equals("DT_INS")) { // lo salto perché già valorizzato con sysdate
				      
					if (entry.getValue() instanceof Campo) {
						Object o = ((Campo)entry.getValue()).getValore();
			      	  	if (o==null)
			      		  ps.setNull(count, java.sql.Types.VARCHAR);
			      		else
			      		  ps.setObject(count,o);
					} else if (entry.getValue() instanceof Relazione) {
						Object o = ((Relazione)entry.getValue()).getRelazione().getValore();
						if (o==null)
							ps.setNull(count, java.sql.Types.VARCHAR);
						else
							ps.setObject(count,o);
					}
				    else {
				    	Object o = entry.getValue();
				    	if (o==null)
							ps.setNull(count, java.sql.Types.VARCHAR);
						else
							ps.setObject(count,o);
				    }
					      
				    count = count +1;
			      }
			}


			map = this.getValoriCustomSql(tabella, nomeTabella);
			entries = map.entrySet();
		    for (Object object : entries) {
		    	Map.Entry entry = (Map.Entry) object;
	      	  	if (entry.getValue() instanceof Campo) {
					Object o = ((Campo)entry.getValue()).getValore();
		      	  	if (o==null)
		      		  ps.setNull(count, java.sql.Types.VARCHAR);
		      		else
		      		  ps.setObject(count,o);
	      	  	}
	      	  	else if (entry.getValue() instanceof Relazione) {
					Object o = ((Relazione)entry.getValue()).getRelazione().getValore();
		      	  	if (o==null)
		      		  ps.setNull(count, java.sql.Types.VARCHAR);
		      		else
		      		  ps.setObject(count,o);		    	  
	      	  	}
	      	  	else {
		      	  	Object o = entry.getValue();
		      	  	if (o==null)
		      		  ps.setNull(count, java.sql.Types.VARCHAR);
		      		else
		      			ps.setObject(count,entry.getValue());
	      	  	}
	      	  		
			      
		      count = count+1;
		      
		    }

		    try {
		    	
		    	ps.execute();
		    	ps.clearParameters();
		    	if (close)
		    		DbUtils.close(ps);
		  	    if (psFromHash==null)
					hashStatement.put(nomeTabella, ps);
		    	
		    	SitSintesiProcessiUtils.operazioneSincronizzata(this.getTabella().getProcessid().getValore(), nomeTabella, SitSintesiProcessiUtils.campi.get("INSERITI"), SitSintesiProcessiUtils.INCREMENTA,belfiore);
	    	
		    } catch (SQLException e) {

		    	// chiudo il cursore per sicurezza, prevenendo ORA-01000
		    	try  {
		    		DbUtils.close(ps);
		    	}catch (Exception ee) {
		    		log.error("Problemi chiusura ps", ee);
				} 
		    	if (psFromHash!=null)
					hashStatement.remove(nomeTabella);
		    	
		    	// non loggo le chiavi duplicate perche' le prevedo
		    	/*
		    	 *  il codice 23000 viene rilanciato anche in caso di [impossibile inserire NULL in ("DIOGENE_F704_GIT"."SIT_T_F24_VERSAMENTI"."PROG_DELEGA")]
		    	 *  per cui 
		    	 */
		    	
		    	if (!"23000".equals(e.getSQLState())) {   // ORA-00001
		    		log.error("ERRORE SQL NON PREVISTO DURANTE INSERT " + nomeTabella ,e);
		    		throw e;
		    	}else
		    		log.warn("ERRORE SQL NON BLOCCANTE DURANTE INSERT " + nomeTabella + ": " + e.getMessage());
		    }
	    
	    // AGGIORNO SOLO SE HO INSERITO ALTRIMENTI SE AGGIORNASSI PRIMA MI RITROVEREI DEI RECORD NON VALIDI
	    // MA NON UN SOSTITUTO
		boolean aggiornato = false;
		
		if (!inReplace && this.getTabella().getDtInizioVal().getValore()!=null && primaAggiorna)	{
			//*** Faccio scadere ueventuali record con lo stesso id_ext prima di inserire
			faiScadereRecords();
			//****
			aggiornato = true;
		} 


	    
	    //per il salvataggio in SIT_SINTESI_PROCESSI
	    if (aggiornato) {
	    	SitSintesiProcessiUtils.operazioneSincronizzata(this.getTabella().getProcessid().getValore(), nomeTabella, SitSintesiProcessiUtils.campi.get("AGGIORNATI"), SitSintesiProcessiUtils.INCREMENTA,belfiore);
	    }
	    
	    return true;
	} catch (Exception e) {
		String msg = "Errore su insert: " + sql + "Parametri:"+ map.toString() + ":" + e.getMessage();
		log.error(msg,e);
		throw new DaoException(msg);
	} 
	

}
	
	

	public void faiScadereRecords() throws SQLException {
		PreparedStatement psDtFineVal =null;

		try {
			String updateFineVal = "UPDATE " + nomeTabella + " SET DT_FINE_VAL=? , DT_MOD = SYSDATE WHERE ID_EXT = ? AND DT_FINE_VAL IS NULL AND DT_INIZIO_VAL <=? AND ID<>?"; 	
			psDtFineVal = conn.prepareStatement(updateFineVal);
			DataDwh dataFine = this.getTabella().getDtInizioVal();  

			psDtFineVal.setTimestamp(1,dataFine.getValore());
			psDtFineVal.setString(2,this.getTabella().getIdExt().getValore());
			psDtFineVal.setTimestamp(3,this.getTabella().getDtInizioVal().getValore());
			psDtFineVal.setString(4,this.getTabella().getId().getValore());
			psDtFineVal.execute();
		} finally {
			DbUtils.close(psDtFineVal);
		}

		
	}

	

	/**
	 * @param condizione : condizione where aggiuntiva senza and iniziale e senza punti interrogativi
	 * @throws SQLException
	 */private void faiScadereRecordsConMasterUgiale(String condizione, boolean delete) throws DaoException {
		PreparedStatement psDtFineVal =null;
		if (condizione==null)
			throw new DaoException("Tabella master non presente! Impossibile far scadere record precedenti a quello inserito!");
		
		try {
		if (!delete) {
					String updateFineVal = "UPDATE " + nomeTabella + " SET DT_FINE_VAL=? , DT_MOD = SYSDATE WHERE DT_FINE_VAL IS NULL AND DT_INIZIO_VAL <=? AND ID<>? AND PROCESSID<>?"; 	
					updateFineVal+= " AND " + condizione;
					
					
					psDtFineVal = conn.prepareStatement(updateFineVal);
					DataDwh dataFine = this.getTabella().getDtInizioVal();  
		
					psDtFineVal.setTimestamp(1,dataFine.getValore());
					psDtFineVal.setTimestamp(2,this.getTabella().getDtInizioVal().getValore());
					psDtFineVal.setString(3,this.getTabella().getId().getValore());
					psDtFineVal.setString(4,this.getTabella().getProcessid().getValore());
					psDtFineVal.execute();
		} else {
			String updateFineVal = "DELETE " + nomeTabella + " WHERE DT_FINE_VAL IS NULL AND DT_INIZIO_VAL <=? AND ID<>? AND PROCESSID<>?"; 	
			updateFineVal+= " AND " + condizione;
			
			
			psDtFineVal = conn.prepareStatement(updateFineVal);

			psDtFineVal.setTimestamp(1,this.getTabella().getDtInizioVal().getValore());
			psDtFineVal.setString(2,this.getTabella().getId().getValore());
			psDtFineVal.setString(3,this.getTabella().getProcessid().getValore());
			psDtFineVal.execute();
		}
		} catch (Exception e) {
			throw new DaoException("Problema ! Impossibile far scadere record precedenti a quello inserito!");
		} 
		finally {
			try {
				DbUtils.close(psDtFineVal);
			} catch (SQLException e) {
				throw new DaoException("Problema su db! Impossibile far scadere record precedenti a quello inserito!");
			}
		} 

		
	}
	
	/**
	 * Metodo che agisce in presenza di mancanza di chiave 
	 * per storicizzare o cancellare i record che abbiano la stesso valore per la masterTable relativa 
	 */
	private void manageIdNulll() throws DaoException{
		// esco se chiave <> null , cioè se hash e idorig non coincidono
		if (tabella.getCtrHash().getValore()!=null && (!tabella.getCtrHash().getValore().equals(tabella.getIdOrig().getValore())))
			return;

		try {
			
			ArrayList<Method> methodsMasterTables = this.tableWithMasterTables.get(tabella.getClass());
			if (methodsMasterTables==null ||  methodsMasterTables.isEmpty())
				return;
	
			String where="1=1 ";
			for (Method method : methodsMasterTables) {
				try {
					Relazione rel = (Relazione) method.invoke(tabella);
					String chiaveEsternaMaster = (String) rel.getRelazione().getValore();
					
					String nomeCampoRelazione = StringUtils.JavaName2NomeCampo(method.getName().substring(3));
					where += "AND " + nomeCampoRelazione +"='" +chiaveEsternaMaster + "'";
					
				} catch (Exception e) {
					log.error("Errore cercando di ricavare la chiave di relazione su tabella Master da tabella " + tabella,e);
					new DaoException("Errore cercando di ricavare la chiave di relazione su tabella Master da tabella " + tabella);
				}
				
			}
			// se lo storico e' disattivato allora cancello i record precedenti con stessa chiave master
			// altrimenti li annullo con dt_fine_val
			if (disattivaStorico) {
				faiScadereRecordsConMasterUgiale(where, true);
			} else 
				faiScadereRecordsConMasterUgiale(where, false);
		} catch (Exception e) {
			log.error("Errore gestendo Id null " + tabella,e);
			new DaoException("Errore cercando di gestire Id a null " + tabella);
		}

	}
	
	
	
	/**
	 * @param belfiore
	 * @return 1=update eseguito , 0=update non eseguito , -1 = record non trovato
	 * @throws DaoException
	 */
	private int manageDisattivaStorico(String belfiore) throws DaoException {
		PreparedStatement psId =null;
		ResultSet rsId = null;
		try {
			//**** se esiste un record con lo stesso ID e hash diverso 
			//**** allora presumo sia un aggiornamento del dato
			String tab = inReplace?nomeTabellaTMP:nomeTabella;
			String controlloID = "SELECT * FROM " + tab + " WHERE ID_EXT = ?";
			psId = conn.prepareStatement(controlloID);
			psId.setString(1,this.getTabella().getIdExt().getValore());
			rsId = psId.executeQuery();
			while (rsId.next()) {
				
				// nel aso che lo storico sia disattivato devo semplicemente vedere se per il record selezionato
				// è avvenuto un aggiornamento : in tal caso aggiorno il record
				// altrimenti esco e non faccio nulla
					if (!rsId.getString("CTR_HASH").equals(this.getTabella().getCtrHash().getValore())) {
						updateNoStessoID(belfiore);
						return 1;
					} else {
						return 0;
					}
			}		
			return -1;
		} catch (Exception e ) {
			throw new DaoException(e);
		}
		finally {
			try {
				DbUtils.close(rsId);
				DbUtils.close(psId);
			} catch (Exception e){
				log.error("Impossibile chiudere resultset in manageDisattivaStorico");
				throw new DaoException(e);
			}
		}
		
	}

	/**
	 * Il metodo E'in grado di preparare ed eseguire una insert sulla tabella specificata nel costruttore dell'oggetto
	 * Prima però controlla che non ci sia un altro record con lo stesso Id, in caso affermativo se Hash diverso ne fa l'update 
	 * altrimenti esce senza fare nulla.
	 * @param conn - connessione al db
	 * @return boolean - indica se il record è stato salvato oppure no
	 * @throws DaoException
	 */
	public boolean save(String belfiore) throws DaoException
	{

		//se l'hash e' nullo, non deve fare nulla
		if (this.getTabella().getCtrHash().getValore() == null) {
			return false;
		}

		manageRicalcoloHash(belfiore);

		if (inReplace) {
			if (disattivaStorico) {
				if (tabella instanceof EseguiQueryInDisabilitaStorico) {
					int ret = manageDisattivaStorico(belfiore);
					// se non ho fatto la update allora posso fare la insert 
					if (ret!=1) 
						insert(false,belfiore); 
				} else
					insert(false,belfiore); 
				
				return true;
			}
		}
		
		

		
		Savepoint savept  = null;
		boolean bloccaInserimento = false;
		try {
			// TODO il savepoinrt non ho potuto metterlo perché dava gravi problemi di prestazioni
			// TODO  va cercato un workaround
			//if (false)
			//	if (!conn.getAutoCommit())
			//		savept = conn.setSavepoint();       // Create a savepoint
			
			
			boolean presentiAltriConIdExtUguale = false;
			boolean idExtFromSeq = false;
			
			
			try {
				//CASO PARTICOLARE:
				//ID_EXT e' SEMPRE DIVERSO perché VIENE DA UNA SEQUENCE
				//IN QUESTO CASO NON SI STORICIZZA MAI, SI CONTROLLA IL CAMPO CTR_HASH
				//QUINDI, SI FA INSERT SE SI HA UN HASH NUOVO, SE NO, NON SI FA NULLA
				if (this.getTabella() instanceof IdExtFromSequence && !disattivaStorico) {
					PreparedStatement psHash = null;
					ResultSet rsHash = null;					
					try {
						String nome = inReplace?nomeTabellaTMP:nomeTabella;
						String controlloHash = "SELECT * FROM " + nome + " WHERE CTR_HASH = ?";
						psHash = conn.prepareStatement(controlloHash);
						psHash.setString(1, this.getTabella().getCtrHash().getValore());
						rsHash = psHash.executeQuery();
						if (rsHash.next()) {
							return false;
						} else {
							idExtFromSeq = true;
						}
					} catch (Exception e) {
						throw e;
					} finally {
						if (rsHash != null)
							rsHash.close();
						if (psHash != null)
							psHash.close();
					}
				}
				//				
				
				if (!idExtFromSeq)  {
					if (disattivaStorico) {
						int ret = manageDisattivaStorico(belfiore);
						if (ret!=-1)
							return ret==1?true:false;
					} else {
						
						PreparedStatement psId =null;
						ResultSet rsId=null;
						
						try {
							//**** se esiste un record con lo stesso ID e hash diverso 
							//**** allora presumo sia un aggiornamento del dato
							String tab = inReplace?nomeTabellaTMP:nomeTabella;
							String controlloID = "SELECT * FROM " + tab + " WHERE ID_EXT = ? AND DT_FINE_VAL IS NULL ORDER BY DT_INIZIO_VAL DESC";
							psId = conn.prepareStatement(controlloID);
							psId.setString(1,this.getTabella().getIdExt().getValore());
							rsId = psId.executeQuery();
							while (rsId.next()) {
								
								gestioneDatoStessoIdExt(rsId);
								
								// se è la stessa fornitura del  record (dunque stessa DT_INIZIO_VAL) 
								// allora se ha un hash diverso vuol dire che è stato un aggiornamento
								// altrimenti lascio stare ed esco
								if (rsId.getString("ID").equals(this.getTabella().getId().getValore())) {
									
									// nel caso lo storico sia con dt_exp_dato è possibile che io abbia più record nello stesso giorno con chiave uguale
									// In questo caso controllo se hanno DT_INI_VAL_DATO diversa
									// Se questo è allora io devo cercare di inserirlo (che abbia o non Hash diverso)
									if (	this.getTabella().getFlagDtValDato().compareTo(BigDecimal.ONE)==-1
											&& (this.getTabella().getDtInizioDato().getValore()!=null && 
												!this.getTabella().getDtInizioDato().getValore().equals(rsId.getTimestamp("DT_INIZIO_DATO")))) {
											presentiAltriConIdExtUguale = true;	
											this.getTabella().getId().setValore(this.getTabella().getDtInizioDato() , this.getTabella().getCtrHash(), this.getTabella().getIdExt());
									} else {
										if (!rsId.getString("CTR_HASH").equals(this.getTabella().getCtrHash().getValore())) {
											updateStessoID(belfiore);
											return true;
										} else {
											return false;
										}
									}
								} else {
									if (!presentiAltriConIdExtUguale && rsId.getObject("DT_FINE_VAL") == null && rsId.getTimestamp("DT_INIZIO_VAL").getTime() > this.getTabella().getDtInizioVal().getValore().getTime()) {
										bloccaInserimento = true;
									}
									if (!presentiAltriConIdExtUguale && rsId.getObject("DT_FINE_VAL") == null && rsId.getTimestamp("DT_INIZIO_VAL").getTime() <= this.getTabella().getDtInizioVal().getValore().getTime()) {
										presentiAltriConIdExtUguale = true;
									}
									if (this.getTabella().getFlagDtValDato().compareTo(BigDecimal.ONE)==0) {
										// nel caso si parli di date val applicative
										// non posso andare in inserimento se l'hash e la data inizio val sono uguali (avrei lo stesso identico record!)
										if (rsId.getString("CTR_HASH").equals(this.getTabella().getCtrHash().getValore()) 
											&& rsId.getTimestamp("DT_INIZIO_VAL").equals(this.getTabella().getDtInizioVal().getValore())) {
											bloccaInserimento = true;
											break;
										}
									} else {
										// hanno lo stesso ID_EXT ma ID DIVERSO, 
										// PERò SE HANNO LO STESSO HASH SIGNIFICA CHE è UNA FORNITURA DUPLICATA DELLA STESSA INFORMAZIONE
										if (rsId.getString("CTR_HASH").equals(this.getTabella().getCtrHash().getValore()))  {
											// ASPETTA !!! Potrebbero esserci le eventuali date applicative diverse!!
											if 
											(
												(
												DateFormat.compare(rsId.getTimestamp("DT_INIZIO_DATO"),this.getTabella().getDtInizioDato().getValore())
												&& DateFormat.compare(rsId.getTimestamp("DT_FINE_DATO"),this.getTabella().getDtFineDato().getValore())
												)
											 &&
												(
														(rsId.getTimestamp("DT_INIZIO_DATO")!=null && this.getTabella().getDtInizioDato().getValore()!=null) 
														||
														(rsId.getTimestamp("DT_FINE_DATO")!=null && this.getTabella().getDtFineDato().getValore()!=null)  
	
												)
											)
											{
												// non faccio nulla visto che già c'è un record esattamente = a quello corrente - con date applicative valorizzate a non null
												bloccaInserimento = true;
												break;
											} else if (
													(rsId.getTimestamp("DT_INIZIO_DATO")==null && this.getTabella().getDtInizioDato().getValore()==null) 
													&&
													(rsId.getTimestamp("DT_FINE_DATO")==null && this.getTabella().getDtFineDato().getValore()==null)  
														) {
												// se sono tutte nulle allora e' come se fossero uguali, blocco ancora l'inserimento
												bloccaInserimento = true;
												break;
											} else if (
													// l'update del record già presente in archivio avviene solo se le date di inizio dato sono = (ipotizzo sia una chiusura applicativa)	
													(rsId.getTimestamp("DT_INIZIO_DATO")!=null && this.getTabella().getDtInizioDato().getValore()!= null) 
													&& 
													DateFormat.compare(rsId.getTimestamp("DT_INIZIO_DATO"),this.getTabella().getDtInizioDato().getValore())
													) {
												ChiaveEsterna ce = new ChiaveEsterna();
												ce.setValore(rsId.getString("ID_EXT"));
												update(ce,belfiore, false, rsId.getString("ID"));
												return true;
											}
										}
									}
										
								}
								
								// esco, mica vado a fare la insert !!!!!
							}
						} finally {
							try {
								DbUtils.close(rsId);
								DbUtils.close(psId);
							} catch (SQLException e) {
								log.error("Impossibile chiudere i cursori");
							}
						}
						
					}
				}
				
			} catch (Exception e) {
				throw e;
			} 

			if (!bloccaInserimento)
				insert(presentiAltriConIdExtUguale,belfiore);
			else
				return false;
			
			
			return true;
		    
		    
		    
		} catch (Exception e){
			
			log.info("Error Saving ID="+this.getTabella().getId().getValore() + "\n" +  " id_ext=" +this.getTabella().getIdExt().getValore());

			
			try
			{
				if (savept!=null)
				conn.rollback(savept);
				savept = null;
			}
			catch (SQLException e1)
			{
				String msg = "Problemi duranbte il rollback di una riga in insert" + nomeTabella;
				log.warn(msg,e1);
			}                       
			String msg = "Errore su insert tabella " + nomeTabella;
			log.error(msg,e);
			throw new DaoException(e);
		} finally {

		}
		
		
	}

	private static LinkedHashMap<String, Object> getValoriCustomSql(TabellaDwh tabella, String nomeTabella) throws Exception {
		LinkedHashMap<String, Object>  m = new LinkedHashMap<String, Object>() ;
		HashMap<String, Method> mappaMetodi = getMappametodiGET(tabella, nomeTabella);
		Set<Entry<String, Method>> metodiGet = mappaMetodi.entrySet();
		for (Iterator iterator = metodiGet.iterator(); iterator.hasNext();) {
			Entry<String, Method> entry = (Entry<String, Method>) iterator.next();
			m.put(entry.getKey(),entry.getValue().invoke(tabella,new Object[0]));
		}
		return m;
	}
	

	private static HashMap<String, Method> getMappametodiGET(TabellaDwh tabella, String nomeTabella) throws Exception
	{
		try {
			
			HashMap<String, Method> mappaMetodi;
			if (!hashGMethods.containsKey(nomeTabella))
				mappaMetodi = new HashMap<String, Method>();
			else {
				mappaMetodi = hashGMethods.get(nomeTabella);
				return mappaMetodi;
			}
			
				
			Set sets = null;
			GenericTuples.T2<DynaBean,HashMap<String,Object>> dyn = DwhUtils.getDynaInfoTable(tabella, false);
			sets = dyn.secondObj.entrySet();
			Iterator it = sets.iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				String setMethod = (String) entry.getKey();
				String campo =  setMethod.substring(3).substring(0, 1).toLowerCase() +  setMethod.substring(3).substring(1,  setMethod.substring(3).length());
				String campoUpper =  campo.substring(0, 1).toUpperCase() +  campo.substring(1,  campo.length());
				String campoDB = StringUtils.JavaName2NomeCampo(campo);
				// se vero significa che non è un campo standard ma uno custom della particolare tabella
				if (!campiSqlStandard.contains(campoDB)) {
					boolean sequenceConn = tabella instanceof IdExtFromSequence && (campo.equalsIgnoreCase("sequenceConn") || campo.equalsIgnoreCase("idOrig"));
					if (!sequenceConn) {
						Method gmeth = tabella.getClass().getMethod("get" + campoUpper, new Class[0]);
						mappaMetodi.put(campoDB, gmeth);
					}					
				}
			}
			hashGMethods.put(nomeTabella, mappaMetodi);
			return mappaMetodi;
		} catch (Exception e) {
			log.error("Impossibile recuperare i nomi e i valori dei campi della tabella custom " + nomeTabella ,e);
			throw e;
		}
	}


	

	public void setConnection(Connection conn)
	{
		this.conn= conn;
		
	}

	public static void main(String args[]) {
		
		Connection conn =null;
		try  {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			 conn = DriverManager.getConnection ("jdbc:oracle:thin:diogene/diogene@praga:1521:praga");
			conn.setAutoCommit(false);
			
			PreparedStatement stmt = conn.prepareStatement(
			"INSERT INTO PIPPO (ID) VALUES (?)");

		stmt.setInt(1, 7);
		stmt.addBatch();

		stmt.setInt(1, 1);
		stmt.addBatch();
		 
		stmt.setInt(1, 9);
		stmt.addBatch();

		int[] updateCounts = stmt.executeBatch();	
		} catch (BatchUpdateException e) {
			int[] a = e.getUpdateCounts();
			log.error(e);
			
		}catch (Exception e) {
			log.error(e);
		}
		try {
			conn.commit();
		} catch (SQLException e) {
			log.error(e);
		}
		

	}
	
	//usato in SitDPersona
	protected void gestioneDatoStessoIdExt(ResultSet rs) {
	}
		

	
}
