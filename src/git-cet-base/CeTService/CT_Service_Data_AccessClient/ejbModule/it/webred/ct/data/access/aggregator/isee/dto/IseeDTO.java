package it.webred.ct.data.access.aggregator.isee.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.math.BigDecimal;
public class IseeDTO extends CeTBaseObject implements Serializable {
		
	private BigDecimal isee;
	private BigDecimal scalaEquivalenza;
	private BigDecimal ise;
	
	public IseeDTO() {
		super();
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

	public BigDecimal getIse() {
		return ise;
	}

	public void setIse(BigDecimal ise) {
		this.ise = ise;
	}

}
