package it.webred.ct.data.model.catasto;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CONS_CONS_TAB database table.
 * 
 */
@Entity
@Table(name="CONS_CONS_TAB")
public class ConsConsTab implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ConsConsTabPK id;

	private String allegato;

	@Column(name="ATTO_FIN")
	private BigDecimal attoFin;

	@Column(name="ATTO_INI")
	private BigDecimal attoIni;

	@Column(name="COD_SOGG_CAT")
	private String codSoggCat;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_INIZIO")
	private Date dataInizio;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_REGISTRAZIONE")
	private Date dataRegistrazione;

	@Column(name="FL_TOTALE")
	private String flTotale;

	@Column(name="PERC_POSS")
	private BigDecimal percPoss;

	@Column(name="PKID_CONS")
	private BigDecimal pkidCons;

	@Column(name="PKID_STACK_FIN")
	private BigDecimal pkidStackFin;

	@Column(name="PKID_STACK_INI")
	private BigDecimal pkidStackIni;

	private BigDecimal sorgente;

	private String sviluppo;

    @Temporal( TemporalType.DATE)
	private Date timestamp;

	@Column(name="TIPO_DOCUMENTO")
	private String tipoDocumento;

	@Column(name="TIPO_TITOLO")
	private String tipoTitolo;

	@Column(name="TIT_NO_COD")
	private String titNoCod;

	private BigDecimal unimm;

	private String utente;

	@Column(name="VALIDO_FIN")
	private String validoFin;

	@Column(name="VALIDO_INI")
	private String validoIni;

    public ConsConsTab() {
    }

	public ConsConsTabPK getId() {
		return this.id;
	}

	public void setId(ConsConsTabPK id) {
		this.id = id;
	}
	
	public String getAllegato() {
		return this.allegato;
	}

	public void setAllegato(String allegato) {
		this.allegato = allegato;
	}

	public BigDecimal getAttoFin() {
		return this.attoFin;
	}

	public void setAttoFin(BigDecimal attoFin) {
		this.attoFin = attoFin;
	}

	public BigDecimal getAttoIni() {
		return this.attoIni;
	}

	public void setAttoIni(BigDecimal attoIni) {
		this.attoIni = attoIni;
	}

	public String getCodSoggCat() {
		return this.codSoggCat;
	}

	public void setCodSoggCat(String codSoggCat) {
		this.codSoggCat = codSoggCat;
	}

	public Date getDataInizio() {
		return this.dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataRegistrazione() {
		return this.dataRegistrazione;
	}

	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public String getFlTotale() {
		return this.flTotale;
	}

	public void setFlTotale(String flTotale) {
		this.flTotale = flTotale;
	}

	public BigDecimal getPercPoss() {
		return this.percPoss;
	}

	public void setPercPoss(BigDecimal percPoss) {
		this.percPoss = percPoss;
	}

	public BigDecimal getPkidCons() {
		return this.pkidCons;
	}

	public void setPkidCons(BigDecimal pkidCons) {
		this.pkidCons = pkidCons;
	}

	public BigDecimal getPkidStackFin() {
		return this.pkidStackFin;
	}

	public void setPkidStackFin(BigDecimal pkidStackFin) {
		this.pkidStackFin = pkidStackFin;
	}

	public BigDecimal getPkidStackIni() {
		return this.pkidStackIni;
	}

	public void setPkidStackIni(BigDecimal pkidStackIni) {
		this.pkidStackIni = pkidStackIni;
	}

	public BigDecimal getSorgente() {
		return this.sorgente;
	}

	public void setSorgente(BigDecimal sorgente) {
		this.sorgente = sorgente;
	}

	public String getSviluppo() {
		return this.sviluppo;
	}

	public void setSviluppo(String sviluppo) {
		this.sviluppo = sviluppo;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getTipoDocumento() {
		return this.tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getTipoTitolo() {
		return this.tipoTitolo;
	}

	public void setTipoTitolo(String tipoTitolo) {
		this.tipoTitolo = tipoTitolo;
	}

	public String getTitNoCod() {
		return this.titNoCod;
	}

	public void setTitNoCod(String titNoCod) {
		this.titNoCod = titNoCod;
	}

	public BigDecimal getUnimm() {
		return this.unimm;
	}

	public void setUnimm(BigDecimal unimm) {
		this.unimm = unimm;
	}

	public String getUtente() {
		return this.utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public String getValidoFin() {
		return this.validoFin;
	}

	public void setValidoFin(String validoFin) {
		this.validoFin = validoFin;
	}

	public String getValidoIni() {
		return this.validoIni;
	}

	public void setValidoIni(String validoIni) {
		this.validoIni = validoIni;
	}

}