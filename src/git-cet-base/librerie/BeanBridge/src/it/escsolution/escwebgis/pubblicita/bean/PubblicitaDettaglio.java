package it.escsolution.escwebgis.pubblicita.bean;

import it.escsolution.escwebgis.common.EscObject;

import java.io.Serializable;
import java.math.BigDecimal;

public class PubblicitaDettaglio extends EscObject implements Serializable {

	private static final long serialVersionUID = 1L;
	String id;
	String tipoPratica;
	Integer numPratica;
	String annoPratica;
	String codClasse;
	String descClasse;
	String codOggetto;
	String descOggetto;
	String annotazioni;
	String indirizzo;
	String via;
	String civico;
	String dtInizio;
	String dtFine;
	Integer numGiorniOccupazione;
	String codZonaCespite;
	String descZonaCespite;
	BigDecimal base;
	BigDecimal altezza;
	BigDecimal supImponibile;
	BigDecimal supEsistente;
	String codCaratteristica;
	String descCaratteristica;
	Integer numEsempleri;
	Integer numFacce;

	public String getChiave() {
		return id;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTipoPratica() {
		return tipoPratica;
	}
	public void setTipoPratica(String tipoPratica) {
		this.tipoPratica = tipoPratica;
	}
	public Integer getNumPratica() {
		return numPratica;
	}
	public void setNumPratica(Integer numPratica) {
		this.numPratica = numPratica;
	}
	public String getAnnoPratica() {
		return annoPratica;
	}
	public void setAnnoPratica(String annoPratica) {
		this.annoPratica = annoPratica;
	}
	public String getCodClasse() {
		return codClasse;
	}
	public void setCodClasse(String codClasse) {
		this.codClasse = codClasse;
	}
	public String getDescClasse() {
		return descClasse;
	}
	public void setDescClasse(String descClasse) {
		this.descClasse = descClasse;
	}
	public String getCodOggetto() {
		return codOggetto;
	}
	public void setCodOggetto(String codOggetto) {
		this.codOggetto = codOggetto;
	}
	public String getDescOggetto() {
		return descOggetto;
	}
	public void setDescOggetto(String descOggetto) {
		this.descOggetto = descOggetto;
	}
	public String getAnnotazioni() {
		return annotazioni;
	}
	public void setAnnotazioni(String annotazioni) {
		this.annotazioni = annotazioni;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public String getDtInizio() {
		return dtInizio;
	}
	public void setDtInizio(String dtInizio) {
		this.dtInizio = dtInizio;
	}
	public String getDtFine() {
		return dtFine;
	}
	public void setDtFine(String dtFine) {
		this.dtFine = dtFine;
	}
	public Integer getNumGiorniOccupazione() {
		return numGiorniOccupazione;
	}
	public void setNumGiorniOccupazione(Integer numGiorniOccupazione) {
		this.numGiorniOccupazione = numGiorniOccupazione;
	}
	public String getCodZonaCespite() {
		return codZonaCespite;
	}
	public void setCodZonaCespite(String codZonaCespite) {
		this.codZonaCespite = codZonaCespite;
	}
	public String getDescZonaCespite() {
		return descZonaCespite;
	}
	public void setDescZonaCespite(String descZonaCespite) {
		this.descZonaCespite = descZonaCespite;
	}
	public BigDecimal getBase() {
		return base;
	}
	public void setBase(BigDecimal base) {
		this.base = base;
	}
	public BigDecimal getAltezza() {
		return altezza;
	}
	public void setAltezza(BigDecimal altezza) {
		this.altezza = altezza;
	}
	public BigDecimal getSupImponibile() {
		return supImponibile;
	}
	public void setSupImponibile(BigDecimal supImponibile) {
		this.supImponibile = supImponibile;
	}
	public BigDecimal getSupEsistente() {
		return supEsistente;
	}
	public void setSupEsistente(BigDecimal supEsistente) {
		this.supEsistente = supEsistente;
	}
	public String getCodCaratteristica() {
		return codCaratteristica;
	}
	public void setCodCaratteristica(String codCaratteristica) {
		this.codCaratteristica = codCaratteristica;
	}
	public String getDescCaratteristica() {
		return descCaratteristica;
	}
	public void setDescCaratteristica(String descCaratteristica) {
		this.descCaratteristica = descCaratteristica;
	}
	public Integer getNumEsempleri() {
		return numEsempleri;
	}
	public void setNumEsempleri(Integer numEsempleri) {
		this.numEsempleri = numEsempleri;
	}
	public Integer getNumFacce() {
		return numFacce;
	}
	public void setNumFacce(Integer numFacce) {
		this.numFacce = numFacce;
	}
	
}
