package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The primary key class for the CS_REL_RELAZIONE_TIPOINT database table.
 * 
 */
@Embeddable
public class CsTbScuolaAnnoPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="SCUOLA_ID")
	private String scuolaId;
	
	@Column(name="ANNO_ID")
	private Long annoId;

	public CsTbScuolaAnnoPK()
	{
		super();
	}
	
	public String getScuolaId() {
		return scuolaId;
	}

	public void setScuolaId(String scuolaId) {
		this.scuolaId = scuolaId;
	}

	public Long getAnnoId() {
		return annoId;
	}

	public void setAnnoId(Long annoId) {
		this.annoId = annoId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CsTbScuolaAnnoPK)) {
			return false;
		}
		CsTbScuolaAnnoPK castOther = (CsTbScuolaAnnoPK)other;
		return 
			(this.scuolaId == castOther.scuolaId)
			&& (this.annoId == castOther.annoId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.scuolaId.hashCode()));
		hash = hash * prime + ((int) (this.annoId ^ (this.annoId >>> 32)));
		
		return hash;
	}
}