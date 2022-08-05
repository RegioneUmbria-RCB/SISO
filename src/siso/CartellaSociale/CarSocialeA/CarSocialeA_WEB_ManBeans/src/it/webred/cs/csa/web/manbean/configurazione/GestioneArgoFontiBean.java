package it.webred.cs.csa.web.manbean.configurazione;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import it.umbriadigitale.argo.ejb.client.cs.bean.ArConfigurazioneService;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArAttivitaDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArFondoDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArFonteDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArOrganizzazioneDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArProgettoDTO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

@ManagedBean
@ViewScoped
public class GestioneArgoFontiBean extends CsUiCompBaseBean {

	private ArConfigurazioneService confArService = (ArConfigurazioneService) getArgoEjb( "ArConfigurazioneServiceBean");
	
	private FontiTableDataModel fontiTableDataModel;
	private List<ArFonteDTO> lstFonti;
	
	private String modalHeader;
	private ArFonteDTO selectedFonte;
	private ArAttivitaDTO selectedAttivita;
	private List<ArFonteDTO> fontiSelezionate;
	
	private List<ArOrganizzazioneDTO> lstOrganizzazioniDTO;
	
	private DualListModel<ArOrganizzazioneDTO> selectedOrganizzazioni;
	private List<SelectItem> organizzazioni = new LinkedList<SelectItem>();
	private List<SelectItem> fondi;
	private List<SelectItem> progetti;
	
	public GestioneArgoFontiBean(){
		caricaOrganizzazioni();
		caricaFondi();
		
		fontiTableDataModel = new FontiTableDataModel();
		fontiTableDataModel.setZsCorrente(getZonaSociale());
	}
	
	private void caricaOrganizzazioni() {

		try {
			String zsCorrente = getZonaSociale();
			lstOrganizzazioniDTO = confArService.getListaOrganizzazioniDTO(zsCorrente);
			
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}

	}
	
	private void caricaFondi() {
		fondi = new ArrayList<SelectItem>();
		try {
			List<ArFondoDTO> lstFondiDTO = confArService.getListaFondiDTO();
			for(ArFondoDTO f: lstFondiDTO) {
				//String desc = "["+f.getCodiceMemo()+"] "+f.getDescrizione();
				SelectItem si = new SelectItem(f.getId(), f.getDescrizione());
				si.setDisabled(!f.isAbilitato());
				fondi.add(si);
			}
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}

	}
	
	 public void onOrganizzazioneTransfer(TransferEvent event) {
		caricaProgetti();
		Long prId = this.selectedFonte.getProgettoDefaultId();
		boolean trovato = false;
		for(SelectItem si : progetti) {
			if(prId==null || prId.equals(si.getValue())) {
				trovato = true;
				break;
			}
		}
		
		if(!trovato)
			this.selectedFonte.setProgettoDefaultId(null);
	}

	private void caricaProgetti() {
		progetti = new ArrayList<SelectItem>();
		try {
			List<Long> ids = new ArrayList<Long>();
			if(this.selectedOrganizzazioni!=null && this.selectedOrganizzazioni.getTarget()!=null) {
				for(ArOrganizzazioneDTO org : selectedOrganizzazioni.getTarget())
					ids.add(org.getId());
			}
			
			List<ArProgettoDTO> lstProgettiDTO = confArService.getListaProgetti(ids);
			for(ArProgettoDTO p : lstProgettiDTO) {
				SelectItem si = new SelectItem(p.getId(), "["+p.getCodiceMemo()+"] "+ p.getDescrizione());
				si.setDisabled(!p.isAbilitato());
				progetti.add(si);
			}
			
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
	
	public void initDialog(ArFonteDTO selected) {
		this.selectedFonte = selected;
		setModalHeader("Modifica Fonte di Finanziamento");		
		this.initPickListOrganizzazioni();
		this.caricaProgetti();
	}
	
	public void initDialogAttivita(ArAttivitaDTO selected) {
		this.selectedAttivita = selected;
	}
	
	public void nuovo() {
		this.selectedFonte = new ArFonteDTO();
		this.selectedFonte.setAbilitato(true);
		this.initPickListOrganizzazioni();
		caricaOrganizzazioni();
		caricaFondi();
		setModalHeader("Nuova Fonte di Finanziamento");
	}
	
	public void nuovaAttivita(ArFonteDTO selected) {
		this.selectedFonte = selected;
		this.selectedAttivita = new ArAttivitaDTO();
		this.selectedAttivita.setAbilitato(true);
		this.setSelectedAttivita(selectedAttivita);
		this.getSelectedAttivita().setProgettoId(this.selectedFonte.getId());
		this.getSelectedAttivita().setProgettoDesc(this.selectedFonte.getDescrizione());
	}
	
	public void salva() throws Exception {
		try {
			
			List<String> msg = new ArrayList<String>();
			
			if(selectedFonte.getFondoId()==null || selectedFonte.getFondoId()==0)
				msg.add("Fondo");
			
			if(StringUtils.isEmpty(selectedFonte.getCodiceMemo()))
				msg.add("Codice");
			
			if(StringUtils.isEmpty(selectedFonte.getDescrizione()))
				msg.add("Descrizione");
			
			
			if(!msg.isEmpty()) {
				this.addWarning("I seguenti campi sono obbligatori:", msg);
				return;
			}
			
			boolean existsPr = confArService.existsFonteFinanziamento(selectedFonte);
			if(existsPr) {
				this.addWarning("Codice fonte di finanziamento già esistente", "");
				return;
			}
		
				
			
			boolean used = false;
			List<Long> nuoveOrg = new ArrayList<Long>();
			for(ArOrganizzazioneDTO ov : selectedOrganizzazioni.getTarget())
				nuoveOrg.add(ov.getId());
			
			List<ArOrganizzazioneDTO> toRemove = new ArrayList<ArOrganizzazioneDTO>();
			for(ArOrganizzazioneDTO o : this.selectedFonte.getLstOrganizzazioni()){
				if(!nuoveOrg.contains(o.getId())) toRemove.add(o);
			}
			
			for(ArOrganizzazioneDTO o : toRemove){
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(o.getId());
				dto.setObj2(selectedFonte.getId());
				boolean exists = interventoService.verificaUsoArFonte(dto);
				if(exists) 
					this.addWarning("Attenzione", "Eliminazione non consentita: il progetto è stato usato presso l'organizzazione "+o.getDescrizione());
				
				used = used|exists;
			}
			
			if(used) return;
			
			this.selectedFonte.setUserUltimaModifica(getCurrentUsername());
			this.selectedFonte.setDataUltimaModifica(new Date());
			
			this.selectedFonte.getLstOrganizzazioni().clear();
			this.selectedFonte.getLstOrganizzazioni().addAll(this.selectedOrganizzazioni.getTarget());
			
			confArService.salvaFonte(this.selectedFonte, toRemove);
			this.selectedFonte = null;
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void eliminaFonte(ArFonteDTO sel){
		try {
			
			if(sel.getAltreOrganizzazioni()){
				this.addWarning("Attenzione", "Eliminazione non consentita: la fonte di finanziamento è configurata anche per organizzazioni esterne alla zona sociale.");
				return;
			}

			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj2(sel.getId());
			boolean exists = interventoService.verificaUsoArFonte(dto);
			if(exists){
				this.addWarning("Attenzione", "Eliminazione non consentita: La fonte di finanziamento selezionata è in uso");
				return;
			}
			
			confArService.eliminaFonte(sel.getId());
			//caricaProgetti();
			this.addInfo("Eliminazione della fonte di finanziamento completata con successo", "");
			
				
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("Errore", "Si è verificato un errore nel'eliminazione del progetto");
		}
	}
	
	public void eliminaFonti(){
		if(this.fontiSelezionate!=null && !this.fontiSelezionate.isEmpty()){
			if(this.fontiSelezionate.size()==1)
				this.eliminaFonte(fontiSelezionate.get(0));
			else{
				this.addWarning("Attenzione", "Eliminare una fonte alla volta");
				return;
			}
		}
	}
	
	public void abilitaFonti(){
	
		if(this.fontiSelezionate!=null && !this.fontiSelezionate.isEmpty()){
			List<Long> lstIds = new ArrayList<Long>();
			for(ArFonteDTO p : fontiSelezionate)
				lstIds.add(p.getId());
			confArService.abilitaFonte(lstIds);
			//this.caricaProgetti();
		}
		this.fontiSelezionate.clear();
	}
	
	public void disabilitaFonti(){
		if(this.fontiSelezionate!=null && !this.fontiSelezionate.isEmpty()){
			List<Long> lstIds = new ArrayList<Long>();
			for(ArFonteDTO p : fontiSelezionate)
				lstIds.add(p.getId());
			confArService.disabilitaFonti(lstIds);
			//this.caricaProgetti();
		}
		this.fontiSelezionate.clear();
	}

	public String getModalHeader() {
		return modalHeader;
	}

	public void setModalHeader(String modalHeader) {
		this.modalHeader = modalHeader;
	}

	public ArFonteDTO getselectedFonte() {
		return selectedFonte;
	}

	public void setselectedFonte(ArFonteDTO selectedFonte) {
		this.selectedFonte = selectedFonte;
	}
	
	private void initPickListOrganizzazioni(){
		List<ArOrganizzazioneDTO> orgSource = new ArrayList<ArOrganizzazioneDTO>();
	    List<ArOrganizzazioneDTO> orgTarget = new ArrayList<ArOrganizzazioneDTO>();
	     
		if (selectedFonte != null &&  selectedFonte.getLstOrganizzazioni() != null && !selectedFonte.getLstOrganizzazioni().isEmpty()){ 
		     List<Long> idsSelected = new ArrayList<Long>();
		     for(ArOrganizzazioneDTO o : selectedFonte.getLstOrganizzazioni())
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

	public FontiTableDataModel getFontiTableDataModel() {
		return fontiTableDataModel;
	}

	public void setFontiTableDataModel(FontiTableDataModel fontiTableDataModel) {
		this.fontiTableDataModel = fontiTableDataModel;
	}

	public List<ArFonteDTO> getLstFonti() {
		return lstFonti;
	}

	public void setLstFonti(List<ArFonteDTO> lstFonti) {
		this.lstFonti = lstFonti;
	}

	public ArFonteDTO getSelectedFonte() {
		return selectedFonte;
	}

	public void setSelectedFonte(ArFonteDTO selectedFonte) {
		this.selectedFonte = selectedFonte;
	}

	public List<ArFonteDTO> getFontiSelezionate() {
		return fontiSelezionate;
	}

	public void setFontiSelezionate(List<ArFonteDTO> fontiSelezionate) {
		this.fontiSelezionate = fontiSelezionate;
	}

	public List<SelectItem> getFondi() {
		return fondi;
	}

	public void setFondi(List<SelectItem> fondi) {
		this.fondi = fondi;
	}
	
	public List<SelectItem> getProgetti() {
		return progetti;
	}

	public void setProgetti(List<SelectItem> progetti) {
		this.progetti = progetti;
	}
}
