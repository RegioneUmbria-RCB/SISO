package it.webred.AMProfiler.beans;

import it.webred.ct.config.parameters.ParameterService;

import java.io.Serializable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class VersioneBean  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String versione;
	
	public String getVersione() {
		
		try {
			Context cont = new InitialContext();

			ParameterService parameterService = (ParameterService) cont
					.lookup("java:global/CT_Service/CT_Config_Manager/ParameterBaseService");

			versione = parameterService.getVersionePiattaforma();
			
			versione = versione != null ? versione : "";

		} catch (Exception e) {
			versione = "";
		}

		return versione;

	}
	
	public String getVersioneSmartWelfare() {
		
		try {
			Context cont = new InitialContext();

			ParameterService parameterService = (ParameterService) cont
					.lookup("java:global/CT_Service/CT_Config_Manager/ParameterBaseService");

			versione = parameterService.getVersioneSmartWelfare();
			
			versione = versione != null ? versione : "";

		} catch (Exception e) {
			versione = "";
		}

		return versione;

	}
}
