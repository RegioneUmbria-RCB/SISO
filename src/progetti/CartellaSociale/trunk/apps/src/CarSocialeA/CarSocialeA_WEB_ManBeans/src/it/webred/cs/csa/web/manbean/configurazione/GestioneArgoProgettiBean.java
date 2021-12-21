package it.webred.cs.csa.web.manbean.configurazione;

import it.umbriadigitale.argo.ejb.client.cs.bean.ArConfigurazioneService;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArAttivitaDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArOrganizzazioneDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArProgettoDTO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;

@ManagedBean
@ViewScoped
public class GestioneArgoProgettiBean extends CsUiCompBaseBean {

	private ArConfigurazioneService confArService = (ArConfigurazioneService) getArgoEjb( "ArConfigurazioneServiceBean");
	
	private ProgettiTableDataModel progettiTableDataModel;
	private List<ArProgettoDTO> lstProgetti;
	private String modalHeader;
	private ArProgettoDTO selectedProgetto;
	private ArAttivitaDTO selectedAttivita;
	private List<ArProgettoDTO> progettiSelezionati;
	
	private List<ArOrganizzazioneDTO> lstOrganizzazioniDTO;
	
	private DualListModel<ArOrganizzazioneDTO> selectedOrganizzazioni;
	private List<SelectItem> organizzazioni = new LinkedList<SelectItem>();
	
	public GestioneArgoProgettiBean(){
		caricaOrganizzazioni();
		
		progettiTableDataModel = new ProgettiTableDataModel();
		progettiTableDataModel.setZsCorrente(getZonaSociale());
	}
	
/*	@PostConstruct
	private void init() {
		caricaOrganizzazioni();
		
		progettiTableDataModel = new ProgettiTableDataModel();
		progettiTableDataModel.setZsCorrente(getZonaSociale());
		//caricaProgetti();
	}*/
	
/*	private void caricaProgetti() {
		
		try {
			logger.debug("Init caricaProgetti");
			String zsCorrente = getZonaSociale();
			lstProgetti = confArService.getListaProgetti(zsCorrente);
			logger.debug("End caricaProgetti");
		} catch(Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
		
	}*/

	private void caricaOrganizzazioni() {

		try {
			String zsCorrente = getZonaSociale();
			lstOrganizzazioniDTO = confArService.getListaOrganizzazioniDTO(zsCorrente);
			
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}

	}
	
	public List<SelectItem> getOrganizzazioni() {

		if (organizzazioni == null || organizzazioni.size() < 1) {
			for (ArOrganizzazioneDTO u : this.lstOrganizzazioniDTO) {
				if (u.getAbilitato())
					organizzazioni.add(new SelectItem(u.getId(), u.getDescrizione()));
			}
		}
		return organizzazioni;
	}
	
	public void initDialog(ArProgettoDTO selected) {
		this.selectedProgetto = selected;
		setModalHeader("Modifica Progetto");		
		this.initPickListOrganizzazioni();
	}
	
	public void initDialogAttivita(ArAttivitaDTO selected) {
		this.selectedAttivita = selected;
	}
	
	public void nuovo() {
		this.selectedProgetto = new ArProgettoDTO();
		this.selectedProgetto.setAbilitato(true);
		this.initPickListOrganizzazioni();
		caricaOrganizzazioni();
		setModalHeader("Nuovo Progetto");
	}
	
	public void nuovaAttivita(ArProgettoDTO selected) {
		this.selectedProgetto = selected;
		this.selectedAttivita = new ArAttivitaDTO();
		this.selectedAttivita.setAbilitato(true);
		this.setSelectedAttivita(selectedAttivita);
		this.getSelectedAttivita().setProgettoId(this.selectedProgetto.getId());
		this.getSelectedAttivita().setProgettoDesc(this.selectedProgetto.getDescrizione());
	}
	
	public void salva() throws Exception {
		try {
			
			boolean existsPr = confArService.existsProgetto(selectedProgetto);
			if(existsPr){
				this.addWarning("Attenzione", "Codice progetto già esistente");
				return;
			}
			
			boolean used = false;
			List<Long> nuoveOrg = new ArrayList<Long>();
			for(ArOrganizzazioneDTO ov : selectedOrganizzazioni.getTarget())
				nuoveOrg.add(ov.getId());
			
			List<ArOrganizzazioneDTO> toRemove = new ArrayList<ArOrganizzazioneDTO>();
			for(ArOrganizzazioneDTO o : this.selectedProgetto.getLstOrganizzazioni()){
				if(!nuoveOrg.contains(o.getId())) toRemove.add(o);
			}
			
			for(ArOrganizzazioneDTO o : toRemove){
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(o.getId());
				dto.setObj2(selectedProgetto.getId());
				boolean exists = confService.verificaUsoArProgettoAttivita(dto);
				if(exists) 
					this.addWarning("Attenzione", "Eliminazione non consentita: il progetto è stato usato presso l'organizzazione "+o.getDescrizione());
				
				used = used|exists;
			}
			
			if(used) return;
			
			this.selectedProgetto.setUserUltimaModifica(getCurrentUsername());
			this.selectedProgetto.setDataUltimaModifica(new Date());
			
			this.selectedProgetto.getLstOrganizzazioni().clear();
			this.selectedProgetto.getLstOrganizzazioni().addAll(this.selectedOrganizzazioni.getTarget());
			
			confArService.salvaProgetto(this.selectedProgetto, toRemove);
			//this.caricaProgetti();
			this.selectedProgetto = null;
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void salvaAttivita() {
		try {
			boolean exists = confArService.existsAttivita(selectedAttivita);
			if(!exists){
				selectedAttivita.setUserUltimaModifica(getCurrentUsername());
				selectedAttivita.setDataUltimaModifica(new Date());
				confArService.salvaAttivita(selectedAttivita);
				//caricaProgetti();
				if(selectedAttivita.getId()==0)
					this.addInfo("La nuova attività '"+selectedAttivita.getDescrizione()+"' è stata associata al progetto: "+selectedAttivita.getProgettoDesc(), "");
				else
					this.addInfo("Attività '"+selectedAttivita.getDescrizione()+"' associata al progetto: '"+selectedAttivita.getProgettoDesc()+"' è stata aggiornata con successo", "");
				this.selectedAttivita = null;
				RequestContext.getCurrentInstance().addCallbackParam("saved", true);
			}else
				this.addWarning("Attenzione", "Codice attività già esistente per il progetto selezionato");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("Errore", "Si è verificato un errore nel salvataggio del sottocorso/attività");
		}
	}
	
	public void eliminaAttivita(ArAttivitaDTO selected){
		try {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj3(selected.getId());
			boolean exists = confService.verificaUsoArProgettoAttivita(dto);
			if(exists){
				this.addWarning("Attenzione", "L'attività selezionata è in uso: eliminazione non consentita");
				return;
			}
			
			confArService.eliminaAttivita(selected.getId());
			//caricaProgetti();
			this.addInfo("Eliminazione dell'attività '"+selected.getDescrizione()+"' completata con successo", "");
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("Errore", "Si è verificato un errore nel'eliminazione del sottocorso/attività");
		}
	}
	
	public void eliminaProgetto(ArProgettoDTO progettoSel){
		try {
			
			if(!progettoSel.getLstAttivita().isEmpty()){
				this.addWarning("Attenzione", "Eliminazione non consentita: rimuovere prima le attività collegate.");
				return;
			}
			
			if(progettoSel.getAltreOrganizzazioni()){
				this.addWarning("Attenzione", "Eliminazione non consentita: il progetto è configurato anche per organizzazioni esterne alla zona sociale.");
				return;
			}

			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj2(progettoSel.getId());
			boolean exists = confService.verificaUsoArProgettoAttivita(dto);
			if(exists){
				this.addWarning("Attenzione", "Eliminazione non consentita: Il progetto selezionato è in uso");
				return;
			}
			
			confArService.eliminaProgetto(progettoSel.getId());
			//caricaProgetti();
			this.addInfo("Eliminazione del progetto completata con successo", "");
			
				
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("Errore", "Si è verificato un errore nel'eliminazione del progetto");
		}
	}
	
	public void eliminaProgetti(){
		if(this.progettiSelezionati!=null && !this.progettiSelezionati.isEmpty()){
			if(this.progettiSelezionati.size()==1)
				this.eliminaProgetto(progettiSelezionati.get(0));
			else{
				this.addWarning("Attenzione", "Eliminare un progetto alla volta");
				return;
			}
		}
	}
	
	public void abilitaProgetti(){
	
		if(this.progettiSelezionati!=null && !this.progettiSelezionati.isEmpty()){
			List<Long> lstIds = new ArrayList<Long>();
			for(ArProgettoDTO p : progettiSelezionati)
				lstIds.add(p.getId());
			confArService.abilitaProgetti(lstIds);
			//this.caricaProgetti();
		}
		this.progettiSelezionati.clear();
	}
	
	public void disabilitaProgetti(){
		if(this.progettiSelezionati!=null && !this.progettiSelezionati.isEmpty()){
			List<Long> lstIds = new ArrayList<Long>();
			for(ArProgettoDTO p : progettiSelezionati)
				lstIds.add(p.getId());
			confArService.disabilitaProgetti(lstIds);
			//this.caricaProgetti();
		}
		this.progettiSelezionati.clear();
	}
	
	public List<ArProgettoDTO> getLstProgetti() {
		return lstProgetti;
	}

	public void setLstProgetti(List<ArProgettoDTO> lstProgetti) {
		this.lstProgetti = lstProgetti;
	}

	public String getModalHeader() {
		return modalHeader;
	}

	public void setModalHeader(String modalHeader) {
		this.modalHeader = modalHeader;
	}

	public ArProgettoDTO getSelectedProgetto() {
		return selectedProgetto;
	}

	public void setSelectedProgetto(ArProgettoDTO selectedProgetto) {
		this.selectedProgetto = selectedProgetto;
	}

	public List<ArProgettoDTO> getProgettiSelezionati() {
		return progettiSelezionati;
	}

	public void setProgettiSelezionati(List<ArProgettoDTO> progettiSelezionati) {
		this.progettiSelezionati = progettiSelezionati;
	}
	
	private void initPickListOrganizzazioni(){
		List<ArOrganizzazioneDTO> orgSource = new ArrayList<ArOrganizzazioneDTO>();
	    List<ArOrganizzazioneDTO> orgTarget = new ArrayList<ArOrganizzazioneDTO>();
	     
		if (selectedProgetto != null &&  selectedProgetto.getLstOrganizzazioni() != null && !selectedProgetto.getLstOrganizzazioni().isEmpty()){ 
		     List<Long> idsSelected = new ArrayList<Long>();
		     for(ArOrganizzazioneDTO o : selectedProgetto.getLstOrganizzazioni())
		    	 idsSelected.add(o.getId());
		     
		     for(ArOrganizzazioneDTO si : lstOrganizzazioniDTO){
		    	 if(!idsSelected.contains(si.getId()))
		    		 orgSource.add(si);
		    	 else
		    		 orgTarget.add(si);
		     }
		     
		}else
			orgSource.addAll(this.lstOrganizzazioniDTO);
		selectedOrganizzazioni = new DualListModel<ArOrganizzazioneDTO>(orgSource, orgTarget);
	}
	
	public List<ArOrganizzazioneDTO> getLstOrganizzazioniDTO() {
		return lstOrganizzazioniDTO;
	}

	public void setLstOrganizzazioniDTO(
			List<ArOrganizzazioneDTO> lstOrganizzazioniDTO) {
		this.lstOrganizzazioniDTO = lstOrganizzazioniDTO;
	}

	public DualListModel<ArOrganizzazioneDTO> getSelectedOrganizzazioni() {
		return selectedOrganizzazioni;
	}

	public void setSelectedOrganizzazioni(DualListModel<ArOrganizzazioneDTO> selectedOrganizzazioni) {
		this.selectedOrganizzazioni = selectedOrganizzazioni;
	}

	public void setOrganizzazioni(List<SelectItem> organizzazioni) {
		this.organizzazioni = organizzazioni;
	}

	public ArAttivitaDTO getSelectedAttivita() {
		return selectedAttivita;
	}

	public void setSelectedAttivita(ArAttivitaDTO selectedAttivita) {
		this.selectedAttivita = selectedAttivita;
	}
	
	public Converter getPickListOrganizzazioniConverter() {
		return new Converter() {
			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
				ArOrganizzazioneDTO ret = null;

				if (submittedValue != null && !submittedValue.trim().equals("")) {
					try {
						Long idOrganizzazione = Long.valueOf(submittedValue);
						for(ArOrganizzazioneDTO o : lstOrganizzazioniDTO){
							if(o.getId().longValue()==idOrganizzazione.longValue()){
								ret = o;
								break;
							}
						}
						
					} catch (Exception e) {
						logger.error(e);
					}
				}

				return ret;
			}

			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {
				if (value != null && value instanceof ArOrganizzazioneDTO)
					return ((ArOrganizzazioneDTO) value).getId().toString();
				else
					return "";
			}
		};
	}

	public ProgettiTableDataModel getProgettiTableDataModel() {
		return progettiTableDataModel;
	}

	public void setProgettiTableDataModel(ProgettiTableDataModel progettiTableDataModel) {
		this.progettiTableDataModel = progettiTableDataModel;
	}
}
