package it.webred.ct.data.model.docfa;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


@Embeddable
public class DocfaFogliMicrozonaPK implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private String foglio;
	
	private String zc;

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getZc() {
		return zc;
	}

	public void setZc(String zc) {
		this.zc = zc;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if(obj instanceof DocfaFogliMicrozonaPK){
			
		DocfaFogliMicrozonaPK pk = (DocfaFogliMicrozonaPK) obj;
		return foglio.equals(pk.getFoglio())
				&& zc.equals(pk.getZc());
		}
		return false;
	}
	
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.foglio.hashCode();
		hash = hash * prime + this.zc.hashCode();
		
		return hash;
    }
}