package it.webred.cs.csa.ejb.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;


public class RelSettCatsocEsclusivaDTO extends CeTBaseObject {
	private static final long serialVersionUID = 1L;
	
	private Long tipoDiarioId;

	private Long categoriaSocialeId;

	private Long settoreId;

	public Long getTipoDiarioId() {
		return tipoDiarioId;
	}

	public void setTipoDiarioId(Long tipoDiarioId) {
		this.tipoDiarioId = tipoDiarioId;
	}

	public Long getCategoriaSocialeId() {
		return categoriaSocialeId;
	}

	public void setCategoriaSocialeId(Long categoriaSocialeId) {
		this.categoriaSocialeId = categoriaSocialeId;
	}

	public Long getSettoreId() {
		return settoreId;
	}

	public void setSettoreId(Long settoreId) {
		this.settoreId = settoreId;
	}
	
}
