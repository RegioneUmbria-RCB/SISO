package it.webred.ct.data.model.catasto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The primary key class for the SITIUIU database table.
 * 
 */
@Embeddable
public class SiticiviUiuPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	@Column(name="PKID_CIVI")
	private BigDecimal pkidCivi;

	@Column(name="PKID_UIU")
	private BigDecimal pkidUiu;
	
	@Temporal( TemporalType.DATE)
	@Column(name="DATA_FINE_VAL")
	private Date dataFineVal;
	
	public BigDecimal getPkidCivi() {
		return this.pkidCivi;
	}

	public void setPkidCivi(BigDecimal pkidCivi) {
		this.pkidCivi = pkidCivi;
	}

	public BigDecimal getPkidUiu() {
		return this.pkidUiu;
	}

	public void setPkidUiu(BigDecimal pkidUiu) {
		this.pkidUiu = pkidUiu;
	}
	public Date getDataFineVal() {
		return this.dataFineVal;
	}

	public void setDataFineVal(Date dataFineVal) {
		this.dataFineVal = dataFineVal;
	}
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ConsUfreTabPK)) {
			return false;
		}
		SiticiviUiuPK castOther = (SiticiviUiuPK)other;
		return 
			(this.pkidCivi  == castOther.pkidCivi)
			&& this.pkidUiu.equals(castOther.pkidCivi)
			&& this.dataFineVal.equals(castOther.dataFineVal);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.pkidCivi.hashCode();
		hash = hash * prime + this.pkidUiu.hashCode();
		hash = hash * prime + this.dataFineVal.hashCode();
		
		return hash;
    }
}
