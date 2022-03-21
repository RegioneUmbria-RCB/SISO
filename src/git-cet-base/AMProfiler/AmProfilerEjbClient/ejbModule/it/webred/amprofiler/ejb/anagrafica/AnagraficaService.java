package it.webred.amprofiler.ejb.anagrafica;

import it.webred.amprofiler.ejb.anagrafica.dto.AnagraficaSearchCriteria;
import it.webred.amprofiler.model.AmAnagrafica;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AnagraficaService {

	public List<AmAnagrafica> getListaAnagrafica(AnagraficaSearchCriteria criteria);
	
	/** Recupera l'anagrafica partendo dallo User Name alla quale è collegata.
	 * @param userName User name dell'anagrafica da recuperare.
	 * @return
	 */
	public AmAnagrafica findAnagraficaByUserName(String userName);

	/** Recupera l'anagrafica partendo dal suo codice fiscale.
	 * @param id Id dell'anagrafica da recuperare
	 * @return
	 */
	public AmAnagrafica findAnagraficaById(Integer id);

	/** Recupera l'anagrafica partendo dal suo codice fiscale.
	 * @param codiceFiscale Codice fiscale dell'anagrafica da recuperare
	 * @return
	 */
	public List<AmAnagrafica>  findAnagraficaByCodiceFiscale(String userName, String codiceFiscale);
	
	/** Recupera l'anagrafica partendo da codice fiscale, e/o cognome, e/o nome.
	 * @param codiceFiscale Codice fiscale dell'anagrafica da recuperare
	 * @param cognome Cognome dell'anagrafica da recuperare
	 * @param nome Nome dell'anagrafica da recuperare
	 * @return
	 */
	public List<AmAnagrafica>  findAnagraficaByCodiceFiscaleCognomeNome(String codiceFiscale, String cognome, String nome);

	boolean createAnagrafica(AmAnagrafica anagrafica);

	boolean updateAnagrafica(AmAnagrafica anagrafica);
}
