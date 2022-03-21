package it.webred.ct.service.indice.web;

import it.webred.cet.permission.CeTUser;
import it.webred.ct.data.access.basic.indice.IndiceDataIn;
import it.webred.ct.data.access.basic.indice.IndiceService;
import it.webred.ct.data.access.basic.indice.dto.IndiceOperationCriteria;
import it.webred.ct.data.access.basic.indice.dto.IndiceSearchCriteria;

import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class IndiceBaseBean {

	protected IndiceService indiceService;
	
	public void setIndiceService(IndiceService indiceService) {
		this.indiceService = indiceService;
	}

	public static ResourceBundle getBundle() {
		return bundle;
	}

	public static void setBundle(ResourceBundle bundle) {
		IndiceBaseBean.bundle = bundle;
	}

	public static void setLogger(Logger logger) {
		IndiceBaseBean.logger = logger;
	}

	private static ResourceBundle bundle;
	
	static {
		bundle = ResourceBundle
				.getBundle("it.webred.ct.service.indice.web.resources.messages");
	}

	private static Logger logger = Logger.getLogger("indice.log");

	protected Logger getLogger() {
		return this.logger;
	}

	/*public Object getCTRemoteService(String serviceName) {		
		Object o = getRemoteService(serviceName, "CT_Service");				
		return o;
	}
	
	private Object getRemoteService(String serviceName, String context) {
		ServiceLocator locator = ServiceLocator.getInstance();
		Object o = locator
				.getReference(context + "/" + serviceName + "/remote");
		return o;
	}

	public Object getSFRemoteService(String serviceName) {
		HttpSession session = getSession();
		Object o = session.getAttribute(serviceName);

		if (o != null)
			return o;

		ServiceLocator locator = ServiceLocator.getInstance();
		o = locator.getReference("CT_Service/" + serviceName + "/remote");
		session.setAttribute(serviceName, o);
		return o;
	}*/

	private void addMessage(String messageKey, FacesMessage.Severity severity,
			String details) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String txt = bundle.getString(messageKey);

		facesContext.addMessage(null, new FacesMessage(severity, txt,
				details == null ? " " : details));

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
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String txt = bundle.getString(messageKey);
		return txt;
	}

	public Object getBeanReference(String beanName) {
		FacesContext context = FacesContext.getCurrentInstance();
		Application a = context.getApplication();
		Object o = a.getVariableResolver().resolveVariable(context, beanName);
		return o;
	}
	
	public CeTUser getUser(){
		
		return (CeTUser) getSession().getAttribute("user");
		
	}
	
	public IndiceDataIn fillEnte(IndiceDataIn dataIn){
		
		CeTUser user = getUser();
		if(user != null){
			dataIn.setUserId(user.getUsername());
			dataIn.setEnteId(user.getCurrentEnte());
			dataIn.setSessionId(user.getSessionId());
		}
		return dataIn;
	
	}
	
	public IndiceOperationCriteria fillEnte(IndiceOperationCriteria opCriteria){
		
		CeTUser user = getUser();
		if(user != null){
			opCriteria.setUserId(user.getUsername());
			opCriteria.setEnteId(user.getCurrentEnte());
			opCriteria.setSessionId(user.getSessionId());
		}
		return opCriteria;
	
	}
	
	public IndiceSearchCriteria fillEnte(IndiceSearchCriteria isCriteria){
		
		CeTUser user = getUser();
		if(user != null){
			isCriteria.setUserId(user.getUsername());
			isCriteria.setEnteId(user.getCurrentEnte());
			isCriteria.setSessionId(user.getSessionId());
		}
		return isCriteria;
	
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
	
	/*public IndiceService getIndiceService() {
		return (IndiceService) getCTRemoteService("IndiceServiceBean");
	}*/
	
}
