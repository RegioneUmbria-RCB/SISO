package it.webred.ct.data.access.basic.docfa;

import it.webred.ct.data.access.basic.CTServiceBaseBean;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class DocfaBaseService extends CTServiceBaseBean implements Serializable {

	
	protected EntityManager manager_diogene;
	
}
