package it.escsolution.escwebgis.rette.bean;

import it.escsolution.escwebgis.common.EscObject;
import it.escsolution.escwebgis.cosapNew.bean.CosapTassa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class RttRate extends EscObject implements Serializable {


	private static final long serialVersionUID = 1L;
	String id;
	String codBolletta;
	String codServizio;
	String numRata;
	String dtScadenzaRata;
	String importoRata;
	String importoPagato;
	String dtPagamento;
	String dtRegPagamento;
	String desDistinta;
	String dtDistinta;
	String idServizio;
	String idPratica;
	String desCanale;
	String desPagamento;
	String note;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCodBolletta() {
		return codBolletta;
	}
	public void setCodBolletta(String codBolletta) {
		this.codBolletta = codBolletta;
	}
	public String getCodServizio() {
		return codServizio;
	}
	public void setCodServizio(String codServizio) {
		this.codServizio = codServizio;
	}
	public String getNumRata() {
		return numRata;
	}
	public void setNumRata(String numRata) {
		this.numRata = numRata;
	}
	public String getDtScadenzaRata() {
		return dtScadenzaRata;
	}
	public void setDtScadenzaRata(String dtScadenzaRata) {
		this.dtScadenzaRata = dtScadenzaRata;
	}
	public String getImportoRata() {
		return importoRata;
	}
	public void setImportoRata(String importoRata) {
		this.importoRata = importoRata;
	}
	public String getImportoPagato() {
		return importoPagato;
	}
	public void setImportoPagato(String importoPagato) {
		this.importoPagato = importoPagato;
	}
	public String getDtPagamento() {
		return dtPagamento;
	}
	public void setDtPagamento(String dtPagamento) {
		this.dtPagamento = dtPagamento;
	}
	public String getDtRegPagamento() {
		return dtRegPagamento;
	}
	public void setDtRegPagamento(String dtRegPagamento) {
		this.dtRegPagamento = dtRegPagamento;
	}
	public String getDesDistinta() {
		return desDistinta;
	}
	public void setDesDistinta(String desDistinta) {
		this.desDistinta = desDistinta;
	}
	public String getDtDistinta() {
		return dtDistinta;
	}
	public void setDtDistinta(String dtDistinta) {
		this.dtDistinta = dtDistinta;
	}
	public String getIdServizio() {
		return idServizio;
	}
	public void setIdServizio(String idServizio) {
		this.idServizio = idServizio;
	}
	public String getIdPratica() {
		return idPratica;
	}
	public void setIdPratica(String idPratica) {
		this.idPratica = idPratica;
	}
	public String getDesCanale() {
		return desCanale;
	}
	public void setDesCanale(String desCanale) {
		this.desCanale = desCanale;
	}
	public String getDesPagamento() {
		return desPagamento;
	}
	public void setDesPagamento(String desPagamento) {
		this.desPagamento = desPagamento;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
