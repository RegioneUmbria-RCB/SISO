package it.webred.cs.json.controller;

import it.webred.cet.permission.CeTUser;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

/**
 * @author Alessandro Feriani	
 *
 */
public class BaseController implements Serializable {

	private static final long serialVersionUID = 1L;

	private CeTUser user;
	
	public static Logger logger = Logger.getLogger(BaseController.class);
	
	public Object getEjb(String ear, String module, String ejbName){
		Context cont;
		try {
			cont = new InitialContext();
			return cont.lookup("java:global/" + ear + "/" + module + "/" + ejbName);
		} catch (NamingException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	protected CeTBaseObject fillEnte(CeTBaseObject ro) {	
		if(user != null){
			ro.setEnteId(user.getCurrentEnte());
			ro.setUserId(user.getUsername());
			ro.setSessionId(user.getSessionId());
		}
		return ro;
	}

	public void setUser( CeTUser user ){
		this.user = user; 
	}
}
