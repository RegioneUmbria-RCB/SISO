package it.webred.cs.sociosan.ejb.dto.isee;

import it.webred.cs.data.model.CsTbTipoIsee;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DatiIsee implements Serializable {
	
	 private Date dataPresentazioneIsee;
	 private Date dataScadenzaIsee;
	 private String note;
	 private CsTbTipoIsee tipo;
	 private BigDecimal ise;
	 private BigDecimal isee;
	 private BigDecimal scalaEquivalenza;
	 
	 private Integer protDSU_anno;
	 private String protDSU_prefisso;
	 private String protDSU_numero;
	 private String protDSU_progressivo;
	 
	public Date getDataPresentazioneIsee() {
		return dataPresentazioneIsee;
	}
	public void setDataPresentazioneIsee(Date dataPresentazioneIsee) {
		this.dataPresentazioneIsee = dataPresentazioneIsee;
	}
	public Date getDataScadenzaIsee() {
		return dataScadenzaIsee;
	}
	public void setDataScadenzaIsee(Date dataScadenzaIsee) {
		this.dataScadenzaIsee = dataScadenzaIsee;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public CsTbTipoIsee getTipo() {
		return tipo;
	}
	public void setTipo(CsTbTipoIsee tipo) {
		this.tipo = tipo;
	}
	public BigDecimal getIse() {
		return ise;
	}
	public void setIse(BigDecimal ise) {
		this.ise = ise;
	}
	public BigDecimal getIsee() {
		return isee;
	}
	public void setIsee(BigDecimal isee) {
		this.isee = isee;
	}
	public BigDecimal getScalaEquivalenza() {
		return scalaEquivalenza;
	}
	public void setScalaEquivalenza(BigDecimal scalaEquivalenza) {
		this.scalaEquivalenza = scalaEquivalenza;
	}
	public Integer getProtDSU_anno() {
		return protDSU_anno;
	}
	public void setProtDSU_anno(Integer protDSU_anno) {
		this.protDSU_anno = protDSU_anno;
	}
	public String getProtDSU_prefisso() {
		return protDSU_prefisso;
	}
	public void setProtDSU_prefisso(String protDSU_prefisso) {
		this.protDSU_prefisso = protDSU_prefisso;
	}
	public String getProtDSU_numero() {
		return protDSU_numero;
	}
	public void setProtDSU_numero(String protDSU_numero) {
		this.protDSU_numero = protDSU_numero;
	}
	public String getProtDSU_progressivo() {
		return protDSU_progressivo;
	}
	public void setProtDSU_progressivo(String protDSU_progressivo) {
		this.protDSU_progressivo = protDSU_progressivo;
	} 
}
