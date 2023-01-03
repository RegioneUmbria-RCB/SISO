package it.webred.cs.csa.ejb.dto.rest;

import java.io.Serializable;

public class TabellaDecodificaDTO extends TabellaDecodificaBaseDTO implements Serializable {
	
	private String id;
	private String tooltip;
 
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

}
