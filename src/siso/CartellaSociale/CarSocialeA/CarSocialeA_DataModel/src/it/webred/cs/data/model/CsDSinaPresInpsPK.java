package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.Column;

public class CsDSinaPresInpsPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "SINA_ID")
	private long sinaId;

	@Column(name = "PRESTAZIONE_INPS_ID")
	private String prestazioneInpsId;

	public CsDSinaPresInpsPK() {
	}

	public long getSinaId() {
		return sinaId;
	}

	public void setSinaId(long sinaId) {
		this.sinaId = sinaId;
	}
	
	public String getPrestazioneInpsId() {
		return prestazioneInpsId;
	}

	public void setPrestazioneInpsId(String prestazioneInpsId) {
		this.prestazioneInpsId = prestazioneInpsId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CsDSinaPresInpsPK)) {
			return false;
		}
		CsDSinaPresInpsPK castOther = (CsDSinaPresInpsPK)other;
		return 
			(this.sinaId == castOther.sinaId) && (this.prestazioneInpsId.equals(castOther.prestazioneInpsId));
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.sinaId ^ (this.sinaId >>> 32)));
		hash = hash * this.prestazioneInpsId.hashCode();
		
		return hash;
	}
}
