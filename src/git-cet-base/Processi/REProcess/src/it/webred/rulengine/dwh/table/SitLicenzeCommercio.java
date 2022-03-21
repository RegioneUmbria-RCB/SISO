package it.webred.rulengine.dwh.table;

import java.math.BigDecimal;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;

public class SitLicenzeCommercio extends TabellaDwhMultiProv {

	private String numero;
	private String numeroProtocollo;
	private String annoProtocollo;
	private String tipologia;
	private String carattere;
	private String stato;
	private DataDwh dataInizioSospensione;
	private DataDwh dataFineSospensione;	
	private RelazioneToMasterTable idExtVie = new RelazioneToMasterTable(SitLicenzeCommercioVie.class, new ChiaveEsterna());
	private String civico;
	private String civico2;
	private String civico3;
	private BigDecimal superficieMinuto;
	private BigDecimal superficieTotale;
	private String sezioneCatastale;
	private String foglio;
	private String particella;
	private String subalterno;
	private String codiceFabbricato;
	private DataDwh dataValidita;
	private DataDwh dataRilascio;
	private String zona;
	private String ragSoc;
	private String note;
	private String riferimAtto;

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

	public DataDwh getDataInizioSospensione() {
		return dataInizioSospensione;
	}

	public void setDataInizioSospensione(DataDwh dataInizioSospensione) {
		this.dataInizioSospensione = dataInizioSospensione;
	}

	public DataDwh getDataFineSospensione() {
		return dataFineSospensione;
	}

	public void setDataFineSospensione(DataDwh dataFineSospensione) {
		this.dataFineSospensione = dataFineSospensione;
	}

	public Relazione getIdExtVie() {
		return idExtVie;
	}

	public void setIdExtVie(ChiaveEsterna idExtVie)
	{
		RelazioneToMasterTable r = new RelazioneToMasterTable(SitLicenzeCommercioVie.class, idExtVie);
		this.idExtVie = r;
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

	public BigDecimal getSuperficieMinuto() {
		return superficieMinuto;
	}

	public void setSuperficieMinuto(BigDecimal superficieMinuto) {
		this.superficieMinuto = superficieMinuto;
	}

	public BigDecimal getSuperficieTotale() {
		return superficieTotale;
	}

	public void setSuperficieTotale(BigDecimal superficieTotale) {
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

	public DataDwh getDataValidita() {
		return dataValidita;
	}

	public void setDataValidita(DataDwh dataValidita) {
		this.dataValidita = dataValidita;
	}

	public DataDwh getDataRilascio() {
		return dataRilascio;
	}

	public void setDataRilascio(DataDwh dataRilascio) {
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

	@Override
	public String getValueForCtrHash()
	{
		return this.numero +
		this.numeroProtocollo +
		this.annoProtocollo +
		this.tipologia +
		this.carattere +
		this.stato +
		this.dataInizioSospensione.getDataFormattata() +
		this.dataFineSospensione.getDataFormattata() +
		(String)this.idExtVie.getRelazione().getValore() +
		this.civico +
		this.civico2 +
		this.civico3 +
		this.superficieMinuto +
		this.superficieTotale +
		this.sezioneCatastale +
		this.foglio +
		this.particella +
		this.subalterno +
		this.codiceFabbricato +
		this.dataValidita.getDataFormattata() +
		this.dataRilascio.getDataFormattata() +
		this.zona +
		this.ragSoc +
		this.note +
		this.riferimAtto +
		this.getProvenienza();
	}
	
}
