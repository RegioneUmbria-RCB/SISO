package it.webred.ct.data.access.basic.docfa.dto;

import java.io.Serializable;

public class OperatoreDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private String cf;
	private String note;
	private String tipo;
	
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	
}
