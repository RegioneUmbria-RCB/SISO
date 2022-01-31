package it.webred.verificaCoordinate.dto;

import java.io.Serializable;

public abstract class VerificaCoordinateBaseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String codEnte;

	public String getCodEnte() {
		return codEnte;
	}

	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}
	
}
