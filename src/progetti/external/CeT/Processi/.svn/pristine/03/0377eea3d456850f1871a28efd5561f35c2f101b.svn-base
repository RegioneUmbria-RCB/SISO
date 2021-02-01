package it.webred.rulengine;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.log4j.Logger;

public class ServiceLocator {
	private static final Logger logger = Logger.getLogger(ServiceLocator.class.getName());
	
	private InitialContext ctx;
	private static ServiceLocator me;
	
	
	private ServiceLocator() {
		try {
			logger.info("Recupero contesto iniziale");
			ctx = new InitialContext();
			
		}catch(NamingException ne) {
			ne.printStackTrace();
		}
	}
	
	
	public static ServiceLocator getInstance() {
		if (me == null)
			me = new ServiceLocator();

		return me;
	}
	
	
	public Object getService(String ear, String module, String ejbName) {
		Object o = null;
		
		try {
			o = ctx.lookup("java:global/" + ear + "/" + module + "/" + ejbName);
		}catch(NamingException ne) {
			ne.printStackTrace();
		}
		
		return o;
	}
	
	public EntityManager getJpa(String name) {
		
		try {
			EntityManagerFactory fac = (EntityManagerFactory) ctx.lookup(name); 
			EntityManager em = fac.createEntityManager();
			return em;
		}catch(NamingException ne) {
			ne.printStackTrace();
		}
		
		return null;
	}
}
