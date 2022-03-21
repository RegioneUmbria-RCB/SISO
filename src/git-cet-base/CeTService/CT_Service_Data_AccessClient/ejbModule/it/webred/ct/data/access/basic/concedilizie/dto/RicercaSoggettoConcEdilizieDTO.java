package it.webred.ct.data.access.basic.concedilizie.dto;

import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;

public class RicercaSoggettoConcEdilizieDTO extends RicercaSoggettoDTO {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String titolo;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

}
