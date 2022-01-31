package it.escsolution.escwebgis.multe.bean;

import it.escsolution.escwebgis.common.EscObject;
import it.escsolution.escwebgis.cosapNew.bean.CosapTassa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Multe extends EscObject implements Serializable {


	private static final long serialVersionUID = 1L;
	String id;
	String tipoVerbale;
	String nrVerbale;
	String dataMulta;
	String importoMulta;
	String importoDovuto;
	String dtScadenzaPagamento;
	String luogoInfrazione;
	String note;
	String tipoEnte;
	String comuneEnte;
	String targa;
	String modello;
	String marca;
	String codicePersona;
	String cognome;
	String nome;
	String luogoNascita;
	String dtNascita;
	String luogoResidenza;
	String indirizzoResidenza;
	String nrPatente;
	String dtRilascioPatente;
	String provRilascioPatente;
	String flagPagamento;
	String estremiPagamento;
	String sistemaPagamento;
	String dtPagamento;
	String importoPagato;
	String codFisc;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTipoVerbale() {
		return tipoVerbale;
	}
	public void setTipoVerbale(String tipoVerbale) {
		this.tipoVerbale = tipoVerbale;
	}
	public String getNrVerbale() {
		return nrVerbale;
	}
	public void setNrVerbale(String nrVerbale) {
		this.nrVerbale = nrVerbale;
	}
	public String getImportoMulta() {
		return importoMulta;
	}
	public void setImportoMulta(String importoMulta) {
		this.importoMulta = importoMulta;
	}
	public String getImportoDovuto() {
		return importoDovuto;
	}
	public void setImportoDovuto(String importoDovuto) {
		this.importoDovuto = importoDovuto;
	}
	public String getDtScadenzaPagamento() {
		return dtScadenzaPagamento;
	}
	public void setDtScadenzaPagamento(String dtScadenzaPagamento) {
		this.dtScadenzaPagamento = dtScadenzaPagamento;
	}
	public String getLuogoInfrazione() {
		return luogoInfrazione;
	}
	public void setLuogoInfrazione(String luogoInfrazione) {
		this.luogoInfrazione = luogoInfrazione;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getTipoEnte() {
		return tipoEnte;
	}
	public void setTipoEnte(String tipoEnte) {
		this.tipoEnte = tipoEnte;
	}
	public String getComuneEnte() {
		return comuneEnte;
	}
	public void setComuneEnte(String comuneEnte) {
		this.comuneEnte = comuneEnte;
	}
	public String getTarga() {
		return targa;
	}
	public void setTarga(String targa) {
		this.targa = targa;
	}
	public String getModello() {
		return modello;
	}
	public void setModello(String modello) {
		this.modello = modello;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getCodicePersona() {
		return codicePersona;
	}
	public void setCodicePersona(String codicePersona) {
		this.codicePersona = codicePersona;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLuogoNascita() {
		return luogoNascita;
	}
	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}
	public String getDtNascita() {
		return dtNascita;
	}
	public void setDtNascita(String dtNascita) {
		this.dtNascita = dtNascita;
	}
	public String getLuogoResidenza() {
		return luogoResidenza;
	}
	public void setLuogoResidenza(String luogoResidenza) {
		this.luogoResidenza = luogoResidenza;
	}
	public String getIndirizzoResidenza() {
		return indirizzoResidenza;
	}
	public void setIndirizzoResidenza(String indirizzoResidenza) {
		this.indirizzoResidenza = indirizzoResidenza;
	}
	public String getNrPatente() {
		return nrPatente;
	}
	public void setNrPatente(String nrPatente) {
		this.nrPatente = nrPatente;
	}
	public String getDtRilascioPatente() {
		return dtRilascioPatente;
	}
	public void setDtRilascioPatente(String dtRilascioPatente) {
		this.dtRilascioPatente = dtRilascioPatente;
	}
	public String getProvRilascioPatente() {
		return provRilascioPatente;
	}
	public void setProvRilascioPatente(String provRilascioPatente) {
		this.provRilascioPatente = provRilascioPatente;
	}
	public String getEstremiPagamento() {
		return estremiPagamento;
	}
	public void setEstremiPagamento(String estremiPagamento) {
		this.estremiPagamento = estremiPagamento;
	}
	public String getSistemaPagamento() {
		return sistemaPagamento;
	}
	public void setSistemaPagamento(String sistemaPagamento) {
		this.sistemaPagamento = sistemaPagamento;
	}
	public String getDtPagamento() {
		return dtPagamento;
	}
	public void setDtPagamento(String dtPagamento) {
		this.dtPagamento = dtPagamento;
	}
	public String getImportoPagato() {
		return importoPagato;
	}
	public void setImportoPagato(String importoPagato) {
		this.importoPagato = importoPagato;
	}
	public String getCodFisc() {
		return codFisc;
	}
	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}
	public String getDataMulta() {
		return dataMulta;
	}
	public void setDataMulta(String dataMulta) {
		this.dataMulta = dataMulta;
	}
	public String getFlagPagamento() {
		return flagPagamento;
	}
	public void setFlagPagamento(String flagPagamento) {
		this.flagPagamento = flagPagamento;
	}

}
