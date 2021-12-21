package it.webred.cs.csa.ejb.dto.pai.pti;

public enum InserimentoMinoreStrutturaEnum {
	INS("INSERITO DALLA STRUTTURA"), PRESA_IN_CARICO("INSERIMENTO COMPLETO PROCESSATO"),
	PRESA_IN_CARICO_NO_DOC("INSERIMENTO PARZIALE PROCESSATO");

	private String descrizione;

	private InserimentoMinoreStrutturaEnum(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizione() {
		return descrizione;
	}

}
