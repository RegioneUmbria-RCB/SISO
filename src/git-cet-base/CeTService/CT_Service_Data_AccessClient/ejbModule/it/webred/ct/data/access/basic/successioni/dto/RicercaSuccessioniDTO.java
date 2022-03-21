package it.webred.ct.data.access.basic.successioni.dto;

import it.webred.ct.data.model.successioni.SuccessioniPK;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RicercaSuccessioniDTO extends CeTBaseObject implements Serializable{
	
	private static final long serialVersionUID = 8688889105108219073L;
	
	private String ufficio = "";
	private String anno = "";
	private String volume = null;
	private String numero = null;
	private String sottonumero = null;
	private String comune = "";
	private String progressivo = null;
	private String fornitura  = null;
	
	private String codFis;
	private String tipoCoinvolgimento = ""; //E= Erede; D= Defunto
	
	private String sezione = "";
	private String foglio = "";
	private String particella = "";
	private String subalterno = "";
	
	private Integer limit = 0;

	public String getCodFis() {
		return codFis;
	}

	public void setCodFis(String codFis) {
		this.codFis = codFis;
	}

	public String getTipoCoinvolgimento() {
		return tipoCoinvolgimento;
	}

	public void setTipoCoinvolgimento(String tipoCoinvolgimento) {
		this.tipoCoinvolgimento = tipoCoinvolgimento;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getParticella() {
		return particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getSubalterno() {
		return subalterno;
	}

	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUfficio() {
		return ufficio;
	}

	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getSottonumero() {
		return sottonumero;
	}

	public void setSottonumero(String sottonumero) {
		this.sottonumero = sottonumero;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getProgressivo() {
		return progressivo;
	}

	public void setProgressivo(String progressivo) {
		this.progressivo = progressivo;
	}

	public String getFornitura() {
		return fornitura;
	}

	public void setFornitura(String fornitura) {
		this.fornitura = fornitura;
	}
	


}

