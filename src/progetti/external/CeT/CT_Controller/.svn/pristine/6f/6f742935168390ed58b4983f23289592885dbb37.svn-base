package it.webred.ct.rulengine.web.bean.status;

import javax.ejb.EJB;

import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ct.data.access.basic.scuole.ScuoleService;
import it.webred.ct.rulengine.scheduler.bean.ProcessService;
import it.webred.ct.rulengine.service.bean.ProcessMonitorService;
import it.webred.ct.rulengine.service.bean.RecuperaComandoService;
import it.webred.ct.rulengine.service.bean.TracciaStatiService;
import it.webred.ct.rulengine.service.bean.VerificaInitProcessService;
import it.webred.ct.rulengine.web.bean.ControllerBaseBean;

public class StatusBaseBean extends ControllerBaseBean{
	
	protected RecuperaComandoService recuperaComandoService = (RecuperaComandoService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "RecuperaComandoServiceBean");
	
	protected TracciaStatiService tracciaStatiService = (TracciaStatiService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "TracciaStatiServiceBean");
	
	protected ProcessMonitorService processMonitorService = (ProcessMonitorService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "ProcessMonitorServiceBean");
	
}
