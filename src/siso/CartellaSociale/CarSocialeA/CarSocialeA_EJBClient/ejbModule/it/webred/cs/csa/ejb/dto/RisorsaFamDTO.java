package it.webred.cs.csa.ejb.dto;

import java.io.Serializable;

import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsASoggettoLAZY;

public class RisorsaFamDTO implements Serializable{
	
	private String cognome;
	private String nome;
	private String cf;
	private CsASoggettoLAZY soggettoCaso; //Valorizzato se presente il caso per il CF
	private boolean hasDatiSociali;
	
	public String getItemLabel(){
		return cognome+" "+nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getNome() {
		return nome;
	}

	public String getCf() {
		return cf;
	}

	public CsASoggettoLAZY getSoggettoCaso() {
		return soggettoCaso;
	}

	public boolean isHasDatiSociali() {
		return hasDatiSociali;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public void setSoggettoCaso(CsASoggettoLAZY soggettoCaso) {
		this.soggettoCaso = soggettoCaso;
	}

	public void setHasDatiSociali(boolean hasDatiSociali) {
		this.hasDatiSociali = hasDatiSociali;
	}
	
	
}
