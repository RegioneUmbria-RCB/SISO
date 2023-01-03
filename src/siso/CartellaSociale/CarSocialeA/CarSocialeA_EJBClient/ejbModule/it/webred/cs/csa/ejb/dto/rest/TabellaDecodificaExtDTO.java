package it.webred.cs.csa.ejb.dto.rest;

import java.io.Serializable;

public class TabellaDecodificaExtDTO extends TabellaDecodificaBaseDTO implements Serializable{

	private String cod;
	private String tooltip;
	
	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	
}
