package it.webred.ct.data.model.compravendite;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the MUI_FABBRICATI_IDENTIFICA database table.
 * 
 */
@Entity
@Table(name="MUI_FABBRICATI_IDENTIFICA")
public class MuiFabbricatiIdentifica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long iid;

	private String anno;

	private String denominatore;

	private String edificabilita;

	private String foglio;

	@Column(name="ID_CATASTALE_IMMOBILE")
	private String idCatastaleImmobile;

	@Column(name="ID_IMMOBILE")
	private String idImmobile;

	@Column(name="ID_NOTA")
	private String idNota;

	@Column(name="IID_FABBRICATIINFO")
	private BigDecimal iidFabbricatiinfo;

	@Column(name="IID_FORNITURA")
	private BigDecimal iidFornitura;

	@Column(name="IID_NOTA")
	private BigDecimal iidNota;

	private String numero;

	@Column(name="NUMERO_PROTOCOLLO")
	private String numeroProtocollo;

	@Column(name="SEZIONE_CENSUARIA")
	private String sezioneCensuaria;

	@Column(name="SEZIONE_URBANA")
	private String sezioneUrbana;

	private String subalterno;

	@Column(name="TIPO_DENUNCIA")
	private String tipoDenuncia;

    public MuiFabbricatiIdentifica() {
    }

	public long getIid() {
		return this.iid;
	}

	public void setIid(long iid) {
		this.iid = iid;
	}

	public String getAnno() {
		return this.anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
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

	public BigDecimal getIidFabbricatiinfo() {
		return this.iidFabbricatiinfo;
	}

	public void setIidFabbricatiinfo(BigDecimal iidFabbricatiinfo) {
		this.iidFabbricatiinfo = iidFabbricatiinfo;
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

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNumeroProtocollo() {
		return this.numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public String getSezioneCensuaria() {
		return this.sezioneCensuaria;
	}

	public void setSezioneCensuaria(String sezioneCensuaria) {
		this.sezioneCensuaria = sezioneCensuaria;
	}

	public String getSezioneUrbana() {
		return this.sezioneUrbana;
	}

	public void setSezioneUrbana(String sezioneUrbana) {
		this.sezioneUrbana = sezioneUrbana;
	}

	public String getSubalterno() {
		return this.subalterno;
	}

	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}

	public String getTipoDenuncia() {
		return this.tipoDenuncia;
	}

	public void setTipoDenuncia(String tipoDenuncia) {
		this.tipoDenuncia = tipoDenuncia;
	}

}