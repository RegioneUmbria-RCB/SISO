package it.webred.ct.data.model.docfa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class DocfaDatiGeneraliPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="PROTOCOLLO_REG")
	private String protocolloReg;
	
	@Temporal( TemporalType.DATE)
	private Date fornitura;

	public String getProtocolloReg() {
		return protocolloReg;
	}

	public void setProtocolloReg(String protocolloReg) {
		this.protocolloReg = protocolloReg;
	}

	public Date getFornitura() {
		return fornitura;
	}
	
	public void setFornitura(Date fornitura) {
		this.fornitura = fornitura;
	}
	 
	
	public boolean equals(Object o) {
		if (! (o instanceof DocfaDatiGeneraliPK) )
			return false;
		DocfaDatiGeneraliPK apk = (DocfaDatiGeneraliPK) o;
		return (this.fornitura.compareTo(apk.getFornitura()) ==0 && this.protocolloReg.equals(apk.getProtocolloReg()) ) ;
				
	}
	
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.protocolloReg.hashCode();
		hash = hash * prime + this.fornitura.hashCode();
		
		
		return hash;
    }

  
	   

}
