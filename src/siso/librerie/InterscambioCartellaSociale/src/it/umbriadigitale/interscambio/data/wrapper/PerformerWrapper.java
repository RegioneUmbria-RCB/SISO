package it.umbriadigitale.interscambio.data.wrapper;

public class PerformerWrapper {
	private String codiceFiscaleOperatoreErogante;
	private String profiloProfessionaleOperatoreEroganteCode;
	private String nomeOperatoreErogante;
	private String cognomeOperatoreErogante;
	
	
	public PerformerWrapper(String codiceFiscaleOperatoreErogante, String profiloProfessionaleOperatoreEroganteCode,
			String nomeOperatoreErogante, String cognomeOperatoreErogante) {
		
		this.codiceFiscaleOperatoreErogante = codiceFiscaleOperatoreErogante;
		this.profiloProfessionaleOperatoreEroganteCode = profiloProfessionaleOperatoreEroganteCode;
		this.nomeOperatoreErogante = nomeOperatoreErogante;
		this.cognomeOperatoreErogante = cognomeOperatoreErogante;
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
}
