package it.webred.ss.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;
import org.springframework.transaction.annotation.Transactional;

import it.webred.ss.data.model.SsOOrganizzazione;
import it.webred.ss.data.model.SsPuntoContatto;
import it.webred.ss.data.model.SsRelUffPcontOrg;
import it.webred.ss.data.model.SsUfficio;

@Named
public class ConfigurazioneDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "SegretariatoSoc_DataModel")
	protected EntityManager em;
	protected static Logger logger = Logger.getLogger("segretariatosoc.log");

	public void salvaUfficio(SsUfficio u) {
		em.merge(u);
	}

	public void salvaPuntoContatto(SsPuntoContatto pc) {
		em.merge(pc);
	}

	public void salvaRelazione(SsRelUffPcontOrg rel) {
		em.merge(rel);
	}

	@Transactional
	public void eliminaUfficio(SsUfficio u) {
		try {
			Query q = em.createNamedQuery("SsUfficio.eliminaUfficioById");
			q.setParameter("id", u.getId());
			q.executeUpdate();
		} catch (Exception e) {
			logger.error("Errore elimina Ufficio " + e.getMessage(), e);
		}

	}

	public List<SsPuntoContatto> getPuntiContatto() {
		Query q = em.createNamedQuery("SsPuntoContatto.findAll");
		return q.getResultList();
	}

	public List<SsUfficio> getUffici() {
		Query q = em.createNamedQuery("SsUfficio.findAll");
		return q.getResultList();
	}

	public List<SsOOrganizzazione> getOrganzzazioniAccesso() {
		Query q = em.createNamedQuery("SsOOrganizzazione.listOrganizzazioneDataRouting");
		return q.getResultList();
	}

	public List<SsRelUffPcontOrg> getRelazioni() {
		Query q = em.createNamedQuery("SsRelUffPcontOrg.findAll");
		return q.getResultList();
	}

	@Transactional
	public void eliminaPuntoContatto(SsPuntoContatto pc) {
		try {
			Query q = em.createNamedQuery("SsPuntoContatto.eliminaPuntoContattoById");
			q.setParameter("id", pc.getId());
			q.executeUpdate();
		} catch (Exception e) {
			logger.error("Errore elimina Punto di Contatto " + e.getMessage(), e);
		}
	}

	public void eliminaRelazione(SsRelUffPcontOrg rel) {
		try {
			Query q = em.createNamedQuery("SsRelUffPcontOrg.eliminaRelazioneById");
			q.setParameter("id", rel.getId());
			q.executeUpdate();
		} catch (Exception e) {
			logger.error("Errore elimina Relazione " + e.getMessage(), e);
		}
	}

	public List<SsRelUffPcontOrg> verificaRelazioni(SsRelUffPcontOrg rel) {
		try {

			Query q = em.createNamedQuery("SsRelUffPcontOrg.existsSchedaAccesso");
			q.setParameter("organizzazioneId", Long.valueOf(rel.getSsOOrganizzazione().getId()));
			q.setParameter("puntoContattoId", Long.valueOf(rel.getSsPuntoContatto().getId()));
			q.setParameter("ufficioId", Long.valueOf(rel.getSsUfficio().getId()));
			return q.getResultList();
		} catch (Exception e) {
			logger.error("Errore verifica Relazione " + e.getMessage(), e);
		}
		return null;
	}

	public void gestisciAttivazioneRelazioniUfficio(Long id, boolean abilita) {
		Query q = em.createNamedQuery("SsRelUffPcontOrg.gestisciAttivazioneByUfficio");
		q.setParameter("ufficioId", id);
		q.setParameter("abilita", abilita);
		q.executeUpdate();
	}

	public void gestisciAttivazioneRelPContatto(Long id, boolean abilita) {
		Query q = em.createNamedQuery("SsRelUffPcontOrg.gestisciAttivazioneByPuntoContatto");
		q.setParameter("puntoContattoId", id);
		q.setParameter("abilita", abilita);
		q.executeUpdate();
		
	}

}
