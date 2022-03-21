package it.umbriadigitale.soclav.managedbeans.configurazione;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import it.umbriadigitale.soclav.DataModelCostanti;
import it.umbriadigitale.soclav.DataModelCostanti.PermessiGenerali;
import it.umbriadigitale.soclav.managedbeans.BaseBean;
import it.umbriadigitale.soclav.model.RdCUser;
import it.umbriadigitale.soclav.model.RdCUserCpi;
import it.umbriadigitale.soclav.model.anpal.RdCTbCpi;
import it.umbriadigitale.soclav.service.interfaccia.IConfigurazioneService;
import it.umbriadigitale.soclav.service.interfaccia.IUserService;
import it.umbriadigitale.soclav.util.HttpRestClient;

@Controller
public class ConfigurazioneBean extends BaseBean  {

	private String widgetVar = "listaUtentiVar";
	
	@Autowired
	private LazyDataModel<RdCUser> lazyListaUtentiModel;
	
	@Autowired
	private LazyDataModel<RdCTbCpi> cpiDataModel;
	
	@Autowired
	private LazyDataModel<RdCUserCpi> cpiUserDataModel;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	private IConfigurazioneService confService;
	
	
	private RdCUser selected;
	private List<RdCTbCpi> selectedCPI;
	private List<RdCUserCpi> selectedCPIUtente;
	
	
	public String getWidgetVar() {
		return widgetVar;
	}

	public void setWidgetVar(String widgetVar) {
		this.widgetVar = widgetVar;
	}
	
    @PostConstruct
    public void initDataModel() {
    	//lazyListaLavoroModel = new LazyListaRdCLavoro<GePiDomandaDTO>();
    	//cpiDataModel = new CpiTableDataModel();
    	
    }
	
	public ActionListener getCloseDialog() {
	    return new ActionListener() {
	        @Override
	        public void processAction(ActionEvent event) throws AbortProcessingException {
	        	//loadListaCasi();
	        }
	    };
	}
	
	public void rowDeselect() {
		this.selected=null;
	}


	public void clearFilters(){
		
	}

	public LazyDataModel<RdCUser> getLazyListaUtentiModel() {
		return lazyListaUtentiModel;
	}

	public void setLazyListaUtentiModel(LazyDataModel<RdCUser> lazyListaUtentiModel) {
		this.lazyListaUtentiModel = lazyListaUtentiModel;
	}

	public RdCUser getSelected() {
		return selected;
	}

	public void setSelected(RdCUser selected) {
		this.selected = selected;
	}
	
	public void inizializzaDialog(RdCUser user) {
		((CpiTableDataModel)this.cpiDataModel).refresh();
		this.selectedCPI = null;
		this.selectedCPIUtente = null;
		this.loadCPI(user);
	}
	
	public void loadCPI(RdCUser user) {
		this.selected = user;
		((CpiUserTableDataModel)cpiUserDataModel).setSelectedUser(this.selected);
	}
	
	public boolean isOperatoreCPI(RdCUser user) {
		HttpRestClient restClient = new HttpRestClient();
		String pathPermessi = user.getPathRoot()+DataModelCostanti.Percorsi.PATH_PERMESSI;
		boolean trovato = false;
		HashMap<String, String> listPermessi = null;
		try {
			listPermessi = restClient.getPermissionUtente(pathPermessi, user.getUsername(), user.getEnteDefault());
		} catch (Exception e) {
			logger.error("Errore verifica permessi utente");
		}
		
		String patt = getPatternPermesso(PermessiGenerali.ITEM,PermessiGenerali.VISUALIZZA_LISTA_ANPAL);
		if (listPermessi != null && listPermessi.get(patt) != null)
			trovato = true;
		
		return trovato;
	}
	
	public void assegnaCPI() {
		
		//TODO:verificare se si tenta di aggiungere qualcosa già presente
		
		confService.associaCPIUtente(this.selected.getUsername(), this.selectedCPI);
		ricaricaUtenteSelezionato();
		this.selectedCPI = null;
	}

	public List<RdCTbCpi> getSelectedCPI() {
		return selectedCPI;
	}

	public void setSelectedCPI(List<RdCTbCpi> selectedCPI) {
		this.selectedCPI = selectedCPI;
	}

	public LazyDataModel<RdCTbCpi> getCpiDataModel() {
		return cpiDataModel;
	}

	public void setCpiDataModel(LazyDataModel<RdCTbCpi> cpiDataModel) {
		this.cpiDataModel = cpiDataModel;
	}

	public IConfigurazioneService getConfService() {
		return confService;
	}
	
	public void setConfService(IConfigurazioneService confService) {
		this.confService = confService;
	}

	public List<RdCUserCpi> getSelectedCPIUtente() {
		return selectedCPIUtente;
	}

	public void setSelectedCPIUtente(List<RdCUserCpi> selectedCPIUtente) {
		this.selectedCPIUtente = selectedCPIUtente;
	}

	public void abilitaUserCPI() {
		if(this.selectedCPIUtente!=null) {
			this.confService.abilita(this.selectedCPIUtente);
			ricaricaUtenteSelezionato();
		}else
			logger.warn("Selezionare un centro d'impiego");
	}
	
	public void disabilitaUserCPI() {
		if(this.selectedCPIUtente!=null) {
			this.confService.disabilita(this.selectedCPIUtente);
			ricaricaUtenteSelezionato();
		}else
			logger.warn("Selezionare un centro d'impiego");
	}
	
	public void eliminaUserCPI() {
		if(this.selectedCPIUtente!=null) {
			this.confService.elimina(this.selectedCPIUtente);
			ricaricaUtenteSelezionato();
		}else
			logger.warn("Selezionare un centro d'impiego");
		
	}

	public LazyDataModel<RdCUserCpi> getCpiUserDataModel() {
		return cpiUserDataModel;
	}

	public void setCpiUserDataModel(LazyDataModel<RdCUserCpi> cpiUserDataModel) {
		this.cpiUserDataModel = cpiUserDataModel;
	}
	
	private void ricaricaUtenteSelezionato() {
		RdCUser currentUser = this.userService.findUserByLogin(this.selected.getUsername());
		loadCPI(currentUser);
	}
}
