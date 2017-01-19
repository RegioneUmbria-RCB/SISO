package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.data.model.CsCComunita;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

@Named
public class ComunitaDAO extends CarSocialeBaseDAO implements Serializable {
		
	private static final long serialVersionUID = 1L;
		
	@SuppressWarnings("unchecked")
	public List<CsCComunita> findComunitaByDescTipo(String desc){
		
		try{
			
			Query q = em.createNamedQuery("CsCComunita.findByDescTipo");
			q.setParameter("descrizione", desc);
			return q.getResultList();
			
		} catch (Throwable e) {	
			logger.error("Errore findComunitaByDescTipo "+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
