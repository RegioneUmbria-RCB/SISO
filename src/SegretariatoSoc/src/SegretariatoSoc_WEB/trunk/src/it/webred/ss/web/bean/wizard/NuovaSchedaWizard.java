package it.webred.ss.web.bean.wizard;

import it.webred.amprofiler.ejb.user.UserService;
import it.webred.amprofiler.model.AmUser;
import it.webred.cs.csa.ejb.client.AccessTableCatSocialeSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableMediciSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableNazioniSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSegrSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.SchedaSegrDTO;
import it.webred.cs.csa.ejb.dto.StatoCartellaDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneBaseDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsCMedico;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsTbCittadinanzaAcq;
import it.webred.cs.data.model.CsTbStatoCivile;
import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.cs.json.OrientamentoLavoro.IOrientamentoLavoro;
import it.webred.cs.json.OrientamentoLavoro.OrientamentoLavoroManBaseBean;
import it.webred.cs.json.abitazione.AbitazioneManBaseBean;
import it.webred.cs.json.abitazione.IAbitazione;
import it.webred.cs.json.familiariConviventi.FamiliariManBaseBean;
import it.webred.cs.json.familiariConviventi.IFamConviventi;
import it.webred.cs.json.intermediazione.IIntermediazioneAb;
import it.webred.cs.json.intermediazione.IntermediazioneManBaseBean;
import it.webred.cs.json.mediazioneculturale.IMediazioneCult;
import it.webred.cs.json.mediazioneculturale.MediazioneCultManBaseBean;
import it.webred.cs.json.orientamentoistruzione.IOrientamentoIstruzione;
import it.webred.cs.json.orientamentoistruzione.OrientamentoIstruzioneManBaseBean;
import it.webred.cs.json.stranieri.IStranieri;
import it.webred.cs.json.stranieri.StranieriManBaseBean;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.ComponenteFamigliaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.IndirizzoAnagrafeDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.siso.esb.Medico;
import it.webred.siso.esb.client.MedicoClient;
import it.webred.siso.ws.client.anag.client.PersonaFindResult;
import it.webred.ss.data.model.SsAnagrafica;
import it.webred.ss.data.model.SsDiario;
import it.webred.ss.data.model.SsIndirizzo;
import it.webred.ss.data.model.SsInterventiSchede;
import it.webred.ss.data.model.SsIntervento;
import it.webred.ss.data.model.SsInterventoEconomico;
import it.webred.ss.data.model.SsInterventoEconomicoTipo;
import it.webred.ss.data.model.SsMotivazione;
import it.webred.ss.data.model.SsMotivazioniSchede;
import it.webred.ss.data.model.SsRelUffPcontOrg;
import it.webred.ss.data.model.SsRelUffPcontOrgPK;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaAccesso;
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
import it.webred.ss.web.bean.util.PuntoContatto;
import it.webred.ss.web.bean.util.Soggetto;
import it.webred.ss.web.bean.util.UserSearchBean;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.naming.NamingException;

import org.apache.commons.lang3.SerializationUtils;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@ViewScoped
public class NuovaSchedaWizard extends SegretariatoSocBaseBean {
	
	private UserSearchBean userSearchBean;

	private String VER_MAX = "";
	private static final String CITTADINANZA_ITA = "ITALIANA";
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
	private Segnalante segnalante;
	private Segnalato segnalato;
	
	/*JSON SEGNALATO*/
	private IStranieri stranieriMan;
	private IAbitazione abitazioneMan;
	private IFamConviventi famConviventiMan;
	
	/*JSON SERVIZI RICHIESTI*/
	private IIntermediazioneAb intermediazioneAbMan;
	private IOrientamentoLavoro orientamentoLavoroManBean;
	private IMediazioneCult mediazioneCultMan;
	private IOrientamentoIstruzione orientamentoIstruzioneMan;

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
	
	private SsScheda scheda;
	private SsScheda schedaPrecedente;
	private boolean abilitaLoadPrecedente;
	private String statoCartella;

	private InterventiView interventiEconomici;

	private List<Long> schedeId;
	private List<SelectItem> operatori;
	private List<SelectItem> sedi;
	private List<String> modalita;
	private List<String> interlocutori;
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
	private List<SelectItem> tipiScheda;
	private List<String> interventiEconomiciTipi;
	private List<SelectItem> categorieSociali;
	private List<String> motiva = new ArrayList<String>();
	private List<Soggetto> nucleoFamiliare; 

	private boolean percentualeDisabled = true;
	private boolean specificareProblemiDisabled = true;
	private boolean skip;
	
	private Boolean showInterventiEconomici = false;
	
	private String indietroButtonTesto;
	private String indietroButtonLink;

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
				SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
						"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
				List<SsInterventoEconomicoTipo> tipi = schedaService.readInterventiEconomiciTipi(dto);

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

	public void setSpecificareProblemiDisabled(boolean specificareProblemiDisabled) {
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
	
	private IIntermediazioneAb loadSchedaJsonIAb(Long schedaId) throws Exception {
		IIntermediazioneAb man = super.getSchedaJsonIntermediazioneAb(schedaId);
		if (man  == null)
			man = initManIntermediazioneAb();
		return man;
	}

	private IOrientamentoLavoro loadSchedaJsonOrientamentoLavoro(Long schedaId) throws Exception {
		IOrientamentoLavoro man = this.getSchedaJsonOrientamentoLavoro(schedaId);
		if (man == null)
			man = initOrientamentoLavoroManBean();
		return man;
	}

	private IMediazioneCult loadSchedaJsonMediazioneCult(Long schedaId) throws Exception {
		IMediazioneCult man = this.getSchedaJsonMediazioneCult(schedaId);
		if (man == null)
			man = initManMediazioneCult();
		return man;
	}

	private IOrientamentoIstruzione loadSchedaJsonOrientamentoIstruzione(Long schedaId) throws Exception {
		IOrientamentoIstruzione man = this.getSchedaJsonOrientamentoIstruzione(schedaId);
		if (man == null)
			man = initManOrientamentoIstruzione();
		return man;
	}

	public IIntermediazioneAb initManIntermediazioneAb() {
		IIntermediazioneAb man = IntermediazioneManBaseBean.initByVersion(VER_MAX);
		if (scheda != null)
				man.setIdSchedaUdc(scheda.getId());
		return man;
	}
	
	private IOrientamentoLavoro initOrientamentoLavoroManBean() {
		IOrientamentoLavoro man = (IOrientamentoLavoro) OrientamentoLavoroManBaseBean.initByVersion(VER_MAX);
		if (scheda != null)
			man.setIdSchedaUdc(scheda.getId());
		onChangeValorizzaCondLavoro(man); //Valorizzo la combo lavoro con il valore impostato nella scheda Segnalato
		return man;
	}
	
	public IMediazioneCult initManMediazioneCult() {
		IMediazioneCult man = MediazioneCultManBaseBean.initByVersion(VER_MAX);
		if (scheda != null)
			man.setIdSchedaUdc(scheda.getId());
		return man;
	}
	
	public IOrientamentoIstruzione initManOrientamentoIstruzione() {
		IOrientamentoIstruzione man = OrientamentoIstruzioneManBaseBean.initByVersion(VER_MAX);
		if (scheda != null)
			man.setIdSchedaUdc(scheda.getId());
		datiAggiuntiviOrientamentoIstruzione(man); // Valorizzo titolo di studio e conoscenza lingua ai valori impostati nei tab precedenti
		return man;
	}
	
		
	private void initManJsonServiziRichiesti(){
		this.intermediazioneAbMan = this.initManIntermediazioneAb();
		this.orientamentoLavoroManBean = this.initOrientamentoLavoroManBean();
		this.mediazioneCultMan = this.initManMediazioneCult();
		this.orientamentoIstruzioneMan = this.initManOrientamentoIstruzione();
	}

	public NuovaSchedaWizard() {
		
		this.userSearchBean = new UserSearchBean(this);
				
		this.accesso = new Accesso();
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
		this.notaDiarioPrivata = new Nota();
		notaDiarioPrivata.setPubblica(false);
		this.notaDiarioPubblica = new Nota();
		notaDiarioPubblica.setPubblica(true);
		this.tipoScheda = null;

		this.operatori = new ArrayList<SelectItem>();
		this.sedi = new ArrayList<SelectItem>();
		this.modalita = new ArrayList<String>();
		this.interlocutori = new ArrayList<String>();
		this.relazioni = new ArrayList<SelectItem>();
		this.inviatiDa = new ArrayList<SelectItem>();
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
		
		initManJsonServiziRichiesti();
		
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
		try {
			String creaNuova = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(CREA_NUOVA_SCHEDA);
			creaNuovaSchedaDaEsistente = Boolean.valueOf(creaNuova.toLowerCase());
		} catch (Exception e) {	}
		
		indietroButtonLink = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("previousPage");
		
		try {
			PersonaFindResult p = null;
			
			if (creaNuovaSchedaDaEsistente == null || creaNuovaSchedaDaEsistente == false) {				
				if (selectedScheda != null)        initFromSelectedScheda(new Long(selectedScheda));
				else if (selectedSoggetto != null) initFromSelectedSoggetto(new Long(selectedSoggetto));
				else if (selectedCf != null)       initFromSelectedCf(selectedCf);
				else if (idAnagSanitaria != null) {
					p = this.getPersonaDaAnagSanitaria(idAnagSanitaria);
					if (p != null)
						segnalato.initFromAnagraficaSanitaria(p);
				}
				
			} else {
				
				//Matteo Leandri 04/07/2016
				//sono arrivato qui passando come parametro la creazione di una nuova scheda
				
				//con l'id della scheda a disposizione recupero nome cognome e codice fiscale
				SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
						"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
				dto.setObj(new Long(selectedScheda));

				SsScheda ssSchedaSelezionata = schedaService.readScheda(dto);
				SsSchedaSegnalato ssSchedaSegnalato = readSegnalatoById(ssSchedaSelezionata.getSegnalato());
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
	
					if (p != null)
						impostaMedicoPersona(p.getCodiceRegionaleMedico());
				}
			}
			
			loadUltimaSchedaSoggetto(segnalato.getAnagrafica().getCodiceFiscale());
			abilitaLoadPrecedente = selectedScheda==null && this.schedaPrecedente!=null;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");
		}

		// set operatore selezionato
		if (accesso.getOperatore() == null)
			accesso.setOperatore(getUserNameOperatore());
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
		loadUltimaSchedaSoggetto(segnalato.getAnagrafica().getCodiceFiscale());
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
		if (tipiScheda.isEmpty()) {
			try {
				SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
						"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
				List<SsTipoScheda> tipi = schedaService.readTipiScheda(dto);

				for (SsTipoScheda tipo : tipi) {
					if (this.isComune() || !tipo.getSolo_comune())
						tipiScheda.add(new SelectItem(tipo.getId(), tipo.getTipo()));
				}

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				addError("lettura.error");
			}
		}
		return tipiScheda;
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
		if (interventi.isEmpty()) {
			readInterventiFromDB(interventi);
		}
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
						List<AmUser> utenti = bean.getUsersByEnteInizialiGruppo(getUser().getCurrentEnte(), ente);
						for (AmUser u : utenti)
							operatori.add(new SelectItem(u.getName(), this.getCognomeNomeUtente(u.getName())));
					} else {
						List<AmUser> utenti = bean.getUsersByEnteInizialiGruppo(getUser().getCurrentEnte(), "SSOCIALE_OPERATORI");
						if(!utenti.isEmpty()){
							SelectItemGroup parGroup = new SelectItemGroup("Operatori");
							List<SelectItem> lst = new ArrayList<SelectItem>();
							for (AmUser u : utenti)
								lst.add(new SelectItem(u.getName(), this.getCognomeNomeUtente(u.getName())));
							parGroup.setSelectItems(lst.toArray(new SelectItem[lst.size()]));
							operatori.add(parGroup);
						}
						
						utenti = bean.getUsersByEnteInizialiGruppo(getUser().getCurrentEnte(), "SSOCIALE_RESPONSABILI");
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

	public void setInterlocutori(List<String> interlocutori) {
		this.interlocutori = interlocutori;
	}

	public List<String> getInterlocutori() {
		if (interlocutori.isEmpty()) {
			interlocutori.add("Autorità giudiziaria");
			interlocutori.add("Istituzionale");
			interlocutori.add("Utente");
			interlocutori.add("Altro soggetto");
		}
		return interlocutori;
	}

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
				confService = (AccessTableConfigurazioneSessionBeanRemote) 
						ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");  
			
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
				bean = (AccessTableConfigurazioneSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");

				inviatiDa = new ArrayList<SelectItem>();
				CeTBaseObject bo = new CeTBaseObject();
				fillUserData(bo);
				List<CsOSettore> lst = bean.getSettoreAll(bo);
				if (lst != null) {
					for (CsOSettore obj : lst) {
						if (obj.getFlgInviante() != null && obj.getFlgInviante()) {
							SelectItem si = new SelectItem(obj.getId(), obj.getNome());
							si.setDisabled(!obj.getAbilitato());
							inviatiDa.add(si);
						}
					}
				}
			} catch (NamingException e) {
				logger.error(e);
			}
		}

		return inviatiDa;
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
				AccessTableConfigurazioneSessionBeanRemote bean = (AccessTableConfigurazioneSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
				CeTBaseObject cet = new CeTBaseObject();
				fillUserData(cet);
				List<CsTbStatoCivile> list = bean.getStatoCivile(cet);

				if (list != null) {
					for (CsTbStatoCivile stato : list) {
						statiCiviliSelectItem.add(new SelectItem(stato.getCod(), stato.getDescrizione()));
					}
				}
			} catch (NamingException e) {
				this.addError("Errore recupero valori Stati Civili");
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
			accessi.add("Decreto");
			accessi.add("Inviato da");
			accessi.add("Spontaneo");
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
			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(idTipo);
			SsTipoScheda tipo = schedaService.readTipoSchedaById(dto);
			return tipo.getPresa_in_carico();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("salva.error");

			return false;
		}
	}


	private boolean isSchedaCompleta() {
		try {
			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

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
						saved = saveAccessoIntoDB();
					else if (event.getOldStep().equals(SEGNALANTE_TAB))
						saved = saveSegnalanteIntoDB();
					else if (event.getOldStep().equals(SEGNALATO_TAB))
						saved = saveSegnalatoIntoDB();
					else if (event.getOldStep().equals(RIFERIMENTO_TAB))
						saved = saveRiferimentoIntoDB();
					else if (event.getOldStep().equals(MOTIVAZIONE_TAB))
						saved = saveMotivazioneIntoDB();
					else if (event.getOldStep().equals(INTERVENTI_TAB))
						saved = saveInterventiIntoDB();

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
		this.saveAccessoIntoDB();
	}
	public void salvaTabSegnalante(){
		Long idScheda = scheda!=null ? scheda.getId() : null;
		logger.debug("Salva Tab Segnalante - ID_SCHEDA:"+ idScheda);
		this.saveSegnalanteIntoDB();
	}
	public void salvaTabUtente(){
		Long idScheda = scheda!=null ? scheda.getId() : null;
		logger.debug("Salva Tab Segnalato - ID_SCHEDA:"+ idScheda);
		this.saveSegnalatoIntoDB();
	}
	public void salvaTabRiferimento(){
		Long idScheda = scheda!=null ? scheda.getId() : null;
		logger.debug("Salva Tab Riferimento - ID_SCHEDA:"+ idScheda);
		this.saveRiferimentoIntoDB();
	}
	public void salvaTabBisogni(){
		Long idScheda = scheda!=null ? scheda.getId() : null;
		logger.debug("Salva Tab Bisogni - ID_SCHEDA:"+ idScheda);
		this.saveMotivazioneIntoDB();
	}
	public void salvaTabServiziRichiesti(){
		Long idScheda = scheda!=null ? scheda.getId() : null;
		logger.debug("Salva Tab Servizi Richiesti - ID_SCHEDA:"+ idScheda);
		this.saveInterventiIntoDB();
	}
	
	public void salvaSchedaCompletaUDC() {
		Long idScheda = scheda!=null ? scheda.getId() : null;
		logger.debug("SalvaSchedaCompletaUDC - ID_SCHEDA:"+idScheda);
		if (!validaPresaInCarico())
			addError("salva.validate.error");
		else {
			boolean cartella = true;
			
			try {
				
				String statoScheda = null;
				
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
				
				
				if (statoScheda==null || statoScheda.isEmpty()) {
					
					saveConsensoPrivacy();
					
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
							
							if (isPropostaPresaInCarico(tipoScheda)) {
								if (categoriaSociale != null)
									cartellaDto.setIdCatSociale(categoriaSociale);
								schedaSegrService.saveSchedaSegr(cartellaDto);
							} else if (statoScheda==null || statoScheda.isEmpty()) {
								schedaSegrService.deleteSchedaSegr(cartellaDto);
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
				} else {
					addError("policy.error.cartella.state");
				}
			} catch (Exception e) {
				logger.error(e);
				addError("salva.error");
			}
		}

	}

	private boolean setSchedaCompleta(boolean completa) {
		try {
			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

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

	public void resetCatSociale() {
		if (tipoScheda == null || (tipoScheda.intValue() != 3 && tipoScheda.intValue() != 6)) {
			categoriaSociale = null;
		}
	}

	private boolean saveInterventiIntoDB() {
		boolean salvato = true;
		try {
			
			if(!validaServizi())
				return false;
			
			SsSchedaInterventi dataModel = new SsSchedaInterventi();
			intervento.fillModel(dataModel);

			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

			BaseDTO dto = new BaseDTO();
			fillUserData(dto);

			boolean reload = false;
			intermediazioneAbMan.setIdSchedaUdc(scheda.getId());
			orientamentoLavoroManBean.setIdSchedaUdc(scheda.getId());
			mediazioneCultMan.setIdSchedaUdc(scheda.getId());
			orientamentoIstruzioneMan.setIdSchedaUdc(scheda.getId());
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
			if (isEnteEsterno() && !interventoEconomicoTipo.isEmpty() && !interventoEconomicoImporto.isEmpty()) {
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

			if(this.isVisPanelStranieri()){
				salvaSchedaIAb(salvato);
				salvaSchedaOrientamentoLavoro(salvato);
				salvato &= salvaSchedaMediazioneCult();
				salvato &= salvaSchedaOrientamentoIstruzione();
			}
			return salvato;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("salva.error");
			salvato = false;
			return salvato;
		}
	}
	
	private void saveConsensoPrivacy(){
		try {
			
			if(privacy==null && this.privacySottoscrivi){
				SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
						"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
	
				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
	
				SsSchedaPrivacy sp = new SsSchedaPrivacy();
				SsSchedaPrivacyPK pk = new SsSchedaPrivacyPK();
				pk.setCf(segnalato.getAnagrafica().getCodiceFiscale());
				pk.setOrganizzazioneId(accesso.getPuntoContatto().getOrganizzazione().getId());
				
				sp.setSchedaId(scheda.getId());
				sp.setDtIns(privacyDate!=null ? privacyDate : accesso.getDataAccesso());
				sp.setId(pk);
				
				dto.setObj(sp);
				
				schedaService.saveConsensoPrivacy(dto);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private boolean saveMotivazioneIntoDB() {
		resetComboMotivazioni();
		try {
			
			if (!validaBisogni())
				return false;
			
			SsSchedaMotivazione dataModel = new SsSchedaMotivazione();
			motivazione.fillModel(dataModel);

			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

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
			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

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
			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

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

	private boolean saveMotivazioniScheda(List<String> motivazioni, SsSchedaMotivazione scheda) {
		showInterventiEconomici = false;
		try {
			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

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
				SsAnagrafica anagrafica = readAnagraficaById(segnalato.getAnagrafica().getId());
				for (SsInterventoEconomico i : this.readInterventiEconomici(anagrafica))
					interventi.add(new InterventoEconomico(i.getId(), i.getImporto(), i.getTipo().getTipo(), i.getErogante(), i.getData()));
				interventiEconomici.setInterventi(interventi);
			}

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("salva.error");

			return false;
		}

	}

	private boolean saveInterventiScheda(List<String> interventi, SsSchedaInterventi intervento) {
		try {
			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

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

			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

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

	private boolean saveAccessoIntoDB() {
		try {

			SsSchedaAccesso accessoModel = new SsSchedaAccesso();
			
			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

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
				scheda = new SsScheda();
				scheda.setAccesso(accessoModel);
				scheda.setCompleta(false);
				scheda.setEliminata(false);
				scheda.setEsterna(this.isEnteEsterno());

				/*
				 * dto.setObj(scheda); scheda=schedaService.saveScheda(dto);
				 */

				if (segnalato.getAnagrafica().getId() == null && segnalato.getAnagrafica().getCodiceFiscale() != null) { // anagrafica
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
					segnalato.getResidenza().fillModel(residenzaModel);
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
				 *  http://progetti.asc.webred.it/browse/SISO-446
				 */
				if (scheda!=null && scheda.getAccesso()!=null) { 
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

	private SsAnagrafica readAnagraficaById(Long id) {
		try {
			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

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

	private boolean saveSegnalanteIntoDB() {
		try {
			SsSchedaSegnalante segnalanteModel = new SsSchedaSegnalante();
			segnalante.fillModel(segnalanteModel);

			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

			BaseDTO dto = new BaseDTO();
			fillUserData(dto);

			if (segnalante.getId() == null) { // insert

				scheda.setSegnalante(segnalanteModel);
				dto.setObj(scheda);
				scheda = schedaService.saveScheda(dto);

				segnalante.setId(scheda.getSegnalante().getId());

			} else {// update
				dto.setObj(segnalanteModel);
				schedaService.updateSegnalante(dto);
			}

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("salva.error");

			return false;
		}
	}

	private boolean saveSegnalatoIntoDB() {
		boolean salvato = true;
		try {

			if (!validaSegnalato())
				return false;

			SsSchedaSegnalato segnalatoModel = new SsSchedaSegnalato();
			segnalato.fillModel(segnalatoModel);

			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

			BaseDTO dto = new BaseDTO();
			fillUserData(dto);

			boolean reload = false;
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
				schedaService.updateSegnalato(dto);
			}

			// save/update residenza e domicilio
			SsIndirizzo residenza = new SsIndirizzo();
			segnalato.getResidenza().fillModel(residenza);

			if (segnalato.getDomicilio().isComeResidenza())
				segnalato.getDomicilio().initFromIndirizzo(segnalato.getResidenza());
			SsIndirizzo domicilio = new SsIndirizzo();
			segnalato.getDomicilio().fillModel(domicilio);

			segnalatoModel.setResidenza(residenza);
			segnalatoModel.setDomicilio(domicilio);
			dto.setObj(segnalatoModel);
			segnalatoModel = schedaService.saveSegnalato(dto);

			// Recuper gli Id Salvati
			if (segnalato.getResidenza().getId() == null)
				segnalato.getResidenza().setId(segnalatoModel.getResidenza().getId());

			if (segnalato.getDomicilio().getId() == null)
				segnalato.getDomicilio().setId(segnalatoModel.getDomicilio().getId());

			if(this.isVisPanelStranieri()){
				/*VALORIZZAZIONE SCHEDA ID - JSON*/
				setSchedaIdJson(scheda.getId());
				
				salvaSchedaStranieri(salvato, reload);
				salvaSchedaAbitazione(salvato, reload);
				salvaSchedaFamConviventi(salvato, reload);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("salva.error");
			salvato = false;
		}
		return salvato;
	}
	
	private boolean schedaStranieriRichiesta(){
		return !CITTADINANZA_ITA.equals(segnalato.getAnagrafica().getCittadinanza()) 
		    && !CITTADINANZA_ITA.equals(segnalato.getAnagrafica().getCittadinanza2());	
	}
	
	private void eliminaDiariScheda(Long schedaId, int tipoDiario) throws Exception{
		try {
			if (scheda.getId() != null && scheda.getId() > 0) {
				AccessTableDiarioSessionBeanRemote diarioService = super.getDiarioCsBean();
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
	
	
	private void salvaSchedaStranieri(boolean salvato, boolean reload) throws Exception {
		boolean stranieriRichiesta = schedaStranieriRichiesta();
		if(stranieriMan.isNew()|| !stranieriRichiesta)
			eliminaDiariScheda(scheda.getId(), DataModelCostanti.TipoDiario.STRANIERI_ID);
		
		// Salvo il PANNELLO STRANIERI
		if (stranieriRichiesta) 
			salvato &= stranieriMan.save();
		else	
			stranieriMan = initManStranieri();

		if (salvato && (reload || stranieriMan.isNew()))
			stranieriMan = loadSchedaJsonStranieri(scheda.getId());
	}

	private void salvaSchedaAbitazione(boolean salvato, boolean reload) throws Exception {
		if(abitazioneMan.isNew())
			eliminaDiariScheda(scheda.getId(), DataModelCostanti.TipoDiario.ABITAZIONE_ID);
		
		salvato = salvato && abitazioneMan.save();
		if (salvato && (reload || abitazioneMan.isNew()))
			abitazioneMan = loadSchedaJsonAbitazione(scheda.getId());
	}
	
	private void salvaSchedaFamConviventi(boolean salvato, boolean reload) throws Exception {
		if(famConviventiMan.isNew())
			eliminaDiariScheda(scheda.getId(), DataModelCostanti.TipoDiario.FAMIGLIA_ID);
		
		salvato = salvato && famConviventiMan.save();
		if (salvato && (reload || famConviventiMan.isNew()))
			famConviventiMan = loadSchedaJsonFamConviventi(scheda.getId());
	}

	
	private void salvaSchedaIAb(boolean salvato) throws Exception {
		// Salvo il PANNELLO // Se selezionato nelle check, altrimenti elimino
		// tutto
		String idInterventoIAB = "0";
		boolean pannelloRichiesto = true; //intervento.getInterventi().contains(idInterventoIAB)
		
		if(intermediazioneAbMan.isNew() || !pannelloRichiesto)
			eliminaDiariScheda(scheda.getId(),DataModelCostanti.TipoDiario.INTERMEDIAZIONE_AB_ID);
		
		if (pannelloRichiesto)
			salvato &= this.intermediazioneAbMan.save();
		else 
			intermediazioneAbMan = initManIntermediazioneAb();

		if (salvato && this.intermediazioneAbMan.isNew())
			intermediazioneAbMan = loadSchedaJsonIAb(scheda.getId());
	}
	
	private void salvaSchedaOrientamentoLavoro(boolean salvato) throws Exception {
		boolean pannelloRichiesto = true; //intervento.getInterventi().contains(idInterventoOrientamento)
		if(pannelloRichiesto)
			salvato = orientamentoLavoroManBean.save();
		else{
			if (scheda.getId() != null && scheda.getId() > 0) {
				eliminaDiariScheda(scheda.getId(),DataModelCostanti.TipoDiario.ORIENTAMENTO_LAVORO_ID);
				orientamentoLavoroManBean = initOrientamentoLavoroManBean();
			}
		}
		
		if (this.orientamentoLavoroManBean.isNew())
			orientamentoLavoroManBean = loadSchedaJsonOrientamentoLavoro(scheda.getId());
		
	}
	
	private boolean salvaSchedaMediazioneCult() throws Exception {
		boolean salvato = true;
		boolean pannelloRichiesto = true; // intervento.getInterventi().contains(idInterventoOrientamento)
		if (pannelloRichiesto)
			salvato = this.mediazioneCultMan.save();
		else {
			if (scheda.getId() != null && scheda.getId() > 0) {
				eliminaDiariScheda(scheda.getId(), DataModelCostanti.TipoDiario.MEDIAZIONE_CULT_ID);
				mediazioneCultMan  = initManMediazioneCult();
			}
		}

		if (salvato && this.mediazioneCultMan.isNew())
			mediazioneCultMan = loadSchedaJsonMediazioneCult(scheda.getId());

		return salvato;
	}
	
	private boolean salvaSchedaOrientamentoIstruzione() throws Exception {
		boolean salvato = true;
		boolean pannelloRichiesto = true; // intervento.getInterventi().contains(idInterventoOrientamento)
		if (pannelloRichiesto)
			salvato = this.orientamentoIstruzioneMan.save();
		else {
			if (scheda.getId() != null && scheda.getId() > 0) {
				eliminaDiariScheda(scheda.getId(), DataModelCostanti.TipoDiario.MEDIAZIONE_CULT_ID);
				orientamentoIstruzioneMan = initManOrientamentoIstruzione();
			}
		}

		if (salvato && this.orientamentoIstruzioneMan.isNew())
			orientamentoIstruzioneMan = loadSchedaJsonOrientamentoIstruzione(scheda.getId());

		return salvato;
	}
	
	

	private boolean validaPresaInCarico() {
		boolean ok = true;

		if (isPropostaPresaInCarico(tipoScheda) && (categoriaSociale == null || categoriaSociale == 0))
			ok = false;

		return ok;
	}

	private boolean validaSegnalato() {
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
		if (anagrafica.getComuneNazioneNascitaMan().getComuneNascitaMan().getComune() == null && anagrafica.getComuneNazioneNascitaMan().getNazioneNascitaMan().getNazione() == null) {
			addErrorMessage("Comune/Stato estero di nascita è un campo obbligatorio", "");
			ok = false;
		}

		if (anagrafica.getCodiceFiscale() == null || "".equals(anagrafica.getCodiceFiscale().trim())) {
			addErrorMessage("Codice fiscale è un campo obbligatorio", "");
			ok = false;
		}
		if (anagrafica.getStatoCivile() == null || "".equals(anagrafica.getStatoCivile().trim())) {
			addErrorMessage("Stato civile è un campo obbligatorio", "");
			ok = false;
		}
		if (anagrafica.getCittadinanza() == null || "".equals(anagrafica.getCittadinanza().trim())) {
			addErrorMessage("Cittadinanza è un campo obbligatorio", "");
			ok = false;
		}
		if(anagrafica.getDatiSesso().getSesso()==null || anagrafica.getDatiSesso().getSesso().isEmpty()) {
			addErrorMessage("Sesso è un campo obbligatorio", "");
			ok = false;
		}
		
		if((segnalato.getTel()==null || segnalato.getTel().isEmpty()) && (segnalato.getCel()==null || segnalato.getCel().isEmpty())) {
			addErrorMessage("Inserire almeno un recapito telefonico", "");
			ok = false;
		}

		Indirizzo residenza = segnalato.getResidenza();
		if (residenza != null) {
			if (residenza.getComune() == null || residenza.getComune().getCodIstatComune() == null ||
					"".equals(residenza.getComune().getCodIstatComune().trim())) {
				addErrorMessage("Comune di Residenza è un campo obbligatorio", "");
				ok = false;
			}
			if (residenza.getVia() == null || "".equals(residenza.getVia().trim())) {
				addErrorMessage("Via di Residenza è un campo obbligatorio", "");
				ok = false;
			}
		} else {
			addErrorMessage("Residenza è un campo obbligatorio", "");
			ok = false;
		}

		if(!segnalato.getFormLavoroMan().validaData())
			ok=false;
		
		if(this.isVisPanelStranieri()){
			boolean utenteInterlocutore = "Utente".equalsIgnoreCase(this.accesso.getInterlocutore());
			boolean cfgStranieriUfficio = this.accesso.getPuntoContatto().getUfficio().isReqStranieri();
			
			stranieriMan.setValidaConoscenzaLingua(utenteInterlocutore);
			stranieriMan.setValidaCampiImmigrazione(cfgStranieriUfficio);//Status, Permesso, Paese provenienza
			
			if(schedaStranieriRichiesta()  && !stranieriMan.validaData()) 
				ok= false;
				
			if(!abitazioneMan.validaData())
				ok=false;
			if(!famConviventiMan.validaData())
				ok=false;
		}
		
		return ok;
	}
	
	private boolean maggiorenne(Date dataRif, Date datanascita){
		boolean mag=false;
		if(dataRif!=null && datanascita!=null){
			Calendar calRif=Calendar.getInstance();
			calRif.setTime(dataRif);
			
			Calendar calNas=Calendar.getInstance();
			calNas.setTime(datanascita);
			
			int diffAnni = calRif.get(Calendar.YEAR)-calNas.get(Calendar.YEAR);
			int monthRif = calRif.get(Calendar.MONTH);
			int monthNas = calNas.get(Calendar.MONTH);
			int dayRif =   calRif.get(Calendar.DAY_OF_MONTH);
			int dayBirth = calNas.get(Calendar.DAY_OF_MONTH);
			
			if(diffAnni>18)
				mag=true;
			else if(diffAnni==18 && monthRif>monthNas)
				mag=true;
			else if(diffAnni==18 && monthRif==monthNas && dayRif>=dayBirth)
				mag = true;
			
		}
		return mag;
	}
	
	private boolean validaBisogni(){
		
		boolean ok = true;
		boolean maggiorenne = maggiorenne(scheda.getAccesso().getData(), segnalato.getAnagrafica().getDataNascita());
		String altro = motivazione.getAltro()!=null ? motivazione.getAltro().trim() : "";
		if (!maggiorenne && (altro.isEmpty())) {
			addErrorMessage("Per soggetti minorenni, specificare meglio il bisogno", "");
			ok = false;
		}
		
		boolean bisogniPresenti = false;
		for (int i = 1; i <= 5; i++)
			bisogniPresenti = bisogniPresenti | motivazione.getMotivoClasseI(i).getMotiviSelected().isEmpty();
		
		boolean datiAssenti = altro.isEmpty() && !bisogniPresenti;
		if(accesso.getPuntoContatto().getUfficio().isReqBisogni() && datiAssenti){
			addErrorMessage("Inserire i bisogni ESPRESSI dall'utente o dal segnalante", "");
			ok = false;
		}
		
		return ok;
	}
	
	private boolean validaServizi(){
		boolean ok = true;
		String altro = intervento.getAltro()!=null ? intervento.getAltro().trim() : "";
		boolean serviziMancanti = intervento.getInterventi().isEmpty(); //TODO: Verificare che gli altri interventi siano presenti
		if(accesso.getPuntoContatto().getUfficio().isReqServizi() && (altro.isEmpty() && serviziMancanti)){
			addErrorMessage("Inserire i servizi richiesti", "");
			ok = false;
		}
		
		return ok;
	}

	private boolean initDiarioSociale(Segnalato segnalato) {
		try {
			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

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
					if (canReadNotaDiario(nota))
						note.add(new Nota(nota, this.getCognomeNomeUtente(nota.getAutore())));
				diarioSociale.populateNote(note);
			}

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");

			return false;
		}
	}

	private boolean canReadNotaDiario(SsDiario nota) {
		if (nota.getPubblica())
			return true;
		if (nota.getAutore().equals(this.getUserNameOperatore()))
			return true;
		if (canReadDiario() && nota.getEnte().getId() == this.getPreselectedPContatto().getOrganizzazione().getId())
			return true;

		return false;
	}

	private boolean initFromSelectedScheda(Long id) {
		try {
			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(id);

			scheda = schedaService.readScheda(dto);

			accesso.initFromModel(scheda.getAccesso());
			segnalante.initFromModel(scheda.getSegnalante(), false);
			segnalato.initFromModel(readSegnalatoById(scheda.getSegnalato()), false);
			if (schedaStranieriRichiesta())
				stranieriMan = loadSchedaJsonStranieri(id);
            abitazioneMan = loadSchedaJsonAbitazione(id);
            famConviventiMan = loadSchedaJsonFamConviventi(id);

			riferimento.initFromModel(scheda.getRiferimento(),false);

			List<SsMotivazioniSchede> schedaLstMotivi = new ArrayList<SsMotivazioniSchede>();
			if (scheda.getMotivazione() != null)
				schedaLstMotivi = readMotivazioniScheda(scheda.getMotivazione());
			motivazione.initFromModel(scheda.getMotivazione(), schedaLstMotivi);

			
			intervento.initFromModel(scheda.getInterventi());
			if (scheda.getInterventi() != null)
				readInterventiScheda(scheda.getInterventi(), intervento.getInterventi());
			
			intermediazioneAbMan = loadSchedaJsonIAb(id);
			orientamentoLavoroManBean = loadSchedaJsonOrientamentoLavoro(id);
			mediazioneCultMan = loadSchedaJsonMediazioneCult(id);
			orientamentoIstruzioneMan = loadSchedaJsonOrientamentoIstruzione(scheda.getId());

			tipoScheda = scheda.getTipo();

			inizializzaCategoria(id);

			initDiarioSociale(segnalato);

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");

			return false;
		}
	}

	private void initStatoPrivacy() {
		privacy = null;
		
		if(segnalato!=null && segnalato.getAnagrafica()!=null && accesso!=null){
			String codfisc = segnalato.getAnagrafica().getCodiceFiscale();
			Long organizzazioneId = accesso.getPuntoContatto().getOrganizzazione().getId();
			if(codfisc!=null && !codfisc.isEmpty() && organizzazioneId!=null && organizzazioneId>0){
				
				try{
					SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
							"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
			
					BaseDTO dto = new BaseDTO();
					fillUserData(dto);
					
					SsSchedaPrivacyPK pk = new SsSchedaPrivacyPK();
					pk.setCf(codfisc);
					pk.setOrganizzazioneId(organizzazioneId);
					dto.setObj(pk);
					privacy = schedaService.findSchedaPrivacyById(dto);
					
				}catch(Exception e){
					logger.error(e);
					addErrorMessage("lettura.error", "Non è stato possibile verificare la sottoscrizione del modulo privacy per il soggetto");
				}
			}
		}
		
		if(privacy==null && accesso!=null && accesso.getDataAccesso()!=null)
			this.privacyDate = accesso.getDataAccesso();
	}
	
	private void inizializzaCartella() {
		statoCartella = null;
		try {
			AccessTableIterStepSessionBeanRemote schedaService = (AccessTableIterStepSessionBeanRemote)
					ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableIterStepSessionBean");

			// Recupero lo stato della cartella associata alla scheda (per id)
			AccessTableSchedaSegrSessionBeanRemote schedaSS = (AccessTableSchedaSegrSessionBeanRemote)
					ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSegrSessionBean");

			it.webred.cs.csa.ejb.dto.BaseDTO dto2 = new it.webred.cs.csa.ejb.dto.BaseDTO();
			fillUserData(dto2);
			dto2.setObj(scheda.getId());
			statoCartella = schedaSS.findStatoSchedaSegrById(dto2);

			it.webred.cs.csa.ejb.dto.BaseDTO dto1 = new it.webred.cs.csa.ejb.dto.BaseDTO();
			fillUserData(dto1);
			dto1.setObj(segnalato.getAnagrafica().getCodiceFiscale());
			List<StatoCartellaDTO> listaStati = schedaService.getStatoCasoBySoggetto(dto1);

			if (listaStati != null && listaStati.size() > 0) {
				for (StatoCartellaDTO st : listaStati) {

					if (statoCartella != null)
						statoCartella += "<br/>";
					else
						statoCartella = "<br/>";

					statoCartella += st.getNomeStato() + "<br/>";
					statoCartella += st.getDataPassaggioStato() != null ? "<b>il</b> " + ddMMyyyyhhmmss.format(st.getDataPassaggioStato()) + "<br/>" : "<br/>";
					if (st.getuNameResponsabile() != null)
						statoCartella += "<b>Responsabile:</b>" + getCognomeNomeUtente(st.getuNameResponsabile()) + "<br/>";

					String catSocs = "";
					if (st.getAreeTarget() != null) {
						catSocs = "<b>Aree Target: </b>";
						for (String cs : st.getAreeTarget())
							catSocs += cs + " ";
						statoCartella += catSocs + "<br/>";
					}

				}
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
			AccessTableSchedaSegrSessionBeanRemote schedaB = (AccessTableSchedaSegrSessionBeanRemote) ClientUtility.getEjbInterface(
					"CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSegrSessionBean");

			it.webred.cs.csa.ejb.dto.BaseDTO dto2 = new it.webred.cs.csa.ejb.dto.BaseDTO();
			fillUserData(dto2);
			dto2.setObj(id);
			categoriaSociale = schedaB.findCategoriaSchedaSegrById(dto2);

		} catch (Exception e) {
			logger.error("Impossibile recuperare la categoria sociale assegnata " + e.getMessage(), e);
			addError("lettura.error", "Impossibile recuperare la categoria sociale assegnata");
		}
	}

	private boolean initFromSelectedSoggetto(Long anagraficaId) {
		SsAnagrafica anagrafica = readAnagraficaById(anagraficaId);
		if (anagrafica != null)
			segnalato.getAnagrafica().initFromAnagrafica(anagrafica, false);
		
		return true;
	}

	private boolean initFromSelectedCf(String cf) {
		SitDPersona soggetto = readSoggettoFromAnagrafeByCf(cf);
		if (soggetto != null) {

			IndirizzoAnagrafeDTO indirizzo = getResidenzaFromAnagrafe(soggetto);
			segnalato.setResidenza(indirizzo, getUser().getCurrentEnte());

			// SitComune luogoNascita =
			// getComuneFromIdExt(soggetto.getIdExtComuneNascita());

			ComponenteFamigliaDTO compDto = new ComponenteFamigliaDTO();
			compDto.setPersona(soggetto);
			fillUserData(compDto);
			try {

				AnagrafeService anagrafeService = (AnagrafeService) ClientUtility.getEjbInterface(
						"CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");

				compDto = anagrafeService.fillInfoAggiuntiveComponente(compDto);

			} catch (NamingException e) {
			}

			// Recupero l'id della tabella CS a partire dall'ID anagrafico
			CsTbStatoCivile statoCivile = getStatoCivileByIdOrigCeT(soggetto.getStatoCivile());
			String statoCivileString = statoCivile!=null ? statoCivile.getDescrizione() : null;
			segnalato.getAnagrafica().initFromAnagrafe(soggetto, compDto, statoCivileString);


			initDiarioSociale(segnalato);

			return true;
		}
		return false;
	}

	public CsTbStatoCivile getStatoCivileByIdOrigCeT(String statoCivile) {
		try {

			if (statoCivile != null) {
				AccessTableConfigurazioneSessionBeanRemote configurazioneService = (AccessTableConfigurazioneSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
				it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
				this.fillUserData(dto);
				dto.setObj(statoCivile);
				CsTbStatoCivile sc = configurazioneService.getStatoCivileByIdOrigCet(dto);
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
			AnagrafeService anagrafeService = (AnagrafeService) ClientUtility.getEjbInterface(
					"CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");
			List<SitDPersona> results = anagrafeService.getListaPersoneByCF(dto);
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
			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

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
			AnagrafeService anagrafeService = (AnagrafeService) ClientUtility.getEjbInterface(
					"CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");

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

	private List<SsInterventoEconomico> readInterventiEconomici(SsAnagrafica anagrafica) {
		try {
			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

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

				List<SsAnagrafica> anagrafiche = schedaService.readAnagraficheByCf(dto);
				for (SsAnagrafica a : anagrafiche) {
					dto.setObj(a);
					interventi.addAll(schedaService.readInterventiEconomici(dto));
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

			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
			BaseDTO dto = new BaseDTO();
			fillUserData(dto);

			List<SsMotivazione> resultclassi = this.motivazioniClasse.get(n.toString());

			if (resultclassi == null || resultclassi.isEmpty()) {
				dto.setObj(n);
				resultclassi = schedaService.readMotivazioniByClasId(dto);
				this.motivazioniClasse.put(n.toString(), resultclassi);
			}

			MotivoClasse mc = motivazione.getMotivoClasseI(n.intValue());
			List<String> lstSel = mc.getMotiviSelected();
			for (SsMotivazione motivo : resultclassi) {
				dto.setObj(motivo.getId());
				SelectItem si = new SelectItem(motivo.getId(), motivo.getMotivo());
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
			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			motiva = new ArrayList<String>();

			for (int i = 0; i < list.size(); i++) {
				List<SsMotivazione> results = schedaService.readMotivazioni(dto);
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

	private List<SsMotivazioniSchede> readMotivazioniScheda(SsSchedaMotivazione scheda) {
		try {
			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

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
			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(scheda);
			List<SsInterventiSchede> results = schedaService.readInterventiScheda(dto);

			interventi.clear();

			for (SsInterventiSchede interventoScheda : results)
				interventi.add(interventoScheda.getIntervento().getId() + "");

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");

			return false;
		}
	}

	private boolean readInterventiFromDB(List<SelectItem> interventi) {
		try {
			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

			BaseDTO dto = new BaseDTO();
			fillUserData(dto);

			List<SsIntervento> results = schedaService.readInterventi(dto);

			for (SsIntervento intervento : results)
				interventi.add(new SelectItem(intervento.getId(), intervento.getIntervento()));

			return true;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");

			return false;
		}
	}

	private SsSchedaSegnalato readSegnalatoById(Long id) {
		try {
			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

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
			AccessTableCatSocialeSessionBeanRemote catSocService = (AccessTableCatSocialeSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableCatSocialeSessionBean");
			CeTBaseObject cet = new CeTBaseObject();
			fillUserData(cet);
			List<CsCCategoriaSociale> list = catSocService.getCategorieSocialiAll(cet);
			for (CsCCategoriaSociale c : list) {
				SelectItem si = new SelectItem(c.getId(), c.getDescrizione().replace("_", " "));
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
				AccessTableMediciSessionBeanRemote bean = (AccessTableMediciSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableMediciSessionBean");
				dto.setObj(query);
				dto.setObj2(new Integer(10));
				List<CsCMedico> lstMedici = bean.searchMedici(dto);
				for (CsCMedico c : lstMedici)
					results.add((c.getCognome() + " " + c.getNome()).toUpperCase());
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
				List<CsTbCittadinanzaAcq> lst = this.getCsConfigurazioneService().getCittadinanzeAcquisite(cet);
				for (CsTbCittadinanzaAcq p : lst) {
					SelectItem si = new SelectItem(p.getId(), p.getDescrizione());
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

	public IOrientamentoLavoro getOrientamentoLavoroManBean() {
		return orientamentoLavoroManBean;
	}

	public void setOrientamentoLavoroManBean(IOrientamentoLavoro orientamentoLavoroManBean) {
		this.orientamentoLavoroManBean = orientamentoLavoroManBean;
	}

	public static String getCittadinanzaIta() {
		return CITTADINANZA_ITA;
	}

	public void changeCittadinanza() {
		if (CITTADINANZA_ITA.equals(segnalato.getAnagrafica().getCittadinanza())) {
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
		return intermediazioneAbMan;
	}

	public void setIntermediazioneAbMan(IIntermediazioneAb intermediazioneAbMan) {
		this.intermediazioneAbMan = intermediazioneAbMan;
	}

	public IMediazioneCult getMediazioneCultMan() {
		return mediazioneCultMan;
	}

	public void setMediazioneCultMan(IMediazioneCult mediazioneCultMan) {
		this.mediazioneCultMan = mediazioneCultMan;
	}

	public void onChangeValorizzaCondLavoro(IOrientamentoLavoro man){
		if(segnalato!=null && segnalato.getFormLavoroMan()!=null){
			BigDecimal idLavoro = segnalato.getFormLavoroMan().getIdCondLavorativa();
		    if(idLavoro!=null) 
		    	man.preValorizzaLavoro(idLavoro);
		}
	}
	
	public void onChangeValorizzaTitStudio(IOrientamentoIstruzione man){
		BigDecimal idTitoloStudio = null;
		List<SelectItem> lstTitoliStudio = null;

		if (segnalato != null && segnalato.getFormLavoroMan() != null) {
			idTitoloStudio = segnalato.getFormLavoroMan().getIdTitoloStudio();
			lstTitoliStudio = segnalato.getFormLavoroMan().getLstTitoliStudio();
		}
		man.preValorizzaDati(idTitoloStudio, lstTitoliStudio, null);
	}
	
	public void onChangeCondLavoro(){
		onChangeValorizzaCondLavoro(orientamentoLavoroManBean);
	}
	

	public void datiAggiuntiviOrientamentoIstruzione(IOrientamentoIstruzione man) {
		onChangeValorizzaTitStudio(man);
		onChangeConoscenzaLinguaItaliana();
	}	
	
	public void onChangeTitoloStudio() {
		onChangeValorizzaTitStudio(orientamentoIstruzioneMan);
	}

	// FIXME
	// TODO
	public void onChangeConoscenzaLinguaItaliana() {
		// String linguaItaLivello = null;
		//
		// if (stranieriMan != null ) {
		// linguaItaLivello = stranieriMan.getJsonCurrent().getLiguaItaLivello() ;
		// }
		// orientamentoIstruzioneMan.preValorizzaDati(null, null, linguaItaLivello);
	}

	public IOrientamentoIstruzione getOrientamentoIstruzioneMan() {
		return orientamentoIstruzioneMan;
	}

	public void setOrientamentoIstruzioneMan(IOrientamentoIstruzione orientamentoIstruzioneMan) {
		this.orientamentoIstruzioneMan = orientamentoIstruzioneMan;
	}

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
	
	public void setSchedaIdJson(Long idUdc){
		stranieriMan.setIdSchedaUdc(idUdc);
		abitazioneMan.setIdSchedaUdc(idUdc);
		famConviventiMan.setIdSchedaUdc(idUdc);
	}
	
	private void loadUltimaSchedaSoggetto(String codiceFiscale) {
		if(codiceFiscale!=null && !codiceFiscale.trim().isEmpty()){
			SsSchedaSessionBeanRemote schedaService;
			try {
				schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
						"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
			
				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
				dto.setObj(codiceFiscale);
		
				List<SsScheda> schedePrecedenti = schedaService.readSchedeByCF(dto);
				if(!schedePrecedenti.isEmpty())
					this.schedaPrecedente=schedePrecedenti.get(0);
				
			} catch (NamingException e) {
				logger.error("Errore caricamento ultima scheda del soggetto:"+codiceFiscale,e);
			}
		}
	}
	
	public void valorizzaSegnalanteDaUltima(){
		SsSchedaSegnalante s = this.schedaPrecedente.getSegnalante();
		segnalante.initFromModel(s,true);
	}
	
	public void valorizzaSegnalatoDaUltima(){
		Long idSegnalato = this.schedaPrecedente.getSegnalato();
		SsSchedaSegnalato ss = null;
		if(idSegnalato!=null){
			SsSchedaSessionBeanRemote schedaService;
			try {
				schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
						"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
			
				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
				dto.setObj(idSegnalato);
				
				ss = schedaService.readSegnalatoById(dto);
				
				if(ss!=null){
					
					segnalato.initFromModel(ss, true);
					
					IStranieri s = this.nuovaDaSchedaJsonStranieri(schedaPrecedente.getId());
					IAbitazione a = this.nuovaDaSchedaJsonAbitazione(schedaPrecedente.getId());
					IFamConviventi f = this.nuovaDaSchedaJsonFamiliari(schedaPrecedente.getId());
					
					if(s!=null){
						stranieriMan = s;
						stranieriMan.setIdSchedaUdc(scheda.getId());
					}
					if(a!=null){ 
						abitazioneMan = a;
						abitazioneMan.setIdSchedaUdc(scheda.getId());
					}
					if(f!=null){ 
						famConviventiMan = f;
						famConviventiMan.setIdSchedaUdc(scheda.getId());
					}
					
				}
				
			} catch (Exception e) {
				logger.error("Errore caricamento Segnalato:"+idSegnalato,e);
			}
	
		}
	}
	
	public void valorizzaRiferimentoDaUltima(){
		SsSchedaRiferimento r = this.schedaPrecedente.getRiferimento();
		riferimento.initFromModel(r, true);
	}
	public void valorizzaMotivazioniDaUltima(){
		SsSchedaMotivazione m = this.schedaPrecedente.getMotivazione();
		this.motivazione.setAltro(m.getAltro());
		List<SsMotivazioniSchede> lst = this.readMotivazioniScheda(m);
		motivazione.initMotiviClasseFromModel(lst);	
	}
	
	public void valorizzaInterventiDaUltima(){
		SsSchedaInterventi i = this.schedaPrecedente.getInterventi();
		this.intervento.setAltro(i.getAltro());
		List<String> lstInterventi = new ArrayList<String>();
		readInterventiScheda(i, lstInterventi);
		intervento.setInterventi(lstInterventi);
		
		IIntermediazioneAb iab = this.nuovaDaSchedaJsonIntermediazioneAb(schedaPrecedente.getId());
		IMediazioneCult mc = this.nuovaDaSchedaJsonMediazioneCulturale(schedaPrecedente.getId());
		IOrientamentoIstruzione oi = this.nuovaDaSchedaJsonOrientamentoIstruzione(schedaPrecedente.getId());
		IOrientamentoLavoro ol = this.nuovaDaSchedaJsonOrientamentoLavoro(schedaPrecedente.getId());
		
		if(iab!=null){
			intermediazioneAbMan = iab;
			intermediazioneAbMan.setIdSchedaUdc(scheda.getId());
		}
		if(mc!=null){ 
			mediazioneCultMan = mc;
			mediazioneCultMan.setIdSchedaUdc(scheda.getId());
		}
		if(oi!=null){ 
			orientamentoIstruzioneMan = oi;
			orientamentoIstruzioneMan.setIdSchedaUdc(scheda.getId());
		}
		if(ol!=null){ 
			orientamentoLavoroManBean = ol;
			orientamentoLavoroManBean.setIdSchedaUdc(scheda.getId());
		}
		
	}

	public boolean isPrivacySottoscrivi() {
		return privacySottoscrivi;
	}

	public String getPrivacyMessaggio() {
		if(this.privacy!=null)
			return this.getMessaggioPrivacy(privacy.getDtIns());
		else
			return null;
	}

	public SsSchedaPrivacy getPrivacy() {
		return privacy;
	}

	public void setPrivacySottoscrivi(boolean privacySottoscrivi) {
		this.privacySottoscrivi = privacySottoscrivi;
	}

	public void setPrivacy(SsSchedaPrivacy privacy) {
		this.privacy = privacy;
	}
	
	public StreamedContent getFilePrivacy(){
		ReportPrivacyBean rb = new ReportPrivacyBean(this.scheda.getId(),this.accesso.getPuntoContatto().getUfficio().getId());
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
		if (interventiErogati==null || interventiErogati.isEmpty()) {
			loadInterventiErogati();
		}
		return interventiErogati;
	}

	public void setInterventiErogati(List<ErogazioneBaseDTO> interventiErogati) {
		this.interventiErogati = interventiErogati;
	
	}
	
	private void loadInterventiErogati(){
		interventiErogati = new ArrayList<ErogazioneBaseDTO>();
		it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
    	fillUserData(dto);
    	dto.setObj(segnalato.getAnagrafica().getCodiceFiscale());  
		try {
    		AccessTableInterventoSessionBeanRemote intService = (AccessTableInterventoSessionBeanRemote) ClientUtility.getEjbInterface(
    				"CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");
    		interventiErogati = intService.getListaInterventiErogatiByCF(dto);
    		
    	} catch(Exception e) {
    		logger.error(e.getMessage(), e);
    		addError("caricamento.error");
		}
	
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
	
}
