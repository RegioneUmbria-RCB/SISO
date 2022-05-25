package it.webred.cs.csa.ejb.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.data.model.sal.CsPaiSAL;
import it.webred.cs.data.model.sal.CsTbPaiSal;

@Named
public class PaiSalDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public List<CsTbPaiSal> findAll() throws Exception{
		Query query = em.createQuery("SELECT d FROM CsTbPaiSal d");
		return (List<CsTbPaiSal>) query.getResultList();
	}
	
	public CsPaiSAL saveSAL(CsPaiSAL sal) {
		CsPaiSAL toReturn = new CsPaiSAL();
		try {
			if (sal.getId() == null) {
				em.persist(sal);
				toReturn = sal;
			} else {
				toReturn = em.merge(sal);
			}
			
//			toReturn = em.merge(sal);
//			em.flush();

		} catch (Exception e) {
			logger.error("salvaSal " + e.getMessage(), e);
			throw new CarSocialeServiceException(e);
		}
	
		return toReturn;
	}
	
	public CsPaiSAL findSalByDiarioPaiId(Long diarioPaiId) {
		try {
			Query query = em.createQuery("SELECT d FROM CsPaiSAL d WHERE d.diarioPaiId = ?1");
			query.setParameter(1, diarioPaiId);
			
			List result = query.getResultList();
			
			if(!result.isEmpty()){
				return (CsPaiSAL) result.get(0);
			}
			
		}catch(Exception e) {
			throw new CarSocialeServiceException(e);
		}
		
		return null;
	}
	
	public CsPaiSAL findById(Long salId ){
		try {
			return em.find(CsPaiSAL.class,  salId);
		}catch(Exception e) {
			throw new CarSocialeServiceException(e);
		}
	}
}
