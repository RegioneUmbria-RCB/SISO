package it.webred.ss.web.bean;

import it.webred.amprofiler.ejb.anagrafica.AnagraficaService;
import it.webred.amprofiler.ejb.perm.LoginBeanService;
import it.webred.amprofiler.ejb.user.UserService;
import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.amprofiler.model.AmGroup;
import it.webred.cet.permission.CeTUser;
import it.webred.cs.csa.ejb.client.AccessTableDatiEsterniSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableConfigurazioneEnteSessionBeanRemote;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableNazioniSessionBeanRemote;
import it.webred.cs.csa.ejb.client.domini.AccessTableDominiAmKeySessionBeanRemote;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.TabUDC;
import it.webred.cs.data.DataModelCostanti.TipoDiario;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.data.model.CsOZonaSoc;
import it.webred.cs.data.model.CsTbAssenzaPermesso;
import it.webred.cs.data.model.CsTbPermesso;
import it.webred.cs.data.model.CsTbStatoCivile;
import it.webred.cs.data.model.CsTbStatus;
import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.cs.data.model.CsTbTipologiaFamiliare;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean.CredenzialiWS;
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
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmInstance;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.application.ApplicationService;
import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.siso.ws.ricerca.dto.PersonaDettaglio;
import it.webred.siso.ws.ricerca.dto.RicercaAnagraficaParams;
import it.webred.ss.data.model.SsDiario;
import it.webred.ss.data.model.SsIndirizzo;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaAccessoInviante;
import it.webred.ss.data.model.SsTipoScheda;
import it.webred.ss.data.model.SsUfficio;
import it.webred.ss.ejb.client.ConfigurazioneSessionBeanRemote;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.web.bean.util.Organizzazione;
import it.webred.ss.web.bean.util.PreselPuntoContatto;
import it.webred.ss.web.bean.util.PuntoContatto;
import it.webred.ss.web.bean.util.UfficiTableBean;
import it.webred.ss.web.bean.wizard.Nota;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class SegretariatoSocBaseBean {

	protected String VER_MAX = "";
	protected HashMap<String, String> mappaLabelUDC;
	public static SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
	protected SimpleDateFormat ddMMyyyyhhmmss = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	protected SimpleDateFormat ddMMyyyyhhmm = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	private Boolean visPanelStranieri;
	
	protected static final String SCHEDA_KEY = "id";
	protected static final String SOGGETTO_KEY = "sog";
	protected static final String CF_KEY = "cf";

	protected static final String ANAG_WS_TIPO = "tipoRicercaSoggetto";
	protected static final String ANAG_WS_KEY = "idAnaWs";
	protected static final String CREA_NUOVA_SCHEDA = "creaNuovaScheda";
	protected static final String RICEVI_SCHEDA = "riceviScheda";
	
	protected static final String SCHEDE_INVIATE_URL = "schede_inviate.faces";
	protected static final String NUOVA_SCHEDA_URL = "editScheda.faces";
	protected static final String RICEVI_SCHEDA_URL = "receiveScheda.faces";
	protected static final String VEDI_SCHEDA_URL = "viewScheda.faces";
	//protected static final String PAGINA_CORRENTE_URL = "schede_inviate.faces";
	
	protected static final String ZONA_SOCIALE_KEY = "zs";
	
	protected ConfigurazioneSessionBeanRemote confService = (ConfigurazioneSessionBeanRemote) getEjb("SegretariatoSoc",
			"SegretariatoSoc_EJB", "ConfigurazioneSessionBean");
	
	protected static LoginBeanService loginService = (LoginBeanService) getEjb("AmProfiler", "AmProfilerEjb", "LoginBean");
	protected ComuneService comuneService = (ComuneService) getEjb("CT_Service", "CT_Config_Manager", "ComuneServiceBean");
	protected static ParameterService paramService = (ParameterService) getEjb("CT_Service", "CT_Config_Manager", "ParameterBaseService");
	protected static LuoghiService luoghiService = (LuoghiService)  getEjb("CT_Service", "CT_Config_Manager", "LuoghiServiceBean");
	
	protected AccessTableInterventoSessionBeanRemote interventoService = 
			(AccessTableInterventoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");
	
	protected AccessTableConfigurazioneSessionBeanRemote configurationCsBean =
			(AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
	
	protected AccessTableConfigurazioneEnteSessionBeanRemote configurationCsEnteBean =
			(AccessTableConfigurazioneEnteSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneEnteSessionBean");
	
	protected SsSchedaSessionBeanRemote schedaService = 
			(SsSchedaSessionBeanRemote) getEjb("SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
	
	protected  AccessTableDominiAmKeySessionBeanRemote amKeyDomini = 
			(AccessTableDominiAmKeySessionBeanRemote) getEjb ("CarSocialeA", "CarSocialeA_EJB", "AccessTableDominiAmKeySessionBean");

	private AccessTableDiarioSessionBeanRemote diarioService;
	
	private final Date currentDate = new Date();


	private String logoBasePath;
	protected String dirLoghi = "/images/logo/";

	private StreamedContent logoComune;

	public static Logger logger = Logger.getLogger("segretariatosoc.log");

	private static ResourceBundle bundle;

	static {
		bundle = ResourceBundle.getBundle("it.webred.ss.web.resources.messages");
	}

	public static final String loginAction = "LOGIN";
	public static final String logoutAction = "LOGOUT";
	public static final String read = "READ";
	public static final String write = "CREATE";
	public static final String edit = "EDIT";
	public static final String receive = "RECEIVE";
	public static final String delete = "DELETE";
	public static final String print = "PRINT";

	public void logAction(String action) {
		logAction(action, null);
	}

	public SsSchedaSessionBeanRemote getSsSchedaService() throws NamingException{
		return this.schedaService;
	}
	
	public AccessTableDiarioSessionBeanRemote getDiarioCsBean() throws NamingException {
		if (diarioService == null)
			diarioService =
					(AccessTableDiarioSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableDiarioSessionBean");
		return diarioService;
	}
	
	public AccessTableDiarioSessionBeanRemote getRemoteDiarioCsBean() throws NamingException {
		if (diarioService == null)
			diarioService = (AccessTableDiarioSessionBeanRemote) ClientUtility
			.getEJBInterfaceForRemoteJBOSSClient(
					AccessTableDiarioSessionBeanRemote.class, "CarSocialeA",
					"CarSocialeA_EJB", "AccessTableDiarioSessionBean");
		return diarioService;
	}
	
	

	public AccessTableConfigurazioneSessionBeanRemote getConfigurationCsBean(){
		return configurationCsBean;
	}
	
	private String getGlobalParameter(String paramName) {
		String valore = amKeyDomini.findByKey(paramName);
		if(!StringUtils.isBlank(valore)) return valore;
		
		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setKey(paramName);
		criteria.setComune(null);
		criteria.setSection(null);
		AmKeyValueExt amKey = paramService.getAmKeyValueExt(criteria);
		if (amKey != null && amKey.getValueConf() != null) {
			return amKey.getValueConf();
		} else
			logger.warn("Parametro '" + paramName + "' non definito");

		return null;
	}
	
	//FINE SISO-438-Possibilità di allegare documenti in UdC
	
	public String addParameter(String url, String key, String value){
		//Matteo Leandri 30/06/2016
    	//Aggiunta possibilità di avere più di un parametro sulla URL
    	StringBuilder sb = new StringBuilder(url);
    	if (url.contains("?")) {
    		sb.append("&");
    	} else {
    		sb.append("?");
    	}
    	
    	try {
			sb.append(key).append("=").append(java.net.URLEncoder.encode(value, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
			return url;
		}
    	
    	return sb.toString();
    }

	public String getCognomeNomeOperatore() {
		CeTUser user = getUser();
		if (user != null)
			return this.getCognomeNomeUtente(user.getUsername());
		else
			return "";
	}
	
	public String getCognomeOperatore() {
		CeTUser user = getUser();
		if (user != null)
			return this.getCognomeUtente(user.getUsername());
		else
			return "";
	}
	
	public String getNomeOperatore() {
		CeTUser user = getUser();
		if (user != null)
			return this.getNomeUtente(user.getUsername());
		else
			return "";
	}
	
	

	public String getUserNameOperatore() {
		CeTUser user = getUser();
		if (user != null) {
			return user.getUsername();
		} else
			return null;
	}

	public void logAction(String action, Long subject) {
		String user = getUserNameOperatore();
		String subjectString = ".";
		if (subject != null)
			subjectString = " (ID SCHEDA: " + subject + ").";
		logger.info(user + " -> " + action + subjectString);
	}

	private static void addMessage(String messageKey, FacesMessage.Severity severity, String details) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String txt = bundle.getString(messageKey);

		facesContext.addMessage(null, new FacesMessage(severity, txt,details == null ? "" : details));
	}

	public void addInfo(String msgKey) {
		addInfo(msgKey, null);
	}

	public void addInfo(String msgKey, String detail) {
		addMessage(msgKey, FacesMessage.SEVERITY_INFO, detail);
	}

	public void addWarning(String msgKey) {
		addWarning(msgKey, (String)null);
	}

	public void addWarning(String msgKey, String detail) {
		addMessage(msgKey, FacesMessage.SEVERITY_WARN, detail);
	}

	public static void addError(String msgKey) {
		addError(msgKey, null);
	}
	
	protected void addWarning(String msgKey, List<String> descrizione) {
		if(descrizione!=null && !descrizione.isEmpty())
			addWarning(msgKey, StringUtils.join(descrizione,"<br/>"));
	}

	public static void addError(String msgKey, String detail) {
		addMessage(msgKey, FacesMessage.SEVERITY_ERROR, detail);
	}

	protected void addErrorMessage(String summary, String descrizione) {
		addMessage(summary, descrizione, FacesMessage.SEVERITY_ERROR);
	}

	public void addWarningMessage(String summary, String descrizione) {
		addMessage(summary, descrizione, FacesMessage.SEVERITY_WARN);
	}

	protected void addInfoMessage(String summary, String descrizione) {
		addMessage(summary, descrizione, FacesMessage.SEVERITY_INFO);
	}
	
	protected void printSelectError(){
    	addError("seleziona.error");
    }
	protected void printPolicyError(){
    	addError("policy.error");
    }
	protected void printPolicyUfficiError(){
    	addError("policy.ufficio.error");
    }
	
	protected void addMessage(String summary, String descrizione, FacesMessage.Severity severity) {
		FacesMessage message = new FacesMessage(severity, summary, descrizione != null ? descrizione : "");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public HttpServletResponse getResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}

	public String getMessage(String messageKey) {
		String txt = bundle.getString(messageKey);
		return txt;
	}

	public static Object getBeanReference(String beanName) {
		FacesContext context = FacesContext.getCurrentInstance();
		Application a = context.getApplication();
		Object o = a.getVariableResolver().resolveVariable(context, beanName);
		return o;
	}

	private String getParamValue(String paramName) {
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String path = ctx.getInitParameter(paramName);
		return path;
	}

	protected Logger getLogger() {
		return logger;
	}

	

	public CeTUser getUser() {

		CeTUser user = (CeTUser) getSession().getAttribute("user");

		String istanza = "SegretariatoSoc";
		try {
			ApplicationService appService = (ApplicationService) ClientUtility.getEjbInterface("CT_Service", "CT_Config_Manager", "ApplicationServiceBean");
			CeTBaseObject cet = new CeTBaseObject();
			fillUserData(cet);
			AmInstance instance = appService.getInstanceByApplicationComune("SegretariatoSoc", cet.getEnteId());
			istanza = instance.getName();
		} catch (Exception e) {

		}
		if (user != null) {
			HttpSession session = getRequest().getSession();
			if (session.getAttribute(loginAction) == null) {
				session.setAttribute(loginAction, true);
				logAction(loginAction);
			}
		}
		return user;

	}
	
	public String getCurrentEnte(){
		String ente = this.getPreselectedBelfioreOrg();
		if(ente==null)
		  ente = this.getUser().getCurrentEnte();
		return ente;
	}

	public boolean isEnteEsterno() {
		// return !getUserOrganization().contains("COMUNE");
		return this.getPreselectedPContatto().getOrganizzazione().getBelfiore() == null;
	}

	public boolean isComune() {
		return this.getPreselectedPContatto().getOrganizzazione().getBelfiore() != null;
	}

	/*
	 * public String getUserOrganization(){
	 * 
	 * String belfiore =
	 * this.getPreselectedPContatto().getOrganizzazione().getBelfiore();
	 * if(belfiore!=null) return "COMUNE "+belfiore; else return
	 * this.getPreselectedPContatto().getOrganizzazione().getNome(); }
	 */

	public String getUserOrganization() {
		CeTUser user = getUser();
		String org = "COMUNE";
		if (user != null)
			for (AmGroup group : getGruppiUtenteEnte()) {
				String[] tokens = group.getName().split("_");
				if (tokens.length >= 3) {
					if (!tokens[0].equals("SSOCIALE")) // skip groups not equal
														// to SSOCIALE (e.g.,
														// CSOCIALE_SOLO_ACCESSO_L872)
						continue;
					if (!group.getName().contains("RESPONSABILI") && !group.getName().contains("OPERATORI"))
						org = tokens[1]; // replace COMUNE with the name of the
											// organization (e.g.,
											// SSOCIALE_CARITAS_L872)
					if (!org.contains(tokens[2])) {
						org += " " + tokens[2]; // attach the id (e.g., L872)
					}
				}
			}
		return org;
	}

	public String getUserOrganizationWithoutId() {
		String org = "";
		CeTUser user = getUser();
		if (user != null)
			for (AmGroup group : getGruppiUtenteEnte()) {
				String[] tokens = group.getName().split("_");
				if (tokens.length >= 2) {
					if (!tokens[0].equals("SSOCIALE")) // skip groups not equal
														// to SSOCIALE (e.g.,
														// CSOCIALE_SOLO_ACCESSO_L872)
						continue;
					org = tokens[0] + "_" + tokens[1];
				}
			}
		return org;
	}

	public boolean isResponsabile() {
		CeTUser user = getUser();
		boolean responsabile = false;
		if (user != null) {
			for (AmGroup group : getGruppiUtenteEnte())
				if (group.getName().contains("RESPONSABILI")) {
					responsabile = true;
					break;
				}
		}

		return responsabile;
	}

	public boolean isResponsabileEnte(String belfiore){
		boolean responsabile = false;
		if (getUser() != null) {
			for (AmGroup group : getListaGruppi(getUser().getUsername(), belfiore))
				if (group.getFkAmComune().equals(belfiore) && group.getName().contains("SSOCIALE_RESPONSABILI")) {
					responsabile = true;
					break;
				}
		}

		return responsabile;
	}
	
	public boolean disableComponent(boolean canAccess) {
		return !canAccess;
	}

	public boolean canRead() {
		return CsUiCompBaseBean.checkPermessoSS("segrsoc-read");
	}

	public boolean canEdit() {
		return CsUiCompBaseBean.checkPermessoSS("segrsoc-edit");
	}

	public boolean canWrite() {
		return CsUiCompBaseBean.checkPermessoSS("segrsoc-write");
	}

	public boolean canDelete() {
		return CsUiCompBaseBean.checkPermessoSS("segrsoc-delete");
	}

	public boolean canPrint() {
		return CsUiCompBaseBean.checkPermessoSS("segrsoc-print");
	}

	public boolean canReadDiario() {
		return CsUiCompBaseBean.checkPermessoSS("segrsoc-readDiario");
	}
	
	public boolean isAccessoDatiSocLav() {
		return CsUiCompBaseBean.checkPermessoSS("segrsoc-accesso dati SOCLAV");
	}

	public boolean canReadInterventiEconomiciNucleo() {
		return CsUiCompBaseBean.checkPermessoSS("segrsoc-readInterventiEconomiciNucleo");
	}

	public boolean isOwner(String operatore) {
		if (getUser() != null) {
			return operatore.equals(this.getUserNameOperatore());
		}
		return false;
	} 
	

	public void fillUserData(CeTBaseObject ro) {

		CeTUser user = (CeTUser) getSession().getAttribute("user");
		if (user != null) {
			ro.setEnteId(user.getCurrentEnte());
			ro.setUserId(user.getUsername());
			ro.setSessionId(user.getSessionId());
		}
		
		String ente = getPreselectedBelfioreOrg();
		if (ente != null)
			ro.setEnteId(ente);
	}
	
	public static void fillEnte(CeTBaseObject ro) {

		CeTUser user = (CeTUser) getSession().getAttribute("user");
		if (user != null) {
			ro.setEnteId(user.getCurrentEnte());
			ro.setUserId(user.getUsername());
			ro.setSessionId(user.getSessionId());
		}
		
		PreselPuntoContatto pContMan = (PreselPuntoContatto)getBeanReference("preselPuntoContatto");
		String ente = pContMan.getPuntoContatto().getOrganizzazione().getBelfiore();
		if (ente != null)
			ro.setEnteId(ente);
	}
	
	public String getBelfioreSceltaEnte() {
		CeTUser user = getUser();
		if (user != null)
			return user.getCurrentEnte();
		else
			return null;
	}

	protected AccessTableNazioniSessionBeanRemote getAmConfigurazioneService() throws NamingException {
		AccessTableNazioniSessionBeanRemote service2 =
				(AccessTableNazioniSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableNazioniSessionBean");
		return service2;
	}

	public String getLabelZonaSociale(){
		return CsUiCompBaseBean.getLabelZonaSociale();
	}
	
	public String getZonaSociale() {
		try {
			
			it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
			this.fillUserData(dto);
				
			String zsValue = "";
			CsOZonaSoc zs = configurationCsEnteBean.findZonaSocAbilitata(dto);
			zsValue = zs!=null ? zs.getNome() : null;
			
			return zsValue;

		} catch (Exception e) {
			addError("lettura.error", "Errore recupero "+getLabelZonaSociale());
		}

		return "";
	}

	public boolean isModuloPorMarche(){
		String valore = this.getModuloPorRegionale();
		return !StringUtils.isEmpty(valore) && valore.contains("Marche");
	}
	
	@SuppressWarnings("unchecked")
	private AmAnagrafica getAnagrafica(String user) {
		AmAnagrafica amAna = null;
		HashMap<String, AmAnagrafica> mappaAnagrafiche = (HashMap<String, AmAnagrafica>) getSession().getAttribute("mappaAnagraficheUdC");
		if (mappaAnagrafiche == null)
			mappaAnagrafiche = new HashMap<String, AmAnagrafica>();
		if (user != null && !user.isEmpty()) {
			amAna = mappaAnagrafiche.get(user);
			if (amAna == null) {
				try {
					AnagraficaService anagraficaService = (AnagraficaService) ClientUtility.getEjbInterface("AmProfiler", "AmProfilerEjb", "AnagraficaServiceBean");
					amAna = anagraficaService.findAnagraficaByUserName(user);
					if (amAna != null) {
						mappaAnagrafiche.put(user, amAna);
						getSession().setAttribute("mappaAnagraficheUdC", mappaAnagrafiche);
					}

				} catch (NamingException e) {
					logger.error(e);
				}
			}
		}
		return amAna;
	}

	public String getCognomeNomeUtente(String user) {
		AmAnagrafica amAna = this.getAnagrafica(user);
		if (amAna != null)
			return amAna.getCognome() + " " + amAna.getNome();
		else
			return user;
	}
	
	public String getCognomeUtente(String user) {
		AmAnagrafica amAna = this.getAnagrafica(user);
		if (amAna != null)
			return amAna.getCognome();
		else
			return user;
	}

	
	public String getNomeUtente(String user) {
		AmAnagrafica amAna = this.getAnagrafica(user);
		if (amAna != null)
			return amAna.getNome();
		else
			return user;
	}


/*	protected String readTipoScheda(Long idTipo) {
		try {
			SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
					"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");

			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(idTipo);
			return schedaService.readTipoSchedaById(dto).getTipo();
		} catch (Exception e) {
			return "";
		}
	}*/

/*	protected PuntoContatto getPContattoFromSession() {
		PuntoContatto pcont = (PuntoContatto) this.getSession().getAttribute("preselectedPContatto");
		return pcont;
	}*/

/*	public Organizzazione getOrganizzazioneFromSession() {
		Organizzazione org = null;
		PuntoContatto pcont = this.getPContattoFromSession();
		if (pcont != null)
			org = pcont.getOrganizzazione();
		return org;
	}
*/
	protected String getDirDatiDiogene() {
		if (this.logoBasePath == null) {
			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setKey("dir.files.datiDiogene");
			criteria.setComune(null);
			criteria.setSection(null);
			this.logoBasePath = getParametro(criteria);
			if (this.logoBasePath == null)
				logger.error("Attenzione: Il path per il recupero logo del report non è impostato");
		}

		return logoBasePath;
	}

	protected String getTipoApplicazione() {

		String paramTipoApplicazione = null;
		BaseDTO baseDto = new BaseDTO();
		this.fillUserData(baseDto);
		if (baseDto.getEnteId() != null) {
			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setKey("smartwelfare.tipoApplicazione");
			criteria.setComune(baseDto.getEnteId());
			criteria.setSection("param.comune");
			paramTipoApplicazione = this.getParametro(criteria);
			if (paramTipoApplicazione == null)
				logger.error("Attenzione: smartwelfare.tipoApplicazione non impostata per il comune " + baseDto.getEnteId());
		}
		if (baseDto.getEnteId() == null || paramTipoApplicazione == null) {
			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setKey("smartwelfare.tipoApplicazione.default");
			criteria.setSection("param.globali");
			paramTipoApplicazione = this.getParametro(criteria);
		}

		if (paramTipoApplicazione == null) {
			logger.error("Attenzione: smartwelfare.tipoApplicazione.default non impostata per il polo.");
			paramTipoApplicazione = "";
		}

		return paramTipoApplicazione.toLowerCase();
	}

	private String getParametro(ParameterSearchCriteria criteria) {
		try {
			AmKeyValueExt amKey = paramService.getAmKeyValueExt(criteria);
			if (amKey != null)
				return amKey.getValueConf();
			else
				return null;

		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	public StreamedContent getLogo(String nomefile) {

		String pathLoghi = this.getPathLoghi();
		if (pathLoghi != null) {
			String slogo = pathLoghi + nomefile;
			File logo = new File(slogo);
			if (logo.exists()) {
				try {
					this.logoComune = new DefaultStreamedContent(new FileInputStream(logo), "image/jpeg");
				} catch (FileNotFoundException e) {
					logger.error("Errore recupero Logo Comune", e);
					this.logoComune = null;
				}
			} else {
				logger.error("Attenzione: Il logo del comune non è presente [" + slogo + "]");
				this.logoComune = null;
			}

		}
		return logoComune;
	}

	public String getLogoTitolo() {
		String titolo = "titolo_" + this.getTipoApplicazione() + ".png";
		return titolo;
	}

	public String getLabelSegrSociale() {
		return this.getTipoApplicazione().equalsIgnoreCase("udc") ? "Ufficio della Cittadinanza" : "Segretariato Sociale";
	}
	
	public String getLabelOrganizzazione(){
		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setKey("smartwelfare.label.tipoOrganizzazione");
		criteria.setSection("param.globali");
		String label = this.getParametro(criteria);
		return label;
	}
	
	public StreamedContent getLogoComune() {
		return this.getLogo("logo_intestazione.jpg");
	}

	public StreamedContent getLogoHeader1() {
		return this.getLogo("logo-header1.jpg");
	}

	public StreamedContent getLogoHeader2() {
		return this.getLogo("logo-header2.jpg");
	}

	protected boolean esisteLogo(String nomeFile) {
		boolean esiste = false;
		String pathLoghi = this.getPathLoghi();
		if (pathLoghi != null) {
			String slogo = pathLoghi + nomeFile;
			File logo = new File(slogo);
			esiste = logo.exists();
		}
		return esiste;
	}

	public boolean esisteLogoComune() {
		return this.esisteLogo("logo_intestazione.jpg");
	}

	public boolean esisteLogoHeader1() {
		return this.esisteLogo("logo-header1.jpg");
	}

	public boolean esisteLogoHeader2() {
		return this.esisteLogo("logo-header2.jpg");
	}

	protected CsTbTipologiaFamiliare getFamiglia(long id) {
		try {
			
				it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillUserData(d);
				d.setObj(new Long(id));
				CsTbTipologiaFamiliare fa = this.configurationCsBean.getTipologiaFamiliareById(d);
				return fa;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error", "Impossibile recuperare la tipologiaFamigliare");
		}
		return null;
	}

	protected CsTbPermesso getPermesso(String id) {
		try {
			if (id != null) {
				it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillUserData(d);
				d.setObj(id);
				CsTbPermesso cl = this.configurationCsBean.getPermessoById(d);
				return cl;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error", "Impossibile recuperare Permesso");
		}
		return null;
	}
	
	//SISO-792
	protected CsTbAssenzaPermesso getAssenzaPermesso(String id) {
		try {
			if (id != null) {
				it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillUserData(d);
				d.setObj(id);
				CsTbAssenzaPermesso cl = configurationCsBean.getAssenzaPermessoById(d);
				return cl;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error", "Impossibile recuperare AssenzaPermesso");
		}
		return null;
	}


	protected CsTbStatus getStatus(String id) {
		try {
			if (id != null) {
				it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillUserData(d);
				d.setObj(id);
				CsTbStatus cl = configurationCsBean.getStatusById(d);
				return cl;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error", "Impossibile recuperare  Status");
		}
		return null;
	}

	/**
	 * GESTIONE ORGANIZZAZIONI
	 * */

	// Carica la lista delle Organizzazioni con Belfiore/Codice fittizio Specificato
	protected List<CsOOrganizzazione> getListaEnti() {
		try {
			CeTBaseObject opDto = new CeTBaseObject();
			this.fillUserData(opDto);
			List<CsOOrganizzazione> lstOrg = configurationCsEnteBean.getOrganizzazioniAccesso(opDto);
			return lstOrg;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error", "Caricamento Enti CS non riuscito !");
		}
		return null;
	}

	protected List<AmGroup> getListaEntiGIT(String username) {
		try {
			UserService bean = (UserService) ClientUtility.getEjbInterface("AmProfiler", "AmProfilerEjb", "UserServiceBean");
			List<AmGroup> lstOrg = bean.getGruppiUtente(username);
			return lstOrg;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error", "Caricamento Enti GIT non riuscito !");
		}
		return null;
	}

	public String getPreselectedBelfioreOrg(){
		String bel = getPreselectedPContatto().getOrganizzazione().getBelfiore();
		return bel;
	}
	
	public String getPreselectedOrganizzazione() {
		String id = getPreselectedPContatto().getOrganizzazione().getBelfiore();
		id+= "|"+ getPreselectedPContatto().getOrganizzazione().getId();
		return id;
	}
	
	public void vaiASchedePerEnte(Long idOrganizzazione){
		
		if(getPreselectedPContatto()==null || this.getPreselectedPContatto().getIdPContatto()==null || this.getPreselectedPContatto().getIdPContatto()<=0)	
			addError("no.puntoContatto.error");
		else{
	        String url = addParameter(SCHEDE_INVIATE_URL, "ente", idOrganizzazione+"");
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(url);
			} catch (IOException e) {
				logger.error(e);
			}
		}
    }

	public void setPreselectedOrganizzazione(String idLista) {
		UfficiTableBean ub = (UfficiTableBean) getBeanReference("ufficiTableBean");
		String codiceFittizio=null;;
		String idOrganizzazione=null;
		if(idLista!=null){
			String ids[] = idLista.split("\\|");
			codiceFittizio = ids[0];
			idOrganizzazione = (ids[1]!=null && !ids[1].isEmpty()) ? ids[1].trim() : null;
		}
		
		if (codiceFittizio != null && !codiceFittizio.isEmpty() && !codiceFittizio.equals(this.getPreselectedBelfioreOrg())) {
			
			CsUiCompBaseBean.aggiornaEnteCeTUser(codiceFittizio);
			
			// Resetto il valore memorizzato precedente
					
			it.webred.cs.csa.ejb.dto.BaseDTO b = new it.webred.cs.csa.ejb.dto.BaseDTO();
			this.fillUserData(b);
			CsOOrganizzazione o = null;
			
			try {
				if(idOrganizzazione!=null){
					b.setObj(new Long(idOrganizzazione));
					o = configurationCsEnteBean.getOrganizzazioneById(b);
				}
				
				//Se non la trovo per id la cerco per codice univoco
				if(o==null){
					b.setObj(codiceFittizio);
					o = configurationCsEnteBean.getOrganizzazioneByCodFittizio(b);
				}
				
			} catch (Exception e) {
				logger.error(e);
			}
			
			String descrizione = o.getNome();
			
			Organizzazione oout = new Organizzazione(o != null ? o.getId() : null, codiceFittizio, 
					(descrizione!=null && !descrizione.isEmpty()) ? descrizione : "- denominazione non trovata -");
			this.getPreselectedPContatto().setOrganizzazione(oout);
					
			ub.onChangeOrganizzazione();

			NavigationBean nb = (NavigationBean) getBeanReference("navigationBean");
			nb.goHome();
		}
	}

	protected String getPathLoghi() {
		BaseDTO baseDto = new BaseDTO();
		fillUserData(baseDto);
		String pathLoghi = null;
		if (baseDto.getEnteId() != null) {
			String dir = this.getDirDatiDiogene();
			if (dir != null)
				pathLoghi = dir + baseDto.getEnteId() + dirLoghi;
		}
		return pathLoghi;
	}

	public PuntoContatto getPreselectedPContatto() {
		PreselPuntoContatto pContMan = (PreselPuntoContatto)getBeanReference("preselPuntoContatto");
		
		if(pContMan==null)
			pContMan = new PreselPuntoContatto();
		return pContMan.getPuntoContatto();
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	protected boolean isAnagrafeSanitariaUmbriaAbilitata(){
		return CsUiCompBaseBean.isAnagrafeSanitariaUmbriaAbilitata();
	}
	
	protected boolean isAnagrafeSanitariaMarcheAbilitata(){
		return CsUiCompBaseBean.isAnagrafeSanitariaMarcheAbilitata();
	}
	
	protected boolean isAnagrafeSigessAbilitata(){
		return CsUiCompBaseBean.isAnagrafeSigessAbilitata();
	}
	
	protected boolean isAnagrafeComunaleInternaAbilitata(){
		return CsUiCompBaseBean.isAnagrafeComunaleInternaAbilitata();
	}
	
	protected CredenzialiWS getCredenzialiWS() {
		CsUiCompBaseBean b = new CsUiCompBaseBean();
		return b.getCredenzialiWS();
	}

	protected URL getMediciWebServiceWSDLLocation() throws NamingException {
		String urlString = getGlobalParameter(DataModelCostanti.AmParameterKey.WS_MEDICI_URL);
		return stringToUrl(urlString);
	}
	
	protected boolean isVisualizzaModuloPorUdc() {
		String val = getGlobalParameter(DataModelCostanti.AmParameterKey.POR_UDC_ABILITA);
		return val!=null && "1".equalsIgnoreCase(val) ? true : false;
	}
	
	public boolean isGestioneCapofilaPic() {
		String val = getGlobalParameter(DataModelCostanti.AmParameterKey.GESTIONE_CAPOFILA_PIC);
		return val!=null && "1".equalsIgnoreCase(val) ? true : false;
	}
	
	protected String getModuloPorRegionale(){
		return this.getGlobalParameter(DataModelCostanti.AmParameterKey.POR_MODELLO_STAMPA);
	}
	
	private static URL stringToUrl(String urlString) {
		URL url;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			url = null;
		}
		return url;
	}
	
	protected IStranieri getCopiaJsonStranieri(Long schedaId) throws Exception {
		IStranieri stranieriMan = null;
		try {

			CsDValutazione val = getSchedaValutazione(schedaId, DataModelCostanti.TipoDiario.STRANIERI_ID);
			stranieriMan = (IStranieri)StranieriManBaseBean.initByModel(val);
	
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");
		}

		return stranieriMan;
	}


	protected IStranieri getSchedaJsonStranieri(Long schedaId) throws Exception {
		IStranieri stranieriMan = null;
		try {

			CsDValutazione val = getSchedaValutazione(schedaId, DataModelCostanti.TipoDiario.STRANIERI_ID);
			stranieriMan = (IStranieri)StranieriManBaseBean.initByModel(val);
	
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");
		}

		return stranieriMan;
	}
	
	public List<CsDValutazione> getSchedeJsonInterventiCustom(SsScheda scheda){
		List<CsDValutazione> val = new ArrayList<CsDValutazione>();
		Integer[] tipiDiario = {TipoDiario.INTERMEDIAZIONE_AB_ID, 
								TipoDiario.ORIENTAMENTO_LAVORO_ID, 
								TipoDiario.MEDIAZIONE_CULT_ID,
								TipoDiario.ORIENTAMENTO_ISTRUZIONE_ID,
								TipoDiario.MEDIAZIONE_CULT_ID,
								TipoDiario.RICHIESTA_SERVIZIO_ID};
		try {
			val = getSchedeValutazione(scheda.getId(), Arrays.asList(tipiDiario));
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");
		}
		return val;
	}
	
	//SISO-438 cambiata visibilità a public
	public IIntermediazioneAb getSchedaJsonIntermediazioneAb(Long schedaId) {
		IIntermediazioneAb man = null;
		try {
			CsDValutazione val = getSchedaValutazione(schedaId, DataModelCostanti.TipoDiario.INTERMEDIAZIONE_AB_ID);
			man = (IIntermediazioneAb)IntermediazioneManBaseBean.initByModel(val);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");
		}

		return man;
	}

	//SISO-438 cambiata visibilità a public
	public IOrientamentoLavoro getSchedaJsonOrientamentoLavoro(Long schedaId) {
		IOrientamentoLavoro man = null;
		try {
			CsDValutazione val = getSchedaValutazione(schedaId, DataModelCostanti.TipoDiario.ORIENTAMENTO_LAVORO_ID);
			man = (IOrientamentoLavoro)OrientamentoLavoroManBaseBean.initByModel(val);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");
		}

		return man;

	}

	//SISO-438 cambiata visibilità a public
	public IMediazioneCult getSchedaJsonMediazioneCult(Long schedaId){
		IMediazioneCult man = null;
		try {
			CsDValutazione val = getSchedaValutazione(schedaId, DataModelCostanti.TipoDiario.MEDIAZIONE_CULT_ID);
			man = MediazioneCultManBaseBean.initByModel(val);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");
		}
		return man;
	}

	//SISO-438 cambiata visibilità a public
	public IOrientamentoIstruzione getSchedaJsonOrientamentoIstruzione(Long schedaId){
		IOrientamentoIstruzione man = null;
		try {
			CsDValutazione val = getSchedaValutazione(schedaId, DataModelCostanti.TipoDiario.ORIENTAMENTO_ISTRUZIONE_ID);
			man = (IOrientamentoIstruzione) OrientamentoIstruzioneManBaseBean.initByModel(val);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error");
		}

		return man;
	}

	protected IAbitazione getSchedaJsonAbitazione(Long schedaId) {
		IAbitazione man = null;
		try {

			CsDValutazione val = getSchedaValutazione(schedaId, DataModelCostanti.TipoDiario.ABITAZIONE_ID);
			man = AbitazioneManBaseBean.initByModel(val);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return man;
	}
	
	protected IFamConviventi getSchedaJsonFamConviventi(Long schedaId) {
		IFamConviventi man = null;
		try {

			CsDValutazione val = getSchedaValutazione(schedaId, DataModelCostanti.TipoDiario.FAMIGLIA_ID);
			man = FamiliariManBaseBean.initByModel(val);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return man;
	}
	
//INIZIO SISO-438
//	protected List<CsDValutazione> getSchedeValutazione(Long schedaId, int tipoDiario) throws NamingException {
//		it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
//		this.fillUserData(dto);
//		dto.setObj(schedaId);
//		dto.setObj2(tipoDiario);
//
//		AccessTableDiarioSessionBeanRemote diarioService;
//		diarioService = this.getDiarioCsBean();
//		List<CsDValutazione> schede = diarioService.getSchedeValutazioneUdcId(dto);
//		if (schede == null || schede.isEmpty()){
//			schede = new ArrayList<CsDValutazione>();
//		} 
//
//		return schede;
//	}
	
	
	protected CsDValutazione getSchedaValutazione(Long diarioId) throws NamingException {
		it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
		this.fillUserData(dto);
		dto.setObj(diarioId); 

		AccessTableDiarioSessionBeanRemote diarioService;
		diarioService = this.getDiarioCsBean();
		CsDValutazione scheda = diarioService.findValutazioneById(dto);
		return scheda;
	}
	
//FINE SISO-438
	
	
	protected CsDValutazione getSchedaValutazione(Long schedaId, int tipoDiario) throws NamingException {
		it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
		this.fillUserData(dto);
		dto.setObj(schedaId);
		dto.setObj2(tipoDiario);

		AccessTableDiarioSessionBeanRemote diarioService;
		diarioService = this.getDiarioCsBean();
		List<CsDValutazione> schede = diarioService.getSchedeValutazioneUdcId(dto);
		if (schede != null && !schede.isEmpty())
			return schede.get(0);

		return null;
	}
	
	protected List<CsDValutazione> getSchedeValutazione(Long schedaId, List<Integer> tipoDiario) throws NamingException {
		it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
		this.fillUserData(dto);
		dto.setObj(schedaId);
		dto.setObj2(tipoDiario);

		AccessTableDiarioSessionBeanRemote diarioService;
		diarioService = this.getDiarioCsBean();
		List<CsDValutazione> schede = diarioService.getSchedeValutazioneUdcId(dto);
		return schede;
	}
	
	protected CsDValutazione getSchedaValutazioneByDiarioId(Long diarioId) throws NamingException {
		it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
		this.fillUserData(dto);
		dto.setObj(diarioId);

		AccessTableDiarioSessionBeanRemote diarioService;
		AccessTableDiarioSessionBeanRemote diarioRemoteService;
		diarioService = this.getDiarioCsBean();
		diarioRemoteService=this.getRemoteDiarioCsBean();
		CsDValutazione valutazione = diarioService.findValutazioneById(dto);
		if(valutazione==null)
		{
		//non l'ho trovati in locale provo in remoto
		if(diarioRemoteService!=null)
			valutazione=diarioRemoteService.findValutazioneById(dto);
		}
		
		return valutazione;
	}
	
	protected CsDValutazione saveSchedaValutazione(CsDValutazione schedaValutazione) throws NamingException {
		it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
		this.fillUserData(dto);
		dto.setObj(schedaValutazione);

		AccessTableDiarioSessionBeanRemote diarioService;
		diarioService = this.getDiarioCsBean();
		CsDValutazione valutazione = diarioService.saveSchedaValutazione(schedaValutazione);
		return valutazione;
	}
	
	protected IAbitazione nuovaDaSchedaJsonAbitazione(Long schedaId){
		IAbitazione man1 = this.getSchedaJsonAbitazione(schedaId);
		if(man1!=null) return AbitazioneManBaseBean.init(man1);
		else return null;
	}
	
	protected IStranieri nuovaDaSchedaJsonStranieri(Long schedaId) throws Exception{
		IStranieri man1 = this.getSchedaJsonStranieri(schedaId);
		if(man1!=null) return StranieriManBaseBean.init(man1);
		else return null;
	}
	
	protected IFamConviventi nuovaDaSchedaJsonFamiliari(Long schedaId){
		IFamConviventi man1 = this.getSchedaJsonFamConviventi(schedaId);
		if(man1!=null) return FamiliariManBaseBean.init(man1);
		else return null;
	}
	
//	protected IOrientamentoIstruzione nuovaDaSchedaJsonOrientamentoIstruzione(Long schedaId){
//		IOrientamentoIstruzione man1 = this.getSchedaJsonOrientamentoIstruzione(schedaId);
//		if(man1!=null) return OrientamentoIstruzioneManBaseBean.init(man1);
//		else return null;
//	}
//	
//	protected IMediazioneCult nuovaDaSchedaJsonMediazioneCulturale(Long schedaId){
//		IMediazioneCult man1 = this.getSchedaJsonMediazioneCult(schedaId);
//		if(man1!=null) return MediazioneCultManBaseBean.init(man1);
//		else return null;
//	}
//
//	protected IIntermediazioneAb nuovaDaSchedaJsonIntermediazioneAb(Long schedaId){
//		IIntermediazioneAb man1 = this.getSchedaJsonIntermediazioneAb(schedaId);
//		if(man1!=null) return IntermediazioneManBaseBean.init(man1);
//		else return null;
//	}
//
//	protected IOrientamentoLavoro nuovaDaSchedaJsonOrientamentoLavoro(Long schedaId){
//		IOrientamentoLavoro man1 = this.getSchedaJsonOrientamentoLavoro(schedaId);
//		if(man1!=null) return OrientamentoLavoroManBaseBean.init(man1);
//		else return null;
//	}


	public boolean isVisPanelStranieri() {
		if(this.visPanelStranieri==null){
			visPanelStranieri=false;
			try{
				ParameterSearchCriteria criteria = new ParameterSearchCriteria();
				criteria.setKey("smartwelfare.gestioneStranieri");
				criteria.setComune(null);
				criteria.setSection(null);
				AmKeyValueExt amKey = paramService.getAmKeyValueExt(criteria);
				if (amKey != null && amKey.getValueConf() != null) {
					String val = amKey.getValueConf();
					this.visPanelStranieri= val!=null && "1".equals(val) ? true : false;
				} 
			}catch(Exception e){
				logger.error("Errore recupero smartwelfare.gestioneStranieri: il pannello non verrà visualizzato");
			}
		}
		return visPanelStranieri;
	}

	public void setVisPanelStranieri(boolean visPanelStranieri) {
		this.visPanelStranieri = visPanelStranieri;
	}

	public void setPreselectedPuntoContatto(PuntoContatto pCont) {
		PreselPuntoContatto pContMan = (PreselPuntoContatto)getBeanReference("preselPuntoContatto");
		pContMan.setPuntoContatto(pCont);
	}
	
	public List<AmGroup> getGruppiUtenteEnte(){
		String belfiore = this.getCurrentEnte();
		List<AmGroup> lstGruppi = getListaGruppi(getUser().getUsername(), belfiore);
		return lstGruppi;
	}
	
	public List<AmGroup> getListaGruppi(String username, String ente){
		List<AmGroup> listaGruppi = null;
		CeTUser user = getUser();
		if(ente.equals(user.getCurrentEnte()))
			listaGruppi = user.getGroupList();
		else{
			listaGruppi = loginService.getGruppi(username, ente);
		}
		return listaGruppi;
	}
	
	public boolean canAccessUfficio(Long ufficioID){
		CeTUser user = getUser();
		boolean ufficioAbilitato = true;
		if (user != null){
			List<AmGroup> gruppiUtente = getGruppiUtenteEnte();
			logger.debug("AM_GROUP per "+user.getUsername()+ ": "+gruppiUtente.size());
			try {
	    	
	    		BaseDTO dto = new BaseDTO();
	        	fillUserData(dto);
	        	dto.setObj(ufficioID);
	        	
	        	SsUfficio ufficio = confService.readUfficio(dto);
	        	if(ufficio!=null){
	        	String escludiGruppi = ufficio.getEscludiAmGroup();
		        	if(escludiGruppi!=null && !escludiGruppi.trim().isEmpty()){
		        		String[] grs = escludiGruppi.split("\\|");
		        		List<String> gruppiEsclusi = Arrays.asList(grs);
	
		        		//Verifico che i gruppi cui appartiene l'utente non siano compresi in quelli esclusi dall'accesso
		        		for(AmGroup gr : gruppiUtente){
		        			String gruppoUtente = gr.getName();
		        			for(String gruppoEscluso : gruppiEsclusi){
		        				if(gruppoEscluso.equals(gruppoUtente)) {
		        					ufficioAbilitato=false;
		        					break;
		        				}
		        			}
		        		}
		        		
		        	}
	        	}
	        		
	    	} catch(Exception e) {
	    		logger.error(e.getMessage(), e);
	    		addError("lettura.error");
	    		
	    		ufficioAbilitato = false;
			}
			
		}
		return ufficioAbilitato;	
	}
	
	
    protected String format(String arg){
    	if(arg!=null)
    		return arg;
    	else
    		return "";
    }
    protected String format(Boolean arg){
    	if(arg!=null && arg)
    		return "Si";
    	return "No";
    }
    protected String formatPercentuale(String arg){
    	if(arg!=null)
    		return arg + "%";
    	else
    		return "";
    		
    }	
	protected Long format(Long arg){
    	if(arg!=null)
    		return arg;
    	return arg;
    }

	public CsOSettoreBASIC getSettore(String idSettore) {
		if(idSettore!=null && !idSettore.isEmpty()){
			try {
				OperatoreDTO dto = new OperatoreDTO();
				this.fillUserData(dto);
				dto.setIdSettore(new Long(idSettore));
				return configurationCsEnteBean.findSettoreBASICById(dto);
				
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return null;
	}
	
	protected String getRelazioneParentaleCs(String relazPar){
		it.webred.cs.csa.ejb.dto.BaseDTO cet = new it.webred.cs.csa.ejb.dto.BaseDTO();
		fillEnte(cet);
		if(relazPar!=null && !relazPar.isEmpty()){
			try{
				AccessTableConfigurazioneSessionBeanRemote confService = getConfigurationCsBean();
				cet.setObj(relazPar);
				CsTbTipoRapportoCon cs = confService.mappaRelazioneParentale(cet);
				return cs!=null ? cs.getDescrizione() : relazPar+" *(codice non mappato)";
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return null;
	}
	
/*	protected String getIdExtSoggetto(String cf){
		RicercaSoggettoAnagrafeDTO dto = new RicercaSoggettoAnagrafeDTO();
    	fillUserData(dto);
    	dto.setCodFis(cf);
    	
    	try {
    		
			dto.setDtRif(new Date());
			List<SitDPersona> listp = anagrafeService.getListaPersoneByCF(dto);
			List<String> lstIds = new ArrayList<String>();
			for(SitDPersona p : listp){
				if(!lstIds.contains(p.getIdExt()))
					lstIds.add(p.getIdExt());
			}

			if(lstIds.size()==1)
				return lstIds.get(0);
    		
    	} catch(Exception e) {
    		logger.error(e.getMessage(), e);
    		addError("caricamento.soggetto.error");
		}
    	return null;
	}
	
	protected List<ComponenteFamigliaDTO> getNucleoFamiliareGIT(String idExt, String cf){
		RicercaSoggettoAnagrafeDTO dto = new RicercaSoggettoAnagrafeDTO();
    	fillUserData(dto);
    	dto.setIdSogg(idExt);
    	dto.setCodFis(cf);
    	
    	try {
    		
    		if(idExt==null){
    			dto.setDtRif(new Date());
    			List<SitDPersona> listp = anagrafeService.getListaPersoneByCF(dto);
    			List<String> lstIds = new ArrayList<String>();
    			for(SitDPersona p : listp){
    				if(!lstIds.contains(p.getIdExt()))
    					lstIds.add(p.getIdExt());
    			}

    			if(lstIds.size()==1)
    				dto.setIdSogg(lstIds.get(0));
    		}
    		

    		return anagrafeService.getListaCompFamiglia(dto);
    		
    	} catch(Exception e) {
    		logger.error(e.getMessage(), e);
    		addError("caricamento.soggetto.error");
		}
    	return null;
	}*/
	
	@SuppressWarnings({ "unchecked", "el-syntax" })
	public static <T> T findBean(String beanName) {
		FacesContext context = FacesContext.getCurrentInstance();
		return (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
	}
		
	public boolean canReadNotaDiario(SsDiario nota, String operatoreAccesso, Long organizzazioneId){
		if(nota.getPubblica()) //la nota è pubblica
			return true;
		
		//responsabile dell'organizzazione in cui è stata inserita la nota
		if(this.isResponsabileEnte(nota.getEnte().getCodRouting()))
			return true;
		
		//l'utente che ha scritto la nota è l'operatore corrente
		if(nota.getAutore().equals(this.getUserNameOperatore())) 
			return true;
		
		//l'operatore che risulta registrato in SS_SCHEDA_ACCESSO è l'utente corrente
		if(operatoreAccesso!=null && operatoreAccesso.equals(this.getUserNameOperatore())) 
			return true;

		//l'operatore possiede il permesso di leggere i DIARI in UDC e si è loggato con la stessa organizzazione di creazione della nota
		if(canReadDiario() && nota.getEnte().getId()== organizzazioneId)
			return true;
		
		return false;
	}
	
	public boolean canReadNotaDiario(SsDiario nota,  String operatoreAccesso){
		return canReadNotaDiario(nota, operatoreAccesso, this.getPreselectedPContatto().getOrganizzazione().getId());
	}
	
	public boolean canDeleteNotaDiario(SsDiario nota, String operatoreAccesso, Long anagraficaId ){
		//responsabile dell'organizzazione in cui è stata inserita la nota
		if(this.isResponsabileEnte(nota.getEnte().getCodRouting()))
			return true;
		
		//l'operatore che risulta registrato in SS_SCHEDA_ACCESSO è l'utente corrente
		if(operatoreAccesso!=null && operatoreAccesso.equals(this.getUserNameOperatore()) && nota.getSoggetto().getId().doubleValue()==anagraficaId.doubleValue()) 
			return true;
		
		if(nota.getAutore().equals(this.getUserNameOperatore()) && nota.getSoggetto().getId().doubleValue()==anagraficaId.doubleValue()) 
			return true;
		
		return false;
	}
	
	
	protected SsSchedaAccessoInviante recuperaSsSchedaAccessoInvianteFromSsScheda(SsScheda scheda){
		SsSchedaAccessoInviante schedaAccessoOrig=null;
		
		if(scheda!=null){
			
			BaseDTO dto = new BaseDTO();
			fillUserData(dto);
			dto.setObj(scheda.getId());
			
			schedaAccessoOrig=schedaService.readSsSchedaAccessoInvianteByIdNuovaScheda(dto);
			
		}
		
		return schedaAccessoOrig;
	}
	
	public String getMSG_INFO_NOTA_PRIVATA() {
		String msg = "Le note private possono essere visualizzare dall'utente corrente solo se: ";
		msg +="<ul>";
		msg+="<li>é il responsabile dell'organizzazione in cui è stata inserita la nota</li>";
		msg+="<li>ha creato la scheda</li>";
		msg+="<li>ha inserito la nota</li>";
		msg+="<li>posside il permesso di leggere i diari ed è attualmente autenticato nella stessa oganizzazione in cui è stata inserita la nota.</li>";
		msg+="</ul>";
		return msg;
		
	}
	
	public static Object getArgoEjb(String ejbName) {
		return getEjb("Argo", "Argo_EJB", ejbName);
	}
	
	public static Object getEjb(String ear, String module, String ejbName) {
		Context cont;
		try {
			cont = new InitialContext();
			return cont.lookup("java:global/" + ear + "/" + module + "/" + ejbName);
		} catch (NamingException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public CsTbStatoCivile getStatoCivileByIdExtCeT(String statoCivile) {
		
			if (statoCivile != null && !statoCivile.isEmpty()) {
				it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillUserData(dto);
				dto.setObj(statoCivile);
				CsTbStatoCivile sc = configurationCsBean.getStatoCivileByIdExtCet(dto);
				
				if(sc==null)
					addWarningMessage("Attenzione", "Non è stata configurata la corrisponenza dei codici di stato civile");
				
				return sc;
			}

		return null;
	}
	
	
	public boolean isComuneFuoriAmbito(String codIstatComune) {
		boolean comuneFuoriAmbito = false;
		if(!StringUtils.isBlank(codIstatComune)){
			try {
				
				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
				dto.setObj(codIstatComune);
				List<BigDecimal> risultato = schedaService.abilitaCodIstatComune(dto);
				logger.debug("Res --> " + (risultato.get(0)));
				int num = risultato.get(0).intValue();
				if (num == 0) {
					comuneFuoriAmbito = true;
				} else if (num == 1) {
					comuneFuoriAmbito = false;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				addError("lettura.error");
			}
		}
		return comuneFuoriAmbito;
	}
	
	protected List<Nota> loadNoteDiarioAccessibili(List<SsDiario> diari, String operatore, Long anagraficaId){
		BaseDTO dto = new BaseDTO();
		fillUserData(dto);
		List<Nota> note = new ArrayList<Nota>();
		for (SsDiario nota : diari){
			Long idAnagrafica = nota.getSoggetto().getId();
			dto.setObj(idAnagrafica);
			List<BigDecimal> lstUffici = schedaService.findUfficioNota(dto);
			boolean accessoUffNota = true;
			int i = 0;
			if(lstUffici.size()>1) logger.warn("Alla nota corrente [ID:"+nota.getId()+"] corrispondono più anagrafiche/uffici");
			while(accessoUffNota && i<lstUffici.size()){
				BigDecimal ufficioID = lstUffici.get(i);
				boolean accessoUfficio = canAccessUfficio(ufficioID.longValue());
				if(!accessoUfficio) logger.warn("L'utente corrente non può accedere alla nota [ID:"+nota.getId()+"] poichè associata all'ufficio [ID:"+ufficioID+"]");
				accessoUffNota = accessoUffNota && accessoUfficio;
				i++;
			}
			
			if(accessoUffNota){
				boolean canDelete = anagraficaId==null  ? false : canDeleteNotaDiario(nota, operatore, anagraficaId);
				 note.add(new Nota(nota, this.getCognomeNomeUtente(nota.getAutore()),
						 canReadNotaDiario(nota, operatore),canDelete));
			}
		}
		return note;
	}

	public String getLabelUDCAccesso(){
		if(mappaLabelUDC==null)mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		return this.mappaLabelUDC.get(TabUDC.ACCESSO_TAB);
	}
	public String getLabelUDCSegnalante(){
		if(mappaLabelUDC==null)mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		return this.mappaLabelUDC.get(TabUDC.SEGNALANTE_TAB);
	}
	public String getLabelUDCSegnalato(){
		if(mappaLabelUDC==null)mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		return this.mappaLabelUDC.get(TabUDC.SEGNALATO_TAB);
	}
	public String getLabelUDCRiferimento(){
		if(mappaLabelUDC==null)mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		return this.mappaLabelUDC.get(TabUDC.RIFERIMENTO_TAB);
	}
	public String getLabelUDCMotivazione(){
		if(mappaLabelUDC==null)mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		return this.mappaLabelUDC.get(TabUDC.MOTIVAZIONE_TAB);
	}
	public String getLabelUDCInterventi(){
		if(mappaLabelUDC==null)mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		return this.mappaLabelUDC.get(TabUDC.INTERVENTI_TAB);
	}
	public String getLabelUDCChiusura(){
		if(mappaLabelUDC==null)mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		return this.mappaLabelUDC.get(TabUDC.CHIUSURA_TAB);
	}
	
	protected Object getRequestParameter(String key){
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
	}
	
	 //Inizio SISO-1110
	public  boolean isViewTipoInterventoStandard() {

		boolean render = false;
		String val = getGlobalParameter(DataModelCostanti.AmParameterKey.TIPO_INTERVENTO_VIEW);
		render = val != null && "treeView".equals(val) ? true : false;
		return render;
	}
	
	public String getTitoloNomenclatoreTipoIntervento() {

		String render = "";
		render = getGlobalParameter(DataModelCostanti.AmParameterKey.NOMENCLATORE_TIPO_INTERVENTO_CUSTOM);
		return render;
	}
	public String getLivelliNomenclatore() {

		String render = "";
		render = getGlobalParameter(DataModelCostanti.AmParameterKey.LIVELLI_NOMENCLATORE_INTERVENTO_CUSTOM);
		return render;
	}
		
	//FIne SISO-1110
	
	public Boolean verificaPresenzaRdC(String cf) {
		boolean verifica = false;
		if(!StringUtils.isBlank(cf)){
			it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
	    	fillUserData(dto);
	    	dto.setObj(cf);  
	    	try {
	    		AccessTableSoggettoSessionBeanRemote soggettiService = 
	    				(AccessTableSoggettoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
	    		
	    		verifica = soggettiService.isBeneficiarioRdC(dto);
	    	} catch(Exception e) {
	    		logger.error(e.getMessage(), e);
	    		addError("caricamento.error");
			}
		}
		return verifica;
	}
	
	//SISO-1531
	public Boolean verificaPresenzaDatiEsterni(String cf) {
		Boolean found = false;
		if(!StringUtils.isBlank(cf)){
			it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
	    	fillUserData(dto);
	    	dto.setObj(cf);
	    	try {
	    		AccessTableDatiEsterniSoggettoSessionBeanRemote soggettoService = 
	    				(AccessTableDatiEsterniSoggettoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableDatiEsterniSoggettoSessionBean");
	    		
	    		found = soggettoService.existsPrestazione(dto);
	    		
	    	} catch(Exception e) {
	    		logger.error(e.getMessage(), e);
	    		addError("caricamento.error");
			}
		}
		return found;
	}
	
	protected SsTipoScheda readTipoSchedaFromIdTipoScheda(Long idTipoScheda) {
		try {
			if (idTipoScheda != null) {
				BaseDTO dto = new BaseDTO();
				fillUserData(dto);
				dto.setObj(idTipoScheda);
				SsTipoScheda tipo = confService.readTipoSchedaById(dto);
				return tipo;
			}
		} catch (Exception e) {
			logger.error("Errore recupero Esito Intervento ", e);
			addErrorMessage("Errore recupero esito intervento", "");
		}

		return null;
	}
	
	public static SsIndirizzo ricercaIndirizzoAllProvenienza (String cf, String tipoRicerca){
		SsIndirizzo indirizzoRes = null;
		RicercaAnagraficaParams rab= new RicercaAnagraficaParams(tipoRicerca,true);
		fillEnte(rab);
		rab.setCf(cf);
		
		PersonaDettaglio p=null;
		try {
			p = CsUiCompBaseBean.ricercaSoggettoAllProvenienza(rab);
		} catch (SocioSanitarioException e) {
			logger.error(e.getMessage(), e);
		}
		if(p!=null){
	    	indirizzoRes = new SsIndirizzo();
			indirizzoRes.setVia(p.getIndirizzoCivicoResidenza());
			//indirizzoRes.setNumero(p.getCivicoResidenza());
			AmTabComuni comune = p.getComuneResidenza();
			if(comune!=null){
				indirizzoRes.setProvCod(comune.getSiglaProv());
				indirizzoRes.setComuneCod(comune.getCodIstatComune());
				indirizzoRes.setComuneDes(comune.getDenominazione());
			}else if(p.getNazioneResidenza()!=null){
				AmTabNazioni nazRes = p.getNazioneResidenza();
				indirizzoRes.setStatoCod(nazRes.getCodIstatNazione());
				indirizzoRes.setStatoDes(nazRes.getNazione());
			}
		}
		
		return indirizzoRes;
	}

}
