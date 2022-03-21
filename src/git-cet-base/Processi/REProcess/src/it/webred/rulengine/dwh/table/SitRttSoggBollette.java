package it.webred.rulengine.dwh.table;

import java.math.BigDecimal;
import it.webred.rulengine.dwh.def.DataDwh;

public class SitRttSoggBollette extends TabellaDwh {

	private String codSoggetto;
	private String codiceFiscale;
	private BigDecimal provenienza;
	private String cognome;
	private String nome;
	private BigDecimal sesso;
	private DataDwh dataNascita;
	private String partitaIva;
	private String comuneNascita;
	private String localitaNascita;

	@Override
	public String getValueForCtrHash() {
		try {
			return codSoggetto
					+ codiceFiscale
					+ (provenienza != null? provenienza.toString(): "")
					+ cognome.toString()
					+ (nome != null? nome.toString(): "")
					+ (sesso != null? sesso.toString(): "")
					+ (dataNascita != null? dataNascita.getDataFormattata(): "")
					+ partitaIva
					+ comuneNascita
					+ localitaNascita;
		} catch (Exception e) {
			return null;
		}

	}

	public String getCodSoggetto() {
		return codSoggetto;
	}

	public void setCodSoggetto(String codSoggetto) {
		this.codSoggetto = codSoggetto;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public BigDecimal getProvenienza() {
		return provenienza;
	}

	public void setProvenienza(BigDecimal provenienza) {
		this.provenienza = provenienza;
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

	public BigDecimal getSesso() {
		return sesso;
	}

	public void setSesso(BigDecimal sesso) {
		this.sesso = sesso;
	}

	public DataDwh getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(DataDwh dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}


	public String getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public String getLocalitaNascita() {
		return localitaNascita;
	}

	public void setLocalitaNascita(String localitaNascita) {
		this.localitaNascita = localitaNascita;
	}

}
