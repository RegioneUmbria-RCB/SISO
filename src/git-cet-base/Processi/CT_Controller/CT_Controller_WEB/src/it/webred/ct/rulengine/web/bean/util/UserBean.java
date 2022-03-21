package it.webred.ct.rulengine.web.bean.util;

import java.io.IOException;

import it.webred.ct.rulengine.web.bean.ControllerBaseBean;
import it.webred.ct.rulengine.web.bean.monitor.PMonitorBean;

import org.apache.log4j.Logger;

public class UserBean extends ControllerBaseBean{

	private static Logger logger = Logger.getLogger(UserBean.class.getName());

	private String username;
	
	public void doLogout(){
		
		getRequest().getSession().invalidate();
		try {
			getResponse().sendRedirect(getRequest().getContextPath() + "/");
		} catch (IOException e) {
			logger.error("Eccezione: "+e.getMessage(),e);
		}
		
	}

	public String getUsername() {
		username = getRequest().getUserPrincipal().getName();
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
