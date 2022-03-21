package it.webred.AMProfiler.parameters.instance;

import it.webred.AMProfiler.parameters.AmProfilerBaseBean;
import it.webred.ct.config.parameters.application.ApplicationService;
import it.webred.ct.config.parameters.comune.ComuneService;

import javax.ejb.EJB;

public class InstanceBaseBean extends AmProfilerBaseBean {

	protected ApplicationService applicationService = (ApplicationService) getEjb(
			"CT_Service", "CT_Config_Manager", "ApplicationServiceBean");

	protected ComuneService comuneService = (ComuneService) getEjb(
			"CT_Service", "CT_Config_Manager", "ComuneServiceBean");

}
