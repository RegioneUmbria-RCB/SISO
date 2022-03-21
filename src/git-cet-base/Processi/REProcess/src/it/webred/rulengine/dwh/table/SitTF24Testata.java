package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.DataDwh;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class SitTF24Testata extends TabellaDwh {

	private DataDwh dtFornitura;
	private BigDecimal progFornitura;
	private BigDecimal numTrasmissione;
	private String codValuta;
	private String codEnte;
	private BigDecimal codIntermediario;
	private String idFile;
	private BigDecimal numG1;
	private BigDecimal numG2;
	private BigDecimal numG3;
	private BigDecimal numG4;
	private BigDecimal numG5;
	private BigDecimal numG9;
	private BigDecimal numTot;
	private String annoMeseRif = "";
	private String release = "";
			

	public DataDwh getDtFornitura() {
		return dtFornitura;
	}



	public void setDtFornitura(DataDwh dataFornitura) {
		this.dtFornitura = dataFornitura;
	}

	public String getCodValuta() {
		return codValuta;
	}



	public void setCodValuta(String codValuta) {
		this.codValuta = codValuta;
	}



	public String getCodEnte() {
		return codEnte;
	}



	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}


	public String getIdFile() {
		return idFile;
	}



	public void setIdFile(String idFile) {
		this.idFile = idFile;
	}



	public BigDecimal getNumG1() {
		return numG1;
	}



	public void setNumG1(BigDecimal numG1) {
		this.numG1 = numG1;
	}



	public BigDecimal getNumG2() {
		return numG2;
	}



	public void setNumG2(BigDecimal numG2) {
		this.numG2 = numG2;
	}



	@Override
	public String getValueForCtrHash()
	{
		return this.dtFornitura.getDataFormattata() +
		this.progFornitura.toString() +
		this.numTrasmissione.toString() +
		this.codValuta +
		this.codEnte +
		this.codIntermediario.toString() +
		this.idFile + this.numG1.toString() + this.numG2.toString() +
		this.numG3.toString() + this.numG4.toString() + this.numG5.toString() + this.numG9.toString() + this.numTot.toString() + this.annoMeseRif + this.release;
	}


	public BigDecimal getNumG3() {
		return numG3;
	}



	public void setNumG3(BigDecimal numG3) {
		this.numG3 = numG3;
	}



	public BigDecimal getNumG4() {
		return numG4;
	}



	public void setNumG4(BigDecimal numG4) {
		this.numG4 = numG4;
	}



	public BigDecimal getNumG5() {
		return numG5;
	}



	public void setNumG5(BigDecimal numG5) {
		this.numG5 = numG5;
	}



	public BigDecimal getNumG9() {
		return numG9;
	}



	public void setNumG9(BigDecimal numG9) {
		this.numG9 = numG9;
	}



	public BigDecimal getNumTot() {
		return numTot;
	}



	public void setNumTot(BigDecimal numTot) {
		this.numTot = numTot;
	}




	public BigDecimal getProgFornitura() {
		return progFornitura;
	}



	public void setProgFornitura(BigDecimal progFornitura) {
		this.progFornitura = progFornitura;
	}



	public BigDecimal getCodIntermediario() {
		return codIntermediario;
	}



	public void setCodIntermediario(BigDecimal codIntermediario) {
		this.codIntermediario = codIntermediario;
	}



	public BigDecimal getNumTrasmissione() {
		return numTrasmissione;
	}



	public void setNumTrasmissione(BigDecimal numTrasmissione) {
		this.numTrasmissione = numTrasmissione;
	}



	public String getAnnoMeseRif() {
		return annoMeseRif;
	}



	public void setAnnoMeseRif(String annoMeseRif) {
		this.annoMeseRif = annoMeseRif;
	}



	public String getRelease() {
		return release;
	}



	public void setRelease(String release) {
		this.release = release;
	}

	
}
