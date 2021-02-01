package it.webred.rulengine.brick.reperimento.executor.timertask;


import it.webred.rulengine.Utils;
import it.webred.rulengine.brick.reperimento.executor.logic.BasicLogic;
import it.webred.rulengine.brick.reperimento.executor.logic.ListapProcessoLogic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

public class VerificaEsitoDaContextListenerTimerTask extends TimerTask {
	
	private Logger log = Logger.getLogger(VerificaEsitoDaContextListenerTimerTask.class.getName());
	
	@Override
	public void run() {		
		
		//log.info("Avvio task di verifica esito esecuzione data source e comandi");
		Connection conn = null;
		
		try {
			//recupero connessione e configurazione
			BasicLogic bl = new BasicLogic();
			conn = bl.getConnection();

			//HashMap<String, Object> configurazione = bl.getConfigurazione();
			
			//cerco in C_EXEC_PROCESSO i record con ESITO null
			String sql = "SELECT * FROM C_EXEC_PROCESSO A, C_PROCESSO B WHERE A.STATO = '" + ListapProcessoLogic.STATO_IN_ESECUZIONE +"' AND A.ESITO IS NULL AND A.FK_COD_LISTA = B.FK_COD_LISTA AND A.FK_COD_PROCESSO = B.COD_PROCESSO";
			Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				String tipoProcesso = rs.getString("TIPO_PROCESSO");
				String processId = rs.getString("PROCESSID");

				log.debug("VerificaEsitoDaContextListenerTimerTask nuovo processid=" + processId);				

				// aggiunto Filippo Mazzini 23.05.12 per configurazione in tabella AM_KEY_VALUE_EXT
				String codEnteProcessId = processId.length() > 3 ? processId.substring(0, 4) : null;
				boolean update = false;
				if (codEnteProcessId != null) {
					log.debug("Cerco valore di 'process.agent.listener.active' per ente = " + codEnteProcessId);
					String value = Utils.getConfigProperty("process.agent.listener.active", codEnteProcessId);
					update = value != null && value.equalsIgnoreCase("Y");
				}
				// fine aggiunto Filippo Mazzini 23.05.12
				if (update) {
					log.debug("Aggiornamento esito processo ["+processId+"]");
					
					Long timeout = rs.getObject("TIMEOUT") == null ? null : new Long(rs.getLong("TIMEOUT"));
					Timer timer = new Timer();
					
					//il test è superfluo in quanto vengono eseguiti solo operazioni di ACQUISIZIONE (o reperimento)
					if (tipoProcesso.equals(ListapProcessoLogic.ACQUISIZIONE)) {
						HashMap<String, Object> configurazione = bl.getConfigurazione(codEnteProcessId);
						log.debug("processid in schedulazione:" + processId);
						timer.schedule(new VerificaLancioDataSourceTimerTask(processId, timeout, 
													(String)((HashMap)configurazione.get(BasicLogic.CONTROLLER_HM_APP)).get(BasicLogic.CONTROLLER_HM_APP_KEY_CARONTE)),
													new java.util.Date());
						log.debug("processid schedulato:" + processId);						
					} 
					
					/*
					else if (tipoProcesso.equals(ListapProcessoLogic.TRATTAMENTO)) {
						timer.schedule(new VerificaExecRECommandTimerTask(processId, timeout, (String)((HashMap)configurazione.get(BasicLogic.CONTROLLER_HM_APP)).get(BasicLogic.CONTROLLER_HM_APP_KEY_RULENGINE)),
								new java.util.Date());
						//timer.scheduleAtFixedRate(new VerificaExecRECommandTimerTask(processId, timeout, (String)((HashMap)configurazione.get(BasicLogic.CONTROLLER_HM_APP)).get(BasicLogic.CONTROLLER_HM_APP_KEY_RULENGINE)),
						//						new java.util.Date(),
						//						ListapProcessoLogic.getTimerTaskPeriod(timeout));
					}
					*/
				}				
			}	
			
			rs.close();
			st.close();
			
		}catch (Exception ex)	{
			log.debug(ex);
			try	{
				conn.rollback();
			}catch (Exception re)	{
				log.debug(re);
			}
		}finally		{
			//log.info("Fine task di verifica esito esecuzione data source e comandi");

			try	{
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			}catch (SQLException e)	{
				log.debug(e);
			}
		}
	}

}
