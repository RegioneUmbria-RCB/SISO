package it.webred.ct.data.model.concedilizie;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;

public class ConcEdilizieVisurePK implements Serializable {
	

	@Column(name="TIPO_ATTO")
	private String tipoAtto;
	
	private BigDecimal inxdoc;


	public String getTipoAtto() {
		return tipoAtto;
	}

	public void setTipoAtto(String tipoAtto) {
		this.tipoAtto = tipoAtto;
	}

	public BigDecimal getInxdoc() {
		return inxdoc;
	}

	public void setInxdoc(BigDecimal inxdoc) {
		this.inxdoc = inxdoc;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if(obj instanceof ConcEdilizieVisurePK){
			
		ConcEdilizieVisurePK pk = (ConcEdilizieVisurePK) obj;
		return tipoAtto.equals(pk.getTipoAtto())
				&& inxdoc.equals(pk.getInxdoc());
		}
		return false;
	}
	
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.tipoAtto.hashCode();
		hash = hash * prime + ((int) (this.inxdoc.hashCode() ^ (this.inxdoc.hashCode() >>> 32)));		
		return hash;
    }
	
}
