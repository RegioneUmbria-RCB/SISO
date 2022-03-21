package it.webred.ct.data.model.docfa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class DocfaAnnotazioniPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Temporal( TemporalType.DATE)
	private Date fornitura;

	@Column(name="PROTOCOLLO_REG")
	private String protocolloReg;
	
	@Column(name="PROG_TRASC")
	private String progTrasc;

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
	 
	public void setProgTrasc(String progTrasc) {
		this.progTrasc = progTrasc;
	}

	public String getProgTrasc() {
		return progTrasc;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if(obj instanceof DocfaAnnotazioniPK){
			
		DocfaAnnotazioniPK pk = (DocfaAnnotazioniPK) obj;
		return fornitura.compareTo(pk.getFornitura()) ==0
				&& protocolloReg.equals(pk.getProtocolloReg())
				&& progTrasc.equals(pk.getProgTrasc());
		}
		return false;
	}
	
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fornitura.hashCode();
		hash = hash * prime + this.protocolloReg.hashCode();
		hash = hash * prime + this.progTrasc.hashCode();
		
		return hash;
    }
	   

}
