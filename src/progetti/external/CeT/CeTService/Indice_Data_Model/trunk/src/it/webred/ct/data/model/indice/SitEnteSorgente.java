package it.webred.ct.data.model.indice;

import java.io.Serializable;

import java.math.BigDecimal;


/**
 * The persistent class for the SIT_ENTE_SORGENTE database table.
 * 
 */
public class SitEnteSorgente implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;

	private BigDecimal civiciRiferimento;

	private String codSorgente;

	private String descrizione;

	private BigDecimal disabilitaStorico;

	private BigDecimal fkEnte;

	private BigDecimal fornituraInReplace;

	private BigDecimal oggettiRiferimento;

	private BigDecimal soggettiRiferimento;

	private String tipoFonte;

	private BigDecimal vieRiferimento;

    public SitEnteSorgente() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getCiviciRiferimento() {
		return this.civiciRiferimento;
	}

	public void setCiviciRiferimento(BigDecimal civiciRiferimento) {
		this.civiciRiferimento = civiciRiferimento;
	}

	public String getCodSorgente() {
		return this.codSorgente;
	}

	public void setCodSorgente(String codSorgente) {
		this.codSorgente = codSorgente;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public BigDecimal getDisabilitaStorico() {
		return this.disabilitaStorico;
	}

	public void setDisabilitaStorico(BigDecimal disabilitaStorico) {
		this.disabilitaStorico = disabilitaStorico;
	}

	public BigDecimal getFkEnte() {
		return this.fkEnte;
	}

	public void setFkEnte(BigDecimal fkEnte) {
		this.fkEnte = fkEnte;
	}

	public BigDecimal getFornituraInReplace() {
		return this.fornituraInReplace;
	}

	public void setFornituraInReplace(BigDecimal fornituraInReplace) {
		this.fornituraInReplace = fornituraInReplace;
	}

	public BigDecimal getOggettiRiferimento() {
		return this.oggettiRiferimento;
	}

	public void setOggettiRiferimento(BigDecimal oggettiRiferimento) {
		this.oggettiRiferimento = oggettiRiferimento;
	}

	public BigDecimal getSoggettiRiferimento() {
		return this.soggettiRiferimento;
	}

	public void setSoggettiRiferimento(BigDecimal soggettiRiferimento) {
		this.soggettiRiferimento = soggettiRiferimento;
	}

	public String getTipoFonte() {
		return this.tipoFonte;
	}

	public void setTipoFonte(String tipoFonte) {
		this.tipoFonte = tipoFonte;
	}

	public BigDecimal getVieRiferimento() {
		return this.vieRiferimento;
	}

	public void setVieRiferimento(BigDecimal vieRiferimento) {
		this.vieRiferimento = vieRiferimento;
	}

}