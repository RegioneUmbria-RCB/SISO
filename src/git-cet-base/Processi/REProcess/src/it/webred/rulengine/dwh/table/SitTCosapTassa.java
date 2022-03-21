package it.webred.rulengine.dwh.table;

import java.math.BigDecimal;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneToMasterTable;

public class SitTCosapTassa extends TabellaDwhMultiProv {
	
	private String codUnivocoCanone;
	private RelazioneToMasterTable idExtContrib = new RelazioneToMasterTable(SitTCosapContrib.class, new ChiaveEsterna());
	private String tipoDocumento;
	private String numeroDocumento;
	private String annoDocumento;
	private String statoDocumento;
	private String numeroProtocollo;
	private String annoContabileDocumento;
	private DataDwh dtProtocollo;
	private DataDwh dtIniValidita;
	private DataDwh dtFinValidita;
	private DataDwh dtRichiesta;
	private String tipoOccupazione;
	private String codiceImmobile;
	private String codiceVia;
	private String sedime;
	private String indirizzo;
	private String civico;
	private String zona;
	private BigDecimal foglio;
	private String particella;
	private BigDecimal subalterno;
	private BigDecimal quantita;
	private String unitaMisuraQuantita;
	private BigDecimal tariffaPerUnita;
	private BigDecimal importoCanone;
	private DataDwh dtIniValiditaTariffa;
	private DataDwh dtFinValiditaTariffa;
	private String descrizione;
	private String note;
	private String codiceEsenzione;
	private BigDecimal scontoAssoluto;
	private BigDecimal percentualeSconto;
	private DataDwh dtIniSconto;
	private DataDwh dtFinSconto;
	
	public String getCodUnivocoCanone() {
		return codUnivocoCanone;
	}
	
	public void setCodUnivocoCanone(String codUnivocoCanone) {
		this.codUnivocoCanone = codUnivocoCanone;
	}
	
	public Relazione getIdExtContrib() {
		return idExtContrib;
	}

	public void setIdExtContrib(ChiaveEsterna idExtContrib) {
		RelazioneToMasterTable r = new RelazioneToMasterTable(SitTCosapContrib.class, idExtContrib);
		this.idExtContrib = r;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getAnnoDocumento() {
		return annoDocumento;
	}

	public void setAnnoDocumento(String annoDocumento) {
		this.annoDocumento = annoDocumento;
	}

	public String getStatoDocumento() {
		return statoDocumento;
	}

	public void setStatoDocumento(String statoDocumento) {
		this.statoDocumento = statoDocumento;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public String getAnnoContabileDocumento() {
		return annoContabileDocumento;
	}

	public void setAnnoContabileDocumento(String annoContabileDocumento) {
		this.annoContabileDocumento = annoContabileDocumento;
	}

	public DataDwh getDtProtocollo() {
		return dtProtocollo;
	}

	public void setDtProtocollo(DataDwh dtProtocollo) {
		this.dtProtocollo = dtProtocollo;
	}

	public DataDwh getDtIniValidita() {
		return dtIniValidita;
	}

	public void setDtIniValidita(DataDwh dtIniValidita) {
		this.dtIniValidita = dtIniValidita;
	}

	public DataDwh getDtFinValidita() {
		return dtFinValidita;
	}

	public void setDtFinValidita(DataDwh dtFinValidita) {
		this.dtFinValidita = dtFinValidita;
	}

	public DataDwh getDtRichiesta() {
		return dtRichiesta;
	}

	public void setDtRichiesta(DataDwh dtRichiesta) {
		this.dtRichiesta = dtRichiesta;
	}

	public String getTipoOccupazione() {
		return tipoOccupazione;
	}

	public void setTipoOccupazione(String tipoOccupazione) {
		this.tipoOccupazione = tipoOccupazione;
	}

	public String getCodiceImmobile() {
		return codiceImmobile;
	}

	public void setCodiceImmobile(String codiceImmobile) {
		this.codiceImmobile = codiceImmobile;
	}

	public String getCodiceVia() {
		return codiceVia;
	}

	public void setCodiceVia(String codiceVia) {
		this.codiceVia = codiceVia;
	}

	public String getSedime() {
		return sedime;
	}

	public void setSedime(String sedime) {
		this.sedime = sedime;
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

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public BigDecimal getFoglio() {
		return foglio;
	}

	public void setFoglio(BigDecimal foglio) {
		this.foglio = foglio;
	}

	public String getParticella() {
		return particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public BigDecimal getSubalterno() {
		return subalterno;
	}

	public void setSubalterno(BigDecimal subalterno) {
		this.subalterno = subalterno;
	}

	public BigDecimal getQuantita() {
		return quantita;
	}

	public void setQuantita(BigDecimal quantita) {
		this.quantita = quantita;
	}

	public String getUnitaMisuraQuantita() {
		return unitaMisuraQuantita;
	}

	public void setUnitaMisuraQuantita(String unitaMisuraQuantita) {
		this.unitaMisuraQuantita = unitaMisuraQuantita;
	}

	public BigDecimal getTariffaPerUnita() {
		return tariffaPerUnita;
	}

	public void setTariffaPerUnita(BigDecimal tariffaPerUnita) {
		this.tariffaPerUnita = tariffaPerUnita;
	}

	public BigDecimal getImportoCanone() {
		return importoCanone;
	}

	public void setImportoCanone(BigDecimal importoCanone) {
		this.importoCanone = importoCanone;
	}

	public DataDwh getDtIniValiditaTariffa() {
		return dtIniValiditaTariffa;
	}

	public void setDtIniValiditaTariffa(DataDwh dtIniValiditaTariffa) {
		this.dtIniValiditaTariffa = dtIniValiditaTariffa;
	}

	public DataDwh getDtFinValiditaTariffa() {
		return dtFinValiditaTariffa;
	}

	public void setDtFinValiditaTariffa(DataDwh dtFinValiditaTariffa) {
		this.dtFinValiditaTariffa = dtFinValiditaTariffa;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCodiceEsenzione() {
		return codiceEsenzione;
	}

	public void setCodiceEsenzione(String codiceEsenzione) {
		this.codiceEsenzione = codiceEsenzione;
	}

	public BigDecimal getScontoAssoluto() {
		return scontoAssoluto;
	}

	public void setScontoAssoluto(BigDecimal scontoAssoluto) {
		this.scontoAssoluto = scontoAssoluto;
	}

	public BigDecimal getPercentualeSconto() {
		return percentualeSconto;
	}

	public void setPercentualeSconto(BigDecimal percentualeSconto) {
		this.percentualeSconto = percentualeSconto;
	}

	public DataDwh getDtIniSconto() {
		return dtIniSconto;
	}

	public void setDtIniSconto(DataDwh dtIniSconto) {
		this.dtIniSconto = dtIniSconto;
	}

	public DataDwh getDtFinSconto() {
		return dtFinSconto;
	}

	public void setDtFinSconto(DataDwh dtFinSconto) {
		this.dtFinSconto = dtFinSconto;
	}
	
	@Override
	public String getValueForCtrHash()
	{
		return this.codUnivocoCanone +
		(String)this.idExtContrib.getRelazione().getValore() +
		this.tipoDocumento +
		this.numeroDocumento +
		this.annoDocumento +
		this.statoDocumento +
		this.numeroProtocollo +
		this.annoContabileDocumento +
		this.dtProtocollo.getDataFormattata() +
		this.dtIniValidita.getDataFormattata() +
		this.dtFinValidita.getDataFormattata() +
		this.dtRichiesta.getDataFormattata() +
		this.tipoOccupazione +
		this.codiceImmobile +
		this.codiceVia +
		this.sedime +
		this.indirizzo +
		this.civico +
		this.zona +
		this.foglio +
		this.particella +
		this.subalterno +
		this.quantita +		
		this.unitaMisuraQuantita +
		this.tariffaPerUnita +
		this.importoCanone +
		this.dtIniValiditaTariffa.getDataFormattata() +
		this.dtFinValiditaTariffa.getDataFormattata() +		
		this.descrizione +
		this.note +
		this.codiceEsenzione +
		this.scontoAssoluto +
		this.percentualeSconto +
		this.dtIniSconto.getDataFormattata() +
		this.dtFinSconto.getDataFormattata() +
		this.getProvenienza();
	}
	
}
