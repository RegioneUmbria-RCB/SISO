package it.umbriadigitale.interscambio.data.wrapper;

public class MedicoPediatraWrapper {
	private String nome;
	private String cognome;
	private String codiceFiscale;
	
	
	public MedicoPediatraWrapper(String nome, String cognome, String codiceFiscale) {
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
	}
	
	
	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}
}
