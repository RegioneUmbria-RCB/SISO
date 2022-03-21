package it.webred.amprofiler.ejb;

import it.webred.ct.config.parameters.ParameterService;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;


public class AmProfilerBaseService {

	protected Logger logger = Logger.getLogger("am_log");
	
	@PersistenceContext(unitName = "AmProfilerDataModel")
	protected EntityManager em;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Config_Manager/ParameterBaseService")
	protected ParameterService parameterService;

	/**
	 * Default constructor.
	 */
	public AmProfilerBaseService() {
	}

}
