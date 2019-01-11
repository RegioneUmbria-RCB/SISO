package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsAIndirizzo;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

@Named
public class IndirizzoDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public CsAIndirizzo getIndirizzoId(long id) {
		CsAIndirizzo indirizzo = em.find(CsAIndirizzo.class, id);
		return indirizzo;
	}
	
	@SuppressWarnings("unchecked")
	public List<CsAIndirizzo> getIndirizziBySoggetto(long idSoggetto) {

		Query q = em.createNamedQuery("CsAIndirizzo.findBySoggetto")
				.setParameter("idSoggetto", idSoggetto);
		return q.getResultList();

	}
	
	public void saveIndirizzo(CsAIndirizzo indirizzo) {

		em.persist(indirizzo);

	}
	
	public void updateIndirizzo(CsAIndirizzo indirizzo) {

		em.merge(indirizzo);

	}

	public CsAAnaIndirizzo saveAnaIndirizzo(CsAAnaIndirizzo indirizzo) {

		em.persist(indirizzo);
		return indirizzo;

	}
	
	public void updateAnaIndirizzo(CsAAnaIndirizzo indirizzo) {

		em.merge(indirizzo);

	}
	
	public void eliminaIndirizzoBySoggetto(Long soggettoId) {
		
		Query q = em.createNamedQuery("CsAIndirizzo.eliminaBySoggettoId");
		q.setParameter("id", soggettoId);
		q.executeUpdate();
		
	}
	
	public void eliminaAnaIndirizzoById(Long id) {
		
		Query q = em.createNamedQuery("CsAAnaIndirizzo.eliminaById");
		q.setParameter("id", id);
		q.executeUpdate();
		
	}
	
	public CsAAnaIndirizzo getAnaIndirizzoById(Long id) {
		
		CsAAnaIndirizzo indirizzo = em.find(CsAAnaIndirizzo.class, id);
		return indirizzo;
		
	}
}
