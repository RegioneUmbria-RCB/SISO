package it.webred.ct.service.gestioneDBDoc.data.access.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

public class GestioneDBDocBaseDAO 	implements Serializable {
		
		@PersistenceContext(unitName="GestioneDBDoc_Data_Model")
		protected EntityManager manager;

		protected Logger logger = Logger.getLogger(GestioneDBDocBaseDAO.class.getName());
		
}
