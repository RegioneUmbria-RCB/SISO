package it.webred.ct.rulengine.scheduler.exception;

public class DummyProcessException extends Throwable {
	
	private Long commandType;
	
	public DummyProcessException(String message,Long commandType) {
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
