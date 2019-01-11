package it.webred.ss.web.bean.util;

import it.webred.amprofiler.model.AmGroup;
import it.webred.cet.permission.CeTUser;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmComune;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.web.bean.SegretariatoSocBaseBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

@ManagedBean
@SessionScoped
public class UserBean extends SegretariatoSocBaseBean{

	private String username;
	private String ente;
	private List<SelectItem> listaOrganizzazioni;
	
	
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
	
	public List<SelectItem> getListaOrganizzazioni(){
			this.loadListaOrganizzazioni();
		return listaOrganizzazioni;
	}
		
	public void loadListaOrganizzazioni(){	
		
		try{
			if(this.listaOrganizzazioni==null || this.listaOrganizzazioni.isEmpty()){
				listaOrganizzazioni = new ArrayList<SelectItem>();
				
				List<String> listaEntiAM = new ArrayList<String>();
				CeTUser user = this.getUser();
				if(user!=null){
					List<AmGroup> lstGruppiAM = super.getListaEntiGIT(user.getUsername());
					//TODO:Modificare Recuperare tutti i Gruppi di tipo SSOCIALE cui appartiene l'utente,
					//Se seleziono la combo scelta ente, carica solo quelli dell'ente selezionato
					for(AmGroup gr : lstGruppiAM){
						if(gr.getName().startsWith("SSOCIALE_"))
							listaEntiAM.add(gr.getFkAmComune());
					}
				}
					
				if(!listaEntiAM.isEmpty()){
			   //Recupero tutti gli enti configurati nella zona sociale
				List<CsOOrganizzazione> lstEnti = super.getListaEnti();
				for(CsOOrganizzazione o : lstEnti){
					String idLista = o.getBelfiore()+"|"+o.getId();
					//Verifico che l'utente sia abilitato all'accesso per l'ente
					if(listaEntiAM.contains(o.getBelfiore())){
						
						//Verifico che abbia almeno un record in SS_PUNTO_CONTATTO, in caso contrario escludo
						SsSchedaSessionBeanRemote service = this.getSsSchedaService();
						BaseDTO bdto = new BaseDTO();
						this.fillUserData(bdto);
						bdto.setObj(o.getId());
						//Verifico se per l'organizzazione esistono punti di contatto
						if(service.esistonoPContatto(bdto)){
							SelectItem si = new SelectItem(idLista,o.getNome());
							this.listaOrganizzazioni.add(si);
						}else{
							SelectItem si = new SelectItem(idLista,o.getNome());
							si.setDisabled(true);
							this.listaOrganizzazioni.add(si);
						}
				  }
				}
			  }else
				  this.addWarning("login.no.organizzazione");
			}
			
			this.valorizzoOrganizzazioneDefault();
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error", "Caricamento Enti non riuscito !");
		}
	}
	
	public void valorizzoOrganizzazioneDefault(){
		//Prevalorizzo con organizzazione di default
		String belfioreSE = getBelfioreSceltaEnte();
		
		if(belfioreSE!=null){
			for(SelectItem si : listaOrganizzazioni){
				if(!si.isDisabled()){
					String idLista = (String)si.getValue();
					String[] ids = idLista.split("\\|");
					String belfiore = ids[0];
				
					//Verifico se corrisponde a quella selezionata da Scelta Ente e la imposto come predefinita.
					if(belfioreSE.equals(belfiore))
						this.setPreselectedOrganizzazione(idLista);
				}
			}
		}
	  }
	
}
