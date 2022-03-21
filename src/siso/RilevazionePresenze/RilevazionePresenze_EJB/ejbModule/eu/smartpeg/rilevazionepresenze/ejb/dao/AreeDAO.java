package eu.smartpeg.rilevazionepresenze.ejb.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import eu.smartpeg.rilevazionepresenze.data.model.Area;

@Named
public class AreeDAO extends RilevazionePresenzeBaseDao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<Area> getAree() {

		try {
			TypedQuery<Area> q = em.createNamedQuery("Area.findAll", Area.class);
			return q.getResultList();

		} catch (Exception e) {
			logger.error("Errore getAree " + e.getMessage(), e);
		}
		return null;
	}

	public Area saveArea(Area area) {
		try {
			Area toReturn = new Area();
			toReturn = em.merge(area);
			em.flush();

			return toReturn;
		} catch (Exception e) {
			logger.error("Errore saveArea " + e.getMessage(), e);
		}
		return null;
	}

	public void eliminaArea(Area area) throws Exception {
//		try {
			Query q = em.createNamedQuery("Area.deleteById");
			q.setParameter("id", area.getId());
			q.executeUpdate();
//		} catch (Exception e) {
//			logger.error("Errore eliminaArea " + e.getMessage(), e);
//		}
	}

	public Area findAreaById(Long idArea) {
		try {

			TypedQuery<Area> q = em.createNamedQuery("Area.findAreaById", Area.class);
			q.setParameter("id", idArea);
			return q.getSingleResult();

		} catch (Exception e) {
			logger.error("Errore getAree " + e.getMessage(), e);
		}
		return null;
	}
}