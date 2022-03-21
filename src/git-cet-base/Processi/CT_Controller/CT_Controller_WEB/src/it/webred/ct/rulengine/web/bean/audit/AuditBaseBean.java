package it.webred.ct.rulengine.web.bean.audit;

import it.webred.ct.config.audit.AuditService;
import it.webred.ct.config.parameters.fonte.FonteService;
import it.webred.ct.rulengine.web.bean.ControllerBaseBean;

public class AuditBaseBean extends ControllerBaseBean{
	
	protected AuditService auditService = (AuditService) getEjb(
			"CT_Service", "CT_Config_Manager", "AuditServiceBean");
	
	protected FonteService fonteService = (FonteService) getEjb(
			"CT_Service", "CT_Config_Manager", "FonteServiceBean");
	
}
