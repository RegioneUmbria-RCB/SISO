package it.webred.ct.data.access.basic.versamenti.iciDM.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ViolazioneIciDmDTO extends IciDmDTO implements Serializable{

	private BigDecimal impImposta;
	private BigDecimal impSoprattassa;
	private BigDecimal impPenaPecuniaria;
	private BigDecimal impInteressi;
	
	private String codTipoImposta;
	private String desTipoImposta;
	
	private String numProvLiq;
	private Date dtProvLiq;

	public BigDecimal getImpImposta() {
		return impImposta;
	}
	public void setImpImposta(BigDecimal impImposta) {
		this.impImposta = impImposta;
	}
	public BigDecimal getImpSoprattassa() {
		return impSoprattassa;
	}
	public void setImpSoprattassa(BigDecimal impSoprattassa) {
		this.impSoprattassa = impSoprattassa;
	}
	public BigDecimal getImpPenaPecuniaria() {
		return impPenaPecuniaria;
	}
	public void setImpPenaPecuniaria(BigDecimal impPenaPecuniaria) {
		this.impPenaPecuniaria = impPenaPecuniaria;
	}
	public BigDecimal getImpInteressi() {
		return impInteressi;
	}
	public void setImpInteressi(BigDecimal impInteressi) {
		this.impInteressi = impInteressi;
	}
	public String getCodTipoImposta() {
		return codTipoImposta;
	}
	public void setCodTipoImposta(String codTipoImposta) {
		this.codTipoImposta = codTipoImposta;
	}
	public String getDesTipoImposta() {
		return desTipoImposta;
	}
	public void setDesTipoImposta(String desTipoImposta) {
		this.desTipoImposta = desTipoImposta;
	}
	public String getNumProvLiq() {
		return numProvLiq;
	}
	public void setNumProvLiq(String numProvLiq) {
		this.numProvLiq = numProvLiq;
	}
	public Date getDtProvLiq() {
		return dtProvLiq;
	}
	public void setDtProvLiq(Date dtProvLiq) {
		this.dtProvLiq = dtProvLiq;
	}
}
