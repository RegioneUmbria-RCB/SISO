package it.escsolution.escwebgis.licenzeCommercioNew.bean;

import it.escsolution.escwebgis.common.EscObject;

import java.io.Serializable;
import java.util.ArrayList;

public class LicenzeCommercioNew extends EscObject implements Serializable {

	private String id;
	private String idExt;
	private String idOrig;
	
	private String numero;
	private String numeroProtocollo;
	private String annoProtocollo;
	private String tipologia;
	private String carattere;
	private String stato;
	private String dataInizioSospensione;
	private String dataFineSospensione;
	private String indirizzo;
	private String civico;
	private String civico2;
	private String civico3;
	private String superficieMinuto;
	private String superficieTotale;
	private String sezioneCatastale;
	private String foglio;
	private String particella;
	private String subalterno;
	private String codiceFabbricato;
	private String dataValidita;
	private String dataRilascio;
	private String zona;
	private String ragSoc;
	private String note;
	private String riferimAtto;
	private String provenienza;
	private ArrayList<LicenzeCommercioAnagNew> anags;
	private String idVia;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdExt() {
		return idExt;
	}
	public void setIdExt(String idExt) {
		this.idExt = idExt;
	}
	public String getIdOrig() {
		return idOrig;
	}
	public void setIdOrig(String idOrig) {
		this.idOrig = idOrig;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}
	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}
	public String getAnnoProtocollo() {
		return annoProtocollo;
	}
	public void setAnnoProtocollo(String annoProtocollo) {
		this.annoProtocollo = annoProtocollo;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public String getCarattere() {
		return carattere;
	}
	public void setCarattere(String carattere) {
		this.carattere = carattere;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getDataInizioSospensione() {
		return dataInizioSospensione;
	}
	public void setDataInizioSospensione(String dataInizioSospensione) {
		this.dataInizioSospensione = dataInizioSospensione;
	}
	public String getDataFineSospensione() {
		return dataFineSospensione;
	}
	public void setDataFineSospensione(String dataFineSospensione) {
		this.dataFineSospensione = dataFineSospensione;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public String getCivico2() {
		return civico2;
	}
	public void setCivico2(String civico2) {
		this.civico2 = civico2;
	}
	public String getCivico3() {
		return civico3;
	}
	public void setCivico3(String civico3) {
		this.civico3 = civico3;
	}
	public String getSuperficieMinuto() {
		return superficieMinuto;
	}
	public void setSuperficieMinuto(String superficieMinuto) {
		this.superficieMinuto = superficieMinuto;
	}
	public String getSuperficieTotale() {
		return superficieTotale;
	}
	public void setSuperficieTotale(String superficieTotale) {
		this.superficieTotale = superficieTotale;
	}
	public String getSezioneCatastale() {
		return sezioneCatastale;
	}
	public void setSezioneCatastale(String sezioneCatastale) {
		this.sezioneCatastale = sezioneCatastale;
	}
	public String getFoglio() {
		return foglio;
	}
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	public String getParticella() {
		return particella;
	}
	public void setParticella(String particella) {
		this.particella = particella;
	}
	public String getSubalterno() {
		return subalterno;
	}
	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}
	public String getCodiceFabbricato() {
		return codiceFabbricato;
	}
	public void setCodiceFabbricato(String codiceFabbricato) {
		this.codiceFabbricato = codiceFabbricato;
	}
	public String getDataValidita() {
		return dataValidita;
	}
	public void setDataValidita(String dataValidita) {
		this.dataValidita = dataValidita;
	}
	public String getDataRilascio() {
		return dataRilascio;
	}
	public void setDataRilascio(String dataRilascio) {
		this.dataRilascio = dataRilascio;
	}
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public String getRagSoc() {
		return ragSoc;
	}
	public void setRagSoc(String ragSoc) {
		this.ragSoc = ragSoc;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getRiferimAtto() {
		return riferimAtto;
	}
	public void setRiferimAtto(String riferimAtto) {
		this.riferimAtto = riferimAtto;
	}
	public String getProvenienza() {
		return provenienza;
	}
	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}
	public ArrayList<LicenzeCommercioAnagNew> getAnags() {
		return anags;
	}
	public void setAnags(ArrayList<LicenzeCommercioAnagNew> anags) {
		this.anags = anags;
	}
	public String getIdVia() {
		return idVia;
	}
	public void setIdVia(String idVia) {
		this.idVia = idVia;
	}
	
}
