package it.webred.ct.data.access.basic.anagrafe.dto;

import java.io.Serializable;
import java.util.Date;
//se servono altri datiper soggetto dall'anagrafe, aggiungi qui
public class SoggettoAnagrafeDTO extends AnagrafeBaseDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String cognome;
	private String nome;
	private Date dtNas;
	private String codFis;
	private String idExtComNas;
	private String desComNas;
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
	public String getIdExtComNas() {
		return idExtComNas;
	}
	public void setIdExtComNas(String idExtComNas) {
		this.idExtComNas = idExtComNas;
	}
	public String getDesComNas() {
		return desComNas;
	}
	public void setDesComNas(String desComNas) {
		this.desComNas = desComNas;
	}
	public String getCodFis() {
		return codFis;
	}
	public void setCodFis(String codFis) {
		this.codFis = codFis;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
