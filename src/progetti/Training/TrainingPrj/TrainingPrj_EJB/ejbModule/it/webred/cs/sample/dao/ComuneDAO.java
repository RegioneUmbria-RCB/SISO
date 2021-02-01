package it.webred.cs.sample.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Named
public class ComuneDAO implements Serializable {
		
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="TrainingPrj_DataModel")
	protected EntityManager em;
		
	public List<String> getTableData(){
		
		try{
			
			Query q = em.createNamedQuery("TrainingPrjComune.getData");
			return q.getResultList();
			
		} catch (Exception e) {	
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
