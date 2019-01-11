package it.webred.cs.jsf.bean.erogazioneIntervento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableComuniSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIndirizzoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableNazioniSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTablePsExportSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.ejb.dto.RisorsaFamDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.IntEsegAttrBean;
import it.webred.cs.csa.ejb.dto.erogazioni.SoggettoErogazioneBean;
import it.webred.cs.csa.ejb.dto.erogazioni.configurazione.ErogStatoCfgDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.ListaBeneficiari;
import it.webred.cs.data.DataModelCostanti.TipoStatoErogazione;
import it.webred.cs.data.model.ArRelClassememoPresInps;
import it.webred.cs.data.model.ArTbPrestazioniInps;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsAIndirizzo;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.CsCfgAttrUnitaMisura;
import it.webred.cs.data.model.CsCfgIntEsegStato;
import it.webred.cs.data.model.CsDIsee;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsIInterventoEseg;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.data.model.CsIInterventoEsegMastSogg;
import it.webred.cs.data.model.CsIInterventoEsegValore;
import it.webred.cs.data.model.CsIInterventoPr;
import it.webred.cs.data.model.CsIPs;
import it.webred.cs.data.model.CsIQuota;
import it.webred.cs.data.model.CsIValQuota;
import it.webred.cs.data.model.CsOOperatoreAnagrafica;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsTbSchedaMultidim;
import it.webred.cs.jsf.bean.DatiTariffeInterventoBean.TIPO_FREQUENZA_SERVIZIO;
import it.webred.cs.jsf.bean.DatiUserSearchBean;
import it.webred.cs.jsf.manbean.RisorsaFamiliareBean;
import it.webred.cs.jsf.manbean.UserSearchBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.siso.ws.client.anag.client.PersonaFindResult;

public class ErogazioneInterventoBean extends CsUiCompBaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private AccessTableAlertSessionBeanRemote alertService=
			(AccessTableAlertSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableAlertSessionBean");
	
	private boolean visualizzaInterventoData = false;

	private SoggettoErogazioneBean soggettoErogazione = null;
	
	private SoggettoErogazioneBean altroSoggettoTmp;
	private boolean nuovoInsSoggManuale=false;
	private UserSearchBean beneficiarioSearchBean = new UserSearchBean();
	private List<SoggettoErogazioneBean> altriSoggetti;
	
	//SISO-760
	private Boolean cambiaBeneficiarioRiferimento = false;
	
	private RisorsaFamiliareBean risorsaFamBean;
	
	private Date dataErog;
	private Date dataErogA;
	private Long statoSelezionatoId;
	
	private List<CsCfgIntEsegStato> listaStatiErogazione;
	private List<SelectItem> lstSelItemStatiErog;
	private List<CsOSettore> listaSettori;
	private List<SelectItem> listaSettoriErGroup;
	/* SISO-663 SM */
	private List<SelectItem> listaSettoriGestGroup;
	private Map<Long, List<CsOSettore>> mapEntiTitolariDiSettore;
	private Map<Long, List<CsOSettore>> mapEntiGestoriDiSettore;
	/* -=- */
	
	private Long selSettoreEroganteId;
 
	//frida	
	private List<String> listaCittadinanze;
	private List<String> listaOpzioniBen;
	private Date dataAttivazioneDa;
	private Boolean testataDisabled = false;
	private boolean dentroFascicolo;  //La variabile specifica se l'apertura della dialog viene effettuata dalla lista interna al Fascicolo del caso o no.
	private Boolean erogazionePossibile=true;
	private CsIInterventoEsegMast nuovoIntEsegMast;
	private CsIInterventoEseg nuovocsIntEseg;
	private List<CsIInterventoEseg> interEsegHistory= new ArrayList<CsIInterventoEseg>();
	private boolean erogazioniEsportate = false;  //SISO-522
	private List<CsOOperatoreSettore> listaOperSettore= new ArrayList<CsOOperatoreSettore>();
	private List<CsOOperatoreAnagrafica> listaOperAnagrafica;
	

	private Long operatoreErogId;
	protected AccessTableOperatoreSessionBeanRemote operatoreService=(AccessTableOperatoreSessionBeanRemote)getCarSocialeEjb ("AccessTableOperatoreSessionBean");
	private String testaPercOrValue=DataModelCostanti.ErogazioneInterventoConst.TESTATA_SPESA_PERCENTUALE;
	private String dettaPercOrValue=DataModelCostanti.ErogazioneInterventoConst.DETTAGLIO_SPESA_PERCENTUALE;
	private String operatoreScegliOrInserisci=DataModelCostanti.ErogazioneInterventoConst.SCEGLI_OPERATORE;
	
	private Long valQuotaTestata;
	private String motivazioneQuotaTestata;
	
	private Boolean calcolaSpesaDaDettaglio = true;
	private Boolean calcolaCompartDaDettaglio = true;
	
	private Boolean isSpesaPercTesta=true;
	private Boolean isSpesaPercDetta=true;
	private Boolean isSpesaValTesta=false;
	private Boolean isSpesaValDetta=false;
	private CsIPs csIPs = null; //MOD-RL
	
	//
	private boolean disabilitaOrganizzazione=false;
	//private List<InterventoErogazAttrBean> erogaziones;
	private HashMap<Long, List<InterventoErogazAttrBean>> mappaErogStatoAttr;
	private InterventoErogazHistoryBean erogazioneHistory = new InterventoErogazHistoryBean();
	private boolean visualizzaAttributi = false;
	private boolean enabledAggiungi = false;
	
	private CsCTipoIntervento tipoIntervento;
	private CsCTipoInterventoCustom tipoIntCustom;
	
	//Gestione ISEE
//	SISO-657	private boolean situazioneEconomicaVerificata;  
	private String iseeSelezionata;
	private List<SelectItem> listaCodiciPrestazione; //MOD-RL
	private List<CsDIsee> listaIsee; //MOD-RL
//	SISO-657 	private String txtProtocolloDomandaPrestazioneLabel ;
//	SISO-657 	private Integer flagProvaMezzi;		
//	private String oldFlagProvaMezzi;		//	SISO-657 
//	SISO-657 	private Integer flagPresoInCarico;		
	 
	/* SISO-663 SM */
	
	/* SISO-720 SM */
	private BigDecimal tariffaLocale;
	private RuoloEnteGestoreSpesa ruoloEnteGestoreSpesa;
	
	private CsOSettore settoreTitolare;
	private CsOSettore settoreGestore;
	
	private final ErogazioneInterventoTooltipText erogazioneInterventoTooltipText = new ErogazioneInterventoTooltipText();
	private boolean gestoreComeTitolare = false;
	
	//SISO-809
	private boolean esportazioneDiGruppo=false;
	
	public enum RuoloEnteGestoreSpesa {
		NON_SELEZIONATO("Ente gestore della spesa non selezionato"), 
		TITOLARE("Ente Titolare"), 
		CAPOFILA("Ente Capofila"), 
		GESTORE("Ente Gestore");
		
		private String _descrizione;
		private RuoloEnteGestoreSpesa(String descrizione) { _descrizione = descrizione; }
		String descrizione() { return _descrizione; }
	}
	
	
	/* -=- */
	
	
	
	protected AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote)  getCarSocialeEjb( "AccessTableInterventoSessionBean");
	protected AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote)  getCarSocialeEjb( "AccessTableConfigurazioneSessionBean");
	protected AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote)  getCarSocialeEjb("AccessTableSoggettoSessionBean");
	protected AccessTableComuniSessionBeanRemote comuniService = (AccessTableComuniSessionBeanRemote)  getCarSocialeEjb( "AccessTableComuniSessionBean"); //MOD-RL	
	protected AccessTableIndirizzoSessionBeanRemote indirizzoService = (AccessTableIndirizzoSessionBeanRemote)  getCarSocialeEjb( "AccessTableIndirizzoSessionBean"); //MOD-RL
	protected AccessTablePsExportSessionBeanRemote psExportService= (AccessTablePsExportSessionBeanRemote) getCarSocialeEjb("AccessTablePsExportSessionBean");  //SISO-524

	
	
	
	public void inizializzaNuovaErogazione(Long tipoInterventoId,Long tipoInterventoCustomId ,SoggettoErogazioneBean soggettoErogazione, Long catSocialeId, boolean dentroFascicolo) {
		this.dentroFascicolo = dentroFascicolo;
		
		//SISO_760
		this.setCambiaBeneficiarioRiferimento(false);
		
		this.altroSoggettoTmp = new SoggettoErogazioneBean(false);
		this.altriSoggetti = new ArrayList<SoggettoErogazioneBean>();
		
		if( soggettoErogazione == null ){
			this.soggettoErogazione = new SoggettoErogazioneBean(true);	
			//this.dentroFascicolo=false;
		}			
		else{
			this.soggettoErogazione = soggettoErogazione; //TODO:Verificare che riferimento sia a true
			this.altriSoggetti.add(soggettoErogazione);
			//this.dentroFascicolo=(soggettoErogazione.getCsASoggetto()!=null);
		}
		
		initRisorsaFamiliareBean();
		nuovoIntEsegMast=new CsIInterventoEsegMast();
		setCsIPs(new CsIPs());	//MOD-RL 
		testataDisabled=false;

		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(tipoInterventoId);
		this.tipoIntervento = interventoService.getTipoInterventoById(dto);
		
		initTipoInterventoCustom(tipoInterventoCustomId);
		
		buildInterventoErogazzAttList(tipoInterventoCustomId!=null ?tipoInterventoCustomId : tipoInterventoId);
		loadListaStatiInterventi(tipoInterventoCustomId!=null ?tipoInterventoCustomId : tipoInterventoId);
		//loadListaSettori();
		resetSettoriTestata();
		resetOrganizAndNuovoIntEseg();	
		
		nuovoIntEsegMast.setTipoBeneficiario(DataModelCostanti.ListaBeneficiari.INDIVIDUALE);
		setRuoloEnteGestoreSpesa(this.nuovoIntEsegMast.getRuoloEnte()!=null ? RuoloEnteGestoreSpesa.valueOf(nuovoIntEsegMast.getRuoloEnte()) : RuoloEnteGestoreSpesa.NON_SELEZIONATO);
		
		//this.codFinanziamento=null;
		
		//Recupera oggetto intervento
		this.erogazioneHistory = new InterventoErogazHistoryBean();
		interEsegHistory=this.erogazioneHistory.initialize(mappaErogStatoAttr, tipoIntervento, tipoIntCustom, catSocialeId); 
				
		resetBoolDisabledSpese();
		
		this.visualizzaInterventoData = false;		
		this.statoSelezionatoId = null;
		this.visualizzaAttributi = false;
		this.enabledAggiungi = false;			
		this.erogazioniEsportate = false;
		
		//Comanda comunque INTERVENTO_ID (non il custom!)
		initDatiSituazioneEconomicaVerificata(tipoInterventoId);
	}
	
	public void inizializzaErogazione(Long tipoInterventoId,Long tipoInterCustomId ,Long interventoId, SoggettoErogazioneBean soggettoErogazione, List<SoggettoErogazioneBean> beneficiari, boolean dentroFascicolo) throws Exception {
		//this.dentroFascicolo=true;
		this.dentroFascicolo = dentroFascicolo;
		
		//SISO-760
		this.setCambiaBeneficiarioRiferimento(false);
		
		altriSoggetti = new ArrayList<SoggettoErogazioneBean>();
		
		loadListaStatiInterventi(tipoInterCustomId!=null ?tipoInterCustomId : tipoInterventoId);
		buildInterventoErogazzAttList(tipoInterCustomId!=null ?tipoInterCustomId : tipoInterventoId);
		//loadListaSettori();
		
		BaseDTO bdto = new BaseDTO();
		fillEnte(bdto);
		bdto.setObj(interventoId);
		CsIIntervento csIIntervento = this.interventoService.getInterventoById(bdto);
		
		initTipoInterventoCustom(tipoInterCustomId);
		
        this.erogazioneHistory = new InterventoErogazHistoryBean();
		if (csIIntervento != null) { //TODO attualmente in realtà questo metodo viene chiamato solo se interventoI!=null
			this.tipoIntervento = csIIntervento.getCsRelSettCsocTipoInter().getCsRelCatsocTipoInter().getCsCTipoIntervento();
			this.interEsegHistory=this.erogazioneHistory.initialize(mappaErogStatoAttr, csIIntervento); 
			this.erogazioniEsportate = erogazioniEsportate(interEsegHistory);
			
			this.visualizzaInterventoData = true;		
			
			//inizio SISO-500  
			if (csIIntervento.getCsIInterventoEsegMast()!=null && csIIntervento.getCsIInterventoEsegMast().getCsIPs()!=null)
				setCsIPs(csIIntervento.getCsIInterventoEsegMast().getCsIPs()); 
		    else
				setCsIPs(new CsIPs());
			//fine SISO-500  
			
		}			
							
		this.soggettoErogazione = soggettoErogazione; //Verificare che il flag riferimento=true
			
		this.altriSoggetti.addAll(beneficiari);
		
	    this.initRisorsaFamiliareBean();
		
		this.resetSettoriTestata();
		this.resetOrganizAndNuovoIntEseg();	
		
		testataDisabled=false;
		 
		this.statoSelezionatoId = null;
		this.onStatoChanged();								
		//nel seguente if dovrebbe entrarci sempre (dato che non è possibile inserire un foglio amministrativo 
		//senza nemmeno una riga di erogazione)
		resetBoolDisabledSpese();
		if(interEsegHistory.size()>0){ 
			this.nuovoIntEsegMast=interEsegHistory.get(0).getCsIInterventoEsegMast();
			setBoolDisabledSpese();
			//TODO set organizz erogante, altra org coinvolta, motivo coinvolg
			this.selSettoreEroganteId=nuovoIntEsegMast.getSettoreErogante()!=null ? nuovoIntEsegMast.getSettoreErogante().getId() : null;
			//this.selSettoreTitolareId=nuovoIntEsegMast.getSettoreTitolare()!=null ? nuovoIntEsegMast.getSettoreTitolare().getId() : null;
			loadListaOperatoriEroganti(this.selSettoreEroganteId);
		
		/*	if(nuovoIntEsegMast.getFfOrigineId()!=null)
				this.origineFinanziamentoId= nuovoIntEsegMast.getFfOrigineId();*/
			
			if(nuovoIntEsegMast.getCsOOperatoreSettore().getCsOOperatore().getId()!=getCurrentOpSettore().getCsOOperatore().getId())
				testataDisabled=true;
		}else{
			//this.codFinanziamento=null;
			this.nuovoIntEsegMast= new CsIInterventoEsegMast();

			//SISO-500  setCsIPs(new CsIPs());//MOD-RL 
		}
		
		if(nuovoIntEsegMast.getTipoBeneficiario()==null || nuovoIntEsegMast.getTipoBeneficiario().isEmpty())
			nuovoIntEsegMast.setTipoBeneficiario(DataModelCostanti.ListaBeneficiari.INDIVIDUALE);
		nuovoIntEsegMast.setCsIIntervento(csIIntervento);
		
		setRuoloEnteGestoreSpesa(this.nuovoIntEsegMast.getRuoloEnte()!=null ? RuoloEnteGestoreSpesa.valueOf(nuovoIntEsegMast.getRuoloEnte()) : RuoloEnteGestoreSpesa.NON_SELEZIONATO);
		//Comanda comunque INTERVENTO_ID (non il custom!)
		initDatiSituazioneEconomicaVerificata(tipoInterventoId );
	}
	

	public void inizializzaDaErogazioneMaster(Long tipoInterventoId,Long tipoInterCustomId , CsIInterventoEsegMast master, SoggettoErogazioneBean soggettoErogazione, List<SoggettoErogazioneBean> beneficiari, boolean flgDaFascicolo) {
		dentroFascicolo= flgDaFascicolo;
		
		//SISO-760
		setCambiaBeneficiarioRiferimento(false);
		
		//Init beneficiari
		altriSoggetti = new ArrayList<SoggettoErogazioneBean>();
		this.soggettoErogazione = soggettoErogazione;
		this.altriSoggetti.addAll(beneficiari);
		
		loadListaStatiInterventi(tipoInterCustomId!=null ?tipoInterCustomId : tipoInterventoId);
		
		buildInterventoErogazzAttList(tipoInterCustomId!=null ?tipoInterCustomId : tipoInterventoId);
		//loadListaSettori();
		
		BaseDTO bdto = new BaseDTO();
		fillEnte(bdto);
		this.nuovoIntEsegMast = master;
		
		this.tipoIntervento = master.getCsCTipoIntervento();
		this.initTipoInterventoCustom(tipoInterCustomId);
		setRuoloEnteGestoreSpesa(this.nuovoIntEsegMast.getRuoloEnte()!=null ? RuoloEnteGestoreSpesa.valueOf(nuovoIntEsegMast.getRuoloEnte()) : RuoloEnteGestoreSpesa.NON_SELEZIONATO);
		

        this.erogazioneHistory = new InterventoErogazHistoryBean();
		this.interEsegHistory = this.erogazioneHistory.initialize(mappaErogStatoAttr, master);
		this.erogazioniEsportate = erogazioniEsportate(interEsegHistory);
		
		this.visualizzaInterventoData = true;				
								
		initRisorsaFamiliareBean();
		this.resetSettoriTestata();
		this.resetOrganizAndNuovoIntEseg();	
	
		 
		this.statoSelezionatoId = null;
		this.onStatoChanged();								
		//nel seguente if dovrebbe entrarci sempre (dato che non è possibile inserire un foglio amministrativo 
		//senza nemmeno una riga di erogazione)
		resetBoolDisabledSpese();
		if(interEsegHistory.size()>0){ 
			this.nuovoIntEsegMast=interEsegHistory.get(0).getCsIInterventoEsegMast();
			setBoolDisabledSpese();
			//TODO set organizz erogante, altra org coinvolta, motivo coinvolg
			this.selSettoreEroganteId=nuovoIntEsegMast.getSettoreErogante()!=null ? nuovoIntEsegMast.getSettoreErogante().getId() : null;
			//this.selSettoreTitolareId = nuovoIntEsegMast.getSettoreTitolare()!=null ? nuovoIntEsegMast.getSettoreTitolare().getId() : null;
			loadListaOperatoriEroganti(this.selSettoreEroganteId);	
			/*if(nuovoIntEsegMast.getFfOrigineId()!=null)
				this.origineFinanziamentoId= nuovoIntEsegMast.getFfOrigineId();*/
		}
		
		//this.codFinanziamento = master.getCodiceFin1();
		
		if(this.isErogazioniPresenti())
			testataDisabled = !this.isOperatoreProprietario();

		testataDisabled = testataDisabled || isErogazioniPresenti();

		setCsIPs(master.getCsIPs()!=null ? master.getCsIPs() : new CsIPs()); //SISO-498

		//Comanda comunque INTERVENTO_ID (non il custom!)
		initDatiSituazioneEconomicaVerificata(tipoInterventoId);
		
	}
	
	
	
	private void initTipoInterventoCustom(Long idTipoInteventoCustom){
		this.tipoIntCustom = null;
		if( idTipoInteventoCustom != null ) {
			BaseDTO bdto = new BaseDTO();
			fillEnte(bdto);
			bdto.setObj(idTipoInteventoCustom);
	        this.tipoIntCustom= interventoService.findTipiInterventoCustomById(bdto);
		}
	}
	
	private void initRisorsaFamiliareBean(){
		risorsaFamBean = null;
		if(this.isNucleoBeneficiario() && this.dentroFascicolo)
			this.risorsaFamBean = new RisorsaFamiliareBean(soggettoErogazione.getCodiceFiscale());
	}
	
	public void initDatiSituazioneEconomicaVerificata(Long idTipoIntervento){
//SISO-657		situazioneEconomicaVerificata = false;
//SISO-657			if (this.getCsIPs()!=null && this.getCsIPs().getDtIns()!=null) { 
//			this.situazioneEconomicaVerificata = true;	
//		}
		
		loadDatiIsee(); //MOD-RL
		loadlListaCodiciPrestazione(idTipoIntervento);//MOD-RL
		
	}
	
	private void resetBoolDisabledSpese(){
		testaPercOrValue=DataModelCostanti.ErogazioneInterventoConst.TESTATA_SPESA_PERCENTUALE;
		dettaPercOrValue=DataModelCostanti.ErogazioneInterventoConst.DETTAGLIO_SPESA_PERCENTUALE;
		this.calcolaCompartDaDettaglio=true;
		this.calcolaSpesaDaDettaglio=true;
		
		isSpesaPercTesta=true;
		isSpesaPercDetta=true;
		isSpesaValTesta=false;
		isSpesaValDetta=false;
		
	}

	private void resetSettoriTestata(){
		//Preseleziono l'organizzazione corrente
		CsOSettore settoreCorrente = getCurrentOpSettore().getCsOSettore();
		this.selSettoreEroganteId = settoreCorrente.getId();
		//this.selSettoreTitolareId = settoreCorrente.getId();
		
		this.disabilitaOrganizzazione=settoreCorrente.getCsOOrganizzazione().getBelfiore()==null;
		//loadListaOrigineFinanziamenti();
	}
	
	private void resetOrganizAndNuovoIntEseg(){					
		this.dataErog=dataAttivazioneDa;	
		this.dataErogA = null;  //SISO-556
		if(this.selSettoreEroganteId!=null && selSettoreEroganteId.compareTo((long)0)!=0){
			loadListaOperatoriEroganti(selSettoreEroganteId);		
		}	
		this.nuovocsIntEseg= new CsIInterventoEseg();
		CsOOperatoreSettore op= getCurrentOpSettore();
		this.operatoreErogId=op.getCsOOperatore().getId();
		this.operatoreScegliOrInserisci=DataModelCostanti.ErogazioneInterventoConst.SCEGLI_OPERATORE;
	}
	
  
	private void setBoolDisabledSpese(){
	
		/* valuto spese master */
		if(nuovoIntEsegMast.getFlagSpesaCalc()==null){
			boolean totaleVal = 
					(nuovoIntEsegMast.getSpesa()!=null             && nuovoIntEsegMast.getSpesa().compareTo(new BigDecimal(0))!=0)||
					(nuovoIntEsegMast.getValoreGestitaEnte()!=null && nuovoIntEsegMast.getValoreGestitaEnte().compareTo(new BigDecimal(0))!=0) ||
					(nuovoIntEsegMast.getPercGestitaEnte()!=null   && nuovoIntEsegMast.getPercGestitaEnte().compareTo(new BigDecimal(0))!=0);
					
			if(totaleVal) calcolaSpesaDaDettaglio=false;
					
		}else
			calcolaSpesaDaDettaglio=nuovoIntEsegMast.getFlagSpesaCalc();
		
		if(calcolaSpesaDaDettaglio){
			this.testaPercOrValue=null;
			this.isSpesaPercTesta=false;
			this.isSpesaValTesta=false;
		}else{
			this.testaPercOrValue = (nuovoIntEsegMast.getPercGestitaEnte()!=null   && nuovoIntEsegMast.getPercGestitaEnte().compareTo(new BigDecimal(0))!=0) ? "testaPercentuale" : "testaValore";
			isSpesaPercTesta=(nuovoIntEsegMast.getPercGestitaEnte()!=null   && nuovoIntEsegMast.getPercGestitaEnte().compareTo(new BigDecimal(0))!=0);
			isSpesaValTesta=(nuovoIntEsegMast.getValoreGestitaEnte()!=null && nuovoIntEsegMast.getValoreGestitaEnte().compareTo(new BigDecimal(0))!=0);
		}
		

		if(nuovoIntEsegMast.getFlagCompartCalc()==null){
			boolean compTotale = 
					(nuovoIntEsegMast.getCompartUtenti()!=null && nuovoIntEsegMast.getCompartUtenti().compareTo(new BigDecimal(0))!=0) ||
					(nuovoIntEsegMast.getCompartSsn()!=null && nuovoIntEsegMast.getCompartSsn().compareTo(new BigDecimal(0))!=0) ||
					(nuovoIntEsegMast.getCompartAltre()!=null && nuovoIntEsegMast.getCompartAltre().compareTo(new BigDecimal(0))!=0);
					
			if(compTotale) this.calcolaCompartDaDettaglio=false;

		}else 
			calcolaCompartDaDettaglio=nuovoIntEsegMast.getFlagCompartCalc();
		
		
		/* valuto spese dettaglio */	
		//Salvando già la somma con flag, non è necessario ricaricare il valore calcolato
/*		if(interEsegHistory.size()>0){
			for (CsIInterventoEseg i : interEsegHistory) {
				
				if(this.calcolaSpesaDaDettaglio){
				if(i.getSpesa()!=null)
					if(i.getSpesa().compareTo(new BigDecimal(0))!=0){
						
						if(nuovoIntEsegMast.getSpesa()==null)
							nuovoIntEsegMast.setSpesa(i.getSpesa());
						else
							nuovoIntEsegMast.setSpesa(nuovoIntEsegMast.getSpesa().add(i.getSpesa()));
					}
				if(i.getValoreGestitaEnte()!=null)
					if(i.getValoreGestitaEnte().compareTo(new BigDecimal(0))!=0){
						isSpesaValTesta=false;
						isSpesaValDetta=true;
						if(nuovoIntEsegMast.getValoreGestitaEnte()==null)
							nuovoIntEsegMast.setValoreGestitaEnte(i.getValoreGestitaEnte());
						else
							nuovoIntEsegMast.setValoreGestitaEnte(nuovoIntEsegMast.getValoreGestitaEnte().add(i.getValoreGestitaEnte()));
					}
				if(i.getPercGestitaEnte()!=null)
					if(i.getPercGestitaEnte().compareTo(new BigDecimal(0))!=0){
						isSpesaPercTesta=false;
						isSpesaPercDetta=true;
						if(nuovoIntEsegMast.getPercGestitaEnte()==null)
							nuovoIntEsegMast.setPercGestitaEnte(i.getPercGestitaEnte());
						else
							nuovoIntEsegMast.setPercGestitaEnte(nuovoIntEsegMast.getPercGestitaEnte().add(i.getPercGestitaEnte()));
					}
				}
				if(this.calcolaCompartDaDettaglio){
				if(i.getCompartUtenti()!=null)
					if(i.getCompartUtenti().compareTo(new BigDecimal(0))!=0){
					
						if(nuovoIntEsegMast.getCompartUtenti()==null)
							nuovoIntEsegMast.setCompartUtenti(i.getCompartUtenti());
						else
							nuovoIntEsegMast.setCompartUtenti(nuovoIntEsegMast.getCompartUtenti().add(i.getCompartUtenti()));
					}
				if(i.getCompartSsn()!=null)
					if(i.getCompartSsn().compareTo(new BigDecimal(0))!=0){
						
						if(nuovoIntEsegMast.getCompartSsn()==null)
							nuovoIntEsegMast.setCompartSsn(i.getCompartSsn());
						else
							nuovoIntEsegMast.setCompartSsn(nuovoIntEsegMast.getCompartSsn().add(i.getCompartSsn()));					
					}
				if(i.getCompartAltre()!=null && i.getCompartAltre().compareTo(new BigDecimal(0))!=0)
						nuovoIntEsegMast.setCompartAltre(nuovoIntEsegMast.getCompartAltre().add(i.getCompartAltre()));
			}
		}		
	  }	*/	
	}
	
	public void addMessage(FacesMessage.Severity tipoMessaggio,String summary) {
        FacesMessage message = new FacesMessage(tipoMessaggio, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	public boolean validaTestata(){
		boolean valido=true;
		
		if(this.isUnicoBeneficiario() && !soggettoErogazione.isValorizzato()){
			addMessage(FacesMessage.SEVERITY_ERROR,"Anagrafica incompleta del soggetto beneficiario");
			valido=false;
		}
		
		if(!this.isUnicoBeneficiario()){
			//Lista beneficiari vuota
			if(getAltriSoggetti().isEmpty()){
				addMessage(FacesMessage.SEVERITY_ERROR,"Inserire almeno un soggetto beneficiario");
				valido=false;
			}else{
				boolean existsRif = false;
				List<String> lstcf = new ArrayList<String>();
				for(SoggettoErogazioneBean seb : this.getAltriSoggetti()){
					if(!seb.isValorizzato()){
						addMessage(FacesMessage.SEVERITY_ERROR,"Dati incompleti per il soggetto: "+ seb.getCognome()+" "+seb.getNome());
						valido=false;
					}
					
					//Verifica che non ci siano soggetti uguali
					if(!lstcf.contains(seb.getCodiceFiscale()))
						lstcf.add(seb.getCodiceFiscale());
					else{
						addMessage(FacesMessage.SEVERITY_ERROR,"Beneficiario duplicato: "+ seb.getCodiceFiscale());
						valido=false;
					}
					
					existsRif = seb.isRiferimento() | existsRif;
				}
				//Verifica che almeno uno dei soggetti sia definito come di riferimento
				if(!existsRif){
					addMessage(FacesMessage.SEVERITY_ERROR,"Selezionare il soggetto beneficiario di riferimento");
					valido=false;
				}
			}
			
		}
		// SISO-663 SM
		if (this.nuovoIntEsegMast.getEnteGestoreSpesa() == null) {
			valido = false;
			addMessage(FacesMessage.SEVERITY_ERROR, "Ente gestore della spesa non selezionato");
		}
		if (selSettoreEroganteId.equals(0)) {
			valido = false;
			addMessage(FacesMessage.SEVERITY_ERROR, "Settore erogante non selezionato");
		}
		
		//INIZIO SISO-556
		if (nuovoIntEsegMast.getValoreGestitaEnte() != null
				&& nuovoIntEsegMast.getSpesa() != null
				&& nuovoIntEsegMast.getValoreGestitaEnte().compareTo( nuovoIntEsegMast.getSpesa() ) > 0  ) { 
			addMessage(FacesMessage.SEVERITY_ERROR,"Valore Spesa Gestita Direttamente in dettaglio non corretto:inserire valore numerico intero o decimale (es.12,34), minore o uguale alla Spesa");
			valido=false;
		}
		//FINE SISO-556
		
		if(!validaDatiCsIPs()) valido = false;
		
		return valido;
		
	}
	
	public boolean salva(CsIIntervento intervento, CsIQuota quota, boolean dentroCaso, CsASoggettoLAZY csASoggetto, String frequenzaAccessoAlServizio, CsIInterventoPr progetto) {
		 //SISO-556 spostata la validazione in fglInterventoBean
		//		if(!this.validaTestata() || !this.validaErogazioni(quota) ) return false;
		
		boolean bOk = true;	
		boolean inviaAlertResponsabile = false;
		List<CsIInterventoEseg> erogAlertTitolare = new ArrayList<CsIInterventoEseg>();
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		
		nuovoIntEsegMast.setCsIQuota(quota);
		nuovoIntEsegMast.setCsIInterventoPr(progetto);
		
		try {			
	
			if(this.getAltriSoggetti().isEmpty() && this.soggettoErogazione.isValorizzato())
				this.getAltriSoggetti().add(this.soggettoErogazione);
	
			 valorizzaInterventoEsegMast(intervento);
		
			//frida			
			if(erogazioneHistory.getRows()!=null){
				if(erogazioneHistory.getRows().size()>0)
				for (InterventoErogazHistoryRowBean row : erogazioneHistory.getRows()) {
					
					dto.setObj(nuovoIntEsegMast);
					 nuovoIntEsegMast = interventoService.salvaInterventoEseguitoMast(dto);
					
					if (row.isNuovaErogazione()) {		   
							CsIInterventoEseg nuovoIntEseg = valorizzaInterventoEseg(row);							
							nuovoIntEseg= nuovoIntEsegMast.addCsIInterventoEseg(nuovoIntEseg);
							valorizzaIntEsegValori(row, nuovoIntEseg);	
							
							 if(row.getIntEseg().getStato().getTipo().equalsIgnoreCase(DataModelCostanti.TipoStatoErogazione.EROGATIVO)){ 
								 inviaAlertResponsabile=true;
							    	
							    	/* == SISO-662 SM DEBUG == */
							    	
							    	//Invio alert al (responsabile) di settore titolare, se quello gestore è diverso
								 
								    /*SISO-775  
								     * il recupero per l'invio dell'alert del settore dell'operatore che ha erogato � inutile in quanto tale valore non viene utilizzato e pu� comunque dare un nullpointer dato che l'operatore sulla riga erogazione non � obbligatorio
								     */
								 
//							    	CsIInterventoEseg intEseg =row.getIntEseg();
//							    	CsOOperatoreSettore opSett = intEseg.getCsOOperatoreSettore();
//							    	CsOSettore settore = opSett.getCsOSettore();
							    	
							    	CsIInterventoPr csIInterventoPr = nuovoIntEsegMast.getCsIInterventoPr();
									CsOSettore settoreTitolare = csIInterventoPr.getSettoreTitolare();
									CsOSettore settoreGestore = csIInterventoPr.getSettoreGestore();
									/* --===-- 
							    	if(settore.getId().equals(settoreTitolare.getId()))
							    		erogAlertTitolare.add(nuovoIntEseg);*/
							    	
							    	if(settoreGestore!=null && settoreGestore.getId().longValue()!=settoreTitolare.getId().longValue())
							    		erogAlertTitolare.add(nuovoIntEseg);
							  }
					}
				}
			}
			
			List<CsIInterventoEseg> storicoDaCancellare = new LinkedList<CsIInterventoEseg>();
			for (InterventoErogazHistoryRowBean eliminaInetEseg : erogazioneHistory.getDeleteErogaz()) {
				if (eliminaInetEseg.getIntEseg() != null)
					storicoDaCancellare.add(eliminaInetEseg.getIntEseg());
			}
			
			
/*			//Cancello eventuali beneficiari presenti, prima dei reinserirne nuovi
			if(nuovoIntEsegMast.getId()>0){
				dto.setObj(nuovoIntEsegMast.getId());
				interventoService.rimuoviBeneficiariMaster(dto);
			}*/
			
			nuovoIntEsegMast.setBeneficiari(null);
			for(SoggettoErogazioneBean se : this.getAltriSoggetti()){
				
				//SISO-760
				if(se.getCodiceFiscale().equalsIgnoreCase(soggettoErogazione.getCodiceFiscale())){
					se.setRiferimento(true);
				}else{
					se.setRiferimento(false);
				}
				
				CsIInterventoEsegMastSogg b = this.valorizzaSoggettiErogazione(se);
				b = nuovoIntEsegMast.addBeneficiario(b);
			}
		
			/*Registrazione su DB*/
			
			if(nuovoIntEsegMast.getCsIInterventoEsegs()!=null && !nuovoIntEsegMast.getCsIInterventoEsegs().isEmpty() && 
			   nuovoIntEsegMast.getBeneficiari()!=null && !nuovoIntEsegMast.getBeneficiari().isEmpty()) {
				
				//INIZIO MOD-RL  
				
				String codiceFiscale = soggettoErogazione.getCodiceFiscale();
				String benefLuogoNascita = codiceFiscale.substring(11,15);
				String sbenefSessoGiorno = codiceFiscale.substring(9,11);
				
				try{
					int benefSessoGiorno = Integer.parseInt( sbenefSessoGiorno );
					int benefSesso = benefSessoGiorno>40?2:1;
					csIPs.setBenefSesso(benefSesso);
					
				}catch(Exception e){
					logger.error("situazioneEconomicaVerificata: Impossibile recuperare il sesso dal '"+sbenefSessoGiorno+"' codice fiscale " + codiceFiscale, e);
				}
				csIPs.setBenefLuogoNascita(benefLuogoNascita);
				
				String iso3166 =  getIso3166ByNazionalita(soggettoErogazione.getCittadinanza());
				csIPs.setBenefCittadinanza(iso3166!=null ? Integer.parseInt(iso3166) : null);   
				Date actual = new Date();
				csIPs.setDtIns(actual);
				csIPs.setDtMod(actual); 
				csIPs.setDenomPrestazione( findDenomizazionePrestazione(csIPs.getCodPrestazione()));

				if (frequenzaAccessoAlServizio.equals(TIPO_FREQUENZA_SERVIZIO.PERIODICO.getCodice())) 
					csIPs.setCarattere(DataModelCostanti.CSIPs.CARATTERE_PRESTAZIONE_DI_TIPO_PERIODICO); 
				else
					csIPs.setCarattere(DataModelCostanti.CSIPs.CARATTERE_PRESTAZIONE_DI_TIPO_OCCASIONALE); 
				
				if (dentroCaso) { 
					long soggettoAnagraficaId = csASoggetto.getAnagraficaId();
					CsAAnaIndirizzo csAAnaIndirizzo = getAnaIndirizzoResidenzaAttuale(soggettoAnagraficaId);
					
					String codComIstat = csAAnaIndirizzo.getComCod();
					
					boolean resInItalia = codComIstat != null;

					String benefNazione;
					if (resInItalia) {
						AmTabComuni comune = comuniService.getComuneByIstat(codComIstat);
						csIPs.setBenefComune(codComIstat);
						csIPs.setBenefRegione(comune.getCodIstatRegione());
						benefNazione = DataModelCostanti.AmTabNazioni.CODICE_ITALIA;
					} else { 
						benefNazione = csAAnaIndirizzo.getStatoCod();
					}
					
					csIPs.setBenefNazione(benefNazione);
				}
				
				if (!csIPs.isSituazioneEconomicaVerificata()) {
					csIPs.setAnnoProtDSU(null);
					resetProtDSU();
					csIPs.setDataDSU(null);
				}else{
					if(csIPs.getAnnoProtDSU()!=null && csIPs.getAnnoProtDSU().intValue()==0) csIPs.setAnnoProtDSU(null);
					if(csIPs.getAnnoProtDSU()==null || csIPs.getNumProtDSU()==null || csIPs.getNumProtDSU().isEmpty())
						this.resetProtDSU();
				}
				
				nuovoIntEsegMast.setCsIPs(csIPs);
				csIPs.setCsIInterventoEsegMast(nuovoIntEsegMast);		 
				//FINE MOD-RL 
				
				dto.setObj(nuovoIntEsegMast);
				nuovoIntEsegMast = interventoService.salvaInterventoEseguitoMast(dto);
			}
			
			if (!storicoDaCancellare.isEmpty()) {
				dto.setObj(storicoDaCancellare);
				interventoService.eliminaInterventoEsegStorico(dto);
			}		
			
		
			
			if(inviaAlertResponsabile || !erogAlertTitolare.isEmpty()){
				
				BaseDTO bdto = new BaseDTO();
				fillEnte(bdto);
				bdto.setObj(nuovoIntEsegMast);
				bdto.setObj2(inviaAlertResponsabile);
				bdto.setObj3(erogAlertTitolare); //Lista delle erogazioni da notificare al titolare
				interventoService.gestisciAlertErogazioni(bdto);
			}
			
		}catch (Exception e) {
			logger.error("Errore salvataggio erogazione", e);
			this.addError("Errore salvataggio erogazione intervento.", "");
			bOk = false;
		}
		return bOk;
	}

	//INIZIO SISO-556
	public boolean validaErogazioni(CsIQuota quota) {
		boolean res = true; 
		//controllo che: se rendiconto periodico, le erogazioni di tipo E devono essere comprese tra data e dataA
		// se rendiconto è ad evento, per le erogazioni di tipo E, la dataA non deve essere valorizzata
		List<InterventoErogazHistoryRowBean> historyRowBeanList = getErogazioneHistory().getRows();
		
		if (quota.getCsIQuotaRipartiz().getFlagRendiconto().
				equals(DataModelCostanti.CsIQuotaRipartiz.FLAG_RENDICONTO_PERIODICO) ) {

			for (InterventoErogazHistoryRowBean interventoErogazHistoryRowBean : historyRowBeanList) {
				if (interventoErogazHistoryRowBean.getStato().getTipo().equals(DataModelCostanti.TipoStatoErogazione.EROGATIVO) ) {
					if (interventoErogazHistoryRowBean.getDataErogazioneA()==null) {
						res = false;
						addMessage(FacesMessage.SEVERITY_ERROR, "In caso di rendicontazione periodica, non possono esserci erogazioni con 'data A' non valorizzata");
						break;
					}
				}
			}
			//SISO-776 tolto l'obbligo in chiusura per le rendicontazione periodiche di avere solo una erogazione
//			if(this.isUnicaErogazioneChiusura()){
//				res=false;
//				addMessage(FacesMessage.SEVERITY_ERROR, "In caso di rendicontazione periodica, non è possibile chiudere l'intervento con una sola erogazione");
//			}
			
		} else{
			
			for (InterventoErogazHistoryRowBean interventoErogazHistoryRowBean : historyRowBeanList) {
				if (interventoErogazHistoryRowBean.getStato().getTipo().equals(DataModelCostanti.TipoStatoErogazione.EROGATIVO) ) {
					if (interventoErogazHistoryRowBean.getDataErogazioneA()!=null) {
						addMessage(FacesMessage.SEVERITY_ERROR, "In caso di rendicontazione ad evento, non possono esserci erogazioni con 'data A' valorizzata");
						res = false;
						break;
					}
				}
			}
		}

		
		return res;
	}
	//FINE SISO-556

	
	/*Verifica se nello storico erogazioni ci siano record con TIPO STATO = E*/
	public boolean isErogazioniPresenti(){
		boolean presenti = false;
		List<InterventoErogazHistoryRowBean> erRows = erogazioneHistory.getRows();
		if(erRows.size()>0){
			int i=0;
			while(!presenti && i<erRows.size()){
				if(DataModelCostanti.TipoStatoErogazione.EROGATIVO.equals(erRows.get(i).getStato().getTipo()))
					presenti = true;
				i++;
			}
		}
		return presenti;
	}
	
	public boolean isUnicaErogazioneChiusura(){
		int countErogazioni = 0;
		boolean chiusura=false;
		List<InterventoErogazHistoryRowBean> erRows = erogazioneHistory.getRows();
		for(InterventoErogazHistoryRowBean erRow : erRows){
			if(DataModelCostanti.TipoStatoErogazione.EROGATIVO.equals(erRow.getStato().getTipo())) countErogazioni++;
			if(erRow.getStato().getFlagChiudi()) chiusura=true;
		}
		return countErogazioni<=1 && chiusura;
	}
	
	public boolean isOperatoreProprietario(){
		Long currentOperatoreID = getCurrentOpSettore().getCsOOperatore().getId();
		boolean proprietario = false;
		if(nuovoIntEsegMast!=null && nuovoIntEsegMast.getCsOOperatoreSettore()!=null)
			proprietario = currentOperatoreID.equals(this.nuovoIntEsegMast.getCsOOperatoreSettore().getCsOOperatore().getId());
		return proprietario;
	}
	
	//INIZIO MOD-RL 
	
	private String findDenomizazionePrestazione(String codPrestazione) {
		
		BaseDTO denomPrestazioneDto = new BaseDTO();
		fillEnte(denomPrestazioneDto);
		denomPrestazioneDto.setObj(codPrestazione); 
		ArTbPrestazioniInps arTbPrestazioniInps = this.confService.findArTbPrestazioniInpsByCodice(denomPrestazioneDto);
		return   arTbPrestazioniInps!=null ? arTbPrestazioniInps.getDenominazione() : null;
	}

	public CsAAnaIndirizzo getAnaIndirizzoResidenzaAttuale(long soggettoAnagraficaId) {
		CsAAnaIndirizzo csAAnaIndirizzo = null;
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(soggettoAnagraficaId);
		List<CsAIndirizzo> listaIndirizzi = indirizzoService.getIndirizziBySoggetto(dto);
 
		CsAIndirizzo iResidenzaAttuale = null;
		
		for (CsAIndirizzo i:listaIndirizzi) {
			if (i.getCsTbTipoIndirizzo().getId()==1 
					&& i.getDataFineApp() == null) {
				iResidenzaAttuale = i;
			}
		}
		
		if (iResidenzaAttuale!=null ) {
			csAAnaIndirizzo = iResidenzaAttuale.getCsAAnaIndirizzo();
		}
		
		return csAAnaIndirizzo;
	}
	
//	public CsAIndirizzo getIndirizzoResidenzaCodFisc(String codFisc) {
//		CsAIndirizzo indirizzoRes = null;
//		try {
//			AccessTablePersonaCiviciSessionBeanRemote bean = (AccessTablePersonaCiviciSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTablePersonaCiviciSessionBean");
//			BaseDTO dto = new BaseDTO();
//			fillEnte(dto);
//			dto.setObj(codFisc);
//			indirizzoRes = bean.getIndirizzoResidenzaByCodFisc(dto);
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
//		return indirizzoRes;
//	}
	//FINE MOD-RL
	
	private CsIInterventoEsegMastSogg valorizzaSoggettiErogazione(SoggettoErogazioneBean se){
		CsIInterventoEsegMastSogg s = new CsIInterventoEsegMastSogg();
		CsASoggettoLAZY casos = se.getCsASoggetto();
		if(casos==null) casos = getSchedaCSByCF(se.getCodiceFiscale());
		
		if(casos!=null){
			se.setCsASoggetto(casos);
			s.setCaso(se.getCsASoggetto().getCsACaso());
		}
	
		s.setCognome(se.getCognome());
		s.setNome(se.getNome());
		s.setCf(se.getCodiceFiscale());
		s.setCittadinanza(se.getCittadinanza());
		s.setAnnoNascita(se.getAnnoNascita());
		s.setRiferimento(se.isRiferimento());
		
		CsOOperatoreSettore opSettoreMast=getCurrentOpSettore();
		s.setUserIns(opSettoreMast.getCsOOperatore().getUsername());
		s.setDtIns(new Date());
		return s;
	}

	//TODO frida completare 23 campi: gli devo passare il master invece dell'intervento
	private CsIInterventoEseg valorizzaInterventoEseg(InterventoErogazHistoryRowBean row) {
		CsIInterventoEseg nuovoIntEseg = new CsIInterventoEseg();
		CsOOperatoreSettore opSettoreMast=getCurrentOpSettore();
		
		nuovoIntEseg.setUserIns(opSettoreMast.getCsOOperatore().getUsername());
		nuovoIntEseg.setDtIns(row.getIntEseg().getDtIns());
		
		nuovoIntEseg.setNomeOperatoreErog(row.getIntEseg().getNomeOperatoreErog());		
		if(nuovoIntEseg.getNomeOperatoreErog()==null || nuovoIntEseg.getNomeOperatoreErog().trim().isEmpty())
			nuovoIntEseg.setCsOOperatoreSettore(opSettoreMast);				
		
		if(row.getIntEseg().getCsOOperatoreSettore()!=null)
			nuovoIntEseg.setCsOOperatoreSettore(row.getIntEseg().getCsOOperatoreSettore());						
	
		nuovoIntEseg.setEnteOperatoreErogante(row.getIntEseg().getEnteOperatoreErogante());
		
		nuovoIntEseg.setDataEsecuzione(row.getIntEseg().getDataEsecuzione());
		nuovoIntEseg.setDataEsecuzione(row.getIntEseg().getDataEsecuzioneA());
		nuovoIntEseg.setCsIInterventoEsegMast(this.nuovoIntEsegMast);

		nuovoIntEseg.setNote(row.getIntEseg().getNote());
		nuovoIntEseg.setStato(row.getIntEseg().getStato());
			
		nuovoIntEseg.setSpesa(row.getIntEseg().getSpesa());		
		nuovoIntEseg.setPercGestitaEnte(row.getIntEseg().getPercGestitaEnte());
		nuovoIntEseg.setValoreGestitaEnte(row.getIntEseg().getValoreGestitaEnte());
		nuovoIntEseg.setCompartUtenti(row.getIntEseg().getCompartUtenti());
		nuovoIntEseg.setCompartAltre(row.getIntEseg().getCompartAltre());
		nuovoIntEseg.setCompartSsn(row.getIntEseg().getCompartSsn());
		nuovoIntEseg.setNoteAltreCompart(row.getIntEseg().getNoteAltreCompart());				
					
		nuovoIntEseg.setNote(row.getIntEseg().getNote());	
		
		CsIValQuota valQuota = row.getIntEseg().getCsIValQuota();
		if(valQuota!=null && valQuota.getValQuota()!=null && valQuota.getNote()!=null){
			valQuota.setUserIns(nuovoIntEseg.getUserIns());
			valQuota.setDtIns(nuovoIntEseg.getDtIns());
			nuovoIntEseg.setCsIValQuota(valQuota);
		}else 
			nuovoIntEseg.setCsIValQuota(null);
				
		return nuovoIntEseg;
	}

	private void valorizzaInterventoEsegMast(CsIIntervento intervento) {		
		CsOOperatoreSettore opSettore = getCurrentOpSettore();
		CsOSettore settoreErogante = getSettore(this.selSettoreEroganteId);
		nuovoIntEsegMast.setSettoreErogante(settoreErogante);
		
		if(nuovoIntEsegMast.getId()>0){
			nuovoIntEsegMast.setUsrMod(opSettore.getCsOOperatore().getUsername());
			nuovoIntEsegMast.setDtMod(new Date());
		}else{
			nuovoIntEsegMast.setCsOOperatoreSettore(opSettore);
			nuovoIntEsegMast.setUserIns(opSettore.getCsOOperatore().getUsername());
			nuovoIntEsegMast.setDtIns(new Date());
		}
		
		//nuovoIntEsegMast.setFfOrigineId(this.origineFinanziamentoId);
		//nuovoIntEsegMast.setFfProgettoDescrizione(this.progettoDescrizione); //SISO-522  
		
		nuovoIntEsegMast.setCsCTipoIntervento(erogazioneHistory.getCsCTipoIntervento());
		nuovoIntEsegMast.setCategoriaSocialeId(erogazioneHistory.getCategoriaSocialeId());
		nuovoIntEsegMast.setCsIInterventoCustom(erogazioneHistory.getCsCTipoInterventoCustom());
		/* altri campi riempiti direttamente da erogazioni.xhtml*/

		/* cancello i valori che sono solo in visualizzazione */
		nuovoIntEsegMast.setFlagSpesaCalc(calcolaSpesaDaDettaglio);
		nuovoIntEsegMast.setFlagCompartCalc(calcolaCompartDaDettaglio);
		
		/* == SISO- SM == */
		nuovoIntEsegMast.setRuoloEnte(ruoloEnteGestoreSpesa.name());
	//	nuovoIntEsegMast.setEnteGestoreSpesa(enteGestoreDellaSpesa);
		/* --===-- */
		
		
	/*	if(this.calcolaSpesaDaDettaglio){
			nuovoIntEsegMast.setSpesa(null);
			nuovoIntEsegMast.setValoreGestitaEnte(null);
			nuovoIntEsegMast.setPercGestitaEnte(null);
		}
		if(this.calcolaCompartDaDettaglio){
			nuovoIntEsegMast.setCompartUtenti(null);
			nuovoIntEsegMast.setCompartAltre(null);
			nuovoIntEsegMast.setCompartSsn(null);
		}*/
 
		//inizio SISO-500
		if (intervento != null) {
			nuovoIntEsegMast.setCsIIntervento(intervento);
			//nuovoIntEsegMast.setCsCTipoIntervento(intervento.getCsRelSettCsocTipoInter().getCsRelCatsocTipoInter().getCsCTipoIntervento());
			//TOGLIERE quello che segue??
//			long idRelazione = new LinkedList<CsFlgIntervento>( intervento.getCsFlgInterventos()).get(0).getCsDDiario().getId();
//			dto.setObj(idRelazione);
//			CsDRelazione csRelazione = diarioService.findRelazioneLazyById(dto);
			//TODO frida, valutare se rimettere
			//nuovoIntEseg.getMaster().setCsDRelazione(csRelazione);
		}	
		//fine SISO-500
	}
	
	private void valorizzaIntEsegValori(InterventoErogazHistoryRowBean row, CsIInterventoEseg nuovoIntEseg) {
		List<CsIInterventoEsegValore> listaIntEsegValore = new LinkedList<CsIInterventoEsegValore>();

		for (InterventoErogazAttrBean cell : row.getAttrCells()) {
			CsIInterventoEsegValore intEsegValore = new CsIInterventoEsegValore();
			if (cell.isPersist() && cell.isAbilitato()) {
				CsCfgAttrUnitaMisura attUm = cell.getAttrUnitaMisuraSelezionata();
				
				intEsegValore.setCsAttributoUnitaMisura(attUm);
				intEsegValore.setCsIInterventoEseg(nuovoIntEseg);
				intEsegValore.setValore(cell.getValue()!=null ? cell.getValue().toString(): null);
				listaIntEsegValore.add(intEsegValore);
			}
		}

		nuovoIntEseg.setDataEsecuzione(row.getDataErogazione());
		nuovoIntEseg.setDataEsecuzioneA(row.getDataErogazioneA());
		nuovoIntEseg.setCsIInterventoEsegValores(listaIntEsegValore);
	}

	private void buildInterventoErogazzAttList(Long tipoInterventoId) {
		BaseDTO bdto = new BaseDTO();
		fillEnte(bdto);
		bdto.setObj(tipoInterventoId);
		//carica configurazioine by tipo intervento
		HashMap<Long, ErogStatoCfgDTO> mappaEseg = interventoService.findConfigIntEsegByTipoIntervento(bdto);
		
		if(mappaEseg.isEmpty())
			logger.warn("Lista 'IntEsegAttrBean' vuota per il tipo intervento:"+tipoInterventoId);
		
		//Creo la mappa
		mappaErogStatoAttr = new HashMap<Long,List<InterventoErogazAttrBean>>();
		Iterator<Long> it = mappaEseg.keySet().iterator();
		while(it.hasNext()){
			Long idStato = (Long)it.next();
			List<IntEsegAttrBean> attrs = mappaEseg.get(idStato).getListaAttributi();
			List<InterventoErogazAttrBean> erogaziones = new ArrayList<InterventoErogazAttrBean>();
			for(IntEsegAttrBean att : attrs){
				InterventoErogazAttrBean intEr = new InterventoErogazAttrBean(att);
				erogaziones.add(intEr);
			}
			mappaErogStatoAttr.put(idStato, erogaziones);	
		}
	}

	
			
	protected CsOOperatoreSettore getOperatoreErogSelezionato() {
		if (operatoreErogId != null) {
			for (CsOOperatoreSettore dOrg : listaOperSettore) {
				if (dOrg.getCsOOperatore().getId() == operatoreErogId.longValue())
					return dOrg;
			}
		}
		return null;
	}	
	
	
	private void loadListaOperatoriEroganti(Long settoreId){
		try {
			listaOperSettore = new ArrayList<CsOOperatoreSettore>();
			listaOperAnagrafica= new ArrayList<CsOOperatoreAnagrafica>();
			
			if(settoreId!=null){
				//dal settore la lista degli operatori
				OperatoreDTO oDto = new OperatoreDTO();
				fillEnte(oDto);
				oDto.setIdSettore(settoreId);//(long)22852);//getCurrentOpSettore().getCsOSettore());
				listaOperSettore=operatoreService.findOperatoreSettoreBySettore(oDto);
				
				/* ottenere l'anagrafica (nome e cognome) per l'operatore */
				List<CsOOperatoreAnagrafica> listaAllAnagrOperatori=new ArrayList<CsOOperatoreAnagrafica>();
				listaAllAnagrOperatori = operatoreService.findAllOperatoreAnagrafica();
				for (CsOOperatoreAnagrafica csOOperatoreAnagrafica : listaAllAnagrOperatori) {
					boolean found=false;
					for (CsOOperatoreSettore c : listaOperSettore) {
						if(c.getCsOOperatore().getId().compareTo(csOOperatoreAnagrafica.getId())==0){
							found=true;
							break;
						}	
					}
					if(found)
						listaOperAnagrafica.add(csOOperatoreAnagrafica);
				}			
			}
			
		} catch (Exception e) {			
			logger.error(e);
		}
	}

	private void loadListaStatiInterventi(Long tipoInterventoId) {
		List<String> listaTipoStato = getListaTipoStatoErogazioneByPermessoErogazione();

		BaseDTO bdto = new BaseDTO();
		fillEnte(bdto);
		bdto.setObj(listaTipoStato);
		bdto.setObj2(tipoInterventoId);
		listaStatiErogazione = interventoService.getListaIntEsegStatoByTipiStato(bdto);
	}
	
	private void setPrimiDatiInNuovoIntEsegTemp(){	
		nuovocsIntEseg.setDataEsecuzione(dataErog);
		nuovocsIntEseg.setDataEsecuzioneA(dataErogA);
		nuovocsIntEseg.setCsOOperatoreSettore(getOperatoreErogSelezionato());
		nuovocsIntEseg.setStato(getStatoSelezionato());
		nuovocsIntEseg.setDtIns(new Date());
	}
	
	public CsCfgIntEsegStato getStatoSelezionato() {
		if (statoSelezionatoId != null) {
			for (CsCfgIntEsegStato stato : listaStatiErogazione) {
				if (stato.getId() == statoSelezionatoId)
					return stato;
			}
		}
		return null;
	}

	protected boolean validaAttributi(boolean rendicontoPeriodico,
				String flagPeriodoRipartiz, boolean visualizzaIntervalloDateErogazione) {   //SISO-556
		boolean bOk = true;
		
		bOk = validaTestata();
		
		if( this.dataErog == null ){
			addError("Erogazioni: inserire la data di erogazione", "");
			bOk = false;			
		}else{
			//INIZIO SISO-556 
			if(rendicontoPeriodico){  
				
				if (visualizzaIntervalloDateErogazione) {
					if(this.dataErogA==null){
						addError("Erogazioni: inserire la data di fine erogazione", "");
						bOk = false;	
					}else{
						
						Calendar dataA = Calendar.getInstance();
						dataA.setTime(dataErogA);	
						
						Calendar dataDa = Calendar.getInstance();
						dataDa.setTime(dataErog);
						
						if (DataModelCostanti.CsIQuotaRipartiz.FLAG_PERIODO_RIPARTIZ_MENSILE.equals(flagPeriodoRipartiz)) {
							//sono accettati i periodi uguali o superiori a 7 giorni, dataA e dataDa comprese
							//quindi ad esempio dal 1 al 31 dicembre è una selezione valida
							dataDa.add(Calendar.MONTH, 1);
							dataDa.add(Calendar.DAY_OF_MONTH, -1);
							
							if(dataA.before(dataDa)){
								addError("Erogazioni: l'intervallo tra la data di inizio e fine deve essere maggiore di un mese", "");
								bOk = false;	
							}
						} else if (DataModelCostanti.CsIQuotaRipartiz.FLAG_PERIODO_RIPARTIZ_SETTIMANALE.equals(flagPeriodoRipartiz)) {
							//sono accettati i periodi uguali o superiori a 7 giorni, dataA e dataDa comprese
							//quindi ad esempio dal 1 al 7 dicembre è una selezione valida
							
							dataDa.add(Calendar.WEEK_OF_YEAR, 1);
							dataDa.add(Calendar.DAY_OF_MONTH, -1);
							
							if(dataA.before(dataDa)){
								addError("Erogazioni: l'intervallo tra la data di inizio e fine deve essere maggiore di una settimana", "");
								bOk = false;	
							}
						}					
						 
					}
				}

			}
			//FINE SISO-556

		}
		

		//INIZIO SISO-556
//		if ( DataModelCostanti.TipoStatoErogazione.EROGATIVO.equals( getStatoSelezionato().getTipo() )  ) {
//
//			if (visualizzaIntervalloDateErogazione) {
//				for (InterventoErogazHistoryRowBean rowBean : erogazioneHistory.getRows()) {
//					if (!controllaGiorni(dataErog, dataErogA,
//							rowBean.getDataErogazione(), rowBean.getDataErogazioneA()) ) {
//						addError("Non possono esserci 2 righe di erogazione nello stesso giorno", "");
//						bOk = false;			
//					}
//				}
//				
//			} else { 
//				for (InterventoErogazHistoryRowBean rowBean : erogazioneHistory.getRows()) {
//					if ( dataErog.equals(rowBean.getDataErogazione()) ) {
//						addError("Non possono esserci 2 righe di erogazione nello stesso giorno", "");
//						bOk = false;			
//					}
//				}
//			}
//			
//			
//		}
		//FINE SISO-556
		
		if( this.nuovoIntEsegMast.getDescInterventoEseg() == null || this.nuovoIntEsegMast.getDescInterventoEseg().isEmpty() ){
			addError("Erogazioni: inserire la descrizione", "");
			bOk = false;
		}
						
		CsCfgIntEsegStato statoSelezionato = getStatoSelezionato();
	    if(statoSelezionato!=null){
			for( InterventoErogazAttrBean attr : this.getErogaziones(statoSelezionato.getId())){
				if( !attr.isValid() ){
					addError("Erogazioni: " + attr.getRequiredMessage(),"");
					bOk = false;
				}
			}
	    }
		return bOk;
	}
	
	/**
	 *  ritorna true se i periodi compresi tra  (dataErog, dataErogA) e (dataErog2, dataErogA2) non si intersecano
	 */
	static boolean controllaGiorni(Date dataErog, Date dataErogA, 
			Date dataErog2, Date dataErogA2){  
		if (dataErogA.before(dataErog2)  ||  dataErogA2.before(dataErog)  ) {
			return true;
		} else{
			return false;
		}  
	}
	
	
	public void aggiungiAttributi(boolean rendicontoPeriodico,
			String flagPeriodoRipartiz, boolean  visualizzaIntervalloDateErogazione) {   //SISO-556
		if( !validaAttributi(rendicontoPeriodico, flagPeriodoRipartiz, visualizzaIntervalloDateErogazione) )
			return;		
			
		//TODO
		/* "nuovocsIntEseg" va in "erogazioneHistory.row", "row" verrà ricaricata prima del salvataggio da "valorizzaInterventoEseg()" */
		this.setPrimiDatiInNuovoIntEsegTemp();	
		CsCfgIntEsegStato stato = getStatoSelezionato();
		this.erogazioneHistory.addNew(getSettore(this.selSettoreEroganteId), nuovoIntEsegMast.getDescInterventoEseg(), stato , nuovocsIntEseg, mappaErogStatoAttr);

		//Clear
		for (InterventoErogazAttrBean attr : this.mappaErogStatoAttr.get(stato.getId())) attr.reset2default();
		this.resetOrganizAndNuovoIntEseg();	
		
		statoSelezionatoId=null;
		if(isErogazioniPresenti())
			testataDisabled=true;
		onStatoChanged();
	}
		
	
	public void onChangeTipoBeneficiario(AjaxBehaviorEvent event){
		
		if(this.getAltriSoggetti().isEmpty()){
			soggettoErogazione.setRiferimento(true);
			altriSoggetti.add(soggettoErogazione);
		}else{
			for(int i=this.getAltriSoggetti().size()-1; i>=0; i--){
				SoggettoErogazioneBean seb = this.getAltriSoggetti().get(i);
				if(!seb.isRiferimento()){
					this.getAltriSoggetti().remove(i);
				}
			}
		}
		
		//SISO-809
		if(this.nuovoIntEsegMast.getTipoBeneficiario().equals(ListaBeneficiari.GRUPPO)){
			this.addMessage(FacesMessage.SEVERITY_WARN, "Questa erogazione non verrà esportata nel casellario."); 
			this.esportazioneDiGruppo=true;
		}else{
			this.esportazioneDiGruppo=false;
		}
		
		initRisorsaFamiliareBean();
		
		
	}
	public void onResetSpeseComp(AjaxBehaviorEvent event){
		setBoolDisabledSpese();
	}
	
	

	//INIZIO SISO-524 
	

	private boolean erogazioniEsportate(List<CsIInterventoEseg> interEsegHistory2) {
		boolean res = false;
		for (CsIInterventoEseg csIInterventoEseg : interEsegHistory2) {
			if (erogazioneEsportata(csIInterventoEseg)) {
				res = true;
				break;
			}
		}
		return res;
	}
	

	private boolean erogazioneEsportata(CsIInterventoEseg csIInterventoEseg) {
		
		if (csIInterventoEseg.getId()!=null) {
			BaseDTO dto = new BaseDTO();
	    	fillEnte(dto);
	    	List<Long> list = new ArrayList<Long>();
	    	list.add(csIInterventoEseg.getId());
	    	dto.setObj(list);  
			
	    	return psExportService.findCsIPsExportByCsIInterventoEsegIds(dto).size()>0; 			
		} else {
			return false;
		}
	} 
	
	private boolean erogazioneEsportata(InterventoErogazHistoryRowBean row) {
		return erogazioneEsportata(row.getIntEseg());
	} 
	
	//FINE SISO-524 
	
	public void rimuoviRigaStorico(InterventoErogazHistoryRowBean row){
		
		//MODIFICA SISO-524 se l'erogazione è stata già esportata non ne consento la rimozione 
    	if (erogazioneEsportata(row)) { 
    		addMessage(FacesMessage.SEVERITY_ERROR,"Non è possibile eliminare la riga in questione, in quanto esportata nel casellario");
		} else {
			this.erogazioneHistory.rimuoviRiga(row);
			
			this.onChangeCalcolaSpeseTotali();
			this.onChangeCalcolaCompartecipazioniTot();
			
			if(!isErogazioniPresenti()){
				boolean erogatoreProprietario = this.isPermessoErogativo() && this.isOperatoreProprietario();
				if(erogatoreProprietario || isPermessoAutorizzativo())
					testataDisabled=false;
			}
		} 

	}
	

	public void onChangeValoriSpesaDettaglio(){

		//INIZIO SISO-556 
		boolean valid = true;
		if (nuovocsIntEseg.getValoreGestitaEnte()!= null ) {
			if (nuovocsIntEseg.getSpesa() == null) { 
				addMessage(FacesMessage.SEVERITY_ERROR,"Valorizzare il campo Spesa");
			} else if ( nuovocsIntEseg.getValoreGestitaEnte().compareTo(nuovocsIntEseg.getSpesa() ) > 0  ) { 
				addMessage(FacesMessage.SEVERITY_ERROR,"Valore Spesa Gestita Direttamente in dettaglio non corretto:inserire valore numerico intero o decimale (es.12,34), minore o uguale alla Spesa");
			}
		}
		
		if (valid) {
			if(this.calcolaSpesaDaDettaglio){
				Double spesaCalc = calcolaMasterSpesaTotale();
				Double percCalc =  calcolaMasterPercSpesa();
				Double valSpesa =  calcolaMasterValSpesa();
				
				nuovoIntEsegMast.setSpesa(new BigDecimal(spesaCalc));
				nuovoIntEsegMast.setPercGestitaEnte(new BigDecimal(percCalc));
				nuovoIntEsegMast.setValoreGestitaEnte(new BigDecimal(valSpesa));
			} 
		}

		//FINE SISO-556
		 
	}
	
	public void onChangeValoriCompartecipazioneDettaglio(){
		if(this.calcolaCompartDaDettaglio){
			Long utente = this.calcolaMasterCompUtenteTotale();
			Long ssn = this.calcolaMasterCompSSNTotale();
			Long altro = this.calcolaMasterCompAltroTotale();
			
			nuovoIntEsegMast.setCompartUtenti(new BigDecimal(utente));
			nuovoIntEsegMast.setCompartSsn(new BigDecimal(ssn));
			nuovoIntEsegMast.setCompartAltre(new BigDecimal(altro));
		}
	}
	
	public void onChangeCalcolaSpeseTotali(){
		
		if(!this.calcolaSpesaDaDettaglio){
			nuovocsIntEseg.setCsIValQuota(new CsIValQuota());
			nuovocsIntEseg.setSpesa(null);
			nuovocsIntEseg.setPercGestitaEnte(null);
			nuovocsIntEseg.setValoreGestitaEnte(null);
		}
			
		this.onChangeValoriSpesaDettaglio();
	}
	
	public void onChangeCalcolaCompartecipazioniTot(){
		if(!this.calcolaCompartDaDettaglio){
			nuovocsIntEseg.setCompartUtenti(null);
			nuovocsIntEseg.setCompartSsn(null);
			nuovocsIntEseg.setCompartAltre(null);
		}
		
		this.onChangeValoriCompartecipazioneDettaglio();
	}
	
	public Long calcolaMasterCompUtenteTotale(){
		long valore=0;
		
		for (InterventoErogazHistoryRowBean i : erogazioneHistory.getRows()) {
			if(i.getIntEseg().getCompartUtenti()!=null && i.getIntEseg().getCompartUtenti().compareTo(new BigDecimal(0))!=0)
					valore += i.getIntEseg().getCompartUtenti().doubleValue();
		}
		
		if(nuovocsIntEseg.getCompartUtenti()!=null)
		  valore+=nuovocsIntEseg.getCompartUtenti().doubleValue();
		
		return valore;
	}
	
	public Long calcolaMasterCompSSNTotale(){
		long valore=0;
	
		for (InterventoErogazHistoryRowBean i : erogazioneHistory.getRows()) {
			if(i.getIntEseg().getCompartSsn()!=null && i.getIntEseg().getCompartSsn().compareTo(new BigDecimal(0))!=0)
					valore += i.getIntEseg().getCompartSsn().doubleValue();
		}
		
		if(nuovocsIntEseg.getCompartSsn()!=null)
		  valore+=nuovocsIntEseg.getCompartSsn().doubleValue();
		
		return valore;
	}
	
	public Long calcolaMasterCompAltroTotale(){
		long valore=0;
		
		for (InterventoErogazHistoryRowBean i : erogazioneHistory.getRows()) {
			if(i.getIntEseg().getCompartAltre()!=null && i.getIntEseg().getCompartAltre().compareTo(new BigDecimal(0))!=0)
					valore += i.getIntEseg().getCompartAltre().doubleValue();
		}
		
		if(nuovocsIntEseg.getCompartAltre()!=null)
		  valore+=nuovocsIntEseg.getCompartAltre().doubleValue();
		
		return valore;
	}
	
	public Double calcolaMasterSpesaTotale(){
		double valore=0;
		
		for (InterventoErogazHistoryRowBean i : erogazioneHistory.getRows()) {
			if(i.getIntEseg().getSpesa()!=null && i.getIntEseg().getSpesa().compareTo(new BigDecimal(0))!=0)
					valore += i.getIntEseg().getSpesa().doubleValue();
		}
		
		if(nuovocsIntEseg.getSpesa()!=null)
		  valore+=nuovocsIntEseg.getSpesa().doubleValue();
		
		return valore;
	}
	
	public Double calcolaMasterValSpesa(){
		
		double valore=0;
		
		for (InterventoErogazHistoryRowBean i : erogazioneHistory.getRows()) {
			boolean valoreDef = i.getIntEseg().getValoreGestitaEnte()!=null && i.getIntEseg().getValoreGestitaEnte().doubleValue()>0;
			boolean percDef = i.getIntEseg().getPercGestitaEnte()!=null && i.getIntEseg().getPercGestitaEnte().doubleValue()>0;
		  if(valoreDef && !percDef)
			valore+=i.getIntEseg().getValoreGestitaEnte().doubleValue();
		  else if(!valoreDef && percDef){
			  if(i.getIntEseg().getSpesa()!=null && i.getIntEseg().getSpesa().compareTo(new BigDecimal(0))!=0){
				  Double percValore = i.getIntEseg().getSpesa().doubleValue()*i.getIntEseg().getPercGestitaEnte().doubleValue()/100;
				  valore+= percValore;
			  }
			 }
		  }
			
		boolean valoreDef = nuovocsIntEseg.getValoreGestitaEnte()!=null && nuovocsIntEseg.getValoreGestitaEnte().doubleValue()>0;
		boolean percDef = nuovocsIntEseg.getPercGestitaEnte()!=null && nuovocsIntEseg.getPercGestitaEnte().doubleValue()>0;
		  if(valoreDef && !percDef)
			valore+=nuovocsIntEseg.getValoreGestitaEnte().doubleValue();
		  else if(!valoreDef && percDef){
			  if(nuovocsIntEseg.getSpesa()!=null && nuovocsIntEseg.getSpesa().compareTo(new BigDecimal(0))!=0){
				  Double percValore = nuovocsIntEseg.getSpesa().doubleValue()*nuovocsIntEseg.getPercGestitaEnte().doubleValue()/100;
				  valore+= percValore;
			  }
		}

		return valore;
	}
	
	public Double calcolaMasterPercSpesa(){
		double valore = 0;
		double valSpesaEnte = calcolaMasterValSpesa();
		double spesaTotale = this.calcolaMasterSpesaTotale();
	
		if(spesaTotale>0)
			valore = valSpesaEnte*100/spesaTotale;
		return valore;
	}
	
	
	public void onSettEroganteTestaChange(AjaxBehaviorEvent event){
		loadListaOperatoriEroganti(this.selSettoreEroganteId);
	}
	

	//frida
	public void setTestaPercValue(AjaxBehaviorEvent event){
		
		if(testaPercOrValue.equals(DataModelCostanti.ErogazioneInterventoConst.TESTATA_SPESA_PERCENTUALE)){			
			isSpesaValTesta=false;		
			if(!calcolaSpesaDaDettaglio){ //se la spesa è già stata settata nel dettaglio vuol dire che nella testata c'è la somma dei dettagli e non deve essere cancellata
				nuovoIntEsegMast.setValoreGestitaEnte(null);
				isSpesaPercTesta=true;
			}				
		}else{			
			isSpesaPercTesta=false;			
			if(!calcolaSpesaDaDettaglio){
				nuovoIntEsegMast.setPercGestitaEnte(null);
				isSpesaValTesta=true;
			}				
		}
	}	
	//frida
	public void setDettaPercValue(AjaxBehaviorEvent event){
		/*boolean _valPerNonSettateNellaTestata=false;
		if(nuovoIntEsegMast.getValoreGestitaEnte()!=null)
			if(nuovoIntEsegMast.getValoreGestitaEnte().compareTo(new BigDecimal(0))!=0)
				_valPerNonSettateNellaTestata=true;
		if(nuovoIntEsegMast.getPercGestitaEnte()!=null)
			if( nuovoIntEsegMast.getPercGestitaEnte().compareTo(new BigDecimal(0))!=0)
				_valPerNonSettateNellaTestata=true;*/
		
		if(dettaPercOrValue.equals(DataModelCostanti.ErogazioneInterventoConst.DETTAGLIO_SPESA_PERCENTUALE)){
			nuovocsIntEseg.setValoreGestitaEnte(null);
			isSpesaValDetta=false;
			if(this.calcolaSpesaDaDettaglio)
				isSpesaPercDetta=true;
		}else{
			nuovocsIntEseg.setPercGestitaEnte(null);
			isSpesaPercDetta=false;
			if(this.calcolaSpesaDaDettaglio)
				isSpesaValDetta=true;
		}
	}
	//frida : o si scelgono organizzazione ed operatore fra una lista oppure si inseriscono un ente e un operatore a mano
	public void setOperErogInputs(AjaxBehaviorEvent event){ 
		if(operatoreScegliOrInserisci.equals(DataModelCostanti.ErogazioneInterventoConst.INSERISCI_OPERATORE)){
			operatoreErogId=Long.valueOf(0);
			nuovocsIntEseg.setCsOOperatoreSettore(null);
		}else{
			CsOOperatoreSettore op=getCurrentOpSettore();
			if(op!=null){
				this.operatoreErogId=op.getCsOOperatore().getId();
				nuovocsIntEseg.setCsOOperatoreSettore(op);
			}	
			
			nuovocsIntEseg.setNomeOperatoreErog("");
			nuovocsIntEseg.setEnteOperatoreErogante("");
		}
	}
	
	public void onStatoChanged() { 
		
		CsCfgIntEsegStato statoSelezionato = getStatoSelezionato();
		if (statoSelezionato != null) {
			visualizzaAttributi = TipoStatoErogazione.EROGATIVO.equals(statoSelezionato.getTipo());
		}
		else
			visualizzaAttributi = false;	
		enabledAggiungi = statoSelezionato != null;		
		
		tariffaLocale=null;
	}

	public void toString(StringBuilder sb) {
		sb.append("**********************************************\n");
		sb.append("***********DATI EROGAZIONE INTERVENTO*********\n");
		sb.append("**********************************************\n");
		sb.append(String.format("%s%s\t\t", "Data:", this.dataErog) + "\n");
		sb.append(String.format("%s%s\t\t", "Stato Selezione= ", "id: " + getStatoSelezionato().getId()) + "\n");
		sb.append("Lista Stati: " + "\n");
		for (CsCfgIntEsegStato stato : listaStatiErogazione) {
			sb.append(String.format("\t" + "%s%s\t\t", "Id " + stato.getId() + ": ", stato.getNome()) + "\n");
			
			sb.append("**************  LISTA ATTRIBUTI  *************\n");
			for (InterventoErogazAttrBean b : getErogaziones(stato.getId()))
				b.toString(sb);
		}
		sb.append(String.format("%s%s\t\t", "Note :", this.nuovoIntEsegMast.getDescInterventoEseg()) + "\n");

	

		sb.append("**************  STORICO  *********************\n");
		this.erogazioneHistory.toString(sb);
	}

	public void setInterventoService(AccessTableInterventoSessionBeanRemote interventoService) {
		this.interventoService = interventoService;
		this.erogazioneHistory.setInterventoService(interventoService);
	}

	public boolean  getEnabledAggiungi(){
		return enabledAggiungi;
	}
	public Date getDataErog() {		
		return dataErog;
	}

	public void setDataErog(Date dataErog) {
		this.dataErog = dataErog;
	}

	public List<CsCfgIntEsegStato> getListaStatiErogazione() {
		return listaStatiErogazione;
	}

	public InterventoErogazHistoryBean getErogazioneHistory() {
		return erogazioneHistory;
	}

	public Long getStatoSelezionatoId() {
		return statoSelezionatoId;
	}

	public void setStatoSelezionatoId(Long selectedStato) {
		this.statoSelezionatoId = selectedStato;
	}

	public boolean isVisualizzaInterventoData() {
		return visualizzaInterventoData;
	}

	public SoggettoErogazioneBean getSoggettoErogazioneBean() {
		return soggettoErogazione;
	}

	public String getNomeTipoErogazione() {
		return this.tipoIntervento!=null ? this.tipoIntervento.getDescrizione() : "";
	}
	
	public String getNomeTipoCustEroga() {
		return this.tipoIntCustom!=null ? this.tipoIntCustom.getDescrizione() : "";
	}
		
	public boolean isVisualizzaAttributi() {
		return visualizzaAttributi;
	}

	//SISO-556 aggiunto il fltro su frequenzaAccessoAlServizio
	public List<SelectItem> getLstSelItemStatiErog(String frequenzaAccessoAlServizio) {
	if(listaStatiErogazione!=null){
		lstSelItemStatiErog = new ArrayList<SelectItem>();
		for(CsCfgIntEsegStato stato : listaStatiErogazione){
			
			//SISO-556   In caso di servizio Unatantum visualizzo solo Erogazione e chiusura" e non "Erogazione"	
			boolean rimuovoStato = false;
			if (frequenzaAccessoAlServizio!= null 
					&& frequenzaAccessoAlServizio.equals(TIPO_FREQUENZA_SERVIZIO.UNATANTUM.getCodice() )
					&& TipoStatoErogazione.EROGATIVO.equals(stato.getTipo() )
					&&  DataModelCostanti.CsCfgIntEsegStato.FLAG_CHIUDI_APERTO == stato.getFlagChiudi()) { 
				rimuovoStato =true;
			}
			
			if (!rimuovoStato) {
				SelectItem si = new SelectItem(stato.getId(),stato.getNome());
				lstSelItemStatiErog.add(si);				
			}
			//FINE SISO-556
			
			
		}
	}
	
	return lstSelItemStatiErog;
}
	
	
	public void setLstSelItemStatiErog(List<SelectItem> lstSelItemStatiErog) {
		this.lstSelItemStatiErog = lstSelItemStatiErog;
	}

	public boolean isDisabilitaOrganizzazione() {
		return disabilitaOrganizzazione;
	}

	public void setDisabilitaOrganizzazione(boolean disabilitaOrganizzazione) {
		this.disabilitaOrganizzazione = disabilitaOrganizzazione;
	}

	public CsCTipoInterventoCustom getTipoIntCustom() {
		return tipoIntCustom;
	}

	public void setTipoIntCustom(CsCTipoInterventoCustom tipoIntCustom) {
		this.tipoIntCustom = tipoIntCustom;
	}
	
	public CsIInterventoEsegMast getNuovoIntEsegMast() {
		return nuovoIntEsegMast;
	}

	public void setNuovoIntEsegMast(CsIInterventoEsegMast nuovoIntEsegMast) {
		this.nuovoIntEsegMast = nuovoIntEsegMast;
	}

	public List<CsOOperatoreAnagrafica> getListaOperAnagrafica() {
		return listaOperAnagrafica;
	}

	public Boolean getTestataDisabled() {
		return testataDisabled;
	}

	public void setTestataDisabled(Boolean testataDisabled) {
		this.testataDisabled = testataDisabled;
	}

	public CsIInterventoEseg getNuovocsIntEseg() {
		return nuovocsIntEseg;
	}

	public String getTestaPercOrValue(){
		return testaPercOrValue;
	}
	
	public void setTestaPercOrValue(String testaPercOrValue){
		this.testaPercOrValue=testaPercOrValue;
	}	
	
	public String getDettaPercOrValue() {
		return dettaPercOrValue;
	}

	public void setDettaPercOrValue(String dettaPercOrValue) {
		this.dettaPercOrValue = dettaPercOrValue;
	}

	public String getOperatoreScegliOrInserisci() {
		return operatoreScegliOrInserisci;
	}

	public void setOperatoreScegliOrInserisci(String operatoreScegliOrInserisci) {
		this.operatoreScegliOrInserisci = operatoreScegliOrInserisci;
	}

	public Long getOperatoreErogId() {
		return operatoreErogId;
	}

	public void setOperatoreErogId(Long operatoreErogId) {
		this.operatoreErogId = operatoreErogId;
	}


	public Date getDataAttivazioneDa() {
		return dataAttivazioneDa;
	}

	public void setDataAttivazioneDa(Date dataAttivazioneDa) {
		this.dataAttivazioneDa = dataAttivazioneDa;
	}

	public boolean isDentroFascicolo() {
		return dentroFascicolo;
	}
	
	public boolean isUnicoBeneficiario() {
		return nuovoIntEsegMast!=null ? DataModelCostanti.ListaBeneficiari.INDIVIDUALE.equalsIgnoreCase(nuovoIntEsegMast.getTipoBeneficiario()) : true;
	}
	
	public boolean isNucleoBeneficiario() {
		return nuovoIntEsegMast!=null ? DataModelCostanti.ListaBeneficiari.NUCLEO.equalsIgnoreCase(nuovoIntEsegMast.getTipoBeneficiario()) : false;
	}
	
	public boolean isGruppoBeneficiario() {
		return nuovoIntEsegMast!=null ? DataModelCostanti.ListaBeneficiari.GRUPPO.equalsIgnoreCase(nuovoIntEsegMast.getTipoBeneficiario()) : false;
	}

	public List<String> getListaOpzioniBen() {
		listaOpzioniBen =new ArrayList<String>();
		
		listaOpzioniBen.add(DataModelCostanti.ListaBeneficiari.INDIVIDUALE);
		listaOpzioniBen.add(DataModelCostanti.ListaBeneficiari.NUCLEO);
		
		if(!this.dentroFascicolo)
			listaOpzioniBen.add(DataModelCostanti.ListaBeneficiari.GRUPPO);
		
		return listaOpzioniBen;
	}

	public Boolean getErogazionePossibile() {
		return erogazionePossibile|| !this.dentroFascicolo;
	}

	public void setErogazionePossibile(Boolean erogazionePossibile) {
		this.erogazionePossibile = erogazionePossibile;
	}

	public Boolean getIsSpesaPercTesta() {
		return isSpesaPercTesta;
	}

	public Boolean getIsSpesaPercDetta() {
		return isSpesaPercDetta;
	}

	public Boolean getIsSpesaValTesta() {
		return isSpesaValTesta;
	}

	public Boolean getIsSpesaValDetta() {
		return isSpesaValDetta;
	}

	public void setIsSpesaPercTesta(Boolean isSpesaPercTesta) {
		this.isSpesaPercTesta = isSpesaPercTesta;
	}

	public void setIsSpesaPercDetta(Boolean isSpesaPercDetta) {
		this.isSpesaPercDetta = isSpesaPercDetta;
	}

	public void setIsSpesaValTesta(Boolean isSpesaValTesta) {
		this.isSpesaValTesta = isSpesaValTesta;
	}

	public void setIsSpesaValDetta(Boolean isSpesaValDetta) {
		this.isSpesaValDetta = isSpesaValDetta;
	}

	public Boolean getCalcolaSpesaDaDettaglio() {
		return calcolaSpesaDaDettaglio;
	}

	public Boolean getCalcolaCompartDaDettaglio() {
		return calcolaCompartDaDettaglio;
	}

	public void setCalcolaSpesaDaDettaglio(Boolean calcolaSpesaDaDettaglio) {
		this.calcolaSpesaDaDettaglio = calcolaSpesaDaDettaglio;
	}

	public void setCalcolaCompartDaDettaglio(Boolean calcolaCompartDaDettaglio) {
		this.calcolaCompartDaDettaglio = calcolaCompartDaDettaglio;
	}
	
//	public boolean isAbilitaCheckCalcolo(){
//		boolean storicoVuoto = this.erogazioneHistory.getRows().isEmpty();
//		return storicoVuoto; //TODO: Abilito anche quando non è vuoto, ma non sono state inserite erogazioni!
//	
//	}

	public UserSearchBean getBeneficiarioSearchBean() {
		return beneficiarioSearchBean;
	}

	public void setBeneficiarioSearchBean(UserSearchBean beneficiarioSearchBean) {
		this.beneficiarioSearchBean = beneficiarioSearchBean;
	}
	
	public void addSoggettoFamiliare(){
		
		RisorsaFamDTO r = this.risorsaFamBean.getSelRisorsa();
		
		if(r == null) {
			addWarning("Scegliere un soggetto tra i parenti/conoscenti del titolare", "");
			return;
		}
		if(r.getSoggettoCaso()==null){
			addWarning("Non è possibile aggiugere il soggetto selezionato: non ha cartella sociale e/o dati sociali attivi", "");
			return;
		}
		
		SoggettoErogazioneBean sero = null;
		if(r!=null && r.getSoggettoCaso()!=null){
			
			CsASoggettoLAZY s = r.getSoggettoCaso();
			sero = new SoggettoErogazioneBean(s, false);
			
			this.getAltriSoggetti().add(sero);
			
		}
	
	}
	
	public void addSoggettoManuale(){
		
		boolean valido = true;
		if(this.altroSoggettoTmp.getCodiceFiscale()==null || altroSoggettoTmp.getCodiceFiscale().isEmpty()){
			addMessage(FacesMessage.SEVERITY_ERROR,"Inserire il codice fiscale del nuovo beneficiario");
			valido=false;
		}
		
		if(this.altroSoggettoTmp.getCognome()==null || altroSoggettoTmp.getCognome().isEmpty()){
			addMessage(FacesMessage.SEVERITY_ERROR,"Inserire il cognome del nuovo beneficiario");
			valido=false;
		}
		
		if(this.altroSoggettoTmp.getNome()==null || altroSoggettoTmp.getNome().isEmpty()){
			addMessage(FacesMessage.SEVERITY_ERROR,"Inserire il nome del nuovo beneficiario");
			valido=false;
		}
		
		if(this.altroSoggettoTmp.getCittadinanza()==null || altroSoggettoTmp.getCittadinanza().isEmpty()){
			addMessage(FacesMessage.SEVERITY_ERROR,"Inserire la cittadinanza del nuovo beneficiario");
			valido=false;
		}
		
		if(this.altroSoggettoTmp.getAnnoNascita()==null || altroSoggettoTmp.getAnnoNascita()<1900){
			addMessage(FacesMessage.SEVERITY_ERROR,"Inserire l'anno di nascita del nuovo beneficiario");
			valido=false;
		}
				
		if(valido && !isSoggettoPresente(this.altroSoggettoTmp.getCodiceFiscale())){
			this.getAltriSoggetti().add(this.altroSoggettoTmp);
			this.altroSoggettoTmp=new SoggettoErogazioneBean(false);
		}else
			addWarning("Non è possibile inserire il soggetto come beneficiario","Il codice fiscale del soggetto selezionato è già presente");
		
	}
	


	public void addDaAnagrafeWrapper() {
		
		DatiUserSearchBean s = beneficiarioSearchBean.getSelSoggetto();
		
		if(s == null) {
			addWarning("Scegliere un soggetto o inserirlo manualmente", "");
			return;
		}
		
		SoggettoErogazioneBean sero = null;
		if(s!=null){
			if(s.getId().trim().startsWith("SANITARIA")){
				PersonaFindResult p = (PersonaFindResult)s.getSoggetto();
				String nazionalita = getCittadinanzaByCodice(p.getCodIstatCittadinanza());
				sero = new SoggettoErogazioneBean(p.getCognome(), p.getNome(), p.getCodfisc(), nazionalita, p.getDataNascita(), false);
			}else{ 
				SitDPersona p = (SitDPersona) s.getSoggetto();
				if(p.getDataMor()!=null && p.getDataMor().before(new Date())){
					addWarning("Non è possibile inserire il soggetto come beneficiario","Il soggetto selezionato è deceduto il "+ddMMyyyy.format(p.getDataMor()));
					return;
				}
				
				sero = new SoggettoErogazioneBean(p,getCittadinanzaByIdExtStato(p.getIdExtStato()), false);
			}
			
			if(sero!=null){
				if(!isSoggettoPresente(sero.getCodiceFiscale())){
					CsASoggettoLAZY caso = getSchedaCSByCF(sero.getCodiceFiscale());
					sero.setCsASoggetto(caso);
				
					this.getAltriSoggetti().add(sero);
				}else{
					addWarning("Non è possibile inserire il soggetto come beneficiario","Il codice fiscale del soggetto selezionato è già presente");
					return;
				}
			}
		}
		beneficiarioSearchBean=new UserSearchBean();
	}
	
	private boolean isSoggettoPresente(String cf){
		boolean trovato = false;
		if(!StringUtils.isBlank(cf)){
			int i =0;
			while(i<this.getAltriSoggetti().size() && !trovato){
				if(this.getAltriSoggetti().get(i).getCodiceFiscale().equals(cf))
					trovato = true;
				i++;
			}
		}
		return trovato;
	}
	
	public CsASoggettoLAZY getSchedaCSByCF(String cf){
		BaseDTO dto = new BaseDTO();
    	fillEnte(dto);
    	dto.setObj(cf);  
    	try {
    		AccessTableSoggettoSessionBeanRemote soggettiService = (AccessTableSoggettoSessionBeanRemote) ClientUtility.getEjbInterface(
    				"CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
    		return soggettiService.getSoggettoByCF(dto);
    		
    	} catch(NamingException e) {
    		logger.error(e.getMessage(), e);
    		return null;
		}
	}
	
	
	
	public void removeBeneficiario(SoggettoErogazioneBean beneficiario){
		this.getAltriSoggetti().remove(beneficiario);
	}
	
	public List<SoggettoErogazioneBean> getAltriSoggetti() {
		if(this.altriSoggetti==null) altriSoggetti = new ArrayList<SoggettoErogazioneBean>();
		
		return altriSoggetti;
	}

	public void setAltriSoggetti(List<SoggettoErogazioneBean> altriSoggetti) {
		this.altriSoggetti = altriSoggetti;
	}

	public SoggettoErogazioneBean getAltroSoggettoTmp() {
		if(altroSoggettoTmp==null) altroSoggettoTmp = new SoggettoErogazioneBean(false);
		return altroSoggettoTmp;
	}

	public void setAltroSoggettoTmp(SoggettoErogazioneBean altroSoggettoTmp) {
		this.altroSoggettoTmp = altroSoggettoTmp;
	}

	public boolean isNuovoInsSoggManuale() {
		return nuovoInsSoggManuale;
	}

	public void setNuovoInsSoggManuale(boolean nuovoInsSoggManuale) {
		this.nuovoInsSoggManuale = nuovoInsSoggManuale;
	}
	
	public  void onChangeTipoNuovoInsSogg(){
		if(nuovoInsSoggManuale){
			beneficiarioSearchBean= new UserSearchBean();
		}else{
			altroSoggettoTmp=new SoggettoErogazioneBean(false);
		}
	}

	public SoggettoErogazioneBean getSoggettoErogazione() {
		return soggettoErogazione;
	}

	public void setSoggettoErogazione(SoggettoErogazioneBean soggettoErogazione) {
		this.soggettoErogazione = soggettoErogazione;
	}
	
	public List<CsIInterventoEseg> getInterEsegHistory() {
		return interEsegHistory;
	}

	public RisorsaFamiliareBean getRisorsaFamBean() {
		return risorsaFamBean;
	}

	public void setRisorsaFamBean(RisorsaFamiliareBean risorsaFamBean) {
		this.risorsaFamBean = risorsaFamBean;
	}
	
	public Long getSelSettoreEroganteId() {
		return selSettoreEroganteId;
	}

	public void setSelSettoreEroganteId(Long selSettoreEroganteId) {
		this.selSettoreEroganteId = selSettoreEroganteId;
	}
	
	public List<String> getListaCittadinanze() {
		if (listaCittadinanze == null) {
				listaCittadinanze = new ArrayList<String>();
				try {
					AccessTableNazioniSessionBeanRemote bean = (AccessTableNazioniSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableNazioniSessionBean");
					listaCittadinanze = bean.getCittadinanze();
					
				} catch (NamingException e) {
					addErrorFromProperties("caricamento.error");
					logger.error(e.getMessage(),e);
				}
			}
	
		return listaCittadinanze;
	}

	public Long getValQuotaTestata() {
		return valQuotaTestata;
	}

	public String getMotivazioneQuotaTestata() {
		return motivazioneQuotaTestata;
	}

	public void setValQuotaTestata(Long valQuotaTestata) {
		this.valQuotaTestata = valQuotaTestata;
	}

	public void setMotivazioneQuotaTestata(String motivazioneQuotaTestata) {
		this.motivazioneQuotaTestata = motivazioneQuotaTestata;
	}
	
	
/*	public void onUpdateQuotaTestata(BigDecimal tariffa){
		tariffa = tariffa!=null ? tariffa : BigDecimal.ZERO;
		Long valquota = this.valQuotaTestata!=null ? this.valQuotaTestata : 0L;
		Long spesa = valquota * tariffa.longValue();
		this.nuovoIntEsegMast.setSpesa(new BigDecimal(spesa));
	}*/
	
	
// SISO-720 SM
	public void onUpdateQuotaDettaglio(BigDecimal tariffa) {
		tariffa = tariffa != null ? tariffa : BigDecimal.ZERO;
		if (!tariffa.equals(tariffaLocale)) {
			tariffa = tariffaLocale;
		}
		BigDecimal valQuota = nuovocsIntEseg.getCsIValQuota().getValQuota() != null
				? nuovocsIntEseg.getCsIValQuota().getValQuota()
				: BigDecimal.ZERO;
		BigDecimal spesa = tariffa.multiply(valQuota);
		this.nuovocsIntEseg.setSpesa(spesa);
	}
	
	public void onUpdateTariffa(BigDecimal tariffa) {
		if (tariffa == null || tariffa.equals(BigDecimal.ZERO)) {
			this.nuovocsIntEseg.setCsIValQuota(new CsIValQuota());
			tariffaLocale = BigDecimal.ZERO;
		}
		else {
			tariffaLocale = tariffa;
			if (this.nuovocsIntEseg.getCsIValQuota() == null) {
				this.nuovocsIntEseg.setCsIValQuota(new CsIValQuota());
			}
		}

		if (this.calcolaSpesaDaDettaglio) {
//			this.onUpdateQuotaDettaglio(tariffa);
			this.onUpdateQuotaDettaglio(tariffaLocale);
		}
		// else
		// this.onUpdateQuotaTestata(tariffa);
	}
// FINE SISO-720 SM
	
	public void onUpdateDatiRif(SoggettoErogazioneBean se){
		if(se.isRiferimento()){
		   soggettoErogazione.setCittadinanza(se.getCittadinanza());
		   soggettoErogazione.setAnnoNascita(se.getAnnoNascita());
		 }
	}
	
	public void onUpdateDatiPrinc(){
		for(SoggettoErogazioneBean s : this.getAltriSoggetti()){
			if(s.isRiferimento()){
				s.setCittadinanza(soggettoErogazione.getCittadinanza());
				s.setAnnoNascita(soggettoErogazione.getAnnoNascita());
			}
		}
	}
	
	//INZIO MOD-RL

	public CsIPs getCsIPs() {
		return csIPs;
	}

	public void setCsIPs(CsIPs csIPs) {
		this.csIPs = csIPs;

	}
	//FINE MOD-RL

	public Date getDataErogA() {
		return dataErogA;
	}
	public void setDataErogA(Date dataErogA) {
		this.dataErogA = dataErogA;
	}

	public List<SelectItem> getListaSettoriErGroup() {
		return listaSettoriErGroup;
	}
	
	public void setListaSettoriErGroup(List<SelectItem> listaSettoriErGroup) {
		this.listaSettoriErGroup = listaSettoriErGroup;
	}

	/* == SISO-663 SM == */
	public List<SelectItem> getListaSettoriGestGroup() {
		return listaSettoriGestGroup;
	}

	public void setListaSettoriGestGroup(List<SelectItem> listaSettoriGestGroup) {
		this.listaSettoriGestGroup = listaSettoriGestGroup;
	}
	/* --===-- */

	public List<InterventoErogazAttrBean> getErogaziones(){
		CsCfgIntEsegStato statoSelezionato = getStatoSelezionato();
		return this.getErogaziones(statoSelezionato!=null ? statoSelezionato.getId() : null);
	}
	
	private List<InterventoErogazAttrBean> getErogaziones(Long statoId){
		if(statoId!=null)
			return this.mappaErogStatoAttr.get(statoId);
		else
			return new ArrayList<InterventoErogazAttrBean>();
	}

	//inizio SISO-556
	public boolean isPnlDettaglioErogazioniRendered(){
		//se ci sono righe di erogazione di chiusura non visualizzo il pannello di inserimento di righe erogazione
		boolean res = true;
		
		List<InterventoErogazHistoryRowBean> interventoErogazHistoryRowBeanList = this.getErogazioneHistory().getRows();

		for (InterventoErogazHistoryRowBean interventoErogazHistoryRowBean : interventoErogazHistoryRowBeanList) {
			
			if (DataModelCostanti.CsCfgIntEsegStato.FLAG_CHIUDI_CHIUSO == interventoErogazHistoryRowBean.getStato().getFlagChiudi()) {
				res = false;
			} 
			
		}
		
		return res;
	}
	
	//fine SISO-556
	
	//Gestione ISEE

	//INIZIO   SISO-657
//	public boolean isProtocolloDomandaPrestazioneRequired() {
//		return situazioneEconomicaVerificata;
//	} 
//
//	public boolean getSituazioneEconomicaVerificata() {
//		return situazioneEconomicaVerificata;
//	}
//	
//
//	public void setSituazioneEconomicaVerificata(boolean situazioneEconomicaVerificata) {
//		this.situazioneEconomicaVerificata = situazioneEconomicaVerificata;
//	}
	//FINE  SISO-657
 
	
	
	public String getIseeSelezionata() {
		return iseeSelezionata;
	}

	public void setIseeSelezionata(String iseeSelezionata) {
		this.iseeSelezionata = iseeSelezionata;
	} 
	
	
	//INIZIO   SISO-657 - Nuovo tracciato casellario PSA - PS - SINA

	public List<CsTbSchedaMultidim> getProvaMezziValueList(){
		List<CsTbSchedaMultidim> result = new ArrayList<CsTbSchedaMultidim>();
		
		CsTbSchedaMultidim prova = new CsTbSchedaMultidim();
		prova.setDescrizione(DataModelCostanti.CSIPs.DESCRIZIONE_FLAG_PROVA_MEZZI_ISEE_E_PROVA_MEZZI);
		prova.setTooltip("Prestazione soggetta a ISEE");
		result.add(prova);


		prova = new CsTbSchedaMultidim();
		prova.setDescrizione(DataModelCostanti.CSIPs.DESCRIZIONE_FLAG_PROVA_MEZZI_NON_ISEE_PROVA_MEZZI);
		prova.setTooltip("Prestazione soggetta a prova dei mezzi, ma non attraverso ISEE (es. prestaz. inps, agevolaz. tribut.)");
		result.add(prova);
		
		
		prova = new CsTbSchedaMultidim();
		prova.setDescrizione(DataModelCostanti.CSIPs.DESCRIZIONE_FLAG_PROVA_MEZZI_ISEE_NON_PROVA_MEZZI);
		prova.setTooltip("Prestazione in generale soggetta a ISEE, ma sottratta alla prova dei mezzi per lo specifico beneficiario in virtu' di altri criteri di bisogno (es. asilo nido per bambino con disabilità  o in famiglie numerose, ecc.)");
		result.add(prova);
		

		prova = new CsTbSchedaMultidim();
		prova.setDescrizione(DataModelCostanti.CSIPs.DESCRIZIONE_FLAG_PROVA_MEZZI_NON_ISEE_NON_PROVA_MEZZI);
		prova.setTooltip("Prestazione non soggetta a prova dei mezzi (assenza di criteri economici nella disciplina dell'erogazione)");
		result.add(prova);
		
		return result;
	}
	
	public void resetFlagProvaMezzi() {
		if (csIPs!=null) {
			csIPs.setFlagProvaMezzi(DataModelCostanti.CSIPs.ID_FLAG_PROVA_MEZZI_VUOTO);   
		}
	}
	
	public boolean isSituazioneEconomicaVerificata() {  
		if (csIPs!=null) 
			return csIPs.isSituazioneEconomicaVerificata();
		else return false;
	}
	
	
	public List<SelectItem> getListaOpzioniProvaMezzi() {	
		List<SelectItem> result = new ArrayList<SelectItem>(); 
	
		result.add(new SelectItem(  DataModelCostanti.CSIPs.ID_FLAG_PROVA_MEZZI_VUOTO , DataModelCostanti.CSIPs.DESCRIZIONE_FLAG_PROVA_MEZZI_VUOTO )); 
		result.add(new SelectItem(  DataModelCostanti.CSIPs.ID_FLAG_PROVA_MEZZI_ISEE_E_PROVA_MEZZI , 
				DataModelCostanti.CSIPs.DESCRIZIONE_FLAG_PROVA_MEZZI_ISEE_E_PROVA_MEZZI )); 
		result.add(new SelectItem(  DataModelCostanti.CSIPs.ID_FLAG_PROVA_MEZZI_NON_ISEE_PROVA_MEZZI , 
				DataModelCostanti.CSIPs.DESCRIZIONE_FLAG_PROVA_MEZZI_NON_ISEE_PROVA_MEZZI)); 
		result.add(new SelectItem(  DataModelCostanti.CSIPs.ID_FLAG_PROVA_MEZZI_ISEE_NON_PROVA_MEZZI , 
				DataModelCostanti.CSIPs.DESCRIZIONE_FLAG_PROVA_MEZZI_ISEE_NON_PROVA_MEZZI )); 
		result.add(new SelectItem(  DataModelCostanti.CSIPs.ID_FLAG_PROVA_MEZZI_NON_ISEE_NON_PROVA_MEZZI , 
				DataModelCostanti.CSIPs.DESCRIZIONE_FLAG_PROVA_MEZZI_NON_ISEE_NON_PROVA_MEZZI )); 
			 
		return result; 
	} 

	

	public List<SelectItem> getListaOpzioniPresoInCarico() {	
		List<SelectItem> result = new ArrayList<SelectItem>();  
		//result.add(new SelectItem(  DataModelCostanti.CSIPs.ID_FLAG_IN_CARICO_VUOTO , DataModelCostanti.CSIPs.DESCRIZIONE_FLAG_IN_CARICO_VUOTO));
		result.add(new SelectItem(  DataModelCostanti.CSIPs.ID_FLAG_IN_CARICO_SI ,  DataModelCostanti.CSIPs.DESCRIZIONE_FLAG_IN_CARICO_SI));
		result.add(new SelectItem(  DataModelCostanti.CSIPs.ID_FLAG_IN_CARICO_NO , DataModelCostanti.CSIPs.DESCRIZIONE_FLAG_IN_CARICO_NO ));
		return result;
	}
	
	public List<SelectItem> getListaAnniDsuTipologia() {	
		List<SelectItem> result = new ArrayList<SelectItem>(); 
		
		if(listaIsee!=null){
			//ordino la lista degli anni
			Collections.sort(listaIsee, new Comparator<CsDIsee>() { 
				@Override
				public int compare(CsDIsee o1, CsDIsee o2) {
					if (!o1.getAnnoRif().endsWith(o2.getAnnoRif())) {
						return  o1.getAnnoRif().compareTo(o2.getAnnoRif());
					} else {
						return o1.getTipologia().compareTo(o2.getTipologia());
					}
				}
			});
			
			for (CsDIsee csDIsee : listaIsee) {
				result.add(new SelectItem(  csDIsee.getDiarioId(), csDIsee.getAnnoRif() + " - " + csDIsee.getTipologia() )); 
			} 
		}
		return result; 
	}

	public List<String> getListaAnniDsu() { 
		ArrayList<String> listaAnni = null;
		 
		if(listaAnni == null) {
			int year = Calendar.getInstance().get(Calendar.YEAR);
			listaAnni = new ArrayList<String>(); 
			listaAnni.add(  String.valueOf(year));
			listaAnni.add( 	String.valueOf(year-1));
			listaAnni.add(  String.valueOf(year-2));
			listaAnni.add(  String.valueOf(year-3));
			listaAnni.add(  String.valueOf(year-4));
			listaAnni.add(  String.valueOf(year-5));
		} 
		return listaAnni;
	}
	

     
    public void confermaAnnoIsee(){
    	for (CsDIsee csDIsee : listaIsee) {
			if (Long.parseLong(iseeSelezionata) == csDIsee.getDiarioId() ) {
				this.loadDatiIsee(csDIsee);				
			}
		}
    	
    }
    
	public void loadDatiIsee(CsDIsee iseeSelezionata){
		csIPs.setDataDSU(iseeSelezionata.getCsDDiario().getDtAmministrativa());
//		csIPs.setCodPrestazione(codPrestazione);
		String dsu = iseeSelezionata.getProtocolloDsu();
		String[] adsu = dsu!=null ? dsu.split("-") : null;
	
		if(adsu!=null && adsu.length>1){
			try{
				csIPs.setPrefixProtDSU(adsu[0]+"-"+adsu[1]+"-"+adsu[2]);
				csIPs.setNumProtDSU(adsu[3]);
				csIPs.setProgProtDSU(adsu[4]);
			}catch(Exception e){
				addWarning("","Il num.protocollo DSU proveniente dai dati ISEE ["+dsu+"] non corrisponde al formato <b>INPS-ISEE-AAAA-NNNNNNNNN-NN</b>, modificare manualmente");
				csIPs.setNumProtDSU(dsu);
			}
		}else csIPs.setNumProtDSU(dsu);
			
		csIPs.setAnnoProtDSU(Integer.parseInt(iseeSelezionata.getAnnoRif()));
	}
    
    public void provaAction(){
    	logger.debug("provaAction " + iseeSelezionata);
    }
	
	private void loadDatiIsee() {
		listaIsee = new ArrayList<CsDIsee>();
		Long idCaso = this.soggettoErogazione.getCsASoggetto()!=null ? this.soggettoErogazione.getCsASoggetto().getCsACaso().getId() : null;
		if(idCaso!=null){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(idCaso);
			listaIsee = diarioService.findIseeByCaso(dto);
		}
	
	}
	
	public List<SelectItem>  getListaCodiciPrestazione() {
		return listaCodiciPrestazione;
	}

	private void loadlListaCodiciPrestazione(Long idTipoIntevento)   {
			listaCodiciPrestazione = new ArrayList<SelectItem>();
			
			if (idTipoIntevento!=null) {
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(idTipoIntevento);
				List<ArRelClassememoPresInps> listaArRelClassememoPresInp = interventoService.findArRelClassememoPresInpbyTipoInterventoId(dto);  
		
				SelectItemGroup g1 = new SelectItemGroup("Già presente in Inps");
				SelectItemGroup g0 = new SelectItemGroup("Da trasmettere a Inps");
				
				List<SelectItem> si1 = new ArrayList<SelectItem>();
				List<SelectItem> si0 = new ArrayList<SelectItem>();
				
				for (ArRelClassememoPresInps a : listaArRelClassememoPresInp) {
					SelectItem si = new SelectItem(a.getArTbPrestazioniInp().getCodice(), a.getArTbPrestazioniInp().getDenominazione());
					if(a.getArTbPrestazioniInp().getFlagNonEsportare())
						si1.add(si);
					else
						si0.add(si);
					
				} 
				if(si0.size()>0){
					g0.setSelectItems(si0.toArray(new SelectItem[si0.size()]));
					listaCodiciPrestazione.add(g0);
				}if(si1.size()>0){
					g1.setSelectItems(si1.toArray(new SelectItem[si1.size()]));
					listaCodiciPrestazione.add(g1);
				}
			}

	}
	
	public void cbxAnnoDsuListener(AjaxBehaviorEvent event){
		if(this.getCsIPs().getAnnoProtDSU()!=null && this.getCsIPs().getAnnoProtDSU()>0)
			this.csIPs.setPrefixProtDSU(DataModelCostanti.CSIPs.PREFIX_PROT_DSU+this.csIPs.getAnnoProtDSU());
		else {
			this.csIPs.setAnnoProtDSU(null);
			resetProtDSU();
		}
	}

	private void resetProtDSU(){
		this.getCsIPs().setPrefixProtDSU(null);
		this.getCsIPs().setNumProtDSU(null);
		this.getCsIPs().setProgProtDSU(null);
	}
	
	//INIZIO   SISO-657 - Nuovo tracciato casellario PSA - PS - SINA

    public void cbxProvaMezziListener(AjaxBehaviorEvent event) {   
    	situazioneEconomicaVerificataListener();
//        logger.debug(oldFlagProvaMezzi );
//        logger.debug(flagProvaMezzi);
//        
//        if ( (!oldFlagProvaMezzi.equals("1") && flagProvaMezzi.equals("1"))
//        		|| (oldFlagProvaMezzi.equals("1") && !flagProvaMezzi.equals("1")) 
//        		) {
//			RequestContext.getCurrentInstance().update("flsDatiIseePerInps");
//		}
//        oldFlagProvaMezzi = flagProvaMezzi;
    }
	
	
    public void situazioneEconomicaVerificataListener() { 
    	
    	if (isSituazioneEconomicaVerificata()) {
    			boolean success = true;
				if (listaCodiciPrestazione==null || listaCodiciPrestazione.size()==0) { // se NON c'è nessun codice prestazione  
					success = false;
			    	addMessage(FacesMessage.SEVERITY_ERROR, "Nessuna tipologia di PSA associata al tipo di intervento selezionato");
				}
	    		
	        	if (this.dentroFascicolo) { 
	    			if (listaIsee==null || listaIsee.size()==0) {
	    				success = false;
	    		    	addMessage(FacesMessage.SEVERITY_ERROR, "Dati ISEE assenti, si prega di inserire i dati ISEE nel fascicolo dell'utente");
					}
	        	}
	        	
				if (success){
					RequestContext context = RequestContext.getCurrentInstance();
					if(!this.getListaAnniDsuTipologia().isEmpty()) {
						context.execute("annoIseeWV.show();");	
						context.execute("  $( 'body' ).append( $('.annoIseeWVFormClass') );  ");	
					}else{
						//TODO:aggiorna il form con i dati vuoti e richiedo inserimento dei valori
						context.update("@(.pgDatiIseePerInpsClass)");
					}
				}
				this.cbxAnnoDsuListener(null);
				
		}else{
			//Reset valori form
			setCsIPs(new CsIPs());
		} 
    } 
    
    

	//INIZIO SISO-524
	public boolean isProtDomPrestDisabled(){
		return erogazioniEsportate;
	}

	public boolean isProvaMezziDisabled(){
		return erogazioniEsportate;
	}

	public boolean isAnnoDsuDisabled(){
		return   //SISO-657 dentroFascicolo || 
				erogazioniEsportate;
	}

	public boolean isNumProtDSUDisabled(){
		return    erogazioniEsportate|| this.csIPs.getAnnoProtDSU()==null || this.csIPs.getAnnoProtDSU()==0;
	}
	public boolean isDataDsuDisabled(){
		return    erogazioniEsportate;
	}
	
 
	//INIZIO SISO-657
	public boolean isDatiDsuRendered(){
		return    //SISO-657 dentroFascicolo || 
				erogazioniEsportate;
	}

	public boolean validaDatiCsIPs() {
		boolean res = true;
		if(this.erogazionePossibile){
			if(nuovoIntEsegMast.getProtDomPrest()==null || nuovoIntEsegMast.getProtDomPrest().isEmpty()){
				res=false;
				addMessage(FacesMessage.SEVERITY_ERROR, "Il campo 'Prot. domanda prestazione' è obbligatorio "); 
			}
			if ( csIPs.getFlagProvaMezzi() == DataModelCostanti.CSIPs.ID_FLAG_PROVA_MEZZI_VUOTO) {
				res = false;
				addMessage(FacesMessage.SEVERITY_ERROR, "Il campo 'Prova dei mezzi' è obbligatorio ");
			}else if(  csIPs.getFlagProvaMezzi().equals(DataModelCostanti.CSIPs.ID_FLAG_PROVA_MEZZI_ISEE_NON_PROVA_MEZZI) 
					|| csIPs.getFlagProvaMezzi().equals(DataModelCostanti.CSIPs.ID_FLAG_PROVA_MEZZI_ISEE_E_PROVA_MEZZI) ){
				
				if(  !(
						(csIPs.getDataDSU() != null && (csIPs.getAnnoProtDSU() != null && csIPs.getAnnoProtDSU()>0) && (csIPs.getNumProtDSU() != null && !csIPs.getNumProtDSU().isEmpty())) ||
						(csIPs.getDataDSU() == null && (csIPs.getAnnoProtDSU() == null || csIPs.getAnnoProtDSU().equals(new Integer(0)))  && (csIPs.getNumProtDSU() == null || csIPs.getNumProtDSU().isEmpty()))
				  ))
				{
				   res = false;
				   addMessage(FacesMessage.SEVERITY_ERROR, "Controllare che i campi anno, data e protocollo DSU siano tutti valorizzati o tutti nulli");
				}
				
				/*
				if(csIPs.getDataDSU()==null){
					res = false;
					addMessage(FacesMessage.SEVERITY_ERROR, "Il campo 'Data presentazione dsu' è obbligatorio");
				}
				if(csIPs.getNumProtDSU()==null||csIPs.getNumProtDSU().isEmpty() || csIPs.getProgProtDSU()==null || csIPs.getProgProtDSU().isEmpty()){
					res = false;
					addMessage(FacesMessage.SEVERITY_ERROR, "Il campo 'Num. protocollo dsu' è obbligatorio");
				}
				if(csIPs.getAnnoProtDSU()==null){
					res = false;
					addMessage(FacesMessage.SEVERITY_ERROR, "Il campo 'Anno dsu' è obbligatorio");
				}
				*/
				
			} 
			if ( csIPs.getFlagInCarico()==null) {
				res = false;
				addMessage(FacesMessage.SEVERITY_ERROR, "Il campo 'Durante il servizio il beneficiario è in carico' è obbligatorio");
			}
			
		}
		return res;
	}
	
	//FINE  SISO-657
	
	public boolean isCodPrestazioneDisabled(){
		return erogazioniEsportate;
	}
	

	public boolean isCambiaDatiIseeBtnDisabled(){
		return erogazioniEsportate;
	}
	//FINE SISO-524 
	
	public String getDescOrgEroganteId() {
		CsOSettore s = this.getSettore(this.selSettoreEroganteId);
		return  (s!=null && s.getCsOOrganizzazione()!=null) ? "- Org. "+s.getCsOOrganizzazione().getNome() : "";
	}

	protected CsOSettore getSettore(Long idSettore) {
		if (idSettore != null) {
			for (CsOSettore s : listaSettori) {
				if (s.getId().longValue() == idSettore.longValue())
					return s;
			}
		}
		return null;
	}

	public List<CsOSettore> getListaSettori() {
		return listaSettori;
	}

	public void setListaSettori(List<CsOSettore> listaSettori) {
		this.listaSettori = listaSettori;
	}

//	public String getRuoloEnteGestioneSpesa() {
//		return ruoloEnteGestioneSpesa;
//	}

//	public void setRuoloEnteGestioneSpesa(String ruoloEnteGestioneSpesa) {
//		this.ruoloEnteGestioneSpesa = ruoloEnteGestioneSpesa;
//	}




	/* SISO-663 SM */

	public List<SelectItem> getEntiGestoriSpesa() {
		List<SelectItem> gestoriSpesa = new ArrayList<SelectItem>();
		SelectItem selectItem;
		for (RuoloEnteGestoreSpesa regs : RuoloEnteGestoreSpesa.values()) {
			if (regs == RuoloEnteGestoreSpesa.NON_SELEZIONATO)
				continue;
			selectItem = new SelectItem(regs, regs.descrizione());
			selectItem.setDisabled(regs == RuoloEnteGestoreSpesa.GESTORE && gestoreComeTitolare);
			gestoriSpesa.add(selectItem);
		}
		return gestoriSpesa;
	}
	

	public void sobGestioneSpesaOnChange() {
		CsOOrganizzazione enteGestoreDellaSpesa=null;
		if(ruoloEnteGestoreSpesa!=null){
		switch (ruoloEnteGestoreSpesa) {
		case NON_SELEZIONATO:
			logger.info("selezionato NON_SELEZIONATO");
			break;
			//setNomeEnteGestoreSpesa(RuoloEnteGestoreSpesa.NON_SELEZIONATO.descrizione());
		case TITOLARE:
			logger.info("selezionato TITOLARE");
			if (settoreTitolare == null) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Settore titolare non selezionato");
				ruoloEnteGestoreSpesa = RuoloEnteGestoreSpesa.NON_SELEZIONATO;
				 enteGestoreDellaSpesa = null;
			}
			else {
				enteGestoreDellaSpesa = settoreTitolare.getCsOOrganizzazione();
			}
			break;
		case CAPOFILA:
			logger.info("selezionato CAPOFILA");
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			enteGestoreDellaSpesa = confService.getOrganizzazioneCapofila(cet);
			if (enteGestoreDellaSpesa == null) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Ente capofila non trovato");
				ruoloEnteGestoreSpesa = RuoloEnteGestoreSpesa.NON_SELEZIONATO;
			}
			break;
		case GESTORE:
			logger.info("selezionato GESTORE");
			if (settoreGestore == null) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Settore gestore non selezionato");
				ruoloEnteGestoreSpesa = RuoloEnteGestoreSpesa.NON_SELEZIONATO;
				enteGestoreDellaSpesa = null;
			}
			else {
				enteGestoreDellaSpesa = settoreGestore.getCsOOrganizzazione();
			}
			break;
		default:
			break;
		}

		logger.info("ente gestore della spesa: " + (enteGestoreDellaSpesa!=null ? enteGestoreDellaSpesa.getBelfiore() : null));
		this.nuovoIntEsegMast.setEnteGestoreSpesa(enteGestoreDellaSpesa);
		
		/*if (enteGestoreDellaSpesa == null) 
			setNomeEnteGestoreSpesa(RuoloEnteGestoreSpesa.NON_SELEZIONATO.descrizione());
		else 
			setNomeEnteGestoreSpesa(enteGestoreDellaSpesa.getNome());*/
		}else{
			logger.warn("ruoloEnteGestoreSpesa: non selezionato");
		}
	}

	public String getNomeEnteGestoreSpesa() { 
		String descGestoreSpesa = "";
		if(this.nuovoIntEsegMast.getEnteGestoreSpesa()!=null)
			descGestoreSpesa=" - "+(this.nuovoIntEsegMast.getEnteGestoreSpesa().getBelfiore()!=null ? "Org. " : "") + this.nuovoIntEsegMast.getEnteGestoreSpesa().getNome();
		else
			descGestoreSpesa = RuoloEnteGestoreSpesa.NON_SELEZIONATO.descrizione();
		return descGestoreSpesa;
	}
	
	public Map<Long, List<CsOSettore>> getMapEntiGestoriDiSettore() { return mapEntiGestoriDiSettore; }
	public void setMapEntiGestoriDiSettore(HashMap<Long, List<CsOSettore>> mapEntiGestoriDiSettore) { this.mapEntiGestoriDiSettore = mapEntiGestoriDiSettore; }

	public Map<Long, List<CsOSettore>> getMapEntiTitolariDiSettore() { return mapEntiTitolariDiSettore; }
	public void setMapEntiTitolariDiSettore(HashMap<Long, List<CsOSettore>> mapEntiTitolariDiSettore) { this.mapEntiTitolariDiSettore = mapEntiTitolariDiSettore; }

	public CsOSettore getSettoreTitolare() { return settoreTitolare; }

	public void setSettoreTitolare(CsOSettore settoreTitolare) {
		if (settoreTitolare.getId() == null) {
			this.settoreTitolare = null;
		}
		else {
			this.settoreTitolare = settoreTitolare;
		}
	}

	public CsOSettore getSettoreGestore() { return settoreGestore; }

	public void setSettoreGestore(CsOSettore settoreGestore) {
		if (settoreGestore.getId() == null) {
			this.settoreGestore = null;
		}
		else {
			this.settoreGestore = settoreGestore;
		}
	}

	public boolean isGestoreComeTitolare() { return gestoreComeTitolare; }
	public void setGestoreComeTitolare(boolean gestoreComeTitolare) { this.gestoreComeTitolare = gestoreComeTitolare; }

	public RuoloEnteGestoreSpesa getRuoloEnteGestoreSpesa() {return ruoloEnteGestoreSpesa; }
	public void setRuoloEnteGestoreSpesa(RuoloEnteGestoreSpesa ruoloEnteGestoreSpesa) {
		if (ruoloEnteGestoreSpesa == RuoloEnteGestoreSpesa.NON_SELEZIONATO) {
			//this.setNomeEnteGestoreSpesa(RuoloEnteGestoreSpesa.NON_SELEZIONATO.descrizione());
			this.nuovoIntEsegMast.setEnteGestoreSpesa(null);
		}
		this.ruoloEnteGestoreSpesa = ruoloEnteGestoreSpesa; 
	}

	public ErogazioneInterventoTooltipText getTooltipText() {
		return erogazioneInterventoTooltipText;
	}
	
	public void validaEnteGestoreSpesa() {
		if (gestoreComeTitolare && ruoloEnteGestoreSpesa == RuoloEnteGestoreSpesa.GESTORE) {
			ruoloEnteGestoreSpesa = RuoloEnteGestoreSpesa.TITOLARE;
		}
		sobGestioneSpesaOnChange();
	}

	/* SISO-720 SM */
	public BigDecimal getTariffaLocale() { return tariffaLocale; }
	public void setTariffaLocale(BigDecimal tariffaLocale) { this.tariffaLocale = tariffaLocale; }
	/* FINE SISO-720 SM */
	
	public Boolean getCambiaBeneficiarioRiferimento() {
		return cambiaBeneficiarioRiferimento;
	}

	public void setCambiaBeneficiarioRiferimento(
			Boolean cambiaBeneficiarioRiferimento) {
		this.cambiaBeneficiarioRiferimento = cambiaBeneficiarioRiferimento;
	}
    //SISO-809
    public boolean getEsportazioneDiGruppo() {
		return esportazioneDiGruppo;
	}

	public void setEsportazioneDiGruppo(boolean esportazioneDiGruppo) {
		this.esportazioneDiGruppo = esportazioneDiGruppo;
	}
	//fine SISO-809
	
	
}
