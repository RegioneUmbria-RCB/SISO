package it.webred.ct.data.model.catasto;

import it.webred.ct.data.model.annotation.IndiceEntity;
import it.webred.ct.data.model.annotation.IndiceKey;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SITICIVI database table.
 * 
 */

@Entity
@IndiceEntity(sorgente="4")
public class Siticivi implements Serializable {
	private static final long serialVersionUID = 1L;

	private String civico;

	@Column(name="COD_NAZIONALE")
	private String codNazionale;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_FINE_VAL")
	private Date dataFineVal;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_INIZIO_VAL")
	private Date dataInizioVal;

	@Column(name="ID_TRASF")
	private BigDecimal idTrasf;

	@Column(name="ID_TRASF_FINE")
	private BigDecimal idTrasfFine;

	private String note;

	@Id
	@Column(name="PKID_CIVI")
	@GeneratedValue(strategy=GenerationType.AUTO)
	@IndiceKey(pos="1")
	private BigDecimal pkidCivi;

	@Column(name="PKID_STRA")
	private BigDecimal pkidStra;

	@Transient
	private Object shape;

	private String utente;

	@Column(name="UTENTE_FINE")
	private String utenteFine;

    public Siticivi() {
    }

	public String getCivico() {
		return this.civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
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

	public BigDecimal getIdTrasf() {
		return this.idTrasf;
	}

	public void setIdTrasf(BigDecimal idTrasf) {
		this.idTrasf = idTrasf;
	}

	public BigDecimal getIdTrasfFine() {
		return this.idTrasfFine;
	}

	public void setIdTrasfFine(BigDecimal idTrasfFine) {
		this.idTrasfFine = idTrasfFine;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public BigDecimal getPkidCivi() {
		return this.pkidCivi;
	}

	public void setPkidCivi(BigDecimal pkidCivi) {
		this.pkidCivi = pkidCivi;
	}

	public BigDecimal getPkidStra() {
		return this.pkidStra;
	}

	public void setPkidStra(BigDecimal pkidStra) {
		this.pkidStra = pkidStra;
	}

	public Object getShape() {
		return this.shape;
	}

	public void setShape(Object shape) {
		this.shape = shape;
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

}