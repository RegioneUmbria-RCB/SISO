package it.webred.ct.rulengine.web.bean.util;

import java.io.IOException;

import it.webred.ct.rulengine.web.bean.ControllerBaseBean;
import it.webred.ct.rulengine.web.bean.monitor.PMonitorBean;

import org.apache.log4j.Logger;

public class PageUiBean extends ControllerBaseBean{

	private static Logger logger = Logger.getLogger(PageUiBean.class.getName());

	private String currentPage = "home";
	
	public void doLogout(){
		
		getRequest().getSession().invalidate();
		try {
			getResponse().sendRedirect(getRequest().getContextPath() + "/");
		} catch (IOException e) {
			logger.error("Eccezione: "+e.getMessage(),e);
		}
		
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	
}
