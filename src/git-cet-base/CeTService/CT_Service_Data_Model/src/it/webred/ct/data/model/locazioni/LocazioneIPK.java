package it.webred.ct.data.model.locazioni;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;

public class LocazioneIPK implements Serializable {

	private String ufficio;

	private String serie;

	private BigDecimal numero;

	private BigDecimal anno;
	
	@Column(name="PROG_IMMOBILE")
	private BigDecimal progImmobile;

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
	
	public BigDecimal getProgImmobile() {
		return progImmobile;
	}

	public void setProgImmobile(BigDecimal progImmobile) {
		this.progImmobile = progImmobile;
	}

	public boolean equals(Object o) {
		if (! (o instanceof LocazioneIPK) )
			return false;
		
		LocazioneIPK apk = (LocazioneIPK) o;
		
		
		return (this.anno.equals(apk.getAnno()) && this.numero.equals(apk.getNumero()) &&
				this.serie.equals(apk.getSerie()) && this.ufficio.equals(apk.getUfficio()) &&
				this.progImmobile.equals(apk.getProgImmobile()));
				
	}
	
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.anno.hashCode() ^ (this.anno.hashCode() >>> 32)));
		hash = hash * prime + this.ufficio.hashCode();
		hash = hash * prime + ((int) (this.numero.hashCode() ^ (this.numero.hashCode() >>> 32)));
		hash = hash * prime + this.serie.hashCode();
		hash = hash * prime + ((int) (this.progImmobile.hashCode() ^ (this.progImmobile.hashCode() >>> 32)));
		
		return hash;
    }
	
	
}
