package it.webred.rulengine.db.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import it.webred.rulengine.Context;
import it.webred.rulengine.db.REngineBaseDAO;
import it.webred.rulengine.db.dao.IRRuleDAO;
import it.webred.rulengine.db.model.RRule;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.rulengine.test.TestMain;

public class RRuleDAOImpl extends REngineBaseDAO implements IRRuleDAO {
	
	private static final Logger logger = Logger.getLogger(RRuleDAOImpl.class.getName());

	

	public List<RRule> getListaRRule() throws Exception {
		
		List<RRule> rrules = new ArrayList<RRule>();
		
		try {
			logger.debug("Recupero elenco regole");
			Query q = emRengine.createNamedQuery("RRule.getListaRRule");
			rrules = q.getResultList();
			logger.debug("Result size ["+rrules.size()+"]");
			
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage());
			throw new RulEngineException("Eccezione: "+e.getMessage(),e);
		}
		
		return rrules;
	}

	public List<RRule> getListaRRule(Long commandId) throws Exception {
		List<RRule> rrules = new ArrayList<RRule>();
		
		try {
			logger.debug("Recupero elenco regole per il comando "+commandId);
			Query q = emRengine.createNamedQuery("RRule.getRCommandListaRRule");
			q.setParameter("commandId", commandId.intValue());
			
			rrules = q.getResultList();
			logger.debug("Result size ["+rrules.size()+"]");
			
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage());
			throw new RulEngineException("Eccezione: "+e.getMessage(),e);
		}
		
		return rrules;
	}

}
