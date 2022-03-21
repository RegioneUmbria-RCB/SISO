package it.webred.rulengine.entrypoint;

import it.webred.rulengine.Context;
import it.webred.rulengine.db.dao.IRAbNormalDAO;
import it.webred.rulengine.db.dao.IRCommandAckDAO;
import it.webred.rulengine.db.dao.IRCommandLaunchDAO;
import it.webred.rulengine.db.dao.impl.RAbNormalDAOImpl;
import it.webred.rulengine.db.dao.impl.RCommandAckDAOImpl;

import it.webred.rulengine.db.dao.impl.RCommandLaunchDAOImpl;

import it.webred.rulengine.db.model.RCommandLaunch;

import org.apache.log4j.Logger;


public class ControlloStato
{
	private static final Logger log = Logger.getLogger(ControlloStato.class.getName());
	
	
	
	public StatoRe controllaStato(String processID,Context ctx) throws Exception
	{		
		StatoRe s = new StatoRe();
		
		try {
			
			IRCommandLaunchDAO rclDao = new RCommandLaunchDAOImpl();
			RCommandLaunch rcl = rclDao.getRCommandLaunchByProcessID(processID);
			if (rcl != null)
			{
				//campo di controllo valorizzato solo se processid trovato
				s.setProcessId(rcl.getProcessid());
				s.setDataInizio(rcl.getDateStart());
				// se non c'è data fine non è finito e non facico altro
				if(rcl.getDateEnd() == null)
					s.setFinito(false);
				else
				{
					s.setDataFine(rcl.getDateEnd());
					s.setFinito(true);
					
					//calcolo numero anomalie
					/*
					Query query = session.createSQLQuery("SELECT count(ab.id) from " +
										"R_COMMAND_LAUNCH cl,  " + 
										"R_COMMAND_ACK ac, " + 
										"R_AB_NORMAL ab " + 
										"where  " + 
										"cl.ID = ac.FK_COMMAND_LAUNCH " + 
										"and ac.ID = ab.FK_COMMAND_ACK " + 
										"and processid=:processid");
					query.setParameter("processid", processID);	
					*/

					IRAbNormalDAO abDao = new RAbNormalDAOImpl();
					Long countAbN = abDao.getCountByFilteredProcess(processID);
					s.setNumeroAnomalie(countAbN.intValue());
					
					/*
					query = session.createSQLQuery("SELECT count(ac.id) from " +
							"R_COMMAND_LAUNCH cl,  " + 
							"R_COMMAND_ACK ac " + 
							"where  " + 
							"cl.ID = ac.FK_COMMAND_LAUNCH " + 
							"AND ac.ACK_NAME = 'ErrorAck' " + 
							"and processid=:processid");
					query.setParameter("processid", processID);	
					*/
					
					IRCommandAckDAO akDao = new RCommandAckDAOImpl();
					Long countAck = akDao.getCountByFilteredProcess(processID);
					s.setNumeroErrori(countAck.intValue());
					
					/*
					query = session.createSQLQuery("SELECT count(ac.id) from " +
							"R_COMMAND_LAUNCH cl,  " + 
							"R_COMMAND_ACK ac " + 
							"where  " + 
							"cl.ID = ac.FK_COMMAND_LAUNCH " + 
							"AND ac.ACK_NAME = 'RejectAck' " + 
							"and processid=:processid");
					query.setParameter("processid", processID);
					*/
					
					Long countRjcAck = akDao.getCountRjcAckByFilteredProcess(processID);
					s.setNumeroReject(countRjcAck.intValue());										
				}
			}
			
		}catch (Exception e) {
			log.error("Errore ws controllaStato", e);
			throw e;
		}
		
		return s;		
	
	}
}
