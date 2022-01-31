package it.escsolution.escwebgis.acqua.bean;

import it.escsolution.escwebgis.common.EscObject;
import it.escsolution.escwebgis.cosapNew.bean.CosapTassa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class AcquaCatasto extends EscObject implements Serializable {


	private static final long serialVersionUID = 1L;
	String id;
	String codServizio;
	String assenzaDatiCat;
	String sezione;
	String foglio;
	String particella;
	String subalterno;
	String estensionePart;
	String tipologiaPart;
	
	private String latitudine;
	private String longitudine;
	private String codEnte;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCodServizio() {
		return codServizio;
	}
	public void setCodServizio(String codServizio) {
		this.codServizio = codServizio;
	}
	public String getAssenzaDatiCat() {
		return assenzaDatiCat;
	}
	public void setAssenzaDatiCat(String assenzaDatiCat) {
		this.assenzaDatiCat = assenzaDatiCat;
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
	public String getEstensionePart() {
		return estensionePart;
	}
	public void setEstensionePart(String estensionePart) {
		this.estensionePart = estensionePart;
	}
	public String getTipologiaPart() {
		return tipologiaPart;
	}
	public void setTipologiaPart(String tipologiaPart) {
		this.tipologiaPart = tipologiaPart;
	}
	public String getLatitudine() {
		return latitudine;
	}
	public void setLatitudine(String latitudine) {
		this.latitudine = latitudine;
	}
	public String getLongitudine() {
		return longitudine;
	}
	public void setLongitudine(String longitudine) {
		this.longitudine = longitudine;
	}
	public String getCodEnte() {
		return codEnte;
	}
	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}

}
