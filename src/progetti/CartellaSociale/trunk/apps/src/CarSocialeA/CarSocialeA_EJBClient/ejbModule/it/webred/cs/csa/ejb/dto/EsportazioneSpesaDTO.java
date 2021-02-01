package it.webred.cs.csa.ejb.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class EsportazioneSpesaDTO implements Serializable {

	protected BigDecimal spesaTestata;
	protected BigDecimal spesaDettaglio;
	
	protected BigDecimal compartAltre;
	protected BigDecimal compartSsn;
	protected BigDecimal compartUtenti;
	protected BigDecimal percGestitaEnte;
	protected BigDecimal valoreGestitaEnte;
	
	
	public BigDecimal getCompartAltre() {
		return compartAltre;
	}
	public void setCompartAltre(BigDecimal compartAltre) {
		this.compartAltre = compartAltre;
	}
	public BigDecimal getCompartSsn() {
		return compartSsn;
	}
	public void setCompartSsn(BigDecimal compartSsn) {
		this.compartSsn = compartSsn;
	}
	public BigDecimal getCompartUtenti() {
		return compartUtenti;
	}
	public void setCompartUtenti(BigDecimal compartUtenti) {
		this.compartUtenti = compartUtenti;
	}
	public BigDecimal getPercGestitaEnte() {
		return percGestitaEnte;
	}
	public void setPercGestitaEnte(BigDecimal percGestitaEnte) {
		this.percGestitaEnte = percGestitaEnte;
	}
	public BigDecimal getValoreGestitaEnte() {
		return valoreGestitaEnte;
	}
	public void setValoreGestitaEnte(BigDecimal valoreGestitaEnte) {
		this.valoreGestitaEnte = valoreGestitaEnte;
	}
	
	public BigDecimal getSpesaTestata() {
		return spesaTestata;
	}

	public void setSpesaTestata(BigDecimal spesaTestata) {
		this.spesaTestata = spesaTestata;
	}

	public BigDecimal getSpesaDettaglio() {
		return spesaDettaglio;
	}

	public void setSpesaDettaglio(BigDecimal spesaDettaglio) {
		this.spesaDettaglio = spesaDettaglio;
	}

	public BigDecimal getSpesa() {
		BigDecimal spTestata = getSpesaTestata() == null ? BigDecimal.ZERO : getSpesaTestata();
		return getSpesaDettaglio()==null?spTestata:getSpesaDettaglio();
	} 
}
