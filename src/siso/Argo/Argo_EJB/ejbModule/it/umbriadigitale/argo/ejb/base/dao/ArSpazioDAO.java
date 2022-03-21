package it.umbriadigitale.argo.ejb.base.dao;


import it.umbriadigitale.argo.data.ArSpazio;
import it.umbriadigitale.argo.ejb.ArgoBaseDAO;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

/**
 * @author alessandro.feriani
 *
 */
@Named
public class ArSpazioDAO extends ArgoBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public void createArSpazio(ArSpazio newSpazio) throws Exception {
			em.persist(newSpazio);
	}

	public  ArSpazio findArSpazioById(long id) throws Exception {
		ArSpazio spazio= em.find(ArSpazio.class,  id);
			return spazio;
	}
	
	public List<ArSpazio>  findArSpazioByBELFIORE(String belfiore) throws Exception {
		 Query q = em.createNamedQuery("ArSpazio.findArSpazioByBELFIORE")
					.setParameter("belfiore", belfiore);
		return  q.getResultList();
	}
	
	

}
