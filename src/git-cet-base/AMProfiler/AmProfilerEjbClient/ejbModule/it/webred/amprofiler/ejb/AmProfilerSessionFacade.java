package it.webred.amprofiler.ejb;

import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.amprofiler.model.AmUser;
import it.webred.ct.config.model.AmComune;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.data.access.basic.anagrafe.dto.AnagraficaDTO;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


public interface AmProfilerSessionFacade {

	/**
	 * @param amAnagrafica
	 * @return true se creata
	 */
	boolean createAmAnagrafica(AmAnagrafica amAnagrafica);

	/**
	 * @param amAnagrafica
	 * @return true se aggiornata
	 */
	boolean updateAmAnagrafica(AmAnagrafica amAnagrafica);

	/**
	 * Recupera l'anagrafica partendo dallo User Name alla quale è collegata.
	 * 
	 * @param username
	 *            User name dell'anagrafica da recuperare.
	 * @return
	 */
	AmAnagrafica findAmAnagraficaByUserName(String username);
	
	List<AnagraficaDTO> findAnagraficaCeTByCodiceFiscale(String ente, String codiceFiscale);

	List<AnagraficaDTO> findAnagraficaFamiliareCeTByCodiceFiscale(String ente, String username, String codiceFiscale);

	/**
	 * Crea e rende persistente un nuovo utente(AmUser) comprensivo della lista
	 * dei suoi Gruppi d'appartenenza (AnGroup) Anche le istanze della lista
	 * chiave/valore vengono create.
	 * 
	 * @param amUser
	 *            Istanza del pojo del sezione con la lista delle istanze delle
	 *            sue AnKeyValue valorizzata
	 * @return l'esito della chiamata.
	 */
	boolean createAmUser(AmUser amUser);

	/**
	 * Elimina l'utente e tutte le relative istanze gruppo
	 * 
	 * @param id
	 * L'id dell'utente da eliminare.
	 * @return l'esito della chiamata.
	 */
	boolean deleteAmUser(String username);
	/**Aggiorna l'utente rendendo persistenti le modifiche effettuate sull'istanza dell'utente(pojo)
	 * @param amUser L'istanza dell'utente(pojo) da aggiornare.
	 * @return l'esito della chiamata.
	 */
	boolean updateAmUser(AmUser amUser);
	/** Recupera un utente partendo dalla sua chiave primaria (name)
	 * @param username Chiave primaria sezione da recuperare.
	 * @return
	 */
	AmUser findAmUserByName( String username);
	/** Recupera se un application item è abilitato per un certo utente
	 * @param username
	 * @param applicationItem nome dell'item dell'applicazione
	 * @return
	 */
	boolean isAmItemEnabled(String ente, String username, String applicationItem);
	/** Recupera la lista dei ruoli per application name
	 * @param amApplicationName
	 * @param username
	 * @return
	 */
	List<String> findApplicationRoles(String ente, String amApplicationName, String username);
	
	List<String> findPermissionByEnteItemUsername(String ente, String applicationItem, String username);
	
	List<String> findPermissionByEnteItemUsername(String ente,
			String applicationItem, String username, List<String> gruppi);
	
	/** Recupera AmUser a partire dal codice Fiscale. 
	 * @param codiceFiscale
	 * @return
	 */
	AmUser findAmUserByCodiceFiscale(String codiceFiscale);
	/**	 recupera gli utenti di AmProfile 
	 * @param amApplicationName nome dell'applicazione
	 * @param gruppi lista dei gruppi ( non filtra per gruppo se null o vuota ) 
	 * @param filtroAnagrafica hashMap con i parametri di filtraggio relativi a tabella anagrafica. I nomi dei campi anagrafica sono lowercase
	 * @param fromIndex valore minimo del range di paginazione
	 * @param maxResults numero massimo di risultati
	 * @return null se non trova niente 
	 * @exception IndexOutOfBoundsException in caso raggiungimento fine DB
	 *  
	 */
	List<AmUser> findAmUser(String amApplicationName, List<String> gruppi, HashMap<String, Object> filtroAnagrafica, int fromIndex, int maxResults);
	
	/*recupero nome ente*/
	String getNomeEnte(String ente);

	List<AmComune> getListaComune();
	
	List<AmComune> getListaComuneByData(Date data);
	
	List<AmTabComuni> getListaComuniIta();
	
	List<AmTabNazioni> getListaNazioni();

	AmTabComuni findComuneByDenominazione(String denominazione);

	AmTabComuni findComuneByDenominazioneProvincia(String denominazione,
			String provincia);

	boolean deleteAmUserByApplication(String username, String application,
			String comune);
	
	public boolean verificaAggiornaAvvocato(String username, String ente);

}
