package it.webred.rulengine.dwh.table;

import java.math.BigDecimal;
import it.webred.rulengine.dwh.def.DataDwh;

public class SitRttBollette extends TabellaDwh {

	private String codSoggetto;
	private String desIntestatario;
	private String codiceFiscale;
	private String indirizzo;
	private String recapito;
	private String codBolletta;
	private BigDecimal anno;
	private String codServizio;
	private BigDecimal idServizio;
	private String numBolletta;
	private BigDecimal numRate;
	private DataDwh dataBolletta;
	private String oggetto;
	private BigDecimal speseSpedizione;
	private BigDecimal totEsenteIva;
	private BigDecimal totImponibileIva;
	private BigDecimal totIva;
	private BigDecimal arrotondamentoPrec;
	private BigDecimal arrotondamentoAtt;
	private BigDecimal importoBollettaPrec;
	private BigDecimal totBolletta;
	private BigDecimal totPagato;
	private BigDecimal flNonPagare;
	private String motNonPagare;
	private String note;

	@Override
	public String getValueForCtrHash() {
		try {
			return codSoggetto
					+ desIntestatario
					+ codiceFiscale
					+ indirizzo
					+ recapito
					+ codBolletta
					+ (anno != null? anno.toString(): "")
					+ codServizio
					+ (idServizio != null? idServizio.toString(): "")
					+ numBolletta
					+ numRate
					+ (dataBolletta != null? dataBolletta.getDataFormattata(): "")
					+ oggetto
					+ speseSpedizione
					+ totEsenteIva
					+ totImponibileIva
					+ totIva
					+ arrotondamentoPrec
					+ arrotondamentoAtt
					+ importoBollettaPrec
					+ (totBolletta != null? totBolletta.toString(): "")
					+ (totPagato != null? totPagato.toString(): "")
					+ (flNonPagare != null? flNonPagare.toString(): "")
					+ motNonPagare
					+ note;
		} catch (Exception e) {
			return null;
		}

	}

	public String getCodSoggetto() {
		return codSoggetto;
	}

	public void setCodSoggetto(String codSoggetto) {
		this.codSoggetto = codSoggetto;
	}

	public String getDesIntestatario() {
		return desIntestatario;
	}

	public void setDesIntestatario(String desIntestatario) {
		this.desIntestatario = desIntestatario;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getRecapito() {
		return recapito;
	}

	public void setRecapito(String recapito) {
		this.recapito = recapito;
	}

	public String getCodBolletta() {
		return codBolletta;
	}

	public void setCodBolletta(String codBolletta) {
		this.codBolletta = codBolletta;
	}

	public BigDecimal getAnno() {
		return anno;
	}

	public void setAnno(BigDecimal anno) {
		this.anno = anno;
	}

	public String getCodServizio() {
		return codServizio;
	}

	public void setCodServizio(String codServizio) {
		this.codServizio = codServizio;
	}

	public BigDecimal getIdServizio() {
		return idServizio;
	}

	public void setIdServizio(BigDecimal idServizio) {
		this.idServizio = idServizio;
	}

	public String getNumBolletta() {
		return numBolletta;
	}

	public void setNumBolletta(String numBolletta) {
		this.numBolletta = numBolletta;
	}

	public DataDwh getDataBolletta() {
		return dataBolletta;
	}

	public void setDataBolletta(DataDwh dataBolletta) {
		this.dataBolletta = dataBolletta;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public BigDecimal getTotBolletta() {
		return totBolletta;
	}

	public void setTotBolletta(BigDecimal totBolletta) {
		this.totBolletta = totBolletta;
	}

	public BigDecimal getTotPagato() {
		return totPagato;
	}

	public void setTotPagato(BigDecimal totPagato) {
		this.totPagato = totPagato;
	}

	public BigDecimal getFlNonPagare() {
		return flNonPagare;
	}

	public void setFlNonPagare(BigDecimal flNonPagare) {
		this.flNonPagare = flNonPagare;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public BigDecimal getNumRate() {
		return numRate;
	}

	public void setNumRate(BigDecimal numRate) {
		this.numRate = numRate;
	}

	public BigDecimal getSpeseSpedizione() {
		return speseSpedizione;
	}

	public void setSpeseSpedizione(BigDecimal speseSpedizione) {
		this.speseSpedizione = speseSpedizione;
	}

	public BigDecimal getTotEsenteIva() {
		return totEsenteIva;
	}

	public void setTotEsenteIva(BigDecimal totEsenteIva) {
		this.totEsenteIva = totEsenteIva;
	}

	public BigDecimal getTotImponibileIva() {
		return totImponibileIva;
	}

	public void setTotImponibileIva(BigDecimal totImponibileIva) {
		this.totImponibileIva = totImponibileIva;
	}

	public BigDecimal getTotIva() {
		return totIva;
	}

	public void setTotIva(BigDecimal totIva) {
		this.totIva = totIva;
	}

	public BigDecimal getArrotondamentoPrec() {
		return arrotondamentoPrec;
	}

	public void setArrotondamentoPrec(BigDecimal arrotondamentoPrec) {
		this.arrotondamentoPrec = arrotondamentoPrec;
	}

	public BigDecimal getArrotondamentoAtt() {
		return arrotondamentoAtt;
	}

	public void setArrotondamentoAtt(BigDecimal arrotondamentoAtt) {
		this.arrotondamentoAtt = arrotondamentoAtt;
	}

	public BigDecimal getImportoBollettaPrec() {
		return importoBollettaPrec;
	}

	public void setImportoBollettaPrec(BigDecimal importoBollettaPrec) {
		this.importoBollettaPrec = importoBollettaPrec;
	}

	public String getMotNonPagare() {
		return motNonPagare;
	}

	public void setMotNonPagare(String motNonPagare) {
		this.motNonPagare = motNonPagare;
	}

}
