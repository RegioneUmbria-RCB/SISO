package it.umbriadigitale.argo.ejb.cs.dao.impl;

import java.util.List;

import javax.persistence.Query;
import it.umbriadigitale.argo.data.cs.data.ArCsSoggettoCs;
import it.umbriadigitale.argo.ejb.cs.dao.ArCsSoggettoCsDAO;
import it.umbriadigitale.argo.ejb.ArgoBaseDAO;

/**
 * 
 * @author andrea.niccolini
 *
 */
public class ArCsSoggettoCsDAOImpl extends ArgoBaseDAO implements ArCsSoggettoCsDAO {

	@Override
	public List<ArCsSoggettoCs> getSoggetti() {
				
		Query q = em.createNamedQuery("ArCsSoggettoCs.findArCsSoggettoCs");
		
		return  q.getResultList();
	}

	@Override
	public void save(ArCsSoggettoCs soggetto) {
		// TODO Auto-generated method stub

	}

}
