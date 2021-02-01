package eu.smartpeg.rilevazionepresenze.web.bean;
import java.util.List;

import eu.smartpeg.rievazionepresenze.dto.BaseDTO;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

import it.webred.amprofiler.ejb.anagrafica.AnagraficaService;
import it.webred.amprofiler.ejb.user.UserService;
import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.amprofiler.model.AmGroup;
import it.webred.cet.permission.CeTUser;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmInstance;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.application.ApplicationService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;

public class RilevazionePresenzeBaseBean {
	public static Logger logger = Logger.getLogger("rilevazionepresenze.log");
	
	private AccessTableConfigurazioneSessionBeanRemote configurationCsBean;
	
	private static ResourceBundle bundle;

	static {
		bundle = ResourceBundle.getBundle("eu.smartpeg.rilevazionepresenze.web.resources.messages");
	}
	
	public static final String loginAction = "LOGIN";
	public static final String logoutAction = "LOGOUT";
	public static final String read = "READ";
	public static final String write = "CREATE";
	public static final String edit = "EDIT";
	public static final String receive = "RECEIVE";
	public static final String delete = "DELETE";
	public static final String print = "PRINT";
	
	protected List<SelectItem> listaOrganizzazioni;
	
	public String getLogoTitolo() {
		String titolo = "titolo_RP.png";
		return titolo;
	}
	
	
	public String getLabelRilevazionePresenze() {
//		return this.getTipoApplicazione().equalsIgnoreCase("udc") ? "Ufficio della Cittadinanza" : "Segretariato Sociale";
		return "Rilevazione Presenze";
	}
	
	public String getCognomeNomeOperatore() {
		CeTUser user = getUser();
		if (user != null)
			return this.getCognomeNomeUtente(user.getUsername());
		else
			return "";
	}
	
	public String getCognomeNomeUtente(String user) {
		AmAnagrafica amAna = this.getAnagrafica(user);
		if (amAna != null)
			return amAna.getCognome() + " " + amAna.getNome();
		else
			return user;
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
	
	@SuppressWarnings({ "unchecked", "el-syntax" })
	public static <T> T findBean(String beanName) {
		FacesContext context = FacesContext.getCurrentInstance();
		return (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
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
	protected void addMessage(String summary, String descrizione, FacesMessage.Severity severity) {
		FacesMessage message = new FacesMessage(severity, summary, descrizione != null ? descrizione : "");
		FacesContext.getCurrentInstance().addMessage(null, message);
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
	
	public CeTUser getUser() {

		CeTUser user = (CeTUser) getSession().getAttribute("user");

		String istanza = "RilevazionePresenze";
		try {
			ApplicationService appService = (ApplicationService) ClientUtility.getEjbInterface("CT_Service", "CT_Config_Manager", "ApplicationServiceBean");
			CeTBaseObject cet = new CeTBaseObject();
			fillUserData(cet);
			AmInstance instance = appService.getInstanceByApplicationComune("RilevazionePresenze", cet.getEnteId());
			istanza = instance.getName();
		} catch (Exception e) {

		}
		if (user != null) {
			HashMap<String, String> permessi = user.getPermList();
			

			HttpSession session = getRequest().getSession();
			if (session.getAttribute(loginAction) == null) {
				session.setAttribute(loginAction, true);
				logAction(loginAction);
			}
		}
		return user;

	}
	public void fillUserData(CeTBaseObject ro) {

		CeTUser user = (CeTUser) getSession().getAttribute("user");
		if (user != null) {
			ro.setEnteId(user.getCurrentEnte());
			ro.setUserId(user.getUsername());
			ro.setSessionId(user.getSessionId());
		}
		
//		String ente = getPreselectedBelfioreOrg();
//		if (ente != null)
//			ro.setEnteId(ente);
	}
	
	public List<AmGroup> getGruppiUtenteEnte(){
		String belfiore = this.getCurrentEnte();
		List<AmGroup> lstGruppi = CsUiCompBaseBean.getListaGruppi(getUser().getUsername(), belfiore);
		return lstGruppi;
	}
	
	public void logAction(String action) {
		logAction(action, null);
	}
	
//	public String getPreselectedBelfioreOrg(){
//		String bel = getPreselectedPContatto().getOrganizzazione().getBelfiore();
//		return bel;
//	}
	
	public void logAction(String action, Long subject) {
		String user = getUserNameOperatore();
		String subjectString = ".";
		if (subject != null)
			subjectString = " (ID SCHEDA: " + subject + ").";
		logger.info(user + " -> " + action + subjectString);
		this.loadListaOrganizzazioni("LOGIN".equalsIgnoreCase(action));
			
	}
	
public void loadListaOrganizzazioni(boolean load){	
		
		try{
		 //if(this.listaOrganizzazioni==null || this.listaOrganizzazioni.isEmpty()){
			listaOrganizzazioni = new ArrayList<SelectItem>();
			
			if(load) {	
				List<String> listaEntiAM = new ArrayList<String>();
				CeTUser user = this.getUser();
				if(user!=null){
					List<AmGroup> lstGruppiAM = getListaEntiGIT(user.getUsername());
					for(AmGroup gr : lstGruppiAM){
						if(gr.getName().startsWith("RILPRES_"))
							listaEntiAM.add(gr.getFkAmComune());
					}
				}
					
				if(!listaEntiAM.isEmpty()){
			   //Recupero tutti gli enti configurati nella zona sociale
				List<CsOOrganizzazione> lstEnti = getListaEnti();
				for(CsOOrganizzazione o : lstEnti){
					String idLista = o.getCodRouting()+"|"+o.getId();
					//Verifico che l'utente sia abilitato all'accesso per l'ente
					if(listaEntiAM.contains(o.getCodRouting())){
							SelectItem si = new SelectItem(idLista,o.getNome());
							listaOrganizzazioni.add(si);
				  }
				}
			  }//else this.addWarning("login.no.organizzazione");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("lettura.error", "Caricamento Enti non riuscito !");
		}
	}
	
	
	public String getUserNameOperatore() {
		CeTUser user = getUser();
		if (user != null) {
			return user.getUsername();
		} else
			return null;
	}
	
	public String getCurrentEnte(){
		CeTUser user = (CeTUser) getSession().getAttribute("user");
		String ente = user.getCurrentEnte();//   this.getPreselectedBelfioreOrg();
		if(ente==null)
		  ente = this.getUser().getCurrentEnte();
		return ente;
	}
	
	public AccessTableConfigurazioneSessionBeanRemote getConfigurationCsBean(){
		if (configurationCsBean == null)
			configurationCsBean =
					(AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
		return configurationCsBean;
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
	
	// Carica la lista delle Organizzazioni con Belfiore/Codice fittizio Specificato
		protected List<CsOOrganizzazione> getListaEnti() {
			try {
				AccessTableConfigurazioneSessionBeanRemote confService = getConfigurationCsBean();
				CeTBaseObject opDto = new CeTBaseObject();
				this.fillUserData(opDto);
				List<CsOOrganizzazione> lstOrg = confService.getOrganizzazioniAccesso(opDto);
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
		
		public String getBelfioreSceltaEnte() {
			CeTUser user = getUser();
			if (user != null)
				return user.getCurrentEnte();
			else
				return null;
		}
		
}