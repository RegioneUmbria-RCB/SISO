package it.webred.ct.data.access.basic.cnc.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

public class CNCBaseDAO implements Serializable {

	@PersistenceContext(unitName="CT_Diogene")
	protected EntityManager manager;

	protected static Logger logger = Logger.getLogger("ctservice.log");

}
