package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.DataDwh;

public class SitLicenzeCommercioAnag  extends TabellaDwhMultiProv {

	private String numero;
	private String codiceFiscale;
	private String cognome;
	private String nome;
	private String denominazione;
	private String formaGiuridica;
	private DataDwh dataNascita;
	private String comuneNascita;
	private String provinciaNascita;
	private String indirizzoResidenza;
	private String civicoResidenza;
	private String capResidenza;
	private String comuneResidenza;
	private String provinciaResidenza;
	private DataDwh dataInizioResidenza;
	private String tel;
	private String fax;
	private String email;
	private String piva;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
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

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getFormaGiuridica() {
		return formaGiuridica;
	}

	public void setFormaGiuridica(String formaGiuridica) {
		this.formaGiuridica = formaGiuridica;
	}

	public DataDwh getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(DataDwh dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public String getProvinciaNascita() {
		return provinciaNascita;
	}

	public void setProvinciaNascita(String provinciaNascita) {
		this.provinciaNascita = provinciaNascita;
	}

	public String getIndirizzoResidenza() {
		return indirizzoResidenza;
	}

	public void setIndirizzoResidenza(String indirizzoResidenza) {
		this.indirizzoResidenza = indirizzoResidenza;
	}

	public String getCivicoResidenza() {
		return civicoResidenza;
	}

	public void setCivicoResidenza(String civicoResidenza) {
		this.civicoResidenza = civicoResidenza;
	}

	public String getCapResidenza() {
		return capResidenza;
	}

	public void setCapResidenza(String capResidenza) {
		this.capResidenza = capResidenza;
	}

	public String getComuneResidenza() {
		return comuneResidenza;
	}

	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}

	public String getProvinciaResidenza() {
		return provinciaResidenza;
	}

	public void setProvinciaResidenza(String provinciaResidenza) {
		this.provinciaResidenza = provinciaResidenza;
	}

	public DataDwh getDataInizioResidenza() {
		return dataInizioResidenza;
	}

	public void setDataInizioResidenza(DataDwh dataInizioResidenza) {
		this.dataInizioResidenza = dataInizioResidenza;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPiva() {
		return piva;
	}

	public void setPiva(String piva) {
		this.piva = piva;
	}

	@Override
	public String getValueForCtrHash()
	{
		return this.numero +
		this.codiceFiscale +
		this.cognome +
		this.nome +
		this.denominazione +
		this.formaGiuridica +
		this.dataNascita.getDataFormattata() +
		this.comuneNascita +
		this.provinciaNascita +
		this.indirizzoResidenza +
		this.civicoResidenza +
		this.capResidenza +
		this.comuneResidenza +
		this.provinciaResidenza +
		this.dataInizioResidenza.getDataFormattata() +
		this.tel +
		this.fax +
		this.email +
		this.piva +
		this.getProvenienza();
	}
	
}
