package it.webred.mui.model;


import java.math.BigDecimal;

public class Possesore extends Familiare{

	private BigDecimal percentualePossesso;
	public BigDecimal getPercentualePossesso() {
		return percentualePossesso;
	}
	public void setPercentualePossesso(BigDecimal percentualePossesso) {
		this.percentualePossesso = percentualePossesso;
	}
	public Possesore() {
		super();
		// TODO Auto-generated constructor stub
	}
	public boolean equals(Object obj){
		return super.equals(obj);
	}
}
