package it.webred.cs.jsf.bean.erogazioneIntervento;

import it.webred.cs.csa.ejb.client.AccessTableComuniSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIndirizzoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableNazioniSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.ejb.dto.RisorsaFamDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.IntEsegAttrBean;
import it.webred.cs.csa.ejb.dto.erogazioni.IntEsegAttrUtilsBean;
import it.webred.cs.csa.ejb.dto.erogazioni.SoggettoErogazioneBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.TipoStatoErogazione;
import it.webred.cs.data.model.ArTbPrestazioniInps;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsAIndirizzo;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.CsCfgAttrUnitaMisura;
import it.webred.cs.data.model.CsCfgIntEseg;
import it.webred.cs.data.model.CsCfgIntEsegStato;
import it.webred.cs.data.model.CsDIsee;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsIInterventoEseg;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.data.model.CsIInterventoEsegMastSogg;
import it.webred.cs.data.model.CsIInterventoEsegValore;
import it.webred.cs.data.model.CsIPs;
import it.webred.cs.data.model.CsIQuota;
import it.webred.cs.data.model.CsIValQuota;
import it.webred.cs.data.model.CsOOperatoreAnagrafica;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.cs.data.model.VLineaFin;
import it.webred.cs.jsf.bean.DatiUserSearchBean;
import it.webred.cs.jsf.manbean.RisorsaFamiliareBean;
import it.webred.cs.jsf.manbean.UserSearchBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.siso.ws.client.anag.client.PersonaFindResult;
import it.webred.siso.ws.client.anag.model.SiancPazientePazienteBean.Master;
import it.webred.cs.jsf.bean.DatiTariffeInterventoBean.TIPO_FREQUENZA_SERVIZIO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;

public class ErogazioneInterventoBean extends CsUiCompBaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean visualizzaInterventoData = false;

	private SoggettoErogazioneBean soggettoErogazione = null;
	
	private SoggettoErogazioneBean altroSoggettoTmp;
	private boolean nuovoInsSoggManuale=false;
	private UserSearchBean beneficiarioSearchBean = new UserSearchBean();
	private List<SoggettoErogazioneBean> altriSoggetti;
	
	private RisorsaFamiliareBean risorsaFamBean;
	
	private Date dataErog;
	private Date dataErogA;
	private Long statoSelezionatoId;
	private String nomeTipoErogazione;		
	
	private List<CsCfgIntEsegStato> listaStatiErogazione;
	private List<SelectItem> lstSelItemStatiErog;
	private List<CsOSettore> listaSettori;
	private List<SelectItem> listaSettoriTitGroup;
	private List<SelectItem> listaSettoriErGroup;
 	private Long selSettoreEroganteId;
	private Long selSettoreTitolareId;
	
	//frida	
	private List<String> listaCittadinanze;
	private List<String> listaOpzioniBen;
	private Date dataAttivazioneDa;
	private String nomeTipoCustEroga = "";
	private Boolean testataDisabled = false;
	private Boolean dentroIntervento=true;
	private Boolean erogazionePossibile=true;
	private CsIInterventoEsegMast nuovoIntEsegMast;
	private CsIInterventoEseg nuovocsIntEseg;
	private List<CsIInterventoEseg> interEsegHistory= new ArrayList<CsIInterventoEseg>();
	private List<CsOOperatoreSettore> listaOperSettore= new ArrayList<CsOOperatoreSettore>();
	private List<CsOOperatoreAnagrafica> listaOperAnagrafica;
	
	private Long origineFinanziamentoId;
	private Long operatoreErogId;
	protected AccessTableOperatoreSessionBeanRemote operatoreService=(AccessTableOperatoreSessionBeanRemote)getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableOperatoreSessionBean");
	private String testaPercOrValue=DataModelCostanti.ErogazioneInterventoConst.TESTATA_SPESA_PERCENTUALE;
	private String dettaPercOrValue=DataModelCostanti.ErogazioneInterventoConst.DETTAGLIO_SPESA_PERCENTUALE;
	private String operatoreScegliOrInserisci=DataModelCostanti.ErogazioneInterventoConst.SCEGLI_OPERATORE;
	private List<VLineaFin> listaOrigineFin= new ArrayList<VLineaFin>();
	private String codFinanziamento;
	
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
	private List<InterventoErogazAttrBean> erogaziones;
	private InterventoErogazHistoryBean erogazioneHistory = new InterventoErogazHistoryBean();
	private boolean visualizzaAttributi = false;
	private boolean enabledAggiungi = false;
	private CsCTipoInterventoCustom tipoIntCustom=null;
	protected AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");
	protected AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
	protected AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
	protected AccessTableComuniSessionBeanRemote comuniService = (AccessTableComuniSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableComuniSessionBean"); //MOD-RL	
	protected AccessTableIndirizzoSessionBeanRemote indirizzoService = (AccessTableIndirizzoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableIndirizzoSessionBean"); //MOD-RL
	
	public void inizializzaNuovaErogazione(Long tipoInterventoId,Long tipoInterventoCustom ,SoggettoErogazioneBean soggettoErogazione, Long catSocialeId) {
		
		this.altroSoggettoTmp = new SoggettoErogazioneBean(false);
		this.altriSoggetti = new ArrayList<SoggettoErogazioneBean>();
		
	
		if( soggettoErogazione == null ){
			this.soggettoErogazione = new SoggettoErogazioneBean(true);	
			this.dentroIntervento=false;
		}			
		else{
			this.soggettoErogazione = soggettoErogazione; //TODO:Verificare che riferimento sia a true
			this.altriSoggetti.add(soggettoErogazione);
			this.dentroIntervento=(soggettoErogazione.getCsASoggetto()!=null);
		}
		
		initRisorsaFamiliareBean();
		nuovoIntEsegMast=new CsIInterventoEsegMast();
		setCsIPs(new CsIPs());	//MOD-RL 
		testataDisabled=false;

		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(tipoInterventoId);
		CsCTipoIntervento csCTipoIntervento = interventoService.findTipiInterventoById(dto);
		this.nomeTipoErogazione = csCTipoIntervento.getDescrizione();
		CsCTipoInterventoCustom csCTipoInterventoCustom=null;
		if(tipoInterventoCustom!=null){
			dto.setObj(tipoInterventoCustom); //per ora la tabella custom non esiste
			csCTipoInterventoCustom = interventoService.findTipiInterventoCustomById(dto);
			this.nomeTipoCustEroga=csCTipoInterventoCustom.getDescrizione();
		}
		else 
		{
			this.nomeTipoCustEroga = null;
		}
		
		buildInterventoErogazzAttList(tipoInterventoCustom!=null ?tipoInterventoCustom : tipoInterventoId);
		loadListaStatiInterventi(tipoInterventoCustom!=null ?tipoInterventoCustom : tipoInterventoId);
		loadListaSettori();
		resetSettoriTestata();
		resetOrganizAndNuovoIntEseg();	
		
		nuovoIntEsegMast.setTipoBeneficiario(DataModelCostanti.ListaBeneficiari.INDIVIDUALE);
		this.tipoIntCustom = null;
		this.codFinanziamento=null;
		if (tipoInterventoCustom != null) {
			dto.setObj(tipoInterventoCustom);
        	this.tipoIntCustom= interventoService.findTipiInterventoCustomById(dto);        	
		}
		//Recupera oggetto intervento
		this.erogazioneHistory = new InterventoErogazHistoryBean();
		if(csCTipoInterventoCustom!=null)
			interEsegHistory=this.erogazioneHistory.initialize(erogaziones, csCTipoIntervento, csCTipoInterventoCustom, catSocialeId); 
		else
			interEsegHistory=this.erogazioneHistory.initialize(erogaziones, csCTipoIntervento, catSocialeId);			
				
		resetBoolDisabledSpese();
		
		this.visualizzaInterventoData = false;		
		this.statoSelezionatoId = null;
		this.visualizzaAttributi = false;
		this.enabledAggiungi = false;			
		
	}
	
	public void inizializzaErogazione(Long tipoInterventoId,Long tipoInterCustomId ,Long interventoId, SoggettoErogazioneBean soggettoErogazione, List<SoggettoErogazioneBean> beneficiari) throws Exception {
		dentroIntervento=true;
		altriSoggetti = new ArrayList<SoggettoErogazioneBean>();
		
		loadListaStatiInterventi(tipoInterCustomId!=null ?tipoInterCustomId : tipoInterventoId);
		
		buildInterventoErogazzAttList(tipoInterCustomId!=null ?tipoInterCustomId : tipoInterventoId);
		loadListaSettori();
		
		BaseDTO bdto = new BaseDTO();
		fillEnte(bdto);
		bdto.setObj(interventoId);
		CsIIntervento csIIntervento = this.interventoService.getInterventoById(bdto);
		
		this.tipoIntCustom = null;
		if( tipoInterCustomId != null ) {
			bdto.setObj(tipoInterCustomId);
	        this.tipoIntCustom= interventoService.findTipiInterventoCustomById(bdto);
	        this.nomeTipoCustEroga=tipoIntCustom.getDescrizione();
		}
		else 
			this.nomeTipoCustEroga = null;

        this.erogazioneHistory = new InterventoErogazHistoryBean();
		if (csIIntervento != null) { //TODO attualmente in realtà questo metodo viene chiamato solo se interventoI!=null
			this.nomeTipoErogazione = csIIntervento.getCsRelSettCsocTipoInter().getCsRelCatsocTipoInter().getCsCTipoIntervento().getDescrizione();				
			this.interEsegHistory=this.erogazioneHistory.initialize(erogaziones, csIIntervento); 
			this.visualizzaInterventoData = true;		
			
			//inizio SISO-500  
			if (csIIntervento.getCsIInterventoEsegMast()!=null) {
				setCsIPs(csIIntervento.getCsIInterventoEsegMast().getCsIPs()); 
			} else{
				setCsIPs(new CsIPs());
			}
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
			this.selSettoreTitolareId=nuovoIntEsegMast.getSettoreTitolare()!=null ? nuovoIntEsegMast.getSettoreTitolare().getId() : null;
			loadListaOperatoriEroganti(this.selSettoreEroganteId);
		
			if(nuovoIntEsegMast.getFfOrigineId()!=null)
				this.origineFinanziamentoId= nuovoIntEsegMast.getFfOrigineId();
			
			if(nuovoIntEsegMast.getCsOOperatoreSettore().getCsOOperatore().getId()!=getCurrentOpSettore().getCsOOperatore().getId())
				testataDisabled=true;
		}else{
			this.codFinanziamento=null;
			this.nuovoIntEsegMast= new CsIInterventoEsegMast();

			//SISO-500  setCsIPs(new CsIPs());//MOD-RL 
		}
		nuovoIntEsegMast.setTipoBeneficiario(DataModelCostanti.ListaBeneficiari.INDIVIDUALE);
		nuovoIntEsegMast.setCsIIntervento(csIIntervento);
		
	}

	private void initRisorsaFamiliareBean(){
		if(this.isNucleoBeneficiario() && this.dentroIntervento)
			this.risorsaFamBean = new RisorsaFamiliareBean(soggettoErogazione.getCodiceFiscale());
		else
			this.risorsaFamBean = null;
	}
	
	public void inizializzaDaErogazioneMaster(Long tipoInterventoId,Long tipoInterCustomId ,Long masterId, SoggettoErogazioneBean soggettoErogazione, List<SoggettoErogazioneBean> beneficiari) {
		dentroIntervento=false;
		altriSoggetti = new ArrayList<SoggettoErogazioneBean>();
		
		loadListaStatiInterventi(tipoInterCustomId!=null ?tipoInterCustomId : tipoInterventoId);
		
		buildInterventoErogazzAttList(tipoInterCustomId!=null ?tipoInterCustomId : tipoInterventoId);
		loadListaSettori();
		
		BaseDTO bdto = new BaseDTO();
		fillEnte(bdto);
		bdto.setObj(masterId);
		CsIInterventoEsegMast master = this.interventoService.getErogazioneMasterById(bdto);
		this.nuovoIntEsegMast = master;
		
		this.tipoIntCustom = null;
		if( tipoInterCustomId != null ) {
			bdto.setObj(tipoInterCustomId);
	        this.tipoIntCustom= interventoService.findTipiInterventoCustomById(bdto);
	        this.nomeTipoCustEroga=tipoIntCustom.getDescrizione();
		}
		else 
			this.nomeTipoCustEroga = null;

        this.erogazioneHistory = new InterventoErogazHistoryBean();
        
		this.nomeTipoErogazione = master.getCsCTipoIntervento().getDescrizione();
		this.interEsegHistory = this.erogazioneHistory.initialize(erogaziones, master);
		this.visualizzaInterventoData = true;				
								
		this.soggettoErogazione = soggettoErogazione;
		this.altriSoggetti.addAll(beneficiari);
		
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
			this.selSettoreTitolareId = nuovoIntEsegMast.getSettoreTitolare()!=null ? nuovoIntEsegMast.getSettoreTitolare().getId() : null;
			loadListaOperatoriEroganti(this.selSettoreEroganteId);

			if(nuovoIntEsegMast.getFfOrigineId()!=null)
				this.origineFinanziamentoId= nuovoIntEsegMast.getFfOrigineId();
		}
		
		this.codFinanziamento = master.getCodiceFin1();
		
		if(this.isErogazioniPresenti())
			testataDisabled = !this.isOperatoreProprietario();

		testataDisabled = testataDisabled || isErogazioniPresenti();

		setCsIPs(master.getCsIPs()); //SISO-498
		
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
		this.selSettoreTitolareId = settoreCorrente.getId();
		
		this.disabilitaOrganizzazione=settoreCorrente.getCsOOrganizzazione().getBelfiore()==null;
		loadListaOrigineFinanziamenti();
	}
	
	private void resetOrganizAndNuovoIntEseg(){					
		this.dataErog=dataAttivazioneDa;		
		if(this.selSettoreEroganteId!=null && selSettoreEroganteId.compareTo((long)0)!=0){
			loadListaOperatoriEroganti(selSettoreEroganteId);		
		}	
		this.nuovocsIntEseg= new CsIInterventoEseg();
		CsOOperatoreSettore op=(CsOOperatoreSettore) getSession().getAttribute("operatoresettore");
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
					
					if(!lstcf.contains(seb.getCodiceFiscale()))
						lstcf.add(seb.getCodiceFiscale());
					else{
						addMessage(FacesMessage.SEVERITY_ERROR,"Beneficiario duplicato: "+ seb.getCodiceFiscale());
						valido=false;
					}
					
					existsRif = seb.isRiferimento() | existsRif;
				}
				if(!existsRif){
					addMessage(FacesMessage.SEVERITY_ERROR,"Selezionare il soggetto beneficiario di riferimento");
					valido=false;
				}
			}
			
		}
			
			//TODO: verificare che non ci siano soggetti uguali
			
			//TODO: Verifica che almeno uno dei soggetti sia definito come di riferimento
			
		return valido;
		
	}
	
	public boolean salva(CsIIntervento intervento, CsIQuota quota, 
			boolean situazioneEconomicaVerificata,
			boolean dentroCaso,
			CsASoggettoLAZY csASoggetto,
			String frequenzaAccessoAlServizio
		) {
		if(!this.validaTestata())
			return false;
		
		boolean bOk = true;	
		nuovoIntEsegMast.setCsIQuota(quota);
		
		try {			
	
			if(this.getAltriSoggetti().isEmpty() && this.soggettoErogazione.isValorizzato())
				this.getAltriSoggetti().add(this.soggettoErogazione);
	
			//frida			
			if(erogazioneHistory.getRows()!=null){
				if(erogazioneHistory.getRows().size()>0)
					 valorizzaInterventoEsegMast(intervento);
				for (InterventoErogazHistoryRowBean row : erogazioneHistory.getRows()) {
					if (row.isNuovaErogazione()) {	
							CsIInterventoEseg nuovoIntEseg = valorizzaInterventoEseg(row);							
							nuovoIntEseg= nuovoIntEsegMast.addCsIInterventoEseg(nuovoIntEseg);
							valorizzaIntEsegValori(row, nuovoIntEseg);												
					}
				}
			}
			
			List<CsIInterventoEseg> storicoDaCancellare = new LinkedList<CsIInterventoEseg>();
			for (InterventoErogazHistoryRowBean eliminaInetEseg : erogazioneHistory.getDeleteErogaz()) {
				if (eliminaInetEseg.getIntEseg() != null)
					storicoDaCancellare.add(eliminaInetEseg.getIntEseg());
			}
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			
/*			//Cancello eventuali beneficiari presenti, prima dei reinserirne nuovi
			if(nuovoIntEsegMast.getId()>0){
				dto.setObj(nuovoIntEsegMast.getId());
				interventoService.rimuoviBeneficiariMaster(dto);
			}*/
			
			nuovoIntEsegMast.setBeneficiari(null);
			for(SoggettoErogazioneBean se : this.getAltriSoggetti()){
				CsIInterventoEsegMastSogg b = this.valorizzaSoggettiErogazione(se);
				b = nuovoIntEsegMast.addBeneficiario(b);
			}
		
			/*Registrazione su DB*/
			
			if(nuovoIntEsegMast.getCsIInterventoEsegs()!=null && !nuovoIntEsegMast.getCsIInterventoEsegs().isEmpty() && 
			   nuovoIntEsegMast.getBeneficiari()!=null && !nuovoIntEsegMast.getBeneficiari().isEmpty()) {
				
				//INIZIO MOD-RL 
				if (situazioneEconomicaVerificata) {
					
					String codiceFiscale = soggettoErogazione.getCodiceFiscale();
					String benefLuogoNascita = codiceFiscale.substring(11,15);
					int benefSessoGiorno = Integer.parseInt( codiceFiscale.substring(9,11) );
					int benefSesso = benefSessoGiorno>40?2:1;
					csIPs.setBenefLuogoNascita(benefLuogoNascita);
					csIPs.setBenefSesso(benefSesso);
					csIPs.setBenefCittadinanza(Integer.parseInt( getCodiceIstatByNazionalita(soggettoErogazione.getCittadinanza() ) ));   
					Date actual = new Date();
					csIPs.setDtIns(actual);
					csIPs.setDtMod(actual); 
					csIPs.setDenomPrestazione( findDenomizazionePrestazione(csIPs.getCodPrestazione())  );

					if (frequenzaAccessoAlServizio.equals(TIPO_FREQUENZA_SERVIZIO.PERIODICO.getCodice())) { 
						csIPs.setCarattere(DataModelCostanti.CSIPs.CARATTERE_PRESTAZIONE_DI_TIPO_PERIODICO); 
					} else { 
						csIPs.setCarattere(DataModelCostanti.CSIPs.CARATTERE_PRESTAZIONE_DI_TIPO_OCCASIONALE); 
					} 
					
					if (dentroCaso) { 
						long soggettoAnagraficaId = csASoggetto.getAnagraficaId();
						CsAAnaIndirizzo csAAnaIndirizzo = getAnaIndirizzoResidenzaAttuale(soggettoAnagraficaId);
						
//						CsAIndirizzo csAIndirizzo = getIndirizzoResidenzaCodFisc(codiceFiscale);
//						String codComIstat = csAIndirizzo.getCsAAnaIndirizzo().getComCod();
						
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
					
					nuovoIntEsegMast.setCsIPs(csIPs);
					csIPs.setCsIInterventoEsegMast(nuovoIntEsegMast);					
				} 
				//FINE MOD-RL 
				
				dto.setObj(nuovoIntEsegMast);
				interventoService.aggiungiInterventoEseguitoMast(dto);
			}
			
			if (!storicoDaCancellare.isEmpty()) {
				dto.setObj(storicoDaCancellare);
				interventoService.eliminaInterventoEsegStorico(dto);
			}		
		}

		catch (Exception e) {
			logger.error("Errore salvataggio erogazione", e);
			this.addError("Errore salvataggio erogazione intervento.", "");
			bOk = false;
		}
		return bOk;
	}
	
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
		ArTbPrestazioniInps arTbPrestazioniInps = interventoService.findArTbPrestazioniInpsByCodice(denomPrestazioneDto);
		return   arTbPrestazioniInps.getDenominazione();
	}

	public void loadDatiIsee(CsDIsee iseeSelezionata){
		csIPs.setDataDSU(iseeSelezionata.getCsDDiario().getDtAmministrativa());
//		csIPs.setCodPrestazione(codPrestazione);
//		csIPs.setNumProtDSU(iseeSelezionata.get);
		csIPs.setAnnoProtDSU(Integer.parseInt(iseeSelezionata.getAnnoRif()));
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
		
		CsOOperatoreSettore opSettoreMast=(CsOOperatoreSettore) getSession().getAttribute("operatoresettore");
		s.setUserIns(opSettoreMast.getCsOOperatore().getUsername());
		s.setDtIns(new Date());
		return s;
	}

	//TODO frida completare 23 campi: gli devo passare il master invece dell'intervento
	private CsIInterventoEseg valorizzaInterventoEseg(InterventoErogazHistoryRowBean row) {
		CsIInterventoEseg nuovoIntEseg = new CsIInterventoEseg();
		CsOOperatoreSettore opSettoreMast=(CsOOperatoreSettore) getSession().getAttribute("operatoresettore");
		
		nuovoIntEseg.setUserIns(opSettoreMast.getCsOOperatore().getUsername());
		nuovoIntEseg.setDtIns(row.getIntEseg().getDtIns());
		
		nuovoIntEseg.setNomeOperatoreErog(row.getIntEseg().getNomeOperatoreErog());		
		if(nuovoIntEseg.getNomeOperatoreErog()==null || nuovoIntEseg.getNomeOperatoreErog().trim().isEmpty())
			nuovoIntEseg.setCsOOperatoreSettore(opSettoreMast);				
		
		if(row.getIntEseg().getCsOOperatoreSettore()!=null)
			nuovoIntEseg.setCsOOperatoreSettore(row.getIntEseg().getCsOOperatoreSettore());						
	
		nuovoIntEseg.setEnteOperatoreErogante(row.getIntEseg().getEnteOperatoreErogante());
		
									
		nuovoIntEseg.setDataEsecuzione(row.getIntEseg().getDataEsecuzione());
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
		CsOOperatoreSettore opSettore = (CsOOperatoreSettore) getSession().getAttribute("operatoresettore");
		CsOSettore settoreErogante = this.getSettore(this.selSettoreEroganteId);
		nuovoIntEsegMast.setSettoreErogante(settoreErogante);
		
		nuovoIntEsegMast.setSettoreTitolare(this.getSettore(this.selSettoreTitolareId));
		if(nuovoIntEsegMast.getId()>0){
			nuovoIntEsegMast.setUsrMod(opSettore.getCsOOperatore().getUsername());
			nuovoIntEsegMast.setDtMod(new Date());
		}else{
			nuovoIntEsegMast.setCsOOperatoreSettore(opSettore);
			nuovoIntEsegMast.setUserIns(opSettore.getCsOOperatore().getUsername());
			nuovoIntEsegMast.setDtIns(new Date());
		}
		
		nuovoIntEsegMast.setFfOrigineId(this.origineFinanziamentoId);
		
		nuovoIntEsegMast.setCsCTipoIntervento(erogazioneHistory.getCsCTipoIntervento());
		nuovoIntEsegMast.setCategoriaSocialeId(erogazioneHistory.getCategoriaSocialeId());
		nuovoIntEsegMast.setCsIInterventoCustom(erogazioneHistory.getCsCTipoInterventoCustom());
		/* altri campi riempiti direttamente da erogazioni.xhtml*/

		/* cancello i valori che sono solo in visualizzazione */
		nuovoIntEsegMast.setFlagSpesaCalc(calcolaSpesaDaDettaglio);
		nuovoIntEsegMast.setFlagCompartCalc(calcolaCompartDaDettaglio);
		
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
			//nuovoIntEseg.getCsIInterventoEsegMast().setCsDRelazione(csRelazione);
		}	
		//fine SISO-500
	}
	
	private void valorizzaIntEsegValori(InterventoErogazHistoryRowBean row, CsIInterventoEseg nuovoIntEseg) {
		List<CsIInterventoEsegValore> listaIntEsegValore = new LinkedList<CsIInterventoEsegValore>();

		for (InterventoErogazAttrBean cell : row.getAttrCells()) {
			CsIInterventoEsegValore intEsegValore = new CsIInterventoEsegValore();
			if (cell.isPersist()) {
				CsCfgAttrUnitaMisura attUm = cell.getAttrUnitaMisuraSelezionata();
				
				intEsegValore.setCsAttributoUnitaMisura(attUm);
				intEsegValore.setCsIInterventoEseg(nuovoIntEseg);
				intEsegValore.setValore(cell.getValue().toString());
				listaIntEsegValore.add(intEsegValore);
			}
		}

		nuovoIntEseg.setDataEsecuzione(row.getDataErogazione());
		nuovoIntEseg.setCsIInterventoEsegValores(listaIntEsegValore);
	}

	private void buildInterventoErogazzAttList(Long tipoInterventoId) {
		BaseDTO bdto = new BaseDTO();
		fillEnte(bdto);
		bdto.setObj(tipoInterventoId);
		//carica configurazioine by tipo intervento
		CsCfgIntEseg csCfgIntEseg = interventoService.findConfigIntErogByTipoIntervento(bdto);
		List<IntEsegAttrBean> lstBean = new ArrayList<IntEsegAttrBean>();
		if(csCfgIntEseg!=null)
			lstBean = IntEsegAttrUtilsBean.Initialize(csCfgIntEseg.getCsCfgIntEsegAttUms());
		else
			logger.warn("Lista 'CsCfgIntEseg' vuota per il tipo intervento:"+tipoInterventoId);

		this.erogaziones = new LinkedList<InterventoErogazAttrBean>();
		if(lstBean.isEmpty())
			logger.warn("Lista 'IntEsegAttrBean' vuota per il tipo intervento:"+tipoInterventoId);
		
		for (IntEsegAttrBean attr : lstBean) {
			InterventoErogazAttrBean intEr = new InterventoErogazAttrBean(attr);
			this.erogaziones.add(intEr);
		}
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
		
	protected VLineaFin getOriginFinanziamentoSelezionato() {
		if (origineFinanziamentoId != null) {			
			for (VLineaFin dOrg : listaOrigineFin) {
				if (dOrg.getId().longValue() == origineFinanziamentoId.longValue())
					return dOrg;
			}
		}
		return null;
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
	
	private void loadListaSettori() {
		CeTBaseObject opDto = new CeTBaseObject();
		fillEnte(opDto);
		listaSettori = confService.getSettoreAll(opDto);
		
		listaSettoriTitGroup = new ArrayList<SelectItem>();
		listaSettoriErGroup = new ArrayList<SelectItem>();
		HashMap<Long, List<CsOSettore>> mappaSettoriTit = new HashMap<Long,List<CsOSettore>>();
		HashMap<Long, List<CsOSettore>> mappaSettoriEr = new HashMap<Long,List<CsOSettore>>();
		if(!listaSettori.isEmpty()){
			for(CsOSettore s : listaSettori){
				 List<CsOSettore> lstTit = mappaSettoriTit.get(s.getCsOOrganizzazione().getId());
				 List<CsOSettore> lstEr = mappaSettoriEr.get(s.getCsOOrganizzazione().getId());
				 if(lstTit==null) lstTit = new ArrayList<CsOSettore>();
				 if(lstEr==null)  lstEr = new ArrayList<CsOSettore>();
				 
				 if(s.getFlagIntTitolare()){
					 lstTit.add(s);
					 mappaSettoriTit.put(s.getCsOOrganizzazione().getId(), lstTit);
				 }
				 if(s.getFlgIntEroga()){
					 lstEr.add(s);
					 mappaSettoriEr.put(s.getCsOOrganizzazione().getId(), lstEr);
				 } 
			}
			
			this.listaSettoriTitGroup = this.loadListaSettoriGroup(mappaSettoriTit);
			this.listaSettoriErGroup =  this.loadListaSettoriGroup(mappaSettoriEr);
			
		}	
	}
	
	private List<SelectItem> loadListaSettoriGroup(HashMap<Long, List<CsOSettore>> mappaSettori){
		ArrayList<SelectItem> listaSettoriGroup = new ArrayList<SelectItem>();
		for(Long ids : mappaSettori.keySet()){
			List<CsOSettore> lst = mappaSettori.get(ids);
			List<SelectItem> settoriGruppo = new ArrayList<SelectItem>();
			if("1".equals(lst.get(0).getCsOOrganizzazione().getAbilitato())){
				SelectItemGroup gr = new SelectItemGroup(lst.get(0).getCsOOrganizzazione().getNome());
				for(CsOSettore s : lst){
					SelectItem si = new SelectItem(s.getId(), s.getNome());
					si.setDisabled(si.isDisabled());
					settoriGruppo.add(si);
				}
				gr.setSelectItems(settoriGruppo.toArray(new SelectItem[settoriGruppo.size()]));
				listaSettoriGroup.add(gr);
			}
		}
		return listaSettoriGroup;
	}
	
	//frida
	private void loadListaOrigineFinanziamenti(){
		this.origineFinanziamentoId=null;
		BaseDTO bdto = new BaseDTO();		
		fillEnte(bdto);		
		listaOrigineFin= interventoService.findAllOrigineFinanziamenti(bdto);
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
		nuovocsIntEseg.setCsOOperatoreSettore(getOperatoreErogSelezionato());
		nuovocsIntEseg.setStato(getStatoSelezionato());
		nuovocsIntEseg.setDtIns(new Date());
	}
	
	protected CsCfgIntEsegStato getStatoSelezionato() {
		if (statoSelezionatoId != null) {
			for (CsCfgIntEsegStato stato : listaStatiErogazione) {
				if (stato.getId() == statoSelezionatoId)
					return stato;
			}
		}
		return null;
	}

	protected boolean validaAttributi(boolean rendicontoPeriodico){
		boolean bOk = true;
		
		bOk = validaTestata();
		
		if( this.dataErog == null ){
			addError("Erogazioni: inserire la data di erogazione", "");
			bOk = false;			
		}else{
			if(rendicontoPeriodico){
				if(this.dataErogA==null){
					addError("Erogazioni: inserire la data di fine erogazione", "");
					bOk = false;	
				}else{
					
					Calendar dataA = Calendar.getInstance();
					dataA.setTime(dataErogA);	
					
					Calendar dataDa = Calendar.getInstance();
					dataDa.setTime(dataErog);
					dataDa.add(Calendar.MONTH, 1);
					
					if(dataA.before(dataDa)){
						addError("Erogazioni: l'intervallo tra la data di inizio e fine deve essere maggiore di un mese", "");
						bOk = false;	
					}
				}
			}
		}
		
		if( this.nuovoIntEsegMast.getDescInterventoEseg() == null || this.nuovoIntEsegMast.getDescInterventoEseg().isEmpty() ){
			addError("Erogazioni: inserire la descrizione", "");
			bOk = false;
		}
						
		CsCfgIntEsegStato statoSelezionato = getStatoSelezionato();
		if( TipoStatoErogazione.EROGATIVO.equals( statoSelezionato.getTipo() ) ){

			for( InterventoErogazAttrBean attr : this.erogaziones )
			{
				if( !attr.isValid() )
				{
					addError("Erogazioni: " + attr.getRequiredMessage(),"");
					bOk = false;
				}
			}
		}

		return bOk;
	}
	
	public void aggiungiAttributi(boolean rendicontoPeriodico) {
		if( !validaAttributi(rendicontoPeriodico) )
			return;		
			
		//TODO
		/* "nuovocsIntEseg" va in "erogazioneHistory.row", "row" verrà ricaricata prima del salvataggio da "valorizzaInterventoEseg()" */
		this.setPrimiDatiInNuovoIntEsegTemp();		
		this.erogazioneHistory.addNew(getSettore(this.selSettoreEroganteId), nuovoIntEsegMast.getDescInterventoEseg(), getStatoSelezionato(), nuovocsIntEseg, visualizzaAttributi ? this.erogaziones : null);

		//Clear
		for (InterventoErogazAttrBean attr : this.erogaziones) attr.reset2default();
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
				if(!seb.isRiferimento())
					this.getAltriSoggetti().remove(i);
			}
		}
		
		initRisorsaFamiliareBean();
	}
	public void onResetSpeseComp(AjaxBehaviorEvent event){
		setBoolDisabledSpese();
	}
	
	public void rimuoviRigaStorico(InterventoErogazHistoryRowBean row){
		this.erogazioneHistory.rimuoviRiga(row);
		
		this.onChangeCalcolaSpeseTotali();
		this.onChangeCalcolaCompartecipazioniTot();
		
		if(!isErogazioniPresenti()){
			boolean erogatoreProprietario = this.isPermessoErogativo() && this.isOperatoreProprietario();
			if(erogatoreProprietario || isPermessoAutorizzativo())
				testataDisabled=false;
		}
	}
	
	public void onChangeValoriSpesaDettaglio(){
		
		if(this.calcolaSpesaDaDettaglio){
			Double spesaCalc = calcolaMasterSpesaTotale();
			Double percCalc =  calcolaMasterPercSpesa();
			Double valSpesa =  calcolaMasterValSpesa();
			
			nuovoIntEsegMast.setSpesa(new BigDecimal(spesaCalc));
			nuovoIntEsegMast.setPercGestitaEnte(new BigDecimal(percCalc));
			nuovoIntEsegMast.setValoreGestitaEnte(new BigDecimal(valSpesa));
		}
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
			CsOOperatoreSettore op=(CsOOperatoreSettore) getSession().getAttribute("operatoresettore");
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
		}
		sb.append(String.format("%s%s\t\t", "Note :", this.nuovoIntEsegMast.getDescInterventoEseg()) + "\n");

		sb.append("**************  LISTA ATTRIBUTI  *************\n");
		for (InterventoErogazAttrBean b : erogaziones)
			b.toString(sb);

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

	public List<InterventoErogazAttrBean> getErogaziones() {
		return erogaziones;
	}

	public void setErogaziones(List<InterventoErogazAttrBean> erogaziones) {
		this.erogaziones = erogaziones;
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
		return nomeTipoErogazione;
	}
		
	public boolean isVisualizzaAttributi() {
		return visualizzaAttributi;
	}

	public List<SelectItem> getLstSelItemStatiErog() {
		if(listaStatiErogazione!=null){
			lstSelItemStatiErog = new ArrayList<SelectItem>();
			for(CsCfgIntEsegStato stato : listaStatiErogazione){
				SelectItem si = new SelectItem(stato.getId(),stato.getNome());
				lstSelItemStatiErog.add(si);
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

	public String getNomeTipoCustEroga() {
		return nomeTipoCustEroga;
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

	public Long getOrigineFinanziamentoId() {
		return origineFinanziamentoId;
	}

	public void setOrigineFinanziamentoId(Long origineFinanziamentoId) {
		this.origineFinanziamentoId = origineFinanziamentoId;
	}

	public List<VLineaFin> getListaOrigineFin() {
		return listaOrigineFin;
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

	public String getCodFinanziamento() {
		return codFinanziamento;
	}

	public void setCodFinanziamento(String codFinanziamento) {
		this.codFinanziamento = codFinanziamento;
	}

	public Boolean getDentroIntervento() {
		return dentroIntervento;
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
		
		if(!this.dentroIntervento)
			listaOpzioniBen.add(DataModelCostanti.ListaBeneficiari.GRUPPO);
		
		return listaOpzioniBen;
	}

	public Boolean getErogazionePossibile() {
		return erogazionePossibile|| !this.dentroIntervento;
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
	
	public boolean isAbilitaCheckCalcolo(){
		boolean storicoVuoto = this.erogazioneHistory.getRows().isEmpty();
		return storicoVuoto; //TODO: Abilito anche quando non è vuoto, ma non sono state inserite erogazioni!
	
	}

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
				String cittadinanza = getCittadinanzaByIstat(p.getCodIstatCittadinanza());
				sero = new SoggettoErogazioneBean(p.getCognome(), p.getNome(), p.getCodfisc(), cittadinanza, p.getDataNascita(), false);
			}else{ 
				SitDPersona p = (SitDPersona) s.getSoggetto();
				if(p.getDataMor()!=null && p.getDataMor().before(new Date())){
					addWarning("Non è possibile inserire il soggetto come beneficiario","Il soggetto selezionato è deceduto il "+ddMMyyyy.format(p.getDataMor()));
					return;
				}
				sero = new SoggettoErogazioneBean(p, false);
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
	
	public List<CsOSettore> getListaSettori() {
		return listaSettori;
	}

	public Long getSelSettoreTitolareId() {
		return selSettoreTitolareId;
	}

	public void setListaSettori(List<CsOSettore> listaSettori) {
		this.listaSettori = listaSettori;
	}

	public void setSelSettoreTitolareId(Long selSettoreTitolareId) {
		this.selSettoreTitolareId = selSettoreTitolareId;
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
	
	
	public void onUpdateQuotaDettaglio(BigDecimal tariffa){
		tariffa = tariffa!=null ? tariffa : BigDecimal.ZERO;
		BigDecimal valQuota = nuovocsIntEseg.getCsIValQuota().getValQuota()!=null ? nuovocsIntEseg.getCsIValQuota().getValQuota() : BigDecimal.ZERO;
		BigDecimal spesa = tariffa.multiply(valQuota);
		this.nuovocsIntEseg.setSpesa(spesa);
	}
	
	public void onUpdateTariffa(BigDecimal tariffa){
		if(tariffa==null || tariffa.equals(BigDecimal.ZERO))
			this.nuovocsIntEseg.setCsIValQuota(new CsIValQuota());
		else{
			if(this.nuovocsIntEseg.getCsIValQuota()==null)
				this.nuovocsIntEseg.setCsIValQuota(new CsIValQuota());
		}
		
		if(this.calcolaSpesaDaDettaglio)
			this.onUpdateQuotaDettaglio(tariffa);
	//	else
	//		this.onUpdateQuotaTestata(tariffa);
	}
	
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

	public List<SelectItem> getListaSettoriTitGroup() {
		return listaSettoriTitGroup;
	}

	public List<SelectItem> getListaSettoriErGroup() {
		return listaSettoriErGroup;
	}

	public void setListaSettoriTitGroup(List<SelectItem> listaSettoriTitGroup) {
		this.listaSettoriTitGroup = listaSettoriTitGroup;
	}

	public void setListaSettoriErGroup(List<SelectItem> listaSettoriErGroup) {
		this.listaSettoriErGroup = listaSettoriErGroup;
	}

}
