package it.webred.cs.csa.web.manbean.scheda.anagrafica;

import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableCatSocialeSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.web.manbean.scheda.SchedaBean;
import it.webred.cs.csa.web.manbean.scheda.invalidita.DatiInvaliditaComp;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsASoggettoCategoriaSoc;
import it.webred.cs.data.model.CsASoggettoCategoriaSocPK;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.jsf.bean.ValiditaCompBaseBean;
import it.webred.cs.jsf.interfaces.ISoggCatSociale;
import it.webred.cs.jsf.manbean.DatiValGestioneMan;
import it.webred.dto.utility.KeyValuePairBean;
import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.data.model.SsCCategoriaSociale;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.ejb.dto.SchedaUdcBaseDTO;
import it.webred.ss.ejb.dto.SchedaUdcDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class SoggCatSocialeBean extends DatiValGestioneMan implements ISoggCatSociale {
	
	private CsASoggettoLAZY soggetto;
	private SchedaUdcBaseDTO schedaUdc;
	private String ufficioSchedaSegr;
	private String catSocSegr;
	private String modalWidgetVar = "wdgCasoCatSocModal";
	private boolean disableEsci;
	private boolean readOnly;
	
	private String settTo;
	private Long settIdTo;
	private Long orgIdTo;
	
	private boolean richiediCategoriaSociale;
	
	@Override
	public void carica(Long anagraficaId) {
		logger.debug("START SoggCatSocialeBean: carica");
		CsASoggettoLAZY s = this.getSoggettoById(anagraficaId);
		this.carica(s);
		logger.debug("END SoggCatSocialeBean: carica");
	}
	
	@Override
	public void carica(CsASoggettoLAZY soggetto) {
		
		try {
		if(soggetto != null) {
				this.soggetto = soggetto;
				itemSelezionato = null;
				catSocSegr = null;
				maxActiveComponents = 10;
				
				
				List<ValiditaCompBaseBean> lstCatSociali = new ArrayList<ValiditaCompBaseBean>();
				
				AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(soggetto.getAnagraficaId());
				List<CsASoggettoCategoriaSoc> lista = soggettoService.getSoggettoCategorieBySoggetto(dto);
				
				for(CsASoggettoCategoriaSoc csSoggCatSoc: lista) {
					ValiditaCompBaseBean comp = new ValiditaCompBaseBean();
					comp.setDataFine(csSoggCatSoc.getId().getDataFineApp());
					comp.setDataInizio(csSoggCatSoc.getDataInizioApp());
					comp.setDescrizione(csSoggCatSoc.getCsCCategoriaSociale().getTooltip());
					comp.setId(new Long(csSoggCatSoc.getId().getCategoriaSocialeId()));
					comp.setPrevalente((csSoggCatSoc.getPrevalente()!=null && csSoggCatSoc.getPrevalente().intValue()==1) ? true : false);
					//SISO-1060
					comp.setDataInserimento(csSoggCatSoc.getDtIns());
					comp.setDataModifica(csSoggCatSoc.getDtMod());
					comp.setUsrInserimento(csSoggCatSoc.getUserIns());
					comp.setUsrModifica(csSoggCatSoc.getUsrMod());
					
					lstCatSociali.add(comp);
				}
				setLstComponents(lstCatSociali);
				setLstComponentsActive(getActiveList());
			
				gestisci();
				
				/* Gestione Read Only
				*   il caso è in modifica se:
				*	ho il permesso modifica tutti i casi settore
				*	se il caso non ha responsabile e l'operatore è il creatore del caso
				*	se sono presente nella lista degli operatori per quel caso
				*/
				readOnly = true;
				CsOOperatoreSettore opSettore = getCurrentOpSettore();
				AccessTableCasoSessionBeanRemote casoService = (AccessTableCasoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableCasoSessionBean");
				dto.setObj(soggetto.getCsACaso().getId());
				dto.setObj2(opSettore.getId());
				boolean isOperatoreAbilitato = casoService.isOperatoreCaso(dto);
				
				if (isModificaCasiSettore() || isOperatoreAbilitato)
					readOnly = false;
				
				//carico ufficio scheda segr
				ufficioSchedaSegr = null;
				if(schedaUdc != null){
					ufficioSchedaSegr = schedaUdc.getUfficioPuntoContatto();
					List<SsCCategoriaSociale> lst = schedaUdc.getScheda().getLstCategorieSociali();
					if(lst!=null && !lst.isEmpty()){
						catSocSegr = lst.get(0).getDescrizione();
						itemSelezionato = lst.get(0).getId()+"|"+lst.get(0).getDescrizione();
					}
				}
			
				RequestContext.getCurrentInstance().execute(modalWidgetVar + ".show()");
			
			} else addWarningFromProperties("seleziona.warning");
		
		} catch (NamingException e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}	
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List<KeyValueDTO> loadListItems() {
		try {
			CsOOperatoreSettore opSettore = getCurrentOpSettore();
			AccessTableCatSocialeSessionBeanRemote bean = 
					(AccessTableCatSocialeSessionBeanRemote) getCarSocialeEjb("AccessTableCatSocialeSessionBean");
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(opSettore.getCsOSettore().getId());
			return bean.getCategorieSocialiBySettore(dto);
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
			return null;
		}
	}
	
	
	@Override
	public boolean salva() {
		boolean ok = true;
		logger.debug("START SoggCatSocialeBean: salva");
		try {
			itemSelezionato = null;
			List<ValiditaCompBaseBean> lstTempActive = getActiveList();
			
			if(lstTempActive.size() > maxActiveComponents) {
				addError("Superato il numero di elementi attivi, massimo: " + maxActiveComponents, null);
				ok = false;
			}
			
			if(settTo == null || "".equals(settTo)) {
				addWarning("E' necessaria almeno una categoria attiva", "");
				ok = false;
			}
			
			int counterPrevalente = 0;
			for(ValiditaCompBaseBean comp: getLstComponents()) {
				if(comp.isPrevalente()){
					counterPrevalente++;
					if(!comp.isAttivo()){
					   addWarning("La categoria prevalente deve essere attiva","");
					   ok=false;
					}
				}
			}
			if(counterPrevalente>1){
				ok=false;
				addWarning("Può esistere una sola categoria prevalente e deve essere attiva.","");
			}
			
			if(counterPrevalente==0){
				if(getLstComponents().size()==1)
					getLstComponents().get(0).setPrevalente(true);
				else{
					ok=false;
					addWarning("Impostare almeno una categoria prevalente.","");
				}
			}
			
			if(ok) {
				lstComponentsActive = lstTempActive;
				SchedaBean schedaBean = (SchedaBean) getSession().getAttribute("schedaBean");
				AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
				AccessTableIterStepSessionBeanRemote iterService = (AccessTableIterStepSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableIterStepSessionBean");

				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				boolean aggiornamento = false;
				if(soggetto != null) {
					dto.setObj(soggetto.getAnagraficaId());
					aggiornamento = soggettoService.eliminaSoggettoCategorieBySoggetto(dto);
				}
				for(ValiditaCompBaseBean comp: getLstComponents()) {
					CsASoggettoCategoriaSoc cs = new CsASoggettoCategoriaSoc();
					CsASoggettoCategoriaSocPK csPK = new CsASoggettoCategoriaSocPK();
					//cs.setCsASoggetto(soggetto);
					cs.setDataInizioApp(comp.getDataInizio());
					cs.setDataInizioSys(new Date());
					cs.setDtIns(new Date());
					cs.setUserIns(dto.getUserId());
					cs.setPrevalente(comp.isPrevalente() ? new Integer(1) : new Integer(0));
					csPK.setCategoriaSocialeId(comp.getId());
					csPK.setSoggettoAnagraficaId(soggetto.getAnagraficaId());
					csPK.setDataFineApp(comp.getDataFine());
					cs.setId(csPK);
					
					dto.setObj(cs);
					soggettoService.saveSoggettoCategoria(dto);					
				}
				
				if(!aggiornamento){
					//segnalo al settore
					CsOOperatoreSettore opSettore = getCurrentOpSettore();
					IterDTO itDto = new IterDTO();
					fillEnte(itDto);
					itDto.setCsACaso(soggetto.getCsACaso());
					itDto.setIdStato(DataModelCostanti.IterStatoInfo.SEGNALATO);
					itDto.setNomeOperatore(itDto.getUserId());
					itDto.setIdSettore(opSettore.getCsOSettore().getId());
					itDto.setIdOpSettoreTo(null);
					itDto.setIdSettTo(settIdTo);
					itDto.setIdOrgTo(orgIdTo);
					itDto.setNota(null);
					itDto.setAttrNewStato(null);
					itDto.setAlertUrl(null);
					itDto.setNotificaSettoreSegnalante(true);
					boolean bSaved = iterService.addIterStep(itDto);
					schedaBean.initializePresaInCarico(soggetto);
				}
				
				addInfoFromProperties("salva.ok");
				
				//carico gli altri dati provenienenti da scheda segretariato
				if(soggetto != null){ 
					  if(schedaUdc != null && schedaUdc.getScheda()!=null) {	
			    		SsSchedaSegnalato segnalato = schedaUdc.getSegnalato();
						//invalidita
						if(segnalato.getInvalidita() != null && segnalato.getInvalidita()>0) {
							schedaBean.getDatiInvaliditaBean().setListaComponenti(new ArrayList());
							schedaBean.getDatiInvaliditaBean().setSoggettoId(soggetto.getAnagraficaId());
							schedaBean.getDatiInvaliditaBean().nuovo();
							DatiInvaliditaComp comp = (DatiInvaliditaComp) schedaBean.getDatiInvaliditaBean().getListaComponenti().get(0);
							comp.setInvaliditaInCorso(true);
							comp.setInvaliditaPerc(new BigDecimal(segnalato.getInvalidita()));
							addWarning("Dati aggiuntivi da Scheda Segretariato in Invalidità", "Salvare per confermare");
						}
						
					  }
					  
					  
			/*			//refresh dei tab per renderizzare i dati caricati  --> Da JSF componente
						RequestContext.getCurrentInstance().update(schedaBean.getIdTabScheda());
						
					}else
						RequestContext.getCurrentInstance().update("frmListaCasis");*/
					  
				}
					
			}
			
			richiediCategoriaSociale=false;
			RequestContext.getCurrentInstance().addCallbackParam("saved", ok);
			
			
		} catch (Exception e) {
			ok=false;
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
		
		logger.debug("END SoggCatSocialeBean: salva");
		
		return ok;
	}

	@Override
	public String getModalWidgetVar() {
		return modalWidgetVar;
	}

	@Override
	public boolean isDisableEsci() {
		return disableEsci;
	}

	public void setDisableEsci(boolean disableEsci) {
		this.disableEsci = disableEsci;
	}

	public String getSettTo() {
		settTo = null;
		settIdTo = null;
		orgIdTo = null;
		
		if (!getActiveList().isEmpty()) {
			CsOOperatoreSettore currOpSett = getCurrentOpSettore();
			settTo = currOpSett.getCsOSettore().getNome();
			settIdTo = currOpSett.getCsOSettore().getId();
			orgIdTo = currOpSett.getCsOSettore().getCsOOrganizzazione().getId();
		}
		
/*		try {
			AccessTableCatSocialeSessionBeanRemote catSocService = (AccessTableCatSocialeSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableCatSocialeSessionBean");
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			for (ValiditaCompBaseBean comp: getActiveList()) {
				dto.setObj(comp.getId());
				CsCCategoriaSociale catSociale = catSocService.getCategoriaSocialeById(dto);
				if(catSociale.getCsRelSettoreCatsocs() != null && !catSociale.getCsRelSettoreCatsocs().isEmpty()) {
					List<CsRelSettoreCatsoc> list = new ArrayList<CsRelSettoreCatsoc>(catSociale.getCsRelSettoreCatsocs());
					CsOSettore settore = list.get(0).getCsOSettore();
					settTo = settore.getNome();
					settIdTo = settore.getId();
					orgIdTo = settore.getCsOOrganizzazione().getId();
					return settTo;
				}
			}
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
		*/
		return settTo;
	}

	public boolean isReadOnly() {		
		return readOnly;
	}

	public SchedaUdcBaseDTO getSchedaUdc() {
		return schedaUdc;
	}

	public void setSchedaUdc(SchedaUdcBaseDTO schedaUdc) {
		this.schedaUdc = schedaUdc;
	}

	public String getUfficioSchedaSegr() {
		return ufficioSchedaSegr;
	}

	public void setUfficioSchedaSegr(String ufficioSchedaSegr) {
		this.ufficioSchedaSegr = ufficioSchedaSegr;
	}

	public String getCatSocSegr() {
		return catSocSegr;
	}

	public void setCatSocSegr(String catSocSegr) {
		this.catSocSegr = catSocSegr;
	}

	@Override
	public List<KeyValuePairBean> getDettaglioSelezione(Long id) {
		return null;
	}

	public boolean isRichiediCategoriaSociale() {
		return richiediCategoriaSociale;
	}

	public void setRichiediCategoriaSociale(boolean richiediCategoriaSociale) {
		this.richiediCategoriaSociale = richiediCategoriaSociale;
	}
	
}
