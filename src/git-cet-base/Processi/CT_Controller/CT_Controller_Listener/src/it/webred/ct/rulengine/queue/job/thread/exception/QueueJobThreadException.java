package it.webred.ct.rulengine.queue.job.thread.exception;

public class QueueJobThreadException extends Exception {
	
	private Long commandType;
	
	public QueueJobThreadException(String message,Long commandType) {
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
