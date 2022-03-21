package it.webred.amprofiler.ejb.section;

import it.webred.amprofiler.ejb.AmProfilerBaseService;
import it.webred.ct.config.model.AmApplication;
import it.webred.ct.config.model.AmFonte;
import it.webred.ct.config.model.AmSection;

import java.util.List;

import javax.ejb.Stateless;

@Stateless
public class SectionServiceBean extends AmProfilerBaseService implements SectionService {

	@SuppressWarnings("unchecked")
	@Override
	public List<AmSection> findAmSections(String applicationName,
			Integer idFonte) {

		// ricerca per nome applicazione
		if (applicationName != null && !"".equals(applicationName)) {
			// senza id Fonte
			if (idFonte == null) {
				return em
						.createQuery(
								"select s from AmSection s where s.amApplication.name = :applicationName order by s.id")
						.setParameter("applicationName", applicationName)
						.getResultList();
			} else { // and id Fonte
				return em
						.createQuery(
								"select s from AmSection s where s.amApplication.name = :applicationName and s.amFonte.id = :idFonte order by s.id")
						.setParameter("applicationName", applicationName)
						.setParameter("idFonte", idFonte).getResultList();
			}
		} else {
			// ricerca per id fonte.
			if (idFonte != null && applicationName != null
					&& !"".equals(applicationName)) {
				return em
						.createQuery(
								"select s from AmSection s where s.amFonte.id = :idFonte order by s.id")
						.setParameter("idFonte", idFonte).getResultList();

			}
		}
		// sezioni Globali
		return em
				.createQuery(
						"select s from AmSection s where s.amApplication is null and s.amInstance is null and s.amComune is null order by s.id")
				.getResultList();

	}

	@Override
	public List<AmSection> findAmSections(String applicationName) {
		return findAmSections(applicationName, null);
	}

	@Override
	public List<AmSection> findAmSections(Integer idFonte) {
		return findAmSections(null, idFonte);
	}

	@Override
	public List<AmSection> findGlobalAmSections() {
		return findAmSections(null, null);
	}

	@Override
	public AmSection findAmSectionById(Integer id) {
		AmSection amSection = (AmSection) em.find(AmSection.class, id);
		if (amSection != null) {
			// Carico la relazione figlia Lazy.
			amSection.getAmKeyValues().size();
		}
		return amSection;
	}

	@Override
	public boolean createAmSection(AmSection amSection,
			String amApplicationName, Integer amIdFonte) {
		AmApplication amApplication = null;
		AmFonte amFonte = null;
		// ricerca applicazione
		if (amApplicationName != null && !"".equals(amApplicationName)) {
			amApplication = (AmApplication) em.find(AmApplication.class,
					amApplicationName);
			// errore se applicazione non esiste
			if (amApplication == null)
				return false;
		}
		// ricerca fonte
		if (amIdFonte != null && !"".equals(amIdFonte)) {
			amFonte = (AmFonte) em.find(AmFonte.class, amIdFonte);
			// errore se istanza non esiste
			if (amFonte == null)
				return false;
		}
		amSection.setAmApplication(amApplication);
		amSection.setAmFonte(amFonte);
		em.persist(amSection);

		return true;
	}

	@Override
	public void updateAmSection(AmSection amSection) {
		if (em.find(AmSection.class, amSection.getId()) != null) {
			em.merge(amSection);
		}
	}

	@Override
	public void deleteAmSection(Integer id) {
		AmSection amSection = em.find(AmSection.class, id);
		if (amSection != null) {
			em.remove(amSection);
		}
	}

}
