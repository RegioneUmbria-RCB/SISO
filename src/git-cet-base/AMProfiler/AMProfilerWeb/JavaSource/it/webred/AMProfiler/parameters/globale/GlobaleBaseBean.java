package it.webred.AMProfiler.parameters.globale;

import it.webred.AMProfiler.parameters.AmProfilerBaseBean;
import it.webred.ct.config.parameters.ParameterService;

import javax.ejb.EJB;

public class GlobaleBaseBean extends AmProfilerBaseBean{

	protected ParameterService amprofilerService = (ParameterService) getEjb("CT_Service", "CT_Config_Manager", "ParameterBaseService");
	
}
