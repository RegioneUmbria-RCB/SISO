package it.siso.isee.obj;

import java.io.Serializable;

public class ModalitaCalcoloISEE implements Serializable{

	private String sommaRedditiComponenti;
	private String sommaRedditiComponentiCorrente;
	private String RedditoFigurativoPatrimonioMobiliare;
	private String detrazioni;
	private String ISR;
	private String patrimonioMobiliare;
	private String detrazioniPatrimonioMobiliare;
	private String ISP;
	private String ISEE;
	private String parametroNucleo;
	private String maggiorazioni;
	private String scalaEquivalenza;
	
	public String getSommaRedditiComponenti() {
		return sommaRedditiComponenti;
	}
	public void setSommaRedditiComponenti(String sommaRedditiComponenti) {
		this.sommaRedditiComponenti = sommaRedditiComponenti;
	}
	public String getSommaRedditiComponentiCorrente() {
		return sommaRedditiComponentiCorrente;
	}
	public void setSommaRedditiComponentiCorrente(
			String sommaRedditiComponentiCorrente) {
		this.sommaRedditiComponentiCorrente = sommaRedditiComponentiCorrente;
	}
	public String getRedditoFigurativoPatrimonioMobiliare() {
		return RedditoFigurativoPatrimonioMobiliare;
	}
	public void setRedditoFigurativoPatrimonioMobiliare(
			String redditoFigurativoPatrimonioMobiliare) {
		RedditoFigurativoPatrimonioMobiliare = redditoFigurativoPatrimonioMobiliare;
	}
	public String getDetrazioni() {
		return detrazioni;
	}
	public void setDetrazioni(String detrazioni) {
		this.detrazioni = detrazioni;
	}
	public String getISR() {
		return ISR;
	}
	public void setISR(String iSR) {
		ISR = iSR;
	}
	public String getPatrimonioMobiliare() {
		return patrimonioMobiliare;
	}
	public void setPatrimonioMobiliare(String patrimonioMobiliare) {
		this.patrimonioMobiliare = patrimonioMobiliare;
	}
	public String getDetrazioniPatrimonioMobiliare() {
		return detrazioniPatrimonioMobiliare;
	}
	public void setDetrazioniPatrimonioMobiliare(
			String detrazioniPatrimonioMobiliare) {
		this.detrazioniPatrimonioMobiliare = detrazioniPatrimonioMobiliare;
	}
	public String getISP() {
		return ISP;
	}
	public void setISP(String iSP) {
		ISP = iSP;
	}
	public String getISEE() {
		return ISEE;
	}
	public void setISEE(String iSEE) {
		ISEE = iSEE;
	}
	public String getParametroNucleo() {
		return parametroNucleo;
	}
	public void setParametroNucleo(String parametroNucleo) {
		this.parametroNucleo = parametroNucleo;
	}
	public String getMaggiorazioni() {
		return maggiorazioni;
	}
	public void setMaggiorazioni(String maggiorazioni) {
		this.maggiorazioni = maggiorazioni;
	}
	public String getScalaEquivalenza() {
		return scalaEquivalenza;
	}
	public void setScalaEquivalenza(String scalaEquivalenza) {
		this.scalaEquivalenza = scalaEquivalenza;
	}
	
	
}
