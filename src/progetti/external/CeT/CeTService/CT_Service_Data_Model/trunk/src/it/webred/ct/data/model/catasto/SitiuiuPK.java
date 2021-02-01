package it.webred.ct.data.model.catasto;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * The primary key class for the SITIUIU database table.
 * 
 */
@Embeddable
public class SitiuiuPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	 
	@Column(name="COD_NAZIONALE")
	private String codNazionale;

	private long foglio;

	private String particella;
	
	private String sub;

	private long unimm;

	@Temporal( TemporalType.DATE)
	@Column(name="DATA_FINE_VAL")
	private java.util.Date dataFineVal;
    
	@Column(name="PKID_UIU")
	private BigDecimal pkidUiu;
    
    public SitiuiuPK() {
    }
	public String getCodNazionale() {
		return this.codNazionale;
	}
	public void setCodNazionale(String codNazionale) {
		this.codNazionale = codNazionale;
	}
	public long getFoglio() {
		return this.foglio;
	}
	public void setFoglio(long foglio) {
		this.foglio = foglio;
	}
	public String getParticella() {

		String s = this.particella;
		while(s.charAt(0) == '0' && s.length()>1){
			s = s.substring(1);
	}
		return s;
	}
	public void setParticella(String particella) {
		
		this.particella = particella;
	}
	public String getSub() {
		return this.sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public long getUnimm() {
		return this.unimm;
	}
	public void setUnimm(long unimm) {
		this.unimm = unimm;
	}
	public java.util.Date getDataFineVal() {
		return this.dataFineVal;
	}
	public void setDataFineVal(java.util.Date dataFineVal) {
		this.dataFineVal = dataFineVal;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SitiuiuPK)) {
			return false;
		}
		SitiuiuPK castOther = (SitiuiuPK)other;
		return 
			this.codNazionale.equals(castOther.codNazionale)
			&& (this.foglio == castOther.foglio)
			&& this.particella.equals(castOther.particella)
			&& this.sub.equals(castOther.sub)
			&& (this.unimm == castOther.unimm)
			&& this.dataFineVal.equals(castOther.dataFineVal);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.codNazionale.hashCode();
		hash = hash * prime + ((int) (this.foglio ^ (this.foglio >>> 32)));
		hash = hash * prime + this.particella.hashCode();
		hash = hash * prime + this.sub.hashCode();
		hash = hash * prime + ((int) (this.unimm ^ (this.unimm >>> 32)));
		hash = hash * prime + this.dataFineVal.hashCode();
		
		return hash;
    }
	public void setPkidUiu(BigDecimal pkidUiu) {
		this.pkidUiu = pkidUiu;
	}
	public BigDecimal getPkidUiu() {
		return pkidUiu;
	}
}