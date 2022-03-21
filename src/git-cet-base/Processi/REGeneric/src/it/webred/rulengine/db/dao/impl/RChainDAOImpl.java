package it.webred.rulengine.db.dao.impl;

import java.sql.Connection;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import it.webred.rulengine.Context;
import it.webred.rulengine.db.REngineBaseDAO;
import it.webred.rulengine.db.dao.IRChainDAO;
import it.webred.rulengine.db.model.RChain;
import it.webred.rulengine.db.model.RCommand;
import it.webred.rulengine.exception.RulEngineException;

public class RChainDAOImpl extends REngineBaseDAO implements IRChainDAO {

	private static final Logger logger = Logger.getLogger(RChainDAOImpl.class.getName());

	

	public List<RChain> getCommandRChains(Long commandId) throws Exception {
		List<RChain> rchains = null;
		
		try {
			logger.debug("Recupero chains di un command");
			Query q = emRengine.createNamedQuery("RChain.getCommandRChains");
			q.setParameter("commandId", commandId);
			
			rchains = q.getResultList();
			logger.debug("Result size ["+rchains.size()+"]");
			
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage());
			throw new RulEngineException("Eccezione: "+e.getMessage(),e);
		}
		
		return rchains;
	}

	public RChain getRChain(Long chainId) throws Exception {
		RChain rc = null;
		
		try {
			logger.debug("Recupero chain ");
			Query q = emRengine.createNamedQuery("RChain.getRChain");
			q.setParameter("chainId", chainId);
			
			rc = (RChain)q.getSingleResult();
			logger.debug("Oggetto RChain recuperato");
			
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage());
			throw new RulEngineException("Eccezione: "+e.getMessage(),e);
		}
		
		return rc;
	}

	public void updateRChain(Connection reconn,RChain r) throws Exception {
		
		try {
			logger.debug("Salvataggio RChain");
			emRengine.merge(r);
			emRengine.flush();
			
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage());
			throw new RulEngineException("Eccezione salvataggio: "+e.getMessage(),e);
		}		
	}

}
