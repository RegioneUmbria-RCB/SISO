package eu.smartpeg.rilevazionepresenze.ejb.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import eu.smartpeg.rilevazionepresenze.data.model.Area;
import eu.smartpeg.rilevazionepresenze.data.model.Struttura;

@Named
public class StruttureDAO extends RilevazionePresenzeBaseDao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<Struttura> getStrutture() {

		try {

			TypedQuery<Struttura> q = em.createNamedQuery("Struttura.findAll", Struttura.class);
			return q.getResultList();

		} catch (Exception e) {
			logger.error("Errore getStrutture " + e.getMessage(), e);
		}
		return null;
	}

	public Struttura saveStruttura(Struttura struttura) {
		try {
			Struttura toReturn = new Struttura();
			toReturn = em.merge(struttura);
			em.flush();

			return toReturn;
		} catch (Exception e) {
			logger.error("Errore saveStruttura " + e.getMessage(), e);
		}
		return null;
	}

	public void eliminaStruttura(Struttura struttura) throws Exception {
		Query q = em.createNamedQuery("Struttura.deleteById");
		q.setParameter("id", struttura.getId());
		q.executeUpdate();
	}

	public void eliminaArea(Struttura struttura, Area area) throws Exception {
//		try {

			struttura.getAreas().remove(area);
			em.merge(struttura);
			em.flush();

//		} catch (Exception e) {
//			logger.error("Errore eliminaArea " + e.getMessage(), e);
//			throw e;
//		}
	}

	public List<Area> getAree() {
		try {

			TypedQuery<Area> q = em.createNamedQuery("Area.findAll", Area.class);
			return q.getResultList();

		} catch (Exception e) {
			logger.error("Errore getAree " + e.getMessage(), e);
		}
		return null;
	}
		
		
	public Struttura findStrutturaById(Long idStruttura) {

		try {

			TypedQuery<Struttura> q = em.createNamedQuery("Struttura.findStrutturaById", Struttura.class);
			q.setParameter("id", idStruttura);
			return q.getSingleResult();

		} catch (Exception e) {
			logger.error("Errore getStrutturaID " + e.getMessage(), e);
		}
		return null;
	}
}