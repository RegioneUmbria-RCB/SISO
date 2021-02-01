package it.webred.ct.proc.ario.caricatori;

//import it.webred.ct.proc.ario.bean.SitRicercaTotale;
import it.webred.ct.proc.ario.bean.HashParametriConfBean;
import it.webred.ct.proc.ario.fonti.DatoDwh;
import it.webred.ct.proc.ario.utils.ArioUtil;
import it.webred.rulengine.exception.RulEngineException;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;
import org.jboss.aop.microcontainer.beans.metadata.FinallyAdviceData;


public class TipoCaricatore {
	
	protected static final Logger log = Logger.getLogger(TipoCaricatore.class.getName());
		
	String sqlEnteSorg    		= null;	
	
	public static Properties props = null;
	public String codEnte;
	private Connection connForInsert;
		
	public TipoCaricatore(String codEnte) {
		super();
		this.codEnte = codEnte;
		
		sqlEnteSorg    			= getProperty("SQL_ENTE_SORGENTE");		
					
	}
	
	
	
	public void elaboraDatiToInsert(DatoDwh classeFonte, String codEnte, HashParametriConfBean paramConfBean) throws CaricatoriException {
		
		int contaGiri = 0;
		long totaleGiri = 0;
		
		try {
						
			ArioUtil au = new ArioUtil();	
			
			contaGiri = 0;
			totaleGiri = contaGiri;
						
			if(classeFonte.existProcessId()){
				
				//########################### GESTIONE TRAMITE PRODESSID ##################################################################
				log.info("############ INFO:Gestione con ProcessID");
								
				//Recupero parametro tabella droppata
				String fonte = String.valueOf(classeFonte.getFkEnteSorgente());				
				boolean tabellaDroppata = this.recuperoTabellaDroppata(codEnte, fonte, paramConfBean.getTabelleDroppate());
				
								
				//if (isDrop){
				
				if (tabellaDroppata){
					
					//Controllo se effettivamente ci sono ProcessID differenti
					boolean isDrop = classeFonte.dwhIsDrop(connForInsert);
					
					if (isDrop){
						//tabella droppata - rieffettuare il caricamento totale dei dati
						log.info("############ INFO:tabella droppata - rieffettuare il caricamento totale dei dati");
						
						//Cancellazione tabella ProcessID
						au.deleteSitCorrelazioneProcessID(classeFonte,connForInsert);																				
						
						String sqlDeleteTot = "";
						String sqlInsertTot = "";
						
						//Determino la query di delete totale
						sqlDeleteTot = classeFonte.getDeleteSQL();										
											
						//elimino record della fonte gia' inseriti in TOTALE.																
						au.deleteDatoTotale(connForInsert,sqlDeleteTot, classeFonte);
										
						
						//Carica tutti i record da tabella DHW
						String sqlAllCaricamentoDWH = classeFonte.getSql(null);					
						
						try {
							ResultSet rs = au.getCaricamenti(connForInsert,classeFonte,sqlAllCaricamentoDWH,codEnte,null,classeFonte.getFkEnteSorgente());
							try {
								while(rs.next()){						
									String salvaSQL = classeFonte.getInsertSQL();												
									classeFonte.prepareSaveDato(classeFonte, connForInsert, salvaSQL, rs, codEnte, paramConfBean);		
									
									//Controllo elementi caricati per commit
									contaGiri ++;
									totaleGiri++;
									if(contaGiri >= 100000){								
										log.info("Elaborazione di " + totaleGiri + " elementi per fonte " + classeFonte.getFkEnteSorgente() + "." + classeFonte.getProgEs());
										//connForInsert.commit();
										contaGiri = 0;
									}
								}
							} finally {
								DbUtils.close(rs);
							}
						} finally {
						}						
						
						//Recupero i tutti i processID della tabella di caricamento DWH									
							try {
								ResultSet rs = au.getAllProcessIdFromTabellaDWH(connForInsert, classeFonte.getTable());
								try {			
									//aggiorno la tabella di correlazione						
									while(rs.next()){
										
										Date data_agg = null;
										String sql = "";
										
										String procID = rs.getString("prID");	
										data_agg = new Date(System.currentTimeMillis());	
										
										boolean pIdTrovato = au.getTrovaProcessId(connForInsert,procID,classeFonte);
										
										if(pIdTrovato){
											sql = classeFonte.getQuerySQLUpdateProcessId();
											au.updateProcessID(connForInsert, sql, classeFonte.getFkEnteSorgente(), classeFonte.getProgEs(), procID, data_agg);
										}else{
											sql = classeFonte.getQuerySQLSaveProcessId();
											au.setProcessID(connForInsert, sql, classeFonte.getFkEnteSorgente(), classeFonte.getProgEs(), procID, data_agg);
										}
									}
								
								} finally {
									DbUtils.close(rs);
								}			
							} finally {
							}			
					}
						
				}else{
					//########## Tabella non droppata - caricare solo i dati aggiunti/aggiornati ###############################à
					log.info("############ INFO:Tabella non droppata - caricare solo i dati aggiunti/aggiornati");			
					
					//Lettura dei ProcessID da tabella Indice
					ResultSet pID = null;
					try {
						pID = au.getProcessidFromIndice(classeFonte, connForInsert, classeFonte.getFkEnteSorgente(), classeFonte.getProgEs());						
						
						//Fase di primo caricamento - non ci sono pID nella tabella di correlazione
						if(!pID.next()){
															
							String sqlDeleteTot = "";
							String sqlInsertTot = "";
							
							//Determino la query di delete totale
							sqlDeleteTot = classeFonte.getDeleteSQL();												
												
							//elimino record della fonte gia' inseriti in TOTALE.																
							au.deleteDatoTotale(connForInsert,sqlDeleteTot, classeFonte);
																	
							//Carica tutti i record da tabella DHW
							String sqlAllCaricamentoDWH = classeFonte.getSql(null);					
							
							try {								
							
								ResultSet rs = au.getCaricamenti(connForInsert,classeFonte,sqlAllCaricamentoDWH,codEnte,null,classeFonte.getFkEnteSorgente());
								try {	
									String salvaSQL = classeFonte.getInsertSQL();
									
									log.info("############ INFO:Inizio salvataggio dati in Tabella TOTALE");
									while(rs.next()){																										
										classeFonte.prepareSaveDato(classeFonte, connForInsert, salvaSQL, rs, codEnte, paramConfBean);	
											
										//Controllo elementi caricati per commit
										contaGiri ++;
										totaleGiri++;
										if(contaGiri >= 100000){								
											log.info("Elaborazione di " + totaleGiri + " elementi per fonte " + classeFonte.getFkEnteSorgente() + "." + classeFonte.getProgEs());
											//connForInsert.commit();
											contaGiri = 0;
										}
									}
								} finally {
									DbUtils.close(rs);
								}
							} finally {
							}
														
							try {
								//Recupero i tutti i processID della tabella di caricamento DWH										
								ResultSet rs = au.getAllProcessIdFromTabellaDWH(connForInsert, classeFonte.getTable());
								try {
																						
									//aggiorno la tabella di correlazione						
									log.info("############ INFO:Inizio Aggiornamento tabella processID");
									while(rs.next()){
										
										Date data_agg = null;
										String sql = "";
										
										String procID = rs.getString("prID");	
										data_agg = new Date(System.currentTimeMillis());	
										
										boolean pIdTrovato = au.getTrovaProcessId(connForInsert,procID, classeFonte);
										
										if(pIdTrovato){
											sql = classeFonte.getQuerySQLUpdateProcessId();
											au.updateProcessID(connForInsert, sql, classeFonte.getFkEnteSorgente(), classeFonte.getProgEs(), procID, data_agg);
										}else{
											sql = classeFonte.getQuerySQLSaveProcessId();
											au.setProcessID(connForInsert, sql, classeFonte.getFkEnteSorgente(), classeFonte.getProgEs(), procID, data_agg);
										}			
									}	
								} finally {
									DbUtils.close(rs);
								}
							} finally {
							}
							log.info("############ INFO:Aggiornamento tabella processID eseguito");
							
							
						}else{
							//Fase di caricamenti successivi - ci sono pID nella tabella di correlazione
							log.info("############ INFO:Fase di caricamenti successivi - ci sono pID nella tabella di correlazione");				
							
							ResultSet newProcId = null;
							try {
								
								//Determino i ProcessID da caricare
								newProcId = au.getNewProcessIdFromTabellaDWH(connForInsert,classeFonte, classeFonte.getFkEnteSorgente(), classeFonte.getTable());
																	
								//Carico i nuovi processi
								while(newProcId.next()){
																		
									String procId = newProcId.getString("PROCESSID");
									
									//Carica tutti i nuovi record da tabella DHW
									String sqlAllCaricamentoDWH = classeFonte.getSql(procId);
									
									boolean aggiornaTabProcessID = false;
									try {
										ResultSet rs = au.getCaricamenti(connForInsert,classeFonte,sqlAllCaricamentoDWH,codEnte,procId,classeFonte.getFkEnteSorgente());
												
										try {
											
																		
											
											String insertSQL = classeFonte.getInsertSQL();
											String updateSQL = classeFonte.getUpdateSQL();
											String searchSQL = classeFonte.getSearchSQL();
											while(rs.next()){																															
												classeFonte.prepareUpdateDato(classeFonte, connForInsert, insertSQL, updateSQL, searchSQL, rs, codEnte, paramConfBean);
												aggiornaTabProcessID = true;	
												
												//Controllo elementi caricati per commit
												contaGiri ++;
												totaleGiri++;
												if(contaGiri >= 100000){								
													log.info("Elaborazione di " + totaleGiri + " elementi per fonte " + classeFonte.getFkEnteSorgente() + "." + classeFonte.getProgEs());
													//connForInsert.commit();
													contaGiri = 0;
												}
											}
										} finally {
											DbUtils.close(rs);
										}
									} finally {
									}
									//Aggiorno la tabella di correlazione se sono stati inseriti nuovi soggetti con il processID indicato	
									if(aggiornaTabProcessID){
										
										Date data_agg = null;
										String sql = "";
																	
										data_agg = new Date(System.currentTimeMillis());	
										
										boolean pIdTrovato = au.getTrovaProcessId(connForInsert,procId,classeFonte);
										
										if(pIdTrovato){
											sql = classeFonte.getQuerySQLUpdateProcessId();
											au.updateProcessID(connForInsert, sql, classeFonte.getFkEnteSorgente(), classeFonte.getProgEs(), procId, data_agg);
										}else{
											sql = classeFonte.getQuerySQLSaveProcessId();
											au.setProcessID(connForInsert, sql, classeFonte.getFkEnteSorgente(), classeFonte.getProgEs(), procId, data_agg);
										}
																	
									}	
								}
							} finally {
								DbUtils.close(newProcId);
							}
						}
					} finally {
						DbUtils.close(pID);
					}
		
				}
			
			}else{
				
				//GESTIONE SENZA PROCESSID
				log.info("############ INFO:GESTIONE SENZA PROCESSID");				
				
				
				//Recupero parametro tabella droppata
				String fonte = String.valueOf(classeFonte.getFkEnteSorgente());				
				boolean tabellaDroppata = this.recuperoTabellaDroppata(codEnte, fonte, paramConfBean.getTabelleDroppate());
								
				
				if(tabellaDroppata){
					//Cancello tutti i record del DWH se la tabella è stata droppata					
					String sqlDeleteTot = "";					
					
					//Determino la query di delete totale
					sqlDeleteTot = classeFonte.getDeleteSQL();											
										
					//elimino record della fonte gia' inseriti in TOTALE.																
					au.deleteDatoTotale(connForInsert,sqlDeleteTot, classeFonte);
				
					connForInsert.commit();
				}
				
				
				//Carica tutti i record da tabella DHW con query che confronta se non sono inseriti
				String sqlAllCaricamentoDWH = classeFonte.getSql(null);				
				
				try {
					ResultSet rs = au.getCaricamentiNoProcessID(connForInsert,classeFonte,sqlAllCaricamentoDWH,codEnte);
					try {
						
						//Inserisco i Record in tabelle TOTALI
						String insertSQL = classeFonte.getInsertSQL();				
						while(rs.next()){																			
							classeFonte.prepareSaveDato(classeFonte, connForInsert, insertSQL, rs, codEnte, paramConfBean);	
							
							//Controllo elementi caricati per commit
							contaGiri ++;
							totaleGiri++;
							if(contaGiri >= 100000){								
								log.info("Elaborazione di " + totaleGiri + " elementi per fonte " + classeFonte.getFkEnteSorgente() + "." + classeFonte.getProgEs());
								//connForInsert.commit();
								contaGiri = 0;
							}
						}
					} finally {
						DbUtils.close(rs);
					}
				} finally {
				}
			}											
						
		} catch (Exception e) {
			log.error("Errore in selezione da caricare",e);
			CaricatoriException ea = new CaricatoriException("Errore in selezione da caricare :",e);
			throw ea;
		}
		
	}
		
	
	//Metodo per controllare la presenza dell'ente Sorgente nel DB		
	public boolean iSEnteSorgentePresente(DatoDwh classeFonte) throws Exception
	{
		String codiceEnte = "";
		boolean result = false;
		try
		{
			int enteS = 0;
			PreparedStatement ps = connForInsert.prepareStatement(sqlEnteSorg);
			
			codiceEnte = Integer.toString(classeFonte.getFkEnteSorgente());
			
			ps.setString(1, codiceEnte);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
			{
				enteS = rs.getInt("ID");
			}
			rs.close();
			ps.close();
			
						
			if(enteS == 0){
				result = false;
			}else{
				result = true;
			}
			
			return result;
			
		}catch (SQLException sqle){
			throw new RulEngineException("get SIT_ENTE_SORGENTE per ente "+codiceEnte+":"+sqle.getMessage());
		}
	}
	

	public String getProperty(String propName) {
		
		if (props==null) {
			
			//Caricamento del file di properties dei caricatori		
	        props = new Properties();
	        try {
	            InputStream is = this.getClass().getResourceAsStream("/sql/caricatori.sql");
	            props.load(is);                     
	        }catch(Exception e) {
	            log.error("Eccezione: "+e.getMessage());
	            return null;
	        }
		}
		
		String p = props.getProperty(propName+ "." + codEnte);
		
		if (p==null)
			p = props.getProperty(propName);
			
		return p;
	}
	
	
	public void setConnForInsert(Connection connForInsert) {
		this.connForInsert = connForInsert;
	}
	
	protected boolean recuperoTabellaDroppata(String codEnte, String fonte, Hashtable<String, Object> tabelleDroppate) throws Exception {
		
		boolean tabDrop = false;		
		
		try{
			
			
			String chiave = "fornitura.in.replace."+fonte;
			
			String param = (String)tabelleDroppate.get(chiave);
			
			if(param!=null){
				if(param.equals("1")){
					tabDrop = true;
				}else{
					tabDrop = false;
				}
			}
						
		}catch (Exception e) {
			throw new RulEngineException("Errore in recupero tabelle droppate: " + e.getMessage());
		}
		
		return tabDrop;
	}
			
}
