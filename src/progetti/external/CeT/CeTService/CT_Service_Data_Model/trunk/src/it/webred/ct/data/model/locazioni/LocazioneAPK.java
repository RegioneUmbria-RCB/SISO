package it.webred.ct.data.model.locazioni;

import java.io.Serializable;
import java.math.BigDecimal;

public class LocazioneAPK implements Serializable {

	private String ufficio;

	private String serie;

	private BigDecimal numero;

	private BigDecimal anno;

	public String getUfficio() {
		return ufficio;
	}

	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public BigDecimal getNumero() {
		return numero;
	}

	public void setNumero(BigDecimal numero) {
		this.numero = numero;
	}

	public BigDecimal getAnno() {
		return anno;
	}

	public void setAnno(BigDecimal anno) {
		this.anno = anno;
	}

	
	public boolean equals(Object o) {
		if (! (o instanceof LocazioneAPK) )
			return false;
		
		LocazioneAPK apk = (LocazioneAPK) o;
		
		
		return (this.anno.equals(apk.getAnno()) && this.numero.equals(apk.getNumero()) &&
				this.serie.equals(apk.getSerie()) && this.ufficio.equals(apk.getUfficio()));
				
	}
	
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.anno.hashCode() ^ (this.anno.hashCode() >>> 32)));
		hash = hash * prime + this.ufficio.hashCode();
		hash = hash * prime + ((int) (this.numero.hashCode() ^ (this.numero.hashCode() >>> 32)));
		hash = hash * prime + this.serie.hashCode();
		
		return hash;
    }
	
	
}
