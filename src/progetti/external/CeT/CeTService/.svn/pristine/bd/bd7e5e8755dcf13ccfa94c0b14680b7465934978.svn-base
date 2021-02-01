package it.webred.ct.config.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the AM_TAB_COMUNI database table.
 * 
 */
@Entity
@Table(name="AM_TAB_COMUNI")
public class AmTabComuni implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="COD_ISTAT_COMUNE")
	private String codIstatComune;

	private String annotazione;

	private Boolean attivo;

	private String cap;

	@Column(name="COD_COMUNE_TRIBUNALE")
	private String codComuneTribunale;

	@Column(name="COD_ISTAT_REGIONE")
	private String codIstatRegione;

	@Column(name="COD_NAZIONALE")
	private String codNazionale;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_INIZIO_VAL")
	private Date dataCostituzione;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_FINE_VAL")
	private Date dataVariazione;

	@Column(name="DENO_PROV")
	private String denoProv;

	private String denominazione;

	private String denominazione2;

	@Column(name="MOTIVO_NONATTIVO")
	private String motivoNonattivo;

	@Column(name="SIGLA_PROV")
	private String siglaProv;

    public AmTabComuni() {
    }

	public String getCodIstatComune() {
		return this.codIstatComune;
	}

	public void setCodIstatComune(String codIstatComune) {
		this.codIstatComune = codIstatComune;
	}

	public String getAnnotazione() {
		return this.annotazione;
	}

	public void setAnnotazione(String annotazione) {
		this.annotazione = annotazione;
	}

	public Boolean getAttivo() {
		return attivo;
	}

	public void setAttivo(Boolean attivo) {
		this.attivo = attivo;
	}

	public String getCap() {
		return this.cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCodComuneTribunale() {
		return this.codComuneTribunale;
	}

	public void setCodComuneTribunale(String codComuneTribunale) {
		this.codComuneTribunale = codComuneTribunale;
	}

	public String getCodIstatRegione() {
		return this.codIstatRegione;
	}

	public void setCodIstatRegione(String codIstatRegione) {
		this.codIstatRegione = codIstatRegione;
	}

	public String getCodNazionale() {
		return this.codNazionale;
	}

	public void setCodNazionale(String codNazionale) {
		this.codNazionale = codNazionale;
	}

	public Date getDataCostituzione() {
		return this.dataCostituzione;
	}

	public void setDataCostituzione(Date dataCostituzione) {
		this.dataCostituzione = dataCostituzione;
	}

	public Date getDataVariazione() {
		return this.dataVariazione;
	}

	public void setDataVariazione(Date dataVariazione) {
		this.dataVariazione = dataVariazione;
	}

	public String getDenoProv() {
		return this.denoProv;
	}

	public void setDenoProv(String denoProv) {
		this.denoProv = denoProv;
	}

	public String getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getDenominazione2() {
		return this.denominazione2;
	}

	public void setDenominazione2(String denominazione2) {
		this.denominazione2 = denominazione2;
	}

	public String getMotivoNonattivo() {
		return this.motivoNonattivo;
	}

	public void setMotivoNonattivo(String motivoNonattivo) {
		this.motivoNonattivo = motivoNonattivo;
	}

	public String getSiglaProv() {
		return this.siglaProv;
	}

	public void setSiglaProv(String siglaProv) {
		this.siglaProv = siglaProv;
	}

}