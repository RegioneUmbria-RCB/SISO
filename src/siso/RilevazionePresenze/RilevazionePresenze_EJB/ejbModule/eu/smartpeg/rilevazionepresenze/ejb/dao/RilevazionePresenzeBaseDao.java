package eu.smartpeg.rilevazionepresenze.ejb.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

public class RilevazionePresenzeBaseDao implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="RilevazionePresenze_DataModel")
	protected EntityManager em;

	protected static Logger logger = Logger.getLogger("RilevazionePresenze_DAO.log");	
}


