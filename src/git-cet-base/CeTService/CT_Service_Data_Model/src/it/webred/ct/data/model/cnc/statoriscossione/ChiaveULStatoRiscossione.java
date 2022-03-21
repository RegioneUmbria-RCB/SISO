package it.webred.ct.data.model.cnc.statoriscossione;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ChiaveULStatoRiscossione implements Serializable {

	@Column(name="COD_AMBITO")
	private String codAmbito;

	@Column(name="COD_ENTE_CREDITORE")
	private String codEnteCreditore;

	@Column(name="PROGRESSIVO_RUOLO")
	private String progressivoRuolo;

	@Column(name="ANNO_RUOLO")
	private String annoRuolo;

	@Column(name="COD_PARTITA")
	private String codPartita;

	@Column(name="PROGRESSIVO_ARTICOLO_RUOLO")
	private String progressivoArticoloRuolo;

	@Column(name="DT_REGISTR_INFORMAZ")
	private String dtRegistrInformaz;

	@Column(name="NUMERO_OPERAZ_CONTAB")
	private String numeroOperazContab;

	@Column(name="PROGRESSIVO_OPERAZ_CONTAB")
	private BigDecimal progressivoOperazContab;

	public String getAnnoRuolo() {
		return this.annoRuolo;
	}

	public void setAnnoRuolo(String annoRuolo) {
		this.annoRuolo = annoRuolo;
	}

	public String getCodAmbito() {
		return this.codAmbito;
	}

	public void setCodAmbito(String codAmbito) {
		this.codAmbito = codAmbito;
	}
	
	public String getCodEnteCreditore() {
		return this.codEnteCreditore;
	}

	public void setCodEnteCreditore(String codEnteCreditore) {
		this.codEnteCreditore = codEnteCreditore;
	}

	public String getCodPartita() {
		return this.codPartita;
	}

	public void setCodPartita(String codPartita) {
		this.codPartita = codPartita;
	}

	public String getDtRegistrInformaz() {
		return this.dtRegistrInformaz;
	}

	public void setDtRegistrInformaz(String dtRegistrInformaz) {
		this.dtRegistrInformaz = dtRegistrInformaz;
	}

	public String getNumeroOperazContab() {
		return this.numeroOperazContab;
	}

	public void setNumeroOperazContab(String numeroOperazContab) {
		this.numeroOperazContab = numeroOperazContab;
	}

	public String getProgressivoArticoloRuolo() {
		return this.progressivoArticoloRuolo;
	}

	public void setProgressivoArticoloRuolo(String progressivoArticoloRuolo) {
		this.progressivoArticoloRuolo = progressivoArticoloRuolo;
	}

	public BigDecimal getProgressivoOperazContab() {
		return this.progressivoOperazContab;
	}

	public void setProgressivoOperazContab(BigDecimal progressivoOperazContab) {
		this.progressivoOperazContab = progressivoOperazContab;
	}

	public String getProgressivoRuolo() {
		return this.progressivoRuolo;
	}

	public void setProgressivoRuolo(String progressivoRuolo) {
		this.progressivoRuolo = progressivoRuolo;
	}

}
