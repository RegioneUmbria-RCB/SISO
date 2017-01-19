package it.webred.cs.csa.web.manbean.configurazione;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import it.webred.amprofiler.ejb.anagrafica.AnagraficaService;
import it.webred.amprofiler.ejb.user.UserService;
import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.amprofiler.model.AmUserUfficio;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOperatoreTipoOperatore;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsTbTipoOperatore;
import it.webred.cs.jsf.bean.DataTableCsOOperatoreSettore;
import it.webred.cs.jsf.bean.DatiOperatore;
import it.webred.cs.jsf.interfaces.IGestioneOperatori;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ss.ejb.dto.BaseDTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class GestioneOperatoriBean extends CsUiCompBaseBean implements IGestioneOperatori, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final DateFormat DF = new SimpleDateFormat("dd/MM/yyyy");
	
	private String widgetVar = "GestioneOperatoriVar";
	
	@ManagedProperty(value="#{ricercaGestOperatoriBean}")
	private RicercaGestOperatoriBean ricercaGestOperatoriBean;
	
	@ManagedProperty(value="#{insUpdDelGestOperatoriBean}")
	private InsUpdDelGestOperatoriBean insUpdDelGestOperatoriBean;

	private List<DatiOperatore> lstOperatori;
	private List<DatiOperatore> lstOperatoriFiltrati;
	private int idxSelected;
	
	protected AnagraficaService anagraficaService = (AnagraficaService) getEjb("AmProfiler", "AmProfilerEjb", "AnagraficaServiceBean");
	protected UserService userService = (UserService) getEjb("AmProfiler", "AmProfilerEjb", "UserServiceBean");
	protected AccessTableOperatoreSessionBeanRemote operatoreService = (AccessTableOperatoreSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableOperatoreSessionBean");

	public String getWidgetVar() {
		return widgetVar;
	}

	public void setWidgetVar(String widgetVar) {
		this.widgetVar = widgetVar;
	}
	
	public RicercaGestOperatoriBean getRicercaGestOperatoriBean() {
		return ricercaGestOperatoriBean;
	}

	public void setRicercaGestOperatoriBean(RicercaGestOperatoriBean ricercaGestOperatoriBean) {
		this.ricercaGestOperatoriBean = ricercaGestOperatoriBean;
	}

	public InsUpdDelGestOperatoriBean getInsUpdDelGestOperatoriBean() {
		return insUpdDelGestOperatoriBean;
	}

	public void setInsUpdDelGestOperatoriBean(InsUpdDelGestOperatoriBean insUpdDelGestOperatoriBean) {
		this.insUpdDelGestOperatoriBean = insUpdDelGestOperatoriBean;
	}
	
	public boolean isPnlGestioneRendered() {
		return getRicercaGestOperatoriBean().getOperatore() != null && getRicercaGestOperatoriBean().getOperatore().getIdOperatore() != null;
	}
	
	private CsOOperatore getCsOOperatore() throws Exception {
		OperatoreDTO opDto = new OperatoreDTO();
		fillEnte(opDto);
		opDto.setUsername(getRicercaGestOperatoriBean().getOperatore().getAmUser().getName());
		return operatoreService.findOperatoreByUsername(opDto);
	}
	
	private CsOOperatoreSettore getCsOOperatoreSettore(Long idSettore) throws Exception {
		OperatoreDTO opDto = new OperatoreDTO();
		opDto.setIdOperatore(getRicercaGestOperatoriBean().getOperatore().getIdOperatore());
		opDto.setIdSettore(idSettore);
		opDto.setDate(new Date());
		fillEnte(opDto);
		return operatoreService.findOperatoreSettoreById(opDto);
	}

	private void caricaLstoperatori() {
		
		try {
			
			lstOperatori = new ArrayList<DatiOperatore>();
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			List<CsOOperatore> listaOp = operatoreService.getOperatoriAll(cet);
			for(CsOOperatore op: listaOp) {
				AmAnagrafica amAna = getAnagraficaByUsername(op.getUsername());
				if(amAna != null) {
					DatiOperatore datiOp = DatiOperatore.copyFromAmAnagrafica(amAna);
					datiOp.setAbilitato("1".equals(op.getAbilitato()));
					
					lstOperatori.add(datiOp);
				}
			}
			lstOperatoriFiltrati = lstOperatori;
			
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	public void dettagliOperatore() {
		
		try {
			DatiOperatore datiOp = lstOperatoriFiltrati.get(idxSelected);
			getRicercaGestOperatoriBean().setOperatore(datiOp);
			//dati ufficio
			AmUserUfficio uff = userService.getDatiUfficio(getRicercaGestOperatoriBean().getOperatore().getAmUser().getName());
			getRicercaGestOperatoriBean().getOperatore().setUfficio(uff);
			//id e abilitazione operatore			
			CsOOperatore op = getCsOOperatore();
			if (op != null) {
				getRicercaGestOperatoriBean().getOperatore().setIdOperatore(op.getId());
				getRicercaGestOperatoriBean().getOperatore().setAbilitato(op.getAbilitato() != null && op.getAbilitato().equals("1"));
			}
			getInsUpdDelGestOperatoriBean().loadOrganizzazioni();
			getInsUpdDelGestOperatoriBean().loadDataTable(getRicercaGestOperatoriBean().getOperatore() == null ? null : getRicercaGestOperatoriBean().getOperatore().getIdOperatore());
			getInsUpdDelGestOperatoriBean().refreshFirma();
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void ricercaOperatore() {
		try {
			String codiceFiscaleRic = getRicercaGestOperatoriBean().getCodiceFiscaleRic();
			String cognomeRic = getRicercaGestOperatoriBean().getCognomeRic();
			String nomeRic = getRicercaGestOperatoriBean().getNomeRic();
			
			if((codiceFiscaleRic == null || "".equals(codiceFiscaleRic)) &&
			(cognomeRic == null || "".equals(cognomeRic)) &&
			(nomeRic == null || "".equals(nomeRic))) {
				getRicercaGestOperatoriBean().setOperatore(null);
				addWarning("Inserire almeno un parametro di ricerca", "");
				return;
			}
			List<AmAnagrafica> lst = anagraficaService.findAnagraficaByCodiceFiscaleCognomeNome(codiceFiscaleRic == null ? codiceFiscaleRic : codiceFiscaleRic.trim().toUpperCase(), 
																								cognomeRic == null ? cognomeRic : cognomeRic.trim().toUpperCase(), 
																								nomeRic == null ? nomeRic : nomeRic.trim().toUpperCase());
			if (lst == null || lst.size() == 0) {
				getRicercaGestOperatoriBean().setOperatore(null);
				addWarning("Nessun operatore trovato", "");
				return;
			}
			if (lst != null && lst.size() > 1) {
				getRicercaGestOperatoriBean().setOperatore(null);
				addWarning("La ricerca ha prodotto più di un risultato; si consiglia di riprovare con parametri differenti", "");
				return;
			}
			getRicercaGestOperatoriBean().setOperatore(DatiOperatore.copyFromAmAnagrafica(lst.get(0)));
			//dati ufficio
			AmUserUfficio uff = userService.getDatiUfficio(getRicercaGestOperatoriBean().getOperatore().getAmUser().getName());
			getRicercaGestOperatoriBean().getOperatore().setUfficio(uff);
			//id e abilitazione operatore			
			CsOOperatore op = getCsOOperatore();
			if (op != null) {
				getRicercaGestOperatoriBean().getOperatore().setIdOperatore(op.getId());
				getRicercaGestOperatoriBean().getOperatore().setAbilitato(op.getAbilitato() != null && op.getAbilitato().equals("1"));
			}
			getInsUpdDelGestOperatoriBean().loadOrganizzazioni();
			getInsUpdDelGestOperatoriBean().loadDataTable(getRicercaGestOperatoriBean().getOperatore() == null ? null : getRicercaGestOperatoriBean().getOperatore().getIdOperatore());
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void salvaOperatore() {
		try {
			boolean update = getRicercaGestOperatoriBean().isCsOperatore();
			CsOOperatore op = null;
			if (update) {
				op = getCsOOperatore();
			} else {
				op = new CsOOperatore();
				op.setUsername(getRicercaGestOperatoriBean().getOperatore().getCodiceFiscale());
				op.setDataInizioVal(DF.parse(DF.format(new Date())));
			}
			op.setAbilitato(getRicercaGestOperatoriBean().getOperatore().isAbilitato() ? "1" : "0");
			operatoreService.insertOrUpdateOperatore(op, update);
			getRicercaGestOperatoriBean().setCodiceFiscaleRic(op.getUsername());
			ricercaOperatore();
			
			//update lista operatori
			caricaLstoperatori();
			
			addInfo("Operatore " + (update ? "modificato" : "inserito") + " correttamente", "");
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void aggiungiOperatoreSettore() {
		try {
			boolean errSettore = getInsUpdDelGestOperatoriBean().getSelSettore() == null || getInsUpdDelGestOperatoriBean().getSelSettore().longValue() == 0;
			boolean errTipoOp = getInsUpdDelGestOperatoriBean().getSelTipoOperatore() == null || getInsUpdDelGestOperatoriBean().getSelTipoOperatore().longValue() == 0;
			String errMsg = null;
			if (errSettore) {
				errMsg = "Selezionare il settore";
			}
			if (errTipoOp) {
				if (errMsg == null) {
					errMsg = "Selezionare il tipo operatore";
				} else {
					errMsg += " e il tipo operatore";
				}
			}
			if (errMsg != null) {
				addWarning(errMsg, "");
				return;
			}
			String amGroup = null;
			if (getInsUpdDelGestOperatoriBean().getSelRuoli() != null && getInsUpdDelGestOperatoriBean().getSelRuoli().size() > 0) {
				for (String selRuolo : getInsUpdDelGestOperatoriBean().getSelRuoli()) {
					if (amGroup == null) {
						amGroup = "";
					} else {
						amGroup += ",";
					}
					amGroup += selRuolo;
				}
			}
			long appartiene = getInsUpdDelGestOperatoriBean().isAppartieneSettore() ? 1L : 0L;
			
			CsOOperatoreSettore opSet = getCsOOperatoreSettore(getInsUpdDelGestOperatoriBean().getSelSettore());
			boolean update = (opSet != null);
			if (!update) {
				opSet = new CsOOperatoreSettore();
				opSet.setDataInizioApp(DF.parse(DF.format(new Date())));
				opSet.setCsOOperatore(new CsOOperatore());
				opSet.getCsOOperatore().setId(getRicercaGestOperatoriBean().getOperatore().getIdOperatore());
				opSet.setCsOSettore(new CsOSettore());
				opSet.getCsOSettore().setId(getInsUpdDelGestOperatoriBean().getSelSettore());
			}
			opSet.setDataFineApp(DataModelCostanti.END_DATE);
			opSet.setAmGroup(amGroup);
			opSet.setAppartiene(appartiene);
			opSet.setFirma(getInsUpdDelGestOperatoriBean().isFirmaSettore());
			if(getInsUpdDelGestOperatoriBean().isFirmaSettore()){
				it.webred.cs.csa.ejb.dto.BaseDTO bdto = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillEnte(bdto);
				bdto.setObj(amGroup);
				bdto.setObj2(getInsUpdDelGestOperatoriBean().getSelSettore().longValue());
				operatoreService.resetFirmaTuttiRespSettore(bdto);
			}
			opSet = operatoreService.insertOrUpdateOperatoreSettore(opSet, update);
			
			//Aggiorno CsOOperatoreTipoOperatore
			Long tipoOpAttuale = getInsUpdDelGestOperatoriBean().getSelTipoOperatore();
			boolean aggiornaTipoOp = tipoOpAttuale!=null && tipoOpAttuale.longValue()!=0;
			if (update && aggiornaTipoOp) {
				OperatoreDTO opDto = new OperatoreDTO();
				opDto.setIdOperatoreSettore(opSet.getId());		
				opDto.setDate(new Date());
				fillEnte(opDto);
				CsOOperatoreTipoOperatore tipoOp = operatoreService.getTipoByOperatoreSettore(opDto);
                aggiornaTipoOp = !(tipoOp!=null && tipoOp.getCsTbTipoOperatore().getId()==tipoOpAttuale.longValue());
				//Se sono uguali non c'è stato aggiornamento del dato
				if (tipoOp != null && aggiornaTipoOp) {
					tipoOp.setDataFineApp(new Date());
					operatoreService.insertOrUpdateTipoOperatore(tipoOp, true);
				}
			}
			if (aggiornaTipoOp) {
				CsOOperatoreTipoOperatore tipoOpNew = new CsOOperatoreTipoOperatore();
				tipoOpNew.setCsTbTipoOperatore(new CsTbTipoOperatore());
				tipoOpNew.getCsTbTipoOperatore().setId(tipoOpAttuale);
				tipoOpNew.setDataInizioApp(DF.parse(DF.format(new Date())));
				tipoOpNew.setDataFineApp(DataModelCostanti.END_DATE);
				tipoOpNew.setCsOOperatoreSettore(new CsOOperatoreSettore());
				tipoOpNew.getCsOOperatoreSettore().setId(opSet.getId());
				operatoreService.insertOrUpdateTipoOperatore(tipoOpNew, false);
			}
			getInsUpdDelGestOperatoriBean().loadDataTable(getRicercaGestOperatoriBean().getOperatore().getIdOperatore());
			addInfo("Associazione operatore/settore inserita correttamente", "");
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void eliminaOperatoreSettore() {
		try {
			List<DataTableCsOOperatoreSettore> selOpSets = getSelOperatoreSettore();
			if (selOpSets == null || selOpSets.size() == 0) {
				addWarning("Selezionare almeno una riga della tabella", "");
				return;
			}
			for (DataTableCsOOperatoreSettore selOpSet : selOpSets) {
				CsOOperatoreSettore opSet = getCsOOperatoreSettore(selOpSet.getCsOSettore().getId());
				if (opSet != null) {
					operatoreService.deleteOperatoreSettore(opSet);
				}
			}
			getInsUpdDelGestOperatoriBean().loadDataTable(getRicercaGestOperatoriBean().getOperatore().getIdOperatore());
			addInfo("Eliminazione associazioni operatore/settore effettuata correttamente", "");
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void disattivaOperatoreSettore() {
		try {
			List<DataTableCsOOperatoreSettore> selOpSets = getSelOperatoreSettore();
			if (selOpSets == null || selOpSets.size() == 0) {
				addWarning("Selezionare almeno una riga della tabella", "");
				return;
			}
			for (DataTableCsOOperatoreSettore selOpSet : selOpSets) {
				CsOOperatoreSettore opSet = getCsOOperatoreSettore(selOpSet.getCsOSettore().getId());
				if (opSet != null) {
					opSet.setDataFineApp(DF.parse(DF.format(new Date())));
					opSet.setFirma(false);
					operatoreService.insertOrUpdateOperatoreSettore(opSet, true);
				}
			}
			getInsUpdDelGestOperatoriBean().loadDataTable(getRicercaGestOperatoriBean().getOperatore().getIdOperatore());
			addInfo("Disattivazione associazioni operatore/settore effettuata correttamente", "");
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void attivaOperatoreSettore() {
		try {
			List<DataTableCsOOperatoreSettore> selOpSets = getSelOperatoreSettore();
			if (selOpSets == null || selOpSets.size() == 0) {
				addWarning("Selezionare almeno una riga della tabella", "");
				return;
			}
			for (DataTableCsOOperatoreSettore selOpSet : selOpSets) {
				CsOOperatoreSettore opSet = getCsOOperatoreSettore(selOpSet.getCsOSettore().getId());
				if (opSet != null) {
					opSet.setDataFineApp(DataModelCostanti.END_DATE);
					operatoreService.insertOrUpdateOperatoreSettore(opSet, true);
				}
			}
			getInsUpdDelGestOperatoriBean().loadDataTable(getRicercaGestOperatoriBean().getOperatore().getIdOperatore());
			addInfo("Attivazione associazioni operatore/settore effettuata correttamente", "");
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	private List<DataTableCsOOperatoreSettore> getSelOperatoreSettore() {
		List<DataTableCsOOperatoreSettore> opSets = getInsUpdDelGestOperatoriBean().getDataTableDati();
		List<DataTableCsOOperatoreSettore> selOpSets = new ArrayList<DataTableCsOOperatoreSettore>();
		for (DataTableCsOOperatoreSettore opSet : opSets) {
			if (opSet.isSelezionato()) {
				selOpSets.add(opSet);
			}
		}
		return selOpSets;
	}

	public List<DatiOperatore> getLstOperatori() {
		
		if(lstOperatori == null) {
			caricaLstoperatori();
		}
		
		return lstOperatori;
	}

	public void setLstOperatori(List<DatiOperatore> lstOperatori) {
		this.lstOperatori = lstOperatori;
	}

	public int getIdxSelected() {
		return idxSelected;
	}

	public void setIdxSelected(int idxSelected) {
		this.idxSelected = idxSelected;
	}

	public List<DatiOperatore> getLstOperatoriFiltrati() {
		return lstOperatoriFiltrati;
	}

	public void setLstOperatoriFiltrati(List<DatiOperatore> lstOperatoriFiltrati) {
		this.lstOperatoriFiltrati = lstOperatoriFiltrati;
	}


	
}
