package it.webred.rulengine.brick.reperimento.executor.timertask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import it.webred.rulengine.brick.reperimento.executor.bean.Processo;
import it.webred.rulengine.brick.reperimento.executor.logic.ListapProcessoLogic;
import it.webred.rulengine.brick.reperimento.executor.logic.ProcessMonitorUpdater;



public class WaitForProcessUntilExecute {
	private static final Logger log = Logger.getLogger(WaitForProcessUntilExecute.class.getName());
	
	private Processo p;
    private String processid;
	boolean finitaEsecuzione =false;
	boolean problemiInvoke = false;
    

	public WaitForProcessUntilExecute(Processo p, String processid) {
		super();
		this.p = p;
		this.processid = processid;
	}
	
	/**
     * Controlla lo stato dell'esecuzione processo nella tabella C_EXEC_PROCESSO
     * Tale tabella contiene un campo STATO che viene valorizzato dai controlli thread dello stato
     * VerificaEsito..... 
     * @return
     */
    public boolean checkStatoEscuzioneInLocal() {
    	return true;
    }
    
    public boolean waitUntilExcuted ()    throws InterruptedException {
    	
	    final Object monitor = new Object ();
	
	    synchronized (monitor)
	    {
	        Runnable r = new Runnable ()
	        {
	            public void run ()
	            {
	                try
	                {
						log.debug(" waitUntilExcuted , inizio ciclo while (true)");
						while (true)
	                    {
	                    	Connection conn = null;
	                    	ResultSet rs = null;
	                    	PreparedStatement st =null;
	                    	try {
	                    		
		                        	log.debug("Sto aspettando la fine del processo " + processid);
		                			//recupero i dati in tabella C_EXEC_PROCESSO
		                			String sql = "select ID, FK_EXEC_LISTAP,FK_COD_LISTA, FK_COD_PROCESSO, STATO, ESITO, DT_START, PROCESSID, PRESENTI_WARNING "
		                				+ " from C_EXEC_PROCESSO "
		                				+ " WHERE processid = ?";
			                    	conn = getConnection();
		                			st = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		                			st.setString(1, processid);
		                			rs = st.executeQuery();
		                			while (rs.next()) {
		                				if (ListapProcessoLogic.STATO_CONCLUSO.equals(rs.getObject("STATO"))) {

		                					finitaEsecuzione = true;
		                					log.info("FINITA ESECUZIONE " + processid);
		                					if (ListapProcessoLogic.ESITO_CHIAMATA_WS_CARONTE_FALLITA.equals(rs.getObject("ESITO")))
		                							problemiInvoke = true;
			                        		//aggiornamento stato monitor processi
		                					//ProcessMonitorUpdater.aggiornaMonitor(conn,rs.getString("ESITO"), processid);
		                				}
		                			}
		                			
		                			rs.close();
		                			st.close();
	
		                        	if (finitaEsecuzione) {
		                        		log.debug("Processo " + processid + " concluso");
		                        		if (problemiInvoke) {
		                        			log.error("Problemi invoke caronte ws ");
		                        			break;
		                        		} else
		                        			break;
		                        	}
	
		                            Thread.sleep (60000);  // ogni 60 secondi vado a vedere se è finito
	                    	} finally {
	                			try
	                			{
	                				DbUtils.close(rs);
	                				DbUtils.close(st);
	                				DbUtils.close(conn);
	                			}
	                			catch (Exception e)
	                			{
	                				log.warn(e);
	                			}
	                    	}
	
	                    }
	                }
	                catch (InterruptedException e) { 
	                	log.error("Errore durante attesa per fine esecuzione",e);
	                }
	                catch (Exception e) { 
	                	log.error("Errore durante attesa per fine esecuzione",e);
	                }
	
	                synchronized (monitor)
	                {
	                    monitor.notify ();
	                }
	            }
	        };
	
	        Thread t = new Thread (r);
	        t.start ();
	
	        try {
	        	// timeout in millisecondi (24 ore di default)
	        	long timeOut = 1000 * 60 * 60 * 24;
	        	if (p.getTimeout()!=null)
	        		timeOut = p.getTimeout().longValue();
	
	        	monitor.wait(timeOut);
	        } finally {
	            t.interrupt ();
	        }
	    }
	
	    if (finitaEsecuzione && problemiInvoke)
	    	throw new InterruptedException("problemi invoke caronte WS");

	    return finitaEsecuzione;
	}
	
	public Connection getConnection() throws SQLException, NamingException {
		//return getConnection("controller");
		return getConnection("REngineDS");
	}
	
	public Connection getConnection(String datasource ) throws SQLException, NamingException {
		Context initContext = new InitialContext();
		//Context envContext = (Context) initContext.lookup("java:/comp/env");
		//DataSource ds = (DataSource) envContext.lookup("jdbc/" + datasource);
		//TODO
		DataSource ds = (DataSource)initContext.lookup("java:/jdbc/"+datasource);
		Connection conn = ds.getConnection();
		return conn;
	}
}
