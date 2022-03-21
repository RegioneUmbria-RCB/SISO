package it.webred.rulengine.dwh.table;

import java.math.BigDecimal;
import java.util.Date;

import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.TipoXml;

public class SitTrffMulte extends TabellaDwh {

	private String statoVerbale;
	private String tipoVerbale;
	private String nrVerbale;
	private String serieVerbale;
	private String annoVerbale;
	private BigDecimal dataMulta;
	private BigDecimal importoMulta;
	private BigDecimal importoDovuto;
	private BigDecimal dtScadenzaPagam;
	private String luogoInfrazione;
	private String note;
	private String tipoEnte;
	private String comuneEnte;
	private String targa;
	private String marca;
	private String modello;
	private String codicePersona;
	private String cognome;
	private String nome;
	private String luogoNascita;
	private String dtNascita;
	private String luogoResidenza;
	private String indirizzoResidenza;
	private String nrPatente;
	private BigDecimal dtRilascioPatente;
	private String provRilascioPatente;
	private String flagPagamento;
	private String estremiPagamento;
	private String sistemaPagamento;
	private BigDecimal dtPagamento;
	private BigDecimal importoPagato;
	private String codFisc;
	private BigDecimal importoScontato;

	@Override
	public String getValueForCtrHash() {
		try {
			return statoVerbale
					+ tipoVerbale
					+ nrVerbale
					+ serieVerbale
					+ annoVerbale
					+ (dataMulta != null? dataMulta.toString(): "")
					+ (importoMulta != null? importoMulta.toString(): "")
					+ (importoDovuto != null? importoDovuto.toString(): "")
					+ dtScadenzaPagam
					+ targa
					+ luogoInfrazione
					+ note
					+ tipoEnte
					+ comuneEnte
					+ marca
					+ modello
					+ codicePersona
					+ cognome
					+ nome
					+ luogoNascita
					+ dtNascita
					+ luogoResidenza
					+ indirizzoResidenza
					+ nrPatente
					+ (dtRilascioPatente != null ? dtRilascioPatente.toString(): "")
					+ provRilascioPatente + flagPagamento
					+ estremiPagamento + sistemaPagamento
					+ (dtPagamento != null ? dtPagamento.toString() : "")
					+ (importoPagato != null ? importoPagato.toString() : "")
					+ codFisc
					+ (importoScontato != null ? importoScontato.toString() : "");
		} catch (Exception e) {
			return null;
		}

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

	public BigDecimal getDataMulta() {
		return dataMulta;
	}

	public void setDataMulta(BigDecimal dataMulta) {
		this.dataMulta = dataMulta;
	}

	public BigDecimal getImportoMulta() {
		return importoMulta;
	}

	public void setImportoMulta(BigDecimal importoMulta) {
		this.importoMulta = importoMulta;
	}

	public BigDecimal getImportoDovuto() {
		return importoDovuto;
	}

	public void setImportoDovuto(BigDecimal importoDovuto) {
		this.importoDovuto = importoDovuto;
	}

	public BigDecimal getDtScadenzaPagam() {
		return dtScadenzaPagam;
	}

	public void setDtScadenzaPagam(BigDecimal dtScadenzaPagam) {
		this.dtScadenzaPagam = dtScadenzaPagam;
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

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModello() {
		return modello;
	}

	public void setModello(String modello) {
		this.modello = modello;
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

	public BigDecimal getDtRilascioPatente() {
		return dtRilascioPatente;
	}

	public void setDtRilascioPatente(BigDecimal dtRilascioPatente) {
		this.dtRilascioPatente = dtRilascioPatente;
	}

	public String getProvRilascioPatente() {
		return provRilascioPatente;
	}

	public void setProvRilascioPatente(String provRilascioPatente) {
		this.provRilascioPatente = provRilascioPatente;
	}

	public String getFlagPagamento() {
		return flagPagamento;
	}

	public void setFlagPagamento(String flagPagamento) {
		this.flagPagamento = flagPagamento;
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

	public BigDecimal getDtPagamento() {
		return dtPagamento;
	}

	public void setDtPagamento(BigDecimal dtPagamento) {
		this.dtPagamento = dtPagamento;
	}

	public BigDecimal getImportoPagato() {
		return importoPagato;
	}

	public void setImportoPagato(BigDecimal importoPagato) {
		this.importoPagato = importoPagato;
	}

	public String getCodFisc() {
		return codFisc;
	}

	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}

	public String getStatoVerbale() {
		return statoVerbale;
	}

	public void setStatoVerbale(String statoVerbale) {
		this.statoVerbale = statoVerbale;
	}

	public String getSerieVerbale() {
		return serieVerbale;
	}

	public void setSerieVerbale(String serieVerbale) {
		this.serieVerbale = serieVerbale;
	}

	public String getAnnoVerbale() {
		return annoVerbale;
	}

	public void setAnnoVerbale(String annoVerbale) {
		this.annoVerbale = annoVerbale;
	}

	public BigDecimal getImportoScontato() {
		return importoScontato;
	}

	public void setImportoScontato(BigDecimal importoScontato) {
		this.importoScontato = importoScontato;
	}

}
