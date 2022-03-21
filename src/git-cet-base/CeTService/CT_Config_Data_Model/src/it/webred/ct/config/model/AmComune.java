package it.webred.ct.config.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the AM_COMUNE database table.
 * 
 */
@Entity
@Table(name="AM_COMUNE")
public class AmComune implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String belfiore;

	private String descrizione;

	//bi-directional many-to-many association to AmInstance
	@ManyToMany(mappedBy="amComunes")
	private List<AmInstance> amInstances;

    public AmComune() {
    }

	public String getBelfiore() {
		return this.belfiore;
	}

	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<AmInstance> getAmInstances() {
		return this.amInstances;
	}

	public void setAmInstances(List<AmInstance> amInstances) {
		this.amInstances = amInstances;
	}
	
}