package it.webred.ct.aggregator.ejb.dto;

import java.io.Serializable;

public class ViaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String idVia;
	private String descrizione;
	
	public String getIdVia() {
		return idVia;
	}
	public void setIdVia(String idVia) {
		this.idVia = idVia;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
