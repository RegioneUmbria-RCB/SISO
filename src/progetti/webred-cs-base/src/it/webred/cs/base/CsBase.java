package it.webred.cs.base;

import it.webred.cs.data.DataModelCostanti;
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

	
	
	protected String getGlobalParameter(String paramName) {
		ParameterService paramService = (ParameterService) getEjb("CT_Service","CT_Config_Manager" , "ParameterBaseService");
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
	

	public boolean isAnagrafeSanitariaUmbriaAbilitata(){
		List<String> s = getTipoRicercaAbilitata();
		return s.contains(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA);
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
	
	public URL getAnagrafeWSDLLocationURL() {
		String urlString = getAnagrafeWSDLLocation();
		return urlString!=null ? stringToUrl(urlString) : null;
	}
	
	public String getAnagrafeWSDLLocation(){
		return getGlobalParameter(DataModelCostanti.AmParameterKey.WS_RICERCA_URL);
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
