/**
 * @author Pietro
 *
 */
package it.webred.ct.support.audit.test;

import it.webred.ct.support.audit.annotation.AuditField;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Interfaccia che tutti i data bean devono implementare principalmente per la
 * creazione della relativa rappresentazione XML.
 * 
 * @author Pietro Bartoccioni
 * 
 */
public abstract class DataBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@AuditField
	private String ente;
	@AuditField
	private String sessionId;
	private String errore = "";
	@AuditField
	public DatiUtente utente;

	/**
	 * Metodo da implementare su tutti i Discendenti per resettare ed
	 * inizializzare tutti i pojo dell'oggetto.
	 */
	public abstract void reset();

	/**
	 * Imposta la stringa contenente l'eventuale errore verificatosi durante
	 * l'esecuzione di un submit()
	 * 
	 * @param errore
	 *            descrizione dell'errore.
	 */
	public void setErrore(String errore) {
		this.errore = errore;
	}

	/**
	 * @return la stringa contenente l'eventuale errore verificatosi durante
	 *         l'esecuzione di un submit()
	 */
	public String getErrore() {
		return errore;
	}


	/**recupera l'ente in cui è eseguita l'operazione
	 * @return
	 */
	public String getEnte() {
    	return ente;
    }

	
	/**imposta l'ente in cui è eseguita l'operazione
	 * @param ente
	 */
	public void setEnte(String ente) {
    	this.ente = ente;
    }

	
	/**recupera la session ID dell'utente
	 * @return
	 */
	public String getSessionId() {
    	return sessionId;
    }


	/**imposta la session ID dell'utente
	 * @param sessionId
	 */
	public void setSessionId(String sessionId) {
    	this.sessionId = sessionId;
    }

	public DatiUtente getUtente() {
		return utente;
	}

	public void setUtente(DatiUtente utente) {
		this.utente = utente;
	}
	
}
