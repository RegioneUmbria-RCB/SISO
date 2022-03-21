package it.umbriadigitale.soclav.managedbeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.Application;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import it.umbriadigitale.soclav.DataModelCostanti;
import it.umbriadigitale.soclav.DataModelCostanti.PermessiGenerali;
import it.umbriadigitale.soclav.model.RdCKeyValueExt;
import it.umbriadigitale.soclav.service.interfaccia.IAnagraficaService;
import it.umbriadigitale.soclav.service.interfaccia.IRdCKeyValueExtService;
import it.umbriadigitale.soclav.service.interfaccia.IUserService;
import it.umbriadigitale.soclav.util.WebAppConfig;


public class BaseBean {
	
	@Autowired
	IUserService userService;
	
	public static final Logger logger = Logger.getLogger("SocLav");
	
	@ManagedProperty("#{webAppConfig}")
	protected WebAppConfig webAppConfig;
	//
	@ManagedProperty("#{keyValueExtService}")
	protected IRdCKeyValueExtService  keyValueExtService;

	public WebAppConfig getWebAppConfig() {
		return webAppConfig;
	}

	public void setWebAppConfig(WebAppConfig webAppConfig) {
		this.webAppConfig = webAppConfig;
	}

	public static Object getBeanReference(String beanName) {
		FacesContext context = FacesContext.getCurrentInstance();
		Application a = context.getApplication();
		Object o = a.getVariableResolver().resolveVariable(context, beanName);
		return o;
	}
	
	protected static String getPatternPermesso(String item, String nomePermesso) {
		String istanza = DataModelCostanti.ISTANZA;
		return "permission@-@" + istanza + "@-@" + item + "@-@" + nomePermesso;
	}
	
	public static boolean verificaPermesso(String item, String permesso) {
		AutenticazioneBean bean = (AutenticazioneBean)getBeanReference("autenticazioneBean");
		boolean autorizza = false;
		if(bean.getCetUser()!=null) {
			HashMap<String,String> mappa = (HashMap<String,String>)bean.getCetUser().getPermList();
			String patt = getPatternPermesso(item, permesso);
			if (mappa != null && mappa.get(patt) != null)
				autorizza = true;
		}
		return autorizza;
	}
	
	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public boolean isVisualizzaPattoLavoro() {
		return verificaPermesso(PermessiGenerali.ITEM,PermessiGenerali.VISUALIZZA_LISTA_ANPAL);
	}
	
	public boolean isVisualizzaPattoSociale() {
		return verificaPermesso(PermessiGenerali.ITEM,PermessiGenerali.VISUALIZZA_LISTA_GEPI);
	}
	
	public static boolean isVisualizzaListaCompletaRdC() {
		return verificaPermesso(PermessiGenerali.ITEM,PermessiGenerali.VISUALIZZA_LISTA_COMPLETA_RDC);
	}
	
	public boolean isVisualizzaConfigurazione() {
		return verificaPermesso(PermessiGenerali.ITEM,PermessiGenerali.CONFIGURAZIONE_UTENTI);
	}
	
	public boolean isGestisciConsensi() {
		return verificaPermesso(PermessiGenerali.ITEM,PermessiGenerali.GESTIONE_CONSENSI);
	}
	
	protected String getGlobalParameter(String paramName) {
		 
		RdCKeyValueExt amKey = this.keyValueExtService.getKeyValueExtparam(paramName);
		if (amKey != null && amKey.getKeyValue() != null && !amKey.getKeyValue().trim().isEmpty()) {
			return amKey.getKeyValue();
		} else
			logger.warn("Parametro '" + paramName + "' non definito");

		return null;
	}

	public IRdCKeyValueExtService getKeyValueExtService() {
		return keyValueExtService;
	}

	public void setKeyValueExtService(IRdCKeyValueExtService keyValueExtService) {
		this.keyValueExtService = keyValueExtService;
	}
	
	public List<String> loadEntiAbilitati(){
		AutenticazioneBean bean = (AutenticazioneBean)BaseBean.getBeanReference("autenticazioneBean");
        List<String> entiAbilitati = new ArrayList<String>();
        if(BaseBean.isVisualizzaListaCompletaRdC())
        	entiAbilitati.add("ALL");
        else {
        	entiAbilitati.add(bean.getCetUser().getCurrentEnte());
        	List<String> enti = userService.findEntiCompetenzaByUsername(bean.getCetUser().getUsername());
        	entiAbilitati.addAll(enti);
        }
        return entiAbilitati;
	}
	
}
