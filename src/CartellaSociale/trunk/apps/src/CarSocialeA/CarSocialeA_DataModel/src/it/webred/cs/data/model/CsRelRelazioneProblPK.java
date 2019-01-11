package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The primary key class for the CS_REL_RELAZIONE_PROB database table.
 * 
 */
@Embeddable
public class CsRelRelazioneProblPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="RELAZIONE_DIARIO_ID")
	private long relazioneDiarioId;
	
	@Column(name="PROB_ID")
	private long problId;
	
	public CsRelRelazioneProblPK()
	{
		super();
	}
	
	public CsRelRelazioneProblPK(long relazioneDiarioId, long problId)
	{
		this.relazioneDiarioId=relazioneDiarioId;
		this.problId=problId;
	}
	
	public long getRelazioneDiarioId() {
		return relazioneDiarioId;
	}

	public void setRelazioneDiarioId(long relazioneDiarioId) {
		this.relazioneDiarioId = relazioneDiarioId;
	}

	public long getProblId() {
		return problId;
	}

	public void setProblId(long probId) {
		this.problId = probId;
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CsRelRelazioneProblPK)) {
			return false;
		}
		CsRelRelazioneProblPK castOther = (CsRelRelazioneProblPK)other;
		return 
			(this.relazioneDiarioId == castOther.relazioneDiarioId)
			&& (this.problId == castOther.problId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.relazioneDiarioId ^ (this.relazioneDiarioId >>> 32)));
		hash = hash * prime + ((int) (this.problId ^ (this.problId >>> 32)));
		
		return hash;
	}
}