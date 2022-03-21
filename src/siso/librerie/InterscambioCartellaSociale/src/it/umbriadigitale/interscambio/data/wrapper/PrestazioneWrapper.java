package it.umbriadigitale.interscambio.data.wrapper;

public class PrestazioneWrapper {
	private String codPrestazione;
	private String dataInizioPrestazioneText;
	private String dataFinePrestazioneText;
	private String codiceFiscaleOperatoreErogante;
	private String profiloProfessionaleOperatoreEroganteCode;
	private String nomeOperatoreErogante;
	private String cognomeOperatoreErogante;
	private String luogoPrestazioneCode;
	
	
	public PrestazioneWrapper(String codPrestazione, String dataInizioPrestazioneText, String dataFinePrestazioneText,
			String codiceFiscaleOperatoreErogante, String profiloProfessionaleOperatoreEroganteCode,
			String nomeOperatoreErogante, String cognomeOperatoreErogante, String luogoPrestazioneCode) {
		
		this.codPrestazione = codPrestazione;
		this.dataInizioPrestazioneText = dataInizioPrestazioneText;
		this.dataFinePrestazioneText = dataFinePrestazioneText;
		this.codiceFiscaleOperatoreErogante = codiceFiscaleOperatoreErogante;
		this.profiloProfessionaleOperatoreEroganteCode = profiloProfessionaleOperatoreEroganteCode;
		this.nomeOperatoreErogante = nomeOperatoreErogante;
		this.cognomeOperatoreErogante = cognomeOperatoreErogante;
		this.luogoPrestazioneCode = luogoPrestazioneCode;
	}
	
	
	public String getCodPrestazione() {
		return codPrestazione;
	}

	public String getDataInizioPrestazioneText() {
		return dataInizioPrestazioneText;
	}

	public String getDataFinePrestazioneText() {
		return dataFinePrestazioneText;
	}

	public String getCodiceFiscaleOperatoreErogante() {
		return codiceFiscaleOperatoreErogante;
	}

	public String getProfiloProfessionaleOperatoreEroganteCode() {
		return profiloProfessionaleOperatoreEroganteCode;
	}

	public String getNomeOperatoreErogante() {
		return nomeOperatoreErogante;
	}

	public String getCognomeOperatoreErogante() {
		return cognomeOperatoreErogante;
	}

	public String getLuogoPrestazioneCode() {
		return luogoPrestazioneCode;
	}
}