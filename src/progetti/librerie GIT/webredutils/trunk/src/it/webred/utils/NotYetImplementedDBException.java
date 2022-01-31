package it.webred.utils;

public class NotYetImplementedDBException extends Exception {
	public NotYetImplementedDBException() {
		super("Database non implementato");
	}
	public NotYetImplementedDBException(String message) {
		super(message);
	}
	public NotYetImplementedDBException(String message, Throwable cause) {
		super(message, cause);
	}
	public NotYetImplementedDBException(Throwable cause) {
		super(cause);
	}
	public static final long serialVersionUID = 1;
}
