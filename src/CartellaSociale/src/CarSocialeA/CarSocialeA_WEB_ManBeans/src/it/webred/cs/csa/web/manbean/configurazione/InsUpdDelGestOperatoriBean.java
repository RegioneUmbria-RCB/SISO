package it.webred.cs.csa.web.manbean.configurazione;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import it.webred.amprofiler.ejb.user.UserService;
import it.webred.amprofiler.model.AmGroup;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOperatoreTipoOperatore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.data.model.CsTbTipoOperatore;
import it.webred.cs.jsf.bean.DataTableCsOOperatoreSettore;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmComune;
import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ct.support.datarouter.CeTBaseObject;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

@ManagedBean
@ViewScoped
public class InsUpdDelGestOperatoriBean extends CsUiCompBaseBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{ricercaGestOperatoriBean}")
	private RicercaGestOperatoriBean ricercaGestOperatoriBean;
	
	private List<CsOOrganizzazione> organizzazioniBean;
	private List<SelectItem> organizzazioni;
	private Long selOrganizzazione;
	private List<SelectItem> settori;
	private Long selSettore;
	private List<SelectItem> tipiOperatore;
	private Long selTipoOperatore;
	private boolean appartieneSettore;
	private boolean firmaSettore;
	private boolean viewRuoli;
	private List<SelectItem> ruoli;
	private List<String> selRuoli;
	private List<DataTableCsOOperatoreSettore> dataTableDati;
	
	private boolean disableFirma=true;
	
	protected ComuneService comuneService = (ComuneService) getEjb("CT_Service", "CT_Config_Manager", "ComuneServiceBean");
	protected AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
	protected AccessTableOperatoreSessionBeanRemote operatoreService = (AccessTableOperatoreSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableOperatoreSessionBean");
	protected UserService userService = (UserService) getEjb("AmProfiler", "AmProfilerEjb", "UserServiceBean");
	
	public RicercaGestOperatoriBean getRicercaGestOperatoriBean() {
		return ricercaGestOperatoriBean;
	}

	public void setRicercaGestOperatoriBean(RicercaGestOperatoriBean ricercaGestOperatoriBean) {
		this.ricercaGestOperatoriBean = ricercaGestOperatoriBean;
	}
	
	public List<SelectItem> getOrganizzazioni() {
		return organizzazioni;
	}

	public void setOrganizzazioni(List<SelectItem> organizzazioni) {
		this.organizzazioni = organizzazioni;
	}

	public Long getSelOrganizzazione() {
		return selOrganizzazione;
	}

	public void setSelOrganizzazione(Long selOrganizzazione) {
		this.selOrganizzazione = selOrganizzazione;
	}

	public List<SelectItem> getSettori() {
		return settori;
	}

	public void setSettori(List<SelectItem> settori) {
		this.settori = settori;
	}

	public Long getSelSettore() {
		return selSettore;
	}

	public void setSelSettore(Long selSettore) {
		this.selSettore = selSettore;
	}

	public List<SelectItem> getTipiOperatore() {
		return tipiOperatore;
	}

	public void setTipiOperatore(List<SelectItem> tipiOperatore) {
		this.tipiOperatore = tipiOperatore;
	}

	public Long getSelTipoOperatore() {
		return selTipoOperatore;
	}

	public void setSelTipoOperatore(Long selTipoOperatore) {
		this.selTipoOperatore = selTipoOperatore;
	}

	public boolean isAppartieneSettore() {
		return appartieneSettore;
	}

	public void setAppartieneSettore(boolean appartieneSettore) {
		this.appartieneSettore = appartieneSettore;
	}

	public boolean isViewRuoli() {
		return viewRuoli;
	}

	public void setViewRuoli(boolean viewRuoli) {
		this.viewRuoli = viewRuoli;
	}

	public List<SelectItem> getRuoli() {
		return ruoli;
	}

	public void setRuoli(List<SelectItem> ruoli) {
		this.ruoli = ruoli;
	}

	public List<String> getSelRuoli() {
		return selRuoli;
	}

	public void setSelRuoli(List<String> selRuoli) {
		this.selRuoli = selRuoli;
	}

	public List<DataTableCsOOperatoreSettore> getDataTableDati() {
		return dataTableDati;
	}

	public void setDataTableDati(List<DataTableCsOOperatoreSettore> dataTableDati) {
		this.dataTableDati = dataTableDati;
	}

	protected void loadOrganizzazioni() {
		selOrganizzazione = null;
		organizzazioni = new ArrayList<SelectItem>();
		try {
			organizzazioni.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			organizzazioniBean = confService.getOrganizzazioni(cet);
			if (organizzazioniBean != null && organizzazioniBean.size() > 0) {
				for (CsOOrganizzazione org : organizzazioniBean) {
					organizzazioni.add(new SelectItem(org.getId(), org.getNome()));
				}
			}
			resetSettori();
			viewRuoli = false;
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	public void onSelOrganizzazione() {
		resetSettori();
		viewRuoli = false;
		if (selOrganizzazione == null) {
			return;
		}
		try {
			List<CsOSettoreBASIC> beanLst = new ArrayList<CsOSettoreBASIC>();
			OperatoreDTO opDto = new OperatoreDTO();
			opDto.setIdOrganizzazione(selOrganizzazione);
			fillEnte(opDto);
			beanLst = operatoreService.findSettoreBASICByOrganizzazione(opDto);
			if (beanLst != null && beanLst.size() > 0) {
				for (CsOSettoreBASIC settore : beanLst) {
					settori.add(new SelectItem(settore.getId(), settore.getNome()));
				}
			}
			
			String belfiore = null;
			for (CsOOrganizzazione org : organizzazioniBean) {
				if (org.getId().longValue() == selOrganizzazione.longValue() && org.getBelfiore() != null) {
					belfiore = org.getBelfiore();
					break;
				}
			}
			if (belfiore != null) {
				List<AmComune> comuni = comuneService.getListaComune();
				if (comuni != null) {
					for (AmComune comune : comuni) {
						if (belfiore.equals(comune.getBelfiore())) {
							viewRuoli = true;
							break;
						}
					}
				}
			}
			if (viewRuoli) {
				loadRuoli(belfiore);
			}
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	public void onSelSettore() {
		resetTipiOperatore();
		try {
			List<CsTbTipoOperatore> beanLst = new ArrayList<CsTbTipoOperatore>();
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			beanLst = confService.getTipiOperatore(cet);
			if (beanLst != null && beanLst.size() > 0) {
				for (CsTbTipoOperatore tipo : beanLst) {
					tipiOperatore.add(new SelectItem(tipo.getId(), tipo.getDescrizione()));
				}
			}
			OperatoreDTO opDto = new OperatoreDTO();
			opDto.setIdOperatore(ricercaGestOperatoriBean.getOperatore().getIdOperatore());
			opDto.setIdSettore(selSettore);			
			opDto.setDate(new Date());
			fillEnte(opDto);		
			CsOOperatoreSettore opSet = operatoreService.findOperatoreSettoreById(opDto);			
			if (opSet != null) {
				opDto.setIdOperatoreSettore(opSet.getId());
				CsOOperatoreTipoOperatore opTipo = operatoreService.getTipoByOperatoreSettore(opDto);
				selTipoOperatore = opTipo == null ? null : opTipo.getCsTbTipoOperatore().getId();
			}			
			appartieneSettore = opSet != null && opSet.getAppartiene() == 1;
			firmaSettore = opSet!=null && opSet.getFirma();
			String amGroup = opSet == null ? null : opSet.getAmGroup();
			selRuoli = null;
			if (amGroup != null && !amGroup.equals("")) {
				String[] arr = amGroup.split(",");
				if (arr.length > 0) {
					selRuoli = new ArrayList<String>();
					for (String gr : arr) {
						selRuoli.add(gr);
					}
				}
			}
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
		refreshFirma();
	}
	
	private void resetSettori() {
		selSettore = null;
		settori = new ArrayList<SelectItem>();
		settori.add(new SelectItem(null, "- seleziona -"));
		resetTipiOperatore();
		resetAppartiene();
		resetRuoli();
	}
	
	private void resetTipiOperatore() {
		selTipoOperatore = null;
		tipiOperatore = new ArrayList<SelectItem>();
		tipiOperatore.add(new SelectItem(null, "- seleziona -"));
	}
	
	private void resetAppartiene() {
		appartieneSettore = false;
	}
	
	private void resetRuoli() {
		selRuoli = null;
		ruoli = null;
	}
	
	private void loadRuoli(String belfiore) throws Exception {
		LinkedHashMap<String, String> hmRuoli = DataTableCsOOperatoreSettore.getCodificaRuoli(belfiore);
		Iterator<String> it = hmRuoli.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			AmGroup group = userService.getGroupByName(key);
			if (group != null) {
				if (ruoli == null) {
					ruoli = new ArrayList<SelectItem>();
				}
				ruoli.add(new SelectItem(key, hmRuoli.get(key)));
			}
		}
		viewRuoli = ruoli != null && ruoli.size() > 0;
	}
	
	protected void loadDataTable(Long idOp) {
		dataTableDati = null;
		if (idOp == null) {
			return;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			OperatoreDTO opDto = new OperatoreDTO();
			opDto.setIdOperatore(idOp);
			opDto.setDate(sdf.parse("01/01/0001")); // In configurazione bisognerebbe vedere tutti i settori abilitati o meno
			fillEnte(opDto);
			List<CsOOperatoreSettore> beanLst = operatoreService.findOperatoreSettoreByOperatore(opDto);
			if (beanLst == null || beanLst.size() == 0) {
				return;
			}
			dataTableDati = new ArrayList<DataTableCsOOperatoreSettore>();
			for (CsOOperatoreSettore opSet : beanLst) {
				opDto.setIdOperatoreSettore(opSet.getId());
				CsOOperatoreTipoOperatore opTipoOp = operatoreService.getTipoByOperatoreSettore(opDto);
				dataTableDati.add(DataTableCsOOperatoreSettore.copyFromCsOOperatoreSettore(opSet, opTipoOp));			
			}
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}		
		
	}

	public boolean isFirmaSettore() {
		return firmaSettore;
	}

	public void setFirmaSettore(boolean firmaSettore) {
		this.firmaSettore = firmaSettore;
	}

	public boolean isDisableFirma() {
		return disableFirma;
	}

	public void setDisableFirma(boolean disableFirma) {
		this.disableFirma = disableFirma;
	}

	public void refreshFirma(){
		List<String> lstRuoli = this.getSelRuoli();
		boolean respsett = false;
		int i=0;
		if(lstRuoli!=null){
			while(i<lstRuoli.size() && !respsett){
				String ruolo = lstRuoli.get(i);
				if(ruolo.contains("RESPO_SETTORE"))
					respsett = true;
				i++;
			}
		}
		if(respsett)
			this.disableFirma=false;
		else{
			this.disableFirma=true;
			this.setFirmaSettore(false);
			}
	}
	
}
