package it.webred.ct.data.model.successioni;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class SuccessioniPK implements Serializable {

	private static final long serialVersionUID = -5243684375268789661L;
	
	private String ufficio = "";
	private String anno = "";
	private BigDecimal volume = null;
	private BigDecimal numero = null;
	private BigDecimal sottonumero = null;
	private String comune = "";
	private BigDecimal progressivo = null;
	private BigDecimal fornitura  = null;
	
	public SuccessioniPK() {
	}//-------------------------------------------------------------------------

	public boolean equals(Object o) {

		boolean uguale = false;
		
		if (! (o instanceof SuccessioniPK) )
			return uguale;

		SuccessioniPK pk = (SuccessioniPK) o;
		
		if ( this.ufficio.equals(pk.getUfficio()) && this.anno.equals(pk.getAnno()) && this.comune.equals(pk.getComune()) && 
				this.volume.compareTo(pk.getVolume()) == 0 && this.numero.compareTo(pk.getNumero()) == 0 && this.sottonumero.compareTo(pk.getSottonumero()) == 0 && this.fornitura.compareTo(pk.getFornitura()) == 0 ){
			uguale = true;
		}
		
		return uguale;
	}//-------------------------------------------------------------------------
	
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ufficio.hashCode();
		hash = hash * prime + this.anno.hashCode();
		hash = hash * prime + this.volume.hashCode();
		hash = hash * prime + this.numero.hashCode();
		hash = hash * prime + this.sottonumero.hashCode();
		hash = hash * prime + this.comune.hashCode();
		hash = hash * prime + this.progressivo.hashCode();
		hash = hash * prime + this.fornitura.hashCode();
		
		return hash;
    }//-------------------------------------------------------------------------


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






	public BigDecimal getVolume() {
		return volume;
	}






	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}






	public BigDecimal getNumero() {
		return numero;
	}






	public void setNumero(BigDecimal numero) {
		this.numero = numero;
	}






	public BigDecimal getSottonumero() {
		return sottonumero;
	}






	public void setSottonumero(BigDecimal sottonumero) {
		this.sottonumero = sottonumero;
	}






	public String getComune() {
		return comune;
	}


	public void setComune(String comune) {
		this.comune = comune;
	}


	public BigDecimal getProgressivo() {
		return progressivo;
	}


	public void setProgressivo(BigDecimal progressivo) {
		this.progressivo = progressivo;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BigDecimal getFornitura() {
		return fornitura;
	}

	public void setFornitura(BigDecimal fornitura) {
		this.fornitura = fornitura;
	}
	
	
	
	
}


