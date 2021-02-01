package it.webred.ct.rulengine.dto;

import it.webred.ct.rulengine.controller.model.RAnagStati;
import it.webred.ct.rulengine.controller.model.RCommand;
import it.webred.ct.rulengine.controller.model.RCommandAck;
import it.webred.ct.rulengine.controller.model.RCommandLaunch;
import it.webred.ct.rulengine.controller.model.RTracciaStati;

import java.io.Serializable;
import java.util.List;

public class LogDTO implements Serializable {
	
	private RCommandLaunch commandLaunch;
	private List<RCommandAck> commandAck;
	private RCommand command;
	private RAnagStati RAnagStato;
	private boolean processing;
	
	public boolean isProcessing() {
		return processing;
	}
	public void setProcessing(boolean processing) {
		this.processing = processing;
	}
	public RCommandLaunch getCommandLaunch() {
		return commandLaunch;
	}
	public void setCommandLaunch(RCommandLaunch commandLaunch) {
		this.commandLaunch = commandLaunch;
	}
	public List<RCommandAck> getCommandAck() {
		return commandAck;
	}
	public void setCommandAck(List<RCommandAck> commandAck) {
		this.commandAck = commandAck;
	}
	public RCommand getCommand() {
		return command;
	}
	public void setCommand(RCommand command) {
		this.command = command;
	}
	public RAnagStati getRAnagStato() {
		return RAnagStato;
	}
	public void setRAnagStato(RAnagStati rAnagStato) {
		RAnagStato = rAnagStato;
	}
	
}
