package it.webred.ct.data.access.aggregator.elaborazioni.dto;

import java.math.BigDecimal;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class ParamCalcoloValImponibileDTO extends CeTBaseObject {

	private static final long serialVersionUID = 1L;
	
	private Double rendita;
	private String categoria;
	
	public Double getRendita() {
		return rendita;
	}
	public void setRendita(Double rendita) {
		this.rendita = rendita;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
}
