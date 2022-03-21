package it.webred.ct.data.model.locazioni;

import it.webred.ct.data.model.annotation.IndiceEntity;
import it.webred.ct.data.model.annotation.IndiceKey;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the LOCAZIONI_B database table.
 * 
 */
@Entity
@Table(name="LOCAZIONI_B")
//@IndiceEntity(sorgente="13")-->>??????????????????????????????????????
@IndiceEntity(sorgente="5")
@IdClass(LocazioneBPK.class)
public class LocazioniB implements Serializable {
	private static final long serialVersionUID = 1L;

	@IndiceKey(pos="2")
	@Id
	private BigDecimal anno;

	@Column(name="CITTA_NASCITA")
	private String cittaNascita;

	@Column(name="CITTA_RESIDENZA")
	private String cittaResidenza;

	@Column(name="CIVICO_RESIDENZA")
	private String civicoResidenza;

	private String codicefiscale;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_CESSIONE")
	private Date dataCessione;

	@Column(name="DATA_NASCITA")
	private String dataNascita;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_SUBENTRO")
	private Date dataSubentro;

	private BigDecimal fornitura;

	@Column(name="INDIRIZZO_RESIDENZA")
	private String indirizzoResidenza;

	@IndiceKey(pos="4")
	@Id
	private BigDecimal numero;

	@Column(name="PROG_NEGOZIO")
	private BigDecimal progNegozio;

	@Column(name="PROG_SOGGETTO")
	@IndiceKey(pos="5")
	@Id
	private BigDecimal progSoggetto;

	@Column(name="PROV_NASCITA")
	private String provNascita;

	@Column(name="PROV_RESIDENZA")
	private String provResidenza;

	@IndiceKey(pos="3")
	@Id
	private String serie;

	private String sesso;

	private BigDecimal sottonumero;

	@Column(name="TIPO_RECORD")
	private String tipoRecord;

	@IndiceKey(pos="6")
	@Column(name="TIPO_SOGGETTO")
	private String tipoSoggetto;

	@IndiceKey(pos="1")
	@Id
	private String ufficio;

    public LocazioniB() {
    }

	public BigDecimal getAnno() {
		return this.anno;
	}

	public void setAnno(BigDecimal anno) {
		this.anno = anno;
	}

	public String getCittaNascita() {
		return this.cittaNascita;
	}

	public void setCittaNascita(String cittaNascita) {
		this.cittaNascita = cittaNascita;
	}

	public String getCittaResidenza() {
		return this.cittaResidenza;
	}

	public void setCittaResidenza(String cittaResidenza) {
		this.cittaResidenza = cittaResidenza;
	}

	public String getCivicoResidenza() {
		return this.civicoResidenza;
	}

	public void setCivicoResidenza(String civicoResidenza) {
		this.civicoResidenza = civicoResidenza;
	}

	public String getCodicefiscale() {
		return this.codicefiscale;
	}

	public void setCodicefiscale(String codicefiscale) {
		this.codicefiscale = codicefiscale;
	}

	public Date getDataCessione() {
		return this.dataCessione;
	}

	public void setDataCessione(Date dataCessione) {
		this.dataCessione = dataCessione;
	}

	public String getDataNascita() {
		return this.dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Date getDataSubentro() {
		return this.dataSubentro;
	}

	public void setDataSubentro(Date dataSubentro) {
		this.dataSubentro = dataSubentro;
	}

	public BigDecimal getFornitura() {
		return this.fornitura;
	}

	public void setFornitura(BigDecimal fornitura) {
		this.fornitura = fornitura;
	}

	public String getIndirizzoResidenza() {
		return this.indirizzoResidenza;
	}

	public void setIndirizzoResidenza(String indirizzoResidenza) {
		this.indirizzoResidenza = indirizzoResidenza;
	}

	public BigDecimal getNumero() {
		return this.numero;
	}

	public void setNumero(BigDecimal numero) {
		this.numero = numero;
	}

	public BigDecimal getProgNegozio() {
		return this.progNegozio;
	}

	public void setProgNegozio(BigDecimal progNegozio) {
		this.progNegozio = progNegozio;
	}

	public BigDecimal getProgSoggetto() {
		return this.progSoggetto;
	}

	public void setProgSoggetto(BigDecimal progSoggetto) {
		this.progSoggetto = progSoggetto;
	}

	public String getProvNascita() {
		return this.provNascita;
	}

	public void setProvNascita(String provNascita) {
		this.provNascita = provNascita;
	}

	public String getProvResidenza() {
		return this.provResidenza;
	}

	public void setProvResidenza(String provResidenza) {
		this.provResidenza = provResidenza;
	}

	public String getSerie() {
		return this.serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public BigDecimal getSottonumero() {
		return this.sottonumero;
	}

	public void setSottonumero(BigDecimal sottonumero) {
		this.sottonumero = sottonumero;
	}

	public String getTipoRecord() {
		return this.tipoRecord;
	}

	public void setTipoRecord(String tipoRecord) {
		this.tipoRecord = tipoRecord;
	}

	public String getTipoSoggetto() {
		return this.tipoSoggetto;
	}

	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}

	public String getUfficio() {
		return this.ufficio;
	}

	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}

}