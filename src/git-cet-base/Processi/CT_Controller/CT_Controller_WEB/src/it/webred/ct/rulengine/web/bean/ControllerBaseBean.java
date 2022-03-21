package it.webred.ct.rulengine.web.bean;

import it.webred.cet.permission.CeTUser;
import it.webred.ct.config.model.AmComune;
import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ct.data.access.basic.indice.IndiceService;

import it.webred.ct.rulengine.scheduler.bean.ProcessService;

import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class ControllerBaseBean {
	
	protected ComuneService comuneService = (ComuneService) getEjb(
			"CT_Service", "CT_Config_Manager", "ComuneServiceBean");
	
	protected static Logger logger = Logger.getLogger(ControllerBaseBean.class.getName());
	
	private static ResourceBundle bundle;
	
	
	
	static {
		bundle = ResourceBundle.getBundle("it.webred.ct.rulengine.web.resources.messages");
	}
	
	private void addMessage(String messageKey, FacesMessage.Severity severity,String details) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String txt = bundle.getString(messageKey);

		facesContext.addMessage(null, new FacesMessage(severity, txt,
				details == null ? " " : ": "+details));
	}
	
	
	public void addInfoMessage(String messageKey) {
		addMessage(messageKey, FacesMessage.SEVERITY_INFO, null);
	}

	public void addErrorMessage(String messageKey, String details) {
		addMessage(messageKey, FacesMessage.SEVERITY_ERROR, details);
	}

	public TimeZone getTimeZone() {
		return TimeZone.getDefault();
	}

	public HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	public HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
	}

	public HttpServletResponse getResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance()
				.getExternalContext().getResponse();
	}

	public String getMessage(String messageKey) {
		String txt = bundle.getString(messageKey);
		return txt;
	}

	public Object getBeanReference(String beanName) {
		FacesContext context = FacesContext.getCurrentInstance();
		Application a = context.getApplication();
		Object o = a.getVariableResolver().resolveVariable(context, beanName);
		return o;
	}
	
	protected String getParamValue(String paramName) {
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String path = ctx.getInitParameter(paramName);
		return path;
	}

	
	public String getUsername() {
		HttpSession session = this.getSession();
		CeTUser user = (CeTUser) session.getAttribute("user");
		if (user != null) {
			return user.getUsername();
		}
		
		return null;

	}
	
	public List<AmComune> getListaEntiAuth() {
			return comuneService.getListaComuneByUsername(getRequest().getUserPrincipal().getName());
		}
		
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
}
