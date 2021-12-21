package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CS_PAI_FASE_CHIUSURA database table.
 * 
 */
@Embeddable
public class CsPaiFaseChiusuraPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="TIPO_PAI")
	private long tipoPai;

	private long fase;

	@Column(name="MOTIVO_CHIUSURA")
	private long motivoChiusura;

	public CsPaiFaseChiusuraPK() {
	}
	public long getTipoPai() {
		return this.tipoPai;
	}
	public void setTipoPai(long tipoPai) {
		this.tipoPai = tipoPai;
	}
	public long getFase() {
		return this.fase;
	}
	public void setFase(long fase) {
		this.fase = fase;
	}
	public long getMotivoChiusura() {
		return this.motivoChiusura;
	}
	public void setMotivoChiusura(long motivoChiusura) {
		this.motivoChiusura = motivoChiusura;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CsPaiFaseChiusuraPK)) {
			return false;
		}
		CsPaiFaseChiusuraPK castOther = (CsPaiFaseChiusuraPK)other;
		return 
			(this.tipoPai == castOther.tipoPai)
			&& (this.fase == castOther.fase)
			&& (this.motivoChiusura == castOther.motivoChiusura);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.tipoPai ^ (this.tipoPai >>> 32)));
		hash = hash * prime + ((int) (this.fase ^ (this.fase >>> 32)));
		hash = hash * prime + ((int) (this.motivoChiusura ^ (this.motivoChiusura >>> 32)));
		
		return hash;
	}
	
}
	
