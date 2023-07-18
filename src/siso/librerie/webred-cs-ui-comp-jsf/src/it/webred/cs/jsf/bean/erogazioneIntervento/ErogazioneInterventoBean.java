package it.webred.cs.jsf.bean.erogazioneIntervento;

import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTablePsExportSessionBeanRemote;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableNazioniSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ErogazionePrestazioneDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.ejb.dto.RisorsaFamDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.IntEsegAttrBean;
import it.webred.cs.csa.ejb.dto.erogazioni.SoggettoErogazioneBean;
import it.webred.cs.csa.ejb.dto.erogazioni.configurazione.ErogStatoCfgDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.CSIPs.FLAG_IN_CARICO;
import it.webred.cs.data.DataModelCostanti.CSIPs.PROVA_MEZZI;
import it.webred.cs.data.DataModelCostanti.ListaBeneficiari;
import it.webred.cs.data.DataModelCostanti.TariffeErogazioni.TIPO_FREQUENZA_SERVIZIO;
import it.webred.cs.data.DataModelCostanti.TariffeErogazioni.TIPO_FREQUENZA_SERVIZIO_CADENZA;
import it.webred.cs.data.DataModelCostanti.TariffeErogazioni.TIPO_RENDICONTO;
import it.webred.cs.data.DataModelCostanti.TipoRicercaSoggetto;
import it.webred.cs.data.DataModelCostanti.TipoStatoErogazione;
import it.webred.cs.data.model.ArRelClassememoPresInps;
import it.webred.cs.data.model.ArTbPrestazioniInps;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsAAnagrafica;
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
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsTbSchedaMultidim;
import it.webred.cs.jsf.bean.DatiUserSearchBean;
import it.webred.cs.jsf.manbean.ComuneNazioneNascitaMan;
import it.webred.cs.jsf.manbean.ComuneNazioneResidenzaMan;
import it.webred.cs.jsf.manbean.ProtocolloDsuMan;
import it.webred.cs.jsf.manbean.RisorsaFamiliareBean;
import it.webred.cs.jsf.manbean.TempCodFiscManager;
import it.webred.cs.jsf.manbean.UserSearchBeanExt;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.sociosan.ejb.dto.isee.DatiIsee;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.config.model.AmTabRegioni;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;
import it.webred.jsf.bean.SessoBean;
import it.webred.siso.ws.ricerca.dto.PersonaDettaglio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.wizard.Wizard;
import org.primefaces.context.RequestContext;
import org.primefaces.util.ComponentUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ErogazioneInterventoBean extends CsUiCompBaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String DATA_EVENTO_LABEL_DEFAULT = "Data evento che dà diritto alla prestazione ";
	
	protected AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) getCarSocialeEjb("AccessTableInterventoSessionBean");
	protected AccessTablePsExportSessionBeanRemote psExportService = (AccessTablePsExportSessionBeanRemote) getCarSocialeEjb("AccessTablePsExportSessionBean"); // SISO-524
	
	private boolean visualizzaInterventoData = false;

	private SoggettoErogazioneBean soggettoErogazione = null;
	private SessoBean sessoBeneficiario; //SISO_1138
	private ProtocolloDsuMan protDsuMan;

	private SoggettoErogazioneBean altroSoggettoTmp;
	private boolean nuovoInsSoggManuale = false;
	private List<SoggettoErogazioneBean> altriSoggetti;

	// SISO-760
	private Boolean cambiaBeneficiarioRiferimento = false;
	private SoggettoErogazioneBean beneficiarioSel;

	private RisorsaFamiliareBean risorsaFamBean;

	private Date dataErog;
	private Date dataErogA;
	private Date dataEvento;
	private Long statoSelezionatoId;

	private List<CsCfgIntEsegStato> listaStatiErogazione;
	private List<SelectItem> lstSelItemStatiErog;
	private List<SelectItem> listaSettoriErGroup;
	
	/* -=- */

	private Long selSettoreEroganteId;

	// frida
	private List<String> listaCittadinanze;
	private List<String> listaOpzioniBen;
	private Date dataAttivazioneDa;
	private Boolean testataDisabled = false;
	private boolean dentroFascicolo;  //La variabile specifica se l'apertura della dialog viene effettuata dalla lista interna al Fascicolo del caso o no.
	private Boolean erogazionePossibile=true;
	private CsIInterventoEsegMast nuovoIntEsegMast;
	private CsIInterventoEseg nuovocsIntEseg;
	private List<CsIInterventoEseg> interEsegHistory = new ArrayList<CsIInterventoEseg>();
	private boolean erogazioniEsportate = false; // SISO-522
	private List<SelectItem> listaOperAnagrafica;

	private Long operatoreErogId; //ID CS_O_OPERATORE_SETTORE erogante
	private String testaPercOrValue = DataModelCostanti.ErogazioneInterventoConst.TESTATA_SPESA_PERCENTUALE;
	private String operatoreScegliOrInserisci = DataModelCostanti.ErogazioneInterventoConst.SCEGLI_OPERATORE;

	private Long valQuotaTestata;
	private String motivazioneQuotaTestata;

	private Boolean calcolaSpesaDaDettaglio = true;

	private Boolean isSpesaPercTesta = true;
	private Boolean isSpesaPercDetta = true;
	private Boolean isSpesaValTesta = false;
	private Boolean isSpesaValDetta = false;
	private CsIPs csIPs = null; // MOD-RL
	//SISO-1162
	private Boolean isCodiceInpsObbligatorio  = true;
	private String labelCodicePrestazione = "Codice prestazione INPS";
	//
	private boolean disabilitaOrganizzazione = false;
	// private List<InterventoErogazAttrBean> erogaziones;
	private HashMap<Long, List<InterventoErogazAttrBean>> mappaErogStatoAttr;
	private InterventoErogazHistoryBean erogazioneHistory = new InterventoErogazHistoryBean();
	private boolean visualizzaAttributi = false;
	private boolean enabledAggiungi = false;

	private CsCTipoIntervento tipoIntervento;
	private CsCTipoInterventoCustom tipoIntCustom;

	// Gestione ISEE
	// SISO-657 private boolean situazioneEconomicaVerificata;
	private String iseeSelezionata;
	private List<SelectItem> listaCodiciPrestazione; // MOD-RL
	private List<String> listaPrestazioniPIC;
	private List<KeyValueDTO> listaIsee; // MOD-RL
	// SISO-657 private String txtProtocolloDomandaPrestazioneLabel ;
	// SISO-657 private Integer flagProvaMezzi;
	// private String oldFlagProvaMezzi; // SISO-657
	// SISO-657 private Integer flagPresoInCarico;

	/* SISO-663 SM */

	/* SISO-720 SM */
	private BigDecimal tariffaLocale;
	//private RuoloEnteGestoreSpesa ruoloEnteGestoreSpesa;

	private CsOSettore settoreTitolare;
	private CsOSettore settoreGestore;

	private final ErogazioneInterventoTooltipText erogazioneInterventoTooltipText = new ErogazioneInterventoTooltipText();
	private boolean gestoreComeTitolare = false;

	// SISO-809
	private boolean esportazioneDiGruppo = false;
	// SISO-722
	private boolean nonSoPresoInCarico = false;

	// SISO-860
	private boolean sceltaRadio = false;
	
	//SISO-962
	private ComuneNazioneResidenzaMan comuneNazioneResidenzaMan = null;
	
	private ComuneNazioneNascitaMan comuneNazioneNascitaMan = null;

	//SISO 1201
	private boolean nazioneResidenzaNonDefinita = false;
	
	//Minori in Struttura
	private Boolean isStrutturaSelezionata = false;
	
	private String dataEventoLabel = DATA_EVENTO_LABEL_DEFAULT;
		
	public ComuneNazioneResidenzaMan getComuneNazioneResidenzaMan() {
		return comuneNazioneResidenzaMan;
	}

	public void setComuneNazioneResidenzaMan(ComuneNazioneResidenzaMan comuneNazioneResidenzaMan) {
		this.comuneNazioneResidenzaMan = comuneNazioneResidenzaMan;
	}
	
	public Boolean getIsStrutturaSelezionata() {
		return isStrutturaSelezionata;
	}

	public void setIsStrutturaSelezionata(Boolean isStrutturaSelezionata) {
		this.isStrutturaSelezionata = isStrutturaSelezionata;
	}

	//SISO-806
	private Long oreErogazione;
	private Long minutiErogazione;

	// #ROMACAPITALE inizio
	private CsOSettore settoreSelezionato;
	
	public CsOSettore getSettoreSelezionato() {
		return settoreSelezionato;
	}

	public void setSettoreSelezionato(CsOSettore settoreSelezionato) {
		this.settoreSelezionato = settoreSelezionato;
	}	

	private List<Long> listaIdSettoriByIntervervento;

	public List<Long> getListaIdSettoriByIntervervento() {
		return listaIdSettoriByIntervervento;
	}

	public void setListaIdSettoriByIntervervento(List<Long> listaIdSettoriByIntervervento) {
		this.listaIdSettoriByIntervervento = listaIdSettoriByIntervervento;
	}

	private Boolean visualizzaPannelloDettaglio;
	
	public Boolean getVisualizzaPannelloDettaglio() {
		return visualizzaPannelloDettaglio;
	}

	public void setVisualizzaPannelloDettaglio(Boolean visualizzaPannelloDettaglio) {
		this.visualizzaPannelloDettaglio = visualizzaPannelloDettaglio;
	}
	// #ROMACAPITALE fine
	public void inizializzaNuovaErogazione(Long tipoInterventoId,Long tipoInterventoCustomId ,SoggettoErogazioneBean soggettoErogazione, Long catSocialeId, boolean dentroFascicolo) {
		this.dentroFascicolo = dentroFascicolo;

		// SISO_760
		this.setCambiaBeneficiarioRiferimento(false);

		this.altroSoggettoTmp = new SoggettoErogazioneBean(false);
		this.altriSoggetti = new ArrayList<SoggettoErogazioneBean>();
		
		if( soggettoErogazione == null ){
			this.soggettoErogazione = new SoggettoErogazioneBean(true);	
			this.sessoBeneficiario = new SessoBean();
			//this.dentroFascicolo=false;
		} else {
			this.soggettoErogazione = soggettoErogazione; //TODO:Verificare che riferimento sia a true
			//this.altriSoggetti.add(soggettoErogazione);
			
			// SISO-1138
			String s = soggettoErogazione.getSesso();		
			this.sessoBeneficiario = new SessoBean(s);
			
			// --
			
			// this.dentroFascicolo=(soggettoErogazione.getCsASoggetto()!=null);
		}

		initRisorsaFamiliareBean();
		nuovoIntEsegMast = new CsIInterventoEsegMast();
		setCsIPs(new CsIPs()); // MOD-RL
		testataDisabled = false;

		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(tipoInterventoId);
		this.tipoIntervento = confService.getTipoInterventoById(dto);

		initTipoInterventoCustom(tipoInterventoCustomId);
		
		buildInterventoErogazzAttList(tipoInterventoCustomId!=null ?tipoInterventoCustomId : tipoInterventoId);
		loadListaStatiInterventi(tipoInterventoCustomId!=null ?tipoInterventoCustomId : tipoInterventoId);
		//loadListaSettori();
		resetSettoriTestata();
		resetOrganizAndNuovoIntEseg();	
		
		nuovoIntEsegMast.setTipoBeneficiario(DataModelCostanti.ListaBeneficiari.INDIVIDUALE);
		nuovoIntEsegMast.setTotBeneficiari(Integer.valueOf(1));
		
		// this.codFinanziamento=null;

		// Recupera oggetto intervento
		this.erogazioneHistory = new InterventoErogazHistoryBean();
		interEsegHistory=this.erogazioneHistory.initialize(mappaErogStatoAttr, tipoIntervento, tipoIntCustom, catSocialeId); 
				
		resetBoolDisabledSpese();

		this.visualizzaInterventoData = false;
		this.statoSelezionatoId = null;
		this.visualizzaAttributi = false;
		this.enabledAggiungi = false;
		this.erogazioniEsportate = false;
		this.sceltaRadio = false;
		// Comanda comunque INTERVENTO_ID (non il custom!)
		initDatiSituazioneEconomicaVerificata(tipoInterventoId);
		
		//SISO-962 INIZIO
		initDatiResidenza();
		initDatiNascita();
		//SISO-962 FINE
	}

	// per #ROMACAPITALE è necessario filtrare la tendina degli eroganti
	// in base all'intervento ISTAT selezionato mappato nella tabella
	// CS_REL_INT_SETTORE e l'intervento CUSTOM (che è uguale all'oganizzazione ma con id diversi)
	public void caricaListaIdSettoriByIntervento(Long idInterventoISTAT, Long idInterventoCUSTOM) {
		setVisualizzaPannelloDettaglio(false);

		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		
		if (idInterventoISTAT != null) {
			dto.setObj(idInterventoISTAT);
		}
		if (idInterventoCUSTOM != null) {
			dto.setObj2(idInterventoCUSTOM);
		}
		
		listaIdSettoriByIntervervento = confEnteService.findIdSettoriByInterventoISTATandInterventoCustom(dto);
		
		settoreSelezionato = null;
	}
	
	private void initDatiNascita() {
		comuneNazioneNascitaMan = new ComuneNazioneNascitaMan();
		
		 //Se mi trovo in una nuova erogazione recupero i dati dalla Cartella sociale
		 //Se sono in visualizza/modifica trovo valorizzati i dati relativi al MastSogg
		 if(StringUtils.isBlank(soggettoErogazione.getComuneNascita()) && 
			StringUtils.isBlank(soggettoErogazione.getNazioneNascita())){
			 
			 	CsASoggettoLAZY csASoggetto = (soggettoErogazione.getCsASoggetto() != null ? soggettoErogazione.getCsASoggetto() :  null);
			 	if(csASoggetto != null){
			 		String jsonComuneNascita = this.getJsonNascitaComuneBean(csASoggetto);
					
			 		soggettoErogazione.setComuneNascita(jsonComuneNascita);
			 		soggettoErogazione.setNazioneNascita(csASoggetto.getCsAAnagrafica().getStatoNascitaCod());
				}
		 }
		
		 this.valorizzaNascitaMan();
		
	}
	
	//SISO-962 INIZIO	
	private void initDatiResidenza(){
		 comuneNazioneResidenzaMan = new ComuneNazioneResidenzaMan();
		 
		 //Se mi trovo in una nuova erogazione recupero i dati dalla Cartella sociale
		 //Se sono in visualizza/modifica trovo valorizzati i dati relativi al MastSogg
		 if(StringUtils.isBlank(soggettoErogazione.getComuneResidenza()) && 
			StringUtils.isBlank(soggettoErogazione.getNazioneResidenza()) && 
			StringUtils.isBlank(soggettoErogazione.getViaResidenza())){
			 	sincronizzaResidenza(soggettoErogazione);
		 }
		 
		 this.valorizzaResidenzaMan();
		
	}
	//SISO-962 FINE
	
	public void inizializzaErogazione(Long tipoInterventoId,Long tipoInterCustomId ,Long interventoId, SoggettoErogazioneBean soggettoErogazione, List<SoggettoErogazioneBean> beneficiari, boolean dentroFascicolo) throws Exception {
		//this.dentroFascicolo=true;
		this.dentroFascicolo = dentroFascicolo;

		// SISO-760
		this.setCambiaBeneficiarioRiferimento(false);

		altriSoggetti = new ArrayList<SoggettoErogazioneBean>();
		
		loadListaStatiInterventi(tipoInterCustomId!=null ?tipoInterCustomId : tipoInterventoId);
		buildInterventoErogazzAttList(tipoInterCustomId!=null ?tipoInterCustomId : tipoInterventoId);
		//loadListaSettori();
		
		BaseDTO bdto = new BaseDTO();
		fillEnte(bdto);
		bdto.setObj(interventoId);
 		CsIIntervento csIIntervento = this.interventoService.getInterventoById(bdto);
		CsIPs csIPs = this.interventoService.getCsIPsByInterventoId(bdto);
		initTipoInterventoCustom(tipoInterCustomId);
		
        this.erogazioneHistory = new InterventoErogazHistoryBean();
		if (csIIntervento != null) { //TODO attualmente in realtà questo metodo viene chiamato solo se interventoI!=null
			this.tipoIntervento = csIIntervento.getCsRelSettCsocTipoInter().getCsRelCatsocTipoInter().getCsCTipoIntervento();
			this.interEsegHistory=this.erogazioneHistory.initialize(mappaErogStatoAttr, csIIntervento); 
			this.erogazioniEsportate = verificaErogazioniEsportate(interEsegHistory);

			this.visualizzaInterventoData = true;

			// inizio SISO-500
			if (csIPs != null) {
				setCsIPs(csIPs);
				this.sceltaRadio = this.getCsIPs().getFlagInCarico() == null ? false : true; // SISO-860
			} else {
				setCsIPs(new CsIPs());
				this.sceltaRadio = false; // SISO-860
				// fine SISO-500
			}
		}

		this.soggettoErogazione = soggettoErogazione; //Verificare che il flag riferimento=true

		this.altriSoggetti.addAll(beneficiari);

		this.initRisorsaFamiliareBean();

		this.resetSettoriTestata();
		this.resetOrganizAndNuovoIntEseg();

		testataDisabled = false;

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
		} else {
			// this.codFinanziamento=null;
			this.nuovoIntEsegMast = new CsIInterventoEsegMast();

			// SISO-500 setCsIPs(new CsIPs());//MOD-RL
		}
		
		if(nuovoIntEsegMast.getTipoBeneficiario()==null || nuovoIntEsegMast.getTipoBeneficiario().isEmpty()){
			nuovoIntEsegMast.setTipoBeneficiario(DataModelCostanti.ListaBeneficiari.INDIVIDUALE);
			nuovoIntEsegMast.setTotBeneficiari(Integer.valueOf(1));
		}
		nuovoIntEsegMast.setInterventoProgrammatoId(csIIntervento!=null ? csIIntervento.getId() : null);
		
		//Comanda comunque INTERVENTO_ID (non il custom!)
		initDatiSituazioneEconomicaVerificata(tipoInterventoId);
		initDatiResidenza();
		initDatiNascita();
		
		//#ROMACAPITALE
		setVisualizzaPannelloDettaglio(false);
		settoreSelezionato = super.findSettoreById(this.selSettoreEroganteId);
		
		if(this.selSettoreEroganteId != null && settoreSelezionato != null && getListaIdSettoriByIntervervento() != null && getListaIdSettoriByIntervervento().size() > 0)
		{
			setVisualizzaPannelloDettaglio(true);
		} else {
			setVisualizzaPannelloDettaglio(false);
		}
		//#ROMACAPITALE fine
	}

	public void inizializzaDaErogazioneMaster(Long tipoInterventoId,Long tipoInterCustomId , CsIInterventoEsegMast master, boolean flgDaFascicolo) {
		dentroFascicolo= flgDaFascicolo;
		
		//SISO-760
		setCambiaBeneficiarioRiferimento(false);

		// Init beneficiari
		List<SoggettoErogazioneBean> beneficiari = new ArrayList<SoggettoErogazioneBean>();
		if (!master.getBeneficiari().isEmpty() && master.getBeneficiari().size() != beneficiari.size()) {
			for (CsIInterventoEsegMastSogg sm : master.getBeneficiari()) {
				if(!sm.getRiferimento()){
					SoggettoErogazioneBean sb = new SoggettoErogazioneBean(sm);
					beneficiari.add(sb);
				}else{
				   soggettoErogazione = new SoggettoErogazioneBean(sm);
				}
			}
		}
		 
		altriSoggetti = new ArrayList<SoggettoErogazioneBean>();
		this.altriSoggetti.addAll(beneficiari);
		
		loadListaStatiInterventi(tipoInterCustomId!=null ?tipoInterCustomId : tipoInterventoId);
		
		buildInterventoErogazzAttList(tipoInterCustomId!=null ?tipoInterCustomId : tipoInterventoId);
		//loadListaSettori();
		
		BaseDTO bdto = new BaseDTO();
		fillEnte(bdto);
		this.nuovoIntEsegMast = master;

		this.tipoIntervento = master.getCsCTipoIntervento();
		this.initTipoInterventoCustom(tipoInterCustomId);

        this.erogazioneHistory = new InterventoErogazHistoryBean();
		this.interEsegHistory = this.erogazioneHistory.initialize(mappaErogStatoAttr, master);
		this.erogazioniEsportate = verificaErogazioniEsportate(interEsegHistory);

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
			
			//#ROMACAPITALE			
			setVisualizzaPannelloDettaglio(false);
			settoreSelezionato = super.findSettoreById(this.selSettoreEroganteId);
			
			if(this.selSettoreEroganteId != null && settoreSelezionato != null && getListaIdSettoriByIntervervento() != null && getListaIdSettoriByIntervervento().size() > 0)
			{
				setVisualizzaPannelloDettaglio(true);
			} else {
				setVisualizzaPannelloDettaglio(false);
			}
			//#ROMACAPITALE fine
		}

		if (this.isErogazioniPresenti())
			testataDisabled = !this.isOperatoreProprietario();

		testataDisabled = testataDisabled || isErogazioniPresenti();

		setCsIPs(master.getCsIPs()!=null ? master.getCsIPs() : new CsIPs()); //SISO-498
		//SISO_1138
		String s = master.getBeneficiarioRiferimento().getSesso();
		this.sessoBeneficiario = new SessoBean(s);
		//SISO-1138 FINE
		
		this.sceltaRadio = this.getCsIPs().getFlagInCarico() == null ? false : true ;

		// Comanda comunque INTERVENTO_ID (non il custom!)
		initDatiSituazioneEconomicaVerificata(tipoInterventoId);
		initDatiResidenza();
		initDatiNascita();
		
		verificaAllineamentoSoggettoCaso();
	}
	
	private void verificaAllineamentoSoggettoCaso() {
		CsASoggettoLAZY csASoggetto = (soggettoErogazione.getCsASoggetto() != null ? soggettoErogazione.getCsASoggetto() :  null);
		if(csASoggetto != null){
		    CsAAnaIndirizzo residenza =  findIndirizzoResidenzaCaso(csASoggetto);
		    String viaResidenzaCaso = residenza!=null ? residenza.getLabelIndirizzo() : null;
		    String statoResidenzaCaso = residenza !=null ? residenza.getStatoCod() : null;
		    String comuneResidenzaCaso = residenza!=null ? getCasoComuneResidenza(residenza) : null;
		    String comuneNascitaCaso = getJsonNascitaComuneBean(csASoggetto);
		    soggettoErogazione.verificaAllineamentoCaso(viaResidenzaCaso, comuneResidenzaCaso, statoResidenzaCaso, comuneNascitaCaso);
		}
	}

	private void initTipoInterventoCustom(Long idTipoInteventoCustom) {
		this.tipoIntCustom = null;
		if (idTipoInteventoCustom != null) {
			BaseDTO bdto = new BaseDTO();
			fillEnte(bdto);
			bdto.setObj(idTipoInteventoCustom);
	        this.tipoIntCustom= confService.findTipoInterventoCustomById(bdto);
		}
	}

	private void initRisorsaFamiliareBean() {
		risorsaFamBean = null;
		if(this.isNucleoBeneficiario() && this.dentroFascicolo)
			this.risorsaFamBean = new RisorsaFamiliareBean(soggettoErogazione.getCf());
	}

	public void initDatiSituazioneEconomicaVerificata(Long idTipoIntervento) {
		// SISO-657 situazioneEconomicaVerificata = false;
		// SISO-657 if (this.getCsIPs()!=null &&
		// this.getCsIPs().getDtIns()!=null) {
		// this.situazioneEconomicaVerificata = true;
		// }

		loadDatiIsee(); // MOD-RL
		loadlListaCodiciPrestazione(idTipoIntervento);// MOD-RL

	}

	private void resetBoolDisabledSpese() {
		testaPercOrValue = DataModelCostanti.ErogazioneInterventoConst.TESTATA_SPESA_PERCENTUALE;
//		this.calcolaCompartDaDettaglio = true;
		this.calcolaSpesaDaDettaglio = true;

		isSpesaPercTesta = true;
		isSpesaPercDetta = true;
		isSpesaValTesta = false;
		isSpesaValDetta = false;

	}

	private void resetSettoriTestata() {
		// Preseleziono l'organizzazione corrente
		CsOSettore settoreCorrente = getCurrentOpSettore().getCsOSettore();
		this.selSettoreEroganteId = settoreCorrente.getId();
		//this.selSettoreTitolareId = settoreCorrente.getId();
		
		this.disabilitaOrganizzazione=settoreCorrente.getCsOOrganizzazione().getCodRouting()==null;
		//loadListaOrigineFinanziamenti();
	}

	private void resetOrganizAndNuovoIntEseg() {
		this.dataErog = dataAttivazioneDa;
		this.dataErogA = null; // SISO-556
		this.dataEvento = null;
		if (this.selSettoreEroganteId != null && selSettoreEroganteId.compareTo((long) 0) != 0) {
			loadListaOperatoriEroganti(selSettoreEroganteId);
		}
		this.nuovocsIntEseg = new CsIInterventoEseg();
		CsOOperatoreSettore op = getCurrentOpSettore();
		this.operatoreErogId = op.getId();
		this.operatoreScegliOrInserisci = DataModelCostanti.ErogazioneInterventoConst.SCEGLI_OPERATORE;
	}

	private void setBoolDisabledSpese() {

		/* valuto spese master */
		if(nuovoIntEsegMast.getFlagSpesaCalc()==null){
			boolean totaleVal = 
					(nuovoIntEsegMast.getSpesa()!=null             && nuovoIntEsegMast.getSpesa().compareTo(new BigDecimal(0))!=0)||
					(nuovoIntEsegMast.getValoreGestitaEnte()!=null && nuovoIntEsegMast.getValoreGestitaEnte().compareTo(new BigDecimal(0))!=0) ||
					(nuovoIntEsegMast.getPercGestitaEnte()!=null   && nuovoIntEsegMast.getPercGestitaEnte().compareTo(new BigDecimal(0))!=0)||
					(nuovoIntEsegMast.getCompartUtenti()!=null     && nuovoIntEsegMast.getCompartUtenti().compareTo(new BigDecimal(0))!=0) ||
					(nuovoIntEsegMast.getCompartSsn()!=null        && nuovoIntEsegMast.getCompartSsn().compareTo(new BigDecimal(0))!=0) ||
					(nuovoIntEsegMast.getCompartAltre()!=null      && nuovoIntEsegMast.getCompartAltre().compareTo(new BigDecimal(0))!=0);
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

	}
	
	public void addMessage(FacesMessage.Severity tipoMessaggio,String summary) {
        FacesMessage message = new FacesMessage(tipoMessaggio, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	private boolean validaCittadinanza(String cittadinanza){
		AmTabNazioni nazione = luoghiService. getNazioneByNazionalita(cittadinanza);
		return nazione!=null && nazione.getAttivo();
	}
	
	public boolean validaTestata(){
		boolean valido=true;
		
		if(this.erogazionePossibile){
			List<String> lstcf = new ArrayList<String>();
			if(!soggettoErogazione.isValorizzato()){
				addWarning("Beneficiari","Anagrafica incompleta del soggetto beneficiario di riferimento");
				valido=false;
			}else if(!validaCittadinanza(soggettoErogazione.getCittadinanza())){
				addWarning("Beneficiari","La cittadinanza del soggetto beneficiario di riferimento non è più valida");
				valido=false;
			}else if(!StringUtils.isEmpty(soggettoErogazione.getCittadinanza2()) && !validaCittadinanza(soggettoErogazione.getCittadinanza2())){
				addWarning("Beneficiari","La seconda cittadinanza del soggetto beneficiario di riferimento non è più valida");
				valido=false;
			}else
				lstcf.add(soggettoErogazione.getCf().toUpperCase());
			
			if (!this.isUnicoBeneficiario()) {
				
				if(nuovoIntEsegMast.getTotBeneficiari()==null || nuovoIntEsegMast.getTotBeneficiari().intValue()==0){
					addWarning("Beneficiari","Il totale dei soggetti beneficiari è un campo obbligatorio");
					valido=false;
				}
					
				if (!getAltriSoggetti().isEmpty()) {
					for(SoggettoErogazioneBean seb : this.getAltriSoggetti()){
						if(!seb.isValorizzato()){
							addWarning("Beneficiari","Dati incompleti per il soggetto: "+ seb.getCognome()+" "+seb.getNome());
							valido=false;
						}
	
						// Verifica che non ci siano soggetti uguali
						if (!lstcf.contains(seb.getCf().toUpperCase()))
							lstcf.add(seb.getCf().toUpperCase());
						else{
							addWarning("Beneficiari","Beneficiario duplicato: "+ seb.getCf());
							valido=false;
						}
					}
					
					Integer countAnagrafiche = this.getAltriSoggetti().size() +1;
					if(countAnagrafiche.compareTo(nuovoIntEsegMast.getTotBeneficiari()) > 0){
						addWarning("Beneficiari","Il totale di beneficiari elencati, compreso quello di riferimento, non può superare il valore dichiarato nel campo 'num.totale soggetti beneficiari' ");
						valido=false;
					}
				}
	
			}
			
			//SISO-1136  Commentato il controllo su obbligatorietà dei campi.
			// SISO-663 SM
//			if (this.nuovoIntEsegMast.getEnteGestoreSpesa() == null) {
//				valido = false;
//				addWarning("Erogazioni", "Ente gestore della spesa non selezionato");
//			}
			if (selSettoreEroganteId == null || selSettoreEroganteId.equals(0)) {
				valido = false;
				this.addErrorCampiObbligatori("Erogazioni", "Settore erogante");
			}
	
			// INIZIO SISO-556
			if (nuovoIntEsegMast.getValoreGestitaEnte() != null
					&& nuovoIntEsegMast.getSpesa() != null
					&& nuovoIntEsegMast.getValoreGestitaEnte().compareTo( nuovoIntEsegMast.getSpesa() ) > 0  ) { 
				addWarning("Erogazioni","Valore quota a carico dell'ente di testata non corretto:inserire valore numerico intero o decimale (es.12,34), minore o uguale alla Spesa");
				valido=false;
			}
			//FINE SISO-556
			BigDecimal totaleCompartecipazioni = BigDecimal.ZERO; 
			
			if(validaImporto(this.nuovoIntEsegMast.getCompartSsn(), "Compartecipazione SSN alla Spesa"))
				totaleCompartecipazioni = totaleCompartecipazioni.add(this.nuovoIntEsegMast.getCompartSsn()); 
			if(validaImporto(this.nuovoIntEsegMast.getCompartUtenti(), "Compartecipazione Utenti alla Spesa"))
				totaleCompartecipazioni = totaleCompartecipazioni.add(this.nuovoIntEsegMast.getCompartUtenti());
			if(validaImporto(this.nuovoIntEsegMast.getCompartAltre(), "Compartecipazione SSN alla Spesa"))
				totaleCompartecipazioni = totaleCompartecipazioni.add(this.nuovoIntEsegMast.getCompartAltre());
			if(totaleCompartecipazioni.compareTo(nuovoIntEsegMast.getSpesa())> 0){
					addWarning("Erogazioni","La somma delle compartecipazioni non può superare il totale della spesa");
					valido=false;
			}
			
			BigDecimal quotaEnte = null;
			if(this.nuovoIntEsegMast.getValoreGestitaEnte()!=null && this.nuovoIntEsegMast.getValoreGestitaEnte().doubleValue()>0)
				quotaEnte = this.nuovoIntEsegMast.getValoreGestitaEnte();
			else if(this.nuovoIntEsegMast.getPercGestitaEnte()!=null && this.nuovoIntEsegMast.getPercGestitaEnte().doubleValue()>0)
				quotaEnte = this.nuovoIntEsegMast.getSpesa().multiply(this.nuovoIntEsegMast.getPercGestitaEnte()).divide(new BigDecimal(100));
			
			 if(quotaEnte!=null){
				 //Valida
//				 BigDecimal quote = totaleCompartecipazioni.add(quotaEnte);
				 BigDecimal quote = (totaleCompartecipazioni.add(quotaEnte)).setScale(2, RoundingMode.HALF_EVEN);
				 
				 if(!quote.equals(this.nuovoIntEsegMast.getSpesa().setScale(2, RoundingMode.HALF_EVEN))){
					 addWarning("Erogazioni","La somma delle compartecipazioni ("+totaleCompartecipazioni+" €) e della quota a carico dell'ente ("+quotaEnte+" €) deve coincidere con il totale della spesa");
					 valido=false;
				 }
			 }else{
				 //Calcola
				 quotaEnte = this.nuovoIntEsegMast.getSpesa().subtract(totaleCompartecipazioni);
				 this.nuovoIntEsegMast.setValoreGestitaEnte(quotaEnte.setScale(2, RoundingMode.HALF_EVEN));
				 BigDecimal percCalc = nuovoIntEsegMast.getSpesa().doubleValue()>0 ? quotaEnte.multiply(new BigDecimal(100)).divide(this.nuovoIntEsegMast.getSpesa()) : BigDecimal.ZERO;
				 this.nuovoIntEsegMast.setPercGestitaEnte(percCalc.setScale(2, RoundingMode.HALF_EVEN));
			 }
			
			if(!validaDatiCsIPs()) valido = false;
			if(esisteInterventoDuplicato()) valido = false;
			
		}
		return valido;

	}
	
	private boolean validaImporto(BigDecimal val, String label){
		boolean valida = true;
		if(val!=null && val.doubleValue()>=0)
			valida = true;
		else if(val == null)
			valida = false;
		else if(val.doubleValue()<0){
			addWarning("Erogazioni",label + "di testata non corretto:inserire valore numerico intero o decimale (es.12,34), minore o uguale alla spesa");
			valida = false;
		}
		return valida;
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
			valorizzaInterventoEsegMast(intervento);

			// frida
			if (erogazioneHistory.getRows() != null) {
				if (erogazioneHistory.getRows().size() > 0){
					for (InterventoErogazHistoryRowBean row : erogazioneHistory.getRows()) {

						dto.setObj(nuovoIntEsegMast);
						nuovoIntEsegMast = interventoService.salvaInterventoEseguitoMast(dto);
						
						if (row.isNuovaErogazione()) {
							CsIInterventoEseg nuovoIntEseg = valorizzaInterventoEseg(row);
							nuovoIntEseg = nuovoIntEsegMast.addCsIInterventoEseg(nuovoIntEseg);
							valorizzaIntEsegValori(row, nuovoIntEseg);

							if (row.getIntEseg().getStato().getTipo().equalsIgnoreCase(DataModelCostanti.TipoStatoErogazione.EROGATIVO)) {
								inviaAlertResponsabile = true;

								/* == SISO-662 SM DEBUG == */

								// Invio alert al (responsabile) di settore
								// titolare, se quello gestore è diverso

								/*
								 * SISO-775 il recupero per l'invio dell'alert del settore dell'operatore che ha
								 * erogato è inutile in quanto tale valore non viene utilizzato e può comunque
								 * dare un nullpointer dato che l'operatore sulla riga erogazione non è
								 * obbligatorio
								 */

								// CsIInterventoEseg intEseg =row.getIntEseg();
								// CsOOperatoreSettore opSett =
								// intEseg.getCsOOperatoreSettore();
								// CsOSettore settore = opSett.getCsOSettore();

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
			}

			List<CsIInterventoEseg> storicoDaCancellare = new LinkedList<CsIInterventoEseg>();
			for (InterventoErogazHistoryRowBean eliminaInetEseg : erogazioneHistory.getDeleteErogaz()) {
				if (eliminaInetEseg.getIntEseg() != null)
					storicoDaCancellare.add(eliminaInetEseg.getIntEseg());
			}
			
			/*			//Cancello eventuali beneficiari presenti, prima dei reinserirne nuovi-->risolto con orphalRemoval=true sul model
			if(nuovoIntEsegMast.getId()>0){
				dto.setObj(nuovoIntEsegMast.getId());
				interventoService.rimuoviBeneficiariMaster(dto);
			}*/
			
			nuovoIntEsegMast.setBeneficiari(null);
			
			//Valorizzo dati del soggetto beneficiario principale
			//SISO-760
			
			soggettoErogazione.setRiferimento(true);			
			this.valorizzaResidenza();
			this.valorizzaLuogoDiNascita();
			this.valorizzaSesso();
			
			CsIInterventoEsegMastSogg brif = this.valorizzaSoggettiErogazione(soggettoErogazione);
			brif = nuovoIntEsegMast.addBeneficiario(brif);
			
			//Valorizzo altri beneficiari, se presenti
			for(SoggettoErogazioneBean se : this.getAltriSoggetti()){
				se.setRiferimento(false);
				CsIInterventoEsegMastSogg b = this.valorizzaSoggettiErogazione(se);
				b = nuovoIntEsegMast.addBeneficiario(b);
			}

			/* Registrazione su DB */

			if (nuovoIntEsegMast.getCsIInterventoEsegs() != null && !nuovoIntEsegMast.getCsIInterventoEsegs().isEmpty() &&
				nuovoIntEsegMast.getBeneficiari() != null && !nuovoIntEsegMast.getBeneficiari().isEmpty()) {

				String codiceFiscale = soggettoErogazione.getCf();
				String benefLuogoNascita = codiceFiscale.substring(11, 15);
			    //SISO-1138
				if (this.sessoBeneficiario != null)
					csIPs.setBenefSesso(this.sessoBeneficiario.getSesso().equalsIgnoreCase("F") ? 2 : 1);
				else
					logger.error("Sesso beneficiario è nullo " + codiceFiscale);
				//SISO-1138 FINE
				csIPs.setBenefLuogoNascita(benefLuogoNascita);
				
				Integer iso3166 =  getIso3166ByNazionalita(soggettoErogazione.getCittadinanza());
				csIPs.setBenefCittadinanza(iso3166);   
				
				Integer iso3166_2 =  getIso3166ByNazionalita(soggettoErogazione.getCittadinanza2());
				csIPs.setBenefCittadinanza2(iso3166_2); 
				
				//SISO-962 inizio
				// L'indirizzo deve essere pescato dalla MastSogg
				String benefNazione= null;
				if(comuneNazioneResidenzaMan.isComune()){
					String codComIstat = comuneNazioneResidenzaMan.getComuneMan().getComune().getCodIstatComune();

					boolean resInItalia = codComIstat != null;
					if (resInItalia) {
						AmTabComuni comune = luoghiService.getComuneItaByIstat(codComIstat);
						csIPs.setBenefComune(codComIstat);
						AmTabRegioni regione = comune.getRegione();
						String codAE = null;
						if(regione!=null && regione.getCodAgenziaEntrate()!=null)
							codAE = regione.getCodAgenziaEntrate();
						else{
							if("BZ".equalsIgnoreCase(comune.getSiglaProv())) codAE = "03";
							if("TN".equalsIgnoreCase(comune.getSiglaProv())) codAE = "18";
						}
						csIPs.setBenefRegione(codAE);
						benefNazione = DataModelCostanti.AmTabNazioni.CODICE_ISO3166_ITALIA;
						//csIPs.setBenefNazione(benefNazione);
					} 		
				}else if(comuneNazioneResidenzaMan.isNazione())
				
					if(comuneNazioneResidenzaMan.getNazioneMan().getNazione() == null && soggettoErogazione.isNazioneResidenzaNonDefinita()) {
						benefNazione = null;
					} else {
						benefNazione = comuneNazioneResidenzaMan.getNazioneMan().getNazione().getIso3166();
					}
						

				benefNazione = benefNazione!=null ? benefNazione : DataModelCostanti.AmTabNazioni.CODICE_ISO3166_NON_DISPONIBILE;
				csIPs.setBenefNazione(benefNazione);
				//SISO-962 Fine
				
				csIPs.setDenomPrestazione(findDenominazionePrestazione(csIPs.getCodPrestazione()));
	
				if(frequenzaAccessoAlServizio.equals(TIPO_FREQUENZA_SERVIZIO.UNATANTUM.getCodice()))	
					csIPs.setCarattere(DataModelCostanti.CSIPs.CARATTERE_PRESTAZIONE_DI_TIPO_OCCASIONALE);
				else //frequenza PERIODICO E IRREGOLARE
					csIPs.setCarattere(DataModelCostanti.CSIPs.CARATTERE_PRESTAZIONE_DI_TIPO_PERIODICO); 
				
				protDsuMan.valorizza(csIPs);
				
				nuovoIntEsegMast.setCsIPs(csIPs);
				csIPs.setCsIInterventoEsegMast(nuovoIntEsegMast);
				// FINE MOD-RL

				dto.setObj(nuovoIntEsegMast);
				nuovoIntEsegMast = interventoService.salvaInterventoEseguitoMast(dto);
			}

			if (!storicoDaCancellare.isEmpty()) {
				dto.setObj(storicoDaCancellare);
				interventoService.eliminaInterventoEsegStorico(dto);
			}

			if (inviaAlertResponsabile || !erogAlertTitolare.isEmpty()) {

				BaseDTO bdto = new BaseDTO();
				fillEnte(bdto);
				bdto.setObj(nuovoIntEsegMast);
				bdto.setObj2(inviaAlertResponsabile);
				bdto.setObj3(erogAlertTitolare); //Lista delle erogazioni da notificare al titolare
				interventoService.gestisciAlertErogazioni(bdto);
			}

		} catch (Exception e) {
			logger.error("Errore salvataggio erogazione", e);
			this.addError("Errore salvataggio erogazione intervento.", "");
			bOk = false;
		}
		return bOk;
	}

	// INIZIO SISO-556
	public boolean validaErogazioni(CsIQuota quota) {
		boolean res = true; 
		
		//controllo che: se rendiconto periodico, le erogazioni di tipo E devono essere comprese tra data e dataA
		// se rendiconto è ad evento, per le erogazioni di tipo E, la dataA non deve essere valorizzata
		List<InterventoErogazHistoryRowBean> historyRowBeanList = this.getStoricoOperazioni();
		
		if (quota.getCsIQuotaRipartiz().getFlagRendiconto().equals(TIPO_RENDICONTO.PERIODICO.getCodice()) ) {

			for (InterventoErogazHistoryRowBean interventoErogazHistoryRowBean : historyRowBeanList) {
				if (interventoErogazHistoryRowBean.getStato().getTipo().equals(DataModelCostanti.TipoStatoErogazione.EROGATIVO) ) {
					if (interventoErogazHistoryRowBean.getDataErogazioneA()==null) {
						res = false;
						addWarning("Erogazioni", "In caso di rendicontazione periodica, non possono esserci erogazioni con 'data A' non valorizzata");
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
						addWarning("Erogazioni","In caso di rendicontazione ad evento, non possono esserci erogazioni con 'data A' valorizzata");
						res = false;
						break;
					}
				}
			}
		}
		// SISO-986 intervento IRREGOLARE: impedimento di inserimento chiusura se esiste una sola riga di Erogazione di tipo E
		if (quota.getCsIQuotaRipartiz().isFlagIrregolare()) {
			if (this.isUnicaErogazioneChiusura()) {
				for (InterventoErogazHistoryRowBean interventoErogazHistoryRowBean : historyRowBeanList) {
					if (interventoErogazHistoryRowBean.getStato().getFlagChiudi().equals(DataModelCostanti.CsCfgIntEsegStato.FLAG_CHIUDI_CHIUSO)) {
						addWarning("Erogazioni", "Stato " + interventoErogazHistoryRowBean.getStato().getNome()
										+ " non consentito per prestazione " + TIPO_FREQUENZA_SERVIZIO.IRREGOLARE.getDescrizione()
										+ ", modificare eventualmente la prestazione in "
										+ TIPO_FREQUENZA_SERVIZIO.UNATANTUM.getDescrizione());
						res = false;
						break;
					}
				}
			}
		}
		// FINE SISO-986
		
		//SISO-962 INIZIO //SISO-1021 devo verificare se comuneNazioneResidenzaMan e il check Nazione non definita è stato selezionato.
		if((this.comuneNazioneResidenzaMan == null )  
				|| (this.comuneNazioneResidenzaMan.getComuneMan().getComune() == null && this.comuneNazioneResidenzaMan.getNazioneMan().getNazione() == null ) 
				&&   !soggettoErogazione.isNazioneResidenzaNonDefinita())
		    {
			addWarning("Erogazioni", "E' obbligatorio inserire il comune o la nazione di residenza del soggetto.");
			res = false;
		}
		if(this.comuneNazioneResidenzaMan.isComune()){
			if(this.soggettoErogazione.getViaResidenza() == null || this.soggettoErogazione.getViaResidenza().isEmpty()){
				this.addErrorCampiObbligatori("Erogazioni", "Via di residenza del soggetto beneficiario");
				res = false;
			}
		 }
		//SISO-962 Fine
		//SISO-1032
		TempCodFiscManager tempMan = (TempCodFiscManager)getReferencedBean("tempCodFiscManager");
		boolean cfTemporeneo=false;
		try {
			cfTemporeneo = tempMan.isTemporaneo(this.soggettoErogazione.getCf());
		} catch (Exception e) {
			logger.error("Errore verifica codice fiscale temporaneo", e);
		}
		if(cfTemporeneo && this.isErogazioniPresenti()){
			this.addError("Erogazioni", "Verificare il codice fiscale: non è possibile erogare ad un soggetto registrato con 'codice fiscale temporaneo'");
			res = false;
		}
		//SISO-1032 Fine
		return res;
	}
	
	// FINE SISO-556

	/* Verifica se nello storico erogazioni ci siano record con TIPO STATO = E */
	public boolean isErogazioniPresenti() {
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

	public boolean isUnicaErogazioneChiusura() {
		int countErogazioni = 0;
		boolean chiusura=false;
		List<InterventoErogazHistoryRowBean> erRows = erogazioneHistory.getRows();
		for(InterventoErogazHistoryRowBean erRow : erRows){
			if (DataModelCostanti.TipoStatoErogazione.EROGATIVO.equals(erRow.getStato().getTipo()))
				countErogazioni++;
			if (erRow.getStato().getFlagChiudi())
				chiusura = true;
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

	// INIZIO MOD-RL

	private String findDenominazionePrestazione(String codPrestazione) {

		BaseDTO denomPrestazioneDto = new BaseDTO();
		fillEnte(denomPrestazioneDto);
		denomPrestazioneDto.setObj(codPrestazione); 
		ArTbPrestazioniInps arTbPrestazioniInps = this.confService.findArTbPrestazioniInpsByCodice(denomPrestazioneDto);
		return   arTbPrestazioniInps!=null ? arTbPrestazioniInps.getDenominazione() : null;
	}

	private CsIInterventoEsegMastSogg valorizzaSoggettiErogazione(SoggettoErogazioneBean se){
		CsIInterventoEsegMastSogg s = new CsIInterventoEsegMastSogg();
		CsASoggettoLAZY casos = se.getCsASoggetto();
		if (casos == null)
			casos = getSchedaCSByCF(se.getCf());
		
		if(casos!=null){
			se.setCsASoggetto(casos);
			s.setCaso(se.getCsASoggetto().getCsACaso());
		}

		s.setRiferimento(se.isRiferimento());
		
		s.setCognome(se.getCognome());
		s.setNome(se.getNome());
		s.setCf(se.getCf().toUpperCase());
		s.setCittadinanza(se.getCittadinanza());
		
		s.setAnnoNascita(se.getAnnoNascita());
		s.setComuneNascita(se.getComuneNascita());
		s.setNazioneNascita(se.getNazioneNascita());
		
		s.setNazioneResidenza(se.getNazioneResidenza());
		s.setNazioneResidenzaNonDefinita(se.isNazioneResidenzaNonDefinita());//SISO-1021
		s.setComuneResidenza(se.getComuneResidenza());
		s.setViaResidenza(se.getViaResidenza());
		s.setSecondaCittadinanza(se.getCittadinanza2());
		s.setDatiValidi(se.getDatiValidi()!=null ? se.getDatiValidi().booleanValue() : false);
	
		CsOOperatoreSettore opSettoreMast = getCurrentOpSettore();
		s.setUserIns(opSettoreMast.getCsOOperatore().getUsername());
		s.setDtIns(new Date());
		//SISO-1138
		s.setSesso(se.getSesso());
		return s;
	}

	//TODO frida completare 23 campi: gli devo passare il master invece dell'intervento
	private CsIInterventoEseg valorizzaInterventoEseg(InterventoErogazHistoryRowBean row) {
		CsIInterventoEseg nuovoIntEseg = new CsIInterventoEseg();
		CsOOperatoreSettore opSettoreMast = getCurrentOpSettore();

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
		nuovoIntEseg.setDataEvento(row.getIntEseg().getDataEvento());
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
		} else
			nuovoIntEseg.setCsIValQuota(null);

		return nuovoIntEseg;
	}

	private void valorizzaInterventoEsegMast(CsIIntervento intervento) {
		CsOOperatoreSettore opSettore = getCurrentOpSettore();
		CsOSettore settoreErogante = super.findSettoreById(this.selSettoreEroganteId);
		nuovoIntEsegMast.setSettoreErogante(settoreErogante);
		
		if(nuovoIntEsegMast.getId()>0){
			nuovoIntEsegMast.setUsrMod(opSettore.getCsOOperatore().getUsername());
			nuovoIntEsegMast.setDtMod(new Date());
		} else {
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
		nuovoIntEsegMast.setInterventoProgrammatoId(intervento!=null ? intervento.getId() : null);

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
		nuovoIntEseg.setDataEvento(row.getDataEvento());
		nuovoIntEseg.setCsIInterventoEsegValores(listaIntEsegValore);
	}

	private void buildInterventoErogazzAttList(Long tipoInterventoId) {
		BaseDTO bdto = new BaseDTO();
		fillEnte(bdto);
		bdto.setObj(tipoInterventoId);
		//carica configurazioine by tipo intervento
		HashMap<Long, ErogStatoCfgDTO> mappaEseg = confService.findConfigIntEsegByTipoIntervento(bdto);
		
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
		CsOOperatoreSettore opSett = null;
		if (operatoreErogId != null) {
			OperatoreDTO o = new OperatoreDTO();
			fillEnte(o);
			o.setIdOperatoreSettore(operatoreErogId);
			try {
				opSett = confEnteService.findOperatoreSettoreById(o);
			} catch (Exception e) {
				logger.error("Impossibile recuperare getOperatoreErogSelezionato "+e.getMessage(), e);
			}
		}
		return opSett;
	}

	private void loadListaOperatoriEroganti(Long settoreId) {
		try {
			listaOperAnagrafica = new ArrayList<SelectItem>();

			if (settoreId != null) {
				// dal settore la lista degli operatori
				OperatoreDTO oDto = new OperatoreDTO();
				fillEnte(oDto);
				oDto.setIdSettore(settoreId);
				List<KeyValueDTO> lstOpSettAnagrafica = confEnteService.findListaOperatoreSettoreBySettore(oDto);
				listaOperAnagrafica.addAll(convertiLista(lstOpSettAnagrafica));
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
		listaStatiErogazione = confService.getListaIntEsegStatoByTipiStato(bdto);
	}

	private void setPrimiDatiInNuovoIntEsegTemp() {
		nuovocsIntEseg.setDataEsecuzione(dataErog);
		nuovocsIntEseg.setDataEsecuzioneA(dataErogA);
		nuovocsIntEseg.setDataEvento(dataEvento);
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
	
	public boolean isStatoErogativo(){
		boolean val = false;
		CsCfgIntEsegStato stato = this.getStatoSelezionato();
		if (stato != null) {
			val = TipoStatoErogazione.EROGATIVO.equals(stato.getTipo());
		}
		return val;
	}

	protected boolean validaAttributi(boolean rendicontoPeriodico, String flagPeriodoRipartiz, boolean visualizzaIntervalloDateErogazione) {   //SISO-556
		boolean bOk = true;
		
		this.valorizzaResidenza();
		this.valorizzaSesso();
		bOk = validaTestata();

		if (this.dataErog == null) {
			this.addErrorCampiObbligatori("Erogazioni", "Data erogazione");
			bOk = false;
		} else {
			// INIZIO SISO-556
			if (rendicontoPeriodico) {
				if (visualizzaIntervalloDateErogazione) {
					if(this.dataErogA==null){
						this.addErrorCampiObbligatori("Erogazioni", "Data di fine erogazione");
						bOk = false;	
					}else if(this.dataErogA.before(this.dataErog)){
						this.addWarning("Erogazioni", "La data di fine erogazione non può essere inferiore a quella di inizio");
						bOk = false;	
					}else{ //SISO-930 CONTROLLI SUI PERIODI DISABILITATI
						
						boolean disabilitaControlliPeriodi=true;
						if(!disabilitaControlliPeriodi){
							Calendar dataA = Calendar.getInstance();
							dataA.setTime(dataErogA);
	
							Calendar dataDa = Calendar.getInstance();
							dataDa.setTime(dataErog);
							
							if (TIPO_FREQUENZA_SERVIZIO_CADENZA.MENSILE.getCodice().equals(flagPeriodoRipartiz)) {
								//sono accettati i periodi uguali o superiori a 1 mese, dataA e dataDa comprese
								//quindi ad esempio dal 1 al 31 dicembre è una selezione valida
								dataDa.add(Calendar.MONTH, 1);
								dataDa.add(Calendar.DAY_OF_MONTH, -1);
								if(dataA.before(dataDa)){
									this.addWarning("Erogazioni", "L'intervallo tra la data di inizio e fine deve essere maggiore di un mese");
									bOk = false;	
								}
							} else if (TIPO_FREQUENZA_SERVIZIO_CADENZA.SETTIMANALE.getCodice().equals(flagPeriodoRipartiz)) {
								//sono accettati i periodi uguali o superiori a 7 giorni, dataA e dataDa comprese
								//quindi ad esempio dal 1 al 7 dicembre è una selezione valida
								
								dataDa.add(Calendar.WEEK_OF_YEAR, 1);
								dataDa.add(Calendar.DAY_OF_MONTH, -1);
								
								if(dataA.before(dataDa)){
									this.addWarning("Erogazioni", "L'intervallo tra la data di inizio e fine deve essere maggiore di una settimana");
									bOk = false;	
								}
							}
						}
					}
					
					if(this.dataEvento==null && isDataEventoObbligatoria()) {
						this.addErrorCampiObbligatori("Erogazioni", this.getDataEventoLabel());
						bOk = false;
					}
				}

			}
			// FINE SISO-556

		}

		// INIZIO SISO-556
		// if ( DataModelCostanti.TipoStatoErogazione.EROGATIVO.equals(
		// getStatoSelezionato().getTipo() ) ) {
		//
		// if (visualizzaIntervalloDateErogazione) {
		// for (InterventoErogazHistoryRowBean rowBean :
		// erogazioneHistory.getRows()) {
		// if (!controllaGiorni(dataErog, dataErogA,
		// rowBean.getDataErogazione(), rowBean.getDataErogazioneA()) ) {
		// addError("Non possono esserci 2 righe di erogazione nello stesso giorno",
		// "");
		// bOk = false;
		// }
		// }
		//
		// } else {
		// for (InterventoErogazHistoryRowBean rowBean :
		// erogazioneHistory.getRows()) {
		// if ( dataErog.equals(rowBean.getDataErogazione()) ) {
		// addError("Non possono esserci 2 righe di erogazione nello stesso giorno",
		// "");
		// bOk = false;
		// }
		// }
		// }
		//
		//
		// }
		// FINE SISO-556
//SISO-1037
//		if (this.nuovoIntEsegMast.getDescInterventoEseg() == null
//				|| this.nuovoIntEsegMast.getDescInterventoEseg().isEmpty()) {
//			this.addErrorCampiObbligatori("Erogazioni", "Descrizione");
//			bOk = false;
//		}
		//FINE SISO-1037
		CsCfgIntEsegStato statoSelezionato = getStatoSelezionato();
	    if(statoSelezionato!=null){
			for( InterventoErogazAttrBean attr : this.getErogaziones(statoSelezionato.getId())){
				if( !attr.isValid() ){
					addWarning("Erogazioni" , attr.getRequiredMessage());
					bOk = false;
				}
			}
		}
		return bOk;
	}

	/**
	 *  ritorna true se i periodi compresi tra  (dataErog, dataErogA) e (dataErog2, dataErogA2) non si intersecano
	 */
	public static boolean controllaGiorni(Date dataErog, Date dataErogA,
			Date dataErog2, Date dataErogA2) {
		if (dataErogA.before(dataErog2) || dataErogA2.before(dataErog)) {
			return true;
		} else {
			return false;
		}
	}

	public void aggiungiAttributi(boolean rendicontoPeriodico, String flagPeriodoRipartiz, boolean  visualizzaIntervalloDateErogazione, String unitaDiMisura) {   //SISO-556 SISO-958
		
		if( !validaAttributi(rendicontoPeriodico, flagPeriodoRipartiz, visualizzaIntervalloDateErogazione) )
			return;		
			
		//TODO
		/* "nuovocsIntEseg" va in "erogazioneHistory.row", "row" verrà ricaricata prima del salvataggio da "valorizzaInterventoEseg()" */
		this.setPrimiDatiInNuovoIntEsegTemp();	
		CsCfgIntEsegStato stato = getStatoSelezionato();
		CsOSettore settoreErogante = super.findSettoreById(selSettoreEroganteId);
		this.erogazioneHistory.addNew(settoreErogante, nuovoIntEsegMast.getDescInterventoEseg(), stato , nuovocsIntEseg, mappaErogStatoAttr, unitaDiMisura );

		//Clear
		for (InterventoErogazAttrBean attr : this.mappaErogStatoAttr.get(stato.getId())) attr.reset2default();
		this.resetOrganizAndNuovoIntEseg();	
		
		statoSelezionatoId=null;
		if(isErogazioniPresenti())
			testataDisabled=true;
		onStatoChanged();

		
		RequestContext.getCurrentInstance().addCallbackParam("added", true);
		resetWizardStep();
		
	}
	//SISO-1201
	private void resetWizardStep() {
		final String STEP0 = "detta_tbSpese";		
		String wizard_id = ComponentUtils.findComponentClientId("wizardSpese");
		if(wizard_id!=null){
			Wizard wizard = (Wizard) FacesContext.getCurrentInstance().getViewRoot().findComponent(wizard_id);
			if(wizard!=null && !STEP0.equals(wizard.getStep())){
				logger.debug("Spese Prestazioni - resetting Wizard server side from step: "+wizard.getStep() + " "+ wizard_id);
			    wizard.setStep(STEP0);
			    RequestContext.getCurrentInstance().update("panelAttributi");			
			}
		}
	}

	public void onChangeTipoBeneficiario(AjaxBehaviorEvent event) {
		
		if(this.isUnicoBeneficiario()){
			altriSoggetti = new ArrayList<SoggettoErogazioneBean>();
			soggettoErogazione.setRiferimento(true);
			nuovoIntEsegMast.setTotBeneficiari(Integer.valueOf(1));
		}else if (nuovoIntEsegMast.getTotBeneficiari()==null){
			nuovoIntEsegMast.setTotBeneficiari(Integer.valueOf(1));
		}	
		
		//SISO-809
		if(this.nuovoIntEsegMast.getTipoBeneficiario().equals(ListaBeneficiari.GRUPPO)){
			this.addMessage(FacesMessage.SEVERITY_WARN, "Questa erogazione non verrà esportata in SIUSS"); 
			this.esportazioneDiGruppo=true;
		}else{
			this.esportazioneDiGruppo=false;
		}

		initRisorsaFamiliareBean();

	}

	public void onResetSpeseComp(AjaxBehaviorEvent event) {
		setBoolDisabledSpese();
	}

	//INIZIO SISO-524 
	
	private boolean verificaErogazioniEsportate(List<CsIInterventoEseg> interEsegHistory2) {
		boolean esportate = false;
		List<Long> list = new ArrayList<Long>();
		for (CsIInterventoEseg csIInterventoEseg : interEsegHistory2){
			if(csIInterventoEseg.getId()!=null)
				list.add(csIInterventoEseg.getId());
		}
		esportate = erogazioniEsportate(list);
		return esportate;
	}

	// ** mod. SISO-886 **//
	public boolean erogazioneEsportata(Long intEsegMastId) {

		BaseDTO dto = new BaseDTO();
		fillEnte(dto);		
		dto.setObj(intEsegMastId);
		return psExportService.findCsIPsExportByCsIInterventoMastIdExported(dto).size() > 0;

	}

	private boolean erogazioniEsportate(List<Long> esegIds) {
		boolean esportata = false;
		if (esegIds != null && !esegIds.isEmpty()) {
			BaseDTO dto = new BaseDTO();
	    	fillEnte(dto);
	    	dto.setObj(esegIds);  
			
	    	esportata = psExportService.verificaErogazioniEsportateByEsegIds(dto);
		}
		return esportata; 	
	}

	public boolean erogazioneEsportata(InterventoErogazHistoryRowBean row) {
		List<Long> list = new ArrayList<Long>();
		if (row.getIntEseg().getId() != null) {
			list.add(row.getIntEseg().getId());
			return erogazioniEsportate(list);
		}
		return false;
	}
	
	// FINE SISO-524

	public void rimuoviRigaStorico(InterventoErogazHistoryRowBean row) {

		//MODIFICA SISO-524 se l'erogazione è stata già esportata non ne consento la rimozione 
    	if (erogazioneEsportata(row)) { 
    		addMessage(FacesMessage.SEVERITY_ERROR,"Non è possibile eliminare la riga in questione, in quanto esportata nel SIUSS");
		} else {
			this.erogazioneHistory.rimuoviRiga(row);
			
			this.onChangeCalcolaSpeseTotali();
			
			if(!isErogazioniPresenti()){
				boolean erogatoreProprietario = isPermessoErogativo() && this.isOperatoreProprietario();
				if(erogatoreProprietario || isPermessoAutorizzativo())
					testataDisabled=false;
			}
		}

	}

	public void onChangeValoriSpesaDettaglio() {

		// INIZIO SISO-556
		boolean valid = true;
		if (nuovocsIntEseg.getValoreGestitaEnte()!= null ) {
			if (nuovocsIntEseg.getSpesa() == null) { 
				addMessage(FacesMessage.SEVERITY_ERROR,"Valorizzare il campo Spesa");
			} else if ( nuovocsIntEseg.getValoreGestitaEnte().compareTo(nuovocsIntEseg.getSpesa() ) > 0  ) { 
				addMessage(FacesMessage.SEVERITY_ERROR,"Valore quota a carico dell'ente in dettaglio non corretto:inserire valore numerico intero o decimale (es.12,34), minore o uguale alla Spesa");
			}
		}

		if (valid) {
			if (this.calcolaSpesaDaDettaglio) {
				Double spesaCalc = calcolaMasterSpesaTotale();
				Double percCalc = calcolaMasterPercSpesa();
				Double valSpesa = calcolaMasterValSpesa();

				nuovoIntEsegMast.setSpesa(new BigDecimal(spesaCalc));
				nuovoIntEsegMast.setPercGestitaEnte(new BigDecimal(percCalc).setScale(2, RoundingMode.HALF_EVEN));
				nuovoIntEsegMast.setValoreGestitaEnte(new BigDecimal(valSpesa).setScale(2, RoundingMode.HALF_EVEN));
				
				
				//SISO-1476
				Double utente = this.calcolaMasterCompUtenteTotale();
				Double ssn = this.calcolaMasterCompSSNTotale();
				Double altro = this.calcolaMasterCompAltroTotale();

				nuovoIntEsegMast.setCompartUtenti(new BigDecimal(utente));
				nuovoIntEsegMast.setCompartSsn(new BigDecimal(ssn));
				nuovoIntEsegMast.setCompartAltre(new BigDecimal(altro));
			}
		}

		// FINE SISO-556

	}

	public void aggiornaCompartecipazioneDettaglio() {
		//SISO-1476
		if (this.calcolaSpesaDaDettaglio) {
			// SISO-1433
			Double utente = this.calcolaMasterCompUtenteTotale();
			Double ssn = this.calcolaMasterCompSSNTotale();
			Double altro = this.calcolaMasterCompAltroTotale();

			nuovoIntEsegMast.setCompartUtenti(new BigDecimal(utente));
			nuovoIntEsegMast.setCompartSsn(new BigDecimal(ssn));
			nuovoIntEsegMast.setCompartAltre(new BigDecimal(altro));
		}
	}
	
	public void onChangeCalcolaSpeseTotali() {

		if (!this.calcolaSpesaDaDettaglio) {
			nuovocsIntEseg.setCsIValQuota(new CsIValQuota());
			nuovocsIntEseg.setSpesa(null);
			nuovocsIntEseg.setPercGestitaEnte(null);
			nuovocsIntEseg.setValoreGestitaEnte(null);
			
			//!SISO-1476 
			nuovocsIntEseg.setCompartUtenti(null);
			nuovocsIntEseg.setCompartSsn(null);
			nuovocsIntEseg.setCompartAltre(null);
		}
//		} else
//			this.calcolaCompartDaDettaglio = true;

		this.onChangeValoriSpesaDettaglio();
	}

	public Double calcolaMasterCompUtenteTotale() {
		double valore = 0;
		
		for (InterventoErogazHistoryRowBean i : erogazioneHistory.getRows()) {
			if(i.getIntEseg().getCompartUtenti()!=null && i.getIntEseg().getCompartUtenti().compareTo(new BigDecimal(0))!=0)
					valore += i.getIntEseg().getCompartUtenti().doubleValue();
			
		}
		
		if (nuovocsIntEseg.getCompartUtenti() != null)
			valore += nuovocsIntEseg.getCompartUtenti().doubleValue();

		return valore;
	}

	public Double calcolaMasterCompSSNTotale() {
		double valore = 0;

		for (InterventoErogazHistoryRowBean i : erogazioneHistory.getRows()) {
			if(i.getIntEseg().getCompartSsn()!=null && i.getIntEseg().getCompartSsn().compareTo(new BigDecimal(0))!=0)
					valore += i.getIntEseg().getCompartSsn().doubleValue();
		}

		if (nuovocsIntEseg.getCompartSsn() != null)
			valore += nuovocsIntEseg.getCompartSsn().doubleValue();

		return valore;
	}

	public Double calcolaMasterCompAltroTotale() {
		double valore = 0;

		for (InterventoErogazHistoryRowBean i : erogazioneHistory.getRows()) {
			if(i.getIntEseg().getCompartAltre()!=null && i.getIntEseg().getCompartAltre().compareTo(new BigDecimal(0))!=0)
					valore += i.getIntEseg().getCompartAltre().doubleValue();
		}

		if (nuovocsIntEseg.getCompartAltre() != null)
			valore += nuovocsIntEseg.getCompartAltre().doubleValue();

		return valore;
	}

	public Double calcolaMasterSpesaTotale() {
		double valore = 0;

		for (InterventoErogazHistoryRowBean i : erogazioneHistory.getRows()) {
			if(i.getIntEseg().getSpesa()!=null && i.getIntEseg().getSpesa().compareTo(new BigDecimal(0))!=0)
					valore += i.getIntEseg().getSpesa().doubleValue();
		}

		if (nuovocsIntEseg.getSpesa() != null)
			valore += nuovocsIntEseg.getSpesa().doubleValue();

		return valore;
	}

	public Double calcolaMasterValSpesa() {

		double valore = 0;

		for (InterventoErogazHistoryRowBean i : erogazioneHistory.getRows()) {
			boolean valoreDef = i.getIntEseg().getValoreGestitaEnte()!=null && i.getIntEseg().getValoreGestitaEnte().doubleValue()>0;
			boolean percDef = i.getIntEseg().getPercGestitaEnte()!=null && i.getIntEseg().getPercGestitaEnte().doubleValue()>0;
		  if(valoreDef) // && !percDef)
			valore+=i.getIntEseg().getValoreGestitaEnte().doubleValue();
		  else if(percDef){
			  //Resta per il pregresso quando i due valori erano esclusivi
			  if(i.getIntEseg().getSpesa()!=null && i.getIntEseg().getSpesa().compareTo(new BigDecimal(0))!=0){
				  Double percValore = i.getIntEseg().getSpesa().doubleValue()*i.getIntEseg().getPercGestitaEnte().doubleValue()/100;
				  valore+= percValore;
			  }
			 }
		  }
			
		boolean valoreDef = nuovocsIntEseg.getValoreGestitaEnte()!=null && nuovocsIntEseg.getValoreGestitaEnte().doubleValue()>0;
		//boolean percDef = nuovocsIntEseg.getPercGestitaEnte()!=null && nuovocsIntEseg.getPercGestitaEnte().doubleValue()>0;
		  if(valoreDef) // && !percDef)
			valore+=nuovocsIntEseg.getValoreGestitaEnte().doubleValue();
		 /* else if(!valoreDef && percDef){
			  if(nuovocsIntEseg.getSpesa()!=null && nuovocsIntEseg.getSpesa().compareTo(new BigDecimal(0))!=0){
				  Double percValore = nuovocsIntEseg.getSpesa().doubleValue()*nuovocsIntEseg.getPercGestitaEnte().doubleValue()/100;
				  valore+= percValore;
			  }
		}
*/
		return valore;
	}

	public Double calcolaDettPercSpesa(double valSpesaEnte, double spesaTotale) {
		double valore = 0;
		
		if (spesaTotale > 0) {
			valore =  valSpesaEnte * 100 / spesaTotale;

		    valore = Math.round(valore * 100.0) / 100.0 ;
		}
		return valore;
	}
	
	public Double calcolaMasterPercSpesa() {
		double valore = 0;
		double valSpesaEnte = calcolaMasterValSpesa();
		double spesaTotale = this.calcolaMasterSpesaTotale();

		if (spesaTotale > 0) {
			valore =  valSpesaEnte * 100 / spesaTotale;

		    valore = Math.round(valore * 100.0) / 100.0 ;
		}
		return valore;
	}

	public void onSettEroganteTestaChange(AjaxBehaviorEvent event) {
		loadListaOperatoriEroganti(this.selSettoreEroganteId);
		
		//#ROMACAPITALE			
		setVisualizzaPannelloDettaglio(false);
		settoreSelezionato = super.findSettoreById(this.selSettoreEroganteId);
		
		if(this.selSettoreEroganteId != null && settoreSelezionato != null && getListaIdSettoriByIntervervento() != null && getListaIdSettoriByIntervervento().size() > 0)
		{
			setVisualizzaPannelloDettaglio(true);
		} else {
			setVisualizzaPannelloDettaglio(false);
		}
		//#ROMACAPITALE fine
	}

	//frida
	public void setTestaPercValue(AjaxBehaviorEvent event){
		
		if(testaPercOrValue.equals(DataModelCostanti.ErogazioneInterventoConst.TESTATA_SPESA_PERCENTUALE)){			
			isSpesaValTesta=false;		
			if(!calcolaSpesaDaDettaglio){ //se la spesa è già stata settata nel dettaglio vuol dire che nella testata c'è la somma dei dettagli e non deve essere cancellata
				nuovoIntEsegMast.setValoreGestitaEnte(null);
				isSpesaPercTesta = true;
			}
		} else {
			isSpesaPercTesta = false;
			if (!calcolaSpesaDaDettaglio) {
				nuovoIntEsegMast.setPercGestitaEnte(null);
				isSpesaValTesta = true;
			}
		}
	}

	//frida : o si scelgono organizzazione ed operatore fra una lista oppure si inseriscono un ente e un operatore a mano
	public void setOperErogInputs(AjaxBehaviorEvent event){ 
		if(operatoreScegliOrInserisci.equals(DataModelCostanti.ErogazioneInterventoConst.INSERISCI_OPERATORE)){
			operatoreErogId=Long.valueOf(0);
			nuovocsIntEseg.setCsOOperatoreSettore(null);
		} else {
			CsOOperatoreSettore op = getCurrentOpSettore();
			if (op != null) {
				operatoreErogId = op.getId();
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
			
			if(this.erogazioneHistory.isVisualizzaStorico() && !this.erogazioneHistory.getRows().isEmpty()){
				if(statoSelezionato.getOrdineDescFase() < this.erogazioneHistory.getRows().get(0).getStato().getOrdineDescFase())
					addMessage(FacesMessage.SEVERITY_ERROR,"Non è possibile selezionare uno stato con fase precedente all'ultimo esistente");
			}
			//se non sono in Stato Erogativo è necessario ripulire le variabili legate alla spesa
			if (!visualizzaAttributi) {
			  onChangeStatoResetSpesa();	
			  
			}
		} else
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

	public boolean getEnabledAggiungi() {
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

	//SISO-806
	public Long getOreErogazione() {
		return oreErogazione;
	}

	public void setOreErogazione(Long oreErogazione) {
		this.oreErogazione = oreErogazione;
	}

	public Long getMinutiErogazione() {
		return minutiErogazione;
	}

	public void setMinutiErogazione(Long minutiErogazione) {
		this.minutiErogazione = minutiErogazione;
	}
    //FINE SISO-806

	//SISO-556 aggiunto il fltro su frequenzaAccessoAlServizio
	public List<SelectItem> getLstSelItemStatiErog(String frequenzaAccessoAlServizio) {
	if(listaStatiErogazione!=null){
		lstSelItemStatiErog = new ArrayList<SelectItem>();
		SelectItemGroup g1 = null; //new SelectItemGroup("Fase di pre-avvio");
		SelectItemGroup g2 = null; //new SelectItemGroup("Fase iniziale");
		SelectItemGroup g3 = null; //new SelectItemGroup("Fase a regime");
		SelectItemGroup g4 = null; //new SelectItemGroup("Fase finale");
		List<SelectItem> lst1 = new ArrayList<SelectItem>();
		List<SelectItem> lst2 = new ArrayList<SelectItem>();
		List<SelectItem> lst3 = new ArrayList<SelectItem>();
		List<SelectItem> lst4 = new ArrayList<SelectItem>();
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
			//	SelectItem si = new SelectItem(stato.getId(),stato.getNome());
			//	lstSelItemStatiErog.add(si);
				switch(stato.getOrdineDescFase()){
				case 100: 
					lst1.add(new SelectItem(stato.getId(),stato.getNome()));
					if(g1==null) g1 = new SelectItemGroup(stato.getDescFase()); 
					break;
				case 200:
					lst2.add(new SelectItem(stato.getId(),stato.getNome()));
					if(g2==null) g2 = new SelectItemGroup(stato.getDescFase()); 
					break;
				case 300:
					lst3.add(new SelectItem(stato.getId(),stato.getNome()));
					if(g3==null) g3 = new SelectItemGroup(stato.getDescFase()); 
					break;
				case 400:
					lst4.add(new SelectItem(stato.getId(),stato.getNome()));
					if(g4==null) g4 = new SelectItemGroup(stato.getDescFase()); 
					break;
				}
			}
			//FINE SISO-556
			
		}
		if(g1!=null){
			g1.setSelectItems(lst1.toArray(new SelectItem[lst1.size()]));
			lstSelItemStatiErog.add(g1);
		} 
		if(g2!=null){
			g2.setSelectItems(lst2.toArray(new SelectItem[lst2.size()]));
			lstSelItemStatiErog.add(g2);
		} 
		if(g3!=null){
			g3.setSelectItems(lst3.toArray(new SelectItem[lst3.size()]));
			lstSelItemStatiErog.add(g3);
		} 
		if(g4!=null){
			g4.setSelectItems(lst4.toArray(new SelectItem[lst4.size()]));
			lstSelItemStatiErog.add(g4);
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

	public List<SelectItem> getListaOperAnagrafica() {
		return listaOperAnagrafica;
	}

	public void setListaOperAnagrafica(List<SelectItem> listaOperAnagrafica) {
		this.listaOperAnagrafica = listaOperAnagrafica;
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

	public String getTestaPercOrValue() {
		return testaPercOrValue;
	}

	public void setTestaPercOrValue(String testaPercOrValue) {
		this.testaPercOrValue = testaPercOrValue;
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
		listaOpzioniBen = new ArrayList<String>();

		listaOpzioniBen.add(DataModelCostanti.ListaBeneficiari.INDIVIDUALE);
		listaOpzioniBen.add(DataModelCostanti.ListaBeneficiari.NUCLEO);

		if (!this.dentroFascicolo)
			listaOpzioniBen.add(DataModelCostanti.ListaBeneficiari.GRUPPO);

		return listaOpzioniBen;
	}

	public Boolean getErogazionePossibile() {
		return erogazionePossibile || !this.dentroFascicolo;
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

	public void setCalcolaSpesaDaDettaglio(Boolean calcolaSpesaDaDettaglio) {
		this.calcolaSpesaDaDettaglio = calcolaSpesaDaDettaglio;
	}
	
	//SISO_1162
	public Boolean getIsCodiceInpsObbligatorio() {
		return isCodiceInpsObbligatorio;
	}

	public void setIsCodiceInpsObbligatorio(Boolean isCodiceInpsObbligatorio) {
		this.isCodiceInpsObbligatorio = isCodiceInpsObbligatorio;
	}
	
//	public boolean isAbilitaCheckCalcolo(){
//		boolean storicoVuoto = this.erogazioneHistory.getRows().isEmpty();
//		return storicoVuoto; //TODO: Abilito anche quando non è vuoto, ma non sono state inserite erogazioni!
//	
//	}

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
		if (r != null && r.getSoggettoCaso() != null) {

			CsASoggettoLAZY s = r.getSoggettoCaso();
			CsAAnagrafica ana = s.getCsAAnagrafica();
			CsAAnaIndirizzo residenza = findIndirizzoResidenzaCaso(s);
			String via = residenza!=null ? residenza.getLabelIndirizzo() : null;
			String jsonComuneNascita = this.getJsonNascitaComuneBean(s);
			sero = new SoggettoErogazioneBean(s, via, getCasoComuneResidenza(residenza), residenza.getStatoCod(), jsonComuneNascita, false);

			this.getAltriSoggetti().add(sero);

		}

	}

	public void addSoggettoManuale() {

		boolean valido = true;
		if(this.altroSoggettoTmp.getCf()==null || altroSoggettoTmp.getCf().isEmpty()){
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
				
		if(valido && !isSoggettoPresente(this.altroSoggettoTmp.getCf())){
			this.getAltriSoggetti().add(this.altroSoggettoTmp);
			this.altroSoggettoTmp=new SoggettoErogazioneBean(false);
		}else
			addWarning("Non è possibile inserire il soggetto come beneficiario","Il codice fiscale del soggetto selezionato è già presente");
		
	}

	public void addDaAnagrafeWrapper(DatiUserSearchBean s) {
		ObjectMapper om = new ObjectMapper();
		if(s == null) {
			addWarning("Scegliere un soggetto o inserirlo manualmente", "");
			return;
		}

		SoggettoErogazioneBean sero = null;
		if(s!=null){
			
			boolean anaSearch = false;
			for(String tipoRicerca : TipoRicercaSoggetto.LISTA_TIPI){
				if(s.getId().trim().startsWith(tipoRicerca)){
					anaSearch = true;
					break;
				}
			}
			
			if(anaSearch){
				PersonaDettaglio p = (PersonaDettaglio)s.getSoggetto();
				
				if(p.isDefunto() && isBloccaUtentiDefunti()){
					addWarning("Non è possibile inserire il soggetto come beneficiario","Il soggetto selezionato è deceduto il "+ddMMyyyy.format(p.getDataMorte()));
					return;
				}
				
				/*Devo comunque interrogare il WS per recuperare i dati estesi*/
				if(p.getProvenienzaRicerca().equalsIgnoreCase(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA) ||
				   p.getProvenienzaRicerca().equalsIgnoreCase(DataModelCostanti.TipoRicercaSoggetto.SIGESS))
					p = CsUiCompBaseBean.getPersonaDaAnagEsterna(p.getProvenienzaRicerca(), p.getIdentificativo());
				
				String jsonN = null;
				if(p.getComuneResidenza()!=null){
					ComuneBean cb = new ComuneBean(p.getComuneNascita());
					try {
						jsonN = om.writeValueAsString(cb);
					} catch (JsonProcessingException e) {}
				}
				String nazioneNascita = p.getNazioneNascita()!=null ? p.getNazioneNascita().getCodIstatNazione() : null;
				
				
				String json = null;
				if(p.getComuneResidenza()!=null){
					ComuneBean cb = new ComuneBean(p.getComuneResidenza());
					try {
						json = om.writeValueAsString(cb);
					} catch (JsonProcessingException e) {}
				}
				String nazioneResidenza = p.getNazioneResidenza()!=null ? p.getNazioneResidenza().getCodIstatNazione() : null;
				
				sero = new SoggettoErogazioneBean(p.getCognome(), p.getNome(), p.getCodfisc(), p.getCittadinanza(), p.getDataNascita(), p.getIndirizzoCivicoResidenza() , json, nazioneResidenza, jsonN, nazioneNascita,  p.getSesso(), false);
				
				UserSearchBeanExt ubean = (UserSearchBeanExt)getReferencedBean("userSearchBeanExt");
				ubean.clearParameters();
				
			}
			
			if(sero!=null){
				if(!isSoggettoPresente(sero.getCf())){
					CsASoggettoLAZY caso = getSchedaCSByCF(sero.getCf());
					sero.setCsASoggetto(caso);

					this.getAltriSoggetti().add(sero);
				}else{
					addWarning("Non è possibile inserire il soggetto come beneficiario","Il codice fiscale del soggetto selezionato è già presente");
					return;
				}
			}
		}
	}

	private boolean isSoggettoPresente(String cf) {
		boolean trovato = false;
		if(!StringUtils.isBlank(cf)){
			//Verifico se coincide con quello di riferimento
			if(this.getSoggettoErogazione().getCf().equalsIgnoreCase(cf))
				trovato = true;
			
			//Verifico se esiste nella lista dei soggetti
			int i =0;
			while(i<this.getAltriSoggetti().size() && !trovato){
				if(this.getAltriSoggetti().get(i).getCf().equalsIgnoreCase(cf))
					trovato = true;
				i++;
			}
		}
		return trovato;
	}

	public void removeBeneficiario(SoggettoErogazioneBean beneficiario) {
		this.getAltriSoggetti().remove(beneficiario);
	}

	public List<SoggettoErogazioneBean> getAltriSoggetti() {
		if (this.altriSoggetti == null)
			altriSoggetti = new ArrayList<SoggettoErogazioneBean>();

		return altriSoggetti;
	}

	public void setAltriSoggetti(List<SoggettoErogazioneBean> altriSoggetti) {
		this.altriSoggetti = altriSoggetti;
	}

	public SoggettoErogazioneBean getAltroSoggettoTmp() {
		if (altroSoggettoTmp == null)
			altroSoggettoTmp = new SoggettoErogazioneBean(false);
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
			//beneficiarioSearchBean= new UserSearchBean();
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
	public void onUpdateQuotaDettaglio(BigDecimal tariffa, boolean isOreMinuti) {
		tariffa = tariffa != null ? tariffa : BigDecimal.ZERO;
//		if (!tariffa.equals(tariffaLocale)) {
//			tariffa = tariffaLocale;
//			
//		}
		//SISO-806
		if(isOreMinuti && (this.oreErogazione != null || this.minutiErogazione != null)) { 
		   
			double minutiInOre = ((double)this.minutiErogazione/60);
			double minutiInOreRound = (int)(Math.round(minutiInOre * 100))/100.0;
			double oreminutiDecimal = this.oreErogazione + minutiInOreRound;
			
			BigDecimal bgOreMinuti = BigDecimal.valueOf(oreminutiDecimal);
			
			nuovocsIntEseg.getCsIValQuota().setValQuota(bgOreMinuti);
		   }
	
		BigDecimal valQuota = nuovocsIntEseg.getCsIValQuota().getValQuota() != null ? nuovocsIntEseg.getCsIValQuota().getValQuota() : BigDecimal.ZERO;
		BigDecimal spesa = tariffa.multiply(valQuota);
		
		if(this.calcolaSpesaDaDettaglio)
		   this.nuovocsIntEseg.setSpesa(spesa);
	}

	public void onUpdateTariffa(BigDecimal tariffa, boolean isOreMinuti) {
		if (tariffa == null || tariffa.equals(BigDecimal.ZERO)) {
			this.nuovocsIntEseg.setCsIValQuota(new CsIValQuota());
//			tariffaLocale = BigDecimal.ZERO;
			this.nuovocsIntEseg.getCsIValQuota().setTariffaCustom(BigDecimal.ZERO);
		} else {
//			tariffaLocale = tariffa;
			this.nuovocsIntEseg.getCsIValQuota().setTariffaCustom(tariffa);
			if (this.nuovocsIntEseg.getCsIValQuota() == null) {
				this.nuovocsIntEseg.setCsIValQuota(new CsIValQuota());
			}
		}

		if (this.calcolaSpesaDaDettaglio) {
			// this.onUpdateQuotaDettaglio(tariffa);
//			this.onUpdateQuotaDettaglio(tariffaLocale, isOreMinuti);
			this.onUpdateQuotaDettaglio(this.nuovocsIntEseg.getCsIValQuota().getTariffaCustom(), isOreMinuti);
		}
		// else
		// this.onUpdateQuotaTestata(tariffa);
	}

	// FINE SISO-720 SM

	public void onUpdateDatiRif(SoggettoErogazioneBean se) {
		if (se.isRiferimento()) {
			soggettoErogazione.setCittadinanza(se.getCittadinanza());
			soggettoErogazione.setAnnoNascita(se.getAnnoNascita());
		}
	}

	public void onUpdateDatiPrinc() {
		for (SoggettoErogazioneBean s : this.getAltriSoggetti()) {
			if (s.isRiferimento()) {
				s.setCittadinanza(soggettoErogazione.getCittadinanza());
				s.setAnnoNascita(soggettoErogazione.getAnnoNascita());
				//SISO-962
				s.setCittadinanza2(soggettoErogazione.getCittadinanza2());
			}
		}
	}

	// INZIO MOD-RL

	public CsIPs getCsIPs() {
		return csIPs;
	}

	public void setCsIPs(CsIPs csIPs) {
		this.csIPs = csIPs;
		this.protDsuMan = new ProtocolloDsuMan(this.csIPs, soggettoErogazione!=null ? soggettoErogazione.getCf() : null);
	}

	// FINE MOD-RL

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

	public List<InterventoErogazAttrBean> getErogaziones(){
		CsCfgIntEsegStato statoSelezionato = getStatoSelezionato();
		return this.getErogaziones(statoSelezionato!=null ? statoSelezionato.getId() : null);
	}

	private List<InterventoErogazAttrBean> getErogaziones(Long statoId) {
		if (statoId != null)
			return this.mappaErogStatoAttr.get(statoId);
		else
			return new ArrayList<InterventoErogazAttrBean>();
	}

	//inizio SISO-556
	public boolean isPnlDettaglioErogazioniRendered(){
		//se ci sono righe di erogazione di chiusura non visualizzo il pannello di inserimento di righe erogazione
		boolean res = true;
		
		List<InterventoErogazHistoryRowBean> interventoErogazHistoryRowBeanList = this.getStoricoOperazioni();
		for (InterventoErogazHistoryRowBean interventoErogazHistoryRowBean : interventoErogazHistoryRowBeanList) {
			
			if (DataModelCostanti.CsCfgIntEsegStato.FLAG_CHIUDI_CHIUSO == interventoErogazHistoryRowBean.getStato().getFlagChiudi()) {
				res = false;
			}

		}

		return res;
	}

	// fine SISO-556

	// Gestione ISEE

	// INIZIO SISO-657
	// public boolean isProtocolloDomandaPrestazioneRequired() {
	// return situazioneEconomicaVerificata;
	// }
	//
	// public boolean getSituazioneEconomicaVerificata() {
	// return situazioneEconomicaVerificata;
	// }
	//
	//
	// public void setSituazioneEconomicaVerificata(boolean
	// situazioneEconomicaVerificata) {
	// this.situazioneEconomicaVerificata = situazioneEconomicaVerificata;
	// }
	// FINE SISO-657

	public String getIseeSelezionata() {
		return iseeSelezionata;
	}

	public void setIseeSelezionata(String iseeSelezionata) {
		this.iseeSelezionata = iseeSelezionata;
	}

	// INIZIO SISO-657 - Nuovo tracciato casellario PSA - PS - SINA

	public List<CsTbSchedaMultidim> getProvaMezziValueList() {
		List<CsTbSchedaMultidim> result = new ArrayList<CsTbSchedaMultidim>();

	    for(PROVA_MEZZI p : PROVA_MEZZI.values()){
	    	if(p.getCodice()>0){
	    		CsTbSchedaMultidim prova = new CsTbSchedaMultidim();
	    		prova.setDescrizione(p.getDescrizione());
	    		prova.setTooltip(p.getTooltip());
	    		result.add(prova);
	    	}	
	    }
		
		return result;
	}
	
	//INIZIO SISO-1021
	public List<CsTbSchedaMultidim> getNazioneResidenzaList() {
		List<CsTbSchedaMultidim> result = new ArrayList<CsTbSchedaMultidim>();
		
		CsTbSchedaMultidim prova = new CsTbSchedaMultidim();
//		prova.setDescrizione("Stato estero");
//		prova.setTooltip("Lo stato estero di Residenza è un dato obbligatorio. Qualora non si conosca lo stato di residenza del beneficiario, ricercare e selezionare il valore NON DISPONIBILE");
		prova.setDescrizione("Stato estero di residenza");
		prova.setTooltip("Lo stato estero di residenza è un dato obbligatorio. Qualora non si conosca lo stato estero di residenza del beneficiario selezionare il check 'Non disponibile'");
		result.add(prova);

		return result;
		}

	public void resetFlagProvaMezzi() {
		if (csIPs != null) {
			csIPs.setFlagProvaMezzi(PROVA_MEZZI.VUOTO.getCodice());
		}
	}

	public boolean isSituazioneEconomicaVerificata() {
		if (csIPs != null)
			return csIPs.isSituazioneEconomicaVerificata();
		else
			return false;
	}

	public List<SelectItem> getListaOpzioniProvaMezzi() {
		List<SelectItem> result = new ArrayList<SelectItem>();
		for(PROVA_MEZZI p : PROVA_MEZZI.values())
			result.add(new SelectItem(p.getCodice(),p.getDescrizione()));
		return result;
	}

	public List<SelectItem> getListaOpzioniPresoInCarico() {
		List<SelectItem> result = new ArrayList<SelectItem>();  
		for(FLAG_IN_CARICO v : FLAG_IN_CARICO.values())
			result.add(new SelectItem( v.getCodice(), v.getDescrizione()));
		return result;
	}
	
	public List<SelectItem> getListaAnniDsuTipologia() {	
		List<SelectItem> result = new ArrayList<SelectItem>(); 
		if(listaIsee!=null){
			for (KeyValueDTO isee : listaIsee)
				result.add(new SelectItem(isee.getCodice(), isee.getDescrizione())); 
		}
		return result;
	}

	public void confermaAnnoIsee() {
		if (!StringUtils.isBlank(iseeSelezionata))
			this.loadDatiIsee(Long.parseLong(iseeSelezionata));
		
	}

	public void loadDatiIsee(Long diarioId) {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(diarioId);
		CsDIsee iseeSelezionata = diarioService.findIseeById(dto);
		csIPs.setDataDSU(iseeSelezionata.getCsDDiario().getDtAmministrativa());
		this.protDsuMan = new ProtocolloDsuMan(iseeSelezionata);
	}

	public void provaAction() {
		logger.debug("provaAction " + iseeSelezionata);
	}

	private void loadDatiIsee() {
		listaIsee = new ArrayList<KeyValueDTO>();
		Long idCaso = this.soggettoErogazione.getCsASoggetto()!=null ? this.soggettoErogazione.getCsASoggetto().getCsACaso().getId() : null;
		if(idCaso!=null){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(idCaso);
			listaIsee = diarioService.findSintesiIseeByCaso(dto);
		}

	}

	public List<SelectItem> getListaCodiciPrestazione() {
		return listaCodiciPrestazione;
	}

	private void loadlListaCodiciPrestazione(Long idTipoIntevento)   {
		this.setLabelCodicePrestazione("Codice prestazione INPS");
		
			listaCodiciPrestazione = new ArrayList<SelectItem>();
			listaPrestazioniPIC = new ArrayList<String>();
			
			if (idTipoIntevento!=null) {
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(idTipoIntevento);
				List<ArRelClassememoPresInps> listaArRelClassememoPresInp = confService.findArRelClassememoPresInpbyTipoInterventoId(dto);  
		
				SelectItemGroup g1 = new SelectItemGroup("Già presente in Inps");
				SelectItemGroup g0 = new SelectItemGroup("Da trasmettere a Inps");
				
				List<SelectItem> si1 = new ArrayList<SelectItem>();
				List<SelectItem> si0 = new ArrayList<SelectItem>();
				
				//SISO-1162
				this.isCodiceInpsObbligatorio = listaArRelClassememoPresInp.size()	> 0;
				this.setLabelCodicePrestazione(this.isCodiceInpsObbligatorio == true ? this.getLabelCodicePrestazione().concat(" *") : this.getLabelCodicePrestazione());
				
				for (ArRelClassememoPresInps a : listaArRelClassememoPresInp) {
					String codice = a.getArTbPrestazioniInp().getCodice();
					if(a.getArTbPrestazioniInp().getFlagEsportaSePic())
						listaPrestazioniPIC.add(codice);
					
					SelectItem si = new SelectItem(codice, codice + " " + a.getArTbPrestazioniInp().getDenominazione());
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
	
	public Boolean getRenderMessaggioPrestazionePic() {
		boolean render = false;
		String codSelected = this.getCsIPs()!=null ? this.getCsIPs().getCodPrestazione() : null;
		Integer inCarico = this.getCsIPs()!=null ? this.getCsIPs().getFlagInCarico() : null;
		if(!StringUtils.isBlank(codSelected) && inCarico!=null) {
			render =  listaPrestazioniPIC.contains(codSelected) && FLAG_IN_CARICO.NO.getCodice()==inCarico;
		}
		return render;
	}
	
	// INIZIO SISO-657 - Nuovo tracciato casellario PSA - PS - SINA

	public void cbxProvaMezziListener(AjaxBehaviorEvent event) {
		situazioneEconomicaVerificataListener();
		// logger.debug(oldFlagProvaMezzi );
		// logger.debug(flagProvaMezzi);
		//
		// if ( (!oldFlagProvaMezzi.equals("1") && flagProvaMezzi.equals("1"))
		// || (oldFlagProvaMezzi.equals("1") && !flagProvaMezzi.equals("1"))
		// ) {
		// RequestContext.getCurrentInstance().update("flsDatiIseePerInps");
		// }
		// oldFlagProvaMezzi = flagProvaMezzi;
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
        	
			if (success) {
				RequestContext context = RequestContext.getCurrentInstance();
				if (!this.getListaAnniDsuTipologia().isEmpty()) {
					context.execute("annoIseeWV.show();");
					context.execute("  $( 'body' ).append( $('.annoIseeWVFormClass') );  ");
				} else {
					// TODO:aggiorna il form con i dati vuoti e richiedo inserimento dei valori
					context.update("@(.pgDatiIseePerInpsClass)");
				}
			}
			protDsuMan.cbxAnnoDsuListener();

		} else {
			// Reset valori form
			this.csIPs.resetDatiIsee();
			setCsIPs(csIPs);
		}
	}

	// INIZIO SISO-524
	public boolean isProtDomPrestDisabled() {
		return erogazioniEsportate;
	}

	public boolean isProvaMezziDisabled() {
		return erogazioniEsportate;
	}

	public boolean isDataDsuDisabled() {
		return erogazioniEsportate;
	}

	// INIZIO SISO-657
	public boolean isDatiDsuRendered() {
		return // SISO-657 dentroFascicolo ||
		erogazioniEsportate;
	}

	// SISO-860
	public void onChangePresaInCarico() {
		this.sceltaRadio = true;
	}
	
	public boolean validaDatiCsIPs() {
		boolean res = true;
		if(this.erogazionePossibile){
			if(nuovoIntEsegMast.getProtDomPrest()==null || nuovoIntEsegMast.getProtDomPrest().isEmpty()){
				res=false;
				this.addErrorCampiObbligatori("Erogazioni", "Prot. domanda prestazione");
			}
			if (csIPs.getFlagProvaMezzi() == PROVA_MEZZI.VUOTO.getCodice()) {
				res = false;
				addMessage(FacesMessage.SEVERITY_ERROR, "Il campo 'Prova dei mezzi' è obbligatorio ");
			}else if(  csIPs.getFlagProvaMezzi().equals(PROVA_MEZZI.ISEE_NON_PROVA_MEZZI.getCodice()) 
					|| csIPs.getFlagProvaMezzi().equals(PROVA_MEZZI.ISEE_E_PROVA_MEZZI.getCodice()) ){
				
				if(!protDsuMan.valida(csIPs.getDataDSU())){
				   res = false;
				   this.addWarning("Erogazioni", "Controllare che i campi anno, data e protocollo DSU siano tutti valorizzati o tutti nulli");
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
//			//SISO-722
//			if (csIPs.getFlagInCarico()==null){
//			res = false;
//				addMessage(FacesMessage.SEVERITY_ERROR, "Il campo 'Durante il servizio il beneficiario è in carico' è obbligatorio");
//			}
//			if(csIPs.getCodPrestazione()== null || csIPs.getCodPrestazione().equals("")){ //SISO-860
			if(StringUtils.isBlank(csIPs.getCodPrestazione()) && this.isCodiceInpsObbligatorio){ //SISO-1162
				res = false;
				this.addErrorCampiObbligatori("Erogazioni", "Codice prestazione INPS");
			}
			if ( csIPs.getFlagInCarico()==null || this.sceltaRadio == false) {
				res = false;
				csIPs.setFlagInCarico(null); //altrimenti restituisce false anche se non premuto, quindi sembra selezionato
				this.addErrorCampiObbligatori("Erogazioni", "Durante il servizio il beneficiario è in carico");
			}
		}
		return res;
	}

	// FINE SISO-657

	// SISO-722
	public void azzeraFlagPresoInCarico() {
		if (this.isNonSoPresoInCarico()) {
			csIPs.setFlagInCarico(null);
		}
	}

	public boolean isCodPrestazioneDisabled() {
		return erogazioniEsportate;
	}

	public boolean isCambiaDatiIseeBtnDisabled() {
		return erogazioniEsportate;
	}

	// FINE SISO-524

	public String getDescOrgEroganteId() {
		CsOSettore s = super.findSettoreById(this.selSettoreEroganteId);
		return  (s!=null && s.getCsOOrganizzazione()!=null) ? "- Org. "+s.getCsOOrganizzazione().getNome() : "";
	}

	public CsOSettore getSettoreTitolare() {
		return settoreTitolare;
	}

	public void setSettoreTitolare(CsOSettore settoreTitolare) {
		if (settoreTitolare.getId() == null) {
			this.settoreTitolare = null;
		} else {
			this.settoreTitolare = settoreTitolare;
		}
	}

	public CsOSettore getSettoreGestore() {
		return settoreGestore;
	}

	public void setSettoreGestore(CsOSettore settoreGestore) {
		if (settoreGestore.getId() == null) {
			this.settoreGestore = null;
		} else {
			this.settoreGestore = settoreGestore;
		}
	}

	public boolean isGestoreComeTitolare() {
		return gestoreComeTitolare;
	}

	public void setGestoreComeTitolare(boolean gestoreComeTitolare) {
		this.gestoreComeTitolare = gestoreComeTitolare;
	}

	public ErogazioneInterventoTooltipText getTooltipText() {
		return erogazioneInterventoTooltipText;
	}

	/* SISO-720 SM */
	public BigDecimal getTariffaLocale() {
		return tariffaLocale;
	}

	public void setTariffaLocale(BigDecimal tariffaLocale) {
		this.tariffaLocale = tariffaLocale;
	}

	/* FINE SISO-720 SM */

	public Boolean getCambiaBeneficiarioRiferimento() {
		return cambiaBeneficiarioRiferimento;
	}

	public void setCambiaBeneficiarioRiferimento(Boolean cambiaBeneficiarioRiferimento) {
		this.cambiaBeneficiarioRiferimento = cambiaBeneficiarioRiferimento;
	}

	// SISO-809
	public boolean getEsportazioneDiGruppo() {
		return esportazioneDiGruppo;
	}

	public void setEsportazioneDiGruppo(boolean esportazioneDiGruppo) {
		this.esportazioneDiGruppo = esportazioneDiGruppo;
	}
	//fine SISO-809

	//SISO-722
	public boolean isNonSoPresoInCarico() {
		return nonSoPresoInCarico;
	}

	public void setNonSoPresoInCarico(boolean nonSoPresoInCarico) {
		this.nonSoPresoInCarico = nonSoPresoInCarico;
	}

	// SISO - 883
	private Boolean esisteInterventoDuplicato() {
		Boolean esiste = false;
		Boolean checkCodPrestazione = false;
		Boolean checkEnteTitolare = false;
		Boolean checkProtocolloInps = false;
		Boolean checkRangeDate = false;

		// SISO - 883
		BaseDTO bdto = new BaseDTO();
		bdto.setObj(soggettoErogazione.getCf());
		fillEnte(bdto);
		List<ErogazionePrestazioneDTO> listErogIntervento = interventoService.recuperaListaErogazioneDuplicateByCf(bdto);
		
		//Erogazione corrente
		Long idEnteTitolare = this.getSettoreTitolare().getCsOOrganizzazione().getId();
		String codPrestazione = this.getCsIPs().getCodPrestazione();
	
		List<Long> lstEsegIds = this.erogazioneHistory.getListaIdInterventiAttivi();
		List<InterventoErogazHistoryRowBean> lstNewErogs = this.getErogazioneHistory().getNewRows();
		
		int i = 0;
		while (!esiste && i<listErogIntervento.size()) {
			
			ErogazionePrestazioneDTO element = listErogIntervento.get(i);
			
			boolean verifica = true;
			/*Serve per escludere eventuali erogazioni eliminate ma non ancora salvate nel DB che bloccherebbero erroneamente il salvataggio*/
			if(element.getMastId().longValue()==this.nuovoIntEsegMast.getId())
				verifica = lstEsegIds.contains(element.getEsegId().longValue());
			
			if(verifica){
				checkCodPrestazione = !StringUtils.isBlank(element.getCodPrestazione()) && codPrestazione.equals(element.getCodPrestazione());
				checkEnteTitolare = element.getIdEnteTitolare()!=null && idEnteTitolare.longValue() == element.getIdEnteTitolare().longValue();
				checkProtocolloInps = checkProtInps(element);
				checkRangeDate =checkRangeDate(lstNewErogs, element.getDataErog(), element.getDataErogA());
			}
			esiste = checkCodPrestazione && checkEnteTitolare && checkProtocolloInps && checkRangeDate;
			i++;
		}
		if (esiste) {
			addMessage(FacesMessage.SEVERITY_WARN,"Intervento duplicato: controllare codice prestazione Inps, ente titolare, protocollo DSU, date erogazione");
		}
		
		//TODO: verifico tra il nuovo intervento e i nuovi presenti se ci sono duplicati di data
		
		return esiste;
	}

	// SISO - 883
	private boolean checkProtInps(ErogazionePrestazioneDTO intervento) {
		// protocolloInps
		String numProtocollo = this.protDsuMan.getDto().getNumero();
		String prefixProtDsu = this.protDsuMan.getDto().getPrefisso();
		String progProtDsu = this.protDsuMan.getDto().getProgressivo();
		boolean checkProtInps = false;
		if(!StringUtils.isBlank(intervento.getPrefixProtDsu()) && 
		   !StringUtils.isBlank(intervento.getNumProtDsu()) && 
		   !StringUtils.isBlank(intervento.getProgProtDsu())){
			if (numProtocollo != null
					&& numProtocollo.equals(intervento.getNumProtDsu())
					&& progProtDsu.equals(intervento.getProgProtDsu())
					&& prefixProtDsu.equals(intervento.getPrefixProtDsu())) {
				checkProtInps = true;
	
			}
		   }
	   return checkProtInps;
	}

	// SISO - 883
	private boolean checkRangeDate(List<InterventoErogazHistoryRowBean> lstNewErogs, Date dataDa, Date dataA) {
		
		boolean checkReDate = false;

		if (lstNewErogs != null) { // lstNewErogs erogazioni inserite nel		// dettaglio non ancora salvate
			for (InterventoErogazHistoryRowBean newRow : lstNewErogs) {
				if(DataModelCostanti.TipoStatoErogazione.EROGATIVO.equals(newRow.getStato().getTipo())){
					if (newRow.getDataErogazioneA() == null) {
						if (dataA != null) {
	
							if (dataDa.before(newRow.getDataErogazione()) && dataA.after(newRow.getDataErogazione())) {
								checkReDate = true;
								break;
							}
							
						}else{//Erogazioni Irregolari
							Calendar cal1 = Calendar.getInstance();
					        Calendar cal2 = Calendar.getInstance();
					        cal1.setTime(dataDa);
					        cal2.setTime(newRow.getDataErogazione());
						
						  if (cal1.equals(cal2)){
								checkReDate = true;
								break;
							}
						}
						
					} else {
						if (dataA != null) {
							if (!ErogazioneInterventoBean.controllaGiorni(newRow.getDataErogazione(), newRow.getDataErogazioneA(), dataDa, dataA)) {
								checkReDate = true;
								break;
							}
						}
					}
				}
			}
		}
		return checkReDate;
	}
	
	public void valorizzaResidenzaMan(){
	 	//Estraggo i dati e deserializzo
		ObjectMapper om = new ObjectMapper();
		try {
			
			if(!StringUtils.isBlank(soggettoErogazione.getNazioneResidenza())){
				this.comuneNazioneResidenzaMan.setNazioneValue();
				AmTabNazioni naz = luoghiService.getNazioneByIstat(soggettoErogazione.getNazioneResidenza());
				this.comuneNazioneResidenzaMan.getNazioneResidenzaMan().setNazione(naz);
			}else if(StringUtils.isBlank(soggettoErogazione.getNazioneResidenza()) && soggettoErogazione.isNazioneResidenzaNonDefinita()){
					this.comuneNazioneResidenzaMan.setNazioneValue();
				
			}else{
				this.comuneNazioneResidenzaMan.setComuneValue();
				if(!StringUtils.isBlank(soggettoErogazione.getComuneResidenza()))
					this.comuneNazioneResidenzaMan.getComuneMan().setComune(om.readValue(soggettoErogazione.getComuneResidenza(), ComuneBean.class));	
			}
		} catch (Exception e) {
			logger.error(e);
		}
		 
	}

	public void valorizzaNascitaMan(){
	 	//Estraggo i dati e deserializzo
		ObjectMapper om = new ObjectMapper();
		try {
			
			if(!StringUtils.isBlank(soggettoErogazione.getNazioneNascita())){
				this.comuneNazioneNascitaMan.setNazioneValue();
				AmTabNazioni naz = luoghiService.getNazioneByIstat(soggettoErogazione.getNazioneNascita());
				this.comuneNazioneNascitaMan.getNazioneNascitaMan().setNazione(naz);
			}else{
				this.comuneNazioneNascitaMan.setComuneValue();
				if(!StringUtils.isBlank(soggettoErogazione.getComuneNascita()))
					this.comuneNazioneNascitaMan.getComuneMan().setComune(om.readValue(soggettoErogazione.getComuneNascita(), ComuneBean.class));	
			}
		} catch (Exception e) {
			logger.error(e);
		}
		 
	}
	
	//SISO_1138
	public void valorizzaSesso(){
		if(this.sessoBeneficiario!=null)
			this.soggettoErogazione.setSesso(this.sessoBeneficiario.getSesso());
	}
	
	public void valorizzaResidenza() {
		//SISO-962 Inizio
		if(this.comuneNazioneResidenzaMan != null && this.comuneNazioneResidenzaMan.isComune() && this.comuneNazioneResidenzaMan.getComuneMan().getComune() != null){
			ObjectMapper om = new ObjectMapper();
			try{
				this.soggettoErogazione.setNazioneResidenza(null);
				this.soggettoErogazione.setNazioneResidenzaNonDefinita(false);//SISO-1021 se la azione estera di residenza è nulla e il comune è valorizzata il check "non disponibile " deve tornare a false
				this.soggettoErogazione.setComuneResidenza(om.writeValueAsString(this.comuneNazioneResidenzaMan.getComuneMan().getComune()));
		 	}catch(Exception ex){
				
			}
		}else if(this.comuneNazioneResidenzaMan != null && this.comuneNazioneResidenzaMan.isNazione() && this.comuneNazioneResidenzaMan.getNazioneMan().getNazione()!=null){
			
			this.soggettoErogazione.setNazioneResidenza(this.comuneNazioneResidenzaMan.getNazioneMan().getNazione().getCodIstatNazione()); //.getIso3166());
			this.soggettoErogazione.setComuneResidenza(null);
			
		}
		//SISO-962 Fine
			
	}
	
	public void valorizzaLuogoDiNascita(){
		try{
			
			ObjectMapper mapper = new ObjectMapper();
			//resetto i valori per poi valorizzare correttamente
			soggettoErogazione.setComuneNascita(null);
			soggettoErogazione.setNazioneNascita(null);
			
			if(comuneNazioneNascitaMan.isComune()){
				ComuneBean comune = this.comuneNazioneNascitaMan.getComuneNascitaMan().getComune();
				soggettoErogazione.setComuneNascita(comune!=null ? mapper.writeValueAsString(comune) : null);
			
			}else{	
				AmTabNazioni naz = this.comuneNazioneNascitaMan.getNazioneNascitaMan().getNazione();
				if(naz!=null){
					soggettoErogazione.setNazioneNascita(naz.getCodIstatNazione());
				}
			}
		} catch (JsonProcessingException e) {
				logger.error("Errore popolamento Comune/Nazione nascita", e);
		}
	}

	public void aggiornaSoggettoRiferimento() {
		this.soggettoErogazione.setRiferimento(false);
		this.altriSoggetti.add(this.soggettoErogazione);
		
		this.beneficiarioSel.setRiferimento(true);
		this.soggettoErogazione=this.beneficiarioSel;
		this.altriSoggetti.remove(this.beneficiarioSel);
		this.beneficiarioSel=null;
		
		/*int index = this.altriSoggetti.indexOf(se);
		for(int i = 0; i<this.altriSoggetti.size(); i++){
			SoggettoErogazioneBean s = this.altriSoggetti.get(i);
			if(i==index) s.setRiferimento(true);
			else s.setRiferimento(false);
		}*/

		this.valorizzaResidenzaMan();
		this.valorizzaNascitaMan();
	}

	// SISO-1138
	public SessoBean getSessoBeneficiario() {
		return sessoBeneficiario;
	}

	public void setSessoBeneficiario(SessoBean sessoBeneficiario) {
		this.sessoBeneficiario = sessoBeneficiario;
	}
	// SISO-1138 FINE

	public ProtocolloDsuMan getProtDsuMan() {
		return protDsuMan;
	}

	public void setProtDsuMan(ProtocolloDsuMan protDsuMan) {
		this.protDsuMan = protDsuMan;
	}
	
	public String getLabelCodicePrestazione() {
		return labelCodicePrestazione;
	}

	public void setLabelCodicePrestazione(String labelCodicePrestazione) {
		this.labelCodicePrestazione = labelCodicePrestazione;
	}
	
	public boolean isDisabilitaBeneficiarioRif(){
		boolean disabilitaDatiMaster = this.isDisabilitaDatiMaster() ;
		boolean disabilitaAnagrafica = this.soggettoErogazione.isDisabilita();
       return disabilitaAnagrafica || disabilitaDatiMaster; 
	}
	
	public boolean isDisabilitaDatiMaster(){
		return !this.getErogazionePossibile() || this.getTestataDisabled();
	}
	
	public SoggettoErogazioneBean getBeneficiarioSel() {
		return beneficiarioSel;
	}

	public void setBeneficiarioSel(SoggettoErogazioneBean beneficiarioSel) {
		this.beneficiarioSel = beneficiarioSel;
	}

	//SISO-1021
	public boolean isNazioneResidenzaNonDefinita() {
		return nazioneResidenzaNonDefinita;
	}

	public void setNazioneResidenzaNonDefinita(boolean nazioneResidenzaNonDefinita) {
		this.nazioneResidenzaNonDefinita = nazioneResidenzaNonDefinita;
	}
	
	public void sincronizzaResidenza(SoggettoErogazioneBean se){
		CsASoggettoLAZY soggetto = se.getCsASoggetto();
		if(soggetto!=null){
			CsAAnaIndirizzo residenza = findIndirizzoResidenzaCaso(soggetto);
			String via = residenza!=null ? residenza.getLabelIndirizzo() : null;
			String statoCod = residenza!=null ? residenza.getStatoCod() : null;
			se.sincronizzaResidenza(via, getCasoComuneResidenza(residenza), statoCod);
			this.valorizzaResidenzaMan();
		}
	}
	
	public void sincronizzaDatiAnagrafici(){
		String comuneNascita = getJsonNascitaComuneBean(soggettoErogazione.getCsASoggetto());
		this.soggettoErogazione.sincronizzaDatiAnagrafici(comuneNascita);
		String s = soggettoErogazione.getSesso();		
		this.sessoBeneficiario = new SessoBean(s);
	}
	
	public String getLabelLuogoResidenza(SoggettoErogazioneBean se){
		String value = "-";
		try {
			
			if(!StringUtils.isBlank(se.getComuneResidenza())){
				ObjectMapper om = new ObjectMapper();
				ComuneBean bean = om.readValue(se.getComuneResidenza(), ComuneBean.class);
				value = bean.getDenominazione()+" ("+bean.getSiglaProv()+")";
			}else{
				if(!StringUtils.isBlank(se.getNazioneResidenza())){
					AmTabNazioni naz = luoghiService.getNazioneByIstat(se.getNazioneResidenza());
					value = naz.getNazione();
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return value;
	}

	//SISO-1133
	public void onImpostaTuttoAZero() {
		
			nuovocsIntEseg.setCompartUtenti(new BigDecimal(0));
			nuovocsIntEseg.setCompartSsn(new BigDecimal(0));
			nuovocsIntEseg.setCompartAltre( new BigDecimal(0));
			
			aggiornaCompartecipazioneDettaglio();
	
	}
     
	// SISO-1133
	public void onChangeStatoResetSpesa() {

		nuovocsIntEseg.setSpesa(null);
		nuovocsIntEseg.setCompartUtenti(null);
		nuovocsIntEseg.setCompartSsn(null);
		nuovocsIntEseg.setCompartAltre(null);
		nuovocsIntEseg.setCsIValQuota(new CsIValQuota());
		nuovocsIntEseg.setSpesa(null);
		nuovocsIntEseg.setPercGestitaEnte(null);
		nuovocsIntEseg.setValoreGestitaEnte(null);
		this.setMinutiErogazione(null);
		this.setOreErogazione(null);

	}
	
	public void onCalcoloValoreGestitaEnte() {
		
		if(this.calcolaSpesaDaDettaglio){
			//calcolo della spesa gestita ente:
			 BigDecimal nuovaSpesaGestitaEnte= new BigDecimal(0);
			if(this.nuovocsIntEseg.getSpesa()!=null && nuovocsIntEseg.getSpesa().compareTo(new BigDecimal(0))!=0){
				BigDecimal compUtenti = nuovocsIntEseg.getCompartUtenti()!=null ? nuovocsIntEseg.getCompartUtenti() : BigDecimal.ZERO;
				BigDecimal compSsn = nuovocsIntEseg.getCompartSsn()!=null ? nuovocsIntEseg.getCompartSsn() : BigDecimal.ZERO;
				
				nuovaSpesaGestitaEnte = (this.nuovocsIntEseg.getSpesa().subtract(compUtenti)).subtract(compSsn);
			}
			
			if (nuovaSpesaGestitaEnte.compareTo(new BigDecimal(0))<0) {
					addWarning("Spesa","La spesa Gestita dall'Ente non può essere minore di zero!");
			}else {
					nuovocsIntEseg.setValoreGestitaEnte(nuovaSpesaGestitaEnte);
			}
		
			if(nuovocsIntEseg.getValoreGestitaEnte()!=null){
				Double percCalc = calcolaDettPercSpesa(nuovocsIntEseg.getValoreGestitaEnte().doubleValue(), this.nuovocsIntEseg.getSpesa().doubleValue());
				nuovocsIntEseg.setPercGestitaEnte(new BigDecimal(percCalc).setScale(2, RoundingMode.HALF_EVEN));
			}
		}
	}
	
	public void onChangeCodPrestazione(){
		protDsuMan.setCodPrestazione(csIPs.getCodPrestazione());
	}
	
	public void handleChangeAnnoProtocollo(){
		DatiIsee dati = this.protDsuMan.cbxAnnoDsuListener();
		if(dati!=null) 
			this.csIPs.setDataDSU(dati.getDataPresentazioneIsee());
	}
	
	public List<InterventoErogazHistoryRowBean> getStoricoOperazioni(){
		return this.erogazioneHistory.getRows();
	}

	public Date getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}
	
	public ComuneNazioneNascitaMan getComuneNazioneNascitaMan() {
		return comuneNazioneNascitaMan;
	}

	public void setComuneNazioneNascitaMan(ComuneNazioneNascitaMan comuneNazioneNascitaMan) {
		this.comuneNazioneNascitaMan = comuneNazioneNascitaMan;
	}

	private boolean isDataEventoObbligatoria(){
		return csIPs!=null && "A1.05.01".equals(csIPs.getCodPrestazione());
	}
	
	public String getDataEventoLabel() {
		if(csIPs!=null && "A1.05.01".equals(csIPs.getCodPrestazione())) //SISO-2384
			this.dataEventoLabel = "Data di effettiva erogazione del contributo (cassa)";
		else this.dataEventoLabel = DATA_EVENTO_LABEL_DEFAULT;
		return dataEventoLabel;
	}	
}
