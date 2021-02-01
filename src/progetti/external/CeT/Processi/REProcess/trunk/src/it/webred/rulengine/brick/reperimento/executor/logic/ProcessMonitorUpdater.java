package it.webred.rulengine.brick.reperimento.executor.logic;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

import org.apache.log4j.Logger;

public class ProcessMonitorUpdater {
	
	private static Logger log = Logger.getLogger(ProcessMonitorUpdater.class.getName());
	
	
	
	public static void aggiornaMonitor(Connection conn,String esitoCaronte,String processId) throws Exception {
		
		try {
			log.debug("Aggiornamento monitor processi RE");
			
			//pid - es.: F704::1@T1292404553843@REP_DEMOG@demografia_2_0
			String[] rePID = processId.split("@");
			String reProcessId = rePID[0]+"@"+rePID[1];
			log.debug("RE processId: "+reProcessId);

			Integer newMonitorStatus = null;
			//recupero stato opportuno
			if(esitoCaronte.equals(ListapProcessoLogic.ESITO_POSITIVO)) {
				newMonitorStatus = new Integer(3); //fornitura disponibile
			}
			else {
				newMonitorStatus = new Integer(2); //errore reperimento
			}
			
			log.debug(processId + " nuovo stato su monitor processi=" + newMonitorStatus);
			
			String sql = "update R_PROCESS_MONITOR set FK_STATO = ?, ISTANTE = ? where PROCESSID = ? ";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, newMonitorStatus);
			st.setLong(2, Calendar.getInstance().getTimeInMillis());
			st.setString(3, reProcessId);
			
			log.debug(processId + " nuovo stato su monitor processi=" + newMonitorStatus);
			
			int upd = st.executeUpdate();
			st.close();
			
			if(upd > 0) {
				log.debug("Monitor processi RE aggiornato");
			}
			
		}catch(Exception e) {
			log.error("Eccezione aggionamento monitor: "+e.getMessage());
			throw e;
		}
		
	}
}

