package it.webred.cs.csa.ejb.domini;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.domini.AccessTableDominiAmKeySessionBeanRemote;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.commons.lang.StringUtils;

@Singleton
@Startup
public class AccessTableDominiAmKeySessionBean extends CarSocialeBaseSessionBean  implements AccessTableDominiAmKeySessionBeanRemote {
	
	private static final long serialVersionUID = 1L;
	
	
	private HashMap<String, String> cache;
	
	@PostConstruct
	@Schedule(hour="0", persistent=false) //ricarica i domini ogni notte a mezzanotte
	public void initDominiSingleton(){
		logger.debug("INIZIO caricamento AccessTableDominiAmKeySessionBean");
		ParameterService paramService = (ParameterService) getEjb("CT_Service", "CT_Config_Manager", "ParameterBaseService");
		cache = new HashMap<String, String>();
	    cache = paramService.getListaParametriGlobali("smartwelfare.");
	    logger.debug("FINE caricamento AccessTableDominiAmKeySessionBean");
	}

	@Override
	@Lock(LockType.READ)
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public String findByKey(String chiave){
		String toReturn = null;
		if(!StringUtils.isBlank(chiave)){
			String value = cache.get(chiave);
			return value;
		}
		
		return toReturn;
	}
}
