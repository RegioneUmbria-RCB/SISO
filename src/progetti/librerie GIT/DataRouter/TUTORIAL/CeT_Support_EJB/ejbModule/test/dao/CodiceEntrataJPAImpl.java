package test.dao;

/**
 * Classe di test per verifcare il funzionamento del datasource router
 * 
 * 
 * Francesco Azzola - 2010
 * 
 * */

import it.webred.ct.support.model.CodiceEntrata;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


public class CodiceEntrataJPAImpl implements CodiceEntrataDAO {
	
	@PersistenceContext(unitName="myPers")
	private EntityManager manager;

	public List<CodiceEntrata> getListaCodici() {
		System.out.println("Lista codici");
		
		Query q = manager.createQuery("SELECT c FROM CodiceEntrata c");
		q.setFirstResult(1);
		q.setMaxResults(20);
		
		List<CodiceEntrata> result = q.getResultList();
		
		return result;		
	}

}
 