package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.affido.CsPaiAffido;
import it.webred.cs.data.model.affido.CsTbPaiAffido;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

@Named
public class PaiAffidoDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*AFFIDO*/
	public CsPaiAffido findAffidoById( Long affidoId ) throws Exception	{
		return em.find(CsPaiAffido.class,  affidoId);
	}
	
	public CsPaiAffido saveAffido(CsPaiAffido affido) throws Exception {
		CsPaiAffido toReturn = em.merge(affido);
		em.flush();
		return toReturn;
	}
	
	public void deleteAffido(CsPaiAffido affido) throws Exception{
		em.remove(affido);
		em.flush();
	}
	
	public List<CsTbPaiAffido> findAll() throws Exception{
		Query query = em.createQuery("SELECT d FROM CsTbPaiAffido d");
		return (List<CsTbPaiAffido>) query.getResultList();
	}
	
	public CsPaiAffido findAffidoByDiarioPaiId(Long diarioPaiId) throws Exception {
		Query query = em.createQuery("SELECT d FROM CsPaiAffido d WHERE d.diarioPaiId = ?1");
		query.setParameter(1, diarioPaiId);
		
		List result = query.getResultList();
		
		if(!result.isEmpty()){
			return (CsPaiAffido) result.get(0);
		}
		
		return null;
	}
	
	public CsAComponente findComponenteById(Long componenteId) throws Exception {
		return em.find(CsAComponente.class,  componenteId);
	}
	
	
}
