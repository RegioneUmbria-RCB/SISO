package it.webred.cs.csa.web.manbean.fascicolo.relazioni;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.DiarioAnagraficaDTO;
import it.webred.cs.csa.ejb.dto.InterventoDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.RelazioneDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloBean;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompBaseBean;
import it.webred.cs.csa.web.manbean.fascicolo.interventiTreeView.TipoInterventoManBean;
import it.webred.cs.csa.web.manbean.fascicolo.valMultidimensionale.ListaValMultidimensionaliBean;
import it.webred.cs.csa.web.manbean.report.ReportBean;
import it.webred.cs.csa.web.manbean.scheda.parenti.ParentiBean;
import it.webred.cs.csa.web.manbean.scheda.parenti.ParentiComp;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsAFamigliaGruppo;
import it.webred.cs.data.model.CsASoggettoLAZY;
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
import it.webred.cs.data.model.CsTbTipoDiario;
import it.webred.cs.jsf.Costanti;
import it.webred.cs.jsf.bean.DatiInterventoBean;
import it.webred.cs.jsf.bean.ValiditaCompBaseBean;
import it.webred.cs.jsf.interfaces.IRelazioni;
import it.webred.cs.jsf.manbean.DiarioDocsMan;
import it.webred.cs.jsf.manbean.SchedaPaiMan;
import it.webred.cs.json.valMultidimensionale.IValMultidimensionale;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.utils.type.Constant;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.primefaces.component.selectcheckboxmenu.SelectCheckboxMenu;
import org.primefaces.component.wizard.Wizard;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.StreamedContent;
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
	private List<SelectItem> listaRichiestaIndagine;
	
	private String selectedProblematicaId;
	private boolean selectedProblematicaRisolta;
	
	private List<SelectItem> lstRiunioneCon;
	private List<CsCDiarioConchi> lstConChi;
	private List<String> lstConChiSel;
	
	private TipoInterventoManBean tipoInterventoManBean;
	
	private Date dtModifica;
	private String opModifica;
	
	private StreamedContent file; //triage pdf
	private TriageBean triageBean;
	
	//SISO-763
	private HashMap<Long, DiarioAnagraficaDTO> idAna2diarioAna;
	//private List<DiarioAnagraficaDTO> famigliaSelezionata;
	private List<CsAAnagrafica> anagrafiche;
	private Boolean relazioneCollegata = false;
	
	//fix caricamento relazione da pai
	private Long idRelazioneSelezionata;
	
	@Override
	public void initializeData() {
		
		try{
			lstConChiSel = new ArrayList<String>();
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
			
			loadListaRichiestaIndagine();
			
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
			
			//SISO-763
			this.idAna2diarioAna = new HashMap<Long, DiarioAnagraficaDTO>();
			anagrafiche = new ArrayList<CsAAnagrafica>();
			
			BaseDTO b = new BaseDTO();
			b.setObj(csASoggetto.getCsAAnagrafica().getCf());
			fillEnte(b);
//			
//			ParentiBean pbean = (ParentiBean) this.getBean("parentiBean");
//			if(pbean.getListaComponenti() == null){
//				pbean.initialize(idSoggetto);
//			}
//			ParentiComp pc = null;
//			for(ValiditaCompBaseBean vcbb : pbean.getListaComponenti()){
//				if(vcbb.getDataFine() == null || vcbb.getDataFine().equals(DataModelCostanti.END_DATE)){
//					pc = (ParentiComp) vcbb;
//					break;
//				}
//			}
//			
//			if(pc != null){
//				for(CsAComponente c : pc.getLstParenti()){
//					CsAAnagrafica ana = c.getCsAAnagrafica();
//					
//					//cambio id anagrafica prendendo quello del soggetto
//					if(c.getCsAFamigliaGruppo() != null && c.getCsAFamigliaGruppo().getCsASoggetto() != null){
//						ana.setId(c.getCsAFamigliaGruppo().getCsASoggetto().getAnagraficaId());
//					}
//					
//					anagrafiche.add(ana);
//				}
//			}
			
			anagrafiche = schedaService.findComponentiGiaFamigliariBySoggettoCf(b);
			
			for(CsAAnagrafica a : anagrafiche){
				if(a.getCf() != null){
					b.setObj(a.getCf());
					CsASoggettoLAZY soggetto =  soggettoService.getSoggettoByCF(b);
					if(soggetto != null){
						a.setId(soggetto.getAnagraficaId());
					}
				}
			}
						
			//carico le relazioni collegate
			b.setObj(csASoggetto.getCsAAnagrafica().getId());
			List<Long> casoIds = diarioService.findDiarioAnaAttProfessionaliCasoIdsByAnagraficaId(b);
			for(Long l : casoIds){
				b.setObj(l);
				listaRelazioniDTO.addAll(diarioService.findRelazioniByCaso(b));
			}
		} 
		catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	private void loadListaRichiestaIndagine(){
		listaRichiestaIndagine = new ArrayList<SelectItem>();
        SelectItem no = new SelectItem("No");
        SelectItem si1 = new SelectItem("Si, prima attività indagine");
        SelectItem si2 = new SelectItem("Si, aggiornamento indagine");   
       
        listaRichiestaIndagine.add(no);
        listaRichiestaIndagine.add(si1);
        listaRichiestaIndagine.add(si2);
	}
	
	@Override
	protected void initializeData(Object data) {
		this.initializeData();
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
		
		//SISO-763
		//this.famigliaSelezionata = new ArrayList<DiarioAnagraficaDTO>();
		buildMap();
		relazioneCollegata = false;
		lstConChiSel = new ArrayList<String>();
	}
	
	@Override
	public void carica() {
						
		try 
		{			
			this.lstConChiSel = new ArrayList<String>();
			modalHeader = "Modifica attività";
			
			CsDRelazione rel = null;
			if(this.idRelazioneSelezionata != null){
				for(RelazioneDTO reldto : listaRelazioniDTO){
					if(reldto.getRelazione().getDiarioId().equals(idRelazioneSelezionata)){
						rel = reldto.getRelazione();
						break;
					}
				}
				//reset
				idRelazioneSelezionata = null;
			}
			else{
				rel = listaRelazioniDTO.get(idxSelected).getRelazione();
			}
			
			
			if(!rel.getLstConChi().isEmpty()){
				for(CsCDiarioConchi cs : rel.getLstConChi()){
					this.lstConChiSel.add(String.valueOf(cs.getId()));
				}
			}
			//Load full
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(rel.getDiarioId());
			relazioneDTO = diarioService.findRelazioneFullById(dto);//qui sono 12 elementi! lista con chi
			
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
			
			//SISO-763
			dto = new BaseDTO();
			dto.setObj(rel.getCsDDiario().getId());
			fillEnte(dto);
			List<DiarioAnagraficaDTO> famigliaSelezionata = diarioService.findDiarioAnagraficaByDiarioId(dto);
			buildMap();
			
			//controllo se il diario selezionato appartiene al caso del soggetto loggato
			relazioneCollegata = true;
			
			IterDTO idto = new IterDTO();
			idto.setIdCaso(idCaso);
			fillEnte(idto);
			CsACaso caso = casoService.findCasoById(idto);
			for(CsDDiario d : caso.getCsDDiarios()){
				if(d.getId() == relazioneDTO.getRelazione().getDiarioId().longValue()){
					relazioneCollegata = false;
				}
			}
			
			for(DiarioAnagraficaDTO da : famigliaSelezionata){
				
				if(idAna2diarioAna.containsKey(da.getAnagraficaId())){
					DiarioAnagraficaDTO tmp = idAna2diarioAna.get(da.getAnagraficaId());
					tmp.setNote(da.getNote());
					tmp.setId(da.getId());
					tmp.setUserIns(da.getUserIns());
					tmp.setDataIns(da.getDataIns());
					tmp.setAnagraficaId(da.getAnagraficaId());
					tmp.setSelezionato(true);
				}
			}

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
			
			
			//se ho editato la scheda triage
			if(relazioneDTO.getRelazione().getMicroAttivita().getFlagTipoForm().equals("3")){
			   dto.setObj2(relazioneDTO.getTriage());	
			}
						
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
				CsOOperatoreSettore opSettore = getCurrentOpSettore();
				
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
			
			//SISO-763
			//update diarioID
			List<DiarioAnagraficaDTO> famigliaSelezionata = new ArrayList<DiarioAnagraficaDTO>();
			for(DiarioAnagraficaDTO da : getFamiglia()){
				if(da.getSelezionato()){
					da.setDiarioId(relazioneDTO.getRelazione().getDiarioId());
					famigliaSelezionata.add(da);
				}
			}
			dto = new BaseDTO();
			dto.setObj(famigliaSelezionata);
			dto.setObj2(relazioneDTO.getRelazione().getDiarioId());
			fillEnte(dto);
			famigliaSelezionata = diarioService.saveDiarioAnagrafica(dto);
			
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
				logger.debug("RelazioniBean - getListaTipiIntervento");
				listaTipiIntervento = new ArrayList<SelectItem>();
				InterventoDTO dto = new InterventoDTO();
				fillEnte(dto);
				dto.setLstIdCatSoc(this.getLstIdCatSoc());
		        CsOOperatoreSettore opSettore = getCurrentOpSettore();
				dto.setIdSettore(opSettore.getCsOSettore().getId());
				List<CsCTipoIntervento> lista = interventoService.findTipiInterventoSettoreCatSoc(dto);
				for(CsCTipoIntervento i: lista){ 
					boolean abilitato = i.getAbilitato()!=null && "1".equalsIgnoreCase(i.getAbilitato());
					SelectItem si = new SelectItem((Long)i.getId(), i.getDescrizione());
					si.setDisabled(!abilitato);
					listaTipiIntervento.add(si);
				}
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
				dto.setObj(selectedId);
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
						dto.setObj(selectedCustomId);
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
		//ReportBean per la creazione del report
		reportS = new ReportBean();
		//Oggetto relativo alla relazione selezionata per la stampa 
		relazioneDTO = listaRelazioniDTO.get(idxSelected);
		
		List<CsCTipoIntervento> listaTipiInterventoAttivi = new ArrayList<CsCTipoIntervento>(); 
		listaTipiInterventoAttivi.addAll(relazioneDTO.getRelazione().getCsCTipoInterventos());
		
		//Avvia l'esportazione della relazione selezionata 
		reportS.esportaRelazione(relazioneDTO.getRelazione(),listaTipiInterventoAttivi, this.getLstIdCatSoc());
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
			//relazioneDTO.getRelazione().setFontiUtilizzate(null);
			relazioneDTO.getRelazione().setSituazioneAmb(null);
			relazioneDTO.getRelazione().setSituazioneParentale(null);
			relazioneDTO.getRelazione().setSituazioneSanitaria(null);
			relazioneDTO.getRelazione().setProposta(null);
			relazioneDTO.getRelazione().setOrganizzazioneServizio(null);
			relazioneDTO.getRelazione().setLstConChi(new ArrayList<CsCDiarioConchi>());
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
		
		boolean procedi = true;
		if ("tab_attività".equals(fromStep) && "tab_problematiche".equals(toStep))
		{		
			if(relazioneDTO.getRelazione().getMicroAttivita()==null)
			{
				addError("E' necessaria la scelta del tipo di attività.", "Dopo aver selezionato il tipo di micro attività cliccare sul pulsante Set.");
				procedi = false;
			}else{
				
				if(!loadRelazione && relazioneDTO.getMicroAttivita().getMacroAttivita().getFlagConChi()
					&& relazioneDTO.getRelazione().getLstConChi().isEmpty()){
					addError("Specificare il valore del campo 'con Chi'","");
					procedi = false;
				}
			}
		}
		
		if(!procedi) return fromStep;
		
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
	public List<CsCDiarioConchi> getLstConChi() {
		if(this.lstConChi == null)
		{
			lstConChi = new ArrayList<CsCDiarioConchi>();
			CsCDiarioConchi altro = null;
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			List<CsCDiarioConchi> diarioConchis = configService.getDiarioConchis(dto);
			for (CsCDiarioConchi cs : diarioConchis) 
			{
				//SelectItem item = new SelectItem(csCDiarioConchi, csCDiarioConchi.getDescrizione(), csCDiarioConchi.getTooltip());
				if("Altri".equalsIgnoreCase(cs.getDescrizione()))
					altro = cs;
				else
					lstConChi.add(cs);
				
			}
			if(altro!=null) this.lstConChi.add(altro);
		}		
		
		return lstConChi;
	}

	/**
	 * @param lstConChi the lstConChi to set
	 */
	public void setLstConChi(List<CsCDiarioConchi> lstConChi) {
		this.lstConChi = lstConChi;
	}
	
	public void conChiSelezionato(){//AjaxBehaviorEvent event
		if(relazioneDTO.getRelazione().getLstConChi()!=null)
			relazioneDTO.getRelazione().getLstConChi().clear();
		
		if(!lstConChiSel.isEmpty()) {
			for(int i=0; i < lstConChiSel.size(); i++){
				long l = Long.valueOf(lstConChiSel.get(i)).longValue();
				for(CsCDiarioConchi cs : lstConChi){
					if(cs.getId() == l){
						relazioneDTO.getRelazione().getLstConChi().add(cs);
					}
				}
					
			}
		}
	}
	
	public List<String> getLstConChiSel() {
		return lstConChiSel;
	}

	public void setLstConChiSel(List<String> lstConChiSel) {
		this.lstConChiSel = lstConChiSel;
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
		logger.debug("deleteProblematica: "+item.getId().getRelazioneDiarioId()+", "+item.getId().getProblId());
		
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
				logger.debug("Relazioni Bean - resetting Wizard server side from step: "+wizard.getStep());
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
	
	public Date getDtModifica() {
		return dtModifica;
	}

	public String getOpModifica() {
		return opModifica;
	}

	/**
	 * residuo-evoluzione-pai aggiunto parametro var al metodo listaRelazioniDTO
	 * per determinare se farsi restituire tutte le attività
	 *  o solo quelle associate al pai
	 * @param var
	 * @return
	 */
	public List<RelazioneDTO> listaRelazioniDTO(Object var) {
		if (var!=null && var.equals("propostaAttivita") ) { 
			FascicoloBean fbean = (FascicoloBean) getBean("fascicoloBean");
			return	fbean.getPaiBean().getTargetRelazioneDTO();
		} else { 
			return this.listaRelazioniDTO;
		} 
	}  
	//INIZIO residuo-evoluzione-pai

	@Override
	public void stampaTriage() {
		relazioneDTO = listaRelazioniDTO.get(idxSelected);
		//if csVlautzione - da diario - è null non va stampata la scheda multidim, ma solo nelle tab
		IValMultidimensionale msmab;
		try {
			FascicoloBean fascicolo = (FascicoloBean)getBeanReference("fascicoloBean");
			if(fascicolo.getListaValMultidimensionaliBean()==null) 
				fascicolo.initializeValMultidimensionaleTab(getCsASoggetto());
			ListaValMultidimensionaliBean lst = fascicolo.getListaValMultidimensionaliBean();
		    lst.initialize(getCsASoggetto(), null, null);
		    msmab = lst.getlastValMultidimensionale();
		} catch (Exception e) {
			msmab=null;
		}
		
        reportS = new ReportBean();
		//Avvio espostazione della scheda triage
        reportS.esportaTriage(relazioneDTO, msmab, triageBean);
	}

	public StreamedContent getFile() {
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public TriageBean getTriageBean() {
		if(triageBean == null) {
			triageBean = new TriageBean();
		}
		return triageBean;
	}

	public void setTriageBean(TriageBean triageBean) {
		this.triageBean = triageBean;
	}
	
	private void buildMap(){
		this.idAna2diarioAna.clear();
		for (CsAAnagrafica fam : anagrafiche) {
			
			//solo i familiari con CF valorizzato
			if(fam.getCf() == null || fam.getCf().trim().isEmpty()){
				continue;
			}
						
			DiarioAnagraficaDTO da = new DiarioAnagraficaDTO();
			da.setAnagraficaId(fam.getId());
			da.setCf(fam.getCf());
			da.setCognome(fam.getCognome());
			da.setNome(fam.getNome());
			idAna2diarioAna.put(fam.getId(), da);
		}
	}

	public HashMap<Long, DiarioAnagraficaDTO> getIdAna2diarioAna() {
		return idAna2diarioAna;
	}

	public void setIdAna2diarioAna(
			HashMap<Long, DiarioAnagraficaDTO> idAna2diarioAna) {
		this.idAna2diarioAna = idAna2diarioAna;
	}
	
	public List<DiarioAnagraficaDTO> getFamiglia(){
		return new ArrayList<DiarioAnagraficaDTO>(idAna2diarioAna.values());
	}

	public Boolean getRelazioneCollegata() {
		return relazioneCollegata;
	}

	public void setRelazioneCollegata(Boolean relazioneCollegata) {
		this.relazioneCollegata = relazioneCollegata;
	}

	public List<SelectItem> getListaRichiestaIndagine() {
		return listaRichiestaIndagine;
	}

	public void setListaRichiestaIndagine(List<SelectItem> listaRichiestaIndagine) {
		this.listaRichiestaIndagine = listaRichiestaIndagine;
	}

	public Long getIdRelazioneSelezionata() {
		return idRelazioneSelezionata;
	}

	public void setIdRelazioneSelezionata(Long idRelazioneSelezionata) {
		this.idRelazioneSelezionata = idRelazioneSelezionata;
	}
	
}
