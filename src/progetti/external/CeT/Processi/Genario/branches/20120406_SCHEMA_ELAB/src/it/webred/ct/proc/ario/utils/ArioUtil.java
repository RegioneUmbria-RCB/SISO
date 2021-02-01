package it.webred.ct.proc.ario.utils;

import it.webred.ct.proc.ario.bean.SitCorrelazioneProcessId;
import it.webred.ct.proc.ario.fonti.DatoDwh;
import it.webred.rulengine.exception.RulEngineException;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;


public class ArioUtil {

	protected static final Logger log = Logger.getLogger(ArioUtil.class.getName());
	public static Properties props = null;
	public String codEnte;
		
	
	//Metodo che effettua la cancellazione dei ProcessID nella tabella di correlazione
	public void deleteSitCorrelazioneProcessID(DatoDwh classeFonte, Connection conn) throws Exception{
		
		try{							
					
			PreparedStatement ps = null;
			String sql = classeFonte.getQuerySQLDeleteProcessId();
									
						 		
			ps = conn.prepareStatement(sql);
			ps.setInt(1, classeFonte.getFkEnteSorgente());	
			ps.setInt(2, classeFonte.getProgEs());	
			
			ps.executeUpdate();
			
			//Committo a seguito della delete
			conn.commit();
			
			
			//Cancello eventuali record con tutte date null
			sql = this.getProperty("SQL_DELETE_ALL_PID");
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, classeFonte.getFkEnteSorgente());
			ps.setInt(2, classeFonte.getProgEs());
			
			ps.executeUpdate();
									
			ps.close();
			
			//Committo a seguito della delete
			conn.commit();
						
		}catch (SQLException sqle){
			throw new RulEngineException("Delete tabella TOTALE per ente "+classeFonte.getFkEnteSorgente()+":"+sqle.getMessage());
		}
	}
	
	
	
	//Metodo che legge la tabella dei processID
	public ResultSet getProcessidFromIndice(DatoDwh classeFonte, Connection conn, int codEnteSorgente, int progEs) throws Exception
	{	
		try
		{			
					
			//Determino i processId caricati in base alla tipologia di caricatore
			String sql = classeFonte.getQuerySQLgetProcessId();
			
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, codEnteSorgente);
			ps.setInt(2, progEs);
			ResultSet rs = ps.executeQuery();
				
			return rs;
			
		}catch (SQLException sqle){
			throw new RulEngineException("get SIT_CORRELAZIONE_PROCESSID per ente "+codEnteSorgente+":"+sqle.getMessage());
		}
				
	}
	
	
	//Metodo che restituisce tutti i processID della tabella del DWH
	public ResultSet getAllProcessIdFromTabellaDWH(Connection conn, String tabella) throws Exception
	{
		Statement st = null;
		ResultSet rs  = null;
		try{			
			st = conn.createStatement();
						
			String sql = this.getProperty("SQL_GET_ALL_PID_DWH");
			sql = sql + " "+ tabella;
			
			rs = st.executeQuery(sql); 
						
			return rs;
			
		}catch (SQLException sqle){
			throw new RulEngineException("Restituzione ProcessID da tabella: "+ sqle.getMessage());
				
		} finally {
		}
	}	
	
	
	//Metodo che scrive il nuovo ProcessID caricato
	public void setProcessID(Connection conn, String sql, int codEnteSorgente, int progEs, String procID, Date dataAggiornamento) throws Exception
	{
						
		try
		{		
			
			SitCorrelazioneProcessId scpId = new SitCorrelazioneProcessId();
			scpId.setProcessId(procID);			
			scpId.setIdEnte(codEnteSorgente);
			scpId.setDataAggiornamento(dataAggiornamento);		
			scpId.setProgEs(progEs);
			
			
			QueryRunner ins = new QueryRunner();
			// tolgo il sedime eventualmente gia duplicato nella descrizione
			Object[] paramsIns = new Object[] { 
					scpId.getProcessId(),					
					scpId.getIdEnte(),	
					scpId.getDataAggiornamento(),
					scpId.getProgEs()
			};
			ins.update(conn, sql, paramsIns);
			
			conn.commit();
								
		}catch (SQLException sqle){
				//verifico se e' errore diverso da chiave duplicata
				if (sqle.getErrorCode() != 1 && sqle.getErrorCode() != 1401) 
					throw sqle;
				else
					log.warn("Inserimento SIT_CORRELAZIONE_PROCESSID "+sqle.getMessage(), sqle);
			}
			finally {
			}		
	}
	
	
	//Metodo che aggiorna il ProcessID presente
	public void updateProcessID(Connection conn, String sql, int codEnteSorgente, int progEs, String procID, Date dataAggiornamento) throws Exception
	{
				
		try{		
			
			SitCorrelazioneProcessId scpId = new SitCorrelazioneProcessId();
			scpId.setProcessId(procID);			
			scpId.setIdEnte(codEnteSorgente);
			scpId.setDataAggiornamento(dataAggiornamento);
			scpId.setProgEs(progEs);
			
			
			QueryRunner ins = new QueryRunner();
			// tolgo il sedime eventualmente gia duplicato nella descrizione
			Object[] paramsIns = new Object[] { 					
					scpId.getDataAggiornamento(),
					scpId.getProcessId(),
					scpId.getIdEnte(),
					scpId.getProgEs()
			};
			ins.update(conn, sql, paramsIns);
			
			conn.commit();
			
								
		}catch (SQLException sqle){
				//verifico se e' errore diverso da chiave duplicata
				if (sqle.getErrorCode() != 1 && sqle.getErrorCode() != 1401) 
					throw sqle;
				else
					log.warn("Inserimento SIT_CORRELAZIONE_PROCESSID "+sqle.getMessage(), sqle);
				    throw new Exception("Inserimento SIT_CORRELAZIONE_PROCESSID  - ERRORE:" + sqle.getMessage());
			}
			finally {
			}		
	}
			
				
	//Metodo che controlla se un PROCESSID per una tipologia di caricamento è già presente
	public boolean getTrovaProcessId(Connection conn, String pId, DatoDwh classeFonte) throws Exception{
		
		boolean trovato = false;

		PreparedStatement prepSt = null;
		ResultSet resSet = null;
		
		try{	
		
			String sql = this.getProperty("SQL_PID_EXIST");

			prepSt = conn.prepareStatement(sql);
					
			prepSt.setString(1, pId);
			prepSt.setInt(2, classeFonte.getFkEnteSorgente());
			prepSt.setInt(3, classeFonte.getProgEs());
							
			resSet = prepSt.executeQuery();
			
			if(resSet.next())
				trovato = true;

		}catch (Exception sqle){
				throw new RulEngineException("Lettura di un processId da tabelle SIT_CORRELAZIONE_PROCESSID - ERRORE:"+sqle.getMessage());
		}			

		resSet.close();
		prepSt.close();
			
		
		return trovato;
			
	}
	
	
	//Metodo che carica i dati dal DWH per casistica con ProcessId
	public ResultSet getCaricamenti(Connection conn,DatoDwh classeFonte, String sql, String codEnte, String processId, int codEnteSorgente) throws Exception {
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{			
			log.debug("query fonte: " + codEnteSorgente + "->" + sql);		
			ps = conn.prepareStatement(sql);// ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);			
			
			//Controllo se nella query va settato il PROCESSID
			if (processId != null && !processId.equals(""))
				ps.setString(1, processId);
					
			//Imposto il parametro codEnte per un eventuale ricerca (no ProcessID)
			if (classeFonte.queryWithParamCodEnte())
				ps.setString(1, codEnte);
				
							
			rs = ps.executeQuery();

				
			return rs;
			
		}catch (SQLException sqle){
			log.error("Query in errore :" + sql,sqle);
			throw new RulEngineException("Caricamento totale da tabella per ente codice :" + codEnteSorgente+" ERRORE:"+sqle.getMessage());
		} finally {
		}
			
	}
	
	
	//Metodo che carica i dati dal DWH per casistica con ProcessId
	public ResultSet getCaricamentiNoProcessID(Connection conn,DatoDwh classeFonte, String sql, String codEnte) throws Exception {
			
		ResultSet rs = null;
		try
		{			
					
			//System.out.println(sql);
			log.debug("Query di lettura DWH: "+sql);
			
			PreparedStatement ps = conn.prepareStatement(sql);			
			
			//Controllo se la stringa sql ha almeno un parametro			
			int res = sql.indexOf("?");
			
			if(res > -1){
				//Imposto il parametro codEnte per un eventuale ricerca 
				if (classeFonte.queryWithParamCodEnte()){				
					ps.setString(1, codEnte);			
					ps.setInt(2, classeFonte.getFkEnteSorgente());
					ps.setInt(3,classeFonte.getProgEs());
				}else{
					ps.setInt(1, classeFonte.getFkEnteSorgente());
					ps.setInt(2,classeFonte.getProgEs());
				}
			}	
							
			rs = ps.executeQuery();

				
			return rs;
			
		}catch (SQLException sqle){
			throw new RulEngineException("Caricamento totale da tabella per ente codice :" + classeFonte.getFkEnteSorgente()+" ERRORE:"+sqle.getMessage() + " SQL: " + sql);
		}		
			
	}
	
	
	//Metodo che effettua la cancellazione delle tabelle TOTALI
	public void deleteDatoTotale(Connection conn, String sqlDelete, DatoDwh classeFonte) throws Exception
	{
		try
		{					
			PreparedStatement ps = conn.prepareStatement(sqlDelete);
			
			int enteSorgente = classeFonte.getFkEnteSorgente();
			int progEs = classeFonte.getProgEs();
			
			ps.setInt(1, enteSorgente);
			ps.setInt(2, progEs);
			ps.executeUpdate();
			ps.close();		
			
			//Committo a seguito della delete
			conn.commit();
			
		}catch (SQLException sqle){
			throw new RulEngineException("Delete tabella TOTALE per ente "+classeFonte.getFkEnteSorgente()+":"+sqle.getMessage());
		}
	}

	
	
	//Metodo che trova i nuovi ProcessId del DHW da dover inserire
	public ResultSet getNewProcessIdFromTabellaDWH(Connection conn,DatoDwh classeFonte, int enteSorg, String tab) throws Exception
	{
		ResultSet rs = null;
		try{						
			
			//Determino i processId caricati in base alla tipologia di caricatore			
			String sql2 = classeFonte.getQuerySQLNewProcessId();
																		
			String sql = "SELECT distinct(processid) FROM "+ tab + " tabDWH WHERE NOT EXISTS " + sql2;															
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, classeFonte.getFkEnteSorgente());
			ps.setInt(2, classeFonte.getProgEs());
			
			rs = ps.executeQuery();
						
			return rs;
			
		}catch (SQLException sqle){
			throw new RulEngineException("Restituzione Nuovi ProcessID da tabella: "+ sqle.getMessage());
				
		}
	}	
			
	
	private String getProperty(String propName) {
		
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
	
	
}
