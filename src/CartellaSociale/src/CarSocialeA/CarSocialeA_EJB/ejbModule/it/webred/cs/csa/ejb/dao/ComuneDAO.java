package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

@Named
public class ComuneDAO extends CarSocialeBaseDAO implements Serializable {
		
	private static final long serialVersionUID = 1L;
		
	@SuppressWarnings("unchecked")
	public List<String> getTableData(){
		
		try{
			
			Query q = em.createNamedQuery("CarSocialeAComune.getData");
			return q.getResultList();
			
		} catch (Exception e) {	
			logger.error("Errore getTableData "+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
