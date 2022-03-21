package it.webred.ct.data.access.basic.catasto.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class InfoPerCategoriaDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private String categoria;
	private BigDecimal consistenzaTot;
	private Integer countUiu;
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public BigDecimal getConsistenzaTot() {
		return consistenzaTot;
	}
	public void setConsistenzaTot(BigDecimal consistenzaTot) {
		this.consistenzaTot = consistenzaTot;
	}
	public Integer getCountUiu() {
		return countUiu;
	}
	public void setCountUiu(Integer countUiu) {
		this.countUiu = countUiu;
	}

}
