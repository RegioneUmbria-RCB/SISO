package it.webred.cs.csa.web.manbean.configurazione;

import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.ejb.dto.configurazione.CsOOperatoreSettoreEstesa;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOperatoreTipoOperatore;
import it.webred.cs.data.model.CsOOpsettoreAlertConfig;
import it.webred.cs.data.model.CsOOpsettoreAlertConfigPK;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsTbTipoOperatore;
import it.webred.cs.jsf.bean.DatiOperatore;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;

@ManagedBean
@ViewScoped
public class GestioneOperatoriBean extends CsUiCompBaseBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final DateFormat DF = new SimpleDateFormat("dd/MM/yyyy");
	
	private String widgetVar = "GestioneOperatoriVar";
	
	@ManagedProperty(value="#{insUpdDelGestOperatoriBean}")
	private InsUpdDelGestOperatoriBean insUpdDelGestOperatoriBean;

	private int idxSelected;
	private OperatoriTableDataModel operatoriDataModel;
	private List<DatiOperatore> operatoriSelezionati;
	private boolean pnlGestioneRendered;
	private boolean pnlGestioneSingola;
	
	protected AccessTableOperatoreSessionBeanRemote operatoreService = 
			(AccessTableOperatoreSessionBeanRemote) getCarSocialeEjb("AccessTableOperatoreSessionBean");

	public GestioneOperatoriBean(){
		this.operatoriDataModel= new OperatoriTableDataModel();
	}
	
	public String getWidgetVar() {
		return widgetVar;
	}

	public void setWidgetVar(String widgetVar) {
		this.widgetVar = widgetVar;
	}
	
	public InsUpdDelGestOperatoriBean getInsUpdDelGestOperatoriBean() {
		return insUpdDelGestOperatoriBean;
	}

	public void setInsUpdDelGestOperatoriBean(InsUpdDelGestOperatoriBean insUpdDelGestOperatoriBean) {
		this.insUpdDelGestOperatoriBean = insUpdDelGestOperatoriBean;
	}
	
	public boolean isPnlGestioneRendered() {
		return this.pnlGestioneRendered;
		//return getRicercaGestOperatoriBean().getOperatore() != null && getRicercaGestOperatoriBean().getOperatore().getIdOperatore() != null;
	}
	
	private CsOOperatore getCsOOperatore(Long idOperatore) throws Exception {
		OperatoreDTO opDto = new OperatoreDTO();
		fillEnte(opDto);
		opDto.setIdOperatore(idOperatore);
		return operatoreService.findOperatoreById(opDto);
		//opDto.setUsername(getRicercaGestOperatoriBean().getOperatore().getAmUser().getName());
		//return operatoreService.findOperatoreByUsername(opDto);
	}
	
	private CsOOperatoreSettore getCsOOperatoreSettore(Long idSettore, Long idOpSettore, Long idOperatore) throws Exception {
		OperatoreDTO opDto = new OperatoreDTO();
		if(idOpSettore!=null)
			opDto.setIdOperatoreSettore(idOpSettore);
		else{
			opDto.setIdOperatore(idOperatore);
			opDto.setIdSettore(idSettore);
			opDto.setDate(new Date());
		}
		fillEnte(opDto);
		return operatoreService.findOperatoreSettoreById(opDto);
	}
	
	private void salvaOperatore(DatiOperatore datiop) {
		try {
			BaseDTO input = new BaseDTO();
			fillEnte(input);
			boolean update = datiop.getIdOperatore()!=null;
			CsOOperatore op = null;
			if (update) {
				op = getCsOOperatore(datiop.getIdOperatore());
			} else {
				op = new CsOOperatore();
				op.setUsername(datiop.getAmUser().getName());
				op.setDataInizioVal(DF.parse(DF.format(new Date())));
			}
			op.setAbilitato(datiop.isAbilitato());
			input.setObj(op);
			if(!(!update && !datiop.isAbilitato()))
				operatoreService.salvaOperatore(input);
			//getRicercaGestOperatoriBean().setCodiceFiscaleRic(op.getUsername());
			//ricercaOperatore();
			
			//update lista operatori
			//caricaLstoperatori();
			
			addInfo("Operatore " + (update ? "modificato" : "inserito") + " correttamente", "");
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	public void aggiungiOperatoreSettore(DatiOperatore op) {
		try {
			
			if (op.getIdOperatore()==null || !op.isAbilitato()) {
				addWarning("Attivare l'operatore prima di procedere alla configurazione", "");
				return;
			}
			
			BaseDTO input = new BaseDTO();
			fillEnte(input);
			
			boolean errSettore = insUpdDelGestOperatoriBean.getSelSettore() == null || insUpdDelGestOperatoriBean.getSelSettore().longValue() == 0;
			boolean errTipoOp = insUpdDelGestOperatoriBean.getSelTipoOperatore() == null || insUpdDelGestOperatoriBean.getSelTipoOperatore().longValue() == 0;		
			String errMsg = null;
			if (errSettore) errMsg = "Selezionare il settore";
			if (errTipoOp) {
				if (errMsg == null) errMsg = "Selezionare il tipo operatore";
				else 				errMsg += " e il tipo operatore";
			}
			if (errMsg != null) {
				addWarning(errMsg, "");
				return;
			}
			String amGroup = null;
			if (insUpdDelGestOperatoriBean.getSelRuoli() != null && insUpdDelGestOperatoriBean.getSelRuoli().size() > 0) {
				for (String selRuolo : insUpdDelGestOperatoriBean.getSelRuoli()) {
					amGroup = (amGroup == null) ? "" : amGroup + ",";
					amGroup += selRuolo;
				}
			}
			long appartiene = getInsUpdDelGestOperatoriBean().isAppartieneSettore() ? 1L : 0L;
			
			CsOOperatoreSettore opSet = getCsOOperatoreSettore(insUpdDelGestOperatoriBean.getSelSettore(), null, op.getIdOperatore());
			boolean update = (opSet != null);
			if (!update) {
				opSet = new CsOOperatoreSettore();
				opSet.setDataInizioApp(DF.parse(DF.format(new Date())));
				opSet.setCsOOperatore(new CsOOperatore());
				opSet.getCsOOperatore().setId(op.getIdOperatore());
				opSet.setCsOSettore(new CsOSettore());
				opSet.getCsOSettore().setId(insUpdDelGestOperatoriBean.getSelSettore());
			}
			opSet.setDataFineApp(DataModelCostanti.END_DATE);
			opSet.setAmGroup(amGroup);
			opSet.setAppartiene(appartiene);
			opSet.setFirma(insUpdDelGestOperatoriBean.isFirmaSettore());
			if(insUpdDelGestOperatoriBean.isFirmaSettore()){
				it.webred.cs.csa.ejb.dto.BaseDTO bdto = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillEnte(bdto);
				bdto.setObj(amGroup);
				bdto.setObj2(insUpdDelGestOperatoriBean.getSelSettore().longValue());
				operatoreService.resetFirmaTuttiRespSettore(bdto);
			}

			opSet = this.salvaOpSettore(opSet);
					
			//Aggiorno CsOOperatoreTipoOperatore
			Long tipoOpAttuale = insUpdDelGestOperatoriBean.getSelTipoOperatore();
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
					this.salvaTipoOperatore(tipoOp);
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
				
				this.salvaTipoOperatore(tipoOpNew);
			}
			
			//Aggiorno la configurazione per la visualizzazione di notifiche/ricezione email
			
			String[] notifiche = insUpdDelGestOperatoriBean.getRiceviNotifiche();
			String[] email = 	 insUpdDelGestOperatoriBean.getRiceviEmail();
			
			List<CsOOpsettoreAlertConfig> lst2Save = new ArrayList<CsOOpsettoreAlertConfig>();
			for(SelectItem os : insUpdDelGestOperatoriBean.getTipiAlert()){
				CsOOpsettoreAlertConfig ops = new CsOOpsettoreAlertConfig();
				CsOOpsettoreAlertConfigPK id = new CsOOpsettoreAlertConfigPK();
				id.setOperatoreSettoreId(opSet.getId());
				id.setTipoCod((String)os.getValue());
				
				ops.setId(id);
				ops.setFlgEmail(Arrays.asList(email).contains((String)os.getValue()));
				ops.setFlgNotifica(Arrays.asList(notifiche).contains((String)os.getValue()));
				
				lst2Save.add(ops);
			} 
			
			input.setObj(lst2Save);
			input.setObj2(opSet.getId());
			operatoreService.insertOrUpdateAlertConfig(input);
			
			getInsUpdDelGestOperatoriBean().loadDataTable(op.getIdOperatore());
			String opDesc = opSet.getCsOOperatore().getCsOOperatoreAnagrafica().getCognome()+" "+opSet.getCsOOperatore().getCsOOperatoreAnagrafica().getNome();
			String setDesc = opSet.getCsOSettore().getDescrizione();
			addInfo("Associazione operatore ["+opDesc+"] /settore["+setDesc+"] inserita correttamente. ", "");
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	public void eliminaOperatoreSettore() {
		try {
			List<CsOOperatoreSettoreEstesa> selOpSets = getSelOperatoreSettore();
			if (selOpSets == null || selOpSets.size() == 0) {
				addWarning("Selezionare almeno una riga della tabella", "");
				return;
			}
			
			for (CsOOperatoreSettoreEstesa selOpSet : selOpSets) {
				CsOOperatoreSettore opSet = getCsOOperatoreSettore(null, selOpSet.getId(), this.getOperatoreSelezionato().getIdOperatore());
				if (opSet != null) {
					BaseDTO input = new BaseDTO();
					fillEnte(input);
					input.setObj(opSet);
					operatoreService.deleteOperatoreSettore(input);
				}
			}
			getInsUpdDelGestOperatoriBean().loadDataTable(this.getOperatoreSelezionato().getIdOperatore());
			addInfo("Eliminazione associazioni operatore/settore effettuata correttamente", "");
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	private DatiOperatore getOperatoreSelezionato(){
		if(this.operatoriSelezionati!=null && this.operatoriSelezionati.size()==1)
			return this.operatoriSelezionati.get(0);
		else return null;
	}
	
	private void disattivaOperatoreSettore(List<CsOOperatoreSettoreEstesa> selOpSets){
		for (CsOOperatoreSettoreEstesa selOpSet : selOpSets) {
			try{
				CsOOperatoreSettore opSet = getCsOOperatoreSettore(null, selOpSet.getId(), null);
				if (opSet != null) {
					opSet.setDataFineApp(DF.parse(DF.format(new Date())));
					opSet.setFirma(false);					
					opSet = this.salvaOpSettore(opSet);
					
					//AGGIORNO CS_O_OPERATORE_TIPOOPERATORE
					OperatoreDTO opDto = new OperatoreDTO();
					opDto.setIdOperatoreSettore(opSet.getId());		
					opDto.setDate(opSet.getDataFineApp());
					fillEnte(opDto);
					
					CsOOperatoreTipoOperatore tipoOp = operatoreService.getTipoByOperatoreSettore(opDto);
	               
					tipoOp.setDataFineApp(opSet.getDataFineApp());
					this.salvaTipoOperatore(tipoOp);
				}
			} catch (Exception e) {
				addErrorFromProperties("salva.error");
				logger.error(e.getMessage(),e);
			}
		}
	}
	
	public void disattivaOperatoreSettore() {

		List<CsOOperatoreSettoreEstesa> selOpSets = getSelOperatoreSettore();
		if (selOpSets == null || selOpSets.size() == 0) {
			addWarning("Selezionare almeno una riga della tabella", "");
			return;
		}
		
		disattivaOperatoreSettore(selOpSets);
		DatiOperatore op = this.getOperatoreSelezionato();
		getInsUpdDelGestOperatoriBean().loadDataTable(op.getIdOperatore());
		addInfo("Disattivazione associazioni operatore/settore effettuata correttamente", "");
	}
	
	public void attivaOperatoreSettore() {
		try {
			List<CsOOperatoreSettoreEstesa> selOpSets = getSelOperatoreSettore();
			if (selOpSets == null || selOpSets.size() == 0) {
				addWarning("Selezionare almeno una riga della tabella", "");
				return;
			}
			
			for (CsOOperatoreSettoreEstesa selOpSet : selOpSets) {
				if(!selOpSet.isAttivo()){
					CsOOperatoreSettore opSet = getCsOOperatoreSettore(null, selOpSet.getId(), null);
					if (opSet != null) {
						
						Date dataFineOld = opSet.getDataFineApp();
						Date oggi = new Date();
						if(!opSet.getDataFineApp().equals(oggi));
							opSet.setDataInizioApp(new Date());
							
						opSet.setDataFineApp(DataModelCostanti.END_DATE);
						opSet = this.salvaOpSettore(opSet);
						
						Iterator<CsOOperatoreTipoOperatore> lstTipo = opSet.getTipoOperatore().iterator();
						while(lstTipo.hasNext()){
							CsOOperatoreTipoOperatore tipo = lstTipo.next();
							if(tipo.getDataFineApp().equals(dataFineOld)){
								tipo.setDataInizioApp(opSet.getDataInizioApp());
								tipo.setDataFineApp(opSet.getDataFineApp());
								this.salvaTipoOperatore(tipo);
							}
						}
						
					}
				}
			}
			DatiOperatore op = this.getOperatoreSelezionato();
			getInsUpdDelGestOperatoriBean().loadDataTable(op.getIdOperatore());
			addInfo("Attivazione associazioni operatore/settore effettuata correttamente", "");
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	private List<CsOOperatoreSettoreEstesa> getSelOperatoreSettore() {
		List<CsOOperatoreSettoreEstesa> opSets = getInsUpdDelGestOperatoriBean().getDataTableDati();
		List<CsOOperatoreSettoreEstesa> selOpSets = new ArrayList<CsOOperatoreSettoreEstesa>();
		for (CsOOperatoreSettoreEstesa opSet : opSets) {
			if (opSet.isSelezionato()) {
				selOpSets.add(opSet);
			}
		}
		return selOpSets;
	}

	public int getIdxSelected() {
		return idxSelected;
	}

	public void setIdxSelected(int idxSelected) {
		this.idxSelected = idxSelected;
	}

	private CsOOperatoreSettore salvaOpSettore(CsOOperatoreSettore opSettore) throws Exception{
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(opSettore);
		return operatoreService.salvaOperatoreSettore(dto);
	}
	

	private CsOOperatoreTipoOperatore salvaTipoOperatore(CsOOperatoreTipoOperatore operatore) throws Exception{
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(operatore);
		return operatoreService.salvaTipoOperatore(dto);
	}
	
	public List<DatiOperatore> getOperatoriSelezionati() {
		return operatoriSelezionati;
	}

	public void setOperatoriSelezionati(List<DatiOperatore> operatoriSelezionati) {
		this.operatoriSelezionati = operatoriSelezionati;
	}

	public void abilitaOperatori(){
		for(DatiOperatore op : operatoriSelezionati){
		    op.setAbilitato(true);
		    salvaOperatore(op);
			logger.debug("Operatori attivati"+op);
		}
	}

	public void disabilitaOperatori(){
		for(DatiOperatore op : operatoriSelezionati){
		    op.setAbilitato(false);
		    salvaOperatore(op);
		    
		    List<CsOOperatoreSettoreEstesa> lst = getInsUpdDelGestOperatoriBean().getConfigurazioniUtente(op.getIdOperatore());
		    disattivaOperatoreSettore(lst);
			getInsUpdDelGestOperatoriBean().loadDataTable(op.getIdOperatore());
		   
			logger.debug("Operatori disattivati"+op);
		}
	}
	
	public void onClose(){
		this.pnlGestioneRendered=false;
		this.pnlGestioneSingola=false;
		this.getInsUpdDelGestOperatoriBean().loadOrganizzazioni();
		getInsUpdDelGestOperatoriBean().refreshFirma();
	}
	
	public void configuraAccesso(){
		this.pnlGestioneRendered=false;
		this.pnlGestioneSingola=false;
		
		if(this.getOperatoriSelezionati().isEmpty()){
			this.addWarning("", "Selezionare un utente del sistema, per visualizzare l'attuale configurazione");
			return;
		}
		
		getInsUpdDelGestOperatoriBean().loadOrganizzazioni();
		getInsUpdDelGestOperatoriBean().refreshFirma();
		
		this.pnlGestioneRendered=true;
		
		getInsUpdDelGestOperatoriBean().setOperatoreSelezionato(null);
		if(this.getOperatoriSelezionati().size()==1){
			DatiOperatore datiop = this.operatoriSelezionati.get(0);
			getInsUpdDelGestOperatoriBean().loadDataTable(datiop.getIdOperatore());
			getInsUpdDelGestOperatoriBean().setOperatoreSelezionato(datiop);
			this.pnlGestioneSingola=true;
		}else this.pnlGestioneSingola=false;
	}
	
	public void salvaConfigurazione(){
		for(DatiOperatore op : this.operatoriSelezionati){
			this.aggiungiOperatoreSettore(op);
		}
	}
	
	public OperatoriTableDataModel getOperatoriDataModel() {
	   return operatoriDataModel;
	}

	public void setOperatoriDataModel(OperatoriTableDataModel operatoriDataModel) {
		this.operatoriDataModel = operatoriDataModel;
	}

	public boolean isPnlGestioneSingola() {
		return pnlGestioneSingola;
	}

	public void setPnlGestioneSingola(boolean pnlGestioneSingola) {
		this.pnlGestioneSingola = pnlGestioneSingola;
	}

	public void setPnlGestioneRendered(boolean pnlGestioneRendered) {
		this.pnlGestioneRendered = pnlGestioneRendered;
	}
	public void onRowDeSelect(){
		this.pnlGestioneRendered=false;
		this.pnlGestioneSingola=false;
	}
}
