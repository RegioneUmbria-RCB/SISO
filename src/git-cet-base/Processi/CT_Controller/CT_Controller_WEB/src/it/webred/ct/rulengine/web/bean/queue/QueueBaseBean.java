package it.webred.ct.rulengine.web.bean.queue;

import javax.ejb.EJB;

import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ct.rulengine.scheduler.bean.ProcessService;
import it.webred.ct.rulengine.service.bean.MainControllerService;
import it.webred.ct.rulengine.service.bean.ProcessMonitorService;
import it.webred.ct.rulengine.service.bean.QuartzService;
import it.webred.ct.rulengine.service.bean.QueueService;
import it.webred.ct.rulengine.service.bean.RecuperaComandoService;
import it.webred.ct.rulengine.service.bean.SchedulerTimeService;
import it.webred.ct.rulengine.service.bean.VerificaInitProcessService;
import it.webred.ct.rulengine.web.bean.ControllerBaseBean;

public class QueueBaseBean extends ControllerBaseBean{
	
	protected QueueService queueService = (QueueService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "QueueServiceBean");
	
}
