package it.webred.siso.ws.client.agag.marche.dto;

import java.io.Serializable;

public class RicercaAnagraficaDTO implements Serializable{

	 private String cognome;
     private String nome;
     private String sesso;
     private String annoNascita;
     private String cf;
     private String idAssistito;
     private String filtro;
     
	public String getFiltro() {
		return filtro;
	}
	public void setFiltro(String filtro) {
		this.filtro = filtro;
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
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getAnnoNascita() {
		return annoNascita;
	}
	public void setAnnoNascita(String annoNascita) {
		this.annoNascita = annoNascita;
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public String getIdAssistito() {
		return idAssistito;
	}
	public void setIdAssistito(String idAssistito) {
		this.idAssistito = idAssistito;
	}
	
	public String stampaParametri(){
		String s = "PARAMETRI INTERROGAZIONE ANAGRAFE MARCHE "
				+ "COGNOME["+cognome+"] "
				+ "NOME["+nome+"] "
				+ "ANNO["+this.annoNascita+"] "
				+ "SESSO["+sesso+"] "
				+ "IDENTIFICATIVO["+this.idAssistito+"]"
				+ "CF["+cf+"]";
		return s;
	}
}
