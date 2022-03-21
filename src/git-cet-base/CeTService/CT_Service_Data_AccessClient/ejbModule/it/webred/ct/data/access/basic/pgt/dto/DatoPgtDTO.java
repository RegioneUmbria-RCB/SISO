package it.webred.ct.data.access.basic.pgt.dto;

import java.io.Serializable;

public class DatoPgtDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String nomeDato;
	private String tipoOracleDato;
	private String tipoJavaDato;
	
	public String getNomeDato() {
		return nomeDato;
	}
	public void setNomeDato(String nomeDato) {
		this.nomeDato = nomeDato;
	}
	public String getTipoOracleDato() {
		return tipoOracleDato;
	}
	public void setTipoOracleDato(String tipoOracleDato) {
		this.tipoOracleDato = tipoOracleDato;
	}
	public String getTipoJavaDato() {
		return tipoJavaDato;
	}
	public void setTipoJavaDato(String tipoJavaDato) {
		this.tipoJavaDato = tipoJavaDato;
	}
	
	

}
