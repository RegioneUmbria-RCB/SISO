package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.DataDwh;

public class SitTCosapContrib extends TabellaDwhMultiProv {
	
	private String tipoPersona;
	private String nome;
	private String cogDenom;
	private String codiceFiscale;
	private String partitaIva;
	private DataDwh dtNascita;
	private String codBelfioreNascita;
	private String descComuneNascita;
	private String codBelfioreResidenza;
	private String descComuneResidenza;
	private String capResidenza;
	private String codiceVia;
	private String sedime;
	private String indirizzo;
	private String civico;
	private DataDwh dtIscrArchivio;
	private DataDwh dtCostitSoggetto;

	public String getCapResidenza() {
		return capResidenza;
	}

	public void setCapResidenza(String capResidenza) {
		this.capResidenza = capResidenza;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCogDenom() {
		return cogDenom;
	}

	public void setCogDenom(String cogDenom) {
		this.cogDenom = cogDenom;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public DataDwh getDtNascita() {
		return dtNascita;
	}

	public void setDtNascita(DataDwh dtNascita) {
		this.dtNascita = dtNascita;
	}

	public String getCodBelfioreNascita() {
		return codBelfioreNascita;
	}

	public void setCodBelfioreNascita(String codBelfioreNascita) {
		this.codBelfioreNascita = codBelfioreNascita;
	}

	public String getDescComuneNascita() {
		return descComuneNascita;
	}

	public void setDescComuneNascita(String descComuneNascita) {
		this.descComuneNascita = descComuneNascita;
	}

	public String getCodBelfioreResidenza() {
		return codBelfioreResidenza;
	}

	public void setCodBelfioreResidenza(String codBelfioreResidenza) {
		this.codBelfioreResidenza = codBelfioreResidenza;
	}

	public String getDescComuneResidenza() {
		return descComuneResidenza;
	}

	public void setDescComuneResidenza(String descComuneResidenza) {
		this.descComuneResidenza = descComuneResidenza;
	}

	public String getCodiceVia() {
		return codiceVia;
	}

	public void setCodiceVia(String codiceVia) {
		this.codiceVia = codiceVia;
	}

	public String getSedime() {
		return sedime;
	}

	public void setSedime(String sedime) {
		this.sedime = sedime;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public DataDwh getDtIscrArchivio() {
		return dtIscrArchivio;
	}

	public void setDtIscrArchivio(DataDwh dtIscrArchivio) {
		this.dtIscrArchivio = dtIscrArchivio;
	}

	public DataDwh getDtCostitSoggetto() {
		return dtCostitSoggetto;
	}

	public void setDtCostitSoggetto(DataDwh dtCostitSoggetto) {
		this.dtCostitSoggetto = dtCostitSoggetto;
	}
	
	@Override
	public String getValueForCtrHash()
	{
		return this.tipoPersona +
		this.nome +
		this.cogDenom +
		this.codiceFiscale +
		this.partitaIva +
		this.dtNascita.getDataFormattata() +
		this.codBelfioreNascita +
		this.descComuneNascita +
		this.codBelfioreResidenza +
		this.descComuneResidenza +
		this.capResidenza +
		this.codiceVia +
		this.sedime +
		this.indirizzo +
		this.civico +
		this.dtIscrArchivio.getDataFormattata() +
		this.dtCostitSoggetto.getDataFormattata() +
		this.getProvenienza();
	}

}
