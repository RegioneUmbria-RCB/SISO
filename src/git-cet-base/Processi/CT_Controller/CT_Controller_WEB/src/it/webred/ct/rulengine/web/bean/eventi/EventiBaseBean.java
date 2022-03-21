package it.webred.ct.rulengine.web.bean.eventi;

import javax.ejb.EJB;

import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ct.config.parameters.fonte.FonteService;
import it.webred.ct.rulengine.service.bean.EventLaunchService;
import it.webred.ct.rulengine.service.bean.EventService;
import it.webred.ct.rulengine.service.bean.MainControllerService;
import it.webred.ct.rulengine.service.bean.ProcessMonitorService;
import it.webred.ct.rulengine.service.bean.RecuperaComandoService;
import it.webred.ct.rulengine.web.bean.ControllerBaseBean;

public class EventiBaseBean extends ControllerBaseBean {
	
	protected MainControllerService mainControllerService = (MainControllerService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "MainControllerServiceBean");
	
	protected RecuperaComandoService recuperaComandoService = (RecuperaComandoService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "RecuperaComandoServiceBean");
	
	protected ProcessMonitorService processMonitorService = (ProcessMonitorService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "ProcessMonitorServiceBean");
	
	protected EventLaunchService eventLaunchService = (EventLaunchService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "EventLaunchServiceBean");
	
	protected EventService eventService = (EventService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "EventServiceBean");
	
	protected FonteService fonteService = (FonteService) getEjb(
			"CT_Service", "CT_Config_Manager", "FonteServiceBean");
}
