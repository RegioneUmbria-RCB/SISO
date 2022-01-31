package it.escsolution.escwebgis.enel.bean;

public class EnelBean2 {

	
	private EnelBean utente = new EnelBean();
	
	// tabella sit_enel_utenza
	private String codiceFiscaleErogante  ;
	private String codiceUtenza	 ;
	private String codiceMerceologico ;
	private String annoRiferimentoDati;
	private String dataPrimaAttivazione;
	private String tipoUtenza;
	private String  comuneAmmUbicazione;
	private String provinciaAmmUbicazione;
	private String capUbicazione;
	private String comuneCatastale;
	private String codiceComuneCatastale;
	private String indirizzoUbicazione;
	private String mesiFatturazione;
	private String kwhFatturati;
	private String spesaAnnua;
	
	// fields che non vengono trattati nel  vecchio tracciato
	private String  tipoUnita;
	private String  sezione;
	private String foglio;
	private String particella;
	private String estensioneParticella;
	private String tipoParticella;
	private String subalterno;
	private String codiceAssenzaCatasto;
	
	private String id;
	
	public String getCodiceFiscaleErogante() {
		return codiceFiscaleErogante;
	}
	public void setCodiceFiscaleErogante(String codiceFiscaleErogante) {
		this.codiceFiscaleErogante = codiceFiscaleErogante;
	}
	public String getCodiceUtenza() {
		return codiceUtenza;
	}
	public void setCodiceUtenza(String codiceUtenza) {
		this.codiceUtenza = codiceUtenza;
	}
	public String getCodiceMerceologico() {
		return codiceMerceologico;
	}
	public void setCodiceMerceologico(String codiceMerceologico) {
		this.codiceMerceologico = codiceMerceologico;
	}
	public String getAnnoRiferimentoDati() {
		return annoRiferimentoDati;
	}
	public void setAnnoRiferimentoDati(String annoRiferimentoDati) {
		this.annoRiferimentoDati = annoRiferimentoDati;
	}
	public String getDataPrimaAttivazione() {
		return dataPrimaAttivazione;
	}
	public void setDataPrimaAttivazione(String dataPrimaAttivazione) {
		this.dataPrimaAttivazione = dataPrimaAttivazione;
	}
	public String getTipoUtenza() {
		return tipoUtenza;
	}
	public void setTipoUtenza(String tipoUtenza) {
		this.tipoUtenza = tipoUtenza;
	}
	public String getComuneAmmUbicazione() {
		return comuneAmmUbicazione;
	}
	public void setComuneAmmUbicazione(String comuneAmmUbicazione) {
		this.comuneAmmUbicazione = comuneAmmUbicazione;
	}
	public String getProvinciaAmmUbicazione() {
		return provinciaAmmUbicazione;
	}
	public void setProvinciaAmmUbicazione(String provinciaAmmUbicazione) {
		this.provinciaAmmUbicazione = provinciaAmmUbicazione;
	}
	public String getCapUbicazione() {
		return capUbicazione;
	}
	public void setCapUbicazione(String capUbicazione) {
		this.capUbicazione = capUbicazione;
	}
	public String getComuneCatastale() {
		return comuneCatastale;
	}
	public void setComuneCatastale(String comuneCatastale) {
		this.comuneCatastale = comuneCatastale;
	}
	public String getCodiceComuneCatastale() {
		return codiceComuneCatastale;
	}
	public void setCodiceComuneCatastale(String codiceComuneCatastale) {
		this.codiceComuneCatastale = codiceComuneCatastale;
	}
	public String getIndirizzoUbicazione() {
		return indirizzoUbicazione;
	}
	public void setIndirizzoUbicazione(String indirizzoUbicazione) {
		this.indirizzoUbicazione = indirizzoUbicazione;
	}
	public String getMesiFatturazione() {
		return mesiFatturazione;
	}
	public void setMesiFatturazione(String mesiFatturazione) {
		this.mesiFatturazione = mesiFatturazione;
	}
	public String getKwhFatturati() {
		return kwhFatturati;
	}
	public void setKwhFatturati(String kwhFatturati) {
		this.kwhFatturati = kwhFatturati;
	}
	public String getSpesaAnnua() {
		return spesaAnnua;
	}
	public void setSpesaAnnua(String spesaAnnua) {
		this.spesaAnnua = spesaAnnua;
	}
	public String getTipoUnita() {
		return tipoUnita;
	}
	public void setTipoUnita(String tipoUnita) {
		this.tipoUnita = tipoUnita;
	}
	public String getSezione() {
		return sezione;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	public String getFoglio() {
		return foglio;
	}
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	public String getParticella() {
		return particella;
	}
	public void setParticella(String particella) {
		this.particella = particella;
	}
	public String getEstensioneParticella() {
		return estensioneParticella;
	}
	public void setEstensioneParticella(String estensioneParticella) {
		this.estensioneParticella = estensioneParticella;
	}
	public String getTipoParticella() {
		return tipoParticella;
	}
	public void setTipoParticella(String tipoParticella) {
		this.tipoParticella = tipoParticella;
	}
	public String getSubalterno() {
		return subalterno;
	}
	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}
	public String getCodiceAssenzaCatasto() {
		return codiceAssenzaCatasto;
	}
	public void setCodiceAssenzaCatasto(String codiceAssenzaCatasto) {
		this.codiceAssenzaCatasto = codiceAssenzaCatasto;
	}
	public EnelBean getUtente() {
		return utente;
	}
	public void setUtente(EnelBean utente) {
		this.utente = utente;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
}
