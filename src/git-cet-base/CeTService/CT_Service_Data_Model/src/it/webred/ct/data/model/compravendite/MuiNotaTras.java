package it.webred.ct.data.model.compravendite;

import it.webred.ct.data.model.annotation.IndiceEntity;
import it.webred.ct.data.model.annotation.IndiceKey;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the MI_DUP_NOTA_TRAS database table.
 * 
 */
@Entity
@Table(name="MUI_NOTA_TRAS")
@IndiceEntity(sorgente="7")
public class MuiNotaTras implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@IndiceKey(pos="1")
	private BigDecimal iid;

	@Column(name="ANNO_NOTA")
	private String annoNota;

    @Temporal( TemporalType.DATE)
	@Column(name="ANNO_NOTA_DATE")
	private Date annoNotaDate;

	@Column(name="COD_FISC_ROG")
	private String codFiscRog;

	@Column(name="CODICE_ATTO")
	private String codiceAtto;

	@Column(name="COGNOME_NOME_ROG")
	private String cognomeNomeRog;

	@Column(name="DATA_IN_DIF")
	private String dataInDif;

	@Column(name="DATA_PRES_ATTO")
	private String dataPresAtto;

	@Column(name="DATA_PRES_ATTO_RET")
	private String dataPresAttoRet;

	@Column(name="DATA_REG_IN_ATTI")
	private String dataRegInAtti;

	@Column(name="DATA_VALIDITA_ATTO")
	private String dataValiditaAtto;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_VALIDITA_ATTO_DATE")
	private Date dataValiditaAttoDate;

	@Column(name="ESITO_NOTA")
	private String esitoNota;

	@Column(name="ESITO_NOTA_NON_REG")
	private String esitoNotaNonReg;

	@Column(name="FLAG_RETTIFICA")
	private String flagRettifica;

	@Column(name="ID_NOTA")
	private String idNota;

	@Column(name="IID_FORNITURA")
	private BigDecimal iidFornitura;

	@Column(name="NUMERO_NOTA_RET")
	private String numeroNotaRet;

	@Column(name="NUMERO_NOTA_TRAS")
	private String numeroNotaTras;

	@Column(name="NUMERO_NOTA_TRAS_LONG")
	private BigDecimal numeroNotaTrasLong;

	@Column(name="NUMERO_REPERTORIO")
	private String numeroRepertorio;

	@Column(name="PROGRESSIVO_NOTA")
	private String progressivoNota;

	@Column(name="REGISTRAZIONE_DIF")
	private String registrazioneDif;

	@Column(name="SEDE_ROG")
	private String sedeRog;

	@Column(name="TIPO_NOTA")
	private String tipoNota;

	@Column(name="TIPO_NOTA_RET")
	private String tipoNotaRet;

    public MuiNotaTras() {
    }

	public BigDecimal getIid() {
		return this.iid;
	}

	public void setIid(BigDecimal iid) {
		this.iid = iid;
	}

	public String getAnnoNota() {
		return this.annoNota;
	}

	public void setAnnoNota(String annoNota) {
		this.annoNota = annoNota;
	}

	public Date getAnnoNotaDate() {
		return this.annoNotaDate;
	}

	public void setAnnoNotaDate(Date annoNotaDate) {
		this.annoNotaDate = annoNotaDate;
	}

	public String getCodFiscRog() {
		return this.codFiscRog;
	}

	public void setCodFiscRog(String codFiscRog) {
		this.codFiscRog = codFiscRog;
	}

	public String getCodiceAtto() {
		return this.codiceAtto;
	}

	public void setCodiceAtto(String codiceAtto) {
		this.codiceAtto = codiceAtto;
	}

	public String getCognomeNomeRog() {
		return this.cognomeNomeRog;
	}

	public void setCognomeNomeRog(String cognomeNomeRog) {
		this.cognomeNomeRog = cognomeNomeRog;
	}

	public String getDataInDif() {
		return this.dataInDif;
	}

	public void setDataInDif(String dataInDif) {
		this.dataInDif = dataInDif;
	}

	public String getDataPresAtto() {
		return this.dataPresAtto;
	}

	public void setDataPresAtto(String dataPresAtto) {
		this.dataPresAtto = dataPresAtto;
	}

	public String getDataPresAttoRet() {
		return this.dataPresAttoRet;
	}

	public void setDataPresAttoRet(String dataPresAttoRet) {
		this.dataPresAttoRet = dataPresAttoRet;
	}

	public String getDataRegInAtti() {
		return this.dataRegInAtti;
	}

	public void setDataRegInAtti(String dataRegInAtti) {
		this.dataRegInAtti = dataRegInAtti;
	}

	public String getDataValiditaAtto() {
		return this.dataValiditaAtto;
	}

	public void setDataValiditaAtto(String dataValiditaAtto) {
		this.dataValiditaAtto = dataValiditaAtto;
	}

	public Date getDataValiditaAttoDate() {
		return this.dataValiditaAttoDate;
	}

	public void setDataValiditaAttoDate(Date dataValiditaAttoDate) {
		this.dataValiditaAttoDate = dataValiditaAttoDate;
	}

	public String getEsitoNota() {
		return this.esitoNota;
	}

	public void setEsitoNota(String esitoNota) {
		this.esitoNota = esitoNota;
	}

	public String getEsitoNotaNonReg() {
		return this.esitoNotaNonReg;
	}

	public void setEsitoNotaNonReg(String esitoNotaNonReg) {
		this.esitoNotaNonReg = esitoNotaNonReg;
	}

	public String getFlagRettifica() {
		return this.flagRettifica;
	}

	public void setFlagRettifica(String flagRettifica) {
		this.flagRettifica = flagRettifica;
	}

	public String getIdNota() {
		return this.idNota;
	}

	public void setIdNota(String idNota) {
		this.idNota = idNota;
	}

	public BigDecimal getIidFornitura() {
		return this.iidFornitura;
	}

	public void setIidFornitura(BigDecimal iidFornitura) {
		this.iidFornitura = iidFornitura;
	}

	public String getNumeroNotaRet() {
		return this.numeroNotaRet;
	}

	public void setNumeroNotaRet(String numeroNotaRet) {
		this.numeroNotaRet = numeroNotaRet;
	}

	public String getNumeroNotaTras() {
		return this.numeroNotaTras;
	}

	public void setNumeroNotaTras(String numeroNotaTras) {
		this.numeroNotaTras = numeroNotaTras;
	}

	public BigDecimal getNumeroNotaTrasLong() {
		return this.numeroNotaTrasLong;
	}

	public void setNumeroNotaTrasLong(BigDecimal numeroNotaTrasLong) {
		this.numeroNotaTrasLong = numeroNotaTrasLong;
	}

	public String getNumeroRepertorio() {
		return this.numeroRepertorio;
	}

	public void setNumeroRepertorio(String numeroRepertorio) {
		this.numeroRepertorio = numeroRepertorio;
	}

	public String getProgressivoNota() {
		return this.progressivoNota;
	}

	public void setProgressivoNota(String progressivoNota) {
		this.progressivoNota = progressivoNota;
	}

	public String getRegistrazioneDif() {
		return this.registrazioneDif;
	}

	public void setRegistrazioneDif(String registrazioneDif) {
		this.registrazioneDif = registrazioneDif;
	}

	public String getSedeRog() {
		return this.sedeRog;
	}

	public void setSedeRog(String sedeRog) {
		this.sedeRog = sedeRog;
	}

	public String getTipoNota() {
		return this.tipoNota;
	}

	public void setTipoNota(String tipoNota) {
		this.tipoNota = tipoNota;
	}

	public String getTipoNotaRet() {
		return this.tipoNotaRet;
	}

	public void setTipoNotaRet(String tipoNotaRet) {
		this.tipoNotaRet = tipoNotaRet;
	}

}