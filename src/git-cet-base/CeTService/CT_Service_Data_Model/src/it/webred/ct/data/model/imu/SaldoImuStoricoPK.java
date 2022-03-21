package it.webred.ct.data.model.imu;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SALDO_IMU_STORICO database table.
 * 
 */
@Embeddable
public class SaldoImuStoricoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String codfisc;

	private long progressivo;

    public SaldoImuStoricoPK() {
    }
	public String getCodfisc() {
		return this.codfisc;
	}
	public void setCodfisc(String codfisc) {
		this.codfisc = codfisc;
	}
	public long getProgressivo() {
		return this.progressivo;
	}
	public void setProgressivo(long progressivo) {
		this.progressivo = progressivo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SaldoImuStoricoPK)) {
			return false;
		}
		SaldoImuStoricoPK castOther = (SaldoImuStoricoPK)other;
		return 
			this.codfisc.equals(castOther.codfisc)
			&& (this.progressivo == castOther.progressivo);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.codfisc.hashCode();
		hash = hash * prime + ((int) (this.progressivo ^ (this.progressivo >>> 32)));
		
		return hash;
    }
}