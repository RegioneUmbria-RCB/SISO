package it.webred.ct.data.model.docfa;

import it.webred.ct.data.model.condono.CondonoCoordinatePK;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable
public class DocfaDatiMetriciPK implements Serializable {
	
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="IDENTIFICATIVO_IMMOBILE")
	private String identificativoImmobile;

	@Column(name="PROG_POLIGONO")
	private String progPoligono;

	public String getIdentificativoImmobile() {
		return identificativoImmobile;
	}

	public void setIdentificativoImmobile(String identificativoImmobile) {
		this.identificativoImmobile = identificativoImmobile;
	}

	public String getProgPoligono() {
		return progPoligono;
	}

	public void setProgPoligono(String progPoligono) {
		this.progPoligono = progPoligono;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if(obj instanceof DocfaDatiMetriciPK){
			
		DocfaDatiMetriciPK pk = (DocfaDatiMetriciPK) obj;
		return identificativoImmobile.equals(pk.getIdentificativoImmobile())
				&& progPoligono.equals(pk.getProgPoligono());
		}
		return false;
	}
	
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.identificativoImmobile.hashCode();
		hash = hash * prime + this.progPoligono.hashCode();
		
		return hash;
    }
}