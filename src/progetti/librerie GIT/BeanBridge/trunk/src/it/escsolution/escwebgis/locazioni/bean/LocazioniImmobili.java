/*
 * Created on 3-mag-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.escsolution.escwebgis.locazioni.bean;

import it.escsolution.escwebgis.common.EscObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LocazioniImmobili extends EscObject implements Serializable{

	private String chiave;
	private String ufficio;
	private Integer anno;
	private String serie;
	private Integer numero;
	private Integer sottonumero;
	private Integer progNegozio;
	private Integer progImmobile;
	
	private String accatastamento;
	private String tipoCatasto;
	private String interoPorzione;
	private	String codiceCat;
	private String comune;
	private String sezUrbana;
	private String foglio;
	private String particella;
	private String subalterno;
	private String indirizzo;
	
	public String getChiave() {
		return chiave;
	}
	public void setChiave(String chiave) {
		this.chiave = chiave;
	}
	public String getUfficio() {
		return ufficio;
	}
	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}
	public Integer getAnno() {
		return anno;
	}
	public void setAnno(Integer anno) {
		this.anno = anno;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public Integer getSottonumero() {
		return sottonumero;
	}
	public void setSottonumero(Integer sottonumero) {
		this.sottonumero = sottonumero;
	}
	public Integer getProgNegozio() {
		return progNegozio;
	}
	public void setProgNegozio(Integer progNegozio) {
		this.progNegozio = progNegozio;
	}
	public Integer getProgImmobile() {
		return progImmobile;
	}
	public void setProgImmobile(Integer progImmobile) {
		this.progImmobile = progImmobile;
	}
	public String getAccatastamento() {
		if("S".equals(this.accatastamento))
			return "SI";
		else 
			return "NO";
	}
	public void setAccatastamento(String accatastamento) {
		this.accatastamento = accatastamento;
	}
	public String getTipoCatasto() {
		if("I".equals(this.tipoCatasto))
			return "TERRENI";
		else if("U".equals(this.tipoCatasto))
			return "URBANO";
		else 
			return "-";
	}
	public void setTipoCatasto(String tipoCatasto) {
		this.tipoCatasto = tipoCatasto;
	}
	public String getInteroPorzione() {
		if("I".equals(this.interoPorzione))
			return "INTERO";
		else if("P".equals(this.interoPorzione))
			return "PORZIONE";
		else 
			return "-";
	}
	public void setInteroPorzione(String interoPorzione) {
		this.interoPorzione = interoPorzione;
	}
	public String getCodiceCat() {
		return codiceCat;
	}
	public void setCodiceCat(String codiceCat) {
		this.codiceCat = codiceCat;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	public String getSezUrbana() {
		return sezUrbana; 
	}
	public void setSezUrbana(String sezUrbana) {
		this.sezUrbana = sezUrbana;
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
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
}
