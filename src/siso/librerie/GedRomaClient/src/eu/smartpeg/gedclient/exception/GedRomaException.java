package eu.smartpeg.gedclient.exception;

public class GedRomaException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String code;
	String message;

	public GedRomaException(String code, String message) {
		super(code + " + " + message);
		this.code = code;
		this.message = message;
	}

}
