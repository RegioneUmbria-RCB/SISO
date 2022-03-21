package it.webred.rulengine.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import it.webred.rulengine.Context;
import it.webred.rulengine.db.REngineBaseDAO;
import it.webred.rulengine.db.dao.IRCommandAckDAO;
import it.webred.rulengine.db.model.RCommandAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.exception.RulEngineException;

public class RCommandAckDAOImpl extends REngineBaseDAO implements IRCommandAckDAO {

	
	private static final Logger logger = Logger.getLogger(RCommandAckDAOImpl.class.getName());
	
	
	public static final String _SEQUENCE_NAME = "SEQ_R_COMMAND_ACK";

	public Long getCountByFilteredProcess(String processId) throws Exception {
		Long count = null;
		
		try {
			logger.debug("Recupero numero abnormal");
			Query q = emRengine.createNamedQuery("RCommandAck.getCountByFilteredProcess");
			q.setParameter("processid", processId);
			
			count = (Long)q.getSingleResult();
			logger.debug("Risultato count: "+count);
			
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage());
			throw new RulEngineException("Eccezione: "+e.getMessage(),e);
		}
		
		return count;
	}
	
	
	public Long getCountRjcAckByFilteredProcess(String processId) throws Exception {
		Long count = null;
		
		try {
			logger.debug("Recupero numero abnormal");
			Query q = emRengine.createNamedQuery("RCommandAck.getCountRjcAckByFilteredProcess");
			q.setParameter("processid", processId);
			
			count = (Long)q.getSingleResult();
			logger.debug("Risultato count: "+count);
			
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage());
			throw new RulEngineException("Eccezione: "+e.getMessage(),e);
		}
		
		return count;
	}


	public void saveRCommandAck(Connection reconn,RCommandAck r) throws Exception {
		PreparedStatement ps = null;
		
		try {
			logger.debug("Salvataggio RCommandAck");
			
			String query = sql.getProperty("re.rcommandack.save");
			
			ps = reconn.prepareStatement(query);
			ps.setLong(1, r.getId().longValue());
			ps.setString(2, r.getMessage());
			ps.setLong(3, r.getRCommandLaunch().getId().longValue());
			ps.setTimestamp(4,new java.sql.Timestamp(r.getLogDate().getTime()));
			ps.setString(5, r.getAckName());
			ps.setLong(6, r.getRCommand().getId());
			
			ps.executeUpdate();
			
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage());
			throw new RulEngineException("Eccezione salvataggio: "+e.getMessage(),e);
		}finally {
			try {
				ps.close();
			}catch(Exception ex) {
				logger.error("Probelmi chiusura statement");
				throw new CommandException("Probelmi chiusura statement");
			}
		}
	}


	public Long getNextID(Connection conn) throws Exception {
		return getNextSequenceId(conn,_SEQUENCE_NAME);
	}


}
