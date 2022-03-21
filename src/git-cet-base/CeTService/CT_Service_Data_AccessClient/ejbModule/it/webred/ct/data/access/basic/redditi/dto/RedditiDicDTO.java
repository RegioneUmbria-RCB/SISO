package it.webred.ct.data.access.basic.redditi.dto;

import it.webred.ct.data.model.redditi.RedRedditiDichiarati;

import java.io.Serializable;

public class RedditiDicDTO extends RedRedditiDichiarati implements Serializable {
	private static final long serialVersionUID = 1L;

	private String codModello;
	private String desQuadro;
	private String valoreContabileF;

	public String getDesQuadro() {
		return desQuadro;
	}

	public void setDesQuadro(String desQuadro) {
		this.desQuadro = desQuadro;
	}

	public void setValoreContabileF(String valoreContabileF) {
		this.valoreContabileF = valoreContabileF;
	}

	public String getValoreContabileF() {
		return valoreContabileF;
	}

	public String getCodModello() {
		return codModello;
	}

	public void setCodModello(String codModello) {
		this.codModello = codModello;
	}

}
