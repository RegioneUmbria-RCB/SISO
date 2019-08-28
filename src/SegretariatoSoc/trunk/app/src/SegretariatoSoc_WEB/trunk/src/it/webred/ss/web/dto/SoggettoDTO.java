package it.webred.ss.web.dto;

import it.webred.cs.data.model.CsASoggettoLAZY;

public class SoggettoDTO {

	private CsASoggettoLAZY csASoggetto;
	private String itemLabel;
	
	public CsASoggettoLAZY getCsASoggetto() {
		return csASoggetto;
	}
	
	public void setCsASoggetto(CsASoggettoLAZY csASoggetto) {
		this.csASoggetto = csASoggetto;
	}

	public String getItemLabel() {
		return itemLabel;
	}

	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}

}
