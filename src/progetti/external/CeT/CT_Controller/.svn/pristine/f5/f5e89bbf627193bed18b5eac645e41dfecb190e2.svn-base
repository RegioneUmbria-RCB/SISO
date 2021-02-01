package it.webred.ct.rulengine.listener;



import it.webred.rulengine.brick.reperimento.executor.logic.BasicLogic;
import it.webred.rulengine.brick.reperimento.executor.logic.ListapProcessoLogic;
import it.webred.rulengine.brick.reperimento.executor.logic.ProcessMonitorUpdater;
import it.webred.rulengine.brick.reperimento.executor.timertask.VerificaEsitoDaContextListenerTimerTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * Application Lifecycle Listener implementation class ProcessAgentListener
 *
 */
public class ProcessAgentListener extends AbstractControllerListener implements ServletContextListener {
	
	private Logger log = Logger.getLogger(ProcessAgentListener.class.getName());
	
	
	private static Long _delay = null;
	private static Long _period = null;
	private static String _yn = null;

	
    /**
     * Default constructor. 
     */
    public ProcessAgentListener() {
        super();
        
        _delay = new Long(_cfg.getProperty("process.agent.timer.delay"));
		_period = new Long(_cfg.getProperty("process.agent.timer.period"));
		_yn = _cfg.getProperty("process.agent.listener.active");
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	
		log.info("[process.agent.listener.active] = " +  _yn);
		
    	if(("Y").equalsIgnoreCase(_yn)) {
    		
    		log.info("[LISTENER ProcessAgentListener ON] ");
    		Timer timer = new Timer();
        	
    		timer.schedule(new VerificaEsitoDaContextListenerTimerTask(), _delay, _period);
        	
        	processiAppesi();
    	}
    	else {
    		log.warn("[LISTENER ProcessAgentListener OFF] ");
    	}
    	
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
    
    
    
    
    private void processiAppesi() {
    	Connection conn = null;
		ResultSet rs= null;
		PreparedStatement ps = null;
		try {
			conn = BasicLogic.getConnection();
			//conn.setAutoCommit(false);
			log.debug("Connesso a RE");
			
			//query per trovare l'id della lista
			String sql = "select PROCESSID FROM C_EXEC_PROCESSO WHERE STATO <> ?";
			//log.debug(sql);
			
			ps = conn.prepareStatement(sql);
			ps.setString(1,ListapProcessoLogic.STATO_CONCLUSO);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				String processid = rs.getString("PROCESSID");
				log.debug("Processo da aggiornare: "+processid);
				
				sql = "update C_EXEC_PROCESSO set STATO=? , ESITO = ? WHERE PROCESSID=?";
				
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setString(1, ListapProcessoLogic.STATO_CONCLUSO);
				pst.setString(2, ListapProcessoLogic.ESITO_INTERRUZIONE_INATTESA);
				pst.setString(3, processid);
				pst.execute();
				pst.close();
				
				//aggiornamento stato monitor processi
				ProcessMonitorUpdater.aggiornaMonitor(conn,ListapProcessoLogic.ESITO_INTERRUZIONE_INATTESA, processid);
			}
			
			//conn.commit();
			log.debug("Aggiornamento stato processi effettuato");
			
		} catch (SQLException e) {
			log.error("errore chiusura processi appesi",e);

		}  catch (Exception e) {
			log.error("errore chiusura processi appesi",e);

		} finally {
			try {
				if (rs!=null)
					rs.close();
				if (ps!=null)
					ps.close();
				if (conn!=null)
					conn.close();
			} catch (SQLException e) {
			} 
		}
    }
	
}
