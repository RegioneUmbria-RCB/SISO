package it.webred.ct.rulengine.web.bean.diag.command.thread.exception;

public class DiagnosticCommandException extends Exception {

	private Long commandType;
	
	public DiagnosticCommandException(String message,Long commandType) {
		super(message);
		this.commandType = commandType;
	}

	public Long getCommandType() {
		return commandType;
	}

	public void setCommandType(Long commandType) {
		this.commandType = commandType;
	}
}
