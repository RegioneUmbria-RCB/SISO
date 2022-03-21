package it.webred.ct.rulengine.service;

import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ct.rulengine.service.bean.ProcessMonitorService;
import it.webred.ct.rulengine.service.bean.RecuperaComandoService;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

public class ControllerBaseService implements Serializable {

	
	protected ComuneService comuneService = (ComuneService) getEjb(
			"CT_Service", "CT_Config_Manager", "ComuneServiceBean"); 
	
	protected ParameterService parameterService = (ParameterService) getEjb(
			"CT_Service", "CT_Config_Manager", "ParameterBaseService"); 
	
	@EJB
	protected ProcessMonitorService processMonitorService;
	
	@EJB
	protected RecuperaComandoService recuperaComandoService;
	
	protected static Logger logger = Logger.getLogger(ControllerBaseService.class.getName());

	public Object getEjb(String ear, String module, String ejbName){
		Context cont;
		try {
			cont = new InitialContext();
			return cont.lookup("java:global/" + ear + "/" + module + "/" + ejbName);
		} catch (NamingException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
}
