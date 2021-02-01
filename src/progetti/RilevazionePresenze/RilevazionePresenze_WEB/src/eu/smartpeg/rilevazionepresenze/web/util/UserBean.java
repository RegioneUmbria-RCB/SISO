package eu.smartpeg.rilevazionepresenze.web.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

import eu.smartpeg.rilevazionepresenze.web.bean.RilevazionePresenzeBaseBean;
import it.webred.ct.config.model.AmComune;
import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ejb.utility.ClientUtility;

@ManagedBean
@SessionScoped
public class UserBean extends RilevazionePresenzeBaseBean implements Serializable {
	private String username;
	private String ente;
	private Boolean autorizzato;
	//private List<SelectItem> listaOrganizzazioni;
	
	public static UserBean getBeanIstance() {
		return findBean("userBean");
	}
	
	public void doLogout(){
		
		logAction(logoutAction);
		
		getRequest().getSession().invalidate();
		getRequest().getSession();
		try {
			getResponse().sendRedirect(getRequest().getContextPath() + "/");
		} catch (IOException e) {
			logger.error("Eccezione: "+e.getMessage(),e);
		}
		
	}

	public boolean isLogged() {
		
		String retry = getRequest().getParameter("retry");
		if("true".equals(retry)){
			Map params = getRequest().getParameterMap();
			params.remove("retry");
			addError("login.denied", "Ricorda che la password e' sensibile alle maiuscole e minuscole");
		}
		
		if (getRequest().getUserPrincipal()==null || getRequest().getUserPrincipal().getName()==null)
			return false;
		
		return true;
	}
	
	public boolean isAdmin(){
		if(!isLogged())
			return false;
		return this.isResponsabile();
	}
	
	public String getUsername() {
		
		if (getRequest().getUserPrincipal()==null || getRequest().getUserPrincipal().getName()==null)
			return "";
		
		username = getRequest().getUserPrincipal().getName();
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEnte() {
		
		String es = getRequest().getParameter("es");
		if(es != null && !es.equals("") && getUser() != null){
			
			try {
				ComuneService comuneService = (ComuneService) ClientUtility.getEjbInterface(
					"CT_Service", "CT_Config_Manager", "ComuneServiceBean");
				AmComune am = comuneService.getComune(getUser().getCurrentEnte());
				ente = am != null? am.getDescrizione(): "";
				ente = ente.substring(0,1).toUpperCase() + ente.substring(1).toLowerCase();
			
			} catch(NamingException e) {
	    		logger.error(e.getMessage(), e);
	    		addError("caricamento.error");
			}
		}
		return ente;
	}
	

	public void setEnte(String ente) {
		this.ente = ente;
	}
	
	
	public boolean isAutorizzato() {
		if(this.listaOrganizzazioni==null) loadListaOrganizzazioni(true);
		if(this.listaOrganizzazioni!=null) {
			autorizzato = false;
			String belfioreSE = getBelfioreSceltaEnte();
		
			if(belfioreSE!=null){
				for(SelectItem si : listaOrganizzazioni){
					if(!si.isDisabled()){
						String idLista = (String)si.getValue();
						String[] ids = idLista.split("\\|");
						String belfiore = ids[0];
					
						//Verifico se corrisponde a quella selezionata da Scelta Ente e la imposto come predefinita.
						if(belfioreSE.equals(belfiore)) {
							autorizzato = true;
							
							//this.setPreselectedOrganizzazione(idLista);
						}
					}
				}
			}
		}
		return autorizzato;
	}

	
}
