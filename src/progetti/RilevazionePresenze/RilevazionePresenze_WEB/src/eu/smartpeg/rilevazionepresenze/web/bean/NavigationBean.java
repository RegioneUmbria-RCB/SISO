package eu.smartpeg.rilevazionepresenze.web.bean;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class NavigationBean extends RilevazionePresenzeBaseBean {
	
	public void goHome() {
		getSession().setAttribute("navigationHistory", "");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("home.faces");
		} catch (IOException e) {
			addError("policy.error", "Errore durante il reindirizzamento");
		}
	}

}
