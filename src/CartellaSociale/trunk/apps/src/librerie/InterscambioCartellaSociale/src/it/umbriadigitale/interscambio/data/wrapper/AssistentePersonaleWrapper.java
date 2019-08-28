package it.umbriadigitale.interscambio.data.wrapper;

public class AssistentePersonaleWrapper {
	private String tipologiaCode;
	private String codiceFiscale;
	private String nome;
	private String cognome;
	private String genereCode;
	private String dataNascitaText;
	private String comuneResidenza;
	
	
	public AssistentePersonaleWrapper(String tipologiaCode, String codiceFiscale, String nome, String cognome,
			String genereCode, String dataNascitaText, String comuneResidenza) {
		
		this.tipologiaCode = tipologiaCode;
		this.codiceFiscale = codiceFiscale;
		this.nome = nome;
		this.cognome = cognome;
		this.genereCode = genereCode;
		this.dataNascitaText = dataNascitaText;
		this.comuneResidenza = comuneResidenza;
	}
	
	
	public String getTipologiaCode() {
		return tipologiaCode;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getGenereCode() {
		return genereCode;
	}

	public String getDataNascitaText() {
		return dataNascitaText;
	}

	public String getComuneResidenza() {
		return comuneResidenza;
	}
}