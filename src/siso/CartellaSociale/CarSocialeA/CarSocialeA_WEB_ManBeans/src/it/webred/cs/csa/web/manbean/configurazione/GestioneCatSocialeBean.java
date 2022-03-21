package it.webred.cs.csa.web.manbean.configurazione;

import it.webred.cs.csa.ejb.client.configurazione.AccessTableCatSocialeSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsRelCatsocTipoInterPK;
import it.webred.cs.data.model.CsRelSettCatsocEsclusiva;
import it.webred.cs.data.model.CsRelSettCatsocEsclusivaPK;
import it.webred.cs.data.model.CsRelSettCsocTipoInter;
import it.webred.cs.data.model.CsRelSettoreCatsoc;
import it.webred.cs.data.model.CsRelSettoreCatsocPK;
import it.webred.cs.data.model.CsTbTipoDiario;
import it.webred.cs.jsf.interfaces.IGestioneCatSociale;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class GestioneCatSocialeBean extends CsUiCompBaseBean implements IGestioneCatSociale {

	private AccessTableCatSocialeSessionBeanRemote catSocService = (AccessTableCatSocialeSessionBeanRemote) getCarSocialeEjb( "AccessTableCatSocialeSessionBean");
	
	//cat sociale
	private List<CsCCategoriaSociale> lstCatSociali;
	private List<CsCCategoriaSociale> selectedCatSociali;
	private CsCCategoriaSociale selectedCatSociale;
	private String newCatSocDescr;
	
	//settore
	private List<CsRelSettoreCatsoc>  lstRelSett;
	
	//settore/tipo inter
	private boolean renderSettTipiinter;
	private List<SelectItem> lstSettori;
	private List<CsRelSettCsocTipoInter> lstRelSettTipointer;
	private List<CsRelSettCsocTipoInter> selectedRelSettTipointer;
	private Long newSettoreId;
	private List<SelectItem> lstTipiIntervento;
	private Long newTipoInterId;
	private List<SelectItem> lstOrganizzazioni;
	private Long newOrganizzazioneId;
	
	//settore/tipo diario
	private boolean renderSettTipidiario;
	private List<CsRelSettCatsocEsclusiva> lstRelSettTipodiario;
	private List<CsRelSettCatsocEsclusiva> selectedRelSettTipodiario;
	private List<SelectItem> lstTipiDiario;
	private Long newTipoDiarioId;
	
	@PostConstruct
	private void init() {
		
		caricaCatSociali();
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		lstTipiIntervento = new ArrayList<SelectItem>();
		List<KeyValueDTO> lstCsCTipiIntervento = confService.findAllTipiIntervento(dto);
		lstTipiIntervento = this.convertiLista(lstCsCTipiIntervento);
		
		CeTBaseObject cet = new CeTBaseObject();
		fillEnte(cet);
		lstOrganizzazioni = new ArrayList<SelectItem>();
		List<CsOOrganizzazione> lstCsOOrganizzazioni = confEnteService.getOrganizzazioni(cet);
		for(CsOOrganizzazione org: lstCsOOrganizzazioni) {
			if(org.getCodRouting()!=null)
				lstOrganizzazioni.add(new SelectItem(org.getId(), org.getNome()));
		}
		if(!lstCsOOrganizzazioni.isEmpty()) {
			newOrganizzazioneId = lstCsOOrganizzazioni.get(0).getId();
			aggiornaSettori();
		}
		
		lstTipiDiario = new ArrayList<SelectItem>();
		List<CsTbTipoDiario> lstCsTbTipiDiario = confService.getTipiDiario(cet);
		for(CsTbTipoDiario tipoDia: lstCsTbTipiDiario) {
			lstTipiDiario.add(new SelectItem(tipoDia.getId(), tipoDia.getDescrizione()));
		}
	}
	
	public void onCatSocialeSelect(SelectEvent event) {  
		renderSettTipiinter = false;
		renderSettTipidiario = false;
    }
	
	private void caricaCatSociali() {
		
		try {
			
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			lstCatSociali = catSocService.getCategorieSocialiAll(cet);
			
		} catch(Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	public void caricaSettTipiinter() {
		
		if(selectedCatSociali != null && !selectedCatSociali.isEmpty()) {
			
			if(selectedCatSociali.size() > 1) {
				addWarning("Attenzione", "Selezionare solo una voce");
				return;
			}
			
			renderSettTipiinter = true;
			renderSettTipidiario = false;
			selectedCatSociale = selectedCatSociali.get(0);
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(selectedCatSociale.getId());
			
			try {
				lstRelSettTipointer = catSocService.findRelSettCatsocTipoInterByCatSoc(dto);
			} catch(Exception e) {
				addErrorFromProperties("caricamento.error");
				logger.error(e.getMessage(),e);
			}
			
		} else addWarning("Seleziona una Categoria Sociale", "");
	}
	
	public void caricaSettori() {
		
		if(selectedCatSociali != null && !selectedCatSociali.isEmpty()) {
			
			if(selectedCatSociali.size() > 1) {
				addWarning("Attenzione", "Selezionare solo una voce");
				return;
			}
			
			renderSettTipiinter = false;
			renderSettTipidiario = false;
			selectedCatSociale = selectedCatSociali.get(0);
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(selectedCatSociale.getId());
			
			
			try {
				lstRelSett = catSocService.findRelSettoreCatsocByCatSoc(dto);
			} catch(Exception e) {
				addErrorFromProperties("caricamento.error");
				logger.error(e.getMessage(),e);
			}
			
		} else addWarning("Seleziona una Categoria Sociale", "");
	}
	
	public void caricaSettTipidiario() {
		
		if(selectedCatSociali != null && !selectedCatSociali.isEmpty()) {
			
			if(selectedCatSociali.size() > 1) {
				addWarning("Attenzione", "Selezionare solo una voce");
				return;
			}
			
			renderSettTipiinter = false;
			renderSettTipidiario = true;
			selectedCatSociale = selectedCatSociali.get(0);
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(selectedCatSociale.getId());
			
			try {
				lstRelSettTipodiario = catSocService.findRelSettCatsocEsclusivaByCatSoc(dto);
			} catch(Exception e) {
				addErrorFromProperties("caricamento.error");
				logger.error(e.getMessage(),e);
			}
			
		} else addWarning("Seleziona una Categoria Sociale", "");
	}
	
	public void attivaCatSociali() {
		
		if(selectedCatSociali != null) {
			try {
				
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				for(CsCCategoriaSociale catsoc: selectedCatSociali) {
					if(!catsoc.getAbilitato()) {
						catsoc.setAbilitato(true);
						dto.setObj(catsoc);
						catSocService.updateCategoriaSociale(dto);
					}
				}
				
				caricaCatSociali();
				refreshTabSettori();
				addInfoFromProperties("salva.ok");
				
			} catch(Exception e) {
				addErrorFromProperties("salva.error");
				logger.error(e.getMessage(),e);
			}
		} else addWarning("Seleziona almeno una Categoria Sociale", "");
		
	}
	
	public void disattivaCatSociali() {
		
		if(selectedCatSociali != null) {
			try {
				
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				for(CsCCategoriaSociale catsoc: selectedCatSociali) {
					if(catsoc.getAbilitato()) {
						catsoc.setAbilitato(false);
						dto.setObj(catsoc);
						catSocService.updateCategoriaSociale(dto);
					}
				}
				
				caricaCatSociali();
				refreshTabSettori();
				addInfoFromProperties("salva.ok");
				
			} catch(Exception e) {
				addErrorFromProperties("salva.error");
				logger.error(e.getMessage(),e);
			}
		} else addWarning("Seleziona almeno una Categoria Sociale", "");
		
	}
	
	public void eliminaCatSociali() {
		
		if(selectedCatSociali != null) {
			try {
				
				boolean ok = true;
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				for(CsCCategoriaSociale catsoc: selectedCatSociali) {
					dto.setObj(catsoc.getId());
					
					try {
						catSocService.eliminaCategoriaSociale(dto);
					} catch(Exception e) {
						ok = false;
					}
					
				}
				
				caricaCatSociali();
				refreshTabSettori();
				renderSettTipidiario = false;
				renderSettTipiinter = false;
				
				if(ok)
					addInfoFromProperties("salva.ok");
				else addWarning("Eliminazione non eseguita completamente", "Una o più Categorie Sociali selezionate hanno dati collegati");
				
			} catch(Exception e) {
				addErrorFromProperties("salva.error");
				logger.error(e.getMessage(),e);
			}
		} else addWarning("Seleziona almeno una Categoria Sociale", "");
		
	}
	
	public void aggiornaSettori() {
		if(newOrganizzazioneId != null) {
			OperatoreDTO dto = new OperatoreDTO();
			fillEnte(dto);
			dto.setIdOrganizzazione(newOrganizzazioneId);
			List<KeyValueDTO> lst = confEnteService.findSettoriByOrganizzazione(dto);
			lstSettori = convertiLista(lst);
		}
	}
	
	public void nuovaCatSociale() {
		
		if(!StringUtils.isBlank(newCatSocDescr)) {
			try {
				
				CsCCategoriaSociale catSoc = new CsCCategoriaSociale();
				catSoc.setAbilitato(true);
				catSoc.setDescrizione(newCatSocDescr);
				catSoc.setTooltip(newCatSocDescr);
				
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(catSoc);
				catSocService.salvaCategoriaSociale(dto);
				
				caricaCatSociali();
				refreshTabSettori();
				
				addInfoFromProperties("salva.ok");
				
			} catch(Exception e) {
				addErrorFromProperties("salva.error");
				logger.error(e.getMessage(),e);
			}
		} else addWarning("inserire una descrizione", "");
		
	}
	
	private void refreshTabSettori(){
		GestioneSettoriBean bean = (GestioneSettoriBean)getBeanReference("gestioneSettoriBean");
		bean.caricaCatSociali();
	}
	
	public void aggiungiSettoreTipoInter() {
		
		try {
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setSettoreId(newSettoreId);
			dto.setObj(selectedCatSociale.getId());
			dto.setObj2(newTipoInterId);
			
			catSocService.aggiungiSettoreTipoInter(dto);
					
			caricaSettTipiinter();
			addInfoFromProperties("salva.ok");
			
		} catch(Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	public void attivaSettoriTipiInter() {
		
		if(selectedRelSettTipointer != null) {
			try {
				
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				Map<Long, Long> settAttivati = new HashMap<Long, Long>();
				Map<Long, Long> tipiInterAttivati = new HashMap<Long, Long>();
				for(CsRelSettCsocTipoInter rel: selectedRelSettTipointer) {
					dto.setObj(rel.getId());
					dto.setObj2(true);
					catSocService.updateRelSettCsocTipoInter(dto);
					
					settAttivati.put(rel.getId().getScsSettoreId(), rel.getId().getScsSettoreId());
					tipiInterAttivati.put(rel.getId().getCstiTipoInterventoId(), rel.getId().getCstiTipoInterventoId());
				}
				
				caricaSettTipiinter();
				
				//attivo il valore anche su CS_REL_SETTORE_CATSOC e CS_REL_CATSOC_TIPOINTER
				for(Long settId: settAttivati.keySet()) {
					CsRelSettoreCatsocPK relSettorePK = new CsRelSettoreCatsocPK();
					relSettorePK.setCategoriaSocialeId(selectedCatSociale.getId());
					relSettorePK.setSettoreId(settId);
					dto.setObj(relSettorePK);
					dto.setObj2(true);
					catSocService.updateRelSettoreCatsoc(dto);
				}
				for(Long tipiInterId: tipiInterAttivati.keySet()) {
					CsRelCatsocTipoInterPK relTipoInterPK = new CsRelCatsocTipoInterPK();
					relTipoInterPK.setCategoriaSocialeId(selectedCatSociale.getId());
					relTipoInterPK.setTipoInterventoId(tipiInterId);
					dto.setObj(relTipoInterPK);
					dto.setObj2(true);
					catSocService.updateRelCatsocTipoInter(dto);
				}
				
				addInfoFromProperties("salva.ok");
				
			} catch(Exception e) {
				addErrorFromProperties("salva.error");
				logger.error(e.getMessage(),e);
			}
		} else addWarning("Seleziona almeno un Settore/Tipo intervento", "");
		
	}
	
	public void disattivaSettoriTipiInter() {
		
		if(selectedRelSettTipointer != null) {
			try {
				
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				Map<Long, Long> settDisattivati = new HashMap<Long, Long>();
				Map<Long, Long> tipiInterDisattivati = new HashMap<Long, Long>();
				for(CsRelSettCsocTipoInter rel: selectedRelSettTipointer) {
					dto.setObj(rel.getId());
					dto.setObj2(false);
					catSocService.updateRelSettCsocTipoInter(dto);
					
					settDisattivati.put(rel.getId().getScsSettoreId(), rel.getId().getScsSettoreId());
					tipiInterDisattivati.put(rel.getId().getCstiTipoInterventoId(), rel.getId().getCstiTipoInterventoId());
				}
				
				caricaSettTipiinter();
				
				//se tutti i valori per un certo settore sono disabilitati lo disabilito anche su CS_REL_SETTORE_CATSOC
				//stessa cosa per CS_REL_CATSOC_TIPOINTER
				for(CsRelSettCsocTipoInter rel: lstRelSettTipointer) {
					if(settDisattivati.get(rel.getId().getScsSettoreId()) != null && new BigDecimal(1).equals(rel.getAbilitato()))
						settDisattivati.remove(rel.getId().getScsSettoreId());
					if(tipiInterDisattivati.get(rel.getId().getCstiTipoInterventoId()) != null && new BigDecimal(1).equals(rel.getAbilitato()))
						tipiInterDisattivati.remove(rel.getId().getCstiTipoInterventoId());
				}
				
				for(Long settId: settDisattivati.keySet()) {
					CsRelSettoreCatsocPK relSettorePK = new CsRelSettoreCatsocPK();
					relSettorePK.setCategoriaSocialeId(selectedCatSociale.getId());
					relSettorePK.setSettoreId(settId);
					dto.setObj(relSettorePK);
					dto.setObj2(false);
					catSocService.updateRelSettoreCatsoc(dto);
				}
				for(Long tipiInterId: tipiInterDisattivati.keySet()) {
					CsRelCatsocTipoInterPK relTipoInterPK = new CsRelCatsocTipoInterPK();
					relTipoInterPK.setCategoriaSocialeId(selectedCatSociale.getId());
					relTipoInterPK.setTipoInterventoId(tipiInterId);
					dto.setObj(relTipoInterPK);
					dto.setObj2(false);
					catSocService.updateRelCatsocTipoInter(dto);
				}
				
				addInfoFromProperties("salva.ok");
				
			} catch(Exception e) {
				addErrorFromProperties("salva.error");
				logger.error(e.getMessage(),e);
			}
		} else addWarning("Seleziona almeno un Settore/Tipo intervento", "");
		
	}
	
	public void eliminaSettoriTipiInter() {
		
		if(selectedRelSettTipointer != null) {
			try {
				
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				Map<Long, Long> settEliminati = new HashMap<Long, Long>();
				Map<Long, Long> tipiInterEliminati = new HashMap<Long, Long>();
				for(CsRelSettCsocTipoInter rel: selectedRelSettTipointer) {
					dto.setObj(rel.getId().getScsCategoriaSocialeId());
					dto.setObj2(rel.getId().getScsSettoreId());
					dto.setObj3(rel.getId().getCstiTipoInterventoId());
					catSocService.eliminaRelSettCsocTipoInter(dto);
					
					settEliminati.put(rel.getId().getScsSettoreId(), rel.getId().getScsSettoreId());
					tipiInterEliminati.put(rel.getId().getCstiTipoInterventoId(), rel.getId().getCstiTipoInterventoId());
				}
				
				caricaSettTipiinter();
				
				//se non sono rimasti dati con il settore eliminato lo elimino anche su CS_REL_SETTORE_CATSOC
				//stessa cosa per CS_REL_CATSOC_TIPOINTER
				for(CsRelSettCsocTipoInter rel: lstRelSettTipointer) {
					if(settEliminati.get(rel.getId().getScsSettoreId()) != null)
						settEliminati.remove(rel.getId().getScsSettoreId());
					if(tipiInterEliminati.get(rel.getId().getCstiTipoInterventoId()) != null)
						tipiInterEliminati.remove(rel.getId().getCstiTipoInterventoId());
				}
				for(Long settId: settEliminati.keySet()) {
					dto.setObj(selectedCatSociale.getId());
					dto.setObj2(settId);
					catSocService.eliminaRelSettoreCatsoc(dto);
				}
				for(Long tipiInterId: tipiInterEliminati.keySet()) {
					dto.setObj(selectedCatSociale.getId());
					dto.setObj2(tipiInterId);
					catSocService.eliminaRelCatsocTipoInter(dto);
				}
				
				addInfoFromProperties("salva.ok");
				
			} catch(Exception e) {
				addErrorFromProperties("salva.error");
				logger.error(e.getMessage(),e);
			}
		} else addWarning("Seleziona almeno un Settore/Tipo intervento", "");
		
	}
	
	public void aggiungiSettoreTipoDiario() {
		try {
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			
			CsRelSettCatsocEsclusiva relSettCatsocEsc = new CsRelSettCatsocEsclusiva();
			CsRelSettCatsocEsclusivaPK relSettCatsocEscPK = new CsRelSettCatsocEsclusivaPK();
			relSettCatsocEsc.setAbilitato(true);
			relSettCatsocEsc.setDtIns(new Date());
			relSettCatsocEsc.setUserIns(dto.getUserId());
			relSettCatsocEscPK.setCategoriaSocialeId(selectedCatSociale.getId());
			relSettCatsocEscPK.setSettoreId(newSettoreId);
			relSettCatsocEscPK.setTipoDiarioId(newTipoDiarioId);
			relSettCatsocEsc.setId(relSettCatsocEscPK);
			dto.setObj(relSettCatsocEsc);
			catSocService.salvaRelSettCatsocEsclusiva(dto);
			
			caricaSettTipidiario();
			addInfoFromProperties("salva.ok");
			
		} catch(Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	public void attivaSettoriTipiDiario() {
		if(selectedRelSettTipodiario != null) {
			try {
				
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				for(CsRelSettCatsocEsclusiva rel: selectedRelSettTipodiario) {
					rel.setAbilitato(true);
					rel.setUsrMod(dto.getUserId());
					rel.setDtMod(new Date());
					dto.setObj(rel);
					catSocService.updateRelSettCatsocEsclusiva(dto);					
				}
				
				caricaSettTipidiario();
				
				addInfoFromProperties("salva.ok");
				
			} catch(Exception e) {
				addErrorFromProperties("salva.error");
				logger.error(e.getMessage(),e);
			}
		} else addWarning("Seleziona almeno un Settore/Tipo diario", "");
	}

	public void disattivaSettoriTipiDiario() {
		if(selectedRelSettTipodiario != null) {
			try {
				
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				for(CsRelSettCatsocEsclusiva rel: selectedRelSettTipodiario) {
					rel.setAbilitato(false);
					rel.setUsrMod(dto.getUserId());
					rel.setDtMod(new Date());
					dto.setObj(rel);
					catSocService.updateRelSettCatsocEsclusiva(dto);					
				}
				
				caricaSettTipidiario();
				
				addInfoFromProperties("salva.ok");
				
			} catch(Exception e) {
				addErrorFromProperties("salva.error");
				logger.error(e.getMessage(),e);
			}
		} else addWarning("Seleziona almeno un Settore/Tipo diario", "");
	}
	
	public void eliminaSettoriTipiDiario() {
		if(selectedRelSettTipodiario != null) {
			try {
				
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				for(CsRelSettCatsocEsclusiva rel: selectedRelSettTipodiario) {
					dto.setObj(rel.getId().getCategoriaSocialeId());
					dto.setObj2(rel.getId().getSettoreId());
					dto.setObj3(rel.getId().getTipoDiarioId());
					catSocService.eliminaRelSettCatsocEsclusiva(dto);					
				}
				
				caricaSettTipidiario();
				
				addInfoFromProperties("salva.ok");
				
			} catch(Exception e) {
				addErrorFromProperties("salva.error");
				logger.error(e.getMessage(),e);
			}
		} else addWarning("Seleziona almeno un Settore/Tipo diario", "");
	}
	
	public List<CsCCategoriaSociale> getLstCatSociali() {
		return lstCatSociali;
	}

	public void setLstCatSociali(List<CsCCategoriaSociale> lstCatSociali) {
		this.lstCatSociali = lstCatSociali;
	}

	public List<CsCCategoriaSociale> getSelectedCatSociali() {
		return selectedCatSociali;
	}

	public void setSelectedCatSociali(List<CsCCategoriaSociale> selectedCatSociali) {
		this.selectedCatSociali = selectedCatSociali;
	}

	public String getNewCatSocDescr() {
		return newCatSocDescr;
	}

	public void setNewCatSocDescr(String newCatSocDescr) {
		this.newCatSocDescr = newCatSocDescr;
	}

	public CsCCategoriaSociale getSelectedCatSociale() {
		return selectedCatSociale;
	}

	public void setSelectedCatSociale(CsCCategoriaSociale selectedCatSociale) {
		this.selectedCatSociale = selectedCatSociale;
	}

	public List<SelectItem> getLstTipiIntervento() {
		return lstTipiIntervento;
	}

	public void setLstTipiIntervento(List<SelectItem> lstTipiIntervento) {
		this.lstTipiIntervento = lstTipiIntervento;
	}

	public Long getNewTipoInterId() {
		return newTipoInterId;
	}

	public void setNewTipoInterId(Long newTipoInterId) {
		this.newTipoInterId = newTipoInterId;
	}

	public List<SelectItem> getLstSettori() {
		return lstSettori;
	}

	public void setLstSettori(List<SelectItem> lstSettori) {
		this.lstSettori = lstSettori;
	}

	public Long getNewSettoreId() {
		return newSettoreId;
	}

	public void setNewSettoreId(Long newSettoreId) {
		this.newSettoreId = newSettoreId;
	}

	public List<CsRelSettCsocTipoInter> getLstRelSettTipointer() {
		return lstRelSettTipointer;
	}

	public void setLstRelSettTipointer(
			List<CsRelSettCsocTipoInter> lstRelSettTipointer) {
		this.lstRelSettTipointer = lstRelSettTipointer;
	}

	public List<CsRelSettCsocTipoInter> getSelectedRelSettTipointer() {
		return selectedRelSettTipointer;
	}

	public void setSelectedRelSettTipointer(
			List<CsRelSettCsocTipoInter> selectedRelSettTipointer) {
		this.selectedRelSettTipointer = selectedRelSettTipointer;
	}

	public boolean isRenderSettTipiinter() {
		return renderSettTipiinter;
	}

	public void setRenderSettTipiinter(boolean renderSettTipiinter) {
		this.renderSettTipiinter = renderSettTipiinter;
	}

	public boolean isRenderSettTipidiario() {
		return renderSettTipidiario;
	}

	public void setRenderSettTipidiario(boolean renderSettTipidiario) {
		this.renderSettTipidiario = renderSettTipidiario;
	}

	public List<CsRelSettCatsocEsclusiva> getLstRelSettTipodiario() {
		return lstRelSettTipodiario;
	}

	public void setLstRelSettTipodiario(
			List<CsRelSettCatsocEsclusiva> lstRelSettTipodiario) {
		this.lstRelSettTipodiario = lstRelSettTipodiario;
	}

	public List<CsRelSettCatsocEsclusiva> getSelectedRelSettTipodiario() {
		return selectedRelSettTipodiario;
	}

	public void setSelectedRelSettTipodiario(
			List<CsRelSettCatsocEsclusiva> selectedRelSettTipodiario) {
		this.selectedRelSettTipodiario = selectedRelSettTipodiario;
	}

	public List<SelectItem> getLstTipiDiario() {
		return lstTipiDiario;
	}

	public void setLstTipiDiario(List<SelectItem> lstTipiDiario) {
		this.lstTipiDiario = lstTipiDiario;
	}

	public Long getNewTipoDiarioId() {
		return newTipoDiarioId;
	}

	public void setNewTipoDiarioId(Long newTipoDiarioId) {
		this.newTipoDiarioId = newTipoDiarioId;
	}

	public List<SelectItem> getLstOrganizzazioni() {
		return lstOrganizzazioni;
	}

	public void setLstOrganizzazioni(List<SelectItem> lstOrganizzazioni) {
		this.lstOrganizzazioni = lstOrganizzazioni;
	}

	public Long getNewOrganizzazioneId() {
		return newOrganizzazioneId;
	}

	public void setNewOrganizzazioneId(Long newOrganizzazioneId) {
		this.newOrganizzazioneId = newOrganizzazioneId;
	}

	public List<CsRelSettoreCatsoc> getLstRelSett() {
		return lstRelSett;
	}

	public void setLstRelSett(List<CsRelSettoreCatsoc> lstRelSett) {
		this.lstRelSett = lstRelSett;
	}
	
}
