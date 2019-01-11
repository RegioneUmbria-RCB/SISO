package it.webred.ss.web.bean;

import it.webred.amprofiler.model.AmGroup;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class NavigationBean extends SegretariatoSocBaseBean {
	
	public void goCartellaSociale() {		
		try {
			String serverName = getRequest().getServerName();
			Integer serverPort = getRequest().getServerPort();
			String urlSegrSociale = "http://" + serverName;
			if(serverPort != null)
				urlSegrSociale += ":" + serverPort;
			urlSegrSociale += "/CarSocialeA_WEB";
			FacesContext.getCurrentInstance().getExternalContext().redirect(urlSegrSociale);
		} catch (Exception e) {
			addError("policy.error", "Errore durante il reindirizzamento");
		}
	}
	
	public void goHome() {
		getSession().setAttribute("navigationHistory", "");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("home.faces");
		} catch (IOException e) {
			addError("policy.error", "Errore durante il reindirizzamento");
		}
	}
	
	public boolean isPermessoCarSocialeEnte(){
		boolean permesso = false;
		String belfiore = this.getCurrentEnte();
		List<AmGroup> lstGruppi = this.getGruppiUtenteEnte();
		int i = 0;
		while(!permesso && i<lstGruppi.size()){
			AmGroup gruppo = lstGruppi.get(i);
			if(gruppo.getFkAmComune().equals(belfiore) && gruppo.getName().startsWith("CSOCIALE_SOLO_ACCESSO_APP_"))
				permesso = true;
			i++;
		}
		return permesso;
	}
}
