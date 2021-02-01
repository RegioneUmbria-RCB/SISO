package eu.smartpeg.rilevazionepresenze.web;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import eu.smartpeg.rievazionepresenze.dto.BaseDTO;
import it.webred.cet.permission.CeTUser;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;

public class RilevazionePresenzeBaseController {



	private String logoBasePath;
	protected String dirLoghi = "/images/logo/";

	private StreamedContent logoComune;

	public static Logger logger = Logger.getLogger("rilevazionepresenze.log");
	
    protected void addMessage(FacesMessage.Severity tipoMessaggio, String summary, String messaggio) {
		FacesMessage message = new FacesMessage(tipoMessaggio, summary, messaggio);
		FacesContext.getCurrentInstance().addMessage(null, message);
	} 

	public String getLabelSegrSociale() {
		return  "Rilevazione Presenze" ;
	}

	public StreamedContent getLogoComune() {
		return this.getLogo("logo_intestazione.jpg");
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
	
	


	public boolean esisteLogoComune() {
		return this.esisteLogo("logo_intestazione.jpg");
	}

	public boolean esisteLogoHeader1() {
		return this.esisteLogo("logo-header1.jpg");
	}

	public boolean esisteLogoHeader2() {
		return this.esisteLogo("logo-header2.jpg");
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
	
	public HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
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

	public static void fillEnte(CeTBaseObject ro) {

		//TODO: getSession non funziona .. perch�?
		CeTUser user = (CeTUser) getSession().getAttribute("user");
		if (user != null) {
			ro.setEnteId(user.getCurrentEnte());
			ro.setUserId(user.getUsername());
			ro.setSessionId(user.getSessionId());
		}
		
//		PreselPuntoContatto pContMan = (PreselPuntoContatto)getBeanReference("preselPuntoContatto");
//		String ente = pContMan.getPuntoContatto().getOrganizzazione().getBelfiore();
//		if (ente != null)
//			ro.setEnteId(ente);
	}
	

}
