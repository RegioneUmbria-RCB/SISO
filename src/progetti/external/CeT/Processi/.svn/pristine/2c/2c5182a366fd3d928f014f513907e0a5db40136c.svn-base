package it.webred.rulengine.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import it.webred.rulengine.db.REngineBaseDAO;
import it.webred.rulengine.db.dao.IRAbNormalDAO;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.exception.RulEngineException;

public class RAbNormalDAOImpl extends REngineBaseDAO implements IRAbNormalDAO {

	private static final Logger logger = Logger.getLogger(RAbNormalDAOImpl.class.getName());
	
	private static final String _SEQUENCE_NAME = "SEQ_R_AB_NORMAL";
	
	public Long getCountByFilteredProcess(String processId) throws Exception {
		Long count = null;
		
		try {
			logger.debug("Recupero numero abnormal");
			Query q = emRengine.createNamedQuery("RAbNormal.getCountByFilteredProcess");
			q.setParameter("processid", processId);
			
			count = (Long)q.getSingleResult();
			logger.debug("Risultato count: "+count);
			
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage());
			throw new RulEngineException("Eccezione: "+e.getMessage(),e);
		}
		
		return count;
	}

	public void saveRAbNormal(Connection reconn,RAbNormal r) throws Exception {
		PreparedStatement ps = null;
		
		try {
			logger.debug("Salvataggio RAbNormal");

			String query = sql.getProperty("re.rabnormal.save");
			
			ps = reconn.prepareStatement(query);
			ps.setLong(1, r.getId().longValue());
			ps.setString(2, r.getEntity());
			ps.setString(3, r.getKey());
			ps.setString(4, r.getMessage());
			ps.setDate(5,new java.sql.Date(r.getMessageDate().getTime()));
			ps.setLong(6, r.getRCommandAck().getId());
			ps.setLong(7, r.getLivelloAnomalia());
			
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
