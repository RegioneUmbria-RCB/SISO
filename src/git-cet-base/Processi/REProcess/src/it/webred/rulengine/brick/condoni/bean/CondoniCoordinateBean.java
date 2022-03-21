package it.webred.rulengine.brick.condoni.bean;

import java.io.Serializable;

public class CondoniCoordinateBean implements Serializable {
	private long codiceCondono;
	private long foglio;
	private String numero;
	private String sub;
	public long getCodiceCondono() {
		return codiceCondono;
	}
	public void setCodiceCondono(long codiceCondono) {
		this.codiceCondono = codiceCondono;
	}
	public long getFoglio() {
		return foglio;
	}
	public void setFoglio(long foglio) {
		this.foglio = foglio;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
}
