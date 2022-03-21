package it.webred.AMProfiler.parameters.comune;

import it.webred.AMProfiler.parameters.AmProfilerBaseBean;
import it.webred.ct.config.parameters.comune.ComuneService;

public class ComuneBaseBean extends AmProfilerBaseBean{

	protected ComuneService comuneService = (ComuneService) getEjb("CT_Service", "CT_Config_Manager", "ComuneServiceBean");

}
