package it.webred.ct.data.model.locazioni;

import it.webred.ct.data.model.annotation.IndiceEntity;
import it.webred.ct.data.model.annotation.IndiceKey;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the LOCAZIONI_I database table.
 * 
 */
@Entity
@Table(name="LOCAZIONI_I")
@IndiceEntity(sorgente="5") 
@IdClass(LocazioneIPK.class)
public class LocazioniI implements Serializable {
	private static final long serialVersionUID = 1L;

	private String accatastamento;

	@IndiceKey(pos="2")
	@Id
	private BigDecimal anno;

	@Column(name="CODICE_CAT")
	private String codiceCat;

	@Column(name="FLG_INTERO_PORZ")
	private String flgInteroPorz;

	private String foglio;

	private BigDecimal fornitura;

	private String indirizzo;

	@IndiceKey(pos="4")
	@Id
	private BigDecimal numero;

	private String particella;

	@Column(name="PROG_IMMOBILE")
	@IndiceKey(pos="5")
	@Id
	private BigDecimal progImmobile;

	@Column(name="PROG_NEGOZIO")
	private BigDecimal progNegozio;

	@IndiceKey(pos="3")
	@Id
	private String serie;

	@Column(name="SEZ_URBANA")
	private String sezUrbana;

	private BigDecimal sottonumero;

	private String subalterno;

	@Column(name="TIPO_CATASTO")
	private String tipoCatasto;

	@Column(name="TIPO_RECORD")
	private String tipoRecord;

	@IndiceKey(pos="1")
	@Id
	private String ufficio;

    public LocazioniI() {
    }

	public String getAccatastamento() {
		return this.accatastamento;
	}

	public void setAccatastamento(String accatastamento) {
		this.accatastamento = accatastamento;
	}

	public BigDecimal getAnno() {
		return this.anno;
	}

	public void setAnno(BigDecimal anno) {
		this.anno = anno;
	}

	public String getCodiceCat() {
		return this.codiceCat;
	}

	public void setCodiceCat(String codiceCat) {
		this.codiceCat = codiceCat;
	}

	public String getFlgInteroPorz() {
		return this.flgInteroPorz;
	}

	public void setFlgInteroPorz(String flgInteroPorz) {
		this.flgInteroPorz = flgInteroPorz;
	}

	public String getFoglio() {
		return this.foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public BigDecimal getFornitura() {
		return this.fornitura;
	}

	public void setFornitura(BigDecimal fornitura) {
		this.fornitura = fornitura;
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

	public String getParticella() {
		return this.particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public BigDecimal getProgImmobile() {
		return this.progImmobile;
	}

	public void setProgImmobile(BigDecimal progImmobile) {
		this.progImmobile = progImmobile;
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

	public String getSezUrbana() {
		return this.sezUrbana;
	}

	public void setSezUrbana(String sezUrbana) {
		this.sezUrbana = sezUrbana;
	}

	public BigDecimal getSottonumero() {
		return this.sottonumero;
	}

	public void setSottonumero(BigDecimal sottonumero) {
		this.sottonumero = sottonumero;
	}

	public String getSubalterno() {
		return this.subalterno;
	}

	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}

	public String getTipoCatasto() {
		return this.tipoCatasto;
	}

	public void setTipoCatasto(String tipoCatasto) {
		this.tipoCatasto = tipoCatasto;
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

}