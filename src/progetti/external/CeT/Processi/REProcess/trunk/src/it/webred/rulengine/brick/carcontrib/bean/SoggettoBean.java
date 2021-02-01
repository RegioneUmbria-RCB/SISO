package it.webred.rulengine.brick.carcontrib.bean;

import java.io.Serializable;
import java.util.Date;

public class SoggettoBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String provenienzaDatiIci;
	private String provenienzaDatiTarsu;
	private String tipoSogg;
	private String codFis;
	private String cognome;
	private String nome;
	private Date dtNas;
	private String parIva;
	private String denom;
	public String getProvenienzaDatiIci() {
		return provenienzaDatiIci;
	}
	public void setProvenienzaDatiIci(String provenienzaDatiIci) {
		this.provenienzaDatiIci = provenienzaDatiIci;
	}
	public String getProvenienzaDatiTarsu() {
		return provenienzaDatiTarsu;
	}
	public void setProvenienzaDatiTarsu(String provenienzaDatiTarsu) {
		this.provenienzaDatiTarsu = provenienzaDatiTarsu;
	}
	public String getTipoSogg() {
		return tipoSogg;
	}
	public void setTipoSogg(String tipoSogg) {
		this.tipoSogg = tipoSogg;
	}
	public String getCodFis() {
		return codFis;
	}
	public void setCodFis(String codFis) {
		this.codFis = codFis;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDtNas() {
		return dtNas;
	}
	public void setDtNas(Date dtNas) {
		this.dtNas = dtNas;
	}
	public String getParIva() {
		return parIva;
	}
	public void setParIva(String parIva) {
		this.parIva = parIva;
	}
	public String getDenom() {
		return denom;
	}
	public void setDenom(String denom) {
		this.denom = denom;
	} 

	
}
