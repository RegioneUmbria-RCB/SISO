package it.webred.cs.csa.ejb.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
//SISO - 883
public class ErogazionePrestazioneDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private BigDecimal idEnteTitolare;
	private BigDecimal mastId;
	private BigDecimal esegId;
	private Date dataErog;
	private Date dataErogA;
	private String cf;
	private String codPrestazione;
	private Date dataDSU;
	private BigDecimal annoProtDsu;
	private String numProtDsu;
	private String prefixProtDsu;
	private String progProtDsu;
	
	public BigDecimal getMastId() {
		return mastId;
	}
	public Date getDataErog() {
		return dataErog;
	}
	public Date getDataErogA() {
		return dataErogA;
	}
	public String getCf() {
		return cf;
	}
	public String getCodPrestazione() {
		return codPrestazione;
	}
	public Date getDataDSU() {
		return dataDSU;
	}
	public BigDecimal getAnnoProtDsu() {
		return annoProtDsu;
	}
	public String getNumProtDsu() {
		return numProtDsu;
	}
	public String getPrefixProtDsu() {
		return prefixProtDsu;
	}
	public String getProgProtDsu() {
		return progProtDsu;
	}
	public void setMastId(BigDecimal mastId) {
		this.mastId = mastId;
	}
	public void setDataErog(Date dataErog) {
		this.dataErog = dataErog;
	}
	public void setDataErogA(Date dataErogA) {
		this.dataErogA = dataErogA;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public void setCodPrestazione(String codPrestazione) {
		this.codPrestazione = codPrestazione;
	}
	public void setDataDSU(Date dataDSU) {
		this.dataDSU = dataDSU;
	}
	public void setAnnoProtDsu(BigDecimal annoProtDsu) {
		this.annoProtDsu = annoProtDsu;
	}
	public void setNumProtDsu(String numProtDsu) {
		this.numProtDsu = numProtDsu;
	}
	public void setPrefixProtDsu(String prefixProtDsu) {
		this.prefixProtDsu = prefixProtDsu;
	}
	public void setProgProtDsu(String progProtDsu) {
		this.progProtDsu = progProtDsu;
	}
	public BigDecimal getIdEnteTitolare() {
		return idEnteTitolare;
	}
	public void setIdEnteTitolare(BigDecimal idEnteTitolare) {
		this.idEnteTitolare = idEnteTitolare;
	}
	public BigDecimal getEsegId() {
		return esegId;
	}
	public void setEsegId(BigDecimal esegId) {
		this.esegId = esegId;
	}
}
