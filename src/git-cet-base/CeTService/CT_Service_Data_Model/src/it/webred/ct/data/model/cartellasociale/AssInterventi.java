package it.webred.ct.data.model.cartellasociale;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ASS_INTERVENTI database table.
 * 
 */
@Entity
@Table(name="ASS_INTERVENTI")
public class AssInterventi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="COD_INTERVENTO")
	private String codIntervento;

	@Column(name="COD_TIPOLOGIA")
	private String codTipologia;

	private String descrizione;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_FINE_VAL")
	private Date dtFineVal;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_INI_VAL")
	private Date dtIniVal;

	@Column(name="ID_INTERVENTO")
	private BigDecimal idIntervento;

    public AssInterventi() {
    }

	public String getCodIntervento() {
		return this.codIntervento;
	}

	public void setCodIntervento(String codIntervento) {
		this.codIntervento = codIntervento;
	}

	public String getCodTipologia() {
		return this.codTipologia;
	}

	public void setCodTipologia(String codTipologia) {
		this.codTipologia = codTipologia;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getDtFineVal() {
		return this.dtFineVal;
	}

	public void setDtFineVal(Date dtFineVal) {
		this.dtFineVal = dtFineVal;
	}

	public Date getDtIniVal() {
		return this.dtIniVal;
	}

	public void setDtIniVal(Date dtIniVal) {
		this.dtIniVal = dtIniVal;
	}

	public BigDecimal getIdIntervento() {
		return this.idIntervento;
	}

	public void setIdIntervento(BigDecimal idIntervento) {
		this.idIntervento = idIntervento;
	}

}