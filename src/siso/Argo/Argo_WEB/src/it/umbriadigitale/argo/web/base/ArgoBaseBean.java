package it.umbriadigitale.argo.web.base;
import it.webred.cet.permission.CeTUser;
import it.webred.cs.base.BaseDTO;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class ArgoBaseBean {

		public static Logger logger = Logger.getLogger("argo.log");
		
		public final static ParameterService paramService = (ParameterService) getEjb("CT_Service", "CT_Config_Manager", "ParameterBaseService");
		   
		private StreamedContent logoComune;
		protected String dirLoghi = "/images/logo/";
		private String logoBasePath;

	/*	private static ResourceBundle bundle;

		static {
			//bundle = ResourceBundle.getBundle("it.umbriadigitale.argo.web.messages", FacesContext.getCurrentInstance().getApplication().getDefaultLocale());
			FacesContext context = FacesContext.getCurrentInstance();
			bundle = context.getApplication().getResourceBundle(context, "msgs");
		}*/
		
		protected String getDirDatiDiogene() {
			if(this.logoBasePath==null){
				ParameterSearchCriteria criteria = new ParameterSearchCriteria();
				criteria.setKey("dir.files.datiDiogene");
				criteria.setComune(null);
				criteria.setSection(null);
				
				AmKeyValueExt amKey = paramService.getAmKeyValueExt(criteria);
				if(amKey != null) 
					 this.logoBasePath =  amKey.getValueConf();
				else 
					logger.error("Attenzione: Il path per il recupero logo del report non è impostato");
			}
			return logoBasePath;
		}
		
		protected String getPathLoghi(){
			BaseDTO baseDto = new BaseDTO();
			fillEnte(baseDto);
			String pathLoghi = null;
			if(baseDto.getEnteId()!=null){
				String dir = this.getDirDatiDiogene();
				if(dir != null) 
					pathLoghi = dir + baseDto.getEnteId() + dirLoghi;
			}
			return pathLoghi;
		}
		
		public static CeTBaseObject fillEnte(CeTBaseObject ro) {	
			CeTUser user = getUser();
			if(user != null){
				ro.setEnteId(user.getCurrentEnte());
				ro.setUserId(user.getUsername());
				ro.setSessionId(user.getSessionId());
			}
			return ro;
		}
		

		public StreamedContent getLogo(String nomefile) {
			
			String pathLoghi = this.getPathLoghi();
			if(pathLoghi!=null){
				String slogo = pathLoghi + nomefile;
				File logo = new File(slogo);
				if(logo.exists()){
					try {
						this.logoComune = new DefaultStreamedContent(new FileInputStream(logo), "image/jpeg");
					} catch (FileNotFoundException e) {
						logger.error("Errore recupero Logo Comune", e);
						this.logoComune=null;
					}
				}else {
					logger.error("Attenzione: Il logo del comune non è presente ["+slogo+"]");
					this.logoComune=null;
				}
				
			}	
			return logoComune;
		}
			protected boolean esisteLogo(String nomeFile){
			boolean esiste = false;
			String pathLoghi = this.getPathLoghi();
			if(pathLoghi!=null){
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
		
		public boolean esisteLogoComune(){
			return this.esisteLogo("logo_intestazione.jpg");
		}
		
		public boolean esisteLogoHeader1() {
			return this.esisteLogo("logo-header1.jpg");
		}
		
		public boolean esisteLogoHeader2() {
			return this.esisteLogo("logo-header2.jpg");
		}


		
		public static Object getEjb(String ear, String module, String ejbName){
			Context cont;
			try {
				cont = new InitialContext();
				return cont.lookup("java:global/" + ear + "/" + module + "/" + ejbName);
			} catch (NamingException e) {
				logger.error(e.getMessage(), e);
			}
			return null;
		}
		
		
		
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
		
		protected void addErrorCampiObbligatori(String nomeTab, String campo, String descrizione){
			String summary = (nomeTab!=null ? nomeTab+": " : "") +campo+" è un campo obbligatorio";
			this.addWarning(summary, descrizione);
		}

		protected void addError( String summary, String descrizione ) {
			addMessage(summary, descrizione, FacesMessage.SEVERITY_ERROR);
		}

		protected void addWarning( String summary, String descrizione ) {
			addMessage(summary, descrizione, FacesMessage.SEVERITY_WARN);
		}

		protected void addInfo( String summary, String descrizione ) {
			addMessage(summary, descrizione, FacesMessage.SEVERITY_INFO);
		}
		
		protected void addMessage( String fieldId, String summary, String descrizione, FacesMessage.Severity severity ) {
			FacesMessage message = new FacesMessage(severity, summary, descrizione != null? descrizione: "" ); 
		    FacesContext.getCurrentInstance().addMessage(fieldId, message);
		}

		protected void addMessage( String summary, String descrizione, FacesMessage.Severity severity ) {
			FacesMessage message = new FacesMessage(severity, summary, descrizione != null? descrizione: "" ); 
		    FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
	/*	public static void addMessageFromProperties(String messageKey, FacesMessage.Severity severity) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			String txt = bundle.getString(messageKey);

			facesContext.addMessage(null, new FacesMessage(severity, txt, ""));
		}
		
		public void addInfoFromProperties(String msgKey) {  
			addMessageFromProperties(msgKey, FacesMessage.SEVERITY_INFO);  
	    }
		
		public void addWarningFromProperties(String msgKey) {  
			addMessageFromProperties(msgKey, FacesMessage.SEVERITY_WARN);  
	    }
		
		public static void addErrorFromProperties(String msgKey) {  
			addMessageFromProperties(msgKey, FacesMessage.SEVERITY_ERROR);  
	    }*/
		
		public static CeTUser getUser() {
			HttpSession session = getSession();
			if(session!=null)
				return (CeTUser) session.getAttribute("user");
			else 
				return null;
		}
		
		private static String getParametro(ParameterSearchCriteria criteria) {
			AmKeyValueExt amKey = paramService.getAmKeyValueExt(criteria);
			if (amKey != null)
				return amKey.getValueConf();
			else
				return null;
		}
		

		public static String getParametroGlobale(String key){
			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setKey(key);
			criteria.setSection("param.globali");
			String label = getParametro(criteria);
			return label;
		}
}
