package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CsOOpsettoreAlertConfigPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="OPERATORE_SETTORE_ID")
    private Long operatoreSettoreId;
	
	@Column(name="TIPO_COD")
	private String tipoCod;
	
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CsOOpsettoreAlertConfigPK)) {
			return false;
		}
		CsOOpsettoreAlertConfigPK castOther = (CsOOpsettoreAlertConfigPK)other;
		return 
			(this.operatoreSettoreId == castOther.operatoreSettoreId)
			&& (this.tipoCod == castOther.tipoCod);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.operatoreSettoreId.intValue();
		hash = hash * prime + this.tipoCod.hashCode();
		
		return hash;
	}

	public Long getOperatoreSettoreId() {
		return operatoreSettoreId;
	}

	public String getTipoCod() {
		return tipoCod;
	}

	public void setOperatoreSettoreId(Long operatoreSettoreId) {
		this.operatoreSettoreId = operatoreSettoreId;
	}

	public void setTipoCod(String tipoCod) {
		this.tipoCod = tipoCod;
	}

}