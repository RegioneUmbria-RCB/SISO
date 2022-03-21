package it.umbriadigitale.argo.ejb;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

public class ArgoBaseDAO implements Serializable {
		
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="Argo_DataModel")
	protected EntityManager em;
	
	protected static Logger logger = Logger.getLogger("argo.log");

}
