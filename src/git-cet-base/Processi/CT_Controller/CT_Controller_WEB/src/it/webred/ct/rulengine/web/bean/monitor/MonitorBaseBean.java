package it.webred.ct.rulengine.web.bean.monitor;

import javax.ejb.EJB;

import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ct.rulengine.scheduler.bean.ProcessService;
import it.webred.ct.rulengine.service.bean.ProcessMonitorService;
import it.webred.ct.rulengine.service.bean.RecuperaComandoService;
import it.webred.ct.rulengine.service.bean.TracciaStatiService;
import it.webred.ct.rulengine.service.bean.VerificaInitProcessService;
import it.webred.ct.rulengine.web.bean.ControllerBaseBean;

public class MonitorBaseBean extends ControllerBaseBean{
	
	protected RecuperaComandoService recuperaComandoService = (RecuperaComandoService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "RecuperaComandoServiceBean");
	
	protected TracciaStatiService tracciaStatiService = (TracciaStatiService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "TracciaStatiServiceBean");
	
	protected ProcessService processService = (ProcessService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "ProcessServiceBean");
	
	protected ProcessMonitorService processMonitorService = (ProcessMonitorService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "ProcessMonitorServiceBean");
	
	protected VerificaInitProcessService verificaService = (VerificaInitProcessService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "VerificaInitProcessServiceBean");
	
	protected ParameterService parameterService = (ParameterService) getEjb(
			"CT_Service", "CT_Config_Manager", "ParameterBaseService");
	
}
