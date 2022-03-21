package it.webred.ct.rulengine.web.bean.scheduler;

import javax.ejb.EJB;

import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ct.rulengine.scheduler.bean.ProcessService;
import it.webred.ct.rulengine.service.bean.MainControllerService;
import it.webred.ct.rulengine.service.bean.ProcessMonitorService;
import it.webred.ct.rulengine.service.bean.QuartzService;
import it.webred.ct.rulengine.service.bean.RecuperaComandoService;
import it.webred.ct.rulengine.service.bean.SchedulerTimeService;
import it.webred.ct.rulengine.service.bean.TracciaStatiService;
import it.webred.ct.rulengine.service.bean.VerificaInitProcessService;
import it.webred.ct.rulengine.web.bean.ControllerBaseBean;

public class SchedulerBaseBean extends ControllerBaseBean{
	
	protected MainControllerService mainControllerService = (MainControllerService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "MainControllerServiceBean");
	
	protected RecuperaComandoService recuperaComandoService = (RecuperaComandoService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "RecuperaComandoServiceBean");
	
	protected SchedulerTimeService schedulerTimeService = (SchedulerTimeService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "SchedulerTimeServiceBean");
	
	protected ProcessMonitorService processMonitorService = (ProcessMonitorService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "ProcessMonitorServiceBean");
	
	protected TracciaStatiService tracciaStatiService = (TracciaStatiService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "TracciaStatiServiceBean");
	
	protected QuartzService quartzService = (QuartzService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "QuartzServiceBean");
	
	
}
