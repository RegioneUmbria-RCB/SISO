package it.webred.ct.data.model.locazioni;

import it.webred.ct.data.model.annotation.IndiceEntity;
import it.webred.ct.data.model.annotation.IndiceKey;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the LOCAZIONI_A database table.
 * 
 */
@Entity
@Table(name="LOCAZIONI_A")
@IndiceEntity(sorgente="5") 
@IdClass(LocazioneAPK.class)
public class LocazioniA implements Serializable {
	private static final long serialVersionUID = 1L;

	@IndiceKey(pos="2")
	@Id
	private BigDecimal anno;

	@Column(name="COD_CAT_COMUNE")
	private String codCatComune;

	@Column(name="CODICE_NEGOZIO")
	private String codiceNegozio;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_FINE")
	private Date dataFine;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_INIZIO")
	private Date dataInizio;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_REGISTRAZIONE")
	private Date dataRegistrazione;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_STIPULA")
	private Date dataStipula;

	private BigDecimal fornitura;

	@Column(name="IMPORTO_CANONE")
	private String importoCanone;

	private String indirizzo;

	@IndiceKey(pos="4")
	@Id
	private BigDecimal numero;

	@Column(name="OGGETTO_LOCAZIONE")
	private String oggettoLocazione;

	@Column(name="PROG_NEGOZIO")
	private BigDecimal progNegozio;

	@IndiceKey(pos="3")
	@Id
	private String serie;

	private BigDecimal sottonumero;

	@Column(name="TIPO_CANONE")
	private String tipoCanone;

	@Column(name="TIPO_RECORD")
	private String tipoRecord;

	@IndiceKey(pos="1")
	@Id
	private String ufficio;

	private String valuta;

    public LocazioniA() {
    }

	public BigDecimal getAnno() {
		return this.anno;
	}

	public void setAnno(BigDecimal anno) {
		this.anno = anno;
	}

	public String getCodCatComune() {
		return this.codCatComune;
	}

	public void setCodCatComune(String codCatComune) {
		this.codCatComune = codCatComune;
	}

	public String getCodiceNegozio() {
		return this.codiceNegozio;
	}

	public void setCodiceNegozio(String codiceNegozio) {
		this.codiceNegozio = codiceNegozio;
	}

	public Date getDataFine() {
		return this.dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	public Date getDataInizio() {
		return this.dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataRegistrazione() {
		return this.dataRegistrazione;
	}

	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public Date getDataStipula() {
		return this.dataStipula;
	}

	public void setDataStipula(Date dataStipula) {
		this.dataStipula = dataStipula;
	}

	public BigDecimal getFornitura() {
		return this.fornitura;
	}

	public void setFornitura(BigDecimal fornitura) {
		this.fornitura = fornitura;
	}

	public String getImportoCanone() {
		return this.importoCanone;
	}

	public void setImportoCanone(String importoCanone) {
		this.importoCanone = importoCanone;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public BigDecimal getNumero() {
		return this.numero;
	}

	public void setNumero(BigDecimal numero) {
		this.numero = numero;
	}

	public String getOggettoLocazione() {
		return this.oggettoLocazione;
	}

	public void setOggettoLocazione(String oggettoLocazione) {
		this.oggettoLocazione = oggettoLocazione;
	}

	public BigDecimal getProgNegozio() {
		return this.progNegozio;
	}

	public void setProgNegozio(BigDecimal progNegozio) {
		this.progNegozio = progNegozio;
	}

	public String getSerie() {
		return this.serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public BigDecimal getSottonumero() {
		return this.sottonumero;
	}

	public void setSottonumero(BigDecimal sottonumero) {
		this.sottonumero = sottonumero;
	}

	public String getTipoCanone() {
		return this.tipoCanone;
	}

	public void setTipoCanone(String tipoCanone) {
		this.tipoCanone = tipoCanone;
	}

	public String getTipoRecord() {
		return this.tipoRecord;
	}

	public void setTipoRecord(String tipoRecord) {
		this.tipoRecord = tipoRecord;
	}

	public String getUfficio() {
		return this.ufficio;
	}

	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}

	public String getValuta() {
		return this.valuta;
	}

	public void setValuta(String valuta) {
		this.valuta = valuta;
	}

}