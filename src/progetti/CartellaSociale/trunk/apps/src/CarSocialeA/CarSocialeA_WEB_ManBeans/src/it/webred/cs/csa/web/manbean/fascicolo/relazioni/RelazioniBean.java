package it.webred.cs.csa.web.manbean.fascicolo.relazioni;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.wizard.Wizard;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.util.ComponentUtils;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.DiarioAnagraficaDTO;
import it.webred.cs.csa.ejb.dto.InterventoDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.RelazioneDTO;
import it.webred.cs.csa.ejb.dto.cartella.RisorsaCalcDTO;
import it.webred.cs.csa.ejb.dto.relazione.RelazioneSintesiDTO;
import it.webred.cs.csa.ejb.dto.relazione.SaveRelazioneDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloBean;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompSecondoLivello;
import it.webred.cs.csa.web.manbean.fascicolo.interventiTreeView.TipoInterventoManBean;
import it.webred.cs.csa.web.manbean.fascicolo.pai.ProgettiIndividualiExtBean;
import it.webred.cs.csa.web.manbean.fascicolo.valMultidimensionale.ListaValMultidimensionaliBean;
import it.webred.cs.csa.web.manbean.report.ReportBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCCategoriaSociale;
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
import it.webred.cs.jsf.bean.DatiInterventoBean;
import it.webred.cs.jsf.interfaces.IRelazioni;
import it.webred.cs.jsf.manbean.DiarioDocsMan;
import it.webred.cs.jsf.manbean.SchedaPaiMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.valMultidimensionale.IValMultidimensionale;
import it.webred.ct.support.datarouter.CeTBaseObject;

public class RelazioniBean extends FascicoloCompSecondoLivello implements IRelazioni{

	@ManagedProperty(value = "#{progettiInvidualiExt}")
	private ProgettiIndividualiExtBean progettiIndividualiExtBean;
	
	private List<RelazioneDTO> listaRelazioniDTO;
	private int idxSelected;
	private RelazioneDTO relazioneDTO;
	private String modalHeader;
	private boolean loadRelazione;
	private boolean uploadDisabled;
	private DiarioDocsMan diarioDocsMan;
	private boolean removeFile=false;
	
	private ReportBean reportS;
	private List<SelectItem> listaTipiIntervento;
	private List<DatiInterventoBean> listaFogliAmmCollegati;
	
	private SchedaPaiMan managerPai;
	private boolean disableNuovoPAI;
	
	private List<SelectItem> catalogoAttivita;    
		
	private List<SelectItem> catalogoProblematiche;
	private List<SelectItem> listaRichiestaIndagine;
	
	private String selectedProblematicaId;
	private boolean selectedProblematicaRisolta;
	private boolean selectedProblematicaVerificata;
	
	private List<SelectItem> lstRiunioneCon;
	private List<CsCDiarioConchi> lstConChi;
	private List<String> lstConChiSel;
	
	private TipoInterventoManBean tipoInterventoManBean;
	
	private Date dtModifica;
	private String opModifica;
	
	private StreamedContent file; //triage pdf
	private TriageBean triageBean;
	
	//SISO-763
	private HashMap<Long, DiarioAnagraficaDTO> idAna2diarioAna = new HashMap<Long, DiarioAnagraficaDTO>();
	//private List<DiarioAnagraficaDTO> famigliaSelezionata;
	private List<RisorsaCalcDTO> anagrafiche;
	private Boolean relazioneCollegata = false;
	
	//fix caricamento relazione da pai
	private Long idRelazioneSelezionata;
	
	//SISO-1110
	protected boolean isTreeViewIntervento;

	private List<SelectItem> tipoInterventosAll = new LinkedList<SelectItem>();
	private List<SelectItem> tipoInterventosRecenti = new LinkedList<SelectItem>();
	private List<SelectItem> tipoInterventosCustom = new LinkedList<SelectItem>();
	private List<SelectItem> tipoInterventosInps = new LinkedList<SelectItem>();
	
	private boolean fromFascicoloCartellaUtente = true;

	private  List<String>  selectedRiunioneConChi = new ArrayList<String>();
	private List<CsOSettore> listaSettoriRiunione ;
	private List<String>  selectedRiunioneConChiToView = new ArrayList<String>();
	
	@Override
	public void initializeData() {
		try {
			lstConChiSel = new ArrayList<String>();
			selectedProblematicaId=null;
			selectedProblematicaRisolta=false;
			selectedProblematicaVerificata=false;
			loadRelazione = false;
			uploadDisabled= false;
//			managerPai = new SchedaPaiMan();
//			managerPai.setIdCaso(idCaso);
//			managerPai.setResponsabileCaso(getOperResponsabileCaso());
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(idCaso);
			if (fromFascicoloCartellaUtente) {

				listaRelazioniDTO = diarioService.findRelazioniByCaso(dto);
				String cf = csASoggetto != null && csASoggetto.getCsAAnagrafica() != null ? csASoggetto.getCsAAnagrafica().getCf() : null;
	          	if (cf != null) {
					dto.setObj(cf);
					listaRelazioniDTO.addAll(diarioService.findRelazioniPaiEsterniByCF(dto));
				} 
			} else {

				listaRelazioniDTO = this.listaRelazioniDTO("propostaAttivitaExt");
			}
			
			diarioDocsMan = new DiarioDocsMan();
			
			loadListaRichiestaIndagine();
			
			loadCatalogoMacroAttivita();
			
			loadCatalogoProblematiche();
			
			// TODO SISO-1280 utilizza il fascicolo
			tipoInterventoManBean = new TipoInterventoManBean(getListaTipiIntervento(), "");
			
			//SISO-763
			this.idAna2diarioAna = new HashMap<Long, DiarioAnagraficaDTO>();
			anagrafiche = new ArrayList<RisorsaCalcDTO>();

			if (fromFascicoloCartellaUtente) {
				BaseDTO b = new BaseDTO();
				fillEnte(b);
				b.setObj(csASoggetto.getCsAAnagrafica().getCf());
				anagrafiche = schedaService.findComponentiGiaFamigliariBySoggettoCf(b);

				for (RisorsaCalcDTO a : anagrafiche) {
					if (a.getCf() != null) {
						b.setObj(a.getCf());
						CsASoggettoLAZY soggetto = soggettoService.getSoggettoByCF(b);
						if (soggetto != null) {
							a.setAnagraficaId(soggetto.getAnagraficaId());
						}
					}
				}

				// carico le relazioni collegate
				b.setObj(csASoggetto.getCsAAnagrafica().getId());
				List<RelazioneDTO> relCollegate = diarioService.findRelazioniCollegate(b);
				listaRelazioniDTO.addAll(relCollegate);
			}
			
//			Leggo i settori per Riunione
			readSettoriPerRiunione();

			
			
		} 
		catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	private void loadCatalogoMacroAttivita(){
		catalogoAttivita = new ArrayList<SelectItem>();
		CeTBaseObject cet = new CeTBaseObject();
		fillEnte(cet);
		for (CsTbMacroAttivita macro : configService.getMacroAttivita(cet)) {
			/*BaseDTO dto1 = new BaseDTO();
			fillEnte(dto1);
			dto1.setObj(macro.getId());
			List<CsTbMicroAttivita> microl = configService.getMicroAttivitaByIdMacroAttivita(dto1);*/
			ArrayList<SelectItem> iteml = new ArrayList<SelectItem>();
			if (macro.getLstMicroAttivita() != null)
				for (CsTbMicroAttivita micro : macro.getLstMicroAttivita()) {
					SelectItem option = new SelectItem(micro,micro.getDescrizione(), micro.getTooltip());
					iteml.add(option);
				}

			SelectItemGroup group = new SelectItemGroup(macro.getDescrizione(), macro.getTooltip(), false,iteml.toArray(new SelectItem[iteml.size()]));
			catalogoAttivita.add(group);
		}
	}
	
	private void loadCatalogoProblematiche(){
		catalogoProblematiche = new ArrayList<SelectItem>();
		CeTBaseObject cet = new CeTBaseObject();
		fillEnte(cet);
		Map<String, Map<String, List<SelectItem>>> mapGroups = new HashMap<String, Map<String, List<SelectItem>>>();
		for (CsTbProbl problematica : configService.getProbl(cet)) {
			String flagMatImm = problematica.getFlagMatImm();
			Map<String, List<SelectItem>> mapGroups2 = mapGroups.get(flagMatImm);
			if (mapGroups2 == null) {
				mapGroups2 = new HashMap<String, List<SelectItem>>();
				mapGroups.put(flagMatImm, mapGroups2);
			}

			String tipo = problematica.getTipo();
			List<SelectItem> items = mapGroups2.get(tipo);
			if (items == null) {
				items = new ArrayList<SelectItem>();
				mapGroups2.put(tipo, items);
			}

			SelectItem option = new SelectItem(problematica.getId(),problematica.getDescrizione(), problematica.getTooltip());
			items.add(option);
		}
		
		
		for (String matImm : mapGroups.keySet()) {
			Map<String, List<SelectItem>> mapGroups2 = mapGroups.get(matImm);
			List<SelectItemGroup> tipoGroups = new ArrayList<SelectItemGroup>();
			for (String tipo : mapGroups2.keySet()) {
				List<SelectItem> probls = mapGroups2.get(tipo);
				SelectItemGroup tipoGroup = new SelectItemGroup(tipo, tipo, false, probls.toArray(new SelectItem[probls.size()]));
				tipoGroups.add(tipoGroup);
			}

			if ("M".equalsIgnoreCase(matImm)) {
				SelectItemGroup groupMat = new SelectItemGroup("Materiale","Problematiche di tipo materiale",false,tipoGroups.toArray(new SelectItem[tipoGroups.size()]));
				catalogoProblematiche.add(groupMat);
			} else if ("I".equalsIgnoreCase(matImm)) {
				SelectItemGroup groupImm = new SelectItemGroup("Immateriale","Problematiche di tipo immateriale",false, tipoGroups.toArray(new SelectItem[tipoGroups.size()]));
				catalogoProblematiche.add(groupImm);
			}

		}
	}
	
	public void initializeExternalPai() {
		try {
			lstConChiSel = new ArrayList<String>();
			selectedProblematicaId=null;
			selectedProblematicaRisolta=false;
			selectedProblematicaVerificata=false;
			loadRelazione = false;
			uploadDisabled= false;
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			// TODO da capire per pai esterni 
			listaRelazioniDTO = new ArrayList<RelazioneDTO>();
			
			diarioDocsMan = new DiarioDocsMan();
			
			loadListaRichiestaIndagine();
			
			loadCatalogoMacroAttivita();

			loadCatalogoProblematiche();
			
			//SISO-1280 non posso caricare i TIPI di intervento con Categoria sociale perchè quando avviamo il Progetto individuale non sappiamo 
			//a quale categoria associare il beneficiario
			
			loadTipoInterventi();
			tipoInterventoManBean = new TipoInterventoManBean(tipoInterventosAll, "");
			
			//SISO-763
			this.idAna2diarioAna = new HashMap<Long, DiarioAnagraficaDTO>();
			anagrafiche = new ArrayList<RisorsaCalcDTO>();

		} 
		catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
 
	
	private void readSettoriPerRiunione() {
	//CARICA La Lista Settori come partecipanti alla Riunione
		CeTBaseObject bo = new CeTBaseObject();
		fillEnte(bo);
		if(listaSettoriRiunione == null ) {
			
			listaSettoriRiunione = configService.getSettoreRiunione(bo);
		}
		 
	 
 }
	protected void loadTipoInterventi() {

		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		List<KeyValueDTO> lstTipoInterventi = interventoService.findAllTipiIntervento(dto);
		List<KeyValueDTO> lstTipoInterventiRecenti = interventoService.findTipiInterventoRecenti(dto);
		List<KeyValueDTO> lstTipoInterventiInps = interventoService.findTipiInterventoInps(dto);//SISO-1162
		List<KeyValueDTO> lstTipoInterventiCustom = interventoService.findTipiInterventoCustomRecenti(dto);
		this.tipoInterventosAll = new LinkedList<SelectItem>();
		this.tipoInterventosRecenti = new LinkedList<SelectItem>();
		this.tipoInterventosCustom = new LinkedList<SelectItem>();
		this.tipoInterventosInps = new LinkedList<SelectItem>(); //SISO-1162
		for (KeyValueDTO i : lstTipoInterventi)
			this.tipoInterventosAll.add(new SelectItem((Long)i.getCodice(), i.getDescrizione()));

		for (KeyValueDTO i : lstTipoInterventiRecenti)
			this.tipoInterventosRecenti.add(new SelectItem((Long)i.getCodice(), i.getDescrizione()));

		for (KeyValueDTO i : lstTipoInterventiCustom)
			this.tipoInterventosCustom.add(new SelectItem((Long)i.getCodice(), i.getDescrizione()));
      
		for (KeyValueDTO i : lstTipoInterventiInps)
           this.tipoInterventosInps.add(new SelectItem((String)i.getCodice(), (String)i.getCodice() + " - " + i.getDescrizione()));
		this.tipoInterventosInps.add(new SelectItem( "Non definito", "Non definito"));
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
					
		if (isTreeViewTipoIntervento())//SISO-1110
		    tipoInterventoManBean.reset();
		else
			tipoInterventoManBean.resetCustomIstat();
		
		resetWizardStep();
		selectedProblematicaId=null;
		selectedProblematicaRisolta=false;
		selectedProblematicaVerificata=false;
		//SISO-763
		//this.famigliaSelezionata = new ArrayList<DiarioAnagraficaDTO>();
		buildMap();
		relazioneCollegata = false;
		lstConChiSel = new ArrayList<String>();
		selectedRiunioneConChi= new ArrayList<String>();
		selectedRiunioneConChiToView = new ArrayList<String>();
	}
	
	@Override
	public void carica() {

		try {
			this.lstConChiSel = new ArrayList<String>();
			modalHeader = "Modifica attività";
			
			BaseDTO dtoL = new BaseDTO();
			fillEnte(dtoL);
			dtoL.setObj(idCaso);
			// TODO da capire per pai esterni 
			listaRelazioniDTO = new ArrayList<RelazioneDTO>();
		
			if (fromFascicoloCartellaUtente) {

				listaRelazioniDTO = diarioService.findRelazioniByCaso(dtoL);
				
				String cf = csASoggetto != null && csASoggetto.getCsAAnagrafica() != null ? csASoggetto.getCsAAnagrafica().getCf() : null;
	          	if (cf != null) {
	          		dtoL.setObj(cf);
					listaRelazioniDTO.addAll(diarioService.findRelazioniPaiEsterniByCF(dtoL));
				} 
			} else {

				listaRelazioniDTO = this.listaRelazioniDTO("propostaAttivitaExt");
			}
			
			
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
			
			
			
			
			
			if(rel.getLstConChi() != null && !rel.getLstConChi().isEmpty()){
				for(CsCDiarioConchi cs : rel.getLstConChi()){
					this.lstConChiSel.add(String.valueOf(cs.getId()));
				}
			}
			
			if(rel.getLstRiunioneConChi()!= null && !rel.getLstRiunioneConChi().isEmpty()) {
				this.selectedRiunioneConChi.clear();
				this.selectedRiunioneConChiToView.clear();
				for(CsOSettore cs : rel.getLstRiunioneConChi()){
					this.selectedRiunioneConChi.add(String.valueOf(cs.getId()));
					this.selectedRiunioneConChiToView.add(cs.getCsOOrganizzazione().getNome() + " - " + cs.getNome() );
				}
			}
			
			
			//Load full
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(rel.getDiarioId());
			relazioneDTO = diarioService.findRelazioneFullById(dto);//qui sono 12 elementi! lista con chi
			
			relazioneDTO.getRelazione().getCsDDiario().setVisSecondoLivello(null); //SISO-812
			
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
			List<CsAComponente> listaParenti = CsUiCompBaseBean.caricaParenti(idSoggetto, null);
			for(CsDDiario d : relazioneDTO.getRelazione().getCsDDiario().getCsDDiariPadre()){
				if(d.getCsTbTipoDiario().getId().equals(DataModelCostanti.TipoDiario.FOGLIO_AMM_ID)) {
					fillEnte(dto);
					dto.setObj(d.getId());
					CsFlgIntervento fgl = diarioService.findFglInterventoById(dto);
					DatiInterventoBean dib = new DatiInterventoBean(fgl.getCsIIntervento(), idSoggetto, listaParenti);
					listaFogliAmmCollegati.add(dib);
				}
			}
			
			if (tipoInterventoManBean != null) {
				if (isTreeViewTipoIntervento())// SISO-1110
					
					tipoInterventoManBean.reset();
				else 
					tipoInterventoManBean.resetCustomIstat();
			}
			
						
			resetWizardStep();
			selectedProblematicaId=null;
			selectedProblematicaRisolta=false;
			selectedProblematicaVerificata=false;
			
			//SISO-763
			dto = new BaseDTO();
			dto.setObj(rel.getCsDDiario().getId());
			fillEnte(dto);
			List<DiarioAnagraficaDTO> famigliaSelezionata = diarioService.findDiarioAnagraficaByDiarioId(dto);
			buildMap();
			
			//controllo se il diario selezionato appartiene al caso del soggetto loggato
			relazioneCollegata = true;

			dto.setObj(idCaso);
			//CsACaso caso = casoService.findCasoById(dto);
			List<RelazioneDTO> relazioniCaso = diarioService.findRelazioniByCaso(dto);
			for(RelazioneDTO d : relazioniCaso){
				if(d.getRelazione().getCsDDiario().getId() == relazioneDTO.getRelazione().getDiarioId().longValue()){
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

			RequestContext.getCurrentInstance().addCallbackParam("isShowDialog", true);
			
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
			RequestContext.getCurrentInstance().addCallbackParam("isShowDialog", false);
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
	protected void save() {
		
		if(!validaRelazione()) {
			return;
		}
		
		try{

			SaveRelazioneDTO dto = new SaveRelazioneDTO();
			fillEnte(dto);
						
			//se ho editato la scheda triage
			if(relazioneDTO.getRelazione().getMicroAttivita().getFlagTipoForm().equals("3")){
			   dto.setTriage(relazioneDTO.getTriage());	
			}
			//SISO 1257 - se ho editato la scheda 
			if(relazioneDTO.isAttivitaSAL()){
			   dto.setSal(relazioneDTO.getSal());	
			}			
			if (relazioneDTO.getRelazione().getDiarioId() != null) {

				if (removeFile || (loadRelazione && !uploadDisabled)) {
					// Ho rimosso il file presente o sono passata ad altra modalità di redazione
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
				
				dto.setRelazione(relazioneDTO.getRelazione());
				this.valorizzaSecondoLivello(relazioneDTO.getRelazione().getCsDDiario());
				
				diarioService.updateRelazione(dto);
			} else {

				CsOOperatoreSettore opSettore = getCurrentOpSettore();
				
				dto.setRelazione(relazioneDTO.getRelazione());
				
				FacesContext context = FacesContext.getCurrentInstance();
				progettiIndividualiExtBean = (ProgettiIndividualiExtBean) context.getApplication()
						.evaluateExpressionGet(context, "#{progettiInvidualiExt}", ProgettiIndividualiExtBean.class);
				
				Long idCasoCorrente = null;
				if (fromFascicoloCartellaUtente)
					idCasoCorrente = idCaso;
				else if (progettiIndividualiExtBean != null && progettiIndividualiExtBean.getPaiBean().getIdCasoSoggEsterno() != null)
					idCasoCorrente = progettiIndividualiExtBean.getPaiBean().getIdCasoSoggEsterno();
				
				dto.setCasoId(idCasoCorrente);
				dto.setOpSettore(opSettore);
		
		        this.valorizzaSecondoLivello(relazioneDTO.getRelazione().getCsDDiario());
		        
				relazioneDTO = diarioService.saveRelazione(dto);
				
				//Invio alert nuovo inserimento
				BaseDTO bdto = new BaseDTO();
				fillEnte(bdto);
				bdto.setObj2(opSettore);
				bdto.setObj3(DataModelCostanti.TipiAlertCod.RELAZIONE);
				bdto.setObj4("una nuova attività professionale");
				if (fromFascicoloCartellaUtente) {
					
					bdto.setObj(getCsASoggetto().getCsAAnagrafica().getCf());
					alertService.addAlertNuovoInserimentoToResponsabileCaso(bdto);
				} else {
					// Pai esterno con soggetto che possiede presente in fascicolo
					if (progettiIndividualiExtBean != null
							&& progettiIndividualiExtBean.getPaiBean().getIdCasoSoggEsterno() != null
							&& progettiIndividualiExtBean.getPaiBean().getSoggRiferimentoPai() != null
							&& progettiIndividualiExtBean.getPaiBean().getSoggRiferimentoPai().getCf() != null) {
						bdto.setObj(progettiIndividualiExtBean.getPaiBean().getSoggRiferimentoPai().getCf());
						alertService.addAlertNuovoInserimentoToResponsabileCaso(bdto);
					}
				}
			}
			
			// salvo il documento
			if (this.loadRelazione || relazioneDTO.isAttivitaSAL()){
				diarioDocsMan.getuFileMan().setIdDiario(relazioneDTO.getRelazione().getCsDDiario().getId());
				for(CsLoadDocumento loadDoc: diarioDocsMan.getuFileMan().getDocumentiUploaded())
					diarioDocsMan.getuFileMan().salvaDocumento(loadDoc);
			}
			
			addInfoFromProperties("salva.ok");
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);

			//inizio modifica evoluzione-pai
			RelazioneSintesiDTO sintesi = this.convertiSintesiDTO(relazioneDTO);
			if (fromFascicoloCartellaUtente) {
				FascicoloBean fbean = (FascicoloBean) getReferencedBean("fascicoloBean");
				if (fbean != null && fbean.getPaiBean()!=null) { 
					fbean.getPaiBean().refreshPicklistRelazioni(sintesi);
				}
			} else {
				if ( progettiIndividualiExtBean != null) {
					progettiIndividualiExtBean.getPaiBean().refreshPicklistRelazioni(sintesi);
				}
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
			BaseDTO bDto = new BaseDTO();
			bDto.setObj(famigliaSelezionata);
			bDto.setObj2(relazioneDTO.getRelazione().getDiarioId());
			fillEnte(bDto);
			famigliaSelezionata = diarioService.saveDiarioAnagrafica(bDto);
			
			initializeData();
			
		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
    private RelazioneSintesiDTO convertiSintesiDTO(RelazioneDTO in){
    	RelazioneSintesiDTO out = new RelazioneSintesiDTO();
    	out.setDiarioId(in.getRelazione().getDiarioId());
    	out.setDtAmministrativa(in.getRelazione().getCsDDiario().getDtAmministrativa());
    	out.setSituazioneAmbientale(in.getRelazione().getSituazioneAmb());
    	out.setSituazioneParentale(in.getRelazione().getSituazioneParentale());
    	out.setSituazioneSanitaria(in.getRelazione().getSituazioneSanitaria());
    	out.setProposta(in.getRelazione().getProposta());
    	out.setDescMacroAttivita(in.getRelazione().getMacroAttivita().getDescrizione());
    	out.setDescMicroAttivita(in.getRelazione().getMicroAttivita().getDescrizione());
    	out.setTipoFormMicroAttivita(in.getRelazione().getMicroAttivita().getFlagTipoForm());
    	out.setPaiCollegati(in.getListaIdsPaiCollegati());
    	return out;
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
		
		if(relazioneDTO.getRelazione().getFlagRilevazioneProblematiche()!=null && relazioneDTO.getRelazione().getFlagRilevazioneProblematiche())
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
	public List<SelectItem> getListaTipiIntervento() {

		if (listaTipiIntervento == null) {
			listaTipiIntervento = new ArrayList<SelectItem>();
			try {
				if (fromFascicoloCartellaUtente) {
					logger.debug("RelazioniBean - getListaTipiIntervento (fascicolo)");
				
					InterventoDTO dto = new InterventoDTO();
					fillEnte(dto);
					dto.setLstIdCatSoc(this.getLstIdCatSoc());
					CsOOperatoreSettore opSettore = getCurrentOpSettore();
					dto.setIdSettore(opSettore.getCsOSettore().getId());
					List<CsCTipoIntervento> lista = interventoService.findTipiInterventoSettoreCatSoc(dto);
					for (CsCTipoIntervento i : lista) {
						boolean abilitato = i.getAbilitato() != null && i.getAbilitato();
						SelectItem si = new SelectItem((Long) i.getId(), i.getDescrizione());
						si.setDisabled(!abilitato);
						listaTipiIntervento.add(si);
					}
				} else {
					logger.debug("RelazioniBean - getListaTipiIntervento (ext)");
					BaseDTO b = new BaseDTO();
					fillEnte(b);
					List<KeyValueDTO> lst = interventoService.findTipiInterventoAbilitati(b);
					listaTipiIntervento = this.convertiLista(lst);
				}
			} catch (Exception e) {
				addErrorFromProperties("caricamento.error");
				logger.error(e.getMessage(), e);
			}
		}

		return listaTipiIntervento;
	}
	
	public void loadListaTipiIntervento(String cf) {
		String filterCategorie = "";
		if (!StringUtils.isBlank(cf)) {
			
			BaseDTO sdto = new BaseDTO();
			fillEnte(sdto);
			sdto.setObj(cf);
			boolean existCarSoc = soggettoService.esisteSchedaSoggettoByCF(sdto);
			if (existCarSoc) {

				try {
					logger.debug("RelazioniBean - getListaTipiIntervento");
					listaTipiIntervento = new ArrayList<SelectItem>();

					List<CsCCategoriaSociale> lstCategorie = soggettoService.getCatSocAttualiByCF(sdto);
					filterCategorie = this.getFilterCategorie(lstCategorie);
					
					List<Long> lst = new ArrayList<Long>();
					if (lstCategorie != null) {
						for (CsCCategoriaSociale c : lstCategorie)
							lst.add(c.getId());
					}

					InterventoDTO dto = new InterventoDTO();
					fillEnte(dto);
					dto.setLstIdCatSoc(lst);
					CsOOperatoreSettore opSettore = getCurrentOpSettore();
					dto.setIdSettore(opSettore.getCsOSettore().getId());
					List<CsCTipoIntervento> lista = interventoService.findTipiInterventoSettoreCatSoc(dto);
					for (CsCTipoIntervento i : lista) {
						boolean abilitato = i.getAbilitato() != null && i.getAbilitato();
						SelectItem si = new SelectItem((Long) i.getId(), i.getDescrizione());
						si.setDisabled(!abilitato);
						listaTipiIntervento.add(si);
					}

					tipoInterventoManBean = new TipoInterventoManBean(listaTipiIntervento, filterCategorie);

				} catch (Exception e) {
					addErrorFromProperties("caricamento.error");
					logger.error(e.getMessage(), e);
				}
			}
		}

	}
	
	@Override
	public void aggiungiTipoInterventoButton() {
		
		try{
			Long selectedId = tipoInterventoManBean.getSelTipoInterventoId();

			if (selectedId != null) {
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(selectedId);
				CsCTipoIntervento tipoInt = interventoService.getTipoInterventoById(dto);
				if (tipoInt != null) {
					CsRelRelazioneTipoint relRelazioneTipoint = new CsRelRelazioneTipoint();
					relRelazioneTipoint.setId(new CsRelRelazioneTipointPK(relazioneDTO.getRelazione().getDiarioId() != null ? relazioneDTO.getRelazione().getDiarioId() : 0, tipoInt.getId()));
					relRelazioneTipoint.setCsDRelazione(relazioneDTO.getRelazione());
					relRelazioneTipoint.setCsCTipoIntervento(tipoInt);

					Long selectedCustomId = tipoInterventoManBean.getSelTipoInterventoCutomId();
					if (selectedCustomId != null) {
						dto.setObj(selectedCustomId);
						CsCTipoIntervento tipoIntCustom = interventoService.getTipoInterventoById(dto);
						if (tipoIntCustom != null)
							relRelazioneTipoint.setCsCTipoInterventoCustom(tipoIntCustom);
					}

					List<CsRelRelazioneTipoint> list_relRelazioneTipoint = relazioneDTO.getRelazione().getCsRelRelazioneTipoint();
					if (list_relRelazioneTipoint == null) {
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

		if (relazioneDTO.getRelazione().getCsRelRelazioneTipoint() != null && item != null) {
			if (relazioneDTO.getRelazione().getCsRelRelazioneTipoint().remove(item)) {
				// BaseDTO dto = new BaseDTO();
				// fillEnte(dto);
				// dto.setObj(item);
				// diarioService.deleteCsRelRelazioneProbl(dto);
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

		// Avvia l'esportazione della relazione selezionata
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

	public List<SelectItem> getCatalogoAttivita() {
		return catalogoAttivita;
	}

	public Converter getMicroAttivitaConverter(){
		return new Converter(){

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
				else if (value instanceof CsTbMicroAttivita) {
					return String.valueOf(((CsTbMicroAttivita) value).getId());
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
		readSettoriPerRiunione();
		if(this.lstRiunioneCon == null || this.lstRiunioneCon.size()<0)
			lstRiunioneCon = this.loadListaSettoriPartecipanti();
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
				boolean aggiungi = (riunioneCon && obj.getFlgRiunioneCon() != null);

				if (comuneValido && aggiungi) {
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

	private List<SelectItem> loadListaSettoriPartecipanti() {
		List<SelectItem> lista = new ArrayList<SelectItem>();
		
		if (listaSettoriRiunione != null) {
			for (CsOSettore obj : listaSettoriRiunione) {
			
					String nomeOrg = obj.getCsOOrganizzazione().getNome();
					SelectItem si = new SelectItem(obj.getId(), nomeOrg + " - " +   obj.getNome());
					si.setDisabled(!obj.getAbilitato());
				
					lista.add(si);
				}				
			}
		
		return lista;
	
	}
	
	public Converter getRiunioneConConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,UIComponent component, String value) {

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
				else if (value instanceof CsOSettore) {
					return String.valueOf(((CsOSettore) value).getId());
				}
				throw new ConverterException("tipo oggetto non valido");
			}
			
		};
		
	}
	/**
	 * @return the lstConChi
	 */
	public List<CsCDiarioConchi> getLstConChi() {
		if (this.lstConChi == null) {
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
	
	public void riunioneConChiSelezionato(){//AjaxBehaviorEvent event
		if(relazioneDTO.getRelazione().getLstRiunioneConChi()!=null) {
			relazioneDTO.getRelazione().getLstRiunioneConChi().clear();
			selectedRiunioneConChiToView.clear();
		}
		
		if(!selectedRiunioneConChi.isEmpty()) {
			for(int i=0; i < selectedRiunioneConChi.size(); i++){
				long l = Long.valueOf(selectedRiunioneConChi.get(i)).longValue();
				for(CsOSettore cs : listaSettoriRiunione){
					if(cs.getId() == l){
						relazioneDTO.getRelazione().getLstRiunioneConChi().add(cs);
						selectedRiunioneConChiToView.add(cs.getCsOOrganizzazione().getNome() + " - " + cs.getNome() );
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

	public Converter getConChiConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,UIComponent component, String value) {

				if(value==null || "NULL".equals(value)) return null;
				
				Long id = Long.valueOf(value);
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(id);
				return diarioService.getDiarioConchi(dto);
				
			}
	
			@Override
			public String getAsString(FacesContext context,UIComponent component, Object value) {
				if (value == null || "NULL".equals(value)) return "NULL";
				else if (value instanceof CsCDiarioConchi) 
					return String.valueOf(((CsCDiarioConchi) value).getId());
				
				throw new ConverterException("tipo oggetto non valido");
			}
			
		};		
	}

	public String printTipoAttivitaProblematica(CsRelRelazioneProbl item) {
		// final String RELAZIONE="Relazione";
		// final String COLLOQUIO="Colloquio";

		if (item != null) {
			CsDRelazione relazioneToTest = item.getCsDRelazione();

			CsDRelazione relazioneRif = item.getCsDRelazioneRif();
			if (relazioneRif != null) {
				relazioneToTest = relazioneRif;
			}

			if (relazioneToTest != null) {
				CsTbMicroAttivita micro = relazioneToTest.getMicroAttivita();
				if (micro != null) {
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

	public void rilevazioniProblematicheButtonValueChangeListener(ValueChangeEvent event) {
		Boolean newValue = (Boolean) event.getNewValue();

		if (newValue) {
			// solo se non sono state già acquisite le problematiche precedenti allora ripete l'analisi
			if (relazioneDTO.getRelazione().getCsRelRelazioneProbl() == null || relazioneDTO.getRelazione().getCsRelRelazioneProbl().isEmpty()) {
				List<CsRelRelazioneProbl> relProblToAdd = acquisizioneProblematichePrecedenti();
				relazioneDTO.getRelazione().setCsRelRelazioneProbl(relProblToAdd);
				addInfo("analisi problematiche effettuata", "acquisizione problematiche da attività precedenti..." );
			}
		} else {
			List<CsRelRelazioneProbl> relProbl = relazioneDTO.getRelazione().getCsRelRelazioneProbl();
			if (relProbl != null) {
				relProbl.clear();
				addInfo("analisi problematiche disattivata", "rimozione delle problematiche...");
			}
		}
	}

	private List<CsRelRelazioneProbl> acquisizioneProblematichePrecedenti() {
		//analisi problematiche
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(getIdCaso());
		CsDRelazione relazioneConProblematichePrecedenti = diarioService.findLastRelazioneProblAperte(dto);
		if (relazioneConProblematichePrecedenti != null && relazioneConProblematichePrecedenti.getCsRelRelazioneProbl() != null) {
			List<CsRelRelazioneProbl> relProblToAdd = new ArrayList<CsRelRelazioneProbl>();
			for (CsRelRelazioneProbl relProbl : relazioneConProblematichePrecedenti.getCsRelRelazioneProbl()) {
				if (Boolean.FALSE.equals(relProbl.getFlagRisolta())) {
					// copia della problematica non risolta precedente
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

	public void deleteProblematica(CsRelRelazioneProbl item) {
		logger.debug("deleteProblematica: "+item.getId().getRelazioneDiarioId()+", "+item.getId().getProblId());

		if (relazioneDTO.getRelazione().getCsRelRelazioneProbl() != null && item != null) {

			if (item.getCsDRelazioneRif() != null)
				addError("Eliminazione della problematica non possibile", "la problematica si riferisce ad una attività precedente e non può essere rimossa.");

			if (relazioneDTO.getRelazione().getCsRelRelazioneProbl().remove(item)) {
				// BaseDTO dto = new BaseDTO();
				// fillEnte(dto);
				// dto.setObj(item);
				// diarioService.deleteCsRelRelazioneProbl(dto);
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
		if (selectedProblematicaId != null && !selectedProblematicaId.isEmpty()) {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(Long.valueOf(selectedProblematicaId));
			tbProbl = configService.getProblById(dto);
		}

		if (tbProbl != null) {
			CsRelRelazioneProbl relProbl = new CsRelRelazioneProbl();
			relProbl.setId(new CsRelRelazioneProblPK(
					relazioneDTO.getRelazione().getDiarioId()!=null?relazioneDTO.getRelazione().getDiarioId():0, tbProbl.getId()));
			relProbl.setCsDRelazioneRif(null);
			relProbl.setCsDRelazione(relazioneDTO.getRelazione());
			relProbl.setCsTbProbl(tbProbl);
			relProbl.setFlagRisolta(selectedProblematicaRisolta);
			relProbl.setFlagVerificata(selectedProblematicaVerificata);

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

		selectedProblematicaId = null;
		selectedProblematicaRisolta = false;
		selectedProblematicaVerificata=false;
	}

	public Converter getFlagRilevazioneProblematicheConverter() {
		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String value) {
				if (value == null || value.isEmpty()|| "NULL".equalsIgnoreCase(value))
					return null;
				try {
					Long id = Long.valueOf(value);
					return id.toString();
				} catch (Exception ex) {
					return null;
				}
			}

			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {
				if (value == null || value.toString().isEmpty())
					return "NULL";

				else if (value instanceof String) {
					return value.toString();
				}
				throw new ConverterException("tipo oggetto non valido");
			}

		};
	}

	public TipoInterventoManBean getTipoInterventoManBean() {
		return tipoInterventoManBean;
	}

	private void resetWizardStep() {
		final String STEP0 = "tab_attività";
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
		List<RelazioneDTO> lstRelazDToToReturn = new ArrayList<RelazioneDTO>();
		if (var!=null && var.equals("propostaAttivita") ) { 
			FascicoloBean fbean = (FascicoloBean) getReferencedBean("fascicoloBean");
			lstRelazDToToReturn =	fbean.getPaiBean().getListaRelazioniDTO();
		} else if(var!=null && var.equals("propostaAttivitaExt")){;
			ProgettiIndividualiExtBean fbean = (ProgettiIndividualiExtBean) getReferencedBean("progettiInvidualiExt");
			lstRelazDToToReturn = fbean.getPaiBean().getListaRelazioniDTO();
		} else { 
			lstRelazDToToReturn =  this.listaRelazioniDTO;
		}
		return lstRelazDToToReturn;
	}

	// INIZIO residuo-evoluzione-pai

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
		// Avvio espostazione della scheda triage
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

	private void buildMap() {
		this.idAna2diarioAna.clear();
		if (anagrafiche != null) {
			for (RisorsaCalcDTO fam : anagrafiche) {

				// solo i familiari con CF valorizzato
				if (StringUtils.isBlank(fam.getCf())) {
					continue;
				}

				DiarioAnagraficaDTO da = new DiarioAnagraficaDTO();
				da.setAnagraficaId(fam.getAnagraficaId());
				da.setCf(fam.getCf());
				da.setCognome(fam.getCognome());
				da.setNome(fam.getNome());
				idAna2diarioAna.put(fam.getAnagraficaId(), da);
			}
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

	@Override
	public boolean isSelectedProblematicaVerificata() {
		return selectedProblematicaVerificata;
	}

	public void setSelectedProblematicaVerificata(
			boolean selectedProblematicaVerificata) {
		this.selectedProblematicaVerificata = selectedProblematicaVerificata;
	}
	//Inizio SISO-1110
	public boolean isTreeViewIntervento() {
	   return isTreeViewTipoIntervento();
	}

	public void setTreeViewIntervento(boolean isTreeViewIntervento) {
		this.isTreeViewIntervento = isTreeViewIntervento;
	}
	//Fine SISO-1110

	public boolean isFromFascicoloCartellaUtente() {
		return fromFascicoloCartellaUtente;
	}

	public void setFromFascicoloCartellaUtente(boolean fromFascicoloCartellaUtente) {
		this.fromFascicoloCartellaUtente = fromFascicoloCartellaUtente;
	}

	public ProgettiIndividualiExtBean getProgettiIndividualiExtBean() {
		return progettiIndividualiExtBean;
	}

	public void setProgettiIndividualiExtBean(ProgettiIndividualiExtBean progettiIndividualiExtBean) {
		this.progettiIndividualiExtBean = progettiIndividualiExtBean;
	}

	public void setLstRiunioneCon(List<SelectItem> lstRiunioneCon) {
		this.lstRiunioneCon = lstRiunioneCon;
	}

	public List<String> getSelectedRiunioneConChi() {
		return selectedRiunioneConChi;
	}

	public void setSelectedRiunioneConChi(List<String> selectedRiunioneConChi) {
		this.selectedRiunioneConChi = selectedRiunioneConChi;
	}

	public List<CsOSettore> getListaSettoriRiunione() {
		return listaSettoriRiunione;
	}

	public void setListaSettoriRiunione(List<CsOSettore> listaSettoriRiunione) {
		this.listaSettoriRiunione = listaSettoriRiunione;
	}

	public List<String> getSelectedRiunioneConChiToView() {
		return selectedRiunioneConChiToView;
	}

	public void setSelectedRiunioneConChiToView(List<String> selectedRiunioneConChiToView) {
		this.selectedRiunioneConChiToView = selectedRiunioneConChiToView;
	}

	


}
