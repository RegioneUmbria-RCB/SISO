package it.webred.cs.csa.web.manbean.configurazione;

import it.webred.cs.csa.ejb.client.AccessTableCatSocialeSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.configurazione.SettoreCatSocialeDTO;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.data.model.CsRelSettoreCatsoc;
import it.webred.cs.data.model.CsRelSettoreCatsocPK;
import it.webred.cs.data.model.CsTbSecondoLivello;
import it.webred.cs.jsf.interfaces.IGestioneSettori;
import it.webred.cs.jsf.manbean.ComuneResidenzaMan;
import it.webred.cs.jsf.manbean.IndirizzoMan;
import it.webred.cs.jsf.manbean.comuneNazione.ComuneGenericMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmComune;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.jsf.bean.ComuneBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class GestioneSettoriBean extends CsUiCompBaseBean implements IGestioneSettori {

	private AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getCarSocialeEjb("AccessTableConfigurazioneSessionBean");
	private AccessTableCatSocialeSessionBeanRemote catSocService = (AccessTableCatSocialeSessionBeanRemote) getCarSocialeEjb( "AccessTableCatSocialeSessionBean");
	
	
	//organizzazioni
	private List<CsOOrganizzazione> lstOrganizzazioni;
	private List<CsOOrganizzazione> selectedOrganizzazioni;
	private CsOOrganizzazione selectedOrganizzazione;
	private CsOOrganizzazione newOrganizzazione;
	private ComuneResidenzaMan newComune;
	//private boolean enteEsterno;
	private boolean enteAccessoAM;
	
	private List<SelectItem> lstAmEnti;
	
	//settori
	private boolean renderSettori;
	private List<SettoreCatSocialeDTO> lstSettori;
	private List<SettoreCatSocialeDTO> selectedSettori;
	private CsOSettore newSettore;
	private IndirizzoMan newIndirizzo;
	private ComuneGenericMan comuneSettoreMan; //Indirizzo del settore
	
	//cat.sociali
	private List<SelectItem> lstCatSociali;
	private List<Long> lstCatSocialiSel;
	
	//II liv
	private List<SelectItem> lst2Liv;
	private String nuovo2Liv;
		
	@PostConstruct
	private void init() {
		
		newOrganizzazione = new CsOOrganizzazione();
		
		newComune = new ComuneResidenzaMan();
		newSettore = new CsOSettore();
		newIndirizzo = new IndirizzoMan();
		comuneSettoreMan = new ComuneGenericMan("Comune");
		caricaOrganizzazioni();
		caricaCatSociali();
		carica2Liv();
		enteAccessoAM = true;
		onChangeEnteAccesso();
	/*	
		setEnteEsterno(false);
		onChangeEnteEsterno();
		*/
		loadAmEnti();
	}
	
	public void caricaCatSociali() {
		
		try {
			
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			List<CsCCategoriaSociale> lst = catSocService.getCategorieSocialiAll(cet);
			
			lstCatSociali = new ArrayList<SelectItem>();
			for(CsCCategoriaSociale c : lst){
				SelectItem si = new SelectItem(c.getId(), c.getDescrizione());
				si.setDisabled(!c.getAbilitato());
				this.lstCatSociali.add(si);
			}
			
		} catch(Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	private void carica2Liv() {
		
		try {
			
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			List<CsTbSecondoLivello> lst = confService.getListaSecondoLivello(cet);
			
			this.setLst2Liv(new ArrayList<SelectItem>());
			for(CsTbSecondoLivello c : lst){
				SelectItem si = new SelectItem(c.getId(), c.getDescrizione());
				si.setDisabled(!c.getAbilitato());
				this.lst2Liv.add(si);
			}
			
		} catch(Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	public void onOrganizzazioneSelect(SelectEvent event) {  
		renderSettori = false;
    }
	
	private void loadAmEnti(){
		lstAmEnti=new ArrayList<SelectItem>();
		try{
			List<AmComune> lst = comuneService.getListaComune();
			for(AmComune a : lst)
				lstAmEnti.add(new SelectItem(a.getBelfiore(), a.getDescrizione()));
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			addError("Errore recupero lista enti", e.getMessage());
		}
		
	}
	
	public void onChangeAmEnte(){
		this.newOrganizzazione.setNome(null);
		
		if(this.newOrganizzazione.getCodRouting()!=null){
			AmComune c = comuneService.getComune(this.newOrganizzazione.getCodRouting());
			if(c!=null)newOrganizzazione.setNome(c.getDescrizione());
			
			//Ricerco, tramite il belfiore, se l'organizzazione è un comune per valorizare il codice catastale
			AmTabComuni amComune = luoghiService.getComuneItaByBelfiore(this.newOrganizzazione.getCodRouting());
			ComuneBean cb = null;
			if(amComune!=null)
				cb = new ComuneBean(amComune); 
			else
				cb = null; 
			
			newComune.setComune(cb);
			newOrganizzazione.setCodCatastale(amComune!=null ? amComune.getCodNazionale() : null);
			newOrganizzazione.setCodExportFlusso(amComune!=null ? amComune.getCodNazionale() : null);
		}
	}
	
/*	public void onChangeEnteEsterno(){
		if(this.enteEsterno){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			
			newOrganizzazione.setCodRouting(null);
			
				if(newComune.getComune() != null) {
					AmTabComuni amComune = luoghiService.getComuneItaByIstat(newComune.getComune().getCodIstatComune());
					newOrganizzazione.setCodCatastale(amComune.getCodNazionale());
				} else 
					newOrganizzazione.setCodCatastale(dto.getEnteId());
				newOrganizzazione.setFlagRiceviSchedeAccoglienza(true);
				
				dto.setObj(newOrganizzazione.getCodCatastale());
				List<CsOOrganizzazione> lst = confService.getOrganizzazioniByCodCatastale(dto);
				if(lst.isEmpty())
					newOrganizzazione.setCodRouting(newOrganizzazione.getCodCatastale());
				else{
					addError("Esiste già un comune con codice "+newOrganizzazione.getCodCatastale(), 
							 "Definire un codice univoco, corrispondente a quello configurato per il datarouting");
					return;
				}	
		}else{
			
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			AmTabComuni amComune = luoghiService.getComuneItaByBelfiore(cet.getEnteId());
			if(amComune!=null) comuneDefault = new ComuneBean(amComune);
			else comuneDefault = null;
			newComune.setComune(comuneDefault);
			
		}
	}*/
	
	public void onChangeEnteAccesso(){
		newOrganizzazione = new CsOOrganizzazione();
		newOrganizzazione.setFlagRiceviSchedeAccoglienza(false);
		onChangeAmEnte();
		newComune = new ComuneResidenzaMan();		
	}
	
	private void caricaOrganizzazioni() {
		
		try {
			
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			lstOrganizzazioni = confService.getOrganizzazioniAll(cet);
			
		} catch(Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	public void caricaSettori() {
		
		if(selectedOrganizzazioni != null && !selectedOrganizzazioni.isEmpty()) {
			
			if(selectedOrganizzazioni.size() > 1) {
				addWarning("Attenzione", "Selezionare solo 1 elemento");
				return;
			}
			
			renderSettori = true;
			selectedOrganizzazione = selectedOrganizzazioni.get(0);
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(selectedOrganizzazione.getId());
			
			String codCatastale = selectedOrganizzazione.getCodCatastale();
			AmTabComuni comune = luoghiService.getComuneItaByBelfiore(codCatastale);
			if(comune!=null){
				ComuneBean cb = new ComuneBean(comune);
				comuneSettoreMan.setComune(cb);
			}
		
			try {
				lstSettori = confService.findSettoreDTOByOrganizzazione(dto);
			} catch(Exception e) {
				addErrorFromProperties("caricamento.error");
				logger.error(e.getMessage(),e);
			}
			
		} else addWarning("Seleziona una Organizzazione", "");
	}
	
	public void attivaOrganizzazioni() {
		
		if(selectedOrganizzazioni != null) {
			try {
				
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				for(CsOOrganizzazione org: selectedOrganizzazioni) {
					if(!"1".equals(org.getAbilitato())) {
						org.setAbilitato("1");
						dto.setObj(org);
						confService.updateOrganizzazione(dto);
					}
				}
				
				caricaOrganizzazioni();
				addInfoFromProperties("salva.ok");
				
			} catch(Exception e) {
				addErrorFromProperties("salva.error");
				logger.error(e.getMessage(),e);
			}
		} else addWarning("Seleziona almeno una Organizzazione", "");
		
	}
	
	public void disattivaOrganizzazioni() {
		
		if(selectedOrganizzazioni != null) {
			try {
				
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				for(CsOOrganizzazione org: selectedOrganizzazioni) {
					if("1".equals(org.getAbilitato())) {
						org.setAbilitato("0");
						dto.setObj(org);
						confService.updateOrganizzazione(dto);
					}
				}
				
				caricaOrganizzazioni();
				addInfoFromProperties("salva.ok");
				
			} catch(Exception e) {
				addErrorFromProperties("salva.error");
				logger.error(e.getMessage(),e);
			}
		} else addWarning("Seleziona almeno una Organizzazione", "");
		
	}
	
	public void eliminaOrganizzazioni() {
		
		if(selectedOrganizzazioni != null) {
			try {
				
				boolean ok = true;
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				for(CsOOrganizzazione org: selectedOrganizzazioni) {
					dto.setObj(org.getId());
					
					try {
						confService.eliminaOrganizzazione(dto);
					} catch(Exception e) {
						ok = false;
					}
					
				}
				
				renderSettori = false;
				caricaOrganizzazioni();
				
				if(ok)
					addInfoFromProperties("salva.ok");
				else addWarning("Eliminazione non eseguita completamente", "Una o più Organizzazioni selezionate hanno dati collegati");
				
			} catch(Exception e) {
				addErrorFromProperties("salva.error");
				logger.error(e.getMessage(),e);
			}
		} else addWarning("Seleziona almeno un'organizzazione", "");
		
	}
	
	public void nuovaOrganizzazione() {
		
		if(newOrganizzazione.getNome() != null && !"".equals(newOrganizzazione.getNome())) {
			try {
				
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				
				newOrganizzazione.setAbilitato("1");
				newOrganizzazione.setTooltip(newOrganizzazione.getNome());
				
				if(!this.enteAccessoAM){
					newOrganizzazione.setFlagRiceviSchedeAccoglienza(false);
					newOrganizzazione.setFlagCapofila(false);
					newOrganizzazione.setCodRouting(null);
					newOrganizzazione.setCodCatastale(null);
					newOrganizzazione.setCodExportFlusso(null);
				}else{
					
					if(newOrganizzazione.getCodRouting()!=null){
						dto.setObj(newOrganizzazione.getCodRouting());
						CsOOrganizzazione o = confService.getOrganizzazioneByCodFittizio(dto);
						if(o!=null){
							addError("Impossibile inserire la nuova organizzazione","Esiste già un comune con cod.identificativo: "+newOrganizzazione.getCodRouting());
							return;
						}
					}
					
					String codIstatComune = newComune.getComune()!=null ? newComune.getComune().getCodIstatComune() : null;
					AmTabComuni amComune = luoghiService.getComuneItaByIstat(codIstatComune);
					newOrganizzazione.setCodCatastale(amComune!=null ? amComune.getCodNazionale() : null);
				}
				
				dto.setObj(newOrganizzazione);
				confService.salvaOrganizzazione(dto);
				
				caricaOrganizzazioni();
				newOrganizzazione = new CsOOrganizzazione();
				newComune = new ComuneResidenzaMan();
				addInfoFromProperties("salva.ok");
				
			} catch(Exception e) {
				addErrorFromProperties("salva.error");
				logger.error(e.getMessage(),e);
			}
		} else addWarning("inserire almeno il nome", "");
		
	}
	
	public void aggiungiSettore() {
		
		if(!StringUtils.isBlank(newSettore.getNome())) {
			try {
				
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				
				newSettore.setAbilitato(true);
				newSettore.setCsOOrganizzazione(selectedOrganizzazione);
				newSettore.setTooltip(newSettore.getNome());
				
				//indirizzo
				if(!StringUtils.isBlank(newIndirizzo.getSelectedIndirizzo())) {
					CsAAnaIndirizzo anaIndirizzo = new CsAAnaIndirizzo();
					anaIndirizzo.setIndirizzo(newIndirizzo.getSelectedIndirizzo());
					anaIndirizzo.setCivicoNumero(newIndirizzo.getSelectedCivico());
					anaIndirizzo.setCodiceVia(newIndirizzo.getSelectedIdVia());
					
					ComuneBean comune = comuneSettoreMan.getComune();
					if(comune!=null){
						anaIndirizzo.setComCod(comune.getCodIstatComune());
						anaIndirizzo.setComDes(comune.getDenominazione());
						anaIndirizzo.setProv(comune.getSiglaProv());
					}
					newSettore.setCsAAnaIndirizzo(anaIndirizzo);
				}
				
				dto.setObj(newSettore);
				dto.setObj2(lstCatSocialiSel);
				confService.salvaSettore(dto);
				
				caricaSettori();
				newSettore = new CsOSettore();
				newIndirizzo = new IndirizzoMan();
				comuneSettoreMan = new ComuneGenericMan("Comune");
				lstCatSocialiSel= new ArrayList<Long>();
				addInfoFromProperties("salva.ok");
				
			} catch(Exception e) {
				addErrorFromProperties("salva.error");
				logger.error(e.getMessage(),e);
			}
		} else addWarning("inserire almeno il nome", "");
		
	}
		
	public void attivaSettori() {
		
		if(selectedSettori != null) {
			try {
				
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				for(SettoreCatSocialeDTO cs: selectedSettori) {
					cs.getSettore().setAbilitato(true);
					dto.setObj(cs.getSettore());
					confService.updateSettore(dto);
				}
				
				caricaSettori();
				addInfoFromProperties("salva.ok");
				
			} catch(Exception e) {
				addErrorFromProperties("salva.error");
				logger.error(e.getMessage(),e);
			}
		} else addWarning("Seleziona almeno un Settore", "");
		
	}
	
	public void disattivaSettori() {
		
		if(selectedSettori != null) {
			try {
				
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				for(SettoreCatSocialeDTO cs: selectedSettori) {
					cs.getSettore().setAbilitato(false);
					dto.setObj(cs.getSettore());
					confService.updateSettore(dto);
				}
				
				caricaSettori();
				addInfoFromProperties("salva.ok");
				
			} catch(Exception e) {
				addErrorFromProperties("salva.error");
				logger.error(e.getMessage(),e);
			}
		} else addWarning("Seleziona almeno un Settore", "");
		
	}
	
	public void eliminaSettori() {
		
		if(selectedSettori != null) {
			try {
				
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				for(SettoreCatSocialeDTO cs: selectedSettori) {
					dto.setObj(cs.getSettore());
					confService.eliminaSettore(dto);
				}
				
				caricaSettori();
				addInfoFromProperties("salva.ok");
				
			} catch(Exception e) {
				addErrorFromProperties("salva.error");
				logger.error(e.getMessage(),e);
			}
		} else addWarning("Seleziona almeno un Settore", "");
		
	}

	public List<CsOOrganizzazione> getLstOrganizzazioni() {
		return lstOrganizzazioni;
	}

	public void setLstOrganizzazioni(List<CsOOrganizzazione> lstOrganizzazioni) {
		this.lstOrganizzazioni = lstOrganizzazioni;
	}

	public List<CsOOrganizzazione> getSelectedOrganizzazioni() {
		return selectedOrganizzazioni;
	}

	public void setSelectedOrganizzazioni(
			List<CsOOrganizzazione> selectedOrganizzazioni) {
		this.selectedOrganizzazioni = selectedOrganizzazioni;
	}

	public CsOOrganizzazione getSelectedOrganizzazione() {
		return selectedOrganizzazione;
	}

	public void setSelectedOrganizzazione(CsOOrganizzazione selectedOrganizzazione) {
		this.selectedOrganizzazione = selectedOrganizzazione;
	}

	public CsOOrganizzazione getNewOrganizzazione() {
		return newOrganizzazione;
	}

	public void setNewOrganizzazione(CsOOrganizzazione newOrganizzazione) {
		this.newOrganizzazione = newOrganizzazione;
	}

	public boolean isRenderSettori() {
		return renderSettori;
	}

	public void setRenderSettori(boolean renderSettori) {
		this.renderSettori = renderSettori;
	}

	public List<SettoreCatSocialeDTO> getLstSettori() {
		return lstSettori;
	}

	public void setLstSettori(List<SettoreCatSocialeDTO> lstSettori) {
		this.lstSettori = lstSettori;
	}

	public List<SettoreCatSocialeDTO> getSelectedSettori() {
		return selectedSettori;
	}

	public void setSelectedSettori(List<SettoreCatSocialeDTO> selectedSettori) {
		this.selectedSettori = selectedSettori;
	}

	public CsOSettore getNewSettore() {
		return newSettore;
	}

	public void setNewSettore(CsOSettore newSettore) {
		this.newSettore = newSettore;
	}

	public IndirizzoMan getNewIndirizzo() {
		return newIndirizzo;
	}

	public void setNewIndirizzo(IndirizzoMan newIndirizzo) {
		this.newIndirizzo = newIndirizzo;
	}
	
	public ComuneGenericMan getComuneSettoreMan() {
		return comuneSettoreMan;
	}

	public void setComuneSettoreMan(ComuneGenericMan comuneSettoreMan) {
		this.comuneSettoreMan = comuneSettoreMan;
	}

	public ComuneResidenzaMan getNewComune() {
		return newComune;
	}

	public void setNewComune(ComuneResidenzaMan newComune) {
		this.newComune = newComune;
	}


	public boolean isEnteAccessoAM() {
		return enteAccessoAM;
	}

	public void setEnteAccessoAM(boolean enteAccessoAM) {
		this.enteAccessoAM = enteAccessoAM;
	}

	public List<SelectItem> getLstAmEnti() {
		return lstAmEnti;
	}

	public void setLstAmEnti(List<SelectItem> lstAmEnti) {
		this.lstAmEnti = lstAmEnti;
	}

	@Override
	public List<SelectItem> getLstCatSociali() {
		return lstCatSociali;
	}

	public void setLstCatSociali(List<SelectItem> lstCatSociali) {
		this.lstCatSociali = lstCatSociali;
	}

	@Override
	public List<Long> getLstCatSocialiSel() {
		return lstCatSocialiSel;
	}

	@Override
	public void setLstCatSocialiSel(List<Long> lstCatSocialiSel) {
		this.lstCatSocialiSel = lstCatSocialiSel;
	}

	public List<SelectItem> getLst2Liv() {
		return lst2Liv;
	}

	public void setLst2Liv(List<SelectItem> lst2Liv) {
		this.lst2Liv = lst2Liv;
	}

	public String getNuovo2Liv() {
		return nuovo2Liv;
	}

	public void setNuovo2Liv(String nuovo2Liv) {
		this.nuovo2Liv = nuovo2Liv;
	}
	
	@Override
	public void salva2Liv(){
		
		if(StringUtils.isBlank(nuovo2Liv)){
		  addWarning("Gestione II Livello","Inserire il nome");
		  return;
		}
		
		//Verifico che il nome non sia già presente
		boolean trovato = false;
		int i = 0;
		for(SelectItem s : this.lst2Liv){
			if(s.getLabel().equalsIgnoreCase(this.nuovo2Liv)){ 
				trovato = true; 
				break;
			}
		}
		if(trovato){
		  addWarning("Gestione II Livello","Nome già presente");
		  return;
		}
		
			try {
				
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				
				dto.setObj(nuovo2Liv);
				confService.salva2Livello(dto);
				
				this.carica2Liv();
				this.nuovo2Liv = null;
				addInfoFromProperties("salva.ok");
				RequestContext.getCurrentInstance().addCallbackParam("saved", true);
				
			} catch(Exception e) {
				addErrorFromProperties("salva.error");
				logger.error(e.getMessage(),e);
			}
	}
	
}
