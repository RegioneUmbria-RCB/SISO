package it.webred.cs.base;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.utils.GenericTuples;

import java.net.MalformedURLException;
import java.net.URL;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;
import it.webred.utils.GenericTuples.T2;


public class CsBase {
	
	protected static Logger logger = Logger.getLogger("carsociale.log");

	
	private URL stringToUrl(String urlString) {
		URL url;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			url=null;
		}
		return url;
	}
	
	
	protected URL getAnagRegWebServiceWSDLLocation(){
		String urlString = this.getConfigParameter("anagreg.url");
		if(urlString!=null && !urlString.isEmpty())
			return stringToUrl(urlString);
		else 
			return null;
		
	}

	
	
	protected URL getSisoWSDLLocation(){
		return getAnagRegWebServiceWSDLLocation();

	}

	protected GenericTuples.T2<String, String> getAtlanteLogin() {
		String user = this.getConfigParameter("atlantews.username");
		String password = this.getConfigParameter("atlantews.password");
		GenericTuples.T2<String, String>  login = new T2<String, String>(user, password);
		return login;
	}

	
	
	private String getConfigParameter(String paramName) {
		ParameterService paramService = (ParameterService) getEjb("CT_Service","CT_Config_Manager" , "ParameterBaseService");
	    ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setKey(paramName);
		criteria.setComune(null);
		criteria.setSection(null);
		AmKeyValueExt amKey = paramService.getAmKeyValueExt(criteria);
		if(amKey != null && amKey.getValueConf()!=null) {
			return amKey.getValueConf();
		}else
			logger.warn("RICERCA IN ANAGRAFE SANITARIA: Parametro 'anagreg.url' non definito");
		
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
	

}
