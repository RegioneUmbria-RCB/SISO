package it.webred.ct.data.access.basic.c336.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class C336CommonDTO extends CeTBaseObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String codIrreg;
	private String codTipDoc;
	private Long idAllegato;
	public String getCodIrreg() {
		return codIrreg;
	}
	public void setCodIrreg(String codIrreg) {
		this.codIrreg = codIrreg;
	}
	public String getCodTipDoc() {
		return codTipDoc;
	}
	public void setCodTipDoc(String codTipDoc) {
		this.codTipDoc = codTipDoc;
	}
	public Long getIdAllegato() {
		return idAllegato;
	}
	public void setIdAllegato(Long idAllegato) {
		this.idAllegato = idAllegato;
	}
	
	
	
	
}
