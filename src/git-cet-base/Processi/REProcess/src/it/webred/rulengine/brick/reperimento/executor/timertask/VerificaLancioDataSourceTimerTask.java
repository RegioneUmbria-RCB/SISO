package it.webred.rulengine.brick.reperimento.executor.timertask;


import it.webred.rulengine.brick.reperimento.executor.caronte.client.ControllaStatoProcessoProxy;
import it.webred.rulengine.brick.reperimento.executor.caronte.client.StatoCaronte;
import it.webred.rulengine.brick.reperimento.executor.logic.BasicLogic;
import it.webred.rulengine.brick.reperimento.executor.logic.ListapProcessoLogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.TimerTask;

import org.apache.axis.AxisFault;
import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

public class VerificaLancioDataSourceTimerTask extends TimerTask {

	private Logger log = Logger.getLogger(VerificaLancioDataSourceTimerTask.class.getName());
	
	private String processId;
	private Long timeout; //timeout del processo (in minuti)
	private String appPath;
	
	public VerificaLancioDataSourceTimerTask() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VerificaLancioDataSourceTimerTask(String processId, Long timeout,
			String appPath) {
		super();
		this.processId = processId;
		this.timeout = timeout;
		this.appPath = appPath;
	}

	@Override
	public void run() {
		log.info("Verifica stato esecuzione REPERIMENTO CARONTE per processId = " + processId);
		Connection conn = null;
		String esito = null;
		
		ResultSet rs = null;
		PreparedStatement st = null;
		
		try {
			//recupero la connessione
			BasicLogic bl = new BasicLogic();
			conn = bl.getConnection();
			conn.setAutoCommit(false);
			if (timeout == null) {
				timeout = new Long(30 * 24 * 60); //valore di default corrispondente a 30 giorni
			}
			//recupero i dati in tabella C_EXEC_PROCESSO
			String sql = "select ID, FK_EXEC_LISTAP, FK_COD_LISTA, FK_COD_PROCESSO, STATO, ESITO, DT_START, PROCESSID, PRESENTI_WARNING from C_EXEC_PROCESSO WHERE PROCESSID = ?";
			st = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			st.setString(1, processId);
			//log.debug(sql);
			//log.debug("Parametro = " + processId);
			
			rs = st.executeQuery();
			boolean trovato = false;
			while (rs.next()) {
				trovato = true;
				if (rs.getObject("ESITO") != null) {
					esito = rs.getString("ESITO");
					esito = esito.equals("") ? null : esito;
				}
				if (esito == null) {
					StatoCaronte statoCaronte = null;
					String WSPath = "/services/ControllaStatoProcesso";
					String nameWS = appPath + WSPath;
					log.debug("Sto invocando il web service: " + nameWS);
					try {					
						statoCaronte = new ControllaStatoProcessoProxy(nameWS).getStato(processId);
					} catch (AxisFault af) {
						
						log.warn("statoCaronte IN ERRORE!!!!");
						log.warn("statoCaronte fititoClient=" + statoCaronte.isFinitoClient());
						log.warn("statoCaronte fititoServer=" + statoCaronte.isFinitoServer());
						log.warn("statoCaronte inErrore=" + statoCaronte.isInErrore());

						//errore
						esito = ListapProcessoLogic.ESITO_CHIAMATA_WS_CARONTE_FALLITA;
						rs.updateString("STATO", ListapProcessoLogic.STATO_CONCLUSO);
						rs.updateString("ESITO", esito);
						rs.updateInt("PRESENTI_WARNING", 0);
					}
					log.debug("statoCaronte fititoClient=" + statoCaronte.isFinitoClient());
					log.debug("statoCaronte fititoServer=" + statoCaronte.isFinitoServer());
					log.debug("statoCaronte inErrore=" + statoCaronte.isInErrore());
					// se null vuol dire che c'e stato un errore
					if (statoCaronte!=null) {
						if (statoCaronte.isFinitoServer() == false) {
							//verifiche sul client
							if (statoCaronte.isFinitoClient() && statoCaronte.isInErrore()) {
								log.warn("statoCaronte IN ERRORE!!!!");
								log.warn("statoCaronte fititoClient=" + statoCaronte.isFinitoClient());
								log.warn("statoCaronte fititoServer=" + statoCaronte.isFinitoServer());
								log.warn("statoCaronte inErrore=" + statoCaronte.isInErrore());
								//errore
								esito = ListapProcessoLogic.ESITO_ERRORI_ESECUZIONE;
								rs.updateString("STATO", ListapProcessoLogic.STATO_CONCLUSO);
								rs.updateString("ESITO", esito);
								rs.updateInt("PRESENTI_WARNING", 0);
							} else {
								log.debug("statoCaronte ANCORA IN ESECUZIONE");
								log.debug("statoCaronte fititoClient=" + statoCaronte.isFinitoClient());
								log.debug("statoCaronte fititoServer=" + statoCaronte.isFinitoServer());
								log.debug("statoCaronte inErrore=" + statoCaronte.isInErrore());
								//ancora in esecuzione:
								//controllo se il processo è in timeout e solo in questo caso valorizzo i campi STATO, ESITO e PRESENTI_WARNING di C_EXEC_PROCESSO
								if (new java.util.Date().getTime() >= rs.getTimestamp("DT_START").getTime() + (timeout.longValue() * 60 * 1000)) {
									esito = ListapProcessoLogic.ESITO_TIMEOUT;
									rs.updateString("STATO", ListapProcessoLogic.STATO_CONCLUSO);
									rs.updateString("ESITO", esito);
									rs.updateInt("PRESENTI_WARNING", 0);
								}
							}
						} else {
							//tutto ok
							log.debug("statoCaronte FINITO!!");
							log.debug("statoCaronte fititoClient=" + statoCaronte.isFinitoClient());
							log.debug("statoCaronte fititoServer=" + statoCaronte.isFinitoServer());
							log.debug("statoCaronte inErrore=" + statoCaronte.isInErrore());
							esito = ListapProcessoLogic.ESITO_POSITIVO;
							rs.updateString("STATO", ListapProcessoLogic.STATO_CONCLUSO);
							rs.updateString("ESITO", esito);
							rs.updateInt("PRESENTI_WARNING", 0);
						}
					}
					rs.updateRow();							
				}
			}
			conn.commit();
			DbUtils.close(rs);
			DbUtils.close(st);
			if (!trovato) {
				throw new Exception("ProcessID non trovato in tabella C_EXEC_PROCESSO");
			}
			if (esito != null) {
				cancel();
				log.info("verificato lancio data source per processId = " + processId + " con ESITO: " + esito);
			} else {
				log.info(processId + " Non concluso");
			}
			
		}catch (Exception e)
		{
			log.error("ERRORE GRAVE", e);
			try
			{
				conn.rollback();
			}
			catch (Exception re)
			{
				log.debug(re);
			}
		}
		finally
		{
			log.info("FINE VERIFICA stato esecuzione REPERIMENTO CARONTE per processId = " + processId);
			try
			{
				DbUtils.close(st);
				DbUtils.close(rs);
				DbUtils.close(conn);
			}
			catch (Exception e)
			{
				log.debug(e);
			}
		}
	}

}
