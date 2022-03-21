package it.webred.cs.csa.web.bean.util;

import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.io.IOException;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class LoginBean extends CsUiCompBaseBean {
	
	public void doLogout(){
		
		getRequest().getSession().invalidate();
		getRequest().getSession();
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("home.faces");
		} catch (IOException e) {
			addError("Errore", "Errore durante il reindirizzamento alla scheda Caso");
		}
		
	}

	@SuppressWarnings("rawtypes")
	public boolean isLogged() {
		
		String retry = getRequest().getParameter("retry");
		if("true".equals(retry)){
			Map params = getRequest().getParameterMap();
			params.remove("retry");
			addError("Accesso non riuscito", "Ricorda che la password è sensibile alle maiuscole e minuscole");
		}
		
		if (getRequest().getUserPrincipal()==null || getRequest().getUserPrincipal().getName()==null)
			return false;
		return true;
	}
	
}
