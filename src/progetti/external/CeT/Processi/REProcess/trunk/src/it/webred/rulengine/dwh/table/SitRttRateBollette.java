package it.webred.rulengine.dwh.table;

import java.math.BigDecimal;
import it.webred.rulengine.dwh.def.DataDwh;

public class SitRttRateBollette extends TabellaDwh {

	private String codBolletta;
	private String codServizio;
	private BigDecimal numRata;
	private DataDwh dtScadenzaRata;
	private BigDecimal importoRata;
	private BigDecimal importoPagato;
	private DataDwh dtPagamento;
	private DataDwh dtRegPagamento;
	private String desDistinta;
	private DataDwh dtDistinta;
	private BigDecimal idServizio;
	private String idPratica;
	private String desCanale;
	private String desPagamento;
	private String note;

	@Override
	public String getValueForCtrHash() {
		try {
			return codBolletta
					+ codServizio
					+ (numRata != null? numRata.toString(): "")
					+ (dtScadenzaRata != null? dtScadenzaRata.getDataFormattata(): "")
					+ (importoRata != null? importoRata.toString(): "")
					+ (importoPagato != null? importoPagato.toString(): "")
					+ (dtPagamento != null? dtPagamento.getDataFormattata(): "")
					+ (dtRegPagamento != null? dtRegPagamento.getDataFormattata(): "")
					+ desDistinta
					+ (dtDistinta != null? dtDistinta.toString(): "")
					+ idServizio
					+ idPratica
					+ desCanale
					+ desPagamento
					+ note;
		} catch (Exception e) {
			return null;
		}

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

	public BigDecimal getNumRata() {
		return numRata;
	}

	public void setNumRata(BigDecimal numRata) {
		this.numRata = numRata;
	}

	public DataDwh getDtScadenzaRata() {
		return dtScadenzaRata;
	}

	public void setDtScadenzaRata(DataDwh dtScadenzaRata) {
		this.dtScadenzaRata = dtScadenzaRata;
	}

	public BigDecimal getImportoRata() {
		return importoRata;
	}

	public void setImportoRata(BigDecimal importoRata) {
		this.importoRata = importoRata;
	}

	public BigDecimal getImportoPagato() {
		return importoPagato;
	}

	public void setImportoPagato(BigDecimal importoPagato) {
		this.importoPagato = importoPagato;
	}

	public DataDwh getDtPagamento() {
		return dtPagamento;
	}

	public void setDtPagamento(DataDwh dtPagamento) {
		this.dtPagamento = dtPagamento;
	}

	public DataDwh getDtRegPagamento() {
		return dtRegPagamento;
	}

	public void setDtRegPagamento(DataDwh dtRegPagamento) {
		this.dtRegPagamento = dtRegPagamento;
	}

	public String getDesDistinta() {
		return desDistinta;
	}

	public void setDesDistinta(String desDistinta) {
		this.desDistinta = desDistinta;
	}

	public DataDwh getDtDistinta() {
		return dtDistinta;
	}

	public void setDtDistinta(DataDwh dtDistinta) {
		this.dtDistinta = dtDistinta;
	}

	public BigDecimal getIdServizio() {
		return idServizio;
	}

	public void setIdServizio(BigDecimal idServizio) {
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
