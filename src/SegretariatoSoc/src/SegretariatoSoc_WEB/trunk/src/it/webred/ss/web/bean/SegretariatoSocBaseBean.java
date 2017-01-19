package it.webred.ss.web.bean;

import it.webred.amprofiler.ejb.anagrafica.AnagraficaService;
import it.webred.amprofiler.ejb.user.UserService;
import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.amprofiler.model.AmGroup;
import it.webred.cet.permission.CeTUser;
import it.webred.cs.csa.ejb.client.AccessTableComuniSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableNazioniSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.data.model.CsOZonaSoc;
import it.webred.cs.data.model.CsTbCittadinanzaAcq;
import it.webred.cs.data.model.CsTbMapTipoRapGit2Cs;
import it.webred.cs.data.model.CsTbPermesso;
import it.webred.cs.data.model.CsTbStatus;
import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.cs.data.model.CsTbTipologiaFamiliare;
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
import it.webred.ct.config.model.AmInstance;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.application.ApplicationService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.ComponenteFamigliaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.IndirizzoAnagrafeDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;
import it.webred.siso.ws.client.anag.client.AnagrafeClient;
import it.webred.siso.ws.client.anag.client.PersonaFindResult;
import it.webred.siso.ws.client.anag.client.RicercaAnagraficaBean;
import it.webred.siso.ws.client.anag.exception.AnagrafeException;
import it.webred.siso.ws.client.anag.exception.AnagrafeSessionException;
import it.webred.ss.data.model.SsIndirizzo;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.web.bean.util.Organizzazione;
import it.webred.ss.web.bean.util.PreselPuntoContatto;
import it.webred.ss.web.bean.util.PuntoContatto;
import it.webred.ss.web.bean.util.Soggetto;
import it.webred.ss.web.bean.util.UfficiTableBean;
import it.webred.ss.web.bean.wizard.Indirizzo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class SegretariatoSocBaseBean {

	protected SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
	protected SimpleDateFormat ddMMyyyyhhmmss = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	private Boolean visPanelStranieri;
	
	protected static final String SCHEDA_KEY = "id";
	protected static final String SOGGETTO_KEY = "sog";
	protected static final String CF_KEY = "cf";
	protected static final String ANAG_SAN_KEY = "idAnaSanitaria";
	protected static final String CREA_NUOVA_SCHEDA = "creaNuovaScheda";

	private AccessTableConfigurazioneSessionBeanRemote configurationCsBean;
	private AccessTableDiarioSessionBeanRemote diarioService;
	private SsSchedaSessionBeanRemote schedaService;
	private final Date currentDate = new Date();
	private Map<String, CsTbTipoRapportoCon> parentelaGitCsMap = new HashMap<String, CsTbTipoRapportoCon>();
	

	// private PuntoContatto preselectedPContatto;

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
	public static final String delete = "DELETE";
	public static final String print = "PRINT";

	public void logAction(String action) {
		logAction(action, null);
	}

	public AccessTableDiarioSessionBeanRemote getDiarioCsBean() throws NamingException {
		if (diarioService == null)
			diarioService =
					(AccessTableDiarioSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableDiarioSessionBean");
		return diarioService;
	}

	public AccessTableConfigurazioneSessionBeanRemote getConfigurationCsBean() throws NamingException {
		if (configurationCsBean == null)
			configurationCsBean =
					(AccessTableConfigurazioneSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
		return configurationCsBean;
	}

	public SsSchedaSessionBeanRemote getSsSchedaService() throws NamingException {
		if (schedaService == null)
			schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface("SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
		return schedaService;
	}
	
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

		facesContext.addMessage(null, new FacesMessage(severity, txt,
				details == null ? "" : details));
	}

	public void addInfo(String msgKey) {
		addInfo(msgKey, null);
	}

	public void addInfo(String msgKey, String detail) {
		addMessage(msgKey, FacesMessage.SEVERITY_INFO, detail);
	}

	public void addWarning(String msgKey) {
		addWarning(msgKey, null);
	}

	public void addWarning(String msgKey, String detail) {
		addMessage(msgKey, FacesMessage.SEVERITY_WARN, detail);
	}

	public static void addError(String msgKey) {
		addError(msgKey, null);
	}

	public static void addError(String msgKey, String detail) {
		addMessage(msgKey, FacesMessage.SEVERITY_ERROR, detail);
	}

	protected void addErrorMessage(String summary, String descrizione) {
		addMessage(summary, descrizione, FacesMessage.SEVERITY_ERROR);
	}

	protected void addWarningMessage(String summary, String descrizione) {
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

	private String getNomeIstanza() {
		String istanza = "SegretariatoSoc";
		try {
			ApplicationService appService = (ApplicationService) ClientUtility.getEjbInterface("CT_Service", "CT_Config_Manager", "ApplicationServiceBean");
			CeTBaseObject cet = new CeTBaseObject();
			fillUserData(cet);
			AmInstance instance = appService.getInstanceByApplicationComune("SegretariatoSoc", cet.getEnteId());
			istanza = instance.getName();
		} catch (Exception e) {
		}
		return istanza;
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
			HashMap<String, String> permessi = user.getPermList();
			// permessi.put("permission@-@SegretariatoSoc@-@NomeDellaFunzioneOAreaFunzionale@-@segrsoc-stampa-statistiche",
			// "permission@-@SegretariatoSoc@-@NomeDellaFunzioneOAreaFunzionale@-@segrsoc-stampa-statistiche");
			// permessi.put("permission@-@"+istanza+"@-@NomeDellaFunzioneOAreaFunzionale@-@segrsoc-invia-caso",
			// "permission@-@"+istanza+"@-@NomeDellaFunzioneOAreaFunzionale@-@segrsoc-invia-caso");

			// Admin segretariato professionale
			// permessi.put("permission@-@"+istanza+"@-@Organization@",
			// "permission@-@"+istanza+"@-@Organization@-@segrsoc-Comune");
			// permessi.put("permission@-@"+istanza+"@-@Role@",
			// "permission@-@"+istanza+"@-@Role@-@segrsoc-Admin");
			// "permission@-@"+istanza+"@-@Task@-@segrsoc-read"
			// "permission@-@"+istanza+"@-@Task@-@segrsoc-write"
			// "permission@-@"+istanza+"@-@Task@-@segrsoc-edit"
			// "permission@-@"+istanza+"@-@Task@-@segrsoc-delete"
			// "permission@-@"+istanza+"@-@Task@-@segrsoc-print"
			// "permission@-@"+istanza+"@-@Task@-@segrsoc-readDiario"
			// "permission@-@"+istanza+"@-@Task@-@segrsoc-readInterventiEconomiciNucleo"
		/*	permessi.put("permission@-@" + istanza + "@-@Task@-@segrsoc-ufficio-0", "permission@-@" + istanza + "@-@Task@-@segrsoc-ufficio-0");
			permessi.put("permission@-@" + istanza + "@-@Task@-@segrsoc-ufficio-1", "permission@-@" + istanza + "@-@Task@-@segrsoc-ufficio-1");
			permessi.put("permission@-@" + istanza + "@-@Task@-@segrsoc-ufficio-2", "permission@-@" + istanza + "@-@Task@-@segrsoc-ufficio-2");
			permessi.put("permission@-@" + istanza + "@-@Task@-@segrsoc-ufficio-3", "permission@-@" + istanza + "@-@Task@-@segrsoc-ufficio-3");
			permessi.put("permission@-@" + istanza + "@-@Task@-@segrsoc-ufficio-4", "permission@-@" + istanza + "@-@Task@-@segrsoc-ufficio-4");
			permessi.put("permission@-@" + istanza + "@-@Task@-@segrsoc-ufficio-5", "permission@-@" + istanza + "@-@Task@-@segrsoc-ufficio-5");
			permessi.put("permission@-@" + istanza + "@-@Task@-@segrsoc-ufficio-6", "permission@-@" + istanza + "@-@Task@-@segrsoc-ufficio-6");
			permessi.put("permission@-@" + istanza + "@-@Task@-@segrsoc-ufficio-7", "permission@-@" + istanza + "@-@Task@-@segrsoc-ufficio-7");
			permessi.put("permission@-@" + istanza + "@-@Task@-@segrsoc-ufficio-8", "permission@-@" + istanza + "@-@Task@-@segrsoc-ufficio-8");
			permessi.put("permission@-@" + istanza + "@-@Task@-@segrsoc-ufficio-9", "permission@-@" + istanza + "@-@Task@-@segrsoc-ufficio-9");
			permessi.put("permission@-@" + istanza + "@-@Task@-@segrsoc-ufficio-10", "permission@-@" + istanza + "@-@Task@-@segrsoc-ufficio-10");
*/
			// Admin ente esterno
			// permessi.put("permission@-@"+istanza+"@-@Organization@",
			// "permission@-@"+istanza+"@-@Organization@-@segrsoc-Caritas");
			// permessi.put("permission@-@"+istanza+"@-@Role@",
			// "permission@-@"+istanza+"@-@Role@-@segrsoc-Admin");
			// permessi.put("permission@-@"+istanza+"@-@Task@-@segrsoc-readDiario",
			// "permission@-@"+istanza+"@-@Task@-@segrsoc-readDiario");
			// permessi.put("permission@-@"+istanza+"@-@Task@-@segrsoc-readInterventiEconomiciNucleo",
			// "permission@-@"+istanza+"@-@Task@-@segrsoc-readInterventiEconomiciNucleo")

			HttpSession session = getRequest().getSession();
			if (session.getAttribute(loginAction) == null) {
				session.setAttribute(loginAction, true);
				logAction(loginAction);
			}
		}
		return user;

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
			for (AmGroup group : user.getGroupList()) {
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
			for (AmGroup group : user.getGroupList()) {
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
			for (AmGroup group : user.getGroupList())
				if (group.getName().contains("RESPONSABILI")) {
					responsabile = true;
					break;
				}
		}

		return responsabile;
	}

	public boolean disableComponent(boolean canAccess) {
		return !canAccess;
	}

	private boolean checkPermesso(String permesso) {
		CeTUser user = getUser();
		if (user != null) {
			String lvl = user.getPermList().get(permesso);
			if (lvl != null && !lvl.isEmpty())
				return true;
		}
		return false;
	}

	private String getPatternPermesso(String permesso) {
		return "permission@-@" + getNomeIstanza() + "@-@SegretariatoSoc@-@" + permesso;
	}

	public boolean canRead() {
		return checkPermesso(getPatternPermesso("segrsoc-read"));
	}

	public boolean canEdit() {
		return checkPermesso(getPatternPermesso("segrsoc-edit"));
	}

	public boolean canWrite() {
		return checkPermesso(getPatternPermesso("segrsoc-write"));
	}

	public boolean canDelete() {
		return checkPermesso(getPatternPermesso("segrsoc-delete"));
	}

	public boolean canPrint() {
		return checkPermesso(getPatternPermesso("segrsoc-print"));
	}

	public boolean canReadDiario() {
		return checkPermesso(getPatternPermesso("segrsoc-readDiario"));
	}

	public boolean canReadInterventiEconomiciNucleo() {
		return checkPermesso(getPatternPermesso("segrsoc-readInterventiEconomiciNucleo"));
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
		
		String ente = getPreselectedOrganizzazione();
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

	private AmTabComuni getComune(String belfiore) {
		AmTabComuni comune = null;
		try {
			AccessTableComuniSessionBeanRemote opService = (AccessTableComuniSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB",
					"AccessTableComuniSessionBean");

			CeTUser user = getUser();
			if (user != null)
				comune = opService.getComuneByBelfiore(belfiore);

		} catch (Exception e) {
			logger.error("Errore recupero getComune", e);
		}
		return comune;
	}

	protected AccessTableConfigurazioneSessionBeanRemote getCsConfigurazioneService() throws NamingException {
		AccessTableConfigurazioneSessionBeanRemote service =
				(AccessTableConfigurazioneSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
		return service;
	}

	protected AccessTableNazioniSessionBeanRemote getAmConfigurazioneService() throws NamingException {
		AccessTableNazioniSessionBeanRemote service2 =
				(AccessTableNazioniSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableNazioniSessionBean");
		return service2;
	}

	public String getZonaSociale() {
		try {
			AccessTableOperatoreSessionBeanRemote opService =
					(AccessTableOperatoreSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableOperatoreSessionBean");

			it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
			this.fillUserData(dto);

			List<CsOZonaSoc> lst = opService.findZoneSocAbilitate(dto);
			if (lst != null && lst.size() > 0)
				return lst.get(0).getNome();

		} catch (Exception e) {
			addError("lettura.error", "Errore recupero zona sociale");
		}

		return "";
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
						this.getSession().setAttribute("mappaAnagraficheUdC", mappaAnagrafiche);
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


	protected String readTipoScheda(Long idTipo) {
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
	}

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
		ParameterService paramService;
		try {

			paramService = (ParameterService) ClientUtility.getEjbInterface("CT_Service", "CT_Config_Manager", "ParameterBaseService");

			AmKeyValueExt amKey = paramService.getAmKeyValueExt(criteria);
			if (amKey != null)
				return amKey.getValueConf();
			else
				return null;

		} catch (NamingException e) {
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

	protected CsTbCittadinanzaAcq getCittadinanzaAcq(Long id) {
		try {
			if (id != null) {
				it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillUserData(d);
				d.setObj(id);
				CsTbCittadinanzaAcq fa = this.getCsConfigurazioneService().getCittadinanzaAcqById(d);
				return fa;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error", "Impossibile recuperare la tipologia di Cittadinanza Acquisita");
		}
		return null;
	}

	protected CsTbTipologiaFamiliare getFamiglia(long id) {
		try {
			
				it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillUserData(d);
				d.setObj(new Long(id));
				CsTbTipologiaFamiliare fa = this.getCsConfigurazioneService().getTipologiaFamiliareById(d);
				return fa;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error", "Impossibile recuperare la tipologiaFamigliare");
		}
		return null;
	}

	protected AmTabNazioni getNazioni(String id) {
		try {
			if (id != null) {
				AmTabNazioni fa = this.getAmConfigurazioneService().getNazioneByIstat(id);
				return fa;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error", "Impossibile recuperare la Ultima Nazionalita");
		}
		return null;
	}

	protected CsTbPermesso getPermesso(String id) {
		try {
			if (id != null) {
				it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillUserData(d);
				d.setObj(id);
				CsTbPermesso cl = this.getCsConfigurazioneService().getPermessoById(d);
				return cl;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error", "Impossibile recuperare Permesso");
		}
		return null;
	}

	protected CsTbStatus getStatus(String id) {
		try {
			if (id != null) {
				it.webred.cs.csa.ejb.dto.BaseDTO d = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillUserData(d);
				d.setObj(id);
				CsTbStatus cl = this.getCsConfigurazioneService().getStatusById(d);
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

	// Carica la lista delle Organizzazioni con Belfiore Specificato
	protected List<CsOOrganizzazione> getListaEnti() {
		try {
			AccessTableConfigurazioneSessionBeanRemote confService = getConfigurationCsBean();
			CeTBaseObject opDto = new CeTBaseObject();
			this.fillUserData(opDto);
			List<CsOOrganizzazione> lstOrg = confService.getOrganizzazioniBelfiore(opDto);
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

	public String getPreselectedOrganizzazione() {
		return getPreselectedPContatto().getOrganizzazione().getBelfiore();
	}

	public void setPreselectedOrganizzazione(String belfiore) {
		UfficiTableBean ub = (UfficiTableBean) this.getBeanReference("ufficiTableBean");
		if (belfiore != null && !belfiore.isEmpty() && !belfiore.equals(this.getPreselectedOrganizzazione())) {
			// Resetto il valore memorizzato precedente
			AmTabComuni comune = this.getComune(belfiore);
			
			it.webred.cs.csa.ejb.dto.BaseDTO b = new it.webred.cs.csa.ejb.dto.BaseDTO();
			this.fillUserData(b);
			b.setObj(belfiore);
			CsOOrganizzazione o = null;
			try {
				o = this.getConfigurationCsBean().getOrganizzazioneByBelfiore(b);
			} catch (Exception e) {
				logger.error(e);
			}
			
			if(comune!=null)
				this.getPreselectedPContatto().setOrganizzazione
					(new Organizzazione(o != null ? o.getId() : null, belfiore, comune.getDenominazione()));
			else
				this.getPreselectedPContatto().setOrganizzazione
					(new Organizzazione(o != null ? o.getId() : null, belfiore, o!=null ? o.getNome() : "- denominazione non trovata -"));
			
			ub.onChangeOrganizzazione();

			NavigationBean nb = (NavigationBean) this.getBeanReference("navigationBean");
			nb.goHome();
		}/*
		 * else ub.onChangeOrganizzazione();
		 */
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
		//PuntoContatto preselectedPContatto = this.getPContattoFromSession();
		PreselPuntoContatto pContMan = (PreselPuntoContatto)getBeanReference("preselPuntoContatto");
		
		if(pContMan==null)
			pContMan = new PreselPuntoContatto();
		return pContMan.getPuntoContatto();
		/*if (preselectedPContatto == null) {
			preselectedPContatto = new PuntoContatto();
			this.getSession().setAttribute("preselectedPContatto", preselectedPContatto);
		}*/
	}

	protected String getDescrizioneIndirizzo(SsIndirizzo jpa) {
		String descrizione = "";
		String comune = "";
		if (jpa != null) {
			comune = this.getDescrizioneComune(jpa.getComune());
			descrizione = (jpa.getVia() != null ? jpa.getVia() : "") + ", " + comune;
		}
		return descrizione;
	}
	
	protected String getDescrizioneComune(String istat){
		String comune = "";
		if (istat != null) {
			ComuneBean comuneRes = new Indirizzo().getComune(istat);
			comune = comuneRes != null ? comuneRes.getDenominazione() + " (" + comuneRes.getSiglaProv() + ")" : "";
		}
		return comune;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public class AnagRegUser {
		private String username;
		private String password;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

	}

	protected AnagRegUser getAnagRegUser() throws NamingException {
		ParameterService paramService = (ParameterService) ClientUtility.getEjbInterface("CT_Service", "CT_Config_Manager", "ParameterBaseService");
		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setKey("anagreg.username");
		criteria.setComune(null);
		criteria.setSection(null);
		AmKeyValueExt amKey = paramService.getAmKeyValueExt(criteria);
		if (amKey != null && amKey.getValueConf() != null) {
			AnagRegUser ars = new AnagRegUser();
			ars.setUsername(amKey.getValueConf());
			criteria.setKey("anagreg.password");
			criteria.setComune(null);
			criteria.setSection(null);
			amKey = paramService.getAmKeyValueExt(criteria);
			if (amKey != null && amKey.getValueConf() != null) {
				ars.setPassword(amKey.getValueConf());
				return ars;
			} else
				logger.warn("RICERCA IN ANAGRAFE SANITARIA: Password utente anagrafe regionale non trovata");
		} else
			logger.warn("RICERCA IN ANAGRAFE SANITARIA: Username utente anagrafe regionale non trovato");
		return null;
	}

	protected URL getMediciWebServiceWSDLLocation() throws NamingException {
		ParameterService paramService = (ParameterService) ClientUtility.getEjbInterface("CT_Service", "CT_Config_Manager", "ParameterBaseService");
		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setKey("mediciws.url");
		criteria.setComune(null);
		criteria.setSection(null);
		AmKeyValueExt amKey = paramService.getAmKeyValueExt(criteria);
		URL url = null;
		if (amKey != null && amKey.getValueConf() != null) {
			String urlString = amKey.getValueConf();
			try {
				url = new URL(urlString);
				return url;
			} catch (MalformedURLException e) {
				url = null;
			}
		} else
			logger.warn("RICERCA IN ANAGRAFE SANITARIA: Parametro 'mediciws.url' non definito");
		return url;
	}

	protected URL getAnagRegWebServiceWSDLLocation() throws NamingException {
		ParameterService paramService = (ParameterService) ClientUtility.getEjbInterface("CT_Service", "CT_Config_Manager", "ParameterBaseService");
		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setKey("anagreg.url");
		criteria.setComune(null);
		criteria.setSection(null);
		AmKeyValueExt amKey = paramService.getAmKeyValueExt(criteria);
		URL url = null;
		if (amKey != null && amKey.getValueConf() != null) {
			String urlString = amKey.getValueConf();
			try {
				url = new URL(urlString);
				return url;
			} catch (MalformedURLException e) {
				url = null;
			}
		} else
			logger.warn("RICERCA IN ANAGRAFE SANITARIA: Parametro 'anagreg.url' non definito");
		return url;
	}

	protected PersonaFindResult getPersonaDaAnagSanitaria(String id) throws AnagrafeException, AnagrafeSessionException, NamingException {
		PersonaFindResult p = null;
		AnagRegUser anagRegUser = getAnagRegUser();
		// precarico anagrafica
		URL wsdlLocation = getAnagRegWebServiceWSDLLocation();
		AnagrafeClient anag = new AnagrafeClient(wsdlLocation);
		if (anagRegUser != null && wsdlLocation != null) {
			RicercaAnagraficaBean rb = new RicercaAnagraficaBean();
			rb.setUsername(anagRegUser.getUsername());
			rb.setPassword(anagRegUser.getPassword());
			anag.openSession(rb);
			RicercaAnagraficaBean rab = new RicercaAnagraficaBean();
			rab.setIdPaziente(id);
			p = anag.getDatiAnagraficiBaseByIdPaziente(rab);
			anag.closeSession();
		}
		return p;
	}

	protected PersonaFindResult getPersonaDaAnagSanitaria(String cognome, String nome, String codFiscale) throws AnagrafeException, AnagrafeSessionException, NamingException {
		PersonaFindResult p = null;
		if (cognome != null && nome != null && codFiscale != null) {
			AnagRegUser anagRegUser = getAnagRegUser();
			// precarico anagrafica
			URL wsdlLocation = getAnagRegWebServiceWSDLLocation();
			if (anagRegUser != null && wsdlLocation != null) {
				try {
					AnagrafeClient anag = new AnagrafeClient(wsdlLocation);
					RicercaAnagraficaBean rb = new RicercaAnagraficaBean();
					rb.setUsername(anagRegUser.getUsername());
					rb.setPassword(anagRegUser.getPassword());
					anag.openSession(rb);
					RicercaAnagraficaBean rab = new RicercaAnagraficaBean();
					rab.setCognomePaziente(cognome);
					rab.setNomePaziente(nome);
					List<PersonaFindResult> listaPersoneCognomeNome = anag.findCognomeNome(rab);
					if (listaPersoneCognomeNome != null) {
						boolean trovato = false;
						int index = 0;
						while (!trovato && index < listaPersoneCognomeNome.size()) {
							PersonaFindResult persona = listaPersoneCognomeNome.get(index);
							if (persona.getCodfisc().trim().toUpperCase().equals(codFiscale.trim().toUpperCase())) {
								String idPaziente = persona.getIdPaziente();
								if (idPaziente != null) {
									rab.setIdPaziente(idPaziente);
								}
								p = anag.getDatiAnagraficiBaseByIdPaziente(rab);
							}
							index++;
						}
					}
					anag.closeSession();
				} catch (Exception e) {
					logger.error("Errore recupero medico soggetto da anagrafe sanitaria per cognome, nome e codice fiscale " + e.getMessage(), e);
				}
			}
		}
		return p;
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

	protected IIntermediazioneAb getSchedaJsonIntermediazioneAb(Long schedaId) {
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

	protected IOrientamentoLavoro getSchedaJsonOrientamentoLavoro(Long schedaId) {
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

	protected IMediazioneCult getSchedaJsonMediazioneCult(Long schedaId){
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
	
	protected IOrientamentoIstruzione getSchedaJsonOrientamentoIstruzione(Long schedaId){
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
	
	protected IOrientamentoIstruzione nuovaDaSchedaJsonOrientamentoIstruzione(Long schedaId){
		IOrientamentoIstruzione man1 = this.getSchedaJsonOrientamentoIstruzione(schedaId);
		if(man1!=null) return OrientamentoIstruzioneManBaseBean.init(man1);
		else return null;
	}
	
	protected IMediazioneCult nuovaDaSchedaJsonMediazioneCulturale(Long schedaId){
		IMediazioneCult man1 = this.getSchedaJsonMediazioneCult(schedaId);
		if(man1!=null) return MediazioneCultManBaseBean.init(man1);
		else return null;
	}

	protected IIntermediazioneAb nuovaDaSchedaJsonIntermediazioneAb(Long schedaId){
		IIntermediazioneAb man1 = this.getSchedaJsonIntermediazioneAb(schedaId);
		if(man1!=null) return IntermediazioneManBaseBean.init(man1);
		else return null;
	}

	protected IOrientamentoLavoro nuovaDaSchedaJsonOrientamentoLavoro(Long schedaId){
		IOrientamentoLavoro man1 = this.getSchedaJsonOrientamentoLavoro(schedaId);
		if(man1!=null) return OrientamentoLavoroManBaseBean.init(man1);
		else return null;
	}


	public boolean isVisPanelStranieri() {
		if(this.visPanelStranieri==null){
			visPanelStranieri=false;
			try{
				ParameterService paramService = (ParameterService) ClientUtility.getEjbInterface("CT_Service", "CT_Config_Manager", "ParameterBaseService");
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
	

/*	public boolean canAccessUfficio(Long ufficio) {
		return checkPermesso("permission@-@" + getNomeIstanza() + "@-@Task@-@segrsoc-ufficio-" + ufficio);
	}*/
	
	public boolean canAccessUfficio(Long ufficioID){
		CeTUser user = getUser();
		boolean ufficioAbilitato = true;
		if (user != null){
			List<AmGroup> gruppiUtente = user.getGroupList();
			logger.debug("AM_GROUP per "+user.getUsername()+ ": "+gruppiUtente.size());
			try {
	    		SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
	    				"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
				
	    		BaseDTO dto = new BaseDTO();
	        	fillUserData(dto);
	        	dto.setObj(ufficioID);
	        	
	        	String escludiGruppi = schedaService.readUfficio(dto).getEscludiAmGroup();
	        	if(escludiGruppi!=null && !escludiGruppi.trim().isEmpty()){
	        		String[] grs = escludiGruppi.split("\\|");
	        		List<String> gruppiEsclusi = Arrays.asList(grs);

	        		//Verifico che i gruppi cui appartiene l'utente non siano compresi in quelli esclusi dall'accesso
	        		int i=0;
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
			AccessTableOperatoreSessionBeanRemote bean;
			try {
				bean = (AccessTableOperatoreSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableOperatoreSessionBean");
				OperatoreDTO dto = new OperatoreDTO();
				this.fillUserData(dto);
				dto.setIdSettore(new Long(idSettore));
				return bean.findSettoreBASICById(dto);
				
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return null;
	}
	
	protected String getMessaggioPrivacy(Date data){
		return "Modulo privacy sottoscritto in data "+ddMMyyyy.format(data);
	}
	
	protected String getRelazioneParentaleCs(String relazPar){
		if(parentelaGitCsMap.isEmpty()){
			AccessTableConfigurazioneSessionBeanRemote configService;
			try{
				configService	= (AccessTableConfigurazioneSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
			
				CeTBaseObject cet = new CeTBaseObject();
				fillEnte(cet);
				List<CsTbMapTipoRapGit2Cs> lista = configService.caricaMappaRelazioniParentaliEnte(cet);
				for(CsTbMapTipoRapGit2Cs cc : lista)
					parentelaGitCsMap.put(cc.getId().getCodOrig(), cc.getCsTbTipoRapportoCon());
				
			} catch (Exception e) {
				logger.error(e);
			}
		}
		CsTbTipoRapportoCon cs =  parentelaGitCsMap.get(relazPar);
		return cs!=null ? cs.getDescrizione() : relazPar+" *(codice non mappato)";
	}
	
	protected String getIdExtSoggetto(String cf){
		RicercaSoggettoAnagrafeDTO dto = new RicercaSoggettoAnagrafeDTO();
    	fillUserData(dto);
    	dto.setCodFis(cf);
    	
    	try {
    		
    		AnagrafeService anagrafeService = (AnagrafeService) ClientUtility.getEjbInterface(
    				"CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");
    		
			dto.setDtRif(new Date());
			List<SitDPersona> listp = anagrafeService.getListaPersoneByCF(dto);
			List<String> lstIds = new ArrayList<String>();
			for(SitDPersona p : listp){
				if(!lstIds.contains(p.getIdExt()))
					lstIds.add(p.getIdExt());
			}

			if(lstIds.size()==1)
				return lstIds.get(0);
    		
    	} catch(NamingException e) {
    		logger.error(e.getMessage(), e);
    		addError("caricamento.soggetto.error");
		}
    	return null;
	}
	
	protected List<ComponenteFamigliaDTO> getNucleoFamiliare(String idExt, String cf){
		RicercaSoggettoAnagrafeDTO dto = new RicercaSoggettoAnagrafeDTO();
    	fillUserData(dto);
    	dto.setIdSogg(idExt);
    	dto.setCodFis(cf);
    	
    	try {
    		
    		AnagrafeService anagrafeService = (AnagrafeService) ClientUtility.getEjbInterface(
    				"CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");
    		
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
    		
    	} catch(NamingException e) {
    		logger.error(e.getMessage(), e);
    		addError("caricamento.soggetto.error");
		}
    	return null;
	}
	protected IndirizzoAnagrafeDTO getResidenzaFromAnagrafe(SitDPersona soggetto) {
		try {

			AnagrafeService anagrafeService = (AnagrafeService) ClientUtility
					.getEjbInterface("CT_Service", "CT_Service_Data_Access",
							"AnagrafeServiceBean");

			RicercaSoggettoAnagrafeDTO dto = new RicercaSoggettoAnagrafeDTO();
			fillUserData(dto);
			dto.setIdSogg(soggetto.getIdExt());
			IndirizzoAnagrafeDTO indirizzo = anagrafeService.getIndirizzoPersona(dto);
			return indirizzo;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
    		addError("caricamento.soggetto.error");
		}

		return null;
	}
	
}

