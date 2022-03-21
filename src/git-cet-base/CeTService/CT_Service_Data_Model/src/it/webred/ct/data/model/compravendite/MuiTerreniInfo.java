package it.webred.ct.data.model.compravendite;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the MUI_TERRENI_INFO database table.
 * 
 */
@Entity
@Table(name="MUI_TERRENI_INFO")
public class MuiTerreniInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long iid;

	private String are;

	private String centiare;

	private String classe;

	@Column(name="CODICE_ESITO")
	private String codiceEsito;

	private String denominatore;

	private String edificabilita;

	private String ettari;

	@Column(name="FLAG_REDDITO")
	private String flagReddito;

	private String foglio;

	@Column(name="ID_CATASTALE_IMMOBILE")
	private String idCatastaleImmobile;

	@Column(name="ID_IMMOBILE")
	private String idImmobile;

	@Column(name="ID_NOTA")
	private String idNota;

	@Column(name="IID_FORNITURA")
	private BigDecimal iidFornitura;

	@Column(name="IID_NOTA")
	private BigDecimal iidNota;

	private String natura;

	private String numero;

	private String partita;

	private String qualita;

	@Column(name="REDDITO_AGRARIO_EURO")
	private String redditoAgrarioEuro;

	@Column(name="REDDITO_DOMINICALE_EURO")
	private String redditoDominicaleEuro;

	@Column(name="SEZIONE_CENSUARUIA")
	private String sezioneCensuaruia;

	private String subalterno;

	@Column(name="TIPOLOGIA_IMMOBILE")
	private String tipologiaImmobile;

    public MuiTerreniInfo() {
    }

	public long getIid() {
		return this.iid;
	}

	public void setIid(long iid) {
		this.iid = iid;
	}

	public String getAre() {
		return this.are;
	}

	public void setAre(String are) {
		this.are = are;
	}

	public String getCentiare() {
		return this.centiare;
	}

	public void setCentiare(String centiare) {
		this.centiare = centiare;
	}

	public String getClasse() {
		return this.classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getCodiceEsito() {
		return this.codiceEsito;
	}

	public void setCodiceEsito(String codiceEsito) {
		this.codiceEsito = codiceEsito;
	}

	public String getDenominatore() {
		return this.denominatore;
	}

	public void setDenominatore(String denominatore) {
		this.denominatore = denominatore;
	}

	public String getEdificabilita() {
		return this.edificabilita;
	}

	public void setEdificabilita(String edificabilita) {
		this.edificabilita = edificabilita;
	}

	public String getEttari() {
		return this.ettari;
	}

	public void setEttari(String ettari) {
		this.ettari = ettari;
	}

	public String getFlagReddito() {
		return this.flagReddito;
	}

	public void setFlagReddito(String flagReddito) {
		this.flagReddito = flagReddito;
	}

	public String getFoglio() {
		return this.foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getIdCatastaleImmobile() {
		return this.idCatastaleImmobile;
	}

	public void setIdCatastaleImmobile(String idCatastaleImmobile) {
		this.idCatastaleImmobile = idCatastaleImmobile;
	}

	public String getIdImmobile() {
		return this.idImmobile;
	}

	public void setIdImmobile(String idImmobile) {
		this.idImmobile = idImmobile;
	}

	public String getIdNota() {
		return this.idNota;
	}

	public void setIdNota(String idNota) {
		this.idNota = idNota;
	}

	public BigDecimal getIidFornitura() {
		return this.iidFornitura;
	}

	public void setIidFornitura(BigDecimal iidFornitura) {
		this.iidFornitura = iidFornitura;
	}

	public BigDecimal getIidNota() {
		return this.iidNota;
	}

	public void setIidNota(BigDecimal iidNota) {
		this.iidNota = iidNota;
	}

	public String getNatura() {
		return this.natura;
	}

	public void setNatura(String natura) {
		this.natura = natura;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPartita() {
		return this.partita;
	}

	public void setPartita(String partita) {
		this.partita = partita;
	}

	public String getQualita() {
		return this.qualita;
	}

	public void setQualita(String qualita) {
		this.qualita = qualita;
	}

	public String getRedditoAgrarioEuro() {
		return this.redditoAgrarioEuro;
	}

	public void setRedditoAgrarioEuro(String redditoAgrarioEuro) {
		this.redditoAgrarioEuro = redditoAgrarioEuro;
	}

	public String getRedditoDominicaleEuro() {
		return this.redditoDominicaleEuro;
	}

	public void setRedditoDominicaleEuro(String redditoDominicaleEuro) {
		this.redditoDominicaleEuro = redditoDominicaleEuro;
	}

	public String getSezioneCensuaruia() {
		return this.sezioneCensuaruia;
	}

	public void setSezioneCensuaruia(String sezioneCensuaruia) {
		this.sezioneCensuaruia = sezioneCensuaruia;
	}

	public String getSubalterno() {
		return this.subalterno;
	}

	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}

	public String getTipologiaImmobile() {
		return this.tipologiaImmobile;
	}

	public void setTipologiaImmobile(String tipologiaImmobile) {
		this.tipologiaImmobile = tipologiaImmobile;
	}

}