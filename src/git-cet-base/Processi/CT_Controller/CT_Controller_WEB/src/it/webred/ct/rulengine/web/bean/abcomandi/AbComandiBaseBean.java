package it.webred.ct.rulengine.web.bean.abcomandi;

import it.webred.ct.rulengine.service.bean.AbComandiService;
import it.webred.ct.rulengine.service.bean.MainControllerService;
import it.webred.ct.rulengine.web.bean.ControllerBaseBean;

public class AbComandiBaseBean extends ControllerBaseBean{
	
	protected MainControllerService mainControllerService = (MainControllerService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "MainControllerServiceBean");
	
	protected AbComandiService abComandiService = (AbComandiService) getEjb(
			"CT_Controller", "CT_Controller_EJB", "AbComandiServiceBean");
	
}
