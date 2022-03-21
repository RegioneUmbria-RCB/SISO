package it.webred.ct.rulengine.web.bean.utilities;

import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ct.controller.ejbclient.utilities.ControllerUtilitiesService;
import it.webred.ct.rulengine.service.bean.UtilService;
import it.webred.ct.rulengine.web.bean.ControllerBaseBean;

import javax.ejb.EJB;

public class UtilitiesBaseBean extends ControllerBaseBean{
	
	protected ControllerUtilitiesService controllerUtService = (ControllerUtilitiesService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "ControllerUtilitiesServiceBean");
	
	protected UtilService utilService = (UtilService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "UtilServiceBean");
	
}
