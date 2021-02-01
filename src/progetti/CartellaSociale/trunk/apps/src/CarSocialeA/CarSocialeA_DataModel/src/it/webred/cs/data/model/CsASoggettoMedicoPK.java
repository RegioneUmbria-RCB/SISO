package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the CS_A_SOGGETTO_MEDICO database table.
 * 
 */
@Embeddable
public class CsASoggettoMedicoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="MEDICO_ID")
	private long medicoId;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FINE_APP")
	private java.util.Date dataFineApp;
	
	@Column(name="SOGGETTO_ANAGRAFICA_ID")
	private long soggettoAnagraficaId;

	public CsASoggettoMedicoPK() {
	}
	public long getMedicoId() {
		return this.medicoId;
	}
	public void setMedicoId(long medicoId) {
		this.medicoId = medicoId;
	}
	public java.util.Date getDataFineApp() {
		return this.dataFineApp;
	}
	public void setDataFineApp(java.util.Date dataFineApp) {
		this.dataFineApp = dataFineApp;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CsASoggettoMedicoPK)) {
			return false;
		}
		CsASoggettoMedicoPK castOther = (CsASoggettoMedicoPK)other;
		return 
			(this.medicoId == castOther.medicoId)
			&& this.dataFineApp.equals(castOther.dataFineApp)
			&& (this.soggettoAnagraficaId == castOther.soggettoAnagraficaId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.medicoId ^ (this.medicoId >>> 32)));
		hash = hash * prime + this.dataFineApp.hashCode();
		hash = hash * prime + ((int) (this.soggettoAnagraficaId ^ (this.soggettoAnagraficaId >>> 32)));
		
		return hash;
	}
	public long getSoggettoAnagraficaId() {
		return soggettoAnagraficaId;
	}
	public void setSoggettoAnagraficaId(long soggettoAnagraficaId) {
		this.soggettoAnagraficaId = soggettoAnagraficaId;
	}
	
	
}