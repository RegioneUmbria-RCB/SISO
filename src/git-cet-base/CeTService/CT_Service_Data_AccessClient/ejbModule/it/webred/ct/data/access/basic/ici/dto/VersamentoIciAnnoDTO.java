package it.webred.ct.data.access.basic.ici.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.math.BigDecimal;

public class VersamentoIciAnnoDTO extends CeTBaseObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String yeaRif;
	private BigDecimal impPagEu;
	public String getYeaRif() {
		return yeaRif;
	}
	public void setYeaRif(String yeaRif) {
		this.yeaRif = yeaRif;
	}
	public BigDecimal getImpPagEu() {
		return impPagEu;
	}
	public void setImpPagEu(BigDecimal impPagEu) {
		this.impPagEu = impPagEu;
	}
	


}
