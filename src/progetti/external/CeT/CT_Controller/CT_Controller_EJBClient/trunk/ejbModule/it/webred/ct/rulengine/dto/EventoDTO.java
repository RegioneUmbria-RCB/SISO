package it.webred.ct.rulengine.dto;

import it.webred.ct.rulengine.controller.model.RAnagStati;
import it.webred.ct.rulengine.controller.model.RCommand;
import it.webred.ct.rulengine.controller.model.RCommandType;

import java.io.Serializable;

public class EventoDTO implements Serializable {
	
	private boolean selected;
	
	private Long id;
	private String standard;
	
	private RCommand nextCommand;
	
	private RCommandType afterRCommandType;
		
	private Long afterIdFonte;
	private String afterFonteName;
	
	private RCommand afterRCommand;
	
	private RAnagStati afterRAnagStato;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public RCommand getNextCommand() {
		return nextCommand;
	}

	public void setNextCommand(RCommand nextCommand) {
		this.nextCommand = nextCommand;
	}

	public RCommandType getAfterRCommandType() {
		return afterRCommandType;
	}

	public void setAfterRCommandType(RCommandType afterRCommandType) {
		this.afterRCommandType = afterRCommandType;
	}

	public Long getAfterIdFonte() {
		return afterIdFonte;
	}

	public void setAfterIdFonte(Long afterIdFonte) {
		this.afterIdFonte = afterIdFonte;
	}

	public String getAfterFonteName() {
		return afterFonteName;
	}

	public void setAfterFonteName(String afterFonteName) {
		this.afterFonteName = afterFonteName;
	}

	public RCommand getAfterRCommand() {
		return afterRCommand;
	}

	public void setAfterRCommand(RCommand afterRCommand) {
		this.afterRCommand = afterRCommand;
	}

	public RAnagStati getAfterRAnagStato() {
		return afterRAnagStato;
	}

	public void setAfterRAnagStato(RAnagStati afterRAnagStato) {
		this.afterRAnagStato = afterRAnagStato;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
	
	
}
