package it.webred.ss.web.bean.report.dto;

public class ConoscenzaLinguaItaliana {

	private boolean bambinoInEtaNonScolastica;
	private boolean attestatoOautovalutazione;
	private LinguaAttestato linguaAttestato = new LinguaAttestato();
	private LinguaAutovalutazione linguaAutovalutazione = new LinguaAutovalutazione();
	private String altreLingueConosciute = "-";



	public boolean isBambinoInEtaNonScolastica() {
		return bambinoInEtaNonScolastica;
	}



	public void setBambinoInEtaNonScolastica(boolean bambinoInEtaNonScolastica) {
		this.bambinoInEtaNonScolastica = bambinoInEtaNonScolastica;
	}



	public boolean isAttestatoOautovalutazione() {
		return attestatoOautovalutazione;
	}



	public void setAttestatoOautovalutazione(boolean attestatoOautovalutazione) {
		this.attestatoOautovalutazione = attestatoOautovalutazione;
	}



	public LinguaAttestato getLinguaAttestato() {
		return linguaAttestato;
	}



	public void setLinguaAttestato(LinguaAttestato linguaAttestato) {
		this.linguaAttestato = linguaAttestato;
	}



	public LinguaAutovalutazione getLinguaAutovalutazione() {
		return linguaAutovalutazione;
	}



	public void setLinguaAutovalutazione(LinguaAutovalutazione linguaAutovalutazione) {
		this.linguaAutovalutazione = linguaAutovalutazione;
	}



	public String getAltreLingueConosciute() {
		return altreLingueConosciute;
	}



	public void setAltreLingueConosciute(String altreLingueConosciute) {
		this.altreLingueConosciute = altreLingueConosciute;
	}

}
