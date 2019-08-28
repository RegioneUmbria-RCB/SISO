package it.umbriadigitale.interscambio.exception;

/**
 * Eccezione lanciata in caso di errori di validazione di un messaggio XML di Interscambio Cartella Sociale
 * 
 * @author Iacopo Sorce
 */
public class InterscambioValidationException extends Exception {
	private static final long serialVersionUID = -7153077418186049738L;	// random generated

	public InterscambioValidationException(String message, Throwable cause) {
		super(message, cause);
	}
}
