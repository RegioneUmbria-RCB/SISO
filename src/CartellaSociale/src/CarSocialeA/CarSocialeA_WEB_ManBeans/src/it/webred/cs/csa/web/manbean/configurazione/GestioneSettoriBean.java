package it.webred.cs.csa.web.manbean.configurazione;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.jsf.interfaces.IGestioneSettori;
import it.webred.cs.jsf.manbean.ComuneResidenzaMan;
import it.webred.cs.jsf.manbean.IndirizzoMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.jsf.bean.ComuneBean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class GestioneSettoriBean extends CsUiCompBaseBean implements IGestioneSettori {

	private AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
	private LuoghiService luoghiService = (LuoghiService) getEjb("CT_Service", "CT_Config_Manager", "LuoghiServiceBean");

	//organizzazioni
	private List<CsOOrganizzazione> lstOrganizzazioni;
	private List<CsOOrganizzazione> selectedOrganizzazioni;
	private CsOOrganizzazione selectedOrganizzazione;
	private CsOOrganizzazione newOrganizzazione;
	private ComuneResidenzaMan newComune;
	private ComuneBean comuneDefault;
	private boolean enteEsterno;
	
	//settori
	private boolean renderSettori;
	private List<CsOSettoreBASIC> lstSettori;
	private List<CsOSettoreBASIC> selectedSettori;
	private CsOSettore newSettore;
	private IndirizzoMan newIndirizzo;
	
	@PostConstruct
	private void init() {
		
		newOrganizzazione = new CsOOrganizzazione();
		newComune = new ComuneResidenzaMan();
		newSettore = new CsOSettore();
		newIndirizzo = new IndirizzoMan();
		caricaOrganizzazioni();
		setEnteEsterno(false);
		
		CeTBaseObject cet = new CeTBaseObject();
		fillEnte(cet);
		AmTabComuni amComune = luoghiService.getComuneItaByBelfiore(cet.getEnteId());
		if(amComune!=null) comuneDefault = new ComuneBean(amComune);
		else comuneDefault = null;
		newComune.setComune(comuneDefault);
	}
	
	public void onOrganizzazioneSelect(SelectEvent event) {  
		renderSettori = false;
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
			
			try {
				lstSettori = confService.findSettoreBASICByOrganizzazione(dto);
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
		} else addWarning("Seleziona almeno una Organizzazione", "");
		
	}
	
	public void nuovaOrganizzazione() {
		
		if(newOrganizzazione.getNome() != null && !"".equals(newOrganizzazione.getNome())) {
			try {
				
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				
				newOrganizzazione.setAbilitato("1");
				newOrganizzazione.setTooltip(newOrganizzazione.getNome());
				if(!enteEsterno){
					if(newComune.getComune() != null) {
						AmTabComuni amComune = luoghiService.getComuneItaByIstat(newComune.getComune().getCodIstatComune());
						newOrganizzazione.setBelfiore(amComune.getCodNazionale());
					} else newOrganizzazione.setBelfiore(dto.getEnteId());
				}else
					newOrganizzazione.setBelfiore(null);
				
				dto.setObj(newOrganizzazione);
				confService.salvaOrganizzazione(dto);
				
				caricaOrganizzazioni();
				newOrganizzazione = new CsOOrganizzazione();
				newComune = new ComuneResidenzaMan();
				newComune.setComune(comuneDefault);
				addInfoFromProperties("salva.ok");
				
			} catch(Exception e) {
				addErrorFromProperties("salva.error");
				logger.error(e.getMessage(),e);
			}
		} else addWarning("inserire almeno il nome", "");
		
	}
	
	public void aggiungiSettore() {
		
		if(newSettore.getNome() != null && !"".equals(newSettore.getNome())) {
			try {
				
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				
				newSettore.setAbilitato(true);
				newSettore.setCsOOrganizzazione(selectedOrganizzazione);
				newSettore.setTooltip(newSettore.getNome());
				
				//indirizzo
				if(newIndirizzo.getSelectedIndirizzo() != null && !"".equals(newIndirizzo.getSelectedIndirizzo())) {
					CsAAnaIndirizzo anaIndirizzo = new CsAAnaIndirizzo();
					anaIndirizzo.setIndirizzo(newIndirizzo.getSelectedIndirizzo());
					anaIndirizzo.setCivicoNumero(newIndirizzo.getSelectedCivico());
					anaIndirizzo.setCodiceVia(newIndirizzo.getSelectedIdVia());
					String belfiore = dto.getEnteId();
					if(selectedOrganizzazione.getBelfiore() != null)
						belfiore = selectedOrganizzazione.getBelfiore();
					AmTabComuni amComune = luoghiService.getComuneItaByBelfiore(belfiore);
					anaIndirizzo.setComCod(amComune.getCodIstatComune());
					anaIndirizzo.setComDes(amComune.getDenominazione());
					anaIndirizzo.setProv(amComune.getSiglaProv());
					newSettore.setCsAAnaIndirizzo(anaIndirizzo);
				}
				
				dto.setObj(newSettore);
				confService.salvaSettore(dto);
				
				caricaSettori();
				newSettore = new CsOSettore();
				newIndirizzo = new IndirizzoMan();
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
				for(CsOSettoreBASIC cs: selectedSettori) {
					cs.setAbilitato(true);
					dto.setObj(cs);
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
				for(CsOSettoreBASIC cs: selectedSettori) {
					cs.setAbilitato(false);
					dto.setObj(cs);
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
				for(CsOSettoreBASIC cs: selectedSettori) {
					dto.setObj(cs);
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

	public List<CsOSettoreBASIC> getLstSettori() {
		return lstSettori;
	}

	public void setLstSettori(List<CsOSettoreBASIC> lstSettori) {
		this.lstSettori = lstSettori;
	}

	public List<CsOSettoreBASIC> getSelectedSettori() {
		return selectedSettori;
	}

	public void setSelectedSettori(List<CsOSettoreBASIC> selectedSettori) {
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

	public ComuneResidenzaMan getNewComune() {
		return newComune;
	}

	public void setNewComune(ComuneResidenzaMan newComune) {
		this.newComune = newComune;
	}

	public boolean isEnteEsterno() {
		return enteEsterno;
	}

	public void setEnteEsterno(boolean enteEsterno) {
		this.enteEsterno = enteEsterno;
	}
	
}
