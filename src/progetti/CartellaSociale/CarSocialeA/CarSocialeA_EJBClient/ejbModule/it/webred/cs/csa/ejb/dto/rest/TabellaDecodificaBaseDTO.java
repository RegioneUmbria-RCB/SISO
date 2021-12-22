package it.webred.cs.csa.ejb.dto.rest;

import java.io.Serializable;

public class TabellaDecodificaBaseDTO implements Serializable {
private String descrizione;
private String tooltip;
public String getDescrizione() {
	return descrizione;
}
public void setDescrizione(String descrizione) {
	this.descrizione = descrizione;
}
public String getTooltip() {
	return tooltip;
}
public void setTooltip(String tooltip) {
	this.tooltip = tooltip;
}

	
}
