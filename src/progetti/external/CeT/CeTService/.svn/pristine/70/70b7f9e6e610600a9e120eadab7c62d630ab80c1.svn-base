package it.webred.ct.data.model.catasto;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SITICONDUZ_IMM_ALL database table.
 * 
 */
@Entity
@Table(name="SITICONDUZ_IMM_ALL")
public class SiticonduzImmAll implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private SiticonduzImmAllPK id;
	
	@Column(name="ATTO_FINE")
	private BigDecimal attoFine;

	@Column(name="ATTO_INIZIO")
	private BigDecimal attoInizio;

	private String cuaa;
   
    @Temporal( TemporalType.DATE)
	@Column(name="DATA_INIZIO")
	private Date dataInizio;

	@Column(name="FL_TOTALE")
	private String flTotale;

	@Column(name="PERC_POSS")
	private BigDecimal percPoss;

	@Column(name="PKID_TIT")
	private BigDecimal pkidTit;

	@Column(name="TIPO_DOCUMENTO")
	private String tipoDocumento;

	@Column(name="TIPO_TITOLO")
	private String tipoTitolo;

	@Column(name="TIT_NO_COD")
	private String titNoCod;

	@Column(name="VALIDO_FINE")
	private String validoFine;

	@Column(name="VALIDO_INIZIO")
	private String validoInizio;

    public SiticonduzImmAll() {
    }
    
    public SiticonduzImmAllPK getId() {
		return id;
	}

	public void setId(SiticonduzImmAllPK id) {
		this.id = id;
	}

	public BigDecimal getAttoFine() {
		return this.attoFine;
	}

	public void setAttoFine(BigDecimal attoFine) {
		this.attoFine = attoFine;
	}

	public BigDecimal getAttoInizio() {
		return this.attoInizio;
	}

	public void setAttoInizio(BigDecimal attoInizio) {
		this.attoInizio = attoInizio;
	}

	public String getCuaa() {
		return this.cuaa;
	}

	public void setCuaa(String cuaa) {
		this.cuaa = cuaa;
	}

	public Date getDataInizio() {
		return this.dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
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

	public BigDecimal getPkidTit() {
		return this.pkidTit;
	}

	public void setPkidTit(BigDecimal pkidTit) {
		this.pkidTit = pkidTit;
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

	public String getValidoFine() {
		return this.validoFine;
	}

	public void setValidoFine(String validoFine) {
		this.validoFine = validoFine;
	}

	public String getValidoInizio() {
		return this.validoInizio;
	}

	public void setValidoInizio(String validoInizio) {
		this.validoInizio = validoInizio;
	}

}