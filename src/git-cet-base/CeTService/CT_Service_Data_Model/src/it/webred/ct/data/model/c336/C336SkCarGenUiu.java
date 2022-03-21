package it.webred.ct.data.model.c336;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the S_C336_SK_CAR_GEN_UIU database table.
 * 
 */
@Entity
@Table(name="S_C336_SK_CAR_GEN_UIU")
public class C336SkCarGenUiu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_PRATICA")
	private Long idPratica;

	@Column(name="VAL_AFFACCI_FRONTE_STRADA")
	private String valAffacciFronteStrada;

	@Column(name="VAL_AFFACCIO_PRINC")
	private String valAffaccioPrinc;

	@Column(name="VAL_ALTRI_IMPIANTI")
	private String valAltriImpianti;

	@Column(name="VAL_AMPIEZZA_MEDIA_ACCESSORI")
	private Float valAmpiezzaMediaAccessori;

	@Column(name="VAL_AMPIEZZA_MEDIA_CAMERE")
	private Float valAmpiezzaMediaCamere;

	@Column(name="VAL_AMPIEZZA_MEDIA_SERVIZI")
	private Float valAmpiezzaMediaServizi;

	@Column(name="VAL_CATEG_CLASSE_UIU")
	private String valCategClasseUiu;

	@Column(name="VAL_CONDIZIONAMENTO")
	private String valCondizionamento;

	@Column(name="VAL_DELTA_RENDITA_ZONA")
	private Float valDeltaRenditaZona;

	@Column(name="VAL_INFISSI")
	private String valInfissi;

	@Column(name="VAL_PAVIMENTAZIONI")
	private String valPavimentazioni;

	@Column(name="VAL_PIANO")
	private String valPiano;

	@Column(name="VAL_RENDITA_MEDIA_UIU")
	private Float valRenditaMediaUiu;

	@Column(name="VAL_RISCALDAMENTO")
	private String valRiscaldamento;

	@Column(name="VAL_RISTRUTTURAZIONE")
	private String valRistrutturazione;

	@Column(name="VAL_SPAZI")
	private String valSpazi;

	@Column(name="VAL_STATO_INTERNO")
	private String valStatoInterno;

    public C336SkCarGenUiu() {
    }

	public Long getIdPratica() {
		return this.idPratica;
	}

	public void setIdPratica(Long idPratica) {
		this.idPratica = idPratica;
	}

	public String getValAffacciFronteStrada() {
		return this.valAffacciFronteStrada;
	}

	public void setValAffacciFronteStrada(String valAffacciFronteStrada) {
		this.valAffacciFronteStrada = valAffacciFronteStrada;
	}

	public String getValAffaccioPrinc() {
		return this.valAffaccioPrinc;
	}

	public void setValAffaccioPrinc(String valAffaccioPrinc) {
		this.valAffaccioPrinc = valAffaccioPrinc;
	}

	public String getValAltriImpianti() {
		return this.valAltriImpianti;
	}

	public void setValAltriImpianti(String valAltriImpianti) {
		this.valAltriImpianti = valAltriImpianti;
	}

	public String getValCategClasseUiu() {
		return this.valCategClasseUiu;
	}

	public void setValCategClasseUiu(String valCategClasseUiu) {
		this.valCategClasseUiu = valCategClasseUiu;
	}

	public String getValCondizionamento() {
		return this.valCondizionamento;
	}

	public void setValCondizionamento(String valCondizionamento) {
		this.valCondizionamento = valCondizionamento;
	}

	public String getValInfissi() {
		return this.valInfissi;
	}

	public void setValInfissi(String valInfissi) {
		this.valInfissi = valInfissi;
	}

	public String getValPavimentazioni() {
		return this.valPavimentazioni;
	}

	public void setValPavimentazioni(String valPavimentazioni) {
		this.valPavimentazioni = valPavimentazioni;
	}

	public String getValPiano() {
		return this.valPiano;
	}

	public void setValPiano(String valPiano) {
		this.valPiano = valPiano;
	}

	public String getValRiscaldamento() {
		return this.valRiscaldamento;
	}

	public void setValRiscaldamento(String valRiscaldamento) {
		this.valRiscaldamento = valRiscaldamento;
	}

	public String getValRistrutturazione() {
		return this.valRistrutturazione;
	}

	public void setValRistrutturazione(String valRistrutturazione) {
		this.valRistrutturazione = valRistrutturazione;
	}

	public String getValSpazi() {
		return this.valSpazi;
	}

	public void setValSpazi(String valSpazi) {
		this.valSpazi = valSpazi;
	}

	public String getValStatoInterno() {
		return this.valStatoInterno;
	}

	public void setValStatoInterno(String valStatoInterno) {
		this.valStatoInterno = valStatoInterno;
	}

	public Float getValAmpiezzaMediaAccessori() {
		return valAmpiezzaMediaAccessori;
	}

	public void setValAmpiezzaMediaAccessori(Float valAmpiezzaMediaAccessori) {
		this.valAmpiezzaMediaAccessori = valAmpiezzaMediaAccessori;
	}

	public Float getValAmpiezzaMediaCamere() {
		return valAmpiezzaMediaCamere;
	}

	public void setValAmpiezzaMediaCamere(Float valAmpiezzaMediaCamere) {
		this.valAmpiezzaMediaCamere = valAmpiezzaMediaCamere;
	}

	public Float getValAmpiezzaMediaServizi() {
		return valAmpiezzaMediaServizi;
	}

	public void setValAmpiezzaMediaServizi(Float valAmpiezzaMediaServizi) {
		this.valAmpiezzaMediaServizi = valAmpiezzaMediaServizi;
	}

	public Float getValDeltaRenditaZona() {
		return valDeltaRenditaZona;
	}

	public void setValDeltaRenditaZona(Float valDeltaRenditaZona) {
		this.valDeltaRenditaZona = valDeltaRenditaZona;
	}

	public Float getValRenditaMediaUiu() {
		return valRenditaMediaUiu;
	}

	public void setValRenditaMediaUiu(Float valRenditaMediaUiu) {
		this.valRenditaMediaUiu = valRenditaMediaUiu;
	}

}