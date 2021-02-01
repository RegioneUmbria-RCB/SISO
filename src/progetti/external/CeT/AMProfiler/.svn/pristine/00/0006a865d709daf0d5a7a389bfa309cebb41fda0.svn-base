package it.webred.AMProfiler.parameters.comune;

import it.webred.AMProfiler.parameters.AmProfilerBaseBean;
import it.webred.ct.config.parameters.application.ApplicationService;
import it.webred.ct.config.parameters.comune.ComuneService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ComuneBaseBean extends AmProfilerBaseBean{

	protected ComuneService comuneService = (ComuneService) getEjb("CT_Service", "CT_Config_Manager", "ComuneServiceBean");

}
