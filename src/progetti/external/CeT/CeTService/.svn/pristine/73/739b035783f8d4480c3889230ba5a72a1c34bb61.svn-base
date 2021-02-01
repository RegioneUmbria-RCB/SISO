package it.webred.ct.data.model.tarsu;

import it.webred.ct.data.model.annotation.IndiceEntity;
import it.webred.ct.data.model.annotation.IndiceKey;
import it.webred.ct.data.model.ici.VTIciCiviciAll;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the V_T_TAR_CIVICI_ALL database table.
 * 
 */
@Entity
@Table(name="V_T_TAR_CIVICI_ALL")
@IndiceEntity(sorgente="2")
public class VTTarCiviciAll implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@IndiceKey(pos="1")
	private String id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@IndiceKey(pos="2")
	@Column(name="NUM_CIV")
	private String numCiv;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@IndiceKey(pos="3")
	@Column(name="ESP_CIV")
	private String espCiv;
	
    public VTTarCiviciAll() {
    }

	public String getEspCiv() {
		return this.espCiv;
	}

	public void setEspCiv(String espCiv) {
		this.espCiv = espCiv;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumCiv() {
		return this.numCiv;
	}

	public void setNumCiv(String numCiv) {
		this.numCiv = numCiv;
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof VTTarCiviciAll)) {
			return false;
		}
		VTTarCiviciAll castOther = (VTTarCiviciAll)other;
		return 
			this.id.equals(castOther.id)
			&& this.numCiv.equals(castOther.numCiv)
			&& this.espCiv.equals(castOther.espCiv);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id.hashCode();
		hash = hash * prime + this.numCiv.hashCode();
		hash = hash * prime + this.espCiv.hashCode();
		
		return hash;
    }

}