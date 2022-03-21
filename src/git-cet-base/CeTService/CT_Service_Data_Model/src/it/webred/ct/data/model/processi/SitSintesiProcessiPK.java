package it.webred.ct.data.model.processi;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SIT_SINTESI_PROCESSI database table.
 * 
 */
@Embeddable
public class SitSintesiProcessiPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String processid;

	@Column(name="NOME_TABELLA")
	private String nomeTabella;

    public SitSintesiProcessiPK() {
    }
	public String getProcessid() {
		return this.processid;
	}
	public void setProcessid(String processid) {
		this.processid = processid;
	}
	public String getNomeTabella() {
		return this.nomeTabella;
	}
	public void setNomeTabella(String nomeTabella) {
		this.nomeTabella = nomeTabella;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SitSintesiProcessiPK)) {
			return false;
		}
		SitSintesiProcessiPK castOther = (SitSintesiProcessiPK)other;
		return 
			this.processid.equals(castOther.processid)
			&& this.nomeTabella.equals(castOther.nomeTabella);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.processid.hashCode();
		hash = hash * prime + this.nomeTabella.hashCode();
		
		return hash;
    }
}