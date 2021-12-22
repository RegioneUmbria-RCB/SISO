package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CsOOpsettoreAuthDownloadPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="OPERATORE_SETTORE_ID")
    private Long operatoreSettoreId;
	
	@Column(name="SOTTOCARTELLA_DOC_ID")
	private Long sottocartellaDocId;
	
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CsOOpsettoreAuthDownloadPK)) {
			return false;
		}
		CsOOpsettoreAuthDownloadPK castOther = (CsOOpsettoreAuthDownloadPK)other;
		return 
			(this.operatoreSettoreId == castOther.operatoreSettoreId)
			&& (this.sottocartellaDocId == castOther.sottocartellaDocId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.operatoreSettoreId.intValue();
		hash = hash * prime + this.sottocartellaDocId.hashCode();
		
		return hash;
	}

	public Long getOperatoreSettoreId() {
		return operatoreSettoreId;
	}

	public void setOperatoreSettoreId(Long operatoreSettoreId) {
		this.operatoreSettoreId = operatoreSettoreId;
	}

	public Long getSottocartellaDocId() {
		return sottocartellaDocId;
	}

	public void setSottocartellaDocId(Long sottocartellaDocId) {
		this.sottocartellaDocId = sottocartellaDocId;
	}
}