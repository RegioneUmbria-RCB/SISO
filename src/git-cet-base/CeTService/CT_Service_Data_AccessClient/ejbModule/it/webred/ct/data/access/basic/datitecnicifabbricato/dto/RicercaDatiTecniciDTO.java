package it.webred.ct.data.access.basic.datitecnicifabbricato.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class RicercaDatiTecniciDTO extends CeTBaseObject implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long idDati;
	private String tipoDati;
	public Long getIdDati() {
		return idDati;
	}
	public void setIdDati(Long idDati) {
		this.idDati = idDati;
	}
	public String getTipoDati() {
		return tipoDati;
	}
	public void setTipoDati(String tipoDati) {
		this.tipoDati = tipoDati;
	}

}
