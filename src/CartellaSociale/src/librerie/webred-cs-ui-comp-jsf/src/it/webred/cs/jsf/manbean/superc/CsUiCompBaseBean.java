package it.webred.cs.jsf.manbean.superc;

import it.webred.amprofiler.ejb.anagrafica.AnagraficaService;
import it.webred.amprofiler.ejb.user.UserService;
import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.amprofiler.model.AmGroup;
import it.webred.amprofiler.model.AmUser;
import it.webred.cet.permission.CeTUser;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableMediciSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.PermessiCasellario;
import it.webred.cs.data.DataModelCostanti.PermessiCaso;
import it.webred.cs.data.DataModelCostanti.PermessiErogazioneInterventi;
import it.webred.cs.data.DataModelCostanti.PermessiFascicolo;
import it.webred.cs.data.DataModelCostanti.PermessiSchedeSegr;
import it.webred.cs.data.DataModelCostanti.TipoStatoErogazione;
import it.webred.cs.data.model.CsCMedico;
import it.webred.cs.data.model.CsCfgParametri;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreAnagrafica;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.jsf.Costanti.TipoPermessoErogazioneInterventi;
import it.webred.cs.jsf.manbean.exception.CsUiCompException;
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
import it.webred.cs.sociosan.ejb.client.AtlanteSessionBeanRemote;
import it.webred.cs.sociosan.ejb.dto.ServiziDTO;
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmComune;
import it.webred.ct.config.model.AmInstance;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.application.ApplicationService;
import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.siso.esb.Medico;
import it.webred.siso.esb.client.MedicoClient;
import it.webred.siso.ws.client.anag.client.AnagrafeClient;
import it.webred.siso.ws.client.anag.client.PersonaFindResult;
import it.webred.siso.ws.client.anag.client.RicercaAnagraficaBean;
import it.webred.siso.ws.client.anag.exception.AnagrafeException;
import it.webred.siso.ws.client.anag.exception.AnagrafeSessionException;
import it.webred.utils.GenericTuples;
import it.webred.utils.GenericTuples.T2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class CsUiCompBaseBean {

	private String logoBasePath;
	protected String dirLoghi = "/images/logo/";
	private StreamedContent logoComune;

	protected final String filtroCasi_STATO="filtroCasi_STATO";
	protected final String filtroCasi_OPERATORE="filtroCasi_OPERATORE";
	protected final String filtroCasi_NON_RESPONSABILE="filtroCasi_NON_RESPONSABILE";
	protected final String filtroCasi_STUDIO="filtroCasi_STUDIO";
	protected final String filtroCasi_LAVORO="filtroCasi_LAVORO";
	protected final String filtroCasi_TUTELA="filtroCasi_TUTELA";
	
	protected SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");

	public static Logger logger = Logger.getLogger("carsociale.log");

	protected void clearParametriFiltro(){
		getSession().setAttribute(filtroCasi_STATO, null);
		getSession().setAttribute(filtroCasi_OPERATORE, null);
		getSession().setAttribute(filtroCasi_NON_RESPONSABILE, null);
		getSession().setAttribute(filtroCasi_STUDIO, null);
		getSession().setAttribute(filtroCasi_LAVORO, null);
		getSession().setAttribute(filtroCasi_TUTELA, null);
	}
	
	private static ResourceBundle bundle;

	static {
		bundle = ResourceBundle.getBundle("it.webred.cs.resources.messages");
	}

	protected ComuneService comuneService = (ComuneService) getEjb("CT_Service", "CT_Config_Manager", "ComuneServiceBean");
	protected AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB",
			"AccessTableDiarioSessionBean");

	public Object getReferencedBean(String nome) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, nome);
	}

	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	protected static HttpServletResponse getResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}

	protected void addErrorCampiObbligatori(String nomeTab, String campo, String descrizione) {
		String summary = (nomeTab != null ? nomeTab + ": " : "") + campo + " è un campo obbligatorio";
		this.addWarning(summary, descrizione);
	}

	protected void addErrorDialog(String summary, String descrizione) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, descrizione != null ? descrizione : "");
		RequestContext.getCurrentInstance().showMessageInDialog(message);
	}

	protected void addWarningDialog(String summary, String descrizione) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, descrizione != null ? descrizione : "");
		RequestContext.getCurrentInstance().showMessageInDialog(message);
	}

	protected void addInfoDialog(String summary, String descrizione) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, descrizione != null ? descrizione : "");
		RequestContext.getCurrentInstance().showMessageInDialog(message);
	}

	protected void addError(String summary, String descrizione) {
		addMessage(summary, descrizione, FacesMessage.SEVERITY_ERROR);
	}

	protected void addWarning(String summary, String descrizione) {
		addMessage(summary, descrizione, FacesMessage.SEVERITY_WARN);
	}

	protected void addInfo(String summary, String descrizione) {
		addMessage(summary, descrizione, FacesMessage.SEVERITY_INFO);
	}

	protected void addMessage(String fieldId, String summary, String descrizione, FacesMessage.Severity severity) {
		FacesMessage message = new FacesMessage(severity, summary, descrizione != null ? descrizione : "");
		FacesContext.getCurrentInstance().addMessage(fieldId, message);
	}

	protected void addMessage(String summary, String descrizione, FacesMessage.Severity severity) {
		FacesMessage message = new FacesMessage(severity, summary, descrizione != null ? descrizione : "");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public static void addMessageFromProperties(String messageKey, FacesMessage.Severity severity) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String txt = bundle.getString(messageKey);
		String desc = txt;

		facesContext.addMessage(null, new FacesMessage(severity, txt, desc));
	}

	public static void addMessageDialogFromProperties(String messageKey, FacesMessage.Severity severity) {
		String txt = bundle.getString(messageKey);
		String desc = txt;

		RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(severity, txt, desc));
	}

	public void addInfoFromProperties(String msgKey) {
		addMessageFromProperties(msgKey, FacesMessage.SEVERITY_INFO);
	}

	public void addWarningFromProperties(String msgKey) {
		addMessageFromProperties(msgKey, FacesMessage.SEVERITY_WARN);
	}

	public static void addErrorFromProperties(String msgKey) {
		addMessageFromProperties(msgKey, FacesMessage.SEVERITY_ERROR);
	}

	public void addInfoDialogFromProperties(String msgKey) {
		addMessageDialogFromProperties(msgKey, FacesMessage.SEVERITY_INFO);
	}

	public void addWarningDialogFromProperties(String msgKey) {
		addMessageDialogFromProperties(msgKey, FacesMessage.SEVERITY_WARN);
	}

	public static void addErrorDialogFromProperties(String msgKey) {
		addMessageDialogFromProperties(msgKey, FacesMessage.SEVERITY_ERROR);
	}

	public static CeTUser getUser() {
		HttpSession session = getSession();
		if (session != null)
			return (CeTUser) session.getAttribute("user");
		else
			return null;
	}

	public static CeTBaseObject fillEnte(CeTBaseObject ro) {

		CeTUser user = getUser();
		CsOOperatoreSettore opSettore = getCurrentOpSettore();
		if (user != null) {
			ro.setEnteId(user.getCurrentEnte());
			ro.setUserId(user.getUsername());
			ro.setSessionId(user.getSessionId());
		}
		if (opSettore != null && opSettore.getCsOSettore().getCsOOrganizzazione().getBelfiore() != null)
			ro.setEnteId(opSettore.getCsOSettore().getCsOOrganizzazione().getBelfiore());
		return ro;
	}

	public UIComponent findComponent(final String id) {
		FacesContext context = FacesContext.getCurrentInstance();
		UIViewRoot root = context.getViewRoot();
		final UIComponent[] found = new UIComponent[1];
		root.visitTree(VisitContext.createVisitContext(context), new VisitCallback() {
			@Override
			public VisitResult visit(VisitContext context, UIComponent component) {
				if (component.getId().equals(id)) {
					found[0] = component;
					return VisitResult.COMPLETE;
				}
				return VisitResult.ACCEPT;
			}
		});
		return found[0];
	}

	public static CsOOperatoreSettore getCurrentOpSettore() {
		return (CsOOperatoreSettore) getSession().getAttribute("operatoresettore");
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<CsOOperatoreSettore> getTempListaSett() {
		return (ArrayList<CsOOperatoreSettore>) getSession().getAttribute("tempListaSett");
	}

	@SuppressWarnings("unchecked")
	public List<AmGroup> getListaGruppi() {
		return (List<AmGroup>) getSession().getAttribute("gruppi");
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

	public static boolean checkPermesso(String item, String permesso) {
		@SuppressWarnings("unchecked")
		HashMap<String, String> map = (HashMap<String, String>) getSession().getAttribute("permessiGruppoSettore");
		if (map != null && map.get(getPatternPermesso(item, permesso)) != null)
			return true;

		return false;
	}

	public static boolean checkPermesso(String permesso) {
		@SuppressWarnings("unchecked")
		HashMap<String, String> map = (HashMap<String, String>) getSession().getAttribute("permessiGruppoSettore");
		if (map != null && map.get(getPatternPermesso(permesso)) != null)
			return true;

		return false;
	}

	private static HashMap<String, String> cacheNomeIstanza = new HashMap<String, String>();

	private static String getNomeIstanza() {
		String nome = "CarSociale";
		CeTBaseObject cet = new CeTBaseObject();
		fillEnte(cet);
		if (cacheNomeIstanza.get(cet.getEnteId()) != null)
			return cacheNomeIstanza.get(cet.getEnteId());

		try {
			ApplicationService appService = (ApplicationService) getEjb("CT_Service", "CT_Config_Manager", "ApplicationServiceBean");
			AmInstance instance = appService.getInstanceByApplicationComune("CarSociale", cet.getEnteId());
			nome = instance.getName();
			cacheNomeIstanza.put(cet.getEnteId(), nome);
		} catch (Exception e) {
			logger.error("", e);
		}
		return nome;
	}

	private static String getPatternPermesso(String nomePermesso) {

		// ES. permission@-@CarSociale@-@CarSociale@-@Crea un caso
		String item = "CarSociale";
		return getPatternPermesso(item, nomePermesso);

	}

	@SuppressWarnings("unused")
	private static String getPatternPermesso(String item, String nomePermesso) {
		String istanza = getNomeIstanza();
		return "permission@-@" + istanza + "@-@" + item + "@-@" + nomePermesso;
	}

	private static HashMap<String, String> cacheTipoApplicazione = new HashMap<String, String>();

	protected String getTipoApplicazione() {
		String paramTipoApplicazione = null;
		BaseDTO baseDto = new BaseDTO();
		fillEnte(baseDto);

		if (baseDto.getEnteId() != null) {

			String key = baseDto.getEnteId();
			if (cacheTipoApplicazione.containsKey(key))
				if (cacheTipoApplicazione.get(key) != null)
					return cacheTipoApplicazione.get(key).toLowerCase();
				else
					logger.warn("Attenzione: smartwelfare.tipoApplicazione non impostata per il comune " + baseDto.getEnteId());

			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setKey("smartwelfare.tipoApplicazione");
			criteria.setComune(baseDto.getEnteId());
			criteria.setSection("param.comune");
			paramTipoApplicazione = this.getParametro(criteria);
			cacheTipoApplicazione.put(baseDto.getEnteId(), paramTipoApplicazione);
			if (paramTipoApplicazione == null)
				logger.warn("Attenzione: smartwelfare.tipoApplicazione non impostata per il comune " + baseDto.getEnteId());
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

	protected Object getBeanReference(String beanName) {
		FacesContext context = FacesContext.getCurrentInstance();
		Application a = context.getApplication();
		Object o = a.getVariableResolver().resolveVariable(context, beanName);
		return o;
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

	protected String getParametro(String sezione, String chiave) {

		try {
			AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB",
					"AccessTableConfigurazioneSessionBean");
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(sezione);
			dto.setObj2(chiave);
			CsCfgParametri csParam = confService.getParametro(dto);

			if (csParam != null)
				return csParam.getValore();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return null;

	}

	public String getNomeEnte() {
		AmComune am = comuneService.getComune(getUser().getCurrentEnte());
		String nomeEnte = am != null ? am.getDescrizione() : "";
		nomeEnte = nomeEnte.substring(0, 1).toUpperCase() + nomeEnte.substring(1).toLowerCase();
		return nomeEnte;
	}

	protected String getDirDatiDiogene() {
		if (this.logoBasePath == null) {
			ParameterService paramService = (ParameterService) getEjb("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setKey("dir.files.datiDiogene");
			criteria.setComune(null);
			criteria.setSection(null);

			AmKeyValueExt amKey = paramService.getAmKeyValueExt(criteria);
			if (amKey != null)
				this.logoBasePath = amKey.getValueConf();
			else
				logger.error("Attenzione: Il path per il recupero logo del report non è impostato");
		}
		return logoBasePath;
	}

	private static HashMap<String, Boolean> cacheVisualizzaFooter = new HashMap<String, Boolean>();

	public boolean isVisualizzaFooter() {
		CeTBaseObject cet = new CeTBaseObject();
		fillEnte(cet);

		if (cacheVisualizzaFooter.get(cet.getEnteId()) != null) {
			return cacheVisualizzaFooter.get(cet.getEnteId());
		}

		boolean visualizzaFooter = false;
		if (cet.getEnteId() != null) {
			ParameterService paramService = (ParameterService) getEjb("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setKey("visualizza.footer");
			criteria.setComune(cet.getEnteId());
			criteria.setInstance(getNomeIstanza());

			AmKeyValueExt amKey = paramService.getAmKeyValueExt(criteria);
			if (amKey != null && "1".equals(amKey.getValueConf()))
				visualizzaFooter = true;
		}
		cacheVisualizzaFooter.put(cet.getEnteId(), visualizzaFooter);
		return visualizzaFooter;
	}

	protected String getPathLoghi() {
		BaseDTO baseDto = new BaseDTO();
		fillEnte(baseDto);
		String pathLoghi = null;
		if (baseDto.getEnteId() != null) {
			String dir = this.getDirDatiDiogene();
			if (dir != null)
				pathLoghi = dir + baseDto.getEnteId() + dirLoghi;
		}
		return pathLoghi;
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

	public StreamedContent getLogoComune() {
		return this.getLogo("logo_intestazione.jpg");
	}

	public StreamedContent getLogoHeader1() {
		return this.getLogo("logo-header1.jpg");
	}

	public StreamedContent getLogoHeader2() {
		return this.getLogo("logo-header2.jpg");
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

	public String getLabelSegrSociale() {
		return this.getTipoApplicazione().equalsIgnoreCase("udc") ? "Ufficio della Cittadinanza" : "Segretariato Sociale";
	}

	public static String getPermessoErogazioneInterventi() {
		String permesso = null;
		if (checkPermesso(PermessiErogazioneInterventi.AUTORIZZA))
			permesso = TipoPermessoErogazioneInterventi.PERMESSO_AUTORIZZATIVO;
		else if (checkPermesso(PermessiErogazioneInterventi.EROGA))
			permesso = TipoPermessoErogazioneInterventi.PERMESSO_EROGATIVO;

		if (permesso == null)
			logger.debug("Permesso Erogazione Interventi non supportato => " + permesso);

		return permesso;
	}

	public boolean isPermessoAutorizzativo(){
		return TipoPermessoErogazioneInterventi.PERMESSO_AUTORIZZATIVO.equals(getPermessoErogazioneInterventi());
	}
	
	public boolean isPermessoErogativo(){
		return TipoPermessoErogazioneInterventi.PERMESSO_EROGATIVO.equals(getPermessoErogazioneInterventi());
	}
	
	public boolean isAbilitaErogazioneInterventi() {
		return checkPermesso(PermessiErogazioneInterventi.ABILITA);
	}
	
	public boolean isRenderListaCasi() {
		return checkPermesso(PermessiCaso.VISUALIZZAZIONE_LISTA_CASI);
	}

	public boolean isRichiediInterventi() {
		return checkPermesso(PermessiErogazioneInterventi.RICHIEDI_INTERVENTI);
	}
	
	public boolean isRenderListaSchedeSegr() {
		return checkPermesso(PermessiSchedeSegr.VISUALIZZA_SCHEDE_SEGR);
	}
	
	public boolean isEsportaCasellario() {
		return checkPermesso(PermessiCasellario.ESPORTA_CASELLARIO);
	}
	
	public boolean isRenderVisualizzaDocIndividuali() {
		return checkPermesso(PermessiFascicolo.UPLOAD_DOC_INDIVIDUALI);
	}


	protected List<String> getListaTipoStatoErogazioneByPermessoErogazione() {
		List<String> lstStati = new LinkedList<String>();
		String permesso = getPermessoErogazioneInterventi();
		if (TipoPermessoErogazioneInterventi.PERMESSO_AUTORIZZATIVO.equals(permesso)) {
			lstStati.add(TipoStatoErogazione.PRELIMINARE);
			lstStati.add(TipoStatoErogazione.EROGATIVO);
		} else
			lstStati.add(TipoStatoErogazione.EROGATIVO);

		return lstStati;
	}

	public static AmAnagrafica getAnagraficaByUsername(String username) {
		HashMap<String, AmAnagrafica> mappaOperatori = (HashMap<String, AmAnagrafica>) getSession().getAttribute("mappaAnagraficaOperatori");
		if (mappaOperatori == null)
			mappaOperatori = new HashMap<String, AmAnagrafica>();

		AmAnagrafica amAna = (AmAnagrafica) mappaOperatori.get(username);

		try {

			if (amAna == null && username != null) {
				AnagraficaService anagraficaService = (AnagraficaService) ClientUtility.getEjbInterface("AmProfiler", "AmProfilerEjb", "AnagraficaServiceBean");
				logger.debug("getAnagraficaByUsername: " + username);
				amAna = anagraficaService.findAnagraficaByUserName(username);
				mappaOperatori.put(username, amAna);
				getSession().setAttribute("mappaAnagraficaOperatori", mappaOperatori);
			}
		} catch (Exception e) {
			logger.error("getAnagraficaByUsername", e);
		}
		return amAna;
	}

	public static AmUser getUserByUsername(String username) {
		AmUser user = null;
		AmAnagrafica amAna = getAnagraficaByUsername(username);
		if (amAna != null)
			user = amAna.getAmUser();
		else {
			try {
				UserService userService = (UserService) ClientUtility.getEjbInterface("AmProfiler", "AmProfilerEjb", "UserServiceBean");
				if (amAna == null)
					user = userService.getUserByName(username);

			} catch (Exception e) {
				logger.error("getUserByUsername", e);
			}
		}
		return user;
	}

	public String getCognomeNomeUtente(String username) {
		AmAnagrafica ana = getAnagraficaByUsername(username);
		if (ana != null)
			return ana.getCognome() + " " + ana.getNome();
		else
			return username;
	}
	

	public String getDenominazioneOperatore(CsOOperatore o) {
		if(o!=null){
			CsOOperatoreAnagrafica ana = o.getCsOOperatoreAnagrafica();
			if (ana != null)
				return ana.getCognome() + " " + ana.getNome();
			else
				return o.getUsername();
		}else
			return "";
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

	protected AnagRegUser getAnagRegUser() {
		ParameterService paramService = (ParameterService) getEjb("CT_Service", "CT_Config_Manager", "ParameterBaseService");
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

	protected URL getMediciWebServiceWSDLLocation() {
		ParameterService paramService = (ParameterService) getEjb("CT_Service", "CT_Config_Manager", "ParameterBaseService");
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

	private URL stringToUrl(String urlString) {
		URL url;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			url = null;
		}
		return url;
	}

	protected URL getAnagRegWebServiceWSDLLocation() {
		String urlString = this.getConfigParameter("anagreg.url");
		return stringToUrl(urlString);

	}

	protected URL getSisoWSDLLocation() {
		return getAnagRegWebServiceWSDLLocation();

	}

	protected GenericTuples.T2<String, String> getAtlanteLogin() {
		String user = this.getConfigParameter("atlantews.username");
		String password = this.getConfigParameter("atlantews.password");
		GenericTuples.T2<String, String> login = null;
		if (user != null && password != null)
			login = new T2<String, String>(user, password);
		return login;
	}

	private String getConfigParameter(String paramName) {
		ParameterService paramService = (ParameterService) getEjb("CT_Service", "CT_Config_Manager", "ParameterBaseService");
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

	protected PersonaFindResult getPersonaDaAnagSanitaria(String id) throws AnagrafeException, AnagrafeSessionException {
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

	protected ServiziDTO getServiziSanitariBase(String cf) throws CsUiCompException {
		try {
			AtlanteSessionBeanRemote bean = (AtlanteSessionBeanRemote) ClientUtility.getEjbInterface("SocioSanitario", "SocioSanitario_EJB",
					"AtlanteSessionBean");
			return bean.getServizi(cf);
		} catch (SocioSanitarioException e) {
			logger.error("Errore nella chiamata al servizio SocioSanitario", e);
			throw new CsUiCompException(e);
		} catch (NamingException e) {
			logger.error("Errore nella creazione client ejb SocioSanitario", e);
			throw new CsUiCompException(e);
		}
	}

	protected CsCMedico getMedicoByCodRegionale(String codRegMedico) throws CsUiCompException {
		CsCMedico medico = null;
		if (codRegMedico != null && !codRegMedico.trim().isEmpty()) {
			AccessTableMediciSessionBeanRemote bean;
			try {
				bean = (AccessTableMediciSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableMediciSessionBean");
			} catch (NamingException e) {
				throw new CsUiCompException(e);
			}
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(codRegMedico);
			medico = bean.getMedicoByCodReg(dto);
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
					medicoRegione = mc.getMedicoByCodiceRegionale(codRegMedico);
					if (medicoRegione != null) {
						String cognomeNewMedico = medicoRegione.getCognome();
						String nomeNewMedico = medicoRegione.getNome();
						String codiceFiscale = medicoRegione.getCodiceFiscale();
						CsCMedico newMedico = new CsCMedico();
						newMedico.setAbilitato("1");
						newMedico.setCognome(cognomeNewMedico);
						newMedico.setNome(nomeNewMedico);
						newMedico.setCodiceRegionale(codRegMedico);
						BaseDTO dto2 = new BaseDTO();
						fillEnte(dto2);
						dto2.setObj(newMedico);
						medico = bean.addNewMedicoFromAnagReg(dto2);
					}
				}
			}
		}
		return medico;
	}

	protected IStranieri getSchedaJsonStranieri(Long schedaId) {
		IStranieri stranieriMan = null;
		try {
			CsDValutazione val = getSchedaValutazione(schedaId, DataModelCostanti.TipoDiario.STRANIERI_ID);
			stranieriMan = StranieriManBaseBean.initByModel(val);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return stranieriMan;
	}

	protected IIntermediazioneAb getSchedaJsonIntermediazioneAb(Long schedaId) {
		IIntermediazioneAb man = null;
		try {

			CsDValutazione val = getSchedaValutazione(schedaId, DataModelCostanti.TipoDiario.INTERMEDIAZIONE_AB_ID);
			man = IntermediazioneManBaseBean.initByModel(val);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return man;
	}

	protected IOrientamentoLavoro getSchedaJsonOrientamentoLav(Long schedaId) {
		IOrientamentoLavoro man = null;
		try {

			CsDValutazione val = getSchedaValutazione(schedaId, DataModelCostanti.TipoDiario.ORIENTAMENTO_LAVORO_ID);
			man = OrientamentoLavoroManBaseBean.initByModel(val);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return man;
	}

	protected IMediazioneCult getSchedaJsonMediazioneCult(Long schedaId) {
		IMediazioneCult man = null;
		try {

			CsDValutazione val = getSchedaValutazione(schedaId, DataModelCostanti.TipoDiario.MEDIAZIONE_CULT_ID);
			man = MediazioneCultManBaseBean.initByModel(val);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return man;
	}
	
	protected IOrientamentoIstruzione getSchedaJsonOrientamentoIstruzione(Long schedaId) {
		IOrientamentoIstruzione man = null;
		try {

			CsDValutazione val = getSchedaValutazione(schedaId, DataModelCostanti.TipoDiario.MEDIAZIONE_CULT_ID);
			man = OrientamentoIstruzioneManBaseBean.initByModel(val);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
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
	
	protected IAbitazione nuovaDaSchedaJsonAbitazione(Long schedaId){
		IAbitazione man1 = this.getSchedaJsonAbitazione(schedaId);
		if(man1!=null) return AbitazioneManBaseBean.init(man1);
		else return null;
	}
	
	protected IStranieri nuovaDaSchedaJsonStranieri(Long schedaId){
		IStranieri man1 = this.getSchedaJsonStranieri(schedaId);
		if(man1!=null) return StranieriManBaseBean.init(man1);
		else return null;
	}
	
	protected IFamConviventi nuovaDaSchedaJsonFamiliari(Long schedaId){
		IFamConviventi man1 = this.getSchedaJsonFamConviventi(schedaId);
		if(man1!=null) return FamiliariManBaseBean.init(man1);
		else return null;
	}

	private CsDValutazione getSchedaValutazione(Long schedaId, int tipoDiario) throws NamingException {
		it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
		fillEnte(dto);
		dto.setObj(schedaId);
		dto.setObj2(tipoDiario);

		List<CsDValutazione> schede = diarioService.getSchedeValutazioneUdcId(dto);
		if (schede != null && !schede.isEmpty())
			return schede.get(0);

		return null;
	}

	public static boolean isVisPanelStranieri() {

		boolean visPanelStranieri = false;
		try {
			ParameterService paramService = (ParameterService) ClientUtility.getEjbInterface("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setKey("smartwelfare.gestioneStranieri");
			criteria.setComune(null);
			criteria.setSection(null);
			AmKeyValueExt amKey = paramService.getAmKeyValueExt(criteria);
			if (amKey != null && amKey.getValueConf() != null) {
				String val = amKey.getValueConf();
				visPanelStranieri = val != null && "1".equals(val) ? true : false;
			}
		} catch (Exception e) {
			logger.error("Errore recupero smartwelfare.gestioneStranieri: il pannello non verrà visualizzato");
		}

		return visPanelStranieri;
	}
	
	public static AmTabNazioni getNazioneByIstat(String codice, String descrizione) {
		AmTabNazioni nazione = null;
		if(codice!=null && !codice.isEmpty()){
			try{
				LuoghiService luoghiService = (LuoghiService)  ClientUtility.getEjbInterface("CT_Service", "CT_Config_Manager", "LuoghiServiceBean");
				nazione = luoghiService.getNazioneByIstat(codice);
			}catch(Exception e){}
			if(nazione==null){
				logger.debug("Ricerco Nazione con cod.istat "+codice+ " --> NON TROVATA!");
			    nazione = new AmTabNazioni();
			    nazione.setCodIstatNazione(codice);
			    nazione.setNazione(descrizione);
			}
		}
		
		return nazione;
	}

	public static String getCittadinanzaByIstat(String codice){
		String cittadinanza = null;
		if("100".equals(codice)){
			cittadinanza = "ITALIANA";
		}else{
			AmTabNazioni nazione = getNazioneByIstat(codice, null);
			if(nazione!=null)
				cittadinanza = nazione.getNazionalita();
		}
		
		return cittadinanza;
	}
	
//INIZIO MOD-RL

	public static String getCodiceIstatByNazionalita(String nazionalita) throws Exception{

		AmTabNazioni nazione = null;

		LuoghiService luoghiService = (LuoghiService)  ClientUtility.getEjbInterface("CT_Service", "CT_Config_Manager", "LuoghiServiceBean");
		nazione = luoghiService. getNazioneByNazionalita(nazionalita);

		if(nazione==null){
			String errorMessage = "Ricerco Nazione con cod.nazionalita "+nazionalita+ " --> NON TROVATA!";
			logger.debug(errorMessage);
			throw new Exception(errorMessage);
		}  
	
		return nazione.getCodIstatNazione();
	}
//FINE MOD-RL
	
	
}
