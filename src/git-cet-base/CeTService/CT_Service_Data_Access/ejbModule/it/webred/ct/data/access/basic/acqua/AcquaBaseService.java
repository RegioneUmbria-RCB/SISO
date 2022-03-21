package it.webred.ct.data.access.basic.acqua;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

public class AcquaBaseService implements Serializable{
		
	
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "CT_Diogene")
	protected EntityManager manager_diogene;

	protected Logger logger = Logger.getLogger("ctservice.log");
	
}
