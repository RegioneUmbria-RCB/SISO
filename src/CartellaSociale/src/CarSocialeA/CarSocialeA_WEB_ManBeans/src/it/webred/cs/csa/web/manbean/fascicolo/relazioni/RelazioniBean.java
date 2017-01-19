package it.webred.cs.csa.web.manbean.fascicolo.relazioni;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.InterventoDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.RelazioneDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloBean;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompBaseBean;
import it.webred.cs.csa.web.manbean.fascicolo.interventiTreeView.TipoInterventoManBean;
import it.webred.cs.csa.web.manbean.report.ReportBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsCDiarioConchi;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDDiarioDoc;
import it.webred.cs.data.model.CsDPai;
import it.webred.cs.data.model.CsDRelazione;
import it.webred.cs.data.model.CsFlgIntervento;
import it.webred.cs.data.model.CsLoadDocumento;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsRelRelazioneProbl;
import it.webred.cs.data.model.CsRelRelazioneProblPK;
import it.webred.cs.data.model.CsRelRelazioneTipoint;
import it.webred.cs.data.model.CsRelRelazioneTipointPK;
import it.webred.cs.data.model.CsTbMacroAttivita;
import it.webred.cs.data.model.CsTbMicroAttivita;
import it.webred.cs.data.model.CsTbProbl;
import it.webred.cs.data.model.CsTbProblematica;
import it.webred.cs.data.model.CsTbTipoDiario;
import it.webred.cs.jsf.bean.DatiInterventoBean;
import it.webred.cs.jsf.interfaces.IRelazioni;
import it.webred.cs.jsf.manbean.DiarioDocsMan;
import it.webred.cs.jsf.manbean.SchedaPaiMan;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.validator.ValidatorException;

import org.primefaces.component.wizard.Wizard;
import org.primefaces.context.DefaultRequestContext;
import org.primefaces.context.PrimeFacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.util.ComponentUtils;

public class RelazioniBean extends FascicoloCompBaseBean implements IRelazioni{

	private List<RelazioneDTO> listaRelazioniDTO;
	private int idxSelected;
	private RelazioneDTO relazioneDTO;
	private String modalHeader;
	private boolean loadRelazione;
	private boolean uploadDisabled;
	private DiarioDocsMan diarioDocsMan;
	private boolean removeFile=false;
	
	private ReportBean reportS;
//	private Long idTipoInterventoSelezionato;
	private List<SelectItem> listaTipiIntervento;
//	private List<CsCTipoIntervento> listaTipiInterventoAttivi;
	private List<DatiInterventoBean> listaFogliAmmCollegati;
	
	private SchedaPaiMan managerPai;
	private boolean disableNuovoPAI;
	
	private List<SelectItem> catalogoAttivita;    
		
	private List<SelectItem> catalogoProblematiche;
	private String selectedProblematicaId;
	private boolean selectedProblematicaRisolta;
	
	private List<SelectItem> lstRiunioneCon;
	private List<SelectItem> lstConChi;
	
	private TipoInterventoManBean tipoInterventoManBean;
	
	@Override
	public void initializeData() {
		
		try{
			selectedProblematicaId=null;
			selectedProblematicaRisolta=false;
			loadRelazione = false;
			uploadDisabled= false;
			managerPai = new SchedaPaiMan();
			managerPai.setIdCaso(idCaso);
			managerPai.setResponsabileCaso(getOperResponsabileCaso());
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(idCaso);
			listaRelazioniDTO = diarioService.findRelazioniByCaso(dto);
			
			diarioDocsMan = new DiarioDocsMan();
				
			catalogoAttivita = new ArrayList<SelectItem>();
			for (CsTbMacroAttivita macro : configService.getMacroAttivita(dto))
			{
				BaseDTO dto1 = new BaseDTO();
				fillEnte(dto1);
				dto1.setObj(macro.getId());
				List<CsTbMicroAttivita> microl = configService.getMicroAttivitaByIdMacroAttivita(dto1);
				ArrayList<SelectItem> iteml=new ArrayList<SelectItem>();
				if(microl!=null)					
				for (CsTbMicroAttivita micro : microl)
				{
					SelectItem option = new SelectItem(micro, micro.getDescrizione(), micro.getTooltip());
					iteml.add(option);
				}
								
				SelectItemGroup group = new SelectItemGroup(macro.getDescrizione(), macro.getTooltip(), 
						false, iteml.toArray( new SelectItem[iteml.size()]) );			
				catalogoAttivita.add(group);
			}			
						
			
			Map <String, Map<String, List<SelectItem>>> mapGroups = new HashMap<String, Map<String, List<SelectItem>>>();					
			for (CsTbProbl problematica : configService.getProbl(dto))
			{									
				String flagMatImm = problematica.getFlagMatImm();
				Map<String, List<SelectItem>> mapGroups2= mapGroups.get(flagMatImm);
				if(mapGroups2==null)
				{
					mapGroups2 = new HashMap<String, List<SelectItem>>();
					mapGroups.put(flagMatImm, mapGroups2);
				}
				
				String tipo = problematica.getTipo();
				List<SelectItem> items = mapGroups2.get(tipo);
				if(items==null)
				{
					items = new ArrayList<SelectItem> ();
					mapGroups2.put(tipo,  items);
				}
				
				SelectItem option = new SelectItem(problematica.getId(), 
						problematica.getDescrizione(), 
						problematica.getTooltip());
				items.add(option);				
			}
			
			catalogoProblematiche = new ArrayList<SelectItem>();
			for(String matImm: mapGroups.keySet())
			{
				Map<String, List<SelectItem>> mapGroups2 = mapGroups.get(matImm);
				List<SelectItemGroup> tipoGroups = new ArrayList<SelectItemGroup>();			
				for(String tipo: mapGroups2.keySet() )
				{
					List<SelectItem> probls = mapGroups2.get(tipo);
					SelectItemGroup tipoGroup = new SelectItemGroup(tipo, tipo, false, 
							probls.toArray(new SelectItem[probls.size()]));
					tipoGroups.add(tipoGroup);									
				}				
				
				SelectItemGroup groupMatImm = null;
				if("M".equalsIgnoreCase(matImm))
				{
					SelectItemGroup groupMat = new SelectItemGroup("Materiale", "Problematiche di tipo materiale", false, tipoGroups.toArray(new SelectItem[tipoGroups.size()]));
					catalogoProblematiche.add(groupMat);
				}
				else if("I".equalsIgnoreCase(matImm))
				{
					SelectItemGroup groupImm = new SelectItemGroup("Immateriale","Problematiche di tipo immateriale", false, tipoGroups.toArray(new SelectItem[tipoGroups.size()]));
					catalogoProblematiche.add(groupImm);
				}
				
			}
			
			tipoInterventoManBean = new TipoInterventoManBean(getListaTipiIntervento(), "");		
						
		} 
		catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	public void eliminaPai(CsDPai pai){
	 try{	
		 managerPai.elimina(pai);
		 
		 RequestContext.getCurrentInstance().addCallbackParam("eliminato", true);		
		 addInfoFromProperties("elimina.ok");
		 
		 this.initializeData();

		} catch(Exception e) {
			addErrorFromProperties("elimina.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void nuovo() {
		
		modalHeader = "Nuova attività professionale";
		relazioneDTO = new RelazioneDTO();
//		listaTipiInterventoAttivi = new ArrayList<CsCTipoIntervento>();
		listaFogliAmmCollegati = new ArrayList<DatiInterventoBean>();
		
		//Gestione Upload File
		loadRelazione = false;
		uploadDisabled = false;
		diarioDocsMan = new DiarioDocsMan();
		diarioDocsMan.getuFileMan().setExternalSave(true);
					
		tipoInterventoManBean.reset();
		resetWizardStep();
		selectedProblematicaId=null;
		selectedProblematicaRisolta=false;
		
	}
	
	@Override
	public void carica() {
						
		try 
		{			
			
			modalHeader = "Modifica attività";
			CsDRelazione rel = listaRelazioniDTO.get(idxSelected).getRelazione();
			
			//Load full
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(rel.getDiarioId());
			relazioneDTO = diarioService.findRelazioneFullById(dto);
			
//			listaTipiInterventoAttivi = new ArrayList<CsCTipoIntervento>(); 
//			listaTipiInterventoAttivi.addAll(relazioneDTO.getRelazione().getCsCTipoInterventos());			
			
			diarioDocsMan = new DiarioDocsMan();
			diarioDocsMan.getuFileMan().setExternalSave(true);
			if(!relazioneDTO.getRelazione().getCsDDiario().getCsDDiarioDocs().isEmpty()){
				loadRelazione = true;
				uploadDisabled=true;
			}else{
				loadRelazione=false;
				uploadDisabled=false;
			}
					
			listaFogliAmmCollegati = new ArrayList<DatiInterventoBean>();
			for(CsDDiario d : relazioneDTO.getRelazione().getCsDDiario().getCsDDiariPadre()){
				if(d.getCsTbTipoDiario().getId().equals(DataModelCostanti.TipoDiario.FOGLIO_AMM_ID)) {
					fillEnte(dto);
					dto.setObj(d.getId());
					CsFlgIntervento fgl = diarioService.findFglInterventoById(dto);
					DatiInterventoBean dib = new DatiInterventoBean(fgl.getCsIIntervento(), idSoggetto);
					listaFogliAmmCollegati.add(dib);
				}
			}
			
			tipoInterventoManBean.reset();
			resetWizardStep();
			selectedProblematicaId=null;
			selectedProblematicaRisolta=false;
			

		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	@Override
	public void eliminaDocumento(){
		uploadDisabled=false;
		removeFile=true;
		diarioDocsMan = new DiarioDocsMan();
		diarioDocsMan.getuFileMan().setExternalSave(true);
	}
	
	@Override
	public void salva() {
		
		if(!validaRelazione()) {
			return;
		}
		
		try{
//			if(listaTipiInterventoAttivi != null)
//			{	
//				HashSet<CsCTipoIntervento> csCTipoInterventos = new HashSet<CsCTipoIntervento>();
//				relazioneDTO.getRelazione().setCsCTipoInterventos(csCTipoInterventos);
//				for(CsCTipoIntervento intervento: listaTipiInterventoAttivi)
//				{					
//					csCTipoInterventos.add(intervento);			
//				}
//			}
//			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			
			if(relazioneDTO.getRelazione().getDiarioId() != null) {
				
				if(removeFile || (loadRelazione && !uploadDisabled)){
					//Ho rimosso il file presente o sono passata ad altra modalità di redazione
					Set<CsDDiarioDoc> lstDocs = relazioneDTO.getRelazione().getCsDDiario().getCsDDiarioDocs();
					Iterator<CsDDiarioDoc> iter = lstDocs.iterator();
					if(lstDocs!=null && !lstDocs.isEmpty()){
						while(iter.hasNext()){
							CsLoadDocumento doc  = (iter.next()).getCsLoadDocumento();
							diarioDocsMan.deleteDocumentoFromDB(doc.getId());
						}
						relazioneDTO.getRelazione().getCsDDiario().setCsDDiarioDocs(null);
					}
				}
				
				dto.setObj(relazioneDTO.getRelazione());
				relazioneDTO.getRelazione().getCsDDiario().setUsrMod(dto.getUserId());
				relazioneDTO.getRelazione().getCsDDiario().setDtMod(new Date());
				
				diarioService.updateRelazione(dto);
			}
			else {
				
				dto.setObj(relazioneDTO.getRelazione());
				//trovo il caso 
				IterDTO itDto = new IterDTO();
				fillEnte(itDto);
				itDto.setIdCaso(csASoggetto.getCsACaso().getId());
				CsOOperatoreSettore opSettore = (CsOOperatoreSettore) getSession().getAttribute("operatoresettore");
				
		        CsACaso csa = new CsACaso();
		        csa.setId(idCaso);
		        
		        CsTbTipoDiario cstd = new CsTbTipoDiario(); 
		        cstd.setId(new Long(DataModelCostanti.TipoDiario.RELAZIONE_ID)); 
		        
		        relazioneDTO.getRelazione().getCsDDiario().setCsACaso(csa);
		        relazioneDTO.getRelazione().getCsDDiario().setResponsabileCaso(this.getOperResponsabileCaso().getId());
		        relazioneDTO.getRelazione().getCsDDiario().setCsTbTipoDiario(cstd);
		        relazioneDTO.getRelazione().getCsDDiario().setDtIns(new Date());
		        relazioneDTO.getRelazione().getCsDDiario().setUserIns(dto.getUserId());
		        relazioneDTO.getRelazione().getCsDDiario().setCsOOperatoreSettore(opSettore);
		        
				relazioneDTO = diarioService.saveRelazione(dto);				
			}
			
			//salvo il documento
			if(this.loadRelazione){
				diarioDocsMan.getuFileMan().setIdDiario(relazioneDTO.getRelazione().getCsDDiario().getId());
				for(CsLoadDocumento loadDoc: diarioDocsMan.getuFileMan().getDocumentiUploaded())
					diarioDocsMan.getuFileMan().salvaDocumento(loadDoc);
			}
			
			addInfoFromProperties("salva.ok");
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
			
			

			//inizio modifica evoluzione-pai
			FascicoloBean fbean = (FascicoloBean) this.getBean("fascicoloBean");
			if (fbean != null && fbean.getPaiBean()!=null) { 
				fbean.getPaiBean().refreshPicklistRelazioni(relazioneDTO);
			}
			//fine modifica evoluzione-pai
			
			initializeData();
			
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	private boolean validaRelazione() {
		
		boolean ok = true;
		
		if(relazioneDTO.getRelazione().getCsDDiario().getDtAmministrativa() == null) {
			ok = false;
			addError("Il campo Data attività è obbligatorio", "");
		}
		
		if(loadRelazione && !uploadDisabled && diarioDocsMan.getuFileMan().getDocumentiUploaded().isEmpty()) {
			ok = false;
			addError("Aggiungere un documento", "");
		}
		
		if(!loadRelazione && null==relazioneDTO.getRelazione().getMicroAttivita())
		{
			ok = false;
			addError("Non è stato scelto il tipo di attività", "");
		}
				
		if(!loadRelazione && null!=relazioneDTO.getRelazione().getMicroAttivita() && "1".equalsIgnoreCase(relazioneDTO.getRelazione().getMicroAttivita().getFlagTipoForm()))
		{
			if((relazioneDTO.getRelazione().getSituazioneAmb() == null || "".equals(relazioneDTO.getRelazione().getSituazioneAmb()))) 
			{
				ok = false;
				addError("Il campo Situazione ambientale è obbligatorio per il tipo di attività scelto", "");
			}
		}
		
		if(relazioneDTO.getRelazione().getFlagRilevazioneProblematiche()!=null && "1".equalsIgnoreCase(relazioneDTO.getRelazione().getFlagRilevazioneProblematiche()))
		{
			if(relazioneDTO.getRelazione().getCsRelRelazioneProbl()==null || relazioneDTO.getRelazione().getCsRelRelazioneProbl().isEmpty())
			{
				ok = false;
				addError("Probleamtiche non specificate", "Avendo abilitato l'analisi delle problematiche, bisogna indicarne almeno una.");
			}
		}
		
		return ok;
	}
	
	@Override
	public List<SelectItem> getListaTipiIntervento()  {
		
		if(listaTipiIntervento == null) {
			try{
				
				listaTipiIntervento = new ArrayList<SelectItem>();
				InterventoDTO dto = new InterventoDTO();
				fillEnte(dto);
				dto.setLstIdCatSoc(this.getLstIdCatSoc());
		        CsOOperatoreSettore opSettore = (CsOOperatoreSettore) getSession().getAttribute("operatoresettore");
				dto.setIdSettore(opSettore.getCsOSettore().getId());
				List<CsCTipoIntervento> lista = interventoService.findTipiInterventoSettoreCatSoc(dto);
				for(CsCTipoIntervento tipoInt: lista) 
					listaTipiIntervento.add(new SelectItem(tipoInt.getId() , tipoInt.getDescrizione()));
			} catch (Exception e) {
				addErrorFromProperties("caricamento.error");
				logger.error(e.getMessage(),e);
			}
		}
		
		return listaTipiIntervento;
	}
	
	@Override
	public void aggiungiTipoInterventoButton() {
		
		try{
			Long selectedId = tipoInterventoManBean.getSelTipoInterventoId();
			
			if(selectedId!=null)
			{
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(String.valueOf(selectedId));
				CsCTipoIntervento tipoInt = interventoService.getTipoInterventoById(dto);
				if(tipoInt!=null)
				{
					CsRelRelazioneTipoint relRelazioneTipoint = new CsRelRelazioneTipoint(); 
					relRelazioneTipoint.setId(new CsRelRelazioneTipointPK(
							relazioneDTO.getRelazione().getDiarioId()!=null?relazioneDTO.getRelazione().getDiarioId():0,									
							tipoInt.getId()));					
					relRelazioneTipoint.setCsDRelazione(relazioneDTO.getRelazione());
					relRelazioneTipoint.setCsCTipoIntervento(tipoInt);
					
					Long selectedCustomId = tipoInterventoManBean.getSelTipoInterventoCutomId();
					if(selectedCustomId!=null)
					{
						dto.setObj(String.valueOf(selectedCustomId));
						CsCTipoIntervento tipoIntCustom = interventoService.getTipoInterventoById(dto);
						if(tipoIntCustom!=null)
						{
							relRelazioneTipoint.setCsCTipoInterventoCustom(tipoIntCustom);
						}
					}					
					
					List<CsRelRelazioneTipoint> list_relRelazioneTipoint = relazioneDTO.getRelazione().getCsRelRelazioneTipoint();
					if(list_relRelazioneTipoint==null)
					{
						list_relRelazioneTipoint = new ArrayList<CsRelRelazioneTipoint>();
						relazioneDTO.getRelazione().setCsRelRelazioneTipoint(list_relRelazioneTipoint);
					}
					
					list_relRelazioneTipoint.add(relRelazioneTipoint);				
				}
			}
				
		
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	public void eliminaTipoInterventoButton(CsRelRelazioneTipoint item) {
		
		if(relazioneDTO.getRelazione().getCsRelRelazioneTipoint()!=null && item!=null)
		{
			if(relazioneDTO.getRelazione().getCsRelRelazioneTipoint().remove(item))
			{
//				BaseDTO dto = new BaseDTO();
//				fillEnte(dto);
//				dto.setObj(item);
//				diarioService.deleteCsRelRelazioneProbl(dto);
				addInfo("Rimozione dell'intervento", "l'azione sarà effettiva al prossimo salvataggio");
			}
		}				
	}

	@Override
	public Long getIdCaso() {
		return idCaso;
	}

	@Override
	public void setIdCaso(Long idCaso) {
		this.idCaso = idCaso;
	}

	@Override
	public String getModalHeader() {
		return modalHeader;
	}

	public void setModalHeader(String modalHeader) {
		this.modalHeader = modalHeader;
	}

	@Override
	public RelazioneDTO getRelazioneDTO() {
		return relazioneDTO;
	}

	public void setRelazione(RelazioneDTO relazione) {
		this.relazioneDTO = relazione;
	}

	@Override
	public int getIdxSelected() {
		return idxSelected;
	}

	public void setIdxSelected(int idxSelected) {
		this.idxSelected = idxSelected;
	}

//	@Override
//	public Long getIdTipoInterventoSelezionato() {
//		return idTipoInterventoSelezionato;
//	}
//
//	public void setIdTipoInterventoSelezionato(Long idTipoInterventoSelezionato) {
//		this.idTipoInterventoSelezionato = idTipoInterventoSelezionato;
//	}
//
	@Override
	public List<CsCTipoIntervento> getListaTipiInterventoAttivi() {
		return new ArrayList<CsCTipoIntervento>(getRelazioneDTO().getRelazione().getCsCTipoInterventos());
	}

	public void setLoadRelazione(boolean loadRelazione) {
		this.loadRelazione = loadRelazione;
	}
//
//	public void setListaTipiInterventoAttivi(
//			List<CsCTipoIntervento> listaTipiInterventoAttivi) {
//		this.listaTipiInterventoAttivi = listaTipiInterventoAttivi;
//	}

	public void setListaTipiIntervento(List<SelectItem> listaTipiIntervento) {
		this.listaTipiIntervento = listaTipiIntervento;
	}

	@Override
	public DiarioDocsMan getDiarioDocsMan() {
		return diarioDocsMan;
	}

	public void setDiarioDocsMan(DiarioDocsMan diarioDocsMan) {
		this.diarioDocsMan = diarioDocsMan;
	}

	public SchedaPaiMan getManagerPai() {
		return managerPai;
	}

	public void setManagerPai(SchedaPaiMan managerPai) {
		this.managerPai = managerPai;
	}

	@Override
	public List<RelazioneDTO> getListaRelazioniDTO() {
		return this.listaRelazioniDTO;
	}

	public boolean isDisableNuovoPAI() {
		return disableNuovoPAI;
	}

	public void setDisableNuovoPAI(boolean disableNuovoPAI) {
		this.disableNuovoPAI = disableNuovoPAI;
	}

	public List<DatiInterventoBean> getListaFogliAmmCollegati() {
		return listaFogliAmmCollegati;
	}

	public void setListaFogliAmmCollegati(
			List<DatiInterventoBean> listaFogliAmmCollegati) {
		this.listaFogliAmmCollegati = listaFogliAmmCollegati;
	}

	@Override
	public void esportaRelazioneStampa() {
		reportS = new ReportBean();
		relazioneDTO = listaRelazioniDTO.get(idxSelected);
		List<CsCTipoIntervento> listaTipiInterventoAttivi = new ArrayList<CsCTipoIntervento>(); 
		listaTipiInterventoAttivi.addAll(relazioneDTO.getRelazione().getCsCTipoInterventos());
		reportS.esportaRelazione(relazioneDTO.getRelazione(),listaTipiInterventoAttivi,this.getLstIdCatSoc());
	}

	public ReportBean getReportS() {
		return reportS;
	}

	public void setReportS(ReportBean reportS) {
		this.reportS = reportS;
	}

	@Override
	public boolean getLoadRelazione() {
		return this.loadRelazione;
	}

	@Override
	public void loadRelazione() {
		// TODO Auto-generated method stub
		
	}

	public boolean isUploadDisabled() {
		return uploadDisabled;
	}

	public void setUploadDisabled(boolean uploadDisabled) {
		this.uploadDisabled = uploadDisabled;
	}
	
	
	public void clearRelazione(){
		if(loadRelazione){
			relazioneDTO.getRelazione().setFontiUtilizzate(null);
			relazioneDTO.getRelazione().setSituazioneAmb(null);
			relazioneDTO.getRelazione().setSituazioneParentale(null);
			relazioneDTO.getRelazione().setSituazioneSanitaria(null);
			relazioneDTO.getRelazione().setProposta(null);
			relazioneDTO.getRelazione().setConChi(null);
			relazioneDTO.getRelazione().setConChiAltro(null);
			relazioneDTO.getRelazione().setRiunioneCon(null);
		}else{
			diarioDocsMan = new DiarioDocsMan();
			diarioDocsMan.getuFileMan().setExternalSave(true);
		}
	}

	public String onWizardFlowProcess(FlowEvent event) {
		String fromStep = event.getOldStep();
		String toStep = event.getNewStep();
		
//		System.out.println("New: "+toStep+", Old: "+fromStep);
		
		if ("tab_attività".equals(fromStep) && "tab_problematiche".equals(toStep))
		{		
			//if(selectedMicroAttivitaId==null)
			if(relazioneDTO.getRelazione().getMicroAttivita()==null)
			{
				//
				addError("E' necessaria la scelta del tipo di attività.", "Dopo aver selezionato il tipo di micro attività cliccare sul pulsante Set.");
				return fromStep;
			}
			
		}
//        if(skip) {
//            skip = false;   //reset in case user goes back
//            return "confirm";
//        }
//        else {
            return toStep;
//        }
    }

	/**
	 * @return the catalogoAttivita
	 */
	public List<SelectItem> getCatalogoAttivita() {
		return catalogoAttivita;
	}		
	
	public Converter getMicroAttivitaConverter()
	{
		return new Converter()
		{

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {				
				
				if(value==null || "NULL".equals(value)) return null;
				
				Long id = Long.valueOf(value);
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(id);
				return configService.getMicroAttivitaById(dto);
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) {
				
				if(value==null || "NULL".equals(value)) 
					return "NULL";
				else if(value instanceof CsTbMicroAttivita)
				{
					return String.valueOf( ((CsTbMicroAttivita)value).getId() );
				}
				throw new ConverterException("tipo oggetto non valido: "+value);
			}
			
		};
	}



	/**
	 * @return the catalogoProblematiche
	 */
	public List<SelectItem> getCatalogoProblematiche() {
		return catalogoProblematiche;
	}

	/**
	 * @param catalogoProblematiche the catalogoProblematiche to set
	 */
	public void setCatalogoProblematiche(List<SelectItem> catalogoProblematiche) {
		this.catalogoProblematiche = catalogoProblematiche;
	}

	/**
	 * @return the selectedProblematicaId
	 */
	public String getSelectedProblematicaId() {
		return selectedProblematicaId;
	}

	/**
	 * @param selectedProblematicaId the selectedProblematicaId to set
	 */
	public void setSelectedProblematicaId(String selectedProblematicaId) {
		this.selectedProblematicaId = selectedProblematicaId;
	}

	/**
	 * @return the selectedProblematicaRisolta
	 */
	public boolean isSelectedProblematicaRisolta() {
		return selectedProblematicaRisolta;
	}

	/**
	 * @param selectedProblematicaRisolta the selectedProblematicaRisolta to set
	 */
	public void setSelectedProblematicaRisolta(boolean selectedProblematicaRisolta) {
		this.selectedProblematicaRisolta = selectedProblematicaRisolta;
	}
	
	
	public List<SelectItem> getLstRiunioneCon() {
		if(this.lstRiunioneCon == null)
			lstRiunioneCon = this.loadListaSettoriFlag(true);
		return lstRiunioneCon;
	}
	
	private List<SelectItem> loadListaSettoriFlag(boolean riunioneCon) {
		List<SelectItem> lista = new ArrayList<SelectItem>();
		SelectItem nullSi = new SelectItem("NULL","- seleziona -");		
		//nullSi.setNoSelectionOption(true);
		lista.add(nullSi);
		
		CeTBaseObject bo = new CeTBaseObject();
		fillEnte(bo);
		List<CsOSettore> lst = configService.getSettoreAll(bo);
		LinkedHashMap<String,List<SelectItem>> mappa = new LinkedHashMap<String,List<SelectItem>>();
		
		if (lst != null) {
			for (CsOSettore obj : lst) {
				//String belfioreOrg = obj.getCsOOrganizzazione().getBelfiore();
				//boolean comuneValido = belfioreOrg==null || belfioreOrg.equals(bo.getEnteId());	
				boolean comuneValido = true;
				boolean aggiungi = 	
						(riunioneCon && obj.getFlgRiunioneCon()!=null);
						
				if(comuneValido && aggiungi){
					String nomeOrg = obj.getCsOOrganizzazione().getNome();
					SelectItem si = new SelectItem(obj, obj.getNome());
					si.setDisabled(!obj.getAbilitato());
					List<SelectItem> gi = mappa.get(nomeOrg);
					if(gi==null)
						gi = new ArrayList<SelectItem>();
					
					gi.add(si);
					mappa.put(nomeOrg, gi);
				}				
			}
			
			Iterator<String> iter = mappa.keySet().iterator();
			while(iter.hasNext()){
				String s = (String) iter.next();
				mappa.get(s);
				SelectItemGroup g = new SelectItemGroup(s);
				List<SelectItem> lstIt = mappa.get(s);
				g.setSelectItems(lstIt.toArray(new SelectItem[lstIt.size()]));
				
				lista.add(g);
			}
		}		

		return lista;
	}
	
	public Converter getRiunioneConConverter()
	{
		return new Converter()
		{

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {				
				
				if(value==null || "NULL".equals(value)) return null;
				
				Long id = Long.valueOf(value);
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(id);
				return configService.getSettoreById(dto);
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) {
				
				if(value==null || "NULL".equals(value)) 
					return "NULL";
				else if(value instanceof CsOSettore)
				{
					return String.valueOf( ((CsOSettore)value).getId() );
				}
				throw new ConverterException("tipo oggetto non valido");
			}
			
		};
		
	}
	/**
	 * @return the lstConChi
	 */
	public List<SelectItem> getLstConChi() {
		if(this.lstConChi == null)
		{
			lstConChi = new ArrayList<SelectItem>();
			SelectItem nullSi = new SelectItem("NULL","- seleziona -");			
			//nullSi.setNoSelectionOption(true);			
			lstConChi.add(nullSi);
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			List<CsCDiarioConchi> diarioConchis = diarioService.getDiarioConchis(dto);
			for (CsCDiarioConchi csCDiarioConchi : diarioConchis) 
			{
				SelectItem item = new SelectItem(csCDiarioConchi, csCDiarioConchi.getDescrizione(), csCDiarioConchi.getTooltip());
				lstConChi.add(item);
			}			
		}		
		
		return lstConChi;
	}

	/**
	 * @param lstConChi the lstConChi to set
	 */
	public void setLstConChi(List<SelectItem> lstConChi) {
		this.lstConChi = lstConChi;
	}

	public Converter getConChiConverter()
	{
		return new Converter()
		{
	
			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {				
				
				if(value==null || "NULL".equals(value)) return null;
				
				Long id = Long.valueOf(value);
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(id);
				return diarioService.getDiarioConchi(dto);
				
			}
	
			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) {				
				if(value==null || "NULL".equals(value)) 
					return "NULL";
				
				else if(value instanceof CsCDiarioConchi)
				{
					return String.valueOf( ((CsCDiarioConchi)value).getId() );
				}
				throw new ConverterException("tipo oggetto non valido");
			}
			
		};		
	}

	public String printTipoAttivitaProblematica(CsRelRelazioneProbl item)
	{
//		final String RELAZIONE="Relazione";
//		final String COLLOQUIO="Colloquio";
		
		if(item!=null)
		{	
			CsDRelazione relazioneToTest = item.getCsDRelazione();
			
			CsDRelazione relazioneRif = item.getCsDRelazioneRif();
			if(relazioneRif!=null)
			{
				relazioneToTest = relazioneRif;
			}
			
			if(relazioneToTest!=null)
			{
				CsTbMicroAttivita micro= relazioneToTest.getMicroAttivita();
				if(micro!=null)
				{
					CsTbMacroAttivita macro = micro.getMacroAttivita();
					String macroDescr = (macro!=null) ? macro.getDescrizione(): "";
					return macroDescr +" "+ micro.getDescrizione();
					
//					if("1".equalsIgnoreCase(micro.getFlagTipoForm()))
//					{						
//					}					
//					else if("2".equalsIgnoreCase(micro.getFlagTipoForm()))
//					{						
//					}
				}
			}
		}
		
		return "--";		
		
	}
	
	public void rilevazioniProblematicheButtonValueChangeListener(ValueChangeEvent event)
	{
		String newValue= (String) event.getNewValue();
		
		if("1".equals(newValue))
		{
			//solo se non sono state già acquisite le problematiche precedenti allora ripete l'analisi
			if(relazioneDTO.getRelazione().getCsRelRelazioneProbl()==null || relazioneDTO.getRelazione().getCsRelRelazioneProbl().isEmpty())
			{
				List<CsRelRelazioneProbl> relProblToAdd = acquisizioneProblematichePrecedenti();		
				relazioneDTO.getRelazione().setCsRelRelazioneProbl(relProblToAdd);
				addInfo("analisi problematiche effettuata", "acquisizione problematiche da attività precedenti..." );
			}
		}
		else
		{
			List<CsRelRelazioneProbl> relProbl = relazioneDTO.getRelazione().getCsRelRelazioneProbl();
			if(relProbl!=null)
			{
				relProbl.clear();
				addInfo("analisi problematiche disattivata", "rimozione delle problematiche..." );
			}			
		}
	}

	private List<CsRelRelazioneProbl> acquisizioneProblematichePrecedenti() {
		//analisi problematiche
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(getIdCaso());			
		CsDRelazione relazioneConProblematichePrecedenti = diarioService.findLastRelazioneProblAperte(dto);
		if(relazioneConProblematichePrecedenti!=null && relazioneConProblematichePrecedenti.getCsRelRelazioneProbl()!=null)	
		{
			List<CsRelRelazioneProbl> relProblToAdd = new ArrayList<CsRelRelazioneProbl>();					
			for (CsRelRelazioneProbl relProbl : relazioneConProblematichePrecedenti.getCsRelRelazioneProbl()) 
			{
				if(Boolean.FALSE.equals(relProbl.getFlagRisolta()))
				{
					//copia della problematica non risolta precedente
					CsRelRelazioneProbl newRelProbl = new CsRelRelazioneProbl();
					newRelProbl.setId(new CsRelRelazioneProblPK(
							relazioneDTO.getRelazione().getDiarioId()!=null?relazioneDTO.getRelazione().getDiarioId():0
							, relProbl.getId().getProblId()));
					newRelProbl.setCsDRelazione(relazioneDTO.getRelazione());
					newRelProbl.setCsDRelazioneRif(relProbl.getCsDRelazione());
					newRelProbl.setCsTbProbl(relProbl.getCsTbProbl());
					newRelProbl.setFlagRisolta(Boolean.FALSE);						
					
					relProblToAdd.add(newRelProbl);
				}					
			}
			
			return relProblToAdd;
			
		}
		
		return null;
	}
	
	public void deleteProblematica(CsRelRelazioneProbl item)
	{
		System.out.println("deleteProblematica: "+item.getId().getRelazioneDiarioId()+", "+item.getId().getProblId());
		
		if(relazioneDTO.getRelazione().getCsRelRelazioneProbl()!=null && item!=null)
		{

			if(item.getCsDRelazioneRif()!=null)
			{
				addError("Eliminazione della problematica non possibile", "la problematica si riferisce ad una attività precedente e non può essere rimossa.");
			}
		
			if(relazioneDTO.getRelazione().getCsRelRelazioneProbl().remove(item))
			{
//				BaseDTO dto = new BaseDTO();
//				fillEnte(dto);
//				dto.setObj(item);
//				diarioService.deleteCsRelRelazioneProbl(dto);
				addInfo("Rimozione della problematica", "l'azione sarà effettiva al prossimo salvataggio");
			}
		}
		
	}
		
	public void aggiungiProblematicaButton() {
		
		List<CsRelRelazioneProbl> problematiche = relazioneDTO.getRelazione().getCsRelRelazioneProbl();
		if(problematiche==null)
		{
			problematiche = new ArrayList<CsRelRelazioneProbl>();
			relazioneDTO.getRelazione().setCsRelRelazioneProbl(problematiche);
		}
		
		CsTbProbl tbProbl = null;
		if(selectedProblematicaId!=null && !selectedProblematicaId.isEmpty())
		{
			BaseDTO dto = new  BaseDTO();
			fillEnte(dto);
			dto.setObj(Long.valueOf(selectedProblematicaId));
			tbProbl = configService.getProblById(dto);
		}
		
		if(tbProbl!=null)
		{
			CsRelRelazioneProbl relProbl = new CsRelRelazioneProbl();
			relProbl.setId(new CsRelRelazioneProblPK(
					relazioneDTO.getRelazione().getDiarioId()!=null?relazioneDTO.getRelazione().getDiarioId():0, tbProbl.getId()));
			relProbl.setCsDRelazioneRif(null);
			relProbl.setCsDRelazione(relazioneDTO.getRelazione());
			relProbl.setCsTbProbl(tbProbl);
			relProbl.setFlagRisolta(selectedProblematicaRisolta);					

			if(problematiche.contains(relProbl))
			{
				//problematica già associata all'attività
				addError("Tipo di problematica già associata all'attività", "Impossibile aggiungere la problematica '"+tbProbl.getDescrizione()+"' perchè già presente tra quelle associate alla attività corrente.");
			}
			else
			{				
				problematiche.add(relProbl);				
			}
		}
		
		selectedProblematicaId=null;
		selectedProblematicaRisolta=false;
	}
	
	public Converter getFlagRilevazioneProblematicheConverter()
	{
		return new Converter()
		{
	
			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {		
				if(value==null || value.isEmpty() || "NULL".equalsIgnoreCase(value)) 
					return null;
				try
				{
					Long id = Long.valueOf(value);				
					return id.toString();
				}
				catch (Exception ex)
				{
					return null;
				}
			}
	
			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) {				
				if(value==null || value.toString().isEmpty()) 
					return "NULL";
				
				else if(value instanceof String)
				{
					return value.toString();
				}
				throw new ConverterException("tipo oggetto non valido");
			}
			
		};		
	}

	public TipoInterventoManBean getTipoInterventoManBean() 
	{		
		return tipoInterventoManBean;
	}

	private void resetWizardStep()
	{
		final String STEP0="tab_attività";
		String wizard_id = ComponentUtils.findComponentClientId("wizard");
		if(wizard_id!=null)
		{
			Wizard wizard = (Wizard) FacesContext.getCurrentInstance().getViewRoot().findComponent(wizard_id);
			if(wizard!=null && !STEP0.equals(wizard.getStep()))
			{
				System.out.println("resetting Wizard server side from step: "+wizard.getStep());
			    wizard.setStep(STEP0);
			    RequestContext.getCurrentInstance().update("dlgRelazione");			
			}
		}
	}
	
	public String getHeaderAttivita()
	{
		if(relazioneDTO!=null && relazioneDTO.getRelazione().getMicroAttivita()!=null)
		{
			return "Attività tipo: '"+relazioneDTO.getRelazione().getMicroAttivita().getMacroAttivita().getDescrizione()
					+" "+relazioneDTO.getRelazione().getMicroAttivita().getDescrizione()+"'";
		}
		else
		{
			return "Scegli il tipo di attività";
		}
		
	}
	

	//INIZIO residuo-evoluzione-pai aggiunto parametro var al metodo listaRelazioniDTO per determinare se farsi restituire tutte le attività o solo quelle associate al pai
	public List<RelazioneDTO> listaRelazioniDTO(Object var) {
		if (var!=null && var.equals("propostaAttivita") ) { 
			FascicoloBean fbean = (FascicoloBean) this.getBean("fascicoloBean");
			return	fbean.getPaiBean().getTargetRelazioneDTO();
		} else { 
			return this.listaRelazioniDTO;
		} 
	}  
	//INIZIO residuo-evoluzione-pai
}
