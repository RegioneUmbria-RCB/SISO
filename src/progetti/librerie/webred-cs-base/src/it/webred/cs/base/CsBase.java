package it.webred.cs.base;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.AmParameterKey;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;


public class CsBase {
	
	public static final SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
	public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
	protected static Logger logger = Logger.getLogger("carsociale.log");
	protected static ParameterService paramService = (ParameterService) getEjb("CT_Service", "CT_Config_Manager", "ParameterBaseService");
	
	protected URL stringToUrl(String urlString) {
		URL url;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			url=null;
		}
		return url;
	}
	
	public class CredenzialiIseeWS{
		private String username;
		private String ufficio;
		private String ente;
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getUfficio() {
			return ufficio;
		}
		public void setUfficio(String ufficio) {
			this.ufficio = ufficio;
		}
		public String getEnte() {
			return ente;
		}
		public void setEnte(String ente) {
			this.ente = ente;
		}
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

	protected CredenzialiWS getCredenzialiWS(String usernameKey, String passwordKey) {
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

	protected CredenzialiIseeWS getCredenzialiIseeWS() {
		
		String username = this.getGlobalParameter(AmParameterKey.WS_ISEE_USERNAME);
		String ufficio = this.getGlobalParameter(AmParameterKey.WS_ISEE_UFFICIO);
		String ente = this.getGlobalParameter(AmParameterKey.WS_ISEE_ENTE);
		
		CredenzialiIseeWS ars  = null;
		if(!StringUtils.isBlank(username) && !StringUtils.isBlank(ufficio) && !StringUtils.isBlank(ente)){
			ars = new CredenzialiIseeWS();
			ars.setUsername(username);
			ars.setUfficio(ufficio);
			ars.setEnte(ente);
		}else{
			logger.warn("Credenziali WS ISEE non definite");
		}
		
	
		return ars;
	}

	
	
	protected String getGlobalParameter(String paramName) {
		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setKey(paramName);
		criteria.setComune(null);
		criteria.setSection(null);
		AmKeyValueExt amKey = paramService.getAmKeyValueExt(criteria);
		if(amKey != null && amKey.getValueConf()!=null) {
			return amKey.getValueConf();
		}else
			logger.warn("Parametro '"+paramName+"' non definito");
		
		return null;
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
	

	public boolean isRicercaIseeAbilitata(){
		return "1".equalsIgnoreCase(this.getGlobalParameter(DataModelCostanti.AmParameterKey.WS_ISEE_ABILITA));
	}
	
	public boolean isAnagrafeSanitariaUmbriaAbilitata(){
		List<String> s = getTipoRicercaAbilitata();
		return s.contains(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA);
	}
	
	public boolean isAnagrafeComunaleInternaAbilitata(){
		List<String> s = getTipoRicercaAbilitata();
		return s.contains(DataModelCostanti.TipoRicercaSoggetto.DEFAULT);
	}
	
	protected List<String> getTipoRicercaAbilitata(){
		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setKey("smartwelfare.ricercaSoggetto.tipo");
		criteria.setComune(null);
		criteria.setSection(null);
		AmKeyValueExt amKey = paramService.getAmKeyValueExt(criteria);
		String[] s = (amKey!=null && amKey.getValueConf()!=null && !amKey.getValueConf().trim().isEmpty()) ? amKey.getValueConf().toUpperCase().split(";") : null;
	    return s!=null ? Arrays.asList(s) : null;
	}
	
	protected URL getAnagRegWebServiceWSDLLocation(){
		String urlString = this.getGlobalParameter(DataModelCostanti.AmParameterKey.WS_RICERCA_URL);
		if(urlString!=null && !urlString.isEmpty())
			return stringToUrl(urlString);
		else 
			return null;
		
	}
	
	public URL getISEEDichiarazioneEndpointURL() {
		String urlString = this.getISEEURL();
		return urlString!=null ? stringToUrl(urlString) : null;
	}
	
	public URL getAnagrafeWSDLLocationURL() {
		String urlString = getAnagrafeWSDLLocation();
		return urlString!=null ? stringToUrl(urlString) : null;
	}
	
	public String getISEEURL(){
		return getGlobalParameter(DataModelCostanti.AmParameterKey.WS_ISEE_URL);
	}
	
	public String getAnagrafeWSDLLocation(){
		return getGlobalParameter(DataModelCostanti.AmParameterKey.WS_RICERCA_URL);
		//return getGlobalParameter(DataModelCostanti.AmParameterKey.KEY_URL_SSOCIALI);
	}

	public CredenzialiWS getCredenzialiWS() {
		return getCredenzialiWS(DataModelCostanti.AmParameterKey.WS_RICERCA_USR,DataModelCostanti.AmParameterKey.WS_RICERCA_PWD);
	}
	
	protected CredenzialiWS getAtlanteLogin() {
		return getCredenzialiWS(DataModelCostanti.AmParameterKey.WS_ATLANTE_USR, DataModelCostanti.AmParameterKey.WS_ATLANTE_PWD);
	}
	
	protected Date parseddMMyyyy(String sdata, String messaggio){
		Date data = null;
		try{
			data = !StringUtils.isBlank(sdata) ? ddMMyyyy.parse(sdata) : null;
		}catch(Exception e){
			logger.warn(e.getMessage()+" ["+messaggio+ " - "+ data +"]", e);
		}
		return data;
	}
}
