package it.webred.cs.data.model.view;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the V_MOBI_INT_EROG database table.
 * 
 */
@Entity
@Table(name="V_MOBI_CASI")
@JsonIgnoreProperties(ignoreUnknown = true)
public class VMobiCasi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CASO_ID")
	private BigDecimal casoId;
	
	@Column(name="SOGG_CF")
	private String soggCf;

	@Column(name="SOGG_COGNOME")
	private String soggCognome;

	@Column(name="SOGG_NOME")
	private String soggNome;

	@Column(name="SOGG_TEL")
	private String soggTel;

	@Column(name="SOGG_CELL")
	private String soggCell;

	@Column(name="SOGG_EMAIL")
	private String soggEmail;

	@Column(name="SOGG_SESSO")
	private String soggSesso;

	@Column(name="SOGG_CITTADINANZA")
	private String soggCittadinanza;

	@Column(name="FLAG_RESPONSABILE")
	private BigDecimal flagResponsabile;

	@Column(name="OPERATORE_ID")
	private BigDecimal operatoreId;

	@Column(name="SETTORE_ID")
	private BigDecimal settoreId;

	@Column(name="SETTORE_NOME")
	private String settoreNome;


	@Column(name="ORG_NOME")
	private String orgNome;

	@Column(name="ORG_BELFIORE")
	private String orgBelfiore;

	@Column(name="USERNAME")
	private String username;

	@Column(name="OPE_COGNOME")
	private String opeCognome;

	@Column(name="OPE_NOME")
	private String opeNome;

	public BigDecimal getCasoId() {
		return casoId;
	}

	public void setCasoId(BigDecimal casoId) {
		this.casoId = casoId;
	}

	public String getSoggCf() {
		return soggCf;
	}

	public void setSoggCf(String soggCf) {
		this.soggCf = soggCf;
	}

	public String getSoggCognome() {
		return soggCognome;
	}

	public void setSoggCognome(String soggCognome) {
		this.soggCognome = soggCognome;
	}

	public String getSoggNome() {
		return soggNome;
	}

	public void setSoggNome(String soggNome) {
		this.soggNome = soggNome;
	}

	public String getSoggTel() {
		return soggTel;
	}

	public void setSoggTel(String soggTel) {
		this.soggTel = soggTel;
	}

	public String getSoggCell() {
		return soggCell;
	}

	public void setSoggCell(String soggCell) {
		this.soggCell = soggCell;
	}

	public String getSoggEmail() {
		return soggEmail;
	}

	public void setSoggEmail(String soggEmail) {
		this.soggEmail = soggEmail;
	}

	public String getSoggSesso() {
		return soggSesso;
	}

	public void setSoggSesso(String soggSesso) {
		this.soggSesso = soggSesso;
	}

	public String getSoggCittadinanza() {
		return soggCittadinanza;
	}

	public void setSoggCittadinanza(String soggCittadinanza) {
		this.soggCittadinanza = soggCittadinanza;
	}

	public BigDecimal getFlagResponsabile() {
		return flagResponsabile;
	}

	public void setFlagResponsabile(BigDecimal flagResponsabile) {
		this.flagResponsabile = flagResponsabile;
	}

	public BigDecimal getOperatoreId() {
		return operatoreId;
	}

	public void setOperatoreId(BigDecimal operatoreId) {
		this.operatoreId = operatoreId;
	}

	public BigDecimal getSettoreId() {
		return settoreId;
	}

	public void setSettoreId(BigDecimal settoreId) {
		this.settoreId = settoreId;
	}

	public String getSettoreNome() {
		return settoreNome;
	}

	public void setSettoreNome(String settoreNome) {
		this.settoreNome = settoreNome;
	}

	public String getOrgNome() {
		return orgNome;
	}

	public void setOrgNome(String orgNome) {
		this.orgNome = orgNome;
	}

	public String getOrgBelfiore() {
		return orgBelfiore;
	}

	public void setOrgBelfiore(String orgBelfiore) {
		this.orgBelfiore = orgBelfiore;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOpeCognome() {
		return opeCognome;
	}

	public void setOpeCognome(String opeCognome) {
		this.opeCognome = opeCognome;
	}

	public String getOpeNome() {
		return opeNome;
	}

	public void setOpeNome(String opeNome) {
		this.opeNome = opeNome;
	}

	
	
	

}