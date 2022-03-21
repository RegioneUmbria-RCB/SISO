package it.webred.ct.aggregator.ejb.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class RichiestaDatiCivicoDTO extends CeTBaseObject {
	
	private static final long serialVersionUID = 1L;
	private String toponimo;
    private String via;
    private String civico;
    private String idVia;
    
	public String getToponimo() {
		return toponimo;
	}
	public void setToponimo(String toponimo) {
		this.toponimo = toponimo;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public void setIdVia(String idVia) {
		this.idVia = idVia;
	}
	public String getIdVia() {
		return idVia;
	}
	
}
