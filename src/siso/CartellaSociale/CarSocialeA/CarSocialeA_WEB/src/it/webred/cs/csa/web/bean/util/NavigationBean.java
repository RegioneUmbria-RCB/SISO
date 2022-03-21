package it.webred.cs.csa.web.bean.util;

import it.webred.amprofiler.model.AmGroup;
import it.webred.cs.csa.web.manbean.fascicolo.pai.ProgettiIndividualiExtBean;
import it.webred.cs.data.DataModelCostanti.PermessiGenerali;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.sociosan.ejb.client.ArgoBufferManagerSessionBeanRemote;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


@ManagedBean
@ViewScoped
public class NavigationBean extends CsUiCompBaseBean {
	
	private String appEmail;
	
	@ManagedProperty(value="#{progettiInvidualiExt}")
	private ProgettiIndividualiExtBean progettiIndividualiExtBean;
	
	public void goHome() {
		getSession().setAttribute("navigationHistory", "");
		redirect("home.faces");
	}
	public void goIterCartellaSociale() {
		getSession().setAttribute("navigationHistory","iterCartellaSociale");
		redirect("iterCartellaSociale.faces");
	}
	public void goListaCasi(boolean clearFilters) {
		if(clearFilters)
			this.clearParametriFiltroCasi();
		
		getSession().setAttribute("navigationHistory", "listaCasi");
		getSession().setAttribute("fromListaCasi", Boolean.TRUE); //SISO-812
		redirect("listaCasi.faces");
	}
	
	//SISO - 812, inizio
	public void goListaCasiAssegnati(boolean clearFilters) {
		if(clearFilters)
			this.clearParametriFiltroCasi();
		
		getSession().setAttribute("navigationHistory", "listaCasiAssegnati");
		getSession().setAttribute("fromListaCasi", Boolean.FALSE);
		redirect("listaCasiAssegnati.faces");
	}
	//SISO - 812, fine
	public void goErogazioniInterventi(){
		getSession().setAttribute("navigationHistory", "erogazioniInterventi");
		redirect("erogazioniInterventi.faces");
	}
	
	// SISO-1280, inizio
	public void goProgettiIndividuali() {
		getSession().setAttribute("navigationHistory", "progettiIndividuali");
		progettiIndividualiExtBean.initializeData();
		redirect("progettiIndividuali.faces");
	}
	// SISO-1280, fine
		
	public void goEsportaCasellario(){
		getSession().setAttribute("navigationHistory", "esportaCasellario");
		redirect("esportaCasellario.faces");
	}
	
	public void goEsportaDatiSinba(){
		getSession().setAttribute("navigationHistory", "esportaDatiSinba");
		redirect("esportaDatiSinba.faces");
	}
	
	public void goInterscambioCartellaSociale() {
		getSession().setAttribute("navigationHistory", "interscambioCartellaSociale");
		redirect("interscambioCartellaSociale.faces");
	}

	/**
	 * Naviga verso la pagina di visualizzazione degli eventi di import/export
	 * Già filtrata in base al caso di provenienza 
	 * @param CF
	 */
	public void goListaEventiFiltrataPerCF(String CF, String type ) {
		getSession().setAttribute("navigationHistory", "interscambioCartellaSociale");

		/**
		 * Immetto il codice fiscale del caso da visualizzare in sessione 
		 */
		getSession().setAttribute("eventListFilter", CF);
		getSession().setAttribute("eventListFilterType", type);

		redirect("interscambioCartellaSociale.faces");
	}
	
	public void goSchedeSegretariato() {
		getSession().setAttribute("navigationHistory", "listaSchedeSegretariato");
		getSession().setAttribute("fromListaCasi", Boolean.FALSE);

		redirect("listaSchedeSegretariato.faces");
	}
	
	public void goRedditoCittadinanza() {
		getSession().setAttribute("navigationHistory", "listaRedditoCittadinanza");
		getSession().setAttribute("fromListaCasi", Boolean.FALSE);

		redirect("listaRedditoCittadinanza.faces");
	}
	
	public void goListaFse() {
		clearParametriFiltroFse();
		getSession().setAttribute("navigationHistory", "listaFse");
		getSession().setAttribute("fromListaCasi", Boolean.FALSE);
		redirect("listaFse.faces");
	}
	
	public void goConfigurazione() {
		getSession().setAttribute("navigationHistory", "configurazione");
		redirect("configurazione.faces");
	}
	//SISO-1233
	public void goMenuLayout() {
		getSession().setAttribute("navigationHistory", "");
		getSession().setAttribute("fromMenuLayout", Boolean.FALSE);
	}
	
	public void goSegrSociale() {		
		try {
			String scheme = getRequest().getScheme();
			String serverName = getRequest().getServerName();
			Integer serverPort = getRequest().getServerPort();
			String urlSegrSociale = scheme + "://" + serverName;
			if(serverPort != null)
				urlSegrSociale += ":" + serverPort;
			urlSegrSociale += "/SegretariatoSoc_WEB";
			FacesContext.getCurrentInstance().getExternalContext().redirect(urlSegrSociale);
		} catch (Exception e) {
			addError("Errore", "Errore durante il reindirizzamento");
		}
	}
	

	//Variabili per attivazione BREADCRUMB
	public boolean isListaCasiAssegnatiVisible() {
		return getNavigationHistory().contains("listaCasiAssegnati");
	}
	public boolean isRedditoCittadinanzaVisibile() {
		return getNavigationHistory().contains("listaRedditoCittadinanza");
	}
	public boolean isProgettiIndividualiVisible(){
		return getNavigationHistory().contains("progettiIndividuali");
	}
	public boolean isSchedeSegretariatoVisible() {
		return getNavigationHistory().contains("listaSchedeSegretariato");
	}
	public boolean isConfigurazioneVisible() {
		return getNavigationHistory().contains("configurazione");
	}
	public boolean isInterscambioVisible(){
		return getNavigationHistory().contains("interscambioCartellaSociale");
	}
	public boolean isListaCasiVisible() {
		return getNavigationHistory().contains("listaCasi") && !getNavigationHistory().contains("listaCasiAssegnati");//SISO-812
	}
	public boolean isListaFseVisibile() {
		return getNavigationHistory().contains("listaFse");
	}
	
	public boolean isCasoVisible() {
		String currentUrl = getRequest().getRequestURL().toString();
		if(currentUrl.contains("scheda.faces") || currentUrl.contains("fascicoloCartellaUtente.faces"))
			return true;
		else return false;
	}
	
	//SISO-812
	public boolean isVisibilePerCasoAssegnato(){
		Object obj = getSession().getAttribute("fromListaCasi");
		return obj!=null ? (Boolean)obj : false;
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
			ArgoBufferManagerSessionBeanRemote argoBufferManager = 
					(ArgoBufferManagerSessionBeanRemote) getEjb("SocioSanitario", "SocioSanitario_EJB", "ArgoBufferManagerSessionBean");
			String link = CsUiCompBaseBean.getAppErogazioniLink();
			String subject = "SISO - APP EROGAZIONI ";
			String messageBody = "L'app per la gestione erogazioni del sistema SISO può essere scaricata da <a href=\""+link+"\">qui</a>";
		
			argoBufferManager.sendSimpleMailFromSISO(appEmail, subject, messageBody);
			addInfo("E-mail inviata","Il collegamento per scaricare l'app erogazioni è stato inviato a "+appEmail);
			appEmail=null;
			
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
	public ProgettiIndividualiExtBean getProgettiIndividualiExtBean() {
		return progettiIndividualiExtBean;
	}
	public void setProgettiIndividualiExtBean(ProgettiIndividualiExtBean progettiIndividualiExtBean) {
		this.progettiIndividualiExtBean = progettiIndividualiExtBean;
	}
	
	private void redirect(String paginaFaces){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(paginaFaces);
		} catch (IOException e) {
			addError("Errore", "Errore durante il reindirizzamento");
		}
	}
/*	
 * public void mobile(){
		AccessTableInterventoErogazioneSessionBeanRemote sb =  (AccessTableInterventoErogazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoErogazioneSessionBean");
		 
		CeTBaseObject bDto = new CeTBaseObject();
		fillEnte(bDto);
		
		sb.verificaLoadingMobileStaging(bDto);
	}*/

	
	
	
}
