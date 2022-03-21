package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The primary key class for the CS_REL_SETT_CATSOC_ESCLUSIVA database table.
 * 
 */
@Embeddable
public class CsRelSettCatsocEsclusivaPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="TIPO_DIARIO_ID")
	private Long tipoDiarioId;
	
	@Column(name="SCS_CATEGORIA_SOCIALE_ID")
	private Long categoriaSocialeId;

	@Column(name="SCS_SETTORE_ID")
	private Long settoreId;
	
	public Long getTipoDiarioId() {
		return tipoDiarioId;
	}

	public void setTipoDiarioId(Long tipoDiarioId) {
		this.tipoDiarioId = tipoDiarioId;
	}

	public Long getCategoriaSocialeId() {
		return categoriaSocialeId;
	}

	public void setCategoriaSocialeId(Long categoriaSocialeId) {
		this.categoriaSocialeId = categoriaSocialeId;
	}

	public Long getSettoreId() {
		return settoreId;
	}

	public void setSettoreId(Long settoreId) {
		this.settoreId = settoreId;
	}
	
	public String getStringId() {
		return categoriaSocialeId + "-" + settoreId + "-" + tipoDiarioId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CsRelSettCatsocEsclusivaPK)) {
			return false;
		}
		CsRelSettCatsocEsclusivaPK castOther = (CsRelSettCatsocEsclusivaPK)other;
		return 
			(this.tipoDiarioId == castOther.tipoDiarioId)
			&& (this.categoriaSocialeId == castOther.categoriaSocialeId)
			&& (this.settoreId == castOther.settoreId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.tipoDiarioId ^ (this.tipoDiarioId >>> 32)));
		hash = hash * prime + ((int) (this.categoriaSocialeId ^ (this.categoriaSocialeId >>> 32)));
		hash = hash * prime + ((int) (this.settoreId ^ (this.settoreId >>> 32)));
		
		return hash;
	}
}