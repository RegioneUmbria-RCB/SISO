package it.webred.cs.base;


import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

public class CsBaseDAO extends CsBase implements Serializable {
		
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="CarSocialeA_DataModel")
	protected EntityManager em;
	

}
