package it.webred.ct.data.access.basic.cnc.flusso750.dto;

import java.io.Serializable;

public class AnnoTributoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String annoRif;
	private String codTipoTributo;
	public String getAnnoRif() {
		return annoRif;
	}
	public void setAnnoRif(String annoRif) {
		this.annoRif = annoRif;
	}
	public String getCodTipoTributo() {
		return codTipoTributo;
	}
	public void setCodTipoTributo(String codTipoTributo) {
		this.codTipoTributo = codTipoTributo;
	}
	
}
