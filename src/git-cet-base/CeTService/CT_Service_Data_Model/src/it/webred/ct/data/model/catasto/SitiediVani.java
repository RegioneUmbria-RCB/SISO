package it.webred.ct.data.model.catasto;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SITIEDI_VANI database table.
 * 
 */

@Entity
@Table(name="SITIEDI_VANI")
public class SitiediVani implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal altezzamax;

	private BigDecimal altezzamin;

	private String ambiente;

	@Column(name="COD_NAZIONALE")
	private String codNazionale;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_FINE_VAL")
	private Date dataFineVal;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_INIZIO_VAL")
	private Date dataInizioVal;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_LAV")
	private Date dataLav;

	private BigDecimal edificio;

	private BigDecimal foglio;

	private String fonte;

	@Column(name="NOME_VANO")
	private String nomeVano;

	private String particella;

	private String piano;

	@Id
	@Column(name="SE_ROW_ID")
	private BigDecimal seRowId;

	@Transient
	private Object shape;

	private String sub;

	@Column(name="SUPE_AMBIENTE")
	private BigDecimal supeAmbiente;

	private BigDecimal unimm;

	private String utente;

	@Column(name="UTENTE_FINE")
	private String utenteFine;

	private BigDecimal vano;

    public SitiediVani() {
    }

	public BigDecimal getAltezzamax() {
		return this.altezzamax;
	}

	public void setAltezzamax(BigDecimal altezzamax) {
		this.altezzamax = altezzamax;
	}

	public BigDecimal getAltezzamin() {
		return this.altezzamin;
	}

	public void setAltezzamin(BigDecimal altezzamin) {
		this.altezzamin = altezzamin;
	}

	public String getAmbiente() {
		return this.ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public String getCodNazionale() {
		return this.codNazionale;
	}

	public void setCodNazionale(String codNazionale) {
		this.codNazionale = codNazionale;
	}

	public Date getDataFineVal() {
		return this.dataFineVal;
	}

	public void setDataFineVal(Date dataFineVal) {
		this.dataFineVal = dataFineVal;
	}

	public Date getDataInizioVal() {
		return this.dataInizioVal;
	}

	public void setDataInizioVal(Date dataInizioVal) {
		this.dataInizioVal = dataInizioVal;
	}

	public Date getDataLav() {
		return this.dataLav;
	}

	public void setDataLav(Date dataLav) {
		this.dataLav = dataLav;
	}

	public BigDecimal getEdificio() {
		return this.edificio;
	}

	public void setEdificio(BigDecimal edificio) {
		this.edificio = edificio;
	}

	public BigDecimal getFoglio() {
		return this.foglio;
	}

	public void setFoglio(BigDecimal foglio) {
		this.foglio = foglio;
	}

	public String getFonte() {
		return this.fonte;
	}

	public void setFonte(String fonte) {
		this.fonte = fonte;
	}

	public String getNomeVano() {
		return this.nomeVano;
	}

	public void setNomeVano(String nomeVano) {
		this.nomeVano = nomeVano;
	}

	public String getParticella() {
		return this.particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getPiano() {
		return this.piano;
	}

	public void setPiano(String piano) {
		this.piano = piano;
	}

	public BigDecimal getSeRowId() {
		return this.seRowId;
	}

	public void setSeRowId(BigDecimal seRowId) {
		this.seRowId = seRowId;
	}

	public Object getShape() {
		return this.shape;
	}

	public void setShape(Object shape) {
		this.shape = shape;
	}

	public String getSub() {
		return this.sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public BigDecimal getSupeAmbiente() {
		return this.supeAmbiente;
	}

	public void setSupeAmbiente(BigDecimal supeAmbiente) {
		this.supeAmbiente = supeAmbiente;
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

	public String getUtenteFine() {
		return this.utenteFine;
	}

	public void setUtenteFine(String utenteFine) {
		this.utenteFine = utenteFine;
	}

	public BigDecimal getVano() {
		return this.vano;
	}

	public void setVano(BigDecimal vano) {
		this.vano = vano;
	}

}