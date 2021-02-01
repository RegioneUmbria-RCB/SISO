package it.webred.ct.data.model.catasto;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_CAT_PLANIMETRIE_C340 database table.
 * 
 */
@Entity
@Table(name="SIT_CAT_PLANIMETRIE_C340")
public class SitCatPlanimetrieC340 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CODICE_AMMINISTRATIVO")
	private String codiceAmministrativo;

	private BigDecimal denominatore;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

	private String foglio;

	@Id
	@Column(name="IDENTIFICATIVO_IMMOBILE")
	private BigDecimal identificativoImmobile;

	@Column(name="NOME_FILE_PLANIMETRICO")
	private String nomeFilePlanimetrico;

	private String numero;

	private String processid;

	@Column(name="RE_FLAG_ELABORATO")
	private String reFlagElaborato;

	private String sezione;

	@Column(name="SEZIONE_URBANA")
	private String sezioneUrbana;

	private String subalterno;

    public SitCatPlanimetrieC340() {
    }

	public String getCodiceAmministrativo() {
		return this.codiceAmministrativo;
	}

	public void setCodiceAmministrativo(String codiceAmministrativo) {
		this.codiceAmministrativo = codiceAmministrativo;
	}

	public BigDecimal getDenominatore() {
		return this.denominatore;
	}

	public void setDenominatore(BigDecimal denominatore) {
		this.denominatore = denominatore;
	}

	public Date getDtExpDato() {
		return this.dtExpDato;
	}

	public void setDtExpDato(Date dtExpDato) {
		this.dtExpDato = dtExpDato;
	}

	public String getFoglio() {
		return this.foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public BigDecimal getIdentificativoImmobile() {
		return this.identificativoImmobile;
	}

	public void setIdentificativoImmobile(BigDecimal identificativoImmobile) {
		this.identificativoImmobile = identificativoImmobile;
	}

	public String getNomeFilePlanimetrico() {
		return this.nomeFilePlanimetrico;
	}

	public void setNomeFilePlanimetrico(String nomeFilePlanimetrico) {
		this.nomeFilePlanimetrico = nomeFilePlanimetrico;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getReFlagElaborato() {
		return this.reFlagElaborato;
	}

	public void setReFlagElaborato(String reFlagElaborato) {
		this.reFlagElaborato = reFlagElaborato;
	}

	public String getSezione() {
		return this.sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public String getSezioneUrbana() {
		return this.sezioneUrbana;
	}

	public void setSezioneUrbana(String sezioneUrbana) {
		this.sezioneUrbana = sezioneUrbana;
	}

	public String getSubalterno() {
		return this.subalterno;
	}

	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}

}