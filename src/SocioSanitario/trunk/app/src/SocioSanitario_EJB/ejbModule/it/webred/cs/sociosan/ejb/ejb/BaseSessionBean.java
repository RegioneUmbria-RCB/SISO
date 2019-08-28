package it.webred.cs.sociosan.ejb.ejb;


import javax.naming.NamingException;

import it.webred.amprofiler.ejb.AmProfilerSessionFacadeBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.ejb.utility.ClientUtility;

import org.apache.log4j.Logger;

public class BaseSessionBean {
	
	protected Logger logger = Logger.getLogger("sociosan_log");
	
	public BaseSessionBean () throws NamingException {
		 
	}

	
}
