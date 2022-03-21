package it.webred.rulengine.test;


import java.util.Iterator;
import java.util.List;


import it.webred.rulengine.db.REngineBaseDAO;
import it.webred.rulengine.db.dao.IRCommandDAO;
import it.webred.rulengine.db.dao.IRConnectionDAO;
import it.webred.rulengine.db.dao.IRRuleDAO;
import it.webred.rulengine.db.dao.impl.RCommandDAOImpl;
import it.webred.rulengine.db.dao.impl.RConnectionDAOImpl;
import it.webred.rulengine.db.dao.impl.RRuleDAOImpl;
import it.webred.rulengine.db.model.RCommand;
import it.webred.rulengine.db.model.RCommandLaunch;
import it.webred.rulengine.db.model.RConnection;
import it.webred.rulengine.db.model.RRule;
import it.webred.rulengine.entrypoint.ControlloStato;
import it.webred.rulengine.entrypoint.StatoRe;
import it.webred.rulengine.impl.CommandUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;


public class TestMain {

	private static final Logger logger = Logger.getLogger(TestMain.class.getName());
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		TestMain tm = new TestMain();
		//tm.testControlloStato();
		//tm.execute();
		//tm.testCommandUtil();
	}
	
	/*
	private void testCommandUtil() {
		logger.debug("Inizio test");
		try {
			RCommandLaunch rcl = CommandUtil.getRCommandLaunch("LUIUATT@1210143809453");
			
			logger.debug("ID cmd lch: "+rcl.getId());
			logger.debug("Data fine: "+rcl.getDateEnd());
			logger.debug("Data inizio: "+rcl.getDateStart());
			logger.debug("Comando: "+rcl.getRCommand().getLongDescr());
			
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage(),e);
		}
	
		logger.debug("Fine test");
	}
	*/
	
	private void testControlloStato() {
		logger.debug("Inizio test");
		try {
			ControlloStato cs = new ControlloStato();
			//StatoRe sRe = cs.controllaStato("HWORLD@1285249428781");
			/*
			logger.debug(sRe.getDataInizio().toString());
			logger.debug(sRe.getDataFine().toString());
			logger.debug(sRe.getNumeroAnomalie());
			logger.debug(sRe.getNumeroErrori());
			logger.debug(sRe.getNumeroReject());
			*/
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage(),e);
		}
	
		logger.debug("Fine test");
	}
	
	
	
	private void execute() {
		logger.debug("Inizio processo");
		
		EntityManager em = null;
		EntityManagerFactory emf = null;
		
		try {
			/*
			logger.debug("Inizializzazione Entity Manager");
			emf = Persistence.createEntityManagerFactory("REngine");
			em = emf.createEntityManager();
			logger.debug("Entity Manager creato");
			*/
			
			/*
			em.getTransaction().begin();
			logger.debug("Inizio transaction");
			
			
			RRule rr = em.find(RRule.class, new Integer("180"));
			logger.debug("Regola recuperata");
			
			logger.debug(rr.getDateStart().toString());
			*/
			
			
			//IRRuleDAO rDao = new RRuleDAOImpl();
			//List ll = rDao.getListaRRule();
			
			//IRConnectionDAO rcDao = new RConnectionDAOImpl();
			//List<RConnection> ll = rcDao.getListaRConnection();
			
			/*
			IRCommandDAO rcDao = new RCommandDAOImpl(em);
			List ll = rcDao.getListaRCommand();
			
			Iterator it = ll.iterator();
			while (it.hasNext())
			{
				RCommand rc = (RCommand)it.next();
				String key = rc.getCodCommand();
				String value = key + " - " + rc.getDescr() + " (" + rc.getRCommandType().getDescr() + ") ";
				logger.debug(value);
			}
			*/
			/*
			logger.debug("Lista recuperata: mo' vedemo !");
			if(ll != null) {
				for(RConnection item: ll) {
					logger.debug(item.getConnString());
				}
				
			}
			*/
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage(),e);
		}
		finally {
			//em.flush();
			//em.close();
		}
		
	}

}
