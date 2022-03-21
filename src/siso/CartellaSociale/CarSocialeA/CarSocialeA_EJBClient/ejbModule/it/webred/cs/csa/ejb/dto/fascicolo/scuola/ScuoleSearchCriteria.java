package it.webred.cs.csa.ejb.dto.fascicolo.scuola;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class ScuoleSearchCriteria extends CeTBaseObject {

	private Long anno;
	private Long tipo;
	private String comune;
	
	public Long getAnno() {
		return anno;
	}
	public void setAnno(Long anno) {
		this.anno = anno;
	}
	public Long getTipo() {
		return tipo;
	}
	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
}
