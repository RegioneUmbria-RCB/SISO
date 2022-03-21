package it.webred.cs.csa.ejb.dto.erogazioni;

import java.io.Serializable;
import java.math.BigDecimal;

public class SpesaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private boolean calcolata;
	private BigDecimal totale;
	private BigDecimal percGestitaEnte;
	private BigDecimal valGestitaEnte;
	
	public SpesaDTO(BigDecimal totale, BigDecimal percentuale, BigDecimal valore, boolean calcolata){
		this.totale=totale;
		this.percGestitaEnte=percentuale;
		this.valGestitaEnte=valore;
		this.calcolata = calcolata;
	}

	public BigDecimal getTotale() {
		return totale;
	}

	public BigDecimal getPercGestitaEnte() {
		return percGestitaEnte;
	}

	public BigDecimal getValGestitaEnte() {
		return valGestitaEnte;
	}

	public void setTotale(BigDecimal totale) {
		this.totale = totale;
	}

	public void setPercGestitaEnte(BigDecimal percGestitaEnte) {
		this.percGestitaEnte = percGestitaEnte;
	}

	public void setValGestitaEnte(BigDecimal valGestitaEnte) {
		this.valGestitaEnte = valGestitaEnte;
	}
	
	public String getDescrizione(){
		String txt="";
		
		String euroSymbol = new String(" \u20ac ");
		
		if(totale != null) //SISO-810
			txt += "<strong>Totale</strong>= "+totale+euroSymbol+"<br/>";
		
		if(percGestitaEnte != null && percGestitaEnte.compareTo(BigDecimal.ZERO)>0)
			txt += "<strong>% gestita direttamente</strong> = "+percGestitaEnte+" % <br/>";
		
		if(valGestitaEnte != null && valGestitaEnte.compareTo(BigDecimal.ZERO)>0)
			txt += "<strong>Val. gestito direttamente</strong> = "+valGestitaEnte+euroSymbol +"<br/>";
		
		
		return txt;
	}

	public boolean isCalcolata() {
		return calcolata;
	}

	public void setCalcolata(boolean calcolata) {
		this.calcolata = calcolata;
	}
	
}
