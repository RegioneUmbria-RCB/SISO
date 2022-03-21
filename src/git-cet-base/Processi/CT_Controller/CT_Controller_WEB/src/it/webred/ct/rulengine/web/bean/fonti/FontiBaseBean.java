package it.webred.ct.rulengine.web.bean.fonti;

import java.util.Properties;

import javax.ejb.EJB;

import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ct.data.access.basic.fonti.FontiService;
import it.webred.ct.data.access.basic.processi.ProcessiService;
import it.webred.ct.rulengine.service.bean.EventService;
import it.webred.ct.rulengine.service.bean.MainControllerService;
import it.webred.ct.rulengine.service.bean.ProcessMonitorService;
import it.webred.ct.rulengine.service.bean.RecuperaComandoService;
import it.webred.ct.rulengine.service.bean.TracciaStatiService;
import it.webred.ct.rulengine.web.bean.ControllerBaseBean;

public class FontiBaseBean extends ControllerBaseBean{
	
	protected TracciaStatiService tracciaStatiService = (TracciaStatiService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "TracciaStatiServiceBean");
	
	protected MainControllerService mainControllerService = (MainControllerService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "MainControllerServiceBean");
	
	protected FontiService fontiService = (FontiService) getEjb(
			"CT_Service", "CT_Service_Data_Access", "FontiServiceBean");
	
	protected ProcessiService processiService = (ProcessiService) getEjb(
			"CT_Service", "CT_Service_Data_Access", "ProcessiServiceBean");
	
	protected ProcessMonitorService processMonitorService = (ProcessMonitorService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "ProcessMonitorServiceBean");
	
	protected RecuperaComandoService recuperaComandoService = (RecuperaComandoService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "RecuperaComandoServiceBean");

}
