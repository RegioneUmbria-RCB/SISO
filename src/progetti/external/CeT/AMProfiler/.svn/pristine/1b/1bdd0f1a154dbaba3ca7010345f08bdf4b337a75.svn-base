package it.webred.amprofiler.ejb.section;

import it.webred.ct.config.model.AmSection;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface SectionService {

	/**
	 * Ricerca le Sezioni(AmSection) partendo dal Nome Applicazione, dall'id
	 * della Fonte alla quale sono associate, nel caso che non venga fornito
	 * nessuno dei due parametri(entrambi = null) le sezioni globali al sistema.
	 * 
	 * @param applicationName
	 *            Nome applicazione della quale recuperare le sezioni.
	 * @param idFonte
	 *            Id della Fonte della quale recuperare le sezioni.
	 * @return
	 */
	public List<AmSection> findAmSections(String applicationName,
			Integer idFonte);

	/**
	 * Ricerca le Sezioni(AmSection) partendo dal Nome Applicazione alla quale
	 * sono associate.
	 * 
	 * @param applicationName
	 *            Nome applicazione della quale recuperare le sezioni.
	 * @return
	 */
	public List<AmSection> findAmSections(String applicationName);

	/**
	 * Ricerca le Sezioni(AmSection) partendo dall'id della Fonte alla quale
	 * sono associate.
	 * 
	 * @param idFonte
	 *            Id della Fonte della quale recuperare le sezioni.
	 * @return
	 */
	public List<AmSection> findAmSections(Integer idFonte);

	/**
	 * Recupera le Sezioni(AmSection) globali ossia tutte quelle non associate
	 * ne a fonti ne ad applicazione.
	 * 
	 * @return
	 */
	public List<AmSection> findGlobalAmSections();

	/**
	 * Recupera una sezione partendo dalla sua chiave primaria (id)
	 * 
	 * @param id
	 *            Chiave primaria sezione da recuperare.
	 * @return
	 */
	public AmSection findAmSectionById(Integer id);

	public boolean createAmSection(AmSection amSection,
			String amApplicationName, Integer amIdFonte);

	public void updateAmSection(AmSection amSection);

	public void deleteAmSection(Integer id);

}
