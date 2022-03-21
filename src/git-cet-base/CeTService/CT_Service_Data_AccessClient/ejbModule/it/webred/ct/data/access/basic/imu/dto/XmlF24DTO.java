package it.webred.ct.data.access.basic.imu.dto;

import java.math.BigDecimal;
import java.util.List;

public class XmlF24DTO {
	
	private static final long serialVersionUID = 1L;
	
	private String codEnte;
	private boolean flgRav;
	private boolean flgImmVar;
	private boolean flgAcconto;
	private boolean flgSaldo;
	private String numImm;
	private String codTributo;
	private String descTributo;
	private String flgRateazione;
	private String annoRif;
	private BigDecimal dovutoScadenza;
	private BigDecimal pagatoScadenza;
	private BigDecimal impDaRavvedere;
	private BigDecimal sanzione;
	private BigDecimal interessi;
	private BigDecimal tasso;
	private BigDecimal detrazione;
	private BigDecimal impDebito;
	
	public String getCodEnte() {
		return codEnte;
	}
	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}
	public boolean isFlgRav() {
		return flgRav;
	}
	public void setFlgRav(boolean flgRav) {
		this.flgRav = flgRav;
	}
	public boolean isFlgImmVar() {
		return flgImmVar;
	}
	public void setFlgImmVar(boolean flgImmVar) {
		this.flgImmVar = flgImmVar;
	}
	public boolean isFlgAcconto() {
		return flgAcconto;
	}
	public void setFlgAcconto(boolean flgAcconto) {
		this.flgAcconto = flgAcconto;
	}
	public boolean isFlgSaldo() {
		return flgSaldo;
	}
	public void setFlgSaldo(boolean flgSaldo) {
		this.flgSaldo = flgSaldo;
	}
	public String getNumImm() {
		return numImm;
	}
	public void setNumImm(String numImm) {
		this.numImm = numImm;
	}
	public String getCodTributo() {
		return codTributo;
	}
	public void setCodTributo(String codTributo) {
		this.codTributo = codTributo;
	}
	
	public String getAnnoRif() {
		return annoRif;
	}
	public void setAnnoRif(String annoRif) {
		this.annoRif = annoRif;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getFlgRateazione() {
		return flgRateazione;
	}
	public void setFlgRateazione(String flgRateazione) {
		this.flgRateazione = flgRateazione;
	}
	public BigDecimal getDovutoScadenza() {
		return dovutoScadenza;
	}
	public void setDovutoScadenza(BigDecimal dovutoScadenza) {
		this.dovutoScadenza = dovutoScadenza;
	}
	public BigDecimal getPagatoScadenza() {
		return pagatoScadenza;
	}
	public void setPagatoScadenza(BigDecimal pagatoScadenza) {
		this.pagatoScadenza = pagatoScadenza;
	}
	public BigDecimal getImpDaRavvedere() {
		return impDaRavvedere;
	}
	public void setImpDaRavvedere(BigDecimal impDaRavvedere) {
		this.impDaRavvedere = impDaRavvedere;
	}
	public BigDecimal getSanzione() {
		return sanzione;
	}
	public void setSanzione(BigDecimal sanzione) {
		this.sanzione = sanzione;
	}
	public BigDecimal getInteressi() {
		return interessi;
	}
	public void setInteressi(BigDecimal interessi) {
		this.interessi = interessi;
	}
	public BigDecimal getTasso() {
		return tasso;
	}
	public void setTasso(BigDecimal tasso) {
		this.tasso = tasso;
	}
	public BigDecimal getDetrazione() {
		return detrazione;
	}
	public void setDetrazione(BigDecimal detrazione) {
		this.detrazione = detrazione;
	}
	public BigDecimal getImpDebito() {
		return impDebito;
	}
	public void setImpDebito(BigDecimal impDebito) {
		this.impDebito = impDebito;
	}
	public String getDescTributo() {
		return descTributo;
	}
	public void setDescTributo(String descTributo) {
		this.descTributo = descTributo;
	}
	
	
}
