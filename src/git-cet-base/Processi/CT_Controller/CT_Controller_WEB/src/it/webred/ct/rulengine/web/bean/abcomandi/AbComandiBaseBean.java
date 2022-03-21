package it.webred.ct.rulengine.web.bean.abcomandi;

import javax.ejb.EJB;

import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ct.rulengine.scheduler.bean.ProcessService;
import it.webred.ct.rulengine.service.bean.AbComandiService;
import it.webred.ct.rulengine.service.bean.MainControllerService;
import it.webred.ct.rulengine.service.bean.ProcessMonitorService;
import it.webred.ct.rulengine.service.bean.RecuperaComandoService;
import it.webred.ct.rulengine.service.bean.TracciaStatiService;
import it.webred.ct.rulengine.service.bean.VerificaInitProcessService;
import it.webred.ct.rulengine.web.bean.ControllerBaseBean;

public class AbComandiBaseBean extends ControllerBaseBean{
	
	protected MainControllerService mainControllerService = (MainControllerService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "MainControllerServiceBean");
	
	protected AbComandiService abComandiService = (AbComandiService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "AbComandiServiceBean");
	
}
