package it.webred.ct.data.model.docfa;


import it.webred.ct.data.model.locazioni.LocazioneAPK;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;


public class DocfaUiuPK implements Serializable {
	private Date fornitura;
	
	@Column(name="PROTOCOLLO_REG")
	private String protocolloReg;
	
	@Column(name="NR_PROG")
	private BigDecimal nrProg;
	
	public Date getFornitura() {
		return fornitura;
	}
	public void setFornitura(Date fornitura) {
		this.fornitura = fornitura;
	}
	public String getProtocolloReg() {
		return protocolloReg;
	}
	public void setProtocolloReg(String protocolloReg) {
		this.protocolloReg = protocolloReg;
	}
	public BigDecimal getNrProg() {
		return nrProg;
	}
	public void setNrProg(BigDecimal nrProg) {
		this.nrProg = nrProg;
	}
	
	public boolean equals(Object o) {
		if (! (o instanceof DocfaUiuPK) )
			return false;
		DocfaUiuPK apk = (DocfaUiuPK) o;
		return (this.fornitura.compareTo(apk.getFornitura()) ==0 && this.protocolloReg.equals(apk.getProtocolloReg()) &&
				this.nrProg.compareTo(apk.getNrProg())==0 )  ;
				
	}
	
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.protocolloReg.hashCode();
		hash = hash * prime + ((int) (this.nrProg.hashCode() ^ (this.nrProg.hashCode() >>> 32)));
		hash = hash * prime + this.fornitura.hashCode();
		
		
		return hash;
    }
}
