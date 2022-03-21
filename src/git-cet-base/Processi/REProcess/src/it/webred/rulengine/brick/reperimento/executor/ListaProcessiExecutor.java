package it.webred.rulengine.brick.reperimento.executor;


import it.webred.rulengine.brick.reperimento.executor.bean.Listap;
import it.webred.rulengine.brick.reperimento.executor.bean.Processo;
import it.webred.rulengine.brick.reperimento.executor.logic.BasicLogic;
import it.webred.rulengine.brick.reperimento.executor.logic.ListapProcessoLogic;
import it.webred.rulengine.brick.reperimento.executor.timertask.WaitForProcessUntilExecute;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;



public class ListaProcessiExecutor extends Executor  {
	
	private static final Logger log = Logger.getLogger(ListaProcessiExecutor.class.getName());
    
	private String belfiore;
	private Long idFonte;
	private String reProcessId;
	
	
	public ListaProcessiExecutor(String belfiore, Long idFonte, String reProcessId) {
		super();
		
		this.belfiore = belfiore;
		this.idFonte = idFonte;
		this.reProcessId = reProcessId;
	}
	
	
	

	
	
	public void exec() throws Exception {
		Connection conn = null;
		int found = 0;
		
		try {
			conn = ListapProcessoLogic.getConnection();
			//conn.setAutoCommit(false);
			//recupero i dati della lista: specifica per l'ente in esame
			Listap listap = new Listap();
			String sql = "select * FROM C_LISTAP WHERE FK_AM_COMUNE = ? AND FK_AM_FONTE = ? ";

			log.debug("sql=" + sql);
			log.debug("belfiore:" + belfiore );
			log.debug("fonte:" + idFonte );
			
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, this.belfiore);
			st.setLong(2, this.idFonte);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				
				log.debug("trovati record , cod_lista=" + rs.getObject("COD_LISTA"));


				
				if (rs.getObject("COD_LISTA") != null) {
					listap.setCodLista(tornaValoreRS(rs, "COD_LISTA", null));
				}
				if (rs.getObject("FK_AM_COMUNE") != null) {
					listap.setFkAmComune(tornaValoreRS(rs, "FK_AM_COMUNE", null));
				}
				if (rs.getObject("FK_AM_FONTE") != null) {
					listap.setFkAmFonte(BasicLogic.getLong(tornaValoreRS(rs, "FK_AM_FONTE", BasicLogic.TIPO_NUM)));
				}
				
				found++;
			}

			rs.close();
			st.close();
			
			//se nn c'è la lista specifica per un ente prendo quella standard
			if(found == 0) {

				sql = "select * FROM C_LISTAP WHERE FK_AM_FONTE = ? ";
				
				st = conn.prepareStatement(sql);
				st.setLong(1, this.idFonte);
				log.debug("Nessuna lista specifica, prendo quella standard:" +sql + " - id_fonte=" + idFonte);

				rs = st.executeQuery();
				
				while (rs.next()) {
					log.debug("trovati record con lista standard, cod_lista=" + rs.getObject("COD_LISTA"));
					
					if (rs.getObject("COD_LISTA") != null) {
						listap.setCodLista(tornaValoreRS(rs, "COD_LISTA", null));
					}
					if (rs.getObject("FK_AM_COMUNE") != null) {
						listap.setFkAmComune(tornaValoreRS(rs, "FK_AM_COMUNE", null));
					}
					if (rs.getObject("FK_AM_FONTE") != null) {
						listap.setFkAmFonte(BasicLogic.getLong(tornaValoreRS(rs, "FK_AM_FONTE", BasicLogic.TIPO_NUM)));
					}
					
					found++;
				}

				rs.close();
				st.close();	
			}
			
			
			//recupero i dati dei processi compresi nella lista
			ArrayList<Processo> processi = new ArrayList<Processo>();
			sql = "select * from C_PROCESSO WHERE FK_COD_LISTA = ? order by NUM_ORDINE";
			log.debug("recuopero processi in lista" + sql + " param:" + listap.getCodLista());
			
			st = conn.prepareStatement(sql);
			st.setString(1, listap.getCodLista());
			rs = st.executeQuery();
			
			while (rs.next()) {
				Processo processo = new Processo();
				log.debug("cod_processo=" + rs.getObject("COD_PROCESSO"));
				
				if (rs.getObject("TIPO_PROCESSO") != null) {
					processo.setTipoProcesso(tornaValoreRS(rs, "TIPO_PROCESSO", null));
				}
				if (rs.getObject("FK_COD_LISTA") != null) {
					processo.setFkCodLista(tornaValoreRS(rs, "FK_COD_LISTA", null));
				}
				if (rs.getObject("COD_PROCESSO") != null) {
					processo.setCodProcesso(tornaValoreRS(rs, "COD_PROCESSO", null));
				}
				if (rs.getObject("NUM_ORDINE") != null) {
					processo.setNumOrdine(new Integer(tornaValoreRS(rs, "NUM_ORDINE", BasicLogic.TIPO_NUM)));
				}				
				if (rs.getObject("TIMEOUT") != null) {
					processo.setTimeout(BasicLogic.getLong((tornaValoreRS(rs, "TIMEOUT", BasicLogic.TIPO_NUM))));
				}
				processi.add(processo);
			}
			rs.close();
			st.close();
			
			Date dtStart = new Date();
			String codExec = this.reProcessId+"@"+listap.getCodLista();


			
			//inserimento in tabella C_EXEC_LISTAP			
			sql = "SELECT COD_EXEC, FK_COD_LISTA, DT_START FROM C_EXEC_LISTAP";
						
			Statement stIns = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stIns.executeQuery(sql);			
			rs.moveToInsertRow();
			updateCampo(rs,"COD_EXEC", codExec); 
			updateCampo(rs,"FK_COD_LISTA", listap.getCodLista()); 
			updateCampo(rs,"DT_START", new java.sql.Timestamp(dtStart.getTime()));
			rs.insertRow();
			rs.close();
			stIns.close();
			log.debug("inserito in C_EXEC_LISTAP codexec=" + codExec);
			
			//inserimenti in tabella C_EXEC_PROCESSO
			for (Processo processo : processi) {
				//query per trovare l'id per C_EXEC_PROCESSO
				long maxId = 1;
				sql = "select MAX(ID) AS MAXID FROM C_EXEC_PROCESSO";			
				stIns = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);	
				rs = stIns.executeQuery(sql);
				while (rs.next()) {
					maxId = rs.getLong("MAXID") + 1;
				}
				rs.close();
				stIns.close();
				
				//inserimento in tabella C_EXEC_PROCESSO
				dtStart = new Date();
				
				//nel caso di esecuzione lista, il processId è dato dalla concatenazione di FK_EXEC_LISTAP e FK_PROCESSO
				String processId = codExec + "@" + processo.getCodProcesso();
				sql = "SELECT ID, FK_EXEC_LISTAP, FK_COD_LISTA, FK_COD_PROCESSO, STATO, ESITO, DT_START, PROCESSID, PRESENTI_WARNING FROM C_EXEC_PROCESSO";
				stIns = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				rs = stIns.executeQuery(sql);		
				rs.moveToInsertRow();
				updateCampo(rs,"ID", maxId);
				updateCampo(rs,"FK_EXEC_LISTAP", codExec);
				updateCampo(rs,"FK_COD_LISTA", processo.getFkCodLista());
				updateCampo(rs,"FK_COD_PROCESSO", processo.getCodProcesso());
				updateCampo(rs,"STATO", ListapProcessoLogic.STATO_NON_AVVIATO);
				updateCampo(rs,"DT_START", new java.sql.Timestamp(dtStart.getTime()));
				updateCampo(rs,"PROCESSID", processId);
				updateCampo(rs,"PRESENTI_WARNING", 0);
				rs.insertRow();
				log.debug("inserito in C_EXEC_PROCESSO codexec=" + codExec + " processid=" + processId);
				
				rs.close();
				stIns.close();

				//conn.commit();
			}
			
			for (Processo processo : processi) {
				String processId = codExec + "@" + processo.getCodProcesso();
				
				sql = "update C_EXEC_PROCESSO set STATO=? WHERE PROCESSID=?";
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setString(1, ListapProcessoLogic.STATO_IN_ESECUZIONE);
				pst.setString(2, processId);
				pst.execute();
				
				log.debug("set stato in esecuzione processid=" + processId);
				
				pst.close();
				//conn.commit();
				
				
				//chiamata a WS
				if (processo.getTipoProcesso().equals(ListapProcessoLogic.ACQUISIZIONE)) {
					//chiamata al servizio di avvio dell'esecuzione di un data source in Caronte Client
					try {
						log.debug("lanciaDataSource cod_processo=" + processo.getCodProcesso() + " processid=" +processId );
						lanciaDataSource(processo.getCodProcesso(), processId);
					} catch(Exception e) {
						//le eccezioni in queste singole chiamate a WS non devono essere bloccanti
						log.error("Eccezione durante la chiamata a W.S.", e);
						throw e;
					}
				}else if (processo.getTipoProcesso().equals(ListapProcessoLogic.TRATTAMENTO)) {
					//chiamata al servizio di attivazione dell'esecuzione di comandi in RulEngine
					try {
						log.debug("execRECommand cod_processo=" + processo.getCodProcesso() + " processid=" +processId );
						execRECommand(processo.getCodProcesso(), processId);
					} catch(Exception e) {
						//le eccezioni in queste singole chiamate a WS non devono essere bloccanti
						log.error("Eccezione durante la chiamata a W.S.", e);
						throw e;
					}
				}
				
				try	   {
					// FINCHÈ NON È IN STATO CONCLUSO NON SI PUÒ CONTINUARE
					log.debug("Avvio WaitForProcessUntilExecute");
			    	WaitForProcessUntilExecute wpe = new WaitForProcessUntilExecute (processo, processId);
			        boolean finito = wpe.waitUntilExcuted();  
			        log.debug(finito ? processId + " processo concluso" : processId + " processo non concluso, POSSIBILI ERRORI NEL PROCESSO, LA LISTA DI PROCESSI VIENE INTERROTTA");
			        
			        // andato in timeout o errore, ci sono stti problemi
			        if (!finito) {
			        	log.error("TIMEOUT OPPURE ERRORE, CI SONO STATI PROBLEMI IN WaitForProcessUntilExecute");
			        	throw new Exception("TIMEOUT OPPURE ERRORE, CI SONO STATI PROBLEMI IN WaitForProcessUntilExecute");
			        	
			        }
			        	
			    }
			    catch (Exception e)
			    {
			       log.error("Error aspettando la fine di un processo",e);
			       throw e;
			    }			    
			}
		}catch(Exception e) {
			log.error("Eccezione processo: "+e.getMessage(),e);
			
			setLogicThrowable(e);
			try
			{
				conn.rollback();
			}
			catch (Exception re)
			{
				log.debug(re);
				setLogicThrowable(re);
			}
			throw e;
		}finally {
			
			try
			{
				if (conn != null && !conn.isClosed())
					conn.close();
			}
			catch (Exception e)
			{
				log.debug(e);
				setLogicThrowable(e);
			}
		}
	}

}






