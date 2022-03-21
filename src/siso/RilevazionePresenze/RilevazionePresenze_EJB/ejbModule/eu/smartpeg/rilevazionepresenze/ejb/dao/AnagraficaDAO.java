package eu.smartpeg.rilevazionepresenze.ejb.dao;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Named;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import eu.smartpeg.rilevazionepresenze.data.model.Anagrafica;
import eu.smartpeg.rilevazionepresenze.data.model.DocumentiAnag;
import eu.smartpeg.rilevazionepresenze.data.model.TipoDocumento;
import eu.smartpeg.rilevazionepresenze.data.dto.RpSearchCriteria;

@Named
public class AnagraficaDAO extends RilevazionePresenzeBaseDao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<Anagrafica> getAnagrafiche() {

		try {

			TypedQuery<Anagrafica> q = em.createNamedQuery("Anagrafica.findAll", Anagrafica.class);
			return q.getResultList();

		} catch (Exception e) {
			logger.error("Errore getAnagrafiche " + e.getMessage(), e);
		}
		return null;
	}

	public List<TipoDocumento> getTipoDocumento() {

		try {

			TypedQuery<TipoDocumento> q = em.createNamedQuery("TipoDocumento.findAll", TipoDocumento.class);
			return q.getResultList();

		} catch (Exception e) {
			logger.error("Errore getTipoDocumento " + e.getMessage(), e);
		}
		return null;
	}

	public List<Anagrafica> getReferenti() {

		try {

			TypedQuery<Anagrafica> q = em.createNamedQuery("Anagrafica.findReferenti", Anagrafica.class);
			return q.getResultList();

		} catch (Exception e) {
			logger.error("Errore getReferenti " + e.getMessage(), e);
		}
		return null;
	}

	public List<Anagrafica> getAppartenentiNucleoFamiliare(Long idReferente) {

		try {

			TypedQuery<Anagrafica> q = em.createNamedQuery("Anagrafica.findAppartenentiNucleo", Anagrafica.class);
			q.setParameter("idReferente", idReferente);
			return q.getResultList();

		} catch (Exception e) {
			logger.error("Errore getAppartenentiNucleoFamiliare " + e.getMessage(), e);
		}
		return null;
	}

	@Transactional
	public void eliminaDocumento(long idDocumento) throws Exception {

		try {
			Query q = em.createNamedQuery("DocumentiAnag.deleteById");
			q.setParameter("id", idDocumento);
			q.executeUpdate();

		} catch (Exception e) {
			logger.error("Errore elimina Documento " + e.getMessage(), e);
		}
	}

	@Transactional
	public Long salvaDocumentoAnagrafica(DocumentiAnag documento) {
		try {
			if (documento.getId() == null) {
				em.persist(documento);
			} else {
				em.merge(documento);
			}

		} catch (Exception e) {
			logger.error("salvaDocumentoAnagrafica " + e.getMessage(), e);

		}
		return documento.getId();
	}

	@Transactional
	public Long saveAnagrafica(Anagrafica anagraficaDeattached) {
		try {
			Set<DocumentiAnag> documentiDaSalvare = new HashSet<>(anagraficaDeattached.getArRpDocumentiAnags());
			Set<DocumentiAnag> documentiEsistenti = new HashSet<>();

			if (anagraficaDeattached.getId() != null) {
				Anagrafica anagraficaEntity = findByID(anagraficaDeattached.getId());
				documentiEsistenti.addAll(anagraficaEntity.getArRpDocumentiAnags());
			}

			// Salvo prima il parent senza figli associati
			anagraficaDeattached.getArRpDocumentiAnags().clear();
			Anagrafica anagraficaParent = em.merge(anagraficaDeattached);
			em.flush();

			// ora mi occupo dei figli

			Set<DocumentiAnag> documentiDaEliminare = new HashSet<>(documentiEsistenti);
			documentiDaEliminare.removeAll(documentiDaSalvare);

			// salvo i figli
			for (DocumentiAnag documento : documentiDaSalvare) {
				documento.setArRpAnagrafica(anagraficaParent);
				em.merge(documento);
			}

			// elimino documenti orfani
			for (DocumentiAnag documento : documentiDaEliminare) {
				eliminaDocumento(documento.getId());
			}

			return anagraficaParent.getId();

		} catch (Exception e) {
			logger.error("Errore saveAnagrafica " + e.getMessage(), e);

		}
		return null;
	}

	@Transactional
	public void eliminaAnagrafica(Anagrafica anagrafica) throws Exception {
		try {
			Query q = em.createNamedQuery("Anagrafica.deleteById");
			q.setParameter("id", anagrafica.getId());
			q.executeUpdate();

		} catch (Exception e) {
			logger.error("Errore elimina Anagrafica " + e.getMessage(), e);
		}
	}

	public Anagrafica findReferenteByID(Long idReferente) {
		try {
			TypedQuery<Anagrafica> q = em.createNamedQuery("Anagrafica.findReferenteById", Anagrafica.class);
			q.setParameter("idReferente", idReferente);
			List<Anagrafica> resultList = q.getResultList();
			if (resultList.size() > 0) {
				return resultList.get(0);
			} else {
				logger.error("ERRORE findReferenteByID per ID: " + idReferente);
			}
			return null;

		} catch (Exception e) {
			logger.error("Errore  findReferenteByID " + e.getMessage(), e);

		}
		return null;
	}

	public List<Anagrafica> searchAnagraficaBySoggettoSegretariato(RpSearchCriteria dto) {

		try {

			String sql = new RpQueryBuilder(dto).createQueryListaSoggetti();
			logger.info("SQL LISTA SOGGETTI RILEVAZIONE PRESENZE [" + sql + "]");

			TypedQuery<Anagrafica> q = em.createQuery(sql, Anagrafica.class);
			return q.getResultList();

		} catch (Exception e) {
			logger.error("Errore caricamento lista soggetti da Rilevazione Presenze " + e.getMessage(), e);
		}
		return null;
	}

	public Anagrafica findAnagraficaById(Long idAnagrafica) {
		try {
			TypedQuery<Anagrafica> q = em.createNamedQuery("Anagrafica.findAnagraficaById", Anagrafica.class);
			q.setParameter("id", idAnagrafica);
			Anagrafica result = q.getSingleResult();
			if (result != null) {
				return result;
			} else {
				logger.error("ERRORE findAnagraficaById per ID: " + idAnagrafica);
			}
			return null;

		} catch (Exception e) {
			logger.error("Errore  findAnagraficaById " + e.getMessage(), e);

		}
		return null;
	}

	public Anagrafica findAnagraficaByCf(String cf) {
		try {
			TypedQuery<Anagrafica> q = em.createNamedQuery("Anagrafica.findAnagraficaByCf", Anagrafica.class);
			q.setParameter("cf", cf);
			Anagrafica result = q.getSingleResult();
			if (result != null) {
				return result;
			} else {
				logger.error("ERRORE findAnagraficaById per Codice Fiscale: " + cf);
			}
			return null;

		} catch (Exception e) {
			logger.error("Errore  findAnagraficaById " + e.getMessage(), e);

		}
		return null;
	}

	public Anagrafica findByID(long idAnagrafica) {
		Anagrafica anagrafica = em.find(Anagrafica.class, idAnagrafica);
		if (anagrafica == null) {
			throw new EntityNotFoundException("Can't find anagrafica for ID " + idAnagrafica);
		}
		return anagrafica;
	}

	public DocumentiAnag findDocumentoById(Long idDocumento) {
		DocumentiAnag documento = em.find(DocumentiAnag.class, idDocumento);
		if (documento == null) {
			throw new EntityNotFoundException("Can't find documento for ID " + idDocumento);
		}
		return documento;
	}
}
