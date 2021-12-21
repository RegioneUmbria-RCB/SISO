package it.umbriadigitale.soclav.util;

import java.io.IOException;

import javax.faces.context.FacesContext;

import org.springframework.stereotype.Component;

import it.umbriadigitale.soclav.managedbeans.BaseBean;

@Component
public class NavigationBean extends BaseBean{
	
	public void goHome() {
		getSession().setAttribute("navigationHistory", "");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("home.faces");
		} catch (IOException e) {
			//addError("Errore", "Errore durante il reindirizzamento");
		}
	}
	public void goListaPattoLavoro() {
		getSession().setAttribute("navigationHistory","listaPattoLavoro");

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("listaPattoLavoro.xhtml");
		} catch (IOException e) {
			//addError("Errore", "Errore durante il reindirizzamento");
		}
	}
	
	public void goListaPattoSociale() {
		getSession().setAttribute("navigationHistory","listaPattoSociale");

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("listaPattoSociale.xhtml");
		} catch (IOException e) {
			//addError("Errore", "Errore durante il reindirizzamento");
		}
	}
	
	public void goConfigurazione() {
		getSession().setAttribute("navigationHistory","configurazione");

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("configurazione.xhtml");
		} catch (IOException e) {
			//addError("Errore", "Errore durante il reindirizzamento");
		}
	}
	
	public void goTestServizi() {
		getSession().setAttribute("navigationHistory","testServizi");

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("testServizi.xhtml");
		} catch (IOException e) {
			//addError("Errore", "Errore durante il reindirizzamento");
		}
	}
	
}
