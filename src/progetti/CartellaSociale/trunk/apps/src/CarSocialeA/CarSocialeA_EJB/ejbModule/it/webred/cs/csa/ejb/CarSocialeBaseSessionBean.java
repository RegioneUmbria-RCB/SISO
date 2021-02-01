package it.webred.cs.csa.ejb;

import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

public class CarSocialeBaseSessionBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	protected static Logger logger = Logger.getLogger("carsociale.log");
	
	protected ParameterService paramService = (ParameterService) getEjb("CT_Service", "CT_Config_Manager", "ParameterBaseService");
	//SISo-1127
	protected  LuoghiService luoghiService = (LuoghiService) getEjb("CT_Service", "CT_Config_Manager", "LuoghiServiceBean");
	
	@EJB
	protected AccessTableAlertSessionBeanRemote alertSessionBean;
	
	protected SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
	
	protected Object getEjb(String ear, String module, String ejbName) {
		Context cont;
		try {
			cont = new InitialContext();
			return cont.lookup("java:global/" + ear + "/" + module + "/" + ejbName);
		} catch (NamingException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	protected String getGlobalParameter(String paramName) {
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
	
	protected void copiaCsTBaseObject (CeTBaseObject in, CeTBaseObject out){
		out.setEnteId(in.getEnteId());
		out.setSessionId(in.getSessionId());
		out.setUserId(in.getUserId());
	}
	
	protected URL stringToUrl(String urlString) {
		URL url;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			url = null;
		}
		return url;
	}
	//SISO-1127 Inizio
	private  AmTabNazioni getNazioneByCodiceGenerico(String codice) {
		AmTabNazioni nazione = null;
		if(codice!=null && !codice.isEmpty()){
			try{
				nazione = luoghiService.getNazioneByCodiceGenerico(codice);
			}catch(Exception e){}
		}
		
		return nazione;
	}

	protected  String getCittadinanzaByCodice(String codice){
		String cittadinanza = null;
		if("100".equals(codice)){
			cittadinanza = "ITALIANA";
		}else{
			AmTabNazioni nazione = getNazioneByCodiceGenerico(codice);
			if(nazione!=null)
				cittadinanza = nazione.getNazionalita();
		}
		
		return cittadinanza;
	}
	//	SISO-1127 Fine
}
