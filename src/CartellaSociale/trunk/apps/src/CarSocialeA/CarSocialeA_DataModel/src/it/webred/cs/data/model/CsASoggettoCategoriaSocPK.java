package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CS_A_SOGGETTO_CATEGORIA_SOC database table.
 * 
 */
@Embeddable
public class CsASoggettoCategoriaSocPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FINE_APP")
	private java.util.Date dataFineApp;

	@Column(name="CATEGORIA_SOCIALE_ID")
	private long categoriaSocialeId;

	public CsASoggettoCategoriaSocPK() {
	}
	public java.util.Date getDataFineApp() {
		return this.dataFineApp;
	}
	public void setDataFineApp(java.util.Date dataFineApp) {
		this.dataFineApp = dataFineApp;
	}
	public long getCategoriaSocialeId() {
		return this.categoriaSocialeId;
	}
	public void setCategoriaSocialeId(long categoriaSocialeId) {
		this.categoriaSocialeId = categoriaSocialeId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CsASoggettoCategoriaSocPK)) {
			return false;
		}
		CsASoggettoCategoriaSocPK castOther = (CsASoggettoCategoriaSocPK)other;
		return 
			this.dataFineApp.equals(castOther.dataFineApp)
			&& (this.categoriaSocialeId == castOther.categoriaSocialeId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.dataFineApp.hashCode();
		hash = hash * prime + ((int) (this.categoriaSocialeId ^ (this.categoriaSocialeId >>> 32)));
		
		return hash;
	}
}