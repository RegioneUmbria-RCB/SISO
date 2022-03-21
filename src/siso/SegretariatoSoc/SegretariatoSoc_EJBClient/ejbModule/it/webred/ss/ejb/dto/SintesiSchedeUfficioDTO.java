package it.webred.ss.ejb.dto;

import it.webred.ss.data.model.SsUfficio;

import java.io.Serializable;

public class SintesiSchedeUfficioDTO implements Serializable {

	private SsUfficio ufficio;
	private Integer totale;
	
	public SintesiSchedeUfficioDTO(SsUfficio u, Integer count){
		ufficio = u;
		totale = count;
	}
	
	public SsUfficio getUfficio() {
		return ufficio;
	}
	public void setUfficio(SsUfficio ufficio) {
		this.ufficio = ufficio;
	}
	public Integer getTotale() {
		return totale;
	}
	public void setTotale(Integer totale) {
		this.totale = totale;
	}
	

}
