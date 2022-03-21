package it.webred.rulengine.impl;


import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;

import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.db.dao.IRCommandAckDAO;
import it.webred.rulengine.db.dao.IRCommandLaunchDAO;
import it.webred.rulengine.db.dao.impl.RAbNormalDAOImpl;
import it.webred.rulengine.db.dao.impl.RCommandAckDAOImpl;
import it.webred.rulengine.db.dao.impl.RCommandLaunchDAOImpl;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.db.model.RCommandAck;
import it.webred.rulengine.db.model.RCommandLaunch;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class CommandUtil {

	private static final Logger log = Logger.getLogger(CommandUtil.class.getName());

	
	private static IRCommandLaunchDAO rclDao = null;
	private static IRCommandAckDAO rckDao = null;
	
	
	static {
		rclDao = new RCommandLaunchDAOImpl();
		rckDao = new RCommandAckDAOImpl();
	}
	
	/**
	 * Metodo che restituisce un command launch dato il processid
	 * 
	 * @param processId -
	 *            codice univoco del processo da salvare
	 * @return RCommandLaunch - Ritorna RCommandLaunch salvato con l'id
	 *         assegnato
	 */
	public static RCommandLaunch getRCommandLaunch(String processId) throws CommandException
	{
		RCommandLaunch rcl = null;
		
		try	{
			rcl = rclDao.getRCommandLaunchByProcessID(processId);
		}
		catch (Exception e) {
			throw new CommandException("Impossibile reperire il command launch con processid=" + processId);
		}

		return rcl;
	}

	
	/**
	 * Metodo aggiornare la data di fine dell'esecuzione del command in base al
	 * ProcessId.
	 * 
	 * @param processId -
	 *            codice univoco del processo da salvare
	 */
	public static void updateRCommandLaunch(String processId)
	{
		try {
			RCommandLaunch rcl = CommandUtil.getRCommandLaunch(processId);
			rcl.setDateEnd(Calendar.getInstance().getTime());
			
			//recupero connessione al db RE dell'ente
			Connection reconn = RulesConnection.getConnection("DEFAULT");
			rclDao.updateRCommandLaunch(reconn,rcl);
		}
		catch (Exception e)		{
			log.error("Errore in scrittura command launch - effettuato rollback",e);
		}
	}

	
	/**
	 * Metodo per il salvataggio di un Command Ack(Anomalia) collegato al
	 * Command Launch passato.
	 * 
	 * @param rcl -
	 *            RComamndLaunch a cui si vuol collegare il CommandAck
	 * @param bi -
	 *            CommandAck da inserire
	 */
	public static void saveRCommandAck(RCommandLaunch rcl, CommandAck bi, BeanCommand bc)
	{
		try		{

			RCommandAck rca = new RCommandAck();
			log.debug(bi==null?"bi null!!!":"messaggio ack:"+ bi.getMessage());
			
			String message = bi.getMessage();
			if (bi instanceof ApplicationAck) {
				
				List<RAbNormal> a = ((ApplicationAck) bi).getAbn();
				if (a!= null && a.size()>0){
					message += " (presenti " + ((ApplicationAck) bi).getAbn().size() + " anomalie)";
				}
			}
			
			rca.setMessage(message);
			rca.setAckName(bi.getClass().getSimpleName());
			rca.setLogDate(new Timestamp(new java.util.Date().getTime()));
			
			if (bc.getRCommand().getId() != null) {
				rca.setRCommand(bc.getRCommand());
			}
			
			rca.setRCommandLaunch(rcl);
			
			//recupero connessione al db RE dell'ente
			Connection reconn = RulesConnection.getConnection("DEFAULT");
			rca.setId(rckDao.getNextID(reconn).intValue());
			rckDao.saveRCommandAck(reconn,rca);
			
			if (bi instanceof ApplicationAck)
			{
				if (((ApplicationAck) bi).getAbn() != null)
					writeRAbNormal(bi, rca);
			}

		}
		catch (Exception e)		{
			log.error("Errore in scrittura command ack - effettuato rollback",e);
		}
	}

	
	/**
	 * Metodo per la scrittura di una lista di anomalie.
	 * 
	 * @param ac -
	 *            CommandAck collegato alle anomalie
	 * @param rca -
	 *            RCommandAck collegato alle anomalie
	 */
	private static void writeRAbNormal(CommandAck ac, RCommandAck rca)
	{
		try	{
			ApplicationAck aa = (ApplicationAck) ac;
			
			Iterator it = aa.getAbn().iterator();
			while (it.hasNext()){
				
				RAbNormal rabn = (RAbNormal) it.next();
				
				if (rabn.getLivelloAnomalia()==null) {
					rabn.setLivelloAnomalia(1);
				}
				
				//recupero connessione al db RE dell'ente
				Connection reconn = RulesConnection.getConnection("DEFAULT");
				rabn.setId(new RAbNormalDAOImpl().getNextID(reconn).intValue());
				//salvataggio oggetto
				(new RAbNormalDAOImpl()).saveRAbNormal(reconn,rabn);
			}
		}
		catch (Exception e) {
			log.error("Errore in scrittura AbNormal, eccezione gestita (non bloccante)",e);
		}
	}	
	
}
