package it.webred.rulengine.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;

import it.webred.rulengine.db.REngineBaseDAO;
import it.webred.rulengine.db.dao.IRCommandLaunchDAO;
import it.webred.rulengine.db.model.RCommandLaunch;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.exception.RulEngineException;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

public class RCommandLaunchDAOImpl extends REngineBaseDAO implements IRCommandLaunchDAO {

	
	private static final Logger logger = Logger.getLogger(RCommandLaunchDAOImpl.class.getName());	
	
	private static final String _SEQUENCE_NAME = "SEQ_R_COMMAND_LAUNCH";
	
	public RCommandLaunch getRCommandLaunchByProcessID(String processId)
			throws Exception {
		
		RCommandLaunch rcl = null;
		
		try {
			logger.debug("Recupero elenco command launch");
			Query q = emRengine.createNamedQuery("RCommandLaunch.getRCommandLaunchByProcessID");
			q.setParameter("processid", processId);
			
			rcl = (RCommandLaunch)q.getSingleResult();
			
			logger.debug("Oggetto RCommandLaunch recuperato");
		
		}catch(NoResultException nre){
			logger.warn("Nessun processo trovato con id "+processId);
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage(),e);
			throw new RulEngineException("Eccezione: "+e.getMessage(),e);
		}
		
		return rcl;
	}

	public void saveRCommandLaunch(Connection reconn,RCommandLaunch r) throws Exception {
		PreparedStatement ps = null;
		
		try {
			logger.debug("Salavatggio RCommandLaunch");	
			
			String query = sql.getProperty("re.rcommandlaunch.save");
			
			ps = reconn.prepareStatement(query);
			ps.setLong(1, r.getId().longValue());
			ps.setLong(2, r.getRCommand().getId().longValue());
			ps.setTimestamp(3, new java.sql.Timestamp(r.getDateStart().getTime()));
			ps.setString(4,r.getProcessid());
			ps.setString(5, r.getBelfiore());
			
			if(r.getIdSched() != null) {
				ps.setLong(6, r.getIdSched());
			}
			else {
				ps.setNull(6, Types.NUMERIC);
			}
			
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

	public void updateRCommandLaunch(Connection reconn,RCommandLaunch r) throws Exception {
		PreparedStatement ps = null;
		
		try {
			logger.debug("Aggiornamento RCommandLaunch");
			
			String query = sql.getProperty("re.rcommandlaunch.update");
			
			ps = reconn.prepareStatement(query);
			ps.setLong(1, r.getRCommand().getId().longValue());
			ps.setTimestamp(2, new java.sql.Timestamp(r.getDateStart().getTime()));
			ps.setTimestamp(3, new java.sql.Timestamp(r.getDateEnd().getTime()));
			ps.setString(4, r.getProcessid());
			ps.setString(5, r.getBelfiore());
			
			if(r.getIdSched() != null) {
				ps.setLong(6, r.getIdSched());
			}
			else {
				ps.setNull(6, Types.NUMERIC);
			}
			
			ps.setLong(7, r.getId());
			
			ps.executeUpdate();
			
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage());
			throw new RulEngineException("Eccezione aggiornamento: "+e.getMessage(),e);
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
