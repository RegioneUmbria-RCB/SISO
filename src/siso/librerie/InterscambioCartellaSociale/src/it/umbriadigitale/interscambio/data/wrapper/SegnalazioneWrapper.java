package it.umbriadigitale.interscambio.data.wrapper;

public class SegnalazioneWrapper {
	private String nomeSegnalante;
	private String cognomeSegnalante;
	private String codiceFiscaleSegnalante;
	private String dataSegnalazioneText;
	private String tipologiaEnteSegnalanteCode;
	private String codiceEnteSegnalante;
	private String denominazioneEnteSegnalante;
	private String bisognoEspressoCode;
	
	
	public SegnalazioneWrapper(String nomeSegnalante, String cognomeSegnalante, String codiceFiscaleSegnalante,
			String dataSegnalazioneText, String tipologiaEnteSegnalanteCode, String codiceEnteSegnalante,
			String denominazioneEnteSegnalante, String bisognoEspressoCode) {
		
		this.nomeSegnalante = nomeSegnalante;
		this.cognomeSegnalante = cognomeSegnalante;
		this.codiceFiscaleSegnalante = codiceFiscaleSegnalante;
		this.dataSegnalazioneText = dataSegnalazioneText;
		this.tipologiaEnteSegnalanteCode = tipologiaEnteSegnalanteCode;
		this.codiceEnteSegnalante = codiceEnteSegnalante;
		this.denominazioneEnteSegnalante = denominazioneEnteSegnalante;
		this.bisognoEspressoCode = bisognoEspressoCode;
	}
	
	
	public String getNomeSegnalante() {
		return nomeSegnalante;
	}

	public String getCognomeSegnalante() {
		return cognomeSegnalante;
	}

	public String getCodiceFiscaleSegnalante() {
		return codiceFiscaleSegnalante;
	}

	public String getDataSegnalazioneText() {
		return dataSegnalazioneText;
	}

	public String getTipologiaEnteSegnalanteCode() {
		return tipologiaEnteSegnalanteCode;
	}

	public String getCodiceEnteSegnalante() {
		return codiceEnteSegnalante;
	}

	public String getDenominazioneEnteSegnalante() {
		return denominazioneEnteSegnalante;
	}

	public String getBisognoEspressoCode() {
		return bisognoEspressoCode;
	}
}