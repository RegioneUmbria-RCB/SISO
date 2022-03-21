package it.webred.rulengine.dwh;

import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.dwh.Dao.TabellaDwhDao;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.dwh.Dao.TabellaDwhDao;
import it.webred.utils.GenericTuples;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

public class SitSintesiProcessiUtils
{

	private static final Logger log = Logger.getLogger(SitSintesiProcessiUtils.class.getName());
	
	private static final long period = 30 * 1000; //periodo di 30 secondi
	
	//HashMap dei salvataggi in SIT_SINTESI_PROCESSI predisposti
	private static ConcurrentHashMap<String, HashMap<String, Object>> processi = new ConcurrentHashMap<String, HashMap<String, Object>>(); //   (HashMap) Collections.synchronizedMap(new HashMap<String, HashMap<String, Object>>());

	// cntiene tutti i processi dei quali si è salvato su db un dato di sintesi
	private static ConcurrentHashMap<String, HashMap<String, Object>> processiSavedCached = new ConcurrentHashMap<String, HashMap<String, Object>>();
	//HashMap dei relativi TimerTask avviati
	private static ConcurrentHashMap<String, EmbeddedTimerTask> tasks = new ConcurrentHashMap<String, EmbeddedTimerTask>();
	//carattere separatore per la chiave composta delle due HashMap processi e tasks
	private final static String SEPARATORE = " ";
	//LinkedHashMap, con relativa inizializzazione, contenente i campi della tabella SIT_SINTESI_PROCESSI
	public static final LinkedHashMap<String, String> campi = new LinkedHashMap<String, String>();	
	static {
		campi.put("PROCESSID", "PROCESSID");
		campi.put("NOME_TABELLA", "NOME_TABELLA");
		campi.put("INSERITI", "INSERITI");
		campi.put("AGGIORNATI", "AGGIORNATI");
		campi.put("SOSTITUITI", "SOSTITUITI");
	}	
	//LinkedHashMap, con relativa inizializzazione, contenente i campi in chiave della tabella SIT_SINTESI_PROCESSI
	public static final LinkedHashMap<String, String> campiChiave = new LinkedHashMap<String, String>();	
	static {
		campiChiave.put("PROCESSID", "PROCESSID");
		campiChiave.put("NOME_TABELLA", "NOME_TABELLA");
	}	
	//costante che indica la chiave della connessione al datawarehouse in una HashMap di oggetti Connection
	//public static final String DWH = "DWH";
	//costanti che indicano il tipo di operazione richiesto al metodo sincronizzato operazioneSincronizzata
	public static final String SALVA_TIMER_TASK = "SALVA_TIMER_TASK";
	public static final String INCREMENTA = "INCREMENTA";
	public static final String SALVA_ULTIMI = "SALVA_ULTIMI";
	public static final String RIMUOVI_SINTESI_PROCESSO = "RIMUOVI_SINTESI_PROCESSO";
	
	/**
	 * Metodo pubblico sincronizzato che, a seconda del parametro tipo, smista le richieste a uno dei tre metodi privati salva, 
	 * incrementa e salvaUltimi.
	 * @param conn -  connesione al Dwh diogene (non un'altra !!!!) solo quella al dwh 
	 * @param processid
	 * @param nomeTabella
	 * @param nomeCampo
	 * @param tipo
	 * @return
	 * @throws Exception
	 */
	public static synchronized boolean operazioneSincronizzata(String processid, String nomeTabella, String nomeCampo, String tipo,String belfiore) 
	throws Exception {
		Connection conn=null;
		try {
			conn= RulesConnection.getConnection("DWH_"+belfiore);
			String chiave = processid + SEPARATORE + nomeTabella;
			if (tipo.equals(SALVA_TIMER_TASK)) {
				if (processi.get(chiave) == null) {
					// se non ci sono più dati da salvare, viene restituito false e il task viene rimosso dall'HashMap tasks
					if (tasks.get(chiave) != null) {
						tasks.remove(chiave);
					}
					log.info("Terminato task di inserimento in SIT_SINTESI_PROCESSI per PROCESSID = '" + processid +
							"' e NOME_TABELLA = '" + nomeTabella +"'");
					TabellaDwhDao.distruggiPreparestatement(nomeTabella);

					return false;
				}
				salva(conn, chiave, false);
				
			} else if (tipo.equals(INCREMENTA)) {
				incrementa(conn, processid, nomeTabella, nomeCampo,belfiore);
			} else if (tipo.equals(SALVA_ULTIMI)) {
				salvaUltimi(conn, processid);
			} else if (tipo.equals(RIMUOVI_SINTESI_PROCESSO)) {
				rimuoviProcesso(conn, processid);
			}		
			return true;
		} finally {
			if(conn!=null)
				conn.close();
		}
		
	}
	
	/**
	 * Inizializza un oggetto dell'HashMap processi, cioè, un contatore di operazioni (inserimento, aggiornamento e sostituzione) 
	 * che costituirà un record da inserire, o aggiornare, in SIT_SINTESI_PROCESSI allo scadere dell'intervallo specificato dalla 
	 * costante period. 
	 * Se si tratta del primo oggetto inizializzato per la chiave passata a parametro, viene anche attivato il task relativo.
	 * @param conn
	 * @param chiave
	 */
	private static void inizializza(String chiave,String belfiore)
	{
		HashMap<String, Object> processo = new HashMap<String, Object>();
		String[] chiaveArr = chiave.split(SEPARATORE);
		processo.put(campi.get("PROCESSID"), chiaveArr[0]);
		processo.put(campi.get("NOME_TABELLA"), chiaveArr[1]);
		processo.put(campi.get("INSERITI"), new Long(0));
		processo.put(campi.get("AGGIORNATI"), new Long(0));
		processo.put(campi.get("SOSTITUITI"), new Long(0));
		processi.put(chiave, processo);
		// se è la prima inizializzazione, attivo anche il task relativo
		if (tasks.get(chiave) == null) {
			EmbeddedTimerTask task = new EmbeddedTimerTask(chiave,belfiore);
			tasks.put(chiave, task);
			new Timer().scheduleAtFixedRate(task, new Date(), period);
			log.info("Avviato task di inserimento in SIT_SINTESI_PROCESSI per PROCESSID = '" + chiaveArr[0] +
						"' e NOME_TABELLA = '" + chiaveArr[1] +"'");
		}
	}
	
	/**
	 * Cancella dall'HashMap processi l'oggetto con la chiave passata a parametro; operazione effettuata subito dopo ogni 
	 * salvataggio in SIT_SINTESI_PROCESSI.
	 * @param chiave
	 */
	private static void cancella(String chiave)
	{
		if (processi.get(chiave) != null) {
			processi.remove(chiave);	
		}
	}
	
	/**
	 * Metodo chiamato dopo le operazioni di inserimento e modifica della classe TabellaDwhDao. 
	 * Nell'oggetto dell'HashMap processi con la chiave corrispondente a processid e nomeTabella passati a parametro, 
	 * incrementa il relativo contatore (inseriti, aggiornati o sostituiti).
	 * @param conn
	 * @param processid
	 * @param nomeTabella
	 * @param nomeCampo
	 */
	private static void incrementa(Connection conn, String processid, String nomeTabella, String nomeCampo,String belfiore) {
		String chiave = processid + SEPARATORE + nomeTabella;
		HashMap<String, Object> processo = processi.get(chiave);
		/* se è stato appena effettuato il salvataggio in SIT_SINTESI_PROCESSI, deve essere inizializzato un nuovo oggetto 
		dell'HashMap processi */
		if (processo == null) {
			inizializza(chiave,belfiore);
			processo = processi.get(chiave);
		}
		Long count = (Long)processo.get(nomeCampo);
		processo.put(nomeCampo, new Long(count.longValue() + 1));
	}
	
	/* classe interna (TimerTask) per la gestione delle operazioni di salvataggio (inserimento o modifica) in SIT_SINTESI_PROCESSI 
	ad intervalli regolari */
	private static class EmbeddedTimerTask extends TimerTask 
	{
		String chiave;
		String belfiore;
		
		public EmbeddedTimerTask(String chiave,String belfiore) {
			this.chiave = chiave;
			this.belfiore = belfiore;
			
		}
		
		public void run()
		{
			String[] chiaveArr = chiave.split(SEPARATORE);			
			try {
				//se non ci sono più dati da salvare, il task viene terminato
				if (!operazioneSincronizzata( chiaveArr[0], chiaveArr[1], null, SALVA_TIMER_TASK,this.belfiore)) {
					cancel();
				}
			}catch (Exception e)
			{
				log.debug("Errore in run SitSintesiProcessi",e);				
			}
		}
	}
	
	/**
	 * Metodo chiamato da CommandLauncher per effettuare il salvataggio dei dati residui in SIT_SINTESI_PROCESSI 
	 * prima di effettuare il commit
	 * @param conn
	 * @param processid
	 * @throws Exception
	 */
	private static void salvaUltimi(Connection conn, String processid) throws Exception {
		log.info("Salvataggio finale dei dati in SIT_SINTESI_PROCESSI per PROCESSID = '" + processid + "'");
		Iterator it = processi.keySet().iterator();
		while (it.hasNext()) {
			String chiave = (String)it.next();
			String[] chiaveArr = chiave.split(SEPARATORE);
			if (chiaveArr[0].equals(processid)) {
				//TabellaDwhDao.flushBatch(processid, chiaveArr[1]);
				salva(conn, chiave, false);
				log.info("Effettuato il salvataggio finale dei dati in SIT_SINTESI_PROCESSI per PROCESSID = '" + chiaveArr[0] +
						"' e NOME_TABELLA = '" + chiaveArr[1] +"'");
			}
		}
	}
	
	/**
	 * Metodo che mette a zero i dati relativi al processId fornito
	 * Il metodo viene usato in caso che il processo di acquisizione abbia effettuato un rollback
	 * @param conn
	 * @param processid
	 * @throws Exception
	 */
	private static void rimuoviProcesso(Connection conn, String processId) throws Exception {
			log.info("Iniziato il salvataggio finale dei dati in SIT_SINTESI_PROCESSI per PROCESSID = '" + processId + "'");
			Iterator it = processiSavedCached.keySet().iterator();
			while (it.hasNext()) {
				String chiave = (String)it.next();
				String[] chiaveArr = chiave.split(SEPARATORE);
				HashMap<String, Object> processo = processiSavedCached.get(chiave);
				if (chiaveArr[0].equals(processId)) {
					processo.put(campi.get("INSERITI"), new Long(0));
					processo.put(campi.get("AGGIORNATI"), new Long(0));
					processo.put(campi.get("SOSTITUITI"), new Long(0));
					processi.put(chiave,processo);
					salva(conn, chiave, true);
					log.info("Effettuato il salvataggio finale dei dati in SIT_SINTESI_PROCESSI per PROCESSID = '" + chiaveArr[0] +
							"' e NOME_TABELLA = '" + chiaveArr[1] +"'");
				}
				TabellaDwhDao.distruggiPreparestatement(chiaveArr[1]);
				// svuoto la cache dei salvati visto che sono praticamente andato ad effettuare un rollback
				processiSavedCached.remove(chiave);
			}
		}
	
	
	
	private static GenericTuples.T2<String, String> selectSintesiProcessi = null;
	private static GenericTuples.T2<String, String> getSqlSintesiProcessi() {
		if (selectSintesiProcessi!=null)
			return selectSintesiProcessi;
		
		String select = "SELECT ";
		int count = 0;
		Iterator it = campi.keySet().iterator();
		while (it.hasNext()) {
			String key = (String)it.next();
			if (count > 0) {
				select += ", ";
			}
			select += key;
			count++;
		}
		select += " FROM SIT_SINTESI_PROCESSI";
		String where = " WHERE ";
		count = 0;
		it = campiChiave.keySet().iterator();
		while (it.hasNext()) {
			String key = (String)it.next();
			if (count > 0) {
				where += " AND ";
			}
			where += key + " = ? ";
			count++;
		}
		selectSintesiProcessi = new GenericTuples.T2(select, where);
		return selectSintesiProcessi;
		
	}

		
	/**
	 * Metodo privato che effettua le operazioni di salvataggio (inserimento o modifica) in SIT_SINTESI_PROCESSI
	 * @param conn
	 * @param chiave
	 * @param sovrascrivi - i dati che sono dentro all'hash per quel processo non vengono sommati ma sovrascritti a quelli presenti eventualmente su db
	 * @throws Exception
	 */
	private static void salva(Connection conn, String chiave, boolean sovrascrivi) throws Exception
	{
		String[] chiaveArr = chiave.split(SEPARATORE);
		//salvataggio (inserimento o modifica) in SIT_SINTESI_PROCESSI
		HashMap<String, Object> processo = processi.get(chiave);
		long inseriti = ((Long)processo.get(campi.get("INSERITI"))).longValue();
		long aggiornati = ((Long)processo.get(campi.get("AGGIORNATI"))).longValue();
		long sostituiti = ((Long)processo.get(campi.get("SOSTITUITI"))).longValue();

		GenericTuples.T2<String, String> sql = getSqlSintesiProcessi();
		
		PreparedStatement ps = conn.prepareStatement(sql.firstObj + sql.secondObj, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ps.setString(1, chiaveArr[0]);
		ps.setString(2, chiaveArr[1]);
		ResultSet rs = ps.executeQuery();
		try {
			if (rs.next()) {
				//modifica
				if (!sovrascrivi)  {
					inseriti = rs.getLong(campi.get("INSERITI")) + inseriti;
					aggiornati = rs.getLong(campi.get("AGGIORNATI")) + aggiornati;
					sostituiti = rs.getLong(campi.get("SOSTITUITI")) + sostituiti;
				}
				rs.updateLong(campi.get("INSERITI"), inseriti);
				rs.updateLong(campi.get("AGGIORNATI"), aggiornati);
				rs.updateLong(campi.get("SOSTITUITI"), sostituiti);
				rs.updateRow();
				rs.close();
				ps.close();
				log.info("Modificato in SIT_SINTESI_PROCESSI il record con PROCESSID = '" + chiaveArr[0] +
						"' e NOME_TABELLA = '" + chiaveArr[1] +"': INSERITI = " + inseriti + 
						", AGGIORNATI = " + aggiornati + ", SOSTITUITI = " + sostituiti);
			}else{
				ResultSet rsIns = null;
				Statement st = null;
				try {
					//inserimento
					st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); 
					rsIns = st.executeQuery(sql.firstObj);			
					rsIns.moveToInsertRow();					
					rsIns.updateString(campi.get("PROCESSID"), chiaveArr[0]);
					rsIns.updateString(campi.get("NOME_TABELLA"), chiaveArr[1]);
					rsIns.updateLong(campi.get("INSERITI"), inseriti);
					rsIns.updateLong(campi.get("AGGIORNATI"), aggiornati);
					rsIns.updateLong(campi.get("SOSTITUITI"), sostituiti);					
					rsIns.insertRow();
					log.info("Inserito in SIT_SINTESI_PROCESSI record con PROCESSID = '" + chiaveArr[0] +
							"' e NOME_TABELLA = '" + chiaveArr[1] +"', con INSERITI = " + inseriti + 
							", AGGIORNATI = " + aggiornati + ", SOSTITUITI = " + sostituiti);
				} finally {
					DbUtils.close(rsIns);
					DbUtils.close(st);
					
				}
			}
		} finally {
			DbUtils.close(rs);
			DbUtils.close(ps);
		}
		processiSavedCached.put(chiave, processi.get(chiave));
		//rimozione dell'oggetto corrispondente alla chiave dall'HashMap processi
		cancella(chiave);
	}
	
}
