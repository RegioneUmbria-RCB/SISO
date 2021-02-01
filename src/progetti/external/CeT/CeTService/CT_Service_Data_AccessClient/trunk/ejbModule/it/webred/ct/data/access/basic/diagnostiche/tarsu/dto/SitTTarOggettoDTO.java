package it.webred.ct.data.access.basic.diagnostiche.tarsu.dto;

import java.io.Serializable;
import it.webred.ct.data.model.tarsu.SitTTarOggetto;

public class SitTTarOggettoDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private SitTTarOggetto sitTTarOggetto;
	private String via;
	
	public SitTTarOggetto getSitTTarOggetto() {
		return sitTTarOggetto;
	}
	public void setSitTTarOggetto(SitTTarOggetto sitTTarOggetto) {
		this.sitTTarOggetto = sitTTarOggetto;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	
}
