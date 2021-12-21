package it.umbriadigitale.soclav.service.impl;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import it.umbriadigitale.soclav.model.RdCKeyValueExt;
import it.umbriadigitale.soclav.service.interfaccia.IRdCKeyValueExtService;

public class BaseServiceImpl {
	
	public final Logger logger = Logger.getLogger("SocLav");
	
	@Autowired 
	IRdCKeyValueExtService  keyValueExtService;
	
	protected String getGlobalParameter(String paramName) {
		 
		RdCKeyValueExt amKey = this.keyValueExtService.getKeyValueExtparam(paramName);
		if (amKey != null && amKey.getKeyValue() != null && !amKey.getKeyValue().trim().isEmpty()) {
			return amKey.getKeyValue();
		} else
			logger.warn("Parametro '" + paramName + "' non definito");

		return null;
	}
}
