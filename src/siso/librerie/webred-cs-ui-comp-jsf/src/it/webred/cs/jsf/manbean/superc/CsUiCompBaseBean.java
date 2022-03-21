package it.webred.cs.jsf.manbean.superc;

import it.roma.comune.servizi.client.CallWS;
import it.roma.comune.servizi.dto.*;
import it.webred.amprofiler.ejb.anagrafica.AnagraficaService;
import it.webred.amprofiler.ejb.perm.LoginBeanService;
import it.webred.amprofiler.ejb.user.UserService;
import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.amprofiler.model.AmGroup;
import it.webred.amprofiler.model.AmUser;
import it.webred.cet.permission.CeTUser;
import it.webred.cs.csa.ejb.client.AccessTableDatiPorSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIndirizzoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableMediciSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableCatSocialeSessionBeanRemote;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableConfigurazioneEnteSessionBeanRemote;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.domini.AccessTableDominiAmKeySessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.StatoCartellaDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.AmParameterKey;
import it.webred.cs.data.DataModelCostanti.FiltroCasi;
import it.webred.cs.data.DataModelCostanti.FiltroFse;
import it.webred.cs.data.DataModelCostanti.PermessiCartella;
import it.webred.cs.data.DataModelCostanti.PermessiCasellario;
import it.webred.cs.data.DataModelCostanti.PermessiDatiSinba;
import it.webred.cs.data.DataModelCostanti.PermessiErogazioneInterventi;
import it.webred.cs.data.DataModelCostanti.PermessiInterscambio;
import it.webred.cs.data.DataModelCostanti.PermessiIter;
import it.webred.cs.data.DataModelCostanti.PermessiProgettiIndividuali;
import it.webred.cs.data.DataModelCostanti.PermessiSchedeSegr;
import it.webred.cs.data.DataModelCostanti.TabUDC;
import it.webred.cs.data.DataModelCostanti.TipiCategoriaSociale;
import it.webred.cs.data.DataModelCostanti.TipoDiario;
import it.webred.cs.data.DataModelCostanti.TipoStatoErogazione;
import it.webred.cs.data.model.ArFfProgetto;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsAIndirizzo;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCMedico;
import it.webred.cs.data.model.CsCfgParametri;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsOZonaSoc;
import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.cs.jsf.Costanti.TipoPermessoErogazioneInterventi;
import it.webred.cs.jsf.manbean.ComuneNazioneNascitaMan;
import it.webred.cs.jsf.manbean.exception.CsUiCompException;
import it.webred.cs.json.abitazione.AbitazioneManBaseBean;
import it.webred.cs.json.abitazione.IAbitazione;
import it.webred.cs.json.familiariConviventi.FamiliariManBaseBean;
import it.webred.cs.json.familiariConviventi.IFamConviventi;
import it.webred.cs.json.serviziorichiestocustom.IServizioRichiestoCustom;
import it.webred.cs.json.serviziorichiestocustom.ServizioRichiestoCustomManBaseBean;
import it.webred.cs.json.stranieri.IStranieri;
import it.webred.cs.json.stranieri.StranieriManBaseBean;
import it.webred.cs.sociosan.ejb.client.AtlanteSessionBeanRemote;
import it.webred.cs.sociosan.ejb.client.RicercaIseeSessionBeanRemote;
import it.webred.cs.sociosan.ejb.client.ricercaSoggetto.RicercaSoggettoSessionBeanRemote;
import it.webred.cs.sociosan.ejb.dto.ServiziDTO;
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmComune;
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
import it.webred.jsf.bean.ComuneBean;
import it.webred.siso.ws.ricerca.dto.*;
import it.webred.ss.data.model.SsScheda;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javax.el.ELContext;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CsUiCompBaseBean {

	private String logoBasePath;
	protected String dirLoghi = "/images/logo/";
	private StreamedContent logoComune;

	public static final SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
	public static final SimpleDateFormat ddMMyyyyhhmmss = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	
	public static Logger logger = Logger.getLogger("carsociale.log");
	
	protected static Boolean flagGestisciInformazioni= false; //SISO-812
	public boolean isIterCartellaSociale(){
		return checkPermesso(PermessiIter.ITEM, PermessiIter.GESTIONE_ITER_BATCH);
	}
	
	protected void clearParametriFiltroFse(){
		getSession().setAttribute(FiltroFse.DATA_DA, null);
		getSession().setAttribute(FiltroFse.DATA_A, null);
		getSession().setAttribute(FiltroFse.RESIDENZA_COMUNE, null);
		getSession().setAttribute(FiltroFse.TIPO, null);
		getSession().setAttribute(FiltroFse.EXTRACT_FIRST, Boolean.TRUE);
	}
	
	protected void clearParametriFiltroCasi(){
		getSession().setAttribute(FiltroCasi.STATO, null);
		getSession().setAttribute(FiltroCasi.OPERATORE, null);
		getSession().setAttribute(FiltroCasi.TIPO_OPERATORE, null);
		getSession().setAttribute(FiltroCasi.STUDIO, null);
		getSession().setAttribute(FiltroCasi.LAVORO, null);
		getSession().setAttribute(FiltroCasi.TUTELA, null);
		getSession().setAttribute(FiltroCasi.TRIBUNALE, null);
		getSession().setAttribute(FiltroCasi.RESIDENZA_TIPO, null);
		getSession().setAttribute(FiltroCasi.RESIDENZA_NAZIONE, null);
		getSession().setAttribute(FiltroCasi.RESIDENZA_COMUNE, null);
	}
	
	private static ResourceBundle bundle;

	static {
		bundle = ResourceBundle.getBundle("it.webred.cs.resources.messages");
	}
	
	// SISO-724 mappa CF - stato cartella
	private HashMap<String, String> cf2StatoCartella = new HashMap<String, String>();
	
	protected static LoginBeanService loginService = (LoginBeanService) getEjb("AmProfiler", "AmProfilerEjb", "LoginBean");
	protected static UserService userService = (UserService) getEjb("AmProfiler", "AmProfilerEjb", "UserServiceBean");
	protected static AnagraficaService anagraficaService = (AnagraficaService) getEjb("AmProfiler", "AmProfilerEjb", "AnagraficaServiceBean");
	
	protected ComuneService comuneService = (ComuneService) getEjb("CT_Service", "CT_Config_Manager", "ComuneServiceBean");
	protected static ApplicationService appService = (ApplicationService) getEjb("CT_Service", "CT_Config_Manager", "ApplicationServiceBean");
	protected static LuoghiService luoghiService = (LuoghiService) getEjb("CT_Service", "CT_Config_Manager", "LuoghiServiceBean");
	protected static ParameterService paramService = (ParameterService) getEjb("CT_Service", "CT_Config_Manager", "ParameterBaseService");
	
	protected AccessTableInterventoSessionBeanRemote interventoService = 
			(AccessTableInterventoSessionBeanRemote) getCarSocialeEjb( "AccessTableInterventoSessionBean");
	
	// SISO-724 - reso static
	protected static AccessTableIndirizzoSessionBeanRemote indirizzoService = (AccessTableIndirizzoSessionBeanRemote) getCarSocialeEjb("AccessTableIndirizzoSessionBean");
	protected static AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) getCarSocialeEjb("AccessTableDiarioSessionBean");
	protected static AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getCarSocialeEjb("AccessTableConfigurazioneSessionBean");
	protected static AccessTableConfigurazioneEnteSessionBeanRemote confEnteService = (AccessTableConfigurazioneEnteSessionBeanRemote) getCarSocialeEjb("AccessTableConfigurazioneEnteSessionBean");
	
	protected static AccessTableMediciSessionBeanRemote mediciSessionBean = (AccessTableMediciSessionBeanRemote) getCarSocialeEjb("AccessTableMediciSessionBean");
	protected AccessTableIterStepSessionBeanRemote iterService = (AccessTableIterStepSessionBeanRemote) getCarSocialeEjb("AccessTableIterStepSessionBean");
	protected static AccessTableSchedaSessionBeanRemote schedaService = (AccessTableSchedaSessionBeanRemote) getCarSocialeEjb ( "AccessTableSchedaSessionBean");
	protected static AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) getCarSocialeEjb ("AccessTableSoggettoSessionBean");
	protected static AccessTableDominiAmKeySessionBeanRemote amKeyDomini = (AccessTableDominiAmKeySessionBeanRemote) getCarSocialeEjb ("AccessTableDominiAmKeySessionBean");
	protected static AccessTableDatiPorSessionBeanRemote datiPorService = (AccessTableDatiPorSessionBeanRemote) CsUiCompBaseBean.getCarSocialeEjb("AccessTableDatiPorSessionBean");
	
	private static RicercaSoggettoSessionBeanRemote getRicercaSoggettoBean(){
		RicercaSoggettoSessionBeanRemote ricercaSoggettoBean =
				(RicercaSoggettoSessionBeanRemote) getEjb("SocioSanitario", "SocioSanitario_EJB", "RicercaSoggettoSessionBean");
		return ricercaSoggettoBean;
	}
	
	protected static RicercaIseeSessionBeanRemote getRicercaIseeBean(){
		RicercaIseeSessionBeanRemote ricercaIseeBean = 
				(RicercaIseeSessionBeanRemote) getEjb("SocioSanitario", "SocioSanitario_EJB", "RicercaIseeSessionBean");
		return ricercaIseeBean;
	}
	
	
	public static Object getReferencedBean(String beanName){
	    Object bean = null;
	    FacesContext fc = FacesContext.getCurrentInstance();
	    if(fc!=null){
	         ELContext elContext = fc.getELContext();
	         bean = elContext.getELResolver().getValue(elContext, null, beanName);
	    }

	    return bean;
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

	protected void addErrorCampiObbligatori(String nomeTab, String campo) {
		String summary = (nomeTab != null ? nomeTab  : "");
		String descrizione = "'" +campo +"' è un campo obbligatorio";
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
	
	protected void addWarning(String summary, List<String> descrizione) {
		if(descrizione!=null && !descrizione.isEmpty())
			addWarning(summary, StringUtils.join(descrizione,"<br/>"));
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
	
	public static HashMap<String, String> getListaPermessi(String username, String ente){
		HashMap<String, String> lista = loginService.getPermissions(username, ente);
		return lista;
	}
	
	public static String getCurrentUsername(){
		return getUser().getUsername();
	}
	
	protected static CeTUser getUser() {
		try{
		HttpSession session = getSession();
		
			return (CeTUser) session.getAttribute("user");
		
		
		}catch (java.lang.NullPointerException nullp) {
			
			return null;
			}
	}

	public static void aggiornaEnteCeTUser(String belfiore){
		HttpSession session = getSession();
		CeTUser user = getUser();
		user.setCurrentEnte(belfiore);
		List<AmGroup> listaGruppi = loginService.getGruppi(user.getUsername(), belfiore);
		user.setGroupList(listaGruppi);
		session.setAttribute("user", user);	
	}
	
	public static CeTBaseObject fillEnte(CeTBaseObject ro) {

		CeTUser user = getUser();
		CsOOperatoreSettore opSettore = getCurrentOpSettore();
		if (user != null) {
			ro.setEnteId(user.getCurrentEnte());
			ro.setUserId(user.getUsername());
			ro.setSessionId(user.getSessionId());
			
			if(ro instanceof BaseDTO){
				if(opSettore != null)
					((BaseDTO)ro).setSettoreId(opSettore.getCsOSettore().getId());	
		
				//SISO-812
				if(isProvenienzaCasiAssegnati())
					((BaseDTO)ro).setNascondiInfoPerSettore(flagGestisciInformazioni);
			}
		}
		String codFittizio = opSettore != null ?  opSettore.getCsOSettore().getCsOOrganizzazione().getCodRouting() : null;
		if (codFittizio!=null) ro.setEnteId(codFittizio);
		return ro;
	}
	
	//SISO-812
	public Boolean isOperatoreSecondoLivello(){
		return getCurrentOpSettore().getCsOSettore().getCsTbSecondoLivello()!=null;
	}
	
	public static String getCurrentBelfioreCS(){
		CeTBaseObject cet  = new CeTBaseObject();
		fillEnte(cet);
		return cet.getEnteId();
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
		try {
			
			return (CsOOperatoreSettore) getSession().getAttribute("operatoresettore");
		
		} catch (java.lang.NullPointerException nullp) {
			
			return null;
			
		}
		
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<CsOOperatoreSettore> getTempListaSett() {
		return (ArrayList<CsOOperatoreSettore>) getSession().getAttribute("tempListaSett");
	}
	
	public static List<Long> getListaIdSettoriAbilitatiOperatore(){
		List<Long> lst = new ArrayList<Long>();
		for(CsOOperatoreSettore opsett : getTempListaSett())
			lst.add(opsett.getCsOSettore().getId());
		return lst;
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
	
	public static Object getCarSocialeEjb(String ejbName) {
		return getEjb("CarSocialeA", "CarSocialeA_EJB", ejbName);
	}
	
	public static Object getArgoEjb(String ejbName) {
		return getEjb("Argo", "Argo_EJB", ejbName);
	}

	
	public static boolean checkPermesso(String item, String permesso) {
		@SuppressWarnings("unchecked")
		HashMap<String, String> map = (HashMap<String, String>) getSession().getAttribute("permessiGruppoSettore");
		String patt = getPatternPermessoCS(item, permesso);
		if (map != null && map.get(patt) != null)
			return true;

		return false;
	}
	
	public static boolean checkPermessoSS(String permesso) {
		String patternPermesso = getPatternPermessoSS(permesso);
		CeTUser user = getUser();
		if (user != null) {
			String lvl = getListaPermessi(getCurrentUsername(), user.getCurrentEnte()).get(patternPermesso);
			if (lvl != null && !lvl.isEmpty())
				return true;
		}
		return false;
	}
	
	public static boolean checkPermessoSS(String permesso, String ente) {
		String patternPermesso = getPatternPermessoSS(permesso);
		CeTUser user = getUser();
		if (user != null) {
			String lvl = getListaPermessi(getCurrentUsername(), ente).get(patternPermesso);
			if (lvl != null && !lvl.isEmpty())
				return true;
		}
		return false;
	}

	public static boolean canReadDiarioSS() {
		return checkPermessoSS("segrsoc-readDiario");
	}
	
	private static HashMap<String, String> cacheNomeIstanza = new HashMap<String, String>();

	private static String getNomeIstanzaCS() {
		String nome = "CarSociale";
		CeTBaseObject cet = new CeTBaseObject();
		fillEnte(cet);
		if (cacheNomeIstanza.get(cet.getEnteId()) != null)
			return cacheNomeIstanza.get(cet.getEnteId());
		
		try {
			AmInstance instance = appService.getInstanceByApplicationComune("CarSociale", cet.getEnteId());
			nome = instance!=null  ? instance.getName() : nome;
			cacheNomeIstanza.put(cet.getEnteId(), nome);
		} catch (Exception e) {
			logger.error("", e);
		}
		return nome;
	}
	
	public static String getNomeIstanzaSS() {
		String istanza = "SegretariatoSoc";
		try {
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			AmInstance instance = appService.getInstanceByApplicationComune("SegretariatoSoc", cet.getEnteId());
			istanza = instance.getName();
		} catch (Exception e) {
		}
		return istanza;
	}

	private static String getPatternPermessoSS(String nomePermesso) {
		String item = "SegretariatoSoc";
		String istanza = getNomeIstanzaSS();
		return "permission@-@" + istanza + "@-@" + item + "@-@" + nomePermesso;

	}
	
	/*private static String getPatternPermessoCS(String nomePermesso) {
		// ES. permission@-@CarSociale@-@CarSociale@-@Crea un caso
		String item = "CarSociale";
		return getPatternPermessoCS(item, nomePermesso);

	}*/

	@SuppressWarnings("unused")
	private static String getPatternPermessoCS(String item, String nomePermesso) {
		String istanza = getNomeIstanzaCS();
		return "permission@-@" + istanza + "@-@" + item + "@-@" + nomePermesso;
	}

	private static HashMap<String, String> cacheTipoApplicazione = new HashMap<String, String>();

	protected String getGoogleDoc(){
		return getGlobalParameter("smartwelfare.news.googledoc");
	}
	
	protected String getMessaggioNews(){
		return getGlobalParameter("smartwelfare.news.messaggio");
	}
	
	public static String getGestioneTipoFamiglia(){
		return getGlobalParameter("smartwelfare.gestione.tipo.famiglia");
	}
	
	protected static String getAppErogazioniLink(){
		return getGlobalParameter("smartwelfare.app.erogazioni");
	}
	
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
			paramTipoApplicazione = getGlobalParameter("smartwelfare.tipoApplicazione.default");
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

	private static String getParametro(ParameterSearchCriteria criteria) {
		AmKeyValueExt amKey = paramService.getAmKeyValueExt(criteria);
		if (amKey != null)
			return amKey.getValueConf();
		else
			return null;
	}

	protected String getParametro(String sezione, String chiave) {

		try {
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
			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setKey("dir.files.datiDiogene");
			criteria.setComune(null);
			criteria.setSection(null);

			AmKeyValueExt amKey = paramService.getAmKeyValueExt(criteria);
			if (amKey != null)
				this.logoBasePath = amKey.getValueConf();
			else
				logger.error("Attenzione: Il path 'dir.files.datiDiogene' non è impostato");
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
			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setKey("visualizza.footer");
			criteria.setComune(cet.getEnteId());
			criteria.setInstance(getNomeIstanzaCS());

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
	
	public  String getLabelOrganizzazione(){ //static
		return getGlobalParameter("smartwelfare.label.tipoOrganizzazione");
	}
	public  String getModuloPorRegionale(){ //static
		return getGlobalParameter(DataModelCostanti.AmParameterKey.POR_MODELLO_STAMPA);
	}
	
	public boolean isModuloPorMarche(){
		String valore = this.getModuloPorRegionale();
		return !StringUtils.isEmpty(valore) && valore.contains("Marche");
	}
	
	public boolean isVisualizzaModuloPorCs() {
		String val = getGlobalParameter(DataModelCostanti.AmParameterKey.POR_CS_ABILITA);
		return val!=null && "1".equalsIgnoreCase(val) ? true : false;
	}
	
	public boolean isVisualizzaModuloPorUdc() {
		String val = getGlobalParameter(DataModelCostanti.AmParameterKey.POR_UDC_ABILITA);
		return val!=null && "1".equalsIgnoreCase(val) ? true : false;
	}
	
	public  Boolean isControlloResDomPOR(){ //static
		String label =  getGlobalParameter(DataModelCostanti.AmParameterKey.POR_RESIDENZA_DOMICILIO_REGIONE);
		return "SI".equalsIgnoreCase(label);
	}

	private  static String getPermessoErogazioneInterventi() {
		String permesso = null;
		if (checkPermesso(PermessiErogazioneInterventi.ITEM, PermessiErogazioneInterventi.AUTORIZZA))
			permesso = TipoPermessoErogazioneInterventi.PERMESSO_AUTORIZZATIVO;
		else if (checkPermesso(PermessiErogazioneInterventi.ITEM, PermessiErogazioneInterventi.EROGA))
			permesso = TipoPermessoErogazioneInterventi.PERMESSO_EROGATIVO;

		if (permesso == null)
			logger.debug("Permesso Erogazione Interventi non supportato => " + permesso);

		return permesso;
	}

	public static boolean isPermessoAutorizzativo(){
		return TipoPermessoErogazioneInterventi.PERMESSO_AUTORIZZATIVO.equals(getPermessoErogazioneInterventi());
	}
	
	public static boolean isPermessoErogativo(){
		return TipoPermessoErogazioneInterventi.PERMESSO_EROGATIVO.equals(getPermessoErogazioneInterventi());
	}
	
	// SISO-1280, inizio
	public boolean isAbilitaProgettiIndividuali() {
		return checkPermesso(PermessiProgettiIndividuali.ITEM, PermessiProgettiIndividuali.ABILITA);
	}
	// SISO-1280, fine
	
	public boolean isAbilitaErogazioneInterventi() {
		return checkPermesso(PermessiErogazioneInterventi.ITEM, PermessiErogazioneInterventi.ABILITA);
	}
	
	public boolean isRenderListaCasi() {
		return checkPermesso(PermessiCartella.ITEM, PermessiCartella.VISUALIZZAZIONE_LISTA_CASI);
	}
	
	public boolean isRenderListaFse() {
		return this.isModuloPorMarche() && checkPermesso(PermessiCartella.ITEM, PermessiCartella.VISUALIZZAZIONE_LISTA_FSE);
	}

	public boolean isRichiediInterventi() {
		return checkPermesso(PermessiErogazioneInterventi.ITEM, PermessiErogazioneInterventi.RICHIEDI_INTERVENTI);
	}
	
	public boolean isRenderListaSchedeSegr() {
		return checkPermesso(PermessiSchedeSegr.ITEM, PermessiSchedeSegr.VISUALIZZA_SCHEDE_SEGR);
	}
	
	public boolean isEsportaCasellario() {
		return checkPermesso(PermessiCasellario.ITEM, PermessiCasellario.ESPORTA_CASELLARIO);
	}
	
	public boolean isInterscambioCartellaSociale(){
		return checkPermesso(PermessiInterscambio.ITEM, PermessiInterscambio.INTERSCAMBIO_EXPORT);
	}
	
	public boolean isAccessoEsternoDatiCartella(){
		return checkPermesso(PermessiCartella.ITEM, PermessiCartella.ACCESSO_ESTERNO_DATI_CARTELLA);
	}
	
	public boolean isModificaCasiSettore(){
		return checkPermesso(PermessiCartella.ITEM, PermessiCartella.MODIFICA_CASI_SETTORE);
	}
	
	public boolean isRdCAbilitato(){
		boolean render = false;
		String val = getGlobalParameter(DataModelCostanti.AmParameterKey.RDC_ABILITA);
		render = val != null && "1".equals(val) ? true : false;
		return render;
	}
	
	public boolean isAccessoDatiRdC(){
		boolean permesso = checkPermesso(PermessiCartella.ITEM, PermessiCartella.VISUALIZZAZIONE_LISTA_RDC);
		return isRdCAbilitato() && permesso;
	}
	
	public boolean isFonteFinanziamentoObbligatorio(){
		boolean render = false;
		String val = getGlobalParameter(DataModelCostanti.AmParameterKey.REQUIRED_FONTE_FINANZIAMENTO);
		render = val != null && "1".equals(val) ? true : false;
		return render;
	}
	
	protected List<String> getListaTipoStatoErogazioneByPermessoErogazione() {
		List<String> lstStati = new LinkedList<String>();
		if (isPermessoAutorizzativo()) {
			lstStati.add(TipoStatoErogazione.PRELIMINARE);
			lstStati.add(TipoStatoErogazione.EROGATIVO);
		} else
			lstStati.add(TipoStatoErogazione.EROGATIVO);

		return lstStati;
	}

	public static AmAnagrafica getAnagraficaByUsernameFORTHREAD(String username) {
		return getAnagraficaByUsername(username, false);
	}

	private static AmAnagrafica getAnagraficaByUsername(String username,  boolean verifyCache) {
		AmAnagrafica amAna = null;
		HashMap<String, AmAnagrafica> mappaOperatori=null;
		if(username!=null && !username.isEmpty()){
			boolean noSession= false;
			try {
				// la sessione può essere nulla se ho parallelizzato l'esecuzione.
				getSession();
			} catch (java.lang.NullPointerException nullp) {
				noSession = true;
			}
			if (verifyCache && !noSession)
				mappaOperatori = (HashMap<String, AmAnagrafica>) getSession().getAttribute("mappaAnagraficaOperatori");
			if (mappaOperatori == null)
				mappaOperatori = new HashMap<String, AmAnagrafica>();
	
		    amAna = (AmAnagrafica) mappaOperatori.get(username);
	
			try {
	
				if (amAna == null) {
					//logger.debug("getAnagraficaByUsername: " + username);
					amAna = anagraficaService.findAnagraficaByUserName(username);
					mappaOperatori.put(username, amAna);
					if (!noSession)
						getSession().setAttribute("mappaAnagraficaOperatori", mappaOperatori);
				}
			} catch (Exception e) {
				logger.error("getAnagraficaByUsername", e);
			}
		}
		return amAna;
	}
	
	public static AmAnagrafica getAnagraficaByUsername(String username) {
		return getAnagraficaByUsername(username, true);
		
	}

	public static AmUser getUserByUsername(String username) {
		AmUser user = null;
		AmAnagrafica amAna = getAnagraficaByUsername(username);
		if (amAna != null)
			user = amAna.getAmUser();
		else {
			try {
				if (amAna == null)
					user = userService.getUserByName(username);

			} catch (Exception e) {
				logger.error("getUserByUsername", e);
			}
		}
		return user;
	}

	public static String getCognomeNomeUtente(String username) {
		AmAnagrafica ana = getAnagraficaByUsername(username);
		if (ana != null)
			return ana.getCognome() + " " + ana.getNome();
		else
			return username;
	}
	

	public String getDenominazioneOperatore(CsOOperatore o) {
		if(o!=null)
			return o.getDenominazione();
		else
			return "";
	}
	
	public String getDenominazioneOperatore(CsOOperatoreBASIC o) {
		if(o!=null)
			return o.getDenominazione();
		else
			return "";
	}

	public class CredenzialiWS {
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

	private CredenzialiWS getCredenzialiWS(String usernameKey, String passwordKey) {
		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setKey(usernameKey);
		criteria.setComune(null);
		criteria.setSection(null);
		AmKeyValueExt amKey = paramService.getAmKeyValueExt(criteria);
		if (amKey != null && amKey.getValueConf() != null) {
			CredenzialiWS ars = new CredenzialiWS();
			ars.setUsername(amKey.getValueConf());
			criteria.setKey(passwordKey);
			criteria.setComune(null);
			criteria.setSection(null);
			amKey = paramService.getAmKeyValueExt(criteria);
			if (amKey != null && amKey.getValueConf() != null) {
				ars.setPassword(amKey.getValueConf());
				return ars;
			} else
				logger.warn("Password Web Service '"+passwordKey+"' non trovata");
		} else
			logger.warn("Username Web Service '"+usernameKey+"' non trovato");
		return null;
	}
	
	public static boolean isAnagrafeAbilitata(String tipo){
		List<String> s = getTipoRicercaAbilitata();
		return (s!=null && s.contains(tipo));
	}
	
	public static boolean isAnagrafeSanitariaUmbriaAbilitata(){
		return isAnagrafeAbilitata(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA);
	}
	
	public static boolean isAnagrafeSanitariaMarcheAbilitata(){
		return isAnagrafeAbilitata(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE);
	}
	
	public static boolean isAnagrafeSigessAbilitata(){
		return isAnagrafeAbilitata(DataModelCostanti.TipoRicercaSoggetto.SIGESS);
	}
	
	public static boolean isAnagrafeComunaleInternaAbilitata(){
		List<String> s = getTipoRicercaAbilitata();
		return s==null || s.isEmpty() || s.contains(DataModelCostanti.TipoRicercaSoggetto.DEFAULT);
	}
	
	protected static List<String> getTipoRicercaAbilitata(){
		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setKey("smartwelfare.ricercaSoggetto.tipo");
		criteria.setComune(null);
		criteria.setSection(null);
		AmKeyValueExt amKey = paramService.getAmKeyValueExt(criteria);
		String[] s = (amKey!=null && amKey.getValueConf()!=null && !amKey.getValueConf().trim().isEmpty()) ? amKey.getValueConf().toUpperCase().split(";") : null;
	    return s!=null ? Arrays.asList(s) : null;
	}
	
	public CredenzialiWS getCredenzialiWS() {
		return getCredenzialiWS(DataModelCostanti.AmParameterKey.WS_RICERCA_USR,DataModelCostanti.AmParameterKey.WS_RICERCA_PWD);
	}
	
	private static String getAnagrafeSigessWSTomcatLocation() {
		String urlString = getGlobalParameter(DataModelCostanti.AmParameterKey.WS_RICERCA_URL_BRIDGE);
		//String urlString = "http://localhost:8099/ClientRomaWS/services/ClientAnagrafeRoma";
		return urlString;
	}
	
	private static String getAnagrafeSigessWSToken() {
		String token = getGlobalParameter(AmParameterKey.WS_RICERCA_URL_BRIDGE_TOKEN);
		return token;
	}
	
	public static String getLabelZonaSociale(){
		String l = getGlobalParameter("smartwelfare.label.zonaSociale");
		return l;
	}
	
	protected boolean getDocIndividualiProtocolloVisibile(){
		String val = getGlobalParameter(AmParameterKey.DOC_INDIVIDUALI_PROTOCOLLO_VIS);
		return "1".equalsIgnoreCase(val);
	}
	
	public String getZonaSociale() {
		try {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
				
			String zsValue = "";
			CsOZonaSoc zs = confEnteService.findZonaSocAbilitata(dto);
			zsValue = zs!=null ? zs.getNome() : null;
			
			return zsValue;

		} catch (Exception e) {
			addError("lettura.error", "Errore recupero "+getLabelZonaSociale());
		}

		return "";
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

	public static URL getAnagrafeWSDLLocationURL() {
		String urlString = getAnagrafeWSDLLocation();
		return urlString!=null ? stringToUrl(urlString) : null;
	}
	
	public static String getAnagrafeWSDLLocation(){
		return getGlobalParameter(DataModelCostanti.AmParameterKey.WS_RICERCA_URL);
	}
	
	public static Integer getMaxVariazioniAnagrafiche(){
		Integer val = null;
		String s = getGlobalParameter(DataModelCostanti.AmParameterKey.MAX_VARIAZIONI_ANAGRAFICHE);
		if(!StringUtils.isEmpty(s)) val = Integer.valueOf(s);
		return val;
	}
	
	protected URL getSisoWSDLLocation() {
		return getAnagrafeWSDLLocationURL();

	}

	protected CredenzialiWS getAtlanteLogin() {
		return getCredenzialiWS(DataModelCostanti.AmParameterKey.WS_ATLANTE_USR, DataModelCostanti.AmParameterKey.WS_ATLANTE_PWD);
	}
	
	private static String getGlobalParameter(String paramName) {
		
		String valore = amKeyDomini.findByKey(paramName);
		if(!StringUtils.isBlank(valore)) return valore;
		
		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setKey(paramName);
		criteria.setComune(null);
		criteria.setSection(null);
		AmKeyValueExt amKey = paramService.getAmKeyValueExt(criteria);
		if (amKey != null && amKey.getValueConf() != null && !amKey.getValueConf().trim().isEmpty()) {
			return amKey.getValueConf();
		} else
			logger.warn("Parametro '" + paramName + "' non definito");

		return null;
	}
	
	public static PersonaDettaglio getPersonaDaAnagEsterna(String tipoRicerca, String id) {
		PersonaDettaglio p=null;
		if(isAnagrafeAbilitata(tipoRicerca)){
			RicercaAnagraficaParams rab = new RicercaAnagraficaParams(tipoRicerca,true);
			fillEnte(rab);
			rab.setIdentificativo(id);
			
			RicercaAnagraficaResult result = getDettaglioPersona(rab);
			List<PersonaDettaglio> elenco = result.getElencoAssistiti();
			if(result.getMessaggio()==null && !elenco.isEmpty()){
				p = elenco.get(0);
			}else
				logger.error("Errore ricerca "+tipoRicerca+" CODICE["+result.getCodice()+"]"+ result.getMessaggio(), result.getEccezione());
		}
		return p;
	}
	
	public static PersonaDettaglio getPersonaDaAnagEsterna(String tipoRicerca, String cognome, String nome, String codFiscale) {
		PersonaDettaglio p=null;
		if(isAnagrafeAbilitata(tipoRicerca)){
			
			RicercaAnagraficaParams rab= new RicercaAnagraficaParams(tipoRicerca,true);
			fillEnte(rab);
			rab.setCognome(cognome);
			rab.setNome(nome);
			rab.setCf(codFiscale);
			
			RicercaAnagraficaResult result = getDettaglioPersona(rab);
			List<PersonaDettaglio> elenco = result.getElencoAssistiti();
			if(result.getMessaggio()==null && !elenco.isEmpty()){
				p = elenco.get(0);
			}else{
				logger.error("Errore ricerca "+tipoRicerca+" CODICE["+result.getCodice()+"]"+ result.getMessaggio(), result.getEccezione());
			}
		}
		return p;
	}
		
	protected ServiziDTO getServiziSanitariBase(String cf) throws CsUiCompException, SocioSanitarioException {
		AtlanteSessionBeanRemote atlanteSessionBean = 
				(AtlanteSessionBeanRemote) getEjb("SocioSanitario", "SocioSanitario_EJB", "AtlanteSessionBean");
		return atlanteSessionBean.getServizi(cf);
	}

	public static CsCMedico getMedicoByCodRegionale(String codRegMedico) throws CsUiCompException {
		CsCMedico medico = null;
		if (codRegMedico != null && !codRegMedico.trim().isEmpty()) {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(codRegMedico);
			medico = mediciSessionBean.getMedicoByCodReg(dto);
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
		}
		return val;
	}
	
	protected List<CsDValutazione> getSchedeValutazione(Long schedaId, List<Integer> tipoDiario) throws NamingException {
		it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
		fillEnte(dto);
		dto.setObj(schedaId);
		dto.setObj2(tipoDiario);

		List<CsDValutazione> schede = diarioService.getSchedeValutazioneUdcId(dto);
		return schede;
	}
	
//INIZIO SISO-438-Possibilità di allegare documenti in UdC
	public static List<IServizioRichiestoCustom> loadSchedaJsonServiziRichiestiCustom(Long schedaId, Boolean residenzaFuoriAmbito) throws Exception {
		return  getSchedaJsonServiziRichiestiCustom(schedaId, residenzaFuoriAmbito);
	}
	
	protected  static List<CsDValutazione> getSchedeValutazione(Long schedaId, int tipoDiario) throws NamingException {
		it.webred.cs.csa.ejb.dto.BaseDTO dto = new it.webred.cs.csa.ejb.dto.BaseDTO();
		fillEnte(dto);
		dto.setObj(schedaId);
		dto.setObj2(tipoDiario);
 
		List<CsDValutazione> schede = diarioService.getSchedeValutazioneUdcId(dto);
		if (schede == null || schede.isEmpty()){
			schede = new ArrayList<CsDValutazione>();
		} 

		return schede;
	}
	
	public static List<IServizioRichiestoCustom> getSchedaJsonServiziRichiestiCustom(Long schedaId, Boolean residenzaFuoriAmbito) throws Exception{
		List<IServizioRichiestoCustom> result = new ArrayList<IServizioRichiestoCustom>();

		List<CsDValutazione> listValutazione = getSchedeValutazione(schedaId, DataModelCostanti.TipoDiario.RICHIESTA_SERVIZIO_ID);
		for (CsDValutazione csDValutazione : listValutazione) {
			IServizioRichiestoCustom man = (IServizioRichiestoCustom) ServizioRichiestoCustomManBaseBean.initByModel(csDValutazione, residenzaFuoriAmbito);
			result.add(man);
		}

		return result;
	}
//FINE SISO-438-Possibilità di allegare documenti in UdC
	

	public static boolean isVisPanelStranieri() {

		boolean visPanelStranieri = false;
		String val = getGlobalParameter("smartwelfare.gestioneStranieri");
		visPanelStranieri = val != null && "1".equals(val) ? true : false;
		
		return visPanelStranieri;
	}
	
	public static boolean isVisProblematicaSoggetto() {

		boolean render = false;
		String val = getGlobalParameter("smartwelfare.datiSociali.abilita.problematicaSoggetto");
		render = val != null && "1".equals(val) ? true : false;
		return render;
	}
	
	public static boolean isVisDataAperturaFascFam() {
		boolean render = false;
		String val = getGlobalParameter("smartwelfare.anagrafica.abilita.dataAperturaFascFam");
		render = val != null && "1".equals(val) ? true : false;
		return render;
	}
	
	public static boolean getColloquioRiservatoDefault() {
		boolean render = false;
		String val = getGlobalParameter("smartwelfare.diari.default.colloquioRiservato");
		render = val != null && "1".equals(val) ? true : false;
		return render;
	}
	
	public static String getLabelTabDisabili(String codice) {
		String val = getGlobalParameter("smartwelfare.disabili.label."+codice);
		return val;
	}
	
	public static boolean isRenderLabelTabDisabili(String codice) {
		boolean render = false;
		String val = getLabelTabDisabili(codice);
		render = !StringUtils.isEmpty(val) ? true : false;
		return render;
	}
	
	public static ComuneNazioneNascitaMan getComuneNazioneNascitaMan(String codComune, String descComune, String prov, String codStato, String descStato){
		ComuneNazioneNascitaMan comuneNazioneNascitaMan = new ComuneNazioneNascitaMan();
		ComuneBean comuneBean = null;
		AmTabNazioni nazione = null;
		if(!StringUtils.isBlank(codComune))
			comuneBean = getComuneBean(codComune, descComune, prov);
		if(!StringUtils.isBlank(codStato))
			nazione = getNazioneByIstat(codStato, descStato);
		comuneNazioneNascitaMan.init(comuneBean,nazione);
		return comuneNazioneNascitaMan;
	}
	
	public static ComuneBean getComuneBean(String codice, String descrizione, String prov){
		ComuneBean comuneBean = null;
		if(!StringUtils.isBlank(codice)){
			AmTabComuni comune = luoghiService.getComuneItaByIstat(codice);
		 	if(comune!=null)
		 		comuneBean = new ComuneBean(comune);
		 	else
		 		comuneBean = new ComuneBean(codice,descrizione,prov);
		}
		return comuneBean;
	}
	
	public static AmTabNazioni getNazioneByIstat(String codice, String descrizione) {
		AmTabNazioni nazione = null;
		if(codice!=null && !codice.isEmpty()){
			codice = "100".equalsIgnoreCase(codice) ? "1" : codice;
			try{
				nazione = luoghiService.getNazioneByIstat(codice);
			}catch(Exception e){}
			if(nazione==null && descrizione!=null){
				logger.debug("Ricerco Nazione con cod.istat "+codice+ " --> NON TROVATA!");
			    nazione = new AmTabNazioni();
			    nazione.setCodIstatNazione(codice);
			    nazione.setNazione(descrizione);
			}
		}
		
		return nazione;
	}
	
	public static AmTabNazioni getNazioneByCodiceGenerico(String codice) {
		AmTabNazioni nazione = null;
		if(codice!=null && !codice.isEmpty()){
			try{
				nazione = luoghiService.getNazioneByCodiceGenerico(codice);
			}catch(Exception e){}
		}
		
		return nazione;
	}
	
	public static String getCittadinanzaByCodice(String codice){
		String cittadinanza = null;
		if("100".equals(codice)){
			cittadinanza = "ITALIANA";
		}else{
			AmTabNazioni nazione = getNazioneByCodiceGenerico(codice);
			if(nazione!=null)
				cittadinanza = nazione.getNazionalita();
		}
		
		//cittadinanza = (cittadinanza!=null && cittadinanza.length() > 255) ? cittadinanza.substring(0, 252) + "..." : cittadinanza;
		
		return cittadinanza;
	}
	
//INIZIO MOD-RL

	public static Integer getIso3166ByNazionalita(String nazionalita) throws Exception{
		String iso3166=null;
	
		if(!StringUtils.isEmpty(nazionalita)){
			AmTabNazioni nazione = luoghiService. getNazioneByNazionalita(nazionalita);
			if(nazione==null){
				String errorMessage = "Ricerco Nazione con cod.nazionalita "+nazionalita+ " --> NON TROVATA!";
				logger.debug(errorMessage);
			}else{
				if(nazione.getIso3166()==null){
					String errorMessage = "Nazione con cod.nazionalita "+nazionalita+ " --> CODICE ISO3166 NON DEFINITO!";
					logger.debug(errorMessage);
					iso3166 = DataModelCostanti.AmTabNazioni.CODICE_ISO3166_NON_DISPONIBILE;
				}else 
					iso3166 = nazione.getIso3166();
			}
		}
		
		return iso3166!=null ? Integer.parseInt(iso3166) : null;
	}
//FINE MOD-RL
		
	public String loadStatoCartella(String cf){
		
		String statoCartella = null;
		
		if(cf!=null && !cf.trim().isEmpty()){
		
				try{
					BaseDTO dto1 = new BaseDTO();
					fillEnte(dto1);
					dto1.setObj(cf);
					List<StatoCartellaDTO> listaStati = iterService.getStatoCasoBySoggetto(dto1);
				
					if (listaStati != null && listaStati.size() > 0) {
						statoCartella = "";
						for (StatoCartellaDTO st : listaStati) {
				            statoCartella += "<br/><br/>";
				            statoCartella += "<b>Cognome e nome: </b>"+StringUtils.replace(st.getDenominazione(), "'", "&#39;")+"<br/>";
							statoCartella += "<b>Stato: </b>"+StringUtils.replace(st.getNomeStato(), "'", "&#39;");
							statoCartella += st.getDataPassaggioStato() != null ? " <b>il</b> "
									+ ddMMyyyyhhmmss.format(st.getDataPassaggioStato())
									+ "<br/>"
									: "<br/>";
							if (st.getuNameResponsabile() != null)
								statoCartella += "<b>Responsabile: </b>"+ StringUtils.replace(getCognomeNomeUtente(st.getuNameResponsabile()), "'", "&#39;")  + "<br/>";
				
							if(st.getOrganizzazione()!=null)
								statoCartella += "<b>Presso: </b>" + StringUtils.replace(st.getOrganizzazione(), "'", "&#39;") + "<br/>";
							
							if(st.getSettore()!=null)
								statoCartella += "<b>Settore: </b>" + st.getSettore() + "<br/>";
							
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
				}
				
		}
		return statoCartella;
	}
	
	public boolean isResponsabileSsEnte(String belfiore){
		boolean responsabile = false;
		if (getUser() != null) {
			for (AmGroup group : getListaGruppi(getCurrentUsername(), belfiore))
				if (group.getFkAmComune().equals(belfiore) && group.getName().contains("SSOCIALE_RESPONSABILI")) {
					responsabile = true;
					break;
				}
		}

		return responsabile;
	}
	
	public static Date getDataUltimaModifica(CsDDiario d){
		Date dtMod = d.getDtMod();
		Date dtIns = d.getDtIns();
		Date dtModifica = dtMod != null ? dtMod : dtIns;
		return dtModifica;
	}
	
	public static String getOpUltimaModifica(CsDDiario d){
		String usrMod = d.getUsrMod()!=null ?  getCognomeNomeUtente(d.getUsrMod()) : null;
		String opModifica = usrMod!=null ? usrMod : (d.getUserIns()!=null ? getCognomeNomeUtente(d.getUserIns()) : null); 
		return opModifica;
	}
	
	public boolean isCodFiscaleInCs(String cf) {
		boolean esiste = false;
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(cf);
		
		AccessTableSoggettoSessionBeanRemote soggettiService = 
				(AccessTableSoggettoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB","AccessTableSoggettoSessionBean");
		esiste = soggettiService.esisteSchedaSoggettoByCF(dto);

		if (esiste) {
			String statoCartella = loadStatoCartella(cf);
			this.addWarningDialog("Esiste già una cartella associata al cod.fiscale "+ cf, statoCartella);
		}
		return esiste;

	}
	
	protected List<SelectItem> loadLstArFfProgetti(){
		List<SelectItem> lstArFfProgetti = new ArrayList<SelectItem>();
		BaseDTO bdto = new BaseDTO();
		fillEnte(bdto);
		bdto.setObj(getCurrentOpSettore().getCsOSettore().getCsOOrganizzazione().getCodRouting());
		List<ArFfProgetto> lst = confService.findProgettiByBelfioreOrganizzazione(bdto);
		for(ArFfProgetto p : lst)
			lstArFfProgetti.add(new SelectItem(p.getId(),p.getDescrizione()));
		
		return lstArFfProgetti;
	}

	//SISO-812
	public static Boolean isFlagGestisciInformazioni() {
		return flagGestisciInformazioni;
	}

	public void setFlagGestisciInformazioni(Boolean flagGestisciInformazioni) {
		this.flagGestisciInformazioni = flagGestisciInformazioni;
	}
	
	public boolean isEsportaDatiSinba() {
		boolean hasPermesso = checkPermesso(PermessiDatiSinba.ITEM, PermessiDatiSinba.ESPORTA_DATI_SINBA);
		
		boolean matchCatSoc=false;
		CsOOperatoreSettore opSettore = getCurrentOpSettore();
		if(opSettore!=null){
			BaseDTO bDto = new BaseDTO();
			fillEnte(bDto);
			bDto.setObj(opSettore.getCsOSettore().getId());
			bDto.setObj2(TipiCategoriaSociale.FAMIGLIA_MINORI_ID);
			
			AccessTableCatSocialeSessionBeanRemote catSocBean = (AccessTableCatSocialeSessionBeanRemote) getCarSocialeEjb("AccessTableCatSocialeSessionBean");
			matchCatSoc = catSocBean.isCategoriaSocialeSettore(bDto);
		}

		return hasPermesso && matchCatSoc;
	}
	
	public static CsAAnaIndirizzo findIndirizzoResidenzaCaso(CsASoggettoLAZY soggetto){
		CsAAnaIndirizzo residenza = null; 
		Long idSoggetto = (soggetto != null ? soggetto.getAnagraficaId() :  null);
		 	if(idSoggetto != null){
			 	BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(idSoggetto);
			    residenza  =   indirizzoService.getIndirizzoResidenza(dto);
		 	}
		return residenza;
	}

	
	public static String getCasoComuneResidenza(CsAAnaIndirizzo residenza){
		String comuneResidenza = null;
		if(residenza!=null){
			
			String comCod = residenza.getComCod();
		 	String comDes = residenza.getComDes();
		 	String comProv = residenza.getProv();
		 	ComuneBean comuneBean = getComuneBean(comCod, comDes, comProv);
		 	
		 	if(comuneBean!=null){
				try{
					ObjectMapper om = new ObjectMapper();
					comuneResidenza  = om.writeValueAsString(comuneBean);
				}catch(Exception ex ){}
		 	}
		}
		return comuneResidenza;
	}
	
	
	private static RicercaAnagraficaResult ricercaAnaSigess(RicercaAnagraficaParams params){
		RicercaAnagraficaResult result = new RicercaAnagraficaResult();
		if(isAnagrafeSigessAbilitata()){
			String wsdlLocation = getAnagrafeWSDLLocation();
			CallWS anag = getAnagrafeSigessCall();
			List<PersonaDettaglio> lstOut = new ArrayList<PersonaDettaglio>();
			
			String cognome = params.getCognome();
			String nome = params.getNome();
			String annoDa = (params.getAnnoNascitaDa()!=null && params.getAnnoNascitaDa()>0) ? params.getAnnoNascitaDa().toString() : null;
			String annoA = (params.getAnnoNascitaA()!=null && params.getAnnoNascitaA()>0) ? params.getAnnoNascitaA().toString() : null;
		    String sesso = params.getSesso();
		    String cf = params.getCf();
			
			if (wsdlLocation != null && anag!=null) {
				
				RicercaResult rr;
				try {
					if(!StringUtils.isBlank(cf))
						rr = anag.eseguiRicercaAnagrafica(wsdlLocation, null, null, null, null, null, cf);
					else
						rr = anag.eseguiRicercaAnagraficaEstesa(wsdlLocation, cognome, nome, annoDa, annoA, sesso);
				
					 if(rr!=null){
						 if(rr.getEsito()!=null && "OK".equalsIgnoreCase(rr.getEsito().trim())){
							 List<Persona> lst = new ArrayList<Persona>();
								
							 if(rr.getElencoPersona().length>0) lst = Arrays.asList(rr.getElencoPersona());
							 
							 for(Persona p : lst){
								 PersonaDettaglio pd = new PersonaDettaglio(DataModelCostanti.TipoRicercaSoggetto.SIGESS);
								 initFromAnaSigess(pd, p.getPersonaCompleta(), p.getDatiDiNascita(), p.getDatiAnagrafeRoma(), p.getDatiIndirizzo(), null);
								 lstOut.add(pd);
							 }
							 
						 }else{
							 logger.error(rr.getEsito());
							 result.setMessaggio("Errore interrogazione anagrafe comunale di Roma:"+ rr.getEsito());
						 }
					 }
				} catch (RemoteException e) {
					logger.error(e);
					result.setMessaggio("Impossibile interrogare l'anagrafe comunale di Roma: "+ "Errore di connessione:"+e.getMessage());
				}
			}else result.setMessaggio("Impossibile interrogare l'anagrafe comunale di Roma: "+"Dati per l'accesso non configurati!");
			
			result.setElencoAssistiti(lstOut);
		}
		return result;
	}
	
	public static RicercaAnagraficaResult getPersonaDaAnagSigess(RicercaAnagraficaParams params){
		RicercaAnagraficaResult result = new RicercaAnagraficaResult();
		List<PersonaDettaglio> lstout = new ArrayList<PersonaDettaglio>();
		
		String wsdlLocation = getAnagrafeWSDLLocation();
		CallWS anag = getAnagrafeSigessCall();
		
		Persona p = null;
		if (wsdlLocation != null && anag!=null) {
			 RicercaResult rr;
			try {
				rr = anag.ricercaPerCodiceIndividuale(wsdlLocation, params.getIdentificativo());
				 if(rr!=null){
					 if(rr.getEsito()!=null && "OK".equalsIgnoreCase(rr.getEsito().trim())){
						 if(rr.getElencoPersona().length>0) p = rr.getElencoPersona()[0];
					 }else{
						 logger.error(rr.getEsito());
						 result.setMessaggio(rr.getEsito());
					 }
					 
					 if(p!=null){
						 PersonaDettaglio pd = new PersonaDettaglio(DataModelCostanti.TipoRicercaSoggetto.SIGESS);
						 initFromAnaSigess(pd, p.getPersonaCompleta(), p.getDatiDiNascita(), p.getDatiAnagrafeRoma(), p.getDatiIndirizzo(), params.getIdentificativo());
						 lstout.add(pd);
					}
					 result.setElencoAssistiti(lstout);		
				 }
			} catch (RemoteException e){
				logger.error(e);
				result.setMessaggio("Impossibile interrogare l'anagrafe comunale di Roma: Errore di connessione:"+e.getMessage());
			}
		}else result.setMessaggio("Impossibile interrogare l'anagrafe comunale di Roma: Dati per l'accesso non configurati!");
		return result;
	}
	
	private static void initFromAnaSigess(PersonaDettaglio anaBean, PersonaCompleta pc, Nascita n, DatiAnagrafeRoma da, DatiIndirizzo obj, String identificativo){
		//PersonaDettaglio anaBean = new PersonaDettaglio(DataModelCostanti.TipoRicercaSoggetto.SIGESS);
		
		Date dataNascita = null;
		if(n.getGiorno()!=null && n.getMese()!=null && n.getAnno()!=null){
			try {
				dataNascita = ddMMyyyy.parse(trim(n.getGiorno())+"/"+trim(n.getMese())+"/"+trim(n.getAnno()));
			} catch (ParseException e) {
				logger.error(e);
			}
		}
		
		anaBean.setIdentificativo(identificativo!=null ? identificativo : trim(da.getCodiceIndividuale()));
		anaBean.setCodfisc(trim(pc.getCodiceFiscale()));
		anaBean.setCognome(trim(pc.getCognome()));
		anaBean.setNome(trim(pc.getNome()));
		anaBean.setDataNascita(dataNascita);
		anaBean.setDefunto(Arrays.asList(DataModelCostanti.SigessFlagStatoAnagrafe.DECEDUTO).contains(da.getFlagVivoResidente()));
		anaBean.setEmigrato(DataModelCostanti.SigessFlagStatoAnagrafe.AIRE.equalsIgnoreCase(da.getFlagVivoResidente()) ||
							DataModelCostanti.SigessFlagStatoAnagrafe.EMIGRATO.equalsIgnoreCase(da.getFlagVivoResidente()));
		anaBean.setSesso(trim(pc.getSesso()));

		// Cittadinanza
		anaBean.setCittadinanza(getCittadinanzaByCodice(trim(pc.getCodiceStatoISTAT())));

		// nascita
		String codIstat = (n.getCodiceProvinciaISTAT()!=null && n.getCodiceComuneISTAT()!=null) ? n.getCodiceProvinciaISTAT()+n.getCodiceComuneISTAT() : null;
		AmTabComuni comuneNascita = luoghiService.getComuneItaByIstat(codIstat);
		if(comuneNascita!=null)
			anaBean.setComuneNascita(comuneNascita);
		else
			anaBean.setNazioneNascita(getNazioneByIstat(n.getCodiceStatoISTAT(), n.getNomeStato()));
		

		//Residenza
		//DatiIndirizzo obj = p.getDatiIndirizzo(); 
		if(obj!=null){
			String municipio = !StringUtils.isBlank(obj.getMunicipio()) ? "Municipio "+obj.getMunicipio()+" - " : "";
			String indirizzoRes = municipio + (!StringUtils.isBlank(obj.getToponimo()) ? trim(obj.getToponimo()) : "");
			anaBean.setIndirizzoResidenza(indirizzoRes);
			//anaBean.setIndirizzoDomicilio(indirizzoRes);
			String civico = !StringUtils.isBlank(obj.getNumero()) ? trim(obj.getNumero()) : "";
			civico+= !StringUtils.isBlank(obj.getDescrizioneCivicoAltro()) ?  " "+trim(obj.getDescrizioneCivicoAltro()) : "";
			anaBean.setCivicoResidenza(civico.trim());
			//anaBean.setCivicoDomicilio(civico.trim()); //Domicilio   (NON PRESENTI)
			if(!StringUtils.isBlank(obj.getMunicipio())){
				String codIstatRoma = "058091";
				AmTabComuni comuneResidenza = luoghiService.getComuneItaByIstat(codIstatRoma);
				anaBean.setComuneResidenza(comuneResidenza);
				//anaBean.setComuneDomicilio(comuneResidenza); //Domicilio   (NON PRESENTI)
			}else{
				logger.debug("SIGESS: Implementare recupero stato estero di residenza");
				//TODO:Implementare recupero 
			}
		}
	
		anaBean.setTelefono(null);
		
		//Dati Medico (NON PRESENTI)
		//Domicilio   (NON PRESENTI)
		
	}
	
	private static String trim(String s){
		return s!=null ? s.trim() : "";
	}
	
	private static RicercaAnagraficaResult loadFamigliaSIGESS(RicercaAnagraficaParams dto){
		RicercaAnagraficaResult result = new RicercaAnagraficaResult();
		List<FamiliareDettaglio> componenti = new ArrayList<FamiliareDettaglio>();
		
		String wsdlLocation = getAnagrafeWSDLLocation();
		CallWS anag = getAnagrafeSigessCall();
		
		Famiglia famiglia = null;
		if (wsdlLocation != null && anag!=null) {
			 RicercaResult rr;
			try {
				rr = anag.eseguiRicercaStatoFamigliaConv(wsdlLocation, dto.getIdentificativo(), dto.getCf());
				 if(rr!=null){
					 if(rr.getEsito()!=null && "OK".equalsIgnoreCase(rr.getEsito().trim())){
						 famiglia = rr.getFamiglia();
					 }else{
						 logger.error(rr.getEsito());
						 result.setMessaggio(rr.getEsito());
					 }
					 
					 if(famiglia!=null){
						BaseDTO bo = new BaseDTO();
						bo.setEnteId(dto.getEnteId()); 
						bo.setUserId(dto.getUserId());
						bo.setSessionId(dto.getSessionId());
						
						List<Componente> componentiSigess = Arrays.asList(famiglia.getComponenti());
						DatiIndirizzo indirizzoFamiglia = famiglia.getDatiIndirizzo();
						
						/* Ricerco il soggetto titolare della cartella e verifico se è intestatario della scheda anagrafica*/
						boolean parentelaValida = false;
						boolean trovato = false;
						int i=0;
						
						while(i<componentiSigess.size() && !trovato){
							Componente componenteDto = componentiSigess.get(i);
							if(componenteDto.getPersonaCompleta().getCodiceFiscale() != null && 
							   componenteDto.getPersonaCompleta().getCodiceFiscale().equalsIgnoreCase(dto.getCf())){
								trovato = true;
								bo.setObj(componenteDto.getRapportoParentela());
								CsTbTipoRapportoCon rapp = confService.mappaRelazioneParentale(bo);
								logger.warn("SIGESS: relazione parentale non mappata["+componenteDto.getRapportoParentela()+"]["+dto.getEnteId()+"]");
								if(rapp!=null && rapp.getId()==DataModelCostanti.INTESTATARIO_SCHEDA_REL_ID) parentelaValida = true;
							}
							i++;
						}
						
						for(Componente f: componentiSigess) {
				    		
							if(f.getPersonaCompleta().getCodiceFiscale() != null && f.getPersonaCompleta().getCodiceFiscale().equals(dto.getCf()))
				    			continue;
							
							FamiliareDettaglio componenteGit = new FamiliareDettaglio();
							initFromAnaSigess(componenteGit, f.getPersonaCompleta(), f.getNascita(), f.getDatiAnagrafeRoma(), indirizzoFamiglia, null);
							
				    		bo.setObj(f.getRapportoParentela());
				    		CsTbTipoRapportoCon parentela = confService.mappaRelazioneParentale(bo);
				    		if(parentela==null) logger.warn("SIGESS: relazione parentale non mappata["+f.getRapportoParentela()+"]["+dto.getEnteId()+"]");
				    		componenteGit.setParentela(parentela);
							componenteGit.setParentelaValida(parentelaValida);
					
				    		componenti.add(componenteGit);
				    		
				    	}
					 }
					
					 result.setElencoFamiliari(componenti);
				 }
			} catch (RemoteException e){
				logger.error(e);
				result.setMessaggio("Impossibile interrogare l'anagrafe comunale di Roma: Errore di connessione:"+e.getMessage());
			}
		}else result.setMessaggio("Impossibile interrogare l'anagrafe comunale di Roma: Dati per l'accesso non configurati!");
		
		
		return result;
		
	}
	
	public static CsAIndirizzo ricercaIndirizzoAllProvenienza (String cf, String tipoRicerca) throws SocioSanitarioException{
		CsAIndirizzo indirizzoRes = null;
		RicercaAnagraficaParams rab= new RicercaAnagraficaParams(tipoRicerca,true);
		fillEnte(rab);
		rab.setCf(cf);
		
		PersonaDettaglio p = ricercaSoggettoAllProvenienza(rab);
		if(p!=null){
	    	indirizzoRes = new CsAIndirizzo();
			CsAAnaIndirizzo indirizzoAna = new CsAAnaIndirizzo();
			//indirizzoAna.setCodiceVia(obj.getCodiceVia());
			indirizzoAna.setIndirizzo(p.getIndirizzoResidenza());
			indirizzoAna.setCivicoNumero(p.getCivicoResidenza());
			//indirizzoAna.setCivicoAltro(obj.getCivicoAltro());
			AmTabComuni comune = p.getComuneResidenza();
			if(comune!=null){
				indirizzoAna.setProv(comune.getSiglaProv());
				indirizzoAna.setComCod(comune.getCodIstatComune());
				indirizzoAna.setComDes(comune.getDenominazione());
			}else if(p.getNazioneResidenza()!=null){
				AmTabNazioni nazRes = p.getNazioneResidenza();
				indirizzoAna.setStatoCod(nazRes.getCodIstatNazione());
				indirizzoAna.setStatoDes(nazRes.getNazione());
			}
			indirizzoRes.setDataInizioApp(p.getDataInizioResidenza()!=null ? p.getDataInizioResidenza() : new Date());
			indirizzoRes.setCsAAnaIndirizzo(indirizzoAna);
		}
		
		return indirizzoRes;
	}
	
	public static PersonaDettaglio ricercaSoggettoAllProvenienza(RicercaAnagraficaParams params) throws SocioSanitarioException{
		PersonaDettaglio p = null;
		List<String> tipiAbilitati = getTipoRicercaAbilitata();
		List<String> tipiToScan = new ArrayList<String>();
		String provenienzaSoggetto = params.getProvenienza();
		/*Riordino la lista delle fonti anagrafiche, mettendo per prima quella da cui è stato inizializzato il soggetto
		 * dove è più probabile trovare il dato che mi interessa
		 * */
		if(!StringUtils.isBlank(provenienzaSoggetto)){
			tipiToScan.add(provenienzaSoggetto);
			for(String t : tipiAbilitati){
				if(!tipiToScan.contains(t))
					tipiToScan.add(t);
			}
		}else tipiToScan.addAll(tipiAbilitati);
		
		for(String tipo : tipiToScan){
			params.setProvenienza(tipo);
			RicercaAnagraficaResult res = ricercaPerDatiAnagrafici(params);
			if(StringUtils.isBlank(res.getMessaggio()) && res.getElencoAssistiti()!=null && !res.getElencoAssistiti().isEmpty()){
				p = res.getElencoAssistiti().get(0);
				return p;
			}
		}
		return p;
	}
	
	public static RicercaAnagraficaResult ricercaPerDatiAnagrafici(RicercaAnagraficaParams params) throws SocioSanitarioException{
		if(params.getProvenienza().equalsIgnoreCase(DataModelCostanti.TipoRicercaSoggetto.SIGESS)){
			return ricercaAnaSigess(params); 
		}else
			return getRicercaSoggettoBean().ricercaPerDatiAnagrafici(params);
	}
	
	public static RicercaAnagraficaResult getDettaglioPersona(RicercaAnagraficaParams params){
		if(params.getProvenienza().equalsIgnoreCase(DataModelCostanti.TipoRicercaSoggetto.SIGESS))
			return getPersonaDaAnagSigess(params);
		else
			return getRicercaSoggettoBean().getDettaglioPersona(params);
	}
	

	public static List<FamiliareDettaglio> getComposizioneFamiliareAllProvenienza(RicercaAnagraficaParams params) {
		List<FamiliareDettaglio> famiglia = null;
		List<String> tipiAbilitati = getTipoRicercaAbilitata();
		List<String> tipiToScan = new ArrayList<String>();
		String provenienzaSoggetto = params.getProvenienza();
		/*Riordino la lista delle fonti anagrafiche, mettendo per prima quella da cui è stato inizializzato il soggetto
		 * dove è più probabile trovare il dato che mi interessa
		 * */
		if(!StringUtils.isBlank(provenienzaSoggetto)){
			tipiToScan.add(provenienzaSoggetto);
			for(String t : tipiAbilitati){
				if(!tipiToScan.contains(t))
					tipiToScan.add(t);
			}
		}else tipiToScan.addAll(tipiAbilitati);
		int index = 0;
		while(famiglia==null && index < tipiToScan.size()){
			String provenienza = tipiToScan.get(index);
			params.setProvenienza(provenienza);
			RicercaAnagraficaResult result = getComposizioneFamiliare(params);
			if(result!=null){
				if(result.getMessaggio()==null && !result.getElencoFamiliari().isEmpty())
					famiglia = result.getElencoFamiliari();
				else if(result.getMessaggio()!=null)
					logger.error("Errore ricerca componenti familiari per il soggetto["+params.getCf() +"]["+params.getIdentificativo()+"] "+params.getProvenienza()+" CODICE["+result.getCodice()+"]"+ result.getMessaggio(), result.getEccezione());
			}
			index++;
		}
		return famiglia;
	}
	
	public static RicercaAnagraficaResult getComposizioneFamiliare(RicercaAnagraficaParams params){
		if(params.getProvenienza().equalsIgnoreCase(DataModelCostanti.TipoRicercaSoggetto.SIGESS))
			return loadFamigliaSIGESS(params);
		else
			return getRicercaSoggettoBean().getComposizioneFamiliare(params);
	}
	
	private static CallWS getAnagrafeSigessCall(){
		String urlTomcat = getAnagrafeSigessWSTomcatLocation();
		String token = getAnagrafeSigessWSToken();
		if(urlTomcat!=null && token!=null) return new CallWS(urlTomcat,token);
		else return null;
	}
	
	//SISO-812
	public static boolean isProvenienzaCasiAssegnati(){
		boolean val = (Boolean)getSession().getAttribute("fromListaCasi")!=null && !(Boolean)getSession().getAttribute("fromListaCasi");
		return val;
	}
	
	//SISO-1233
		public static boolean isProvenienzaLayout(){
			boolean val = (Boolean)getSession().getAttribute("fromMenuLayout")!=null && !(Boolean)getSession().getAttribute("fromMenuLayout");
			return val;
		}
	
	public static boolean isPermessoCarSocialeEnte(String belfiore, List<AmGroup> lstGruppi){
		boolean permesso = false;
		int i = 0;
		while(!permesso && i<lstGruppi.size()){
			AmGroup gruppo = lstGruppi.get(i);
			if(gruppo.getFkAmComune().equals(belfiore) && gruppo.getName().startsWith("CSOCIALE_SOLO_ACCESSO_APP_"))
				permesso = true;
			i++;
		}
		return permesso;
	}

	public static String getLabelUDC(String tab){
		return getGlobalParameter("smartwelfare.label.udc.tab."+tab);
	}
		
	public static HashMap<String,String> getMappaLabelUDC(){
		HashMap<String,String> mappa = new HashMap<String, String>();
		mappa.put(TabUDC.ACCESSO_TAB, getLabelUDC(TabUDC.ACCESSO_TAB));
		mappa.put(TabUDC.SEGNALANTE_TAB, getLabelUDC(TabUDC.SEGNALANTE_TAB));
		mappa.put(TabUDC.SEGNALATO_TAB, getLabelUDC(TabUDC.SEGNALATO_TAB));
		mappa.put(TabUDC.RIFERIMENTO_TAB, getLabelUDC(TabUDC.RIFERIMENTO_TAB));
		mappa.put(TabUDC.MOTIVAZIONE_TAB, getLabelUDC(TabUDC.MOTIVAZIONE_TAB));
		mappa.put(TabUDC.INTERVENTI_TAB, getLabelUDC(TabUDC.INTERVENTI_TAB));
		mappa.put(TabUDC.CHIUSURA_TAB, getLabelUDC(TabUDC.CHIUSURA_TAB));
		return mappa;
	}
	
	//Inizio SISO-1110
	public static boolean isTreeViewTipoIntervento() {

		boolean render = false;
		String val = getGlobalParameter(DataModelCostanti.AmParameterKey.TIPO_INTERVENTO_VIEW);
		render = val != null && "treeView".equals(val) ? true : false; //per #ROMACAPITALE il parametro non deve essere treeView e al momento abbiamo commentato per avere "false"
		return render;
	}
	
	public static String getTitoloNomenclatoreTipoIntervento() {

		String render = "";
		render = getGlobalParameter(DataModelCostanti.AmParameterKey.NOMENCLATORE_TIPO_INTERVENTO_CUSTOM);
		return render;
	}
	
	public static String getLivelliNomenclatore() {

		String render = "";
		render = getGlobalParameter(DataModelCostanti.AmParameterKey.LIVELLI_NOMENCLATORE_INTERVENTO_CUSTOM);
		return render;
	}
	
	//FIne SISO-1110
	
	//#ROMACAPITALE
	public static boolean isRilevazionePresenzeAbilita() {

		boolean render = false;
		String val = getGlobalParameter(DataModelCostanti.AmParameterKey.RILEVAZIONE_PRESENZE_ABILITA);
		render = val != null && "1".equals(val) ? true : false; 
		return render;
	}
	
	
	
	public static List<CsAComponente> caricaParenti(Long soggettoId, Date dtRif) {
		List<CsAComponente> lstComponenti = new ArrayList<CsAComponente>();
		if(soggettoId!=null){
			BaseDTO bo = new BaseDTO();
			fillEnte(bo);
			bo.setObj(soggettoId);
			bo.setObj2(dtRif);
			lstComponenti = schedaService.findComponentiFamigliaAllaDataBySoggettoId(bo);
		}else
			logger.error("Impossibile caricare la lista di familiari: soggettoId non valorizzato");
		return lstComponenti;
	}
	
	public boolean verificaBeneficiarioRdC(String cf) {
		boolean val = false;
		if(!StringUtils.isEmpty(cf)) {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(cf);
			val = soggettoService.isBeneficiarioRdC(dto);
		}
		return val;
	}
	
	public static List<SelectItem> convertiLista(List<KeyValueDTO> in){
		List<SelectItem> out = new ArrayList<SelectItem>();
		for(KeyValueDTO k : in){
			SelectItem si = new SelectItem(k.getCodice(), k.getDescrizione());
			si.setDisabled(!k.isAbilitato());
			out.add(si);
		}
		return out;
	}
	
	protected CsASoggettoLAZY getSoggettoById(Long anagraficaId){
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(anagraficaId);
		return soggettoService.getSoggettoById(dto);
	}
	
	protected CsASoggettoLAZY getSchedaCSByCF(String cf) {
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

	protected CsOSettore findSettoreById(Long idSettore) {
		if (idSettore != null) {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(idSettore);
			return confEnteService.getSettoreById(dto);
		}
		return null;
	}
	
}
