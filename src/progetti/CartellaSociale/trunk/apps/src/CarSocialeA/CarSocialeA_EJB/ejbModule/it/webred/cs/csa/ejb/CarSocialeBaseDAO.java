package it.webred.cs.csa.ejb;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

public class CarSocialeBaseDAO implements Serializable {
		
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="CarSocialeA_DataModel")
	protected EntityManager em;
	
	protected static Logger logger = Logger.getLogger("carsociale.log");

}
