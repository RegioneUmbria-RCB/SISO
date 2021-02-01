package it.webred.rulengine.db.dao.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import it.webred.rulengine.db.REngineBaseDAO;
import it.webred.rulengine.db.dao.IRCommandDAO;
import it.webred.rulengine.db.model.RCommand;
import it.webred.rulengine.exception.RulEngineException;


public class RCommandDAOImpl extends REngineBaseDAO implements IRCommandDAO {

	private static final Logger logger = Logger.getLogger(RCommandDAOImpl.class.getName());
	
	private static final String _SEQUENCE_NAME = "SEQ_R_COMMAND";

	public List<RCommand> getListaRCommand() throws Exception {
		
		List<RCommand> rcmd = new ArrayList<RCommand>();
		
		try {
			logger.debug("Recupero elenco comandi");
			Query q = emRengine.createNamedQuery("RCommand.getListaRCommand");
			rcmd = q.getResultList();
			logger.debug("Result size ["+rcmd.size()+"]");
			
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage());
			throw new RulEngineException("Eccezione: "+e.getMessage(),e);
		}
		
		return rcmd;
	}
	

	public RCommand getRCommandByCommandId(Long commandId) throws Exception {
		RCommand rcmd = null;
		
		try {
			//logger.debug("Recupero command ");
			Query q = emRengine.createNamedQuery("RCommand.getRCommandByCommandId");
			q.setParameter("commandId", commandId.intValue());
			
			rcmd = (RCommand)q.getSingleResult();
			//logger.debug("Oggetto RCommand recuperato");
			
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage());
			throw new RulEngineException("Eccezione: "+e.getMessage(),e);
		}
		
		return rcmd;
	}


	public RCommand getRCommand(Long commandId) throws Exception {
		RCommand rcmd = null;
		
		try {
			//logger.debug("Recupero command ");
			Query q = emRengine.createNamedQuery("RCommand.getRCommand");
			q.setParameter("commandId", commandId.intValue());
			
			rcmd = (RCommand)q.getSingleResult();
			//logger.debug("Oggetto RCommand recuperato");
			
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage());
			throw new RulEngineException("Eccezione: "+e.getMessage(),e);
		}
		
		return rcmd;
	}


	public RCommand getRCommandByCodCommand(String codCommand) throws Exception {
		RCommand rcmd = null;
		
		try {
			//logger.debug("Recupero command ");
			Query q = emRengine.createNamedQuery("RCommand.getRCommandByCodCommand");
			q.setParameter("codCommand", codCommand);
			
			rcmd = (RCommand)q.getSingleResult();
			//logger.info("Oggetto RCommand recuperato");
			
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage());
			throw new RulEngineException("Eccezione: "+e.getMessage(),e);
		}
		
		return rcmd;
	}


	public RCommand getRCommandByRRuleClassname(String classname) throws Exception {
		RCommand rcmd = null;
		
		try {
			//logger.debug("Recupero command ");
			Query q = emRengine.createNamedQuery("RCommand.getRCommandByRRuleClassname");
			q.setParameter("classname", classname);
			
			//rcmd = (RCommand)q.getSingleResult();
			List<RCommand> rcmds = q.getResultList();
			if(rcmds != null && rcmds.size() > 0) {
				rcmd = rcmds.get(0);
				//logger.debug("Oggetto RCommand recuperato");	
			}
			
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage());
			throw new RulEngineException("Eccezione: "+e.getMessage(),e);
		}
		
		return rcmd;		
	}


	public RCommand getRCommand2(Long commandId) throws Exception {
		RCommand rcmd = null;
		
		try {
			//logger.debug("Recupero command ");
			Query q = emRengine.createNamedQuery("RCommand.getRCommand2");
			q.setParameter("commandId", commandId.intValue());
			
			rcmd = (RCommand)q.getSingleResult();
			//logger.debug("Oggetto RCommand recuperato");
			
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage());
			throw new RulEngineException("Eccezione: "+e.getMessage(),e);
		}
		
		return rcmd;	
	}


	public RCommand getRCommandByCodCommand2(String codCommand) throws Exception {
		RCommand rcmd = null;
		
		try {
			//logger.debug("Recupero command ");
			//logger.info("emRengine: "+emRengine);
			Query q = emRengine.createNamedQuery("RCommand.getRCommandByCodCommand2");
			q.setParameter("codCommand", codCommand);
			
			logger.info("getRCommandByCodCommand2 - " + codCommand);
			
			rcmd = (RCommand)q.getSingleResult();
			//logger.debug("Oggetto RCommand recuperato");
			
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage());
			throw new RulEngineException("Eccezione: "+e.getMessage(),e);
		}
		
		return rcmd;
	}


	public void saveRCommand(Connection reconn,RCommand r) throws Exception {
		
		try {
			//logger.debug("Salavatggio RCommand");
			//emRengine.persist(r);
			//emRengine.flush();
			
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage());
			throw new RulEngineException("Eccezione salvataggio: "+e.getMessage(),e);
		}		
	}


	public void updateRCommand(Connection reconn,RCommand r) throws Exception {
		
		try {
			//logger.debug("Aggiornamento RCommand");
			//emRengine.merge(r);
			//emRengine.flush();
			
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage());
			throw new RulEngineException("Eccezione salvataggio: "+e.getMessage(),e);
		}			
	}

	
	
}
