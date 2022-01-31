package it.webred.mui.model;

public class MiDupFabbricatiIdentifica extends MiDupFabbricatiIdentificaBase {
	private String codiceVia = null;
	private String numeroCivico = null;
	public String getCodiceVia() {
		return codiceVia;
	}
	public String getNumeroCivico() {
		return numeroCivico;
	}
	public void setCodiceVia(String codiceVia) {
		this.codiceVia = codiceVia;
	}
	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}
}
