package it.webred.ss.dao;

import it.webred.ss.data.model.SsTest;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Named
public class TestDAO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName="SegretariatoSoc_DataModel")
	protected EntityManager em;
		
	@SuppressWarnings("unchecked")
	public List<SsTest> getTestByDescrizione(String descrizione) {
			
		System.out.println("______*_*_* SONO DENTRO IL DAO");
		//qui andranno effettuate tutte le query o chiamate di basso livello al DB
		
		Query q = em.createNamedQuery("SsTest.findTestByDescrizione").setParameter("descrizione", descrizione);
		//i parametri nell'orm sono definiti da ":"
		return q.getResultList();

	}
	
	public void saveTest(SsTest test){
		
		//se l'id è null, verrà assegnato un valore numerico usando la sequence mappata nella classe SsTest 
		em.persist(test);
		
	}
}
