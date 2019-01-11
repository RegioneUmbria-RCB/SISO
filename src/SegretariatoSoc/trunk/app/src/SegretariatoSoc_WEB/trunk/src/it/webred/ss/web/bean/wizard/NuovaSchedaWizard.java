package it.webred.ss.web.bean.wizard;

import it.webred.amprofiler.ejb.user.UserService;
import it.webred.amprofiler.model.AmUser;
import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableCatSocialeSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableMediciSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableNazioniSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSegrSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.SchedaSegrDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneBaseDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.Scheda;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsCMedico;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.CsDClob;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsTbCittadinanzaAcq;
import it.webred.cs.data.model.CsTbStatoCivile;
import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.OrientamentoLavoro.IOrientamentoLavoro;
import it.webred.cs.json.abitazione.AbitazioneManBaseBean;
import it.webred.cs.json.abitazione.IAbitazione;
import it.webred.cs.json.familiariConviventi.FamiliariManBaseBean;
import it.webred.cs.json.familiariConviventi.IFamConviventi;
import it.webred.cs.json.intermediazione.IIntermediazioneAb;
import it.webred.cs.json.mediazioneculturale.IMediazioneCult;
import it.webred.cs.json.orientamentoistruzione.IOrientamentoIstruzione;
import it.webred.cs.json.serviziorichiestocustom.IServizioRichiestoCustom;
import it.webred.cs.json.stranieri.IStranieri;
import it.webred.cs.json.stranieri.StranieriManBaseBean;
import it.webred.cs.sociosan.ejb.client.ArgoBufferManagerSessionBeanRemote;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.ComponenteFamigliaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.IndirizzoAnagDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.siso.esb.Medico;
import it.webred.siso.esb.client.MedicoClient;
import it.webred.siso.ws.client.anag.client.PersonaFindResult;
import it.webred.ss.data.model.ArBufferSsInvio;
import it.webred.ss.data.model.ArOOrganizzazione;
import it.webred.ss.data.model.SsAnagrafica;
import it.webred.ss.data.model.SsDiario;
import it.webred.ss.data.model.SsIndirizzo;
import it.webred.ss.data.model.SsInterventiSchede;
import it.webred.ss.data.model.SsIntervento;
import it.webred.ss.data.model.SsInterventoEconomico;
import it.webred.ss.data.model.SsInterventoEconomicoTipo;
import it.webred.ss.data.model.SsMotivazione;
import it.webred.ss.data.model.SsMotivazioniSchede;
import it.webred.ss.data.model.SsOOrganizzazione;
import it.webred.ss.data.model.SsRelUffPcontOrg;
import it.webred.ss.data.model.SsRelUffPcontOrgPK;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaAccesso;
import it.webred.ss.data.model.SsSchedaAccessoInviante;
import it.webred.ss.data.model.SsSchedaInterventi;
import it.webred.ss.data.model.SsSchedaMotivazione;
import it.webred.ss.data.model.SsSchedaPrivacy;
import it.webred.ss.data.model.SsSchedaPrivacyPK;
import it.webred.ss.data.model.SsSchedaRiferimento;
import it.webred.ss.data.model.SsSchedaSegnalante;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.data.model.SsTipoScheda;
import it.webred.ss.data.model.SsUfficio;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.web.bean.SegretariatoSocBaseBean;
import it.webred.ss.web.bean.report.ReportPrivacyBean;
import it.webred.ss.web.bean.util.Organizzazione;
import it.webred.ss.web.bean.util.PuntoContatto;
import it.webred.ss.web.bean.util.Soggetto;
import it.webred.ss.web.bean.util.Ufficio;
import it.webred.ss.web.bean.util.UserBean;
import it.webred.ss.web.bean.util.UserSearchBean;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@ViewScoped
public class NuovaSchedaWizard extends SegretariatoSocBaseBean {
	
	

	private UserSearchBean userSearchBean;

	public String VER_MAX = "";
	private static final String ACCESSO_TAB = "accesso";
	private static final String SEGNALANTE_TAB = "segnalante";
	private static final String SEGNALATO_TAB = "segnalato";
	private static final String RIFERIMENTO_TAB = "riferimento";
	private static final String MOTIVAZIONE_TAB = "motivazione";
	private static final String INTERVENTI_TAB = "interventi";
	private static final String CHIUSURA_TAB = "chiusura";

	private String[] steps = new String[] { ACCESSO_TAB, SEGNALANTE_TAB, SEGNALATO_TAB, RIFERIMENTO_TAB, MOTIVAZIONE_TAB, INTERVENTI_TAB, CHIUSURA_TAB };

	private static final String HOME = "home.faces";
	private static final String COMUNE = "COMUNE";

	private Accesso accesso;
	private Accesso accessoOrig;
	
	private Segnalante segnalante;
	private Segnalato segnalato;
	
	/*JSON SEGNALATO*/
	private IStranieri stranieriMan;
	private IAbitazione abitazioneMan;
	private IFamConviventi famConviventiMan;
	
	//INIZIO SISO-438-Possibilità di allegare documenti in UdC
	private List<SelectItem> tipoInterventosCustom = new LinkedList<SelectItem>();	
	public AccessTableInterventoSessionBeanRemote interventoService = getAccessTableInterventoService();
	private Long selectedTipoInterventoCustom;
	//FINE SISO-438-Possibilità di allegare documenti in UdC
	
	/*JSON SERVIZI RICHIESTI*/
	private ServiziRichiestiInterventiCustomBean serviziRichiestiInterventiCustomBean; //SISO-438

	private PersonaRiferimento riferimento;
	private Motivazione motivazione;

	private Interventi intervento;
	private Long tipoScheda;
	private DiarioSociale diarioSociale;
	private Nota notaDiarioPubblica;
	private Nota notaDiarioPrivata;
	private String interventoEconomicoTipo;
	private String interventoEconomicoImporto;
	private Long categoriaSociale;
	
	/*Modulo Privacy - Tab Interventi*/
	private boolean privacySottoscrivi;  //Visualizzato nel caso in cui non siano presenti moduli (salvo se TRUE)
	private Date privacyDate;
	private SsSchedaPrivacy privacy;
	
//	private boolean privacyAnonimo;
	private Long privacyAnonimo_prev_anagraficaId=null;
	
	private SsScheda scheda;
	private SsScheda schedaPrecedente;
	private boolean abilitaLoadPrecedente;
	private String statoCartella;

	private InterventiView interventiEconomici;

	private List<Long> schedeId;
	private List<SelectItem> operatori;
	private List<SelectItem> sedi;
	private List<String> modalita;
	private List<SelectItem> interlocutori;
	private List<SelectItem> relazioni;
	// private List<String> invianti;
	private List<String> statiCivili;
	private List<SelectItem> statiCiviliSelectItem;
	private List<SelectItem> cittadinanze;
	private List<SelectItem> cittadinanzeAcquisite;
	private List<String> residenze;
	private List<String> medici;
	private List<String> assistenti;
	private List<String> accessi;
	private List<String> parentele;

	private HashMap<String, List<SsMotivazione>> motivazioniClasse;
	private List<SelectItem> motivazioni;
	private List<SelectItem> motivazioni1;
	private List<SelectItem> motivazioni2;
	private List<SelectItem> motivazioni3;
	private List<SelectItem> motivazioni4;
	private List<SelectItem> motivazioni5;

	private List<ErogazioneBaseDTO> interventiErogati;
	private List<SelectItem> interventi;
	private List<SelectItem> inviatiDa;
	private List<SelectItem> settori;	//SISO-346
	private List<SelectItem> tipiScheda;
	private List<SelectItem> tipiSchedaTips;
	private List<SsTipoScheda> tipiSchedaModel;
	private List<String> interventiEconomiciTipi;
	private List<SelectItem> categorieSociali;
	private List<String> motiva = new ArrayList<String>();
	private List<Soggetto> nucleoFamiliare; 

	/* modulo invio scheda ad altri uffici/enti */
	private List<SelectItem> organizzazioniDiZona;
	private List<SelectItem> organizzazioniAltraZona;
	private List<SelectItem> organizzazioniAltre;
	private List<SelectItem> ufficiOrganizzazione;

	private boolean reloadUfficiOrganizzazione = false;
	private boolean schedaRecuperataDaInvio=false;

	private long organizzazioneDiZonaId = -1;
	private long organizzazioneAltraZonaId = -1;
	private long organizzazioneAltreId = -1;
	private long ufficioOrganizzazioneId = -1;
	private SsUfficio ufficioDest = null;

	private String origBelfiore;
	private Organizzazione origOrganizzazione;
	private String origUriService;
	private Long origId;
	private Date origDataAccesso;
	private String origCognome;
	private String origAccessoModalita;
	private String origAccessoIneterlocutore;
	private String origAccessoMotivo;
	private String origNome;
	private Date origDataNascita;
	private Ufficio origUfficio;
	private String origPuntoContatto;
	private String origCognomeOperatore;
	private String origNomeOperatore;
	private String origEmail;
	private String origTelefono;
	private String origZonaSoc;
	private String origTipoIntervento;
	private Date origDataInvio;

	private String emailDest;

	private String emailSubj;

	private String emailTxt;

	private String destBelfiore;
	private String destZonaSoc;
	private Long destOrganizzazioneId;

	private SsUfficio destUfficio;

	private String dateParser = "dd/MM/yyyy";

	private static final String TAG_ORIG_DESC_ORGANIZZAZIONE = "ORIG_DESC_ORGANIZZAZIONE";
	private static final String TAG_ORIG_DATA_ACCESSO = "ORIG_DATA_ACCESSO";
	private static final String TAG_ORIG_COGNOME = "ORIG_COGNOME";
	private static final String TAG_ORIG_NOME = "ORIG_NOME";
	private static final String TAG_ORIG_COGNOME_OPERATORE = "ORIG_COGNOME_OPERATORE";
	private static final String TAG_ORIG_NOME_OPERATORE = "ORIG_NOME_OPERATORE";
	private static final String TAG_ORIG_TELEFONO = "ORIG_TELEFONO";
	private static final String TAG_ORIG_EMAIL = "ORIG_EMAIL";
	private static final String TAG_ORIG_ZONA_SOC = "ORIG_ZONA_SOC";
	private static final String TAG_LABEL_UDC = "LABEL_UDC";

	private boolean infoSuInvioSchedaRecuperate = false;

	private ArBufferSsInvio schedaInfoInvio = new ArBufferSsInvio();
	
	private List<SsInterventiSchede> ssInterventiSchede=new ArrayList<SsInterventiSchede>();

	/*--- modulo invio scheda ad altri uffici/enti */

	private boolean percentualeDisabled = true;
	private boolean specificareProblemiDisabled = true;
	private boolean skip;

	private Boolean showInterventiEconomici = false;

	private String indietroButtonTesto;
	private String indietroButtonLink;
	
	private Nota selectedNota2Delete;

	public String getInterventoEconomicoTipo() {
		return interventoEconomicoTipo;
	}

	public void setInterventoEconomicoTipo(String interventoEconomicoTipo) {
		this.interventoEconomicoTipo = interventoEconomicoTipo;
	}

	public String getInterventoEconomicoImporto() {
		return interventoEconomicoImporto;
	}

	public void setInterventoEconomicoImporto(String interventoEconomicoImporto) {
		this.interventoEconomicoImporto = interventoEconomicoImporto;
	}

	public List<String> getInterventiEconomiciTipi() {
		if (interventiEconomiciTipi.isEmpty()) {
			try {
				SsSchedaSessionBeanRemote schedaService =this.getSsSchedaService();
				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
				List<SsInterventoEconomicoTipo> tipi = schedaService
						.readInterventiEconomiciTipi(dto);

				for (SsInterventoEconomicoTipo tipo : tipi)
					interventiEconomiciTipi.add(tipo.getTipo());

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				addError("lettura.error");
			}
		}
		return interventiEconomiciTipi;
	}

	public void setInterventiEconomiciTipi(List<String> interventiEconomiciTipi) {
		this.interventiEconomiciTipi = interventiEconomiciTipi;
	}

	public InterventiView getInterventiEconomici() {
		return interventiEconomici;
	}

	public void setInterventiEconomici(InterventiView interventiEconomici) {
		this.interventiEconomici = interventiEconomici;
	}

	public Boolean getShowInterventiEconomici() {
		return showInterventiEconomici;
	}

	public void setShowInterventiEconomici(Boolean showInterventiEconomici) {
		this.showInterventiEconomici = showInterventiEconomici;
	}

	public PersonaRiferimento getRiferimento() {
		return riferimento;
	}

	public void setProblemiRiferimentoChanged(AjaxBehaviorEvent event) {
		this.specificareProblemiDisabled = !specificareProblemiDisabled;
	}

	public boolean isPercentualeDisabled() {
		return percentualeDisabled;
	}

	public void setPercentualeDisabled(boolean percentualeDisabled) {
		this.percentualeDisabled = percentualeDisabled;
	}

	public boolean isSpecificareProblemiDisabled() {
		return specificareProblemiDisabled;
	}

	public void setSpecificareProblemiDisabled(
			boolean specificareProblemiDisabled) {
		this.specificareProblemiDisabled = specificareProblemiDisabled;
	}

	public IStranieri initManStranieri() {
		
		IStranieri stranieriMan = StranieriManBaseBean.initByVersion(VER_MAX);
		if (scheda!= null)
			stranieriMan.setIdSchedaUdc(scheda.getId());

		return stranieriMan;
	}

	public IAbitazione initManAbitazione() {
		IAbitazione abitazioneMan = AbitazioneManBaseBean.initByVersion(VER_MAX);
		if (scheda!=null)
			abitazioneMan.setIdSchedaUdc(scheda.getId());
			
		return abitazioneMan;
	}
	
	public IFamConviventi initManFamConviventi() {
		IFamConviventi famConviventiMan = FamiliariManBaseBean.initByVersion(VER_MAX);
		if (scheda != null)
			famConviventiMan.setIdSchedaUdc(scheda.getId());
		
		return famConviventiMan;
	}
	
	private IStranieri loadSchedaJsonStranieri(Long schedaId) throws Exception {
		IStranieri stranieriMan = super.getSchedaJsonStranieri(schedaId);
		if (stranieriMan == null)
			stranieriMan = initManStranieri();
		return stranieriMan;
		//segnalato.setStranieriMan(stranieriMan);
	}

	private IAbitazione loadSchedaJsonAbitazione(Long schedaId) throws Exception {
		IAbitazione abitazioneMan = super.getSchedaJsonAbitazione(schedaId);
		if (abitazioneMan == null)
			abitazioneMan = initManAbitazione();
		return abitazioneMan;
		//segnalato.setAbitazioneMan(abitazioneMan);
	}
	
	private IFamConviventi loadSchedaJsonFamConviventi(Long schedaId) throws Exception {
		IFamConviventi famConviventiMan = super.getSchedaJsonFamConviventi(schedaId);
		if (famConviventiMan == null)
			famConviventiMan = initManFamConviventi();
		return famConviventiMan;
		//segnalato.setFamConviventiMan(famConviventiMan);
	}



	public NuovaSchedaWizard() {
		
		this.userSearchBean = new UserSearchBean(this);
				
		this.accesso = new Accesso();
		this.accessoOrig = new Accesso();
		this.segnalante = new Segnalante();
		
		this.segnalato = new Segnalato();
		this.stranieriMan = initManStranieri();
		this.abitazioneMan = initManAbitazione();
		this.famConviventiMan = initManFamConviventi();
		
		this.riferimento = new PersonaRiferimento();
		this.motivazione = new Motivazione();
		
		this.intervento = new Interventi();
		this.privacySottoscrivi=false;
		//this.privacyDate=new Date();
		
		this.diarioSociale = new DiarioSociale();
	
		notaDiarioPrivata = new Nota();
		notaDiarioPrivata.setPubblica(false);
		notaDiarioPrivata.setData(new Date());
		
		notaDiarioPubblica = new Nota();
		notaDiarioPubblica.setPubblica(true);
		notaDiarioPubblica.setData(new Date());
		this.tipoScheda = null;

		this.operatori = new ArrayList<SelectItem>();
		this.sedi = new ArrayList<SelectItem>();
		this.modalita = new ArrayList<String>();
		this.interlocutori = new ArrayList<SelectItem>();
		this.relazioni = new ArrayList<SelectItem>();
		this.inviatiDa = new ArrayList<SelectItem>();
		this.settori = new ArrayList<SelectItem>();	//SISO-346
		this.statiCivili = new ArrayList<String>();
		this.cittadinanze = new ArrayList<SelectItem>();
		this.cittadinanzeAcquisite = new ArrayList<SelectItem>();
		this.residenze = new ArrayList<String>();
		this.medici = new ArrayList<String>();
		// this.uffici = new ArrayList<String>();
		this.assistenti = new ArrayList<String>();
		this.accessi = new ArrayList<String>();
		this.parentele = new ArrayList<String>();
		this.motivazioni = new ArrayList<SelectItem>();
		this.motivazioni1 = new ArrayList<SelectItem>();
		this.motivazioni2 = new ArrayList<SelectItem>();
		this.motivazioni3 = new ArrayList<SelectItem>();
		this.motivazioni5 = new ArrayList<SelectItem>();
		this.motivazioni4 = new ArrayList<SelectItem>();
		this.interventi = new ArrayList<SelectItem>();
		this.tipiScheda = new ArrayList<SelectItem>();
		this.interventiEconomiciTipi = new ArrayList<String>();
		this.categorieSociali = new ArrayList<SelectItem>();
		this.motiva = new ArrayList<String>();
		this.interventiEconomici = new InterventiView();

		this.statoCartella = null;
		this.categoriaSociale = null;

		this.schedaPrecedente = null;
		this.nucleoFamiliare = new ArrayList<Soggetto>();
		

		serviziRichiestiInterventiCustomBean = new ServiziRichiestiInterventiCustomBean(this, scheda);  //SISO-438 
		
		
		// Modifica SISO Umbria
		PuntoContatto pCont = this.getPreselectedPContatto();
		if (pCont != null){
			PuntoContatto copia;
			try {
				copia = (PuntoContatto)SerializationUtils.clone(pCont);
				accesso.setPuntoContatto(copia);
			} catch (Exception e) {
				accesso.setPuntoContatto(pCont);
				logger.error("Errore clonazione PuntoContatto", e);
			}
			
		}

		String selectedScheda = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(SCHEDA_KEY);

		//Record presente in ssAnagrafica
		String selectedSoggetto = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(SOGGETTO_KEY); 
		
		String selectedCf = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(CF_KEY);
		
		//Seleziono record da anag.sanitaria
		String idAnagSanitaria = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(ANAG_SAN_KEY); 
		
		Boolean creaNuovaSchedaDaEsistente = null;
		Boolean riceviSchedaDaRemoto = null;
		String zsRemoto= null;
		try {
			String creaNuova = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(CREA_NUOVA_SCHEDA);
			creaNuovaSchedaDaEsistente = creaNuova!=null ? Boolean.valueOf(creaNuova.toLowerCase()) : Boolean.FALSE;
		} catch (Exception e) {	
			logger.error(e.getMessage(), e);
		}
		
		try {
			String riceviScheda = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(RICEVI_SCHEDA);
			zsRemoto = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(ZONA_SOCIALE_KEY); 
			riceviSchedaDaRemoto = zsRemoto!=null ? Boolean.valueOf(riceviScheda.toLowerCase()) : Boolean.FALSE;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		indietroButtonLink = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("previousPage");
		
		try {
			PersonaFindResult p = null;
			
			if (creaNuovaSchedaDaEsistente == null || creaNuovaSchedaDaEsistente == false) {				
				if (selectedScheda != null) { 
					if (riceviSchedaDaRemoto != null && riceviSchedaDaRemoto && zsRemoto!=null){
						initFromSelectedReceivedScheda(new Long(selectedScheda), zsRemoto);
					}else initFromSelectedScheda(new Long(selectedScheda));
				} else{

//SISO-438					serviziRichiestiInterventiCustomBean.initManJsonServiziRichiesti(scheda, VER_MAX); 

					if (selectedSoggetto != null) initFromSelectedSoggetto(new Long(selectedSoggetto));
					else if (selectedCf != null)       initFromSelectedCf(selectedCf);
					else if (idAnagSanitaria != null) {
						p = this.getPersonaDaAnagSanitaria(idAnagSanitaria);
						if (p != null)
							segnalato.initFromAnagraficaSanitaria(p);
					} 
				}

			} 
			
			else {
				
				//serviziRichiestiInterventiCustomBean.initManJsonServiziRichiesti(scheda, VER_MAX); //SISO-438
				
				//Matteo Leandri 04/07/2016
				//sono arrivato qui passando come parametro la creazione di una nuova scheda
				
				//con l'id della scheda a disposizione recupero nome cognome e codice fiscale
				SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();

				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
				dto.setObj(new Long(selectedScheda));

				schedaPrecedente = schedaService.readScheda(dto);
				SsSchedaSegnalato ssSchedaSegnalato = readSegnalatoById(schedaPrecedente.getSegnalato());
				//Ricerco per codice fiscale in anagrafica comune
				SitDPersona sitDPersona = readSoggettoFromAnagrafeByCf(ssSchedaSegnalato.getAnagrafica().getCf());
				
				//avendo a disposizione nome, cognome e cf recupero i dati dall'anagrafe del comune e dall'anagrafe sanitaria
				if (sitDPersona != null) {
					if (sitDPersona.getDataMor() != null) {
						addWarning("policy.error","Il soggetto selezionato è deceduto il "+ddMMyyyy.format(sitDPersona.getDataMor()));
						FacesContext.getCurrentInstance().getExternalContext().redirect(HOME);
					} else {
						initFromSelectedCf(ssSchedaSegnalato.getAnagrafica().getCf());
					}
				} else {
					//cerco in anagrafe sanitaria
					p = this.getPersonaDaAnagSanitaria(ssSchedaSegnalato.getAnagrafica().getCognome(), ssSchedaSegnalato.getAnagrafica().getNome(), ssSchedaSegnalato.getAnagrafica().getCf());
					if (p != null) {
						if (p.getDataMor() != null) {
							addWarning("policy.error","Il soggetto selezionato è deceduto il "+ddMMyyyy.format(p.getDataMor()));
							FacesContext.getCurrentInstance().getExternalContext().redirect(HOME);
						} else {
							segnalato.initFromAnagraficaSanitaria(p);
						}
					} else {
						//se non trova il cf in ANAGRAFE COMUNALE o in ANAGRAFE SANITARIA cerca tra le SCHEDE SS CREATE e VALIDE (=soggetto NON RESIDENTE NEL COMUNE)
						dto = new it.webred.ss.ejb.dto.BaseDTO();
						fillUserData(dto);
						dto.setObj(ssSchedaSegnalato.getAnagrafica().getCf());
						SsAnagrafica ssAnagrafica = schedaService.readAnagraficaByCf(dto) ;
						if (ssAnagrafica != null)
							segnalato.getAnagrafica().initFromAnagrafica(ssAnagrafica, true);
					}
				}
			}
			
			initStatoPrivacy();
			if(segnalato.getAnagrafica()!=null && segnalato.getAnagrafica().getCodiceFiscale()!=null && segnalato.getAnagrafica().getIdExtAnagrafeEnte()==null)
				segnalato.getAnagrafica().setIdExtAnagrafeEnte(getIdExtSoggetto(segnalato.getAnagrafica().getCodiceFiscale()));
			
			// Valorizzo il medico
			if (segnalato.getMedico() == null || "".equals(segnalato.getMedico().trim())) {
				if(scheda==null || !scheda.getCompleta()){ //se è null è una nuova creazione
					if (p == null)
						p = getPersonaDaAnagSanitaria(segnalato.getAnagrafica().getCognome(), segnalato.getAnagrafica().getNome(), segnalato.getAnagrafica().getCodiceFiscale());
					
					
					if (p != null){
						if(segnalato.getTesseraSanitaria()==null || segnalato.getTesseraSanitaria().isEmpty()) segnalato.setTesseraSanitaria(p.getNumeroTesseraSanitaria());
						impostaMedicoPersona(p.getCodiceRegionaleMedico());
					}
				}
			}
			
			if(selectedScheda==null)
				loadUltimaSchedaSoggettoUfficio(segnalato.getAnagrafica().getCodiceFiscale(), accesso.getPuntoContatto());
			abilitaLoadPrecedente = (selectedScheda==null || creaNuovaSchedaDaEsistente) && this.schedaPrecedente!=null;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");
		}

		// set operatore selezionato
		if (accesso.getOperatore() == null)
			accesso.setOperatore(getUserNameOperatore());
	}
	
	
	public boolean isDisabilitaAnnullaInvio(){
		boolean disabilita=false;
	
		disabilita=(schedaInfoInvio==null || schedaInfoInvio.getRicezData()!=null || schedaInfoInvio.getOrigDataInvio()==null );
		
		return disabilita;
	}
	
	public boolean isSchedaInviataRicevuta(){
		return schedaInfoInvio!=null && schedaInfoInvio.getRicezData()!=null && schedaInfoInvio.getOrigDataInvio()!=null;
	}
		
	public void goBack() {
		try {
			if (indietroButtonLink == null || indietroButtonLink.isEmpty())
				indietroButtonLink = HOME;
			FacesContext.getCurrentInstance().getExternalContext().redirect(indietroButtonLink);
		} catch (IOException e) {
			logger.error(e);
		}
	}
	
	public void onChangeCodiceFiscale(){
		/*Cambio il codice fiscale, solo quando è una nuova scheda creata da zero*/
		loadUltimaSchedaSoggettoUfficio(segnalato.getAnagrafica().getCodiceFiscale(), accesso.getPuntoContatto());
		abilitaLoadPrecedente = this.schedaPrecedente!=null;
		if(abilitaLoadPrecedente)
			this.addInfo("caricamento.schedaPrecedenteSuCF", "per caricare i dati dall'ultima scheda inserita, premere il pulsante 'Importa da precedente'");
		
		initStatoPrivacy();
	}
	
	public Long getTipoScheda() {
		return tipoScheda;
	}

	public void setTipoScheda(Long tipo) {
		this.tipoScheda = tipo;
	}
	
	
	public void onChangeTipoScheda(){
		
		resetCatSociale();
		
//		resetValoriInvioScheda();
//		
//		if (isVisPanelInvioUfficio() || this.isVisPanelInvioEnte() ) {
//			if(isVisPanelInvioUfficio()){
//				// ho scelto invio ad altro ufficio, quindi seleziono la mia zona
//				// come OrganizzazioneDiZonaId
//				PuntoContatto pContMan = this.getAccesso().getPuntoContatto();
//				Organizzazione currOrgInviante = pContMan.getOrganizzazione();
//				setOrganizzazioneDiZonaId(currOrgInviante.getId());
//				// inizializzo la lista degli uffici
//				getUfficiOrganizzazione();
//			}
//			initInvioSchedaGeneric();
//		}else
//			schedaInfoInvio=null;
	}

	public Nota getNotaDiarioPubblica() {
		return notaDiarioPubblica;
	}

	public void setNotaDiarioPubblica(Nota notaDiarioPubblica) {
		this.notaDiarioPubblica = notaDiarioPubblica;
	}

	public Nota getNotaDiarioPrivata() {
		return notaDiarioPrivata;
	}

	public void setNotaDiarioPrivata(Nota notaDiarioPrivata) {
		this.notaDiarioPrivata = notaDiarioPrivata;
	}

	public DiarioSociale getDiarioSociale() {
		return this.diarioSociale;
	}

	public Interventi getIntervento() {
		return intervento;
	}

	public void setIntervento(Interventi intervento) {
		this.intervento = intervento;
	}

	public List<SelectItem> getTipiScheda() {
		if (tipiScheda.isEmpty()) 
			this.loadTipiScheda();
		return tipiScheda;
	}
	
    private void loadTipiScheda(){			
			try {
				
				tipiScheda = new ArrayList<SelectItem>();
				
				SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();

				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
				this.tipiSchedaModel = schedaService.readTipiScheda(dto);
			 	
				
				SelectItemGroup comunicazioneGroup = new SelectItemGroup("Comunicazione a cartella");
				List<SelectItem> lst_comunicazioneGroup = new ArrayList<SelectItem>();
				
//				SelectItemGroup invioGroup = new SelectItemGroup("Invio ad altri");
//				List<SelectItem> lst_invioGroup = new ArrayList<SelectItem>();
				
				SelectItemGroup sempliciGroup = new SelectItemGroup("Interventi semplici");
				List<SelectItem> lst_sempliciGroup = new ArrayList<SelectItem>();
				
				for(SsTipoScheda tipo: this.tipiSchedaModel)
				{
					if (this.isComune() || !tipo.getSolo_comune())
					{
						if(tipo.getPresa_in_carico()){
							SelectItem si = new SelectItem(tipo.getId(),tipo.getTipo());
							si.setDisabled(this.isPrivacyAnonimo());
							lst_comunicazioneGroup.add(si);
						}
//						else if(tipo.getTipo().toLowerCase().contains("invio"))
//							lst_invioGroup.add(new SelectItem(tipo.getId(),tipo.getTipo()));
						else
							lst_sempliciGroup.add(new SelectItem(tipo.getId(),tipo.getTipo()));
					}
					
				}
				
				comunicazioneGroup.setSelectItems(lst_comunicazioneGroup.toArray(new SelectItem[lst_comunicazioneGroup.size()]));
//				invioGroup.setSelectItems(lst_invioGroup.toArray(new SelectItem[lst_invioGroup.size()]));
				sempliciGroup.setSelectItems(lst_sempliciGroup.toArray(new SelectItem[lst_sempliciGroup.size()]));
				
				tipiScheda.add(comunicazioneGroup);
//				tipiScheda.add(invioGroup);
				tipiScheda.add(sempliciGroup);
				
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				addError("lettura.error");
			}
	}

	
	public List<SelectItem> getTipiSchedaTips() {
		if(tipiSchedaTips==null || tipiSchedaTips.isEmpty())
		{
			this.tipiSchedaTips=new ArrayList<SelectItem>();
			getTipiScheda();
			for (SsTipoScheda tipo : this.tipiSchedaModel) {
				if (this.isComune() || !tipo.getSolo_comune())
					tipiSchedaTips.add(new SelectItem(tipo.getTipo(), tipo.getTooltip()));
			}
		}
		return tipiSchedaTips;
	}

	public void setTipiScheda(List<SelectItem> tipischeda) {
		this.tipiScheda = tipischeda;
	}

	public void setInterventi(List<SelectItem> interventi) {
		this.interventi = interventi;
	}
	
	public Motivazione getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(Motivazione motivazione) {
		this.motivazione = motivazione;
	}

	public List<SelectItem> getMotivazioni() {
		if (motivazioni.isEmpty()) {
			Long n = (long) 0;
			readMotivazioniFromDB(motivazioni, n);
		}
		return motivazioni;
	}

	public void setMotivazioni(List<SelectItem> motivazioni) {
		this.motivazioni = motivazioni;
	}

	public List<SelectItem> getInterventi() {
		readInterventiFromDB();
		return interventi;
	}

	public List<String> getMedici() {
		return medici;
	}

	public void setMedici(List<String> medici) {
		this.medici = medici;
	}

	public List<SelectItem> getOperatori() {
		if (operatori.isEmpty()) {
			try {
				UserService bean = (UserService) ClientUtility.getEjbInterface("AmProfiler", "AmProfilerEjb", "UserServiceBean");

					if (isEnteEsterno()) {
						String ente = getUserOrganizationWithoutId();
						List<AmUser> utenti = bean.getUsersByEnteInizialiGruppo(getCurrentEnte(), ente);
						for (AmUser u : utenti)
							operatori.add(new SelectItem(u.getName(), this.getCognomeNomeUtente(u.getName())));
					} else {
						List<AmUser> utenti = bean.getUsersByEnteInizialiGruppo(getCurrentEnte(), "SSOCIALE_OPERATORI");
						if(!utenti.isEmpty()){
							SelectItemGroup parGroup = new SelectItemGroup("Operatori");
							List<SelectItem> lst = new ArrayList<SelectItem>();
							for (AmUser u : utenti)
								lst.add(new SelectItem(u.getName(), this.getCognomeNomeUtente(u.getName())));
							parGroup.setSelectItems(lst.toArray(new SelectItem[lst.size()]));
							operatori.add(parGroup);
						}
						
						utenti = bean.getUsersByEnteInizialiGruppo(getCurrentEnte(), "SSOCIALE_RESPONSABILI");
						if(!utenti.isEmpty()){
							SelectItemGroup parGroup = new SelectItemGroup("Responsabili");
							List<SelectItem> lst = new ArrayList<SelectItem>();
							for (AmUser u : utenti)
								lst.add(new SelectItem(u.getName(), this.getCognomeNomeUtente(u.getName())));
							parGroup.setSelectItems(lst.toArray(new SelectItem[lst.size()]));
							operatori.add(parGroup);
						}
						
					}

			} catch (NamingException e) {
				logger.error(e);
			}
		}
		return operatori;
	}

	public void setOperatori(List<SelectItem> operatori) {
		this.operatori = operatori;
	}

	public List<SelectItem> getSedi() {
		if (sedi.isEmpty()) {
			readUfficiFromDB(sedi);
		}
		return sedi;
	}

	public void setSedi(List<SelectItem> sedi) {
		this.sedi = sedi;
	}

	public List<String> getAssistenti() {
		return assistenti;
	}

	public void setModalita(List<String> modalita) {
		this.modalita = modalita;
	}

	public List<String> getModalita() {
		if (modalita.isEmpty()) {
			modalita.add("Di Persona");
			modalita.add("e-mail");
			modalita.add("Rilevato a domicilio");
			modalita.add("Segnalazione scritta");
			modalita.add("Segnalazione telefonica");
			
		}
		return modalita;
	}

	public void setInterlocutori(List<SelectItem> interlocutori) {
		this.interlocutori = interlocutori;
	}
	
	//INIZIO SISO-346

//	public List<String> getInterlocutori() {
//		if (interlocutori.isEmpty()) {
//			interlocutori.add("Autorità giudiziaria");
//			interlocutori.add("Istituzionale");
//			interlocutori.add("Utente");
//			interlocutori.add("Altro soggetto");
//		}
//		return interlocutori;
//		
//	}
	
	public List<SelectItem> getInterlocutori() {
		if (interlocutori.isEmpty()) {
			interlocutori.add(new SelectItem(Scheda.Accessi.Interlocutori.UTENTE, Scheda.Accessi.Interlocutori.UTENTE));  
			interlocutori.add(new SelectItem(Scheda.Accessi.Interlocutori.PARENTE_O_CONOSCENTE, Scheda.Accessi.Interlocutori.PARENTE_O_CONOSCENTE) ); 
			interlocutori.add(new SelectItem(Scheda.Accessi.Interlocutori.ORGANIZZAZIONE, Scheda.Accessi.Interlocutori.ORGANIZZAZIONE) ); 
			interlocutori.add(new SelectItem(Scheda.Accessi.Interlocutori.ALTRO_SOGGETTO, Scheda.Accessi.Interlocutori.ALTRO_SOGGETTO));  
			 
			
			SelectItem item = new SelectItem(Scheda.Accessi.Interlocutori.ATTIVITA_GIUDIZIARIA, Scheda.Accessi.Interlocutori.ATTIVITA_GIUDIZIARIA); 
			if (accesso.getInterlocutore()==null || !accesso.getInterlocutore().equals( Scheda.Accessi.Interlocutori.ATTIVITA_GIUDIZIARIA)) {
				item.setDisabled(true); 
			}
			interlocutori.add(item);
			item = new SelectItem(Scheda.Accessi.Interlocutori.ISTITUZIONALE, Scheda.Accessi.Interlocutori.ISTITUZIONALE) ;  
			if (accesso.getInterlocutore()==null || !accesso.getInterlocutore().equals( Scheda.Accessi.Interlocutori.ISTITUZIONALE)) {
				item.setDisabled(true); 
			}
			interlocutori.add(item); 

		}
		return interlocutori;
		
	}
	
	//FINE SISO-346

	public void setRelazioni(List<SelectItem> relazioni) {
		this.relazioni = relazioni;
	}

	public List<SelectItem> getRelazioni() {
		if (relazioni==null || relazioni.isEmpty()) {
			relazioni = new ArrayList<SelectItem>();
			
			CeTBaseObject bo = new CeTBaseObject();
			fillUserData(bo);
			AccessTableConfigurazioneSessionBeanRemote confService;
			try{
				confService = this.getCsConfigurazioneService();
				List<CsTbTipoRapportoCon> lstParenti =    confService.getTipoRapportoConParenti(bo);
				List<CsTbTipoRapportoCon> lstConoscenti = confService.getTipoRapportoConConoscenti(bo);
				
				if(!lstParenti.isEmpty()){
					SelectItemGroup parGroup = new SelectItemGroup("Parenti");
					List<SelectItem> lst = new ArrayList<SelectItem>();
					for(CsTbTipoRapportoCon rapporto : lstParenti)
						lst.add(new SelectItem(rapporto.getId(),rapporto.getDescrizione()));
					
					parGroup.setSelectItems(lst.toArray(new SelectItem[lst.size()]));
					relazioni.add(parGroup);
				}
				
				if(!lstConoscenti.isEmpty()){
					SelectItemGroup conGroup = new SelectItemGroup("Conoscenti");
					List<SelectItem> lst = new ArrayList<SelectItem>();
					for(CsTbTipoRapportoCon rapporto : lstConoscenti)
						lst.add(new SelectItem(rapporto.getId(),rapporto.getDescrizione()));
					
					conGroup.setSelectItems(lst.toArray(new SelectItem[lst.size()]));
					relazioni.add(conGroup);
				}
			} catch (NamingException e) {
				logger.error(e);
				addError("lettura.error","Non è stato possibile recuperare la lista delle relazioni");
			}
			  
		}
		return relazioni;
	}

	public List<SelectItem> getInviatiDa() {
		if (inviatiDa.isEmpty()) {
			AccessTableConfigurazioneSessionBeanRemote bean;
			try {
				bean = this.getCsConfigurazioneService();
				inviatiDa = new ArrayList<SelectItem>();
				CeTBaseObject bo = new CeTBaseObject();
				fillUserData(bo);
				List<CsOSettore> lst = bean.getSettoreAll(bo);
				
				loadListaSettoriInviante(lst);
				
			} catch (NamingException e) {
				logger.error(e);
			}
		}

		return inviatiDa;
	}
	
	private void loadListaSettoriInviante(List<CsOSettore> listaSettori) {
		
		inviatiDa = new ArrayList<SelectItem>();
		HashMap<Long, List<CsOSettore>> mappaSettori = new HashMap<Long,List<CsOSettore>>();
		if(!listaSettori.isEmpty()){
			for(CsOSettore s : listaSettori){
				 List<CsOSettore> lst = mappaSettori.get(s.getCsOOrganizzazione().getId());
				 if(lst==null) lst = new ArrayList<CsOSettore>();
				 
				 if(s.getFlgInviante()){
					 lst.add(s);
					 mappaSettori.put(s.getCsOOrganizzazione().getId(), lst);
				 }
			}
			
			inviatiDa = loadListaSettoriGroup(mappaSettori);
			
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

	private void impostaMedicoPersona(String codRegionaleMedico) throws NamingException {
	  try{	
		if (codRegionaleMedico != null && !codRegionaleMedico.trim().isEmpty()) {
			AccessTableMediciSessionBeanRemote bean = (AccessTableMediciSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableMediciSessionBean");
			it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
			this.fillUserData(dto);
			dto.setObj(codRegionaleMedico);
			CsCMedico medico = bean.getMedicoByCodReg(dto);
			if (medico == null) {// se il medico non è in CsCMedici, scarico i
									// suoi dati da CsVMedici e lo carico su
									// CsCMedici
				// CsVMedico
				// medicoRegione=bean.getMedicoRegioneByCodiceRegionale(dto);
				URL url = getMediciWebServiceWSDLLocation();
				MedicoClient mc = null;
				Medico medicoRegione = null;
				if (url != null) {
					mc = new MedicoClient(url);
					medicoRegione = mc.getMedicoByCodiceRegionale(codRegionaleMedico);
					if (medicoRegione != null) {
						String cognomeNewMedico = medicoRegione.getCognome();
						String nomeNewMedico = medicoRegione.getNome();
						String codiceFiscale = medicoRegione.getCodiceFiscale();
						CsCMedico newMedico = new CsCMedico();
						newMedico.setAbilitato("1");
						newMedico.setCognome(cognomeNewMedico);
						newMedico.setNome(nomeNewMedico);
						newMedico.setCodiceRegionale(codRegionaleMedico);
						it.webred.cs.csa.ejb.dto.BaseDTO dto2 = new it.webred.cs.csa.ejb.dto.BaseDTO();
						this.fillUserData(dto2);
						dto2.setObj(newMedico);
						medico = bean.addNewMedicoFromAnagReg(dto2);
					}
				}
			}
			if (medico != null) {// se ho trovato il medico sulla vista e l'ho
									// aggiunto a CsCMedici, lo setto sulla
									// scheda
				String denominazione = (medico.getCognome() == null ? "" : medico.getCognome()).trim();
				String nome = (medico.getNome() == null ? "" : medico.getNome()).trim();
				if (!nome.equals("")) {
					if (!denominazione.equals("")) {
						denominazione += " ";
					}
					denominazione += nome;
				}
				segnalato.setMedico(medico.getCognome() + " " + medico.getNome());
			}
		}
	  }catch(Exception e){
		  logger.error(e.getMessage(), e);
		  addError("lettura.error","Non è stato possibile recuperare il nominativo del medico di base da Anagrafe Sanitaria Regionale");
	  }
	}

	public List<String> getStatiCivili() {
		if (statiCivili.isEmpty()) {
			List<SelectItem> list = getStatiCiviliSelectItem();

			if (list != null) {
				for (SelectItem item : list) {
					statiCivili.add(item.getLabel());
				}
			}
		}
		return statiCivili;
	}
	
	public void setStatiCivili(List<String> statiCivili) {
		this.statiCivili = statiCivili;
	}
	
	public List<SelectItem> getStatiCiviliSelectItem() {
		if (statiCiviliSelectItem == null || statiCiviliSelectItem.isEmpty()) {
			try {
				statiCiviliSelectItem = new ArrayList<SelectItem>();
				AccessTableConfigurazioneSessionBeanRemote bean = this.getCsConfigurazioneService();
				CeTBaseObject cet = new CeTBaseObject();
				fillUserData(cet);
				List<CsTbStatoCivile> list = bean.getStatoCivile(cet);

				if (list != null) {
					for (CsTbStatoCivile stato : list) {
						statiCiviliSelectItem.add(new SelectItem(stato.getCod(), stato.getDescrizione()));
					}
				}
			} catch (NamingException e) {
				addError("Errore recupero valori Stati Civili");
				logger.error(e);
			}
		}
		return statiCiviliSelectItem;
	}
	
	public void setStatiCiviliSelectItem(List<SelectItem> statiCiviliSelectItem) {
		this.statiCiviliSelectItem = statiCiviliSelectItem;
	}

	public List<SelectItem> getCittadinanze() {
		if (cittadinanze.isEmpty()) {
			try {
				AccessTableNazioniSessionBeanRemote bean = (AccessTableNazioniSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableNazioniSessionBean");
				List<String> beanLstCittadinanze = bean.getCittadinanze();
				if (beanLstCittadinanze != null) {
					for (String cittadinanza : beanLstCittadinanze) {
						// in AM_TAB_NAZIONI il campo NAZIONALITA ha lunghezza
						// 500, in CS_A_SOGGETTO il campo CITTADINANZA ha
						// lunghezza 255
						if (cittadinanza.length() > 255) {
							cittadinanza = cittadinanza.substring(0, 252) + "...";
						}
						cittadinanze.add(new SelectItem(cittadinanza, cittadinanza));
					}
				}
			} catch (NamingException e) {
				this.addError("Errore recupero valori Cittadinanze");
				logger.error("getCittadinanze", e);
			}
		}

		return cittadinanze;
	}

	public void setCittadinanze(List<SelectItem> cittadinanze) {
		this.cittadinanze = cittadinanze;
	}

	public List<String> getResidenze() {
		return residenze;
	}

	public void setResidenze(List<String> residenze) {
		this.residenze = residenze;
	}

	public List<String> getAccessi() {
		if (accessi.isEmpty()) {
			accessi.add(DataModelCostanti.Scheda.Accessi.MOTIVO_DECRETO); 
			accessi.add(DataModelCostanti.Scheda.Accessi.MOTIVO_INVIATO_DA);
			accessi.add(DataModelCostanti.Scheda.Accessi.MOTIVO_SPONTANEO);
			accessi.add(DataModelCostanti.Scheda.Accessi.MOTIVO_CONVOCATO);
		}
		return accessi;
	}

	public void setAccessi(List<String> accessi) {
		this.accessi = accessi;
	}

	public void setRiferimento(PersonaRiferimento riferimento) {
		this.riferimento = riferimento;
	}

	public List<String> getParentele() {
		return parentele;
	}

	public void setParentele(List<String> parentele) {
		this.parentele = parentele;
	}

	public Accesso getAccesso() {
		return accesso;
	}

	public void setAccesso(Accesso accesso) {
		this.accesso = accesso;
	}

	public Segnalante getSegnalante() {
		return segnalante;
	}

	public void setSegnalante(Segnalante segnalante) {
		this.segnalante = segnalante;
	}

	public Segnalato getSegnalato() {
		return segnalato;
	}
	

	public void setSegnalato(Segnalato segnalato) {
		this.segnalato = segnalato;
	}

	public boolean isPropostaPresaInCarico(Long idTipo) {
		try {
			SsSchedaSessionBeanRemote schedaService =this.getSsSchedaService();

			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(idTipo);
			SsTipoScheda tipo = schedaService.readTipoSchedaById(dto);
			return tipo!=null ? tipo.getPresa_in_carico() : false;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("salva.error");

			return false;
		}
	}


	private boolean isSchedaCompleta() {
		try {
			SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();
			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(scheda.getId());
			return schedaService.isSchedaCompleta(dto);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("salva.error");

			return false;
		}
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public String onFlowProcess(FlowEvent event) {
		logger.info("Current wizard step:" + event.getOldStep());
		logger.info("Next step:" + event.getNewStep());

		try {

			if (skip) {
				skip = false; // reset in case user goes back
				return "esterni";
			} else {
				if (!backButtonPressed(event)) {
					// save current tab into DB

					boolean saved = true;
					if (event.getOldStep().equals(ACCESSO_TAB))
						saved = saveAccessoIntoDB(true);
					else if (event.getOldStep().equals(SEGNALANTE_TAB))
						saved = saveSegnalanteIntoDB(true);
					else if (event.getOldStep().equals(SEGNALATO_TAB))
						saved = saveSegnalatoIntoDB(true);
					else if (event.getOldStep().equals(RIFERIMENTO_TAB))
						saved = saveRiferimentoIntoDB();
					else if (event.getOldStep().equals(MOTIVAZIONE_TAB))
						saved = saveMotivazioneIntoDB(true);
					else if (event.getOldStep().equals(INTERVENTI_TAB))
						saved = saveInterventiIntoDB(true);

					if (saved)
						return event.getNewStep();
					else
						return event.getOldStep();
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return event.getNewStep();
	}
	
	public void salvaTabAccesso(){
		Long idScheda = scheda!=null ? scheda.getId() : null;
		logger.debug("Salva Tab Accesso - ID_SCHEDA:"+ idScheda);
		this.saveAccessoIntoDB(true);
	}
	
	public void salvaTabSegnalante(){
		Long idScheda = scheda!=null ? scheda.getId() : null;
		logger.debug("Salva Tab Segnalante - ID_SCHEDA:"+ idScheda);
		this.saveSegnalanteIntoDB(true);
	}

	public void salvaTabUtente(){
		Long idScheda = scheda!=null ? scheda.getId() : null;
		logger.debug("Salva Tab Segnalato - ID_SCHEDA:"+ idScheda);
		this.saveSegnalatoIntoDB(true);
	}
	public void salvaTabRiferimento(){
		Long idScheda = scheda!=null ? scheda.getId() : null;
		logger.debug("Salva Tab Riferimento - ID_SCHEDA:"+ idScheda);
		this.saveRiferimentoIntoDB();
	}
	public void salvaTabBisogni(){
		Long idScheda = scheda!=null ? scheda.getId() : null;
		logger.debug("Salva Tab Bisogni - ID_SCHEDA:"+ idScheda);
		this.saveMotivazioneIntoDB(true);
	}
	public void salvaTabServiziRichiesti(){
		Long idScheda = scheda!=null ? scheda.getId() : null;
		logger.debug("Salva Tab Servizi Richiesti - ID_SCHEDA:"+ idScheda);
		this.saveInterventiIntoDB(true);
	}
	
	public void salvaSchedaCompletaUDC() {
		Long idScheda = scheda!=null ? scheda.getId() : null;
		if (validaPresaInCarico() /*&& this.validaInvioAltroEnteUfficio()*/){
			
			logger.debug("SalvaSchedaCompletaUDC - ID_SCHEDA:"+idScheda);
			boolean cartella = true;
		
			try {
				
				AccessTableSchedaSegrSessionBeanRemote schedaSegrService =
						(AccessTableSchedaSegrSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSegrSessionBean");
				
				AccessTableAlertSessionBeanRemote alertService=
						(AccessTableAlertSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableAlertSessionBean");
				
				
				//Consentire il salvataggio privacy in ogni caso	
				boolean esitoPrivacy = saveConsensoPrivacy();
				boolean schedaInCS = this.isSchedaPicInCS();
				
				boolean modified = isSchedaCompleta();					
	
				boolean saved = setSchedaCompleta(true);
				if (saved) {
	
					if (!modified)
						logAction(write, scheda.getId());
					
					try {
						SchedaSegrDTO cartellaDto = new SchedaSegrDTO();
						fillUserData(cartellaDto);
						cartellaDto.setId(scheda.getId());
						
						String flag = modified ? "M" : "I";
						cartellaDto.setFlgStato(flag);

						// fase di invio scheda se tipoScheda un invio
//						
//						if (isTipoSchedaInvio() && !isSchedaInviata())
//						  cartella = invioScheda();
//						
						// ****** fine fase invio scheda

						//Modifico CS_SS_SCHEDA_SEGR solo se non è presa in carico in CS, altrimenti mando un alert.
						if(!schedaInCS){ 
							if (isPropostaPresaInCarico(tipoScheda)) { 
								if (categoriaSociale != null)
									cartellaDto.setIdCatSociale(categoriaSociale);
								schedaSegrService.saveSchedaSegr(cartellaDto);
							} else
								schedaSegrService.deleteSchedaSegr(cartellaDto);
							
							//Invio alert solo se è nuovo inserimento (non in modifica)
							if(!modified){
								it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
								fillUserData(dto);
								dto.setObj(this.segnalato.getAnagrafica().getCodiceFiscale().toUpperCase());
								dto.setObj2(this.accesso.getPuntoContatto().getOrganizzazione().getId());
								dto.setObj3(DataModelCostanti.TipiAlertCod.UDC);
								alertService.addAlertResponsabileCaso(dto);
							}
						
						}
						
					} catch (Exception e) {
						cartella = false;
						logger.error(e);
					}
	
					if (cartella)
						addInfo("salva.ok");
					else
						addInfo("comunicazione.error");
	
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
					try {
						/*
						 * UfficiTableBean uff = (UfficiTableBean)
						 * this.getBeanReference("ufficiTableBean");
						 * uff.populateUffici();
						 */
						FacesContext.getCurrentInstance().getExternalContext().redirect(HOME);
					} catch (IOException e) {
						logger.error(e);
					}
	
				} else
					addError("salva.error");
				
			} catch (Exception e) {
				logger.error(e);
				addError("salva.error");
			}
		}

	}

	private boolean setSchedaCompleta(boolean completa) {
		try {
			SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();
			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			Object[] param = new Object[] { scheda.getId(), completa, tipoScheda };
			dto.setObj(param);
			schedaService.updateCompletamentoScheda(dto);

			// save note di diario
			saveOrUpdateNotaDiario(notaDiarioPubblica);
			saveOrUpdateNotaDiario(notaDiarioPrivata);

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("salva.error");

			return false;
		}
	}
	
	public void eliminaNota(Nota nota){
		saveOrUpdateNotaDiario(nota);
	}

	public boolean annullaInvio() {
		try {
			rimuoviSchedaInvioBuffer();
			// inizializzo le info sull'invio scheda
			this.schedaInfoInvio = new ArBufferSsInvio();
			// forzo a ricaricare le info
			this.infoSuInvioSchedaRecuperate = false;
			this.recuperaInfoSuInvioScheda(scheda);
			this.initInvioSchedaGeneric();
			return true;

		} catch (Exception e) {
			return false;
		}
	}
	
	private void rimuoviSchedaInvioBuffer() throws NamingException{
		SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();
		BaseDTO dto = new BaseDTO();
		fillUserData(dto);

		dto.setObj(schedaInfoInvio);
		schedaService.annullaInvioScheda(dto);
	}
	
	private boolean resetTipoScheda() {
		boolean result = true;
		this.tipoScheda = null;
		try {
			salvaSchedaCompletaUDC();
		} catch (Exception e) {
			result = false;
		}

		return result;
	}

	private boolean invioScheda() {
		boolean result=false;
		try {
			SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();
			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			ArBufferSsInvio schedaBuffer = new ArBufferSsInvio();

			this.emailDest = this.emailDest!=null && !this.emailDest.isEmpty() ? this.emailDest : null;
			
			// popola l'ARBufferInvio e passalo come object nel dto
			// recupero l'organizzazione destinataria

			schedaBuffer.setDestBelfiore(this.destBelfiore);
			schedaBuffer.setDestZonaSoc(this.destZonaSoc);
			schedaBuffer.setDestUfficio(this.destUfficio != null ? this.destUfficio.getNome() : null);
			schedaBuffer.setEmailDest(this.emailDest);
			schedaBuffer.setEmailSubj(this.emailSubj);
			schedaBuffer.setEmailTxt(this.emailTxt);
			schedaBuffer.setOrigAccessoIneterlocutore(this.origAccessoIneterlocutore);
			schedaBuffer.setOrigAccessoModalita(this.origAccessoModalita);
			schedaBuffer.setOrigAccessoMotivo(this.origAccessoMotivo);
			schedaBuffer.setOrigBelfiore(origBelfiore);
			schedaBuffer.setOrigCognome(origCognome);
			schedaBuffer.setOrigCognomeOperatore(origCognomeOperatore);
			schedaBuffer.setOrigDataAccesso(origDataAccesso);
			schedaBuffer.setOrigDataInvio(new Date());
			schedaBuffer.setOrigDataNascita(origDataNascita);
			schedaBuffer.setOrigDescOrganizzazione(origOrganizzazione!=null ? origOrganizzazione.getNome() : null);
			schedaBuffer.setOrigEmail(origEmail);
			schedaBuffer.setOrigId(origId);
			schedaBuffer.setOrigNome(origNome);
			schedaBuffer.setOrigNomeOperatore(origNomeOperatore);
			schedaBuffer.setOrigNomeUfficio(origUfficio!=null ? origUfficio.getNome() : null);
			schedaBuffer.setOrigPuntoContatto(origPuntoContatto);
			schedaBuffer.setOrigTelefono(origTelefono);
			schedaBuffer.setOrigTipoIntervento(origTipoIntervento);
			String uriService = recuperaAbsoluteURI();
			schedaBuffer.setOrigUriService(uriService);
			schedaBuffer.setOrigZonaSoc(origZonaSoc);
			schedaBuffer.setOrigIdUfficio(origUfficio!=null ? origUfficio.getId() : null);
			schedaBuffer.setDestUfficioId(this.destUfficio != null ? destUfficio.getId() : null);
			schedaBuffer.setOrigIdOrganizzazione(origOrganizzazione!=null ? origOrganizzazione.getId() : null);
			schedaBuffer.setDestOrganizzazioneId(this.destOrganizzazioneId);
			dto.setObj(schedaBuffer);
			
			ArgoBufferManagerSessionBeanRemote argoBufferManager = (ArgoBufferManagerSessionBeanRemote) ClientUtility
					.getEjbInterface("SocioSanitario",
							"SocioSanitario_EJB", "ArgoBufferManagerSessionBean");

			boolean inviataEmail=false;
			boolean inviataScheda=false;
			try {
				String htmlEmailTxt = this.emailTxt.replaceAll("(\r\n|\n)", "<br/>");
				//htmlEmailTxt=StringEscapeUtils.escapeHtml(htmlEmailTxt); 
				
				/*String s1 = StringEscapeUtils.escapeHtml(htmlEmailTxt);
				String s2 = StringEscapeUtils.unescapeHtml(htmlEmailTxt);*/
				
				argoBufferManager.sendSimpleMailFromSISO(this.emailDest, this.emailSubj, htmlEmailTxt);
				inviataEmail=true;
				
			} catch (Exception e) {
				inviataEmail=false;
				logger.error(e.getMessage(), e);
//				addError("invia_email.error");
			}
			
			try {
				schedaService.inviaScheda(dto);
				inviataScheda=true;
			} catch (Exception e) {
				inviataScheda=false;
				logger.error(e.getMessage(), e);
			}
				
			if(inviataScheda){
				result= true;
				if(inviataEmail){
					result= true;
				}
				else
				{
					addError("invia_email.error");
					result= false;
				}
			}
			else
			{
				addError("invia_scheda.error");
				result= false;
			}
			
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("salva.error");

			result= false;
		}
		return result;
	}
	
	
	

	public boolean isSchedaInviata() {
		boolean inviata = false;

		//recuperaInfoSuInvioScheda(this.scheda); Metodo eseguito a valle delle azioni di rimozione/inserimento
		
		if (this.schedaInfoInvio != null
				&& this.schedaInfoInvio.getId() != null
				&& this.schedaInfoInvio.getId() > 0) {
			inviata = true;
		}

		return inviata;
	}

	public void recuperaInfoSuInvioScheda(SsScheda scheda) {
		
		if (!infoSuInvioSchedaRecuperate) {
			
			try {
				SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();

				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
				dto.setObj(scheda.getId());
				dto.setObj2(getZonaSociale());

				List<ArBufferSsInvio> schedeTrovateInvio = schedaService.readSchedeInviateByIdOrigZs(dto);
				// non dovrebbe esserci mai più di un invio per scheda, per sicurezza ordino per data invio descrescente e prendo la più recente
				if (schedeTrovateInvio != null && schedeTrovateInvio.size() > 0) {
					
					this.schedaInfoInvio = schedeTrovateInvio.get(0);

					// cerco l'organizzazione tra le Ar e le Cs
					ArOOrganizzazione currentDestOrganizzazione = readSsArOrganizzazione(this.schedaInfoInvio.getDestOrganizzazioneId());

					if (currentDestOrganizzazione.getBelfiore() == null) {
						// � una altra organizzazione
						this.setOrganizzazioneAltreId(currentDestOrganizzazione.getId());

					} else {
						if (currentDestOrganizzazione.getZona_nome() != null && !currentDestOrganizzazione.getZona_nome().isEmpty()) {
							// � organizzazione di altra zona
							this.setOrganizzazioneAltraZonaId(currentDestOrganizzazione.getId());
						} else {
							// � organizzazione di zona
							this.setOrganizzazioneDiZonaId(currentDestOrganizzazione.getId());
						}
					}

					setUfficioOrganizzazioneId(this.schedaInfoInvio.getDestUfficioId()!=null ? this.schedaInfoInvio.getDestUfficioId():-1);
					// necessario inserimento id in buffer_invio
					// secondo documentazione dovrei trovare ufficio dal nome
					this.emailDest = this.schedaInfoInvio.getEmailDest();
					
					this.emailSubj = this.schedaInfoInvio.getEmailSubj();
					this.emailTxt =  this.schedaInfoInvio.getEmailTxt();
				}
				infoSuInvioSchedaRecuperate = true;
			} catch (NamingException e) {
				logger.error(e.getMessage(), e);
				infoSuInvioSchedaRecuperate = false;
			}

		}
	}

	private String recuperaAbsoluteURI() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		String file = request.getRequestURI();
		String uri = "";
		if (request.getQueryString() != null) {
			file += '?' + request.getQueryString();
		}
		URL reconstructedURL = null;
		try {
			reconstructedURL = new URL(request.getScheme(),
					request.getServerName(), request.getServerPort(), file);
		} catch (MalformedURLException e) {
			logger.error(e);
		}
		if (reconstructedURL != null) {
			uri = request.getServerName();// non serve la porta + ":" + request.getServerPort();
		}
		return uri;
	}
	
	public boolean isTipoPicSs(){
		boolean tipoPicSs = false;
		if(tipoScheda!=null){
			try{
				SsTipoScheda tipo = this.readTipoSchedaFromIdTipoScheda(tipoScheda);
				if(tipo!=null && tipo.getPresa_in_carico()!=null)
					tipoPicSs = tipo.getPresa_in_carico();
			
			} catch (Exception e) {logger.error(e.getMessage(), e);}
		}
		return tipoPicSs;
	}
	
	public void resetCatSociale() {
		if (tipoScheda == null || !this.isTipoPicSs()) 
			categoriaSociale = null;
	}

	private void resetValoriInvioScheda() {
		infoSuInvioSchedaRecuperate=false;
		this.setOrganizzazioneAltraZonaId(-1);
		this.setOrganizzazioneAltreId(-1);
		this.setOrganizzazioneDiZonaId(-1);
		this.setUfficioOrganizzazioneId(-1);
		this.ufficiOrganizzazione = null;
		this.reloadUfficiOrganizzazione = true;
		this.origEmail = null;
		
		this.emailSubj = null;
		this.emailTxt = null;
	}

	private void initInvioDatiAccesso(){
		
		this.origAccessoIneterlocutore = this.accesso.getInterlocutore();
		this.origAccessoModalita = this.accesso.getModalitaAccesso();
		this.origAccessoMotivo = this.accesso.getMotivo();
		this.origDataAccesso = this.accesso.getDataAccesso();
		
		/* Se entro in modifica di una scheda completa/incompleta per inviarla senza aver selezionato il punto di contatto dalla home page, 
		 * recuperando il pCont da contesto potrebbe non essere valorizzato o essere diverso da quello salvato nella SCHEDA_ACCESSO.
		 * Chi è l'inviante? Non dovrebbe essere il punto di contatto che ha fatto l'accesso?
		 * In caso contrario, se non selezionato il PCONT, nel buffer viene inserito il valore NULL e di conseguenza alla ricezione non c'è punto di contatto.*/
		
		//PuntoContatto pContManCurr = getPreselectedPContatto();
		PuntoContatto pContMan = accesso.getPuntoContatto();
		String msgTempalte = pContMan.getUfficio().getTemplateMailInvio();
		this.emailTxt = msgTempalte; // sostituisci le variabili nel testo
										// con i valori di ORIG_ quindi di
										// BUFFER
		
		this.origOrganizzazione = pContMan.getOrganizzazione();
		this.origUfficio = pContMan.getUfficio();
		this.origPuntoContatto = pContMan.getNomePContatto();
		this.origBelfiore = pContMan.getOrganizzazione().getBelfiore();
		this.origTelefono = pContMan.getUfficio().getTel();
		this.origEmail =    pContMan.getUfficio().getEmail();
		
		
		this.emailTxt = completeEmailTxt();
		
		String htmlEmailTxt = this.emailTxt.replaceAll("(\r\n|\n)", "<br/>");
		logger.debug(htmlEmailTxt);
		
		String s1 = StringEscapeUtils.escapeHtml(htmlEmailTxt);
		String s2 = StringEscapeUtils.unescapeHtml(htmlEmailTxt);
	
		logger.debug(s1);
		logger.debug(s2);
	}
	
	private void initInvioDatiSegnalato(){
		this.origDataNascita = this.segnalato.getAnagrafica().getDataNascita();
		this.origCognome = this.segnalato.getAnagrafica().getCognome();
		this.origNome = this.segnalato.getAnagrafica().getNome();
		if (this.segnalato != null && this.segnalato.getAnagrafica() != null) {
			this.emailSubj = "Invio scheda accesso "
					+ this.segnalato.getAnagrafica().getCognome() + " "
					+ this.segnalato.getAnagrafica().getNome();
		}
	}
	
	public void initInvioSchedaGeneric() {
		// la scheda da inviare
		this.origId = this.scheda.getId();
			
		if(this.isVisPanelInvioEnte())    origTipoIntervento = "Invio ad altro ente";
		if(this.isVisPanelInvioUfficio()) origTipoIntervento = "Invio ad altro ufficio";
		
		this.origCognomeOperatore = UserBean.getBeanIstance().getCognomeOperatore();
		this.origNomeOperatore =    UserBean.getBeanIstance().getNomeOperatore();

		// recuperaOperatoreDaOperatoriItemList(this.accesso.getOperatore());
		// //verificare altrimenti devo andare su CS_O_OPERATORE_ANAGRAFICA

		this.origZonaSoc = UserBean.getBeanIstance().getZonaSociale();
		
		initInvioDatiSegnalato();
		initInvioDatiAccesso();

	}

	private String completeEmailTxt() { 
		//Testo email configurato per l'ufficio di provenienza
		
		String result = "";
		SimpleDateFormat dtf = new SimpleDateFormat(dateParser);

		result = this.emailTxt.replaceAll("\\b" + TAG_ORIG_COGNOME + "\\b",   (this.origCognome != null ? this.origCognome : "-"));
		result = result.replaceAll("\\b" + TAG_ORIG_NOME + "\\b",             (this.origNome != null ? this.origNome : "-"));
		result = result.replaceAll("\\b" + TAG_ORIG_COGNOME_OPERATORE + "\\b",(this.origCognomeOperatore != null ? this.origCognomeOperatore: "-"));
		result = result.replaceAll("\\b" + TAG_ORIG_NOME_OPERATORE + "\\b",   (this.origNomeOperatore != null ? this.origNomeOperatore: "-"));
		result = result.replaceAll("\\b" + TAG_ORIG_DATA_ACCESSO + "\\b",     (this.origDataAccesso != null ? dtf.format(this.origDataAccesso) : "-"));
		result = result.replaceAll("\\b" + TAG_ORIG_DESC_ORGANIZZAZIONE + "\\b",
				    (this.origOrganizzazione != null
								&& this.origOrganizzazione.getNome() != null
								&& !this.origOrganizzazione.getNome().isEmpty() ? this.origOrganizzazione.getNome() : "-"));
		result = result.replaceAll("\\b" + TAG_ORIG_EMAIL + "\\b",   (this.origEmail != null ? this.origEmail : "-"));
		result = result.replaceAll("\\b" + TAG_ORIG_TELEFONO + "\\b",(this.origTelefono != null ? this.origTelefono : "-"));
		result = result.replaceAll("\\b" + TAG_ORIG_ZONA_SOC + "\\b",(this.origZonaSoc != null ? this.origZonaSoc : "-"));
		result = result.replaceAll("\\b" + TAG_LABEL_UDC + "\\b",    (this.getLabelSegrSociale() != null ? this.getLabelSegrSociale() : "-"));
		return result;
	}

	private boolean saveInterventiIntoDB(boolean validate) {
		boolean salvato = true;
		try {
			boolean schedaInviata = isSchedaInviata();
			boolean validaInvio = true;
			if( isTipoSchedaInvio() && !schedaInviata)
				validaInvio = this.validaInvioAltroEnteUfficio();
			
			if (validate && (!validaServizi() || !validaInvio))
				return false;

			SsSchedaInterventi dataModel = new SsSchedaInterventi();
			intervento.fillModel(dataModel);

			SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();

			BaseDTO dto = new BaseDTO();
			fillUserData(dto);

			boolean reload = false; 
			serviziRichiestiInterventiCustomBean.setIdSchedaUdc(scheda.getId()); //SISO-438 
			
			if (intervento.getId() == null) { // insert
				reload = true;
				scheda.setInterventi(dataModel);
				dto.setObj(scheda);
				scheda = schedaService.saveScheda(dto);
				intervento.setId(scheda.getInterventi().getId());
				dataModel = scheda.getInterventi();

				// save interventi scheda
				saveInterventiScheda(intervento.getInterventi(), dataModel);

			} else { // update
				dto.setObj(dataModel);
				schedaService.updateInterventi(dto);
				schedaService.deleteInterventiScheda(dto);
				saveInterventiScheda(intervento.getInterventi(), dataModel);
			}

			// save intervento economico per enti esterni
			if (isEnteEsterno() && interventoEconomicoTipo!=null && !interventoEconomicoTipo.isEmpty()
					&& !interventoEconomicoImporto.isEmpty()) {
				SsInterventoEconomico ie = new SsInterventoEconomico();
				ie.setData(new Date());

				ie.setErogante(getUserOrganization());
				ie.setImporto(new Long(interventoEconomicoImporto));

				// lettura soggetto e tipo intervento
				dto.setObj(segnalato.getAnagrafica().getId());
				SsAnagrafica ana = schedaService.readAnagraficaById(dto);
				dto.setObj(interventoEconomicoTipo);
				SsInterventoEconomicoTipo tipo = schedaService.readInterventoEconomicoTipoByTipo(dto);
				ie.setSoggetto(ana);
				ie.setTipo(tipo);
				dto.setObj(ie);
				schedaService.writeInterventoEconomico(dto);
			}

			// save note di diario
			saveOrUpdateNotaDiario(notaDiarioPubblica);
			saveOrUpdateNotaDiario(notaDiarioPrivata);

			if (this.isVisPanelStranieri()) {
				salvato &= serviziRichiestiInterventiCustomBean.salvaManJsonServiziRichiesti(salvato, scheda); //SISO-438  
			}
			
			//SISO 659		
			if (isTipoSchedaInvio() && !schedaInviata)
			{
				if( this.validaInvioAltroEnteUfficio())
				{
					Long idScheda = scheda!=null ? scheda.getId() : null;
					logger.debug("INVIO SCHEDA - idScheda: "+idScheda);		
					boolean ok = invioScheda();
					
					if(!ok) 
						throw new Exception("INVIO SCHEDA - Errore idScheda: "+idScheda);
					else{
						this.infoSuInvioSchedaRecuperate = false;
						this.recuperaInfoSuInvioScheda(scheda);
						logger.debug("INVIO SCHEDA - OK idScheda: "+idScheda);
						}
				}
			}
			
			return salvato;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("salva.error");
			salvato = false;
			return salvato;
		}
		finally
		{
			//resetValoriInvioScheda();			
			this.isSchedaInviata();
		}
	}

	private boolean saveConsensoPrivacy() {
		boolean esito=false;
		try {

			if (privacy == null && this.privacySottoscrivi) {
				SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();
				BaseDTO dto = new BaseDTO();
				fillUserData(dto);

				SsSchedaPrivacy sp = new SsSchedaPrivacy();
				SsSchedaPrivacyPK pk = new SsSchedaPrivacyPK();
				pk.setCf(segnalato.getAnagrafica().getCodiceFiscale());
				pk.setOrganizzazioneId(accesso.getPuntoContatto().getOrganizzazione().getId());

				sp.setSchedaId(scheda.getId());
				sp.setDtIns(privacyDate != null ? privacyDate : accesso.getDataAccesso());
				sp.setId(pk);

				dto.setObj(sp);

				schedaService.saveConsensoPrivacy(dto);
				esito = true;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			esito = false;
		}
		
		return esito;
	}

	private boolean saveMotivazioneIntoDB(boolean validate) {
		resetComboMotivazioni();
		try {

			if (validate && !validaBisogni())
				return false;

			SsSchedaMotivazione dataModel = new SsSchedaMotivazione();
			motivazione.fillModel(dataModel);

			SsSchedaSessionBeanRemote schedaService =this.getSsSchedaService();

			BaseDTO dto = new BaseDTO();
			fillUserData(dto);

			if (motivazione.getId() == null) { // insert

				scheda.setMotivazione(dataModel);
				dto.setObj(scheda);
				scheda = schedaService.saveScheda(dto);

				// save generated id after DB insertion
				motivazione.setId(scheda.getMotivazione().getId());
				dataModel.setId(motivazione.getId());

				// save motivazioni scheda
				// saveMotivazioniScheda(motivazione.getClasse0().getMotivazioni(),
				// dataModel);
				for (int i = 1; i <= 5; i++)
					saveMotivazioniScheda(motivazione.getMotivoClasseI(i).getMotiviSelected(), dataModel);

			} else { // update

				dto.setObj(dataModel);
				schedaService.updateMotivazione(dto);
				dto.setObj(dataModel);
				schedaService.deleteMotivazioniScheda(dto);
				// saveMotivazioniScheda(motivazione.getClasse0().getMotivazioni(),
				// dataModel);
				for (int i = 1; i <= 5; i++)
					saveMotivazioniScheda(motivazione.getMotivoClasseI(i).getMotiviSelected(), dataModel);
			}

			// save note di diario
			saveOrUpdateNotaDiario(notaDiarioPubblica);
			saveOrUpdateNotaDiario(notaDiarioPrivata);
			inizializzaMotivazioni();
			return true;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("salva.error");

			return false;
		}
	}

	private void resetComboMotivazioni() {
		this.motivazioni1 = new ArrayList<SelectItem>();
		this.motivazioni2 = new ArrayList<SelectItem>();
		this.motivazioni3 = new ArrayList<SelectItem>();
		this.motivazioni4 = new ArrayList<SelectItem>();
		this.motivazioni5 = new ArrayList<SelectItem>();
	}

	private void inizializzaMotivazioni() {
		List<SsMotivazioniSchede> schedaLstMotivi = new ArrayList<SsMotivazioniSchede>();
		if (scheda.getMotivazione() != null)
			schedaLstMotivi = readMotivazioniScheda(scheda.getMotivazione());

		motivazione.initMotiviClasseFromModel(schedaLstMotivi);
	}

	private void saveOrUpdateNotaDiario(Nota nota) {
		if (nota.getId() == null && !nota.isEmpty()) {
			nota.setAuthor(this.getUserNameOperatore(), this.getPreselectedPContatto().getOrganizzazione());
			saveNotaIntoDB(nota);
		} else if (nota.getId() != null) {
			updateNotaIntoDB(nota);
		}
	}

	private boolean updateNotaIntoDB(Nota nota) {
		try {
			SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();

			SsDiario model = new SsDiario();
			nota.fillModel(model);

			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(model);

			schedaService.updateNotaDiario(dto);

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("salva.error");

			return false;
		}
	}

	private boolean saveNotaIntoDB(Nota nota) {
		try {
			SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();

			SsDiario model = new SsDiario();
			SsAnagrafica anaModel = new SsAnagrafica();
			segnalato.fillAnagraficaModel(anaModel);
			nota.fillModel(model, anaModel);

			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(model);

			model.setId(schedaService.writeNotaDiario(dto));
			nota.setId(model.getId());

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("salva.error");

			return false;
		}
	}
	
	private boolean eliminaNotaDaDB(Long idNota) {
		try {
			SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();

			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(idNota);

			schedaService.deleteNotaDiario(dto);

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("salva.error");

			return false;
		}
	}


	public void eliminaNotaDiario(){
		if(this.selectedNota2Delete!=null){
			this.eliminaNotaDaDB(selectedNota2Delete.getId());
			this.diarioSociale.getNote().remove(selectedNota2Delete);
		}
	}
	
	private boolean saveMotivazioniScheda(List<String> motivazioni,
			SsSchedaMotivazione scheda) {
		showInterventiEconomici = false;
		try {
			SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();
			BaseDTO dto = new BaseDTO();
			fillUserData(dto);

			for (String motivo : motivazioni) {
				dto.setObj(new Long(motivo));
				SsMotivazione motivazioneModel = schedaService.readMotivazione(dto);

				if (motivazioneModel.getEconomico())
					showInterventiEconomici = true;

				Object[] param = new Object[] { motivazioneModel, scheda };
				dto.setObj(param);
				schedaService.writeMotivazioneScheda(dto);
			}

			if (showInterventiEconomici) {
				List<InterventoEconomico> interventi = new ArrayList<InterventoEconomico>();
				SsAnagrafica anagrafica = readAnagraficaById(segnalato
						.getAnagrafica().getId());
				for (SsInterventoEconomico i : this.readInterventiEconomici(anagrafica))
					interventi.add(new InterventoEconomico(i.getId(), i
							.getImporto(), i.getTipo().getTipo(), i
							.getErogante(), i.getData()));
				interventiEconomici.setInterventi(interventi);
			}

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("salva.error");

			return false;
		}

	}

	private boolean saveInterventiScheda(List<String> interventi,
			SsSchedaInterventi intervento) {
		try {
			SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();

			BaseDTO dto = new BaseDTO();
			fillUserData(dto);

			for (String in : interventi) {
				dto.setObj(new Long(in));
				SsIntervento interventoModel = schedaService.readIntervento(dto);

				Object[] param = new Object[] { interventoModel, intervento };
				dto.setObj(param);
				schedaService.writeInterventoScheda(dto);
			}

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("salva.error");

			return false;
		}
	}

	private boolean saveRiferimentoIntoDB() {
		try {
			SsSchedaRiferimento dataModel = new SsSchedaRiferimento();
			riferimento.fillModel(dataModel);

			SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();

			BaseDTO dto = new BaseDTO();
			fillUserData(dto);

			if (riferimento.getId() == null) { // insert

				scheda.setRiferimento(dataModel);
				dto.setObj(scheda);
				scheda = schedaService.saveScheda(dto);

				// save generated id after DB insertion
				riferimento.setId(scheda.getRiferimento().getId());
				dataModel.setId(riferimento.getId());

			} else { // update
				dto.setObj(dataModel);
				schedaService.updateRiferimento(dto);
			}
			return true;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("salva.error");

			return false;
		}
	}
	
	private boolean validaAccesso(){
		return (accesso.getPuntoContatto().getIdPContatto()!=null && accesso.getPuntoContatto().getIdPContatto()>-1);
	}

	private boolean saveAccessoIntoDB(boolean validate) {
			
			if (validate && !validaAccesso())
			{
				addWarning("no.puntoContatto.error");
				return false;
			}
			else
			{
				
				try {
				SsSchedaAccesso accessoModel = new SsSchedaAccesso();
	
				SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();
				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
	
				SsRelUffPcontOrgPK relPK = new SsRelUffPcontOrgPK();
				relPK.setOrganizzazioneId(accesso.getPuntoContatto().getOrganizzazione().getId());
				relPK.setPuntoContattoId(new Long(accesso.getPuntoContatto().getIdPContatto()));
				relPK.setUfficioId(accesso.getPuntoContatto().getUfficio().getId());
				dto.setObj(relPK);
				SsRelUffPcontOrg rel = schedaService.getSsRelUffPcontOrg(dto);
				accessoModel.setSsRelUffPcontOrg(rel);
	
				accesso.fillModel(accessoModel);
	
				if (accesso.getId() == null) { // insert
	
					// save generated id after DB insertion
					/*
					 * accessoModel.setId(schedaService.saveAccesso(dto));
					 * accesso.setId(accessoModel.getId());
					 */
	
					// save a new record into SS_SCHEDA with reference to
					// SS_SCHEDA_ACCESSO
					if(scheda==null || scheda.getId()==null || scheda.getId()<=0)
					{
						scheda = new SsScheda();
					}
					scheda.setAccesso(accessoModel);
					scheda.setCompleta(false);
					scheda.setEliminata(false);
					scheda.setEsterna(this.isEnteEsterno());
	
					/*
					 * dto.setObj(scheda); scheda=schedaService.saveScheda(dto);
					 */
	
					if (segnalato.getAnagrafica().getId() == null
							&& segnalato.getAnagrafica().getCodiceFiscale() != null) { // anagrafica
																						// caricata
																						// da
																						// git
																						// e
																						// non
																						// presente
																						// in
																						// SS
						// save anagrafica in SS_ANAGRAFICA
						SsAnagrafica anagrafica = new SsAnagrafica();
						segnalato.fillAnagraficaModel(anagrafica);
						dto.setObj(anagrafica);
						anagrafica.setId(schedaService.saveAnagrafica(dto));
						segnalato.getAnagrafica().setId(anagrafica.getId());
	
						// save residenza in SS_INDIRIZZO
						SsIndirizzo residenzaModel = new SsIndirizzo();
						segnalato.getResidenza().fillModel(residenzaModel, segnalato.isSenzaFissaDimora());
						/*
						 * dto.setObj(residenzaModel);
						 * residenzaModel.setId(schedaService.writeIndirizzo(dto));
						 */
	
						// save SS_SCHEDA_SEGNALATO
						SsSchedaSegnalato segnalatoModel = new SsSchedaSegnalato();
						segnalatoModel.setAnagrafica(anagrafica);
						segnalatoModel.setResidenza(residenzaModel);
	
						dto.setObj(segnalatoModel);
						segnalatoModel.setId(schedaService.saveSegnalato(dto).getId());
						segnalato.setId(segnalatoModel.getId());
						segnalato.getAnagrafica().setId(segnalatoModel.getAnagrafica().getId());
						segnalato.getResidenza().setId(residenzaModel.getId());
	
						// update SS_SCHEDA_SEGNALATO e SS_SCHEDA con ref chiavi
						// esterne
						/*
						 * Object[] params = new Object[]{segnalatoModel.getId(),
						 * anagrafica}; dto.setObj(params);
						 * schedaService.updateAnagraficaRef(dto); params = new
						 * Object[]{segnalatoModel.getId(), residenzaModel};
						 * dto.setObj(params);
						 * schedaService.updateResidenzaRef(dto);
						 */
	
					} else if (segnalato.getAnagrafica().getId() != null && segnalato.getId() == null) { // scheda
															// nuova
															// con
															// anagrafica
															// presente
															// in
															// SS
						// read anagrafica from SS_ANAGRAFICA
						SsAnagrafica anagrafica = readAnagraficaById(segnalato.getAnagrafica().getId());
	
						// save SS_SCHEDA_SEGNALATO
						SsSchedaSegnalato segnalatoModel = new SsSchedaSegnalato();
						segnalatoModel.setAnagrafica(anagrafica);
	
						dto.setObj(segnalatoModel);
						segnalatoModel.setId(schedaService.saveSegnalato(dto).getId());
						segnalato.setId(segnalatoModel.getId());
	
					}
	
					// Aggiorna il Segnalato nella Scheda
					scheda.setSegnalato(segnalato.getId());
					dto.setObj(scheda);
					scheda = schedaService.saveScheda(dto);
					/**
					 * http://progetti.asc.webred.it/browse/SISO-446
					 */
					if (scheda != null && scheda.getAccesso() != null) {
						accesso.initFromModel(scheda.getAccesso());
					}
	
					} else { // update
						dto.setObj(accessoModel);
						schedaService.updateAccesso(dto);
					}
	 
				return true;
	
					} catch (Exception e) {
					logger.error(e.getMessage(), e);
					addError("salva.error");
		
					return false;
				}
			}
		
	}

	private SsAnagrafica readAnagraficaById(Long id) {
		try {
			SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();
			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(id);

			return schedaService.readAnagraficaById(dto);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");

			return null;
		}
	}

	private boolean saveSegnalanteIntoDB(boolean validate) {
		
		try {
			if (validate && !validaSegnalante())
				return false;
				
			 SsSchedaSegnalante segnalanteModel = segnalante.fillModel();

			SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();
			BaseDTO dto = new BaseDTO();
			fillUserData(dto);

			if(!segnalante.isEmpty()){
				if (segnalante.getId() == null) { // insert
					scheda.setSegnalante(segnalanteModel);
					dto.setObj(scheda);
					scheda = schedaService.saveScheda(dto);
	
					segnalante.setId(scheda.getSegnalante().getId());
	
				} else {// update
					dto.setObj(segnalanteModel);
					schedaService.updateSegnalante(dto);
				}
			}else{
				//Cancello la scheda interlocutore
				if(segnalante.getId()!=null){
					scheda.setSegnalante(segnalanteModel);
					dto.setObj(scheda);
					scheda = schedaService.saveScheda(dto);
				}
			}
			return true;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("salva.error");

			return false;
		}
	}

	private boolean saveSegnalatoIntoDB(boolean validate) {
		boolean salvato = true;
		try {

			if (validate && !validaSegnalato())
				return false;

			SsSchedaSegnalato segnalatoModel = new SsSchedaSegnalato();
			segnalato.fillModel(segnalatoModel);

			SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();
			BaseDTO dto = new BaseDTO();
			fillUserData(dto);

			boolean reload = false;
			
			// save/update residenza e domicilio
			SsIndirizzo residenza = new SsIndirizzo();
			segnalato.getResidenza().fillModel(residenza, segnalato.isSenzaFissaDimora());

			SsIndirizzo domicilio = null;
			if (!segnalato.isDomicilioAsResidenza()){
				domicilio = new SsIndirizzo();
				segnalato.getDomicilio().fillModel(domicilio, segnalato.isSenzaFissaDimora());
			}
			
			segnalatoModel.setResidenza(residenza);
			segnalatoModel.setDomicilio(domicilio);
			//dto.setObj(segnalatoModel);
			
			
			if (segnalato.getId() == null) { // anagrafica non presente in SS e
												// anagrafe e non già
												// precedentemente inserita
				reload = true;
				SsAnagrafica anagrafica = segnalatoModel.getAnagrafica();
				segnalatoModel.setAnagrafica(anagrafica);
				dto.setObj(segnalatoModel);
				segnalatoModel = schedaService.saveSegnalato(dto);
				segnalato.setId(segnalatoModel.getId());
				segnalato.getAnagrafica().setId(segnalatoModel.getAnagrafica().getId());

				// Aggiorna il Segnalato nella Scheda
				scheda.setSegnalato(segnalatoModel.getId());
				dto.setObj(scheda);
				scheda = schedaService.saveScheda(dto);

			} else {
				// update cittadinanza anagrafica
				segnalatoModel.getAnagrafica().setCittadinanza(segnalato.getAnagrafica().getCittadinanza());
				segnalatoModel.getAnagrafica().setStato_civile(segnalato.getAnagrafica().getStatoCivile());

				// update segnalato
				dto.setObj(segnalatoModel);
				segnalatoModel = schedaService.saveSegnalato(dto);
			}

			
			
			// Recuper gli Id Salvati
			//if (segnalato.getResidenza().getId() == null)
			segnalato.getResidenza().setId(segnalatoModel.getResidenza().getId());

			if (segnalatoModel.getDomicilio()!=null)
			segnalato.getDomicilio().setId(segnalatoModel.getDomicilio().getId());

			if (this.isVisPanelStranieri()) {
				/* VALORIZZAZIONE SCHEDA ID - JSON */
				setSchedaIdJson(scheda.getId());

				salvaSchedaStranieri(salvato, reload);
				salvaSchedaAbitazione(salvato, reload);
				salvaSchedaFamConviventi(salvato, reload);
			}

			loadTipiScheda(); //Ricarica per gestire la disabilitazioni delle proposte di prese in carico
			initDiarioSociale(segnalato); //Spostato in questo punto, altrimenti per i nuovi inserimenti non mostra i diari precedentemente inseriti
			riferimentoComeSegnalanteChecked();//SISO-448
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("salva.error");
			salvato = false;
		}
		return salvato;
	}

	private boolean schedaStranieriRichiesta() {
		return  segnalato.getAnagrafica().isCittadinanzaStraniera(); 
	}

	private void eliminaDiariScheda(Long schedaId, int tipoDiario)
			throws Exception {
		try {
			if (scheda.getId() != null && scheda.getId() > 0) {
				AccessTableDiarioSessionBeanRemote diarioService = super
						.getDiarioCsBean();
				it.webred.cs.csa.ejb.dto.BaseDTO bcs = new it.webred.cs.csa.ejb.dto.BaseDTO();
				this.fillUserData(bcs);
				bcs.setObj(schedaId);
				bcs.setObj2(tipoDiario);
				diarioService.deleteSchedeValutazioneByUdcId(bcs);
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	private void salvaSchedaStranieri(boolean salvato, boolean reload)
			throws Exception {
		boolean stranieriRichiesta = schedaStranieriRichiesta();
		if (stranieriMan.isNew() || !stranieriRichiesta)
			eliminaDiariScheda(scheda.getId(),
					DataModelCostanti.TipoDiario.STRANIERI_ID);

		// Salvo il PANNELLO STRANIERI
		if (stranieriRichiesta)
			salvato &= stranieriMan.save();
		else
			stranieriMan = initManStranieri();

		if (salvato && (reload || stranieriMan.isNew()))
			stranieriMan = loadSchedaJsonStranieri(scheda.getId());
	}

	private void salvaSchedaAbitazione(boolean salvato, boolean reload)
			throws Exception {
		if (abitazioneMan.isNew())
			eliminaDiariScheda(scheda.getId(),
					DataModelCostanti.TipoDiario.ABITAZIONE_ID);

		salvato = salvato && abitazioneMan.save();
		if (salvato && (reload || abitazioneMan.isNew()))
			abitazioneMan = loadSchedaJsonAbitazione(scheda.getId());
	}

	private void salvaSchedaFamConviventi(boolean salvato, boolean reload)
			throws Exception {
		if (famConviventiMan.isNew())
			eliminaDiariScheda(scheda.getId(),DataModelCostanti.TipoDiario.FAMIGLIA_ID);

		salvato = salvato && famConviventiMan.save();
		if (salvato && (reload || famConviventiMan.isNew()))
			famConviventiMan = loadSchedaJsonFamConviventi(scheda.getId());
	}
 

	private boolean validaPresaInCarico() {
		boolean ok = true;

		if (isPropostaPresaInCarico(tipoScheda) && (categoriaSociale == null || categoriaSociale == 0)){
			ok = false;
			addError("salva.validate.error");
		}

		return ok;
	}

	private boolean validaInvioAltroEnteUfficio() {
		boolean ok = true;

		if (isTipoSchedaInvio()){
			if (this.organizzazioneAltraZonaId <=0 && this.organizzazioneAltreId <=0 && this.organizzazioneDiZonaId <=0){
			ok = false;
			addErrorMessage("Selezionare un'organizzazione a cui inviare la scheda", "");
			}
		/*	if (this.emailDest.isEmpty() || this.emailTxt.isEmpty()){
				ok = false;
				addErrorMessage("Inserire testo dell'email e destinatario", "");
			}*/
			
			if (isVisPanelInvioUfficio()) {
				// invio altro ufficio (quindi per forza nella proria zonasociale)
				if (this.ufficioOrganizzazioneId == -1){
					addErrorMessage("Selezionare un ufficio a cui inviare la scheda", "");
					ok = false;
				}
			}
		}
		return ok;
	}

	private boolean validaSegnalato() throws NamingException {
		boolean ok = true;

		Anagrafica anagrafica = segnalato.getAnagrafica();
		if (anagrafica.getCognome() == null || "".equals(anagrafica.getCognome().trim())) {
			addErrorMessage("Cognome è un campo obbligatorio", "");
			ok = false;
		}
		if (anagrafica.getNome() == null || "".equals(anagrafica.getNome().trim())) {
			addErrorMessage("Nome è un campo obbligatorio", "");
			ok = false;
		}
		if (anagrafica.getComuneNazioneNascitaMan().getComuneNascitaMan().getComune() == null
				&& anagrafica.getComuneNazioneNascitaMan().getNazioneNascitaMan().getNazione() == null) {
			addErrorMessage("Comune/Stato estero di nascita è un campo obbligatorio","");
			ok = false;
		}

		if (anagrafica.getCodiceFiscale() == null || "".equals(anagrafica.getCodiceFiscale().trim())) {
			addErrorMessage("Codice fiscale è un campo obbligatorio", "");
			ok = false;
		}else if(anagrafica.getCognome()!=null && anagrafica.getNome()!=null){
			boolean soggettoNonCoerente = false;
			//TODO: Verifica che il codice fiscale(se fittizio) non appartenga a soggetti diversi da quello corrente
			BaseDTO dto = new BaseDTO();
			this.fillUserData(dto);
			dto.setObj(anagrafica.getCodiceFiscale());
			dto.setObj2(anagrafica.getCognome());
			dto.setObj3(anagrafica.getNome());
			
			soggettoNonCoerente = getSsSchedaService().esistonoDuplicatiCF(dto);
			if(soggettoNonCoerente){
				addWarningMessage("Non è possibile usare uno stesso codice fiscale per soggetti diversi. \n Nel sistema sono state registrate schede con c.f. "+anagrafica.getCodiceFiscale()+" ma con diversi dati anagrafici.", "");
				ok = false;
			}
		}
			
			
		if (anagrafica.getStatoCivile() == null || "".equals(anagrafica.getStatoCivile().trim())) {
			addErrorMessage("Stato civile è un campo obbligatorio", "");
			ok = false;
		}
		if (anagrafica.getCittadinanza() == null || "".equals(anagrafica.getCittadinanza().trim())) {
			addErrorMessage("Cittadinanza è un campo obbligatorio", "");
			ok = false;
		}
		if (anagrafica.getDatiSesso().getSesso() == null || anagrafica.getDatiSesso().getSesso().isEmpty()) {
			addErrorMessage("Sesso è un campo obbligatorio", "");
			ok = false;
		}
		
		if((segnalato.getTel()==null || segnalato.getTel().isEmpty()) && (segnalato.getCel()==null || segnalato.getCel().isEmpty())) {
			addErrorMessage("Inserire almeno un recapito telefonico", "");
			ok = false;
		}

		boolean okIndirizzo = true;
		if(!segnalato.isSenzaFissaDimora()){
			Indirizzo residenza = segnalato.getResidenza();
			if (residenza != null) {
				okIndirizzo = segnalato.getResidenza().validaIndirizzo();
			} else {
				addErrorMessage("Residenza è un campo obbligatorio", "");
				okIndirizzo = false;
			}
		}
		if(!okIndirizzo) ok = false;
		
		boolean okDomicilio = true;
		if(!segnalato.isDomicilioAsResidenza()){
			Indirizzo domicilio = segnalato.getDomicilio();
			if (domicilio != null) {
				if((domicilio.getVia()==null || domicilio.getVia().isEmpty()) && !domicilio.getComuneNazioneMan().isValorizzato()){
					if(segnalato.getNoteDomicilio()==null || segnalato.getNoteDomicilio().isEmpty()){
						addErrorMessage("Inserire almeno delle note nel domicilio", "");
						okDomicilio = false;
					}
				}else
					okDomicilio = segnalato.getDomicilio().validaIndirizzo();
			}
		}
		if(!okDomicilio) ok = false;

		if (!segnalato.getFormLavoroMan().validaData())
			ok = false;

		if (this.isVisPanelStranieri()) {
			boolean utenteInterlocutore = Scheda.Accessi.Interlocutori.UTENTE.equalsIgnoreCase(this.accesso.getInterlocutore());
			boolean cfgStranieriUfficio = this.accesso.getPuntoContatto().getUfficio().isReqStranieri();

			stranieriMan.setValidaConoscenzaLingua(utenteInterlocutore);
			stranieriMan.setValidaCampiImmigrazione(cfgStranieriUfficio);// Status,
																			// Permesso,
																			// Paese
																			// provenienza

			if (schedaStranieriRichiesta() && !stranieriMan.validaData())
				ok = false;

			if (!abitazioneMan.validaData())
				ok = false;
			if (!famConviventiMan.validaData())
				ok = false;
			
			
			
		}

		return ok;
	}

	private boolean maggiorenne(Date dataRif, Date datanascita) {
		boolean mag = false;
		if (dataRif != null && datanascita != null) {
			Calendar calRif = Calendar.getInstance();
			calRif.setTime(dataRif);

			Calendar calNas = Calendar.getInstance();
			calNas.setTime(datanascita);

			int diffAnni = calRif.get(Calendar.YEAR)
					- calNas.get(Calendar.YEAR);
			int monthRif = calRif.get(Calendar.MONTH);
			int monthNas = calNas.get(Calendar.MONTH);
			int dayRif = calRif.get(Calendar.DAY_OF_MONTH);
			int dayBirth = calNas.get(Calendar.DAY_OF_MONTH);

			if (diffAnni > 18)
				mag = true;
			else if (diffAnni == 18 && monthRif > monthNas)
				mag = true;
			else if (diffAnni == 18 && monthRif == monthNas
					&& dayRif >= dayBirth)
				mag = true;

		}
		return mag;
	}

	private boolean validaBisogni() {

		boolean ok = true;
		boolean maggiorenne=false;
		if(scheda.getAccesso()!=null){
			maggiorenne = maggiorenne(scheda.getAccesso().getData(), segnalato.getAnagrafica().getDataNascita());
		}
		else if(accessoOrig!=null && accessoOrig.getDataAccesso()!=null)
		{
			maggiorenne = maggiorenne(accessoOrig.getDataAccesso(), segnalato.getAnagrafica().getDataNascita());
		}
	
		boolean bisogniPresenti = false;
		for (int i = 1; i <= 5; i++)
			bisogniPresenti = bisogniPresenti| motivazione.getMotivoClasseI(i).getMotiviSelected().isEmpty();
        
		String altro = motivazione.getAltro() != null ? motivazione.getAltro().trim() : "";
		
		boolean datiAssenti = altro.isEmpty() && !bisogniPresenti;
		if ((accesso.getPuntoContatto().getUfficio().isReqBisogni() || !maggiorenne) && datiAssenti) {
			addErrorMessage("Inserire i bisogni ESPRESSI dall'utente o dal segnalante","");
			ok = false;
		}
		
		if (!maggiorenne && (altro.isEmpty())) {
			addErrorMessage("Per soggetti minorenni, specificare meglio il bisogno", "");
			ok = false;
		}
		
		return ok;
	}

	private boolean validaServizi() {
		boolean ok = true;
		String altro = intervento.getAltro() != null ? intervento.getAltro().trim() : "";
		boolean serviziMancanti = intervento.getInterventi().isEmpty(); // TODO:
																		// Verificare
																		// che
																		// gli
																		// altri
																		// interventi
																		// siano
																		// presenti
		if (accesso.getPuntoContatto().getUfficio().isReqServizi() && (altro.isEmpty() && serviziMancanti)) {
			addErrorMessage("Inserire i servizi richiesti", "");
			ok = false;
		}

		return ok;
	}

	private boolean initDiarioSociale(Segnalato segnalato) {
		try {
			diarioSociale = new DiarioSociale();
			SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();
			SsAnagrafica anaModel = new SsAnagrafica();
			segnalato.fillAnagraficaModel(anaModel);

			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(anaModel.getCf());
			List<SsAnagrafica> anagrafiche = schedaService.readAnagraficheByCf(dto);

			for (SsAnagrafica ana : anagrafiche) {
				dto.setObj(ana);
				List<SsDiario> diario = schedaService.readDiarioSociale(dto);
				List<Nota> note = new ArrayList<Nota>();
				for (SsDiario nota : diario)
					 note.add(new Nota(nota, this.getCognomeNomeUtente(nota.getAutore()),
							 canReadNotaDiario(nota, accesso.getOperatore()),
							 canDeleteNotaDiario(nota, accesso.getOperatore(), segnalato.getAnagrafica().getId())));
				diarioSociale.populateNote(note); 
			}

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");

			return false;
		}
	}
	
	private SsSchedaAccessoInviante fromArBufferSSInvioToSsSchedaAccessoInviante(
			ArBufferSsInvio bufferSchedaSelected,Long idNuovaScheda) {
		
		SsSchedaAccessoInviante copiaSchedaInviante = new SsSchedaAccessoInviante();
		copiaSchedaInviante.setIdNuovaScheda(idNuovaScheda);
		copiaSchedaInviante.setOrigAccessoIneterlocutore(bufferSchedaSelected.getOrigAccessoIneterlocutore());
		copiaSchedaInviante.setOrigAccessoModalita(bufferSchedaSelected.getOrigAccessoModalita());
		copiaSchedaInviante.setOrigAccessoMotivo(bufferSchedaSelected.getOrigAccessoMotivo());
		copiaSchedaInviante.setOrigBelfiore(bufferSchedaSelected.getOrigBelfiore());
		copiaSchedaInviante.setOrigCognome(bufferSchedaSelected.getOrigCognome());
		copiaSchedaInviante.setOrigCognomeOperatore(bufferSchedaSelected.getOrigCognomeOperatore());
		copiaSchedaInviante.setOrigDataAccesso(bufferSchedaSelected.getOrigDataAccesso());
		copiaSchedaInviante.setOrigDataInvio(bufferSchedaSelected.getOrigDataInvio());
		copiaSchedaInviante.setOrigDataNascita(bufferSchedaSelected.getOrigDataNascita());
		copiaSchedaInviante.setOrigDescOrganizzazione(bufferSchedaSelected.getOrigDescOrganizzazione());
		copiaSchedaInviante.setOrigEmail(bufferSchedaSelected.getOrigEmail());
		copiaSchedaInviante.setOrigId(bufferSchedaSelected.getOrigId());
		copiaSchedaInviante.setOrigIdOrganizzazione(bufferSchedaSelected.getOrigIdOrganizzazione());
		copiaSchedaInviante.setOrigIdUfficio(bufferSchedaSelected.getOrigIdUfficio());
		copiaSchedaInviante.setOrigNome(bufferSchedaSelected.getOrigNome());
		copiaSchedaInviante.setOrigNomeOperatore(bufferSchedaSelected.getOrigNomeOperatore());
		copiaSchedaInviante.setOrigNomeUfficio(bufferSchedaSelected.getOrigNomeUfficio());
		copiaSchedaInviante.setOrigPuntoContatto(bufferSchedaSelected.getOrigPuntoContatto());
		copiaSchedaInviante.setOrigTelefono(bufferSchedaSelected.getOrigTelefono());
		copiaSchedaInviante.setOrigTipoIntervento(bufferSchedaSelected.getOrigTipoIntervento());
		copiaSchedaInviante.setOrigUriService(bufferSchedaSelected.getOrigUriService());
		copiaSchedaInviante.setOrigZonaSoc(bufferSchedaSelected.getOrigZonaSoc());
		
		return copiaSchedaInviante;
	}
	
	public void onChangePuntoContatto(){
		try {
			
			List<SelectItem> lstPCont = accesso.getPuntoContatto().getUfficio().getListaPContatto();
			if(accesso.getPuntoContatto().getIdPContatto()!=null && accesso.getPuntoContatto().getIdPContatto()>0){
				for(SelectItem si : lstPCont){
					if((Long)si.getValue()==accesso.getPuntoContatto().getIdPContatto())
						accesso.getPuntoContatto().setNomePContatto(si.getLabel());
				}
			}else{
				accesso.getPuntoContatto().setIdPContatto(null);
				accesso.getPuntoContatto().setNomePContatto(null);
			}
			
		} catch (Exception e) {
			logger.error("onChangePuntoContatto"+e.getMessage(),e);
		}
		
	}
	
	private boolean initFromSelectedReceivedScheda(Long idArBufferSsInvio, String zonaSociale) {
		try {
			
			
			schedaRecuperataDaInvio=true;
			
			SsSchedaSessionBeanRemote schedaService =this.getSsSchedaService();
			
			SsSchedaSessionBeanRemote ssSchedaRemoteService=null;
			
			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(idArBufferSsInvio);
			dto.setObj2(zonaSociale);

			List<ArBufferSsInvio> bufferSchedeSelected= schedaService.readSchedeInviateByIdOrigZs(dto);
			
			if(bufferSchedeSelected!=null && bufferSchedeSelected.size()>0){
				ArBufferSsInvio bufferSchedaSelected=bufferSchedeSelected.get(0);
				SsScheda remoteScheda = null;
				boolean zonaSocialeLocale = this.getZonaSociale().equalsIgnoreCase(bufferSchedaSelected.getOrigZonaSoc());
				
				BaseDTO bDto = new BaseDTO();
				fillUserData(bDto);
				bDto.setEnteId(bufferSchedaSelected.getOrigBelfiore()); // inserire orig_belfiore
				bDto.setObj(bufferSchedaSelected.getOrigId()); // inserire orig_id
				
				if(zonaSocialeLocale){
					//provo a cercare la scheda nel service locale
					remoteScheda = schedaService.readScheda(bDto);
				}
			
				if(!zonaSocialeLocale || (remoteScheda!=null && remoteScheda.getId()<=0))
				{
					//se non la trovo provo a collegarmi al service remoto	
					ssSchedaRemoteService =this.getSsSchedaService();
//					, "",
//									bufferSchedaSelected.getOrigUriService());			
					remoteScheda = ssSchedaRemoteService.readScheda(bDto);
				}
				
				//inizializzo i vari MAN tramite i JSON con la scheda Remota
				if (schedaStranieriRichiesta())
					stranieriMan = loadSchedaJsonStranieri(remoteScheda.getId());
				abitazioneMan = loadSchedaJsonAbitazione(remoteScheda.getId());
				famConviventiMan = loadSchedaJsonFamConviventi(remoteScheda.getId());
				
				accesso.initFromModel(remoteScheda.getAccesso()); //inizializzo temporaneamente, poi verrà sovrascritto
				segnalante.initFromModel(remoteScheda.getSegnalante(), false);
				riferimento.initFromModel(remoteScheda.getRiferimento(), false);
				
				List<SsMotivazioniSchede> schedaLstMotivi = new ArrayList<SsMotivazioniSchede>();
				if (remoteScheda.getMotivazione() != null)
					schedaLstMotivi = readMotivazioniScheda(remoteScheda.getMotivazione());
				motivazione.initFromModel(remoteScheda.getMotivazione(), schedaLstMotivi);

				intervento.initFromModel(remoteScheda.getInterventi());
				if (remoteScheda.getInterventi() != null)
					readInterventiScheda(remoteScheda.getInterventi(),
							intervento.getInterventi());
				 
				serviziRichiestiInterventiCustomBean.nuoviDaSchedaJson(remoteScheda.getId(), null); 
				
				// copio la scheda originale (tranne accesso e invio) in una nuova scheda
				scheda=SerializationUtils.clone(remoteScheda);
				
				scheda.setAccesso(null);
				scheda.setTipo(null); //elimino il tipo=scheda inviata
				scheda.setCompleta(false);
				
				scheda.setId(null); //cos� da crearne una nuova
				scheda.setIdentificativo(null); //cos� da crearne una nuova
				
				//elimino gli id da tutti gli oggetti in foreign cos� da creare nuovi anche loro
				scheda.getInterventi().setId(null);
				scheda.getMotivazione().setId(null);
				scheda.getRiferimento().setId(null);
				if(scheda.getSegnalante()!=null) scheda.getSegnalante().setId(null);
				//salvo l'id del segnalato
				Long remoteSegnalatoId=remoteScheda.getSegnalato();
				scheda.setSegnalato(null); // in un secondo momento visto che nel jpa non c'� riferimento all'oggetto devo recuperare i dati e salvarlo a mano
				
				BaseDTO bSchedaDto = new BaseDTO();
				fillUserData(bSchedaDto);
				bSchedaDto.setObj(scheda);
				
				//salvo la nuova scheda
				scheda=schedaService.saveScheda(bSchedaDto);
				
				//Prima di salvare devo valorizzare almeno ufficio, punto contatto e organizzazione altrimenti la perdo.
				accesso = new Accesso();
				accesso.setInterlocutore(remoteScheda.getAccesso().getInterlocutore());
				
				PuntoContatto pCont = this.getPreselectedPContatto();
				if (pCont != null){
					PuntoContatto copia;
					try {
						copia = (PuntoContatto)SerializationUtils.clone(pCont);
						accesso.setPuntoContatto(copia);
					} catch (Exception e) {
						accesso.setPuntoContatto(pCont);
						logger.error("Errore clonazione PuntoContatto", e);
					}	
				}
				
				this.saveAccessoIntoDB(false);
				
				//DA QUI IN POI this.scheda contiente tutti i dati della nuovaSchedaRicevuta
				
				//metto l'id della nuova scheda sui Man
				stranieriMan.setIdSchedaUdc(this.scheda.getId());
				abitazioneMan.setIdSchedaUdc(this.scheda.getId());
				famConviventiMan.setIdSchedaUdc(this.scheda.getId());
				
				serviziRichiestiInterventiCustomBean.setIdSchedaUdc(this.scheda.getId()); //SISO-438
				
				/*Dalla scheda ricevuta vanno eliminati i SERVIZI CUSTOM di INVIO, altrimenti al salvataggio tenta di mandare nuovamente la mail*/
				for(IServizioRichiestoCustom s : serviziRichiestiInterventiCustomBean.getServiziRichiestiCustomDaSalvare()){
					if(s.isTipoInvioScheda()) serviziRichiestiInterventiCustomBean.elimina(s);
				}
				
				riferimento.initFromModel(this.scheda.getRiferimento(), false);
				
				//cambio il riferimento alle SsMotivazioniSchede e li punto alla nuova scheda
				//tolgo gli id in modo da forzare la creazine di una nuova motivazione
				if (this.scheda.getMotivazione() != null){
					for (SsMotivazioniSchede ssMotivazioniSchede : schedaLstMotivi) {
						ssMotivazioniSchede.setScheda(this.scheda.getMotivazione());
						ssMotivazioniSchede.setId(null);
					}
				}
				
				motivazione.initFromModel(this.scheda.getMotivazione(), schedaLstMotivi);

			    intervento.setId(this.scheda.getInterventi().getId()); 
					
				
				//recupero il SEGNALATO -- visto che nel JPA ho solo id
				SsSchedaSegnalato segnalatoModel= readSegnalatoById(remoteSegnalatoId,schedaService);
				if(segnalatoModel==null || segnalatoModel.getId()==null || segnalatoModel.getId()<=0)
					{
					//non l'ho trovato in locale provo in remoto
					if(ssSchedaRemoteService!=null)
						segnalatoModel= readSegnalatoById(remoteSegnalatoId,ssSchedaRemoteService);
					}
				
				if(segnalatoModel!=null)
					segnalato.initFromModel(segnalatoModel,true);
					
				this.saveSegnalanteIntoDB(false);
				this.saveSegnalatoIntoDB(false);
				
				initDiarioSociale(segnalato);
				
				this.saveInterventiIntoDB(false);
				
				this.salvaTabRiferimento();
				
				//recupero info sull'ACCESSO originale ci inizializzo la wizard e lo utilizzo nel salvataggioTabBisogni per capire se maggiorenne
				//informazioni sulla scheda originale
				
				SsSchedaAccessoInviante schedaOriginale= recuperaSsSchedaAccessoInvianteFromSsScheda(scheda);
				if(schedaOriginale!=null && schedaOriginale.getId()!=null && schedaOriginale.getId()>-1)
					accessoOrig.initFromModelAccessoInviante(schedaOriginale);
				
				this.saveMotivazioneIntoDB(false);
				
				//recupero i dati di ACCESSO dal buffer invio
				SsSchedaAccessoInviante copiaSchedaAccessoInviante=fromArBufferSSInvioToSsSchedaAccessoInviante(bufferSchedaSelected,this.scheda.getId());
				
				//salvo i dati di accesso in SS_SCHEDA_ACCESSO_INVIANTE con FK l'id della nuova scheda appena creata
				BaseDTO bAccessoDto = new BaseDTO();
				fillUserData(bAccessoDto);
				bAccessoDto.setObj(copiaSchedaAccessoInviante);
				Long idAccessoInvianteCreata=schedaService.saveAccessoInviante(bAccessoDto);
				if(idAccessoInvianteCreata!=null && idAccessoInvianteCreata>-1)
					copiaSchedaAccessoInviante.setId(idAccessoInvianteCreata);
				
				if(copiaSchedaAccessoInviante!=null && copiaSchedaAccessoInviante.getId()!=null && copiaSchedaAccessoInviante.getId()>-1)
					accessoOrig.initFromModelAccessoInviante(copiaSchedaAccessoInviante);
				
	
				//recupero il CS_D_DIARIO
				
				AccessTableDiarioSessionBeanRemote diarioService;
				AccessTableDiarioSessionBeanRemote diarioRemoteService;
				diarioService = this.getDiarioCsBean();
				diarioRemoteService = this.getRemoteDiarioCsBean();
				boolean isRemoteDiario=false;
			
				
				List<CsDDiario> diariSchedaRemota=new ArrayList<CsDDiario>();
				
				it.webred.cs.csa.ejb.dto.BaseDTO bDtoDiario = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillUserData(bDtoDiario);
				
				bDtoDiario.setObj(remoteScheda.getId());
				
				diariSchedaRemota=diarioService.findDiarioBySchedaId(bDtoDiario);
				if(diariSchedaRemota==null || diariSchedaRemota.isEmpty())
				{
				//non l'ho trovati in locale provo in remoto
				if(ssSchedaRemoteService!=null)
					diariSchedaRemota=diarioRemoteService.findDiarioBySchedaId(bDtoDiario);
					isRemoteDiario=true;
				}
				
				//imposto l'idScheda in CsDDiario con l'id della nuova scheda
				for (CsDDiario csDDiario : diariSchedaRemota) {

					//SISO-438 i diari di tipo richiesta servizio e documenti individuali lì ho già salvati in precedenza
					if (csDDiario.getCsTbTipoDiario().getId() != DataModelCostanti.TipoDiario.RICHIESTA_SERVIZIO_ID
							&& csDDiario.getCsTbTipoDiario().getId() != DataModelCostanti.TipoDiario.DOC_INDIVIDUALE_ID) { 
						
						long id_diario_bk=csDDiario.getId();
						csDDiario.setSchedaId(this.scheda.getId());
						//elimino gli id di tutti i CsDdiario e tutti i suoi oggetti interni in modo da farne creare nuovi ma con identici valori
						csDDiario.setId(0);
						
						//creo la copia del clob
						CsDClob clob = csDDiario.getCsDClob();
						if (clob!=null) {		 
							clob.setId(0);
							
							it.webred.cs.csa.ejb.dto.BaseDTO bDtoClob = new it.webred.cs.csa.ejb.dto.BaseDTO();
							fillUserData(bDtoClob);
							bDtoClob.setObj(clob);
							
							clob=diarioService.createClob(bDtoClob);
							csDDiario.setCsDClob(clob);
						}

						
						it.webred.cs.csa.ejb.dto.BaseDTO bDtoNuovoDiario = new it.webred.cs.csa.ejb.dto.BaseDTO();
						fillUserData(bDtoNuovoDiario);
						
						bDtoNuovoDiario.setObj(csDDiario);
						
						csDDiario=diarioService.createDiario(bDtoNuovoDiario);
						
						//recupero tutte le CsDValutazione
						List<CsDValutazione> valutazioni=new ArrayList<CsDValutazione>();
						valutazioni=getSchedaValutazioneByDiarioId(id_diario_bk);
						
						//salvo una copia della valutazione come nuova
						for (CsDValutazione csDValutazione : valutazioni) {
							csDValutazione.setDiarioId(csDDiario.getId());
							csDValutazione.setCsDDiario(csDDiario);
							saveSchedaValutazione(csDValutazione);
						}
					
					}
					
				}
				
				
				//#####################################
				
				//inizializzo dati per la wizard
				
				//inizializzo i vari MAN tramite i JSON con la scheda Remota
				if (schedaStranieriRichiesta())
					stranieriMan = loadSchedaJsonStranieri(this.scheda.getId());
				abitazioneMan = loadSchedaJsonAbitazione(this.scheda.getId());
				famConviventiMan = loadSchedaJsonFamConviventi(this.scheda.getId());
				
				riferimento.initFromModel(this.scheda.getRiferimento(), false);
				
				schedaLstMotivi = new ArrayList<SsMotivazioniSchede>();
				if (this.scheda.getMotivazione() != null)
					schedaLstMotivi = readMotivazioniScheda(this.scheda.getMotivazione());
				motivazione.initFromModel(this.scheda.getMotivazione(), schedaLstMotivi);

				intervento.initFromModel(this.scheda.getInterventi());
				if (this.scheda.getInterventi() != null)
					readInterventiScheda(this.scheda.getInterventi(),
							intervento.getInterventi());
				 
				loadManJsonServiziRichiesti(this.scheda  // SISO-438//, false
						);	
				
				inizializzaCategoria(this.scheda.getId());

				
				//segnala come ricevuta la scheda
				BaseDTO ricevutaBufferdto = new BaseDTO();
				fillUserData(ricevutaBufferdto);
				bufferSchedaSelected.setRicezData(new Date());
				ricevutaBufferdto.setObj(bufferSchedaSelected);
				schedaService.riceviSchedaBuffer(ricevutaBufferdto);
				return true;
			}
			else return false;
			

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("ricezione.error");

			return false;
		}
	}

	


	private boolean initFromSelectedScheda(Long id) {
		try {
			SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();
			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(id);

			scheda = schedaService.readScheda(dto);

			accesso.initFromModel(scheda.getAccesso());
			// recupero se esiste la schedaAccessoInviante
			SsSchedaAccessoInviante schedaOriginale= recuperaSsSchedaAccessoInvianteFromSsScheda(scheda);
			if(schedaOriginale!=null && schedaOriginale.getId()!=null && schedaOriginale.getId()>-1)
			{
				// inizializza accessoOrig tramite la schedaInviante recuperata
				accessoOrig.initFromModelAccessoInviante(schedaOriginale);
				schedaRecuperataDaInvio=true;
			}
			else
			{
				schedaRecuperataDaInvio=false;
			}
			
			segnalante.initFromModel(scheda.getSegnalante(), false);
			segnalato.initFromModel(readSegnalatoById(scheda.getSegnalato()),false);
			if (schedaStranieriRichiesta())
				stranieriMan = loadSchedaJsonStranieri(id);
			abitazioneMan = loadSchedaJsonAbitazione(id);
			famConviventiMan = loadSchedaJsonFamConviventi(id);

			riferimento.initFromModel(scheda.getRiferimento(), false);

			List<SsMotivazioniSchede> schedaLstMotivi = new ArrayList<SsMotivazioniSchede>();
			if (scheda.getMotivazione() != null)
				schedaLstMotivi = readMotivazioniScheda(scheda.getMotivazione());
			motivazione.initFromModel(scheda.getMotivazione(), schedaLstMotivi);

			intervento.initFromModel(scheda.getInterventi());
			if (scheda.getInterventi() != null)
				readInterventiScheda(scheda.getInterventi(),intervento.getInterventi());

			loadManJsonServiziRichiesti(scheda   //SISO-438
					//, false
					);
			
			tipoScheda = scheda.getTipo();

			inizializzaCategoria(id);

			//initDiarioSociale(segnalato);

			if(this.isTipoSchedaInvio()){
				/* Se presente il tipo intervento ma non l'invio (perchè magari rimosso invio dopo un primo salvataggio) 
				 * inizializzo con i dati base, che poi verranno eventualmente sovrascritti dai dati precedenti, recuperati dal BUFFER
				 */
				initInvioSchedaGeneric(); 
				recuperaInfoSuInvioScheda(scheda);
			}

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");

			return false;
		}
	}

	private void initStatoPrivacy() {
		privacy = null;

		if (segnalato != null && segnalato.getAnagrafica() != null && accesso != null) {
			String codfisc = segnalato.getAnagrafica().getCodiceFiscale();
			Long organizzazioneId = accesso.getPuntoContatto().getOrganizzazione().getId();
			if (codfisc != null && !codfisc.isEmpty() && organizzazioneId != null && organizzazioneId > 0) {

				try {
					SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();
					BaseDTO dto = new BaseDTO();
					fillUserData(dto);

					SsSchedaPrivacyPK pk = new SsSchedaPrivacyPK();
					pk.setCf(codfisc);
					pk.setOrganizzazioneId(organizzazioneId);
					dto.setObj(pk);
					privacy = schedaService.findSchedaPrivacyById(dto);

				} catch (Exception e) {
					logger.error(e);
					addErrorMessage(
							"lettura.error",
							"Non è stato possibile verificare la sottoscrizione del modulo privacy per il soggetto");
				}
			}
		}

		if (privacy == null && accesso != null
				&& accesso.getDataAccesso() != null)
			this.privacyDate = accesso.getDataAccesso();
	}

	private void inizializzaCartella() {
		statoCartella = null;
		try {
			// Recupero lo stato della cartella associata alla scheda (per id)
			AccessTableSchedaSegrSessionBeanRemote schedaSS = (AccessTableSchedaSegrSessionBeanRemote) ClientUtility
					.getEjbInterface("CarSocialeA", "CarSocialeA_EJB","AccessTableSchedaSegrSessionBean");
			
			if(scheda!=null){
				it.webred.cs.csa.ejb.dto.BaseDTO dto2 = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillUserData(dto2);
				dto2.setObj(scheda.getId());
				String statoCsSs = schedaSS.findStatoSchedaSegrById(dto2);
				statoCartella = statoCsSs!=null ? statoCsSs : ""; 
	            
				String statoCS = (new CsUiCompBaseBean()).loadStatoCartella(segnalato.getAnagrafica().getCodiceFiscale());
				statoCartella+= statoCS!=null ? statoCS : "";
				
				if(statoCartella.trim().isEmpty()) statoCartella = null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("cartellaPrint.error");

		}
	}

	/*
	 * private void inizializzaCartella() { statoCartella = null; try{
	 * AccessTableIterStepSessionBeanRemote schedaService =
	 * (AccessTableIterStepSessionBeanRemote)
	 * ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB",
	 * "AccessTableIterStepSessionBean");
	 * 
	 * AccessTableSoggettoSessionBeanRemote soggettiService =
	 * (AccessTableSoggettoSessionBeanRemote)
	 * ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB",
	 * "AccessTableSoggettoSessionBean");
	 * 
	 * AccessTableOperatoreSessionBeanRemote operatoreService =
	 * (AccessTableOperatoreSessionBeanRemote)
	 * ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB",
	 * "AccessTableOperatoreSessionBean");
	 * 
	 * //Recupero lo stato della cartella associata alla scheda (per id)
	 * AccessTableSchedaSegrSessionBeanRemote schedaSS =
	 * (AccessTableSchedaSegrSessionBeanRemote)
	 * ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB",
	 * "AccessTableSchedaSegrSessionBean");
	 * 
	 * 
	 * it.webred.cs.csa.ejb.dto.BaseDTO dto2 = new
	 * it.webred.cs.csa.ejb.dto.BaseDTO(); fillUserData(dto2);
	 * dto2.setObj(scheda.getId()); statoCartella =
	 * schedaSS.findStatoSchedaSegrById(dto2);
	 * 
	 * it.webred.cs.csa.ejb.dto.BaseDTO dto1 = new
	 * it.webred.cs.csa.ejb.dto.BaseDTO(); fillUserData(dto1);
	 * dto1.setObj(segnalato.getAnagrafica().getCodiceFiscale());
	 * 
	 * //Recupero lo stato della cartella in C.Sociale per il soggetto
	 * List<CsASoggettoLAZY> soggetti = soggettiService.getSoggettiByCF(dto1);
	 * if(soggetti!=null && soggetti.size()>0){ for(CsASoggettoLAZY soggetto :
	 * soggetti){ IterDTO itr = new IterDTO(); fillUserData(itr);
	 * itr.setIdCaso(soggetto.getCsACaso().getId()); CsIterStepByCasoDTO stato =
	 * schedaService.getLastIterStepByCasoDTO(itr);
	 * 
	 * if(stato!=null){ if(statoCartella!=null) statoCartella+="<br/>"; else
	 * statoCartella="<br/>";
	 * 
	 * Long idResponsabile =
	 * stato.getCsItStep().getCsDDiario().getResponsabileCaso(); statoCartella
	 * += stato.getCsItStep().getCsCfgItStato().getNome()+"<br/>"; statoCartella
	 * += stato.getCsItStep().getCsDDiario().getDtAmministrativa()!=null ?
	 * "<b>il</b> "+
	 * ddMMyyyyORA.format(stato.getCsItStep().getCsDDiario().getDtAmministrativa
	 * ())+"<br/>" : "<br/>"; if(idResponsabile!=null){ OperatoreDTO odto = new
	 * OperatoreDTO(); this.fillUserData(odto);
	 * odto.setIdOperatore(idResponsabile); try { CsOOperatore asResponsabile =
	 * operatoreService.findOperatoreById(odto); if(asResponsabile != null)
	 * statoCartella
	 * +="<b>Responsabile:</b>"+getCognomeNomeUtente(asResponsabile.
	 * getUsername())+"<br/>"; } catch (Exception e) { logger.error(e); } }
	 * 
	 * dto1.setObj(soggetto.getAnagraficaId());
	 * List<CsASoggettoCategoriaSocLAZY> lstCatSoc =
	 * soggettiService.getSoggettoCategorieAttualiBySoggetto(dto1); String
	 * catSocs = ""; if(lstCatSoc!=null){ catSocs ="<b>Aree Target: </b>";
	 * for(CsASoggettoCategoriaSocLAZY cs : lstCatSoc) catSocs+=
	 * cs.getCsCCategoriaSociale().getDescrizione()+ (cs.getPrevalente()==1 ?
	 * "*" : ""); statoCartella += catSocs+"<br/>"; }
	 * 
	 * } } }
	 * 
	 * 
	 * } catch(Exception e) { logger.error(e.getMessage(), e);
	 * addError("cartellaPrint.error");
	 * 
	 * 
	 * } }
	 */

	private void inizializzaCategoria(Long id) {
		try {
			AccessTableSchedaSegrSessionBeanRemote schedaB = (AccessTableSchedaSegrSessionBeanRemote) ClientUtility
					.getEjbInterface("CarSocialeA", "CarSocialeA_EJB",
							"AccessTableSchedaSegrSessionBean");

			it.webred.cs.csa.ejb.dto.BaseDTO dto2 = new it.webred.cs.csa.ejb.dto.BaseDTO();
			fillUserData(dto2);
			dto2.setObj(id);
			categoriaSociale = schedaB.findCategoriaSchedaSegrById(dto2);

		} catch (Exception e) {
			logger.error("Impossibile recuperare la categoria sociale assegnata "+ e.getMessage(), e);
			addError("lettura.error","Impossibile recuperare la categoria sociale assegnata");
		}
	}

	private boolean initFromSelectedSoggetto(Long anagraficaId) {
		SsAnagrafica anagrafica = readAnagraficaById(anagraficaId);
		if (anagrafica != null)
			segnalato.getAnagrafica().initFromAnagrafica(anagrafica, false);

		return true;
	}
	
	private void initFromAnonimo() throws Exception
	{
		SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();
		BaseDTO dto = new BaseDTO();
		fillUserData(dto);
		dto.setObj(Anagrafica.SEGNALATO_CF_ANONIMO);

		SsAnagrafica anagraficaAnonimo = schedaService.readAnagraficaByCf(dto);
		segnalato.getAnagrafica().initFromAnagrafica(anagraficaAnonimo, false);
	}

	private boolean initFromSelectedCf(String cf) {
		SitDPersona soggetto = readSoggettoFromAnagrafeByCf(cf);
		if (soggetto != null) {

			IndirizzoAnagDTO indirizzo = getResidenzaFromAnagrafe(cf);
			segnalato.setResidenza(indirizzo, getCurrentEnte());

			// SitComune luogoNascita =
			// getComuneFromIdExt(soggetto.getIdExtComuneNascita());

			ComponenteFamigliaDTO compDto = new ComponenteFamigliaDTO();
			compDto.setPersona(soggetto);
			fillUserData(compDto);
			try {

				AnagrafeService anagrafeService = (AnagrafeService) ClientUtility
						.getEjbInterface("CT_Service",
								"CT_Service_Data_Access", "AnagrafeServiceBean");

				compDto = anagrafeService.fillInfoAggiuntiveComponente(compDto);

			} catch (NamingException e) {
			}

			// Recupero l'id della tabella CS a partire dall'ID anagrafico
			CsTbStatoCivile statoCivile = getStatoCivileByIdExtCeT(soggetto.getStatoCivile());
			String statoCivileString = statoCivile != null ? statoCivile.getDescrizione() : null;
			segnalato.getAnagrafica().initFromAnagrafe(soggetto, compDto,statoCivileString);

			//initDiarioSociale(segnalato);

			return true;
		}
		return false;
	}

	

	public CsTbStatoCivile getStatoCivileByIdExtCeT(String statoCivile) {
		try {

			if (statoCivile != null) {
				AccessTableConfigurazioneSessionBeanRemote configurazioneService =this.getCsConfigurazioneService();
				it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
				this.fillUserData(dto);
				dto.setObj(statoCivile);
				CsTbStatoCivile sc = configurazioneService.getStatoCivileByIdExtCet(dto);
				
				if(sc==null)
					this.addWarningMessage("Attenzione", "Non è stata configurata la corrisponenza dei codici di stato civile");
				
				return sc;
			}

		} catch (NamingException e) {
		}

		return null;
	}

	private SitDPersona readSoggettoFromAnagrafeByCf(String cf) {
		RicercaSoggettoAnagrafeDTO dto = new RicercaSoggettoAnagrafeDTO();
		fillUserData(dto);
		dto.setCodFis(cf);

		try {
			AnagrafeService anagrafeService = (AnagrafeService) ClientUtility
					.getEjbInterface("CT_Service", "CT_Service_Data_Access",
							"AnagrafeServiceBean");
			List<SitDPersona> results = anagrafeService
					.getListaPersoneByCF(dto);
			if (results != null && !results.isEmpty())
				return results.get(0);

		} catch (NamingException e) {
			logger.error(e.getMessage(), e);
			addError("caricamento.soggetto.error");
		}
		return null;
	}

	private boolean readUfficiFromDB(List<SelectItem> uffici) {
		try {
			SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();

			BaseDTO dto = new BaseDTO();
			fillUserData(dto);

			List<SsUfficio> results = schedaService.readUffici(dto);

			for (SsUfficio row : results) {
				Long ufficio = row.getId();
				if (canAccessUfficio(ufficio))
					uffici.add(new SelectItem(row.getId(), row.getNome()));
			}

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");

			return false;
		}
	}

	private List<ComponenteFamigliaDTO> getNucleoFamiliare(SsAnagrafica soggetto) {
		RicercaSoggettoAnagrafeDTO dto = new RicercaSoggettoAnagrafeDTO();
		fillUserData(dto);
		try {
			AnagrafeService anagrafeService = (AnagrafeService) ClientUtility
					.getEjbInterface("CT_Service", "CT_Service_Data_Access",
							"AnagrafeServiceBean");

			dto.setCodFis(soggetto.getCf());
			dto.setIdSogg(anagrafeService.getIdPersonaByCF(dto));
			dto.setCognome(soggetto.getCognome());
			dto.setNome(soggetto.getNome());
			dto.setDtNas(soggetto.getData_nascita());

			return anagrafeService.getListaCompFamiglia(dto);

		} catch (NamingException e) {
			// logger.error(e.getMessage(), e);
			// addError("caricamento.soggetto.error");
			return new ArrayList<ComponenteFamigliaDTO>();
		}
	}

	public String getInterventiEconomiciHeader() {
		if (canReadInterventiEconomiciNucleo())
			return "Interventi economici effettuati nell'ultimo anno a favore del nucleo famigliare";
		else
			return "Interventi economici effettuati nell'ultimo anno a favore del soggetto segnalato";

	}

	private List<SsInterventoEconomico> readInterventiEconomici(
			SsAnagrafica anagrafica) {
		try {
			SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();

			List<SsAnagrafica> nucleo = new ArrayList<SsAnagrafica>();
			nucleo.add(anagrafica);

			if (canReadInterventiEconomiciNucleo()) {
				try {
					List<ComponenteFamigliaDTO> famiglia = getNucleoFamiliare(anagrafica);
					for (ComponenteFamigliaDTO comp : famiglia) {
						BaseDTO dto = new BaseDTO();
						fillUserData(dto);
						dto.setObj(comp.getPersona().getCodfisc());
						if (schedaService.isAnagraficaEsterna(dto))
							nucleo.add(schedaService.readAnagraficaByCf(dto));
					}
				} catch (Exception e) {
					// possible empty nucleo famigliare
				}
			}

			List<SsInterventoEconomico> interventi = new ArrayList<SsInterventoEconomico>();
			for (SsAnagrafica ana : nucleo) {
				BaseDTO dto = new BaseDTO();
				dto.setObj(ana.getCf());
				fillUserData(dto);

				List<SsAnagrafica> anagrafiche = schedaService
						.readAnagraficheByCf(dto);
				for (SsAnagrafica a : anagrafiche) {
					dto.setObj(a);
					interventi.addAll(schedaService
							.readInterventiEconomici(dto));
				}
			}

			return interventi;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");

			return null;
		}
	}

	private boolean readMotivazioniFromDB(List<SelectItem> motivazioni, Long n) {
		try {
			if (this.motivazioniClasse == null)
				this.motivazioniClasse = new HashMap<String, List<SsMotivazione>>();

			SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();
			BaseDTO dto = new BaseDTO();
			fillUserData(dto);

			List<SsMotivazione> resultclassi = this.motivazioniClasse.get(n
					.toString());

			if (resultclassi == null || resultclassi.isEmpty()) {
				dto.setObj(n);
				resultclassi = schedaService.readMotivazioniByClasId(dto);
				this.motivazioniClasse.put(n.toString(), resultclassi);
			}

			MotivoClasse mc = motivazione.getMotivoClasseI(n.intValue());
			List<String> lstSel = mc.getMotiviSelected();
			for (SsMotivazione motivo : resultclassi) {
				dto.setObj(motivo.getId());
				SelectItem si = new SelectItem(motivo.getId(),motivo.getMotivo());
				boolean disabled = !"1".equals(motivo.getAbilitato());
				if (disabled) {
					// Recupero le schede del caso corrente, se qualcuna ha come
					// motivazione una disabilitata la inserisco
					if (lstSel != null && lstSel.contains(motivo.getId().toString()))
						si.setDisabled(false);
					else
						si.setDisabled(disabled);
				}
				if ("1".equals(motivo.getAbilitato()) || !si.isDisabled())
					motivazioni.add(si);
			}

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");

			return false;
		}
	}

	public List<String> schedaNome(List<String> list) {
		try {
			SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();

			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			motiva = new ArrayList<String>();

			for (int i = 0; i < list.size(); i++) {
				List<SsMotivazione> results = schedaService
						.readMotivazioni(dto);
				for (SsMotivazione motivo : results) {
					if (list.get(i).equals(Long.toString(motivo.getId()))) {
						motiva.add(motivo.getMotivo() + "");
					}
				}
			}

			return motiva;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");

		}
		return list;
	}

	public void visual() {
		motivazione.refreshDescrizioni(this.motivazioniClasse);
	}

	private List<SsMotivazioniSchede> readMotivazioniScheda(
			SsSchedaMotivazione scheda) {
		try {
			SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();

			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(scheda);
			return schedaService.readMotivazioniScheda(dto);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");
		}
		return null;
	}

	private boolean readInterventiScheda(SsSchedaInterventi scheda, List<String> interventi) {
		try {
			SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();
			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(scheda);
			if(ssInterventiSchede==null)
			{
				ssInterventiSchede=new ArrayList<SsInterventiSchede>();
			}
			ssInterventiSchede = schedaService
					.readInterventiScheda(dto);

			interventi.clear();

			for (SsInterventiSchede interventoScheda : ssInterventiSchede)
				interventi.add(interventoScheda.getIntervento().getId() + "");

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");

			return false;
		}
	}

	private void readInterventiFromDB() {
		interventi = new ArrayList<SelectItem>();
		List<SelectItem> interventiStranieri = new ArrayList<SelectItem>();
		try {
			SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();
			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			List<SsIntervento> results = schedaService.readInterventi(dto);
			
			for (SsIntervento intervento : results){
				SelectItem si = new SelectItem(intervento.getId(), intervento.getIntervento());
				if(!intervento.getAbilitato()) 
				{	si.setDisabled(true);
					if(this.getIntervento()!=null && this.getIntervento().getInterventi()!=null && this.getIntervento().getInterventi().contains(intervento.getId().toString()))
						si.setDisabled(false);
				}
				
				if (intervento.getAbilitato() || !si.isDisabled()){
					if(intervento.getSoloStranieri())
						interventiStranieri.add(si);
					else
						interventi.add(si);
				}
			}	

			if(segnalato.getAnagrafica().isCittadinanzaStraniera())
				interventi.addAll(interventiStranieri);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");
		}
	}


	private SsSchedaSegnalato readSegnalatoById(Long id) {
		try {
			SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();
			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(id);

			return schedaService.readSegnalatoById(dto);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");

			return null;
		}
	}
	
	private SsSchedaSegnalato readSegnalatoById(Long id,SsSchedaSessionBeanRemote schedaService) {
		try {
			

			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(id);

			return schedaService.readSegnalatoById(dto);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");

			return null;
		}
	}


	public void riferimentoComeSegnalanteChecked() {
		if (riferimento.getComeSegnalante()) {
			riferimento.initFromSegnalante(segnalante);
		}
	}

	private boolean backButtonPressed(FlowEvent event) {
		String currentTab = event.getOldStep();
		String newTab = event.getNewStep();
		int index = steps.length;
		for (int i = 0; i < steps.length; i++)
			if (currentTab.equals(steps[i])) {
				index = i;
				break;
			}
		return index != 0 && newTab.equals(steps[index - 1]);
	}

	public boolean renderInterventoEconomico() {
		return isEnteEsterno();
	}

	public Long getCategoriaSociale() {
		return categoriaSociale;
	}

	public void setCategoriaSociale(Long categoriaSociale) {
		this.categoriaSociale = categoriaSociale;
	}

	public List<SelectItem> getCategorieSociali() {
		if (categorieSociali.isEmpty())
			readCategorieFromDB(categorieSociali);
		return categorieSociali;
	}

	private boolean readCategorieFromDB(List<SelectItem> categorie) {
		try {
			AccessTableCatSocialeSessionBeanRemote catSocService = (AccessTableCatSocialeSessionBeanRemote) ClientUtility
					.getEjbInterface("CarSocialeA", "CarSocialeA_EJB",
							"AccessTableCatSocialeSessionBean");
			CeTBaseObject cet = new CeTBaseObject();
			fillUserData(cet);
			List<CsCCategoriaSociale> list = catSocService
					.getCategorieSocialiAll(cet);
			for (CsCCategoriaSociale c : list) {
				SelectItem si = new SelectItem(c.getId(), c.getDescrizione()
						.replace("_", " "));
				si.setDisabled(!"1".equals(c.getAbilitato()));
				categorie.add(si);
			}
			return true;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");

			return false;
		}
	}

	public void setCategorieSociali(List<SelectItem> categorieSociali) {
		this.categorieSociali = categorieSociali;
	}

	/*
	 * public void change() {
	 * 
	 * if(segnalato.getCodStatus() == null ||
	 * segnalato.getCodStatus().equals("")){ segnalato.setCodUltimoPaese(null);
	 * ultPaeseReq = false; }else ultPaeseReq = true; }
	 */

	public List<Long> getSchedeId() {
		return schedeId;
	}

	public void setSchedeId(List<Long> schedeId) {
		this.schedeId = schedeId;
	}

	public List<SelectItem> getMotivazioni1() {
		if (motivazioni1.isEmpty()) {
			Long n = (long) 1;
			readMotivazioniFromDB(motivazioni1, n);
		}
		return motivazioni1;
	}

	public void setMotivazioni1(List<SelectItem> motivazioni1) {
		this.motivazioni1 = motivazioni1;
	}

	public List<SelectItem> getMotivazioni2() {
		if (motivazioni2.isEmpty()) {
			Long n = (long) 2;
			readMotivazioniFromDB(motivazioni2, n);
		}
		return motivazioni2;
	}

	public void setMotivazioni2(List<SelectItem> motivazioni2) {
		this.motivazioni2 = motivazioni2;
	}

	public List<SelectItem> getMotivazioni3() {
		if (motivazioni3.isEmpty()) {
			Long n = (long) 3;
			readMotivazioniFromDB(motivazioni3, n);
		}
		return motivazioni3;
	}

	public void setMotivazioni3(List<SelectItem> motivazioni3) {
		this.motivazioni3 = motivazioni3;
	}

	public List<SelectItem> getMotivazioni4() {
		if (motivazioni4.isEmpty()) {
			Long n = (long) 4;
			readMotivazioniFromDB(motivazioni4, n);
		}
		return motivazioni4;
	}

	public void setMotivazioni4(List<SelectItem> motivazioni4) {
		this.motivazioni4 = motivazioni4;
	}

	public List<SelectItem> getMotivazioni5() {
		if (motivazioni5.isEmpty()) {
			Long n = (long) 5;
			readMotivazioniFromDB(motivazioni5, n);
		}
		return motivazioni5;
	}

	public void setMotivazioni5(List<SelectItem> motivazioni5) {
		this.motivazioni5 = motivazioni5;
	}

	public List<String> getMotiva() {
		return motiva;
	}

	public void setMotiva(List<String> motiva) {
		this.motiva = motiva;
	}

	public void setInviatiDa(List<SelectItem> inviatiDa) {
		this.inviatiDa = inviatiDa;
	}

	public String getStatoCartella() {
		if (statoCartella == null)
			inizializzaCartella();
		return statoCartella;
	}

	public void setStatoCartella(String statoCartella) {
		this.statoCartella = statoCartella;
	}

	public List<String> completeMedico(String query) {
		List<String> results = new ArrayList<String>();
		if (query != null && query.trim().length() >= 3) {
			it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
			this.fillUserData(dto);
			query = query.trim().toUpperCase();
			try {
				AccessTableMediciSessionBeanRemote bean = (AccessTableMediciSessionBeanRemote) ClientUtility
						.getEjbInterface("CarSocialeA", "CarSocialeA_EJB",
								"AccessTableMediciSessionBean");
				dto.setObj(query);
				dto.setObj2(new Integer(10));
				List<CsCMedico> lstMedici = bean.searchMedici(dto);
				for (CsCMedico c : lstMedici)
					results.add((c.getCognome() + " " + c.getNome())
							.toUpperCase());
			} catch (NamingException e) {
			}
		}
		return results;
	}

	public List<SelectItem> getCittadinanzeAcquisite() {
		if (cittadinanzeAcquisite.isEmpty()) {
			try {
				CeTBaseObject cet = new CeTBaseObject();
				this.fillUserData(cet);
				List<CsTbCittadinanzaAcq> lst = this
						.getCsConfigurazioneService().getCittadinanzeAcquisite(
								cet);
				for (CsTbCittadinanzaAcq p : lst) {
					SelectItem si = new SelectItem(p.getId(),
							p.getDescrizione());
					si.setDisabled(!p.getAbilitato());
					cittadinanzeAcquisite.add(si);
				}

			} catch (Exception e) {
				addError("Errore recupero valori cittadinanze acquisite");
				logger.error("getCittadinanzeAcquisite", e);
			}
		}

		return cittadinanzeAcquisite;
	}

	public void setCittadinanzeAcquisite(List<SelectItem> cittadinanzeAcquisite) {
		this.cittadinanzeAcquisite = cittadinanzeAcquisite;
	}


	public static String getCittadinanzaIta() {
		return DataModelCostanti.CITTADINANZA_ITA;
	}

	public void onChangeCittadinanza() {
		if (DataModelCostanti.CITTADINANZA_ITA.equals(segnalato.getAnagrafica().getCittadinanza())) {
			stranieriMan = initManStranieri();
		}
	}

	public String[] getSteps() {
		return steps;
	}

	public SsScheda getScheda() {
		return scheda;
	}

	public HashMap<String, List<SsMotivazione>> getMotivazioniClasse() {
		return motivazioniClasse;
	}

	public void setSteps(String[] steps) {
		this.steps = steps;
	}

	public void setDiarioSociale(DiarioSociale diarioSociale) {
		this.diarioSociale = diarioSociale;
	}

	public void setScheda(SsScheda scheda) {
		this.scheda = scheda;
	}

	public void setAssistenti(List<String> assistenti) {
		this.assistenti = assistenti;
	}

	public void setMotivazioniClasse(
			HashMap<String, List<SsMotivazione>> motivazioniClasse) {
		this.motivazioniClasse = motivazioniClasse;
	}

	

	public IIntermediazioneAb getIntermediazioneAbMan() {
		return  serviziRichiestiInterventiCustomBean.getIntermediazioneAbMan(); //SISO-438 intermediazioneAbMan;
	}

	public void setIntermediazioneAbMan(IIntermediazioneAb intermediazioneAbMan) {
		serviziRichiestiInterventiCustomBean.setIntermediazioneAbMan(intermediazioneAbMan); //SISO-438 
	}


	public void onChangeValorizzaCondLavoro(IOrientamentoLavoro man) {
		if (segnalato != null && segnalato.getFormLavoroMan() != null) {
			BigDecimal idLavoro = segnalato.getFormLavoroMan().getIdCondLavorativa();
			if (idLavoro != null && man!=null)
				man.preValorizzaLavoro(idLavoro);
			else{logger.warn("Impossibile aggiornare CondLavoro in OrientamentoLavoroManBean["+man+"], idLavoro["+idLavoro+"]");}
				
		}
	}

	public void onChangeValorizzaTitStudio(IOrientamentoIstruzione man) {
		BigDecimal idTitoloStudio = null;
		List<SelectItem> lstTitoliStudio = null;

		if (segnalato != null && segnalato.getFormLavoroMan() != null) {
			idTitoloStudio = segnalato.getFormLavoroMan().getIdTitoloStudio();
			lstTitoliStudio = segnalato.getFormLavoroMan().getLstTitoliStudio();
		}
		man.preValorizzaDati(idTitoloStudio, lstTitoliStudio, null);
	}

	public void onChangeCondLavoro() {
		onChangeValorizzaCondLavoro( serviziRichiestiInterventiCustomBean.getOrientamentoLavoroManBean() );
	}

	public void datiAggiuntiviOrientamentoIstruzione(IOrientamentoIstruzione man) {
		onChangeValorizzaTitStudio(man);
		onChangeConoscenzaLinguaItaliana();
	}

	public void onChangeTitoloStudio() {
		/**
		 * SISO-438 rispetto alla versione di codice precedente adesse le schede valutazione Intermediazione OrientamentoLavoro MediazioneCult  OrientamentoIstruzione possono essere vuote
		 */
		if (serviziRichiestiInterventiCustomBean.getOrientamentoIstruzioneMan()!=null) { 
			onChangeValorizzaTitStudio( serviziRichiestiInterventiCustomBean.getOrientamentoIstruzioneMan() );
		}
	}

	// FIXME
	// TODO
	public void onChangeConoscenzaLinguaItaliana() {
		// String linguaItaLivello = null;
		//
		// if (stranieriMan != null ) {
		// linguaItaLivello = stranieriMan.getJsonCurrent().getLiguaItaLivello()
		// ;
		// }
		// orientamentoIstruzioneMan.preValorizzaDati(null, null,
		// linguaItaLivello);
	}

//INIZIO SISO-438
	public IOrientamentoLavoro getOrientamentoLavoroManBean() {
		return serviziRichiestiInterventiCustomBean.getOrientamentoLavoroManBean();
	}

	public void setOrientamentoLavoroManBean(
			IOrientamentoLavoro orientamentoLavoroManBean) {
		this.serviziRichiestiInterventiCustomBean.setOrientamentoLavoroManBean(orientamentoLavoroManBean);
	}
	public IMediazioneCult getMediazioneCultMan() {
		return serviziRichiestiInterventiCustomBean.getMediazioneCultMan();
	}

	public void setMediazioneCultMan(IMediazioneCult mediazioneCultMan) {
		this.serviziRichiestiInterventiCustomBean.setMediazioneCultMan(mediazioneCultMan);
	}
	
	public IOrientamentoIstruzione getOrientamentoIstruzioneMan() {
		return serviziRichiestiInterventiCustomBean.getOrientamentoIstruzioneMan();
	}

	public void setOrientamentoIstruzioneMan(
			IOrientamentoIstruzione orientamentoIstruzioneMan) {
		this.serviziRichiestiInterventiCustomBean.setOrientamentoIstruzioneMan(orientamentoIstruzioneMan);
	}
//FINE SISO-438

	public SsScheda getSchedaPrecedente() {
		return schedaPrecedente;
	}

	public void setSchedaPrecedente(SsScheda schedaPrecedente) {
		this.schedaPrecedente = schedaPrecedente;
	}

	public boolean isAbilitaLoadPrecedente() {
		return abilitaLoadPrecedente;
	}

	public void setAbilitaLoadPrecedente(boolean abilitaLoadPrecedente) {
		this.abilitaLoadPrecedente = abilitaLoadPrecedente;
	}

	public IStranieri getStranieriMan() {
		return stranieriMan;
	}

	public IAbitazione getAbitazioneMan() {
		return abitazioneMan;
	}

	public IFamConviventi getFamConviventiMan() {
		return famConviventiMan;
	}

	public void setStranieriMan(IStranieri stranieriMan) {
		this.stranieriMan = stranieriMan;
	}

	public void setAbitazioneMan(IAbitazione abitazioneMan) {
		this.abitazioneMan = abitazioneMan;
	}

	public void setFamConviventiMan(IFamConviventi famConviventiMan) {
		this.famConviventiMan = famConviventiMan;
	}

	public void setSchedaIdJson(Long idUdc) {
		stranieriMan.setIdSchedaUdc(idUdc);
		abitazioneMan.setIdSchedaUdc(idUdc);
		famConviventiMan.setIdSchedaUdc(idUdc);
	}

	private void loadUltimaSchedaSoggettoUfficio(String codiceFiscale, PuntoContatto pCont) {
		
		if (codiceFiscale != null && !codiceFiscale.trim().isEmpty()) {
			SsSchedaSessionBeanRemote schedaService;
			try {
				schedaService = this.getSsSchedaService();
				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
				dto.setObj(codiceFiscale);
				dto.setObj2(pCont.getUfficio().getId());
				List<SsScheda> schedePrecedenti = schedaService.readSchedeByUfficioCF(dto);
				if (!schedePrecedenti.isEmpty())
					this.schedaPrecedente = schedePrecedenti.get(0);

			} catch (NamingException e) {
				logger.error("Errore caricamento ultima scheda del soggetto:"+ codiceFiscale, e);
			}
		}else this.schedaPrecedente=null;
	}

	public void valorizzaSegnalanteDaUltima() {
		SsSchedaSegnalante s = this.schedaPrecedente.getSegnalante();
		segnalante.initFromModel(s, true);
	}

	public void valorizzaSegnalatoDaUltima() {
		Long idSegnalato = this.schedaPrecedente.getSegnalato();
		SsSchedaSegnalato ss = null;
		if (idSegnalato != null) {
			SsSchedaSessionBeanRemote schedaService;
			try {
				schedaService = this.getSsSchedaService();

				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
				dto.setObj(idSegnalato);

				ss = schedaService.readSegnalatoById(dto);

				if (ss != null) {

					segnalato.initFromModel(ss, true);

					IStranieri s = this.nuovaDaSchedaJsonStranieri(schedaPrecedente.getId());
					IAbitazione a = this.nuovaDaSchedaJsonAbitazione(schedaPrecedente.getId());
					IFamConviventi f = this.nuovaDaSchedaJsonFamiliari(schedaPrecedente.getId());

					if (s != null) {
						stranieriMan = s;
						stranieriMan.setIdSchedaUdc(scheda.getId());
					}
					if (a != null) {
						abitazioneMan = a;
						abitazioneMan.setIdSchedaUdc(scheda.getId());
					}
					if (f != null) {
						famConviventiMan = f;
						famConviventiMan.setIdSchedaUdc(scheda.getId());
					}

				}

			} catch (Exception e) {
				logger.error("Errore caricamento Segnalato:" + idSegnalato, e);
			}

		}
	}

	public void valorizzaRiferimentoDaUltima() {
		SsSchedaRiferimento r = this.schedaPrecedente.getRiferimento();
		riferimento.initFromModel(r, true);
	}

	public void valorizzaMotivazioniDaUltima() {
		SsSchedaMotivazione m = this.schedaPrecedente.getMotivazione();
		this.motivazione.setAltro(m.getAltro());
		List<SsMotivazioniSchede> lst = this.readMotivazioniScheda(m);
		motivazione.initMotiviClasseFromModel(lst);
	}

	public void valorizzaInterventiDaUltima() {
		SsSchedaInterventi i = this.schedaPrecedente.getInterventi();
		this.intervento.setAltro(i.getAltro());
		List<String> lstInterventi = new ArrayList<String>();
		readInterventiScheda(i, lstInterventi);
		intervento.setInterventi(lstInterventi);

		serviziRichiestiInterventiCustomBean.nuoviDaSchedaJson(schedaPrecedente.getId(), scheda.getId());
	 
	}
	
 
	

	public boolean isPrivacySottoscrivi() {
		return privacySottoscrivi;
	}

	public String getPrivacyMessaggio() {
		if (this.privacy != null)
			return this.getMessaggioPrivacy(privacy.getDtIns());
		else
			return null;
	}

	public SsSchedaPrivacy getPrivacy() {
		return privacy;
	}

	public void setPrivacySottoscrivi(boolean privacySottoscrivi) {
		if(this.isPrivacyAnonimo() && privacySottoscrivi)
		{
			this.setPrivacyAnonimo(false);
		}
		this.privacySottoscrivi = privacySottoscrivi;
	}

	public void setPrivacy(SsSchedaPrivacy privacy) {
		this.privacy = privacy;
	}

	public StreamedContent getFilePrivacy() {
		ReportPrivacyBean rb = new ReportPrivacyBean(this.scheda.getId(),
				this.accesso.getPuntoContatto().getUfficio().getId());
		return rb.getFile();
	}

	public Date getPrivacyDate() {
		return privacyDate;
	}

	public void setPrivacyDate(Date privacyDate) {
		this.privacyDate = privacyDate;
	}

	public String getIndietroButtonTesto() {
		return indietroButtonTesto;
	}

	public void setIndietroButtonTesto(String indietroButtonTesto) {
		this.indietroButtonTesto = indietroButtonTesto;
	}

	public UserSearchBean getUserSearchBean() {
		return userSearchBean;
	}

	public void setUserSearchBean(UserSearchBean userSearchBean) {
		this.userSearchBean = userSearchBean;
	}

	public List<ErogazioneBaseDTO> getInterventiErogati() {
		if (interventiErogati == null || interventiErogati.isEmpty()) {
			loadInterventiErogati();
		}
		return interventiErogati;
	}

	public void setInterventiErogati(List<ErogazioneBaseDTO> interventiErogati) {
		this.interventiErogati = interventiErogati;

	}

	private void loadInterventiErogati() {
		interventiErogati = new ArrayList<ErogazioneBaseDTO>();
		it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
		fillUserData(dto);
		dto.setObj(segnalato.getAnagrafica().getCodiceFiscale());
		try {
			AccessTableInterventoSessionBeanRemote intService = (AccessTableInterventoSessionBeanRemote) ClientUtility
					.getEjbInterface("CarSocialeA", "CarSocialeA_EJB",
							"AccessTableInterventoSessionBean");
			interventiErogati = intService.getListaInterventiErogatiByCF(dto);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("caricamento.error");
		}

	}

	private SsTipoScheda readTipoSchedaFromIdTipoScheda(Long idTipoScheda) {
		try{
		if(idTipoScheda!=null){	
			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(tipoScheda);
			SsTipoScheda tipo = this.getSsSchedaService().readTipoSchedaById(dto);
			return tipo;
		}	
		}catch(Exception e){
			logger.error("Errore recupero Esito Intervento ",e);
			addErrorMessage("Errore recupero esito intervento","");
		}

		return null;
	}

	public boolean isTipoSchedaInvio() {
		return (isVisPanelInvioUfficio() || isVisPanelInvioEnte());
	}

	public boolean isVisPanelInvioUfficio() {
//TODO SISO-659
		List<?> l = this.serviziRichiestiInterventiCustomBean.getServiziRichiestiCustom("->", "ufficio"); //invio altro ufficio		
		
		SsTipoScheda ssTipoScheda =  readTipoSchedaFromIdTipoScheda(this.tipoScheda);
		boolean oldInvio = false;
		if (ssTipoScheda!=null && ssTipoScheda.getTipo().toLowerCase().contains("invio") && ssTipoScheda.getTipo().toLowerCase().contains("ufficio")) 
			oldInvio = true;
		
		return !l.isEmpty() || oldInvio;

	}

	public boolean isVisPanelInvioEnte() {
//TODO SISO-659
		List<?> l = this.serviziRichiestiInterventiCustomBean.getServiziRichiestiCustom("->", "ente"); //invio altro ente	

		//return !l.isEmpty();

		SsTipoScheda ssTipoScheda =  readTipoSchedaFromIdTipoScheda(this.tipoScheda);
		boolean oldInvio = false;
		if (ssTipoScheda!=null && ssTipoScheda.getTipo().toLowerCase().contains("invio") && ssTipoScheda.getTipo().toLowerCase().contains("ente")) // TODO aggiungere flag // su SS_TIPO_SCHEDA // ?!
			oldInvio = true;
		return !l.isEmpty() || oldInvio;
	}
	
	

	public List<SelectItem> getOrganizzazioniDiZona() {
		Organizzazione currOrg = this.getPreselectedPContatto().getOrganizzazione();
//		this.organizzazioniDiZona = new ArrayList<SelectItem>();
		
		if (organizzazioniDiZona == null || organizzazioniDiZona.isEmpty()) {
			try {
				SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();
				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
				List<SsOOrganizzazione> orgs = schedaService.readOrganizzazioniZona(dto);
				this.organizzazioniDiZona = new ArrayList<SelectItem>();
				
				
				for (SsOOrganizzazione org : orgs){
					SelectItem si = new SelectItem(org.getId(),org.getNome());
					if(this.isVisPanelInvioEnte() && si.getValue().equals(currOrg.getId())) 
					{
						si.setDisabled(true);
					}
					this.organizzazioniDiZona.add(si);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				addError("lettura.error");
			}
		}else{
			for (SelectItem si : organizzazioniDiZona){
				if(this.isVisPanelInvioEnte() && si.getValue().equals(currOrg.getId())) 
				{
					si.setDisabled(true);				
				}
				else 
				{
					si.setDisabled(false);
				}
			}
		}

		return organizzazioniDiZona;
	}

	public void setOrganizzazioniDiZona(List<SelectItem> organizzazioniDiZona) {
		this.organizzazioniDiZona = organizzazioniDiZona;
	}

	public List<SelectItem> getOrganizzazioniAltraZona() {
		if (organizzazioniAltraZona == null || organizzazioniAltraZona.isEmpty()) {
			organizzazioniAltraZona = new ArrayList<SelectItem>();
			try {
				SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();

				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
				List<ArOOrganizzazione> orgs = schedaService.readOrganizzazioniFuoriZona(dto);
				this.organizzazioniAltraZona = new ArrayList<SelectItem>();
				for (ArOOrganizzazione org : orgs)
					this.organizzazioniAltraZona.add(new SelectItem(org.getId(), org.getNome()));

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				addError("lettura.error");
			}
		}
		return organizzazioniAltraZona;
	}

	public void setOrganizzazioniAltraZona(
			List<SelectItem> organizzazioniAltraZona) {
		this.organizzazioniAltraZona = organizzazioniAltraZona;
	}

	public List<SelectItem> getOrganizzazioniAltre() {
		if (organizzazioniAltre == null || organizzazioniAltre.isEmpty()) {
			organizzazioniAltre = new ArrayList<SelectItem>();
			try {
				SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();
				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
				List<SsOOrganizzazione> orgs = schedaService.readOrganizzazioniAltre(dto);
				this.organizzazioniAltre = new ArrayList<SelectItem>();
				for (SsOOrganizzazione org : orgs)
					this.organizzazioniAltre.add(new SelectItem(org.getId(),org.getNome()));

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				addError("lettura.error");
			}
		}
		return organizzazioniAltre;
	}

	public void setOrganizzazioniAltre(List<SelectItem> organizzazioniAltre) {
		this.organizzazioniAltre = organizzazioniAltre;
	}

	public long getOrganizzazioneDiZonaId() {
		return organizzazioneDiZonaId;
	}

	public void setOrganizzazioneDiZonaId(long organizzazioneDiZonaId) {
		if (this.organizzazioneDiZonaId != organizzazioneDiZonaId) {
			this.reloadUfficiOrganizzazione = true;

			SsOOrganizzazione selectedOrg = readSsOrganizzazione(organizzazioneDiZonaId);
			this.destBelfiore = selectedOrg!=null ? selectedOrg.getBelfiore() : null;
			this.destOrganizzazioneId = selectedOrg!=null ? selectedOrg.getId() : null;
			this.organizzazioneDiZonaId = selectedOrg!=null ? organizzazioneDiZonaId : -1;
			this.destZonaSoc = organizzazioneDiZonaId>0 ? UserBean.getBeanIstance().getZonaSociale() : null;
		}
		else
			this.organizzazioneDiZonaId = organizzazioneDiZonaId;
		
		this.organizzazioneAltraZonaId = -1;
		this.organizzazioneAltreId = -1;
		this.ufficioOrganizzazioneId = -1;
	}

	private SsOOrganizzazione readSsOrganizzazione(long organizzazioneDiZonaId2) {
		try {
			SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();

			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setOrganizzazione(organizzazioneDiZonaId2);

			return schedaService.readSsOOrganizzazioniById(dto);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");

			return null;
		}
	}

	public long getOrganizzazioneAltraZonaId() {
		return organizzazioneAltraZonaId;
	}

	public void setOrganizzazioneAltraZonaId(long organizzazioneAltraZonaId) {
		if (this.organizzazioneAltraZonaId != organizzazioneAltraZonaId) {
			
			ArOOrganizzazione selectedOrg = readArOrganizzazione(organizzazioneAltraZonaId);
			this.destBelfiore = selectedOrg!=null ? selectedOrg.getBelfiore() : null;
			this.destOrganizzazioneId = selectedOrg!=null ? selectedOrg.getId() : null;
			this.destZonaSoc = selectedOrg!=null ? selectedOrg.getZona_nome() : null;
			this.organizzazioneAltraZonaId = selectedOrg!=null ? organizzazioneAltraZonaId : -1;

		}

		this.organizzazioneAltreId = -1;
		this.organizzazioneDiZonaId = -1;
		this.ufficioOrganizzazioneId = -1;
	}

	private ArOOrganizzazione readArOrganizzazione(long organizzazioneAltraZonaId2) {
		try {
			SsSchedaSessionBeanRemote schedaService =this.getSsSchedaService();
			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(organizzazioneAltraZonaId2);

			return schedaService.readArOOrganizzazioniById(dto);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");

			return null;
		}
	}
	
	private ArOOrganizzazione readSsArOrganizzazione(long organizzazioneId) {
		// cerca sia in Ar che Cs e restituisce una pseudo ArOOrganizzazione
		// da cui si capisce se � una org di zona, fuori zona o altra org.
		try {
			SsSchedaSessionBeanRemote schedaService =this.getSsSchedaService();
			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setOrganizzazione(organizzazioneId);

			return schedaService.readSsArOOrganizzazioniById(dto);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");

			return null;
		}
	}
	
	public long getOrganizzazioneAltreId() {
		return organizzazioneAltreId;
	}

	public void setOrganizzazioneAltreId(long organizzazioneAltreId) {
		if (this.organizzazioneAltreId != organizzazioneAltreId) {
			
			ArOOrganizzazione selectedOrg = readSsArOrganizzazione(organizzazioneAltreId);
			this.destBelfiore = selectedOrg!=null ? selectedOrg.getBelfiore() : null;
			this.destOrganizzazioneId = selectedOrg!=null ? selectedOrg.getId() : null;
			this.destZonaSoc = selectedOrg!=null ? selectedOrg.getZona_nome() : null;
			this.organizzazioneAltreId = selectedOrg!=null ? organizzazioneAltreId : -1;
		}

		this.organizzazioneAltraZonaId = -1;
		this.organizzazioneDiZonaId = -1;
		this.ufficioOrganizzazioneId = -1;
	}


	public List<SelectItem> getUfficiOrganizzazione() {
		if (ufficiOrganizzazione == null || ufficiOrganizzazione.isEmpty() || reloadUfficiOrganizzazione) {

			ufficiOrganizzazione = new ArrayList<SelectItem>();
			HashMap<Long, SsRelUffPcontOrg> ufficiOrganizzazioneHash = new HashMap<Long, SsRelUffPcontOrg>();
			this.reloadUfficiOrganizzazione = false;
			
			
			
			if (this.organizzazioneDiZonaId == -1 && this.organizzazioneAltraZonaId==-1 && this.organizzazioneAltreId==-1 && this.isVisPanelInvioUfficio()) {
				PuntoContatto pContMan = this.getPreselectedPContatto();
				Organizzazione currOrg = pContMan.getOrganizzazione();
				
				//Inizializzo con l'organizzazione di default
				setOrganizzazioneDiZonaId(currOrg.getId());
			} 
			
			if(organizzazioneDiZonaId>=0){
				try {
					SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();
					BaseDTO dto = new BaseDTO();
					fillUserData(dto);
					dto.setOrganizzazione(organizzazioneDiZonaId);
					List<SsRelUffPcontOrg> ufficiOrgRel = schedaService.readUfficiOrganizzazione(dto);
	
					for (SsRelUffPcontOrg uffRelOrg : ufficiOrgRel) {
						if (!ufficiOrganizzazioneHash.containsKey(uffRelOrg.getSsUfficio().getId())) {
							ufficiOrganizzazioneHash.put(uffRelOrg.getSsUfficio().getId(), uffRelOrg);
							this.ufficiOrganizzazione.add(new SelectItem(uffRelOrg.getSsUfficio().getId(), uffRelOrg.getSsUfficio().getNome()));
						}
					}
	
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					addError("lettura.error");
				}
			}
		}

		return ufficiOrganizzazione;
	}

	public void setUfficiOrganizzazione(List<SelectItem> ufficiOrganizzazione) {
		this.ufficiOrganizzazione = ufficiOrganizzazione;
	}

	public long getUfficioOrganizzazioneId() {
		return ufficioOrganizzazioneId;
	}

	public void setUfficioOrganizzazioneId(long ufficioOrganizzazioneId) {
		this.ufficioOrganizzazioneId = ufficioOrganizzazioneId;
		// recupero l'intero ufficio e inizializzo l'email destinatario
		if (ufficioDest == null || ufficioDest.getId() != ufficioOrganizzazioneId) {
			if (ufficioOrganizzazioneId > -1) {
				try {

					SsSchedaSessionBeanRemote schedaService = this.getSsSchedaService();
					BaseDTO dto = new BaseDTO();
					fillUserData(dto);
					dto.setObj(ufficioOrganizzazioneId);
					this.ufficioDest = schedaService.readUfficio(dto);
					if (this.ufficioDest != null && this.ufficioDest.getId() > -1) {
						this.emailDest = this.ufficioDest.getEmail();
						this.destUfficio = this.ufficioDest;
					}

				} catch (Exception e) {
                     logger.error("setUfficioOrganizzazioneId",e);
				}

			} else {
				this.ufficioDest = null;
				this.destUfficio = null;
				this.emailDest = "";

			}
		}
	}

	public boolean isReloadUfficiOrganizzazione() {
		return reloadUfficiOrganizzazione;
	}

	public void setReloadUfficiOrganizzazione(boolean reloadUfficiOrganizzazione) {
		this.reloadUfficiOrganizzazione = reloadUfficiOrganizzazione;
	}

	public String getOrigBelfiore() {
		return origBelfiore;
	}

	public void setOrigBelfiore(String origBelfiore) {
		this.origBelfiore = origBelfiore;
	}

	public String getOrigUriService() {
		return origUriService;
	}

	public void setOrigUriService(String origUriService) {
		this.origUriService = origUriService;
	}

	public Long getOrigId() {
		return origId;
	}

	public void setOrigId(Long origId) {
		this.origId = origId;
	}

	public Date getOrigDataAccesso() {
		return origDataAccesso;
	}

	public void setOrigDataAccesso(Date origDataAccesso) {
		this.origDataAccesso = origDataAccesso;
	}

	public String getOrigCognome() {
		return origCognome;
	}

	public void setOrigCognome(String origCognome) {
		this.origCognome = origCognome;
	}

	public String getOrigAccessoModalita() {
		return origAccessoModalita;
	}

	public void setOrigAccessoModalita(String origAccessoModalita) {
		this.origAccessoModalita = origAccessoModalita;
	}

	public String getOrigAccessoIneterlocutore() {
		return origAccessoIneterlocutore;
	}

	public void setOrigAccessoIneterlocutore(String origAccessoIneterlocutore) {
		this.origAccessoIneterlocutore = origAccessoIneterlocutore;
	}

	public String getOrigAccessoMotivo() {
		return origAccessoMotivo;
	}

	public void setOrigAccessoMotivo(String origAccessoMotivo) {
		this.origAccessoMotivo = origAccessoMotivo;
	}

	public String getOrigNome() {
		return origNome;
	}

	public void setOrigNome(String origNome) {
		this.origNome = origNome;
	}

	public Date getOrigDataNascita() {
		return origDataNascita;
	}

	public void setOrigDataNascita(Date origDataNascita) {
		this.origDataNascita = origDataNascita;
	}

	public String getOrigPuntoContatto() {
		return origPuntoContatto;
	}

	public void setOrigPuntoContatto(String origPuntoContatto) {
		this.origPuntoContatto = origPuntoContatto;
	}

	public String getOrigEmail() {
		return origEmail;
	}

	public void setOrigEmail(String origEmail) {
		this.origEmail = origEmail;
	}

	public String getOrigTelefono() {
		return origTelefono;
	}

	public void setOrigTelefono(String origTelefono) {
		this.origTelefono = origTelefono;
	}

	public String getOrigZonaSoc() {
		return origZonaSoc;
	}

	public void setOrigZonaSoc(String origZonaSoc) {
		this.origZonaSoc = origZonaSoc;
	}

	public String getOrigTipoIntervento() {
		return origTipoIntervento;
	}

	public void setOrigTipoIntervento(String origTipoIntervento) {
		this.origTipoIntervento = origTipoIntervento;
	}

	public Date getOrigDataInvio() {
		return origDataInvio;
	}

	public void setOrigDataInvio(Date origDataInvio) {
		this.origDataInvio = origDataInvio;
	}

	public String getEmailDest() {
		return emailDest;
	}

	public void setEmailDest(String emailDest) {
		this.emailDest = emailDest;
	}

	public String getEmailSubj() {
		return emailSubj;
	}

	public void setEmailSubj(String emailSubj) {
		this.emailSubj = emailSubj;
	}

	public String getEmailTxt() {
		return emailTxt;
	}

	public void setEmailTxt(String emailTxt) {
		this.emailTxt = emailTxt;
	}

	public String getDestBelfiore() {
		return destBelfiore;
	}

	public void setDestBelfiore(String destBelfiore) {
		this.destBelfiore = destBelfiore;
	}

	public ArBufferSsInvio getSchedaInfoInvio() {
		return schedaInfoInvio;
	}

	public void setSchedaInfoInvio(ArBufferSsInvio schedaInfoInvio) {
		this.schedaInfoInvio = schedaInfoInvio;
	}

	public List<Soggetto> getNucleoFamiliare() {
		return nucleoFamiliare;
	}

	public void setNucleoFamiliare(List<Soggetto> nucleoFamiliare) {
		this.nucleoFamiliare = nucleoFamiliare;
	}

	public void loadNucleoFamiliareSegnalato(){
		nucleoFamiliare = new ArrayList<Soggetto>();
		List<ComponenteFamigliaDTO> famiglia = 
				getNucleoFamiliare(segnalato.getAnagrafica().getIdExtAnagrafeEnte(), segnalato.getAnagrafica().getCodiceFiscale());
		
		for(ComponenteFamigliaDTO c: famiglia){
			SitDPersona p = c.getPersona();
			Soggetto s = new Soggetto(new Long(-1), p.getId(), null, p.getCognome(), p.getNome(), p.getCodfisc(), p.getDataNascita(), p.getDataMor(),p.getSesso());
			s.setParentela(getRelazioneParentaleCs(c.getRelazPar()));
			
			nucleoFamiliare.add(s);
		}
	}
	
	public boolean isSchedaPicInCS(){
		String statoScheda = null;
		try{
			AccessTableSchedaSegrSessionBeanRemote schedaSegrService =
					(AccessTableSchedaSegrSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSegrSessionBean");
			if (scheda.getId()!=null) {
				// http://progetti.asc.webred.it/browse/SISO-419
				// se CS_SS_SCHEDA_SEGR.STATO!=NULL non posso fare modifiche
				it.webred.cs.csa.ejb.dto.BaseDTO baseDto = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillUserData(baseDto);
				baseDto.setObj(scheda.getId());
				statoScheda = schedaSegrService.findStatoSchedaSegrById(baseDto);
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			addError("lettura.error");
			return true; //In caso di errore evito comunque di cancellare la scheda di riferimento
		}
		return statoScheda!=null && !statoScheda.isEmpty();
	}
	
	public Accesso getAccessoOrig() {
		return accessoOrig;
	}

	public boolean isSchedaRecuperataDaInvio() {
		return schedaRecuperataDaInvio;
	}

	//INIZIO SISO-346
	public boolean isInvianteRendered() {
		return DataModelCostanti.Scheda.Accessi.MOTIVO_INVIATO_DA.equals(accesso.getMotivo() );
	}

	public void motivoAccessoChanged() { 
		accesso.setInviante(null);
	}

	public String getSpecificareMotivoLabel() {
		if (DataModelCostanti.Scheda.Accessi.MOTIVO_CONVOCATO.equals(accesso.getMotivo() )) {
			return "Specificare il motivo della convocazione *";
		} else { 
			return "Specificare";
		}
	}

	public boolean isMotivoRequired() {
		if (DataModelCostanti.Scheda.Accessi.MOTIVO_CONVOCATO.equals(accesso.getMotivo() )) {
			return true;
		} else { 
			return false;
		}
	}
	
	

	public void interlocutoriChanged(AjaxBehaviorEvent event) {
		try {
			accesso.setUtenteAccompagnato(null) ;
			accesso.setUtentePresenteInformato(null);
			this.onChangeUtenteAccompagnato();
			
			if (Scheda.Accessi.Interlocutori.UTENTE.equalsIgnoreCase(this.accesso.getInterlocutore())) { 
				RequestContext context = RequestContext.getCurrentInstance(); 
				context.execute("utenteAccompagnatoWV.show();");
			} else {
				RequestContext context = RequestContext.getCurrentInstance(); 
				context.execute("utentePresenteInformatoWV.show();");
			}
		} catch (Exception e) {
			addErrorMessage("Errore di sistema'", ""); 
		}

	
	}
	
	
	public boolean isUtenteAccompagnatoRendered(){
		boolean isUtente = Scheda.Accessi.Interlocutori.UTENTE.equalsIgnoreCase(this.accesso.getInterlocutore());
		if (isUtente && accesso.getUtenteAccompagnato()!=null && !accesso.getUtenteAccompagnato().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isUtentePresenteInformatoRendered(){
		boolean isUtente = Scheda.Accessi.Interlocutori.UTENTE.equalsIgnoreCase(this.accesso.getInterlocutore());
		if (!isUtente && accesso.getUtentePresenteInformato()!=null && !accesso.getUtentePresenteInformato().equals("") ) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isPnlSegnalanteRendered() {
		//visualizzo la tab dell'interlocutore nel caso in cui siano stati già inseriti i dati, oppure nel caso in cui l'interlocutore non sia l'utente
		if ( Scheda.Accessi.Interlocutori.UTENTE.equalsIgnoreCase(this.accesso.getInterlocutore()) && (segnalante==null || segnalante.getId()==null || segnalante.isEmpty()) ) {
			return false;
		} else {
			return true;
		}
	}
	  

	private boolean validaSegnalante() { 
		boolean result = false;


		if ( Scheda.Accessi.Interlocutori.ORGANIZZAZIONE.equalsIgnoreCase(this.accesso.getInterlocutore())){
			
			//quando l'interlocutore è un organizzazione il campo ente/servizio di appartenenza è obbligatorio sempre
			if ( segnalante.getSettoreId()!=null  && segnalante.getSettoreId()>0 ) {
				result = true;
			}

			//il seguente controllo ha senso solo per le schede inserite prima dell'intervento SISO-346, che potevano avere il campo "ente" valorizzato
			//dopo il SISO-346 la stessa entità viene rappresentata dal campo settoreId, legato al una combobox
			if ( segnalante.getEnte()!=null 
					&& !segnalante.getEnte().trim().equals("") ) {
				result = true;
			}
			

			if (!result) {
				addErrorMessage("Valorizzare 'Ente/Servizio'", ""); 
			}
			
		} else {
			//il seguente controllo ha senso solo per le schede inserite prima dell'intervento SISO-346, che potevano avere il campo "ente" valorizzato
			//dopo il SISO-346 la stessa entità viene rappresentata dal campo settoreId, legato al una combobox
			if(!Scheda.Accessi.Interlocutori.UTENTE.equalsIgnoreCase(accesso.getInterlocutore())){
			
				if ( segnalante.getEnte()!=null && !segnalante.getEnte().trim().equals("") ) 
					result = true;
				 
				if ( segnalante.getSettoreId()!=null && segnalante.getSettoreId()>0 )
					result = true;
	
				if (  segnalante.getNome()!=null 
						&& !segnalante.getNome().trim().equals("")
						&& segnalante.getCognome()!=null  
						&& !segnalante.getCognome().trim().equals("")  ) {  
					result = true;
				}  
			
				if (!result) addErrorMessage("Valorizzare 'Ente/Servizio' oppure 'Nome' e 'Cognome' ", ""); 
				
			}else result = true;
		}

		return result;
	}
	
	
	

	public List<SelectItem> getSettori() {
		if (settori.isEmpty()) {
			AccessTableConfigurazioneSessionBeanRemote bean;
			try {
				bean = this.getCsConfigurazioneService();
				settori = new ArrayList<SelectItem>();
				CeTBaseObject bo = new CeTBaseObject();
				fillUserData(bo);
				List<CsOSettore> lst = bean.getSettoreAll(bo);
				
				loadListaSettori(lst, settori);
				
			} catch (NamingException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return settori;
	}
	

	private void loadListaSettori(List<CsOSettore> listaSettori, List<SelectItem> listaSelectItem) {
		
		listaSelectItem.clear();
		HashMap<Long, List<CsOSettore>> mappaSettori = new HashMap<Long,List<CsOSettore>>();
//		if(!listaSettori.isEmpty()) {
			for(CsOSettore s : listaSettori) {
				 List<CsOSettore> lst = mappaSettori.get(s.getCsOOrganizzazione().getId());
				 if(lst==null) lst = new ArrayList<CsOSettore>();
				 
//				 if(s.getFlgInviante()){
					 lst.add(s);
					 mappaSettori.put(s.getCsOOrganizzazione().getId(), lst);
//				 }
			}
			
			listaSelectItem.addAll(loadListaSettoriGroup(mappaSettori));
//		}
	}
	

	public boolean isEnteCampoAperto(){
		if (segnalante.getEnte()!= null 
				&& !segnalante.getEnte().trim().equals("") ) {
			return true;
		} else {
			return false;
		}
	}
	

	public boolean isUtenteAccompagnatoreDisabled(){ 
		if (accesso.getUtenteAccompagnato()!=null && accesso.getUtenteAccompagnato()) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isUtenteAccompagnatoSelected(){
		if (accesso.getUtenteAccompagnato()!=null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isAccompagnatorePopupRendered(){
		if (accesso.getUtenteAccompagnato()!=null && accesso.getUtenteAccompagnato()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void onChangeUtenteAccompagnato(){
		if(accesso.getUtenteAccompagnato()==null || !accesso.getUtenteAccompagnato())
			accesso.setAccompagnatore(null);
	}

	public void utenteAccompagnatoDgClosed() {
		accesso.setAccompagnatore(accesso.getAccompagnatorePopup());
	}
		
	
	//FINE SISO-346
	

	//INIZIO SISO-438-Possibilità di allegare documenti in UdC
	private void loadManJsonServiziRichiesti(SsScheda scheda
			//, boolean loadToClone
			) throws Exception {
		serviziRichiestiInterventiCustomBean.loadManJsonServiziRichiesti(scheda
				//, loadToClone
				);
	}
	
	public ServiziRichiestiInterventiCustomBean getServiziRichiestiInterventiCustomBean() {
		return serviziRichiestiInterventiCustomBean;
	}

	public void setServiziRichiestiInterventiCustomBean(
			ServiziRichiestiInterventiCustomBean serviziRichiestiInterventiCustomBean) {
		this.serviziRichiestiInterventiCustomBean = serviziRichiestiInterventiCustomBean;
	}

	public List<SelectItem> getTipoInterventosCustom() {
		if (tipoInterventosCustom==null || tipoInterventosCustom.isEmpty()) 
			loadTipoInterventi();
		return tipoInterventosCustom;
	}
	
	public Long getSelectedTipoInterventoCustom() {
		return selectedTipoInterventoCustom;
	}

	public void setSelectedTipoInterventoCustom(Long selectedTipoInterventoCustom) {
		this.selectedTipoInterventoCustom = selectedTipoInterventoCustom;
	}

	protected void loadTipoInterventi() {
		it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
		fillEnte(dto);
		
		List<CsCTipoInterventoCustom> lstTipoInterventiCustom = interventoService.findTipiIntCustom(dto); 
		this.tipoInterventosCustom = new LinkedList<SelectItem>();  

		for (CsCTipoInterventoCustom ic : lstTipoInterventiCustom) {
			this.tipoInterventosCustom.add(new SelectItem((Long)ic.getId(), ic.getDescrizione()));			
		}

	}

	public void setTipoInterventosCustom(List<SelectItem> tipoInterventosCustom) {
		this.tipoInterventosCustom = tipoInterventosCustom;
	}
	
	//FINE SISO-438-Possibilità di allegare documenti in UdC
	public void eliminaServiziRichiestiInterventiCustomBean(IServizioRichiestoCustom servizio){
		this.serviziRichiestiInterventiCustomBean.elimina(servizio);
		if(servizio.isTipoInvioScheda()){
			this.annullaInvio();
			this.infoSuInvioSchedaRecuperate=false;
			this.recuperaInfoSuInvioScheda(scheda);
		}
	}
	
	public void aggiungiServiziRichiestiInterventiCustomBean(){
		this.serviziRichiestiInterventiCustomBean.aggiungi();
		this.onChangeCondLavoro();
		
		// SISO-659: INVIO scheda
		resetValoriInvioScheda();		
		if (isVisPanelInvioUfficio() || this.isVisPanelInvioEnte() ) {
			if(isVisPanelInvioUfficio()){
				// ho scelto invio ad altro ufficio, quindi seleziono la mia zona
				// come OrganizzazioneDiZonaId
				PuntoContatto pContMan = this.getAccesso().getPuntoContatto();
				Organizzazione currOrgInviante = pContMan.getOrganizzazione();
				setOrganizzazioneDiZonaId(currOrgInviante.getId());
				// inizializzo la lista degli uffici
				getUfficiOrganizzazione();
			}
			initInvioSchedaGeneric();
		} else {
			schedaInfoInvio=null;
		}
		//
	}

	public Nota getSelectedNota2Delete() {
		return selectedNota2Delete;
	}

	public void setSelectedNota2Delete(Nota selectedNota2Delete) {
		this.selectedNota2Delete = selectedNota2Delete;
	}

	public boolean isPrivacyAnonimo() {
		if(segnalato!=null)
			if(segnalato.getAnagrafica()!=null)
				if( Anagrafica.SEGNALATO_CF_ANONIMO.equals(segnalato.getAnagrafica().getCodiceFiscale()) )
				{
					return true;
				}
					
		return false; //privacyAnonimo;
	}
	public void setPrivacyAnonimo(boolean privacyAnonimo) {
		
	}
	public void valueChangePrivacyAnonimo(ValueChangeEvent ev) {	
		try 
		{
			Boolean privacyAnonimo = (Boolean) ev.getNewValue();
			if(privacyAnonimo!=null && privacyAnonimo)
			{
				if(segnalato!=null)
				{
					Anagrafica a = segnalato.getAnagrafica();
					if( a!=null)
					{
						if( a.getId()!=null)
						{
							privacyAnonimo_prev_anagraficaId = a.getId();
						}
						
						a.setNome(Anagrafica.SEGNALATO_CF_ANONIMO);
						a.setCognome(Anagrafica.SEGNALATO_CF_ANONIMO);
						a.setCodiceFiscale(Anagrafica.SEGNALATO_CF_ANONIMO);
						
					}
					
//				initFromAnonimo();
				}		
				
	
			}
			else
			{
				if(privacyAnonimo_prev_anagraficaId!=null && privacyAnonimo_prev_anagraficaId>0)
				{
					SsAnagrafica old_anagrafica = readAnagraficaById(privacyAnonimo_prev_anagraficaId);
					if (old_anagrafica != null)
						segnalato.getAnagrafica().initFromAnagrafica(old_anagrafica, false);
				}			
				else
				{
					segnalato.setAnagrafica(new Anagrafica());				
				}	
				
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("ERROR lettura utente anonimo ");
		}
		
		
		
//		this.privacyAnonimo = privacyAnonimo;
	}
	
	public boolean isDisabilitaCategoria(){
		boolean disabled = this.isSchedaPicInCS() || !this.isTipoPicSs();
		return disabled;
	}
	

}
