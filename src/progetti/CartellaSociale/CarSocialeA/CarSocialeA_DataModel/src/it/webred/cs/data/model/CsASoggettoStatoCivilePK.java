package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CS_A_SOGGETTO_STATO_CIVILE database table.
 * 
 */
@Embeddable
public class CsASoggettoStatoCivilePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="STATO_CIVILE_COD")
	private String statoCivileCod;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FINE_APP")
	private java.util.Date dataFineApp;

	@Column(name="SOGGETTO_ANAGRAFICA_ID")
	private long soggettoAnagraficaId;

	public CsASoggettoStatoCivilePK() {
	}
	public String getStatoCivileCod() {
		return this.statoCivileCod;
	}
	public void setStatoCivileCod(String statoCivileCod) {
		this.statoCivileCod = statoCivileCod;
	}
	public java.util.Date getDataFineApp() {
		return this.dataFineApp;
	}
	public void setDataFineApp(java.util.Date dataFineApp) {
		this.dataFineApp = dataFineApp;
	}
	public long getSoggettoAnagraficaId() {
		return this.soggettoAnagraficaId;
	}
	public void setSoggettoAnagraficaId(long soggettoAnagraficaId) {
		this.soggettoAnagraficaId = soggettoAnagraficaId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CsASoggettoStatoCivilePK)) {
			return false;
		}
		CsASoggettoStatoCivilePK castOther = (CsASoggettoStatoCivilePK)other;
		return 
			this.statoCivileCod.equals(castOther.statoCivileCod)
			&& this.dataFineApp.equals(castOther.dataFineApp)
			&& (this.soggettoAnagraficaId == castOther.soggettoAnagraficaId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.statoCivileCod.hashCode();
		hash = hash * prime + this.dataFineApp.hashCode();
		hash = hash * prime + ((int) (this.soggettoAnagraficaId ^ (this.soggettoAnagraficaId >>> 32)));
		
		return hash;
	}
}