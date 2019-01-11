package it.webred.cs.csa.web.bean.util;

import it.webred.amprofiler.model.AmGroup;
import it.webred.cs.csa.ejb.client.AccessTableInterventoErogazioneSessionBeanRemote;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.PermessiGenerali;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.sociosan.ejb.client.CTConfigClientSessionBeanRemote;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.mailing.MailUtils;
import it.webred.mailing.MailUtils.MailAddressList;
import it.webred.mailing.MailUtils.MailParamBean;

import java.io.IOException;
import java.util.List;

import javax.activation.FileDataSource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class NavigationBean extends CsUiCompBaseBean {
	
	private String appEmail;
	
	public void goHome() {
		getSession().setAttribute("navigationHistory", "");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("home.faces");
		} catch (IOException e) {
			addError("Errore", "Errore durante il reindirizzamento");
		}
	}
	
	public void goListaCasi(boolean clearFilters) {
		if(clearFilters)
			this.clearParametriFiltro();
		
		getSession().setAttribute("navigationHistory", "listaCasi");
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("listaCasi.faces");
		} catch (IOException e) {
			addError("Errore", "Errore durante il reindirizzamento");
		}
	}
	
	public void goErogazioniInterventi(){
		getSession().setAttribute("navigationHistory", "erogazioniInterventi");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("erogazioniInterventi.faces");
		} catch (IOException e) {
			addError("Errore", "Errore durante il reindirizzamento");
		}
	}
	
	public void goEsportaCasellario(){
		getSession().setAttribute("navigationHistory", "esportaCasellario");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("esportaCasellario.faces");
		} catch (IOException e) {
			addError("Errore", "Errore durante il reindirizzamento");
		}
	}
	
	public void goInterscambioCartellaSociale() {
		getSession().setAttribute("navigationHistory",
				"interscambioCartellaSociale");

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("interscambioCartellaSociale.faces");
		} catch (IOException e) {
			addError("Errore", "Errore durante il reindirizzamento");
		}
	}

	/**
	 * Naviga verso la pagina di visualizzazione degli eventi di import/export
	 * Già filtrata in base al caso di provenienza 
	 * @param CF
	 */
	public void goListaEventiFiltrataPerCF(String CF, String type ) {
		getSession().setAttribute("navigationHistory",
				"interscambioCartellaSociale");

		/**
		 * Immetto il codice fiscale del caso da visualizzare in sessione 
		 */
		getSession().setAttribute("eventListFilter", CF);
		getSession().setAttribute("eventListFilterType", type);

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("interscambioCartellaSociale.faces");
		} catch (IOException e) {
			addError("Errore", "Errore durante il reindirizzamento");
		}
	}
	public boolean isInterscambioVisible(){
		return getNavigationHistory().contains("interscambioCartellaSociale");
	}
	
	public boolean isListaCasiVisible() {
		return getNavigationHistory().contains("listaCasi");
	}
	
	public void goSchedeSegretariato() {
		getSession().setAttribute("navigationHistory", "listaSchedeSegretariato");

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("listaSchedeSegretariato.faces");
		} catch (IOException e) {
			addError("Errore", "Errore durante il reindirizzamento");
		}
	}
	
	public boolean isSchedeSegretariatoVisible() {
		return getNavigationHistory().contains("listaSchedeSegretariato");
	}
	
	public void goConfigurazione() {
		getSession().setAttribute("navigationHistory", "configurazione");
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("configurazione.faces");
		} catch (IOException e) {
			addError("Errore", "Errore durante il reindirizzamento");
		}
	}
	
	public void goSegrSociale() {		
		try {
			String serverName = getRequest().getServerName();
			Integer serverPort = getRequest().getServerPort();
			String urlSegrSociale = "http://" + serverName;
			if(serverPort != null)
				urlSegrSociale += ":" + serverPort;
			urlSegrSociale += "/SegretariatoSoc_WEB";
			FacesContext.getCurrentInstance().getExternalContext().redirect(urlSegrSociale);
		} catch (Exception e) {
			addError("Errore", "Errore durante il reindirizzamento");
		}
	}
	
	public boolean isConfigurazioneVisible() {
		return getNavigationHistory().contains("configurazione");
	}
	
	public boolean isCasoVisible() {
		String currentUrl = getRequest().getRequestURL().toString();
		if(currentUrl.contains("scheda.faces") || currentUrl.contains("fascicoloCartellaUtente.faces"))
			return true;
		else return false;
	}
	
	public boolean isDocIndividualiVisible() {
		String currentUrl = getRequest().getRequestURL().toString();
		if(currentUrl.contains("docIndividuali.faces"))
			return true;
		else return false;
	}
	
	public boolean isRenderDownloadAppErogazioni(){
		String link = CsUiCompBaseBean.getAppErogazioniLink();
		return (link!=null && !link.trim().isEmpty()) ;
	}
	
	public void sendEmailApp(){
		try{
		CTConfigClientSessionBeanRemote mailConf = 
				(CTConfigClientSessionBeanRemote) ClientUtility.getEjbInterface("SocioSanitario","SocioSanitario_EJB", "CTConfigClientSessionBean");
		String link = CsUiCompBaseBean.getAppErogazioniLink();
		if(appEmail!=null && !appEmail.trim().isEmpty()){
			
				// Now try to send email
				MailAddressList addressTO = new MailAddressList(appEmail);
				MailAddressList addressCC = new MailAddressList();
				MailAddressList addressBCC = new MailAddressList();
	
				// Segnalibri
				String subject = "SISO - APP EROGAZIONI ";
				String messageBody = "L'app per la gestione erogazioni del sistema SISO può essere scaricata da <a href=\""+link+"\">qui</a>";
				
				MailParamBean params = mailConf.getSISOMailParametres();
				MailUtils.sendEmail(params, addressTO, addressCC, addressBCC, subject,messageBody, (FileDataSource[]) null);
				addInfo("E-mail inviata","Il collegamento per scaricare l'app erogazioni è stato inviato a "+appEmail);
				appEmail=null;
		}
		}catch(Exception e){
			this.addError("Non è stato possibile inviare l'email", e.getMessage());
			logger.error("__ Errore in invio email per invio link APP EROGAZIONI:"+e.getMessage());
		}
	}
	
	public boolean isAutorizzatoConfigurazione() {
		return CsUiCompBaseBean.checkPermesso(PermessiGenerali.ITEM, PermessiGenerali.AMMINISTRAZIONE_ORG_SETT_OP);
	}
	
	public boolean isPermessoUdcEnte(){
		boolean permesso = false; 
		String belfiore = getCurrentBelfioreCS();
		List<AmGroup> lstGruppi = getListaGruppi(getCurrentUsername(), belfiore);
		int i = 0;
		while(!permesso && i<lstGruppi.size()){
			AmGroup gruppo = lstGruppi.get(i);
			if(gruppo.getFkAmComune().equals(belfiore) && gruppo.getName().startsWith("SSOCIALE_"))
				permesso = true;
			i++;
		}
		return permesso;
	}
	
	private String getNavigationHistory() {
		String nav = (String) getSession().getAttribute("navigationHistory");
		if(nav == null)
			return "";
		return nav;
	}

	public String getAppEmail() {
		return appEmail;
	}

	public void setAppEmail(String appEmail) {
		this.appEmail = appEmail;
	}
	
/*	
 * public void mobile(){
		AccessTableInterventoErogazioneSessionBeanRemote sb =  (AccessTableInterventoErogazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoErogazioneSessionBean");
		 
		CeTBaseObject bDto = new CeTBaseObject();
		fillEnte(bDto);
		
		sb.verificaLoadingMobileStaging(bDto);
	}*/

	
	
}
