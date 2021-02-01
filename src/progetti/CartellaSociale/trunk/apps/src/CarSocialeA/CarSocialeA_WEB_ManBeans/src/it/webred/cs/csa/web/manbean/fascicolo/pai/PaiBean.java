package it.webred.cs.csa.web.manbean.fascicolo.pai;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.RelazioneDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneDettaglioDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneMasterDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloBean;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompSecondoLivello;
import it.webred.cs.csa.web.manbean.fascicolo.FglInterventoBean;
import it.webred.cs.csa.web.manbean.fascicolo.erogazioniInterventi.ErogInterventoRowBean;
import it.webred.cs.csa.web.manbean.fascicolo.interventiTreeView.TipoInterventoManBean;
import it.webred.cs.csa.web.manbean.scheda.anagrafica.AnagraficaBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.Pai.PERIODO_TEMPORALE;
import it.webred.cs.data.model.ArFfProgetto;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCCategoriaSocialeBASIC;
import it.webred.cs.data.model.CsDPai;
import it.webred.cs.data.model.CsFlgIntervento;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsTbMotivoChiusuraPai;
import it.webred.cs.data.model.CsTbProgettoAltro;
import it.webred.cs.data.model.CsTbTipoPai;
import it.webred.cs.jsf.bean.DatiInterventoBean;
import it.webred.cs.jsf.interfaces.IPai;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DualListModel;

public class PaiBean extends FascicoloCompSecondoLivello implements IPai, Serializable {

	private static final long serialVersionUID = 6070604949953337370L;

	private static final String MESSAGES_ID = "messagesPai";

	private List<CsDPai> pais;
	private List<CsDPai> filteredPais;
	private List<SelectItem> lstTipoPai;
	private List<SelectItem> lstMotivoChiusuraByTipoPai;
	private List<SelectItem> lstCittadinanzaPai;
	private int idxSelected;
	private CsDPai selectedPai;
	private String modalHeader;
	private String widgetVar;
	private DualListModel<RelazioneDTO> picklistRelazioni;
	private DualListModel<DatiInterventoBean> picklistInterventi;
	
	private CsASoggettoLAZY soggetto;
	
	//SISO-748
	private DualListModel<ErogInterventoRowBean> picklistErogazioni;
	private List<ErogInterventoRowBean> listaErogazioniByCaso;
	private boolean onUpdateErogazioni=false;
	
	private boolean onUpdateRelazioni=false;
	private boolean onUpdateInterventi=false;
	private boolean onClosing=false;
	private boolean validaChiusura;
	
	private List<String> erogazioniNuoveAssociate;
	
	private List<SelectItem> lstRadioOptions;
	private List<SelectItem> lstArFfProgetti;

	//SISO-1131
	private List<SelectItem> listaProgettiAltro;
	private CsTbProgettoAltro selectedProgettoAltro;
	private boolean abilitaMenuProgettiAltro = false;
	
	//inizio evoluzione-pai
	private TipoInterventoManBean tipoInterventoManBean;

	//fine evoluzione-pai
	
	//SISO-155
	private List<CsTbTipoPai> progettiPai;
	private String tipoProgetto = null;
	private PaiAffidoBean paiAffidoBean = new PaiAffidoBean();
	
	private int tabViewIndex = 0;
	
	//SISO-520
	private Date dataNuovoMonitoraggio;
	
    //SISO 724
	public void onChangeTabView(TabChangeEvent tce){
	   try{
		    FacesContext context = FacesContext.getCurrentInstance();
		    Map<String,String> params = context.getExternalContext().getRequestParameterMap();
		    TabView tabView = (TabView) tce.getComponent();
		    String activeIndexValue = params.get(tabView.getClientId(context) + "_tabindex");
		
		    this.tabViewIndex = Integer.parseInt(activeIndexValue);
		}catch(Exception ex){
			this.tabViewIndex = 0;
		}
	}
	
	//SISO-155
	public void onChangeTipoProgetto(AjaxBehaviorEvent event){
			
		SelectOneMenu som = (SelectOneMenu) event.getSource();
       
		long tipoProgettoId = (Long) ((javax.faces.component.UIInput)event.getComponent()).getValue();
		
			tipoProgetto = null;
			
			for(CsTbTipoPai tp : progettiPai){
				if(tp.getId() == tipoProgettoId){
					tipoProgetto = tp.getProgetto();
					break;
				}
			}
			if (tipoProgetto.equals(PaiProgettiEnum.AFFIDO.getProgetto())) {
				paiAffidoBean.nuovo(idSoggetto);
			}
		}
//	}
	
	
	@Override
	public void initializeData() {
		try {
			tabViewIndex=0;
			onUpdateRelazioni=false;
			onUpdateInterventi=false;
			picklistRelazioni=null;
			picklistInterventi=null;
			onClosing=false;
			
			//SISO-748
			picklistErogazioni=null;
			setOnUpdateErogazioni(false);
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(idCaso);
			pais = diarioService.findPaiByCaso(dto);
			
			filteredPais = pais;
			selectedPai = null;

			//inizio evoluzione-pai  
			loadListaTipiIntervento();
			tipoInterventoManBean = new TipoInterventoManBean(listTipoInterventos, getFilterCategorie()); 
			//fine evoluzione-pai
			
			//SISO-155
			tipoProgetto = null;
			progettiPai = confService.findListaProgettiPai(dto);
//			ParentiBean pbean = (ParentiBean) this.getBean("parentiBean");
//			if(pbean.getLstComponentiGit() == null || pbean.getLstComponentiGit().isEmpty()){
//				pbean.initialize(idSoggetto);
//			}
//			//SISO-520
			this.dataNuovoMonitoraggio=null;
			
			this.erogazioniNuoveAssociate=new ArrayList<String>();
			
			//SISO-1131
			this.setSelectedProgettoAltro(new CsTbProgettoAltro());
			
			
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}
	
	@Override
	protected void initializeData(Object data) {
		this.initializeData();
	}


	@Override
	public void nuovo() {
		picklistRelazioni=null;
		picklistInterventi=null;
		onUpdateRelazioni=false;
		onUpdateInterventi=false;
		onClosing=false;
		modalHeader = "Nuovo progetto";
		selectedPai = new CsDPai(); 
		selectedPai.setTipoProgettoId(0L);
		
		//SISO-748
		picklistErogazioni=null;
		setOnUpdateErogazioni(false);
		
		//inizio evoluzione-pai   
		getPicklistRelazioni();
		getPicklistInterventi();
		getPicklistErogazioni(); //SISO-748
		//fine evoluzione-pai
		
		//SISO-155
		tipoProgetto = null;
		paiAffidoBean.nuovo(idSoggetto);
		
		//SISO_1034
		selectedPai.setMonitoraggioObiettivi(false);
	//	this.selectedPai.setMonitoraggioObiettivi(false);
		
		//SISO_1023
		if (selectedPai.getCittadinanza() == null || "".equals(selectedPai.getCittadinanza().trim()))
			if (this.cittadinanzaCaso(idSoggetto) != null) {
				selectedPai.setCittadinanza(this.cittadinanzaCaso(idSoggetto));
			}
		//SISO-1131
		this.setSelectedProgettoAltro(new CsTbProgettoAltro());
		
	}

	@Override
	public void carica() {
		//per la modifica
		try {			
			picklistRelazioni=null;
			picklistInterventi=null;
			onUpdateRelazioni=false;
			onUpdateInterventi=false;
			onClosing=false;
			selectedPai = pais.get(idxSelected);
			selectedPai.getCsDDiario().setVisSecondoLivello(null); //SISO-812
			this.lstMotivoChiusuraByTipoPai = null;
			
			//SISO-748
			picklistErogazioni=null;
			setOnUpdateErogazioni(false);

			//inizio evoluzione-pai   
			getPicklistRelazioni();
			getPicklistInterventi(); 
			getPicklistErogazioni(); //SISO-748
			
			this.dataNuovoMonitoraggio=null; //SISO-520
			
			//SISO-155
			tipoProgetto = selectedPai.getCsTbTipoPai().getProgetto();
			if(tipoProgetto != null && tipoProgetto.equals(PaiProgettiEnum.AFFIDO.getProgetto())){
				paiAffidoBean.findAffidoByPai(selectedPai.getDiarioId(), idSoggetto);
			}
			
			modalHeader = selectedPai.isClosed() ? "Progetto chiuso:" : "Modifica progetto:";
			modalHeader +="	["+selectedPai.getDiarioId()+"]";
			
						
			//SISO_1023
			if (selectedPai.getCittadinanza() == null || "".equals(selectedPai.getCittadinanza().trim()))
				if (this.cittadinanzaCaso(idSoggetto) != null) {
					selectedPai.setCittadinanza(this.cittadinanzaCaso(idSoggetto));
				}
			//SISO-1034: per gestire il monitoraggio degli obiettivi prima della modifica
			if(selectedPai.getMonitoraggioObiettivi() == null)
				selectedPai.setMonitoraggioObiettivi(false);
			
			//SISO-1131
			this.setSelectedProgettoAltro(this.selectedPai.getCsTbProgettoAltro()!=null ? this.selectedPai.getCsTbProgettoAltro(): new CsTbProgettoAltro());
			
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void caricaChiudi() {
		//chiudi();
		carica();
		modalHeader = "Chiusura progetto: ["+selectedPai.getDiarioId()+"]";
		onClosing=true;
	}
	
	
	public boolean isClosed(){
		boolean closed = false;
		if(this.onClosing)
			closed = this.selectedPai.isClosed() && this.validaChiusura;
		else
			closed = this.selectedPai.isClosed();
		return closed;
	}
	
	@Override
	public void chiudi() {
		if (!validaChiusuraPai()) {
			this.validaChiusura = false;
			return;
		}
		this.validaChiusura = true;
		salva();
	}

	@Override
	protected void save(){	
		if (!validaPai())
			return;

		//SISO-155
		if(tipoProgetto != null && tipoProgetto.equals(PaiProgettiEnum.AFFIDO.getProgetto())){
			String valida = paiAffidoBean.validaAffidoPai(idSoggetto,selectedPai.getCsDDiario().getDtAttivazioneDa());
			if(valida != null){
				addError("Errore", valida);
				return;
			}
		}
		
		////SISO-520
		//if(this.selectedPai.getDataMonitoraggio()==null){
			//addError("Errore Affido","Indicare la data ultimo monitoraggio.");
	//}

		try {
			//SISO-1131
			if(this.selectedProgettoAltro !=null){
				//SISO-1131 devo salvare nuovo progetto altro.
				this.selectedPai.setCsTbProgettoAltro(null);
				if (selectedProgettoAltro.getId() == 0 && selectedProgettoAltro.getDescrizione()!=null){
					//salvataggio
					BaseDTO dto = new BaseDTO();
				    fillEnte(dto);
					dto.setObj(this.selectedProgettoAltro);
					selectedProgettoAltro = interventoService.salvaProgettoAltro(dto);
				
				}
				if (selectedProgettoAltro.getId() > 0){
					this.selectedPai.setCsTbProgettoAltro(selectedProgettoAltro);
					}
			}
						
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			
			//SISO-520
			selectedPai.setDataMonitoraggio(this.dataNuovoMonitoraggio);

			if (selectedPai.getDiarioId() != null) {

				dto.setObj(selectedPai);
				
				if (this.selectedPai.getMonitoraggioObiettivi())
					this.pulisciObiettivi();
				this.valorizzaSecondoLivello(selectedPai.getCsDDiario());
				
				diarioService.updatePai(dto);
				
			} else {

				CsOOperatoreSettore opSettore = getCurrentOpSettore();
				
				dto.setObj(selectedPai);
				dto.setObj2(idCaso);
				dto.setObj3(opSettore);
				
				if (this.selectedPai.getMonitoraggioObiettivi())
					this.pulisciObiettivi();
				this.valorizzaSecondoLivello(selectedPai.getCsDDiario());
				
				selectedPai = diarioService.savePai(dto);
			}
			
			//SISO-155
			if(tipoProgetto != null && tipoProgetto.equals(PaiProgettiEnum.AFFIDO.getProgetto())){
				paiAffidoBean.salva(selectedPai.getDiarioId());
			}

			this.updateInterventi();
			this.updateRelazioni();
			this.updateErogazioni(); //SISO-748
			
			FascicoloBean fbean = (FascicoloBean) getReferencedBean("fascicoloBean");
			//inizio modifica evoluzione-pai
			if (fbean != null && fbean.getInterventiBean() != null) {
				fbean.getInterventiBean().refreshListaInterventi(null);
			}
			
			addInfoFromProperties("salva.ok");
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);

			initializeData();

		} catch (Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(), e);
		}
	}

	private boolean validaPai() {
		boolean ok = true;
		List<String> errors = this.valida();
		for (String err : errors) {
			ok = false;
			addError(err, "");
		}
		return ok;
	}

	private void pulisciObiettivi(){
		
		this.selectedPai.setVerificaOgni(BigDecimal.ZERO);
		this.selectedPai.setVerificaUnitaMisura("Giorni");
		this.selectedPai.setObiettiviBreve("");
		this.selectedPai.setObiettiviMedio("");
		this.selectedPai.setObiettiviLungo("");
		this.selectedPai.setDataMonitoraggio(null);
		this.selectedPai.setMotivazioniProgetto("");//SISO-1172
	}
	
	private boolean validaChiusuraPai() {
		boolean ok = true;
		List<String> errors = this.validaChiusura();
		for (String err : errors) {
			ok = false;
			addError(err, "");
		}
		return ok;
	}

	//SISO-520
    public void salvataggioConMonitoraggio(){
    	if(this.dataNuovoMonitoraggio == null){
    		addError("Errore PAI", "Indicare la data di nuovo monitoraggio");
    		}else{
    			salva();
    		}
    }
    
    public void salvataggioSenzaMonitoraggio(){
    	if(this.dataNuovoMonitoraggio != null)
    		addError("Errore PAI", "Impossibile inserire la data di nuovo monitoraggio. E' necessario monitorare prima gli obiettivi");
    	else
    		salva();
    }
	@Override
    public void salvaGestioneMonitoraggioObiettivi() {
    	
 	    if(this.selectedPai.getMonitoraggioObiettivi())
	    	this.salvataggioSenzaMonitoraggio();
	    else
	    	RequestContext.getCurrentInstance().execute("PF('confermaSalvataggioDettaglioPai').show();");    
	}
    //SISO-1023
    private String cittadinanzaCaso(Long idSoggetto){
	   	String cittadinanza =""; 
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(idSoggetto);
		
		soggetto = soggettoService.getSoggettoById(dto);
		CsAAnagrafica csAna = soggetto.getCsAAnagrafica();
		
		cittadinanza = csAna.getCittadinanza();
		
		return cittadinanza;
			
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

	@Override
	public void setModalHeader(String modalHeader) {
		this.modalHeader = modalHeader;
	}

	@Override
	public String getWidgetVar() {
		return widgetVar;
	}

	@Override
	public void setWidgetVar(String widgetVar) {
		this.widgetVar = widgetVar;
	}

	@Override
	public CsDPai getSelectedPai() {
		return selectedPai;
	}

	@Override
	public void setSelectedPai(CsDPai pai) {
		this.selectedPai = pai;
	}

	@Override
	public List<CsDPai> getPais() {
		return this.pais;
	}

	public void setPais(List<CsDPai> pais) {
		this.pais = pais;
	}

	@Override
	public List<CsDPai> getFilteredPais() {
		return this.filteredPais;
	}

	@Override
	public void setFilteredPais(List<CsDPai> filteredPais) {
		this.filteredPais = filteredPais;
	}

	private List<SelectItem> generateListTipoPai() {
		lstTipoPai = new ArrayList<SelectItem>();
		CeTBaseObject bo = new CeTBaseObject();
		fillEnte(bo);
		List<CsTbTipoPai> lst = configService.getTipoPai(bo);
		if (lst != null) {
			for (CsTbTipoPai obj : lst) {
				lstTipoPai.add(new SelectItem(obj.getId(),  obj.getDescrizione() , obj.getDescrizione()));
			}
		}

		return lstTipoPai;
	}
	
	@Override
	public List<SelectItem> getLstTipoPai() {
		if (lstTipoPai == null || lstTipoPai.isEmpty())
			lstTipoPai = generateListTipoPai();
		List<SelectItem> out = new ArrayList<SelectItem>();
		out.addAll(lstTipoPai);
		return out;
	}
	
	@Override
	public List<SelectItem> getLstTipoPaiFiltro() {
		if (lstTipoPai == null || lstTipoPai.isEmpty())
			lstTipoPai = generateListTipoPai();
		List<SelectItem> out = new ArrayList<SelectItem>();
		out.add(new SelectItem(null, ""));
		out.addAll(lstTipoPai);
		return out;
	}
	
	@Override
	public List<SelectItem> getLstMotivoChiusuraByTipoPai() {
		if (lstMotivoChiusuraByTipoPai == null || lstMotivoChiusuraByTipoPai.isEmpty()){
			if (this.selectedPai.getCsTbTipoPai().getId() != 0){
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(this.selectedPai.getCsTbTipoPai().getId());
				List<CsTbMotivoChiusuraPai> lst = configService.getLstMotivoChiusuraPai(dto);
				lstMotivoChiusuraByTipoPai = new ArrayList<SelectItem>();
				if (lst != null) {
					for (CsTbMotivoChiusuraPai obj : lst) {
						lstMotivoChiusuraByTipoPai.add(new SelectItem(obj.getId(),  obj.getDescrizione() ));
					}
				}
			}
		}	

			return lstMotivoChiusuraByTipoPai;
		}


	@Override
	public SelectItem[] getLstCittadinanza() {
		if (lstCittadinanzaPai == null || lstCittadinanzaPai.isEmpty()) {
			AnagraficaBean anag = new AnagraficaBean();
			lstCittadinanzaPai = anag.getLstCittadinanze();
		}
		return lstCittadinanzaPai.toArray(new SelectItem[lstCittadinanzaPai.size()] );
	}
	
	@Override
	public List<SelectItem> getStatusOptions() {
		List<SelectItem> options = new ArrayList<SelectItem>();

		options.add(new SelectItem("", ""));
		options.add(new SelectItem("true", "sì"));
		options.add(new SelectItem("false", "no"));

		return options;
	}

	@Override
	public int getIdxSelected() {
		return this.idxSelected;
	}

	@Override
	public void setIdxSelected(int idxSelected) {
		this.idxSelected = idxSelected;

	}
	



	private List<String> validaChiusura() {
		ArrayList<String> errors = new ArrayList<String>();

		if (StringUtils.isNotEmpty(this.selectedPai.getObiettiviBreve()) && this.selectedPai.getRaggiuntiBreve() == 0) {
			errors.add("Inserire il raggiungimento degli obiettivi a breve termine");
		}
		if (StringUtils.isNotEmpty(this.selectedPai.getObiettiviMedio()) && this.selectedPai.getRaggiuntiMedio() == 0) {
			errors.add("Inserire il raggiungimento degli obiettivi a medio termine");
		}
		if (StringUtils.isNotEmpty(this.selectedPai.getObiettiviLungo()) && this.selectedPai.getRaggiuntiLungo() == 0) {
			errors.add("Inserire il raggiungimento degli obiettivi a lungo termine");
		}
		//SISO-520
		if (this.selectedPai.getCsDDiario().getDtChiusuraDa() !=null){
			long diff=this.selectedPai.getCsDDiario().getDtChiusuraDa().getTime()-this.selectedPai.getCsDDiario().getDtAttivazioneDa().getTime();
			if(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)<7){
				errors.add("La differenza tra la data di chiusura e la data di attivazione deve essere maggiore o uguale a sette giorni.");
			}
		}

		if (StringUtils.isEmpty(this.selectedPai.getMotivoChiusura())) {
			errors.add("Indicare il motivo della chiusura");
		}
		else if(StringUtils.equalsIgnoreCase("Altro", this.selectedPai.getMotivoChiusura()) && StringUtils.isEmpty(this.selectedPai.getMotivoChiusuraSpec()) )
		{
			errors.add("Specificare la descrizione del motivo della chiusura (avendo indicato 'Altro')");
		}

		if (this.selectedPai.getCsDDiario().getDtChiusuraDa() == null || 
				this.selectedPai.getCsDDiario().getDtChiusuraDa().equals(DataModelCostanti.END_DATE)	) {
			errors.add("Indicare la data di chiusura");
		} 	
		
		
		
		//verificare se ci sono interventi associati ancora aperti e che la relativa data di chiuera sia compresa nella data del progetto
		if(this.picklistInterventi!=null && this.picklistInterventi.getTarget()!=null)
		{
			boolean aperti = false;
			Date maxDataChiusuraFA = new Date(Long.MIN_VALUE);

			for (DatiInterventoBean i: this.picklistInterventi.getTarget())
			{
				for (CsFlgIntervento f : i.getListaFogli())
				{
					if (f.getCsDDiario().getDtChiusuraDa() == null) {
						aperti = true;
					}	
					if (!aperti) 
					{
						if (f.getCsDDiario().getDtChiusuraDa().after(maxDataChiusuraFA)) {
							maxDataChiusuraFA = f.getCsDDiario().getDtChiusuraDa();
						}
					}
				}
			}
			if (aperti) {
				errors.add("Ci sono interventi associati ancora aperti: è necessario chiuderli ovvero scollegarli dal progetto per poter chiudere il PAI");
			}
			else if (maxDataChiusuraFA.after(this.selectedPai.getCsDDiario().getDtChiusuraDa())) {
				errors.add("Ci sono interventi associati con fogli amministrativi la cui data di chiusura è successiva a quella di chiusura del progetto");
			}					
		}
		
		//verificare per ogni mast se ci sono erogazioni ancora aperte e che la data di chiusura sia compresa nella data del progetto
		if(this.picklistErogazioni!=null && this.picklistErogazioni.getTarget()!=null)
		{
			boolean aperti = false;
			boolean dopoDataChiusura = false;
			
			for(ErogInterventoRowBean eirb:  picklistErogazioni.getTarget())
			{
				for(ErogazioneDettaglioDTO d : eirb.getLstInterventiEseguiti()){
					
					//si prendono in considerazione solo le erogazioni di tipo E
					if(!d.getStatoErogazione().getTipo().equalsIgnoreCase("E")){
						continue;
					}
					
					if(!d.getStatoErogazione().getFlagChiudi()){
					    aperti = true;
					    break;
					}
					
					Date dataCheck = d.getDataErogazioneA() == null ? d.getDataErogazione() : d.getDataErogazioneA();
					
					if(dataCheck.after(this.selectedPai.getCsDDiario().getDtChiusuraDa())){
						dopoDataChiusura = true;
						break;
					}
				}
				
				if(aperti || dopoDataChiusura){
					break;
				}
			}
			
			if (aperti) {
				errors.add("Ci sono erogazioni associati ancora aperti: è necessario chiuderli per poter chiudere il PAI");
			}
			else if (dopoDataChiusura) {
				errors.add("Ci sono erogazioni associati con fogli amministrativi la cui data di chiusura è successiva a quella di chiusura del progetto");
			}	
		}

		return errors;
	}

	public List<String> valida() {
		ArrayList<String> errors = new ArrayList<String>();

		if (this.selectedPai.getCsTbTipoPai() == null || this.selectedPai.getCsTbTipoPai().getId() == 0) {
			errors.add("Indicare il tipo di progetto");
		}

		if (this.selectedPai.getCittadinanza() == null || "".equals(selectedPai.getCittadinanza().trim())) {
			errors.add("Indicare la cittadinanza al tempo del PAI");
		}
		
		if (this.selectedPai.getCsDDiario().getDtAttivazioneDa() == null) {
			errors.add("Indicare la data di attivazione");
		}
//		if (this.selectedPai.getCsDDiario().getDtChiusuraDa() == null) {
//			errors.add("Indicare la data di chiusura anche se presunta");
//		}
		//SISO-1131
		//se ho selezionato il Progetto
        if (this.selectedPai.getTipoProgettoId()>0){		
        	String nomeProgettoSelezionato="";
			for(SelectItem itm : lstArFfProgetti){
				if(itm.getValue().equals(this.selectedPai.getTipoProgettoId())){
					nomeProgettoSelezionato = itm.getLabel();
						break;
				}
			
			}
			if(!nomeProgettoSelezionato.isEmpty() && nomeProgettoSelezionato.equalsIgnoreCase("ALTRO")){
				if(this.selectedProgettoAltro != null ){
					if(this.selectedProgettoAltro.getDescrizione()== null || this.selectedProgettoAltro.getDescrizione().isEmpty()){
						errors.add("Il campo Specificare è obbligatorio");
					}
					}else{
						errors.add("Il campo Specificare è obbligatorio");
					}
				
				}
				
		
		}
		
		//SISO-1034
		if (!this.selectedPai.getMonitoraggioObiettivi()){
			
			//SISO-1172
			if (StringUtils.isEmpty(this.selectedPai.getMotivazioniProgetto())) {
				errors.add("Inserire le motivazioni del progetto");
			}
			//FINE SISO-1172
			if (this.selectedPai.getVerificaOgni() == null || StringUtils.isEmpty(this.selectedPai.getVerificaUnitaMisura())) {
				errors.add("Indicare la frequenza di verifica");
			}
			if (StringUtils.isEmpty(this.selectedPai.getObiettiviBreve()) && StringUtils.isEmpty(this.selectedPai.getObiettiviMedio())
					&& StringUtils.isEmpty(this.selectedPai.getObiettiviLungo())) {
				errors.add("Inserire almeno un obiettivo");
			}
			
			//ISSUE-520 
			if(this.dataNuovoMonitoraggio !=null && !this.dataNuovoMonitoraggio.before((new Date()))){
				errors.add("La data ultimo monitoraggio non può essere superiore alla data odierna.");
			}
			//if(this.selectedPai.getDataMonitoraggio()==null){
				//errors.add("Indicare la data ultimo monitoraggio.");
			//}
			if (this.selectedPai.getCsDDiario().getDtChiusuraDa() !=null &&  
					this.selectedPai.getCsDDiario().getDtChiusuraDa().before(this.selectedPai.getCsDDiario().getDtAttivazioneDa()))
			{
				errors.add("La data di chiusura deve essere successiva alla data di attivazione.");
			}
		}
	
		if(this.pais.size() > 0)
		{
			// verificare esistenza di altri progetti dello stesso tipo nello stesso periodo			
			CsTbTipoPai tipo = this.selectedPai.getCsTbTipoPai();
			Date da = this.selectedPai.getCsDDiario().getDtAttivazioneDa();
			Date a = this.selectedPai.getCsDDiario().getDtChiusuraDa();
			for (CsDPai p: this.pais)
			{
				if(p.getDiarioId().equals(this.selectedPai.getDiarioId()))
				{
					continue;
				}
				else if(tipo.getId() != p.getCsTbTipoPai().getId())
				{
					continue;
				}
				else
				{
					try {
						if(VerificaSovrapposizioneRangeDate(da, a, p.getCsDDiario().getDtAttivazioneDa(), p.getCsDDiario().getDtChiusuraDa()))
						{
							errors.add("Esiste già un progetto dello stesso tipo nello stesso periodo (ID: "+p.getDiarioId()+").\n Nota: le data del progetto corrente si sovrappongono al periodo di apertura dell'altro progetto esistente");
						}
					} catch (Exception e) {						
						//errors.add(e.getLocalizedMessage());
						logger.error(e.getMessage(), e);
					}
//					if(between(a, p.getCsDDiario().getDtAttivazioneDa(), p.getCsDDiario().getDtChiusuraDa()) )
//					{
//						//ERROR: il progetto corrente finisce tra l'inizio e la fine di un altro progetto esistente
//						errors.add("Esiste già un progetto dello stesso tipo nello stesso periodo (ID: "+p.getDiarioId()+").\n Nota: la data di chiusura del progetto corrente si sovrappone al periodo di apertura dell'altro progetto esistente");
//					}
//					if(between(da, p.getCsDDiario().getDtAttivazioneDa(), p.getCsDDiario().getDtChiusuraDa()) )
//					{
//						//ERROR: il progetto corrente inizia tra l'inizio e la fine di un altro progetto esistente
//						errors.add("Esiste già un progetto dello stesso tipo nello stesso periodo (ID: "+p.getDiarioId()+").\n Nota: la data di attivazione del progetto corrente si sovrappone al periodo di apertura dell'altro progetto esistente");
//					}
				}
			}
		}
		
		//verificare coerenza delle date di tutte le entità (interventi, relazioni) collegate al progetto
		{
			Date da = this.selectedPai.getCsDDiario().getDtAttivazioneDa();
			Date a = this.selectedPai.getCsDDiario().getDtChiusuraDa();
			if(this.picklistInterventi!=null && this.picklistInterventi.getTarget()!=null)
			{
				for (DatiInterventoBean i: this.picklistInterventi.getTarget())
				{
					if(!between(i.getFineDa(), da, a) || 
						!between(i.getFineA(), da, a) ||
						!between(i.getInizioA(), da, a) ||
						!between(i.getInizioDa(), da, a) )
					{
						//ERROR: la durata del progetto corrente non comprende le date di inizio e fine di tutti gli interventi associati
						errors.add("Esiste un intervento associato al progetto (ID: "+i.getIdIntervento()+") le cui date di inizio e fine non sono comprese nella durata del progetto corrente.");
					}					
				}
			}
			if(this.picklistRelazioni!=null && this.picklistRelazioni.getTarget()!=null)
			{
				for (RelazioneDTO r: this.picklistRelazioni.getTarget())
				{
					Date dAmm = r.getRelazione().getCsDDiario().getDtAmministrativa();
					
					if (!between(dAmm, da, a) )
					{
						//ERROR: la durata del progetto corrente non comprende le date di tutte le relazioni associate
						errors.add("Esiste una relazione associata al progetto (ID: "+r.getRelazione().getDiarioId()+") la cui data non è compresa nella durata del progetto corrente.");
					}
				}
			}
			if(this.picklistErogazioni!=null && this.picklistErogazioni.getTarget()!=null)
			{
				//per ogni mast controllo le erogazioni associate
				for (ErogInterventoRowBean erb: this.picklistErogazioni.getTarget())
				{
					for(ErogazioneDettaglioDTO ed : erb.getLstInterventiEseguiti()){
						
						if (ed.getStatoErogazione().getTipo().equalsIgnoreCase("E") && !between(ed.getDataErogazione(), da, a) )
						{
							//ERROR: la durata del progetto corrente non comprende le date di tutte le relazioni associate
							errors.add("Esiste una erogazione associata al progetto (ID: "+ed.getIdInterventoEseg()+") la cui data non è compresa nella durata del progetto corrente.");
						}
					}
				}
			}
		}		
		
		if (this.selectedPai.getDiarioId() == null) { // nuovo progetto
		}
		
		return errors;
	}

	//////////////////////////////////////////////////////////////////////////
	public List<RelazioneDTO> getLstRelazioni()
	{
		return getLstRelazioni(false);
	}
	public List<RelazioneDTO> getLstRelazioni(boolean onlyAssociatedToPai) {
		if(selectedPai==null) return null;
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(idCaso);
		List<RelazioneDTO> lst = diarioService.findRelazioniByCaso(dto);
		
		if (onlyAssociatedToPai)
		{						
			for (Iterator<RelazioneDTO> it=lst.iterator(); it.hasNext(); )
			{
				RelazioneDTO r=it.next();
				if(! r.isRelatedToPai(selectedPai))
				{
					it.remove();
				}
			}
		}
		
//		if (onlyAssociabiliToPai)
//		{
//			for (Iterator<RelazioneDTO> it=lst.iterator(); it.hasNext(); )
//			{
//				RelazioneDTO r=it.next();
//				if(r.getListaPaiDTO()!=null && !r.getListaPaiDTO().isEmpty()
//						&& !r.isRelatedToPai(selectedPai))
//				{
//					it.remove();
//				}
//			}
//		}
					
		return lst;
	}
	
	public List<DatiInterventoBean> getLstInterventi(){
		return elaboraLstInterventi(loadLstInterventi(), false, true);
	}
	
	private List<CsIIntervento> loadLstInterventi(){
		List<CsIIntervento> lsti = new ArrayList<CsIIntervento>();
		try {
			if (idCaso != null && idCaso > 0) {
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(idCaso);
				lsti = interventoService.getListaInterventiByCaso(dto);
			}
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
		return lsti;
	}

	public List<DatiInterventoBean> elaboraLstInterventi(List<CsIIntervento> lsti, boolean onlyAssociatedToPai, boolean onlyAssociabiliToPai) {
		List<DatiInterventoBean> lst = new ArrayList<DatiInterventoBean>();
			logger.info("PaiBean - Elaborazione lista Interventi totale["+lsti.size()+"] onlyAssociatedToPai["+onlyAssociatedToPai+"], onlyAssociabiliToPai["+onlyAssociabiliToPai+"]");
			
			if (onlyAssociatedToPai)
			{									
				for (Iterator<CsIIntervento> it=lsti.iterator(); it.hasNext(); )
				{						
					CsIIntervento i=it.next();
					CsDPai pai = i.getCsDPai();			
					if(pai!=null && selectedPai!=null && pai.getDiarioId().equals( selectedPai.getDiarioId()))
					{
						DatiInterventoBean dib = new DatiInterventoBean(i, idSoggetto);
						lst.add(dib);
					}						
				}
			}
			
			if (onlyAssociabiliToPai)
			{
				for (Iterator<CsIIntervento> it=lsti.iterator(); it.hasNext(); )
				{						
					CsIIntervento i=it.next();
					CsDPai pai = i.getCsDPai();		
					if(!(pai!=null && selectedPai!=null && !pai.getDiarioId().equals( selectedPai.getDiarioId())))
					{
						DatiInterventoBean dib = new DatiInterventoBean(i, idSoggetto);
						lst.add(dib);
					}
				}
			}
			
		return lst;
	}
	
	private List<ErogInterventoRowBean> elaboraLstErogazioni(boolean onlyAssociatedToPai, boolean onlyAssociabiliToPai){
		
		List<ErogInterventoRowBean> toReturn = new LinkedList<ErogInterventoRowBean>();
		logger.info("PaiBean - Elaborazione lista Erogazioni totale["+listaErogazioniByCaso.size()+"] onlyAssociatedToPai["+onlyAssociatedToPai+"], onlyAssociabiliToPai["+onlyAssociabiliToPai+"]");
		
		if (onlyAssociatedToPai)
		{									
			for(ErogInterventoRowBean eirb : listaErogazioniByCaso){
				if((eirb.getDiarioPaiId()!=null && selectedPai!=null && eirb.getDiarioPaiId().equals(selectedPai.getDiarioId())))
					toReturn.add(eirb);				
			}
		}
		
		if (onlyAssociabiliToPai)
		{
			for(ErogInterventoRowBean eirb : listaErogazioniByCaso){
				if(!(eirb.getDiarioPaiId()!=null && selectedPai!=null && eirb.getDiarioPaiId().equals(selectedPai.getDiarioId())))
					toReturn.add(eirb);
			}
		}

			return toReturn;
	}
	
	
	//SISO-748
	public List<ErogInterventoRowBean> getLstErogazioni(){
		
		List<ErogInterventoRowBean> toReturn = new LinkedList<ErogInterventoRowBean>();
		CsOOperatoreSettore opSettore = CsUiCompBaseBean.getCurrentOpSettore();
		ErogazioniSearchCriteria bDto = new ErogazioniSearchCriteria();
		CsUiCompBaseBean.fillEnte(bDto);
		bDto.setSettoreId(opSettore.getCsOSettore().getId());
		bDto.setOrganizzazioneId(opSettore.getCsOSettore().getCsOOrganizzazione().getId());
		bDto.setFirst(0);
		bDto.setPageSize(0);
		bDto.setPermessoAutorizzativo(CsUiCompBaseBean.isPermessoAutorizzativo());
		bDto.setSearchErogatiNoIntervento(true); //Con questo parametro attivo non carica le erogazioni collegate alle richieste di intervento
		bDto.setSearchByCaso(true);
		bDto.setCasoId(idCaso);
		
		logger.debug("Caricamento erogazioni PAI settore["+bDto.getSettoreId()+"], "
				+ "organizzazione["+bDto.getOrganizzazioneId()+"], "
				+ "autorizzativo ["+bDto.isPermessoAutorizzativo()+"], "
				+ "erogatiNoIntervento ["+bDto.isSearchErogatiNoIntervento()+"], "
				+ "searchByCaso ["+bDto.isSearchByCaso()+"], "
				+ "idCaso ["+bDto.getCasoId()+"]");
		
		List<ErogazioneMasterDTO> lst = interventoService.searchListaErogInterventi(bDto);
		for(ErogazioneMasterDTO dae : lst){
			ErogInterventoRowBean row = new ErogInterventoRowBean(dae);
			toReturn.add(row);
		}
		
		return toReturn;
		
	}

	public DualListModel<RelazioneDTO> getPicklistRelazioni() {
		if(picklistRelazioni==null)
		{			 
			List<RelazioneDTO> target = getLstRelazioni(true); 
			List<RelazioneDTO> source = getLstRelazioni();
			if(source!=null) source.removeAll(target);
			picklistRelazioni = new DualListModel<RelazioneDTO>(source, target); 
		}
			
		
		return picklistRelazioni;
    }

	public void setPicklistRelazioni(DualListModel<RelazioneDTO> picklistRelazioni) {
		this.picklistRelazioni = picklistRelazioni;
	}


	//INIZIO residuo-evoluzione-pai 
	public List<RelazioneDTO> getTargetRelazioneDTO() {
		return picklistRelazioni.getTarget();
	}

	public List<DatiInterventoBean> getTargetDatiInterventoBean() {
		return picklistInterventi.getTarget();
	}
	
	//SISO-748
	public List<ErogInterventoRowBean> getTargetErogazione(){
		return picklistErogazioni.getTarget();
	}
	//FINEresiduo-evoluzione-pai 
	
	
	public DualListModel<DatiInterventoBean> getPicklistInterventi() {
		if(picklistInterventi==null)
		{	
			List<CsIIntervento> lstInterventi = loadLstInterventi();
			List<DatiInterventoBean> target = elaboraLstInterventi(lstInterventi, true, false);
			List<DatiInterventoBean> source = elaboraLstInterventi(lstInterventi, false, true);
			source.removeAll(target);
			picklistInterventi = new DualListModel<DatiInterventoBean>(source, target);
		}
			
		
		return picklistInterventi;
    }



	public void setPicklistInterventi(DualListModel<DatiInterventoBean> picklistInterventi) {
		this.picklistInterventi = picklistInterventi;
	}
	
	//SISO-748
	public DualListModel<ErogInterventoRowBean> getPicklistErogazioni() {
		if(picklistErogazioni == null){
			listaErogazioniByCaso = getLstErogazioni();
			List<ErogInterventoRowBean> source = elaboraLstErogazioni(false, true);
			List<ErogInterventoRowBean> target = elaboraLstErogazioni(true, false); 
			source.removeAll(target);
			picklistErogazioni = new DualListModel<ErogInterventoRowBean>(source,target);
		}
		return picklistErogazioni;
	}
	
	public Converter getPicklistRelazioniConverter()
	{
		return new Converter()
		{			
			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {			
				RelazioneDTO ret = null;
				
				if (submittedValue != null && !submittedValue.trim().equals("")) {
					try {
						BaseDTO dto = new BaseDTO();
						fillEnte(dto);
						dto.setObj(Long.valueOf(submittedValue));
						ret = diarioService.findRelazioneFullById(dto);	
					}
					catch (Exception e) {
						logger.error(e);
					}				
				}
				
				return ret;
			}

			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {
				if(value!=null && value instanceof RelazioneDTO)
					return ((RelazioneDTO)value).getRelazione().getDiarioId().toString();
				else
					return "";
			}			
		};
	}
	
	public Converter getPicklistInterventiConverter()
	{
		return new Converter()
		{			

			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {			
				DatiInterventoBean ret = null;
				
				if (submittedValue != null && !submittedValue.trim().equals("")) {
					try {
						BaseDTO dto = new BaseDTO();
						fillEnte(dto);
						dto.setObj(Long.valueOf(submittedValue));
						CsIIntervento csiinterv  = interventoService.getInterventoById(dto);
						ret = new DatiInterventoBean(csiinterv, idSoggetto);							
					}
					catch (Exception e) {
						logger.error(e);
					}				
				}
				
				return ret;
			}

			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {
				if(value!=null && value instanceof DatiInterventoBean)
					return ((DatiInterventoBean)value).getIdIntervento().toString();
				else
					return "";
			}
			
		};
	}
	
	//SISO-748
	public Converter getPicklistErogazioniConverter()
	{
		return new Converter()
		{			

			@Override
			public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {			
				ErogInterventoRowBean ret = null;
				
				if (submittedValue != null && !submittedValue.trim().equals("")) {
					try {
						String idRow = submittedValue;
						
						for(ErogInterventoRowBean eir: listaErogazioniByCaso){
							if(eir.getIdRow().equalsIgnoreCase(idRow)){
								ret = eir;
								break;
							}
						}
					}
					catch (Exception e) {
						logger.error(e);
					}				
				}
				
				return ret;
			}

			@Override
			public String getAsString(FacesContext context, UIComponent component, Object value) {
				if(value!=null && value instanceof ErogInterventoRowBean)
					return ((ErogInterventoRowBean)value).getIdRow().toString();
				else
					return "";
			}
			
		};
	}
		
	public boolean isOnUpdateRelazioni() {
		return onUpdateRelazioni;
	}

	public void setOnUpdateRelazioni(boolean onUpdateRelazioni) {
		this.onUpdateRelazioni = onUpdateRelazioni;
		this.setTabViewIndex(1);
	}
	
	public int getTabViewIndex() {
		return tabViewIndex;
	}


	public void setTabViewIndex(int tabViewIndex) {
		this.tabViewIndex = tabViewIndex;
	}


	public boolean isOnUpdateInterventi() {
		return onUpdateInterventi;
	}

	public void setOnUpdateInterventi(boolean onUpdateInterventi) {
		this.onUpdateInterventi = onUpdateInterventi;
		this.setTabViewIndex(0);
	}
	
	private void updateInterventi() throws Exception 
	{
		if(picklistInterventi!=null && selectedPai!=null)
		{
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			
			if(picklistInterventi.getTarget()!=null)
			{
				for (DatiInterventoBean di : picklistInterventi.getTarget())
				{					
					dto.setObj(di.getIdIntervento());
					dto.setObj2(selectedPai);
					interventoService.salvaRifInterventoToPai(dto);
				}		
			}
			if(picklistInterventi.getSource()!=null)
			{
				for (DatiInterventoBean di : picklistInterventi.getSource())
				{									
					dto.setObj(di.getIdIntervento());
					dto.setObj2(null);
					dto.setObj3(selectedPai.getDiarioId());
					interventoService.salvaRifInterventoToPai(dto);
				}		
			}
		}				
	}
	
	private void updateRelazioni() throws Exception 
	{	
		if(picklistRelazioni!=null && selectedPai!=null)
		{
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);

			if(picklistRelazioni.getTarget()!=null)
			{
				for (RelazioneDTO r : picklistRelazioni.getTarget())
				{								
					dto.setObj(r.getRelazione().getDiarioId());
					dto.setObj2(selectedPai.getDiarioId());
					diarioService.salvaRifRelazioneToPai(dto);
				}		
			}
			if(picklistRelazioni.getSource()!=null)
			{
				for (RelazioneDTO r : picklistRelazioni.getSource())
				{									
					dto.setObj(r.getRelazione().getDiarioId());
					dto.setObj2(null);
					dto.setObj3(selectedPai.getDiarioId());
					diarioService.salvaRifRelazioneToPai(dto);
				}		
			}
		}			
	}
	
	//SISO-748
	private void updateErogazioni() throws Exception
	{
		if(picklistErogazioni!=null && selectedPai!=null)
		{
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			
			if(picklistErogazioni.getTarget() != null)
			{
				for(ErogInterventoRowBean di : picklistErogazioni.getTarget())
				{
					dto.setObj(di.getMaster().getIdInterventoEsegMaster());
					dto.setObj2(selectedPai.getDiarioId());
					interventoService.salvaRifErogazioneToPai(dto);
				}
			}
			if(picklistErogazioni.getSource() != null)
			{
				for(ErogInterventoRowBean di : picklistErogazioni.getSource())
				{
					dto.setObj(di.getMaster().getIdInterventoEsegMaster());
					dto.setObj2(null);
					interventoService.salvaRifErogazioneToPai(dto);
				}
			}
			
		}
	}

	/**
	 * @return the onClosing
	 */
	public final boolean isOnClosing() {
		return onClosing;
	}

	/**
	 * @param onClosing the onClosing to set
	 */
	public final void setOnClosing(boolean onClosing) {
		this.onClosing = onClosing;
	}
	
	@Override
	protected final void addError(String summary, String descrizione) 
	{		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, descrizione != null ? descrizione : "");
		FacesContext.getCurrentInstance().addMessage(MESSAGES_ID, message);	
	}
	
	private static boolean between(Date d, Date min, Date max)	
	{
		if(d==null) 
			return true;
		
		if(max!=null && min==null)
			return !d.after(max);
		if(min!=null && max==null)
			return !d.before(min);
		if(min!=null && max!=null)
			return (!d.before(min) && !d.after(max));
		
		return false;		
	}
	
	/// <summary>
	/// Verifica se due range di date si sovrappongono
	/// </summary>
	/// <param name="ev1Begin">inizio primo range</param>
	/// <param name="ev1End">fine primo range</param>
	/// <param name="ev2Begin">inizio secondo range</param>
	/// <param name="ev2End">fine secondo range</param>
	/// <returns>true se sovrapposti</returns>
	///
	// casi possibili:
	//1) ev2 contenuto in ev1
	//    ev1: +---------------+ 
	//    ev2:       +---+
	//2) ev1 contenuto in ev2 
	//    ev1:       +--------+
	//    ev2: +-----------------+
	//3) parzialmente contenuto, ev1 inizia prima di ev2
	//    ev1:   +--------+
	//    ev2:        +---------------+
	//4) parzialmente contenuto, ev1 inizia dopo ev2
	//    ev1:       +--------+
	//    ev2: +--------+
	//5) eventi esattamente sovrapposti
	//    ev1: +--------+
	//    ev2: +--------+
	//6) ev1 contenuto in ev2 con data inizio uguale
	//    ev1: +--------+
	//    ev2: +---------------+
	//7) ev1 contenuto in ev2 con data fine uguale
	//    ev1:      +---+
	//    ev2: +--------+
	public static boolean VerificaSovrapposizioneRangeDate(Date ev1Begin, Date ev1End, Date ev2Begin, Date ev2End) throws Exception
	{
	  if (ev1Begin==null  || ev2Begin==null) //|| ev1End==null 
	  {
		  throw new Exception("Date null");
	  }
	  
	  //esiste un pai aperto dello stesso tipo, non posso salvare
	  if(ev2End==null){
		  return true;
	  }
	  
	  //la data del pai selezionato deve essere almeno maggiore della data di chiusura del pai analizzato
	  if(!ev1Begin.after(ev2End)){
		  return true;
	  }else{
		  return false;
	  }
		  
//	  if ((ev1End != null && ev1Begin.after(ev1End)) || (ev2End != null && ev2Begin.after(ev2End)))
//	  {
//	    //return true;  // se le date sono invertite indico come periodo sovrapposto
//	    // in laternativa
//	    throw new Exception("Date invertite");
//	  }  
//	 
//	  if ((!ev1Begin.after(ev2Begin) && !ev1End.before(ev2End))  // 1 e 5
//	    || (!ev1Begin.before(ev2Begin) && !ev1End.after(ev2End)) // 2, 6 e 7
//	    || (ev1Begin.before(ev2Begin) && ev1End.after(ev2Begin)) // 3
//	    || (ev1Begin.before(ev2End) && ev1End.after(ev2End))    // 4
//	    )
//	    return true;
//	  else
//	    return false;
	}
	

	 //inizio evoluzione-pai
	@Override
	public boolean isPaiDetailRendered() { 
		return selectedPai!=null;
	}


	public TipoInterventoManBean getTipoInterventoManBean() {
		return tipoInterventoManBean;
	}


	public void setTipoInterventoManBean(TipoInterventoManBean tipoInterventoManBean) {
		this.tipoInterventoManBean = tipoInterventoManBean;
	}

//	public List<SelectItem> getListaTipiIntervento()  {
//		
//		if(listaTipiIntervento == null) {
//			try{
//				
//				listaTipiIntervento = new ArrayList<SelectItem>();
//				InterventoDTO dto = new InterventoDTO();
//				fillEnte(dto);
//				dto.setLstIdCatSoc(this.getLstIdCatSoc());
//		        CsOOperatoreSettore opSettore = (CsOOperatoreSettore) getSession().getAttribute("operatoresettore");
//				dto.setIdSettore(opSettore.getCsOSettore().getId());
//				List<CsCTipoIntervento> lista = interventoService.findTipiInterventoSettoreCatSoc(dto);
//				for(CsCTipoIntervento tipoInt: lista) 
//					listaTipiIntervento.add(new SelectItem(tipoInt.getId() , tipoInt.getDescrizione()));
//			} catch (Exception e) {
//				addErrorFromProperties("caricamento.error");
//				logger.error(e.getMessage(),e);
//			}
//		}
//		
//		return listaTipiIntervento;
//	}
	

	@ManagedProperty(value="#{fascicoloBean}")
	private FascicoloBean fascicoloBean;

	
	public FascicoloBean getFascicoloBean() {
		return fascicoloBean;
	}

	public void setFascicoloBean(FascicoloBean fascicoloBean) {
		this.fascicoloBean = fascicoloBean;
	}
	
	public void refreshPicklistInterventi(CsIIntervento inter){
		if(inter!=null){
			if (picklistInterventi!=null) {		//è null quando il salvataggio viene richiamato dalla tab "Interventi"; è valorizzato quando viene richiamato nella tab PAI
				picklistInterventi.getTarget().add( new DatiInterventoBean(inter, idSoggetto) );
				//UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
				//UIComponent component = viewRoot.findComponent("fascicoloTab:paiForm:paiComponent:paitabview:dataTableInterventi_panel");
				//logger.debug(component.getClientId());
				RequestContext.getCurrentInstance().update("fascicoloTab:paiForm:paiComponent:paitabview:dataTableInterventi_panel"); 
				this.setTabViewIndex(1);
			}
		}
	}
	
	
	public void refreshPicklistRelazioni(RelazioneDTO relazioneDto){
		if (picklistRelazioni!=null) {  //è null quando il salvataggio viene richiamato dalla tab "Attività professionali"; è valorizzato quando viene richiamato nella tab PAI
			
			Iterator<RelazioneDTO> it = picklistRelazioni.getTarget().iterator();
			
			while(it.hasNext()){
				RelazioneDTO rdto = it.next();
				if(rdto.getRelazione().getDiarioId().equals(relazioneDto.getRelazione().getDiarioId())){
					it.remove();
					break;
				}
			}
			
			picklistRelazioni.getTarget().add(relazioneDto);
			
//			UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
//			UIComponent component = viewRoot.findComponent("fascicoloTab:paiForm:paiComponent:paitabview:dataTableRelazioni_panel");
//			logger.debug(component.getClientId()); 
			RequestContext.getCurrentInstance().update("fascicoloTab:paiForm:paiComponent:paitabview:dataTableRelazioni_panel");
//			RequestContext.getCurrentInstance().update("@([id$=paiTabViewContainer])");
		}
	}

	//SISO-748
	public void refreshPicklistErogazioni(Long idMast){
    	picklistErogazioni = null;
    	getPicklistErogazioni();
    	
    	if(idMast!=null){
    		String id="E"+idMast;
    		if(!this.erogazioniNuoveAssociate.contains(id))
    			this.erogazioniNuoveAssociate.add(id);
    	}
    	
    	if (picklistErogazioni!=null) {
    		List<ErogInterventoRowBean> tmp = new ArrayList<ErogInterventoRowBean>();
    		tmp.addAll(picklistErogazioni.getSource());
    		Iterator it = tmp.iterator(); //Utilizzo una lista di appoggio per evitare java.util.ConcurrentModificationException
    		while(it.hasNext()){
    			ErogInterventoRowBean row = (ErogInterventoRowBean)it.next();
    			if(erogazioniNuoveAssociate.contains(row.getIdRow())){
    				 this.picklistErogazioni.getTarget().add(row);
    				 this.picklistErogazioni.getSource().remove(row);
    			}
    		}
			RequestContext.getCurrentInstance().update("fascicoloTab:paiForm:paiComponent:paitabview:dataTableInterventi_panel"); 
			this.setTabViewIndex(0);
		}
    }

	
	@Override
	public void aggiungiTipoInterventoButton() {
		

		FacesContext context = FacesContext.getCurrentInstance();
		fascicoloBean = (FascicoloBean) context.getApplication()
				.evaluateExpressionGet(context, "#{fascicoloBean}", FascicoloBean.class);
		
		fascicoloBean.getInterventiBean().getErogazioniInterventiBean().SetFromFascicolo(getCsASoggetto());
		boolean interventoSelezionato = fascicoloBean.getInterventiBean().inizializzaNuovoIntervento(tipoInterventoManBean);
		 

		if (!interventoSelezionato) {
			RequestContext.getCurrentInstance().addCallbackParam("interventoSelezionato", false);
		} else { 
			RequestContext.getCurrentInstance().addCallbackParam("interventoSelezionato", true);
		}
	}
	
	@Override
	public void aggiungiTipoErogazioneButton(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		fascicoloBean = (FascicoloBean) context.getApplication()
				.evaluateExpressionGet(context, "#{fascicoloBean}", FascicoloBean.class);

		fascicoloBean.getInterventiBean().getErogazioniInterventiBean().SetFromFascicolo(getCsASoggetto());
		fascicoloBean.getInterventiBean().inizializzaNuovaErogazione(tipoInterventoManBean,true);
		 
	}

	private List<SelectItem> listTipoInterventos = null;
	private void loadListaTipiIntervento() {
		if (listTipoInterventos == null)
		{
			// filterCategorie="";
			listTipoInterventos = new LinkedList<SelectItem>();
			
			FglInterventoBean fglInterventoBean = (FglInterventoBean) getReferencedBean("fglInterventoBean");
			
			List<SelectItem> listTipoIntervento = fglInterventoBean.getLstTipoIntervento();
			for (SelectItem item : listTipoIntervento) {

				SelectItemGroup itemGroup = (SelectItemGroup) item;
				for (SelectItem selectItem : itemGroup.getSelectItems()) {

					String values = (String) selectItem.getValue();
					String vals[] = values.split("@");
					Long id = new Long(vals[1]);
					String label = (String) selectItem.getLabel();
					this.listTipoInterventos.add(new SelectItem(id, label));
				}
			}
		}
	}
	
	
	private String getFilterCategorie() {
		FglInterventoBean fglInterventoBean = (FglInterventoBean) getReferencedBean("fglInterventoBean");

		String filterCategorie = "";
		if (fglInterventoBean.getCatsocCorrenti() != null) {
			for (CsCCategoriaSocialeBASIC item : fglInterventoBean.getCatsocCorrenti()) {
				if (filterCategorie.length() > 0) {
					filterCategorie = filterCategorie + ",";//la virgola la metto solo se sono più di due elementi
				}
				filterCategorie = filterCategorie + item.getDescrizione();
			}
		}
		return filterCategorie;
	}

	public List<SelectItem> getLstRadioOptions() {
		if(lstRadioOptions==null){
			lstRadioOptions = new ArrayList<SelectItem>();
			lstRadioOptions.add(new SelectItem(1, "No"));
			lstRadioOptions.add(new SelectItem(2, "Parzialmente"));
			lstRadioOptions.add(new SelectItem(3, "Si"));
			
		}
		
		return lstRadioOptions;
	}
	
	public List<SelectItem> getLstArFfProgetti() {
		if(this.lstArFfProgetti==null)
			lstArFfProgetti = this.loadLstArFfProgetti();
		return lstArFfProgetti;
	}
	
	public void setLstArFfProgetti(List<SelectItem> lstArFfProgetti) {
		this.lstArFfProgetti = lstArFfProgetti;
	}


	public void setPicklistErogazioni(DualListModel<ErogInterventoRowBean> picklistErogazioni) {
		this.picklistErogazioni = picklistErogazioni;
	}


	public List<ErogInterventoRowBean> getListaErogazioniByCaso() {
		return listaErogazioniByCaso;
	}


	public void setListaErogazioniByCaso(List<ErogInterventoRowBean> listaErogazioniByCaso) {
		this.listaErogazioniByCaso = listaErogazioniByCaso;
	}


	public boolean isOnUpdateErogazioni() {
		return onUpdateErogazioni;
	}


	public void setOnUpdateErogazioni(boolean onUpdateErogazioni) {
		this.onUpdateErogazioni = onUpdateErogazioni;
		this.setTabViewIndex(0);
	}

	public PaiAffidoBean getPaiAffidoBean() {
		return paiAffidoBean;
	}

	public void setPaiAffidoBean(PaiAffidoBean paiAffidoBean) {
		this.paiAffidoBean = paiAffidoBean;
	}

	public String getTipoProgetto() {
		return tipoProgetto;
	}

	public void setTipoProgetto(String tipoProgetto) {
		this.tipoProgetto = tipoProgetto;
	}
    //fine evoluzione-pai

	public Date getDataNuovoMonitoraggio() {
		return dataNuovoMonitoraggio;
	}

	public void setDataNuovoMonitoraggio(Date dataNuovoMonitoraggio) {
		this.dataNuovoMonitoraggio = dataNuovoMonitoraggio;
	}
	
	@Override
	public PERIODO_TEMPORALE[] getListaPeriodi(){
		return DataModelCostanti.Pai.PERIODO_TEMPORALE.values();
	}

	//Inizio SISO-1110
	public String getViewIntervento() {
	   if (!isTreeViewTipoIntervento())
	       return "/jsp/protected/treeTipoIntervento/tipoInterventoSelect.xhtml";
	   else 
		   return "/jsp/protected/treeTipoIntervento/tipoInterventoTreePai.xhtml";
	}
	//Fine SISO-1110
	
    //SISO-1131
	public List<CsTbProgettoAltro> loadListaProgettoAltro(String query) {
		List<CsTbProgettoAltro> result= new ArrayList<CsTbProgettoAltro>();
		
		it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
    	fillEnte(d);
    	if(!query.isEmpty()){
    		d.setObj(query);
		result = interventoService.findProgettiAltroPerDesc(d);
		}
		return result;
	}
	
	public void onProgettoAltroSelect(javax.faces.event.AjaxBehaviorEvent event) {
     	this.selectedPai.setCsTbProgettoAltro(selectedProgettoAltro);
	}
	
	@Override
	public boolean isAbilitaMenuProgettiAltro() {
		String tipoProgettoAltro = "";
		if (this.selectedPai.getTipoProgettoId() != null){
			if (this.selectedPai.getTipoProgettoId()>=0){
				
				for(SelectItem itm : lstArFfProgetti){
					if(itm.getValue().equals(this.selectedPai.getTipoProgettoId())){
						tipoProgettoAltro = itm.getLabel();
						this.abilitaMenuProgettiAltro = (!tipoProgettoAltro.isEmpty() && tipoProgettoAltro.equalsIgnoreCase("ALTRO"));
						break;
					}
				}
				
			}
			else{
				this.abilitaMenuProgettiAltro = false;
			}
		}
		else {
			this.abilitaMenuProgettiAltro = false;
		}
			return this.abilitaMenuProgettiAltro;
	}

	

	public void setAbilitaMenuProgettiAltro(boolean abilitaMenuProgettiAltro) {
		this.abilitaMenuProgettiAltro = abilitaMenuProgettiAltro;
	}

	/**
	 * @return the selectedProgettoAltro
	 */
	public CsTbProgettoAltro getSelectedProgettoAltro() {
		return selectedProgettoAltro;
	}

	/**
	 * @param selectedProgettoAltro the selectedProgettoAltro to set
	 */
	public void setSelectedProgettoAltro(CsTbProgettoAltro selectedProgettoAltro) {
		this.selectedProgettoAltro = selectedProgettoAltro;
	}
	
	
	public void onChangeProgetto(AjaxBehaviorEvent event){

    	    long tipoProgettoId = (Long) ((javax.faces.component.UIInput)event.getComponent()).getValue();
			
			String progetto = "";

			for(SelectItem itm : lstArFfProgetti){
				if(itm.getValue().equals(this.selectedPai.getTipoProgettoId())){
					progetto = itm.getLabel();
					break;
				}
			}
			
			
			if(!progetto.isEmpty() && progetto.equalsIgnoreCase("ALTRO")){
				this.setSelectedProgettoAltro(new CsTbProgettoAltro());
				this.selectedPai.setCsTbProgettoAltro(new CsTbProgettoAltro());
			}
			
		
		}
	//FINE SISO-1131
}
