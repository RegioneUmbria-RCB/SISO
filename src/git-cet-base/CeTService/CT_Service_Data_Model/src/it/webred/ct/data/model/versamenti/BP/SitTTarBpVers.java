package it.webred.ct.data.model.versamenti.BP;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_T_TAR_BP_VERS database table.
 * 
 */
@Entity
@Table(name="SIT_T_TAR_BP_VERS")
public class SitTTarBpVers implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="CC_BENEFICIARIO")
	private String ccBeneficiario;

	private String cin;

	@Column(name="CTR_HASH")
	private String ctrHash;

	private String divisa;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_ACCETTAZIONE")
	private Date dtAccettazione;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_ACCREDITO")
	private Date dtAccredito;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE_DATO")
	private Date dtFineDato;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE_VAL")
	private Date dtFineVal;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO_DATO")
	private Date dtInizioDato;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO_VAL")
	private Date dtInizioVal;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_MOD")
	private Date dtMod;

	@Column(name="FK_ENTE_SORGENTE")
	private BigDecimal fkEnteSorgente;

	@Column(name="FLAG_DT_VAL_DATO")
	private BigDecimal flagDtValDato;

	@Column(name="FLAG_SOSTITUTIVO")
	private String flagSostitutivo;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_ORIG")
	private String idOrig;

	private String importo;

	@Column(name="NUM_BOLLETTINO")
	private String numBollettino;

	private String processid;

	@Column(name="PROG_CAR")
	private BigDecimal progCar;

	@Column(name="PROG_SEL")
	private BigDecimal progSel;

	@Column(name="TIPO_ACCETTAZIONE")
	private String tipoAccettazione;

	@Column(name="TIPO_DOCUMENTO")
	private String tipoDocumento;

	@Column(name="UFFICIO_SPORTELLO")
	private String ufficioSportello;

	public SitTTarBpVers() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCcBeneficiario() {
		return this.ccBeneficiario;
	}

	public void setCcBeneficiario(String ccBeneficiario) {
		this.ccBeneficiario = ccBeneficiario;
	}

	public String getCin() {
		return this.cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getCtrHash() {
		return this.ctrHash;
	}

	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}

	public String getDivisa() {
		return this.divisa;
	}

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

	public Date getDtAccettazione() {
		return this.dtAccettazione;
	}

	public void setDtAccettazione(Date dtAccettazione) {
		this.dtAccettazione = dtAccettazione;
	}

	public Date getDtAccredito() {
		return this.dtAccredito;
	}

	public void setDtAccredito(Date dtAccredito) {
		this.dtAccredito = dtAccredito;
	}

	public Date getDtExpDato() {
		return this.dtExpDato;
	}

	public void setDtExpDato(Date dtExpDato) {
		this.dtExpDato = dtExpDato;
	}

	public Date getDtFineDato() {
		return this.dtFineDato;
	}

	public void setDtFineDato(Date dtFineDato) {
		this.dtFineDato = dtFineDato;
	}

	public Date getDtFineVal() {
		return this.dtFineVal;
	}

	public void setDtFineVal(Date dtFineVal) {
		this.dtFineVal = dtFineVal;
	}

	public Date getDtInizioDato() {
		return this.dtInizioDato;
	}

	public void setDtInizioDato(Date dtInizioDato) {
		this.dtInizioDato = dtInizioDato;
	}

	public Date getDtInizioVal() {
		return this.dtInizioVal;
	}

	public void setDtInizioVal(Date dtInizioVal) {
		this.dtInizioVal = dtInizioVal;
	}

	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public Date getDtMod() {
		return this.dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public BigDecimal getFkEnteSorgente() {
		return this.fkEnteSorgente;
	}

	public void setFkEnteSorgente(BigDecimal fkEnteSorgente) {
		this.fkEnteSorgente = fkEnteSorgente;
	}

	public BigDecimal getFlagDtValDato() {
		return this.flagDtValDato;
	}

	public void setFlagDtValDato(BigDecimal flagDtValDato) {
		this.flagDtValDato = flagDtValDato;
	}

	public String getFlagSostitutivo() {
		return this.flagSostitutivo;
	}

	public void setFlagSostitutivo(String flagSostitutivo) {
		this.flagSostitutivo = flagSostitutivo;
	}

	public String getIdExt() {
		return this.idExt;
	}

	public void setIdExt(String idExt) {
		this.idExt = idExt;
	}

	public String getIdOrig() {
		return this.idOrig;
	}

	public void setIdOrig(String idOrig) {
		this.idOrig = idOrig;
	}

	public String getImporto() {
		return this.importo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
	}

	public String getNumBollettino() {
		return this.numBollettino;
	}

	public void setNumBollettino(String numBollettino) {
		this.numBollettino = numBollettino;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public BigDecimal getProgCar() {
		return this.progCar;
	}

	public void setProgCar(BigDecimal progCar) {
		this.progCar = progCar;
	}

	public BigDecimal getProgSel() {
		return this.progSel;
	}

	public void setProgSel(BigDecimal progSel) {
		this.progSel = progSel;
	}

	public String getTipoAccettazione() {
		return this.tipoAccettazione;
	}

	public void setTipoAccettazione(String tipoAccettazione) {
		this.tipoAccettazione = tipoAccettazione;
	}

	public String getTipoDocumento() {
		return this.tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getUfficioSportello() {
		return this.ufficioSportello;
	}

	public void setUfficioSportello(String ufficioSportello) {
		this.ufficioSportello = ufficioSportello;
	}

}