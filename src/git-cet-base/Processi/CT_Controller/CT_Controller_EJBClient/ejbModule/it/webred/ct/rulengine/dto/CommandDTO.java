package it.webred.ct.rulengine.dto;

import it.webred.ct.rulengine.controller.model.RCommand;

import java.io.Serializable;

public class CommandDTO implements Serializable {
	
	private RCommand rCommand;
	private boolean running;
	private String status;
	private String description;
	private boolean processable;
	
	public RCommand getrCommand() {
		return rCommand;
	}
	public void setrCommand(RCommand rCommand) {
		this.rCommand = rCommand;
	}
	public boolean isRunning() {
		return running;
	}
	public void setRunning(boolean running) {
		this.running = running;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isProcessable() {
		return processable;
	}
	public void setProcessable(boolean processable) {
		this.processable = processable;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
