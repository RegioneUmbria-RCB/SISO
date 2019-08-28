/**
 * 
 */
package it.webred.cs.csa.web.manbean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author alessandro.feriani
 *
 */
public abstract class BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public BaseBean() {
		// TODO Auto-generated constructor stub
	}

	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	protected HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	protected HttpServletResponse getResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}

	protected void addError( String summary, String descrizione ) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, descrizione ); 
	    FacesContext.getCurrentInstance().addMessage(null, message);
	}

	protected void addWarning( String summary, String descrizione ) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, descrizione ); 
	    FacesContext.getCurrentInstance().addMessage(null, message);
	}

	protected void addInfo( String summary, String descrizione ) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, descrizione ); 
	    FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
