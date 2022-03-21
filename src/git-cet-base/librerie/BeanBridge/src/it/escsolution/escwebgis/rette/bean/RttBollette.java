package it.escsolution.escwebgis.rette.bean;

import it.escsolution.escwebgis.common.EscObject;
import it.escsolution.escwebgis.cosapNew.bean.CosapTassa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class RttBollette extends EscObject implements Serializable {


	private static final long serialVersionUID = 1L;
	String id;
	String codSoggetto;
	String desIntestatario;
	String codiceFiscale;
	String indirizzo;
	String recapito;
	String codBolletta;
	String anno;
	String codServizio;
	String idServizio;
	String numBolletta;
	String numRate;
	String dataBolletta;
	String oggetto;
	String speseSpedizione;
	String totEsenteIva;
	String totImponibileIva;
	String totIva;
	String arrotondamentoPrec;
	String arrotondamentoAtt;
	String importoBollettaPrec;
	String totBolletta;
	String totPagato;
	String flNonPagare;
	String motNonPagare;
	String note;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
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
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}
	public String getCodServizio() {
		return codServizio;
	}
	public void setCodServizio(String codServizio) {
		this.codServizio = codServizio;
	}
	public String getIdServizio() {
		return idServizio;
	}
	public void setIdServizio(String idServizio) {
		this.idServizio = idServizio;
	}
	public String getNumBolletta() {
		return numBolletta;
	}
	public void setNumBolletta(String numBolletta) {
		this.numBolletta = numBolletta;
	}
	public String getNumRate() {
		return numRate;
	}
	public void setNumRate(String numRate) {
		this.numRate = numRate;
	}
	public String getDataBolletta() {
		return dataBolletta;
	}
	public void setDataBolletta(String dataBolletta) {
		this.dataBolletta = dataBolletta;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getSpeseSpedizione() {
		return speseSpedizione;
	}
	public void setSpeseSpedizione(String speseSpedizione) {
		this.speseSpedizione = speseSpedizione;
	}
	public String getTotEsenteIva() {
		return totEsenteIva;
	}
	public void setTotEsenteIva(String totEsenteIva) {
		this.totEsenteIva = totEsenteIva;
	}
	public String getTotImponibileIva() {
		return totImponibileIva;
	}
	public void setTotImponibileIva(String totImponibileIva) {
		this.totImponibileIva = totImponibileIva;
	}
	public String getTotIva() {
		return totIva;
	}
	public void setTotIva(String totIva) {
		this.totIva = totIva;
	}
	public String getArrotondamentoPrec() {
		return arrotondamentoPrec;
	}
	public void setArrotondamentoPrec(String arrotondamentoPrec) {
		this.arrotondamentoPrec = arrotondamentoPrec;
	}
	public String getArrotondamentoAtt() {
		return arrotondamentoAtt;
	}
	public void setArrotondamentoAtt(String arrotondamentoAtt) {
		this.arrotondamentoAtt = arrotondamentoAtt;
	}
	public String getImportoBollettaPrec() {
		return importoBollettaPrec;
	}
	public void setImportoBollettaPrec(String importoBollettaPrec) {
		this.importoBollettaPrec = importoBollettaPrec;
	}
	public String getTotBolletta() {
		return totBolletta;
	}
	public void setTotBolletta(String totBolletta) {
		this.totBolletta = totBolletta;
	}
	public String getTotPagato() {
		return totPagato;
	}
	public void setTotPagato(String totPagato) {
		this.totPagato = totPagato;
	}
	public String getFlNonPagare() {
		return flNonPagare;
	}
	public void setFlNonPagare(String flNonPagare) {
		this.flNonPagare = flNonPagare;
	}
	public String getMotNonPagare() {
		return motNonPagare;
	}
	public void setMotNonPagare(String motNonPagare) {
		this.motNonPagare = motNonPagare;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
