package it.umbriadigitale.argo.web.base.beans;

import it.umbriadigitale.argo.web.base.ArgoBaseBean;
import it.webred.amprofiler.ejb.anagrafica.AnagraficaService;
import it.webred.amprofiler.ejb.perm.LoginBeanService;
import it.webred.amprofiler.ejb.user.UserService;
import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.amprofiler.model.AmUser;
import it.webred.ct.config.model.AmComune;
import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ejb.utility.ClientUtility;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class UserBean extends ArgoBaseBean {
	
	private String username;
	private String ente;
	private String denominazione;
	
	private ComuneService comuneService = (ComuneService) getEjb("CT_Service", "CT_Config_Manager", "ComuneServiceBean");
	
	public String getDenominazione() {
		if(denominazione == null) {
			String username = getUsername();
			AmAnagrafica amAna = getAnagraficaByUsername(username);
			denominazione = amAna.getCognome() + " " + amAna.getNome();
		}
		return denominazione;
	}
	
	public static AmAnagrafica getAnagraficaByUsername(String username) {
		HashMap<String,AmAnagrafica> mappaOperatori = (HashMap<String,AmAnagrafica>)getSession().getAttribute("mappaAnagraficaOperatori");
		if(mappaOperatori==null)
			mappaOperatori = new HashMap<String,AmAnagrafica>();
		
		AmAnagrafica amAna = (AmAnagrafica)mappaOperatori.get(username);
		
		try{
			
			if(amAna==null  && username!=null){
				AnagraficaService anagraficaService = (AnagraficaService) ClientUtility.getEjbInterface("AmProfiler", "AmProfilerEjb", "AnagraficaServiceBean");
				logger.debug("getAnagraficaByUsername: "+ username);
				amAna = anagraficaService.findAnagraficaByUserName(username);
				mappaOperatori.put(username, amAna);
				getSession().setAttribute("mappaAnagraficaOperatori", mappaOperatori);
			}
		}catch(Exception e){
			logger.error("getAnagraficaByUsername",e);
		}
		return amAna;
	}
	
	public static AmUser getUserByUsername(String username) {
		AmUser user = null;
		AmAnagrafica amAna = getAnagraficaByUsername(username);
		if(amAna!=null)
			user = amAna.getAmUser();
		else{
			try{ 
				UserService userService = (UserService) ClientUtility.getEjbInterface("AmProfiler", "AmProfilerEjb", "UserServiceBean");
				if(amAna==null)
					user = userService.getUserByName(username);
				
			}catch(Exception e){
				logger.error("getUserByUsername",e);
			}
		}
		return user;
	}
	
	
/*	public String getCognomeNomeUtente(String username){
		AmAnagrafica ana =  getAnagraficaByUsername(username);
		if(ana!=null)
			return ana.getCognome()+" "+ana.getNome();
		else
			return username;	
	}*/
	
	

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
	
	@PostConstruct
	public void init() {
		
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
			AmComune am = comuneService.getComune(getUser().getCurrentEnte());
			ente = am != null? am.getDescrizione(): "";
			ente = ente.substring(0,1).toUpperCase() + ente.substring(1).toLowerCase();
		}
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}

	

	
}
