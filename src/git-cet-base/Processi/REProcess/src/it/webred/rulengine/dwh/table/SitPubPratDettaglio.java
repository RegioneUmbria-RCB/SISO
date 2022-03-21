package it.webred.rulengine.dwh.table;

import java.math.BigDecimal;
import java.sql.Timestamp;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;

public class SitPubPratDettaglio extends TabellaDwhMultiProv {
	
	private RelazioneToMasterTable idExtTestata = new RelazioneToMasterTable(SitPubPratTestata.class, new ChiaveEsterna());
	private String tipoPratica ;
	private	BigDecimal numPratica ;
	private	String annoPratica;
	private	String codCls;
	private	String descCls;
	private	String codOggetto;
	private	String descOggetto;
	private	String annotazioni;
	private String indirizzo;
	private	String via;
	private	String civico;
	private	DataDwh dtInizio;
	private	DataDwh dtFine;
	private	BigDecimal giorniOccupazione;
	private	String codZonaCespite;
	private	String descZonaCespite;
	
	private	BigDecimal base;
	private	BigDecimal altezza;
	private	BigDecimal supImponibile;
	private	BigDecimal supEsistente;
	
	private	String codCaratteristica;
	private	String descCaratteristica;
	
	private	BigDecimal numEsemplari;
	private	BigDecimal numFacce;
	
	
	public String getTipoPratica() {
		return tipoPratica;
	}

	public void setTipoPratica(String tipoPratica) {
		this.tipoPratica = tipoPratica;
	}

	public BigDecimal getNumPratica() {
		return numPratica;
	}

	public void setNumPratica(BigDecimal numPratica) {
		this.numPratica = numPratica;
	}

	public BigDecimal getGiorniOccupazione() {
		return giorniOccupazione;
	}

	public void setGiorniOccupazione(BigDecimal giorniOccupazione) {
		this.giorniOccupazione = giorniOccupazione;
	}

	public BigDecimal getNumEsemplari() {
		return numEsemplari;
	}

	public void setNumEsemplari(BigDecimal numEsemplari) {
		this.numEsemplari = numEsemplari;
	}

	public BigDecimal getNumFacce() {
		return numFacce;
	}

	public void setNumFacce(BigDecimal numFacce) {
		this.numFacce = numFacce;
	}

	public void setIdExtTestata(RelazioneToMasterTable idExtTestata) {
		this.idExtTestata = idExtTestata;
	}

	public String getAnnoPratica() {
		return annoPratica;
	}

	public void setAnnoPratica(String annoPratica) {
		this.annoPratica = annoPratica;
	}

	public String getCodCls() {
		return codCls;
	}

	public void setCodCls(String codCls) {
		this.codCls = codCls;
	}

	public String getDescCls() {
		return descCls;
	}

	public void setDescCls(String descCls) {
		this.descCls = descCls;
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

	public DataDwh getDtInizio() {
		return dtInizio;
	}

	public void setDtInizio(DataDwh dtInizio) {
		this.dtInizio = dtInizio;
	}

	public DataDwh getDtFine() {
		return dtFine;
	}

	public void setDtFine(DataDwh dtFine) {
		this.dtFine = dtFine;
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

	

	@Override
	public String getValueForCtrHash()
	{
		return this.getTipoPratica() +
				(String)this.idExtTestata.getRelazione().getValore() +
				this.getNumPratica() +
				this.getAnnoPratica() + 
				this.getCodCls() +
				this.getDescCls() + 
				this.getCodOggetto() +
				this.getDescOggetto() +
				this.getAnnotazioni() +
				this.getIndirizzo() +
				this.getVia() +
				this.getCivico() +
				this.getDtInizio().getDataFormattata() +
				this.getDtFine().getDataFormattata() +
				this.getGiorniOccupazione() +
				this.getCodZonaCespite() +
				this.getDescZonaCespite() +
				this.getBase()+
				this.getAltezza() +
				this.getSupImponibile() +
				this.getSupEsistente() +
				this.getCodCaratteristica() +
				this.getDescCaratteristica() +
				this.getNumEsemplari() +
				this.getNumFacce() +
				this.getProvenienza();
		
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public RelazioneToMasterTable getIdExtTestata() {
		return idExtTestata;
	}
	
	public void setIdExtTestata(ChiaveEsterna idExtTestata) {
		RelazioneToMasterTable r = new RelazioneToMasterTable(SitPubPratTestata.class, idExtTestata);
		this.idExtTestata = r;
	}
	
}
