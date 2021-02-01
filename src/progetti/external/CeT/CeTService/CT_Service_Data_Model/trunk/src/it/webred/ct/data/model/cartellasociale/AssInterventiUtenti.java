package it.webred.ct.data.model.cartellasociale;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ASS_INTERVENTI_UTENTI database table.
 * 
 */
@Entity
@Table(name="ASS_INTERVENTI_UTENTI")
public class AssInterventiUtenti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_INTERVENTO")
	private BigDecimal idIntervento;
	
	@Column(name="CADENZA_INT")
	private BigDecimal cadenzaInt;

	@Column(name="COD_ENTE_EROGANTE")
	private String codEnteErogante;

	@Column(name="COD_FISC")
	private String codFisc;

	@Column(name="COD_INTERVENTO")
	private String codIntervento;

	@Column(name="COD_NOM_CISIS")
	private String codNomCisis;

	@Column(name="COD_STATO_CONTR_ECON")
	private String codStatoContrEcon;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_REVOCA")
	private Date dataRevoca;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_APPR_INTERVENTO")
	private Date dtApprIntervento;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_EROG_CONTRIBUTO")
	private Date dtErogContributo;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_FINE_INTERVENTO")
	private Date dtFineIntervento;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_FINE_STATO_CONTR_EC")
	private Date dtFineStatoContrEc;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_FINE_VAL")
	private Date dtFineVal;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_INI_STATO_CONTR_EC")
	private Date dtIniStatoContrEc;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_INIZIO_VAL")
	private Date dtInizioVal;

	@Column(name="ESITO_INTERVENTO")
	private String esitoIntervento;

	@Column(name="IMPORTO_INTERVENTO")
	private BigDecimal importoIntervento;

	private String note;

    public AssInterventiUtenti() {
    }

	public BigDecimal getCadenzaInt() {
		return this.cadenzaInt;
	}

	public void setCadenzaInt(BigDecimal cadenzaInt) {
		this.cadenzaInt = cadenzaInt;
	}

	public String getCodEnteErogante() {
		return this.codEnteErogante;
	}

	public void setCodEnteErogante(String codEnteErogante) {
		this.codEnteErogante = codEnteErogante;
	}

	public String getCodFisc() {
		return this.codFisc;
	}

	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}

	public String getCodIntervento() {
		return this.codIntervento;
	}

	public void setCodIntervento(String codIntervento) {
		this.codIntervento = codIntervento;
	}

	public String getCodNomCisis() {
		return this.codNomCisis;
	}

	public void setCodNomCisis(String codNomCisis) {
		this.codNomCisis = codNomCisis;
	}

	public String getCodStatoContrEcon() {
		return this.codStatoContrEcon;
	}

	public void setCodStatoContrEcon(String codStatoContrEcon) {
		this.codStatoContrEcon = codStatoContrEcon;
	}

	public Date getDataRevoca() {
		return this.dataRevoca;
	}

	public void setDataRevoca(Date dataRevoca) {
		this.dataRevoca = dataRevoca;
	}

	public Date getDtApprIntervento() {
		return this.dtApprIntervento;
	}

	public void setDtApprIntervento(Date dtApprIntervento) {
		this.dtApprIntervento = dtApprIntervento;
	}

	public Date getDtErogContributo() {
		return this.dtErogContributo;
	}

	public void setDtErogContributo(Date dtErogContributo) {
		this.dtErogContributo = dtErogContributo;
	}

	public Date getDtFineIntervento() {
		return this.dtFineIntervento;
	}

	public void setDtFineIntervento(Date dtFineIntervento) {
		this.dtFineIntervento = dtFineIntervento;
	}

	public Date getDtFineStatoContrEc() {
		return this.dtFineStatoContrEc;
	}

	public void setDtFineStatoContrEc(Date dtFineStatoContrEc) {
		this.dtFineStatoContrEc = dtFineStatoContrEc;
	}

	public Date getDtFineVal() {
		return this.dtFineVal;
	}

	public void setDtFineVal(Date dtFineVal) {
		this.dtFineVal = dtFineVal;
	}

	public Date getDtIniStatoContrEc() {
		return this.dtIniStatoContrEc;
	}

	public void setDtIniStatoContrEc(Date dtIniStatoContrEc) {
		this.dtIniStatoContrEc = dtIniStatoContrEc;
	}

	public Date getDtInizioVal() {
		return this.dtInizioVal;
	}

	public void setDtInizioVal(Date dtInizioVal) {
		this.dtInizioVal = dtInizioVal;
	}

	public String getEsitoIntervento() {
		return this.esitoIntervento;
	}

	public void setEsitoIntervento(String esitoIntervento) {
		this.esitoIntervento = esitoIntervento;
	}

	public BigDecimal getImportoIntervento() {
		return this.importoIntervento;
	}

	public void setImportoIntervento(BigDecimal importoIntervento) {
		this.importoIntervento = importoIntervento;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public BigDecimal getIdIntervento() {
		return idIntervento;
	}

	public void setIdIntervento(BigDecimal idIntervento) {
		this.idIntervento = idIntervento;
	}

}