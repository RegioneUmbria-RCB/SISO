package it.webred.ct.data.model.docfa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

@Embeddable
public class DocfaInPartePK implements Serializable {
	
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

    @Temporal( TemporalType.DATE)
	private Date fornitura;

	@Column(name="PROTOCOLLO_REG")
	private String protocolloReg;

	@Column(name="NR_PROG")
	private BigDecimal nrProg;

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

	public Date getFornitura() {
		return this.fornitura;
	}

	public void setFornitura(Date fornitura) {
		this.fornitura = fornitura;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if(obj instanceof DocfaInPartePK){
			
		DocfaInPartePK pk = (DocfaInPartePK) obj;
		return fornitura.compareTo(pk.getFornitura()) ==0
				&& protocolloReg.equals(pk.getProtocolloReg())
				&& nrProg.equals(pk.getNrProg());
		}
		return false;
	}
	
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fornitura.hashCode();
		hash = hash * prime + this.protocolloReg.hashCode();
		hash = hash * prime + ((int) (this.nrProg.hashCode() ^ (this.nrProg.hashCode() >>> 32)));
		
		return hash;
    }
	
}