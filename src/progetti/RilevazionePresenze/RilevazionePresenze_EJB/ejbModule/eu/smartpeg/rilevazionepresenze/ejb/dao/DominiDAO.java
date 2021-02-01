package eu.smartpeg.rilevazionepresenze.ejb.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.persistence.TypedQuery;

import eu.smartpeg.rilevazionepresenze.data.model.Motivazione;

@Named
public class DominiDAO extends RilevazionePresenzeBaseDao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * metodo per recuperare le motivazioni e riempire il menù a tendina
	 */
	public List<Motivazione> findAllMotivazioni() {

		try {

			TypedQuery<Motivazione> q = em.createNamedQuery("Motivazione.findAll", Motivazione.class);
			return q.getResultList();

		} catch (Exception e) {
			logger.error("Errore getStrutture " + e.getMessage(), e);
		}
		return null;
	}
}