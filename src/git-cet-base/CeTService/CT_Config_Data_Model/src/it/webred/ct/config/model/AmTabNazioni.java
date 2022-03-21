package it.webred.ct.config.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the AM_TAB_NAZIONI database table.
 * 
 */
@Entity
@Table(name="AM_TAB_NAZIONI")
public class AmTabNazioni implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String codice;

	@Column(name="COD_ISTAT_NAZIONE")
	private String codIstatNazione;

	@Column(name="COD_NAZIO_CIE")
	private String codNazioCie;

	@Column(name="COD_NAZIO_MOTOR")
	private String codNazioMotor;

	@Column(name="COD_NAZIONE_ANAGRAFE")
	private String codNazioneAnagrafe;

	@Column(name="COD_TERR_ESTERO_PER_AIRE")
	private String codTerrEsteroPerAire;

	private String filler;

	@Column(name="FLAG_CEE")
	private String flagCee;

	private String nazionalita;

	private String nazione;

	private String sigla;
	
	private String iso3166;
	
	@Column(name="COD_AG_ENTRATE")
	private String codAgEntrate;

	private Boolean attivo;
	
	@Column(name="NAZIONALITA_PRINCIPALE")
	private Boolean nazionalitaPrincipale;
	
    public AmTabNazioni() {
    }

	public String getCodice() {
		return this.codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getCodIstatNazione() {
		return this.codIstatNazione;
	}

	public void setCodIstatNazione(String codIstatNazione) {
		this.codIstatNazione = codIstatNazione;
	}

	public String getCodNazioCie() {
		return this.codNazioCie;
	}

	public void setCodNazioCie(String codNazioCie) {
		this.codNazioCie = codNazioCie;
	}

	public String getCodNazioMotor() {
		return this.codNazioMotor;
	}

	public void setCodNazioMotor(String codNazioMotor) {
		this.codNazioMotor = codNazioMotor;
	}

	public String getCodNazioneAnagrafe() {
		return this.codNazioneAnagrafe;
	}

	public void setCodNazioneAnagrafe(String codNazioneAnagrafe) {
		this.codNazioneAnagrafe = codNazioneAnagrafe;
	}

	public String getCodTerrEsteroPerAire() {
		return this.codTerrEsteroPerAire;
	}

	public void setCodTerrEsteroPerAire(String codTerrEsteroPerAire) {
		this.codTerrEsteroPerAire = codTerrEsteroPerAire;
	}

	public String getFiller() {
		return this.filler;
	}

	public void setFiller(String filler) {
		this.filler = filler;
	}

	public String getFlagCee() {
		return this.flagCee;
	}

	public void setFlagCee(String flagCee) {
		this.flagCee = flagCee;
	}

	public String getNazionalita() {
		return this.nazionalita;
	}

	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}

	public String getNazione() {
		return this.nazione;
	}

	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	public String getSigla() {
		return this.sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getIso3166() {
		return iso3166;
	}

	public void setIso3166(String iso3166) {
		this.iso3166 = iso3166;
	}

	public String getCodAgEntrate() {
		return codAgEntrate;
	}

	public void setCodAgEntrate(String codAgEntrate) {
		this.codAgEntrate = codAgEntrate;
	}

	public Boolean getAttivo() {
		return attivo;
	}

	public void setAttivo(Boolean attivo) {
		this.attivo = attivo;
	}

	public Boolean getNazionalitaPrincipale() {
		return nazionalitaPrincipale;
	}

	public void setNazionalitaPrincipale(Boolean nazionalitaPrincipale) {
		this.nazionalitaPrincipale = nazionalitaPrincipale;
	}
	
}