package it.webred.ct.data.model.anagrafe;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the SIT_D_FAM_STORICO database table.
 * 
 */
@Entity
@Table(name="SIT_D_FAM_STORICO")
public class SitDFamStorico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String codfisc;

	private String cognome;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_EMI")
	private Date dataEmi;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FIN_RIF")
	private Date dataFinRif;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_IMM")
	private Date dataImm;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INI_RIF")
	private Date dataIniRif;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INIZIO_RESIDENZA")
	private Date dataInizioResidenza;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_MOR")
	private Date dataMor;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_NASCITA")
	private Date dataNascita;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FIN_DATO_PF")
	private Date dtFinDatoPf;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INI_DATO_PF")
	private Date dtIniDatoPf;

	@Column(name="ID_EXT_D_FAMIGLIA")
	private String idExtDFamiglia;

	@Column(name="ID_EXT_D_PERSONA")
	private String idExtDPersona;

	private String nome;

	private String processid;

	private String sesso;

	public SitDFamStorico() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodfisc() {
		return this.codfisc;
	}

	public void setCodfisc(String codfisc) {
		this.codfisc = codfisc;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDataEmi() {
		return this.dataEmi;
	}

	public void setDataEmi(Date dataEmi) {
		this.dataEmi = dataEmi;
	}

	public Date getDataFinRif() {
		return this.dataFinRif;
	}

	public void setDataFinRif(Date dataFinRif) {
		this.dataFinRif = dataFinRif;
	}

	public Date getDataImm() {
		return this.dataImm;
	}

	public void setDataImm(Date dataImm) {
		this.dataImm = dataImm;
	}

	public Date getDataIniRif() {
		return this.dataIniRif;
	}

	public void setDataIniRif(Date dataIniRif) {
		this.dataIniRif = dataIniRif;
	}

	public Date getDataInizioResidenza() {
		return this.dataInizioResidenza;
	}

	public void setDataInizioResidenza(Date dataInizioResidenza) {
		this.dataInizioResidenza = dataInizioResidenza;
	}

	public Date getDataMor() {
		return this.dataMor;
	}

	public void setDataMor(Date dataMor) {
		this.dataMor = dataMor;
	}

	public Date getDataNascita() {
		return this.dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Date getDtFinDatoPf() {
		return this.dtFinDatoPf;
	}

	public void setDtFinDatoPf(Date dtFinDatoPf) {
		this.dtFinDatoPf = dtFinDatoPf;
	}

	public Date getDtIniDatoPf() {
		return this.dtIniDatoPf;
	}

	public void setDtIniDatoPf(Date dtIniDatoPf) {
		this.dtIniDatoPf = dtIniDatoPf;
	}

	public String getIdExtDFamiglia() {
		return this.idExtDFamiglia;
	}

	public void setIdExtDFamiglia(String idExtDFamiglia) {
		this.idExtDFamiglia = idExtDFamiglia;
	}

	public String getIdExtDPersona() {
		return this.idExtDPersona;
	}

	public void setIdExtDPersona(String idExtDPersona) {
		this.idExtDPersona = idExtDPersona;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

}