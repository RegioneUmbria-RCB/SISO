package it.webred.ct.data.model.anagrafe;

import it.webred.ct.data.model.annotation.IndiceKey;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the V_STORICO_ANA_COORD_CAT  database view.
 * 
 */
@Entity
@Table(name="V_STORICO_ANA_COORD_CAT")
public class SitDPersCoordCatV implements Serializable {
	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@IndiceKey(pos="1")
	private String id;
	
	@Column(name="ID_EXT")
	private String idExt;
	

	@Column(name="ID_ORIG")
	private String idOrig;
	
	private String cognome;
	private String nome;
	private String sesso;
	
	@Temporal( TemporalType.DATE)
	@Column(name="DATA_NASCITA")
	private Date dataNascita;
	
	@Column(name="COMUNE_NASCITA")
	private String comuneNascita;
	
	@Column(name="PROV_NASCITA")
	private String provNascita;
	
	private String foglio;
	private String mappale;
	private String subalterno;
	
	@Column(name="TIPO_INDIRIZZO_PERS")
	private String tipoIndirizzoPers;
	
	@Column(name="INDIRIZZO_PERS")
	private String indirizzoPers;
	
	@Column(name="CIVICO_PERS")
	private String civicoPers;
	
	@Column(name="INDIRIZZO_CAT")
	private String indirizzoCat;
	
	@Column(name="CIVICO_CAT")
	private String civicoCat;
	
	@Column(name="BARRATO_CAT")
	private String barratoCat;
	
	@Temporal( TemporalType.DATE)
	@Column(name="DT_INIZIO_VAL_PERS")
	private Date dtInizioValPers;
	
	@Temporal( TemporalType.DATE)
	@Column(name="DT_FINE_VAL_PERS")
	private Date dtFineValPers;

	@Temporal( TemporalType.DATE)
	@Column(name="DT_INIZIO_DATO_PERS")
	private Date dtInizioDatoPers;
	
	@Temporal( TemporalType.DATE)
	@Column(name="DT_FINE_DATO_PERS")
	private Date dtFineDatoPers;
	
	@Temporal( TemporalType.DATE)
	@Column(name="DT_INIZIO_DATO_CAT")
	private Date dtInizioDatoCat;
	
	@Temporal( TemporalType.DATE)
	@Column(name="DT_FINE_DATO_CAT")
	private Date dtFineDatoCat;
	
	@Temporal( TemporalType.DATE)
	@Column(name="DT_INIZIO_FILE_CAT")
	private Date dtInizioFileCat;
	
	@Temporal( TemporalType.DATE)
	@Column(name="DT_FINE_FILE_CAT")
	private Date dtFineFileCat;

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

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public String getProvNascita() {
		return provNascita;
	}

	public void setProvNascita(String provNascita) {
		this.provNascita = provNascita;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getMappale() {
		return mappale;
	}

	public void setMappale(String mappale) {
		this.mappale = mappale;
	}

	public String getSubalterno() {
		return subalterno;
	}

	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}

	public String getTipoIndirizzoPers() {
		return tipoIndirizzoPers;
	}

	public void setTipoIndirizzoPers(String tipoIndirizzoPers) {
		this.tipoIndirizzoPers = tipoIndirizzoPers;
	}

	public String getIndirizzoPers() {
		return indirizzoPers;
	}

	public void setIndirizzoPers(String indirizzoPers) {
		this.indirizzoPers = indirizzoPers;
	}

	public String getCivicoPers() {
		return civicoPers;
	}

	public void setCivicoPers(String civicoPers) {
		this.civicoPers = civicoPers;
	}

	public String getIndirizzoCat() {
		return indirizzoCat;
	}

	public void setIndirizzoCat(String indirizzoCat) {
		this.indirizzoCat = indirizzoCat;
	}

	public String getCivicoCat() {
		return civicoCat;
	}

	public void setCivicoCat(String civicoCat) {
		this.civicoCat = civicoCat;
	}

	public String getBarratoCat() {
		return barratoCat;
	}

	public void setBarratoCat(String barratoCat) {
		this.barratoCat = barratoCat;
	}

	public Date getDtInizioValPers() {
		return dtInizioValPers;
	}

	public void setDtInizioValPers(Date dtInizioValPers) {
		this.dtInizioValPers = dtInizioValPers;
	}

	public Date getDtFineValPers() {
		return dtFineValPers;
	}

	public void setDtFineValPers(Date dtFineValPers) {
		this.dtFineValPers = dtFineValPers;
	}

	public Date getDtInizioDatoPers() {
		return dtInizioDatoPers;
	}

	public void setDtInizioDatoPers(Date dtInizioDatoPers) {
		this.dtInizioDatoPers = dtInizioDatoPers;
	}

	public Date getDtFineDatoPers() {
		return dtFineDatoPers;
	}

	public void setDtFineDatoPers(Date dtFineDatoPers) {
		this.dtFineDatoPers = dtFineDatoPers;
	}

	public Date getDtInizioDatoCat() {
		return dtInizioDatoCat;
	}

	public void setDtInizioDatoCat(Date dtInizioDatoCat) {
		this.dtInizioDatoCat = dtInizioDatoCat;
	}

	public Date getDtFineDatoCat() {
		return dtFineDatoCat;
	}

	public void setDtFineDatoCat(Date dtFineDatoCat) {
		this.dtFineDatoCat = dtFineDatoCat;
	}

	public Date getDtInizioFileCat() {
		return dtInizioFileCat;
	}

	public void setDtInizioFileCat(Date dtInizioFileCat) {
		this.dtInizioFileCat = dtInizioFileCat;
	}

	public Date getDtFineFileCat() {
		return dtFineFileCat;
	}

	public void setDtFineFileCat(Date dtFineFileCat) {
		this.dtFineFileCat = dtFineFileCat;
	}

	
	
	
	
}