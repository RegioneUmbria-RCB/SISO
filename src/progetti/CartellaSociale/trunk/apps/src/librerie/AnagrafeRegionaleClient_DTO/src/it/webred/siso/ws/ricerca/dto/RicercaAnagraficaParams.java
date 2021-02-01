package it.webred.siso.ws.ricerca.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class RicercaAnagraficaParams extends CeTBaseObject{

	 private String cognome;
     private String nome;
     private String sesso;
     private Integer annoNascitaDa;
     private Integer annoNascitaA;
     
     private String cf;
     
     private String identificativo;
     private String provenienza;
     
     private boolean dettaglio;
     private boolean caricaMedico=false;
     
     private String filtro;

     public RicercaAnagraficaParams(String provenienza, boolean dettaglio){
    	 this.provenienza=provenienza;
    	 this.dettaglio = dettaglio;
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

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getIdentificativo() {
		return identificativo;
	}

	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}

	public String getProvenienza() {
		return provenienza;
	}

	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public Integer getAnnoNascitaDa() {
		return annoNascitaDa;
	}

	public void setAnnoNascitaDa(Integer annoNascitaDa) {
		this.annoNascitaDa = annoNascitaDa;
	}

	public Integer getAnnoNascitaA() {
		return annoNascitaA;
	}

	public void setAnnoNascitaA(Integer annoNascitaA) {
		this.annoNascitaA = annoNascitaA;
	}

	public boolean isDettaglio() {
		return dettaglio;
	}

	public void setDettaglio(boolean dettaglio) {
		this.dettaglio = dettaglio;
	}
	public boolean isCaricaMedico() {
		return caricaMedico;
	}

	public void setCaricaMedico(boolean caricaMedico) {
		this.caricaMedico = caricaMedico;
	}

	public String stampaParametri(){
		String s = "PARAMETRI INTERROGAZIONE ANAGRAFE "
				+ "COGNOME["+cognome+"] "
				+ "NOME["+nome+"] "
				+ "ANNO INIZIALE["+this.annoNascitaDa+"] "
				+ "ANNO FINALE["+this.annoNascitaA+"] "
				+ "SESSO["+sesso+"] "
				+ "IDENTIFICATIVO["+identificativo+"]"
				+ "PROVENIENZA["+provenienza+"]"
				+ "CF["+cf+"]";
		return s;
	}
   
}
